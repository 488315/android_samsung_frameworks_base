package com.android.server.accessibility.magnification;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
