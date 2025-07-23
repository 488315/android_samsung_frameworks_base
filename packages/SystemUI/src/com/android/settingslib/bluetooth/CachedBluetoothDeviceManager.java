package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class CachedBluetoothDeviceManager {
    static int sLateBondingTimeoutMillis = 10000;
    public final LocalBluetoothManager mBtManager;
    final List<CachedBluetoothDevice> mCachedDevices;
    public final Context mContext;
    CsipDeviceManager mCsipDeviceManager;
    public final List mFilteredCachedDevices;
    HearingAidDeviceManager mHearingAidDeviceManager;
    public final Map stubInfoMap;

    public CachedBluetoothDeviceManager(Context context, LocalBluetoothManager localBluetoothManager) {
        ArrayList arrayList = new ArrayList();
        this.mFilteredCachedDevices = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mCachedDevices = arrayList2;
        this.stubInfoMap = new HashMap();
        this.mContext = context;
        this.mBtManager = localBluetoothManager;
        this.mHearingAidDeviceManager = new HearingAidDeviceManager(context, localBluetoothManager, arrayList2, arrayList);
        this.mCsipDeviceManager = new CsipDeviceManager(context, localBluetoothManager, arrayList2, arrayList);
        setStubInfo("com.samsung.android.app.watchmanagerstub");
    }

    public final CachedBluetoothDevice addDevice(LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothDevice bluetoothDevice) {
        LocalBluetoothAdapter localBluetoothAdapter = this.mBtManager.mLocalAdapter;
        CachedBluetoothDevice cachedBluetoothDevice = new CachedBluetoothDevice(this.mContext, localBluetoothProfileManager, bluetoothDevice);
        synchronized (this) {
            try {
                this.mCsipDeviceManager.initCsipDeviceIfNeeded(cachedBluetoothDevice);
                this.mHearingAidDeviceManager.initHearingAidDeviceIfNeeded(cachedBluetoothDevice);
                if (localBluetoothAdapter.mAdapter.isCustomDeviceAddress(cachedBluetoothDevice.mDevice.getAddress())) {
                    return null;
                }
                this.mCsipDeviceManager.setMemberDeviceIfNeeded(cachedBluetoothDevice);
                if (!this.mHearingAidDeviceManager.setSubDeviceIfNeeded(cachedBluetoothDevice)) {
                    if (this.mCachedDevices.contains(cachedBluetoothDevice)) {
                        Log.d("CachedBluetoothDeviceManager", "addDevice :: newDevice is added already");
                        return findDevice(bluetoothDevice);
                    }
                    boolean addDevice = addDevice(cachedBluetoothDevice);
                    cachedBluetoothDevice.mSequence = this.mCachedDevices.indexOf(cachedBluetoothDevice);
                    if (!addDevice) {
                        this.mBtManager.mEventManager.dispatchDeviceAdded(cachedBluetoothDevice);
                    }
                }
                return cachedBluetoothDevice;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final synchronized void clearNonBondedDevices() {
        try {
            for (int size = this.mCachedDevices.size() - 1; size >= 0; size--) {
                CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevices.get(size);
                if (cachedBluetoothDevice.mBondState == 10 && !cachedBluetoothDevice.mIsRestored) {
                    removeDevice(cachedBluetoothDevice);
                }
            }
            updateSequeces();
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized CachedBluetoothDevice findDevice(BluetoothDevice bluetoothDevice) {
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
            if (cachedBluetoothDevice.mDevice.equals(bluetoothDevice)) {
                return cachedBluetoothDevice;
            }
            Set<CachedBluetoothDevice> set = cachedBluetoothDevice.mMemberDevices;
            if (!set.isEmpty()) {
                for (CachedBluetoothDevice cachedBluetoothDevice2 : set) {
                    if (cachedBluetoothDevice2.mDevice.equals(bluetoothDevice)) {
                        return cachedBluetoothDevice2;
                    }
                }
            }
            CachedBluetoothDevice cachedBluetoothDevice3 = cachedBluetoothDevice.mSubDevice;
            if (cachedBluetoothDevice3 != null && cachedBluetoothDevice3.mDevice.equals(bluetoothDevice)) {
                return cachedBluetoothDevice3;
            }
        }
        return null;
    }

    public final synchronized CachedBluetoothDevice findFilteredDevice(BluetoothDevice bluetoothDevice) {
        ArrayList arrayList = (ArrayList) this.mFilteredCachedDevices;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
            if (cachedBluetoothDevice.mDevice.equals(bluetoothDevice)) {
                return cachedBluetoothDevice;
            }
        }
        return null;
    }

    public final synchronized Collection getCachedDevicesCopy() {
        return new ArrayList(this.mFilteredCachedDevices);
    }

    public final HearingAidDeviceManager getHearingAidDeviceManager() {
        return this.mHearingAidDeviceManager;
    }

    public final synchronized boolean isSubDevice(BluetoothDevice bluetoothDevice) {
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
            if (!cachedBluetoothDevice.mDevice.equals(bluetoothDevice)) {
                Set set = cachedBluetoothDevice.mMemberDevices;
                if (set.isEmpty()) {
                    CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
                    if (cachedBluetoothDevice2 != null && cachedBluetoothDevice2.mDevice.equals(bluetoothDevice)) {
                        return true;
                    }
                } else {
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        if (((CachedBluetoothDevice) it.next()).mDevice.equals(bluetoothDevice)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public final boolean isValidStub() {
        Log.d("CachedBluetoothDeviceManager", "isValidStub: packageName = com.samsung.android.app.watchmanagerstub");
        return ((HashMap) this.stubInfoMap).get("com.samsung.android.app.watchmanagerstub") != null && ((Integer) ((HashMap) this.stubInfoMap).get("com.samsung.android.app.watchmanagerstub")).intValue() > 100;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0064, code lost:
    
        if (r0.getMajorDeviceClass() == 7936) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean needListFiltering(com.android.settingslib.bluetooth.CachedBluetoothDevice r9) {
        /*
            Method dump skipped, instructions count: 515
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDeviceManager.needListFiltering(com.android.settingslib.bluetooth.CachedBluetoothDevice):boolean");
    }

    public final synchronized void removeDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mCachedDevices.remove(cachedBluetoothDevice);
        ((ArrayList) this.mFilteredCachedDevices).remove(cachedBluetoothDevice);
    }

    public final synchronized void removeRestoredDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.intent.action.NOTIFY_REMOVED_SYNC_DEVICE_BLUETOOTH");
        intent.putExtra("android.bluetooth.device.extra.DEVICE", bluetoothDevice);
        intent.setFlags(268435456);
        intent.setPackage("com.android.bluetooth");
        this.mContext.sendBroadcast(intent);
        removeDevice(cachedBluetoothDevice);
    }

    public final void setStubInfo(String str) {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            for (ApplicationInfo applicationInfo : packageManager.getInstalledApplications(0)) {
                if (applicationInfo.packageName.equals(str) && applicationInfo.enabled) {
                    int i = packageManager.getPackageInfo(str, 0).versionCode;
                    this.stubInfoMap.put(str, Integer.valueOf(i));
                    Log.d("CachedBluetoothDeviceManager", "setStubInfo: INSTALLER_STUB is exist. Package : " + str + ", Version : " + i);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public final synchronized void updateSequeces() {
        for (int size = this.mCachedDevices.size() - 1; size >= 0; size--) {
            this.mCachedDevices.get(size).mSequence = size;
        }
    }

    public final synchronized boolean addDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        boolean z;
        if (needListFiltering(cachedBluetoothDevice)) {
            z = true;
        } else {
            this.mCachedDevices.add(cachedBluetoothDevice);
            ((ArrayList) this.mFilteredCachedDevices).add(cachedBluetoothDevice);
            z = false;
        }
        return z;
    }

    public final CachedBluetoothDevice addDevice(BluetoothDevice bluetoothDevice) {
        LocalBluetoothManager localBluetoothManager = this.mBtManager;
        LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        LocalBluetoothProfileManager localBluetoothProfileManager = localBluetoothManager.mProfileManager;
        synchronized (this) {
            try {
                CachedBluetoothDevice findDevice = findDevice(bluetoothDevice);
                if (findDevice == null) {
                    findDevice = new CachedBluetoothDevice(this.mContext, localBluetoothProfileManager, bluetoothDevice);
                    if (localBluetoothAdapter.mAdapter.isCustomDeviceAddress(findDevice.mDevice.getAddress())) {
                        return null;
                    }
                    this.mCsipDeviceManager.initCsipDeviceIfNeeded(findDevice);
                    this.mHearingAidDeviceManager.initHearingAidDeviceIfNeeded(findDevice);
                    this.mCsipDeviceManager.setMemberDeviceIfNeeded(findDevice);
                    if (!this.mHearingAidDeviceManager.setSubDeviceIfNeeded(findDevice)) {
                        if (this.mCachedDevices.contains(findDevice)) {
                            Log.d("CachedBluetoothDeviceManager", "addDevice :: newDevice is added already");
                            return findDevice(bluetoothDevice);
                        }
                        boolean addDevice = addDevice(findDevice);
                        findDevice.mSequence = this.mCachedDevices.indexOf(findDevice);
                        if (!addDevice) {
                            this.mBtManager.mEventManager.dispatchDeviceAdded(findDevice);
                        }
                    }
                }
                return findDevice;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
