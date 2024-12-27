package com.android.internal.hidden_from_bootclasspath.android.service.controls.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_HOME_PANEL_DREAM =
            "android.service.controls.flags.home_panel_dream";

    public static boolean homePanelDream() {
        return FEATURE_FLAGS.homePanelDream();
    }
}
