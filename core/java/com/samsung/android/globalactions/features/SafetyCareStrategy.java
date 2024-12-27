package com.samsung.android.globalactions.features;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.DefaultActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.InitializationStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SystemConditions;

/* loaded from: classes6.dex */
public class SafetyCareStrategy implements InitializationStrategy, DefaultActionsCreationStrategy {
    private static final String TAG = "SafetyCareStrategy";
    private final ConditionChecker mConditionChecker;
    private final SamsungGlobalActions mGlobalActions;
    boolean mIsFirstEmergencyActionCheck = true;
    boolean mLastEmergencyActionResult = false;
    private final LogWrapper mLogWrapper;

    public SafetyCareStrategy(SamsungGlobalActions globalActions, ConditionChecker conditionChecker, LogWrapper logWrapper) {
        this.mGlobalActions = globalActions;
        this.mConditionChecker = conditionChecker;
        this.mLogWrapper = logWrapper;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.InitializationStrategy
    public void onInitialize(boolean isKeyguardShowing) {
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_MODIFYING)) {
            this.mLogWrapper.elog(TAG, "Cannot use Global Action because Emergency mode entering..");
            this.mGlobalActions.setDisabled();
        }
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.DefaultActionsCreationStrategy
    public boolean onCreateEmergencyAction() {
        if (this.mIsFirstEmergencyActionCheck) {
            this.mIsFirstEmergencyActionCheck = false;
            if (this.mConditionChecker.isEnabled(SystemConditions.IS_DOMESTIC_OTA_MODE)) {
                this.mLogWrapper.logDebug(TAG, "EmergenceyMode is disable on OTA mode.");
                this.mLastEmergencyActionResult = false;
                return this.mLastEmergencyActionResult;
            }
            if (!this.mConditionChecker.isEnabled(SystemConditions.IS_MISSING_PHONE_LOCK) && this.mConditionChecker.isEnabled(SystemConditions.CAN_SET_MODE) && !this.mConditionChecker.isEnabled(SystemConditions.IS_UPSM_ENABLED) && !this.mConditionChecker.isEnabled(SystemConditions.IS_KIDS_HOME_MODE)) {
                this.mLastEmergencyActionResult = true;
            } else {
                this.mLastEmergencyActionResult = false;
            }
        }
        return this.mLastEmergencyActionResult;
    }
}
