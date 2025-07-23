package com.samsung.android.globalactions.features;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModelFactory;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.SystemConditions;

/* loaded from: classes6.dex */
public class LockdownModeStrategy implements ActionsCreationStrategy {
    private final ConditionChecker mConditionChecker;
    private final ActionViewModelFactory mViewModelFactory;

    public LockdownModeStrategy(ActionViewModelFactory actionViewModelFactory, ConditionChecker conditionChecker) {
        this.mViewModelFactory = actionViewModelFactory;
        this.mConditionChecker = conditionChecker;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy
    public void onCreateActions(SamsungGlobalActions samsungGlobalActions) {
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_LOCK_DOWN_IN_POWER_MENU) && this.mConditionChecker.isEnabled(SystemConditions.IS_CURRENT_USER_SECURE) && this.mConditionChecker.isEnabled(SystemConditions.IS_STRONG_AUTH_FOR_LOCK_DOWN)) {
            if (this.mConditionChecker.isEnabled(SystemConditions.FRONT_LARGE_COVER_DISPLAY) && this.mConditionChecker.isEnabled(SystemConditions.IS_FOLDED)) {
                return;
            }
            samsungGlobalActions.addAction(this.mViewModelFactory.createActionViewModel(samsungGlobalActions, DefaultActionNames.ACTION_LOCKDOWN_MODE));
        }
    }
}
