package com.samsung.android.server.continuity.autoswitch;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SemSystemProperties;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;

import com.samsung.android.server.continuity.AbstractPreconditionObserver$$ExternalSyntheticLambda1;
import com.samsung.android.server.continuity.common.Utils;
import com.samsung.android.server.continuity.sem.SemWrapper;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AutoSwitchSettingHelper {
    public final Consumer mAutoSwitchModeChangedConsumer;
    public final ArrayList mAutoSwitchableDevices = new ArrayList(3);
    public final AnonymousClass1 mBtStateReceiver;
    public final Context mContext;
    public boolean mIsAutoSwitchModeEnabled;
    public boolean mIsRegisterBtStateReceiver;
    public final AnonymousClass1 mReceiver;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$1] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$1] */
    public AutoSwitchSettingHelper(
            Context context,
            AbstractPreconditionObserver$$ExternalSyntheticLambda1
                    abstractPreconditionObserver$$ExternalSyntheticLambda1) {
        this.mContext = context;
        this.mAutoSwitchModeChangedConsumer =
                abstractPreconditionObserver$$ExternalSyntheticLambda1;
        final int i = 0;
        this.mBtStateReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper.1
                    public final /* synthetic */ AutoSwitchSettingHelper this$0;

                    {
                        this.this$0 = this;
                    }

                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    /* JADX WARN: Code restructure failed: missing block: B:29:0x0037, code lost:

                       if (r2.equals("com.samsung.intent.action.SETTINGS_WIFI_BLUETOOTH_RESET") == false) goto L9;
                    */
                    @Override // android.content.BroadcastReceiver
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onReceive(
                            android.content.Context r6, android.content.Intent r7) {
                        /*
                            r5 = this;
                            r6 = 1
                            r0 = 0
                            java.lang.String r1 = "[MCF_DS_SYS]_AutoSwitchSettingHelper"
                            int r2 = r2
                            switch(r2) {
                                case 0: goto L8a;
                                default: goto L9;
                            }
                        L9:
                            java.lang.String r2 = r7.getAction()
                            if (r2 != 0) goto L11
                            goto L89
                        L11:
                            r3 = -1
                            int r4 = r2.hashCode()
                            switch(r4) {
                                case -1981957079: goto L31;
                                case -1560387183: goto L26;
                                case -585356236: goto L1b;
                                default: goto L19;
                            }
                        L19:
                            r0 = r3
                            goto L3a
                        L1b:
                            java.lang.String r0 = "com.samsung.bluetooth.device.action.AUTO_SWITCH_MODE_CHANGED"
                            boolean r0 = r2.equals(r0)
                            if (r0 != 0) goto L24
                            goto L19
                        L24:
                            r0 = 2
                            goto L3a
                        L26:
                            java.lang.String r0 = "android.intent.action.SETTINGS_SOFT_RESET"
                            boolean r0 = r2.equals(r0)
                            if (r0 != 0) goto L2f
                            goto L19
                        L2f:
                            r0 = r6
                            goto L3a
                        L31:
                            java.lang.String r4 = "com.samsung.intent.action.SETTINGS_WIFI_BLUETOOTH_RESET"
                            boolean r4 = r2.equals(r4)
                            if (r4 != 0) goto L3a
                            goto L19
                        L3a:
                            switch(r0) {
                                case 0: goto L73;
                                case 1: goto L73;
                                case 2: goto L3e;
                                default: goto L3d;
                            }
                        L3d:
                            goto L89
                        L3e:
                            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper r5 = r5.this$0
                            r5.getClass()
                            java.lang.String r0 = "android.bluetooth.device.extra.DEVICE"
                            android.os.Parcelable r7 = r7.getParcelableExtra(r0)
                            android.bluetooth.BluetoothDevice r7 = (android.bluetooth.BluetoothDevice) r7
                            if (r7 == 0) goto L89
                            java.lang.String r0 = "mReceiver.onReceive - AUTO_SWITCH_MODE_CHANGED"
                            android.util.Log.i(r1, r0)
                            com.samsung.android.server.continuity.autoswitch.BluetoothDeviceDb$DeviceProperty r0 = new com.samsung.android.server.continuity.autoswitch.BluetoothDeviceDb$DeviceProperty
                            java.lang.String r1 = r7.getAddress()
                            java.lang.String r2 = r7.getName()
                            r0.<init>(r1, r2)
                            android.os.UserHandle r1 = com.samsung.android.server.continuity.sem.SemWrapper.SEM_ALL
                            int r7 = r7.semGetAutoSwitchMode()
                            if (r7 != r6) goto L6c
                            r5.addDevice(r0)
                            goto L6f
                        L6c:
                            r5.removeDevice(r0)
                        L6f:
                            r5.setAutoSwitchModeEnabled()
                            goto L89
                        L73:
                            java.lang.String r6 = "mReceiver.onReceive - RESET : "
                            java.lang.String r6 = r6.concat(r2)
                            android.util.Log.i(r1, r6)
                            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper r6 = r5.this$0
                            java.util.ArrayList r6 = r6.mAutoSwitchableDevices
                            r6.clear()
                            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper r5 = r5.this$0
                            r5.setAutoSwitchModeEnabled()
                        L89:
                            return
                        L8a:
                            java.lang.String r6 = r7.getAction()
                            java.lang.String r2 = "com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED"
                            boolean r2 = r2.equals(r6)
                            if (r2 != 0) goto L9e
                            java.lang.String r2 = "android.bluetooth.adapter.action.STATE_CHANGED"
                            boolean r6 = r2.equals(r6)
                            if (r6 == 0) goto Ld2
                        L9e:
                            java.lang.String r6 = "android.bluetooth.adapter.extra.STATE"
                            r2 = 10
                            int r6 = r7.getIntExtra(r6, r2)
                            java.lang.String r7 = "onReceive bt state : "
                            com.android.server.DirEncryptService$$ExternalSyntheticOutline0.m(r6, r7, r1)
                            r7 = 15
                            if (r6 == r7) goto Lb4
                            r7 = 12
                            if (r6 != r7) goto Ld2
                        Lb4:
                            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper r5 = r5.this$0
                            boolean r6 = r5.mIsRegisterBtStateReceiver
                            if (r6 == 0) goto Lc3
                            r5.mIsRegisterBtStateReceiver = r0
                            android.content.Context r6 = r5.mContext
                            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$1 r7 = r5.mBtStateReceiver
                            r6.unregisterReceiver(r7)
                        Lc3:
                            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$$ExternalSyntheticLambda0 r6 = new com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$$ExternalSyntheticLambda0
                            r6.<init>(r5)
                            java.util.concurrent.ThreadPoolExecutor r5 = com.samsung.android.server.continuity.common.ExecutorUtil.sExecutorIO
                            com.samsung.android.server.continuity.common.ExecutorUtil$ThrowExceptionRunnable r7 = new com.samsung.android.server.continuity.common.ExecutorUtil$ThrowExceptionRunnable
                            r7.<init>(r6)
                            r5.execute(r7)
                        Ld2:
                            return
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper.AnonymousClass1.onReceive(android.content.Context,"
                                    + " android.content.Intent):void");
                    }
                };
        final int i2 = 1;
        this.mReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper.1
                    public final /* synthetic */ AutoSwitchSettingHelper this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        /*
                            this = this;
                            r6 = 1
                            r0 = 0
                            java.lang.String r1 = "[MCF_DS_SYS]_AutoSwitchSettingHelper"
                            int r2 = r2
                            switch(r2) {
                                case 0: goto L8a;
                                default: goto L9;
                            }
                        L9:
                            java.lang.String r2 = r7.getAction()
                            if (r2 != 0) goto L11
                            goto L89
                        L11:
                            r3 = -1
                            int r4 = r2.hashCode()
                            switch(r4) {
                                case -1981957079: goto L31;
                                case -1560387183: goto L26;
                                case -585356236: goto L1b;
                                default: goto L19;
                            }
                        L19:
                            r0 = r3
                            goto L3a
                        L1b:
                            java.lang.String r0 = "com.samsung.bluetooth.device.action.AUTO_SWITCH_MODE_CHANGED"
                            boolean r0 = r2.equals(r0)
                            if (r0 != 0) goto L24
                            goto L19
                        L24:
                            r0 = 2
                            goto L3a
                        L26:
                            java.lang.String r0 = "android.intent.action.SETTINGS_SOFT_RESET"
                            boolean r0 = r2.equals(r0)
                            if (r0 != 0) goto L2f
                            goto L19
                        L2f:
                            r0 = r6
                            goto L3a
                        L31:
                            java.lang.String r4 = "com.samsung.intent.action.SETTINGS_WIFI_BLUETOOTH_RESET"
                            boolean r4 = r2.equals(r4)
                            if (r4 != 0) goto L3a
                            goto L19
                        L3a:
                            switch(r0) {
                                case 0: goto L73;
                                case 1: goto L73;
                                case 2: goto L3e;
                                default: goto L3d;
                            }
                        L3d:
                            goto L89
                        L3e:
                            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper r5 = r5.this$0
                            r5.getClass()
                            java.lang.String r0 = "android.bluetooth.device.extra.DEVICE"
                            android.os.Parcelable r7 = r7.getParcelableExtra(r0)
                            android.bluetooth.BluetoothDevice r7 = (android.bluetooth.BluetoothDevice) r7
                            if (r7 == 0) goto L89
                            java.lang.String r0 = "mReceiver.onReceive - AUTO_SWITCH_MODE_CHANGED"
                            android.util.Log.i(r1, r0)
                            com.samsung.android.server.continuity.autoswitch.BluetoothDeviceDb$DeviceProperty r0 = new com.samsung.android.server.continuity.autoswitch.BluetoothDeviceDb$DeviceProperty
                            java.lang.String r1 = r7.getAddress()
                            java.lang.String r2 = r7.getName()
                            r0.<init>(r1, r2)
                            android.os.UserHandle r1 = com.samsung.android.server.continuity.sem.SemWrapper.SEM_ALL
                            int r7 = r7.semGetAutoSwitchMode()
                            if (r7 != r6) goto L6c
                            r5.addDevice(r0)
                            goto L6f
                        L6c:
                            r5.removeDevice(r0)
                        L6f:
                            r5.setAutoSwitchModeEnabled()
                            goto L89
                        L73:
                            java.lang.String r6 = "mReceiver.onReceive - RESET : "
                            java.lang.String r6 = r6.concat(r2)
                            android.util.Log.i(r1, r6)
                            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper r6 = r5.this$0
                            java.util.ArrayList r6 = r6.mAutoSwitchableDevices
                            r6.clear()
                            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper r5 = r5.this$0
                            r5.setAutoSwitchModeEnabled()
                        L89:
                            return
                        L8a:
                            java.lang.String r6 = r7.getAction()
                            java.lang.String r2 = "com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED"
                            boolean r2 = r2.equals(r6)
                            if (r2 != 0) goto L9e
                            java.lang.String r2 = "android.bluetooth.adapter.action.STATE_CHANGED"
                            boolean r6 = r2.equals(r6)
                            if (r6 == 0) goto Ld2
                        L9e:
                            java.lang.String r6 = "android.bluetooth.adapter.extra.STATE"
                            r2 = 10
                            int r6 = r7.getIntExtra(r6, r2)
                            java.lang.String r7 = "onReceive bt state : "
                            com.android.server.DirEncryptService$$ExternalSyntheticOutline0.m(r6, r7, r1)
                            r7 = 15
                            if (r6 == r7) goto Lb4
                            r7 = 12
                            if (r6 != r7) goto Ld2
                        Lb4:
                            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper r5 = r5.this$0
                            boolean r6 = r5.mIsRegisterBtStateReceiver
                            if (r6 == 0) goto Lc3
                            r5.mIsRegisterBtStateReceiver = r0
                            android.content.Context r6 = r5.mContext
                            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$1 r7 = r5.mBtStateReceiver
                            r6.unregisterReceiver(r7)
                        Lc3:
                            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$$ExternalSyntheticLambda0 r6 = new com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$$ExternalSyntheticLambda0
                            r6.<init>(r5)
                            java.util.concurrent.ThreadPoolExecutor r5 = com.samsung.android.server.continuity.common.ExecutorUtil.sExecutorIO
                            com.samsung.android.server.continuity.common.ExecutorUtil$ThrowExceptionRunnable r7 = new com.samsung.android.server.continuity.common.ExecutorUtil$ThrowExceptionRunnable
                            r7.<init>(r6)
                            r5.execute(r7)
                        Ld2:
                            return
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper.AnonymousClass1.onReceive(android.content.Context,"
                                    + " android.content.Intent):void");
                    }
                };
    }

    public final void addDevice(BluetoothDeviceDb$DeviceProperty bluetoothDeviceDb$DeviceProperty) {
        boolean contains = this.mAutoSwitchableDevices.contains(bluetoothDeviceDb$DeviceProperty);
        String str = bluetoothDeviceDb$DeviceProperty.mAddress;
        String str2 = bluetoothDeviceDb$DeviceProperty.mName;
        if (contains) {
            Log.d(
                    "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                    "addDevice - already exist name="
                            + Utils.secureName(str2)
                            + ", bt="
                            + Utils.secureMac(str));
            return;
        }
        Log.i(
                "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                "addDevice - (added) name="
                        + Utils.secureName(str2)
                        + ", bt="
                        + Utils.secureMac(str));
        this.mAutoSwitchableDevices.add(bluetoothDeviceDb$DeviceProperty);
    }

    public final void removeDevice(
            BluetoothDeviceDb$DeviceProperty bluetoothDeviceDb$DeviceProperty) {
        if (this.mAutoSwitchableDevices.remove(bluetoothDeviceDb$DeviceProperty)) {
            Log.i(
                    "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                    "removeDevice - (removed) name="
                            + Utils.secureName(bluetoothDeviceDb$DeviceProperty.mName)
                            + ", bt="
                            + Utils.secureMac(bluetoothDeviceDb$DeviceProperty.mAddress));
        }
    }

    public final void setAutoSwitchModeEnabled() {
        boolean z = this.mAutoSwitchableDevices.size() != 0;
        if (z != this.mIsAutoSwitchModeEnabled) {
            this.mIsAutoSwitchModeEnabled = z;
            FlashNotificationsController$$ExternalSyntheticOutline0.m(
                    "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                    new StringBuilder("setAutoSwitchModeEnabled - "),
                    this.mIsAutoSwitchModeEnabled);
            this.mAutoSwitchModeChangedConsumer.accept(
                    Boolean.valueOf(this.mIsAutoSwitchModeEnabled));
        }
    }

    public final void setStandAloneBleMode(boolean z) {
        boolean semSetStandAloneBleMode;
        UserHandle userHandle = SemWrapper.SEM_ALL;
        if ("factory"
                .equalsIgnoreCase(
                        SemSystemProperties.get("ro.factory.factory_binary", "Unknown"))) {
            Log.w(
                    "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                    "setStandAloneBleMode - FactoryBinary, ignore!");
            return;
        }
        try {
            BluetoothManager bluetoothManager =
                    (BluetoothManager) this.mContext.getSystemService("bluetooth");
            if (bluetoothManager == null) {
                Log.e(
                        "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                        "setStandAloneBleMode - null bluetoothManager");
                return;
            }
            BluetoothAdapter adapter = bluetoothManager.getAdapter();
            if (adapter == null) {
                Log.e(
                        "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                        "setStandAloneBleMode - null bluetoothAdapter");
                return;
            }
            Log.i("[MCF_DS_SYS]_AutoSwitchSettingHelper", "setStandAloneBleMode - " + z);
            if (Build.VERSION.SEM_PLATFORM_INT >= 160000) {
                semSetStandAloneBleMode = false;
                try {
                    semSetStandAloneBleMode =
                            ((Boolean)
                                            adapter.getClass()
                                                    .getMethod(
                                                            "semSetSystemAppStandAloneBleMode",
                                                            Boolean.TYPE,
                                                            String.class)
                                                    .invoke(
                                                            adapter,
                                                            Boolean.valueOf(z),
                                                            "autoswitch"))
                                    .booleanValue();
                } catch (Exception e) {
                    Log.w(
                            "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                            "semSetSystemAppStandAloneBleMode" + e.getMessage());
                }
            } else {
                semSetStandAloneBleMode = adapter.semSetStandAloneBleMode(z);
            }
            if (semSetStandAloneBleMode) {
                return;
            }
            Log.w(
                    "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                    "setStandAloneBleMode - Failed to set StandAlone Ble Mode");
        } catch (SecurityException e2) {
            Log.w(
                    "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                    "setStandAloneBleMode - SecurityException " + e2.getMessage());
        }
    }
}
