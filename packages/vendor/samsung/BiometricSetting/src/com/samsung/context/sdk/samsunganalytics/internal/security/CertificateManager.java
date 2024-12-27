package com.samsung.context.sdk.samsunganalytics.internal.security;

import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public final class CertificateManager {
    public SSLContext sslContext;

    public abstract class Singleton {
        public static final CertificateManager instance;

        static {
            CertificateManager certificateManager = new CertificateManager();
            try {
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null, null);
                KeyStore keyStore2 = KeyStore.getInstance("AndroidCAStore");
                keyStore2.load(null, null);
                Enumeration<String> aliases = keyStore2.aliases();
                while (aliases.hasMoreElements()) {
                    String nextElement = aliases.nextElement();
                    X509Certificate x509Certificate =
                            (X509Certificate) keyStore2.getCertificate(nextElement);
                    if (nextElement.startsWith("system:")) {
                        keyStore.setCertificateEntry(nextElement, x509Certificate);
                    }
                }
                TrustManagerFactory trustManagerFactory =
                        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);
                SSLContext sSLContext = SSLContext.getInstance("TLS");
                certificateManager.sslContext = sSLContext;
                sSLContext.init(null, trustManagerFactory.getTrustManagers(), null);
                Debug.LogENG("pinning success");
            } catch (Exception e) {
                Debug.LogENG("pinning fail : " + e.getMessage());
            }
            instance = certificateManager;
        }
    }
}
