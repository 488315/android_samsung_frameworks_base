package com.android.server.net;

public abstract class Flags {
    public static boolean networkBlockedForTopSleepingAndAbove() {
        return true;
    }

    public static boolean useDifferentDelaysForBackgroundChain() {
        return false;
    }

    public static boolean useMeteredFirewallChains() {
        return true;
    }
}
