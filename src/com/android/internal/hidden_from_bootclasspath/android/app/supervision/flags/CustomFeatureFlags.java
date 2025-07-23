package com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_DEPRECATE_DPM_SUPERVISION_APIS, Flags.FLAG_ENABLE_APP_APPROVAL, Flags.FLAG_ENABLE_SUPERVISION_APP_SERVICE, Flags.FLAG_ENABLE_SUPERVISION_PIN_RECOVERY_SCREEN, Flags.FLAG_ENABLE_SUPERVISION_SETTINGS_SCREEN, Flags.FLAG_ENABLE_SYNC_WITH_DPM, Flags.FLAG_ENABLE_WEB_CONTENT_FILTERS_SCREEN, Flags.FLAG_SUPERVISION_API, Flags.FLAG_SUPERVISION_API_ON_WEAR, Flags.FLAG_SUPERVISION_MANAGER_APIS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean deprecateDpmSupervisionApis() {
        return getValue(Flags.FLAG_DEPRECATE_DPM_SUPERVISION_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deprecateDpmSupervisionApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean enableAppApproval() {
        return getValue(Flags.FLAG_ENABLE_APP_APPROVAL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAppApproval();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean enableSupervisionAppService() {
        return getValue(Flags.FLAG_ENABLE_SUPERVISION_APP_SERVICE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSupervisionAppService();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean enableSupervisionPinRecoveryScreen() {
        return getValue(Flags.FLAG_ENABLE_SUPERVISION_PIN_RECOVERY_SCREEN, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSupervisionPinRecoveryScreen();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean enableSupervisionSettingsScreen() {
        return getValue(Flags.FLAG_ENABLE_SUPERVISION_SETTINGS_SCREEN, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSupervisionSettingsScreen();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean enableSyncWithDpm() {
        return getValue(Flags.FLAG_ENABLE_SYNC_WITH_DPM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSyncWithDpm();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean enableWebContentFiltersScreen() {
        return getValue(Flags.FLAG_ENABLE_WEB_CONTENT_FILTERS_SCREEN, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableWebContentFiltersScreen();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean supervisionApi() {
        return getValue(Flags.FLAG_SUPERVISION_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supervisionApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean supervisionApiOnWear() {
        return getValue(Flags.FLAG_SUPERVISION_API_ON_WEAR, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supervisionApiOnWear();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.FeatureFlags
    public boolean supervisionManagerApis() {
        return getValue(Flags.FLAG_SUPERVISION_MANAGER_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supervisionManagerApis();
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
        return Arrays.asList(Flags.FLAG_DEPRECATE_DPM_SUPERVISION_APIS, Flags.FLAG_ENABLE_APP_APPROVAL, Flags.FLAG_ENABLE_SUPERVISION_APP_SERVICE, Flags.FLAG_ENABLE_SUPERVISION_PIN_RECOVERY_SCREEN, Flags.FLAG_ENABLE_SUPERVISION_SETTINGS_SCREEN, Flags.FLAG_ENABLE_SYNC_WITH_DPM, Flags.FLAG_ENABLE_WEB_CONTENT_FILTERS_SCREEN, Flags.FLAG_SUPERVISION_API, Flags.FLAG_SUPERVISION_API_ON_WEAR, Flags.FLAG_SUPERVISION_MANAGER_APIS);
    }
}
