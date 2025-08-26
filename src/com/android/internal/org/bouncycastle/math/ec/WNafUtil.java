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
        final int iMin = Math.min(16, getWindowSize(order == null ? curve.getFieldSize() + 1 : order.bitLength()) + 3);
        curve.precompute(eCPoint, PRECOMP_NAME, new PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.WNafUtil.1
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                WNafPreCompInfo wNafPreCompInfo = preCompInfo instanceof WNafPreCompInfo ? (WNafPreCompInfo) preCompInfo : null;
                if (wNafPreCompInfo != null && wNafPreCompInfo.getConfWidth() == iMin) {
                    wNafPreCompInfo.setPromotionCountdown(0);
                    return wNafPreCompInfo;
                }
                WNafPreCompInfo wNafPreCompInfo2 = new WNafPreCompInfo();
                wNafPreCompInfo2.setPromotionCountdown(0);
                wNafPreCompInfo2.setConfWidth(iMin);
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
        BigInteger bigIntegerAdd = bigInteger.shiftLeft(1).add(bigInteger);
        int iBitLength = bigIntegerAdd.bitLength();
        int i = iBitLength >> 1;
        int[] iArr = new int[i];
        BigInteger bigIntegerXor = bigIntegerAdd.xor(bigInteger);
        int i2 = iBitLength - 1;
        int i3 = 0;
        int i4 = 1;
        int i5 = 0;
        while (i4 < i2) {
            if (bigIntegerXor.testBit(i4)) {
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
        int iBitLength = (bigInteger.bitLength() / i) + 1;
        int[] iArr = new int[iBitLength];
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
                int iIntValue = bigInteger.intValue() & i3;
                if (z) {
                    iIntValue++;
                }
                z = (iIntValue & i4) != 0;
                if (z) {
                    iIntValue -= i2;
                }
                if (i6 > 0) {
                    i5--;
                }
                iArr[i6] = i5 | (iIntValue << 16);
                i5 = i;
                i6++;
            }
        }
        return iBitLength > i6 ? trim(iArr, i6) : iArr;
    }

    public static byte[] generateJSF(BigInteger bigInteger, BigInteger bigInteger2) {
        int iMax = Math.max(bigInteger.bitLength(), bigInteger2.bitLength()) + 1;
        byte[] bArr = new byte[iMax];
        BigInteger bigIntegerShiftRight = bigInteger;
        BigInteger bigIntegerShiftRight2 = bigInteger2;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if ((i | i2) == 0 && bigIntegerShiftRight.bitLength() <= i3 && bigIntegerShiftRight2.bitLength() <= i3) {
                break;
            }
            int iIntValue = (bigIntegerShiftRight.intValue() >>> i3) + i;
            int i5 = iIntValue & 7;
            int iIntValue2 = (bigIntegerShiftRight2.intValue() >>> i3) + i2;
            int i6 = iIntValue2 & 7;
            int i7 = iIntValue & 1;
            if (i7 != 0) {
                i7 -= iIntValue & 2;
                if (i5 + i7 == 4 && (iIntValue2 & 3) == 2) {
                    i7 = -i7;
                }
            }
            int i8 = iIntValue2 & 1;
            if (i8 != 0) {
                i8 -= iIntValue2 & 2;
                if (i6 + i8 == 4 && (iIntValue & 3) == 2) {
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
                bigIntegerShiftRight = bigIntegerShiftRight.shiftRight(30);
                bigIntegerShiftRight2 = bigIntegerShiftRight2.shiftRight(30);
                i3 = 0;
            }
            bArr[i4] = (byte) ((i8 & 15) | (i7 << 4));
            i4++;
        }
        return iMax > i4 ? trim(bArr, i4) : bArr;
    }

    public static byte[] generateNaf(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return EMPTY_BYTES;
        }
        BigInteger bigIntegerAdd = bigInteger.shiftLeft(1).add(bigInteger);
        int iBitLength = bigIntegerAdd.bitLength();
        int i = iBitLength - 1;
        byte[] bArr = new byte[i];
        BigInteger bigIntegerXor = bigIntegerAdd.xor(bigInteger);
        int i2 = 1;
        while (i2 < i) {
            if (bigIntegerXor.testBit(i2)) {
                bArr[i2 - 1] = (byte) (bigInteger.testBit(i2) ? -1 : 1);
                i2++;
            }
            i2++;
        }
        bArr[iBitLength - 2] = 1;
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
        int iBitLength = bigInteger.bitLength() + 1;
        byte[] bArr = new byte[iBitLength];
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
                int iIntValue = bigInteger.intValue() & i3;
                if (z) {
                    iIntValue++;
                }
                z = (iIntValue & i4) != 0;
                if (z) {
                    iIntValue -= i2;
                }
                if (i6 > 0) {
                    i5--;
                }
                int i7 = i6 + i5;
                bArr[i7] = (byte) iIntValue;
                i6 = i7 + 1;
                i5 = i;
            }
        }
        return iBitLength > i6 ? trim(bArr, i6) : bArr;
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
            /* JADX WARN: Removed duplicated region for block: B:45:0x00ef A[PHI: r14
              0x00ef: PHI (r14v6 com.android.internal.org.bouncycastle.math.ec.ECPoint) = 
              (r14v4 com.android.internal.org.bouncycastle.math.ec.ECPoint)
              (r14v9 com.android.internal.org.bouncycastle.math.ec.ECPoint)
              (r14v9 com.android.internal.org.bouncycastle.math.ec.ECPoint)
              (r14v9 com.android.internal.org.bouncycastle.math.ec.ECPoint)
              (r14v9 com.android.internal.org.bouncycastle.math.ec.ECPoint)
             binds: [B:28:0x0091, B:30:0x009d, B:32:0x00a5, B:34:0x00af, B:40:0x00bd] A[DONT_GENERATE, DONT_INLINE]] */
            /* JADX WARN: Removed duplicated region for block: B:58:0x0117 A[LOOP:1: B:57:0x0115->B:58:0x0117, LOOP_END] */
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                ECPoint eCPointTwice;
                ECPoint[] eCPointArrResizeTable;
                ECPoint[] preCompNeg;
                int length;
                ECPoint[] eCPointArrResizeTable2;
                int i2;
                ECPoint eCPointCreatePoint;
                int coordinateSystem;
                ECFieldElement zCoord = null;
                WNafPreCompInfo wNafPreCompInfo = preCompInfo instanceof WNafPreCompInfo ? (WNafPreCompInfo) preCompInfo : null;
                int iMax = Math.max(2, Math.min(16, i));
                if (checkExisting(wNafPreCompInfo, iMax, 1 << (iMax - 2), z)) {
                    wNafPreCompInfo.decrementPromotionCountdown();
                    return wNafPreCompInfo;
                }
                WNafPreCompInfo wNafPreCompInfo2 = new WNafPreCompInfo();
                if (wNafPreCompInfo != null) {
                    wNafPreCompInfo2.setPromotionCountdown(wNafPreCompInfo.decrementPromotionCountdown());
                    wNafPreCompInfo2.setConfWidth(wNafPreCompInfo.getConfWidth());
                    eCPointArrResizeTable = wNafPreCompInfo.getPreComp();
                    preCompNeg = wNafPreCompInfo.getPreCompNeg();
                    eCPointTwice = wNafPreCompInfo.getTwice();
                } else {
                    eCPointTwice = null;
                    eCPointArrResizeTable = null;
                    preCompNeg = null;
                }
                int iMin = Math.min(16, Math.max(wNafPreCompInfo2.getConfWidth(), iMax));
                int i3 = 1 << (iMin - 2);
                int length2 = 0;
                if (eCPointArrResizeTable == null) {
                    eCPointArrResizeTable = WNafUtil.EMPTY_POINTS;
                    length = 0;
                } else {
                    length = eCPointArrResizeTable.length;
                }
                if (length < i3) {
                    eCPointArrResizeTable = WNafUtil.resizeTable(eCPointArrResizeTable, i3);
                    if (i3 == 1) {
                        eCPointArrResizeTable[0] = eCPoint.normalize();
                    } else {
                        if (length == 0) {
                            eCPointArrResizeTable[0] = eCPoint;
                            i2 = 1;
                        } else {
                            i2 = length;
                        }
                        if (i3 == 2) {
                            eCPointArrResizeTable[1] = eCPoint.threeTimes();
                        } else {
                            ECPoint eCPointAdd = eCPointArrResizeTable[i2 - 1];
                            if (eCPointTwice == null) {
                                eCPointTwice = eCPointArrResizeTable[0].twice();
                                if (eCPointTwice.isInfinity() || !ECAlgorithms.isFpCurve(curve) || curve.getFieldSize() < 64 || !((coordinateSystem = curve.getCoordinateSystem()) == 2 || coordinateSystem == 3 || coordinateSystem == 4)) {
                                    eCPointCreatePoint = eCPointTwice;
                                } else {
                                    zCoord = eCPointTwice.getZCoord(0);
                                    eCPointCreatePoint = curve.createPoint(eCPointTwice.getXCoord().toBigInteger(), eCPointTwice.getYCoord().toBigInteger());
                                    ECFieldElement eCFieldElementSquare = zCoord.square();
                                    eCPointAdd = eCPointAdd.scaleX(eCFieldElementSquare).scaleY(eCFieldElementSquare.multiply(zCoord));
                                    if (length == 0) {
                                        eCPointArrResizeTable[0] = eCPointAdd;
                                    }
                                }
                                while (i2 < i3) {
                                    eCPointAdd = eCPointAdd.add(eCPointCreatePoint);
                                    eCPointArrResizeTable[i2] = eCPointAdd;
                                    i2++;
                                }
                            }
                        }
                        curve.normalizeAll(eCPointArrResizeTable, length, i3 - length, zCoord);
                    }
                }
                if (z) {
                    if (preCompNeg == null) {
                        eCPointArrResizeTable2 = new ECPoint[i3];
                    } else {
                        length2 = preCompNeg.length;
                        if (length2 < i3) {
                            eCPointArrResizeTable2 = WNafUtil.resizeTable(preCompNeg, i3);
                        }
                        while (length2 < i3) {
                            preCompNeg[length2] = eCPointArrResizeTable[length2].negate();
                            length2++;
                        }
                    }
                    preCompNeg = eCPointArrResizeTable2;
                    while (length2 < i3) {
                    }
                }
                wNafPreCompInfo2.setPreComp(eCPointArrResizeTable);
                wNafPreCompInfo2.setPreCompNeg(preCompNeg);
                wNafPreCompInfo2.setTwice(eCPointTwice);
                wNafPreCompInfo2.setWidth(iMin);
                return wNafPreCompInfo2;
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
                int width = wNafPreCompInfo.getWidth();
                if (checkExisting(wNafPreCompInfo2, width, wNafPreCompInfo.getPreComp().length, z)) {
                    wNafPreCompInfo2.decrementPromotionCountdown();
                    return wNafPreCompInfo2;
                }
                WNafPreCompInfo wNafPreCompInfo3 = new WNafPreCompInfo();
                wNafPreCompInfo3.setPromotionCountdown(wNafPreCompInfo.getPromotionCountdown());
                ECPoint twice = wNafPreCompInfo.getTwice();
                if (twice != null) {
                    wNafPreCompInfo3.setTwice(eCPointMap.map(twice));
                }
                ECPoint[] preComp = wNafPreCompInfo.getPreComp();
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
