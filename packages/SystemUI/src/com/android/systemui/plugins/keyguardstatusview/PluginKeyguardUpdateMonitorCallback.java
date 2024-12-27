package com.android.systemui.plugins.keyguardstatusview;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import com.android.systemui.plugins.annotations.VersionCheck;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.TimeZone;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface PluginKeyguardUpdateMonitorCallback {
    void dismissFaceWidgetFullScreen();

    void onBiometricAcquired();

    void onBiometricAuthFailed();

    void onBiometricAuthenticated(int i, boolean z);

    void onBiometricError(int i, String str);

    void onBiometricHelp(int i, String str);

    void onBiometricLockoutChanged(boolean z);

    void onBiometricRunningStateChanged(boolean z);

    void onBootCompleted();

    void onClockVisibilityChanged();

    void onConfigurationChanged(Configuration configuration);

    void onDevicePolicyManagerStateChanged();

    void onDeviceProvisioned();

    void onDexModeChanged(boolean z);

    void onDlsViewModeChanged(int i);

    void onDreamingStateChanged(boolean z);

    void onEmergencyCallAction();

    void onEmergencyStateChanged(int i);

    void onFaceUnlockStateChanged(boolean z, int i);

    void onFailedUnlockAttemptChanged();

    void onFinishedGoingToSleep(int i);

    void onHasLockscreenWallpaperChanged(boolean z);

    void onICCCardStateChanged(String str);

    void onKeyguardBouncerFullyShowingChanged(boolean z);

    void onKeyguardBouncerStateChanged(boolean z);

    void onKeyguardVisibilityChanged(boolean z);

    void onLocaleChanged();

    @VersionCheck(version = 2013)
    void onLockDisabledChanged();

    void onLockModeChanged();

    void onLogoutEnabledChanged();

    void onNotifyKeyguardLockout();

    void onOpenThemeChangeStarted();

    void onOpenThemeChanged();

    void onOpenThemeReApply();

    void onPackageAdded(String str);

    void onPackageChanged(String str);

    void onPackageDataCleared(String str);

    void onPackageRemoved(String str, boolean z);

    void onPhoneStateChanged(int i);

    @VersionCheck(version = VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403)
    void onPrimaryBouncerVisibilityChanged(boolean z);

    void onRefreshBatteryInfo();

    void onRefreshCarrierInfo();

    void onRemoteLockInfoChanged();

    void onRingerModeChanged(int i);

    void onScreenTurnedOff();

    void onScreenTurnedOn();

    void onSecurityModeChanged(boolean z);

    void onSecurityViewChanged();

    void onSetBackground(Bitmap bitmap);

    void onShadeExpandedChanged(boolean z);

    void onSimStateChanged(int i, int i2, int i3);

    void onSimulationUnlock(int i);

    void onStartedGoingToSleep(int i);

    void onStartedWakingUp();

    void onStrongAuthStateChanged(int i);

    void onSystemDialogsShowing();

    void onTelephonyCapable(boolean z);

    void onTimeChanged();

    void onTimeZoneChanged(TimeZone timeZone);

    void onTrustAgentErrorMessage(CharSequence charSequence);

    void onTrustChanged(int i);

    void onTrustGrantedWithFlags(int i, int i2);

    void onTrustManagedChanged(int i);

    void onUpdateCoverState(boolean z);

    @VersionCheck(version = 1032)
    void onUpdateFaceWidgetMediaOutputRemoteViews(Intent intent);

    void onUserInfoChanged(int i);

    void onUserSwitchComplete(int i);

    void onUserSwitching(int i);

    void onUserUnlocked();

    void showBackgroundAuthToast(int i);

    void updateBackgroundAuthToast(boolean z, int i);
}
