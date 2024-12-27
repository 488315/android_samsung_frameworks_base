package com.android.media.codec.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.media.codec.flags.FeatureFlags
    public boolean aidlHal() {
        return true;
    }

    @Override // com.android.media.codec.flags.FeatureFlags
    public boolean codecImportance() {
        return true;
    }

    @Override // com.android.media.codec.flags.FeatureFlags
    public boolean largeAudioFrame() {
        return true;
    }
}
