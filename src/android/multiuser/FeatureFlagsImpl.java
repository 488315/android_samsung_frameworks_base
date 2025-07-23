package android.multiuser;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.multiuser.FeatureFlags
    public boolean addLauncherUserConfig() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean addUiForSoundsFromBackgroundUsers() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean allowMainUserToAccessBlockedNumberProvider() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean allowResolverSheetForPrivateSpace() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean allowSupervisingProfile() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean avatarSync() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean blockPrivateSpaceCreation() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheProfileIdsReadOnly() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheProfileParentReadOnly() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheProfileTypeReadOnly() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheProfilesReadOnly() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheQuietModeState() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserInfoReadOnly() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserPropertiesCorrectlyReadOnly() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserRestrictionsReadOnly() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserSerialNumber() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserSerialNumberReadOnly() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserStartRealtimeReadOnly() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cacheUserUnlockRealtimeReadOnly() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cachesNotInvalidatedAtStartReadOnly() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean cachingDevelopmentImprovements() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean deletePrivateSpaceFromReset() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean disablePrivateSpaceItemsOnHome() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableBiometricsToUnlockPrivateSpace() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableHidingProfiles() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableLauncherAppsHiddenProfileChecks() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableMovingContentIntoPrivateSpace() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePermissionToAccessHiddenProfiles() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePrivateSpaceAutolockOnRestarts() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePrivateSpaceFeatures() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePrivateSpaceIntentRedirection() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePsSensitiveNotificationsToggle() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableSystemUserOnlyForServicesAndProviders() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarConcurrentFileWrite() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarContentProviderNullAuthority() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarCrossUserLeak() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarPickerNotRespondingForNewUser() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarPickerReadBackOrder() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixAvatarPickerSelectedReadBack() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixDisablingOfMuToggleWhenRestrictionApplied() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixGetUserPropertyCache() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean fixLargeDisplayPrivateSpaceSettings() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean getUserSwitchabilityPermission() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean handleInterleavedSettingsForPrivateSpace() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean ignoreRestrictionsWhenDeletingPrivateProfile() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean invalidateCacheOnUsersChangedReadOnly() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean logoutUserApi() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean modifyPrivateSpaceSecondaryUnlockSetupFlow() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean moveQuietModeOperationsToSeparateThread() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean multipleAlarmNotificationsSupport() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean multiuserWidget() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean newMultiuserSettingsUx() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean placeAddUserDialogWithinActivity() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean privateSpaceSearchIllustrationConfig() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean profilesForAll() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean propertyInvalidatedCacheBypassMismatchedUids() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean reorderWallpaperDuringUserSwitch() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean requirePinBeforeUserDeletion() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean restrictQuietModeCredentialBugFixToManagedProfiles() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean saveGlobalAndGuestRestrictionsOnSystemUserXml() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean scheduleStopOfBackgroundUser() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean setPowerModeDuringUserSwitch() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean showCustomUnlockTitleInsidePrivateProfile() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean showDifferentCreationErrorForUnsupportedDevices() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean showSetScreenLockDialog() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean stopPreviousUserApps() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean supportAutolockForPrivateSpace() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean supportCommunalProfile() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean supportCommunalProfileNextgen() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean unicornModeRefactoringForHsumReadOnly() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean useAllCpusDuringUserSwitch() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean usePrivateSpaceIconInBiometricPrompt() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean useUnifiedResources() {
        return false;
    }
}
