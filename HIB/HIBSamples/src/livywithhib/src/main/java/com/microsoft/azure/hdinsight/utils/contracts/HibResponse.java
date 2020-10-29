package com.microsoft.azure.hdinsight.utils;

/*
 * Class for deserializing the HIB response.
*/
public class HibResponse {
  private String UserPrincipalName;
  private String SamAccountName;
  private String Realm;
  private String ServicePrincipalName;
  private String KerberosTicket;
  private String CorrelationId;
  private String ResponseTimestamp;
  private String Code;
  private String Message;
  private int HttpStatusCode;

  public HibResponse() {
    super();
  }

  public HibResponse(
      String userPrincipalName, 
      String samAccountName, 
      String realm, 
      String servicePrincipalName, 
      String kerberosTicket,
      String correlationId,
      String responseTimestamp) {
    super();

    UserPrincipalName = userPrincipalName;
    SamAccountName = samAccountName;
    Realm = realm;
    ServicePrincipalName = servicePrincipalName;
    KerberosTicket = kerberosTicket;
    CorrelationId = correlationId;
    ResponseTimestamp = responseTimestamp;
  }

  public HibResponse(
      String code, 
      String message, 
      String correlationId,
      String responseTimestamp) {
    super();
  }

  public String getUserPrincipalName() {
    return UserPrincipalName;
  }

  public String getSamAccountName() {
    return SamAccountName;
  }

  public String getRealm() {
    return Realm;
  }

  public String getServicePrincipalName() {
    return ServicePrincipalName;
  }

  public String getKerberosTicket() {
    return KerberosTicket;
  }

  public String getCorrelationId() {
    return CorrelationId;
  }

  public String getResponseTimestamp() {
    return ResponseTimestamp;
  }

  public String getCode() {
    return Code;
  }

  public String getMessage() {
    return Message;
  }

  public int getHttpStatusCode() {
    return HttpStatusCode;
  }

  public void setUserPrincipalName(String userPrincipalName) {
    UserPrincipalName = userPrincipalName;
  }

  public void setSamAccountName(String samAccountName) {
    SamAccountName = samAccountName;
  }

  public void setRealm(String realm) {
    Realm = realm;
  }

  public void setServicePrincipalName(String servicePrincipalName) {
    ServicePrincipalName = servicePrincipalName;
  }

  public void setKerberosTicket(String kerberosTicket) {
    KerberosTicket = kerberosTicket;
  }

  public void setCorrelationId(String correlationId) {
    CorrelationId = correlationId;
  }

  public void setResponseTimestamp(String responseTimestamp) {
    ResponseTimestamp = responseTimestamp;
  }

  public void setCode(String code) {
    Code = code;
  }

  public void setMessage(String message) {
    Message = message;
  }

  public void setHttpStatusCode(int httpStatusCode) {
    HttpStatusCode = httpStatusCode;
  }
}
