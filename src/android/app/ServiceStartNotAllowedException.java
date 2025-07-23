package android.app;

/* loaded from: classes.dex */
public abstract class ServiceStartNotAllowedException extends IllegalStateException {
    ServiceStartNotAllowedException(String str) {
        super(str);
    }

    public static ServiceStartNotAllowedException newInstance(boolean z, String str) {
        if (z) {
            return new ForegroundServiceStartNotAllowedException(str);
        }
        return new BackgroundServiceStartNotAllowedException(str);
    }

    @Override // java.lang.Throwable
    public synchronized Throwable getCause() {
        return null;
    }
}
