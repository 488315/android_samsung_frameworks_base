package android.multiuser;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_LAUNCHER_USER_CONFIG = "android.multiuser.add_launcher_user_config";
    public static final String FLAG_ADD_UI_FOR_SOUNDS_FROM_BACKGROUND_USERS = "android.multiuser.add_ui_for_sounds_from_background_users";
    public static final String FLAG_ALLOW_MAIN_USER_TO_ACCESS_BLOCKED_NUMBER_PROVIDER = "android.multiuser.allow_main_user_to_access_blocked_number_provider";
    public static final String FLAG_ALLOW_RESOLVER_SHEET_FOR_PRIVATE_SPACE = "android.multiuser.allow_resolver_sheet_for_private_space";
    public static final String FLAG_ALLOW_SUPERVISING_PROFILE = "android.multiuser.allow_supervising_profile";
    public static final String FLAG_AVATAR_SYNC = "android.multiuser.avatar_sync";
    public static final String FLAG_BIND_WALLPAPER_SERVICE_ON_ITS_OWN_THREAD_DURING_A_USER_SWITCH = "android.multiuser.bind_wallpaper_service_on_its_own_thread_during_a_user_switch";
    public static final String FLAG_BLOCK_PRIVATE_SPACE_CREATION = "android.multiuser.block_private_space_creation";
    public static final String FLAG_CACHES_NOT_INVALIDATED_AT_START_READ_ONLY = "android.multiuser.caches_not_invalidated_at_start_read_only";
    public static final String FLAG_CACHE_PROFILES_READ_ONLY = "android.multiuser.cache_profiles_read_only";
    public static final String FLAG_CACHE_PROFILE_IDS_READ_ONLY = "android.multiuser.cache_profile_ids_read_only";
    public static final String FLAG_CACHE_PROFILE_PARENT_READ_ONLY = "android.multiuser.cache_profile_parent_read_only";
    public static final String FLAG_CACHE_PROFILE_TYPE_READ_ONLY = "android.multiuser.cache_profile_type_read_only";
    public static final String FLAG_CACHE_QUIET_MODE_STATE = "android.multiuser.cache_quiet_mode_state";
    public static final String FLAG_CACHE_USER_INFO_READ_ONLY = "android.multiuser.cache_user_info_read_only";
    public static final String FLAG_CACHE_USER_PROPERTIES_CORRECTLY_READ_ONLY = "android.multiuser.cache_user_properties_correctly_read_only";
    public static final String FLAG_CACHE_USER_RESTRICTIONS_READ_ONLY = "android.multiuser.cache_user_restrictions_read_only";
    public static final String FLAG_CACHE_USER_SERIAL_NUMBER = "android.multiuser.cache_user_serial_number";
    public static final String FLAG_CACHE_USER_SERIAL_NUMBER_READ_ONLY = "android.multiuser.cache_user_serial_number_read_only";
    public static final String FLAG_CACHE_USER_START_REALTIME_READ_ONLY = "android.multiuser.cache_user_start_realtime_read_only";
    public static final String FLAG_CACHE_USER_UNLOCK_REALTIME_READ_ONLY = "android.multiuser.cache_user_unlock_realtime_read_only";
    public static final String FLAG_CACHING_DEVELOPMENT_IMPROVEMENTS = "android.multiuser.caching_development_improvements";
    public static final String FLAG_DELETE_PRIVATE_SPACE_FROM_RESET = "android.multiuser.delete_private_space_from_reset";
    public static final String FLAG_DISABLE_PRIVATE_SPACE_ITEMS_ON_HOME = "android.multiuser.disable_private_space_items_on_home";
    public static final String FLAG_ENABLE_BIOMETRICS_TO_UNLOCK_PRIVATE_SPACE = "android.multiuser.enable_biometrics_to_unlock_private_space";
    public static final String FLAG_ENABLE_HIDING_PROFILES = "android.multiuser.enable_hiding_profiles";
    public static final String FLAG_ENABLE_LAUNCHER_APPS_HIDDEN_PROFILE_CHECKS = "android.multiuser.enable_launcher_apps_hidden_profile_checks";
    public static final String FLAG_ENABLE_MOVING_CONTENT_INTO_PRIVATE_SPACE = "android.multiuser.enable_moving_content_into_private_space";
    public static final String FLAG_ENABLE_PERMISSION_TO_ACCESS_HIDDEN_PROFILES = "android.multiuser.enable_permission_to_access_hidden_profiles";
    public static final String FLAG_ENABLE_PRIVATE_SPACE_AUTOLOCK_ON_RESTARTS = "android.multiuser.enable_private_space_autolock_on_restarts";
    public static final String FLAG_ENABLE_PRIVATE_SPACE_FEATURES = "android.multiuser.enable_private_space_features";
    public static final String FLAG_ENABLE_PRIVATE_SPACE_INTENT_REDIRECTION = "android.multiuser.enable_private_space_intent_redirection";
    public static final String FLAG_ENABLE_PS_SENSITIVE_NOTIFICATIONS_TOGGLE = "android.multiuser.enable_ps_sensitive_notifications_toggle";
    public static final String FLAG_ENABLE_SYSTEM_USER_ONLY_FOR_SERVICES_AND_PROVIDERS = "android.multiuser.enable_system_user_only_for_services_and_providers";
    public static final String FLAG_FIX_AVATAR_CONCURRENT_FILE_WRITE = "android.multiuser.fix_avatar_concurrent_file_write";
    public static final String FLAG_FIX_AVATAR_CONTENT_PROVIDER_NULL_AUTHORITY = "android.multiuser.fix_avatar_content_provider_null_authority";
    public static final String FLAG_FIX_AVATAR_CROSS_USER_LEAK = "android.multiuser.fix_avatar_cross_user_leak";
    public static final String FLAG_FIX_AVATAR_PICKER_NOT_RESPONDING_FOR_NEW_USER = "android.multiuser.fix_avatar_picker_not_responding_for_new_user";
    public static final String FLAG_FIX_AVATAR_PICKER_READ_BACK_ORDER = "android.multiuser.fix_avatar_picker_read_back_order";
    public static final String FLAG_FIX_AVATAR_PICKER_SELECTED_READ_BACK = "android.multiuser.fix_avatar_picker_selected_read_back";
    public static final String FLAG_FIX_DISABLING_OF_MU_TOGGLE_WHEN_RESTRICTION_APPLIED = "android.multiuser.fix_disabling_of_mu_toggle_when_restriction_applied";
    public static final String FLAG_FIX_GET_USER_PROPERTY_CACHE = "android.multiuser.fix_get_user_property_cache";
    public static final String FLAG_FIX_LARGE_DISPLAY_PRIVATE_SPACE_SETTINGS = "android.multiuser.fix_large_display_private_space_settings";
    public static final String FLAG_GET_USER_SWITCHABILITY_PERMISSION = "android.multiuser.get_user_switchability_permission";
    public static final String FLAG_HANDLE_INTERLEAVED_SETTINGS_FOR_PRIVATE_SPACE = "android.multiuser.handle_interleaved_settings_for_private_space";
    public static final String FLAG_IGNORE_RESTRICTIONS_WHEN_DELETING_PRIVATE_PROFILE = "android.multiuser.ignore_restrictions_when_deleting_private_profile";
    public static final String FLAG_INVALIDATE_CACHE_ON_USERS_CHANGED_READ_ONLY = "android.multiuser.invalidate_cache_on_users_changed_read_only";
    public static final String FLAG_LOGOUT_USER_API = "android.multiuser.logout_user_api";
    public static final String FLAG_MODIFY_PRIVATE_SPACE_SECONDARY_UNLOCK_SETUP_FLOW = "android.multiuser.modify_private_space_secondary_unlock_setup_flow";
    public static final String FLAG_MOVE_QUIET_MODE_OPERATIONS_TO_SEPARATE_THREAD = "android.multiuser.move_quiet_mode_operations_to_separate_thread";
    public static final String FLAG_MULTIPLE_ALARM_NOTIFICATIONS_SUPPORT = "android.multiuser.multiple_alarm_notifications_support";
    public static final String FLAG_MULTIUSER_WIDGET = "android.multiuser.multiuser_widget";
    public static final String FLAG_NEW_MULTIUSER_SETTINGS_UX = "android.multiuser.new_multiuser_settings_ux";
    public static final String FLAG_PLACE_ADD_USER_DIALOG_WITHIN_ACTIVITY = "android.multiuser.place_add_user_dialog_within_activity";
    public static final String FLAG_PRIVATE_SPACE_SEARCH_ILLUSTRATION_CONFIG = "android.multiuser.private_space_search_illustration_config";
    public static final String FLAG_PROFILES_FOR_ALL = "android.multiuser.profiles_for_all";
    public static final String FLAG_PROPERTY_INVALIDATED_CACHE_BYPASS_MISMATCHED_UIDS = "android.multiuser.property_invalidated_cache_bypass_mismatched_uids";
    public static final String FLAG_REORDER_WALLPAPER_DURING_USER_SWITCH = "android.multiuser.reorder_wallpaper_during_user_switch";
    public static final String FLAG_REQUIRE_PIN_BEFORE_USER_DELETION = "android.multiuser.require_pin_before_user_deletion";
    public static final String FLAG_RESTRICT_QUIET_MODE_CREDENTIAL_BUG_FIX_TO_MANAGED_PROFILES = "android.multiuser.restrict_quiet_mode_credential_bug_fix_to_managed_profiles";
    public static final String FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML = "android.multiuser.save_global_and_guest_restrictions_on_system_user_xml";
    public static final String FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML_READ_ONLY = "android.multiuser.save_global_and_guest_restrictions_on_system_user_xml_read_only";
    public static final String FLAG_SCHEDULE_STOP_OF_BACKGROUND_USER = "android.multiuser.schedule_stop_of_background_user";
    public static final String FLAG_SET_POWER_MODE_DURING_USER_SWITCH = "android.multiuser.set_power_mode_during_user_switch";
    public static final String FLAG_SHOW_CUSTOM_UNLOCK_TITLE_INSIDE_PRIVATE_PROFILE = "android.multiuser.show_custom_unlock_title_inside_private_profile";
    public static final String FLAG_SHOW_DIFFERENT_CREATION_ERROR_FOR_UNSUPPORTED_DEVICES = "android.multiuser.show_different_creation_error_for_unsupported_devices";
    public static final String FLAG_SHOW_SET_SCREEN_LOCK_DIALOG = "android.multiuser.show_set_screen_lock_dialog";
    public static final String FLAG_STOP_PREVIOUS_USER_APPS = "android.multiuser.stop_previous_user_apps";
    public static final String FLAG_SUPPORT_AUTOLOCK_FOR_PRIVATE_SPACE = "android.multiuser.support_autolock_for_private_space";
    public static final String FLAG_SUPPORT_COMMUNAL_PROFILE = "android.multiuser.support_communal_profile";
    public static final String FLAG_SUPPORT_COMMUNAL_PROFILE_NEXTGEN = "android.multiuser.support_communal_profile_nextgen";
    public static final String FLAG_UNICORN_MODE_REFACTORING_FOR_HSUM_READ_ONLY = "android.multiuser.unicorn_mode_refactoring_for_hsum_read_only";
    public static final String FLAG_USE_ALL_CPUS_DURING_USER_SWITCH = "android.multiuser.use_all_cpus_during_user_switch";
    public static final String FLAG_USE_PRIVATE_SPACE_ICON_IN_BIOMETRIC_PROMPT = "android.multiuser.use_private_space_icon_in_biometric_prompt";
    public static final String FLAG_USE_UNIFIED_RESOURCES = "android.multiuser.use_unified_resources";

    public static boolean addLauncherUserConfig() {
        return FEATURE_FLAGS.addLauncherUserConfig();
    }

    public static boolean addUiForSoundsFromBackgroundUsers() {
        return FEATURE_FLAGS.addUiForSoundsFromBackgroundUsers();
    }

    public static boolean allowMainUserToAccessBlockedNumberProvider() {
        return FEATURE_FLAGS.allowMainUserToAccessBlockedNumberProvider();
    }

    public static boolean allowResolverSheetForPrivateSpace() {
        return FEATURE_FLAGS.allowResolverSheetForPrivateSpace();
    }

    public static boolean allowSupervisingProfile() {
        return FEATURE_FLAGS.allowSupervisingProfile();
    }

    public static boolean avatarSync() {
        return FEATURE_FLAGS.avatarSync();
    }

    public static boolean bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch() {
        return FEATURE_FLAGS.bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch();
    }

    public static boolean blockPrivateSpaceCreation() {
        return FEATURE_FLAGS.blockPrivateSpaceCreation();
    }

    public static boolean cacheProfileIdsReadOnly() {
        return FEATURE_FLAGS.cacheProfileIdsReadOnly();
    }

    public static boolean cacheProfileParentReadOnly() {
        return FEATURE_FLAGS.cacheProfileParentReadOnly();
    }

    public static boolean cacheProfileTypeReadOnly() {
        return FEATURE_FLAGS.cacheProfileTypeReadOnly();
    }

    public static boolean cacheProfilesReadOnly() {
        return FEATURE_FLAGS.cacheProfilesReadOnly();
    }

    public static boolean cacheQuietModeState() {
        return FEATURE_FLAGS.cacheQuietModeState();
    }

    public static boolean cacheUserInfoReadOnly() {
        return FEATURE_FLAGS.cacheUserInfoReadOnly();
    }

    public static boolean cacheUserPropertiesCorrectlyReadOnly() {
        return FEATURE_FLAGS.cacheUserPropertiesCorrectlyReadOnly();
    }

    public static boolean cacheUserRestrictionsReadOnly() {
        return FEATURE_FLAGS.cacheUserRestrictionsReadOnly();
    }

    public static boolean cacheUserSerialNumber() {
        return FEATURE_FLAGS.cacheUserSerialNumber();
    }

    public static boolean cacheUserSerialNumberReadOnly() {
        return FEATURE_FLAGS.cacheUserSerialNumberReadOnly();
    }

    public static boolean cacheUserStartRealtimeReadOnly() {
        return FEATURE_FLAGS.cacheUserStartRealtimeReadOnly();
    }

    public static boolean cacheUserUnlockRealtimeReadOnly() {
        return FEATURE_FLAGS.cacheUserUnlockRealtimeReadOnly();
    }

    public static boolean cachesNotInvalidatedAtStartReadOnly() {
        return FEATURE_FLAGS.cachesNotInvalidatedAtStartReadOnly();
    }

    public static boolean cachingDevelopmentImprovements() {
        return FEATURE_FLAGS.cachingDevelopmentImprovements();
    }

    public static boolean deletePrivateSpaceFromReset() {
        return FEATURE_FLAGS.deletePrivateSpaceFromReset();
    }

    public static boolean disablePrivateSpaceItemsOnHome() {
        return FEATURE_FLAGS.disablePrivateSpaceItemsOnHome();
    }

    public static boolean enableBiometricsToUnlockPrivateSpace() {
        return FEATURE_FLAGS.enableBiometricsToUnlockPrivateSpace();
    }

    public static boolean enableHidingProfiles() {
        return FEATURE_FLAGS.enableHidingProfiles();
    }

    public static boolean enableLauncherAppsHiddenProfileChecks() {
        return FEATURE_FLAGS.enableLauncherAppsHiddenProfileChecks();
    }

    public static boolean enableMovingContentIntoPrivateSpace() {
        return FEATURE_FLAGS.enableMovingContentIntoPrivateSpace();
    }

    public static boolean enablePermissionToAccessHiddenProfiles() {
        return FEATURE_FLAGS.enablePermissionToAccessHiddenProfiles();
    }

    public static boolean enablePrivateSpaceAutolockOnRestarts() {
        return FEATURE_FLAGS.enablePrivateSpaceAutolockOnRestarts();
    }

    public static boolean enablePrivateSpaceFeatures() {
        return FEATURE_FLAGS.enablePrivateSpaceFeatures();
    }

    public static boolean enablePrivateSpaceIntentRedirection() {
        return FEATURE_FLAGS.enablePrivateSpaceIntentRedirection();
    }

    public static boolean enablePsSensitiveNotificationsToggle() {
        return FEATURE_FLAGS.enablePsSensitiveNotificationsToggle();
    }

    public static boolean enableSystemUserOnlyForServicesAndProviders() {
        return FEATURE_FLAGS.enableSystemUserOnlyForServicesAndProviders();
    }

    public static boolean fixAvatarConcurrentFileWrite() {
        return FEATURE_FLAGS.fixAvatarConcurrentFileWrite();
    }

    public static boolean fixAvatarContentProviderNullAuthority() {
        return FEATURE_FLAGS.fixAvatarContentProviderNullAuthority();
    }

    public static boolean fixAvatarCrossUserLeak() {
        return FEATURE_FLAGS.fixAvatarCrossUserLeak();
    }

    public static boolean fixAvatarPickerNotRespondingForNewUser() {
        return FEATURE_FLAGS.fixAvatarPickerNotRespondingForNewUser();
    }

    public static boolean fixAvatarPickerReadBackOrder() {
        return FEATURE_FLAGS.fixAvatarPickerReadBackOrder();
    }

    public static boolean fixAvatarPickerSelectedReadBack() {
        return FEATURE_FLAGS.fixAvatarPickerSelectedReadBack();
    }

    public static boolean fixDisablingOfMuToggleWhenRestrictionApplied() {
        return FEATURE_FLAGS.fixDisablingOfMuToggleWhenRestrictionApplied();
    }

    public static boolean fixGetUserPropertyCache() {
        return FEATURE_FLAGS.fixGetUserPropertyCache();
    }

    public static boolean fixLargeDisplayPrivateSpaceSettings() {
        return FEATURE_FLAGS.fixLargeDisplayPrivateSpaceSettings();
    }

    public static boolean getUserSwitchabilityPermission() {
        return FEATURE_FLAGS.getUserSwitchabilityPermission();
    }

    public static boolean handleInterleavedSettingsForPrivateSpace() {
        return FEATURE_FLAGS.handleInterleavedSettingsForPrivateSpace();
    }

    public static boolean ignoreRestrictionsWhenDeletingPrivateProfile() {
        return FEATURE_FLAGS.ignoreRestrictionsWhenDeletingPrivateProfile();
    }

    public static boolean invalidateCacheOnUsersChangedReadOnly() {
        return FEATURE_FLAGS.invalidateCacheOnUsersChangedReadOnly();
    }

    public static boolean logoutUserApi() {
        return FEATURE_FLAGS.logoutUserApi();
    }

    public static boolean modifyPrivateSpaceSecondaryUnlockSetupFlow() {
        return FEATURE_FLAGS.modifyPrivateSpaceSecondaryUnlockSetupFlow();
    }

    public static boolean moveQuietModeOperationsToSeparateThread() {
        return FEATURE_FLAGS.moveQuietModeOperationsToSeparateThread();
    }

    public static boolean multipleAlarmNotificationsSupport() {
        return FEATURE_FLAGS.multipleAlarmNotificationsSupport();
    }

    public static boolean multiuserWidget() {
        return FEATURE_FLAGS.multiuserWidget();
    }

    public static boolean newMultiuserSettingsUx() {
        return FEATURE_FLAGS.newMultiuserSettingsUx();
    }

    public static boolean placeAddUserDialogWithinActivity() {
        return FEATURE_FLAGS.placeAddUserDialogWithinActivity();
    }

    public static boolean privateSpaceSearchIllustrationConfig() {
        return FEATURE_FLAGS.privateSpaceSearchIllustrationConfig();
    }

    public static boolean profilesForAll() {
        return FEATURE_FLAGS.profilesForAll();
    }

    public static boolean propertyInvalidatedCacheBypassMismatchedUids() {
        return FEATURE_FLAGS.propertyInvalidatedCacheBypassMismatchedUids();
    }

    public static boolean reorderWallpaperDuringUserSwitch() {
        return FEATURE_FLAGS.reorderWallpaperDuringUserSwitch();
    }

    public static boolean requirePinBeforeUserDeletion() {
        return FEATURE_FLAGS.requirePinBeforeUserDeletion();
    }

    public static boolean restrictQuietModeCredentialBugFixToManagedProfiles() {
        return FEATURE_FLAGS.restrictQuietModeCredentialBugFixToManagedProfiles();
    }

    public static boolean saveGlobalAndGuestRestrictionsOnSystemUserXml() {
        return FEATURE_FLAGS.saveGlobalAndGuestRestrictionsOnSystemUserXml();
    }

    public static boolean saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly() {
        return FEATURE_FLAGS.saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly();
    }

    public static boolean scheduleStopOfBackgroundUser() {
        return FEATURE_FLAGS.scheduleStopOfBackgroundUser();
    }

    public static boolean setPowerModeDuringUserSwitch() {
        return FEATURE_FLAGS.setPowerModeDuringUserSwitch();
    }

    public static boolean showCustomUnlockTitleInsidePrivateProfile() {
        return FEATURE_FLAGS.showCustomUnlockTitleInsidePrivateProfile();
    }

    public static boolean showDifferentCreationErrorForUnsupportedDevices() {
        return FEATURE_FLAGS.showDifferentCreationErrorForUnsupportedDevices();
    }

    public static boolean showSetScreenLockDialog() {
        return FEATURE_FLAGS.showSetScreenLockDialog();
    }

    public static boolean stopPreviousUserApps() {
        return FEATURE_FLAGS.stopPreviousUserApps();
    }

    public static boolean supportAutolockForPrivateSpace() {
        return FEATURE_FLAGS.supportAutolockForPrivateSpace();
    }

    public static boolean supportCommunalProfile() {
        return FEATURE_FLAGS.supportCommunalProfile();
    }

    public static boolean supportCommunalProfileNextgen() {
        return FEATURE_FLAGS.supportCommunalProfileNextgen();
    }

    public static boolean unicornModeRefactoringForHsumReadOnly() {
        return FEATURE_FLAGS.unicornModeRefactoringForHsumReadOnly();
    }

    public static boolean useAllCpusDuringUserSwitch() {
        return FEATURE_FLAGS.useAllCpusDuringUserSwitch();
    }

    public static boolean usePrivateSpaceIconInBiometricPrompt() {
        return FEATURE_FLAGS.usePrivateSpaceIconInBiometricPrompt();
    }

    public static boolean useUnifiedResources() {
        return FEATURE_FLAGS.useUnifiedResources();
    }
}
