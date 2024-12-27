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
public class EmergencyCallActionViewModel implements ActionViewModel {
    public static final String DISPLAY_CATEGORY_BUILTIN = "com.samsung.android.hardware.display.category.BUILTIN";
    private final Context mContext;
    private final SamsungGlobalActions mGlobalActions;
    private ActionInfo mInfo;
    private final SamsungGlobalActionsAnalytics mSAnalytics;
    private final SystemController mSystemController;
    private final TelecomManager mTelecomManager;

    public EmergencyCallActionViewModel(Context context, SamsungGlobalActions globalActions, SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics, SystemController systemController) {
        this.mContext = context;
        this.mSAnalytics = samsungGlobalActionsAnalytics;
        this.mSystemController = systemController;
        this.mGlobalActions = globalActions;
        this.mTelecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public ActionInfo getActionInfo() {
        return this.mInfo;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void setActionInfo(ActionInfo info) {
        this.mInfo = info;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPress() {
        Intent emergencyDialIntent;
        if (this.mTelecomManager == null) {
            return;
        }
        DisplayManager dm = (DisplayManager) this.mContext.getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = dm.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        Display display = displays[0];
        int length = displays.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Display d = displays[i];
            if (d.getDisplayId() != 1) {
                i++;
            } else {
                display = d;
                break;
            }
        }
        if (display != null) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this.mContext, 0, 0);
            int displayId = 0;
            if (SemWindowManager.getInstance().isFolded()) {
                displayId = display.getDisplayId();
                emergencyDialIntent = new Intent();
                emergencyDialIntent.setAction("com.samsung.android.app.telephonyui.action.OPEN_EMERGENCY_DIALER_COVER_SCREEN");
            } else {
                emergencyDialIntent = this.mTelecomManager.createLaunchEmergencyDialerIntent(null).setFlags(343932928).putExtra("com.android.phone.EmergencyDialer.extra.ENTRY_TYPE", 2);
            }
            emergencyDialIntent.addFlags(268468224);
            emergencyDialIntent.putExtra("from_global_action", true);
            options.setLaunchDisplayId(displayId);
            this.mContext.startActivityAsUser(emergencyDialIntent, options.toBundle(), new UserHandle(ActivityManager.getCurrentUser()));
        }
        this.mGlobalActions.dismissDialog(true);
        this.mSAnalytics.sendEventLog(SamsungGlobalActionsAnalytics.SID_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.EID_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.DID_EMERGENCY_SOS, 9L);
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public boolean showBeforeProvisioning() {
        return true;
    }
}
