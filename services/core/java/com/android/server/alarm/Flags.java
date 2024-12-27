package com.android.server.alarm;

public abstract class Flags {
    public static boolean startUserBeforeScheduledAlarms() {
        return false;
    }

    public static boolean useFrozenStateToDropListenerAlarms() {
        return true;
    }
}
