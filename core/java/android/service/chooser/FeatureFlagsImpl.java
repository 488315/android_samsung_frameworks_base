package android.service.chooser;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.service.chooser.FeatureFlags
    public boolean chooserAlbumText() {
        return true;
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean chooserPayloadToggling() {
        return true;
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean enableChooserResult() {
        return true;
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean enableSharesheetMetadataExtra() {
        return true;
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean fixResolverMemoryLeak() {
        return true;
    }
}
