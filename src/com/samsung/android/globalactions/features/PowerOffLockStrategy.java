package com.samsung.android.globalactions.features;

import android.os.CancellationSignal;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.util.BiometricPromptWrapper;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.SystemConditions;

/* loaded from: classes6.dex */
public class PowerOffLockStrategy implements SecureConfirmStrategy {
    private final BiometricPromptWrapper mBiometricPromptWrapper;
    private final ConditionChecker mConditionChecker;

    @Override // com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy
    public boolean hasSecureConfirmCondition() {
        return true;
    }

    public PowerOffLockStrategy(ConditionChecker conditionChecker, BiometricPromptWrapper biometricPromptWrapper) {
        this.mConditionChecker = conditionChecker;
        this.mBiometricPromptWrapper = biometricPromptWrapper;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy
    public boolean doActionBeforeSecureConfirm(final ActionViewModel actionViewModel, final SamsungGlobalActions samsungGlobalActions) {
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_SECURE_KEYGUARD)) {
            return true;
        }
        if (!this.mConditionChecker.isEnabled(SystemConditions.IS_POWER_OFF_UNLOCK_ALWAYS_REQUIRED)) {
            return false;
        }
        CancellationSignal cancellationSignal = new CancellationSignal();
        this.mBiometricPromptWrapper.initPrompt(" ", 33023);
        this.mBiometricPromptWrapper.setRunnable(new Runnable() { // from class: com.samsung.android.globalactions.features.PowerOffLockStrategy$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                actionViewModel.onPressSecureConfirm();
            }
        }, new Runnable() { // from class: com.samsung.android.globalactions.features.PowerOffLockStrategy$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                samsungGlobalActions.dismissDialog(false);
            }
        });
        this.mBiometricPromptWrapper.buildAndRun(cancellationSignal);
        return true;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy
    public boolean isNeedSecureConfirm() {
        return (this.mConditionChecker.isEnabled(SystemConditions.IS_KNOXGUARD_LOCKED) || this.mConditionChecker.isEnabled(SystemConditions.IS_SIM_LOCK) || this.mConditionChecker.isEnabled(SystemConditions.IS_POWER_OFF_UNLOCK_NOT_REQUIRED) || !this.mBiometricPromptWrapper.canAuthenticate(33023)) ? false : true;
    }
}
