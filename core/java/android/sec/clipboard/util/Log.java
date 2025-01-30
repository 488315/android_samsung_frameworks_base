package android.sec.clipboard.util;

/* loaded from: classes3.dex */
public class Log {
    private static final String mTagPrefix = "SEM_CLIP_";

    /* renamed from: d */
    public static int m59d(String tag, String msg) {
        return android.util.Log.m94d(mTagPrefix + tag, msg);
    }

    /* renamed from: v */
    public static int m62v(String tag, String msg) {
        return android.util.Log.m100v(mTagPrefix + tag, msg);
    }

    /* renamed from: i */
    public static int m61i(String tag, String msg) {
        return android.util.Log.m98i(mTagPrefix + tag, msg);
    }

    /* renamed from: w */
    public static int m63w(String tag, String msg) {
        return android.util.Log.m102w(mTagPrefix + tag, msg);
    }

    /* renamed from: e */
    public static int m60e(String tag, String msg) {
        return android.util.Log.m96e(mTagPrefix + tag, msg);
    }

    public static int secD(String tag, String msg) {
        return android.util.secutil.Log.m125d(mTagPrefix + tag, msg);
    }

    public static int secV(String tag, String msg) {
        return android.util.secutil.Log.m131v(mTagPrefix + tag, msg);
    }

    public static int secI(String tag, String msg) {
        return android.util.secutil.Log.m129i(mTagPrefix + tag, msg);
    }

    public static int secW(String tag, String msg) {
        return android.util.secutil.Log.m133w(mTagPrefix + tag, msg);
    }

    public static int secE(String tag, String msg) {
        return android.util.secutil.Log.m127e(mTagPrefix + tag, msg);
    }
}
