package com.android.keyguard;

import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.plugins.clocks.WeatherData;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.samsung.android.cover.CoverState;
import java.util.TimeZone;

public class KeyguardUpdateMonitorCallback {
    public void onRefreshBatteryInfo(BatteryStatus batteryStatus) {
    }

    public void onRefreshBatteryInfo(KeyguardBatteryStatus keyguardBatteryStatus) {
    }

    public void onDevicePolicyManagerStateChanged() {
    }

    public void onDeviceProvisioned() {
    }

    public void onEmergencyCallAction() {
    }

    public void onFacesCleared() {
    }

    public void onFailedUnlockAttemptChanged() {
    }

    public void onFingerprintsCleared() {
    }

    public void onKeyguardDismissAnimationFinished() {
    }

    public void onKeyguardGoingAway() {
    }

    public void onLocaleChanged() {
    }

    public void onLockModeChanged() {
    }

    public void onLogoutEnabledChanged() {
    }

    public void onOfflineStateChanged() {
    }

    public void onOwnerInfoChanged() {
    }

    public void onRemoteLockInfoChanged() {
    }

    public void onRequireUnlockForNfc() {
    }

    @Deprecated
    public void onStartedWakingUp() {
    }

    public void onSystemDialogsShowing() {
    }

    public void onTimeChanged() {
    }

    public void onUdfpsFingerDown() {
    }

    public void onUdfpsFingerUp() {
    }

    public void onUnlocking() {
    }

    public void onUserUnlocked() {
    }

    public void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
    }

    public void onBiometricEnrollmentStateChanged(BiometricSourceType biometricSourceType) {
    }

    public void onBiometricLockoutChanged(boolean z) {
    }

    public void onDlsViewModeChanged(int i) {
    }

    public void onDreamingStateChanged(boolean z) {
    }

    public void onDualDARInnerLockscreenRequirementChanged(int i) {
    }

    public void onEmergencyStateChanged(int i) {
    }

    public void onEnabledTrustAgentsChanged(int i) {
    }

    public void onFaceWidgetFullscreenModeChanged(boolean z) {
    }

    @Deprecated
    public void onFinishedGoingToSleep(int i) {
    }

    public void onForceIsDismissibleChanged(boolean z) {
    }

    public void onKeyguardBouncerFullyShowingChanged(boolean z) {
    }

    public void onKeyguardBouncerStateChanged(boolean z) {
    }

    public void onKeyguardVisibilityChanged(boolean z) {
    }

    public void onLockDisabledChanged(boolean z) {
    }

    public void onLockedOutStateChanged(BiometricSourceType biometricSourceType) {
    }

    public void onNonStrongBiometricAllowedChanged(int i) {
    }

    public void onPackageAdded(String str) {
    }

    public void onPackageChanged(String str) {
    }

    public void onPackageDataCleared(String str) {
    }

    public void onPhoneStateChanged(int i) {
    }

    public void onPrimaryBouncerVisibilityChanged(boolean z) {
    }

    public void onRefreshCarrierInfo(Intent intent) {
    }

    public void onSecondaryLockscreenRequirementChanged(int i) {
    }

    public void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
    }

    public void onShadeExpandedChanged(boolean z) {
    }

    public void onSimulationFailToUnlock(int i) {
    }

    @Deprecated
    public void onStartedGoingToSleep(int i) {
    }

    public void onStrongAuthStateChanged(int i) {
    }

    public void onTableModeChanged(boolean z) {
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

    public void onUSBRestrictionChanged(boolean z) {
    }

    public void onUpdateCoverState(CoverState coverState) {
    }

    public void onUserSwitchComplete(int i) {
    }

    public void onUserSwitching(int i) {
    }

    public void onWeatherDataChanged(WeatherData weatherData) {
    }

    public void onBiometricAcquired(BiometricSourceType biometricSourceType, int i) {
    }

    public void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
    }

    public void onDualDarInnerLockScreenStateChanged(int i, boolean z) {
    }

    public void onPackageRemoved(String str, boolean z) {
    }

    public void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
    }

    public void onBiometricDetected(int i, BiometricSourceType biometricSourceType, boolean z) {
    }

    public void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
    }

    public void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
    }

    public void onSimStateChanged(int i, int i2, int i3) {
    }

    public void onTrustGrantedForCurrentUser(boolean z, boolean z2, TrustGrantFlags trustGrantFlags, String str) {
    }
}
