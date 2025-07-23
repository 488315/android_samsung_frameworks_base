package com.android.internal.org.bouncycastle.jce.provider;

import android.sec.enterprise.certificate.CertificatePolicy;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1String;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.x500.RDN;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x500.style.BCStyle;
import com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints;
import com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint;
import com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName;
import com.android.internal.org.bouncycastle.asn1.x509.Extension;
import com.android.internal.org.bouncycastle.asn1.x509.GeneralName;
import com.android.internal.org.bouncycastle.asn1.x509.GeneralNames;
import com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree;
import com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint;
import com.android.internal.org.bouncycastle.asn1.x509.NameConstraints;
import com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation;
import com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker;
import com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters;
import com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector;
import com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters;
import com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil;
import com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
class RFC3280CertPathUtilities {
    public static final String ANY_POLICY = "2.5.29.32.0";
    protected static final int CRL_SIGN = 6;
    protected static final int KEY_CERT_SIGN = 5;
    private static final Class revChkClass = ClassUtil.loadClass(RFC3280CertPathUtilities.class, "java.security.cert.PKIXRevocationChecker");
    public static final String CERTIFICATE_POLICIES = Extension.certificatePolicies.getId();
    public static final String POLICY_MAPPINGS = Extension.policyMappings.getId();
    public static final String INHIBIT_ANY_POLICY = Extension.inhibitAnyPolicy.getId();
    public static final String ISSUING_DISTRIBUTION_POINT = Extension.issuingDistributionPoint.getId();
    public static final String FRESHEST_CRL = Extension.freshestCRL.getId();
    public static final String DELTA_CRL_INDICATOR = Extension.deltaCRLIndicator.getId();
    public static final String POLICY_CONSTRAINTS = Extension.policyConstraints.getId();
    public static final String BASIC_CONSTRAINTS = Extension.basicConstraints.getId();
    public static final String CRL_DISTRIBUTION_POINTS = Extension.cRLDistributionPoints.getId();
    public static final String SUBJECT_ALTERNATIVE_NAME = Extension.subjectAlternativeName.getId();
    public static final String NAME_CONSTRAINTS = Extension.nameConstraints.getId();
    public static final String AUTHORITY_KEY_IDENTIFIER = Extension.authorityKeyIdentifier.getId();
    public static final String KEY_USAGE = Extension.keyUsage.getId();
    public static final String CRL_NUMBER = Extension.cRLNumber.getId();
    protected static final String[] crlReasons = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    RFC3280CertPathUtilities() {
    }

    protected static void processCRLB2(DistributionPoint distributionPoint, Object obj, X509CRL x509crl) throws AnnotatedException {
        int i;
        GeneralName[] generalNameArr;
        try {
            IssuingDistributionPoint issuingDistributionPoint = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT));
            if (issuingDistributionPoint != null) {
                if (issuingDistributionPoint.getDistributionPoint() != null) {
                    DistributionPointName distributionPoint2 = IssuingDistributionPoint.getInstance(issuingDistributionPoint).getDistributionPoint();
                    ArrayList arrayList = new ArrayList();
                    if (distributionPoint2.getType() == 0) {
                        for (GeneralName generalName : GeneralNames.getInstance(distributionPoint2.getName()).getNames()) {
                            arrayList.add(generalName);
                        }
                    }
                    if (distributionPoint2.getType() == 1) {
                        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
                        try {
                            Enumeration objects = ASN1Sequence.getInstance(PrincipalUtils.getIssuerPrincipal(x509crl)).getObjects();
                            while (objects.hasMoreElements()) {
                                aSN1EncodableVector.add((ASN1Encodable) objects.nextElement());
                            }
                            aSN1EncodableVector.add(distributionPoint2.getName());
                            arrayList.add(new GeneralName(X500Name.getInstance(new DERSequence(aSN1EncodableVector))));
                        } catch (Exception e) {
                            throw new AnnotatedException("Could not read CRL issuer.", e);
                        }
                    }
                    if (distributionPoint.getDistributionPoint() != null) {
                        DistributionPointName distributionPoint3 = distributionPoint.getDistributionPoint();
                        GeneralName[] names = distributionPoint3.getType() == 0 ? GeneralNames.getInstance(distributionPoint3.getName()).getNames() : null;
                        if (distributionPoint3.getType() == 1) {
                            if (distributionPoint.getCRLIssuer() != null) {
                                generalNameArr = distributionPoint.getCRLIssuer().getNames();
                            } else {
                                generalNameArr = new GeneralName[1];
                                try {
                                    generalNameArr[0] = new GeneralName(PrincipalUtils.getEncodedIssuerPrincipal(obj));
                                } catch (Exception e2) {
                                    throw new AnnotatedException("Could not read certificate issuer.", e2);
                                }
                            }
                            names = generalNameArr;
                            for (int i2 = 0; i2 < names.length; i2++) {
                                Enumeration objects2 = ASN1Sequence.getInstance(names[i2].getName().toASN1Primitive()).getObjects();
                                ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
                                while (objects2.hasMoreElements()) {
                                    aSN1EncodableVector2.add((ASN1Encodable) objects2.nextElement());
                                }
                                aSN1EncodableVector2.add(distributionPoint3.getName());
                                names[i2] = new GeneralName(X500Name.getInstance(new DERSequence(aSN1EncodableVector2)));
                            }
                        }
                        if (names != null) {
                            while (i < names.length) {
                                i = arrayList.contains(names[i]) ? 0 : i + 1;
                            }
                        }
                        throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
                    }
                    if (distributionPoint.getCRLIssuer() == null) {
                        throw new AnnotatedException("Either the cRLIssuer or the distributionPoint field must be contained in DistributionPoint.");
                    }
                    GeneralName[] names2 = distributionPoint.getCRLIssuer().getNames();
                    while (i < names2.length) {
                        i = arrayList.contains(names2[i]) ? 0 : i + 1;
                    }
                    throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
                }
                try {
                    BasicConstraints basicConstraints = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension) obj, BASIC_CONSTRAINTS));
                    if (obj instanceof X509Certificate) {
                        if (issuingDistributionPoint.onlyContainsUserCerts() && basicConstraints != null && basicConstraints.isCA()) {
                            throw new AnnotatedException("CA Cert CRL only contains user certificates.");
                        }
                        if (issuingDistributionPoint.onlyContainsCACerts() && (basicConstraints == null || !basicConstraints.isCA())) {
                            throw new AnnotatedException("End CRL only contains CA certificates.");
                        }
                    }
                    if (issuingDistributionPoint.onlyContainsAttributeCerts()) {
                        throw new AnnotatedException("onlyContainsAttributeCerts boolean is asserted.");
                    }
                } catch (Exception e3) {
                    throw new AnnotatedException("Basic constraints extension could not be decoded.", e3);
                }
            }
        } catch (Exception e4) {
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", e4);
        }
    }

    protected static void processCRLB1(DistributionPoint distributionPoint, Object obj, X509CRL x509crl) throws AnnotatedException {
        ASN1Primitive extensionValue = CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT);
        boolean z = true;
        boolean z2 = extensionValue != null && IssuingDistributionPoint.getInstance(extensionValue).isIndirectCRL();
        try {
            byte[] encoded = PrincipalUtils.getIssuerPrincipal(x509crl).getEncoded();
            if (distributionPoint.getCRLIssuer() != null) {
                GeneralName[] names = distributionPoint.getCRLIssuer().getNames();
                boolean z3 = false;
                for (int i = 0; i < names.length; i++) {
                    if (names[i].getTagNo() == 4) {
                        try {
                            if (Arrays.areEqual(names[i].getName().toASN1Primitive().getEncoded(), encoded)) {
                                z3 = true;
                            }
                        } catch (IOException e) {
                            throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", e);
                        }
                    }
                }
                if (z3 && !z2) {
                    throw new AnnotatedException("Distribution point contains cRLIssuer field but CRL is not indirect.");
                }
                if (!z3) {
                    throw new AnnotatedException("CRL issuer of CRL does not match CRL issuer of distribution point.");
                }
                z = z3;
            } else if (!PrincipalUtils.getIssuerPrincipal(x509crl).equals(PrincipalUtils.getEncodedIssuerPrincipal(obj))) {
                z = false;
            }
            if (!z) {
                throw new AnnotatedException("Cannot find matching CRL issuer for certificate.");
            }
        } catch (IOException e2) {
            throw new AnnotatedException("Exception encoding CRL issuer: " + e2.getMessage(), e2);
        }
    }

    protected static ReasonsMask processCRLD(X509CRL x509crl, DistributionPoint distributionPoint) throws AnnotatedException {
        ReasonsMask reasonsMask;
        ReasonsMask reasonsMask2;
        try {
            IssuingDistributionPoint issuingDistributionPoint = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT));
            if (issuingDistributionPoint != null && issuingDistributionPoint.getOnlySomeReasons() != null && distributionPoint.getReasons() != null) {
                return new ReasonsMask(distributionPoint.getReasons()).intersect(new ReasonsMask(issuingDistributionPoint.getOnlySomeReasons()));
            }
            if ((issuingDistributionPoint == null || issuingDistributionPoint.getOnlySomeReasons() == null) && distributionPoint.getReasons() == null) {
                return ReasonsMask.allReasons;
            }
            if (distributionPoint.getReasons() == null) {
                reasonsMask = ReasonsMask.allReasons;
            } else {
                reasonsMask = new ReasonsMask(distributionPoint.getReasons());
            }
            if (issuingDistributionPoint == null) {
                reasonsMask2 = ReasonsMask.allReasons;
            } else {
                reasonsMask2 = new ReasonsMask(issuingDistributionPoint.getOnlySomeReasons());
            }
            return reasonsMask.intersect(reasonsMask2);
        } catch (Exception e) {
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", e);
        }
    }

    protected static Set processCRLF(X509CRL x509crl, Object obj, X509Certificate x509Certificate, PublicKey publicKey, PKIXExtendedParameters pKIXExtendedParameters, List list, JcaJceHelper jcaJceHelper) throws AnnotatedException {
        int i;
        X509CertSelector x509CertSelector = new X509CertSelector();
        try {
            x509CertSelector.setSubject(PrincipalUtils.getIssuerPrincipal(x509crl).getEncoded());
            PKIXCertStoreSelector<? extends Certificate> build = new PKIXCertStoreSelector.Builder(x509CertSelector).build();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            try {
                CertPathValidatorUtilities.findCertificates(linkedHashSet, build, pKIXExtendedParameters.getCertificateStores());
                CertPathValidatorUtilities.findCertificates(linkedHashSet, build, pKIXExtendedParameters.getCertStores());
                linkedHashSet.add(x509Certificate);
                Iterator it = linkedHashSet.iterator();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    X509Certificate x509Certificate2 = (X509Certificate) it.next();
                    if (x509Certificate2.equals(x509Certificate)) {
                        arrayList.add(x509Certificate2);
                        arrayList2.add(publicKey);
                    } else {
                        try {
                            PKIXCertPathBuilderSpi pKIXCertPathBuilderSpi = new PKIXCertPathBuilderSpi(true);
                            X509CertSelector x509CertSelector2 = new X509CertSelector();
                            x509CertSelector2.setCertificate(x509Certificate2);
                            PKIXExtendedParameters.Builder targetConstraints = new PKIXExtendedParameters.Builder(pKIXExtendedParameters).setTargetConstraints(new PKIXCertStoreSelector.Builder(x509CertSelector2).build());
                            if (list.contains(x509Certificate2)) {
                                targetConstraints.setRevocationEnabled(false);
                            } else {
                                targetConstraints.setRevocationEnabled(true);
                            }
                            List<? extends Certificate> certificates = pKIXCertPathBuilderSpi.engineBuild(new PKIXExtendedBuilderParameters.Builder(targetConstraints.build()).build()).getCertPath().getCertificates();
                            arrayList.add(x509Certificate2);
                            arrayList2.add(CertPathValidatorUtilities.getNextWorkingKey(certificates, 0, jcaJceHelper));
                        } catch (CertPathBuilderException e) {
                            throw new AnnotatedException("CertPath for CRL signer failed to validate.", e);
                        } catch (CertPathValidatorException e2) {
                            throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", e2);
                        } catch (Exception e3) {
                            throw new AnnotatedException(e3.getMessage());
                        }
                    }
                }
                HashSet hashSet = new HashSet();
                AnnotatedException annotatedException = null;
                for (i = 0; i < arrayList.size(); i++) {
                    boolean[] keyUsage = ((X509Certificate) arrayList.get(i)).getKeyUsage();
                    if (keyUsage != null && (keyUsage.length <= 6 || !keyUsage[6])) {
                        annotatedException = new AnnotatedException("Issuer certificate key usage extension does not permit CRL signing.");
                    } else {
                        hashSet.add(arrayList2.get(i));
                    }
                }
                if (hashSet.isEmpty() && annotatedException == null) {
                    throw new AnnotatedException("Cannot find a valid issuer certificate.");
                }
                if (!hashSet.isEmpty() || annotatedException == null) {
                    return hashSet;
                }
                throw annotatedException;
            } catch (AnnotatedException e4) {
                throw new AnnotatedException("Issuer certificate for CRL cannot be searched.", e4);
            }
        } catch (IOException e5) {
            throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate for CRL could not be set.", e5);
        }
    }

    protected static PublicKey processCRLG(X509CRL x509crl, Set set) throws AnnotatedException {
        Iterator it = set.iterator();
        Exception e = null;
        while (it.hasNext()) {
            PublicKey publicKey = (PublicKey) it.next();
            try {
                x509crl.verify(publicKey);
                return publicKey;
            } catch (Exception e2) {
                e = e2;
            }
        }
        throw new AnnotatedException("Cannot verify CRL.", e);
    }

    protected static X509CRL processCRLH(Set set, PublicKey publicKey) throws AnnotatedException {
        Iterator it = set.iterator();
        Exception e = null;
        while (it.hasNext()) {
            X509CRL x509crl = (X509CRL) it.next();
            try {
                x509crl.verify(publicKey);
                return x509crl;
            } catch (Exception e2) {
                e = e2;
            }
        }
        if (e == null) {
            return null;
        }
        throw new AnnotatedException("Cannot verify delta CRL.", e);
    }

    protected static void processCRLC(X509CRL x509crl, X509CRL x509crl2, PKIXExtendedParameters pKIXExtendedParameters) throws AnnotatedException {
        if (x509crl == null) {
            return;
        }
        if (x509crl.hasUnsupportedCriticalExtension()) {
            throw new AnnotatedException("delta CRL has unsupported critical extensions");
        }
        try {
            String str = ISSUING_DISTRIBUTION_POINT;
            IssuingDistributionPoint issuingDistributionPoint = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl2, str));
            if (pKIXExtendedParameters.isUseDeltasEnabled()) {
                if (!PrincipalUtils.getIssuerPrincipal(x509crl).equals(PrincipalUtils.getIssuerPrincipal(x509crl2))) {
                    throw new AnnotatedException("Complete CRL issuer does not match delta CRL issuer.");
                }
                try {
                    IssuingDistributionPoint issuingDistributionPoint2 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl, str));
                    if (issuingDistributionPoint != null ? !issuingDistributionPoint.equals(issuingDistributionPoint2) : issuingDistributionPoint2 != null) {
                        throw new AnnotatedException("Issuing distribution point extension from delta CRL and complete CRL does not match.");
                    }
                    try {
                        String str2 = AUTHORITY_KEY_IDENTIFIER;
                        ASN1Primitive extensionValue = CertPathValidatorUtilities.getExtensionValue(x509crl2, str2);
                        try {
                            ASN1Primitive extensionValue2 = CertPathValidatorUtilities.getExtensionValue(x509crl, str2);
                            if (extensionValue == null) {
                                throw new AnnotatedException("CRL authority key identifier is null.");
                            }
                            if (extensionValue2 == null) {
                                throw new AnnotatedException("Delta CRL authority key identifier is null.");
                            }
                            if (!extensionValue.equals(extensionValue2)) {
                                throw new AnnotatedException("Delta CRL authority key identifier does not match complete CRL authority key identifier.");
                            }
                        } catch (AnnotatedException e) {
                            throw new AnnotatedException("Authority key identifier extension could not be extracted from delta CRL.", e);
                        }
                    } catch (AnnotatedException e2) {
                        throw new AnnotatedException("Authority key identifier extension could not be extracted from complete CRL.", e2);
                    }
                } catch (Exception e3) {
                    throw new AnnotatedException("Issuing distribution point extension from delta CRL could not be decoded.", e3);
                }
            }
        } catch (Exception e4) {
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", e4);
        }
    }

    protected static void processCRLI(Date date, X509CRL x509crl, Object obj, CertStatus certStatus, PKIXExtendedParameters pKIXExtendedParameters) throws AnnotatedException {
        if (!pKIXExtendedParameters.isUseDeltasEnabled() || x509crl == null) {
            return;
        }
        CertPathValidatorUtilities.getCertStatus(date, x509crl, obj, certStatus);
    }

    protected static void processCRLJ(Date date, X509CRL x509crl, Object obj, CertStatus certStatus) throws AnnotatedException {
        if (certStatus.getCertStatus() == 11) {
            CertPathValidatorUtilities.getCertStatus(date, x509crl, obj, certStatus);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x014b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0153, code lost:
    
        throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Certificate policies extension could not be decoded.", r0, r16, r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0076, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00aa, code lost:
    
        r5 = r18[r7].iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00b4, code lost:
    
        if (r5.hasNext() == false) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00b6, code lost:
    
        r6 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) r5.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00c6, code lost:
    
        if (com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ANY_POLICY.equals(r6.getValidPolicy()) == false) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00d0, code lost:
    
        r5 = ((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r4, com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.CERTIFICATE_POLICIES)).getObjects();
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00d8, code lost:
    
        if (r5.hasMoreElements() == false) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00da, code lost:
    
        r8 = com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation.getInstance(r5.nextElement());
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00ee, code lost:
    
        if (com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ANY_POLICY.equals(r8.getPolicyIdentifier().getId()) == false) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00f0, code lost:
    
        r5 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getQualifierSet(r8.getPolicyQualifiers());
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x010c, code lost:
    
        r10 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0111, code lost:
    
        if (r4.getCriticalExtensionOIDs() == null) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0113, code lost:
    
        r12 = r4.getCriticalExtensionOIDs().contains(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0120, code lost:
    
        r5 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) r6.getParent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x012e, code lost:
    
        if (com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ANY_POLICY.equals(r5.getValidPolicy()) == false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0130, code lost:
    
        r5 = new com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode(new java.util.ArrayList(), r7, (java.util.Set) r13.get(r11), r5, r10, r11, r12);
        r5.addChild(r5);
        r18[r7].add(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x011f, code lost:
    
        r12 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00f9, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0101, code lost:
    
        throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy qualifier info set could not be decoded.", r0, r16, r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0102, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x010a, code lost:
    
        throw new java.security.cert.CertPathValidatorException("Policy information could not be decoded.", r0, r16, r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x010b, code lost:
    
        r5 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode prepareCertB(java.security.cert.CertPath r16, int r17, java.util.List[] r18, com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode r19, int r20) throws java.security.cert.CertPathValidatorException {
        /*
            Method dump skipped, instructions count: 432
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareCertB(java.security.cert.CertPath, int, java.util.List[], com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode, int):com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode");
    }

    protected static void prepareNextCertA(CertPath certPath, int i) throws CertPathValidatorException {
        try {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), POLICY_MAPPINGS));
            if (aSN1Sequence != null) {
                for (int i2 = 0; i2 < aSN1Sequence.size(); i2++) {
                    try {
                        ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(i2));
                        ASN1ObjectIdentifier aSN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(aSN1Sequence2.getObjectAt(0));
                        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = ASN1ObjectIdentifier.getInstance(aSN1Sequence2.getObjectAt(1));
                        if (ANY_POLICY.equals(aSN1ObjectIdentifier.getId())) {
                            throw new CertPathValidatorException("IssuerDomainPolicy is anyPolicy", null, certPath, i);
                        }
                        if (ANY_POLICY.equals(aSN1ObjectIdentifier2.getId())) {
                            throw new CertPathValidatorException("SubjectDomainPolicy is anyPolicy", null, certPath, i);
                        }
                    } catch (Exception e) {
                        throw new ExtCertPathValidatorException("Policy mappings extension contents could not be decoded.", e, certPath, i);
                    }
                }
            }
        } catch (AnnotatedException e2) {
            throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", e2, certPath, i);
        }
    }

    protected static void processCertF(CertPath certPath, int i, PKIXPolicyNode pKIXPolicyNode, int i2) throws CertPathValidatorException {
        if (i2 <= 0 && pKIXPolicyNode == null) {
            throw new ExtCertPathValidatorException("No valid policy tree found when one expected.", null, certPath, i);
        }
    }

    protected static PKIXPolicyNode processCertE(CertPath certPath, int i, PKIXPolicyNode pKIXPolicyNode) throws CertPathValidatorException {
        try {
            if (ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), CERTIFICATE_POLICIES)) == null) {
                return null;
            }
            return pKIXPolicyNode;
        } catch (AnnotatedException e) {
            throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", e, certPath, i);
        }
    }

    protected static void processCertBC(CertPath certPath, int i, PKIXNameConstraintValidator pKIXNameConstraintValidator, boolean z) throws CertPathValidatorException {
        List<? extends Certificate> certificates = certPath.getCertificates();
        X509Certificate x509Certificate = (X509Certificate) certificates.get(i);
        int size = certificates.size();
        int i2 = size - i;
        if (!CertPathValidatorUtilities.isSelfIssued(x509Certificate) || (i2 >= size && !z)) {
            try {
                ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(PrincipalUtils.getSubjectPrincipal(x509Certificate));
                try {
                    pKIXNameConstraintValidator.checkPermittedDN(aSN1Sequence);
                    pKIXNameConstraintValidator.checkExcludedDN(aSN1Sequence);
                    try {
                        GeneralNames generalNames = GeneralNames.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, SUBJECT_ALTERNATIVE_NAME));
                        RDN[] rDNs = X500Name.getInstance(aSN1Sequence).getRDNs(BCStyle.EmailAddress);
                        for (int i3 = 0; i3 != rDNs.length; i3++) {
                            GeneralName generalName = new GeneralName(1, ((ASN1String) rDNs[i3].getFirst().getValue()).getString());
                            try {
                                pKIXNameConstraintValidator.checkPermitted(generalName);
                                pKIXNameConstraintValidator.checkExcluded(generalName);
                            } catch (PKIXNameConstraintValidatorException e) {
                                throw new CertPathValidatorException("Subtree check for certificate subject alternative email failed.", e, certPath, i);
                            }
                        }
                        if (generalNames != null) {
                            try {
                                GeneralName[] names = generalNames.getNames();
                                for (int i4 = 0; i4 < names.length; i4++) {
                                    try {
                                        pKIXNameConstraintValidator.checkPermitted(names[i4]);
                                        pKIXNameConstraintValidator.checkExcluded(names[i4]);
                                    } catch (PKIXNameConstraintValidatorException e2) {
                                        throw new CertPathValidatorException("Subtree check for certificate subject alternative name failed.", e2, certPath, i);
                                    }
                                }
                            } catch (Exception e3) {
                                throw new CertPathValidatorException("Subject alternative name contents could not be decoded.", e3, certPath, i);
                            }
                        }
                    } catch (Exception e4) {
                        throw new CertPathValidatorException("Subject alternative name extension could not be decoded.", e4, certPath, i);
                    }
                } catch (PKIXNameConstraintValidatorException e5) {
                    throw new CertPathValidatorException("Subtree check for certificate subject failed.", e5, certPath, i);
                }
            } catch (Exception e6) {
                throw new CertPathValidatorException("Exception extracting subject name when checking subtrees.", e6, certPath, i);
            }
        }
    }

    protected static PKIXPolicyNode processCertD(CertPath certPath, int i, Set set, PKIXPolicyNode pKIXPolicyNode, List[] listArr, int i2, boolean z) throws CertPathValidatorException {
        String str;
        int i3;
        List<? extends Certificate> certificates = certPath.getCertificates();
        X509Certificate x509Certificate = (X509Certificate) certificates.get(i);
        int size = certificates.size();
        int i4 = size - i;
        try {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, CERTIFICATE_POLICIES));
            if (aSN1Sequence == null || pKIXPolicyNode == null) {
                return null;
            }
            Enumeration objects = aSN1Sequence.getObjects();
            HashSet hashSet = new HashSet();
            while (objects.hasMoreElements()) {
                PolicyInformation policyInformation = PolicyInformation.getInstance(objects.nextElement());
                ASN1ObjectIdentifier policyIdentifier = policyInformation.getPolicyIdentifier();
                hashSet.add(policyIdentifier.getId());
                if (!ANY_POLICY.equals(policyIdentifier.getId())) {
                    try {
                        Set qualifierSet = CertPathValidatorUtilities.getQualifierSet(policyInformation.getPolicyQualifiers());
                        if (!CertPathValidatorUtilities.processCertD1i(i4, listArr, policyIdentifier, qualifierSet)) {
                            CertPathValidatorUtilities.processCertD1ii(i4, listArr, policyIdentifier, qualifierSet);
                        }
                    } catch (CertPathValidatorException e) {
                        throw new ExtCertPathValidatorException("Policy qualifier info set could not be build.", e, certPath, i);
                    }
                }
            }
            if (set.isEmpty() || set.contains(ANY_POLICY)) {
                set.clear();
                set.addAll(hashSet);
            } else {
                HashSet hashSet2 = new HashSet();
                for (Object obj : set) {
                    if (hashSet.contains(obj)) {
                        hashSet2.add(obj);
                    }
                }
                set.clear();
                set.addAll(hashSet2);
            }
            if (i2 > 0 || ((i4 < size || z) && CertPathValidatorUtilities.isSelfIssued(x509Certificate))) {
                Enumeration objects2 = aSN1Sequence.getObjects();
                while (true) {
                    if (!objects2.hasMoreElements()) {
                        break;
                    }
                    PolicyInformation policyInformation2 = PolicyInformation.getInstance(objects2.nextElement());
                    if (ANY_POLICY.equals(policyInformation2.getPolicyIdentifier().getId())) {
                        Set qualifierSet2 = CertPathValidatorUtilities.getQualifierSet(policyInformation2.getPolicyQualifiers());
                        List list = listArr[i4 - 1];
                        for (int i5 = 0; i5 < list.size(); i5++) {
                            PKIXPolicyNode pKIXPolicyNode2 = (PKIXPolicyNode) list.get(i5);
                            for (Object obj2 : pKIXPolicyNode2.getExpectedPolicies()) {
                                if (obj2 instanceof String) {
                                    str = (String) obj2;
                                } else if (obj2 instanceof ASN1ObjectIdentifier) {
                                    str = ((ASN1ObjectIdentifier) obj2).getId();
                                }
                                String str2 = str;
                                Iterator children = pKIXPolicyNode2.getChildren();
                                boolean z2 = false;
                                while (children.hasNext()) {
                                    if (str2.equals(((PKIXPolicyNode) children.next()).getValidPolicy())) {
                                        z2 = true;
                                    }
                                }
                                if (!z2) {
                                    HashSet hashSet3 = new HashSet();
                                    hashSet3.add(str2);
                                    PKIXPolicyNode pKIXPolicyNode3 = new PKIXPolicyNode(new ArrayList(), i4, hashSet3, pKIXPolicyNode2, qualifierSet2, str2, false);
                                    pKIXPolicyNode2.addChild(pKIXPolicyNode3);
                                    listArr[i4].add(pKIXPolicyNode3);
                                }
                            }
                        }
                    }
                }
            }
            PKIXPolicyNode pKIXPolicyNode4 = pKIXPolicyNode;
            for (int i6 = i4 - 1; i6 >= 0; i6--) {
                List list2 = listArr[i6];
                for (0; i3 < list2.size(); i3 + 1) {
                    PKIXPolicyNode pKIXPolicyNode5 = (PKIXPolicyNode) list2.get(i3);
                    i3 = (pKIXPolicyNode5.hasChildren() || (pKIXPolicyNode4 = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode4, listArr, pKIXPolicyNode5)) != null) ? i3 + 1 : 0;
                }
            }
            Set<String> criticalExtensionOIDs = x509Certificate.getCriticalExtensionOIDs();
            if (criticalExtensionOIDs != null) {
                boolean contains = criticalExtensionOIDs.contains(CERTIFICATE_POLICIES);
                List list3 = listArr[i4];
                for (int i7 = 0; i7 < list3.size(); i7++) {
                    ((PKIXPolicyNode) list3.get(i7)).setCritical(contains);
                }
            }
            return pKIXPolicyNode4;
        } catch (AnnotatedException e2) {
            throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", e2, certPath, i);
        }
    }

    protected static void processCertA(CertPath certPath, PKIXExtendedParameters pKIXExtendedParameters, Date date, PKIXCertRevocationChecker pKIXCertRevocationChecker, int i, PublicKey publicKey, boolean z, X500Name x500Name, X509Certificate x509Certificate) throws CertPathValidatorException {
        X509Certificate x509Certificate2 = (X509Certificate) certPath.getCertificates().get(i);
        if (!z) {
            try {
                CertPathValidatorUtilities.verifyX509Certificate(x509Certificate2, publicKey, pKIXExtendedParameters.getSigProvider());
            } catch (GeneralSecurityException e) {
                throw new ExtCertPathValidatorException("Could not validate certificate signature.", e, certPath, i);
            }
        }
        try {
            Date validCertDateFromValidityModel = CertPathValidatorUtilities.getValidCertDateFromValidityModel(date, pKIXExtendedParameters.getValidityModel(), certPath, i);
            try {
                x509Certificate2.checkValidity(validCertDateFromValidityModel);
                if (pKIXCertRevocationChecker != null) {
                    pKIXCertRevocationChecker.initialize(new PKIXCertRevocationCheckerParameters(pKIXExtendedParameters, validCertDateFromValidityModel, certPath, i, x509Certificate, publicKey));
                    pKIXCertRevocationChecker.check(x509Certificate2);
                }
                X500Name issuerPrincipal = PrincipalUtils.getIssuerPrincipal(x509Certificate2);
                if (issuerPrincipal.equals(x500Name)) {
                    return;
                }
                throw new ExtCertPathValidatorException("IssuerName(" + issuerPrincipal + ") does not match SubjectName(" + x500Name + ") of signing certificate.", null, certPath, i);
            } catch (CertificateExpiredException e2) {
                throw new ExtCertPathValidatorException("Could not validate certificate: " + e2.getMessage(), e2, certPath, i);
            } catch (CertificateNotYetValidException e3) {
                throw new ExtCertPathValidatorException("Could not validate certificate: " + e3.getMessage(), e3, certPath, i);
            }
        } catch (AnnotatedException e4) {
            throw new ExtCertPathValidatorException("Could not validate time of certificate.", e4, certPath, i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002e, code lost:
    
        r3 = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(r1, false).intValueExact();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0037, code lost:
    
        if (r3 >= r5) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0039, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static int prepareNextCertI1(java.security.cert.CertPath r3, int r4, int r5) throws java.security.cert.CertPathValidatorException {
        /*
            java.util.List r0 = r3.getCertificates()
            java.lang.Object r0 = r0.get(r4)
            java.security.cert.X509Certificate r0 = (java.security.cert.X509Certificate) r0
            java.lang.String r1 = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.POLICY_CONSTRAINTS     // Catch: java.lang.Exception -> L44
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive r0 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r0, r1)     // Catch: java.lang.Exception -> L44
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence r0 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(r0)     // Catch: java.lang.Exception -> L44
            if (r0 == 0) goto L43
            java.util.Enumeration r0 = r0.getObjects()
        L1a:
            boolean r1 = r0.hasMoreElements()
            if (r1 == 0) goto L43
            java.lang.Object r1 = r0.nextElement()     // Catch: java.lang.IllegalArgumentException -> L3a
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject r1 = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(r1)     // Catch: java.lang.IllegalArgumentException -> L3a
            int r2 = r1.getTagNo()     // Catch: java.lang.IllegalArgumentException -> L3a
            if (r2 != 0) goto L1a
            r0 = 0
            com.android.internal.org.bouncycastle.asn1.ASN1Integer r0 = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(r1, r0)     // Catch: java.lang.IllegalArgumentException -> L3a
            int r3 = r0.intValueExact()     // Catch: java.lang.IllegalArgumentException -> L3a
            if (r3 >= r5) goto L43
            return r3
        L3a:
            r5 = move-exception
            com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException r0 = new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException
            java.lang.String r1 = "Policy constraints extension contents cannot be decoded."
            r0.<init>(r1, r5, r3, r4)
            throw r0
        L43:
            return r5
        L44:
            r5 = move-exception
            com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException r0 = new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException
            java.lang.String r1 = "Policy constraints extension cannot be decoded."
            r0.<init>(r1, r5, r3, r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertI1(java.security.cert.CertPath, int, int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002f, code lost:
    
        r4 = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(r1, false).intValueExact();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0038, code lost:
    
        if (r4 >= r6) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003a, code lost:
    
        return r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static int prepareNextCertI2(java.security.cert.CertPath r4, int r5, int r6) throws java.security.cert.CertPathValidatorException {
        /*
            java.util.List r0 = r4.getCertificates()
            java.lang.Object r0 = r0.get(r5)
            java.security.cert.X509Certificate r0 = (java.security.cert.X509Certificate) r0
            java.lang.String r1 = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.POLICY_CONSTRAINTS     // Catch: java.lang.Exception -> L45
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive r0 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r0, r1)     // Catch: java.lang.Exception -> L45
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence r0 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(r0)     // Catch: java.lang.Exception -> L45
            if (r0 == 0) goto L44
            java.util.Enumeration r0 = r0.getObjects()
        L1a:
            boolean r1 = r0.hasMoreElements()
            if (r1 == 0) goto L44
            java.lang.Object r1 = r0.nextElement()     // Catch: java.lang.IllegalArgumentException -> L3b
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject r1 = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(r1)     // Catch: java.lang.IllegalArgumentException -> L3b
            int r2 = r1.getTagNo()     // Catch: java.lang.IllegalArgumentException -> L3b
            r3 = 1
            if (r2 != r3) goto L1a
            r0 = 0
            com.android.internal.org.bouncycastle.asn1.ASN1Integer r0 = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(r1, r0)     // Catch: java.lang.IllegalArgumentException -> L3b
            int r4 = r0.intValueExact()     // Catch: java.lang.IllegalArgumentException -> L3b
            if (r4 >= r6) goto L44
            return r4
        L3b:
            r6 = move-exception
            com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException r0 = new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException
            java.lang.String r1 = "Policy constraints extension contents cannot be decoded."
            r0.<init>(r1, r6, r4, r5)
            throw r0
        L44:
            return r6
        L45:
            r6 = move-exception
            com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException r0 = new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException
            java.lang.String r1 = "Policy constraints extension cannot be decoded."
            r0.<init>(r1, r6, r4, r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertI2(java.security.cert.CertPath, int, int):int");
    }

    protected static void prepareNextCertG(CertPath certPath, int i, PKIXNameConstraintValidator pKIXNameConstraintValidator) throws CertPathValidatorException {
        try {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), NAME_CONSTRAINTS));
            NameConstraints nameConstraints = aSN1Sequence != null ? NameConstraints.getInstance(aSN1Sequence) : null;
            if (nameConstraints != null) {
                GeneralSubtree[] permittedSubtrees = nameConstraints.getPermittedSubtrees();
                if (permittedSubtrees != null) {
                    try {
                        pKIXNameConstraintValidator.intersectPermittedSubtree(permittedSubtrees);
                    } catch (Exception e) {
                        throw new ExtCertPathValidatorException("Permitted subtrees cannot be build from name constraints extension.", e, certPath, i);
                    }
                }
                GeneralSubtree[] excludedSubtrees = nameConstraints.getExcludedSubtrees();
                if (excludedSubtrees != null) {
                    for (int i2 = 0; i2 != excludedSubtrees.length; i2++) {
                        try {
                            pKIXNameConstraintValidator.addExcludedSubtree(excludedSubtrees[i2]);
                        } catch (Exception e2) {
                            throw new ExtCertPathValidatorException("Excluded subtrees cannot be build from name constraints extension.", e2, certPath, i);
                        }
                    }
                }
            }
        } catch (Exception e3) {
            throw new ExtCertPathValidatorException("Name constraints extension could not be decoded.", e3, certPath, i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x0121, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void checkCRL(com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters r19, com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint r20, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters r21, java.util.Date r22, java.util.Date r23, java.security.cert.X509Certificate r24, java.security.cert.X509Certificate r25, java.security.PublicKey r26, com.android.internal.org.bouncycastle.jce.provider.CertStatus r27, com.android.internal.org.bouncycastle.jce.provider.ReasonsMask r28, java.util.List r29, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper r30) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException, com.android.internal.org.bouncycastle.jce.provider.RecoverableCertPathValidatorException {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.checkCRL(com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters, com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters, java.util.Date, java.util.Date, java.security.cert.X509Certificate, java.security.cert.X509Certificate, java.security.PublicKey, com.android.internal.org.bouncycastle.jce.provider.CertStatus, com.android.internal.org.bouncycastle.jce.provider.ReasonsMask, java.util.List, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static void checkCRLs(com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters r19, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters r20, java.util.Date r21, java.util.Date r22, java.security.cert.X509Certificate r23, java.security.cert.X509Certificate r24, java.security.PublicKey r25, java.util.List r26, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper r27) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException, com.android.internal.org.bouncycastle.jce.provider.RecoverableCertPathValidatorException {
        /*
            Method dump skipped, instructions count: 378
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.checkCRLs(com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters, java.util.Date, java.util.Date, java.security.cert.X509Certificate, java.security.cert.X509Certificate, java.security.PublicKey, java.util.List, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper):void");
    }

    protected static int prepareNextCertJ(CertPath certPath, int i, int i2) throws CertPathValidatorException {
        int intValueExact;
        try {
            ASN1Integer aSN1Integer = ASN1Integer.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), INHIBIT_ANY_POLICY));
            return (aSN1Integer == null || (intValueExact = aSN1Integer.intValueExact()) >= i2) ? i2 : intValueExact;
        } catch (Exception e) {
            throw new ExtCertPathValidatorException("Inhibit any-policy extension cannot be decoded.", e, certPath, i);
        }
    }

    protected static void prepareNextCertK(CertPath certPath, int i) throws CertPathValidatorException {
        try {
            BasicConstraints basicConstraints = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), BASIC_CONSTRAINTS));
            if (basicConstraints != null) {
                if (!basicConstraints.isCA()) {
                    throw new CertPathValidatorException("Not a CA certificate", null, certPath, i);
                }
                return;
            }
            throw new CertPathValidatorException("Intermediate certificate lacks BasicConstraints", null, certPath, i);
        } catch (Exception e) {
            throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", e, certPath, i);
        }
    }

    protected static int prepareNextCertL(CertPath certPath, int i, int i2) throws CertPathValidatorException {
        if (CertPathValidatorUtilities.isSelfIssued((X509Certificate) certPath.getCertificates().get(i))) {
            return i2;
        }
        if (i2 > 0) {
            return i2 - 1;
        }
        throw new ExtCertPathValidatorException("Max path length not greater than zero", null, certPath, i);
    }

    protected static int prepareNextCertM(CertPath certPath, int i, int i2) throws CertPathValidatorException {
        ASN1Integer pathLenConstraintInteger;
        try {
            BasicConstraints basicConstraints = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), BASIC_CONSTRAINTS));
            return (basicConstraints == null || !basicConstraints.isCA() || (pathLenConstraintInteger = basicConstraints.getPathLenConstraintInteger()) == null) ? i2 : Math.min(i2, pathLenConstraintInteger.intPositiveValueExact());
        } catch (Exception e) {
            throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", e, certPath, i);
        }
    }

    protected static void prepareNextCertN(CertPath certPath, int i) throws CertPathValidatorException {
        boolean[] keyUsage = ((X509Certificate) certPath.getCertificates().get(i)).getKeyUsage();
        if (keyUsage != null) {
            if (keyUsage.length <= 5 || !keyUsage[5]) {
                throw new ExtCertPathValidatorException("Issuer certificate keyusage extension is critical and does not permit key signing.", null, certPath, i);
            }
        }
    }

    protected static void prepareNextCertO(CertPath certPath, int i, Set set, List list) throws CertPathValidatorException {
        X509Certificate x509Certificate = (X509Certificate) certPath.getCertificates().get(i);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                ((PKIXCertPathChecker) it.next()).check(x509Certificate, set);
            } catch (CertPathValidatorException e) {
                throw new CertPathValidatorException(e.getMessage(), e.getCause(), certPath, i);
            }
        }
        if (set.isEmpty()) {
            return;
        }
        throw new ExtCertPathValidatorException("Certificate has unsupported critical extension: " + set, null, certPath, i);
    }

    protected static int prepareNextCertH1(CertPath certPath, int i, int i2) {
        return (CertPathValidatorUtilities.isSelfIssued((X509Certificate) certPath.getCertificates().get(i)) || i2 == 0) ? i2 : i2 - 1;
    }

    protected static int prepareNextCertH2(CertPath certPath, int i, int i2) {
        return (CertPathValidatorUtilities.isSelfIssued((X509Certificate) certPath.getCertificates().get(i)) || i2 == 0) ? i2 : i2 - 1;
    }

    protected static int prepareNextCertH3(CertPath certPath, int i, int i2) {
        return (CertPathValidatorUtilities.isSelfIssued((X509Certificate) certPath.getCertificates().get(i)) || i2 == 0) ? i2 : i2 - 1;
    }

    protected static int wrapupCertA(int i, X509Certificate x509Certificate) {
        return (CertPathValidatorUtilities.isSelfIssued(x509Certificate) || i == 0) ? i : i - 1;
    }

    protected static int wrapupCertB(CertPath certPath, int i, int i2) throws CertPathValidatorException {
        try {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), POLICY_CONSTRAINTS));
            if (aSN1Sequence != null) {
                Enumeration objects = aSN1Sequence.getObjects();
                while (objects.hasMoreElements()) {
                    ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objects.nextElement();
                    if (aSN1TaggedObject.getTagNo() == 0) {
                        try {
                            if (ASN1Integer.getInstance(aSN1TaggedObject, false).intValueExact() == 0) {
                                return 0;
                            }
                        } catch (Exception e) {
                            throw new ExtCertPathValidatorException("Policy constraints requireExplicitPolicy field could not be decoded.", e, certPath, i);
                        }
                    }
                }
            }
            return i2;
        } catch (AnnotatedException e2) {
            throw new ExtCertPathValidatorException("Policy constraints could not be decoded.", e2, certPath, i);
        }
    }

    protected static void wrapupCertF(CertPath certPath, int i, List list, Set set) throws CertPathValidatorException {
        X509Certificate x509Certificate = (X509Certificate) certPath.getCertificates().get(i);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                ((PKIXCertPathChecker) it.next()).check(x509Certificate, set);
            } catch (CertPathValidatorException e) {
                throw new ExtCertPathValidatorException(e.getMessage(), e, certPath, i);
            } catch (Exception e2) {
                throw new CertPathValidatorException(CertificatePolicy.MSG_ADDITIONAL_CHECKER, e2, certPath, i);
            }
        }
        if (set.isEmpty()) {
            return;
        }
        throw new ExtCertPathValidatorException("Certificate has unsupported critical extension: " + set, null, certPath, i);
    }

    protected static PKIXPolicyNode wrapupCertG(CertPath certPath, PKIXExtendedParameters pKIXExtendedParameters, Set set, int i, List[] listArr, PKIXPolicyNode pKIXPolicyNode, Set set2) throws CertPathValidatorException {
        int size = certPath.getCertificates().size();
        if (pKIXPolicyNode == null) {
            if (pKIXExtendedParameters.isExplicitPolicyRequired()) {
                throw new ExtCertPathValidatorException("Explicit policy requested but none available.", null, certPath, i);
            }
            return null;
        }
        if (CertPathValidatorUtilities.isAnyPolicy(set)) {
            if (pKIXExtendedParameters.isExplicitPolicyRequired()) {
                if (set2.isEmpty()) {
                    throw new ExtCertPathValidatorException("Explicit policy requested but none available.", null, certPath, i);
                }
                HashSet hashSet = new HashSet();
                for (List list : listArr) {
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        PKIXPolicyNode pKIXPolicyNode2 = (PKIXPolicyNode) list.get(i2);
                        if (ANY_POLICY.equals(pKIXPolicyNode2.getValidPolicy())) {
                            Iterator children = pKIXPolicyNode2.getChildren();
                            while (children.hasNext()) {
                                hashSet.add(children.next());
                            }
                        }
                    }
                }
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    set2.contains(((PKIXPolicyNode) it.next()).getValidPolicy());
                }
                if (pKIXPolicyNode != null) {
                    for (int i3 = size - 1; i3 >= 0; i3--) {
                        List list2 = listArr[i3];
                        for (int i4 = 0; i4 < list2.size(); i4++) {
                            PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) list2.get(i4);
                            if (!pKIXPolicyNode3.hasChildren()) {
                                pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode3);
                            }
                        }
                    }
                    return pKIXPolicyNode;
                }
            }
            return pKIXPolicyNode;
        }
        HashSet<PKIXPolicyNode> hashSet2 = new HashSet();
        for (List list3 : listArr) {
            for (int i5 = 0; i5 < list3.size(); i5++) {
                PKIXPolicyNode pKIXPolicyNode4 = (PKIXPolicyNode) list3.get(i5);
                if (ANY_POLICY.equals(pKIXPolicyNode4.getValidPolicy())) {
                    Iterator children2 = pKIXPolicyNode4.getChildren();
                    while (children2.hasNext()) {
                        PKIXPolicyNode pKIXPolicyNode5 = (PKIXPolicyNode) children2.next();
                        if (!ANY_POLICY.equals(pKIXPolicyNode5.getValidPolicy())) {
                            hashSet2.add(pKIXPolicyNode5);
                        }
                    }
                }
            }
        }
        for (PKIXPolicyNode pKIXPolicyNode6 : hashSet2) {
            if (!set.contains(pKIXPolicyNode6.getValidPolicy())) {
                pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode6);
            }
        }
        if (pKIXPolicyNode != null) {
            for (int i6 = size - 1; i6 >= 0; i6--) {
                List list4 = listArr[i6];
                for (int i7 = 0; i7 < list4.size(); i7++) {
                    PKIXPolicyNode pKIXPolicyNode7 = (PKIXPolicyNode) list4.get(i7);
                    if (!pKIXPolicyNode7.hasChildren()) {
                        pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode7);
                    }
                }
            }
        }
        return pKIXPolicyNode;
    }
}
