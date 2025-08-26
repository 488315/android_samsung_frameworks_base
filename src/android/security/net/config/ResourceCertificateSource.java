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
        CertificateFactory certificateFactory;
        InputStream inputStreamOpenRawResource;
        synchronized (this.mLock) {
            if (this.mCertificates != null) {
                return;
            }
            ArraySet arraySet = new ArraySet();
            InputStream inputStream = null;
            try {
                try {
                    certificateFactory = CertificateFactory.getInstance("X.509");
                    inputStreamOpenRawResource = this.mContext.getResources().openRawResource(this.mResourceId);
                } catch (CertificateException e) {
                    e = e;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                Collection<? extends Certificate> collectionGenerateCertificates = certificateFactory.generateCertificates(inputStreamOpenRawResource);
                IoUtils.closeQuietly(inputStreamOpenRawResource);
                TrustedCertificateIndex trustedCertificateIndex = new TrustedCertificateIndex();
                for (Certificate certificate : collectionGenerateCertificates) {
                    arraySet.add((X509Certificate) certificate);
                    trustedCertificateIndex.index((X509Certificate) certificate);
                }
                this.mCertificates = arraySet;
                this.mIndex = trustedCertificateIndex;
                this.mContext = null;
            } catch (CertificateException e2) {
                e = e2;
                inputStream = inputStreamOpenRawResource;
                throw new RuntimeException("Failed to load trust anchors from id " + this.mResourceId, e);
            } catch (Throwable th2) {
                th = th2;
                inputStream = inputStreamOpenRawResource;
                IoUtils.closeQuietly(inputStream);
                throw th;
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
