package com.android.systemui.accessibility.hearingaid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
