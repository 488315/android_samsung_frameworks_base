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

    public static void writeWmOnPausedCalled(int token, String componentName, String reason, long time) {
        EventLog.writeEvent(WM_ON_PAUSED_CALLED, Integer.valueOf(token), componentName, reason, Long.valueOf(time));
    }

    public static void writeWmOnResumeCalled(int token, String componentName, String reason, long time) {
        EventLog.writeEvent(WM_ON_RESUME_CALLED, Integer.valueOf(token), componentName, reason, Long.valueOf(time));
    }

    public static void writeWmOnStopCalled(int token, String componentName, String reason, long time) {
        EventLog.writeEvent(WM_ON_STOP_CALLED, Integer.valueOf(token), componentName, reason, Long.valueOf(time));
    }

    public static void writeWmOnCreateCalled(int token, String componentName, String reason, long time) {
        EventLog.writeEvent(WM_ON_CREATE_CALLED, Integer.valueOf(token), componentName, reason, Long.valueOf(time));
    }

    public static void writeWmOnRestartCalled(int token, String componentName, String reason, long time) {
        EventLog.writeEvent(WM_ON_RESTART_CALLED, Integer.valueOf(token), componentName, reason, Long.valueOf(time));
    }

    public static void writeWmOnStartCalled(int token, String componentName, String reason, long time) {
        EventLog.writeEvent(WM_ON_START_CALLED, Integer.valueOf(token), componentName, reason, Long.valueOf(time));
    }

    public static void writeWmOnDestroyCalled(int token, String componentName, String reason, long time) {
        EventLog.writeEvent(WM_ON_DESTROY_CALLED, Integer.valueOf(token), componentName, reason, Long.valueOf(time));
    }

    public static void writeWmOnActivityResultCalled(int token, String componentName, String reason) {
        EventLog.writeEvent(WM_ON_ACTIVITY_RESULT_CALLED, Integer.valueOf(token), componentName, reason);
    }

    public static void writeWmOnTopResumedGainedCalled(int token, String componentName, String reason) {
        EventLog.writeEvent(WM_ON_TOP_RESUMED_GAINED_CALLED, Integer.valueOf(token), componentName, reason);
    }

    public static void writeWmOnTopResumedLostCalled(int token, String componentName, String reason) {
        EventLog.writeEvent(WM_ON_TOP_RESUMED_LOST_CALLED, Integer.valueOf(token), componentName, reason);
    }

    public static void writeScreenDisabled(String tag, int state) {
        EventLog.writeEvent(SCREEN_DISABLED, tag, Integer.valueOf(state));
    }

    public static void writeDismissScreen(int pid, String tag) {
        EventLog.writeEvent(DISMISS_SCREEN, Integer.valueOf(pid), tag);
    }

    public static void writePendingIntentAfterUnlock(int pid, String tag) {
        EventLog.writeEvent(PENDING_INTENT_AFTER_UNLOCK, Integer.valueOf(pid), tag);
    }

    public static void writeWmOnIdleCalled(String componentName) {
        EventLog.writeEvent(WM_ON_IDLE_CALLED, componentName);
    }
}
