package com.android.server.notification;

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
