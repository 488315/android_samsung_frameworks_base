package android.multiuser;

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
                            Flags.FLAG_ADD_UI_FOR_SOUNDS_FROM_BACKGROUND_USERS,
                            Flags.FLAG_ALLOW_MAIN_USER_TO_ACCESS_BLOCKED_NUMBER_PROVIDER,
                            Flags.FLAG_ALLOW_RESOLVER_SHEET_FOR_PRIVATE_SPACE,
                            Flags.FLAG_AVATAR_SYNC,
                            Flags
                                    .FLAG_BIND_WALLPAPER_SERVICE_ON_ITS_OWN_THREAD_DURING_A_USER_SWITCH,
                            Flags.FLAG_BLOCK_PRIVATE_SPACE_CREATION,
                            Flags.FLAG_DELETE_PRIVATE_SPACE_FROM_RESET,
                            Flags.FLAG_DISABLE_PRIVATE_SPACE_ITEMS_ON_HOME,
                            Flags.FLAG_ENABLE_BIOMETRICS_TO_UNLOCK_PRIVATE_SPACE,
                            Flags.FLAG_ENABLE_HIDING_PROFILES,
                            Flags.FLAG_ENABLE_LAUNCHER_APPS_HIDDEN_PROFILE_CHECKS,
                            Flags.FLAG_ENABLE_PERMISSION_TO_ACCESS_HIDDEN_PROFILES,
                            Flags.FLAG_ENABLE_PRIVATE_SPACE_AUTOLOCK_ON_RESTARTS,
                            Flags.FLAG_ENABLE_PRIVATE_SPACE_FEATURES,
                            Flags.FLAG_ENABLE_PRIVATE_SPACE_INTENT_REDIRECTION,
                            Flags.FLAG_ENABLE_PS_SENSITIVE_NOTIFICATIONS_TOGGLE,
                            Flags.FLAG_ENABLE_SYSTEM_USER_ONLY_FOR_SERVICES_AND_PROVIDERS,
                            Flags.FLAG_FIX_AVATAR_CONCURRENT_FILE_WRITE,
                            Flags.FLAG_FIX_AVATAR_PICKER_READ_BACK_ORDER,
                            Flags.FLAG_FIX_AVATAR_PICKER_SELECTED_READ_BACK,
                            Flags.FLAG_HANDLE_INTERLEAVED_SETTINGS_FOR_PRIVATE_SPACE,
                            Flags.FLAG_MOVE_QUIET_MODE_OPERATIONS_TO_SEPARATE_THREAD,
                            Flags.FLAG_NEW_MULTIUSER_SETTINGS_UX,
                            Flags.FLAG_REORDER_WALLPAPER_DURING_USER_SWITCH,
                            Flags.FLAG_RESTRICT_QUIET_MODE_CREDENTIAL_BUG_FIX_TO_MANAGED_PROFILES,
                            Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML,
                            Flags
                                    .FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML_READ_ONLY,
                            Flags.FLAG_SCHEDULE_STOP_OF_BACKGROUND_USER,
                            Flags.FLAG_SET_POWER_MODE_DURING_USER_SWITCH,
                            Flags.FLAG_SHOW_SET_SCREEN_LOCK_DIALOG,
                            Flags.FLAG_START_USER_BEFORE_SCHEDULED_ALARMS,
                            Flags.FLAG_SUPPORT_AUTOLOCK_FOR_PRIVATE_SPACE,
                            Flags.FLAG_SUPPORT_COMMUNAL_PROFILE,
                            Flags.FLAG_SUPPORT_COMMUNAL_PROFILE_NEXTGEN,
                            Flags.FLAG_USE_ALL_CPUS_DURING_USER_SWITCH,
                            Flags.FLAG_USE_PRIVATE_SPACE_ICON_IN_BIOMETRIC_PROMPT,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean addUiForSoundsFromBackgroundUsers() {
        return getValue(
                Flags.FLAG_ADD_UI_FOR_SOUNDS_FROM_BACKGROUND_USERS,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).addUiForSoundsFromBackgroundUsers();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean allowMainUserToAccessBlockedNumberProvider() {
        return getValue(
                Flags.FLAG_ALLOW_MAIN_USER_TO_ACCESS_BLOCKED_NUMBER_PROVIDER,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).allowMainUserToAccessBlockedNumberProvider();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean allowResolverSheetForPrivateSpace() {
        return getValue(
                Flags.FLAG_ALLOW_RESOLVER_SHEET_FOR_PRIVATE_SPACE,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).allowResolverSheetForPrivateSpace();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean avatarSync() {
        return getValue(
                Flags.FLAG_AVATAR_SYNC,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).avatarSync();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch() {
        return getValue(
                Flags.FLAG_BIND_WALLPAPER_SERVICE_ON_ITS_OWN_THREAD_DURING_A_USER_SWITCH,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda34
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean blockPrivateSpaceCreation() {
        return getValue(
                Flags.FLAG_BLOCK_PRIVATE_SPACE_CREATION,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda18
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).blockPrivateSpaceCreation();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean deletePrivateSpaceFromReset() {
        return getValue(
                Flags.FLAG_DELETE_PRIVATE_SPACE_FROM_RESET,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda19
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).deletePrivateSpaceFromReset();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean disablePrivateSpaceItemsOnHome() {
        return getValue(
                Flags.FLAG_DISABLE_PRIVATE_SPACE_ITEMS_ON_HOME,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).disablePrivateSpaceItemsOnHome();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableBiometricsToUnlockPrivateSpace() {
        return getValue(
                Flags.FLAG_ENABLE_BIOMETRICS_TO_UNLOCK_PRIVATE_SPACE,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableBiometricsToUnlockPrivateSpace();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableHidingProfiles() {
        return getValue(
                Flags.FLAG_ENABLE_HIDING_PROFILES,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableHidingProfiles();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableLauncherAppsHiddenProfileChecks() {
        return getValue(
                Flags.FLAG_ENABLE_LAUNCHER_APPS_HIDDEN_PROFILE_CHECKS,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda28
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableLauncherAppsHiddenProfileChecks();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePermissionToAccessHiddenProfiles() {
        return getValue(
                Flags.FLAG_ENABLE_PERMISSION_TO_ACCESS_HIDDEN_PROFILES,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enablePermissionToAccessHiddenProfiles();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePrivateSpaceAutolockOnRestarts() {
        return getValue(
                Flags.FLAG_ENABLE_PRIVATE_SPACE_AUTOLOCK_ON_RESTARTS,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enablePrivateSpaceAutolockOnRestarts();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePrivateSpaceFeatures() {
        return getValue(
                Flags.FLAG_ENABLE_PRIVATE_SPACE_FEATURES,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda27
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enablePrivateSpaceFeatures();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePrivateSpaceIntentRedirection() {
        return getValue(
                Flags.FLAG_ENABLE_PRIVATE_SPACE_INTENT_REDIRECTION,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda23
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enablePrivateSpaceIntentRedirection();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePsSensitiveNotificationsToggle() {
        return getValue(
                Flags.FLAG_ENABLE_PS_SENSITIVE_NOTIFICATIONS_TOGGLE,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda22
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enablePsSensitiveNotificationsToggle();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableSystemUserOnlyForServicesAndProviders() {
        return getValue(
                Flags.FLAG_ENABLE_SYSTEM_USER_ONLY_FOR_SERVICES_AND_PROVIDERS,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda17
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableSystemUserOnlyForServicesAndProviders();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarConcurrentFileWrite() {
        return getValue(
                Flags.FLAG_FIX_AVATAR_CONCURRENT_FILE_WRITE,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fixAvatarConcurrentFileWrite();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarPickerReadBackOrder() {
        return getValue(
                Flags.FLAG_FIX_AVATAR_PICKER_READ_BACK_ORDER,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda32
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fixAvatarPickerReadBackOrder();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarPickerSelectedReadBack() {
        return getValue(
                Flags.FLAG_FIX_AVATAR_PICKER_SELECTED_READ_BACK,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fixAvatarPickerSelectedReadBack();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean handleInterleavedSettingsForPrivateSpace() {
        return getValue(
                Flags.FLAG_HANDLE_INTERLEAVED_SETTINGS_FOR_PRIVATE_SPACE,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).handleInterleavedSettingsForPrivateSpace();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean moveQuietModeOperationsToSeparateThread() {
        return getValue(
                Flags.FLAG_MOVE_QUIET_MODE_OPERATIONS_TO_SEPARATE_THREAD,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda35
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).moveQuietModeOperationsToSeparateThread();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean newMultiuserSettingsUx() {
        return getValue(
                Flags.FLAG_NEW_MULTIUSER_SETTINGS_UX,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda20
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).newMultiuserSettingsUx();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean reorderWallpaperDuringUserSwitch() {
        return getValue(
                Flags.FLAG_REORDER_WALLPAPER_DURING_USER_SWITCH,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda29
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reorderWallpaperDuringUserSwitch();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean restrictQuietModeCredentialBugFixToManagedProfiles() {
        return getValue(
                Flags.FLAG_RESTRICT_QUIET_MODE_CREDENTIAL_BUG_FIX_TO_MANAGED_PROFILES,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .restrictQuietModeCredentialBugFixToManagedProfiles();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean saveGlobalAndGuestRestrictionsOnSystemUserXml() {
        return getValue(
                Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda31
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).saveGlobalAndGuestRestrictionsOnSystemUserXml();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly() {
        return getValue(
                Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML_READ_ONLY,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda30
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean scheduleStopOfBackgroundUser() {
        return getValue(
                Flags.FLAG_SCHEDULE_STOP_OF_BACKGROUND_USER,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).scheduleStopOfBackgroundUser();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean setPowerModeDuringUserSwitch() {
        return getValue(
                Flags.FLAG_SET_POWER_MODE_DURING_USER_SWITCH,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).setPowerModeDuringUserSwitch();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean showSetScreenLockDialog() {
        return getValue(
                Flags.FLAG_SHOW_SET_SCREEN_LOCK_DIALOG,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).showSetScreenLockDialog();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean startUserBeforeScheduledAlarms() {
        return getValue(
                Flags.FLAG_START_USER_BEFORE_SCHEDULED_ALARMS,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda33
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).startUserBeforeScheduledAlarms();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean supportAutolockForPrivateSpace() {
        return getValue(
                Flags.FLAG_SUPPORT_AUTOLOCK_FOR_PRIVATE_SPACE,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda24
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).supportAutolockForPrivateSpace();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean supportCommunalProfile() {
        return getValue(
                Flags.FLAG_SUPPORT_COMMUNAL_PROFILE,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda25
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).supportCommunalProfile();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean supportCommunalProfileNextgen() {
        return getValue(
                Flags.FLAG_SUPPORT_COMMUNAL_PROFILE_NEXTGEN,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda26
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).supportCommunalProfileNextgen();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean useAllCpusDuringUserSwitch() {
        return getValue(
                Flags.FLAG_USE_ALL_CPUS_DURING_USER_SWITCH,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda21
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useAllCpusDuringUserSwitch();
                    }
                });
    }

    @Override // android.multiuser.FeatureFlags
    public boolean usePrivateSpaceIconInBiometricPrompt() {
        return getValue(
                Flags.FLAG_USE_PRIVATE_SPACE_ICON_IN_BIOMETRIC_PROMPT,
                new Predicate() { // from class:
                                  // android.multiuser.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).usePrivateSpaceIconInBiometricPrompt();
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
                Flags.FLAG_ADD_UI_FOR_SOUNDS_FROM_BACKGROUND_USERS,
                Flags.FLAG_ALLOW_MAIN_USER_TO_ACCESS_BLOCKED_NUMBER_PROVIDER,
                Flags.FLAG_ALLOW_RESOLVER_SHEET_FOR_PRIVATE_SPACE,
                Flags.FLAG_AVATAR_SYNC,
                Flags.FLAG_BIND_WALLPAPER_SERVICE_ON_ITS_OWN_THREAD_DURING_A_USER_SWITCH,
                Flags.FLAG_BLOCK_PRIVATE_SPACE_CREATION,
                Flags.FLAG_DELETE_PRIVATE_SPACE_FROM_RESET,
                Flags.FLAG_DISABLE_PRIVATE_SPACE_ITEMS_ON_HOME,
                Flags.FLAG_ENABLE_BIOMETRICS_TO_UNLOCK_PRIVATE_SPACE,
                Flags.FLAG_ENABLE_HIDING_PROFILES,
                Flags.FLAG_ENABLE_LAUNCHER_APPS_HIDDEN_PROFILE_CHECKS,
                Flags.FLAG_ENABLE_PERMISSION_TO_ACCESS_HIDDEN_PROFILES,
                Flags.FLAG_ENABLE_PRIVATE_SPACE_AUTOLOCK_ON_RESTARTS,
                Flags.FLAG_ENABLE_PRIVATE_SPACE_FEATURES,
                Flags.FLAG_ENABLE_PRIVATE_SPACE_INTENT_REDIRECTION,
                Flags.FLAG_ENABLE_PS_SENSITIVE_NOTIFICATIONS_TOGGLE,
                Flags.FLAG_ENABLE_SYSTEM_USER_ONLY_FOR_SERVICES_AND_PROVIDERS,
                Flags.FLAG_FIX_AVATAR_CONCURRENT_FILE_WRITE,
                Flags.FLAG_FIX_AVATAR_PICKER_READ_BACK_ORDER,
                Flags.FLAG_FIX_AVATAR_PICKER_SELECTED_READ_BACK,
                Flags.FLAG_HANDLE_INTERLEAVED_SETTINGS_FOR_PRIVATE_SPACE,
                Flags.FLAG_MOVE_QUIET_MODE_OPERATIONS_TO_SEPARATE_THREAD,
                Flags.FLAG_NEW_MULTIUSER_SETTINGS_UX,
                Flags.FLAG_REORDER_WALLPAPER_DURING_USER_SWITCH,
                Flags.FLAG_RESTRICT_QUIET_MODE_CREDENTIAL_BUG_FIX_TO_MANAGED_PROFILES,
                Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML,
                Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML_READ_ONLY,
                Flags.FLAG_SCHEDULE_STOP_OF_BACKGROUND_USER,
                Flags.FLAG_SET_POWER_MODE_DURING_USER_SWITCH,
                Flags.FLAG_SHOW_SET_SCREEN_LOCK_DIALOG,
                Flags.FLAG_START_USER_BEFORE_SCHEDULED_ALARMS,
                Flags.FLAG_SUPPORT_AUTOLOCK_FOR_PRIVATE_SPACE,
                Flags.FLAG_SUPPORT_COMMUNAL_PROFILE,
                Flags.FLAG_SUPPORT_COMMUNAL_PROFILE_NEXTGEN,
                Flags.FLAG_USE_ALL_CPUS_DURING_USER_SWITCH,
                Flags.FLAG_USE_PRIVATE_SPACE_ICON_IN_BIOMETRIC_PROMPT);
    }
}
