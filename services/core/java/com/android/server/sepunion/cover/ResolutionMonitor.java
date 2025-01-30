package com.android.server.sepunion.cover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import com.samsung.android.sepunion.Log;

/* loaded from: classes3.dex */
public class ResolutionMonitor {
    public static final String TAG = "CoverManager_" + ResolutionMonitor.class.getSimpleName();
    public final Runnable mCallbackRunnable;
    public Configuration mConfiguration;
    public final Context mContext;
    public final Handler mHandler;

    public ResolutionMonitor(Context context, Looper looper, Runnable runnable) {
        this.mContext = context;
        this.mHandler = new Handler(looper);
        this.mCallbackRunnable = runnable;
        this.mConfiguration = context.getResources().getConfiguration();
        registerReceiver();
    }

    public final void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.sepunion.cover.ResolutionMonitor.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.CONFIGURATION_CHANGED".equals(intent.getAction())) {
                    Configuration configuration = ResolutionMonitor.this.mContext.getResources().getConfiguration();
                    int updateFrom = ResolutionMonitor.this.mConfiguration.updateFrom(configuration);
                    Log.d(ResolutionMonitor.TAG, "onReceive " + updateFrom);
                    if ((updateFrom & 536875008) == 536875008) {
                        ResolutionMonitor.this.mHandler.post(ResolutionMonitor.this.mCallbackRunnable);
                    }
                    ResolutionMonitor.this.mConfiguration = new Configuration(configuration);
                }
            }
        }, intentFilter);
    }
}
