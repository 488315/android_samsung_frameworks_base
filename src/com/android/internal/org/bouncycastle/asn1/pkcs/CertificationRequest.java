package com.android.internal.org.bouncycastle.asn1.pkcs;

import com.android.internal.org.bouncycastle.asn1.ASN1BitString;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DERBitString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes5.dex */
public class CertificationRequest extends ASN1Object {
    protected CertificationRequestInfo reqInfo;
    protected AlgorithmIdentifier sigAlgId;
    protected ASN1BitString sigBits;

    public static CertificationRequest getInstance(Object obj) {
        if (obj instanceof CertificationRequest) {
            return (CertificationRequest) obj;
        }
        if (obj != null) {
            return new CertificationRequest(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    protected CertificationRequest() {
        this.reqInfo = null;
        this.sigAlgId = null;
        this.sigBits = null;
    }

    public CertificationRequest(CertificationRequestInfo certificationRequestInfo, AlgorithmIdentifier algorithmIdentifier, ASN1BitString aSN1BitString) {
        this.reqInfo = certificationRequestInfo;
        this.sigAlgId = algorithmIdentifier;
        this.sigBits = aSN1BitString;
    }

    public CertificationRequest(ASN1Sequence aSN1Sequence) {
        this.reqInfo = null;
        this.sigAlgId = null;
        this.sigBits = null;
        this.reqInfo = CertificationRequestInfo.getInstance(aSN1Sequence.getObjectAt(0));
        this.sigAlgId = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
        this.sigBits = (DERBitString) aSN1Sequence.getObjectAt(2);
    }

    public CertificationRequestInfo getCertificationRequestInfo() {
        return this.reqInfo;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.sigAlgId;
    }

    public ASN1BitString getSignature() {
        return this.sigBits;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.reqInfo);
        aSN1EncodableVector.add(this.sigAlgId);
        aSN1EncodableVector.add(this.sigBits);
        return new DERSequence(aSN1EncodableVector);
    }
}
