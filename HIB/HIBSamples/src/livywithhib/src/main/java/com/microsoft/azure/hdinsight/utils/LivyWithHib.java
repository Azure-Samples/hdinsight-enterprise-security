package com.microsoft.azure.hdinsight.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.lang.StringBuilder;
import java.io.*;
import java.net.*;

/**
 * Livy / Spark submit with HIB.
 */
public class LivyWithHib {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LivyWithHib.class);

        if (args.length == 0 || args.length > 2) {
            System.out.println("Invalid usage: com.microsoft.azure.hdinsight.utils.LivyWithHib <clustername> <fileName>");
            return;
        }

        String clusterName = args[0];
        String fileName = args[1];

        System.out.printf("Livy connection started for cluster: %s, fileName: %s\n", clusterName, fileName);

        try {
            OAuthHelper oAuthHelper = new OAuthHelper(null, null, null);

            TokenResult tokenResult = oAuthHelper.getNewToken(OAuthHelper.HIB_RESOURCE, true);
            // System.out.printf("OAuth token obtained successfully: %s\n", tokenResult.getAccessToken());
            logger.info("OAuth token obtained successfully");

            String requestUrl = String.format("https://%s.azurehdinsight.net/livy/batches", clusterName);
            
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            LivyRequest livyRequest = gson.fromJson(br, LivyRequest.class);
            
            System.out.printf("Livy request payload: %s\n", gson.toJson(livyRequest));
            
            String authorizationHeader = String.format("Bearer %s", tokenResult.getAccessToken());
            ApiResponse apiResponse = HttpHelper.makeRequest(authorizationHeader, requestUrl, "POST", gson.toJson(livyRequest), getDefaultRequestHeaders());

            //System.out.printf("Http status code: %s\n", apiResponse.getHttpStatusCode());
            logger.info("Http status code: " + apiResponse.getHttpStatusCode());
            logger.info("Http response: " + apiResponse.getResponseBody());
            System.out.printf("Response body: %s\n", apiResponse.getResponseBody());           
        } catch (Exception exc) {
            System.out.printf(String.format("%s\n", exc.toString()));
            return;
        }
    }

    private static HashMap<String, String> getDefaultRequestHeaders() {
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Accept", "application/x-thrift");
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("X-Requested-By", "LivyWithHib");

        return requestHeaders;
    }
}
