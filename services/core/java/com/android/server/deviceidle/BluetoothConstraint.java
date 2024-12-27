package com.android.server.deviceidle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

import com.android.server.DeviceIdleInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BluetoothConstraint implements IDeviceIdleConstraint {
    public final BluetoothManager mBluetoothManager;
    public final Context mContext;
    public final Handler mHandler;
    public final DeviceIdleInternal mLocalService;
    public volatile boolean mConnected = true;
    public volatile boolean mMonitoring = false;
    final BroadcastReceiver mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.deviceidle.BluetoothConstraint.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("android.bluetooth.device.action.ACL_CONNECTED"
                            .equals(intent.getAction())) {
                        BluetoothConstraint.this.mLocalService.exitIdle("bluetooth");
                        return;
                    }
                    BluetoothConstraint bluetoothConstraint = BluetoothConstraint.this;
                    boolean isBluetoothConnected =
                            BluetoothConstraint.isBluetoothConnected(
                                    bluetoothConstraint.mBluetoothManager);
                    if (isBluetoothConnected != bluetoothConstraint.mConnected) {
                        bluetoothConstraint.mConnected = isBluetoothConnected;
                        bluetoothConstraint.mLocalService.onConstraintStateChanged(
                                bluetoothConstraint, bluetoothConstraint.mConnected);
                    }
                }
            };
    public final BluetoothConstraint$$ExternalSyntheticLambda0 mTimeoutCallback =
            new Runnable() { // from class:
                             // com.android.server.deviceidle.BluetoothConstraint$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothConstraint bluetoothConstraint = BluetoothConstraint.this;
                    synchronized (bluetoothConstraint) {
                        if (bluetoothConstraint.mMonitoring) {
                            bluetoothConstraint.mMonitoring = false;
                            bluetoothConstraint.mLocalService.onConstraintStateChanged(
                                    bluetoothConstraint, false);
                        }
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.deviceidle.BluetoothConstraint$$ExternalSyntheticLambda0] */
    public BluetoothConstraint(
            Context context, Handler handler, DeviceIdleInternal deviceIdleInternal) {
        this.mContext = context;
        this.mHandler = handler;
        this.mLocalService = deviceIdleInternal;
        this.mBluetoothManager =
                (BluetoothManager) context.getSystemService(BluetoothManager.class);
    }

    public static boolean isBluetoothConnected(BluetoothManager bluetoothManager) {
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        return adapter != null
                && adapter.isEnabled()
                && bluetoothManager.getConnectedDevices(7).size() > 0;
    }

    public final synchronized void startMonitoring() {
        this.mConnected = true;
        this.mMonitoring = true;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(Message.obtain(handler, this.mTimeoutCallback), 1200000L);
        boolean isBluetoothConnected = isBluetoothConnected(this.mBluetoothManager);
        if (isBluetoothConnected != this.mConnected) {
            this.mConnected = isBluetoothConnected;
            this.mLocalService.onConstraintStateChanged(this, this.mConnected);
        }
    }

    public final synchronized void stopMonitoring() {
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mHandler.removeCallbacks(this.mTimeoutCallback);
        this.mMonitoring = false;
    }
}
