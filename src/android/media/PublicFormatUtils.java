package android.media;

/* loaded from: classes2.dex */
class PublicFormatUtils {
    private static native int nativeGetHalDataspace(int i);

    private static native int nativeGetHalFormat(int i);

    private static native int nativeGetPublicFormat(int i, int i2);

    PublicFormatUtils() {
    }

    public static int getHalFormat(int i) {
        return nativeGetHalFormat(i);
    }

    public static int getHalDataspace(int i) {
        return nativeGetHalDataspace(i);
    }

    public static int getPublicFormat(int i, int i2) {
        return nativeGetPublicFormat(i, i2);
    }
}
