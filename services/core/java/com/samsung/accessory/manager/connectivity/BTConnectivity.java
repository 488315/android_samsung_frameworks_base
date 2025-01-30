package com.samsung.accessory.manager.connectivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.connectivity.Connectivity;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/* loaded from: classes.dex */
public class BTConnectivity extends Connectivity {
    public static final UUID MY_UUID_INSECURE = UUID.fromString("0172c870-6e31-11e4-9803-0800200c9a66");
    public static final String TAG = "SAccessoryManager_" + BTConnectivity.class.getSimpleName();
    public final BluetoothAdapter mAdapter;
    public AdapterStateChangedHandler mAdapterStateChangedHandler;
    public BTStateReceiver mBTStateReceiver;
    public BluetoothSocket mSocket;

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean disconnect() {
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean openNode(AuthenticationResult authenticationResult) {
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean sendStopAuth() {
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void sendStopUsbAuth() {
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public byte[] sendSynchronously(byte[] bArr, AuthenticationResult authenticationResult) {
        return null;
    }

    public class BTStateReceiver extends BroadcastReceiver {
        public BTStateReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                BTConnectivity.this.mAdapterStateChangedHandler.onReceive(context, intent);
            }
        }
    }

    public class AdapterStateChangedHandler implements Connectivity.StateChangedHandler {
        public AdapterStateChangedHandler() {
        }

        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
            if (intExtra == 10) {
                BTConnectivity.this.mStateChangedCallback.onStateChanged(2);
            } else {
                if (intExtra != 12) {
                    return;
                }
                BTConnectivity.this.mStateChangedCallback.onStateChanged(1);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BTConnectivity(Context context) {
        super(context);
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mAdapterStateChangedHandler = new AdapterStateChangedHandler();
        this.mBTStateReceiver = new BTStateReceiver();
        context.registerReceiver(this.mBTStateReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean enable() {
        Log.d(TAG, "enable");
        if (!this.mAdapter.enable()) {
            return false;
        }
        this.mEnabledInternal = true;
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean disable() {
        Log.d(TAG, "disable");
        return this.mAdapter.disable();
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean connect(String str) {
        Log.d(TAG, "connect");
        try {
            BluetoothSocket createInsecureRfcommSocketToServiceRecord = this.mAdapter.getRemoteDevice(str).createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);
            this.mSocket = createInsecureRfcommSocketToServiceRecord;
            createInsecureRfcommSocketToServiceRecord.connect();
            Connectivity.StateChangedCallback stateChangedCallback = this.mStateChangedCallback;
            if (stateChangedCallback != null) {
                stateChangedCallback.onConnectionStateChanged(1);
            }
        } catch (IOException unused) {
        }
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean isEnabled() {
        boolean isEnabled = this.mAdapter.isEnabled();
        Log.d(TAG, "isEnabled = " + isEnabled);
        return isEnabled;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public byte[] sendStartAuth(AuthenticationResult authenticationResult) {
        return Connectivity.NOT_SUPPORT;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void close() {
        BTStateReceiver bTStateReceiver = this.mBTStateReceiver;
        if (bTStateReceiver != null) {
            this.mContext.unregisterReceiver(bTStateReceiver);
            this.mBTStateReceiver = null;
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(" Current BTConnectivity state:");
    }
}
