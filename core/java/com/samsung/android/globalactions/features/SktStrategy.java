package com.samsung.android.globalactions.features;

import com.android.internal.R;

import com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.ToastController;

public class SktStrategy implements ActionInteractionStrategy {
    private static final String TAG = "SktStrategy";
    private final ConditionChecker mConditionChecker;
    private final LogWrapper mLogWrapper;
    private final ResourcesWrapper mResourcesWrapper;
    private final ToastController mToastController;

    public SktStrategy(
            ConditionChecker conditionChecker,
            ToastController toastController,
            ResourcesWrapper resourcesWrapper,
            LogWrapper logWrapper) {
        this.mConditionChecker = conditionChecker;
        this.mToastController = toastController;
        this.mResourcesWrapper = resourcesWrapper;
        this.mLogWrapper = logWrapper;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy
    public boolean onPressPowerAction() {
        this.mLogWrapper.v(TAG, "onPressPowerAction");
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_SECURE_KEYGUARD)
                && this.mConditionChecker.isEnabled(SystemConditions.IS_TSAFE_LOCK)) {
            this.mToastController.showToast(
                    this.mResourcesWrapper.getString(R.string.globalactions_unable_power_off_msg),
                    1);
            return true;
        }
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_CARRIRER_LOCK_PLUS_ENABLED)) {
            this.mToastController.showToast(
                    this.mResourcesWrapper.getString(
                            R.string.globalactions_unable_power_off_msg_fmm),
                    1);
            return true;
        }
        return false;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy
    public boolean onPressRestartAction() {
        this.mLogWrapper.v(TAG, "onPressRestartAction");
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_CARRIRER_LOCK_PLUS_ENABLED)) {
            this.mToastController.showToast(
                    this.mResourcesWrapper.getString(R.string.globalactions_unable_restart_msg_fmm),
                    1);
            return true;
        }
        return false;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy
    public boolean onPressEmergencyModeAction() {
        this.mLogWrapper.v(TAG, "onPressEmergencyModeAction");
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_CARRIRER_LOCK_PLUS_ENABLED)) {
            this.mToastController.showToast(
                    this.mResourcesWrapper.getString(
                            R.string.globalactions_unable_emergency_msg_fmm),
                    1);
            return true;
        }
        return false;
    }
}
