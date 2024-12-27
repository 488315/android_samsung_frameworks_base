package android.multiuser;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.multiuser.FeatureFlags
    public boolean addUiForSoundsFromBackgroundUsers() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean allowMainUserToAccessBlockedNumberProvider() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean allowResolverSheetForPrivateSpace() {
        return true;
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
        return false;
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
    public boolean handleInterleavedSettingsForPrivateSpace() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean moveQuietModeOperationsToSeparateThread() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean newMultiuserSettingsUx() {
        return false;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean reorderWallpaperDuringUserSwitch() {
        return true;
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
        return false;
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
    public boolean showSetScreenLockDialog() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean startUserBeforeScheduledAlarms() {
        return false;
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
    public boolean useAllCpusDuringUserSwitch() {
        return true;
    }

    @Override // android.multiuser.FeatureFlags
    public boolean usePrivateSpaceIconInBiometricPrompt() {
        return true;
    }
}
