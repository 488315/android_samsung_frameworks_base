package com.android.systemui;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class SysuiRestartReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("com.android.systemui.action.RESTART".equals(intent.getAction())) {
            NotificationManager.from(context).cancel(intent.getData().toString().substring(10), 6);
            Process.killProcess(Process.myPid());
        }
    }
}
