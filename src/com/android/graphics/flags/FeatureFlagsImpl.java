package com.android.graphics.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.graphics.flags.FeatureFlags
    public boolean displayBt2020Colorspace() {
        return false;
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean exactComputeBounds() {
        return true;
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean gradientDrawableShapeArcForRoundedCap() {
        return false;
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean iconLoadDrawableReturnNullWhenUriDecodeFails() {
        return false;
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean okLabColorspace() {
        return true;
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean yuvImageCompressToUltraHdr() {
        return false;
    }
}
