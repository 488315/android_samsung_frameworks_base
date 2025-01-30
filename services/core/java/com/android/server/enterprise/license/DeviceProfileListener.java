package com.android.server.enterprise.license;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class DeviceProfileListener extends BroadcastReceiver {
    public static final String TAG = "[EnterpriseLicenseService] " + DeviceProfileListener.class.getSimpleName();
    public final Context mContext;
    public List mObservers = new ArrayList();

    public DeviceProfileListener(Context context) {
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED);
        intentFilter.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED);
        intentFilter.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED);
        context.registerReceiver(this, intentFilter);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive(" + intent.getAction() + ")");
        String action = intent.getAction();
        action.hashCode();
        switch (action) {
            case "android.intent.action.MANAGED_PROFILE_ADDED":
                UserHandle userHandle = (UserHandle) intent.getExtra("android.intent.extra.USER");
                if (userHandle != null) {
                    notifyProfileOwnerAdded(userHandle.getIdentifier());
                    break;
                }
                break;
            case "android.app.action.DEVICE_OWNER_CHANGED":
                String stringExtra = intent.getStringExtra(DevicePolicyListener.EXTRA_DO_PO_PACKAGE_NAME);
                if (intent.getBooleanExtra(DevicePolicyListener.EXTRA_DO_CHANGED_STATUS, false)) {
                    notifyDeviceOwnerAdded(stringExtra);
                    break;
                } else {
                    notifyDeviceOwnerRemoved(stringExtra);
                    break;
                }
            case "android.intent.action.MANAGED_PROFILE_REMOVED":
                UserHandle userHandle2 = (UserHandle) intent.getExtra("android.intent.extra.USER");
                if (userHandle2 != null) {
                    notifyProfileOwnerRemoved(userHandle2.getIdentifier());
                    break;
                }
                break;
        }
    }

    public void registerObserver(IDeviceProfileObserver iDeviceProfileObserver) {
        Log.d(TAG, "registerObserver()");
        this.mObservers.add(iDeviceProfileObserver);
    }

    public final void notifyProfileOwnerRemoved(int i) {
        Log.d(TAG, "notifyProfileOwnerRemoved()");
        Iterator it = this.mObservers.iterator();
        while (it.hasNext()) {
            ((IDeviceProfileObserver) it.next()).onProfileOwnerRemoved(i);
        }
    }

    public final void notifyProfileOwnerAdded(int i) {
        Log.d(TAG, "notifyProfileOwnerAdded()");
        Iterator it = this.mObservers.iterator();
        while (it.hasNext()) {
            ((IDeviceProfileObserver) it.next()).onProfileOwnerAdded(i);
        }
    }

    public final void notifyDeviceOwnerAdded(String str) {
        Log.d(TAG, "notifyDeviceOwnerAdded()");
        Iterator it = this.mObservers.iterator();
        while (it.hasNext()) {
            ((IDeviceProfileObserver) it.next()).onDeviceOwnerAdded(str);
        }
    }

    public final void notifyDeviceOwnerRemoved(String str) {
        Log.d(TAG, "notifyDeviceOwnerRemoved()");
        Iterator it = this.mObservers.iterator();
        while (it.hasNext()) {
            ((IDeviceProfileObserver) it.next()).onDeviceOwnerRemoved(str);
        }
    }
}
