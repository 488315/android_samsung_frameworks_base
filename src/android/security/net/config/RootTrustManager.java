package android.security.net.config;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509ExtendedTrustManager;

/* loaded from: classes3.dex */
public class RootTrustManager extends X509ExtendedTrustManager {
    private final ApplicationConfig mConfig;

    public RootTrustManager(ApplicationConfig applicationConfig) {
        if (applicationConfig == null) {
            throw new NullPointerException("config must not be null");
        }
        this.mConfig = applicationConfig;
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        this.mConfig.getConfigForHostname("").getTrustManager().checkClientTrusted(x509CertificateArr, str);
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) throws CertificateException {
        this.mConfig.getConfigForHostname("").getTrustManager().checkClientTrusted(x509CertificateArr, str, socket);
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) throws CertificateException {
        this.mConfig.getConfigForHostname("").getTrustManager().checkClientTrusted(x509CertificateArr, str, sSLEngine);
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) throws CertificateException {
        if (socket instanceof SSLSocket) {
            SSLSession handshakeSession = ((SSLSocket) socket).getHandshakeSession();
            if (handshakeSession == null) {
                throw new CertificateException("Not in handshake; no session available");
            }
            this.mConfig.getConfigForHostname(handshakeSession.getPeerHost()).getTrustManager().checkServerTrusted(x509CertificateArr, str, socket);
            return;
        }
        checkServerTrusted(x509CertificateArr, str);
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) throws CertificateException {
        SSLSession handshakeSession = sSLEngine.getHandshakeSession();
        if (handshakeSession == null) {
            throw new CertificateException("Not in handshake; no session available");
        }
        this.mConfig.getConfigForHostname(handshakeSession.getPeerHost()).getTrustManager().checkServerTrusted(x509CertificateArr, str, sSLEngine);
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        if (this.mConfig.hasPerDomainConfigs()) {
            throw new CertificateException("Domain specific configurations require that hostname aware checkServerTrusted(X509Certificate[], String, String) is used");
        }
        this.mConfig.getConfigForHostname("").getTrustManager().checkServerTrusted(x509CertificateArr, str);
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] x509CertificateArr, String str, String str2) throws CertificateException {
        if (str2 == null && this.mConfig.hasPerDomainConfigs()) {
            throw new CertificateException("Domain specific configurations require that the hostname be provided");
        }
        return this.mConfig.getConfigForHostname(str2).getTrustManager().checkServerTrusted(x509CertificateArr, str, str2);
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] x509CertificateArr, byte[] bArr, byte[] bArr2, String str, String str2) throws CertificateException {
        if (str2 == null && this.mConfig.hasPerDomainConfigs()) {
            throw new CertificateException("Domain specific configurations require that the hostname be provided");
        }
        return this.mConfig.getConfigForHostname(str2).getTrustManager().checkServerTrusted(x509CertificateArr, bArr, bArr2, str, str2);
    }

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        return this.mConfig.getConfigForHostname("").getTrustManager().getAcceptedIssuers();
    }

    public boolean isSameTrustConfiguration(String str, String str2) {
        return this.mConfig.getConfigForHostname(str).equals(this.mConfig.getConfigForHostname(str2));
    }
}
