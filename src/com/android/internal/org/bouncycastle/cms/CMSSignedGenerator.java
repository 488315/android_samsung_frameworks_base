package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.internal.org.bouncycastle.cert.X509AttributeCertificateHolder;
import com.android.internal.org.bouncycastle.cert.X509CRLHolder;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import com.android.internal.org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Store;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class CMSSignedGenerator {
    public static final String DIGEST_MD5;
    public static final String DIGEST_SHA1;
    public static final String DIGEST_SHA224;
    public static final String DIGEST_SHA256;
    public static final String DIGEST_SHA384;
    public static final String DIGEST_SHA512;
    private static final Map EC_ALGORITHMS;
    public static final String ENCRYPTION_DSA;
    public static final String ENCRYPTION_ECDSA;
    private static final String ENCRYPTION_ECDSA_WITH_SHA1;
    private static final String ENCRYPTION_ECDSA_WITH_SHA224;
    private static final String ENCRYPTION_ECDSA_WITH_SHA256;
    private static final String ENCRYPTION_ECDSA_WITH_SHA384;
    private static final String ENCRYPTION_ECDSA_WITH_SHA512;
    public static final String ENCRYPTION_RSA;
    public static final String ENCRYPTION_RSA_PSS;
    private static final Set NO_PARAMS;
    protected List _signers;
    protected List certs;
    protected List crls;
    protected DigestAlgorithmIdentifierFinder digestAlgIdFinder;
    protected Map digests;
    protected List signerGens;

    static {
        String id = OIWObjectIdentifiers.idSHA1.getId();
        DIGEST_SHA1 = id;
        String id2 = NISTObjectIdentifiers.id_sha224.getId();
        DIGEST_SHA224 = id2;
        String id3 = NISTObjectIdentifiers.id_sha256.getId();
        DIGEST_SHA256 = id3;
        String id4 = NISTObjectIdentifiers.id_sha384.getId();
        DIGEST_SHA384 = id4;
        String id5 = NISTObjectIdentifiers.id_sha512.getId();
        DIGEST_SHA512 = id5;
        DIGEST_MD5 = PKCSObjectIdentifiers.md5.getId();
        ENCRYPTION_RSA = PKCSObjectIdentifiers.rsaEncryption.getId();
        String id6 = X9ObjectIdentifiers.id_dsa_with_sha1.getId();
        ENCRYPTION_DSA = id6;
        String id7 = X9ObjectIdentifiers.ecdsa_with_SHA1.getId();
        ENCRYPTION_ECDSA = id7;
        ENCRYPTION_RSA_PSS = PKCSObjectIdentifiers.id_RSASSA_PSS.getId();
        String id8 = X9ObjectIdentifiers.ecdsa_with_SHA1.getId();
        ENCRYPTION_ECDSA_WITH_SHA1 = id8;
        String id9 = X9ObjectIdentifiers.ecdsa_with_SHA224.getId();
        ENCRYPTION_ECDSA_WITH_SHA224 = id9;
        String id10 = X9ObjectIdentifiers.ecdsa_with_SHA256.getId();
        ENCRYPTION_ECDSA_WITH_SHA256 = id10;
        String id11 = X9ObjectIdentifiers.ecdsa_with_SHA384.getId();
        ENCRYPTION_ECDSA_WITH_SHA384 = id11;
        String id12 = X9ObjectIdentifiers.ecdsa_with_SHA512.getId();
        ENCRYPTION_ECDSA_WITH_SHA512 = id12;
        HashSet hashSet = new HashSet();
        NO_PARAMS = hashSet;
        HashMap map = new HashMap();
        EC_ALGORITHMS = map;
        hashSet.add(id6);
        hashSet.add(id7);
        hashSet.add(id8);
        hashSet.add(id9);
        hashSet.add(id10);
        hashSet.add(id11);
        hashSet.add(id12);
        map.put(id, id8);
        map.put(id2, id9);
        map.put(id3, id10);
        map.put(id4, id11);
        map.put(id5, id12);
    }

    protected CMSSignedGenerator() {
        this(new DefaultDigestAlgorithmIdentifierFinder());
    }

    protected CMSSignedGenerator(DigestAlgorithmIdentifierFinder digestAlgorithmIdentifierFinder) {
        this.certs = new ArrayList();
        this.crls = new ArrayList();
        this._signers = new ArrayList();
        this.signerGens = new ArrayList();
        this.digests = new HashMap();
        this.digestAlgIdFinder = digestAlgorithmIdentifierFinder;
    }

    protected Map getBaseParameters(ASN1ObjectIdentifier aSN1ObjectIdentifier, AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        HashMap map = new HashMap();
        map.put("contentType", aSN1ObjectIdentifier);
        map.put(CMSAttributeTableGenerator.DIGEST_ALGORITHM_IDENTIFIER, algorithmIdentifier);
        map.put(CMSAttributeTableGenerator.DIGEST, Arrays.clone(bArr));
        return map;
    }

    public void addCertificate(X509CertificateHolder x509CertificateHolder) throws CMSException {
        this.certs.add(x509CertificateHolder.toASN1Structure());
    }

    public void addCertificates(Store store) throws CMSException {
        this.certs.addAll(CMSUtils.getCertificatesFromStore(store));
    }

    public void addCRL(X509CRLHolder x509CRLHolder) {
        this.crls.add(x509CRLHolder.toASN1Structure());
    }

    public void addCRLs(Store store) throws CMSException {
        this.crls.addAll(CMSUtils.getCRLsFromStore(store));
    }

    public void addAttributeCertificate(X509AttributeCertificateHolder x509AttributeCertificateHolder) throws CMSException {
        this.certs.add(new DERTaggedObject(false, 2, (ASN1Encodable) x509AttributeCertificateHolder.toASN1Structure()));
    }

    public void addAttributeCertificates(Store store) throws CMSException {
        this.certs.addAll(CMSUtils.getAttributeCertificatesFromStore(store));
    }

    public void addSigners(SignerInformationStore signerInformationStore) {
        Iterator<SignerInformation> it = signerInformationStore.getSigners().iterator();
        while (it.hasNext()) {
            this._signers.add(it.next());
        }
    }

    public void addSignerInfoGenerator(SignerInfoGenerator signerInfoGenerator) {
        this.signerGens.add(signerInfoGenerator);
    }

    public Map getGeneratedDigests() {
        return new HashMap(this.digests);
    }
}
