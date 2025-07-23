package android.util;

/* loaded from: classes4.dex */
public final class CloseGuard {
    private final dalvik.system.CloseGuard mImpl = getImpl();

    private dalvik.system.CloseGuard getImpl$ravenwood() {
        return null;
    }

    public static CloseGuard get() {
        return new CloseGuard();
    }

    private dalvik.system.CloseGuard getImpl() {
        return dalvik.system.CloseGuard.get();
    }

    public void open(String str) {
        dalvik.system.CloseGuard closeGuard = this.mImpl;
        if (closeGuard != null) {
            closeGuard.open(str);
        }
    }

    public void close() {
        dalvik.system.CloseGuard closeGuard = this.mImpl;
        if (closeGuard != null) {
            closeGuard.close();
        }
    }

    public void warnIfOpen() {
        dalvik.system.CloseGuard closeGuard = this.mImpl;
        if (closeGuard != null) {
            closeGuard.warnIfOpen();
        }
    }
}
