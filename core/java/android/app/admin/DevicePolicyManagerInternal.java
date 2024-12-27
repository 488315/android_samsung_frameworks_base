package android.app.admin;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import java.util.List;

/* loaded from: classes.dex */
public abstract class DevicePolicyManagerInternal {

    public interface OnCrossProfileWidgetProvidersChangeListener {
        void onCrossProfileWidgetProvidersChanged(int i, List<String> list);
    }

    public abstract void addOnCrossProfileWidgetProvidersChangeListener(OnCrossProfileWidgetProvidersChangeListener onCrossProfileWidgetProvidersChangeListener);

    public abstract void broadcastIntentToManifestReceivers(Intent intent, UserHandle userHandle, boolean z);

    public abstract boolean canSilentlyInstallPackage(String str, int i);

    public abstract Intent createShowAdminSupportIntent(int i, boolean z);

    public abstract Intent createUserRestrictionSupportIntent(int i, String str);

    public abstract void enforceAuditLoggingPolicy(boolean z);

    public abstract void enforcePermission(String str, String str2, int i);

    public abstract void enforceSecurityLoggingPolicy(boolean z);

    public abstract List<String> getAllCrossProfilePackages(int i);

    public abstract List<Bundle> getApplicationRestrictionsPerAdminForUser(String str, int i);

    public abstract List<String> getCrossProfileWidgetProviders(int i);

    public abstract List<String> getDefaultCrossProfilePackages();

    @Deprecated
    public abstract ComponentName getDeviceOwnerComponent(boolean z);

    public abstract int getDeviceOwnerUserId();

    protected abstract DevicePolicyCache getDevicePolicyCache();

    protected abstract DeviceStateCache getDeviceStateCache();

    public abstract CharSequence getPrintingDisabledReasonForUser(int i);

    public abstract ComponentName getProfileOwnerAsUser(int i);

    public abstract ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle userHandle);

    public abstract List<UserManager.EnforcingUser> getUserRestrictionSources(String str, int i);

    public abstract boolean hasPermission(String str, String str2, int i);

    public abstract boolean isActiveDeviceOwner(int i);

    public abstract boolean isActiveProfileOwner(int i);

    public abstract boolean isActiveSupervisionApp(int i);

    public abstract boolean isDeviceOrProfileOwnerInCallingUser(String str);

    public abstract boolean isUserAffiliatedWithDevice(int i);

    public abstract boolean isUserOrganizationManaged(int i);

    public abstract void notifyChangesOnWifiPolicy();

    public abstract void reportSeparateProfileChallengeChanged(int i);

    public abstract void resetOp(int i, String str, int i2);

    public abstract void setMinimumRequiredWifiSecurityLevel(ComponentName componentName, int i, int i2);

    public abstract void setWifiSsidPolicy(ComponentName componentName, WifiSsidPolicy wifiSsidPolicy, int i);

    public abstract boolean supportsResetOp(int i);
}
