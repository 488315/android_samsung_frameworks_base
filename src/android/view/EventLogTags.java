package android.view;

import android.util.EventLog;

/* loaded from: classes4.dex */
public class EventLogTags {
    public static final int IMF_IME_ANIM_CANCEL = 32008;
    public static final int IMF_IME_ANIM_FINISH = 32007;
    public static final int IMF_IME_ANIM_START = 32006;
    public static final int IMF_IME_REMOTE_ANIM_CANCEL = 32011;
    public static final int IMF_IME_REMOTE_ANIM_END = 32010;
    public static final int IMF_IME_REMOTE_ANIM_START = 32009;
    public static final int SURFACEVIEW_CALLBACK = 60006;
    public static final int SURFACEVIEW_LAYOUT = 60005;
    public static final int VIEWROOT_DRAW_EVENT = 60004;
    public static final int VIEW_ENQUEUE_INPUT_EVENT = 62002;

    private EventLogTags() {
    }

    public static void writeImfImeAnimStart(String str, int i, float f, String str2, String str3, String str4) {
        EventLog.writeEvent(IMF_IME_ANIM_START, str, Integer.valueOf(i), Float.valueOf(f), str2, str3, str4);
    }

    public static void writeImfImeAnimFinish(String str, int i, float f, int i2, String str2) {
        EventLog.writeEvent(IMF_IME_ANIM_FINISH, str, Integer.valueOf(i), Float.valueOf(f), Integer.valueOf(i2), str2);
    }

    public static void writeImfImeAnimCancel(String str, int i, String str2) {
        EventLog.writeEvent(IMF_IME_ANIM_CANCEL, str, Integer.valueOf(i), str2);
    }

    public static void writeImfImeRemoteAnimStart(String str, int i, int i2, float f, float f2, float f3, String str2, String str3, String str4, String str5) {
        EventLog.writeEvent(IMF_IME_REMOTE_ANIM_START, str, Integer.valueOf(i), Integer.valueOf(i2), Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), str2, str3, str4, str5);
    }

    public static void writeImfImeRemoteAnimEnd(String str, int i, int i2, float f, String str2, String str3, String str4, String str5) {
        EventLog.writeEvent(IMF_IME_REMOTE_ANIM_END, str, Integer.valueOf(i), Integer.valueOf(i2), Float.valueOf(f), str2, str3, str4, str5);
    }

    public static void writeImfImeRemoteAnimCancel(String str, int i, String str2) {
        EventLog.writeEvent(IMF_IME_REMOTE_ANIM_CANCEL, str, Integer.valueOf(i), str2);
    }

    public static void writeViewEnqueueInputEvent(String str, String str2) {
        EventLog.writeEvent(VIEW_ENQUEUE_INPUT_EVENT, str, str2);
    }

    public static void writeViewrootDrawEvent(String str, String str2) {
        EventLog.writeEvent(VIEWROOT_DRAW_EVENT, str, str2);
    }

    public static void writeSurfaceviewLayout(String str, int i, int i2, int i3, int i4, String str2, int i5, int i6, int i7) {
        EventLog.writeEvent(SURFACEVIEW_LAYOUT, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), str2, Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7));
    }

    public static void writeSurfaceviewCallback(String str, String str2) {
        EventLog.writeEvent(SURFACEVIEW_CALLBACK, str, str2);
    }
}
