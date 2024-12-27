package android.graphics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class FrameInfo {
    public static final int ANIMATION_START = 6;
    public static final int DRAW_START = 8;
    public static final int FLAGS = 0;
    public static final long FLAG_SURFACE_CANVAS = 4;
    public static final long FLAG_WINDOW_VISIBILITY_CHANGED = 1;
    public static final int FRAME_DEADLINE = 9;
    private static final int FRAME_INFO_SIZE = 12;
    public static final int FRAME_INTERVAL = 11;
    public static final int FRAME_START_TIME = 10;
    public static final int FRAME_TIMELINE_VSYNC_ID = 1;
    public static final int HANDLE_INPUT_START = 5;
    public static final int INPUT_EVENT_ID = 4;
    public static final int INTENDED_VSYNC = 2;
    public static final long INVALID_VSYNC_ID = -1;
    public static final int PERFORM_TRAVERSALS_START = 7;
    public static final int VSYNC = 3;
    public long[] frameInfo = new long[12];

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrameInfoFlags {
    }

    public void setVsync(long intendedVsync, long usedVsync, long frameTimelineVsyncId, long frameDeadline, long frameStartTime, long frameInterval) {
        this.frameInfo[1] = frameTimelineVsyncId;
        this.frameInfo[2] = intendedVsync;
        this.frameInfo[3] = usedVsync;
        this.frameInfo[0] = 0;
        this.frameInfo[9] = frameDeadline;
        this.frameInfo[10] = frameStartTime;
        this.frameInfo[11] = frameInterval;
    }

    public void markInputHandlingStart() {
        this.frameInfo[5] = System.nanoTime();
    }

    public void markAnimationsStart() {
        this.frameInfo[6] = System.nanoTime();
    }

    public void markPerformTraversalsStart() {
        this.frameInfo[7] = System.nanoTime();
    }

    public void addFlags(long flags) {
        long[] jArr = this.frameInfo;
        jArr[0] = jArr[0] | flags;
    }
}
