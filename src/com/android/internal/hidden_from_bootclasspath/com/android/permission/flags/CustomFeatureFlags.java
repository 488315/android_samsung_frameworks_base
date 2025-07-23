package com.android.internal.hidden_from_bootclasspath.com.android.permission.flags;

import android.os.Build;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(""));
    private Map<String, Integer> mFinalizedFlags = new HashMap(Map.ofEntries(Map.entry(Flags.FLAG_PRIVATE_PROFILE_SUPPORTED, 35), Map.entry(Flags.FLAG_PRIVATE_PROFILE_TITLE_API, 35), Map.entry(Flags.FLAG_WEAR_PRIVACY_DASHBOARD_ENABLED_READ_ONLY, 35), Map.entry("", Integer.MAX_VALUE)));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean addBannersToPrivacySensitiveAppsForAaos() {
        return getValue(Flags.FLAG_ADD_BANNERS_TO_PRIVACY_SENSITIVE_APPS_FOR_AAOS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addBannersToPrivacySensitiveAppsForAaos();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean appPermissionFragmentUsesPreferences() {
        return getValue(Flags.FLAG_APP_PERMISSION_FRAGMENT_USES_PREFERENCES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appPermissionFragmentUsesPreferences();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean archivingReadOnly() {
        return getValue(Flags.FLAG_ARCHIVING_READ_ONLY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).archivingReadOnly();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean crossUserRoleEnabled() {
        return getValue(Flags.FLAG_CROSS_USER_ROLE_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).crossUserRoleEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean crossUserRoleUxBugfixEnabled() {
        return getValue(Flags.FLAG_CROSS_USER_ROLE_UX_BUGFIX_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).crossUserRoleUxBugfixEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean declutteredPermissionManagerEnabled() {
        return getValue(Flags.FLAG_DECLUTTERED_PERMISSION_MANAGER_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).declutteredPermissionManagerEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean defaultAppsRecommendationEnabled() {
        return getValue(Flags.FLAG_DEFAULT_APPS_RECOMMENDATION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).defaultAppsRecommendationEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean enableCoarseFineLocationPromptForAaos() {
        return getValue(Flags.FLAG_ENABLE_COARSE_FINE_LOCATION_PROMPT_FOR_AAOS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCoarseFineLocationPromptForAaos();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean enhancedConfirmationBackportEnabled() {
        return getValue(Flags.FLAG_ENHANCED_CONFIRMATION_BACKPORT_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enhancedConfirmationBackportEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean expressiveDesignEnabled() {
        return getValue(Flags.FLAG_EXPRESSIVE_DESIGN_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).expressiveDesignEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean fixSafetyCenterTouchTarget() {
        return getValue(Flags.FLAG_FIX_SAFETY_CENTER_TOUCH_TARGET, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixSafetyCenterTouchTarget();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean odadNotificationsSupported() {
        return getValue(Flags.FLAG_ODAD_NOTIFICATIONS_SUPPORTED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).odadNotificationsSupported();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean permissionTimelineAttributionLabelFix() {
        return getValue(Flags.FLAG_PERMISSION_TIMELINE_ATTRIBUTION_LABEL_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).permissionTimelineAttributionLabelFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean privateProfileSupported() {
        return getValue(Flags.FLAG_PRIVATE_PROFILE_SUPPORTED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).privateProfileSupported();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean privateProfileTitleApi() {
        return getValue(Flags.FLAG_PRIVATE_PROFILE_TITLE_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).privateProfileTitleApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean safetyCenterEnabledNoDeviceConfig() {
        return getValue(Flags.FLAG_SAFETY_CENTER_ENABLED_NO_DEVICE_CONFIG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).safetyCenterEnabledNoDeviceConfig();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean safetyCenterIssueOnlyAffectsGroupStatus() {
        return getValue(Flags.FLAG_SAFETY_CENTER_ISSUE_ONLY_AFFECTS_GROUP_STATUS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).safetyCenterIssueOnlyAffectsGroupStatus();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean wearComposeMaterial3() {
        return getValue(Flags.FLAG_WEAR_COMPOSE_MATERIAL3, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wearComposeMaterial3();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.FeatureFlags
    public boolean wearPrivacyDashboardEnabledReadOnly() {
        return getValue(Flags.FLAG_WEAR_PRIVACY_DASHBOARD_ENABLED_READ_ONLY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wearPrivacyDashboardEnabledReadOnly();
            }
        });
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ADD_BANNERS_TO_PRIVACY_SENSITIVE_APPS_FOR_AAOS, Flags.FLAG_APP_PERMISSION_FRAGMENT_USES_PREFERENCES, Flags.FLAG_ARCHIVING_READ_ONLY, Flags.FLAG_CROSS_USER_ROLE_ENABLED, Flags.FLAG_CROSS_USER_ROLE_UX_BUGFIX_ENABLED, Flags.FLAG_DECLUTTERED_PERMISSION_MANAGER_ENABLED, Flags.FLAG_DEFAULT_APPS_RECOMMENDATION_ENABLED, Flags.FLAG_ENABLE_COARSE_FINE_LOCATION_PROMPT_FOR_AAOS, Flags.FLAG_ENHANCED_CONFIRMATION_BACKPORT_ENABLED, Flags.FLAG_EXPRESSIVE_DESIGN_ENABLED, Flags.FLAG_FIX_SAFETY_CENTER_TOUCH_TARGET, Flags.FLAG_ODAD_NOTIFICATIONS_SUPPORTED, Flags.FLAG_PERMISSION_TIMELINE_ATTRIBUTION_LABEL_FIX, Flags.FLAG_PRIVATE_PROFILE_SUPPORTED, Flags.FLAG_PRIVATE_PROFILE_TITLE_API, Flags.FLAG_SAFETY_CENTER_ENABLED_NO_DEVICE_CONFIG, Flags.FLAG_SAFETY_CENTER_ISSUE_ONLY_AFFECTS_GROUP_STATUS, Flags.FLAG_WEAR_COMPOSE_MATERIAL3, Flags.FLAG_WEAR_PRIVACY_DASHBOARD_ENABLED_READ_ONLY);
    }

    public boolean isFlagFinalized(String str) {
        return this.mFinalizedFlags.containsKey(str) && Build.VERSION.SDK_INT >= this.mFinalizedFlags.get(str).intValue();
    }
}
