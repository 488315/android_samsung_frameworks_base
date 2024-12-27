package com.android.systemui.globalactions.features;

import com.android.systemui.globalactions.util.SystemUIConditions;
import com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KnoxMDMStrategy implements ActionInteractionStrategy {
    public final ConditionChecker mConditionChecker;

    public KnoxMDMStrategy(ConditionChecker conditionChecker) {
        this.mConditionChecker = conditionChecker;
    }

    public final boolean onLongPressPowerAction() {
        return !this.mConditionChecker.isEnabled(SystemUIConditions.IS_SAFE_MODE_ALLOWED);
    }

    public final boolean onPressDataModeAction() {
        return (this.mConditionChecker.isEnabled(SystemUIConditions.IS_CELLULAR_DATA_ALLOWED) && this.mConditionChecker.isEnabled(SystemUIConditions.IS_SETTINGS_CHANGES_ALLOWED)) ? false : true;
    }

    public final boolean onPressPowerAction() {
        return !this.mConditionChecker.isEnabled(SystemUIConditions.IS_POWER_OFF_ALLOWED);
    }
}
