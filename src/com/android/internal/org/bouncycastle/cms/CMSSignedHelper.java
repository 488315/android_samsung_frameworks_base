package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1Set;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERNull;
import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate;
import com.android.internal.org.bouncycastle.asn1.x509.Certificate;
import com.android.internal.org.bouncycastle.asn1.x509.CertificateList;
import com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.internal.org.bouncycastle.cert.X509AttributeCertificateHolder;
import com.android.internal.org.bouncycastle.cert.X509CRLHolder;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import com.android.internal.org.bouncycastle.util.CollectionStore;
import com.android.internal.org.bouncycastle.util.Store;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
class CMSSignedHelper {
    static final CMSSignedHelper INSTANCE = new CMSSignedHelper();
    private static final Map encryptionAlgs = new HashMap();

    CMSSignedHelper() {
    }

    static {
        addEntries(NISTObjectIdentifiers.dsa_with_sha224, "DSA");
        addEntries(NISTObjectIdentifiers.dsa_with_sha256, "DSA");
        addEntries(NISTObjectIdentifiers.dsa_with_sha384, "DSA");
        addEntries(NISTObjectIdentifiers.dsa_with_sha512, "DSA");
        addEntries(OIWObjectIdentifiers.dsaWithSHA1, "DSA");
        addEntries(OIWObjectIdentifiers.md5WithRSA, "RSA");
        addEntries(OIWObjectIdentifiers.sha1WithRSA, "RSA");
        addEntries(PKCSObjectIdentifiers.md5WithRSAEncryption, "RSA");
        addEntries(PKCSObjectIdentifiers.sha1WithRSAEncryption, "RSA");
        addEntries(PKCSObjectIdentifiers.sha224WithRSAEncryption, "RSA");
        addEntries(PKCSObjectIdentifiers.sha256WithRSAEncryption, "RSA");
        addEntries(PKCSObjectIdentifiers.sha384WithRSAEncryption, "RSA");
        addEntries(PKCSObjectIdentifiers.sha512WithRSAEncryption, "RSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA1, "ECDSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA224, "ECDSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA256, "ECDSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA384, "ECDSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA512, "ECDSA");
        addEntries(X9ObjectIdentifiers.id_dsa_with_sha1, "DSA");
        addEntries(X9ObjectIdentifiers.id_dsa, "DSA");
        addEntries(PKCSObjectIdentifiers.rsaEncryption, "RSA");
        addEntries(TeleTrusTObjectIdentifiers.teleTrusTRSAsignatureAlgorithm, "RSA");
        addEntries(X509ObjectIdentifiers.id_ea_rsa, "RSA");
    }

    private static void addEntries(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        encryptionAlgs.put(aSN1ObjectIdentifier.getId(), str);
    }

    String getEncryptionAlgName(String str) {
        String str2 = (String) encryptionAlgs.get(str);
        return str2 != null ? str2 : str;
    }

    AlgorithmIdentifier fixDigestAlgID(AlgorithmIdentifier algorithmIdentifier, DigestAlgorithmIdentifierFinder digestAlgorithmIdentifierFinder) {
        ASN1Encodable parameters = algorithmIdentifier.getParameters();
        return (parameters == null || DERNull.INSTANCE.equals(parameters)) ? digestAlgorithmIdentifierFinder.find(algorithmIdentifier.getAlgorithm()) : algorithmIdentifier;
    }

    void setSigningEncryptionAlgorithmMapping(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        addEntries(aSN1ObjectIdentifier, str);
    }

    Store getCertificates(ASN1Set aSN1Set) {
        if (aSN1Set != null) {
            ArrayList arrayList = new ArrayList(aSN1Set.size());
            Enumeration objects = aSN1Set.getObjects();
            while (objects.hasMoreElements()) {
                ASN1Primitive aSN1Primitive = ((ASN1Encodable) objects.nextElement()).toASN1Primitive();
                if (aSN1Primitive instanceof ASN1Sequence) {
                    arrayList.add(new X509CertificateHolder(Certificate.getInstance(aSN1Primitive)));
                }
            }
            return new CollectionStore(arrayList);
        }
        return new CollectionStore(new ArrayList());
    }

    Store getAttributeCertificates(ASN1Set aSN1Set) {
        if (aSN1Set != null) {
            ArrayList arrayList = new ArrayList(aSN1Set.size());
            Enumeration objects = aSN1Set.getObjects();
            while (objects.hasMoreElements()) {
                ASN1Primitive aSN1Primitive = ((ASN1Encodable) objects.nextElement()).toASN1Primitive();
                if (aSN1Primitive instanceof ASN1TaggedObject) {
                    ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Primitive;
                    if (aSN1TaggedObject.getTagNo() == 1 || aSN1TaggedObject.getTagNo() == 2) {
                        arrayList.add(new X509AttributeCertificateHolder(AttributeCertificate.getInstance(aSN1TaggedObject.getBaseUniversal(false, 16))));
                    }
                }
            }
            return new CollectionStore(arrayList);
        }
        return new CollectionStore(new ArrayList());
    }

    Store getCRLs(ASN1Set aSN1Set) {
        if (aSN1Set != null) {
            ArrayList arrayList = new ArrayList(aSN1Set.size());
            Enumeration objects = aSN1Set.getObjects();
            while (objects.hasMoreElements()) {
                ASN1Primitive aSN1Primitive = ((ASN1Encodable) objects.nextElement()).toASN1Primitive();
                if (aSN1Primitive instanceof ASN1Sequence) {
                    arrayList.add(new X509CRLHolder(CertificateList.getInstance(aSN1Primitive)));
                }
            }
            return new CollectionStore(arrayList);
        }
        return new CollectionStore(new ArrayList());
    }
}
