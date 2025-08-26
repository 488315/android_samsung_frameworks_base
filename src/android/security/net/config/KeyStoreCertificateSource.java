package android.security.net.config;

import android.util.ArraySet;
import com.android.org.conscrypt.TrustedCertificateIndex;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes3.dex */
class KeyStoreCertificateSource implements CertificateSource {
    private Set<X509Certificate> mCertificates;
    private TrustedCertificateIndex mIndex;
    private final KeyStore mKeyStore;
    private final Object mLock = new Object();

    @Override // android.security.net.config.CertificateSource
    public void handleTrustStorageUpdate() {
    }

    public KeyStoreCertificateSource(KeyStore keyStore) {
        this.mKeyStore = keyStore;
    }

    @Override // android.security.net.config.CertificateSource
    public Set<X509Certificate> getCertificates() {
        ensureInitialized();
        return this.mCertificates;
    }

    private void ensureInitialized() {
        synchronized (this.mLock) {
            if (this.mCertificates != null) {
                return;
            }
            try {
                TrustedCertificateIndex trustedCertificateIndex = new TrustedCertificateIndex();
                ArraySet arraySet = new ArraySet(this.mKeyStore.size());
                Enumeration<String> enumerationAliases = this.mKeyStore.aliases();
                while (enumerationAliases.hasMoreElements()) {
                    X509Certificate x509Certificate = (X509Certificate) this.mKeyStore.getCertificate(enumerationAliases.nextElement());
                    if (x509Certificate != null) {
                        arraySet.add(x509Certificate);
                        trustedCertificateIndex.index(x509Certificate);
                    }
                }
                this.mIndex = trustedCertificateIndex;
                this.mCertificates = arraySet;
            } catch (KeyStoreException e) {
                throw new RuntimeException("Failed to load certificates from KeyStore", e);
            }
        }
    }

    @Override // android.security.net.config.CertificateSource
    public X509Certificate findBySubjectAndPublicKey(X509Certificate x509Certificate) {
        ensureInitialized();
        java.security.cert.TrustAnchor trustAnchorFindBySubjectAndPublicKey = this.mIndex.findBySubjectAndPublicKey(x509Certificate);
        if (trustAnchorFindBySubjectAndPublicKey == null) {
            return null;
        }
        return trustAnchorFindBySubjectAndPublicKey.getTrustedCert();
    }

    @Override // android.security.net.config.CertificateSource
    public X509Certificate findByIssuerAndSignature(X509Certificate x509Certificate) {
        ensureInitialized();
        java.security.cert.TrustAnchor trustAnchorFindByIssuerAndSignature = this.mIndex.findByIssuerAndSignature(x509Certificate);
        if (trustAnchorFindByIssuerAndSignature == null) {
            return null;
        }
        return trustAnchorFindByIssuerAndSignature.getTrustedCert();
    }

    @Override // android.security.net.config.CertificateSource
    public Set<X509Certificate> findAllByIssuerAndSignature(X509Certificate x509Certificate) {
        ensureInitialized();
        Set setFindAllByIssuerAndSignature = this.mIndex.findAllByIssuerAndSignature(x509Certificate);
        if (setFindAllByIssuerAndSignature.isEmpty()) {
            return Collections.EMPTY_SET;
        }
        ArraySet arraySet = new ArraySet(setFindAllByIssuerAndSignature.size());
        Iterator it = setFindAllByIssuerAndSignature.iterator();
        while (it.hasNext()) {
            arraySet.add(((java.security.cert.TrustAnchor) it.next()).getTrustedCert());
        }
        return arraySet;
    }
}
