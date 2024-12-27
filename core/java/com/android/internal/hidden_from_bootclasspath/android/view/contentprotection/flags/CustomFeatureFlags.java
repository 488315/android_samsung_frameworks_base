package com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_BLOCKLIST_UPDATE_ENABLED, Flags.FLAG_CREATE_ACCESSIBILITY_OVERLAY_APP_OP_ENABLED, Flags.FLAG_MANAGE_DEVICE_POLICY_ENABLED, Flags.FLAG_PARSE_GROUPS_CONFIG_ENABLED, Flags.FLAG_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER_APP_OP_ENABLED, Flags.FLAG_SETTING_UI_ENABLED, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.FeatureFlags
    public boolean blocklistUpdateEnabled() {
        return getValue(Flags.FLAG_BLOCKLIST_UPDATE_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).blocklistUpdateEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.FeatureFlags
    public boolean createAccessibilityOverlayAppOpEnabled() {
        return getValue(Flags.FLAG_CREATE_ACCESSIBILITY_OVERLAY_APP_OP_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).createAccessibilityOverlayAppOpEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.FeatureFlags
    public boolean manageDevicePolicyEnabled() {
        return getValue(Flags.FLAG_MANAGE_DEVICE_POLICY_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).manageDevicePolicyEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.FeatureFlags
    public boolean parseGroupsConfigEnabled() {
        return getValue(Flags.FLAG_PARSE_GROUPS_CONFIG_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).parseGroupsConfigEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.FeatureFlags
    public boolean rapidClearNotificationsByListenerAppOpEnabled() {
        return getValue(Flags.FLAG_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER_APP_OP_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rapidClearNotificationsByListenerAppOpEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.FeatureFlags
    public boolean settingUiEnabled() {
        return getValue(Flags.FLAG_SETTING_UI_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).settingUiEnabled();
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
        return Arrays.asList(Flags.FLAG_BLOCKLIST_UPDATE_ENABLED, Flags.FLAG_CREATE_ACCESSIBILITY_OVERLAY_APP_OP_ENABLED, Flags.FLAG_MANAGE_DEVICE_POLICY_ENABLED, Flags.FLAG_PARSE_GROUPS_CONFIG_ENABLED, Flags.FLAG_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER_APP_OP_ENABLED, Flags.FLAG_SETTING_UI_ENABLED);
    }
}
