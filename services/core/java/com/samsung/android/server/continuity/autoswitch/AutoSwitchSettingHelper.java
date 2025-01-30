package com.samsung.android.server.continuity.autoswitch;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import android.util.Log;
import com.samsung.android.server.continuity.autoswitch.BluetoothDeviceDb;
import com.samsung.android.server.continuity.common.ExecutorUtil;
import com.samsung.android.server.continuity.common.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class AutoSwitchSettingHelper {
    public final Consumer mAutoSwitchModeChangedConsumer;
    public final Context mContext;
    public boolean mIsAutoSwitchModeEnabled;
    public boolean mIsRegisterBtStateReceiver;
    public final ArrayList mAutoSwitchableDevices = new ArrayList(3);
    public final BroadcastReceiver mBtStateReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED".equals(action) || "android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                Log.i("[MCF_DS_SYS]_AutoSwitchSettingHelper", "onReceive bt state : " + intExtra);
                if (intExtra == 15 || intExtra == 12) {
                    AutoSwitchSettingHelper.this.checkAutoSwitchEnabled();
                }
            }
        }
    };
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
            }
            switch (action) {
                case "com.samsung.intent.action.SETTINGS_WIFI_BLUETOOTH_RESET":
                case "android.intent.action.SETTINGS_SOFT_RESET":
                    Log.i("[MCF_DS_SYS]_AutoSwitchSettingHelper", "mReceiver.onReceive - RESET : " + action);
                    AutoSwitchSettingHelper.this.mAutoSwitchableDevices.clear();
                    AutoSwitchSettingHelper.this.setAutoSwitchModeEnabled();
                    break;
                case "com.samsung.bluetooth.device.action.AUTO_SWITCH_MODE_CHANGED":
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (bluetoothDevice != null) {
                        Log.i("[MCF_DS_SYS]_AutoSwitchSettingHelper", "mReceiver.onReceive - AUTO_SWITCH_MODE_CHANGED");
                        BluetoothDeviceDb.DeviceProperty deviceProperty = new BluetoothDeviceDb.DeviceProperty(bluetoothDevice.getAddress(), bluetoothDevice.getName());
                        if (bluetoothDevice.semGetAutoSwitchMode() == 1) {
                            AutoSwitchSettingHelper.this.addDevice(deviceProperty);
                        } else {
                            AutoSwitchSettingHelper.this.removeDevice(deviceProperty);
                        }
                        AutoSwitchSettingHelper.this.setAutoSwitchModeEnabled();
                        break;
                    }
                    break;
            }
        }
    };

    public AutoSwitchSettingHelper(Context context, Consumer consumer) {
        this.mContext = context;
        this.mAutoSwitchModeChangedConsumer = consumer;
    }

    public void start() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Log.e("[MCF_DS_SYS]_AutoSwitchSettingHelper", "start - null bluetoothAdapter");
            return;
        }
        if (defaultAdapter.semIsBleEnabled()) {
            Log.i("[MCF_DS_SYS]_AutoSwitchSettingHelper", "start - BleEnabled true");
            setStandAloneBleMode(true);
            checkAutoSwitchEnabled();
        } else {
            registerBtStateReceiver();
            setStandAloneBleMode(true);
        }
        registerObserver();
    }

    public void stop() {
        this.mIsAutoSwitchModeEnabled = false;
        unregisterObserver();
        unregisterBtStateReceiver();
    }

    public boolean isAutoSwitchModeEnabled() {
        return this.mIsAutoSwitchModeEnabled;
    }

    public final void registerObserver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.bluetooth.device.action.AUTO_SWITCH_MODE_CHANGED");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.samsung.intent.action.SETTINGS_WIFI_BLUETOOTH_RESET");
        this.mContext.registerReceiver(this.mReceiver, intentFilter2, "com.sec.android.settings.permission.WIFI_BLUETOOTH_RESET", null);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.SETTINGS_SOFT_RESET");
        this.mContext.registerReceiver(this.mReceiver, intentFilter3, "com.sec.android.settings.permission.SOFT_RESET", null);
    }

    public final void unregisterObserver() {
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    public static BluetoothDevice getRemoteDevice(String str) {
        BluetoothAdapter defaultAdapter;
        if (str == null || (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) == null) {
            return null;
        }
        return defaultAdapter.getRemoteDevice(str);
    }

    public final void addDevice(BluetoothDeviceDb.DeviceProperty deviceProperty) {
        if (this.mAutoSwitchableDevices.contains(deviceProperty)) {
            Log.d("[MCF_DS_SYS]_AutoSwitchSettingHelper", "addDevice - already exist name=" + Utils.secureName(deviceProperty.getName()) + ", bt=" + Utils.secureMac(deviceProperty.getAddress()));
            return;
        }
        Log.i("[MCF_DS_SYS]_AutoSwitchSettingHelper", "addDevice - (added) name=" + Utils.secureName(deviceProperty.getName()) + ", bt=" + Utils.secureMac(deviceProperty.getAddress()));
        this.mAutoSwitchableDevices.add(deviceProperty);
    }

    public final void removeDevice(BluetoothDeviceDb.DeviceProperty deviceProperty) {
        if (this.mAutoSwitchableDevices.remove(deviceProperty)) {
            Log.i("[MCF_DS_SYS]_AutoSwitchSettingHelper", "removeDevice - (removed) name=" + Utils.secureName(deviceProperty.getName()) + ", bt=" + Utils.secureMac(deviceProperty.getAddress()));
        }
    }

    public final void checkAutoSwitchEnabled(ArrayList arrayList) {
        Log.d("[MCF_DS_SYS]_AutoSwitchSettingHelper", "checkAutoSwitchEnabled - deviceList size:" + arrayList.size());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            BluetoothDeviceDb.DeviceProperty deviceProperty = (BluetoothDeviceDb.DeviceProperty) it.next();
            BluetoothDevice remoteDevice = getRemoteDevice(deviceProperty.getAddress());
            if (remoteDevice != null) {
                if (remoteDevice.semGetAutoSwitchMode() == 1) {
                    addDevice(deviceProperty);
                } else {
                    removeDevice(deviceProperty);
                }
            }
        }
        setAutoSwitchModeEnabled();
    }

    public final void setAutoSwitchModeEnabled() {
        boolean z = this.mAutoSwitchableDevices.size() != 0;
        if (z != this.mIsAutoSwitchModeEnabled) {
            this.mIsAutoSwitchModeEnabled = z;
            Log.i("[MCF_DS_SYS]_AutoSwitchSettingHelper", "setAutoSwitchModeEnabled - " + this.mIsAutoSwitchModeEnabled);
            this.mAutoSwitchModeChangedConsumer.accept(Boolean.valueOf(this.mIsAutoSwitchModeEnabled));
        }
    }

    public final void setStandAloneBleMode(boolean z) {
        if ("factory".equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", "Unknown"))) {
            Log.w("[MCF_DS_SYS]_AutoSwitchSettingHelper", "setStandAloneBleMode - FactoryBinary, ignore!");
            return;
        }
        try {
            BluetoothManager bluetoothManager = (BluetoothManager) this.mContext.getApplicationContext().getSystemService("bluetooth");
            if (bluetoothManager == null) {
                Log.e("[MCF_DS_SYS]_AutoSwitchSettingHelper", "setStandAloneBleMode - null bluetoothManager");
                return;
            }
            BluetoothAdapter adapter = bluetoothManager.getAdapter();
            if (adapter == null) {
                Log.e("[MCF_DS_SYS]_AutoSwitchSettingHelper", "setStandAloneBleMode - null bluetoothAdapter");
                return;
            }
            Log.i("[MCF_DS_SYS]_AutoSwitchSettingHelper", "setStandAloneBleMode - " + z);
            if (adapter.semSetStandAloneBleMode(z)) {
                return;
            }
            Log.w("[MCF_DS_SYS]_AutoSwitchSettingHelper", "setStandAloneBleMode - Failed to set StandAlone Ble Mode");
        } catch (SecurityException e) {
            Log.w("[MCF_DS_SYS]_AutoSwitchSettingHelper", "setStandAloneBleMode - SecurityException " + e.getMessage());
        }
    }

    public final void checkAutoSwitchEnabled() {
        unregisterBtStateReceiver();
        ExecutorUtil.executeOnIO(new Runnable() { // from class: com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AutoSwitchSettingHelper.this.lambda$checkAutoSwitchEnabled$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkAutoSwitchEnabled$1() {
        final ArrayList retrieveBackupDevices = BluetoothDeviceDb.retrieveBackupDevices(this.mContext.getApplicationContext());
        ExecutorUtil.executeOnMain(new Runnable() { // from class: com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AutoSwitchSettingHelper.this.lambda$checkAutoSwitchEnabled$0(retrieveBackupDevices);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkAutoSwitchEnabled$0(ArrayList arrayList) {
        checkAutoSwitchEnabled(arrayList);
        setStandAloneBleMode(false);
    }

    public final void registerBtStateReceiver() {
        if (this.mIsRegisterBtStateReceiver) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mContext.registerReceiver(this.mBtStateReceiver, intentFilter);
        this.mIsRegisterBtStateReceiver = true;
    }

    public final void unregisterBtStateReceiver() {
        if (this.mIsRegisterBtStateReceiver) {
            this.mIsRegisterBtStateReceiver = false;
            this.mContext.unregisterReceiver(this.mBtStateReceiver);
        }
    }

    public void disableAutoSwitchDevices() {
        Log.d("[MCF_DS_SYS]_AutoSwitchSettingHelper", "disableAutoSwitchDevices - mAutoSwitchableDevices size:" + this.mAutoSwitchableDevices.size());
        Iterator it = this.mAutoSwitchableDevices.iterator();
        while (it.hasNext()) {
            BluetoothDevice remoteDevice = getRemoteDevice(((BluetoothDeviceDb.DeviceProperty) it.next()).getAddress());
            if (remoteDevice != null && remoteDevice.semGetAutoSwitchMode() == 1) {
                remoteDevice.semSetAutoSwitchMode(0);
            }
        }
    }

    public String getAutoSwitchDeviceAddress() {
        BluetoothDeviceDb.DeviceProperty deviceProperty;
        if (this.mAutoSwitchableDevices.isEmpty() || (deviceProperty = (BluetoothDeviceDb.DeviceProperty) this.mAutoSwitchableDevices.get(0)) == null) {
            return null;
        }
        return deviceProperty.getAddress();
    }
}
