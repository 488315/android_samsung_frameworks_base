package com.android.graphics.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.graphics.flags.FeatureFlags
    public boolean exactComputeBounds() {
        return false;
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean iconLoadDrawableReturnNullWhenUriDecodeFails() {
        return false;
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean okLabColorspace() {
        return false;
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean yuvImageCompressToUltraHdr() {
        return false;
    }
}
