package android.graphics;

/* loaded from: classes.dex */
public final class Compatibility {
    private static int sTargetSdkVersion;

    private Compatibility() {
    }

    public static void setTargetSdkVersion(int i) {
        sTargetSdkVersion = i;
        Canvas.setCompatibilityVersion(i);
    }

    public static int getTargetSdkVersion() {
        return sTargetSdkVersion;
    }
}
