package com.android.graphics.hwui.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean animateHdrTransitions() {
        return true;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean clipShader() {
        return true;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean clipSurfaceviews() {
        return false;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean drawRegion() {
        return false;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean gainmapAnimations() {
        return true;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean gainmapConstructorWithMetadata() {
        return true;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean hdr10bitPlus() {
        return false;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean highContrastTextLuminance() {
        return true;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean highContrastTextSmallTextRect() {
        return false;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean initializeGlAlways() {
        return false;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean limitedHdr() {
        return true;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean matrix44() {
        return true;
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean requestedFormatsV() {
        return true;
    }
}
