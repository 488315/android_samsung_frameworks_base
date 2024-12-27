package com.android.internal.hidden_from_bootclasspath.android.app.smartspace.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ACCESS_SMARTSPACE = "android.app.smartspace.flags.access_smartspace";
    public static final String FLAG_REMOTE_VIEWS = "android.app.smartspace.flags.remote_views";

    public static boolean accessSmartspace() {
        return FEATURE_FLAGS.accessSmartspace();
    }

    public static boolean remoteViews() {
        return FEATURE_FLAGS.remoteViews();
    }
}
