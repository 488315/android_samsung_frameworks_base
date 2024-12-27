package com.android.systemui;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

public class SysuiRestartReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("com.android.systemui.action.RESTART".equals(intent.getAction())) {
            NotificationManager.from(context).cancel(intent.getData().toString().substring(10), 6);
            Process.killProcess(Process.myPid());
        }
    }
}
