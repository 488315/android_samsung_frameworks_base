package com.android.server.sepunion.cover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.samsung.android.sepunion.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ResolutionMonitor {
    public final Runnable mCallbackRunnable;
    public Configuration mConfiguration;
    public final Context mContext;
    public final Handler mHandler;

    public ResolutionMonitor(Context context, Looper looper, Runnable runnable) {
        this.mContext = context;
        this.mHandler = new Handler(looper);
        this.mCallbackRunnable = runnable;
        this.mConfiguration = context.getResources().getConfiguration();
        context.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.sepunion.cover.ResolutionMonitor.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.CONFIGURATION_CHANGED".equals(intent.getAction())) {
                    Configuration configuration = ResolutionMonitor.this.mContext.getResources().getConfiguration();
                    int updateFrom = ResolutionMonitor.this.mConfiguration.updateFrom(configuration);
                    Log.d("CoverManager_ResolutionMonitor", "onReceive " + updateFrom);
                    if ((updateFrom & 536875008) == 536875008) {
                        ResolutionMonitor resolutionMonitor = ResolutionMonitor.this;
                        resolutionMonitor.mHandler.post(resolutionMonitor.mCallbackRunnable);
                    }
                    ResolutionMonitor.this.mConfiguration = new Configuration(configuration);
                }
            }
        }, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.CONFIGURATION_CHANGED"));
    }
}
