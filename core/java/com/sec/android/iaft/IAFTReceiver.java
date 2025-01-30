package com.sec.android.iaft;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.p009os.Bundle;
import android.util.Log;

/* loaded from: classes6.dex */
public class IAFTReceiver extends BroadcastReceiver {
    private static final String TAG = "IAFTManager";
    public static final String TRACE_RESULT = "com.android.internal.intent.action.TRACE_RESULT";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.m94d(TAG, "IAFTReceiver onReceive.");
        if (TRACE_RESULT.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            int tid = extras.getInt("tid");
            int code = extras.getInt("code");
            int freq = extras.getInt("freq");
            IAFTManagerServiceImpl.sendResult(tid, code, freq);
        }
    }
}
