package android.service.autofill;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ADD_LAST_FOCUSED_ID_TO_CLIENT_STATE,
                            Flags.FLAG_ADD_SESSION_ID_TO_CLIENT_STATE,
                            Flags.FLAG_AUTOFILL_CREDMAN_DEV_INTEGRATION,
                            Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION,
                            Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION_PHASE2,
                            Flags.FLAG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY,
                            Flags.FLAG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE,
                            Flags.FLAG_RELAYOUT,
                            Flags.FLAG_REMOTE_FILL_SERVICE_USE_WEAK_REFERENCE,
                            Flags.FLAG_TEST,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean addLastFocusedIdToClientState() {
        return getValue(
                Flags.FLAG_ADD_LAST_FOCUSED_ID_TO_CLIENT_STATE,
                new Predicate() { // from class:
                                  // android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).addLastFocusedIdToClientState();
                    }
                });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean addSessionIdToClientState() {
        return getValue(
                Flags.FLAG_ADD_SESSION_ID_TO_CLIENT_STATE,
                new Predicate() { // from class:
                                  // android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).addSessionIdToClientState();
                    }
                });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanDevIntegration() {
        return getValue(
                Flags.FLAG_AUTOFILL_CREDMAN_DEV_INTEGRATION,
                new Predicate() { // from class:
                                  // android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).autofillCredmanDevIntegration();
                    }
                });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanIntegration() {
        return getValue(
                Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION,
                new Predicate() { // from class:
                                  // android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).autofillCredmanIntegration();
                    }
                });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanIntegrationPhase2() {
        return getValue(
                Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION_PHASE2,
                new Predicate() { // from class:
                                  // android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).autofillCredmanIntegrationPhase2();
                    }
                });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean fillFieldsFromCurrentSessionOnly() {
        return getValue(
                Flags.FLAG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY,
                new Predicate() { // from class:
                                  // android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fillFieldsFromCurrentSessionOnly();
                    }
                });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean includeInvisibleViewGroupInAssistStructure() {
        return getValue(
                Flags.FLAG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE,
                new Predicate() { // from class:
                                  // android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).includeInvisibleViewGroupInAssistStructure();
                    }
                });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean relayout() {
        return getValue(
                Flags.FLAG_RELAYOUT,
                new Predicate() { // from class:
                                  // android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).relayout();
                    }
                });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean remoteFillServiceUseWeakReference() {
        return getValue(
                Flags.FLAG_REMOTE_FILL_SERVICE_USE_WEAK_REFERENCE,
                new Predicate() { // from class:
                                  // android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).remoteFillServiceUseWeakReference();
                    }
                });
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean test() {
        return getValue(
                Flags.FLAG_TEST,
                new Predicate() { // from class:
                                  // android.service.autofill.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).test();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_ADD_LAST_FOCUSED_ID_TO_CLIENT_STATE,
                Flags.FLAG_ADD_SESSION_ID_TO_CLIENT_STATE,
                Flags.FLAG_AUTOFILL_CREDMAN_DEV_INTEGRATION,
                Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION,
                Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION_PHASE2,
                Flags.FLAG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY,
                Flags.FLAG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE,
                Flags.FLAG_RELAYOUT,
                Flags.FLAG_REMOTE_FILL_SERVICE_USE_WEAK_REFERENCE,
                Flags.FLAG_TEST);
    }
}
