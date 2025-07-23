package com.android.internal.org.bouncycastle.jce.provider;

import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.Extension;
import com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate;
import com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters;
import com.android.internal.org.bouncycastle.jcajce.util.BCJcaJceHelper;
import com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
public class PKIXCertPathValidatorSpi extends CertPathValidatorSpi {
    private final JcaJceHelper helper;
    private final boolean isForCRLCheck;

    public PKIXCertPathValidatorSpi() {
        this(false);
    }

    public PKIXCertPathValidatorSpi(boolean z) {
        this.helper = new BCJcaJceHelper();
        this.isForCRLCheck = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier] */
    @Override // java.security.cert.CertPathValidatorSpi
    public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters certPathParameters) throws CertPathValidatorException, InvalidAlgorithmParameterException {
        PKIXExtendedParameters pKIXExtendedParameters;
        List<? extends Certificate> list;
        X500Name ca;
        PublicKey cAPublicKey;
        int i;
        HashSet hashSet;
        X500Name x500Name;
        List list2;
        HashSet hashSet2;
        if (certPathParameters instanceof PKIXParameters) {
            PKIXExtendedParameters.Builder builder = new PKIXExtendedParameters.Builder((PKIXParameters) certPathParameters);
            if (certPathParameters instanceof ExtendedPKIXParameters) {
                ExtendedPKIXParameters extendedPKIXParameters = (ExtendedPKIXParameters) certPathParameters;
                builder.setUseDeltasEnabled(extendedPKIXParameters.isUseDeltasEnabled());
                builder.setValidityModel(extendedPKIXParameters.getValidityModel());
            }
            pKIXExtendedParameters = builder.build();
        } else if (certPathParameters instanceof PKIXExtendedBuilderParameters) {
            pKIXExtendedParameters = ((PKIXExtendedBuilderParameters) certPathParameters).getBaseParameters();
        } else if (certPathParameters instanceof PKIXExtendedParameters) {
            pKIXExtendedParameters = (PKIXExtendedParameters) certPathParameters;
        } else {
            throw new InvalidAlgorithmParameterException("Parameters must be a " + PKIXParameters.class.getName() + " instance.");
        }
        if (pKIXExtendedParameters.getTrustAnchors() == null) {
            throw new InvalidAlgorithmParameterException("trustAnchors is null, this is not allowed for certification path validation.");
        }
        List<? extends Certificate> certificates = certPath.getCertificates();
        int size = certificates.size();
        int i2 = -1;
        if (certificates.isEmpty()) {
            throw new CertPathValidatorException("Certification path is empty.", null, certPath, -1);
        }
        Date validityDate = CertPathValidatorUtilities.getValidityDate(pKIXExtendedParameters, new Date());
        Set initialPolicies = pKIXExtendedParameters.getInitialPolicies();
        try {
            TrustAnchor findTrustAnchor = CertPathValidatorUtilities.findTrustAnchor((X509Certificate) certificates.get(certificates.size() - 1), pKIXExtendedParameters.getTrustAnchors(), pKIXExtendedParameters.getSigProvider());
            if (findTrustAnchor == null) {
                list = certificates;
                try {
                    throw new CertPathValidatorException("Trust anchor for certification path not found.", null, certPath, -1);
                } catch (AnnotatedException e) {
                    e = e;
                    throw new CertPathValidatorException(e.getMessage(), e.getUnderlyingException(), certPath, list.size() - 1);
                }
            }
            checkCertificate(findTrustAnchor.getTrustedCert());
            PKIXExtendedParameters build = new PKIXExtendedParameters.Builder(pKIXExtendedParameters).setTrustAnchor(findTrustAnchor).build();
            int i3 = size + 1;
            ArrayList[] arrayListArr = new ArrayList[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                arrayListArr[i4] = new ArrayList();
            }
            HashSet hashSet3 = new HashSet();
            hashSet3.add(RFC3280CertPathUtilities.ANY_POLICY);
            PKIXPolicyNode pKIXPolicyNode = new PKIXPolicyNode(new ArrayList(), 0, hashSet3, null, new HashSet(), RFC3280CertPathUtilities.ANY_POLICY, false);
            arrayListArr[0].add(pKIXPolicyNode);
            PKIXNameConstraintValidator pKIXNameConstraintValidator = new PKIXNameConstraintValidator();
            HashSet hashSet4 = new HashSet();
            int i5 = build.isExplicitPolicyRequired() ? 0 : i3;
            int i6 = build.isAnyPolicyInhibited() ? 0 : i3;
            if (build.isPolicyMappingInhibited()) {
                i3 = 0;
            }
            X509Certificate trustedCert = findTrustAnchor.getTrustedCert();
            try {
                if (trustedCert != null) {
                    ca = PrincipalUtils.getSubjectPrincipal(trustedCert);
                    cAPublicKey = trustedCert.getPublicKey();
                } else {
                    ca = PrincipalUtils.getCA(findTrustAnchor);
                    cAPublicKey = findTrustAnchor.getCAPublicKey();
                }
                try {
                    i2 = CertPathValidatorUtilities.getAlgorithmIdentifier(cAPublicKey);
                    i2.getAlgorithm();
                    i2.getParameters();
                    if (build.getTargetConstraints() != null) {
                        i = 1;
                        if (!build.getTargetConstraints().match((Certificate) certificates.get(0))) {
                            throw new ExtCertPathValidatorException("Target certificate in certification path does not match targetConstraints.", null, certPath, 0);
                        }
                    } else {
                        i = 1;
                    }
                    List certPathCheckers = build.getCertPathCheckers();
                    Iterator it = certPathCheckers.iterator();
                    while (it.hasNext()) {
                        ((PKIXCertPathChecker) it.next()).init(false);
                    }
                    ProvCrlRevocationChecker provCrlRevocationChecker = build.isRevocationEnabled() ? new ProvCrlRevocationChecker(this.helper) : null;
                    int i7 = i3;
                    int size2 = certificates.size() - 1;
                    int i8 = i5;
                    PKIXPolicyNode pKIXPolicyNode2 = pKIXPolicyNode;
                    int i9 = size;
                    X509Certificate x509Certificate = null;
                    while (size2 >= 0) {
                        int i10 = size - size2;
                        List<? extends Certificate> list3 = certificates;
                        X509Certificate x509Certificate2 = (X509Certificate) certificates.get(size2);
                        boolean z = size2 == list3.size() + (-1) ? i : 0;
                        try {
                            checkCertificate(x509Certificate2);
                            X509Certificate x509Certificate3 = trustedCert;
                            ArrayList[] arrayListArr2 = arrayListArr;
                            PublicKey publicKey = cAPublicKey;
                            List list4 = certPathCheckers;
                            int i11 = i8;
                            int i12 = i9;
                            X500Name x500Name2 = ca;
                            TrustAnchor trustAnchor = findTrustAnchor;
                            PKIXNameConstraintValidator pKIXNameConstraintValidator2 = pKIXNameConstraintValidator;
                            RFC3280CertPathUtilities.processCertA(certPath, build, validityDate, provCrlRevocationChecker, size2, publicKey, z, x500Name2, x509Certificate3);
                            ProvCrlRevocationChecker provCrlRevocationChecker2 = provCrlRevocationChecker;
                            PKIXExtendedParameters pKIXExtendedParameters2 = build;
                            Date date = validityDate;
                            int i13 = size2;
                            RFC3280CertPathUtilities.processCertBC(certPath, i13, pKIXNameConstraintValidator2, this.isForCRLCheck);
                            HashSet hashSet5 = hashSet4;
                            int i14 = i6;
                            PKIXPolicyNode processCertE = RFC3280CertPathUtilities.processCertE(certPath, i13, RFC3280CertPathUtilities.processCertD(certPath, i13, hashSet5, pKIXPolicyNode2, arrayListArr2, i14, this.isForCRLCheck));
                            RFC3280CertPathUtilities.processCertF(certPath, i13, processCertE, i11);
                            if (i10 != size) {
                                if (x509Certificate2 != null) {
                                    hashSet4 = hashSet5;
                                    int i15 = i;
                                    if (x509Certificate2.getVersion() == i15) {
                                        if (i10 != i15 || !x509Certificate2.equals(trustAnchor.getTrustedCert())) {
                                            throw new CertPathValidatorException("Version 1 certificates can't be used as CA ones.", null, certPath, i13);
                                        }
                                    }
                                } else {
                                    hashSet4 = hashSet5;
                                }
                                RFC3280CertPathUtilities.prepareNextCertA(certPath, i13);
                                PKIXPolicyNode prepareCertB = RFC3280CertPathUtilities.prepareCertB(certPath, i13, arrayListArr2, processCertE, i7);
                                RFC3280CertPathUtilities.prepareNextCertG(certPath, i13, pKIXNameConstraintValidator2);
                                int prepareNextCertH1 = RFC3280CertPathUtilities.prepareNextCertH1(certPath, i13, i11);
                                int prepareNextCertH2 = RFC3280CertPathUtilities.prepareNextCertH2(certPath, i13, i7);
                                int prepareNextCertH3 = RFC3280CertPathUtilities.prepareNextCertH3(certPath, i13, i14);
                                int prepareNextCertI1 = RFC3280CertPathUtilities.prepareNextCertI1(certPath, i13, prepareNextCertH1);
                                int prepareNextCertI2 = RFC3280CertPathUtilities.prepareNextCertI2(certPath, i13, prepareNextCertH2);
                                i14 = RFC3280CertPathUtilities.prepareNextCertJ(certPath, i13, prepareNextCertH3);
                                RFC3280CertPathUtilities.prepareNextCertK(certPath, i13);
                                i9 = RFC3280CertPathUtilities.prepareNextCertM(certPath, i13, RFC3280CertPathUtilities.prepareNextCertL(certPath, i13, i12));
                                RFC3280CertPathUtilities.prepareNextCertN(certPath, i13);
                                Set<String> criticalExtensionOIDs = x509Certificate2.getCriticalExtensionOIDs();
                                if (criticalExtensionOIDs != null) {
                                    hashSet2 = new HashSet(criticalExtensionOIDs);
                                    hashSet2.remove(RFC3280CertPathUtilities.KEY_USAGE);
                                    hashSet2.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                                    hashSet2.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                                    hashSet2.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                                    hashSet2.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                                    hashSet2.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                                    hashSet2.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                                    hashSet2.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                                    hashSet2.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                                    hashSet2.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                                } else {
                                    hashSet2 = new HashSet();
                                }
                                RFC3280CertPathUtilities.prepareNextCertO(certPath, i13, hashSet2, list4);
                                x500Name = PrincipalUtils.getSubjectPrincipal(x509Certificate2);
                                pKIXPolicyNode2 = prepareCertB;
                                try {
                                    PublicKey nextWorkingKey = CertPathValidatorUtilities.getNextWorkingKey(certPath.getCertificates(), i13, this.helper);
                                    AlgorithmIdentifier algorithmIdentifier = CertPathValidatorUtilities.getAlgorithmIdentifier(nextWorkingKey);
                                    algorithmIdentifier.getAlgorithm();
                                    algorithmIdentifier.getParameters();
                                    cAPublicKey = nextWorkingKey;
                                    trustedCert = x509Certificate2;
                                    list2 = list4;
                                    i8 = prepareNextCertI1;
                                    i7 = prepareNextCertI2;
                                    i6 = i14;
                                    arrayListArr = arrayListArr2;
                                    provCrlRevocationChecker = provCrlRevocationChecker2;
                                    i = 1;
                                    size2 = i13 - 1;
                                    build = pKIXExtendedParameters2;
                                    pKIXNameConstraintValidator = pKIXNameConstraintValidator2;
                                    findTrustAnchor = trustAnchor;
                                    ca = x500Name;
                                    certPathCheckers = list2;
                                    validityDate = date;
                                    x509Certificate = x509Certificate2;
                                    certificates = list3;
                                } catch (CertPathValidatorException e2) {
                                    throw new CertPathValidatorException("Next working key could not be retrieved.", e2, certPath, i13);
                                }
                            } else {
                                hashSet4 = hashSet5;
                            }
                            list2 = list4;
                            i9 = i12;
                            pKIXPolicyNode2 = processCertE;
                            i8 = i11;
                            cAPublicKey = publicKey;
                            x500Name = x500Name2;
                            trustedCert = x509Certificate3;
                            i6 = i14;
                            arrayListArr = arrayListArr2;
                            provCrlRevocationChecker = provCrlRevocationChecker2;
                            i = 1;
                            size2 = i13 - 1;
                            build = pKIXExtendedParameters2;
                            pKIXNameConstraintValidator = pKIXNameConstraintValidator2;
                            findTrustAnchor = trustAnchor;
                            ca = x500Name;
                            certPathCheckers = list2;
                            validityDate = date;
                            x509Certificate = x509Certificate2;
                            certificates = list3;
                        } catch (AnnotatedException e3) {
                            throw new CertPathValidatorException(e3.getMessage(), e3.getUnderlyingException(), certPath, size2);
                        }
                    }
                    PKIXExtendedParameters pKIXExtendedParameters3 = build;
                    int i16 = size2;
                    ArrayList[] arrayListArr3 = arrayListArr;
                    List list5 = certPathCheckers;
                    TrustAnchor trustAnchor2 = findTrustAnchor;
                    PKIXPolicyNode pKIXPolicyNode3 = pKIXPolicyNode2;
                    int i17 = i16 + 1;
                    int wrapupCertB = RFC3280CertPathUtilities.wrapupCertB(certPath, i17, RFC3280CertPathUtilities.wrapupCertA(i8, x509Certificate));
                    Set<String> criticalExtensionOIDs2 = x509Certificate.getCriticalExtensionOIDs();
                    if (criticalExtensionOIDs2 != null) {
                        hashSet = new HashSet(criticalExtensionOIDs2);
                        hashSet.remove(RFC3280CertPathUtilities.KEY_USAGE);
                        hashSet.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                        hashSet.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                        hashSet.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                        hashSet.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                        hashSet.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                        hashSet.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                        hashSet.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                        hashSet.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                        hashSet.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                        hashSet.remove(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS);
                        hashSet.remove(Extension.extendedKeyUsage.getId());
                    } else {
                        hashSet = new HashSet();
                    }
                    RFC3280CertPathUtilities.wrapupCertF(certPath, i17, list5, hashSet);
                    PKIXPolicyNode wrapupCertG = RFC3280CertPathUtilities.wrapupCertG(certPath, pKIXExtendedParameters3, initialPolicies, i17, arrayListArr3, pKIXPolicyNode3, hashSet4);
                    if (wrapupCertB > 0 || wrapupCertG != null) {
                        return new PKIXCertPathValidatorResult(trustAnchor2, wrapupCertG, x509Certificate.getPublicKey());
                    }
                    throw new CertPathValidatorException("Path processing failed on policy.", null, certPath, i16);
                } catch (CertPathValidatorException e4) {
                    throw new ExtCertPathValidatorException("Algorithm identifier of public key of trust anchor could not be read.", e4, certPath, -1);
                }
            } catch (RuntimeException e5) {
                throw new ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", e5, certPath, i2);
            }
        } catch (AnnotatedException e6) {
            e = e6;
            list = certificates;
        }
    }

    static void checkCertificate(X509Certificate x509Certificate) throws AnnotatedException {
        try {
            TBSCertificate.getInstance(x509Certificate.getTBSCertificate());
        } catch (IllegalArgumentException e) {
            throw new AnnotatedException(e.getMessage());
        } catch (CertificateEncodingException e2) {
            throw new AnnotatedException("unable to process TBSCertificate", e2);
        }
    }
}
