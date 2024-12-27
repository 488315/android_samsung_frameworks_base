package com.android.systemui.accessibility.hearingaid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.Flags;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class HearingDevicesDialogReceiver extends BroadcastReceiver {
    public final HearingDevicesDialogManager mDialogManager;

    public HearingDevicesDialogReceiver(HearingDevicesDialogManager hearingDevicesDialogManager) {
        this.mDialogManager = hearingDevicesDialogManager;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Flags.FEATURE_FLAGS.getClass();
        if ("com.android.systemui.action.LAUNCH_HEARING_DEVICES_DIALOG".equals(intent.getAction())) {
            this.mDialogManager.showDialog(null);
        }
    }
}
