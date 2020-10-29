package com.microsoft.azure.hdinsight.utils;

import java.util.*;

/*
 * Class for encapsulating a HTTP api response.
*/
public class ApiResponse {
  private String RequestUrl;
  private String ResponseBody; 
  private Map<String, String> ResponseHeaders;
  private int HttpStatusCode;
  private String KerberosTicket;
  private String OAuthAccessToken;

  public ApiResponse() {
    super();
  }

  public String getRequestUrl() {
    return this.RequestUrl;
  }

  public void setRequestUrl(String requestUrl) {
    this.RequestUrl = requestUrl;
  }

  public String getResponseBody() {
    return this.ResponseBody;
  }

  public void setResponseBody(String responseBody) {
    this.ResponseBody = responseBody;
  }

  public Map<String, String> getResponseHeaders() {
    return this.ResponseHeaders;
  }

  public void setResponseHeaders(Map<String, String> responseHeaders) {
    this.ResponseHeaders = responseHeaders;
  }

  public int getHttpStatusCode() {
    return this.HttpStatusCode;
  }

  public void setHttpStatusCode(int httpStatusCode) {
    this.HttpStatusCode = httpStatusCode;
  }

  public String getKerberosTicket() {
    return this.KerberosTicket;
  }

  public void setKerberosTicket(String kerberosTicket) {
    this.KerberosTicket = kerberosTicket;
  }

  public String getOAuthAccessToken() {
    return this.OAuthAccessToken;
  }

  public void setOAuthAccessToken(String oauthAccessToken) {
    this.OAuthAccessToken = oauthAccessToken;
  }
}
