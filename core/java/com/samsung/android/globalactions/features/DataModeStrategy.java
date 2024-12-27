package com.samsung.android.globalactions.features;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModelFactory;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.SystemConditions;

public class DataModeStrategy implements ActionsCreationStrategy {
    private final ConditionChecker mConditionChecker;
    private final ActionViewModelFactory mViewModelFactory;

    public DataModeStrategy(
            ActionViewModelFactory viewModelFactory, ConditionChecker conditionChecker) {
        this.mViewModelFactory = viewModelFactory;
        this.mConditionChecker = conditionChecker;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy
    public void onCreateActions(SamsungGlobalActions globalActions) {
        if (!this.mConditionChecker.isEnabled(SystemConditions.IS_IN_LOCK_TASK_MODE)) {
            ActionViewModel viewModel =
                    this.mViewModelFactory.createActionViewModel(
                            globalActions, DefaultActionNames.ACTION_DATA_MODE);
            globalActions.addAction(viewModel);
        }
    }
}
