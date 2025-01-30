package com.samsung.android.knoxguard.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Slog;
import com.samsung.android.knoxguard.service.KnoxGuardSeService;
import com.samsung.android.knoxguard.service.utils.Utils;

/* loaded from: classes2.dex */
public class AlarmReceiver extends BroadcastReceiver {
    public static final String TAG = "KG." + AlarmReceiver.class.getSimpleName();

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int intExtra = intent.getIntExtra("alarm_type", -1);
        String str = TAG;
        Slog.i(str, "onReceive : " + intExtra + " @" + System.currentTimeMillis());
        if (intExtra == 0) {
            int clientHealth = KnoxGuardSeService.getClientHealth();
            Slog.i(str, "clientHealthState " + clientHealth);
            if (clientHealth == 1 || !Utils.needClientHealthCheck()) {
                return;
            }
            KnoxGuardSeService.setClientHealth(2);
            Utils.lockSeDevice(context, "2001");
            return;
        }
        if (1 == intExtra) {
            if (Utils.setRetryLock(context)) {
                return;
            }
            Slog.i(str, "System ui is not ready - power off ");
            Utils.powerOff(context, 4);
            return;
        }
        Slog.w(str, "onReceive - unidentified alarm action: " + intExtra);
    }
}
