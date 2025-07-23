package com.samsung.android.sdk.bixby2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
