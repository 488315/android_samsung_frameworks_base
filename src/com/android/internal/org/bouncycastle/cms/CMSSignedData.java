package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1Set;
import com.android.internal.org.bouncycastle.asn1.BERSequence;
import com.android.internal.org.bouncycastle.asn1.DLSet;
import com.android.internal.org.bouncycastle.asn1.cms.ContentInfo;
import com.android.internal.org.bouncycastle.asn1.cms.SignedData;
import com.android.internal.org.bouncycastle.asn1.cms.SignerInfo;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.cert.X509AttributeCertificateHolder;
import com.android.internal.org.bouncycastle.cert.X509CRLHolder;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import com.android.internal.org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import com.android.internal.org.bouncycastle.util.Encodable;
import com.android.internal.org.bouncycastle.util.Store;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class CMSSignedData implements Encodable {
    ContentInfo contentInfo;
    private Map hashes;
    CMSTypedData signedContent;
    SignedData signedData;
    SignerInformationStore signerInfoStore;
    private static final CMSSignedHelper HELPER = CMSSignedHelper.INSTANCE;
    private static final DefaultDigestAlgorithmIdentifierFinder DIGEST_ALG_ID_FINDER = new DefaultDigestAlgorithmIdentifierFinder();

    private CMSSignedData(CMSSignedData cMSSignedData) {
        this.signedData = cMSSignedData.signedData;
        this.contentInfo = cMSSignedData.contentInfo;
        this.signedContent = cMSSignedData.signedContent;
        this.signerInfoStore = cMSSignedData.signerInfoStore;
    }

    public CMSSignedData(byte[] bArr) throws CMSException {
        this(CMSUtils.readContentInfo(bArr));
    }

    public CMSSignedData(CMSProcessable cMSProcessable, byte[] bArr) throws CMSException {
        this(cMSProcessable, CMSUtils.readContentInfo(bArr));
    }

    public CMSSignedData(Map map, byte[] bArr) throws CMSException {
        this(map, CMSUtils.readContentInfo(bArr));
    }

    public CMSSignedData(CMSProcessable cMSProcessable, InputStream inputStream) throws CMSException {
        this(cMSProcessable, CMSUtils.readContentInfo((InputStream) new ASN1InputStream(inputStream)));
    }

    public CMSSignedData(InputStream inputStream) throws CMSException {
        this(CMSUtils.readContentInfo(inputStream));
    }

    public CMSSignedData(final CMSProcessable cMSProcessable, ContentInfo contentInfo) throws CMSException {
        if (cMSProcessable instanceof CMSTypedData) {
            this.signedContent = (CMSTypedData) cMSProcessable;
        } else {
            this.signedContent = new CMSTypedData() { // from class: com.android.internal.org.bouncycastle.cms.CMSSignedData.1
                @Override // com.android.internal.org.bouncycastle.cms.CMSTypedData
                public ASN1ObjectIdentifier getContentType() {
                    return CMSSignedData.this.signedData.getEncapContentInfo().getContentType();
                }

                @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
                public void write(OutputStream outputStream) throws IOException, CMSException {
                    cMSProcessable.write(outputStream);
                }

                @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
                public Object getContent() {
                    return cMSProcessable.getContent();
                }
            };
        }
        this.contentInfo = contentInfo;
        this.signedData = getSignedData();
    }

    public CMSSignedData(Map map, ContentInfo contentInfo) throws CMSException {
        this.hashes = map;
        this.contentInfo = contentInfo;
        this.signedData = getSignedData();
    }

    public CMSSignedData(ContentInfo contentInfo) throws CMSException {
        this.contentInfo = contentInfo;
        SignedData signedData = getSignedData();
        this.signedData = signedData;
        ASN1Encodable content = signedData.getEncapContentInfo().getContent();
        if (content != null) {
            if (content instanceof ASN1OctetString) {
                this.signedContent = new CMSProcessableByteArray(this.signedData.getEncapContentInfo().getContentType(), ((ASN1OctetString) content).getOctets());
                return;
            } else {
                this.signedContent = new PKCS7ProcessableObject(this.signedData.getEncapContentInfo().getContentType(), content);
                return;
            }
        }
        this.signedContent = null;
    }

    private SignedData getSignedData() throws CMSException {
        try {
            return SignedData.getInstance(this.contentInfo.getContent());
        } catch (ClassCastException e) {
            throw new CMSException("Malformed content.", e);
        } catch (IllegalArgumentException e2) {
            throw new CMSException("Malformed content.", e2);
        }
    }

    public int getVersion() {
        return this.signedData.getVersion().intValueExact();
    }

    public SignerInformationStore getSignerInfos() {
        Map map;
        Object algorithm;
        if (this.signerInfoStore == null) {
            ASN1Set signerInfos = this.signedData.getSignerInfos();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i != signerInfos.size(); i++) {
                SignerInfo signerInfo = SignerInfo.getInstance(signerInfos.getObjectAt(i));
                ASN1ObjectIdentifier contentType = this.signedData.getEncapContentInfo().getContentType();
                Map map2 = this.hashes;
                if (map2 == null) {
                    arrayList.add(new SignerInformation(signerInfo, contentType, this.signedContent, null));
                } else {
                    if (map2.keySet().iterator().next() instanceof String) {
                        map = this.hashes;
                        algorithm = signerInfo.getDigestAlgorithm().getAlgorithm().getId();
                    } else {
                        map = this.hashes;
                        algorithm = signerInfo.getDigestAlgorithm().getAlgorithm();
                    }
                    arrayList.add(new SignerInformation(signerInfo, contentType, null, (byte[]) map.get(algorithm)));
                }
            }
            this.signerInfoStore = new SignerInformationStore(arrayList);
        }
        return this.signerInfoStore;
    }

    public boolean isDetachedSignature() {
        return this.signedData.getEncapContentInfo().getContent() == null && this.signedData.getSignerInfos().size() > 0;
    }

    public boolean isCertificateManagementMessage() {
        return this.signedData.getEncapContentInfo().getContent() == null && this.signedData.getSignerInfos().size() == 0;
    }

    public Store<X509CertificateHolder> getCertificates() {
        return HELPER.getCertificates(this.signedData.getCertificates());
    }

    public Store<X509CRLHolder> getCRLs() {
        return HELPER.getCRLs(this.signedData.getCRLs());
    }

    public Store<X509AttributeCertificateHolder> getAttributeCertificates() {
        return HELPER.getAttributeCertificates(this.signedData.getCertificates());
    }

    public Set<AlgorithmIdentifier> getDigestAlgorithmIDs() {
        HashSet hashSet = new HashSet();
        Enumeration objects = this.signedData.getDigestAlgorithms().getObjects();
        while (objects.hasMoreElements()) {
            hashSet.add(AlgorithmIdentifier.getInstance(objects.nextElement()));
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public String getSignedContentTypeOID() {
        return this.signedData.getEncapContentInfo().getContentType().getId();
    }

    public CMSTypedData getSignedContent() {
        return this.signedContent;
    }

    public ContentInfo toASN1Structure() {
        return this.contentInfo;
    }

    @Override // com.android.internal.org.bouncycastle.util.Encodable
    public byte[] getEncoded() throws IOException {
        return this.contentInfo.getEncoded();
    }

    public static CMSSignedData addDigestAlgorithm(CMSSignedData cMSSignedData, AlgorithmIdentifier algorithmIdentifier) {
        return addDigestAlgorithm(cMSSignedData, algorithmIdentifier, DIGEST_ALG_ID_FINDER);
    }

    public static CMSSignedData addDigestAlgorithm(CMSSignedData cMSSignedData, AlgorithmIdentifier algorithmIdentifier, DigestAlgorithmIdentifierFinder digestAlgorithmIdentifierFinder) {
        Set<AlgorithmIdentifier> digestAlgorithmIDs = cMSSignedData.getDigestAlgorithmIDs();
        AlgorithmIdentifier fixDigestAlgID = HELPER.fixDigestAlgID(algorithmIdentifier, digestAlgorithmIdentifierFinder);
        if (digestAlgorithmIDs.contains(fixDigestAlgID)) {
            return cMSSignedData;
        }
        CMSSignedData cMSSignedData2 = new CMSSignedData(cMSSignedData);
        HashSet hashSet = new HashSet();
        Iterator<AlgorithmIdentifier> it = digestAlgorithmIDs.iterator();
        while (it.hasNext()) {
            hashSet.add(HELPER.fixDigestAlgID(it.next(), digestAlgorithmIdentifierFinder));
        }
        hashSet.add(fixDigestAlgID);
        ASN1Set convertToDlSet = CMSUtils.convertToDlSet(hashSet);
        ASN1Sequence aSN1Sequence = (ASN1Sequence) cMSSignedData.signedData.toASN1Primitive();
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(aSN1Sequence.size());
        aSN1EncodableVector.add(aSN1Sequence.getObjectAt(0));
        aSN1EncodableVector.add(convertToDlSet);
        for (int i = 2; i != aSN1Sequence.size(); i++) {
            aSN1EncodableVector.add(aSN1Sequence.getObjectAt(i));
        }
        cMSSignedData2.signedData = SignedData.getInstance(new BERSequence(aSN1EncodableVector));
        cMSSignedData2.contentInfo = new ContentInfo(cMSSignedData2.contentInfo.getContentType(), cMSSignedData2.signedData);
        return cMSSignedData2;
    }

    public static CMSSignedData replaceSigners(CMSSignedData cMSSignedData, SignerInformationStore signerInformationStore) {
        return replaceSigners(cMSSignedData, signerInformationStore, DIGEST_ALG_ID_FINDER);
    }

    public static CMSSignedData replaceSigners(CMSSignedData cMSSignedData, SignerInformationStore signerInformationStore, DigestAlgorithmIdentifierFinder digestAlgorithmIdentifierFinder) {
        CMSSignedData cMSSignedData2 = new CMSSignedData(cMSSignedData);
        cMSSignedData2.signerInfoStore = signerInformationStore;
        HashSet hashSet = new HashSet();
        Collection<SignerInformation> signers = signerInformationStore.getSigners();
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(signers.size());
        for (SignerInformation signerInformation : signers) {
            CMSUtils.addDigestAlgs(hashSet, signerInformation, digestAlgorithmIdentifierFinder);
            aSN1EncodableVector.add(signerInformation.toASN1Structure());
        }
        ASN1Set convertToDlSet = CMSUtils.convertToDlSet(hashSet);
        DLSet dLSet = new DLSet(aSN1EncodableVector);
        ASN1Sequence aSN1Sequence = (ASN1Sequence) cMSSignedData.signedData.toASN1Primitive();
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector(aSN1Sequence.size());
        aSN1EncodableVector2.add(aSN1Sequence.getObjectAt(0));
        aSN1EncodableVector2.add(convertToDlSet);
        for (int i = 2; i != aSN1Sequence.size() - 1; i++) {
            aSN1EncodableVector2.add(aSN1Sequence.getObjectAt(i));
        }
        aSN1EncodableVector2.add(dLSet);
        cMSSignedData2.signedData = SignedData.getInstance(new BERSequence(aSN1EncodableVector2));
        cMSSignedData2.contentInfo = new ContentInfo(cMSSignedData2.contentInfo.getContentType(), cMSSignedData2.signedData);
        return cMSSignedData2;
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.internal.org.bouncycastle.cms.CMSSignedData replaceCertificatesAndCRLs(com.android.internal.org.bouncycastle.cms.CMSSignedData r8, com.android.internal.org.bouncycastle.util.Store r9, com.android.internal.org.bouncycastle.util.Store r10, com.android.internal.org.bouncycastle.util.Store r11) throws com.android.internal.org.bouncycastle.cms.CMSException {
        /*
            com.android.internal.org.bouncycastle.cms.CMSSignedData r0 = new com.android.internal.org.bouncycastle.cms.CMSSignedData
            r0.<init>(r8)
            r1 = 0
            if (r9 != 0) goto La
            if (r10 == 0) goto L2d
        La:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            if (r9 == 0) goto L18
            java.util.List r9 = com.android.internal.org.bouncycastle.cms.CMSUtils.getCertificatesFromStore(r9)
            r2.addAll(r9)
        L18:
            if (r10 == 0) goto L21
            java.util.List r9 = com.android.internal.org.bouncycastle.cms.CMSUtils.getAttributeCertificatesFromStore(r10)
            r2.addAll(r9)
        L21:
            com.android.internal.org.bouncycastle.asn1.ASN1Set r9 = com.android.internal.org.bouncycastle.cms.CMSUtils.createBerSetFromList(r2)
            int r10 = r9.size()
            if (r10 == 0) goto L2d
            r5 = r9
            goto L2e
        L2d:
            r5 = r1
        L2e:
            if (r11 == 0) goto L40
            java.util.List r9 = com.android.internal.org.bouncycastle.cms.CMSUtils.getCRLsFromStore(r11)
            com.android.internal.org.bouncycastle.asn1.ASN1Set r9 = com.android.internal.org.bouncycastle.cms.CMSUtils.createBerSetFromList(r9)
            int r10 = r9.size()
            if (r10 == 0) goto L40
            r6 = r9
            goto L41
        L40:
            r6 = r1
        L41:
            com.android.internal.org.bouncycastle.asn1.cms.SignedData r2 = new com.android.internal.org.bouncycastle.asn1.cms.SignedData
            com.android.internal.org.bouncycastle.asn1.cms.SignedData r9 = r8.signedData
            com.android.internal.org.bouncycastle.asn1.ASN1Set r3 = r9.getDigestAlgorithms()
            com.android.internal.org.bouncycastle.asn1.cms.SignedData r9 = r8.signedData
            com.android.internal.org.bouncycastle.asn1.cms.ContentInfo r4 = r9.getEncapContentInfo()
            com.android.internal.org.bouncycastle.asn1.cms.SignedData r8 = r8.signedData
            com.android.internal.org.bouncycastle.asn1.ASN1Set r7 = r8.getSignerInfos()
            r2.<init>(r3, r4, r5, r6, r7)
            r0.signedData = r2
            com.android.internal.org.bouncycastle.asn1.cms.ContentInfo r8 = new com.android.internal.org.bouncycastle.asn1.cms.ContentInfo
            com.android.internal.org.bouncycastle.asn1.cms.ContentInfo r9 = r0.contentInfo
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier r9 = r9.getContentType()
            com.android.internal.org.bouncycastle.asn1.cms.SignedData r10 = r0.signedData
            r8.<init>(r9, r10)
            r0.contentInfo = r8
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.cms.CMSSignedData.replaceCertificatesAndCRLs(com.android.internal.org.bouncycastle.cms.CMSSignedData, com.android.internal.org.bouncycastle.util.Store, com.android.internal.org.bouncycastle.util.Store, com.android.internal.org.bouncycastle.util.Store):com.android.internal.org.bouncycastle.cms.CMSSignedData");
    }
}
