package com.samsung.android.globalactions.features;

import com.samsung.android.globalactions.presentation.strategies.SoftwareUpdateStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.SystemController;

/* loaded from: classes6.dex */
public class SecFOTAForceUpdateStrategy implements SoftwareUpdateStrategy {
    private final ConditionChecker mConditionChecker;
    private final SystemController mSystemController;

    public SecFOTAForceUpdateStrategy(
            ConditionChecker conditionChecker, SystemController systemController) {
        this.mConditionChecker = conditionChecker;
        this.mSystemController = systemController;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.SoftwareUpdateStrategy
    public boolean onUpdate() {
        return this.mConditionChecker.isEnabled(SystemConditions.IS_SEC_FOTA_CLIENT_PACKAGE_ENABLED)
                && this.mConditionChecker.isEnabled(
                        SystemConditions.IS_FOTA_AVAILABLE_FOR_GLOBALACTIONS);
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.SoftwareUpdateStrategy
    public void update() {
        this.mSystemController.startSecForceUpdate();
    }
}
