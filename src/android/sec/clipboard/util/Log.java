package android.sec.clipboard.util;

/* loaded from: classes3.dex */
public class Log {
    private static final String mTagPrefix = "SEM_CLIP_";

    public static int d(String str, String str2) {
        return android.util.Log.d(mTagPrefix + str, str2);
    }

    public static int v(String str, String str2) {
        return android.util.Log.v(mTagPrefix + str, str2);
    }

    public static int i(String str, String str2) {
        return android.util.Log.i(mTagPrefix + str, str2);
    }

    public static int w(String str, String str2) {
        return android.util.Log.w(mTagPrefix + str, str2);
    }

    public static int e(String str, String str2) {
        return android.util.Log.e(mTagPrefix + str, str2);
    }

    public static int secD(String str, String str2) {
        return android.util.secutil.Log.d(mTagPrefix + str, str2);
    }

    public static int secV(String str, String str2) {
        return android.util.secutil.Log.v(mTagPrefix + str, str2);
    }

    public static int secI(String str, String str2) {
        return android.util.secutil.Log.i(mTagPrefix + str, str2);
    }

    public static int secW(String str, String str2) {
        return android.util.secutil.Log.w(mTagPrefix + str, str2);
    }

    public static int secE(String str, String str2) {
        return android.util.secutil.Log.e(mTagPrefix + str, str2);
    }
}
