package android.webkit;

import android.util.EventLog;

/* loaded from: classes4.dex */
public class EventLogTags {
    public static final int BROWSER_DOUBLE_TAP_DURATION = 70102;
    public static final int BROWSER_SNAP_CENTER = 70150;
    public static final int BROWSER_ZOOM_LEVEL_CHANGE = 70101;
    public static final int EXP_DET_ATTEMPT_TO_CALL_OBJECT_GETCLASS = 70151;

    private EventLogTags() {
    }

    public static void writeBrowserZoomLevelChange(int i, int i2, long j) {
        EventLog.writeEvent(BROWSER_ZOOM_LEVEL_CHANGE, Integer.valueOf(i), Integer.valueOf(i2), Long.valueOf(j));
    }

    public static void writeBrowserDoubleTapDuration(int i, long j) {
        EventLog.writeEvent(BROWSER_DOUBLE_TAP_DURATION, Integer.valueOf(i), Long.valueOf(j));
    }

    public static void writeBrowserSnapCenter() {
        EventLog.writeEvent(BROWSER_SNAP_CENTER, new Object[0]);
    }

    public static void writeExpDetAttemptToCallObjectGetclass(String str) {
        EventLog.writeEvent(EXP_DET_ATTEMPT_TO_CALL_OBJECT_GETCLASS, str);
    }
}
