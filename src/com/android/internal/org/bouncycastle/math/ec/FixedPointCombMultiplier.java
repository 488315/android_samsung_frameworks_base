package com.android.internal.org.bouncycastle.math.ec;

import com.android.internal.org.bouncycastle.math.raw.Nat;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class FixedPointCombMultiplier extends AbstractECMultiplier {
    @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECMultiplier
    protected ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        ECCurve curve = eCPoint.getCurve();
        if (bigInteger.bitLength() > FixedPointUtil.getCombSize(curve)) {
            throw new IllegalStateException("fixed-point comb doesn't support scalars larger than the curve order");
        }
        FixedPointPreCompInfo precompute = FixedPointUtil.precompute(eCPoint);
        ECLookupTable lookupTable = precompute.getLookupTable();
        int width = precompute.getWidth();
        int i = ((r0 + width) - 1) / width;
        ECPoint infinity = curve.getInfinity();
        int i2 = width * i;
        int[] fromBigInteger = Nat.fromBigInteger(i2, bigInteger);
        int i3 = i2 - 1;
        for (int i4 = 0; i4 < i; i4++) {
            int i5 = 0;
            for (int i6 = i3 - i4; i6 >= 0; i6 -= i) {
                int i7 = fromBigInteger[i6 >>> 5] >>> (i6 & 31);
                i5 = ((i5 ^ (i7 >>> 1)) << 1) ^ i7;
            }
            infinity = infinity.twicePlus(lookupTable.lookup(i5));
        }
        return infinity.add(precompute.getOffset());
    }
}
