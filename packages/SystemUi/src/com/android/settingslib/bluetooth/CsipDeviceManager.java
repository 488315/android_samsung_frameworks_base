package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothCsipSetCoordinator;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothUuid;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CsipDeviceManager {
    public final LocalBluetoothManager mBtManager;
    public final List mCachedDevices;
    public final List mFilteredCachedDevices;

    public CsipDeviceManager(LocalBluetoothManager localBluetoothManager, List<CachedBluetoothDevice> list, List<CachedBluetoothDevice> list2) {
        this.mBtManager = localBluetoothManager;
        this.mCachedDevices = list;
        this.mFilteredCachedDevices = list2;
    }

    public static boolean isValidGroupId(int i) {
        return i != -1;
    }

    public static void log(String str) {
        Log.d("CsipDeviceManager", str);
    }

    public boolean addMemberDevicesIntoMainDevice(final int i, CachedBluetoothDevice cachedBluetoothDevice) {
        boolean z;
        if (cachedBluetoothDevice == null) {
            log("addMemberDevicesIntoMainDevice: No main device. Do nothing.");
            return false;
        }
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        CachedBluetoothDevice findMainDevice = findMainDevice(cachedBluetoothDevice);
        boolean z2 = findMainDevice == null;
        LocalBluetoothManager localBluetoothManager = this.mBtManager;
        if (z2) {
            z = false;
        } else {
            log("addMemberDevicesIntoMainDevice: The PreferredMainDevice have the mainDevice. Do switch relationship between the mainDeviceOfPreferredMainDevice and PreferredMainDevice");
            localBluetoothManager.mEventManager.dispatchDeviceRemoved(findMainDevice);
            ((HashSet) findMainDevice.mMemberDevices).remove(cachedBluetoothDevice);
            cachedBluetoothDevice.mLeadDevice = null;
            BluetoothDevice bluetoothDevice2 = findMainDevice.mDevice;
            short s = findMainDevice.mRssi;
            boolean z3 = findMainDevice.mJustDiscovered;
            findMainDevice.mDevice = cachedBluetoothDevice.mDevice;
            findMainDevice.mRssi = cachedBluetoothDevice.mRssi;
            findMainDevice.mJustDiscovered = cachedBluetoothDevice.mJustDiscovered;
            findMainDevice.fillData();
            cachedBluetoothDevice.mDevice = bluetoothDevice2;
            cachedBluetoothDevice.mRssi = s;
            cachedBluetoothDevice.mJustDiscovered = z3;
            cachedBluetoothDevice.fillData();
            findMainDevice.addMemberDevice(cachedBluetoothDevice);
            findMainDevice.refresh();
            localBluetoothManager.mEventManager.dispatchDeviceAdded(findMainDevice);
            z = true;
        }
        List list = this.mCachedDevices;
        List<CachedBluetoothDevice> list2 = (List) list.stream().filter(new Predicate() { // from class: com.android.settingslib.bluetooth.CsipDeviceManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((CachedBluetoothDevice) obj).mGroupId == i;
            }
        }).collect(Collectors.toList());
        boolean z4 = list2.size() > 1;
        CachedBluetoothDevice findDevice = localBluetoothManager.mCachedDeviceManager.findDevice(bluetoothDevice);
        if (z4) {
            for (CachedBluetoothDevice cachedBluetoothDevice2 : list2) {
                BluetoothDevice bluetoothDevice3 = cachedBluetoothDevice2.mDevice;
                if (bluetoothDevice3 != null && !bluetoothDevice3.equals(bluetoothDevice)) {
                    HashSet hashSet = (HashSet) cachedBluetoothDevice2.mMemberDevices;
                    Iterator it = hashSet.iterator();
                    while (it.hasNext()) {
                        CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) it.next();
                        if (!cachedBluetoothDevice3.equals(findDevice)) {
                            findDevice.addMemberDevice(cachedBluetoothDevice3);
                        }
                    }
                    hashSet.clear();
                    findDevice.addMemberDevice(cachedBluetoothDevice2);
                    list.remove(cachedBluetoothDevice2);
                    localBluetoothManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice2);
                    z = true;
                }
            }
        }
        if (z) {
            log("addMemberDevicesIntoMainDevice: After changed, CachedBluetoothDevice list: " + list);
        }
        return z;
    }

    public final CachedBluetoothDevice findMainDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        List<CachedBluetoothDevice> list;
        if (cachedBluetoothDevice == null || (list = this.mCachedDevices) == null) {
            return null;
        }
        for (CachedBluetoothDevice cachedBluetoothDevice2 : list) {
            if (isValidGroupId(cachedBluetoothDevice2.mGroupId)) {
                HashSet<CachedBluetoothDevice> hashSet = (HashSet) cachedBluetoothDevice2.mMemberDevices;
                if (hashSet.isEmpty()) {
                    continue;
                } else {
                    for (CachedBluetoothDevice cachedBluetoothDevice3 : hashSet) {
                        if (cachedBluetoothDevice3 != null && cachedBluetoothDevice3.equals(cachedBluetoothDevice)) {
                            return cachedBluetoothDevice2;
                        }
                    }
                }
            }
        }
        return null;
    }

    public final int getBaseGroupId(BluetoothDevice bluetoothDevice) {
        CsipSetCoordinatorProfile csipSetCoordinatorProfile;
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mBtManager.mProfileManager;
        if (localBluetoothProfileManager != null && (csipSetCoordinatorProfile = localBluetoothProfileManager.mCsipSetCoordinatorProfile) != null) {
            BluetoothCsipSetCoordinator bluetoothCsipSetCoordinator = csipSetCoordinatorProfile.mService;
            Map groupUuidMapByDevice = (bluetoothCsipSetCoordinator == null || bluetoothDevice == null) ? null : bluetoothCsipSetCoordinator.getGroupUuidMapByDevice(bluetoothDevice);
            if (groupUuidMapByDevice == null) {
                return -1;
            }
            for (Map.Entry entry : groupUuidMapByDevice.entrySet()) {
                if (((ParcelUuid) entry.getValue()).equals(BluetoothUuid.CAP)) {
                    log(" entry.getKey() = " + entry.getKey());
                    return ((Integer) entry.getKey()).intValue();
                }
            }
        }
        return -1;
    }

    public List<CachedBluetoothDevice> getGroupDevicesFromAllOfDevicesList(int i) {
        ArrayList arrayList = new ArrayList();
        if (!isValidGroupId(i)) {
            return arrayList;
        }
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
            if (i == cachedBluetoothDevice.mGroupId) {
                arrayList.add(cachedBluetoothDevice);
                arrayList.addAll(cachedBluetoothDevice.mMemberDevices);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CachedBluetoothDevice getPreferredMainDevice(int i, List<CachedBluetoothDevice> list) {
        BluetoothDevice bluetoothDevice;
        CachedBluetoothDevice findDevice;
        CachedBluetoothDevice orElse;
        if (list != null && !list.isEmpty()) {
            CachedBluetoothDevice orElse2 = list.stream().filter(new CsipDeviceManager$$ExternalSyntheticLambda1(0)).filter(new CsipDeviceManager$$ExternalSyntheticLambda1(1)).findFirst().orElse(null);
            if (orElse2 != null && orElse2.isConnected()) {
                log("getPreferredMainDevice: The connected DUAL mode device");
                return orElse2;
            }
            LocalBluetoothManager localBluetoothManager = this.mBtManager;
            LeAudioProfile leAudioProfile = localBluetoothManager.mProfileManager.mLeAudioProfile;
            if (leAudioProfile != null) {
                Log.d("LeAudioProfile", "getConnectedGroupLeadDevice");
                BluetoothLeAudio bluetoothLeAudio = leAudioProfile.mService;
                if (bluetoothLeAudio == null) {
                    Log.e("LeAudioProfile", "No service.");
                } else {
                    bluetoothDevice = bluetoothLeAudio.getConnectedGroupLeadDevice(i);
                    if (bluetoothDevice != null) {
                        log("getPreferredMainDevice: The LeadDevice from LE profile is " + bluetoothDevice.getAnonymizedAddress());
                    }
                    findDevice = bluetoothDevice == null ? localBluetoothManager.mCachedDeviceManager.findDevice(bluetoothDevice) : null;
                    if (findDevice != null) {
                        log("getPreferredMainDevice: The LeadDevice is not in the all of devices list");
                    } else if (findDevice.isConnected()) {
                        log("getPreferredMainDevice: The connected LeadDevice from LE profile");
                        return findDevice;
                    }
                    orElse = list.stream().filter(new CsipDeviceManager$$ExternalSyntheticLambda1(2)).findFirst().orElse(null);
                    if (orElse == null) {
                        log("getPreferredMainDevice: One of the connected devices.");
                        return orElse;
                    }
                    if (orElse2 != null) {
                        log("getPreferredMainDevice: The DUAL mode device.");
                        return orElse2;
                    }
                    if (!list.isEmpty()) {
                        log("getPreferredMainDevice: One of the group devices.");
                        return list.get(0);
                    }
                }
            }
            bluetoothDevice = null;
            if (bluetoothDevice != null) {
            }
            if (bluetoothDevice == null) {
            }
            if (findDevice != null) {
            }
            orElse = list.stream().filter(new CsipDeviceManager$$ExternalSyntheticLambda1(2)).findFirst().orElse(null);
            if (orElse == null) {
            }
        }
        return null;
    }

    public final void initCsipDeviceIfNeeded(CachedBluetoothDevice cachedBluetoothDevice) {
        if (cachedBluetoothDevice.mBondState != 12) {
            cachedBluetoothDevice.setGroupId(-1);
            return;
        }
        int baseGroupId = getBaseGroupId(cachedBluetoothDevice.mDevice);
        if (isValidGroupId(baseGroupId)) {
            log("initCsipDeviceIfNeeded: " + cachedBluetoothDevice + " (group: " + baseGroupId + ")");
            cachedBluetoothDevice.setGroupId(baseGroupId);
        }
    }

    public final boolean isMainHearableDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mBtManager.mProfileManager;
        if (!cachedBluetoothDevice.isHearableUsingWearableManager()) {
            return false;
        }
        int i = cachedBluetoothDevice.mType;
        return (i == 1 || i == 3) && localBluetoothProfileManager != null && cachedBluetoothDevice.hasProfile(localBluetoothProfileManager.mA2dpProfile);
    }

    public void onGroupIdChanged(int i) {
        if (!isValidGroupId(i)) {
            log("onGroupIdChanged: groupId is invalid");
            return;
        }
        ArrayList arrayList = new ArrayList();
        List list = this.mCachedDevices;
        for (int size = list.size() - 1; size >= 0; size--) {
            CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) list.get(size);
            if (cachedBluetoothDevice.mGroupId == i) {
                if (isMainHearableDevice(cachedBluetoothDevice)) {
                    arrayList.add(0, cachedBluetoothDevice);
                } else {
                    arrayList.add(cachedBluetoothDevice);
                }
                if (arrayList.size() == 2) {
                    break;
                }
            }
        }
        if (arrayList.size() == 2) {
            CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) arrayList.get(0);
            CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) arrayList.get(1);
            cachedBluetoothDevice2.addMemberDevice(cachedBluetoothDevice3);
            cachedBluetoothDevice3.mVisible = false;
            this.mFilteredCachedDevices.remove(cachedBluetoothDevice3);
            this.mBtManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice3);
        }
    }

    public final boolean onProfileConnectionStateChangedIfProcessed(int i, CachedBluetoothDevice cachedBluetoothDevice) {
        log("onProfileConnectionStateChangedIfProcessed: " + cachedBluetoothDevice + ", state: " + i + ", groupId = " + cachedBluetoothDevice.mGroupId);
        if (i != 0) {
            if (i != 2) {
                return false;
            }
            if (!isValidGroupId(cachedBluetoothDevice.mGroupId)) {
                initCsipDeviceIfNeeded(cachedBluetoothDevice);
            }
            onGroupIdChanged(cachedBluetoothDevice.mGroupId);
            CachedBluetoothDevice findMainDevice = findMainDevice(cachedBluetoothDevice);
            if (findMainDevice == null) {
                return false;
            }
            if (findMainDevice.isConnected()) {
                findMainDevice.refresh();
            }
            return true;
        }
        CachedBluetoothDevice findMainDevice2 = findMainDevice(cachedBluetoothDevice);
        if (findMainDevice2 != null) {
            findMainDevice2.refresh();
            return true;
        }
        HashSet hashSet = (HashSet) cachedBluetoothDevice.mMemberDevices;
        if (hashSet.isEmpty()) {
            return false;
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            if (((CachedBluetoothDevice) it.next()).isConnected()) {
                return true;
            }
        }
        return false;
    }

    public final void setMemberDeviceIfNeeded(CachedBluetoothDevice cachedBluetoothDevice) {
        String str;
        String str2;
        int i = cachedBluetoothDevice.mGroupId;
        if (isValidGroupId(i)) {
            List list = this.mCachedDevices;
            int size = list.size() - 1;
            CachedBluetoothDevice cachedBluetoothDevice2 = null;
            while (true) {
                if (size < 0) {
                    break;
                }
                CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) list.get(size);
                if (cachedBluetoothDevice3.mGroupId == i) {
                    if (((HashSet) cachedBluetoothDevice3.mMemberDevices).size() > 0) {
                        cachedBluetoothDevice2 = cachedBluetoothDevice3;
                        break;
                    }
                    cachedBluetoothDevice2 = cachedBluetoothDevice3;
                }
                size--;
            }
            if (cachedBluetoothDevice2 == null) {
                str = "";
                str2 = "";
            } else if (isMainHearableDevice(cachedBluetoothDevice)) {
                cachedBluetoothDevice.addMemberDevice(cachedBluetoothDevice2);
                Iterator it = ((HashSet) cachedBluetoothDevice2.mMemberDevices).iterator();
                while (it.hasNext()) {
                    cachedBluetoothDevice.addMemberDevice((CachedBluetoothDevice) it.next());
                }
                Iterator it2 = ((HashSet) cachedBluetoothDevice2.mMemberDevices).iterator();
                while (it2.hasNext()) {
                    ((CachedBluetoothDevice) it2.next()).mLeadDevice = null;
                    it2.remove();
                }
                cachedBluetoothDevice2.setName(cachedBluetoothDevice.getName());
                cachedBluetoothDevice2.mVisible = false;
                str = cachedBluetoothDevice.mDevice.getAddressForLogging();
                str2 = cachedBluetoothDevice2.mDevice.getAddressForLogging();
                this.mFilteredCachedDevices.remove(cachedBluetoothDevice2);
            } else {
                cachedBluetoothDevice2.addMemberDevice(cachedBluetoothDevice);
                cachedBluetoothDevice.setName(cachedBluetoothDevice2.getName());
                cachedBluetoothDevice.mVisible = false;
                String addressForLogging = cachedBluetoothDevice2.mDevice.getAddressForLogging();
                str2 = cachedBluetoothDevice.mDevice.getAddressForLogging();
                str = addressForLogging;
            }
            log("setMemberDeviceIfNeeded, main: " + str + ", member: " + str2);
            BluetoothDump.BtLog("CsipDeviceManager -- setMemberDeviceIfNeeded, main: " + str + ", member: " + str2);
        }
    }

    public final void updateCsipDevices() {
        HashSet hashSet = new HashSet();
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
            log("updateCsipDevices: cachedDevice = " + cachedBluetoothDevice.mDevice.getAddressForLogging() + ", groupId = " + cachedBluetoothDevice.mGroupId);
            if (!isValidGroupId(cachedBluetoothDevice.mGroupId)) {
                int baseGroupId = getBaseGroupId(cachedBluetoothDevice.mDevice);
                if (isValidGroupId(baseGroupId)) {
                    cachedBluetoothDevice.setGroupId(baseGroupId);
                    hashSet.add(Integer.valueOf(baseGroupId));
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            onGroupIdChanged(((Integer) it.next()).intValue());
        }
    }

    public boolean updateRelationshipOfGroupDevices(int i) {
        if (!isValidGroupId(i)) {
            log("The device is not group.");
            return false;
        }
        log("updateRelationshipOfGroupDevices: mCachedDevices list =" + this.mCachedDevices.toString());
        List<CachedBluetoothDevice> groupDevicesFromAllOfDevicesList = getGroupDevicesFromAllOfDevicesList(i);
        CachedBluetoothDevice preferredMainDevice = getPreferredMainDevice(i, groupDevicesFromAllOfDevicesList);
        log("The preferredMainDevice= " + preferredMainDevice + " and the groupDevicesList of groupId= " + i + " =" + groupDevicesFromAllOfDevicesList);
        return addMemberDevicesIntoMainDevice(i, preferredMainDevice);
    }
}
