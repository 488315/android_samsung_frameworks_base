package android.sec.clipboard.util;

public class Log {
    private static final String mTagPrefix = "SEM_CLIP_";

    public static int d(String tag, String msg) {
        return android.util.Log.d(mTagPrefix + tag, msg);
    }

    public static int v(String tag, String msg) {
        return android.util.Log.v(mTagPrefix + tag, msg);
    }

    public static int i(String tag, String msg) {
        return android.util.Log.i(mTagPrefix + tag, msg);
    }

    public static int w(String tag, String msg) {
        return android.util.Log.w(mTagPrefix + tag, msg);
    }

    public static int e(String tag, String msg) {
        return android.util.Log.e(mTagPrefix + tag, msg);
    }

    public static int secD(String tag, String msg) {
        return android.util.secutil.Log.d(mTagPrefix + tag, msg);
    }

    public static int secV(String tag, String msg) {
        return android.util.secutil.Log.v(mTagPrefix + tag, msg);
    }

    public static int secI(String tag, String msg) {
        return android.util.secutil.Log.i(mTagPrefix + tag, msg);
    }

    public static int secW(String tag, String msg) {
        return android.util.secutil.Log.w(mTagPrefix + tag, msg);
    }

    public static int secE(String tag, String msg) {
        return android.util.secutil.Log.e(mTagPrefix + tag, msg);
    }
}
