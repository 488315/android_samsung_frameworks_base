package com.samsung.android.sdk.scs.base.connection;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.samsung.android.sdk.scs.base.utils.Log;

/* loaded from: classes4.dex */
public final class ConnectionManager {
    public Context mContext;
    public InternalServiceConnectionListener mInternalServiceConnectionListener;
    public boolean mIsConnected = false;
    public final AnonymousClass1 mServiceConnection = new ServiceConnection() { // from class: com.samsung.android.sdk.scs.base.connection.ConnectionManager.1
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("ScsApi@ConnectionManager", "onServiceConnected " + componentName);
            ConnectionManager.this.notifyServiceConnection(1, componentName, iBinder);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.d("ScsApi@ConnectionManager", "onServiceDisconnected " + componentName);
            ConnectionManager.this.notifyServiceConnection(2, null, null);
        }
    };

    public final void disconnect() {
        Log.d("ScsApi@ConnectionManager", "disConnectService mIsConnected = " + this.mIsConnected);
        if (this.mIsConnected) {
            Log.d("ScsApi@ConnectionManager", "unbindService");
            this.mIsConnected = false;
            this.mContext.unbindService(this.mServiceConnection);
            notifyServiceConnection(2, null, null);
        }
    }

    public final boolean isServiceConnected() {
        Log.d("ScsApi@ConnectionManager", "isServiceConnected = " + this.mIsConnected);
        return this.mIsConnected;
    }

    public final void notifyServiceConnection(int i, ComponentName componentName, IBinder iBinder) {
        Log.d("ScsApi@ConnectionManager", "notifyServiceConnection : " + i);
        InternalServiceConnectionListener internalServiceConnectionListener = this.mInternalServiceConnectionListener;
        if (internalServiceConnectionListener != null) {
            if (i == 1) {
                this.mIsConnected = true;
                internalServiceConnectionListener.onConnected(componentName, iBinder);
            } else if (i == 2) {
                this.mIsConnected = false;
                internalServiceConnectionListener.onDisconnected(componentName);
            } else {
                if (i != 3) {
                    return;
                }
                this.mIsConnected = false;
                internalServiceConnectionListener.onError();
            }
        }
    }
}
