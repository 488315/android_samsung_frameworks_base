package android.app.admin;

import android.app.admin.SecurityLog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.os.UserManager;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public abstract class DevicePolicyManagerInternal {

    public interface OnCrossProfileWidgetProvidersChangeListener {
        void onCrossProfileWidgetProvidersChanged(int i, List<String> list);
    }

    public abstract void addOnCrossProfileWidgetProvidersChangeListener(OnCrossProfileWidgetProvidersChangeListener onCrossProfileWidgetProvidersChangeListener);

    public abstract void broadcastIntentToManifestReceivers(Intent intent, UserHandle userHandle, boolean z);

    public abstract boolean canSilentlyInstallPackage(String str, int i);

    public abstract boolean clearResetPasswordTokenMDM(ComponentName componentName, int i);

    public abstract Intent createShowAdminSupportIntent(int i, boolean z);

    public abstract Intent createUserRestrictionSupportIntent(int i, String str);

    public abstract void enforceAuditLoggingPolicy(boolean z);

    public abstract void enforcePermission(String str, String str2, int i);

    public abstract void enforceSecurityLoggingPolicy(boolean z);

    public abstract List<String> getAllCrossProfilePackages(int i);

    public abstract Bundle getApplicationRestrictionsMDM(ComponentName componentName, String str, int i);

    public abstract List<Bundle> getApplicationRestrictionsPerAdminForUser(String str, int i);

    public abstract List<String> getCrossProfileWidgetProviders(int i);

    public abstract int getDOMinimumRequiredWifiSecurityLevel();

    public abstract WifiSsidPolicy getDOWifiSsidPolicy();

    public abstract List<String> getDefaultCrossProfilePackages();

    public abstract Map getDelegatedPackages(int i);

    @Deprecated
    public abstract ComponentName getDeviceOwnerComponent(boolean z);

    public abstract int getDeviceOwnerUserId();

    protected abstract DevicePolicyCache getDevicePolicyCache();

    protected abstract DeviceStateCache getDeviceStateCache();

    public abstract CharSequence getPrintingDisabledReasonForUser(int i);

    public abstract ComponentName getProfileOwnerAsUser(int i);

    public abstract ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle userHandle);

    public abstract List<UserManager.EnforcingUser> getUserRestrictionSources(String str, int i);

    public abstract boolean hasDelegatedPermission(String str, int i, String str2);

    public abstract boolean hasPermission(String str, String str2, int i);

    public abstract boolean isActiveDeviceOwner(int i);

    public abstract boolean isActiveProfileOwner(int i);

    public abstract boolean isActiveSupervisionApp(int i);

    public abstract boolean isDeviceOrProfileOwnerInCallingUser(String str);

    public abstract boolean isResetPasswordTokenActiveMDM(ComponentName componentName, int i);

    public abstract boolean isUserAffiliatedWithDevice(int i);

    public abstract boolean isUserOrganizationManaged(int i);

    public abstract void notifyChangesOnWifiPolicy();

    public abstract void reportSeparateProfileChallengeChanged(int i);

    public abstract void resetOp(int i, String str, int i2);

    public abstract boolean resetPasswordWithTokenMDM(ComponentName componentName, String str, byte[] bArr, int i, int i2);

    public abstract void setApplicationRestrictionsMDM(ComponentName componentName, String str, Bundle bundle, int i);

    public abstract void setInternalEventsCallback(Consumer<List<SecurityLog.SecurityEvent>> consumer);

    public abstract void setKeyguardDisabledFeaturesMDM(ComponentName componentName, int i, int i2);

    public abstract void setMaximumFailedPasswordsForWipeMDM(ComponentName componentName, int i, int i2);

    public abstract void setMaximumTimeToLockMDM(ComponentName componentName, long j, int i);

    public abstract void setMinimumRequiredWifiSecurityLevel(ComponentName componentName, int i, int i2);

    public abstract void setPasswordExpirationTimeoutMDM(ComponentName componentName, long j, int i);

    public abstract void setPasswordHistoryLengthMDM(ComponentName componentName, int i, int i2);

    public abstract void setPasswordMinimumLengthMDM(ComponentName componentName, int i, int i2);

    public abstract void setPasswordMinimumLettersMDM(ComponentName componentName, int i, int i2);

    public abstract void setPasswordMinimumLowerCaseMDM(ComponentName componentName, int i, int i2);

    public abstract void setPasswordMinimumNonLetterMDM(ComponentName componentName, int i, int i2);

    public abstract void setPasswordMinimumNumericMDM(ComponentName componentName, int i, int i2);

    public abstract void setPasswordMinimumSymbolsMDM(ComponentName componentName, int i, int i2);

    public abstract void setPasswordMinimumUpperCaseMDM(ComponentName componentName, int i, int i2);

    public abstract void setPasswordQualityMDM(ComponentName componentName, int i, int i2);

    public abstract boolean setResetPasswordTokenMDM(ComponentName componentName, byte[] bArr, int i);

    public abstract void setTrustAgentConfigurationMDM(int i, ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle);

    public abstract void setWifiSsidPolicy(ComponentName componentName, WifiSsidPolicy wifiSsidPolicy, int i);

    public abstract boolean supportsResetOp(int i);
}
