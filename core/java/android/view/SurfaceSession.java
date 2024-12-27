package android.view;

public final class SurfaceSession {
    private long mNativeClient = nativeCreate();

    private static native long nativeCreate();

    private static native void nativeDestroy(long j);

    protected void finalize() throws Throwable {
        try {
            kill();
        } finally {
            super.finalize();
        }
    }

    public void kill() {
        if (this.mNativeClient != 0) {
            nativeDestroy(this.mNativeClient);
            this.mNativeClient = 0L;
        }
    }
}
