package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothCsipSetCoordinator;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class CsipDeviceManager {
    public final LocalBluetoothManager mBtManager;
    public final List mCachedDevices;
    public final List mFilteredCachedDevices;

    public CsipDeviceManager(Context context, LocalBluetoothManager localBluetoothManager, List<CachedBluetoothDevice> list, List<CachedBluetoothDevice> list2) {
        this.mBtManager = localBluetoothManager;
        this.mCachedDevices = list;
        this.mFilteredCachedDevices = list2;
    }

    public static boolean isDeviceConnected(CachedBluetoothDevice cachedBluetoothDevice) {
        if (cachedBluetoothDevice == null) {
            return false;
        }
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        return cachedBluetoothDevice.isConnected() && bluetoothDevice.getBondState() == 12 && bluetoothDevice.isConnected();
    }

    public static boolean isValidGroupId(int i) {
        return i != -1;
    }

    public static void log(String str) {
        Log.d("CsipDeviceManager", str);
    }

    public boolean addMemberDevicesIntoMainDevice(final int i, CachedBluetoothDevice cachedBluetoothDevice) throws Throwable {
        boolean z;
        if (cachedBluetoothDevice == null) {
            log("addMemberDevicesIntoMainDevice: No main device. Do nothing.");
            return false;
        }
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        CachedBluetoothDevice cachedBluetoothDeviceFindMainDevice = findMainDevice(cachedBluetoothDevice);
        LocalBluetoothManager localBluetoothManager = this.mBtManager;
        if (cachedBluetoothDeviceFindMainDevice == null) {
            z = false;
        } else {
            log("addMemberDevicesIntoMainDevice: The PreferredMainDevice have the mainDevice. Do switch relationship between the mainDeviceOfPreferredMainDevice and PreferredMainDevice");
            localBluetoothManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDeviceFindMainDevice);
            ((HashSet) cachedBluetoothDeviceFindMainDevice.mMemberDevices).remove(cachedBluetoothDevice);
            cachedBluetoothDevice.mLeadDevice = null;
            BluetoothDevice bluetoothDevice2 = cachedBluetoothDeviceFindMainDevice.mDevice;
            short s = cachedBluetoothDeviceFindMainDevice.mRssi;
            boolean z2 = cachedBluetoothDeviceFindMainDevice.mJustDiscovered;
            HearingAidInfo hearingAidInfo = cachedBluetoothDeviceFindMainDevice.mHearingAidInfo;
            cachedBluetoothDeviceFindMainDevice.mDevice = cachedBluetoothDevice.mDevice;
            cachedBluetoothDeviceFindMainDevice.mRssi = cachedBluetoothDevice.mRssi;
            cachedBluetoothDeviceFindMainDevice.mJustDiscovered = cachedBluetoothDevice.mJustDiscovered;
            cachedBluetoothDeviceFindMainDevice.mHearingAidInfo = cachedBluetoothDevice.mHearingAidInfo;
            cachedBluetoothDeviceFindMainDevice.fillData();
            cachedBluetoothDevice.mDevice = bluetoothDevice2;
            cachedBluetoothDevice.mRssi = s;
            cachedBluetoothDevice.mJustDiscovered = z2;
            cachedBluetoothDevice.mHearingAidInfo = hearingAidInfo;
            cachedBluetoothDevice.fillData();
            cachedBluetoothDeviceFindMainDevice.addMemberDevice(cachedBluetoothDevice);
            cachedBluetoothDeviceFindMainDevice.refresh();
            localBluetoothManager.mEventManager.dispatchDeviceAdded(cachedBluetoothDeviceFindMainDevice);
            z = true;
        }
        List<CachedBluetoothDevice> list = (List) this.mCachedDevices.stream().filter(new Predicate() { // from class: com.android.settingslib.bluetooth.CsipDeviceManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((CachedBluetoothDevice) obj).mGroupId == i;
            }
        }).collect(Collectors.toList());
        boolean z3 = list.size() > 1;
        CachedBluetoothDevice cachedBluetoothDeviceFindDevice = localBluetoothManager.mCachedDeviceManager.findDevice(bluetoothDevice);
        if (z3) {
            log("addMemberDevicesIntoMainDevice: haveMultiMainDevicesInAllOfDevicesList. Combine them and also keep the preferred main device as main device.");
            for (CachedBluetoothDevice cachedBluetoothDevice2 : list) {
                BluetoothDevice bluetoothDevice3 = cachedBluetoothDevice2.mDevice;
                if (bluetoothDevice3 != null && !bluetoothDevice3.equals(bluetoothDevice)) {
                    HashSet hashSet = (HashSet) cachedBluetoothDevice2.mMemberDevices;
                    Iterator it = hashSet.iterator();
                    while (it.hasNext()) {
                        CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) it.next();
                        if (!cachedBluetoothDevice3.equals(cachedBluetoothDeviceFindDevice)) {
                            cachedBluetoothDeviceFindDevice.addMemberDevice(cachedBluetoothDevice3);
                        }
                    }
                    hashSet.clear();
                    cachedBluetoothDeviceFindDevice.addMemberDevice(cachedBluetoothDevice2);
                    this.mCachedDevices.remove(cachedBluetoothDevice2);
                    localBluetoothManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice2);
                    cachedBluetoothDeviceFindDevice.refresh();
                    z = true;
                }
            }
            boolean z4 = BluetoothUtils.DEBUG;
            log("addMemberDevicesIntoMainDevice: skip sync source, flag disabled");
        }
        if (z) {
            log("addMemberDevicesIntoMainDevice: After changed, CachedBluetoothDevice list: " + this.mCachedDevices);
        }
        return z;
    }

    public final CachedBluetoothDevice findMainDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        List<CachedBluetoothDevice> list = this.mCachedDevices;
        if (list == null) {
            return null;
        }
        for (CachedBluetoothDevice cachedBluetoothDevice2 : list) {
            if (isValidGroupId(cachedBluetoothDevice2.mGroupId)) {
                HashSet hashSet = (HashSet) cachedBluetoothDevice2.mMemberDevices;
                if (hashSet.isEmpty()) {
                    continue;
                } else {
                    Iterator it = hashSet.iterator();
                    while (it.hasNext()) {
                        CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) it.next();
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
        if (localBluetoothProfileManager == null || (csipSetCoordinatorProfile = localBluetoothProfileManager.mCsipSetCoordinatorProfile) == null) {
            return -1;
        }
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
        return -1;
    }

    public List<CachedBluetoothDevice> getGroupDevicesFromAllOfDevicesList(int i) {
        ArrayList arrayList = new ArrayList();
        if (isValidGroupId(i)) {
            for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
                if (i == cachedBluetoothDevice.mGroupId) {
                    arrayList.add(cachedBluetoothDevice);
                    arrayList.addAll(cachedBluetoothDevice.mMemberDevices);
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CachedBluetoothDevice getPreferredMainDevice(int i, List<CachedBluetoothDevice> list) {
        BluetoothDevice connectedGroupLeadDevice;
        CachedBluetoothDevice cachedBluetoothDeviceFindDevice;
        CachedBluetoothDevice cachedBluetoothDeviceOrElse;
        if (list != null && !list.isEmpty()) {
            CachedBluetoothDevice cachedBluetoothDeviceOrElse2 = list.stream().filter(new CsipDeviceManager$$ExternalSyntheticLambda1(0)).filter(new CsipDeviceManager$$ExternalSyntheticLambda1(1)).findFirst().orElse(null);
            if (isDeviceConnected(cachedBluetoothDeviceOrElse2)) {
                log("getPreferredMainDevice: The connected DUAL mode device");
                return cachedBluetoothDeviceOrElse2;
            }
            LocalBluetoothManager localBluetoothManager = this.mBtManager;
            LeAudioProfile leAudioProfile = localBluetoothManager.mProfileManager.mLeAudioProfile;
            if (leAudioProfile != null) {
                Log.d("LeAudioProfile", "getConnectedGroupLeadDevice");
                BluetoothLeAudio bluetoothLeAudio = leAudioProfile.mService;
                if (bluetoothLeAudio == null) {
                    Log.e("LeAudioProfile", "No service.");
                    connectedGroupLeadDevice = null;
                    if (connectedGroupLeadDevice != null) {
                        log("getPreferredMainDevice: The LeadDevice from LE profile is " + connectedGroupLeadDevice.getAnonymizedAddress());
                    }
                    cachedBluetoothDeviceFindDevice = connectedGroupLeadDevice != null ? localBluetoothManager.mCachedDeviceManager.findDevice(connectedGroupLeadDevice) : null;
                    if (cachedBluetoothDeviceFindDevice == null) {
                        log("getPreferredMainDevice: The LeadDevice is not in the all of devices list");
                    } else if (isDeviceConnected(cachedBluetoothDeviceFindDevice)) {
                        log("getPreferredMainDevice: The connected LeadDevice from LE profile");
                        return cachedBluetoothDeviceFindDevice;
                    }
                    cachedBluetoothDeviceOrElse = list.stream().filter(new Predicate() { // from class: com.android.settingslib.bluetooth.CsipDeviceManager$$ExternalSyntheticLambda3
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return CsipDeviceManager.isDeviceConnected((CachedBluetoothDevice) obj);
                        }
                    }).findFirst().orElse(null);
                    if (cachedBluetoothDeviceOrElse != null) {
                        log("getPreferredMainDevice: One of the connected devices.");
                        return cachedBluetoothDeviceOrElse;
                    }
                    if (cachedBluetoothDeviceOrElse2 != null) {
                        log("getPreferredMainDevice: The DUAL mode device.");
                        return cachedBluetoothDeviceOrElse2;
                    }
                    if (!list.isEmpty()) {
                        log("getPreferredMainDevice: One of the group devices.");
                        return list.get(0);
                    }
                } else {
                    connectedGroupLeadDevice = bluetoothLeAudio.getConnectedGroupLeadDevice(i);
                    if (connectedGroupLeadDevice != null) {
                    }
                    if (connectedGroupLeadDevice != null) {
                    }
                    if (cachedBluetoothDeviceFindDevice == null) {
                    }
                    cachedBluetoothDeviceOrElse = list.stream().filter(new Predicate() { // from class: com.android.settingslib.bluetooth.CsipDeviceManager$$ExternalSyntheticLambda3
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return CsipDeviceManager.isDeviceConnected((CachedBluetoothDevice) obj);
                        }
                    }).findFirst().orElse(null);
                    if (cachedBluetoothDeviceOrElse != null) {
                    }
                }
            } else {
                connectedGroupLeadDevice = null;
                if (connectedGroupLeadDevice != null) {
                }
                if (connectedGroupLeadDevice != null) {
                }
                if (cachedBluetoothDeviceFindDevice == null) {
                }
                cachedBluetoothDeviceOrElse = list.stream().filter(new Predicate() { // from class: com.android.settingslib.bluetooth.CsipDeviceManager$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return CsipDeviceManager.isDeviceConnected((CachedBluetoothDevice) obj);
                    }
                }).findFirst().orElse(null);
                if (cachedBluetoothDeviceOrElse != null) {
                }
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

    public void onGroupIdChanged(int i) {
        LocalBluetoothManager localBluetoothManager;
        if (!isValidGroupId(i)) {
            log("onGroupIdChanged: groupId is invalid");
            return;
        }
        ArrayList arrayList = new ArrayList();
        int size = this.mCachedDevices.size() - 1;
        while (true) {
            localBluetoothManager = this.mBtManager;
            if (size < 0) {
                break;
            }
            CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) this.mCachedDevices.get(size);
            if (cachedBluetoothDevice.mGroupId == i) {
                LocalBluetoothProfileManager localBluetoothProfileManager = localBluetoothManager.mProfileManager;
                if (localBluetoothProfileManager == null || !cachedBluetoothDevice.hasProfile(localBluetoothProfileManager.mA2dpProfile)) {
                    arrayList.add(cachedBluetoothDevice);
                } else {
                    arrayList.add(0, cachedBluetoothDevice);
                }
                if (arrayList.size() == 2) {
                    break;
                }
            }
            size--;
        }
        if (arrayList.size() == 2) {
            CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) arrayList.get(0);
            CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) arrayList.get(1);
            cachedBluetoothDevice2.addMemberDevice(cachedBluetoothDevice3);
            cachedBluetoothDevice3.mVisible = false;
            if (!Objects.equals(cachedBluetoothDevice3.getName(), cachedBluetoothDevice2.getName())) {
                cachedBluetoothDevice3.setName(cachedBluetoothDevice2.getName());
            }
            this.mFilteredCachedDevices.remove(cachedBluetoothDevice3);
            localBluetoothManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice3);
        }
    }

    public final boolean onProfileConnectionStateChangedIfProcessed(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        log("onProfileConnectionStateChangedIfProcessed: " + cachedBluetoothDevice + ", state: " + i + ", groupId = " + cachedBluetoothDevice.mGroupId);
        if (i != 0) {
            if (i != 2) {
                return false;
            }
            if (!isValidGroupId(cachedBluetoothDevice.mGroupId)) {
                initCsipDeviceIfNeeded(cachedBluetoothDevice);
            }
            onGroupIdChanged(cachedBluetoothDevice.mGroupId);
            CachedBluetoothDevice cachedBluetoothDeviceFindMainDevice = findMainDevice(cachedBluetoothDevice);
            if (cachedBluetoothDeviceFindMainDevice == null) {
                return false;
            }
            if (cachedBluetoothDeviceFindMainDevice.isConnected()) {
                cachedBluetoothDeviceFindMainDevice.refresh();
            }
            return true;
        }
        CachedBluetoothDevice cachedBluetoothDeviceFindMainDevice2 = findMainDevice(cachedBluetoothDevice);
        if (cachedBluetoothDeviceFindMainDevice2 != null) {
            cachedBluetoothDeviceFindMainDevice2.refresh();
            return true;
        }
        HashSet hashSet = (HashSet) cachedBluetoothDevice.mMemberDevices;
        if (hashSet.isEmpty()) {
            return false;
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            if (((CachedBluetoothDevice) it.next()).isConnected()) {
                cachedBluetoothDevice.refresh();
                return true;
            }
        }
        log("onProfileConnectionStateChangedIfProcessed: break disconnected");
        return false;
    }

    public final void setMemberDeviceIfNeeded(CachedBluetoothDevice cachedBluetoothDevice) {
        int i = cachedBluetoothDevice.mGroupId;
        if (isValidGroupId(i)) {
            int size = this.mCachedDevices.size() - 1;
            CachedBluetoothDevice cachedBluetoothDevice2 = null;
            while (true) {
                if (size < 0) {
                    break;
                }
                CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) this.mCachedDevices.get(size);
                if (cachedBluetoothDevice3.mGroupId == i) {
                    if (((HashSet) cachedBluetoothDevice3.mMemberDevices).size() > 0) {
                        cachedBluetoothDevice2 = cachedBluetoothDevice3;
                        break;
                    }
                    cachedBluetoothDevice2 = cachedBluetoothDevice3;
                }
                size--;
            }
            if (cachedBluetoothDevice2 != null) {
                LocalBluetoothProfileManager localBluetoothProfileManager = this.mBtManager.mProfileManager;
                if (!(localBluetoothProfileManager != null && cachedBluetoothDevice.hasProfile(localBluetoothProfileManager.mA2dpProfile))) {
                    cachedBluetoothDevice2.addMemberDevice(cachedBluetoothDevice);
                    cachedBluetoothDevice.setName(cachedBluetoothDevice2.getName());
                    cachedBluetoothDevice.mVisible = false;
                    log("setMemberDeviceIfNeeded, main: " + cachedBluetoothDevice2.mDevice + ", member: " + cachedBluetoothDevice.mDevice);
                    BluetoothDump.BtLog("CsipDeviceManager -- setMemberDeviceIfNeeded, main: " + cachedBluetoothDevice2.mDevice + ", member: " + cachedBluetoothDevice.mDevice);
                    return;
                }
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
                this.mFilteredCachedDevices.remove(cachedBluetoothDevice2);
                log("setMemberDeviceIfNeeded, main: " + cachedBluetoothDevice.mDevice + ", member: " + cachedBluetoothDevice2.mDevice);
                BluetoothDump.BtLog("CsipDeviceManager -- setMemberDeviceIfNeeded, main: " + cachedBluetoothDevice.mDevice + ", member: " + cachedBluetoothDevice2.mDevice);
            }
        }
    }

    public final void updateCsipDevices() {
        HashSet hashSet = new HashSet();
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
            log("updateCsipDevices: cachedDevice = " + cachedBluetoothDevice.mDevice + ", groupId = " + cachedBluetoothDevice.mGroupId);
            if (!isValidGroupId(cachedBluetoothDevice.mGroupId)) {
                int baseGroupId = getBaseGroupId(cachedBluetoothDevice.mDevice);
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(baseGroupId, "updateCsipDevices: propose new group id ", " for device ");
                sbM.append(cachedBluetoothDevice.mDevice);
                log(sbM.toString());
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
