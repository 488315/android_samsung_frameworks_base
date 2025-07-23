package com.android.server.selinux.flags;

/* loaded from: classes6.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_SELINUX_LOGS_COLLECT = "com.android.server.selinux.flags.selinux_logs_collect";

    public static boolean selinuxLogsCollect() {
        return FEATURE_FLAGS.selinuxLogsCollect();
    }
}
