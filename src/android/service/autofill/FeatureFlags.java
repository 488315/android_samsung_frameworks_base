package android.service.autofill;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean addAccessibilityTitleForAugmentedAutofillDropdown();

    boolean addLastFocusedIdToClientState();

    boolean addLastFocusedIdToFillEventHistory();

    boolean addSessionIdToClientState();

    boolean autofillCredmanDevIntegration();

    boolean autofillCredmanIntegration();

    boolean autofillCredmanIntegrationPhase2();

    boolean autofillSessionDestroyed();

    boolean autofillWMetrics();

    boolean fillDialogImprovements();

    boolean fillDialogImprovementsImpl();

    boolean fillFieldsFromCurrentSessionOnly();

    boolean fixGetAutofillComponent();

    boolean highlightAutofillSingleField();

    boolean improveFillDialogAconfig();

    boolean includeInvisibleViewGroupInAssistStructure();

    boolean metricsFixes();

    boolean multipleFillHistory();

    boolean relayout();

    boolean relayoutFix();

    boolean remoteFillServiceUseWeakReference();

    boolean test();

    boolean testFlag();
}
