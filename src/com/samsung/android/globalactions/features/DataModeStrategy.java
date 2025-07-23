package com.samsung.android.globalactions.features;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModelFactory;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.SystemConditions;

/* loaded from: classes6.dex */
public class DataModeStrategy implements ActionsCreationStrategy {
    private final ConditionChecker mConditionChecker;
    private final ActionViewModelFactory mViewModelFactory;

    public DataModeStrategy(ActionViewModelFactory actionViewModelFactory, ConditionChecker conditionChecker) {
        this.mViewModelFactory = actionViewModelFactory;
        this.mConditionChecker = conditionChecker;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy
    public void onCreateActions(SamsungGlobalActions samsungGlobalActions) {
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_IN_LOCK_TASK_MODE)) {
            return;
        }
        samsungGlobalActions.addAction(this.mViewModelFactory.createActionViewModel(samsungGlobalActions, DefaultActionNames.ACTION_DATA_MODE));
    }
}
