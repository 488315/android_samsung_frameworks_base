package android.telephony;

/* loaded from: classes4.dex */
public class PersistentLogger {
    private final PersistentLoggerBackend mPersistentLoggerBackend;

    public PersistentLogger(PersistentLoggerBackend persistentLoggerBackend) {
        this.mPersistentLoggerBackend = persistentLoggerBackend;
    }

    public void debug(String tag, String msg) {
        this.mPersistentLoggerBackend.debug(tag, msg);
    }

    public void info(String tag, String msg) {
        this.mPersistentLoggerBackend.info(tag, msg);
    }

    public void warn(String tag, String msg) {
        this.mPersistentLoggerBackend.warn(tag, msg);
    }

    public void warn(String tag, String msg, Throwable t) {
        this.mPersistentLoggerBackend.warn(tag, msg, t);
    }

    public void error(String tag, String msg) {
        this.mPersistentLoggerBackend.error(tag, msg);
    }

    public void error(String tag, String msg, Throwable t) {
        this.mPersistentLoggerBackend.error(tag, msg, t);
    }
}
