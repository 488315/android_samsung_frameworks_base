package com.samsung.android.globalactions.features;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.InitializationStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.SystemConditions;

/* loaded from: classes6.dex */
public class KnoxContainerStrategy implements InitializationStrategy {
    private final ConditionChecker mConditionChecker;
    private final SamsungGlobalActions mGlobalActions;

    public KnoxContainerStrategy(SamsungGlobalActions globalActions, ConditionChecker conditionChecker) {
        this.mGlobalActions = globalActions;
        this.mConditionChecker = conditionChecker;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.InitializationStrategy
    public void onInitialize(boolean isKeyguardShowing) {
        if (!isKeyguardShowing && this.mConditionChecker.isEnabled(SystemConditions.IS_VALID_VERSION)) {
            this.mGlobalActions.setKeyguardShowing(this.mConditionChecker.isEnabled(SystemConditions.GET_KEYGUARD_SHOW_STATE) | isKeyguardShowing);
        }
    }
}
