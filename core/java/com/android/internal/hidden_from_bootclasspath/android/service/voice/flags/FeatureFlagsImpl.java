package com.android.internal.hidden_from_bootclasspath.android.service.voice.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.service.voice.flags.FeatureFlags
    public boolean allowForegroundActivitiesInOnShow() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.voice.flags.FeatureFlags
    public boolean allowHotwordBumpEgress() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.voice.flags.FeatureFlags
    public boolean allowTrainingDataEgressFromHds() {
        return false;
    }
}
