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
        int iFloatToIntBits = 1;
        for (float f : fArr) {
            iFloatToIntBits = ((iFloatToIntBits << 5) - iFloatToIntBits) ^ Float.floatToIntBits(f);
        }
        return iFloatToIntBits;
    }

    public static <T> int hashCodeGeneric(T... tArr) {
        if (tArr == null) {
            return 0;
        }
        int length = tArr.length;
        int iHashCode = 1;
        for (int i = 0; i < length; i++) {
            T t = tArr[i];
            iHashCode = ((iHashCode << 5) - iHashCode) ^ (t == null ? 0 : t.hashCode());
        }
        return iHashCode;
    }
}
