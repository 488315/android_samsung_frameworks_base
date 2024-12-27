package android.service.autofill;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean addLastFocusedIdToClientState();

    boolean addSessionIdToClientState();

    boolean autofillCredmanDevIntegration();

    boolean autofillCredmanIntegration();

    boolean autofillCredmanIntegrationPhase2();

    boolean fillFieldsFromCurrentSessionOnly();

    boolean includeInvisibleViewGroupInAssistStructure();

    boolean relayout();

    boolean remoteFillServiceUseWeakReference();

    boolean test();
}
