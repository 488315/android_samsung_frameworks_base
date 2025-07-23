package android.service.autofill;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.service.autofill.FeatureFlags
    public boolean addAccessibilityTitleForAugmentedAutofillDropdown() {
        return true;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean addLastFocusedIdToClientState() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean addLastFocusedIdToFillEventHistory() {
        return true;
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
    public boolean autofillSessionDestroyed() {
        return true;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillWMetrics() {
        return true;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean fillDialogImprovements() {
        return true;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean fillDialogImprovementsImpl() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean fillFieldsFromCurrentSessionOnly() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean fixGetAutofillComponent() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean highlightAutofillSingleField() {
        return true;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean improveFillDialogAconfig() {
        return true;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean includeInvisibleViewGroupInAssistStructure() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean metricsFixes() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean multipleFillHistory() {
        return true;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean relayout() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean relayoutFix() {
        return true;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean remoteFillServiceUseWeakReference() {
        return true;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean test() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean testFlag() {
        return false;
    }
}
