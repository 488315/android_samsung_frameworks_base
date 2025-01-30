package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class Log {
    static final boolean ENABLE_FULL_LOGGING = android.util.Log.isLoggable(TextClassifier.LOG_TAG, 2);

    private Log() {
    }

    /* renamed from: v */
    public static void m168v(String tag, String msg) {
        if (ENABLE_FULL_LOGGING) {
            android.util.Log.m100v(tag, msg);
        }
    }

    /* renamed from: d */
    public static void m166d(String tag, String msg) {
        android.util.Log.m94d(tag, msg);
    }

    /* renamed from: w */
    public static void m169w(String tag, String msg) {
        android.util.Log.m102w(tag, msg);
    }

    /* renamed from: e */
    public static void m167e(String tag, String msg, Throwable tr) {
        if (ENABLE_FULL_LOGGING) {
            android.util.Log.m97e(tag, msg, tr);
        } else {
            String trString = tr != null ? tr.getClass().getSimpleName() : "??";
            android.util.Log.m94d(tag, String.format("%s (%s)", msg, trString));
        }
    }
}
