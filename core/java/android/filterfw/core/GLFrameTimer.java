package android.filterfw.core;

/* compiled from: GLFrame.java */
class GLFrameTimer {
    private static StopWatchMap mTimer = null;

    GLFrameTimer() {}

    public static StopWatchMap get() {
        if (mTimer == null) {
            mTimer = new StopWatchMap();
        }
        return mTimer;
    }
}
