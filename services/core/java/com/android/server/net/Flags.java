package com.android.server.net;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
