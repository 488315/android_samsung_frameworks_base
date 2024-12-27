package android.service.chooser;

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
