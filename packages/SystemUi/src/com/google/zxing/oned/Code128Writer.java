package com.google.zxing.oned;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Code128Writer extends OneDimensionalCodeWriter {
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        if (barcodeFormat == BarcodeFormat.CODE_128) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_128, but got " + barcodeFormat);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00b9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x003d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a3  */
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean[] encode(String str) {
        boolean z;
        int i;
        int length = str.length();
        if (length >= 1 && length <= 80) {
            for (int i2 = 0; i2 < length; i2++) {
                char charAt = str.charAt(i2);
                if (charAt < ' ' || charAt > '~') {
                    switch (charAt) {
                        case IKnoxCustomManager.Stub.TRANSACTION_setZeroPageState /* 241 */:
                        case IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState /* 242 */:
                        case IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentMode /* 243 */:
                        case IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode /* 244 */:
                            break;
                        default:
                            throw new IllegalArgumentException("Bad character in input: " + charAt);
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 1;
            while (true) {
                int[][] iArr = Code128Reader.CODE_PATTERNS;
                if (i3 < length) {
                    int i7 = (i5 == 99 ? 2 : 4) + i3;
                    int length2 = str.length();
                    for (int i8 = i3; i8 < i7 && i8 < length2; i8++) {
                        char charAt2 = str.charAt(i8);
                        if (charAt2 < '0' || charAt2 > '9') {
                            if (charAt2 != 241) {
                                z = false;
                                int i9 = 100;
                                i = z ? 99 : 100;
                                if (i == i5) {
                                    if (i5 != 100) {
                                        switch (str.charAt(i3)) {
                                            case IKnoxCustomManager.Stub.TRANSACTION_setZeroPageState /* 241 */:
                                                i3++;
                                                i9 = 102;
                                                break;
                                            case IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState /* 242 */:
                                                i3++;
                                                i9 = 97;
                                                break;
                                            case IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentMode /* 243 */:
                                                i3++;
                                                i9 = 96;
                                                break;
                                            case IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode /* 244 */:
                                                break;
                                            default:
                                                int i10 = i3 + 2;
                                                i9 = Integer.parseInt(str.substring(i3, i10));
                                                i3 = i10;
                                                break;
                                        }
                                    } else {
                                        i9 = str.charAt(i3) - ' ';
                                    }
                                    i3++;
                                } else {
                                    i9 = i5 == 0 ? i == 100 ? 104 : 105 : i;
                                    i5 = i;
                                }
                                arrayList.add(iArr[i9]);
                                i4 += i9 * i6;
                                if (i3 != 0) {
                                    i6++;
                                }
                            } else {
                                i7++;
                            }
                        }
                    }
                    if (i7 <= length2) {
                        z = true;
                        int i92 = 100;
                        if (z) {
                        }
                        if (i == i5) {
                        }
                        arrayList.add(iArr[i92]);
                        i4 += i92 * i6;
                        if (i3 != 0) {
                        }
                    }
                    z = false;
                    int i922 = 100;
                    if (z) {
                    }
                    if (i == i5) {
                    }
                    arrayList.add(iArr[i922]);
                    i4 += i922 * i6;
                    if (i3 != 0) {
                    }
                } else {
                    arrayList.add(iArr[i4 % 103]);
                    arrayList.add(iArr[106]);
                    Iterator it = arrayList.iterator();
                    int i11 = 0;
                    while (it.hasNext()) {
                        for (int i12 : (int[]) it.next()) {
                            i11 += i12;
                        }
                    }
                    boolean[] zArr = new boolean[i11];
                    Iterator it2 = arrayList.iterator();
                    int i13 = 0;
                    while (it2.hasNext()) {
                        i13 += OneDimensionalCodeWriter.appendPattern(zArr, i13, (int[]) it2.next(), true);
                    }
                    return zArr;
                }
            }
        } else {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Contents length should be between 1 and 80 characters, but got ", length));
        }
    }
}
