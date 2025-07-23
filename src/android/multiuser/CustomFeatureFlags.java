package android.multiuser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADD_LAUNCHER_USER_CONFIG, Flags.FLAG_ADD_UI_FOR_SOUNDS_FROM_BACKGROUND_USERS, Flags.FLAG_ALLOW_MAIN_USER_TO_ACCESS_BLOCKED_NUMBER_PROVIDER, Flags.FLAG_ALLOW_RESOLVER_SHEET_FOR_PRIVATE_SPACE, Flags.FLAG_ALLOW_SUPERVISING_PROFILE, Flags.FLAG_AVATAR_SYNC, Flags.FLAG_BIND_WALLPAPER_SERVICE_ON_ITS_OWN_THREAD_DURING_A_USER_SWITCH, Flags.FLAG_BLOCK_PRIVATE_SPACE_CREATION, Flags.FLAG_CACHE_PROFILE_IDS_READ_ONLY, Flags.FLAG_CACHE_PROFILE_PARENT_READ_ONLY, Flags.FLAG_CACHE_PROFILE_TYPE_READ_ONLY, Flags.FLAG_CACHE_PROFILES_READ_ONLY, Flags.FLAG_CACHE_QUIET_MODE_STATE, Flags.FLAG_CACHE_USER_INFO_READ_ONLY, Flags.FLAG_CACHE_USER_PROPERTIES_CORRECTLY_READ_ONLY, Flags.FLAG_CACHE_USER_RESTRICTIONS_READ_ONLY, Flags.FLAG_CACHE_USER_SERIAL_NUMBER, Flags.FLAG_CACHE_USER_SERIAL_NUMBER_READ_ONLY, Flags.FLAG_CACHE_USER_START_REALTIME_READ_ONLY, Flags.FLAG_CACHE_USER_UNLOCK_REALTIME_READ_ONLY, Flags.FLAG_CACHES_NOT_INVALIDATED_AT_START_READ_ONLY, Flags.FLAG_CACHING_DEVELOPMENT_IMPROVEMENTS, Flags.FLAG_DELETE_PRIVATE_SPACE_FROM_RESET, Flags.FLAG_DISABLE_PRIVATE_SPACE_ITEMS_ON_HOME, Flags.FLAG_ENABLE_BIOMETRICS_TO_UNLOCK_PRIVATE_SPACE, Flags.FLAG_ENABLE_HIDING_PROFILES, Flags.FLAG_ENABLE_LAUNCHER_APPS_HIDDEN_PROFILE_CHECKS, Flags.FLAG_ENABLE_MOVING_CONTENT_INTO_PRIVATE_SPACE, Flags.FLAG_ENABLE_PERMISSION_TO_ACCESS_HIDDEN_PROFILES, Flags.FLAG_ENABLE_PRIVATE_SPACE_AUTOLOCK_ON_RESTARTS, Flags.FLAG_ENABLE_PRIVATE_SPACE_FEATURES, Flags.FLAG_ENABLE_PRIVATE_SPACE_INTENT_REDIRECTION, Flags.FLAG_ENABLE_PS_SENSITIVE_NOTIFICATIONS_TOGGLE, Flags.FLAG_ENABLE_SYSTEM_USER_ONLY_FOR_SERVICES_AND_PROVIDERS, Flags.FLAG_FIX_AVATAR_CONCURRENT_FILE_WRITE, Flags.FLAG_FIX_AVATAR_CONTENT_PROVIDER_NULL_AUTHORITY, Flags.FLAG_FIX_AVATAR_CROSS_USER_LEAK, Flags.FLAG_FIX_AVATAR_PICKER_NOT_RESPONDING_FOR_NEW_USER, Flags.FLAG_FIX_AVATAR_PICKER_READ_BACK_ORDER, Flags.FLAG_FIX_AVATAR_PICKER_SELECTED_READ_BACK, Flags.FLAG_FIX_DISABLING_OF_MU_TOGGLE_WHEN_RESTRICTION_APPLIED, Flags.FLAG_FIX_GET_USER_PROPERTY_CACHE, Flags.FLAG_FIX_LARGE_DISPLAY_PRIVATE_SPACE_SETTINGS, Flags.FLAG_GET_USER_SWITCHABILITY_PERMISSION, Flags.FLAG_HANDLE_INTERLEAVED_SETTINGS_FOR_PRIVATE_SPACE, Flags.FLAG_IGNORE_RESTRICTIONS_WHEN_DELETING_PRIVATE_PROFILE, Flags.FLAG_INVALIDATE_CACHE_ON_USERS_CHANGED_READ_ONLY, Flags.FLAG_LOGOUT_USER_API, Flags.FLAG_MODIFY_PRIVATE_SPACE_SECONDARY_UNLOCK_SETUP_FLOW, Flags.FLAG_MOVE_QUIET_MODE_OPERATIONS_TO_SEPARATE_THREAD, Flags.FLAG_MULTIPLE_ALARM_NOTIFICATIONS_SUPPORT, Flags.FLAG_MULTIUSER_WIDGET, Flags.FLAG_NEW_MULTIUSER_SETTINGS_UX, Flags.FLAG_PLACE_ADD_USER_DIALOG_WITHIN_ACTIVITY, Flags.FLAG_PRIVATE_SPACE_SEARCH_ILLUSTRATION_CONFIG, Flags.FLAG_PROFILES_FOR_ALL, Flags.FLAG_PROPERTY_INVALIDATED_CACHE_BYPASS_MISMATCHED_UIDS, Flags.FLAG_REORDER_WALLPAPER_DURING_USER_SWITCH, Flags.FLAG_REQUIRE_PIN_BEFORE_USER_DELETION, Flags.FLAG_RESTRICT_QUIET_MODE_CREDENTIAL_BUG_FIX_TO_MANAGED_PROFILES, Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML, Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML_READ_ONLY, Flags.FLAG_SCHEDULE_STOP_OF_BACKGROUND_USER, Flags.FLAG_SET_POWER_MODE_DURING_USER_SWITCH, Flags.FLAG_SHOW_CUSTOM_UNLOCK_TITLE_INSIDE_PRIVATE_PROFILE, Flags.FLAG_SHOW_DIFFERENT_CREATION_ERROR_FOR_UNSUPPORTED_DEVICES, Flags.FLAG_SHOW_SET_SCREEN_LOCK_DIALOG, Flags.FLAG_STOP_PREVIOUS_USER_APPS, Flags.FLAG_SUPPORT_AUTOLOCK_FOR_PRIVATE_SPACE, Flags.FLAG_SUPPORT_COMMUNAL_PROFILE, Flags.FLAG_SUPPORT_COMMUNAL_PROFILE_NEXTGEN, Flags.FLAG_UNICORN_MODE_REFACTORING_FOR_HSUM_READ_ONLY, Flags.FLAG_USE_ALL_CPUS_DURING_USER_SWITCH, Flags.FLAG_USE_PRIVATE_SPACE_ICON_IN_BIOMETRIC_PROMPT, Flags.FLAG_USE_UNIFIED_RESOURCES, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean addLauncherUserConfig() {
        return getValue(Flags.FLAG_ADD_LAUNCHER_USER_CONFIG, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda73
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addLauncherUserConfig();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean addUiForSoundsFromBackgroundUsers() {
        return getValue(Flags.FLAG_ADD_UI_FOR_SOUNDS_FROM_BACKGROUND_USERS, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addUiForSoundsFromBackgroundUsers();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean allowMainUserToAccessBlockedNumberProvider() {
        return getValue(Flags.FLAG_ALLOW_MAIN_USER_TO_ACCESS_BLOCKED_NUMBER_PROVIDER, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowMainUserToAccessBlockedNumberProvider();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean allowResolverSheetForPrivateSpace() {
        return getValue(Flags.FLAG_ALLOW_RESOLVER_SHEET_FOR_PRIVATE_SPACE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowResolverSheetForPrivateSpace();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean allowSupervisingProfile() {
        return getValue(Flags.FLAG_ALLOW_SUPERVISING_PROFILE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda69
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowSupervisingProfile();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean avatarSync() {
        return getValue(Flags.FLAG_AVATAR_SYNC, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).avatarSync();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch() {
        return getValue(Flags.FLAG_BIND_WALLPAPER_SERVICE_ON_ITS_OWN_THREAD_DURING_A_USER_SWITCH, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda71
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean blockPrivateSpaceCreation() {
        return getValue(Flags.FLAG_BLOCK_PRIVATE_SPACE_CREATION, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).blockPrivateSpaceCreation();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheProfileIdsReadOnly() {
        return getValue(Flags.FLAG_CACHE_PROFILE_IDS_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheProfileIdsReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheProfileParentReadOnly() {
        return getValue(Flags.FLAG_CACHE_PROFILE_PARENT_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda58
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheProfileParentReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheProfileTypeReadOnly() {
        return getValue(Flags.FLAG_CACHE_PROFILE_TYPE_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda43
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheProfileTypeReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheProfilesReadOnly() {
        return getValue(Flags.FLAG_CACHE_PROFILES_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheProfilesReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheQuietModeState() {
        return getValue(Flags.FLAG_CACHE_QUIET_MODE_STATE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda66
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheQuietModeState();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserInfoReadOnly() {
        return getValue(Flags.FLAG_CACHE_USER_INFO_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda61
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheUserInfoReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserPropertiesCorrectlyReadOnly() {
        return getValue(Flags.FLAG_CACHE_USER_PROPERTIES_CORRECTLY_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda68
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheUserPropertiesCorrectlyReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserRestrictionsReadOnly() {
        return getValue(Flags.FLAG_CACHE_USER_RESTRICTIONS_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda55
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheUserRestrictionsReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserSerialNumber() {
        return getValue(Flags.FLAG_CACHE_USER_SERIAL_NUMBER, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheUserSerialNumber();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserSerialNumberReadOnly() {
        return getValue(Flags.FLAG_CACHE_USER_SERIAL_NUMBER_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheUserSerialNumberReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserStartRealtimeReadOnly() {
        return getValue(Flags.FLAG_CACHE_USER_START_REALTIME_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheUserStartRealtimeReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserUnlockRealtimeReadOnly() {
        return getValue(Flags.FLAG_CACHE_USER_UNLOCK_REALTIME_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda56
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheUserUnlockRealtimeReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cachesNotInvalidatedAtStartReadOnly() {
        return getValue(Flags.FLAG_CACHES_NOT_INVALIDATED_AT_START_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda48
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cachesNotInvalidatedAtStartReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cachingDevelopmentImprovements() {
        return getValue(Flags.FLAG_CACHING_DEVELOPMENT_IMPROVEMENTS, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda44
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cachingDevelopmentImprovements();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean deletePrivateSpaceFromReset() {
        return getValue(Flags.FLAG_DELETE_PRIVATE_SPACE_FROM_RESET, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deletePrivateSpaceFromReset();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean disablePrivateSpaceItemsOnHome() {
        return getValue(Flags.FLAG_DISABLE_PRIVATE_SPACE_ITEMS_ON_HOME, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disablePrivateSpaceItemsOnHome();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableBiometricsToUnlockPrivateSpace() {
        return getValue(Flags.FLAG_ENABLE_BIOMETRICS_TO_UNLOCK_PRIVATE_SPACE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableBiometricsToUnlockPrivateSpace();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableHidingProfiles() {
        return getValue(Flags.FLAG_ENABLE_HIDING_PROFILES, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableHidingProfiles();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableLauncherAppsHiddenProfileChecks() {
        return getValue(Flags.FLAG_ENABLE_LAUNCHER_APPS_HIDDEN_PROFILE_CHECKS, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda50
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableLauncherAppsHiddenProfileChecks();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableMovingContentIntoPrivateSpace() {
        return getValue(Flags.FLAG_ENABLE_MOVING_CONTENT_INTO_PRIVATE_SPACE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda49
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMovingContentIntoPrivateSpace();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePermissionToAccessHiddenProfiles() {
        return getValue(Flags.FLAG_ENABLE_PERMISSION_TO_ACCESS_HIDDEN_PROFILES, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePermissionToAccessHiddenProfiles();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePrivateSpaceAutolockOnRestarts() {
        return getValue(Flags.FLAG_ENABLE_PRIVATE_SPACE_AUTOLOCK_ON_RESTARTS, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePrivateSpaceAutolockOnRestarts();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePrivateSpaceFeatures() {
        return getValue(Flags.FLAG_ENABLE_PRIVATE_SPACE_FEATURES, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda47
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePrivateSpaceFeatures();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePrivateSpaceIntentRedirection() {
        return getValue(Flags.FLAG_ENABLE_PRIVATE_SPACE_INTENT_REDIRECTION, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda39
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePrivateSpaceIntentRedirection();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePsSensitiveNotificationsToggle() {
        return getValue(Flags.FLAG_ENABLE_PS_SENSITIVE_NOTIFICATIONS_TOGGLE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePsSensitiveNotificationsToggle();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableSystemUserOnlyForServicesAndProviders() {
        return getValue(Flags.FLAG_ENABLE_SYSTEM_USER_ONLY_FOR_SERVICES_AND_PROVIDERS, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSystemUserOnlyForServicesAndProviders();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarConcurrentFileWrite() {
        return getValue(Flags.FLAG_FIX_AVATAR_CONCURRENT_FILE_WRITE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixAvatarConcurrentFileWrite();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarContentProviderNullAuthority() {
        return getValue(Flags.FLAG_FIX_AVATAR_CONTENT_PROVIDER_NULL_AUTHORITY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixAvatarContentProviderNullAuthority();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarCrossUserLeak() {
        return getValue(Flags.FLAG_FIX_AVATAR_CROSS_USER_LEAK, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixAvatarCrossUserLeak();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarPickerNotRespondingForNewUser() {
        return getValue(Flags.FLAG_FIX_AVATAR_PICKER_NOT_RESPONDING_FOR_NEW_USER, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda70
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixAvatarPickerNotRespondingForNewUser();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarPickerReadBackOrder() {
        return getValue(Flags.FLAG_FIX_AVATAR_PICKER_READ_BACK_ORDER, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda62
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixAvatarPickerReadBackOrder();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarPickerSelectedReadBack() {
        return getValue(Flags.FLAG_FIX_AVATAR_PICKER_SELECTED_READ_BACK, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixAvatarPickerSelectedReadBack();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixDisablingOfMuToggleWhenRestrictionApplied() {
        return getValue(Flags.FLAG_FIX_DISABLING_OF_MU_TOGGLE_WHEN_RESTRICTION_APPLIED, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda59
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixDisablingOfMuToggleWhenRestrictionApplied();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixGetUserPropertyCache() {
        return getValue(Flags.FLAG_FIX_GET_USER_PROPERTY_CACHE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda51
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixGetUserPropertyCache();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixLargeDisplayPrivateSpaceSettings() {
        return getValue(Flags.FLAG_FIX_LARGE_DISPLAY_PRIVATE_SPACE_SETTINGS, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda57
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixLargeDisplayPrivateSpaceSettings();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean getUserSwitchabilityPermission() {
        return getValue(Flags.FLAG_GET_USER_SWITCHABILITY_PERMISSION, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getUserSwitchabilityPermission();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean handleInterleavedSettingsForPrivateSpace() {
        return getValue(Flags.FLAG_HANDLE_INTERLEAVED_SETTINGS_FOR_PRIVATE_SPACE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).handleInterleavedSettingsForPrivateSpace();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean ignoreRestrictionsWhenDeletingPrivateProfile() {
        return getValue(Flags.FLAG_IGNORE_RESTRICTIONS_WHEN_DELETING_PRIVATE_PROFILE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda72
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreRestrictionsWhenDeletingPrivateProfile();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean invalidateCacheOnUsersChangedReadOnly() {
        return getValue(Flags.FLAG_INVALIDATE_CACHE_ON_USERS_CHANGED_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).invalidateCacheOnUsersChangedReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean logoutUserApi() {
        return getValue(Flags.FLAG_LOGOUT_USER_API, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).logoutUserApi();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean modifyPrivateSpaceSecondaryUnlockSetupFlow() {
        return getValue(Flags.FLAG_MODIFY_PRIVATE_SPACE_SECONDARY_UNLOCK_SETUP_FLOW, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).modifyPrivateSpaceSecondaryUnlockSetupFlow();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean moveQuietModeOperationsToSeparateThread() {
        return getValue(Flags.FLAG_MOVE_QUIET_MODE_OPERATIONS_TO_SEPARATE_THREAD, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda74
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).moveQuietModeOperationsToSeparateThread();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean multipleAlarmNotificationsSupport() {
        return getValue(Flags.FLAG_MULTIPLE_ALARM_NOTIFICATIONS_SUPPORT, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda45
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).multipleAlarmNotificationsSupport();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean multiuserWidget() {
        return getValue(Flags.FLAG_MULTIUSER_WIDGET, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda60
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).multiuserWidget();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean newMultiuserSettingsUx() {
        return getValue(Flags.FLAG_NEW_MULTIUSER_SETTINGS_UX, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newMultiuserSettingsUx();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean placeAddUserDialogWithinActivity() {
        return getValue(Flags.FLAG_PLACE_ADD_USER_DIALOG_WITHIN_ACTIVITY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).placeAddUserDialogWithinActivity();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean privateSpaceSearchIllustrationConfig() {
        return getValue(Flags.FLAG_PRIVATE_SPACE_SEARCH_ILLUSTRATION_CONFIG, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).privateSpaceSearchIllustrationConfig();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean profilesForAll() {
        return getValue(Flags.FLAG_PROFILES_FOR_ALL, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).profilesForAll();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean propertyInvalidatedCacheBypassMismatchedUids() {
        return getValue(Flags.FLAG_PROPERTY_INVALIDATED_CACHE_BYPASS_MISMATCHED_UIDS, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda64
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).propertyInvalidatedCacheBypassMismatchedUids();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean reorderWallpaperDuringUserSwitch() {
        return getValue(Flags.FLAG_REORDER_WALLPAPER_DURING_USER_SWITCH, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda52
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reorderWallpaperDuringUserSwitch();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean requirePinBeforeUserDeletion() {
        return getValue(Flags.FLAG_REQUIRE_PIN_BEFORE_USER_DELETION, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda65
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).requirePinBeforeUserDeletion();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean restrictQuietModeCredentialBugFixToManagedProfiles() {
        return getValue(Flags.FLAG_RESTRICT_QUIET_MODE_CREDENTIAL_BUG_FIX_TO_MANAGED_PROFILES, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).restrictQuietModeCredentialBugFixToManagedProfiles();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean saveGlobalAndGuestRestrictionsOnSystemUserXml() {
        return getValue(Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda54
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).saveGlobalAndGuestRestrictionsOnSystemUserXml();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly() {
        return getValue(Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda53
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean scheduleStopOfBackgroundUser() {
        return getValue(Flags.FLAG_SCHEDULE_STOP_OF_BACKGROUND_USER, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).scheduleStopOfBackgroundUser();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean setPowerModeDuringUserSwitch() {
        return getValue(Flags.FLAG_SET_POWER_MODE_DURING_USER_SWITCH, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setPowerModeDuringUserSwitch();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean showCustomUnlockTitleInsidePrivateProfile() {
        return getValue(Flags.FLAG_SHOW_CUSTOM_UNLOCK_TITLE_INSIDE_PRIVATE_PROFILE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda63
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).showCustomUnlockTitleInsidePrivateProfile();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean showDifferentCreationErrorForUnsupportedDevices() {
        return getValue(Flags.FLAG_SHOW_DIFFERENT_CREATION_ERROR_FOR_UNSUPPORTED_DEVICES, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).showDifferentCreationErrorForUnsupportedDevices();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean showSetScreenLockDialog() {
        return getValue(Flags.FLAG_SHOW_SET_SCREEN_LOCK_DIALOG, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).showSetScreenLockDialog();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean stopPreviousUserApps() {
        return getValue(Flags.FLAG_STOP_PREVIOUS_USER_APPS, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).stopPreviousUserApps();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean supportAutolockForPrivateSpace() {
        return getValue(Flags.FLAG_SUPPORT_AUTOLOCK_FOR_PRIVATE_SPACE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda40
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportAutolockForPrivateSpace();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean supportCommunalProfile() {
        return getValue(Flags.FLAG_SUPPORT_COMMUNAL_PROFILE, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda42
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportCommunalProfile();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean supportCommunalProfileNextgen() {
        return getValue(Flags.FLAG_SUPPORT_COMMUNAL_PROFILE_NEXTGEN, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda46
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportCommunalProfileNextgen();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean unicornModeRefactoringForHsumReadOnly() {
        return getValue(Flags.FLAG_UNICORN_MODE_REFACTORING_FOR_HSUM_READ_ONLY, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).unicornModeRefactoringForHsumReadOnly();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean useAllCpusDuringUserSwitch() {
        return getValue(Flags.FLAG_USE_ALL_CPUS_DURING_USER_SWITCH, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useAllCpusDuringUserSwitch();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean usePrivateSpaceIconInBiometricPrompt() {
        return getValue(Flags.FLAG_USE_PRIVATE_SPACE_ICON_IN_BIOMETRIC_PROMPT, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).usePrivateSpaceIconInBiometricPrompt();
            }
        });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean useUnifiedResources() {
        return getValue(Flags.FLAG_USE_UNIFIED_RESOURCES, new Predicate() { // from class: android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda67
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useUnifiedResources();
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
        return Arrays.asList(Flags.FLAG_ADD_LAUNCHER_USER_CONFIG, Flags.FLAG_ADD_UI_FOR_SOUNDS_FROM_BACKGROUND_USERS, Flags.FLAG_ALLOW_MAIN_USER_TO_ACCESS_BLOCKED_NUMBER_PROVIDER, Flags.FLAG_ALLOW_RESOLVER_SHEET_FOR_PRIVATE_SPACE, Flags.FLAG_ALLOW_SUPERVISING_PROFILE, Flags.FLAG_AVATAR_SYNC, Flags.FLAG_BIND_WALLPAPER_SERVICE_ON_ITS_OWN_THREAD_DURING_A_USER_SWITCH, Flags.FLAG_BLOCK_PRIVATE_SPACE_CREATION, Flags.FLAG_CACHE_PROFILE_IDS_READ_ONLY, Flags.FLAG_CACHE_PROFILE_PARENT_READ_ONLY, Flags.FLAG_CACHE_PROFILE_TYPE_READ_ONLY, Flags.FLAG_CACHE_PROFILES_READ_ONLY, Flags.FLAG_CACHE_QUIET_MODE_STATE, Flags.FLAG_CACHE_USER_INFO_READ_ONLY, Flags.FLAG_CACHE_USER_PROPERTIES_CORRECTLY_READ_ONLY, Flags.FLAG_CACHE_USER_RESTRICTIONS_READ_ONLY, Flags.FLAG_CACHE_USER_SERIAL_NUMBER, Flags.FLAG_CACHE_USER_SERIAL_NUMBER_READ_ONLY, Flags.FLAG_CACHE_USER_START_REALTIME_READ_ONLY, Flags.FLAG_CACHE_USER_UNLOCK_REALTIME_READ_ONLY, Flags.FLAG_CACHES_NOT_INVALIDATED_AT_START_READ_ONLY, Flags.FLAG_CACHING_DEVELOPMENT_IMPROVEMENTS, Flags.FLAG_DELETE_PRIVATE_SPACE_FROM_RESET, Flags.FLAG_DISABLE_PRIVATE_SPACE_ITEMS_ON_HOME, Flags.FLAG_ENABLE_BIOMETRICS_TO_UNLOCK_PRIVATE_SPACE, Flags.FLAG_ENABLE_HIDING_PROFILES, Flags.FLAG_ENABLE_LAUNCHER_APPS_HIDDEN_PROFILE_CHECKS, Flags.FLAG_ENABLE_MOVING_CONTENT_INTO_PRIVATE_SPACE, Flags.FLAG_ENABLE_PERMISSION_TO_ACCESS_HIDDEN_PROFILES, Flags.FLAG_ENABLE_PRIVATE_SPACE_AUTOLOCK_ON_RESTARTS, Flags.FLAG_ENABLE_PRIVATE_SPACE_FEATURES, Flags.FLAG_ENABLE_PRIVATE_SPACE_INTENT_REDIRECTION, Flags.FLAG_ENABLE_PS_SENSITIVE_NOTIFICATIONS_TOGGLE, Flags.FLAG_ENABLE_SYSTEM_USER_ONLY_FOR_SERVICES_AND_PROVIDERS, Flags.FLAG_FIX_AVATAR_CONCURRENT_FILE_WRITE, Flags.FLAG_FIX_AVATAR_CONTENT_PROVIDER_NULL_AUTHORITY, Flags.FLAG_FIX_AVATAR_CROSS_USER_LEAK, Flags.FLAG_FIX_AVATAR_PICKER_NOT_RESPONDING_FOR_NEW_USER, Flags.FLAG_FIX_AVATAR_PICKER_READ_BACK_ORDER, Flags.FLAG_FIX_AVATAR_PICKER_SELECTED_READ_BACK, Flags.FLAG_FIX_DISABLING_OF_MU_TOGGLE_WHEN_RESTRICTION_APPLIED, Flags.FLAG_FIX_GET_USER_PROPERTY_CACHE, Flags.FLAG_FIX_LARGE_DISPLAY_PRIVATE_SPACE_SETTINGS, Flags.FLAG_GET_USER_SWITCHABILITY_PERMISSION, Flags.FLAG_HANDLE_INTERLEAVED_SETTINGS_FOR_PRIVATE_SPACE, Flags.FLAG_IGNORE_RESTRICTIONS_WHEN_DELETING_PRIVATE_PROFILE, Flags.FLAG_INVALIDATE_CACHE_ON_USERS_CHANGED_READ_ONLY, Flags.FLAG_LOGOUT_USER_API, Flags.FLAG_MODIFY_PRIVATE_SPACE_SECONDARY_UNLOCK_SETUP_FLOW, Flags.FLAG_MOVE_QUIET_MODE_OPERATIONS_TO_SEPARATE_THREAD, Flags.FLAG_MULTIPLE_ALARM_NOTIFICATIONS_SUPPORT, Flags.FLAG_MULTIUSER_WIDGET, Flags.FLAG_NEW_MULTIUSER_SETTINGS_UX, Flags.FLAG_PLACE_ADD_USER_DIALOG_WITHIN_ACTIVITY, Flags.FLAG_PRIVATE_SPACE_SEARCH_ILLUSTRATION_CONFIG, Flags.FLAG_PROFILES_FOR_ALL, Flags.FLAG_PROPERTY_INVALIDATED_CACHE_BYPASS_MISMATCHED_UIDS, Flags.FLAG_REORDER_WALLPAPER_DURING_USER_SWITCH, Flags.FLAG_REQUIRE_PIN_BEFORE_USER_DELETION, Flags.FLAG_RESTRICT_QUIET_MODE_CREDENTIAL_BUG_FIX_TO_MANAGED_PROFILES, Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML, Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML_READ_ONLY, Flags.FLAG_SCHEDULE_STOP_OF_BACKGROUND_USER, Flags.FLAG_SET_POWER_MODE_DURING_USER_SWITCH, Flags.FLAG_SHOW_CUSTOM_UNLOCK_TITLE_INSIDE_PRIVATE_PROFILE, Flags.FLAG_SHOW_DIFFERENT_CREATION_ERROR_FOR_UNSUPPORTED_DEVICES, Flags.FLAG_SHOW_SET_SCREEN_LOCK_DIALOG, Flags.FLAG_STOP_PREVIOUS_USER_APPS, Flags.FLAG_SUPPORT_AUTOLOCK_FOR_PRIVATE_SPACE, Flags.FLAG_SUPPORT_COMMUNAL_PROFILE, Flags.FLAG_SUPPORT_COMMUNAL_PROFILE_NEXTGEN, Flags.FLAG_UNICORN_MODE_REFACTORING_FOR_HSUM_READ_ONLY, Flags.FLAG_USE_ALL_CPUS_DURING_USER_SWITCH, Flags.FLAG_USE_PRIVATE_SPACE_ICON_IN_BIOMETRIC_PROMPT, Flags.FLAG_USE_UNIFIED_RESOURCES);
    }
}
