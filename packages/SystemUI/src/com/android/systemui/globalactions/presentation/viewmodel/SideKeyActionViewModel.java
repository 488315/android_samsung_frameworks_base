package com.android.systemui.globalactions.presentation.viewmodel;

import android.content.Context;
import android.content.Intent;
import com.android.systemui.globalactions.util.ActivityStarterWrapper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.viewmodel.ActionInfo;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.KeyGuardManagerWrapper;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;

public final class SideKeyActionViewModel implements ActionViewModel {
    public final ActivityStarterWrapper mActivityStarterWrapper;
    public final SamsungGlobalActions mGlobalActions;
    public ActionInfo mInfo;
    public final LogWrapper mLogWrapper;
    public final SamsungGlobalActionsAnalytics mSamsungGlobalActionsAnalytics;

    public SideKeyActionViewModel(Context context, SamsungGlobalActions samsungGlobalActions, LogWrapper logWrapper, FeatureFactory featureFactory, KeyGuardManagerWrapper keyGuardManagerWrapper, ActivityStarterWrapper activityStarterWrapper, ConditionChecker conditionChecker, SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics) {
        this.mGlobalActions = samsungGlobalActions;
        this.mLogWrapper = logWrapper;
        this.mActivityStarterWrapper = activityStarterWrapper;
        this.mSamsungGlobalActionsAnalytics = samsungGlobalActionsAnalytics;
    }

    public final ActionInfo getActionInfo() {
        return this.mInfo;
    }

    public final void onPress() {
        this.mLogWrapper.i("SideKeyActionViewModel", "Running side key settings activity");
        this.mSamsungGlobalActionsAnalytics.sendEventLog("611", "6111", "Side key settings", 8L);
        Intent intent = new Intent("com.samsung.android.intent.action.SIDE_KEY_SETTINGS");
        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        this.mActivityStarterWrapper.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        this.mGlobalActions.dismissDialog(false);
    }

    public final void setActionInfo(ActionInfo actionInfo) {
        this.mInfo = actionInfo;
    }
}
