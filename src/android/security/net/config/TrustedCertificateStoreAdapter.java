package android.security.net.config;

import com.android.org.conscrypt.TrustedCertificateStore;
import java.io.File;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

/* loaded from: classes3.dex */
public class TrustedCertificateStoreAdapter extends TrustedCertificateStore {
    private final NetworkSecurityConfig mConfig;

    public TrustedCertificateStoreAdapter(NetworkSecurityConfig networkSecurityConfig) {
        this.mConfig = networkSecurityConfig;
    }

    public X509Certificate findIssuer(X509Certificate x509Certificate) {
        TrustAnchor trustAnchorFindTrustAnchorByIssuerAndSignature = this.mConfig.findTrustAnchorByIssuerAndSignature(x509Certificate);
        if (trustAnchorFindTrustAnchorByIssuerAndSignature == null) {
            return null;
        }
        return trustAnchorFindTrustAnchorByIssuerAndSignature.certificate;
    }

    public Set<X509Certificate> findAllIssuers(X509Certificate x509Certificate) {
        return this.mConfig.findAllCertificatesByIssuerAndSignature(x509Certificate);
    }

    public X509Certificate getTrustAnchor(X509Certificate x509Certificate) {
        TrustAnchor trustAnchorFindTrustAnchorBySubjectAndPublicKey = this.mConfig.findTrustAnchorBySubjectAndPublicKey(x509Certificate);
        if (trustAnchorFindTrustAnchorBySubjectAndPublicKey == null) {
            return null;
        }
        return trustAnchorFindTrustAnchorBySubjectAndPublicKey.certificate;
    }

    public boolean isUserAddedCertificate(X509Certificate x509Certificate) {
        TrustAnchor trustAnchorFindTrustAnchorBySubjectAndPublicKey = this.mConfig.findTrustAnchorBySubjectAndPublicKey(x509Certificate);
        if (trustAnchorFindTrustAnchorBySubjectAndPublicKey == null) {
            return false;
        }
        return trustAnchorFindTrustAnchorBySubjectAndPublicKey.overridesPins;
    }

    public File getCertificateFile(File file, X509Certificate x509Certificate) {
        throw new UnsupportedOperationException();
    }

    public Certificate getCertificate(String str) {
        throw new UnsupportedOperationException();
    }

    public Certificate getCertificate(String str, boolean z) {
        throw new UnsupportedOperationException();
    }

    public Date getCreationDate(String str) {
        throw new UnsupportedOperationException();
    }

    public Set<String> aliases() {
        throw new UnsupportedOperationException();
    }

    public Set<String> userAliases() {
        throw new UnsupportedOperationException();
    }

    public Set<String> allSystemAliases() {
        throw new UnsupportedOperationException();
    }

    public boolean containsAlias(String str) {
        throw new UnsupportedOperationException();
    }

    public String getCertificateAlias(Certificate certificate) {
        throw new UnsupportedOperationException();
    }

    public String getCertificateAlias(Certificate certificate, boolean z) {
        throw new UnsupportedOperationException();
    }
}
