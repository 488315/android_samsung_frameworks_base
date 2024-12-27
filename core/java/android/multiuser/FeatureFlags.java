package android.multiuser;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean addUiForSoundsFromBackgroundUsers();

    boolean allowMainUserToAccessBlockedNumberProvider();

    boolean allowResolverSheetForPrivateSpace();

    boolean avatarSync();

    boolean bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch();

    boolean blockPrivateSpaceCreation();

    boolean deletePrivateSpaceFromReset();

    boolean disablePrivateSpaceItemsOnHome();

    boolean enableBiometricsToUnlockPrivateSpace();

    boolean enableHidingProfiles();

    boolean enableLauncherAppsHiddenProfileChecks();

    boolean enablePermissionToAccessHiddenProfiles();

    boolean enablePrivateSpaceAutolockOnRestarts();

    boolean enablePrivateSpaceFeatures();

    boolean enablePrivateSpaceIntentRedirection();

    boolean enablePsSensitiveNotificationsToggle();

    boolean enableSystemUserOnlyForServicesAndProviders();

    boolean fixAvatarConcurrentFileWrite();

    boolean fixAvatarPickerReadBackOrder();

    boolean fixAvatarPickerSelectedReadBack();

    boolean handleInterleavedSettingsForPrivateSpace();

    boolean moveQuietModeOperationsToSeparateThread();

    boolean newMultiuserSettingsUx();

    boolean reorderWallpaperDuringUserSwitch();

    boolean restrictQuietModeCredentialBugFixToManagedProfiles();

    boolean saveGlobalAndGuestRestrictionsOnSystemUserXml();

    boolean saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly();

    boolean scheduleStopOfBackgroundUser();

    boolean setPowerModeDuringUserSwitch();

    boolean showSetScreenLockDialog();

    boolean startUserBeforeScheduledAlarms();

    boolean supportAutolockForPrivateSpace();

    boolean supportCommunalProfile();

    boolean supportCommunalProfileNextgen();

    boolean useAllCpusDuringUserSwitch();

    boolean usePrivateSpaceIconInBiometricPrompt();
}
