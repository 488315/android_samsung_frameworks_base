package com.samsung.android.sdk.bixby2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ApplicationTriggerReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Log.i("ApplicationTriggerReceiver", "onReceived()");
        if (context != null) {
            context.unregisterReceiver(this);
            Log.i("ApplicationTriggerReceiver", "ApplicationTriggerReceiver unRegistered");
        }
    }
}
