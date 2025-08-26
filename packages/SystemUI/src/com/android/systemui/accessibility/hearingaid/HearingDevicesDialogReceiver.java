package com.android.systemui.accessibility.hearingaid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes.dex */
public class HearingDevicesDialogReceiver extends BroadcastReceiver {
    public final HearingDevicesDialogManager mDialogManager;

    public HearingDevicesDialogReceiver(HearingDevicesDialogManager hearingDevicesDialogManager) {
        this.mDialogManager = hearingDevicesDialogManager;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("com.android.systemui.action.LAUNCH_HEARING_DEVICES_DIALOG".equals(intent.getAction())) {
            this.mDialogManager.showDialog(null, 1);
        }
    }
}
