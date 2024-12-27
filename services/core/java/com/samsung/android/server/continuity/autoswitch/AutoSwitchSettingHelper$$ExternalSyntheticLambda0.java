package com.samsung.android.server.continuity.autoswitch;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;

import com.samsung.android.server.continuity.common.ExecutorUtil;
import com.samsung.android.server.continuity.sem.SemWrapper;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class AutoSwitchSettingHelper$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ AutoSwitchSettingHelper f$0;

    @Override // java.lang.Runnable
    public final void run() {
        final AutoSwitchSettingHelper autoSwitchSettingHelper = this.f$0;
        Context applicationContext = autoSwitchSettingHelper.mContext.getApplicationContext();
        final ArrayList arrayList = new ArrayList(5);
        Uri parse = Uri.parse("content://com.samsung.bt.btservice.btsettingsprovider/bonddevice");
        if (applicationContext.getContentResolver() != null) {
            try {
                Cursor query =
                        applicationContext
                                .getContentResolver()
                                .query(
                                        parse,
                                        null,
                                        "bond_state == 2 OR bond_state == 1 OR bond_state == 4",
                                        null,
                                        "timestamp DESC");
                if (query != null) {
                    try {
                        query.moveToFirst();
                        while (!query.isAfterLast()) {
                            String string = query.getString(query.getColumnIndex("address"));
                            if (string != null) {
                                arrayList.add(
                                        new BluetoothDeviceDb$DeviceProperty(
                                                string,
                                                query.getString(query.getColumnIndex("name"))));
                                query.moveToNext();
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            query.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                Log.e("[MCF_DS_SYS]_BluetoothDeviceDb", "retrieveBackupDevices - Exception : " + e);
            }
        }
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BluetoothAdapter defaultAdapter;
                        AutoSwitchSettingHelper autoSwitchSettingHelper2 =
                                AutoSwitchSettingHelper.this;
                        ArrayList arrayList2 = arrayList;
                        autoSwitchSettingHelper2.getClass();
                        Log.d(
                                "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                                "checkAutoSwitchEnabled - deviceList size:" + arrayList2.size());
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            BluetoothDeviceDb$DeviceProperty bluetoothDeviceDb$DeviceProperty =
                                    (BluetoothDeviceDb$DeviceProperty) it.next();
                            String str = bluetoothDeviceDb$DeviceProperty.mAddress;
                            BluetoothDevice bluetoothDevice = null;
                            if (str != null
                                    && (defaultAdapter = BluetoothAdapter.getDefaultAdapter())
                                            != null) {
                                bluetoothDevice = defaultAdapter.getRemoteDevice(str);
                            }
                            if (bluetoothDevice != null) {
                                UserHandle userHandle = SemWrapper.SEM_ALL;
                                if (bluetoothDevice.semGetAutoSwitchMode() == 1) {
                                    autoSwitchSettingHelper2.addDevice(
                                            bluetoothDeviceDb$DeviceProperty);
                                } else {
                                    autoSwitchSettingHelper2.removeDevice(
                                            bluetoothDeviceDb$DeviceProperty);
                                }
                            }
                        }
                        autoSwitchSettingHelper2.setAutoSwitchModeEnabled();
                        autoSwitchSettingHelper2.setStandAloneBleMode(false);
                    }
                };
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            runnable.run();
        } else {
            ExecutorUtil.sHandler.post(runnable);
        }
    }
}
