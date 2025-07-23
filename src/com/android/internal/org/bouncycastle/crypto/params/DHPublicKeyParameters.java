package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.org.bouncycastle.math.raw.Nat;
import com.android.internal.org.bouncycastle.util.Integers;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class DHPublicKeyParameters extends DHKeyParameters {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private BigInteger y;

    public DHPublicKeyParameters(BigInteger bigInteger, DHParameters dHParameters) {
        super(false, dHParameters);
        this.y = validate(bigInteger, dHParameters);
    }

    private BigInteger validate(BigInteger bigInteger, DHParameters dHParameters) {
        if (bigInteger == null) {
            throw new NullPointerException("y value cannot be null");
        }
        BigInteger p = dHParameters.getP();
        BigInteger bigInteger2 = TWO;
        if (bigInteger.compareTo(bigInteger2) < 0 || bigInteger.compareTo(p.subtract(bigInteger2)) > 0) {
            throw new IllegalArgumentException("invalid DH public key");
        }
        BigInteger q = dHParameters.getQ();
        if (q != null && (!(p.testBit(0) && p.bitLength() - 1 == q.bitLength() && p.shiftRight(1).equals(q)) ? ONE.equals(bigInteger.modPow(q, p)) : 1 == legendre(bigInteger, p))) {
            throw new IllegalArgumentException("Y value does not appear to be in correct group");
        }
        return bigInteger;
    }

    public BigInteger getY() {
        return this.y;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters
    public int hashCode() {
        return super.hashCode() ^ this.y.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters
    public boolean equals(Object obj) {
        return (obj instanceof DHPublicKeyParameters) && ((DHPublicKeyParameters) obj).getY().equals(this.y) && super.equals(obj);
    }

    private static int legendre(BigInteger bigInteger, BigInteger bigInteger2) {
        int bitLength = bigInteger2.bitLength();
        int[] fromBigInteger = Nat.fromBigInteger(bitLength, bigInteger);
        int[] fromBigInteger2 = Nat.fromBigInteger(bitLength, bigInteger2);
        int length = fromBigInteger2.length;
        int i = 0;
        while (true) {
            int i2 = fromBigInteger[0];
            if (i2 == 0) {
                Nat.shiftDownWord(length, fromBigInteger, 0);
            } else {
                int numberOfTrailingZeros = Integers.numberOfTrailingZeros(i2);
                if (numberOfTrailingZeros > 0) {
                    Nat.shiftDownBits(length, fromBigInteger, numberOfTrailingZeros, 0);
                    int i3 = fromBigInteger2[0];
                    i ^= (numberOfTrailingZeros << 1) & (i3 ^ (i3 >>> 1));
                }
                int compare = Nat.compare(length, fromBigInteger, fromBigInteger2);
                if (compare == 0) {
                    break;
                }
                if (compare < 0) {
                    i ^= fromBigInteger[0] & fromBigInteger2[0];
                    int[] iArr = fromBigInteger2;
                    fromBigInteger2 = fromBigInteger;
                    fromBigInteger = iArr;
                }
                while (true) {
                    int i4 = length - 1;
                    if (fromBigInteger[i4] != 0) {
                        break;
                    }
                    length = i4;
                }
                Nat.sub(length, fromBigInteger, fromBigInteger2, fromBigInteger);
            }
        }
        if (Nat.isOne(length, fromBigInteger2)) {
            return 1 - (i & 2);
        }
        return 0;
    }
}
