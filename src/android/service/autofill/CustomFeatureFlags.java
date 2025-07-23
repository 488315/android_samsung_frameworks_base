package android.service.autofill;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADD_ACCESSIBILITY_TITLE_FOR_AUGMENTED_AUTOFILL_DROPDOWN, Flags.FLAG_ADD_LAST_FOCUSED_ID_TO_CLIENT_STATE, Flags.FLAG_ADD_LAST_FOCUSED_ID_TO_FILL_EVENT_HISTORY, Flags.FLAG_ADD_SESSION_ID_TO_CLIENT_STATE, Flags.FLAG_AUTOFILL_CREDMAN_DEV_INTEGRATION, Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION, Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION_PHASE2, Flags.FLAG_AUTOFILL_SESSION_DESTROYED, Flags.FLAG_AUTOFILL_W_METRICS, Flags.FLAG_FILL_DIALOG_IMPROVEMENTS, Flags.FLAG_FILL_DIALOG_IMPROVEMENTS_IMPL, Flags.FLAG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY, Flags.FLAG_FIX_GET_AUTOFILL_COMPONENT, Flags.FLAG_HIGHLIGHT_AUTOFILL_SINGLE_FIELD, Flags.FLAG_IMPROVE_FILL_DIALOG_ACONFIG, Flags.FLAG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE, Flags.FLAG_METRICS_FIXES, Flags.FLAG_MULTIPLE_FILL_HISTORY, Flags.FLAG_RELAYOUT, Flags.FLAG_RELAYOUT_FIX, Flags.FLAG_REMOTE_FILL_SERVICE_USE_WEAK_REFERENCE, Flags.FLAG_TEST, Flags.FLAG_TEST_FLAG, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean addAccessibilityTitleForAugmentedAutofillDropdown() {
        return getValue(Flags.FLAG_ADD_ACCESSIBILITY_TITLE_FOR_AUGMENTED_AUTOFILL_DROPDOWN, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addAccessibilityTitleForAugmentedAutofillDropdown();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean addLastFocusedIdToClientState() {
        return getValue(Flags.FLAG_ADD_LAST_FOCUSED_ID_TO_CLIENT_STATE, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addLastFocusedIdToClientState();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean addLastFocusedIdToFillEventHistory() {
        return getValue(Flags.FLAG_ADD_LAST_FOCUSED_ID_TO_FILL_EVENT_HISTORY, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addLastFocusedIdToFillEventHistory();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean addSessionIdToClientState() {
        return getValue(Flags.FLAG_ADD_SESSION_ID_TO_CLIENT_STATE, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addSessionIdToClientState();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanDevIntegration() {
        return getValue(Flags.FLAG_AUTOFILL_CREDMAN_DEV_INTEGRATION, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).autofillCredmanDevIntegration();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanIntegration() {
        return getValue(Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).autofillCredmanIntegration();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanIntegrationPhase2() {
        return getValue(Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION_PHASE2, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).autofillCredmanIntegrationPhase2();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillSessionDestroyed() {
        return getValue(Flags.FLAG_AUTOFILL_SESSION_DESTROYED, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).autofillSessionDestroyed();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillWMetrics() {
        return getValue(Flags.FLAG_AUTOFILL_W_METRICS, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).autofillWMetrics();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean fillDialogImprovements() {
        return getValue(Flags.FLAG_FILL_DIALOG_IMPROVEMENTS, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fillDialogImprovements();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean fillDialogImprovementsImpl() {
        return getValue(Flags.FLAG_FILL_DIALOG_IMPROVEMENTS_IMPL, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fillDialogImprovementsImpl();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean fillFieldsFromCurrentSessionOnly() {
        return getValue(Flags.FLAG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fillFieldsFromCurrentSessionOnly();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean fixGetAutofillComponent() {
        return getValue(Flags.FLAG_FIX_GET_AUTOFILL_COMPONENT, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixGetAutofillComponent();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean highlightAutofillSingleField() {
        return getValue(Flags.FLAG_HIGHLIGHT_AUTOFILL_SINGLE_FIELD, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).highlightAutofillSingleField();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean improveFillDialogAconfig() {
        return getValue(Flags.FLAG_IMPROVE_FILL_DIALOG_ACONFIG, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).improveFillDialogAconfig();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean includeInvisibleViewGroupInAssistStructure() {
        return getValue(Flags.FLAG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).includeInvisibleViewGroupInAssistStructure();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean metricsFixes() {
        return getValue(Flags.FLAG_METRICS_FIXES, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).metricsFixes();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean multipleFillHistory() {
        return getValue(Flags.FLAG_MULTIPLE_FILL_HISTORY, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).multipleFillHistory();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean relayout() {
        return getValue(Flags.FLAG_RELAYOUT, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).relayout();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean relayoutFix() {
        return getValue(Flags.FLAG_RELAYOUT_FIX, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).relayoutFix();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean remoteFillServiceUseWeakReference() {
        return getValue(Flags.FLAG_REMOTE_FILL_SERVICE_USE_WEAK_REFERENCE, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).remoteFillServiceUseWeakReference();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean test() {
        return getValue(Flags.FLAG_TEST, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).test();
            }
        });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean testFlag() {
        return getValue(Flags.FLAG_TEST_FLAG, new Predicate() { // from class: android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).testFlag();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ADD_ACCESSIBILITY_TITLE_FOR_AUGMENTED_AUTOFILL_DROPDOWN, Flags.FLAG_ADD_LAST_FOCUSED_ID_TO_CLIENT_STATE, Flags.FLAG_ADD_LAST_FOCUSED_ID_TO_FILL_EVENT_HISTORY, Flags.FLAG_ADD_SESSION_ID_TO_CLIENT_STATE, Flags.FLAG_AUTOFILL_CREDMAN_DEV_INTEGRATION, Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION, Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION_PHASE2, Flags.FLAG_AUTOFILL_SESSION_DESTROYED, Flags.FLAG_AUTOFILL_W_METRICS, Flags.FLAG_FILL_DIALOG_IMPROVEMENTS, Flags.FLAG_FILL_DIALOG_IMPROVEMENTS_IMPL, Flags.FLAG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY, Flags.FLAG_FIX_GET_AUTOFILL_COMPONENT, Flags.FLAG_HIGHLIGHT_AUTOFILL_SINGLE_FIELD, Flags.FLAG_IMPROVE_FILL_DIALOG_ACONFIG, Flags.FLAG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE, Flags.FLAG_METRICS_FIXES, Flags.FLAG_MULTIPLE_FILL_HISTORY, Flags.FLAG_RELAYOUT, Flags.FLAG_RELAYOUT_FIX, Flags.FLAG_REMOTE_FILL_SERVICE_USE_WEAK_REFERENCE, Flags.FLAG_TEST, Flags.FLAG_TEST_FLAG);
    }
}
