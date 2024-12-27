package com.android.server.notification;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class Flags {
    public static boolean allNotifsNeedTtl() {
        return false;
    }

    public static boolean autogroupSummaryIconUpdate() {
        return true;
    }

    public static boolean crossAppPoliteNotifications() {
        return false;
    }

    public static boolean expireBitmaps() {
        return true;
    }

    public static boolean notificationReduceMessagequeueUsage() {
        return false;
    }

    public static boolean persistIncompleteRestoreData() {
        return true;
    }

    public static boolean politeNotifications() {
        return false;
    }

    public static boolean rejectOldNotifications() {
        return false;
    }

    public static boolean traceCancelEvents() {
        return false;
    }

    public static boolean useSsmUserSwitchSignal() {
        return false;
    }
}
