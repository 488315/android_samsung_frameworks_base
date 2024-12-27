package com.samsung.android.knox.analytics.activation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;

import com.samsung.android.knox.analytics.util.Log;

public final class DevicePolicyListener extends BroadcastReceiver {
    public static final String ACTION_DEVICE_OWNER_CHANGED =
            "android.app.action.DEVICE_OWNER_CHANGED";
    public static final String ACTION_PROFILE_OWNER_ADDED =
            "android.intent.action.MANAGED_PROFILE_ADDED";
    public static final String ACTION_PROFILE_OWNER_REMOVED =
            "android.intent.action.MANAGED_PROFILE_REMOVED";
    public static final String EXTRA_DO_CHANGED_STATUS =
            "com.samsung.android.knox.intent.extra.EXTRA_DO_CHANGED_STATUS";
    public static final String EXTRA_DO_PO_PACKAGE_NAME =
            "com.samsung.android.knox.intent.extra.EXTRA_DO_PO_PACKAGE_NAME";
    public static final String TAG = "[KnoxAnalytics] DevicePolicyListener";
    public final ActivationMonitor mActivationMonitor;
    public final Context mContext;

    public DevicePolicyListener(ActivationMonitor activationMonitor, Context context) {
        this.mActivationMonitor = activationMonitor;
        this.mContext = context;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive(" + intent.getAction() + ")");
        String action = intent.getAction();
        action.getClass();
        switch (action) {
            case "android.intent.action.MANAGED_PROFILE_ADDED":
                UserHandle userHandle = (UserHandle) intent.getExtra("android.intent.extra.USER");
                if (userHandle != null) {
                    this.mActivationMonitor.onPoAdded(userHandle.getIdentifier());
                    break;
                }
                break;
            case "android.app.action.DEVICE_OWNER_CHANGED":
                this.mActivationMonitor.checkDO(
                        intent.getStringExtra(EXTRA_DO_PO_PACKAGE_NAME),
                        intent.getBooleanExtra(EXTRA_DO_CHANGED_STATUS, false));
                break;
            case "android.intent.action.MANAGED_PROFILE_REMOVED":
                this.mActivationMonitor.onPoRemoved(
                        intent.getStringExtra(EXTRA_DO_PO_PACKAGE_NAME));
                break;
        }
    }

    public final void register() {
        Log.d(TAG, "start()");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_DEVICE_OWNER_CHANGED);
        intentFilter.addAction(ACTION_PROFILE_OWNER_ADDED);
        intentFilter.addAction(ACTION_PROFILE_OWNER_REMOVED);
        this.mContext.registerReceiver(this, intentFilter);
    }
}
