package com.android.systemui.globalactions.features;

import com.android.systemui.globalactions.util.SystemUIConditions;
import com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class KnoxMDMStrategy implements ActionInteractionStrategy {
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
