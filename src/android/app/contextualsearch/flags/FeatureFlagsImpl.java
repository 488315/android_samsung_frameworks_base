package android.app.contextualsearch.flags;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean contextualSearchPreventSelfCapture() {
        return true;
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean enableService() {
        return true;
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean enableTokenRefresh() {
        return false;
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean includeAudioPlayingStatus() {
        return false;
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean multiWindowScreenContext() {
        return false;
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean reportSecureSurfacesInAssistStructure() {
        return true;
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean selfInvocation() {
        return false;
    }
}
