package android.util;

/* loaded from: classes4.dex */
public final class CloseGuard {
    private final dalvik.system.CloseGuard mImpl = getImpl();

    public static CloseGuard get() {
        return new CloseGuard();
    }

    private dalvik.system.CloseGuard getImpl() {
        return dalvik.system.CloseGuard.get();
    }

    private dalvik.system.CloseGuard getImpl$ravenwood() {
        return null;
    }

    public void open(String closeMethodName) {
        if (this.mImpl != null) {
            this.mImpl.open(closeMethodName);
        }
    }

    public void close() {
        if (this.mImpl != null) {
            this.mImpl.close();
        }
    }

    public void warnIfOpen() {
        if (this.mImpl != null) {
            this.mImpl.warnIfOpen();
        }
    }
}
