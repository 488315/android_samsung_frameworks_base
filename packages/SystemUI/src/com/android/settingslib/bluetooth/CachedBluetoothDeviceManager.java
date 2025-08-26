package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.ParcelUuid;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.util.ArrayUtils;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                    boolean zAddDevice = addDevice(cachedBluetoothDevice);
                    cachedBluetoothDevice.mSequence = this.mCachedDevices.indexOf(cachedBluetoothDevice);
                    if (!zAddDevice) {
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

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0064, code lost:
    
        if (r0.getMajorDeviceClass() == 7936) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean needListFiltering(CachedBluetoothDevice cachedBluetoothDevice) {
        if (cachedBluetoothDevice.mVisible) {
            if (cachedBluetoothDevice.mBondState != 12 && !cachedBluetoothDevice.mIsRestored) {
                if (cachedBluetoothDevice.getName().equals(cachedBluetoothDevice.mDevice.getAddress())) {
                    boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "is_display_bluetooth_ledevice", 0) == 1;
                    Log.e("CachedBluetoothDeviceManager", "needListFiltering() isShowLeDevice - " + z);
                    if (!z) {
                        int i = cachedBluetoothDevice.mType;
                        if (i != 2) {
                            if (i == 3) {
                                BluetoothClass bluetoothClass = cachedBluetoothDevice.mDevice.getBluetoothClass();
                                if (cachedBluetoothDevice.mManufacturerData == null) {
                                    if (bluetoothClass != null) {
                                    }
                                }
                            }
                        }
                    }
                }
                ManufacturerData manufacturerData = cachedBluetoothDevice.mManufacturerData;
                if (manufacturerData != null) {
                    int i2 = manufacturerData.mManufacturerType;
                    if (i2 == 1) {
                        byte[] bArr = manufacturerData.mData.mDeviceId;
                        if (bArr[0] == 0 && bArr[1] == 1) {
                            Log.d("CachedBluetoothDeviceManager", "[" + cachedBluetoothDevice.getNameForLog() + "] is Old Format Wearable Device.");
                            return false;
                        }
                    } else if (i2 == 3) {
                        Log.d("CachedBluetoothDeviceManager", "[" + cachedBluetoothDevice.getNameForLog() + "] is SS Standard Format Wearable Device.");
                        return false;
                    }
                    String upperCase = cachedBluetoothDevice.mDeviceName.toUpperCase();
                    if (upperCase.contains("GEAR") || upperCase.contains("GALAXY FIT") || upperCase.contains("GALAXY RING") || upperCase.contains("GALAXY WATCH")) {
                        Log.d("CachedBluetoothDeviceManager", "[" + cachedBluetoothDevice.getNameForLog() + "] has SS Wearable Name.");
                        return false;
                    }
                }
                Log.d("CachedBluetoothDeviceManager", "[" + cachedBluetoothDevice.getNameForLog() + "] is NOT Wearable Device.");
                if (cachedBluetoothDevice.mManufacturerData != null && cachedBluetoothDevice.mBondState == 10) {
                    synchronized (this) {
                        try {
                            for (CachedBluetoothDevice cachedBluetoothDevice2 : this.mCachedDevices) {
                                if (cachedBluetoothDevice2.mType == 2 && BluetoothUtils.compareSameWithGear(cachedBluetoothDevice.mDevice.getAddress(), cachedBluetoothDevice2.mDevice.getAddress())) {
                                    removeDevice(cachedBluetoothDevice2);
                                    this.mBtManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice2);
                                    return false;
                                }
                            }
                        } finally {
                        }
                    }
                }
                if (Settings.Global.getInt(this.mContext.getContentResolver(), "is_display_bluetooth_le_device_without_interesing_uuids", 0) == 1) {
                    Log.w("CachedBluetoothDeviceManager", "showLeDeviceWithoutInterestingUuids is true. Just show " + cachedBluetoothDevice.getNameForLog());
                    return false;
                }
                if (cachedBluetoothDevice.mIsRestored) {
                    Log.d("CachedBluetoothDeviceManager", "needListFiltering() -" + cachedBluetoothDevice.getNameForLog() + " is Restored Device. this must be in the list");
                    return false;
                }
                if (cachedBluetoothDevice.mDevice.getInquiryResultType() == 2) {
                    ParcelUuid[] leService16BitsUuidData = cachedBluetoothDevice.mDevice.getLeService16BitsUuidData();
                    if (!ArrayUtils.contains(leService16BitsUuidData, BluetoothUuid.HEARING_AID)) {
                        ParcelUuid parcelUuid = BluetoothUuid.LE_AUDIO;
                        if (!ArrayUtils.contains(leService16BitsUuidData, parcelUuid)) {
                            ParcelUuid[] leComplete16BitsUuidData = cachedBluetoothDevice.mDevice.getLeComplete16BitsUuidData();
                            if (!ArrayUtils.contains(leComplete16BitsUuidData, BluetoothUuid.HOGP) && !ArrayUtils.contains(leComplete16BitsUuidData, parcelUuid) && !ArrayUtils.contains(cachedBluetoothDevice.mDevice.getLeComplete128BitsUuidData(), ParcelUuid.fromString("7d74f4bd-c74a-4431-862c-cce884371592")) && (cachedBluetoothDevice.mAppearance & 960) != 960) {
                                Log.w("CachedBluetoothDeviceManager", cachedBluetoothDevice.getNameForLog() + " is Uninteresting LE Device. Filter this device out!");
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
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
                CachedBluetoothDevice cachedBluetoothDeviceFindDevice = findDevice(bluetoothDevice);
                if (cachedBluetoothDeviceFindDevice == null) {
                    cachedBluetoothDeviceFindDevice = new CachedBluetoothDevice(this.mContext, localBluetoothProfileManager, bluetoothDevice);
                    if (localBluetoothAdapter.mAdapter.isCustomDeviceAddress(cachedBluetoothDeviceFindDevice.mDevice.getAddress())) {
                        return null;
                    }
                    this.mCsipDeviceManager.initCsipDeviceIfNeeded(cachedBluetoothDeviceFindDevice);
                    this.mHearingAidDeviceManager.initHearingAidDeviceIfNeeded(cachedBluetoothDeviceFindDevice);
                    this.mCsipDeviceManager.setMemberDeviceIfNeeded(cachedBluetoothDeviceFindDevice);
                    if (!this.mHearingAidDeviceManager.setSubDeviceIfNeeded(cachedBluetoothDeviceFindDevice)) {
                        if (this.mCachedDevices.contains(cachedBluetoothDeviceFindDevice)) {
                            Log.d("CachedBluetoothDeviceManager", "addDevice :: newDevice is added already");
                            return findDevice(bluetoothDevice);
                        }
                        boolean zAddDevice = addDevice(cachedBluetoothDeviceFindDevice);
                        cachedBluetoothDeviceFindDevice.mSequence = this.mCachedDevices.indexOf(cachedBluetoothDeviceFindDevice);
                        if (!zAddDevice) {
                            this.mBtManager.mEventManager.dispatchDeviceAdded(cachedBluetoothDeviceFindDevice);
                        }
                    }
                }
                return cachedBluetoothDeviceFindDevice;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
