package com.android.keyguard;

import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.plugins.WeatherData;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.samsung.android.cover.CoverState;
import java.util.TimeZone;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardUpdateMonitorCallback {
    public void onRefreshBatteryInfo() {
    }

    public /* bridge */ /* synthetic */ void onRefreshBatteryInfo(KeyguardBatteryStatus keyguardBatteryStatus) {
    }

    public void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
    }

    public /* bridge */ /* synthetic */ void onBiometricLockoutChanged(boolean z) {
    }

    public /* bridge */ /* synthetic */ void onDlsViewModeChanged(int i) {
    }

    public void onDreamingStateChanged(boolean z) {
    }

    public /* bridge */ /* synthetic */ void onDualDARInnerLockscreenRequirementChanged(int i) {
    }

    public /* bridge */ /* synthetic */ void onDualDarInnerLockScreenStateChanged(boolean z) {
    }

    public /* bridge */ /* synthetic */ void onEmergencyStateChanged(int i) {
    }

    public void onEnabledTrustAgentsChanged(int i) {
    }

    public void onFinishedGoingToSleep(int i) {
    }

    public void onKeyguardBouncerFullyShowingChanged(boolean z) {
    }

    public void onKeyguardBouncerStateChanged(boolean z) {
    }

    public void onKeyguardVisibilityChanged(boolean z) {
    }

    public /* bridge */ /* synthetic */ void onLockDisabledChanged(boolean z) {
    }

    public void onLockedOutStateChanged(BiometricSourceType biometricSourceType) {
    }

    public /* bridge */ /* synthetic */ void onPackageAdded(String str) {
    }

    public /* bridge */ /* synthetic */ void onPackageChanged(String str) {
    }

    public /* bridge */ /* synthetic */ void onPackageDataCleared(String str) {
    }

    public void onPhoneStateChanged(int i) {
    }

    public /* bridge */ /* synthetic */ void onPrimaryBouncerVisibilityChanged(boolean z) {
    }

    public void onRefreshCarrierInfo(Intent intent) {
    }

    public void onSecondaryLockscreenRequirementChanged(int i) {
    }

    public /* bridge */ /* synthetic */ void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
    }

    public void onShadeExpandedChanged(boolean z) {
    }

    public /* bridge */ /* synthetic */ void onSimulationFailToUnlock(int i) {
    }

    public void onStartedGoingToSleep(int i) {
    }

    public void onStrongAuthStateChanged(int i) {
    }

    public /* bridge */ /* synthetic */ void onTableModeChanged(boolean z) {
    }

    public void onTelephonyCapable(boolean z) {
    }

    public void onTimeFormatChanged(String str) {
    }

    public void onTimeZoneChanged(TimeZone timeZone) {
    }

    public void onTrustAgentErrorMessage(CharSequence charSequence) {
    }

    public void onTrustChanged(int i) {
    }

    public void onTrustManagedChanged(int i) {
    }

    public /* bridge */ /* synthetic */ void onUSBRestrictionChanged(boolean z) {
    }

    public /* bridge */ /* synthetic */ void onUpdateCoverState(CoverState coverState) {
    }

    public void onUserSwitchComplete(int i) {
    }

    public void onUserSwitching(int i) {
    }

    public void onWeatherDataChanged(WeatherData weatherData) {
    }

    public void onBiometricDetected() {
    }

    public void onBiometricsCleared() {
    }

    public void onDevicePolicyManagerStateChanged() {
    }

    public void onDeviceProvisioned() {
    }

    public void onEmergencyCallAction() {
    }

    public /* bridge */ /* synthetic */ void onFailedUnlockAttemptChanged() {
    }

    public void onKeyguardDismissAnimationFinished() {
    }

    public void onKeyguardGoingAway() {
    }

    public /* bridge */ /* synthetic */ void onLocaleChanged() {
    }

    public /* bridge */ /* synthetic */ void onLockModeChanged() {
    }

    public void onLogoutEnabledChanged() {
    }

    public /* bridge */ /* synthetic */ void onOfflineStateChanged() {
    }

    public /* bridge */ /* synthetic */ void onOwnerInfoChanged() {
    }

    public /* bridge */ /* synthetic */ void onRemoteLockInfoChanged() {
    }

    public void onRequireUnlockForNfc() {
    }

    public void onStartedWakingUp() {
    }

    public /* bridge */ /* synthetic */ void onSystemDialogsShowing() {
    }

    public void onTimeChanged() {
    }

    public /* bridge */ /* synthetic */ void onUdfpsFingerDown$1() {
    }

    public /* bridge */ /* synthetic */ void onUdfpsFingerUp$1() {
    }

    public /* bridge */ /* synthetic */ void onUnlocking() {
    }

    public void onUserUnlocked() {
    }

    public /* bridge */ /* synthetic */ void onWallpaperTypeChanged() {
    }

    public /* bridge */ /* synthetic */ void onBackDropViewShowing(boolean z, boolean z2) {
    }

    public void onBiometricAcquired(BiometricSourceType biometricSourceType, int i) {
    }

    public void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
    }

    public /* bridge */ /* synthetic */ void onPackageRemoved(String str, boolean z) {
    }

    public void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
    }

    public void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
    }

    public void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
    }

    public void onSimStateChanged(int i, int i2, int i3) {
    }

    public void onTrustGrantedForCurrentUser(boolean z, TrustGrantFlags trustGrantFlags, String str) {
    }
}
