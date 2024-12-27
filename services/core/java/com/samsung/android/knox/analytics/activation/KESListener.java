package com.samsung.android.knox.analytics.activation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.samsung.android.knox.analytics.util.Log;

public final class KESListener extends BroadcastReceiver {
    public static final String ACTION =
            "com.sec.enterprise.knox.cloudmdm.smdms.intent.action.KES_STATE_CHANGED";
    public static final String EXTRA_ENROLL_STATUS =
            "com.sec.enterprise.knox.cloudmdm.smdms.intent.extra.ENROLLED";
    public static final String EXTRA_PACKAGE_NAME =
            "com.sec.enterprise.knox.cloudmdm.smdms.intent.extra.PACKAGE_NAME";
    public static final String KME_BROADCAST_PERMISSION =
            "com.samsung.android.knox.permission.KNOX_KES_INTERNAL";
    public static final String TAG = "[KnoxAnalytics] KESListener";
    public final ActivationMonitor mActivationMonitor;
    public final Context mContext;

    public KESListener(ActivationMonitor activationMonitor, Context context) {
        this.mContext = context;
        this.mActivationMonitor = activationMonitor;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())) {
            Log.d(TAG, "onReceive(" + intent.getAction() + ")");
            this.mActivationMonitor.checkKes(
                    intent.getStringExtra(EXTRA_PACKAGE_NAME),
                    intent.getBooleanExtra(EXTRA_ENROLL_STATUS, false));
        }
    }

    public final void register() {
        Log.d(TAG, "start()");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        this.mContext.registerReceiver(this, intentFilter, KME_BROADCAST_PERMISSION, null, 2);
    }
}
