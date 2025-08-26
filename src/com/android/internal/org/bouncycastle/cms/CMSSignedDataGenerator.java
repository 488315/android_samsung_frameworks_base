package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Set;
import com.android.internal.org.bouncycastle.asn1.BEROctetString;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERSet;
import com.android.internal.org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.cms.ContentInfo;
import com.android.internal.org.bouncycastle.asn1.cms.SignedData;
import com.android.internal.org.bouncycastle.asn1.cms.SignerInfo;
import com.android.internal.org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/* loaded from: classes5.dex */
public class CMSSignedDataGenerator extends CMSSignedGenerator {
    private boolean isDefiniteLength;
    private List signerInfs;

    public CMSSignedDataGenerator() {
        this.signerInfs = new ArrayList();
        this.isDefiniteLength = false;
    }

    public CMSSignedDataGenerator(DigestAlgorithmIdentifierFinder digestAlgorithmIdentifierFinder) {
        super(digestAlgorithmIdentifierFinder);
        this.signerInfs = new ArrayList();
        this.isDefiniteLength = false;
    }

    public void setDefiniteLengthEncoding(boolean z) {
        this.isDefiniteLength = z;
    }

    public CMSSignedData generate(CMSTypedData cMSTypedData) throws CMSException {
        return generate(cMSTypedData, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x008e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CMSSignedData generate(CMSTypedData cMSTypedData, boolean z) throws IOException, CMSException {
        ASN1Encodable bEROctetString;
        ASN1Set aSN1Set;
        ASN1Set aSN1SetCreateBerSetFromList;
        if (!this.signerInfs.isEmpty()) {
            throw new IllegalStateException("this method can only be used with SignerInfoGenerator");
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        this.digests.clear();
        for (SignerInformation signerInformation : this._signers) {
            CMSUtils.addDigestAlgs(linkedHashSet, signerInformation, this.digestAlgIdFinder);
            aSN1EncodableVector.add(signerInformation.toASN1Structure());
        }
        ASN1ObjectIdentifier contentType = cMSTypedData.getContentType();
        ASN1Set aSN1SetCreateBerSetFromList2 = null;
        if (cMSTypedData.getContent() == null) {
            bEROctetString = null;
        } else {
            ByteArrayOutputStream byteArrayOutputStream = z ? new ByteArrayOutputStream() : null;
            OutputStream safeOutputStream = CMSUtils.getSafeOutputStream(CMSUtils.attachSignersToOutputStream(this.signerGens, byteArrayOutputStream));
            try {
                cMSTypedData.write(safeOutputStream);
                safeOutputStream.close();
                if (z) {
                    if (this.isDefiniteLength) {
                        bEROctetString = new DEROctetString(byteArrayOutputStream.toByteArray());
                    } else {
                        bEROctetString = new BEROctetString(byteArrayOutputStream.toByteArray());
                    }
                }
            } catch (IOException e) {
                throw new CMSException("data processing exception: " + e.getMessage(), e);
            }
        }
        for (SignerInfoGenerator signerInfoGenerator : this.signerGens) {
            SignerInfo signerInfoGenerate = signerInfoGenerator.generate(contentType);
            linkedHashSet.add(signerInfoGenerate.getDigestAlgorithm());
            aSN1EncodableVector.add(signerInfoGenerate);
            byte[] calculatedDigest = signerInfoGenerator.getCalculatedDigest();
            if (calculatedDigest != null) {
                this.digests.put(signerInfoGenerate.getDigestAlgorithm().getAlgorithm().getId(), calculatedDigest);
            }
        }
        if (this.certs.size() != 0) {
            if (this.isDefiniteLength) {
                aSN1SetCreateBerSetFromList = CMSUtils.createDlSetFromList(this.certs);
            } else {
                aSN1SetCreateBerSetFromList = CMSUtils.createBerSetFromList(this.certs);
            }
            aSN1Set = aSN1SetCreateBerSetFromList;
        } else {
            aSN1Set = null;
        }
        if (this.crls.size() != 0) {
            if (this.isDefiniteLength) {
                aSN1SetCreateBerSetFromList2 = CMSUtils.createDlSetFromList(this.crls);
            } else {
                aSN1SetCreateBerSetFromList2 = CMSUtils.createBerSetFromList(this.crls);
            }
        }
        return new CMSSignedData(cMSTypedData, new ContentInfo(CMSObjectIdentifiers.signedData, new SignedData(CMSUtils.convertToDlSet(linkedHashSet), new ContentInfo(contentType, bEROctetString), aSN1Set, aSN1SetCreateBerSetFromList2, new DERSet(aSN1EncodableVector))));
    }

    public SignerInformationStore generateCounterSigners(SignerInformation signerInformation) throws CMSException {
        return generate(new CMSProcessableByteArray(null, signerInformation.getSignature()), false).getSignerInfos();
    }
}
