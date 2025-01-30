package com.samsung.android.mcfds.lib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.mcfds.lib.IMcfDeviceSyncService;
import com.samsung.android.mcfds.lib.common.AbstractManager$CoreInterface;
import com.samsung.android.mcfds.lib.common.ISimpleCallback;

/* loaded from: classes2.dex */
public abstract class AbstractDeviceSyncManager {
    public final Context mContext;
    public StateListener mListener;
    public IMcfDeviceSyncService mService;
    public int mServiceState;
    public final AbstractManager$CoreInterface mCoreInterface = new AbstractManager$CoreInterface() { // from class: com.samsung.android.mcfds.lib.AbstractDeviceSyncManager.1
        @Override // com.samsung.android.mcfds.lib.common.AbstractManager$CoreInterface
        public Message obtain(int i, Bundle bundle) {
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.obj = bundle;
            return obtain;
        }

        @Override // com.samsung.android.mcfds.lib.common.AbstractManager$CoreInterface
        public int sendMessage(Message message) {
            if (AbstractDeviceSyncManager.this.mService != null) {
                try {
                    return AbstractDeviceSyncManager.this.mService.internalCommand(message);
                } catch (RemoteException e) {
                    Log.w("[MCF_DS_LIB]_DeviceSyncManager", "sendMessage : [ " + message.what + " ] " + e.getMessage());
                    return -1;
                }
            }
            Log.w("[MCF_DS_LIB]_DeviceSyncManager", "sendMessage : Service is invalid");
            return -1;
        }
    };
    public final ISimpleCallback mServiceStateListener = new ISimpleCallback.Stub() { // from class: com.samsung.android.mcfds.lib.AbstractDeviceSyncManager.2
        @Override // com.samsung.android.mcfds.lib.common.ISimpleCallback
        public void onCallback(Message message) {
            if (message.what == 10000) {
                Log.d("[MCF_DS_LIB]_DeviceSyncManager", "mServiceStateListener - " + message.arg1);
                if (message.arg1 == 1) {
                    AbstractDeviceSyncManager.this.mServiceState = 5;
                    AbstractDeviceSyncManager.this.start();
                    AbstractDeviceSyncManager.this.notifyStateChanged(5);
                } else {
                    if (AbstractDeviceSyncManager.this.mServiceState != 5) {
                        AbstractDeviceSyncManager.this.mServiceState = 4;
                        return;
                    }
                    AbstractDeviceSyncManager.this.mServiceState = 4;
                    AbstractDeviceSyncManager.this.stop();
                    AbstractDeviceSyncManager.this.notifyStateChanged(4);
                }
            }
        }
    };
    public final ServiceConnection mServiceConnection = new ServiceConnection() { // from class: com.samsung.android.mcfds.lib.AbstractDeviceSyncManager.3
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("[MCF_DS_LIB]_DeviceSyncManager", "onServiceConnected");
            AbstractDeviceSyncManager.this.mServiceState = 3;
            AbstractDeviceSyncManager.this.mService = IMcfDeviceSyncService.Stub.asInterface(iBinder);
            AbstractDeviceSyncManager.this.notifyStateChanged(3);
            AbstractDeviceSyncManager.this.registerServiceStateListener();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("[MCF_DS_LIB]_DeviceSyncManager", "onServiceDisconnected");
            AbstractDeviceSyncManager.this.mServiceState = 1;
            AbstractDeviceSyncManager.this.mService = null;
            AbstractDeviceSyncManager.this.stop();
            AbstractDeviceSyncManager.this.notifyStateChanged(1);
        }
    };

    public interface StateListener {
        void onStateChanged(int i);
    }

    public abstract boolean bindService(UserHandle userHandle, Intent intent, ServiceConnection serviceConnection);

    public void start() {
    }

    public void stop() {
    }

    public AbstractDeviceSyncManager(Context context) {
        this.mContext = context;
    }

    public int getNearbyDeviceCount(int i) {
        Log.i("[MCF_DS_LIB]_DeviceSyncManager", "getNearbyDeviceCount");
        Bundle bundle = new Bundle();
        bundle.putInt("KEY_INPUT", i);
        AbstractManager$CoreInterface abstractManager$CoreInterface = this.mCoreInterface;
        return abstractManager$CoreInterface.sendMessage(abstractManager$CoreInterface.obtain(1000, bundle));
    }

    public int initMcfDeviceSyncMainController(int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("initMcfDeviceSyncMainController, bindReason: ");
        sb.append(i);
        sb.append(", hasAutoSwitchDeviceMac: ");
        sb.append(str != null);
        Log.i("[MCF_DS_LIB]_DeviceSyncManager", sb.toString());
        Bundle bundle = new Bundle();
        bundle.putInt("KEY_BIND_REASON", i);
        bundle.putString("KEY_AUTO_SWITCH_DEVICE", str);
        AbstractManager$CoreInterface abstractManager$CoreInterface = this.mCoreInterface;
        return abstractManager$CoreInterface.sendMessage(abstractManager$CoreInterface.obtain(1002, bundle));
    }

    public final void registerServiceStateListener() {
        Bundle bundle = new Bundle();
        bundle.putBinder("CALLBACK", this.mServiceStateListener.asBinder());
        AbstractManager$CoreInterface abstractManager$CoreInterface = this.mCoreInterface;
        abstractManager$CoreInterface.sendMessage(abstractManager$CoreInterface.obtain(10, bundle));
    }

    public final void unregisterServiceStateListener() {
        AbstractManager$CoreInterface abstractManager$CoreInterface = this.mCoreInterface;
        abstractManager$CoreInterface.sendMessage(abstractManager$CoreInterface.obtain(11, null));
    }

    public boolean connectServiceAsUser(StateListener stateListener, UserHandle userHandle) {
        int i = this.mServiceState;
        if (i != 0 && i != 1) {
            Log.w("[MCF_DS_LIB]_DeviceSyncManager", "connectServiceAsUser : invalid request " + this.mServiceState);
            return true;
        }
        Intent intent = new Intent("com.samsung.android.mcfds.ACTION_START");
        intent.setComponent(new ComponentName("com.samsung.android.mcfds", "com.samsung.android.mcfds.McfDeviceSyncService"));
        intent.putExtra("Caller", this.mContext.getPackageName());
        if (bindService(userHandle, intent, this.mServiceConnection)) {
            Log.i("[MCF_DS_LIB]_DeviceSyncManager", "connectServiceAsUser : success " + userHandle.toString());
            this.mServiceState = 2;
            this.mListener = stateListener;
            return true;
        }
        Log.w("[MCF_DS_LIB]_DeviceSyncManager", "connectService : failed");
        return false;
    }

    public void disconnectService() {
        if (this.mServiceState == 0) {
            Log.w("[MCF_DS_LIB]_DeviceSyncManager", "disconnectService : invalid request");
            return;
        }
        Log.i("[MCF_DS_LIB]_DeviceSyncManager", "disconnectService");
        unregisterServiceStateListener();
        stop();
        try {
            this.mContext.unbindService(this.mServiceConnection);
        } catch (IllegalArgumentException unused) {
            Log.w("[MCF_DS_LIB]_DeviceSyncManager", "disconnectService : IllegalArgumentException");
        }
        this.mServiceState = 0;
        this.mService = null;
        this.mListener = null;
    }

    public int getServiceState() {
        return this.mServiceState;
    }

    public final void notifyStateChanged(int i) {
        StateListener stateListener = this.mListener;
        if (stateListener != null) {
            stateListener.onStateChanged(i);
        }
    }
}
