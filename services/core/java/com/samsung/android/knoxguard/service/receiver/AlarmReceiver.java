package com.samsung.android.knoxguard.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.knoxguard.service.utils.Utils;

public final class AlarmReceiver extends BroadcastReceiver {
    public static final String TAG = "KG.AlarmReceiver";

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        int intExtra = intent.getIntExtra(Constants.ALARM_TYPE, -1);
        String str = TAG;
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(intExtra, "onReceive : ", " @");
        m.append(System.currentTimeMillis());
        Slog.i(str, m.toString());
        if (1 != intExtra) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    intExtra, "onReceive - unidentified alarm action: ", str);
        } else {
            if (Utils.setRetryLock(context)) {
                return;
            }
            Slog.i(str, "System ui is not ready - power off ");
            Utils.powerOff(context, 4);
        }
    }
}
