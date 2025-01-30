package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CachedBluetoothDeviceManager {
    static int sLateBondingTimeoutMillis = 5000;
    public final LocalBluetoothManager mBtManager;
    final List<CachedBluetoothDevice> mCachedDevices;
    public final Context mContext;
    CsipDeviceManager mCsipDeviceManager;
    public final List mFilteredCachedDevices;
    HearingAidDeviceManager mHearingAidDeviceManager;
    public int mOngoingSetMemberGroupId;
    public BluetoothDevice mOngoingSetMemberPair;
    public final Map stubInfoMap;

    public CachedBluetoothDeviceManager(Context context, LocalBluetoothManager localBluetoothManager) {
        ArrayList arrayList = new ArrayList();
        this.mFilteredCachedDevices = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mCachedDevices = arrayList2;
        this.mOngoingSetMemberGroupId = -1;
        this.stubInfoMap = new HashMap();
        this.mContext = context;
        this.mBtManager = localBluetoothManager;
        this.mHearingAidDeviceManager = new HearingAidDeviceManager(context, localBluetoothManager, arrayList2, arrayList);
        this.mCsipDeviceManager = new CsipDeviceManager(localBluetoothManager, arrayList2, arrayList);
        setStubInfo("com.samsung.android.app.watchmanagerstub");
        setStubInfo("com.sec.android.app.applinker");
    }

    public final CachedBluetoothDevice addDevice(LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothDevice bluetoothDevice) {
        LocalBluetoothAdapter localBluetoothAdapter = this.mBtManager.mLocalAdapter;
        CachedBluetoothDevice cachedBluetoothDevice = new CachedBluetoothDevice(this.mContext, localBluetoothProfileManager, bluetoothDevice);
        synchronized (this) {
            this.mCsipDeviceManager.initCsipDeviceIfNeeded(cachedBluetoothDevice);
            this.mHearingAidDeviceManager.initHearingAidDeviceIfNeeded(cachedBluetoothDevice);
            if (localBluetoothAdapter.mAdapter.isCustomDeviceAddress(cachedBluetoothDevice.getAddress())) {
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
        }
    }

    public final synchronized void clearNonBondedDevices() {
        int size = this.mCachedDevices.size();
        while (true) {
            size--;
            if (size >= 0) {
                CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevices.get(size);
                if (cachedBluetoothDevice.mBondState == 10 && !cachedBluetoothDevice.mIsRestored) {
                    removeDevice(cachedBluetoothDevice);
                }
            } else {
                updateSequeces();
            }
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

    public final synchronized Collection getCachedDevicesCopy() {
        return new ArrayList(this.mFilteredCachedDevices);
    }

    public final int getStubVersion(String str) {
        HashMap hashMap = (HashMap) this.stubInfoMap;
        if (hashMap.get(str) != null) {
            return ((Integer) hashMap.get(str)).intValue();
        }
        return -1;
    }

    public final synchronized void initCsipDeviceIfNeeded(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mCsipDeviceManager.initCsipDeviceIfNeeded(cachedBluetoothDevice);
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

    public final boolean isValidStub(String str) {
        Log.d("CachedBluetoothDeviceManager", "isValidStub: packageName = ".concat(str));
        boolean equals = "com.samsung.android.app.watchmanagerstub".equals(str);
        Map map = this.stubInfoMap;
        if (equals) {
            HashMap hashMap = (HashMap) map;
            return hashMap.get(str) != null && ((Integer) hashMap.get(str)).intValue() > 100;
        }
        if (!"com.sec.android.app.applinker".equals(str)) {
            return false;
        }
        HashMap hashMap2 = (HashMap) map;
        return hashMap2.get(str) != null && ((Integer) hashMap2.get(str)).intValue() > 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x005e, code lost:
    
        if (r0.getMajorDeviceClass() == 7936) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:42:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean needListFiltering(CachedBluetoothDevice cachedBluetoothDevice) {
        boolean z;
        if (!cachedBluetoothDevice.mVisible) {
            return true;
        }
        if (cachedBluetoothDevice.mBondState == 12) {
            return false;
        }
        if (cachedBluetoothDevice.getName().equals(cachedBluetoothDevice.getAddress())) {
            boolean z2 = Settings.Global.getInt(this.mContext.getContentResolver(), "is_display_bluetooth_ledevice", 0) == 1;
            Log.e("CachedBluetoothDeviceManager", "needListFiltering() isShowLeDevice - " + z2);
            if (!z2) {
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
                return true;
            }
        }
        ManufacturerData manufacturerData = cachedBluetoothDevice.mManufacturerData;
        if (manufacturerData != null) {
            ManufacturerData.Data data = manufacturerData.mData;
            byte[] bArr = data.mDeviceId;
            if (bArr[0] == 0 && bArr[1] == 1) {
                int i2 = manufacturerData.mManufacturerType;
                if (i2 == 1) {
                    byte[] bArr2 = manufacturerData.mManufacturerRawData;
                    if (bArr2 != null && bArr2.length > 10) {
                        byte b = bArr2[10];
                        if ((b & 2) == 2) {
                            Log.d("CachedBluetoothDeviceManager", "isBleWearableDevice :: [" + cachedBluetoothDevice.getNameForLog() + "] is LE gear device.");
                        } else if (b == 0) {
                            String upperCase = cachedBluetoothDevice.mDeviceName.toUpperCase();
                            if ((upperCase.startsWith("GEAR") || upperCase.startsWith("GALAXY WATCH")) && upperCase.endsWith(" LE")) {
                                Log.d("CachedBluetoothDeviceManager", "isBleWearableDevice :: [" + cachedBluetoothDevice.getNameForLog() + "] is LE gear device.");
                            }
                        }
                        z = true;
                    }
                } else if (i2 == 3) {
                    byte b2 = data.mBluetoothType;
                    if ((b2 & 2) == 2) {
                        Log.d("CachedBluetoothDeviceManager", "isBleWearableDevice :: [" + cachedBluetoothDevice.getNameForLog() + "] is LE gear device.");
                    } else if (b2 == 0) {
                        String upperCase2 = cachedBluetoothDevice.mDeviceName.toUpperCase();
                        if ((upperCase2.startsWith("GEAR") || upperCase2.startsWith("GALAXY WATCH")) && upperCase2.endsWith(" LE")) {
                            Log.d("CachedBluetoothDeviceManager", "isBleWearableDevice :: [" + cachedBluetoothDevice.getNameForLog() + "] is LE gear device.");
                        }
                    }
                    z = true;
                }
                if (!z) {
                    String str = cachedBluetoothDevice.mDeviceName;
                    if (!str.toUpperCase().startsWith("GEAR") && !str.toUpperCase().startsWith("GALAXY WATCH")) {
                        Log.d("CachedBluetoothDeviceManager", "compareSameWithGear :: It is not gear device.");
                        return false;
                    }
                    int indexOf = str.indexOf("(");
                    if (indexOf == -1 || str.length() - 1 <= indexOf) {
                        Log.d("CachedBluetoothDeviceManager", "compareSameWithGear :: It is not gear name format.");
                        return false;
                    }
                    String substring = str.substring(indexOf + 1, indexOf + 2);
                    if (cachedBluetoothDevice.mBondState == 10) {
                        synchronized (this) {
                            for (CachedBluetoothDevice cachedBluetoothDevice2 : this.mCachedDevices) {
                                if (cachedBluetoothDevice2.mManufacturerData != null && cachedBluetoothDevice2.mType != 2 && BluetoothUtils.compareSameWithGear(0, substring, cachedBluetoothDevice.mDevice.getAddress(), cachedBluetoothDevice2.mDevice.getAddress())) {
                                    return true;
                                }
                            }
                        }
                    }
                } else if (cachedBluetoothDevice.mManufacturerData != null && cachedBluetoothDevice.mBondState == 10) {
                    synchronized (this) {
                        for (CachedBluetoothDevice cachedBluetoothDevice3 : this.mCachedDevices) {
                            if (cachedBluetoothDevice3.mType == 2 && BluetoothUtils.compareSameWithGear(1, "", cachedBluetoothDevice.mDevice.getAddress(), cachedBluetoothDevice3.mDevice.getAddress())) {
                                removeDevice(cachedBluetoothDevice3);
                                this.mBtManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice3);
                                return false;
                            }
                        }
                    }
                }
                return false;
            }
        }
        z = false;
        if (!z) {
        }
        return false;
    }

    public final synchronized void onDeviceUnpaired(CachedBluetoothDevice cachedBluetoothDevice) {
        int i = cachedBluetoothDevice.mGroupId;
        cachedBluetoothDevice.setGroupId(-1);
        CachedBluetoothDevice findMainDevice = this.mCsipDeviceManager.findMainDevice(cachedBluetoothDevice);
        HashSet<CachedBluetoothDevice> hashSet = (HashSet) cachedBluetoothDevice.mMemberDevices;
        if (!hashSet.isEmpty()) {
            for (CachedBluetoothDevice cachedBluetoothDevice2 : hashSet) {
                cachedBluetoothDevice2.unpair();
                cachedBluetoothDevice2.setGroupId(-1);
                ((HashSet) cachedBluetoothDevice.mMemberDevices).remove(cachedBluetoothDevice2);
                cachedBluetoothDevice2.mLeadDevice = null;
            }
        } else if (findMainDevice != null) {
            findMainDevice.unpair();
        }
        BluetoothDevice bluetoothDevice = this.mOngoingSetMemberPair;
        if (bluetoothDevice != null && this.mOngoingSetMemberGroupId == i) {
            if (bluetoothDevice.getBondState() == 11) {
                BluetoothDump.BtLog("CachedBluetoothDeviceManager -- onDeviceUnpaired: cancelBondProcess()");
                this.mOngoingSetMemberPair.cancelBondProcess();
            } else if (this.mOngoingSetMemberPair.getBondState() == 12) {
                BluetoothDump.BtLog("CachedBluetoothDeviceManager -- onDeviceUnpaired: removeBond()");
                this.mOngoingSetMemberPair.removeBond();
            }
        }
        CachedBluetoothDevice findMainDevice2 = this.mHearingAidDeviceManager.findMainDevice(cachedBluetoothDevice);
        CachedBluetoothDevice cachedBluetoothDevice3 = cachedBluetoothDevice.mSubDevice;
        if (cachedBluetoothDevice3 != null) {
            if (cachedBluetoothDevice3.mIsRestored) {
                removeRestoredDevice(cachedBluetoothDevice3);
            } else {
                cachedBluetoothDevice3.unpairLegacy();
            }
            cachedBluetoothDevice.mSubDevice = null;
        } else if (findMainDevice2 != null) {
            if (findMainDevice2.mIsRestored) {
                removeRestoredDevice(findMainDevice2);
            } else {
                findMainDevice2.unpairLegacy();
            }
            findMainDevice2.mSubDevice = null;
        }
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
        intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
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
        this.mCachedDevices.add(cachedBluetoothDevice);
        if (needListFiltering(cachedBluetoothDevice)) {
            z = true;
        } else {
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
            CachedBluetoothDevice findDevice = findDevice(bluetoothDevice);
            if (findDevice == null) {
                findDevice = new CachedBluetoothDevice(this.mContext, localBluetoothProfileManager, bluetoothDevice);
                if (localBluetoothAdapter.mAdapter.isCustomDeviceAddress(findDevice.getAddress())) {
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
        }
    }
}
