package com.android.server.accessibility.magnification;

public final class MagnificationThumbnailFeatureFlag extends MagnificationFeatureFlagBase {
    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    public final boolean getDefaultValue() {
        return true;
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    public final String getFeatureName() {
        return "enable_magnifier_thumbnail";
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    public final String getNamespace() {
        return "accessibility";
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    public /* bridge */ /* synthetic */ boolean setFeatureFlagEnabled(boolean z) {
        return super.setFeatureFlagEnabled(z);
    }
}
