package com.microsoft.azure.hdinsight.utils;

import java.util.HashMap;

/**
 * Beeline with HIB.
 * We get Hive Server2 auth cookie by making a gateway connection and use the cookie in the beeline connection string
 * This outputs the JDBC connection string to the console which is then picked up by a wrapper script to launch beeline
 */
public class BeelineWithHib {
    public final static String HOST_HEADERNAME = "x-ms-hdi-host";
    public final static String COOKIE_HEADERNAME = "Set-Cookie";
    public final static String ERROR_HEADERNAME = "x-ms-hdi-errorcode";
    public final static String HIVE_JDBC_PREFIX = "jdbc:hive2://";
    public final static String PORT_AND_TRANSPORTMODE = ":10001/;transportMode=http;http.cookie.";

    private static String hostName = "";
    private static String cookie = "";
    private static String errorCode = "";
    private static String beelineConnectionString = "";
    private static String httpStatusResponse = "";


    public static void main(String[] args) {

        if (args.length == 0 || args.length > 1) {
            System.out.println("Invalid usage: BeelineWithHib <clustername>.");
            return;
        }

        String clusterName = args[0];

        System.out.printf("Beeline connection started for cluster: %s, using: deviceFlow\n", clusterName);

        try {
            OAuthHelper oAuthHelper = new OAuthHelper(null, null, null);

            TokenResult tokenResult = oAuthHelper.getNewToken(OAuthHelper.HIB_RESOURCE, true);
            String requestUrl = String.format("https://%s.azurehdinsight.net/hive2", clusterName);
            String authorizationHeader = String.format("Bearer %s", tokenResult.getAccessToken());
            ApiResponse apiResponse = HttpHelper.makeRequest(authorizationHeader, requestUrl, "POST", "\n\nOpenSession\n\n", getDefaultRequestHeaders());

            if (apiResponse.getResponseHeaders() != null) {
                processHeaderName(apiResponse);
            }

            if (!hostName.equalsIgnoreCase("") && !cookie.equalsIgnoreCase("")) {
                beelineConnectionString = String.format("'%s%s%s%s'",HIVE_JDBC_PREFIX, hostName, PORT_AND_TRANSPORTMODE, cookie);
            }

            if (apiResponse.getHttpStatusCode() == 200 || apiResponse.getHttpStatusCode() == 500) {
                System.out.printf("authentication is successful\n");
                System.out.printf("JDBC URL for beeline connection: %s\n", beelineConnectionString);
            }
            else {
                System.err.printf("authentication is not successful, http status code: %s\nerror code: %s\n", httpStatusResponse, errorCode);
            }

        } catch (Exception exc) {
            System.out.printf("Caught exception\n");
            System.out.printf(String.format("%s\n", exc.toString()));
            System.exit(1);
        }
    }

    private static HashMap<String, String> getDefaultRequestHeaders() {
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Accept", "application/x-thrift");
        requestHeaders.put("Content-Type", "application/x-thrift");
        requestHeaders.put("Content-Length", "0");

        return requestHeaders;
    }

    private static void processHeaderName(ApiResponse apiResponse) {
        for (String headerName : apiResponse.getResponseHeaders().keySet()) {
            String value = apiResponse.getResponseHeaders().get(headerName);

            if (headerName != null) {
                if (HOST_HEADERNAME.equalsIgnoreCase(headerName)) {
                    hostName = value.substring(0,value.length()-1);
                }
                else if (COOKIE_HEADERNAME.equalsIgnoreCase(headerName)) {
                    String[] parts = value.split(" HttpOnly");
                    cookie = parts[0];
                }
                else if (ERROR_HEADERNAME.equalsIgnoreCase(headerName)){
                    errorCode = value;
                }
            }
            else {
                httpStatusResponse = value;
            }
        }
    }
}
