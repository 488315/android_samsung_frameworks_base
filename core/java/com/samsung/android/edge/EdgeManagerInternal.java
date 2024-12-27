package com.samsung.android.edge;

import android.content.ComponentName;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;

/* loaded from: classes6.dex */
public abstract class EdgeManagerInternal {
    public static final String NOTIFICATION_KEY_BUBBLE = "bubble";
    public static final String NOTIFICATION_KEY_CAN_BYPASSDND = "canBypassDnd";
    public static final String NOTIFICATION_KEY_IS_HEAD_UP = "isHeadUp";
    public static final String NOTIFICATION_KEY_IS_INTERRUPT = "isInterrupt";
    public static final String NOTIFICATION_KEY_IS_UPDATE = "isUpdate";
    public static final String NOTIFICATION_KEY_SMART_POPUP = "smartPopup";
    public static final String NOTIFICATION_KEY_SOUND = "sound";
    public static final String NOTIFICATION_KEY_VIBRATE = "vibrate";
    public static final String NOTIFICATION_KEY_VISIBILITY = "visibility";

    public abstract boolean hideForNotification(StatusBarNotification statusBarNotification);

    public abstract void hideForWakeLock(String str, int i);

    public abstract void hideForWakeLockByWindow(String str, String str2);

    public abstract void setSuppressed(int i);

    public abstract boolean showForNotification(
            StatusBarNotification statusBarNotification, Bundle bundle);

    public abstract void showForResumedActivity(ComponentName componentName);

    public abstract boolean showForToast(String str, String str2);

    public abstract boolean showForWakeLock(String str, int i);

    public abstract boolean showForWakeLockByWindow(String str, String str2);

    public abstract boolean showForWakeUp(String str, int i);

    public abstract boolean showForWakeUpByWindow(String str, String str2, int i);

    public abstract void statusBarDisabled(int i, int i2);
}
