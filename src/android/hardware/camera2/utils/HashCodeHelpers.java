package android.hardware.camera2.utils;

/* loaded from: classes2.dex */
public final class HashCodeHelpers {
    public static int hashCode(int... iArr) {
        if (iArr == null) {
            return 0;
        }
        int i = 1;
        for (int i2 : iArr) {
            i = ((i << 5) - i) ^ i2;
        }
        return i;
    }

    public static int hashCode(float... fArr) {
        if (fArr == null) {
            return 0;
        }
        int i = 1;
        for (float f : fArr) {
            i = ((i << 5) - i) ^ Float.floatToIntBits(f);
        }
        return i;
    }

    public static <T> int hashCodeGeneric(T... tArr) {
        if (tArr == null) {
            return 0;
        }
        int length = tArr.length;
        int i = 1;
        for (int i2 = 0; i2 < length; i2++) {
            T t = tArr[i2];
            i = ((i << 5) - i) ^ (t == null ? 0 : t.hashCode());
        }
        return i;
    }
}
