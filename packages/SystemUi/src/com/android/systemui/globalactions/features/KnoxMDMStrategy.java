package com.android.systemui.globalactions.features;

import com.android.systemui.globalactions.util.SystemUIConditions;
import com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
