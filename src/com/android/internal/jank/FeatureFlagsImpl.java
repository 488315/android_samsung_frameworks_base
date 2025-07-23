package com.android.internal.jank;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.jank.FeatureFlags
    public boolean ignoreHwuiIsFirstFrame() {
        return true;
    }

    @Override // com.android.internal.jank.FeatureFlags
    public boolean useSfFrameDuration() {
        return true;
    }
}
