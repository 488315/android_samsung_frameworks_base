package android.service.autofill;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.service.autofill.FeatureFlags
    public boolean addLastFocusedIdToClientState() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean addSessionIdToClientState() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanDevIntegration() {
        return true;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanIntegration() {
        return true;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanIntegrationPhase2() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean fillFieldsFromCurrentSessionOnly() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean includeInvisibleViewGroupInAssistStructure() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean relayout() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean remoteFillServiceUseWeakReference() {
        return true;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean test() {
        return false;
    }
}
