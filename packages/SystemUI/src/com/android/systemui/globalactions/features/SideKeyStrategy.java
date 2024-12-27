package com.android.systemui.globalactions.features;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.systemui.R;
import com.android.systemui.globalactions.util.ScreenCapturePopupController;
import com.android.systemui.globalactions.util.SystemUIConditions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.ActionUpdateStrategy;
import com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.DisposingStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModelFactory;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.SystemConditions;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SideKeyStrategy implements ActionsCreationStrategy, ActionUpdateStrategy, DisposingStrategy {
    public final ConditionChecker mConditionChecker;
    public final Context mContext;
    public final ScreenCapturePopupController mPopupController;
    public final ActionViewModelFactory mViewModelFactory;
    public int sSideKeyType = -1;
    public final int TYPE_COMBINATION = 1;

    public SideKeyStrategy(ActionViewModelFactory actionViewModelFactory, ConditionChecker conditionChecker, Context context, ScreenCapturePopupController screenCapturePopupController) {
        this.mViewModelFactory = actionViewModelFactory;
        this.mConditionChecker = conditionChecker;
        this.mContext = context;
        this.mPopupController = screenCapturePopupController;
    }

    public final void onCreateActions(SamsungGlobalActions samsungGlobalActions) {
        if (this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_SIDE_VIEW_COVER_CLOSED) || this.mConditionChecker.isEnabled(SystemConditions.IS_RBM_MODE) || !this.mConditionChecker.isEnabled(SystemConditions.IS_USER_UNLOCKED) || this.mConditionChecker.isEnabled(SystemConditions.IS_FMM_LOCKED) || this.mConditionChecker.isEnabled(SystemConditions.IS_RMM_LOCKED) || this.mConditionChecker.isEnabled(SystemConditions.IS_EMERGENCY_MODE) || this.mConditionChecker.isEnabled(SystemConditions.IS_IN_LOCK_TASK_MODE) || this.mConditionChecker.isEnabled(SystemConditions.IS_REPAIR_MODE) || this.mConditionChecker.isEnabled(SystemUIConditions.GET_PROKIOSK_STATE) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_KIOSK_MODE) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_FUNCTION_KEY_SETTING_HIDE)) {
            return;
        }
        if (this.mConditionChecker.isEnabled(SystemConditions.FRONT_LARGE_COVER_DISPLAY) && this.mConditionChecker.isEnabled(SystemConditions.IS_FOLDED)) {
            return;
        }
        samsungGlobalActions.addAction(this.mViewModelFactory.createActionViewModel(samsungGlobalActions, "side_key_settings"));
        int sideKeyType = samsungGlobalActions.getSideKeyType();
        this.sSideKeyType = sideKeyType;
        if (sideKeyType == this.TYPE_COMBINATION) {
            samsungGlobalActions.addAction(this.mViewModelFactory.createActionViewModel(samsungGlobalActions, "screen_capture_popup"));
        }
    }

    public final void onDispose() {
        if (this.sSideKeyType == this.TYPE_COMBINATION) {
            ScreenCapturePopupController screenCapturePopupController = this.mPopupController;
            SharedPreferences.Editor edit = screenCapturePopupController.mPrefrerences.edit();
            edit.putLong("dismissTime", System.currentTimeMillis());
            edit.apply();
            screenCapturePopupController.mLogWrapper.logDebug("ScreenCapturePopupController", "saveTime : " + screenCapturePopupController.mPrefrerences.getLong("dismissTime", 0L));
        }
    }

    public final void onUpdateAction(ActionViewModel actionViewModel) {
        if (actionViewModel.getActionInfo().getName() == "force_restart_message") {
            int integer = this.mContext.getResources().getInteger(17695179);
            actionViewModel.getActionInfo().setStateLabel(this.mContext.getResources().getQuantityString(R.plurals.globalactions_force_restart_message, integer, Integer.valueOf(integer)));
        }
    }
}
