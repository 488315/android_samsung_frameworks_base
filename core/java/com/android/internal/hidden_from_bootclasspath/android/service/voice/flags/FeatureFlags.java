package com.android.internal.hidden_from_bootclasspath.android.service.voice.flags;

public interface FeatureFlags {
    boolean allowForegroundActivitiesInOnShow();

    boolean allowHotwordBumpEgress();

    boolean allowTrainingDataEgressFromHds();
}
