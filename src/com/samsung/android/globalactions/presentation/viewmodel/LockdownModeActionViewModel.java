package com.samsung.android.globalactions.presentation.viewmodel;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.util.LockPatternUtilsWrapper;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;

/* loaded from: classes6.dex */
public class LockdownModeActionViewModel implements ActionViewModel {
    private final SamsungGlobalActions mGlobalActions;
    private ActionInfo mInfo;
    private final LockPatternUtilsWrapper mLockPatternUtilsWrapper;
    private final SamsungGlobalActionsAnalytics mSAnalytics;

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onLongPress() {
    }

    public LockdownModeActionViewModel(SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics, LockPatternUtilsWrapper lockPatternUtilsWrapper, SamsungGlobalActions samsungGlobalActions) {
        this.mSAnalytics = samsungGlobalActionsAnalytics;
        this.mLockPatternUtilsWrapper = lockPatternUtilsWrapper;
        this.mGlobalActions = samsungGlobalActions;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public ActionInfo getActionInfo() {
        return this.mInfo;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void setActionInfo(ActionInfo actionInfo) {
        this.mInfo = actionInfo;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPress() {
        this.mSAnalytics.sendEventLog(SamsungGlobalActionsAnalytics.SID_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.EID_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.DID_LOCK_DOWN, 4L);
        this.mGlobalActions.dismissDialog(true);
        this.mLockPatternUtilsWrapper.lockDownDelayed(500);
    }
}
