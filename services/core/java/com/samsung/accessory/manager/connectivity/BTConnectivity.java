package com.samsung.accessory.manager.connectivity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public final class BTConnectivity extends Connectivity {
    public static final UUID MY_UUID_INSECURE =
            UUID.fromString("0172c870-6e31-11e4-9803-0800200c9a66");
    public static final String TAG = "SAccessoryManager_BTConnectivity";
    public final BluetoothAdapter mAdapter;
    public final AdapterStateChangedHandler mAdapterStateChangedHandler;
    public BTStateReceiver mBTStateReceiver;

    public final class AdapterStateChangedHandler {
        public AdapterStateChangedHandler() {}
    }

    public final class BTStateReceiver extends BroadcastReceiver {
        public BTStateReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                AdapterStateChangedHandler adapterStateChangedHandler =
                        BTConnectivity.this.mAdapterStateChangedHandler;
                adapterStateChangedHandler.getClass();
                int intExtra =
                        intent.getIntExtra(
                                "android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                BTConnectivity bTConnectivity = BTConnectivity.this;
                if (intExtra == 10) {
                    bTConnectivity.mStateChangedCallback.onStateChanged(2);
                } else {
                    if (intExtra != 12) {
                        return;
                    }
                    bTConnectivity.mStateChangedCallback.onStateChanged(1);
                }
            }
        }
    }

    public BTConnectivity(Context context) {
        super(context);
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mAdapterStateChangedHandler = new AdapterStateChangedHandler();
        this.mBTStateReceiver = new BTStateReceiver();
        context.registerReceiver(
                this.mBTStateReceiver,
                new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void close() {
        BTStateReceiver bTStateReceiver = this.mBTStateReceiver;
        if (bTStateReceiver != null) {
            this.mContext.unregisterReceiver(bTStateReceiver);
            this.mBTStateReceiver = null;
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void connect() {
        Log.d(TAG, "connect");
        try {
            this.mAdapter
                    .getRemoteDevice((String) null)
                    .createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE)
                    .connect();
            AuthenticationSession.AnonymousClass1 anonymousClass1 = this.mStateChangedCallback;
            if (anonymousClass1 != null) {
                anonymousClass1.onConnectionStateChanged();
            }
        } catch (IOException unused) {
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean disable() {
        Log.d(TAG, "disable");
        return this.mAdapter.disable();
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void disconnect() {}

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void dump(PrintWriter printWriter) {
        printWriter.println(" Current BTConnectivity state:");
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean enable() {
        Log.d(TAG, "enable");
        if (!this.mAdapter.enable()) {
            return false;
        }
        this.mEnabledInternal = true;
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean isEnabled() {
        boolean isEnabled = this.mAdapter.isEnabled();
        Log.d(TAG, "isEnabled = " + isEnabled);
        return isEnabled;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean openNode() {
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final byte[] sendStartAuth(AuthenticationResult authenticationResult) {
        return Connectivity.NOT_SUPPORT;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean sendStopAuth() {
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void sendStopUsbAuth() {}

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final byte[] sendSynchronously(byte[] bArr, AuthenticationResult authenticationResult) {
        return null;
    }
}
