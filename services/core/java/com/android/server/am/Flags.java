package com.android.server.am;

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
