package com.android.internal.org.bouncycastle.asn1.pkcs;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.x509.DigestInfo;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class MacData extends ASN1Object {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    DigestInfo digInfo;
    BigInteger iterationCount;
    byte[] salt;

    public static MacData getInstance(Object obj) {
        if (obj instanceof MacData) {
            return (MacData) obj;
        }
        if (obj != null) {
            return new MacData(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private MacData(ASN1Sequence aSN1Sequence) {
        this.digInfo = DigestInfo.getInstance(aSN1Sequence.getObjectAt(0));
        this.salt = Arrays.clone(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets());
        if (aSN1Sequence.size() == 3) {
            this.iterationCount = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(2)).getValue();
        } else {
            this.iterationCount = ONE;
        }
    }

    public MacData(DigestInfo digestInfo, byte[] bArr, int i) {
        this.digInfo = digestInfo;
        this.salt = Arrays.clone(bArr);
        this.iterationCount = BigInteger.valueOf(i);
    }

    public DigestInfo getMac() {
        return this.digInfo;
    }

    public byte[] getSalt() {
        return Arrays.clone(this.salt);
    }

    public BigInteger getIterationCount() {
        return this.iterationCount;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.digInfo);
        aSN1EncodableVector.add(new DEROctetString(this.salt));
        if (!this.iterationCount.equals(ONE)) {
            aSN1EncodableVector.add(new ASN1Integer(this.iterationCount));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
