package com.samsung.android.globalactions.features;

import com.samsung.android.globalactions.presentation.strategies.DefaultActionsCreationStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.SystemConditions;

/* loaded from: classes6.dex */
public class ReserveBatteryModeStrategy implements DefaultActionsCreationStrategy {
    private final ConditionChecker mConditionChecker;

    public ReserveBatteryModeStrategy(ConditionChecker conditionChecker) {
        this.mConditionChecker = conditionChecker;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.DefaultActionsCreationStrategy
    public boolean onCreateEmergencyAction() {
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_RBM_MODE)) {
            return false;
        }
        return true;
    }
}
