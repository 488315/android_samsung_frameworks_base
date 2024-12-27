package com.samsung.android.globalactions.presentation.strategies;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;

public interface SecureConfirmStrategy {
    boolean doActionBeforeSecureConfirm(
            ActionViewModel actionViewModel, SamsungGlobalActions samsungGlobalActions);

    default boolean isNeedSecureConfirm() {
        return true;
    }

    default boolean hasSecureConfirmCondition() {
        return false;
    }

    default boolean isFoldedState() {
        return false;
    }
}
