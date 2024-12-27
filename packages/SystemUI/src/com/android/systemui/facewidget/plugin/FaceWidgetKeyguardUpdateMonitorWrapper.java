package com.android.systemui.facewidget.plugin;

import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitorCallback;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.samsung.android.cover.CoverState;
import java.util.HashMap;
import java.util.TimeZone;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FaceWidgetKeyguardUpdateMonitorWrapper implements PluginKeyguardUpdateMonitor {
    public final HashMap mCallbackWrapperMap = new HashMap();
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final SelectedUserInteractor mSelectedUserInteractor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class FaceWidgetKeyguardUpdateMonitorCallbackWrapper extends KeyguardUpdateMonitorCallback {
        public final PluginKeyguardUpdateMonitorCallback mCallback;

        public FaceWidgetKeyguardUpdateMonitorCallbackWrapper(PluginKeyguardUpdateMonitorCallback pluginKeyguardUpdateMonitorCallback) {
            this.mCallback = pluginKeyguardUpdateMonitorCallback;
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
            this.mCallback.onBiometricAuthFailed();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
            this.mCallback.onBiometricAuthenticated(i, z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
            this.mCallback.onBiometricError(i, str);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
            this.mCallback.onBiometricHelp(i, str);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricLockoutChanged(boolean z) {
            this.mCallback.onBiometricLockoutChanged(z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
            this.mCallback.onBiometricRunningStateChanged(z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onDeviceProvisioned() {
            this.mCallback.onDeviceProvisioned();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onDreamingStateChanged(boolean z) {
            this.mCallback.onDreamingStateChanged(z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onEmergencyCallAction() {
            this.mCallback.onEmergencyCallAction();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onEmergencyStateChanged(int i) {
            this.mCallback.onEmergencyStateChanged(i);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onFailedUnlockAttemptChanged() {
            this.mCallback.onFailedUnlockAttemptChanged();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onFinishedGoingToSleep(int i) {
            this.mCallback.onFinishedGoingToSleep(i);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
            this.mCallback.onKeyguardBouncerFullyShowingChanged(z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardVisibilityChanged(boolean z) {
            this.mCallback.onKeyguardVisibilityChanged(z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLocaleChanged() {
            this.mCallback.onLocaleChanged();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLockDisabledChanged(boolean z) {
            try {
                this.mCallback.onLockDisabledChanged();
            } catch (AbstractMethodError unused) {
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLockModeChanged() {
            this.mCallback.onLockModeChanged();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLogoutEnabledChanged() {
            this.mCallback.onLogoutEnabledChanged();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPackageAdded(String str) {
            this.mCallback.onPackageAdded(str);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPackageChanged(String str) {
            this.mCallback.onPackageChanged(str);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPackageDataCleared(String str) {
            this.mCallback.onPackageDataCleared(str);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPackageRemoved(String str, boolean z) {
            this.mCallback.onPackageRemoved(str, z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPhoneStateChanged(int i) {
            this.mCallback.onPhoneStateChanged(i);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPrimaryBouncerVisibilityChanged(boolean z) {
            this.mCallback.onPrimaryBouncerVisibilityChanged(z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRefreshBatteryInfo(BatteryStatus batteryStatus) {
            this.mCallback.onRefreshBatteryInfo();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRefreshCarrierInfo(Intent intent) {
            this.mCallback.onRefreshCarrierInfo();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRemoteLockInfoChanged() {
            this.mCallback.onRemoteLockInfoChanged();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
            this.mCallback.onSecurityViewChanged();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onShadeExpandedChanged(boolean z) {
            this.mCallback.onShadeExpandedChanged(z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSimStateChanged(int i, int i2, int i3) {
            this.mCallback.onSimStateChanged(i, i2, i3);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStartedGoingToSleep(int i) {
            this.mCallback.onStartedGoingToSleep(i);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStartedWakingUp() {
            this.mCallback.onStartedWakingUp();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStrongAuthStateChanged(int i) {
            this.mCallback.onStrongAuthStateChanged(i);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSystemDialogsShowing() {
            this.mCallback.onSystemDialogsShowing();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTelephonyCapable(boolean z) {
            this.mCallback.onTelephonyCapable(z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTimeChanged() {
            this.mCallback.onTimeChanged();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTimeZoneChanged(TimeZone timeZone) {
            this.mCallback.onTimeZoneChanged(timeZone);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustAgentErrorMessage(CharSequence charSequence) {
            this.mCallback.onTrustAgentErrorMessage(charSequence);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustChanged(int i) {
            this.mCallback.onTrustChanged(i);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustManagedChanged(int i) {
            this.mCallback.onTrustManagedChanged(i);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUpdateCoverState(CoverState coverState) {
            this.mCallback.onUpdateCoverState(!coverState.switchState);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            this.mCallback.onUserSwitchComplete(i);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitching(int i) {
            this.mCallback.onUserSwitching(i);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserUnlocked() {
            this.mCallback.onUserUnlocked();
        }
    }

    public FaceWidgetKeyguardUpdateMonitorWrapper(KeyguardUpdateMonitor keyguardUpdateMonitor, SelectedUserInteractor selectedUserInteractor) {
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSelectedUserInteractor = selectedUserInteractor;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final void dispatchFaceWidgetFullScreenMode(boolean z) {
        this.mKeyguardUpdateMonitor.setFaceWidgetFullScreenMode(z);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final int getCurrentUser() {
        return this.mSelectedUserInteractor.getSelectedUserId();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final boolean isDeviceInteractive() {
        return this.mKeyguardUpdateMonitor.isScreenOn();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final boolean isDeviceProvisioned() {
        return this.mKeyguardUpdateMonitor.mDeviceProvisioned;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final boolean isEnabledBioUnlock() {
        return this.mKeyguardUpdateMonitor.isFaceOptionEnabled();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final boolean isFMMLock() {
        return this.mKeyguardUpdateMonitor.isFMMLock();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final boolean isFingerprintOptionEnabled() {
        return this.mKeyguardUpdateMonitor.isFingerprintOptionEnabled();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final boolean isKeyguardShowing() {
        return this.mKeyguardUpdateMonitor.mKeyguardShowing;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final boolean isKeyguardUnlocking() {
        return this.mKeyguardUpdateMonitor.isKeyguardUnlocking();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final boolean isKeyguardVisible() {
        return this.mKeyguardUpdateMonitor.isKeyguardVisible();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final boolean isRemoteLock() {
        return this.mKeyguardUpdateMonitor.isRemoteLockEnabled();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final boolean isSystemUser() {
        return this.mSelectedUserInteractor.getSelectedUserId() == 0;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final boolean isUnlockWithFingerprintPossible(int i) {
        return this.mKeyguardUpdateMonitor.isUnlockWithFingerprintPossible(i);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final boolean isUserUnlocked() {
        return this.mKeyguardUpdateMonitor.isUserUnlocked();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final void registerCallback(PluginKeyguardUpdateMonitorCallback pluginKeyguardUpdateMonitorCallback) {
        if (pluginKeyguardUpdateMonitorCallback == null) {
            return;
        }
        FaceWidgetKeyguardUpdateMonitorCallbackWrapper faceWidgetKeyguardUpdateMonitorCallbackWrapper = new FaceWidgetKeyguardUpdateMonitorCallbackWrapper(pluginKeyguardUpdateMonitorCallback);
        this.mCallbackWrapperMap.put(pluginKeyguardUpdateMonitorCallback, faceWidgetKeyguardUpdateMonitorCallbackWrapper);
        this.mKeyguardUpdateMonitor.registerCallback(faceWidgetKeyguardUpdateMonitorCallbackWrapper);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor
    public final void removeCallback(PluginKeyguardUpdateMonitorCallback pluginKeyguardUpdateMonitorCallback) {
        FaceWidgetKeyguardUpdateMonitorCallbackWrapper faceWidgetKeyguardUpdateMonitorCallbackWrapper;
        if (pluginKeyguardUpdateMonitorCallback == null || (faceWidgetKeyguardUpdateMonitorCallbackWrapper = (FaceWidgetKeyguardUpdateMonitorCallbackWrapper) this.mCallbackWrapperMap.get(pluginKeyguardUpdateMonitorCallback)) == null) {
            return;
        }
        this.mCallbackWrapperMap.remove(pluginKeyguardUpdateMonitorCallback);
        this.mKeyguardUpdateMonitor.removeCallback(faceWidgetKeyguardUpdateMonitorCallbackWrapper);
    }
}
