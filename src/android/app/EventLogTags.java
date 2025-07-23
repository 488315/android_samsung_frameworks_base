package android.app;

import android.util.EventLog;

/* loaded from: classes.dex */
public class EventLogTags {
    public static final int DISMISS_SCREEN = 36100;
    public static final int PENDING_INTENT_AFTER_UNLOCK = 36101;
    public static final int SCREEN_DISABLED = 36099;
    public static final int WM_ON_ACTIVITY_RESULT_CALLED = 30062;
    public static final int WM_ON_CREATE_CALLED = 30057;
    public static final int WM_ON_DESTROY_CALLED = 30060;
    public static final int WM_ON_IDLE_CALLED = 1000204;
    public static final int WM_ON_PAUSED_CALLED = 30021;
    public static final int WM_ON_RESTART_CALLED = 30058;
    public static final int WM_ON_RESUME_CALLED = 30022;
    public static final int WM_ON_START_CALLED = 30059;
    public static final int WM_ON_STOP_CALLED = 30049;
    public static final int WM_ON_TOP_RESUMED_GAINED_CALLED = 30064;
    public static final int WM_ON_TOP_RESUMED_LOST_CALLED = 30065;

    private EventLogTags() {
    }

    public static void writeWmOnPausedCalled(int i, String str, String str2, long j) {
        EventLog.writeEvent(WM_ON_PAUSED_CALLED, Integer.valueOf(i), str, str2, Long.valueOf(j));
    }

    public static void writeWmOnResumeCalled(int i, String str, String str2, long j) {
        EventLog.writeEvent(WM_ON_RESUME_CALLED, Integer.valueOf(i), str, str2, Long.valueOf(j));
    }

    public static void writeWmOnStopCalled(int i, String str, String str2, long j) {
        EventLog.writeEvent(WM_ON_STOP_CALLED, Integer.valueOf(i), str, str2, Long.valueOf(j));
    }

    public static void writeWmOnCreateCalled(int i, String str, String str2, long j) {
        EventLog.writeEvent(WM_ON_CREATE_CALLED, Integer.valueOf(i), str, str2, Long.valueOf(j));
    }

    public static void writeWmOnRestartCalled(int i, String str, String str2, long j) {
        EventLog.writeEvent(WM_ON_RESTART_CALLED, Integer.valueOf(i), str, str2, Long.valueOf(j));
    }

    public static void writeWmOnStartCalled(int i, String str, String str2, long j) {
        EventLog.writeEvent(WM_ON_START_CALLED, Integer.valueOf(i), str, str2, Long.valueOf(j));
    }

    public static void writeWmOnDestroyCalled(int i, String str, String str2, long j) {
        EventLog.writeEvent(WM_ON_DESTROY_CALLED, Integer.valueOf(i), str, str2, Long.valueOf(j));
    }

    public static void writeWmOnActivityResultCalled(int i, String str, String str2) {
        EventLog.writeEvent(WM_ON_ACTIVITY_RESULT_CALLED, Integer.valueOf(i), str, str2);
    }

    public static void writeWmOnTopResumedGainedCalled(int i, String str, String str2) {
        EventLog.writeEvent(WM_ON_TOP_RESUMED_GAINED_CALLED, Integer.valueOf(i), str, str2);
    }

    public static void writeWmOnTopResumedLostCalled(int i, String str, String str2) {
        EventLog.writeEvent(WM_ON_TOP_RESUMED_LOST_CALLED, Integer.valueOf(i), str, str2);
    }

    public static void writeScreenDisabled(String str, int i) {
        EventLog.writeEvent(SCREEN_DISABLED, str, Integer.valueOf(i));
    }

    public static void writeDismissScreen(int i, String str) {
        EventLog.writeEvent(DISMISS_SCREEN, Integer.valueOf(i), str);
    }

    public static void writePendingIntentAfterUnlock(int i, String str) {
        EventLog.writeEvent(PENDING_INTENT_AFTER_UNLOCK, Integer.valueOf(i), str);
    }

    public static void writeWmOnIdleCalled(String str) {
        EventLog.writeEvent(WM_ON_IDLE_CALLED, str);
    }
}
