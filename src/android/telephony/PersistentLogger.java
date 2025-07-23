package android.telephony;

/* loaded from: classes4.dex */
public class PersistentLogger {
    private final PersistentLoggerBackend mPersistentLoggerBackend;

    public PersistentLogger(PersistentLoggerBackend persistentLoggerBackend) {
        this.mPersistentLoggerBackend = persistentLoggerBackend;
    }

    public void debug(String str, String str2) {
        this.mPersistentLoggerBackend.debug(str, str2);
    }

    public void info(String str, String str2) {
        this.mPersistentLoggerBackend.info(str, str2);
    }

    public void warn(String str, String str2) {
        this.mPersistentLoggerBackend.warn(str, str2);
    }

    public void warn(String str, String str2, Throwable th) {
        this.mPersistentLoggerBackend.warn(str, str2, th);
    }

    public void error(String str, String str2) {
        this.mPersistentLoggerBackend.error(str, str2);
    }

    public void error(String str, String str2, Throwable th) {
        this.mPersistentLoggerBackend.error(str, str2, th);
    }
}
