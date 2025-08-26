package com.samsung.android.sdk.bixby2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* loaded from: classes4.dex */
public class ApplicationTriggerReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Log.i("ApplicationTriggerReceiver", "onReceived()");
        if (context != null) {
            context.unregisterReceiver(this);
            Log.i("ApplicationTriggerReceiver", "ApplicationTriggerReceiver unRegistered");
        }
    }
}
