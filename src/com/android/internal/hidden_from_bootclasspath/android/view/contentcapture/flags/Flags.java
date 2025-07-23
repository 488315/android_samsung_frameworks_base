package com.android.internal.hidden_from_bootclasspath.android.view.contentcapture.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CCAPI_BAKLAVA_ENABLED = "android.view.contentcapture.flags.ccapi_baklava_enabled";
    public static final String FLAG_FLUSH_AFTER_EACH_FRAME = "android.view.contentcapture.flags.flush_after_each_frame";
    public static final String FLAG_RUN_ON_BACKGROUND_THREAD_ENABLED = "android.view.contentcapture.flags.run_on_background_thread_enabled";

    public static boolean ccapiBaklavaEnabled() {
        return FEATURE_FLAGS.ccapiBaklavaEnabled();
    }

    public static boolean flushAfterEachFrame() {
        return FEATURE_FLAGS.flushAfterEachFrame();
    }

    public static boolean runOnBackgroundThreadEnabled() {
        return FEATURE_FLAGS.runOnBackgroundThreadEnabled();
    }
}
