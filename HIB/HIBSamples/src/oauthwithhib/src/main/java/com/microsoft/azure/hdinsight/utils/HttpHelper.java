/**
 * Http utils for extended tests
 */
package com.microsoft.azure.hdinsight.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http helper class
 *
 */
public class HttpHelper {
    public static ApiResponse makeRequest(
    		String authorizationHeader, 
    		String requestUrl, 
    		String httpMethod, 
    		String requestBody,
    		HashMap<String, String> requestHeaders) throws IOException {

      ApiResponse apiResponse = new ApiResponse();

      URL url = new URL(requestUrl);
      apiResponse.setRequestUrl(url.toString());
      System.out.println("Http request: " + httpMethod + " " + url.toString());
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod(httpMethod);
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setUseCaches(false);
      
      if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
    	  conn.setRequestProperty("Authorization", authorizationHeader);
      }

      if (requestHeaders != null)
      {
    	  for(Map.Entry<String, String> entry : requestHeaders.entrySet()) 
    	  {
    		  conn.setRequestProperty(entry.getKey(), entry.getValue());
    	  }
      }
   	  
      if (requestBody != null && !requestBody.isEmpty()) {
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(requestBody);
        wr.flush();
      }
 
      apiResponse.setHttpStatusCode(conn.getResponseCode());
      StringBuilder response;
      BufferedReader bufferedReader = null;

      try {
        InputStream is = null;
        if (200 <= apiResponse.getHttpStatusCode() && apiResponse.getHttpStatusCode() <= 399) {
          is = conn.getInputStream();
        } else {
          is = conn.getErrorStream();
        }

        if (is != null) {
          bufferedReader = new BufferedReader(new InputStreamReader(is));
        }

        String inputLine;
        response = new StringBuilder();
        while (bufferedReader != null && (inputLine = bufferedReader.readLine()) != null) {
          response.append(inputLine);
        }
        
        apiResponse.setResponseBody(response.toString());
                  
      } finally {
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      }
      
      Map<String, List<String>> map = conn.getHeaderFields();
      
      for(Map.Entry<String, List<String>> entry : map.entrySet()) {
    	  if (apiResponse.getResponseHeaders() == null) {
    		  apiResponse.setResponseHeaders(new HashMap<String, String>());
    	  }
   	  
    	  StringBuilder sb = new StringBuilder();
    	  
    	  for (int i = 0; i < entry.getValue().size(); ++i) {
              sb.append(entry.getValue().get(i));
              sb.append(';');
          }

    	  apiResponse.getResponseHeaders().put(entry.getKey(), sb.toString());
      }

      return apiResponse;
    }
}
