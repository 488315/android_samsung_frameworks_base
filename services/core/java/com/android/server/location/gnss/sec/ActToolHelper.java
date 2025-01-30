package com.android.server.location.gnss.sec;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* loaded from: classes2.dex */
public class ActToolHelper {
    public void notifyEvent(Context context, String str) {
        Log.i("ActToolHelper", "sendBroadcast to ActTool : event=" + str);
        Intent intent = new Intent();
        intent.setAction("com.salab.act.intent.LOG_ACT");
        intent.putExtra("dumpname", "GNSS_ASSERT");
        intent.putExtra("CONFI_GNSS_ASSERT", str);
        context.sendBroadcast(intent);
    }
}
