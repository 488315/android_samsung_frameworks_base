package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes.dex */
public class LocalBluetoothAdapter {
    public static LocalBluetoothAdapter sInstance;
    public final BluetoothAdapter mAdapter;
    public long mLastScan;
    public LocalBluetoothProfileManager mProfileManager;
    public int mState = Integer.MIN_VALUE;

    private LocalBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.mAdapter = bluetoothAdapter;
    }

    public static List getFilteredGroupList(List list, long j, Function function) {
        HashSet hashSet = new HashSet();
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) it.next();
            Long l = (Long) function.apply(bluetoothDevice);
            if (l.longValue() == j) {
                arrayList.add(bluetoothDevice);
            } else if (!hashSet.contains(l)) {
                hashSet.add(l);
                arrayList.add(bluetoothDevice);
            }
        }
        return arrayList;
    }

    public static synchronized LocalBluetoothAdapter getInstance() {
        BluetoothAdapter defaultAdapter;
        try {
            if (sInstance == null && (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) != null) {
                sInstance = new LocalBluetoothAdapter(defaultAdapter);
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    public final void setBluetoothEnabled(boolean z) {
        if (z ? this.mAdapter.enable() : this.mAdapter.disable()) {
            setBluetoothStateInt(z ? 11 : 13);
        } else if (this.mAdapter.getState() != this.mState) {
            setBluetoothStateInt(this.mAdapter.getState());
        }
    }

    public final synchronized void setBluetoothStateInt(int i) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        synchronized (this) {
            if (this.mState == i) {
                return;
            }
            this.mState = i;
            if (i == 12 && (localBluetoothProfileManager = this.mProfileManager) != null) {
                localBluetoothProfileManager.updateLocalProfiles();
                BluetoothEventManager bluetoothEventManager = localBluetoothProfileManager.mEventManager;
                bluetoothEventManager.readRestoredDevices();
                bluetoothEventManager.readPairedDevices();
            }
        }
    }
}
