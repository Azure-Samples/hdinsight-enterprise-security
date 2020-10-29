package com.microsoft.azure.hdinsight.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.naming.ServiceUnavailableException;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.DeviceCode;

/**
 * Uses ADAL to acquire tokens. Caches them locally for efficiency.
 */
public class OAuthHelper {
    private final static String AUTHORITY = "https://login.microsoftonline.com/common/";
    private final static String CLIENT_ID = "";
    private final static String POWERSHELL_CLIENT_ID = "1950a258-227b-4e31-a9cf-717495945fc2";
    public final static String HIB_RESOURCE = "https://hib.azurehdinsight.net";

    private String UserPrincipalName;
    private String Password;
    private String TenantId;

    public OAuthHelper(String userPrincipalName, String password, String tenantId) {
        this.UserPrincipalName = userPrincipalName;
        this.Password = password;
        this.TenantId = tenantId;
    }

    /**
     * Get access token using device flow if interactive auth is enabled else use the password grant flow (ROPC).
     * @param resourceId
     *            Identifier of the target resource that is the recipient of the
     *            requested token.
     * @param useInteractiveAuth
     *            When this is set true uses device flow to get access token
     * @return
     *      It returns Access Token
     * @throws Exception
     */
    public TokenResult getNewToken(String resourceId, boolean useInteractiveAuth) throws Exception {
        AuthenticationResult result = null;
        TokenResult tokenResult = null;

        if (useInteractiveAuth) {
            result = this.getAccessTokenUsingDeviceCodeFlow(resourceId);
        } else {
            result = this.getAccessTokenUsingROPC(resourceId);
        }

        //Uncomment for debugging
        //System.out.println("Access Token - " + result.getAccessToken());
        //System.out.println("Refresh Token - " + result.getRefreshToken());
        //System.out.println("ID Token - " + result.getIdToken());

        tokenResult = new TokenResult(this.UserPrincipalName);
        tokenResult.setAccessToken(result.getAccessToken());
        tokenResult.setRefreshToken(result.getRefreshToken());
        tokenResult.setIdToken(result.getIdToken());

        return tokenResult;
    }

    /**
     * Get the oauth access token using the password grant flow (ROPC). This method calls the acquireToken method of AuthenticationContext
     * @param resourceId
     *            Identifier of the target resource that is the recipient of the
     *            requested token.
     * @return  Returns AuthenticationResult. It contains Access
     *         Token and the Access Token's expiration time. Refresh Token
     *         property will be null for this overload.
     * @throws AuthenticationException
     */
    private AuthenticationResult getAccessTokenUsingROPC(String resourceId) throws Exception {
        //Uncomment for debugging
        //System.out.println("ROPC flow invoked to get access token");

        AuthenticationContext context = null;
        AuthenticationResult result = null;
        ExecutorService service = null;

        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(AUTHORITY.replace("common", this.TenantId), true, service);
            Future<AuthenticationResult> futureResult = context.acquireToken(
                    resourceId,
                    POWERSHELL_CLIENT_ID,
                    this.UserPrincipalName,
                    this.Password,
                    null);
            result = futureResult.get();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException("AAD Authentication result was null");
        }

        return result;
    }

    /**
     * Get the oauth access token using the device flow. This method calls the acquireTokenByDeviceCode method of AuthenticationContext
     * @param resourceId
     *            Identifier of the target resource that is the recipient of the
     *            requested token.
     * @return  Returns AuthenticationResult. It contains Access
     *         Token and the Access Token's expiration time. Refresh Token
     *         property will be null for this overload.
     * @throws AuthenticationException
     */
    private AuthenticationResult getAccessTokenUsingDeviceCodeFlow(String resourceId) throws Exception {
        AuthenticationContext context = null;
        AuthenticationResult result = null;
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(AUTHORITY, true, service);
            Future<DeviceCode> future = context.acquireDeviceCode(CLIENT_ID, resourceId, null);
            DeviceCode deviceCode = future.get();
            System.out.println(deviceCode.getMessage());
            System.out.println("Press Enter after authenticating...");
            System.in.read();
            Future<AuthenticationResult> futureResult = context.acquireTokenByDeviceCode(deviceCode, null);
            result = futureResult.get();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException("AAD Authentication result was null");
        }

        return result;
    }

    /**
     * Renew the access token from the authority using a Refresh Token previously received. This method calls the acquireTokenByRefreshToken method of AuthenticationContext
     * @param refreshToken
     * @throws Exception
     */
    public void renewAccessToken(String refreshToken) throws Exception {
        AuthenticationContext context = null;
        AuthenticationResult result = null;
        ExecutorService service = null;

        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(AUTHORITY, true, service);
            Future<AuthenticationResult> futureResult = context.acquireTokenByRefreshToken(refreshToken, CLIENT_ID, null);
            result = futureResult.get();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException("AAD Authentication result was null");
        }

        return;
    }
}
