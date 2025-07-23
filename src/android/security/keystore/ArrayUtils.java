package android.security.keystore;

import java.util.function.Consumer;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static String[] nullToEmpty(String[] strArr) {
        return strArr != null ? strArr : EmptyArray.STRING;
    }

    public static String[] cloneIfNotEmpty(String[] strArr) {
        return (strArr == null || strArr.length <= 0) ? strArr : (String[]) strArr.clone();
    }

    public static int[] cloneIfNotEmpty(int[] iArr) {
        return (iArr == null || iArr.length <= 0) ? iArr : (int[]) iArr.clone();
    }

    public static byte[] cloneIfNotEmpty(byte[] bArr) {
        return (bArr == null || bArr.length <= 0) ? bArr : (byte[]) bArr.clone();
    }

    public static byte[] concat(byte[] bArr, byte[] bArr2) {
        return concat(bArr, 0, bArr != null ? bArr.length : 0, bArr2, 0, bArr2 != null ? bArr2.length : 0);
    }

    public static byte[] concat(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        if (i2 == 0) {
            return subarray(bArr2, i3, i4);
        }
        if (i4 == 0) {
            return subarray(bArr, i, i2);
        }
        byte[] bArr3 = new byte[i2 + i4];
        System.arraycopy(bArr, i, bArr3, 0, i2);
        System.arraycopy(bArr2, i3, bArr3, i2, i4);
        return bArr3;
    }

    public static int copy(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        if (bArr2 == null || bArr == null) {
            return 0;
        }
        if (i3 > bArr2.length - i2) {
            i3 = bArr2.length - i2;
        }
        if (i3 > bArr.length - i) {
            i3 = bArr.length - i;
        }
        if (i3 <= 0) {
            return 0;
        }
        System.arraycopy(bArr, i, bArr2, i2, i3);
        return i3;
    }

    public static byte[] subarray(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return EmptyArray.BYTE;
        }
        if (i == 0 && i2 == bArr.length) {
            return bArr;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    public static int[] concat(int[] iArr, int[] iArr2) {
        if (iArr == null || iArr.length == 0) {
            return iArr2;
        }
        if (iArr2 == null || iArr2.length == 0) {
            return iArr;
        }
        int[] iArr3 = new int[iArr.length + iArr2.length];
        System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
        System.arraycopy(iArr2, 0, iArr3, iArr.length, iArr2.length);
        return iArr3;
    }

    public static void forEach(int[] iArr, Consumer<Integer> consumer) {
        for (int i : iArr) {
            consumer.accept(Integer.valueOf(i));
        }
    }
}
