package com.samsung.android.knox.dar.ddar.securesession;

import com.samsung.android.knox.dar.ddar.securesession.SecureSessionManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes6.dex */
public class SecureClient {
    private String clientId;
    private Map<String, SecureSessionManager.SecureSession> sessionHandles = new HashMap();
    private SecureSessionManager.PrivateSessionEndpoint privateSessionEndpoint = new SecureSessionManager.PrivateSessionEndpoint();

    public SecureClient(String str) throws Exception {
        this.clientId = str;
    }

    public String getPublicKeyString() {
        return this.privateSessionEndpoint.getPublicKeyString();
    }

    public synchronized void initializeSecureSession(String str, String str2) throws Exception {
        this.sessionHandles.put(str, new SecureSessionManager.SecureSession(this.privateSessionEndpoint, new SecureSessionManager.PublicSessionEndpoint(str2)));
    }

    public synchronized boolean hasActiveSecureSessions() {
        return !this.sessionHandles.isEmpty();
    }

    public synchronized void terminateSecureSession(String str) throws Exception {
        if (this.sessionHandles.containsKey(str)) {
            this.sessionHandles.get(str).destroySessionkey();
            this.sessionHandles.remove(str);
        }
    }

    public synchronized void destroy() throws Exception {
        Iterator<Map.Entry<String, SecureSessionManager.SecureSession>> it = this.sessionHandles.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().destroySessionkey();
        }
        this.sessionHandles.clear();
        this.privateSessionEndpoint.destroy();
    }

    public synchronized String encryptMessageFor(String str, String str2) throws Exception {
        return this.sessionHandles.get(str).encryptString(str2);
    }

    public synchronized byte[] encryptMessageFor(String str, byte[] bArr) throws Exception {
        return this.sessionHandles.get(str).encryptBytes(bArr);
    }

    public synchronized String decryptMessageFrom(String str, String str2) throws Exception {
        return this.sessionHandles.get(str).decryptString(str2);
    }

    public synchronized byte[] decryptMessageFrom(String str, byte[] bArr) throws Exception {
        return this.sessionHandles.get(str).decryptBytes(bArr);
    }

    public String getClientId() {
        return this.clientId;
    }
}
