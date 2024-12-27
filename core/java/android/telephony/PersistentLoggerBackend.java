package android.telephony;

/* loaded from: classes4.dex */
public interface PersistentLoggerBackend {
    void debug(String str, String str2);

    void error(String str, String str2);

    void error(String str, String str2, Throwable th);

    void info(String str, String str2);

    void warn(String str, String str2);

    void warn(String str, String str2, Throwable th);
}
