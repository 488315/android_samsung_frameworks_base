package android.app;

import android.app.IActivityManager;
import android.content.Intent;
import android.os.IBinder;

@Deprecated
/* loaded from: classes.dex */
public abstract class ActivityManagerNative {
    public static IActivityManager asInterface(IBinder iBinder) {
        return IActivityManager.Stub.asInterface(iBinder);
    }

    public static IActivityManager getDefault() {
        return ActivityManager.getService();
    }

    public static boolean isSystemReady() {
        return ActivityManager.isSystemReady();
    }

    public static void broadcastStickyIntent(Intent intent, String str, int i) {
        broadcastStickyIntent(intent, str, -1, i);
    }

    public static void broadcastStickyIntent(Intent intent, String str, int i, int i2) {
        ActivityManager.broadcastStickyIntent(intent, i, i2);
    }

    public static void noteWakeupAlarm(PendingIntent pendingIntent, int i, String str, String str2) {
        ActivityManager.noteWakeupAlarm(pendingIntent, null, i, str, str2);
    }

    public static void noteAlarmStart(PendingIntent pendingIntent, int i, String str) {
        ActivityManager.noteAlarmStart(pendingIntent, null, i, str);
    }

    public static void noteAlarmFinish(PendingIntent pendingIntent, int i, String str) {
        ActivityManager.noteAlarmFinish(pendingIntent, null, i, str);
    }
}
