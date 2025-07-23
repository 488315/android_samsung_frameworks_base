package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class Log {
    static final boolean ENABLE_FULL_LOGGING = android.util.Log.isLoggable(TextClassifier.LOG_TAG, 2);

    private Log() {
    }

    public static void v(String str, String str2) {
        if (ENABLE_FULL_LOGGING) {
            android.util.Log.v(str, str2);
        }
    }

    public static void d(String str, String str2) {
        android.util.Log.d(str, str2);
    }

    public static void w(String str, String str2) {
        android.util.Log.w(str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        if (ENABLE_FULL_LOGGING) {
            android.util.Log.e(str, str2, th);
        } else {
            android.util.Log.d(str, String.format("%s (%s)", str2, th != null ? th.getClass().getSimpleName() : "??"));
        }
    }
}
