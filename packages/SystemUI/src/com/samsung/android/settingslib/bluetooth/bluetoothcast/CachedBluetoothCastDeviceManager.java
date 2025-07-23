package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.content.Context;
import android.util.Log;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CachedBluetoothCastDeviceManager {
    public final LocalBluetoothManager mBtManager;
    public final Context mContext;
    public final String TAG = getClass().getSimpleName();
    public final List mCachedCastDevices = new ArrayList();

    public CachedBluetoothCastDeviceManager(Context context, LocalBluetoothManager localBluetoothManager) {
        this.mContext = context;
        this.mBtManager = localBluetoothManager;
    }

    public final CachedBluetoothCastDevice addCastDevice(LocalBluetoothCastProfileManager localBluetoothCastProfileManager, SemBluetoothCastDevice semBluetoothCastDevice) {
        CachedBluetoothCastDevice cachedBluetoothCastDevice = new CachedBluetoothCastDevice(this.mContext, localBluetoothCastProfileManager, semBluetoothCastDevice);
        synchronized (this) {
            try {
                Log.d(this.TAG, "addCastDevice :: " + cachedBluetoothCastDevice.getName());
                if (((ArrayList) this.mCachedCastDevices).contains(cachedBluetoothCastDevice)) {
                    Log.d(this.TAG, "addCastDevice :: newDevice is added already");
                    return findCastDevice(semBluetoothCastDevice);
                }
                ((ArrayList) this.mCachedCastDevices).add(cachedBluetoothCastDevice);
                cachedBluetoothCastDevice.mSequence = ((ArrayList) this.mCachedCastDevices).indexOf(cachedBluetoothCastDevice);
                BluetoothCastEventManager bluetoothCastEventManager = this.mBtManager.mCastEventManager;
                synchronized (bluetoothCastEventManager.mCallbacks) {
                    try {
                        ArrayList arrayList = (ArrayList) bluetoothCastEventManager.mCallbacks;
                        int size = arrayList.size();
                        int i = 0;
                        while (i < size) {
                            Object obj = arrayList.get(i);
                            i++;
                            ((BluetoothCastCallback) obj).onCastDeviceAdded();
                        }
                    } finally {
                    }
                }
                return cachedBluetoothCastDevice;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final synchronized CachedBluetoothCastDevice findCastDevice(SemBluetoothCastDevice semBluetoothCastDevice) {
        ArrayList arrayList = (ArrayList) this.mCachedCastDevices;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            CachedBluetoothCastDevice cachedBluetoothCastDevice = (CachedBluetoothCastDevice) obj;
            if (cachedBluetoothCastDevice.mCastDevice.equals(semBluetoothCastDevice)) {
                return cachedBluetoothCastDevice;
            }
        }
        return null;
    }

    public final synchronized Collection getCachedCastDevicesCopy() {
        return new ArrayList(this.mCachedCastDevices);
    }
}
