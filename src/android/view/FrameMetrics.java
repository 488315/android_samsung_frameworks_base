package android.view;

import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class FrameMetrics {
    public static final int ANIMATION_DURATION = 2;
    public static final int COMMAND_ISSUE_DURATION = 6;
    public static final int DEADLINE = 13;
    public static final int DRAW_DURATION = 4;
    private static final int[] DURATIONS = {2, 5, 5, 6, 6, 7, 7, 8, 8, 13, 14, 15, 15, 16, 16, 21, 2, 17, 0, 0, 0, 0, 0, 0, 23, 20, 2, 9};
    public static final int FIRST_DRAW_FRAME = 9;
    public static final int FRAME_TIMELINE_VSYNC_ID = 14;
    public static final int GPU_DURATION = 12;
    public static final int INPUT_HANDLING_DURATION = 1;
    public static final int INTENDED_VSYNC_TIMESTAMP = 10;
    public static final int LAYOUT_MEASURE_DURATION = 3;
    public static final int SWAP_BUFFERS_DURATION = 7;
    public static final int SYNC_DURATION = 5;
    public static final int TOTAL_DURATION = 8;
    public static final int UNKNOWN_DELAY_DURATION = 0;
    public static final int VSYNC_TIMESTAMP = 11;
    public final long[] mTimingData;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Index {
        public static final int ANIMATION_START = 6;
        public static final int COMMAND_SUBMISSION_COMPLETED = 23;
        public static final int DEQUEUE_BUFFER_DURATION = 18;
        public static final int DISPLAY_PRESENT_TIME = 22;
        public static final int DRAW_START = 8;
        public static final int FLAGS = 0;
        public static final int FRAME_COMPLETED = 17;
        public static final int FRAME_DEADLINE = 9;
        public static final int FRAME_INTERVAL = 11;
        public static final int FRAME_START_TIME = 10;
        public static final int FRAME_STATS_COUNT = 24;
        public static final int FRAME_TIMELINE_VSYNC_ID = 1;
        public static final int GPU_COMPLETED = 20;
        public static final int HANDLE_INPUT_START = 5;
        public static final int INPUT_EVENT_ID = 4;
        public static final int INTENDED_VSYNC = 2;
        public static final int ISSUE_DRAW_COMMANDS_START = 15;
        public static final int PERFORM_TRAVERSALS_START = 7;
        public static final int QUEUE_BUFFER_DURATION = 19;
        public static final int SWAP_BUFFERS = 16;
        public static final int SWAP_BUFFERS_COMPLETED = 21;
        public static final int SYNC_QUEUED = 13;
        public static final int SYNC_START = 14;
        public static final int VSYNC = 3;
        public static final int WORKLOAD_TARGET = 12;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Metric {
    }

    public FrameMetrics(FrameMetrics frameMetrics) {
        long[] jArr = new long[24];
        this.mTimingData = jArr;
        System.arraycopy(frameMetrics.mTimingData, 0, jArr, 0, jArr.length);
    }

    public FrameMetrics() {
        this.mTimingData = new long[24];
    }

    public long getMetric(int i) {
        long[] jArr;
        if (i >= 0) {
            if (i > (Flags.jankApi() ? 14 : 13) || (jArr = this.mTimingData) == null) {
                return -1L;
            }
            if (i == 9) {
                return (jArr[0] & 1) != 0 ? 1L : 0L;
            }
            if (i == 10) {
                return jArr[2];
            }
            if (i == 11) {
                return jArr[3];
            }
            if (i == 14) {
                return jArr[1];
            }
            int i2 = i * 2;
            int[] iArr = DURATIONS;
            return jArr[iArr[i2 + 1]] - jArr[iArr[i2]];
        }
        return -1L;
    }
}
