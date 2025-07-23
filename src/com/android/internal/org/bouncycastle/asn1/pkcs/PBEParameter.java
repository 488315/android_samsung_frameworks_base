package com.android.internal.org.bouncycastle.asn1.pkcs;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class PBEParameter extends ASN1Object {
    ASN1Integer iterations;
    ASN1OctetString salt;

    public PBEParameter(byte[] bArr, int i) {
        if (bArr.length != 8) {
            throw new IllegalArgumentException("salt length must be 8");
        }
        this.salt = new DEROctetString(bArr);
        this.iterations = new ASN1Integer(i);
    }

    private PBEParameter(ASN1Sequence aSN1Sequence) {
        this.salt = (ASN1OctetString) aSN1Sequence.getObjectAt(0);
        this.iterations = (ASN1Integer) aSN1Sequence.getObjectAt(1);
    }

    public static PBEParameter getInstance(Object obj) {
        if (obj instanceof PBEParameter) {
            return (PBEParameter) obj;
        }
        if (obj != null) {
            return new PBEParameter(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public BigInteger getIterationCount() {
        return this.iterations.getValue();
    }

    public byte[] getSalt() {
        return this.salt.getOctets();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.salt);
        aSN1EncodableVector.add(this.iterations);
        return new DERSequence(aSN1EncodableVector);
    }
}
