package com.android.keyguard;

import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Message;
import com.android.internal.widget.RemoteLockInfo;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.samsung.android.cover.CoverState;
import java.io.PrintWriter;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface KeyguardSecUpdateMonitor {
    default boolean checkValidPrevCredentialType() {
        return false;
    }

    default int getBiometricType() {
        return -1;
    }

    default CoverState getCoverState() {
        return null;
    }

    default int getCredentialTypeForUser(int i) {
        return -1;
    }

    default KeyguardSecurityModel.SecurityMode getCurrentSecurityMode() {
        return KeyguardSecurityModel.SecurityMode.Invalid;
    }

    default String getDeviceOwnerInfo() {
        return null;
    }

    default KeyguardConstants$KeyguardDismissActionType getDismissActionType() {
        return null;
    }

    default boolean getFaceStrongBiometric() {
        return false;
    }

    default int getFailedBiometricUnlockAttempts() {
        return -1;
    }

    default int getFailedFMMUnlockAttempt(int i) {
        return 0;
    }

    default int getFailedUnlockAttempts(int i) {
        return 0;
    }

    default KeyguardFastBioUnlockController getFastBioUnlockController() {
        return null;
    }

    default boolean getFingerprintAuthenticated() {
        return false;
    }

    default Message getKeyguardBatteryMessage(Intent intent) {
        return null;
    }

    default KeyguardBatteryStatus getKeyguardBatteryStatus() {
        return null;
    }

    default long getLockoutAttemptDeadline() {
        return 0L;
    }

    default long getLockoutBiometricAttemptDeadline() {
        return 0L;
    }

    default int getMaxFailedUnlockAttempts() {
        return -1;
    }

    default String getOwnerInfo() {
        return null;
    }

    default int getPrevCredentialType() {
        return 0;
    }

    default int getRemainingAttempt(int i) {
        return -1;
    }

    default int getRemainingAttemptsBeforePermanentLock() {
        return -1;
    }

    default int getRemainingAttemptsBeforeWipe() {
        return 0;
    }

    default RemoteLockInfo getRemoteLockInfo() {
        return null;
    }

    default int getRemoteLockType() {
        return 0;
    }

    default boolean hasRedactedNotifications() {
        return false;
    }

    default boolean is2StepVerification() {
        return false;
    }

    default boolean isActiveDismissAction() {
        return false;
    }

    default boolean isAllEnabledSlotOutOfService() {
        return false;
    }

    default boolean isAllSimState() {
        return false;
    }

    default boolean isAuthenticatedWithBiometric(int i) {
        return false;
    }

    default boolean isAutoWipe() {
        return false;
    }

    default boolean isBiometricErrorLockoutPermanent() {
        return false;
    }

    default boolean isBiometricsAuthenticatedOnLock() {
        return false;
    }

    default boolean isBouncerFullyShown() {
        return false;
    }

    default boolean isCameraDisabledByPolicy() {
        return false;
    }

    default boolean isCarrierLock() {
        return false;
    }

    default boolean isCoverClosed() {
        return false;
    }

    default boolean isDeviceOwnerInfoEnabled() {
        return false;
    }

    default boolean isDismissActionExist() {
        return false;
    }

    default boolean isDualDarInnerAuthRequired(int i) {
        return false;
    }

    default boolean isDualDarInnerAuthShowing() {
        return false;
    }

    default boolean isDualDisplayPolicyAllowed() {
        return false;
    }

    default boolean isESimEmbedded() {
        return false;
    }

    default boolean isESimRemoveButtonClicked() {
        return false;
    }

    default boolean isEarlyWakeUp() {
        return false;
    }

    default boolean isEmergencyCallOnly() {
        return false;
    }

    default boolean isEnabledWof() {
        return false;
    }

    default boolean isFMMLock() {
        return false;
    }

    default boolean isFaceOptionEnabled() {
        return false;
    }

    default boolean isFingerprintDisabledWithBadQuality() {
        return false;
    }

    default boolean isFingerprintLeave() {
        return false;
    }

    default boolean isFingerprintOptionEnabled() {
        return false;
    }

    default boolean isForcedLock() {
        return false;
    }

    default boolean isForgotPasswordView() {
        return false;
    }

    default boolean isFullscreenBouncer() {
        return false;
    }

    default boolean isHiddenInputContainer() {
        return false;
    }

    default boolean isIccBlockedPermanently() {
        return false;
    }

    default boolean isInDisplayFingerprintMarginAccepted() {
        return false;
    }

    default boolean isKeyguardUnlocking() {
        return false;
    }

    default boolean isKidsModeRunning() {
        return false;
    }

    default boolean isLockScreenRotationAllowed(Context context) {
        return true;
    }

    default boolean isLockscreenDisabled() {
        return false;
    }

    default boolean isMaxFailedBiometricUnlockAttempts(int i) {
        return false;
    }

    default boolean isMaxFailedBiometricUnlockAttemptsShort() {
        return false;
    }

    default boolean isNowBarExpandMode() {
        return false;
    }

    default boolean isOutOfService() {
        return false;
    }

    default boolean isOwnerInfoEnabled() {
        return false;
    }

    default boolean isPerformingWipeOut() {
        return false;
    }

    default boolean isPermanentLock() {
        return false;
    }

    default boolean isRemoteLockEnabled() {
        return false;
    }

    default boolean isRemoteLockMode() {
        return false;
    }

    default boolean isScreenOffMemoRunning() {
        return false;
    }

    default boolean isScreenOn() {
        return false;
    }

    default boolean isSecure() {
        return false;
    }

    default boolean isShortcutLaunchInProgress() {
        return false;
    }

    default boolean isShowEditModeRequest() {
        return false;
    }

    default boolean isSimDisabledPermanently() {
        return false;
    }

    default boolean isSimPinPassed(int i, int i2) {
        return false;
    }

    default boolean isSimState(int i) {
        return false;
    }

    default boolean isTimerRunning() {
        return false;
    }

    default boolean isUdfpsFingerDown() {
        return false;
    }

    default boolean isUnlockCompleted() {
        return false;
    }

    default boolean isUpdateSecurityMessage() {
        return false;
    }

    default boolean isUserUnlocked() {
        return false;
    }

    default void runSystemUserOnly(Runnable runnable) {
    }

    default long setLockoutAttemptDeadline(int i, int i2) {
        return 0L;
    }

    default boolean updateCarrierLock(int i) {
        return false;
    }

    default boolean updateFMMLock(int i, boolean z) {
        return false;
    }

    default boolean updatePermanentLock(int i) {
        return false;
    }

    default int getBiometricType(int i) {
        return -1;
    }

    default int getFailedBiometricUnlockAttempts(int i) {
        return -1;
    }

    default boolean isSecure(int i) {
        return false;
    }

    default void runSystemUserOnly(Runnable runnable, Executor executor) {
    }

    default void clearESimRemoved() {
    }

    default void clearFingerBadQualityCounts() {
    }

    default void dispatchForceStartFingerprint() {
    }

    default void dispatchStartSubscreenFingerprint() {
    }

    default void dispatchStopSubscreenBiometric() {
    }

    default void enableSecurityDebug() {
    }

    default void notifyFailedUnlockAttemptChanged() {
    }

    default void onLockIconPressed() {
    }

    default void requestSessionClose() {
    }

    default void setupLocked$2() {
    }

    default void addFailedFMMUnlockAttempt(int i) {
    }

    default void clearFailedUnlockAttempts(boolean z) {
    }

    default void dispatchCallback(KeyguardUpdateMonitor$$ExternalSyntheticLambda9 keyguardUpdateMonitor$$ExternalSyntheticLambda9) {
    }

    default void dispatchCoverState(CoverState coverState) {
    }

    default void dispatchDlsBiometricMode(boolean z) {
    }

    default void dispatchDlsViewMode(int i) {
    }

    default void dispatchNotiStarState(boolean z) {
    }

    default void dispatchSecurityModeChanged(KeyguardSecurityModel.SecurityMode securityMode) {
    }

    default void dispatchStartSubscreenBiometric(Intent intent) {
    }

    default void dispatchStartedEarlyWakingUp(int i) {
    }

    default void dispatchStatusBarState(boolean z) {
    }

    default void dispatchSubScreenBouncerStateChanged(boolean z) {
    }

    default void dumpAllUsers(PrintWriter printWriter) {
    }

    default void handleSecMessage(Message message) {
    }

    default void registerPreCallback(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
    }

    default void removeESim(int i) {
    }

    default void resetSimPinPassed(int i) {
    }

    default void sendBiometricUnlockState(BiometricSourceType biometricSourceType) {
    }

    default void sendPrimaryBouncerVisibilityChanged(boolean z) {
    }

    default void setDisableBiometricBySecurityDialog(boolean z) {
    }

    default void setDismissActionExist(boolean z) {
    }

    default void setDismissActionType(KeyguardConstants$KeyguardDismissActionType keyguardConstants$KeyguardDismissActionType) {
    }

    default void setFaceWidgetFullScreenMode(boolean z) {
    }

    default void setHasRedactedNotifications(boolean z) {
    }

    default void setNowBarExpandMode(boolean z) {
    }

    default void setPanelExpandingStarted(boolean z) {
    }

    default void setScreenOrientationNoSensorValue(boolean z) {
    }

    default void setShortcutLaunchInProgress(boolean z) {
    }

    default void setUnlockingKeyguard(boolean z) {
    }

    default void updateRemoteLockInfo(RemoteLockInfo remoteLockInfo) {
    }

    default void updateSIPShownState(boolean z) {
    }

    default void updateUserUnlockNotification(int i) {
    }

    default void updatedSimPinPassed(int i) {
    }

    default void dispatchDualDarInnerLockScreenState(int i, boolean z) {
    }

    default void setFocusForBiometrics(int i, boolean z) {
    }

    default void updateEsimState(int i, int i2) {
    }

    default void sendKeyguardStateUpdated(boolean z, boolean z2, boolean z3, boolean z4) {
    }
}
