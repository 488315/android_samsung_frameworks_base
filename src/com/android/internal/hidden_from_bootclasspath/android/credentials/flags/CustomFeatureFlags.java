package com.android.internal.hidden_from_bootclasspath.android.credentials.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_CLEAR_CREDENTIALS_FIX_ENABLED, Flags.FLAG_CLEAR_SESSION_ENABLED, Flags.FLAG_CONFIGURABLE_SELECTOR_UI_ENABLED, Flags.FLAG_CREDMAN_BIOMETRIC_API_ENABLED, Flags.FLAG_FIX_METRIC_DUPLICATION_EMITS, Flags.FLAG_FRAMEWORK_SESSION_ID_METRIC_BUNDLE, Flags.FLAG_HYBRID_FILTER_OPT_FIX_ENABLED, Flags.FLAG_INSTANT_APPS_ENABLED, Flags.FLAG_NEW_FRAMEWORK_METRICS, Flags.FLAG_NEW_SETTINGS_INTENTS, Flags.FLAG_NEW_SETTINGS_UI, Flags.FLAG_PACKAGE_UPDATE_FIX_ENABLED, Flags.FLAG_PROPAGATE_USER_CONTEXT_FOR_INTENT_CREATION, Flags.FLAG_SAFEGUARD_CANDIDATE_CREDENTIALS_API_CALLER, Flags.FLAG_SELECTOR_UI_IMPROVEMENTS_ENABLED, Flags.FLAG_SETTINGS_ACTIVITY_ENABLED, Flags.FLAG_SETTINGS_W_FIXES, Flags.FLAG_TTL_FIX_ENABLED, Flags.FLAG_WEAR_CREDENTIAL_MANAGER_ENABLED, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean clearCredentialsFixEnabled() {
        return getValue(Flags.FLAG_CLEAR_CREDENTIALS_FIX_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).clearCredentialsFixEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean clearSessionEnabled() {
        return getValue(Flags.FLAG_CLEAR_SESSION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).clearSessionEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean configurableSelectorUiEnabled() {
        return getValue(Flags.FLAG_CONFIGURABLE_SELECTOR_UI_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).configurableSelectorUiEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean credmanBiometricApiEnabled() {
        return getValue(Flags.FLAG_CREDMAN_BIOMETRIC_API_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).credmanBiometricApiEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean fixMetricDuplicationEmits() {
        return getValue(Flags.FLAG_FIX_METRIC_DUPLICATION_EMITS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixMetricDuplicationEmits();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean frameworkSessionIdMetricBundle() {
        return getValue(Flags.FLAG_FRAMEWORK_SESSION_ID_METRIC_BUNDLE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).frameworkSessionIdMetricBundle();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean hybridFilterOptFixEnabled() {
        return getValue(Flags.FLAG_HYBRID_FILTER_OPT_FIX_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hybridFilterOptFixEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean instantAppsEnabled() {
        return getValue(Flags.FLAG_INSTANT_APPS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).instantAppsEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean newFrameworkMetrics() {
        return getValue(Flags.FLAG_NEW_FRAMEWORK_METRICS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newFrameworkMetrics();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean newSettingsIntents() {
        return getValue(Flags.FLAG_NEW_SETTINGS_INTENTS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newSettingsIntents();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean newSettingsUi() {
        return getValue(Flags.FLAG_NEW_SETTINGS_UI, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newSettingsUi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean packageUpdateFixEnabled() {
        return getValue(Flags.FLAG_PACKAGE_UPDATE_FIX_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).packageUpdateFixEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean propagateUserContextForIntentCreation() {
        return getValue(Flags.FLAG_PROPAGATE_USER_CONTEXT_FOR_INTENT_CREATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).propagateUserContextForIntentCreation();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean safeguardCandidateCredentialsApiCaller() {
        return getValue(Flags.FLAG_SAFEGUARD_CANDIDATE_CREDENTIALS_API_CALLER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).safeguardCandidateCredentialsApiCaller();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean selectorUiImprovementsEnabled() {
        return getValue(Flags.FLAG_SELECTOR_UI_IMPROVEMENTS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).selectorUiImprovementsEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean settingsActivityEnabled() {
        return getValue(Flags.FLAG_SETTINGS_ACTIVITY_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).settingsActivityEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean settingsWFixes() {
        return getValue(Flags.FLAG_SETTINGS_W_FIXES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).settingsWFixes();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean ttlFixEnabled() {
        return getValue(Flags.FLAG_TTL_FIX_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ttlFixEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.credentials.flags.FeatureFlags
    public boolean wearCredentialManagerEnabled() {
        return getValue(Flags.FLAG_WEAR_CREDENTIAL_MANAGER_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.credentials.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wearCredentialManagerEnabled();
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
        return Arrays.asList(Flags.FLAG_CLEAR_CREDENTIALS_FIX_ENABLED, Flags.FLAG_CLEAR_SESSION_ENABLED, Flags.FLAG_CONFIGURABLE_SELECTOR_UI_ENABLED, Flags.FLAG_CREDMAN_BIOMETRIC_API_ENABLED, Flags.FLAG_FIX_METRIC_DUPLICATION_EMITS, Flags.FLAG_FRAMEWORK_SESSION_ID_METRIC_BUNDLE, Flags.FLAG_HYBRID_FILTER_OPT_FIX_ENABLED, Flags.FLAG_INSTANT_APPS_ENABLED, Flags.FLAG_NEW_FRAMEWORK_METRICS, Flags.FLAG_NEW_SETTINGS_INTENTS, Flags.FLAG_NEW_SETTINGS_UI, Flags.FLAG_PACKAGE_UPDATE_FIX_ENABLED, Flags.FLAG_PROPAGATE_USER_CONTEXT_FOR_INTENT_CREATION, Flags.FLAG_SAFEGUARD_CANDIDATE_CREDENTIALS_API_CALLER, Flags.FLAG_SELECTOR_UI_IMPROVEMENTS_ENABLED, Flags.FLAG_SETTINGS_ACTIVITY_ENABLED, Flags.FLAG_SETTINGS_W_FIXES, Flags.FLAG_TTL_FIX_ENABLED, Flags.FLAG_WEAR_CREDENTIAL_MANAGER_ENABLED);
    }
}
