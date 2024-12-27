package com.android.internal.hidden_from_bootclasspath.android.provider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_A11Y_STANDALONE_GESTURE_ENABLED, Flags.FLAG_BACKUP_TASKS_SETTINGS_SCREEN, Flags.FLAG_SYSTEM_SETTINGS_DEFAULT, Flags.FLAG_USER_KEYS, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.FeatureFlags
    public boolean a11yStandaloneGestureEnabled() {
        return getValue(Flags.FLAG_A11Y_STANDALONE_GESTURE_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.provider.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).a11yStandaloneGestureEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.FeatureFlags
    public boolean backupTasksSettingsScreen() {
        return getValue(Flags.FLAG_BACKUP_TASKS_SETTINGS_SCREEN, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.provider.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).backupTasksSettingsScreen();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.FeatureFlags
    public boolean systemSettingsDefault() {
        return getValue(Flags.FLAG_SYSTEM_SETTINGS_DEFAULT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.provider.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).systemSettingsDefault();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.FeatureFlags
    public boolean userKeys() {
        return getValue(Flags.FLAG_USER_KEYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.provider.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).userKeys();
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
        return Arrays.asList(Flags.FLAG_A11Y_STANDALONE_GESTURE_ENABLED, Flags.FLAG_BACKUP_TASKS_SETTINGS_SCREEN, Flags.FLAG_SYSTEM_SETTINGS_DEFAULT, Flags.FLAG_USER_KEYS);
    }
}
