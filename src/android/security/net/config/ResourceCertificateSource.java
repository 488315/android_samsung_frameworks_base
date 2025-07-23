package android.security.net.config;

import android.content.Context;
import android.util.ArraySet;
import com.android.org.conscrypt.TrustedCertificateIndex;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public class ResourceCertificateSource implements CertificateSource {
    private Set<X509Certificate> mCertificates;
    private Context mContext;
    private TrustedCertificateIndex mIndex;
    private final Object mLock = new Object();
    private final int mResourceId;

    @Override // android.security.net.config.CertificateSource
    public void handleTrustStorageUpdate() {
    }

    public ResourceCertificateSource(int i, Context context) {
        this.mResourceId = i;
        this.mContext = context;
    }

    private void ensureInitialized() {
        synchronized (this.mLock) {
            if (this.mCertificates != null) {
                return;
            }
            ArraySet arraySet = new ArraySet();
            InputStream inputStream = null;
            try {
                try {
                    CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                    InputStream openRawResource = this.mContext.getResources().openRawResource(this.mResourceId);
                    try {
                        Collection<? extends Certificate> generateCertificates = certificateFactory.generateCertificates(openRawResource);
                        IoUtils.closeQuietly(openRawResource);
                        TrustedCertificateIndex trustedCertificateIndex = new TrustedCertificateIndex();
                        for (Certificate certificate : generateCertificates) {
                            arraySet.add((X509Certificate) certificate);
                            trustedCertificateIndex.index((X509Certificate) certificate);
                        }
                        this.mCertificates = arraySet;
                        this.mIndex = trustedCertificateIndex;
                        this.mContext = null;
                    } catch (CertificateException e) {
                        e = e;
                        inputStream = openRawResource;
                        throw new RuntimeException("Failed to load trust anchors from id " + this.mResourceId, e);
                    } catch (Throwable th) {
                        th = th;
                        inputStream = openRawResource;
                        IoUtils.closeQuietly(inputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (CertificateException e2) {
                e = e2;
            }
        }
    }

    @Override // android.security.net.config.CertificateSource
    public Set<X509Certificate> getCertificates() {
        ensureInitialized();
        return this.mCertificates;
    }

    @Override // android.security.net.config.CertificateSource
    public X509Certificate findBySubjectAndPublicKey(X509Certificate x509Certificate) {
        ensureInitialized();
        java.security.cert.TrustAnchor findBySubjectAndPublicKey = this.mIndex.findBySubjectAndPublicKey(x509Certificate);
        if (findBySubjectAndPublicKey == null) {
            return null;
        }
        return findBySubjectAndPublicKey.getTrustedCert();
    }

    @Override // android.security.net.config.CertificateSource
    public X509Certificate findByIssuerAndSignature(X509Certificate x509Certificate) {
        ensureInitialized();
        java.security.cert.TrustAnchor findByIssuerAndSignature = this.mIndex.findByIssuerAndSignature(x509Certificate);
        if (findByIssuerAndSignature == null) {
            return null;
        }
        return findByIssuerAndSignature.getTrustedCert();
    }

    @Override // android.security.net.config.CertificateSource
    public Set<X509Certificate> findAllByIssuerAndSignature(X509Certificate x509Certificate) {
        ensureInitialized();
        Set findAllByIssuerAndSignature = this.mIndex.findAllByIssuerAndSignature(x509Certificate);
        if (findAllByIssuerAndSignature.isEmpty()) {
            return Collections.EMPTY_SET;
        }
        ArraySet arraySet = new ArraySet(findAllByIssuerAndSignature.size());
        Iterator it = findAllByIssuerAndSignature.iterator();
        while (it.hasNext()) {
            arraySet.add(((java.security.cert.TrustAnchor) it.next()).getTrustedCert());
        }
        return arraySet;
    }
}
