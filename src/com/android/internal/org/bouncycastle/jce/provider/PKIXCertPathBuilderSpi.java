package com.android.internal.org.bouncycastle.jce.provider;

import android.media.MediaMetrics;
import com.android.internal.org.bouncycastle.jcajce.PKIXCertStore;
import com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters;
import com.android.internal.org.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class PKIXCertPathBuilderSpi extends CertPathBuilderSpi {
    private Exception certPathException;
    private final boolean isForCRLCheck;

    public PKIXCertPathBuilderSpi() {
        this(false);
    }

    PKIXCertPathBuilderSpi(boolean z) {
        this.isForCRLCheck = z;
    }

    @Override // java.security.cert.CertPathBuilderSpi
    public CertPathBuilderResult engineBuild(CertPathParameters certPathParameters) throws CertPathBuilderException, InvalidAlgorithmParameterException {
        PKIXExtendedBuilderParameters pKIXExtendedBuilderParameters;
        Exception exc;
        PKIXExtendedBuilderParameters.Builder builder;
        if (certPathParameters instanceof PKIXBuilderParameters) {
            PKIXBuilderParameters pKIXBuilderParameters = (PKIXBuilderParameters) certPathParameters;
            PKIXExtendedParameters.Builder builder2 = new PKIXExtendedParameters.Builder(pKIXBuilderParameters);
            if (certPathParameters instanceof ExtendedPKIXParameters) {
                ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = (ExtendedPKIXBuilderParameters) certPathParameters;
                Iterator it = extendedPKIXBuilderParameters.getAdditionalStores().iterator();
                while (it.hasNext()) {
                    builder2.addCertificateStore((PKIXCertStore) it.next());
                }
                builder = new PKIXExtendedBuilderParameters.Builder(builder2.build());
                builder.addExcludedCerts(extendedPKIXBuilderParameters.getExcludedCerts());
                builder.setMaxPathLength(extendedPKIXBuilderParameters.getMaxPathLength());
            } else {
                builder = new PKIXExtendedBuilderParameters.Builder(pKIXBuilderParameters);
            }
            pKIXExtendedBuilderParameters = builder.build();
        } else if (certPathParameters instanceof PKIXExtendedBuilderParameters) {
            pKIXExtendedBuilderParameters = (PKIXExtendedBuilderParameters) certPathParameters;
        } else {
            throw new InvalidAlgorithmParameterException("Parameters must be an instance of " + PKIXBuilderParameters.class.getName() + " or " + PKIXExtendedBuilderParameters.class.getName() + MediaMetrics.SEPARATOR);
        }
        ArrayList arrayList = new ArrayList();
        Iterator it2 = CertPathValidatorUtilities.findTargets(pKIXExtendedBuilderParameters).iterator();
        CertPathBuilderResult certPathBuilderResult = null;
        while (it2.hasNext() && certPathBuilderResult == null) {
            certPathBuilderResult = build((X509Certificate) it2.next(), pKIXExtendedBuilderParameters, arrayList);
        }
        if (certPathBuilderResult == null && (exc = this.certPathException) != null) {
            if (exc instanceof AnnotatedException) {
                throw new CertPathBuilderException(this.certPathException.getMessage(), this.certPathException.getCause());
            }
            throw new CertPathBuilderException("Possible certificate chain could not be validated.", this.certPathException);
        }
        if (certPathBuilderResult == null && this.certPathException == null) {
            throw new CertPathBuilderException("Unable to find certificate chain.");
        }
        return certPathBuilderResult;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00f3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected java.security.cert.CertPathBuilderResult build(java.security.cert.X509Certificate r6, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters r7, java.util.List r8) {
        /*
            r5 = this;
            boolean r0 = r8.contains(r6)
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            java.util.Set r0 = r7.getExcludedCerts()
            boolean r0 = r0.contains(r6)
            if (r0 == 0) goto L13
            return r1
        L13:
            int r0 = r7.getMaxPathLength()
            r2 = -1
            if (r0 == r2) goto L27
            int r0 = r8.size()
            int r0 = r0 + (-1)
            int r2 = r7.getMaxPathLength()
            if (r0 <= r2) goto L27
            return r1
        L27:
            r8.add(r6)
            com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory r0 = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory     // Catch: java.lang.Exception -> Lf7
            r0.<init>()     // Catch: java.lang.Exception -> Lf7
            com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi r2 = new com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi     // Catch: java.lang.Exception -> Lf7
            boolean r3 = r5.isForCRLCheck     // Catch: java.lang.Exception -> Lf7
            r2.<init>(r3)     // Catch: java.lang.Exception -> Lf7
            com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters r3 = r7.getBaseParameters()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.util.Set r3 = r3.getTrustAnchors()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters r4 = r7.getBaseParameters()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.lang.String r4 = r4.getSigProvider()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            boolean r3 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.isIssuerTrustAnchor(r6, r3, r4)     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            if (r3 == 0) goto L7a
            java.security.cert.CertPath r0 = r0.engineGenerateCertPath(r8)     // Catch: java.lang.Exception -> L71 com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.security.cert.CertPathValidatorResult r7 = r2.engineValidate(r0, r7)     // Catch: java.lang.Exception -> L68 com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.security.cert.PKIXCertPathValidatorResult r7 = (java.security.cert.PKIXCertPathValidatorResult) r7     // Catch: java.lang.Exception -> L68 com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.security.cert.PKIXCertPathBuilderResult r2 = new java.security.cert.PKIXCertPathBuilderResult     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.security.cert.TrustAnchor r3 = r7.getTrustAnchor()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.security.cert.PolicyNode r4 = r7.getPolicyTree()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.security.PublicKey r7 = r7.getPublicKey()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            r2.<init>(r0, r3, r4, r7)     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            return r2
        L68:
            r7 = move-exception
            com.android.internal.org.bouncycastle.jce.provider.AnnotatedException r0 = new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.lang.String r2 = "Certification path could not be validated."
            r0.<init>(r2, r7)     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            throw r0     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
        L71:
            r7 = move-exception
            com.android.internal.org.bouncycastle.jce.provider.AnnotatedException r0 = new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.lang.String r2 = "Certification path could not be constructed from certificate list."
            r0.<init>(r2, r7)     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            throw r0     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
        L7a:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            r0.<init>()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters r2 = r7.getBaseParameters()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.util.List r2 = r2.getCertificateStores()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            r0.addAll(r2)     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = com.android.internal.org.bouncycastle.asn1.x509.Extension.issuerAlternativeName     // Catch: java.security.cert.CertificateParsingException -> Le5 com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.lang.String r2 = r2.getId()     // Catch: java.security.cert.CertificateParsingException -> Le5 com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            byte[] r2 = r6.getExtensionValue(r2)     // Catch: java.security.cert.CertificateParsingException -> Le5 com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters r3 = r7.getBaseParameters()     // Catch: java.security.cert.CertificateParsingException -> Le5 com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.util.Map r3 = r3.getNamedCertificateStoreMap()     // Catch: java.security.cert.CertificateParsingException -> Le5 com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.util.List r2 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getAdditionalStoresFromAltNames(r2, r3)     // Catch: java.security.cert.CertificateParsingException -> Le5 com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            r0.addAll(r2)     // Catch: java.security.cert.CertificateParsingException -> Le5 com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.util.HashSet r2 = new java.util.HashSet     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            r2.<init>()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters r3 = r7.getBaseParameters()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Ldc
            java.util.List r3 = r3.getCertStores()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Ldc
            java.util.Collection r0 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.findIssuerCerts(r6, r3, r0)     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Ldc
            r2.addAll(r0)     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Ldc
            boolean r0 = r2.isEmpty()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            if (r0 != 0) goto Ld4
            java.util.Iterator r0 = r2.iterator()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
        Lc1:
            boolean r2 = r0.hasNext()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            if (r2 == 0) goto Lf1
            if (r1 != 0) goto Lf1
            java.lang.Object r2 = r0.next()     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.security.cert.X509Certificate r2 = (java.security.cert.X509Certificate) r2     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.security.cert.CertPathBuilderResult r1 = r5.build(r2, r7, r8)     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            goto Lc1
        Ld4:
            com.android.internal.org.bouncycastle.jce.provider.AnnotatedException r7 = new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.lang.String r0 = "No issuer certificate for certificate in certification path found."
            r7.<init>(r0)     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            throw r7     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
        Ldc:
            r7 = move-exception
            com.android.internal.org.bouncycastle.jce.provider.AnnotatedException r0 = new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.lang.String r2 = "Cannot find issuer certificate for certificate in certification path."
            r0.<init>(r2, r7)     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            throw r0     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
        Le5:
            r7 = move-exception
            com.android.internal.org.bouncycastle.jce.provider.AnnotatedException r0 = new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            java.lang.String r2 = "No additional X.509 stores can be added from certificate locations."
            r0.<init>(r2, r7)     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
            throw r0     // Catch: com.android.internal.org.bouncycastle.jce.provider.AnnotatedException -> Lee
        Lee:
            r7 = move-exception
            r5.certPathException = r7
        Lf1:
            if (r1 != 0) goto Lf6
            r8.remove(r6)
        Lf6:
            return r1
        Lf7:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.String r6 = "Exception creating support classes."
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathBuilderSpi.build(java.security.cert.X509Certificate, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters, java.util.List):java.security.cert.CertPathBuilderResult");
    }
}
