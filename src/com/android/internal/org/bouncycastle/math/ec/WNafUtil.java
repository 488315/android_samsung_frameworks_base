package com.android.internal.org.bouncycastle.math.ec;

import com.android.internal.logging.nano.MetricsProto;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public abstract class WNafUtil {
    private static final int[] DEFAULT_WINDOW_SIZE_CUTOFFS = {13, 41, 121, 337, MetricsProto.MetricsEvent.ACTION_APPOP_DENIED_REQUEST_INSTALL_PACKAGES, 2305};
    private static final byte[] EMPTY_BYTES = new byte[0];
    private static final int[] EMPTY_INTS = new int[0];
    private static final ECPoint[] EMPTY_POINTS = new ECPoint[0];
    private static final int MAX_WIDTH = 16;
    public static final String PRECOMP_NAME = "bc_wnaf";

    public static void configureBasepoint(ECPoint eCPoint) {
        ECCurve curve = eCPoint.getCurve();
        if (curve == null) {
            return;
        }
        BigInteger order = curve.getOrder();
        final int min = Math.min(16, getWindowSize(order == null ? curve.getFieldSize() + 1 : order.bitLength()) + 3);
        curve.precompute(eCPoint, PRECOMP_NAME, new PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.WNafUtil.1
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                WNafPreCompInfo wNafPreCompInfo = preCompInfo instanceof WNafPreCompInfo ? (WNafPreCompInfo) preCompInfo : null;
                if (wNafPreCompInfo != null && wNafPreCompInfo.getConfWidth() == min) {
                    wNafPreCompInfo.setPromotionCountdown(0);
                    return wNafPreCompInfo;
                }
                WNafPreCompInfo wNafPreCompInfo2 = new WNafPreCompInfo();
                wNafPreCompInfo2.setPromotionCountdown(0);
                wNafPreCompInfo2.setConfWidth(min);
                if (wNafPreCompInfo != null) {
                    wNafPreCompInfo2.setPreComp(wNafPreCompInfo.getPreComp());
                    wNafPreCompInfo2.setPreCompNeg(wNafPreCompInfo.getPreCompNeg());
                    wNafPreCompInfo2.setTwice(wNafPreCompInfo.getTwice());
                    wNafPreCompInfo2.setWidth(wNafPreCompInfo.getWidth());
                }
                return wNafPreCompInfo2;
            }
        });
    }

    public static int[] generateCompactNaf(BigInteger bigInteger) {
        if ((bigInteger.bitLength() >>> 16) != 0) {
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        }
        if (bigInteger.signum() == 0) {
            return EMPTY_INTS;
        }
        BigInteger add = bigInteger.shiftLeft(1).add(bigInteger);
        int bitLength = add.bitLength();
        int i = bitLength >> 1;
        int[] iArr = new int[i];
        BigInteger xor = add.xor(bigInteger);
        int i2 = bitLength - 1;
        int i3 = 0;
        int i4 = 1;
        int i5 = 0;
        while (i4 < i2) {
            if (xor.testBit(i4)) {
                iArr[i3] = i5 | ((bigInteger.testBit(i4) ? -1 : 1) << 16);
                i4++;
                i5 = 1;
                i3++;
            } else {
                i5++;
            }
            i4++;
        }
        int i6 = i3 + 1;
        iArr[i3] = 65536 | i5;
        return i > i6 ? trim(iArr, i6) : iArr;
    }

    public static int[] generateCompactWindowNaf(int i, BigInteger bigInteger) {
        if (i == 2) {
            return generateCompactNaf(bigInteger);
        }
        if (i < 2 || i > 16) {
            throw new IllegalArgumentException("'width' must be in the range [2, 16]");
        }
        if ((bigInteger.bitLength() >>> 16) != 0) {
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        }
        if (bigInteger.signum() == 0) {
            return EMPTY_INTS;
        }
        int bitLength = (bigInteger.bitLength() / i) + 1;
        int[] iArr = new int[bitLength];
        int i2 = 1 << i;
        int i3 = i2 - 1;
        int i4 = i2 >>> 1;
        int i5 = 0;
        int i6 = 0;
        boolean z = false;
        while (i5 <= bigInteger.bitLength()) {
            if (bigInteger.testBit(i5) == z) {
                i5++;
            } else {
                bigInteger = bigInteger.shiftRight(i5);
                int intValue = bigInteger.intValue() & i3;
                if (z) {
                    intValue++;
                }
                z = (intValue & i4) != 0;
                if (z) {
                    intValue -= i2;
                }
                if (i6 > 0) {
                    i5--;
                }
                iArr[i6] = i5 | (intValue << 16);
                i5 = i;
                i6++;
            }
        }
        return bitLength > i6 ? trim(iArr, i6) : iArr;
    }

    public static byte[] generateJSF(BigInteger bigInteger, BigInteger bigInteger2) {
        int max = Math.max(bigInteger.bitLength(), bigInteger2.bitLength()) + 1;
        byte[] bArr = new byte[max];
        BigInteger bigInteger3 = bigInteger;
        BigInteger bigInteger4 = bigInteger2;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if ((i | i2) == 0 && bigInteger3.bitLength() <= i3 && bigInteger4.bitLength() <= i3) {
                break;
            }
            int intValue = (bigInteger3.intValue() >>> i3) + i;
            int i5 = intValue & 7;
            int intValue2 = (bigInteger4.intValue() >>> i3) + i2;
            int i6 = intValue2 & 7;
            int i7 = intValue & 1;
            if (i7 != 0) {
                i7 -= intValue & 2;
                if (i5 + i7 == 4 && (intValue2 & 3) == 2) {
                    i7 = -i7;
                }
            }
            int i8 = intValue2 & 1;
            if (i8 != 0) {
                i8 -= intValue2 & 2;
                if (i6 + i8 == 4 && (intValue & 3) == 2) {
                    i8 = -i8;
                }
            }
            if ((i << 1) == i7 + 1) {
                i ^= 1;
            }
            if ((i2 << 1) == i8 + 1) {
                i2 ^= 1;
            }
            i3++;
            if (i3 == 30) {
                bigInteger3 = bigInteger3.shiftRight(30);
                bigInteger4 = bigInteger4.shiftRight(30);
                i3 = 0;
            }
            bArr[i4] = (byte) ((i8 & 15) | (i7 << 4));
            i4++;
        }
        return max > i4 ? trim(bArr, i4) : bArr;
    }

    public static byte[] generateNaf(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return EMPTY_BYTES;
        }
        BigInteger add = bigInteger.shiftLeft(1).add(bigInteger);
        int bitLength = add.bitLength();
        int i = bitLength - 1;
        byte[] bArr = new byte[i];
        BigInteger xor = add.xor(bigInteger);
        int i2 = 1;
        while (i2 < i) {
            if (xor.testBit(i2)) {
                bArr[i2 - 1] = (byte) (bigInteger.testBit(i2) ? -1 : 1);
                i2++;
            }
            i2++;
        }
        bArr[bitLength - 2] = 1;
        return bArr;
    }

    public static byte[] generateWindowNaf(int i, BigInteger bigInteger) {
        if (i == 2) {
            return generateNaf(bigInteger);
        }
        if (i < 2 || i > 8) {
            throw new IllegalArgumentException("'width' must be in the range [2, 8]");
        }
        if (bigInteger.signum() == 0) {
            return EMPTY_BYTES;
        }
        int bitLength = bigInteger.bitLength() + 1;
        byte[] bArr = new byte[bitLength];
        int i2 = 1 << i;
        int i3 = i2 - 1;
        int i4 = i2 >>> 1;
        int i5 = 0;
        int i6 = 0;
        boolean z = false;
        while (i5 <= bigInteger.bitLength()) {
            if (bigInteger.testBit(i5) == z) {
                i5++;
            } else {
                bigInteger = bigInteger.shiftRight(i5);
                int intValue = bigInteger.intValue() & i3;
                if (z) {
                    intValue++;
                }
                z = (intValue & i4) != 0;
                if (z) {
                    intValue -= i2;
                }
                if (i6 > 0) {
                    i5--;
                }
                int i7 = i6 + i5;
                bArr[i7] = (byte) intValue;
                i6 = i7 + 1;
                i5 = i;
            }
        }
        return bitLength > i6 ? trim(bArr, i6) : bArr;
    }

    public static int getNafWeight(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return 0;
        }
        return bigInteger.shiftLeft(1).add(bigInteger).xor(bigInteger).bitCount();
    }

    public static WNafPreCompInfo getWNafPreCompInfo(ECPoint eCPoint) {
        return getWNafPreCompInfo(eCPoint.getCurve().getPreCompInfo(eCPoint, PRECOMP_NAME));
    }

    public static WNafPreCompInfo getWNafPreCompInfo(PreCompInfo preCompInfo) {
        if (preCompInfo instanceof WNafPreCompInfo) {
            return (WNafPreCompInfo) preCompInfo;
        }
        return null;
    }

    public static int getWindowSize(int i) {
        return getWindowSize(i, DEFAULT_WINDOW_SIZE_CUTOFFS, 16);
    }

    public static int getWindowSize(int i, int i2) {
        return getWindowSize(i, DEFAULT_WINDOW_SIZE_CUTOFFS, i2);
    }

    public static int getWindowSize(int i, int[] iArr) {
        return getWindowSize(i, iArr, 16);
    }

    public static int getWindowSize(int i, int[] iArr, int i2) {
        int i3 = 0;
        while (i3 < iArr.length && i >= iArr[i3]) {
            i3++;
        }
        return Math.max(2, Math.min(i2, i3 + 2));
    }

    public static WNafPreCompInfo precompute(final ECPoint eCPoint, final int i, final boolean z) {
        final ECCurve curve = eCPoint.getCurve();
        return (WNafPreCompInfo) curve.precompute(eCPoint, PRECOMP_NAME, new PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.WNafUtil.2
            /* JADX WARN: Removed duplicated region for block: B:43:0x00f2 A[LOOP:0: B:42:0x00f0->B:43:0x00f2, LOOP_END] */
            /* JADX WARN: Removed duplicated region for block: B:54:0x0117 A[LOOP:1: B:53:0x0115->B:54:0x0117, LOOP_END] */
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.android.internal.org.bouncycastle.math.ec.PreCompInfo precompute(com.android.internal.org.bouncycastle.math.ec.PreCompInfo r14) {
                /*
                    Method dump skipped, instructions count: 303
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.math.ec.WNafUtil.AnonymousClass2.precompute(com.android.internal.org.bouncycastle.math.ec.PreCompInfo):com.android.internal.org.bouncycastle.math.ec.PreCompInfo");
            }

            private boolean checkExisting(WNafPreCompInfo wNafPreCompInfo, int i2, int i3, boolean z2) {
                if (wNafPreCompInfo == null || wNafPreCompInfo.getWidth() < Math.max(wNafPreCompInfo.getConfWidth(), i2) || !checkTable(wNafPreCompInfo.getPreComp(), i3)) {
                    return false;
                }
                return !z2 || checkTable(wNafPreCompInfo.getPreCompNeg(), i3);
            }

            private boolean checkTable(ECPoint[] eCPointArr, int i2) {
                return eCPointArr != null && eCPointArr.length >= i2;
            }
        });
    }

    public static WNafPreCompInfo precomputeWithPointMap(ECPoint eCPoint, final ECPointMap eCPointMap, final WNafPreCompInfo wNafPreCompInfo, final boolean z) {
        return (WNafPreCompInfo) eCPoint.getCurve().precompute(eCPoint, PRECOMP_NAME, new PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.WNafUtil.3
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                WNafPreCompInfo wNafPreCompInfo2 = preCompInfo instanceof WNafPreCompInfo ? (WNafPreCompInfo) preCompInfo : null;
                int width = WNafPreCompInfo.this.getWidth();
                if (checkExisting(wNafPreCompInfo2, width, WNafPreCompInfo.this.getPreComp().length, z)) {
                    wNafPreCompInfo2.decrementPromotionCountdown();
                    return wNafPreCompInfo2;
                }
                WNafPreCompInfo wNafPreCompInfo3 = new WNafPreCompInfo();
                wNafPreCompInfo3.setPromotionCountdown(WNafPreCompInfo.this.getPromotionCountdown());
                ECPoint twice = WNafPreCompInfo.this.getTwice();
                if (twice != null) {
                    wNafPreCompInfo3.setTwice(eCPointMap.map(twice));
                }
                ECPoint[] preComp = WNafPreCompInfo.this.getPreComp();
                int length = preComp.length;
                ECPoint[] eCPointArr = new ECPoint[length];
                for (int i = 0; i < preComp.length; i++) {
                    eCPointArr[i] = eCPointMap.map(preComp[i]);
                }
                wNafPreCompInfo3.setPreComp(eCPointArr);
                wNafPreCompInfo3.setWidth(width);
                if (z) {
                    ECPoint[] eCPointArr2 = new ECPoint[length];
                    for (int i2 = 0; i2 < length; i2++) {
                        eCPointArr2[i2] = eCPointArr[i2].negate();
                    }
                    wNafPreCompInfo3.setPreCompNeg(eCPointArr2);
                }
                return wNafPreCompInfo3;
            }

            private boolean checkExisting(WNafPreCompInfo wNafPreCompInfo2, int i, int i2, boolean z2) {
                if (wNafPreCompInfo2 == null || wNafPreCompInfo2.getWidth() < i || !checkTable(wNafPreCompInfo2.getPreComp(), i2)) {
                    return false;
                }
                return !z2 || checkTable(wNafPreCompInfo2.getPreCompNeg(), i2);
            }

            private boolean checkTable(ECPoint[] eCPointArr, int i) {
                return eCPointArr != null && eCPointArr.length >= i;
            }
        });
    }

    private static byte[] trim(byte[] bArr, int i) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

    private static int[] trim(int[] iArr, int i) {
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ECPoint[] resizeTable(ECPoint[] eCPointArr, int i) {
        ECPoint[] eCPointArr2 = new ECPoint[i];
        System.arraycopy(eCPointArr, 0, eCPointArr2, 0, eCPointArr.length);
        return eCPointArr2;
    }
}
