package android.multiuser;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean addLauncherUserConfig();

    boolean addUiForSoundsFromBackgroundUsers();

    boolean allowMainUserToAccessBlockedNumberProvider();

    boolean allowResolverSheetForPrivateSpace();

    boolean allowSupervisingProfile();

    boolean avatarSync();

    boolean bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch();

    boolean blockPrivateSpaceCreation();

    boolean cacheProfileIdsReadOnly();

    boolean cacheProfileParentReadOnly();

    boolean cacheProfileTypeReadOnly();

    boolean cacheProfilesReadOnly();

    boolean cacheQuietModeState();

    boolean cacheUserInfoReadOnly();

    boolean cacheUserPropertiesCorrectlyReadOnly();

    boolean cacheUserRestrictionsReadOnly();

    boolean cacheUserSerialNumber();

    boolean cacheUserSerialNumberReadOnly();

    boolean cacheUserStartRealtimeReadOnly();

    boolean cacheUserUnlockRealtimeReadOnly();

    boolean cachesNotInvalidatedAtStartReadOnly();

    boolean cachingDevelopmentImprovements();

    boolean deletePrivateSpaceFromReset();

    boolean disablePrivateSpaceItemsOnHome();

    boolean enableBiometricsToUnlockPrivateSpace();

    boolean enableHidingProfiles();

    boolean enableLauncherAppsHiddenProfileChecks();

    boolean enableMovingContentIntoPrivateSpace();

    boolean enablePermissionToAccessHiddenProfiles();

    boolean enablePrivateSpaceAutolockOnRestarts();

    boolean enablePrivateSpaceFeatures();

    boolean enablePrivateSpaceIntentRedirection();

    boolean enablePsSensitiveNotificationsToggle();

    boolean enableSystemUserOnlyForServicesAndProviders();

    boolean fixAvatarConcurrentFileWrite();

    boolean fixAvatarContentProviderNullAuthority();

    boolean fixAvatarCrossUserLeak();

    boolean fixAvatarPickerNotRespondingForNewUser();

    boolean fixAvatarPickerReadBackOrder();

    boolean fixAvatarPickerSelectedReadBack();

    boolean fixDisablingOfMuToggleWhenRestrictionApplied();

    boolean fixGetUserPropertyCache();

    boolean fixLargeDisplayPrivateSpaceSettings();

    boolean getUserSwitchabilityPermission();

    boolean handleInterleavedSettingsForPrivateSpace();

    boolean ignoreRestrictionsWhenDeletingPrivateProfile();

    boolean invalidateCacheOnUsersChangedReadOnly();

    boolean logoutUserApi();

    boolean modifyPrivateSpaceSecondaryUnlockSetupFlow();

    boolean moveQuietModeOperationsToSeparateThread();

    boolean multipleAlarmNotificationsSupport();

    boolean multiuserWidget();

    boolean newMultiuserSettingsUx();

    boolean placeAddUserDialogWithinActivity();

    boolean privateSpaceSearchIllustrationConfig();

    boolean profilesForAll();

    boolean propertyInvalidatedCacheBypassMismatchedUids();

    boolean reorderWallpaperDuringUserSwitch();

    boolean requirePinBeforeUserDeletion();

    boolean restrictQuietModeCredentialBugFixToManagedProfiles();

    boolean saveGlobalAndGuestRestrictionsOnSystemUserXml();

    boolean saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly();

    boolean scheduleStopOfBackgroundUser();

    boolean setPowerModeDuringUserSwitch();

    boolean showCustomUnlockTitleInsidePrivateProfile();

    boolean showDifferentCreationErrorForUnsupportedDevices();

    boolean showSetScreenLockDialog();

    boolean stopPreviousUserApps();

    boolean supportAutolockForPrivateSpace();

    boolean supportCommunalProfile();

    boolean supportCommunalProfileNextgen();

    boolean unicornModeRefactoringForHsumReadOnly();

    boolean useAllCpusDuringUserSwitch();

    boolean usePrivateSpaceIconInBiometricPrompt();

    boolean useUnifiedResources();
}
