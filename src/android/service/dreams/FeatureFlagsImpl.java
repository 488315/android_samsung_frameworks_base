package android.service.dreams;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.service.dreams.FeatureFlags
    public boolean allowDreamWhenPostured() {
        return false;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean cleanupDreamSettingsOnUninstall() {
        return true;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dismissDreamOnKeyguardDismiss() {
        return true;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamHandlesBeingObscured() {
        return true;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamHandlesConfirmKeys() {
        return true;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamOverlayHost() {
        return false;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamWakeRedirect() {
        return true;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamsV2() {
        return false;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean publishPreviewStateToOverlay() {
        return false;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean startAndStopDozingInBackground() {
        return true;
    }
}
