package android.service.dreams;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.service.dreams.FeatureFlags
    public boolean dismissDreamOnKeyguardDismiss() {
        return true;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamHandlesBeingObscured() {
        return false;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamHandlesConfirmKeys() {
        return false;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamOverlayHost() {
        return false;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamWakeRedirect() {
        return false;
    }
}
