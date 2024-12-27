package com.android.server.am;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class Flags {
    public static boolean avoidRepeatedBcastReEnqueues() {
        return true;
    }

    public static boolean avoidResolvingType() {
        return true;
    }

    public static boolean batchingOomAdj() {
        return false;
    }

    public static boolean deferOutgoingBroadcasts() {
        return false;
    }

    public static boolean fgsBootCompleted() {
        return true;
    }

    public static boolean fgsDisableSaw() {
        return true;
    }

    public static boolean followUpOomadjUpdates() {
        return false;
    }

    public static boolean logExcessiveBinderProxies() {
        return false;
    }

    public static boolean migrateFullOomadjUpdates() {
        return true;
    }

    public static boolean newFgsRestrictionLogic() {
        return true;
    }

    public static boolean oomadjusterCorrectnessRewrite() {
        return true;
    }

    public static boolean serviceBindingOomAdjPolicy() {
        return true;
    }

    public static boolean simplifyProcessTraversal() {
        return true;
    }

    public static boolean skipUnimportantConnections() {
        return false;
    }

    public static boolean traceReceiverRegistration() {
        return true;
    }

    public static boolean usePermissionManagerForBroadcastDeliveryCheck() {
        return true;
    }
}
