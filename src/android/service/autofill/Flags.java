package android.service.autofill;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_ACCESSIBILITY_TITLE_FOR_AUGMENTED_AUTOFILL_DROPDOWN = "android.service.autofill.add_accessibility_title_for_augmented_autofill_dropdown";
    public static final String FLAG_ADD_LAST_FOCUSED_ID_TO_CLIENT_STATE = "android.service.autofill.add_last_focused_id_to_client_state";
    public static final String FLAG_ADD_LAST_FOCUSED_ID_TO_FILL_EVENT_HISTORY = "android.service.autofill.add_last_focused_id_to_fill_event_history";
    public static final String FLAG_ADD_SESSION_ID_TO_CLIENT_STATE = "android.service.autofill.add_session_id_to_client_state";
    public static final String FLAG_AUTOFILL_CREDMAN_DEV_INTEGRATION = "android.service.autofill.autofill_credman_dev_integration";
    public static final String FLAG_AUTOFILL_CREDMAN_INTEGRATION = "android.service.autofill.autofill_credman_integration";
    public static final String FLAG_AUTOFILL_CREDMAN_INTEGRATION_PHASE2 = "android.service.autofill.autofill_credman_integration_phase2";
    public static final String FLAG_AUTOFILL_SESSION_DESTROYED = "android.service.autofill.autofill_session_destroyed";
    public static final String FLAG_AUTOFILL_W_METRICS = "android.service.autofill.autofill_w_metrics";
    public static final String FLAG_FILL_DIALOG_IMPROVEMENTS = "android.service.autofill.fill_dialog_improvements";
    public static final String FLAG_FILL_DIALOG_IMPROVEMENTS_IMPL = "android.service.autofill.fill_dialog_improvements_impl";
    public static final String FLAG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY = "android.service.autofill.fill_fields_from_current_session_only";
    public static final String FLAG_FIX_GET_AUTOFILL_COMPONENT = "android.service.autofill.fix_get_autofill_component";
    public static final String FLAG_HIGHLIGHT_AUTOFILL_SINGLE_FIELD = "android.service.autofill.highlight_autofill_single_field";
    public static final String FLAG_IMPROVE_FILL_DIALOG_ACONFIG = "android.service.autofill.improve_fill_dialog_aconfig";
    public static final String FLAG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE = "android.service.autofill.include_invisible_view_group_in_assist_structure";
    public static final String FLAG_METRICS_FIXES = "android.service.autofill.metrics_fixes";
    public static final String FLAG_MULTIPLE_FILL_HISTORY = "android.service.autofill.multiple_fill_history";
    public static final String FLAG_RELAYOUT = "android.service.autofill.relayout";
    public static final String FLAG_RELAYOUT_FIX = "android.service.autofill.relayout_fix";
    public static final String FLAG_REMOTE_FILL_SERVICE_USE_WEAK_REFERENCE = "android.service.autofill.remote_fill_service_use_weak_reference";
    public static final String FLAG_TEST = "android.service.autofill.test";
    public static final String FLAG_TEST_FLAG = "android.service.autofill.test_flag";

    public static boolean addAccessibilityTitleForAugmentedAutofillDropdown() {
        return FEATURE_FLAGS.addAccessibilityTitleForAugmentedAutofillDropdown();
    }

    public static boolean addLastFocusedIdToClientState() {
        return FEATURE_FLAGS.addLastFocusedIdToClientState();
    }

    public static boolean addLastFocusedIdToFillEventHistory() {
        return FEATURE_FLAGS.addLastFocusedIdToFillEventHistory();
    }

    public static boolean addSessionIdToClientState() {
        return FEATURE_FLAGS.addSessionIdToClientState();
    }

    public static boolean autofillCredmanDevIntegration() {
        return FEATURE_FLAGS.autofillCredmanDevIntegration();
    }

    public static boolean autofillCredmanIntegration() {
        return FEATURE_FLAGS.autofillCredmanIntegration();
    }

    public static boolean autofillCredmanIntegrationPhase2() {
        return FEATURE_FLAGS.autofillCredmanIntegrationPhase2();
    }

    public static boolean autofillSessionDestroyed() {
        return FEATURE_FLAGS.autofillSessionDestroyed();
    }

    public static boolean autofillWMetrics() {
        return FEATURE_FLAGS.autofillWMetrics();
    }

    public static boolean fillDialogImprovements() {
        return FEATURE_FLAGS.fillDialogImprovements();
    }

    public static boolean fillDialogImprovementsImpl() {
        return FEATURE_FLAGS.fillDialogImprovementsImpl();
    }

    public static boolean fillFieldsFromCurrentSessionOnly() {
        return FEATURE_FLAGS.fillFieldsFromCurrentSessionOnly();
    }

    public static boolean fixGetAutofillComponent() {
        return FEATURE_FLAGS.fixGetAutofillComponent();
    }

    public static boolean highlightAutofillSingleField() {
        return FEATURE_FLAGS.highlightAutofillSingleField();
    }

    public static boolean improveFillDialogAconfig() {
        return FEATURE_FLAGS.improveFillDialogAconfig();
    }

    public static boolean includeInvisibleViewGroupInAssistStructure() {
        return FEATURE_FLAGS.includeInvisibleViewGroupInAssistStructure();
    }

    public static boolean metricsFixes() {
        return FEATURE_FLAGS.metricsFixes();
    }

    public static boolean multipleFillHistory() {
        return FEATURE_FLAGS.multipleFillHistory();
    }

    public static boolean relayout() {
        return FEATURE_FLAGS.relayout();
    }

    public static boolean relayoutFix() {
        return FEATURE_FLAGS.relayoutFix();
    }

    public static boolean remoteFillServiceUseWeakReference() {
        return FEATURE_FLAGS.remoteFillServiceUseWeakReference();
    }

    public static boolean test() {
        return FEATURE_FLAGS.test();
    }

    public static boolean testFlag() {
        return FEATURE_FLAGS.testFlag();
    }
}
