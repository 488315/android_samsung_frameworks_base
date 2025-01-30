package com.samsung.context.sdk.samsunganalytics.internal.security;

import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CertificateManager {
    public SSLContext sslContext;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Singleton {
        public static final CertificateManager instance = new CertificateManager();

        private Singleton() {
        }
    }

    private CertificateManager() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            KeyStore keyStore2 = KeyStore.getInstance("AndroidCAStore");
            keyStore2.load(null, null);
            Enumeration<String> aliases = keyStore2.aliases();
            while (aliases.hasMoreElements()) {
                String nextElement = aliases.nextElement();
                X509Certificate x509Certificate = (X509Certificate) keyStore2.getCertificate(nextElement);
                if (nextElement.startsWith("system:")) {
                    keyStore.setCertificateEntry(nextElement, x509Certificate);
                }
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            this.sslContext = sSLContext;
            sSLContext.init(null, trustManagerFactory.getTrustManagers(), null);
            Debug.LogENG("pinning success");
        } catch (Exception e) {
            Debug.LogENG("pinning fail : " + e.getMessage());
        }
    }
}
