package com.samsung.android.globalactions.presentation.viewmodel;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.UserHandle;
import android.telecom.TelecomManager;
import android.view.Display;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;
import com.samsung.android.globalactions.util.SystemController;
import com.samsung.android.view.SemWindowManager;

/* loaded from: classes6.dex */
public class MedicalInfoActionViewModel implements ActionViewModel {
    public static final String DISPLAY_CATEGORY_BUILTIN = "com.samsung.android.hardware.display.category.BUILTIN";
    private final Context mContext;
    private final SamsungGlobalActions mGlobalActions;
    private ActionInfo mInfo;
    private final SamsungGlobalActionsAnalytics mSAnalytics;
    private final SystemController mSystemController;
    private final TelecomManager mTelecomManager;

    public MedicalInfoActionViewModel(Context context, SamsungGlobalActions samsungGlobalActions, SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics, SystemController systemController) {
        this.mContext = context;
        this.mSAnalytics = samsungGlobalActionsAnalytics;
        this.mSystemController = systemController;
        this.mGlobalActions = samsungGlobalActions;
        this.mTelecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
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
        if (this.mTelecomManager == null) {
            return;
        }
        Display[] displays = ((DisplayManager) this.mContext.getSystemService(Context.DISPLAY_SERVICE)).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        int i = 0;
        Display display = displays[0];
        int length = displays.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            Display display2 = displays[i2];
            if (display2.getDisplayId() == 1) {
                display = display2;
                break;
            }
            i2++;
        }
        if (display != null) {
            Intent intent = new Intent();
            ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(this.mContext, 0, 0);
            if (SemWindowManager.getInstance().isFolded()) {
                i = display.getDisplayId();
                intent.setAction("com.samsung.android.app.telephonyui.action.OPEN_EMERGENCY_DIALER_COVER_SCREEN");
                intent.putExtra("launchMedicalInfo", true);
            } else {
                intent.setAction("com.samsung.android.app.telephonyui.action.OPEN_MEDICAL_INFO");
            }
            intent.addFlags(268468224);
            intent.putExtra("from_global_action", true);
            makeCustomAnimation.setLaunchDisplayId(i);
            this.mContext.startActivityAsUser(intent, makeCustomAnimation.toBundle(), new UserHandle(ActivityManager.getCurrentUser()));
        }
        this.mGlobalActions.dismissDialog(true);
        this.mSAnalytics.sendEventLog(SamsungGlobalActionsAnalytics.SID_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.EID_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.DID_EMERGENCY_SOS, 9L);
    }
}
