package android.app;

/* loaded from: classes.dex */
public abstract class ServiceStartNotAllowedException extends IllegalStateException {
    ServiceStartNotAllowedException(String message) {
        super(message);
    }

    public static ServiceStartNotAllowedException newInstance(boolean foreground, String message) {
        if (foreground) {
            return new ForegroundServiceStartNotAllowedException(message);
        }
        return new BackgroundServiceStartNotAllowedException(message);
    }

    @Override // java.lang.Throwable
    public synchronized Throwable getCause() {
        return null;
    }
}
