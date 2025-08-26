package com.android.internal.org.bouncycastle.crypto.signers;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.io.IOException;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class StandardDSAEncoding implements DSAEncoding {
    public static final StandardDSAEncoding INSTANCE = new StandardDSAEncoding();

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAEncoding
    public byte[] encode(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) throws IOException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        encodeValue(bigInteger, aSN1EncodableVector, bigInteger2);
        encodeValue(bigInteger, aSN1EncodableVector, bigInteger3);
        return new DERSequence(aSN1EncodableVector).getEncoded(ASN1Encoding.DER);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAEncoding
    public BigInteger[] decode(BigInteger bigInteger, byte[] bArr) throws IOException {
        ASN1Sequence aSN1Sequence = (ASN1Sequence) ASN1Primitive.fromByteArray(bArr);
        if (aSN1Sequence.size() == 2) {
            BigInteger bigIntegerDecodeValue = decodeValue(bigInteger, aSN1Sequence, 0);
            BigInteger bigIntegerDecodeValue2 = decodeValue(bigInteger, aSN1Sequence, 1);
            if (Arrays.areEqual(encode(bigInteger, bigIntegerDecodeValue, bigIntegerDecodeValue2), bArr)) {
                return new BigInteger[]{bigIntegerDecodeValue, bigIntegerDecodeValue2};
            }
        }
        throw new IllegalArgumentException("Malformed signature");
    }

    protected BigInteger checkValue(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger2.signum() < 0 || (bigInteger != null && bigInteger2.compareTo(bigInteger) >= 0)) {
            throw new IllegalArgumentException("Value out of range");
        }
        return bigInteger2;
    }

    protected BigInteger decodeValue(BigInteger bigInteger, ASN1Sequence aSN1Sequence, int i) {
        return checkValue(bigInteger, ((ASN1Integer) aSN1Sequence.getObjectAt(i)).getValue());
    }

    protected void encodeValue(BigInteger bigInteger, ASN1EncodableVector aSN1EncodableVector, BigInteger bigInteger2) {
        aSN1EncodableVector.add(new ASN1Integer(checkValue(bigInteger, bigInteger2)));
    }
}
