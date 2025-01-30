package com.samsung.android.knox.dar.ddar.securesession;

import com.samsung.android.knox.dar.ddar.securesession.SecureSessionManager;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class SecureClient {
    private String clientId;
    private Map<String, SecureSessionManager.SecureSession> sessionHandles = new HashMap();
    private SecureSessionManager.PrivateSessionEndpoint privateSessionEndpoint = new SecureSessionManager.PrivateSessionEndpoint();

    public SecureClient(String clientId) throws Exception {
        this.clientId = clientId;
    }

    public String getPublicKeyString() {
        return this.privateSessionEndpoint.getPublicKeyString();
    }

    public synchronized void initializeSecureSession(String otherClientId, String otherClientPubKey) throws Exception {
        SecureSessionManager.PublicSessionEndpoint publicSessionEndpoint = new SecureSessionManager.PublicSessionEndpoint(otherClientPubKey);
        this.sessionHandles.put(otherClientId, new SecureSessionManager.SecureSession(this.privateSessionEndpoint, publicSessionEndpoint));
    }

    public synchronized boolean hasActiveSecureSessions() {
        return !this.sessionHandles.isEmpty();
    }

    public synchronized void terminateSecureSession(String otherClientId) throws Exception {
        if (this.sessionHandles.containsKey(otherClientId)) {
            this.sessionHandles.get(otherClientId).destroySessionkey();
            this.sessionHandles.remove(otherClientId);
        }
    }

    public synchronized void destroy() throws Exception {
        for (String client : this.sessionHandles.keySet()) {
            this.sessionHandles.get(client).destroySessionkey();
        }
        this.sessionHandles.clear();
        this.privateSessionEndpoint.destroy();
    }

    public synchronized String encryptMessageFor(String toClient, String message) throws Exception {
        return this.sessionHandles.get(toClient).encryptString(message);
    }

    public synchronized byte[] encryptMessageFor(String toClient, byte[] message) throws Exception {
        return this.sessionHandles.get(toClient).encryptBytes(message);
    }

    public synchronized String decryptMessageFrom(String fromClient, String cipher) throws Exception {
        return this.sessionHandles.get(fromClient).decryptString(cipher);
    }

    public synchronized byte[] decryptMessageFrom(String fromClient, byte[] cipher) throws Exception {
        return this.sessionHandles.get(fromClient).decryptBytes(cipher);
    }

    public String getClientId() {
        return this.clientId;
    }
}
