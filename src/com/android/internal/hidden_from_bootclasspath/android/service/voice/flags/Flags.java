package com.android.internal.hidden_from_bootclasspath.android.service.voice.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALLOW_FOREGROUND_ACTIVITIES_IN_ON_SHOW = "android.service.voice.flags.allow_foreground_activities_in_on_show";
    public static final String FLAG_ALLOW_HOTWORD_BUMP_EGRESS = "android.service.voice.flags.allow_hotword_bump_egress";
    public static final String FLAG_ALLOW_TRAINING_DATA_EGRESS_FROM_HDS = "android.service.voice.flags.allow_training_data_egress_from_hds";

    public static boolean allowForegroundActivitiesInOnShow() {
        return FEATURE_FLAGS.allowForegroundActivitiesInOnShow();
    }

    public static boolean allowHotwordBumpEgress() {
        return FEATURE_FLAGS.allowHotwordBumpEgress();
    }

    public static boolean allowTrainingDataEgressFromHds() {
        return FEATURE_FLAGS.allowTrainingDataEgressFromHds();
    }
}
