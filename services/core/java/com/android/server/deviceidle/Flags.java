package com.android.server.deviceidle;

public abstract class Flags {
    public static boolean disableWakelocksInLightIdle() {
        return false;
    }

    public static boolean removeIdleLocation() {
        return false;
    }
}
