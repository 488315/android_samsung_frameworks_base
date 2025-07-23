package android.security.net.config;

import android.util.ArrayMap;
import com.android.org.conscrypt.CertPinManager;
import com.android.org.conscrypt.TrustManagerImpl;
import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;

/* loaded from: classes3.dex */
public class NetworkSecurityTrustManager extends X509ExtendedTrustManager {
    private final TrustManagerImpl mDelegate;
    private X509Certificate[] mIssuers;
    private final Object mIssuersLock = new Object();
    private final NetworkSecurityConfig mNetworkSecurityConfig;

    public NetworkSecurityTrustManager(NetworkSecurityConfig networkSecurityConfig) {
        if (networkSecurityConfig == null) {
            throw new NullPointerException("config must not be null");
        }
        this.mNetworkSecurityConfig = networkSecurityConfig;
        try {
            TrustedCertificateStoreAdapter trustedCertificateStoreAdapter = new TrustedCertificateStoreAdapter(networkSecurityConfig);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            this.mDelegate = new TrustManagerImpl(keyStore, (CertPinManager) null, trustedCertificateStoreAdapter);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        this.mDelegate.checkClientTrusted(x509CertificateArr, str);
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) throws CertificateException {
        this.mDelegate.checkClientTrusted(x509CertificateArr, str, socket);
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) throws CertificateException {
        this.mDelegate.checkClientTrusted(x509CertificateArr, str, sSLEngine);
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        checkServerTrusted(x509CertificateArr, str, (String) null);
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) throws CertificateException {
        checkPins(this.mDelegate.getTrustedChainForServer(x509CertificateArr, str, socket));
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) throws CertificateException {
        checkPins(this.mDelegate.getTrustedChainForServer(x509CertificateArr, str, sSLEngine));
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] x509CertificateArr, String str, String str2) throws CertificateException {
        List<X509Certificate> checkServerTrusted = this.mDelegate.checkServerTrusted(x509CertificateArr, str, str2);
        checkPins(checkServerTrusted);
        return checkServerTrusted;
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] x509CertificateArr, byte[] bArr, byte[] bArr2, String str, String str2) throws CertificateException {
        List<X509Certificate> checkServerTrusted = this.mDelegate.checkServerTrusted(x509CertificateArr, bArr, bArr2, str, str2);
        checkPins(checkServerTrusted);
        return checkServerTrusted;
    }

    private void checkPins(List<X509Certificate> list) throws CertificateException {
        PinSet pins = this.mNetworkSecurityConfig.getPins();
        if (pins.pins.isEmpty() || System.currentTimeMillis() > pins.expirationTime || !isPinningEnforced(list)) {
            return;
        }
        Set<String> pinAlgorithms = pins.getPinAlgorithms();
        ArrayMap arrayMap = new ArrayMap(pinAlgorithms.size());
        for (int size = list.size() - 1; size >= 0; size--) {
            byte[] encoded = list.get(size).getPublicKey().getEncoded();
            for (String str : pinAlgorithms) {
                MessageDigest messageDigest = (MessageDigest) arrayMap.get(str);
                if (messageDigest == null) {
                    try {
                        messageDigest = MessageDigest.getInstance(str);
                        arrayMap.put(str, messageDigest);
                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (pins.pins.contains(new Pin(str, messageDigest.digest(encoded)))) {
                    return;
                }
            }
        }
        throw new CertificateException("Pin verification failed");
    }

    private boolean isPinningEnforced(List<X509Certificate> list) throws CertificateException {
        if (list.isEmpty()) {
            return false;
        }
        if (this.mNetworkSecurityConfig.findTrustAnchorBySubjectAndPublicKey(list.get(list.size() - 1)) == null) {
            throw new CertificateException("Trusted chain does not end in a TrustAnchor");
        }
        return !r1.overridesPins;
    }

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        X509Certificate[] x509CertificateArr;
        synchronized (this.mIssuersLock) {
            if (this.mIssuers == null) {
                Set<TrustAnchor> trustAnchors = this.mNetworkSecurityConfig.getTrustAnchors();
                X509Certificate[] x509CertificateArr2 = new X509Certificate[trustAnchors.size()];
                Iterator<TrustAnchor> it = trustAnchors.iterator();
                int i = 0;
                while (it.hasNext()) {
                    x509CertificateArr2[i] = it.next().certificate;
                    i++;
                }
                this.mIssuers = x509CertificateArr2;
            }
            x509CertificateArr = (X509Certificate[]) this.mIssuers.clone();
        }
        return x509CertificateArr;
    }

    public void handleTrustStorageUpdate() {
        synchronized (this.mIssuersLock) {
            this.mIssuers = null;
            this.mDelegate.handleTrustStorageUpdate();
        }
    }
}
