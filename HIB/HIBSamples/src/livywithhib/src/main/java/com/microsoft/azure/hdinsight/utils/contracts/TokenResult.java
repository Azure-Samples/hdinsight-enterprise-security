package com.microsoft.azure.hdinsight.utils;

public class TokenResult {
  private String userPrincipalName;
  private String shortUserName;
  private String refreshToken;
  private String accessToken;
  private String idToken;
  private String kerberosTicket;

  public TokenResult(String _userPrincipalName) {
    userPrincipalName = _userPrincipalName;
  }

  public TokenResult(String _userPrincipalName, String _shortUserName) {
    userPrincipalName = _userPrincipalName;
    shortUserName = _shortUserName;
  }

  public String getUserPrincipalName() {
    return userPrincipalName;
  }

  public String getShortUserName() {
    return shortUserName;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String _accessToken) {
    accessToken = _accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String _refreshToken) {
    refreshToken = _refreshToken;
  }

  public String getIdToken() {
    return idToken;
  }

  public void setIdToken(String _idToken) {
    idToken = _idToken;
  }

  public String getKerberosTicket() {
    return this.kerberosTicket;
  }

  public void setKerberosTicket(String _kerberosTicket) {
    this.kerberosTicket = _kerberosTicket;
  }
}
