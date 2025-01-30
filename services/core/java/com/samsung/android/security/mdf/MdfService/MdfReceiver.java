package com.samsung.android.security.mdf.MdfService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import com.android.server.backup.BackupAgentTimeoutParameters;

/* loaded from: classes2.dex */
public class MdfReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.d("MdfService", "onReceive");
        String action = intent.getAction();
        final MdfPolicy mdfPolicy = MdfPolicy.getInstance(context);
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            Log.i("MdfService", "Send Samsung Analytics log");
            new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.security.mdf.MdfService.MdfReceiver.1
                @Override // java.lang.Runnable
                public void run() {
                    mdfPolicy.sendSamsungAnalyticsMultiLog();
                }
            }, BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
        } else {
            Log.d("MdfService", "Invalid action");
        }
    }
}
