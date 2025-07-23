package android.security.net.config;

import android.util.ArraySet;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes3.dex */
public final class CertificatesEntryRef {
    private final boolean mDisableCT;
    private final boolean mOverridesPins;
    private final CertificateSource mSource;

    public CertificatesEntryRef(CertificateSource certificateSource, boolean z, boolean z2) {
        this.mSource = certificateSource;
        this.mOverridesPins = z;
        this.mDisableCT = z2;
    }

    boolean overridesPins() {
        return this.mOverridesPins;
    }

    boolean disableCT() {
        return this.mDisableCT;
    }

    public Set<TrustAnchor> getTrustAnchors() {
        ArraySet arraySet = new ArraySet();
        Iterator<X509Certificate> it = this.mSource.getCertificates().iterator();
        while (it.hasNext()) {
            arraySet.add(new TrustAnchor(it.next(), this.mOverridesPins));
        }
        return arraySet;
    }

    public TrustAnchor findBySubjectAndPublicKey(X509Certificate x509Certificate) {
        X509Certificate findBySubjectAndPublicKey = this.mSource.findBySubjectAndPublicKey(x509Certificate);
        if (findBySubjectAndPublicKey == null) {
            return null;
        }
        return new TrustAnchor(findBySubjectAndPublicKey, this.mOverridesPins);
    }

    public TrustAnchor findByIssuerAndSignature(X509Certificate x509Certificate) {
        X509Certificate findByIssuerAndSignature = this.mSource.findByIssuerAndSignature(x509Certificate);
        if (findByIssuerAndSignature == null) {
            return null;
        }
        return new TrustAnchor(findByIssuerAndSignature, this.mOverridesPins);
    }

    public Set<X509Certificate> findAllCertificatesByIssuerAndSignature(X509Certificate x509Certificate) {
        return this.mSource.findAllByIssuerAndSignature(x509Certificate);
    }

    public void handleTrustStorageUpdate() {
        this.mSource.handleTrustStorageUpdate();
    }
}
