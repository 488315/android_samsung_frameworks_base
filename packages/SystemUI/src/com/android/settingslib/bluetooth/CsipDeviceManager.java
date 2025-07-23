package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothCsipSetCoordinator;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public boolean addMemberDevicesIntoMainDevice(final int i, CachedBluetoothDevice cachedBluetoothDevice) {
        boolean z;
        if (cachedBluetoothDevice == null) {
            log("addMemberDevicesIntoMainDevice: No main device. Do nothing.");
            return false;
        }
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        CachedBluetoothDevice findMainDevice = findMainDevice(cachedBluetoothDevice);
        LocalBluetoothManager localBluetoothManager = this.mBtManager;
        if (findMainDevice == null) {
            z = false;
        } else {
            log("addMemberDevicesIntoMainDevice: The PreferredMainDevice have the mainDevice. Do switch relationship between the mainDeviceOfPreferredMainDevice and PreferredMainDevice");
            localBluetoothManager.mEventManager.dispatchDeviceRemoved(findMainDevice);
            ((HashSet) findMainDevice.mMemberDevices).remove(cachedBluetoothDevice);
            cachedBluetoothDevice.mLeadDevice = null;
            BluetoothDevice bluetoothDevice2 = findMainDevice.mDevice;
            short s = findMainDevice.mRssi;
            boolean z2 = findMainDevice.mJustDiscovered;
            HearingAidInfo hearingAidInfo = findMainDevice.mHearingAidInfo;
            findMainDevice.mDevice = cachedBluetoothDevice.mDevice;
            findMainDevice.mRssi = cachedBluetoothDevice.mRssi;
            findMainDevice.mJustDiscovered = cachedBluetoothDevice.mJustDiscovered;
            findMainDevice.mHearingAidInfo = cachedBluetoothDevice.mHearingAidInfo;
            findMainDevice.fillData();
            cachedBluetoothDevice.mDevice = bluetoothDevice2;
            cachedBluetoothDevice.mRssi = s;
            cachedBluetoothDevice.mJustDiscovered = z2;
            cachedBluetoothDevice.mHearingAidInfo = hearingAidInfo;
            cachedBluetoothDevice.fillData();
            findMainDevice.addMemberDevice(cachedBluetoothDevice);
            findMainDevice.refresh();
            localBluetoothManager.mEventManager.dispatchDeviceAdded(findMainDevice);
            z = true;
        }
        List<CachedBluetoothDevice> list = (List) this.mCachedDevices.stream().filter(new Predicate() { // from class: com.android.settingslib.bluetooth.CsipDeviceManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((CachedBluetoothDevice) obj).mGroupId == i;
            }
        }).collect(Collectors.toList());
        boolean z3 = list.size() > 1;
        CachedBluetoothDevice findDevice = localBluetoothManager.mCachedDeviceManager.findDevice(bluetoothDevice);
        if (z3) {
            log("addMemberDevicesIntoMainDevice: haveMultiMainDevicesInAllOfDevicesList. Combine them and also keep the preferred main device as main device.");
            for (CachedBluetoothDevice cachedBluetoothDevice2 : list) {
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
                    this.mCachedDevices.remove(cachedBluetoothDevice2);
                    localBluetoothManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice2);
                    findDevice.refresh();
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.settingslib.bluetooth.CachedBluetoothDevice getPreferredMainDevice(int r7, java.util.List<com.android.settingslib.bluetooth.CachedBluetoothDevice> r8) {
        /*
            r6 = this;
            r0 = 0
            if (r8 == 0) goto Lc7
            boolean r1 = r8.isEmpty()
            if (r1 == 0) goto Lb
            goto Lc7
        Lb:
            java.util.stream.Stream r1 = r8.stream()
            com.android.settingslib.bluetooth.CsipDeviceManager$$ExternalSyntheticLambda1 r2 = new com.android.settingslib.bluetooth.CsipDeviceManager$$ExternalSyntheticLambda1
            r3 = 0
            r2.<init>(r3)
            java.util.stream.Stream r1 = r1.filter(r2)
            com.android.settingslib.bluetooth.CsipDeviceManager$$ExternalSyntheticLambda1 r2 = new com.android.settingslib.bluetooth.CsipDeviceManager$$ExternalSyntheticLambda1
            r3 = 1
            r2.<init>(r3)
            java.util.stream.Stream r1 = r1.filter(r2)
            java.util.Optional r1 = r1.findFirst()
            java.lang.Object r1 = r1.orElse(r0)
            com.android.settingslib.bluetooth.CachedBluetoothDevice r1 = (com.android.settingslib.bluetooth.CachedBluetoothDevice) r1
            boolean r2 = isDeviceConnected(r1)
            if (r2 == 0) goto L39
            java.lang.String r6 = "getPreferredMainDevice: The connected DUAL mode device"
            log(r6)
            return r1
        L39:
            com.android.settingslib.bluetooth.LocalBluetoothManager r2 = r6.mBtManager
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r3 = r2.mProfileManager
            com.android.settingslib.bluetooth.LeAudioProfile r3 = r3.mLeAudioProfile
            if (r3 == 0) goto L57
            java.lang.String r4 = "getConnectedGroupLeadDevice"
            java.lang.String r5 = "LeAudioProfile"
            android.util.Log.d(r5, r4)
            android.bluetooth.BluetoothLeAudio r3 = r3.mService
            if (r3 != 0) goto L52
            java.lang.String r7 = "No service."
            android.util.Log.e(r5, r7)
            goto L57
        L52:
            android.bluetooth.BluetoothDevice r7 = r3.getConnectedGroupLeadDevice(r7)
            goto L58
        L57:
            r7 = r0
        L58:
            if (r7 == 0) goto L6f
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "getPreferredMainDevice: The LeadDevice from LE profile is "
            r3.<init>(r4)
            java.lang.String r4 = r7.getAnonymizedAddress()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            log(r3)
        L6f:
            if (r7 == 0) goto L78
            com.android.settingslib.bluetooth.CachedBluetoothDeviceManager r2 = r2.mCachedDeviceManager
            com.android.settingslib.bluetooth.CachedBluetoothDevice r7 = r2.findDevice(r7)
            goto L79
        L78:
            r7 = r0
        L79:
            if (r7 != 0) goto L81
            java.lang.String r7 = "getPreferredMainDevice: The LeadDevice is not in the all of devices list"
            log(r7)
            goto L8d
        L81:
            boolean r2 = isDeviceConnected(r7)
            if (r2 == 0) goto L8d
            java.lang.String r6 = "getPreferredMainDevice: The connected LeadDevice from LE profile"
            log(r6)
            return r7
        L8d:
            java.util.stream.Stream r7 = r8.stream()
            com.android.settingslib.bluetooth.CsipDeviceManager$$ExternalSyntheticLambda3 r2 = new com.android.settingslib.bluetooth.CsipDeviceManager$$ExternalSyntheticLambda3
            r2.<init>()
            java.util.stream.Stream r6 = r7.filter(r2)
            java.util.Optional r6 = r6.findFirst()
            java.lang.Object r6 = r6.orElse(r0)
            com.android.settingslib.bluetooth.CachedBluetoothDevice r6 = (com.android.settingslib.bluetooth.CachedBluetoothDevice) r6
            if (r6 == 0) goto Lac
            java.lang.String r7 = "getPreferredMainDevice: One of the connected devices."
            log(r7)
            return r6
        Lac:
            if (r1 == 0) goto Lb4
            java.lang.String r6 = "getPreferredMainDevice: The DUAL mode device."
            log(r6)
            return r1
        Lb4:
            boolean r6 = r8.isEmpty()
            if (r6 != 0) goto Lc7
            java.lang.String r6 = "getPreferredMainDevice: One of the group devices."
            log(r6)
            r6 = 0
            java.lang.Object r6 = r8.get(r6)
            com.android.settingslib.bluetooth.CachedBluetoothDevice r6 = (com.android.settingslib.bluetooth.CachedBluetoothDevice) r6
            return r6
        Lc7:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CsipDeviceManager.getPreferredMainDevice(int, java.util.List):com.android.settingslib.bluetooth.CachedBluetoothDevice");
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
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(baseGroupId, "updateCsipDevices: propose new group id ", " for device ");
                m.append(cachedBluetoothDevice.mDevice);
                log(m.toString());
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
