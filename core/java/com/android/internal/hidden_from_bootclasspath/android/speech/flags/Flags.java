package com.android.internal.hidden_from_bootclasspath.android.speech.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_MULTILANG_EXTRA_LAUNCH = "android.speech.flags.multilang_extra_launch";

    public static boolean multilangExtraLaunch() {
        return FEATURE_FLAGS.multilangExtraLaunch();
    }
}
