package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiPortInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.location.gnss.hal.GnssNative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class HdmiCecNetwork {
    public final Handler mHandler;
    public final HdmiCecController mHdmiCecController;
    public final HdmiControlService mHdmiControlService;
    public final HdmiMhlControllerStub mHdmiMhlController;
    public final Object mLock;
    public UnmodifiableSparseIntArray mPortIdMap;
    public UnmodifiableSparseArray mPortInfoMap;
    public final SparseArray mLocalDevices = new SparseArray();
    public final SparseArray mDeviceInfos = new SparseArray();
    public final ArraySet mCecSwitches = new ArraySet();
    public List mSafeAllDeviceInfos = Collections.emptyList();
    public List mSafeExternalInputs = Collections.emptyList();
    public List mPortInfo = Collections.emptyList();
    public int mPhysicalAddress = GnssNative.GNSS_AIDING_TYPE_ALL;

    public HdmiCecNetwork(
            HdmiControlService hdmiControlService,
            HdmiCecController hdmiCecController,
            HdmiMhlControllerStub hdmiMhlControllerStub) {
        this.mHdmiControlService = hdmiControlService;
        this.mHdmiCecController = hdmiCecController;
        this.mHdmiMhlController = hdmiMhlControllerStub;
        this.mHandler = new Handler(hdmiControlService.mHandler.getLooper());
        this.mLock = hdmiControlService.mLock;
    }

    public final void addCecDevice(HdmiDeviceInfo hdmiDeviceInfo) {
        assertRunOnServiceThread();
        HdmiDeviceInfo addDeviceInfo = addDeviceInfo(hdmiDeviceInfo);
        if (isLocalDeviceAddress(hdmiDeviceInfo.getLogicalAddress())) {
            return;
        }
        this.mHdmiControlService.checkAndUpdateAbsoluteVolumeBehavior();
        if (hdmiDeviceInfo.getPhysicalAddress() == 65535) {
            return;
        }
        if (addDeviceInfo == null || addDeviceInfo.getPhysicalAddress() == 65535) {
            invokeDeviceEventListener(hdmiDeviceInfo, 1);
        } else {
            if (addDeviceInfo.equals(hdmiDeviceInfo)) {
                return;
            }
            invokeDeviceEventListener(addDeviceInfo, 2);
            invokeDeviceEventListener(hdmiDeviceInfo, 1);
        }
    }

    public final HdmiDeviceInfo addDeviceInfo(HdmiDeviceInfo hdmiDeviceInfo) {
        assertRunOnServiceThread();
        HdmiDeviceInfo cecDeviceInfo = getCecDeviceInfo(hdmiDeviceInfo.getLogicalAddress());
        int logicalAddress = hdmiDeviceInfo.getLogicalAddress();
        int physicalAddress = hdmiDeviceInfo.getPhysicalAddress();
        HdmiControlService hdmiControlService = this.mHdmiControlService;
        if (physicalAddress != hdmiControlService.mHdmiCecNetwork.getPhysicalAddress()) {
            Iterator it = ((ArrayList) hdmiControlService.getAllCecLocalDevices()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                HdmiCecLocalDevice hdmiCecLocalDevice = (HdmiCecLocalDevice) it.next();
                if (hdmiCecLocalDevice.getDeviceInfo().getLogicalAddress() == logicalAddress) {
                    HdmiLogger.debug(
                            "allocate logical address for " + hdmiCecLocalDevice.getDeviceInfo(),
                            new Object[0]);
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(hdmiCecLocalDevice);
                    hdmiControlService.allocateLogicalAddress(arrayList, 4);
                    break;
                }
            }
        }
        if (cecDeviceInfo != null) {
            removeDeviceInfo(hdmiDeviceInfo.getId());
        }
        this.mDeviceInfos.append(hdmiDeviceInfo.getId(), hdmiDeviceInfo);
        updateSafeDeviceInfoList();
        return cecDeviceInfo;
    }

    public final void assertRunOnServiceThread() {
        if (Looper.myLooper() != this.mHandler.getLooper()) {
            throw new IllegalStateException("Should run on service thread.");
        }
    }

    public final void clearDeviceList() {
        assertRunOnServiceThread();
        Iterator it = ((ArrayList) HdmiUtils.sparseArrayToList(this.mDeviceInfos)).iterator();
        while (it.hasNext()) {
            HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) it.next();
            if (hdmiDeviceInfo.getPhysicalAddress() != getPhysicalAddress()
                    && hdmiDeviceInfo.getPhysicalAddress() != 65535) {
                invokeDeviceEventListener(hdmiDeviceInfo, 2);
            }
        }
        this.mDeviceInfos.clear();
        updateSafeDeviceInfoList();
    }

    public final HdmiDeviceInfo getCecDeviceInfo(int i) {
        assertRunOnServiceThread();
        return (HdmiDeviceInfo) this.mDeviceInfos.get(HdmiDeviceInfo.idForCecDevice(i));
    }

    public final List getDeviceInfoList() {
        assertRunOnServiceThread();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mDeviceInfos.size(); i++) {
            HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) this.mDeviceInfos.valueAt(i);
            if (!isLocalDeviceAddress(hdmiDeviceInfo.getLogicalAddress())) {
                arrayList.add(hdmiDeviceInfo);
            }
        }
        return arrayList;
    }

    public final HdmiCecLocalDevice getLocalDevice(int i) {
        return (HdmiCecLocalDevice) this.mLocalDevices.get(i);
    }

    public final List getLocalDeviceList() {
        assertRunOnServiceThread();
        return HdmiUtils.sparseArrayToList(this.mLocalDevices);
    }

    public final int getPhysicalAddress() {
        if (this.mPhysicalAddress == 65535) {
            HdmiCecController hdmiCecController = this.mHdmiCecController;
            hdmiCecController.assertRunOnServiceThread();
            this.mPhysicalAddress = hdmiCecController.mNativeWrapperImpl.nativeGetPhysicalAddress();
        }
        return this.mPhysicalAddress;
    }

    public final HdmiPortInfo getPortInfo(int i) {
        return (HdmiPortInfo) this.mPortInfoMap.mArray.get(i, null);
    }

    public final HdmiDeviceInfo getSafeCecDeviceInfo(int i) {
        for (HdmiDeviceInfo hdmiDeviceInfo : this.mSafeAllDeviceInfos) {
            if (hdmiDeviceInfo.isCecDevice() && hdmiDeviceInfo.getLogicalAddress() == i) {
                return hdmiDeviceInfo;
            }
        }
        return null;
    }

    public final boolean hideDevicesBehindLegacySwitch(HdmiDeviceInfo hdmiDeviceInfo) {
        if (!isLocalDeviceAddress(0)) {
            return false;
        }
        int physicalAddress = hdmiDeviceInfo.getPhysicalAddress();
        Iterator it = this.mCecSwitches.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            int i = 0;
            while (true) {
                if (i > 12) {
                    break;
                }
                if (((physicalAddress >> i) & 15) == 0) {
                    i += 4;
                } else if (((intValue >> i) & 15) == 0) {
                    int i2 = i + 4;
                    if ((physicalAddress >> i2) == (intValue >> i2)) {
                        return false;
                    }
                } else {
                    continue;
                }
            }
        }
        return hdmiDeviceInfo.getPhysicalAddress() != 65535;
    }

    public void initPortInfo() {
        HdmiPortInfo[] hdmiPortInfoArr;
        assertRunOnServiceThread();
        HdmiCecController hdmiCecController = this.mHdmiCecController;
        if (hdmiCecController != null) {
            hdmiPortInfoArr = hdmiCecController.mNativeWrapperImpl.nativeGetPortInfos();
            this.mPhysicalAddress = GnssNative.GNSS_AIDING_TYPE_ALL;
        } else {
            hdmiPortInfoArr = null;
        }
        if (hdmiPortInfoArr == null) {
            return;
        }
        SparseArray sparseArray = new SparseArray();
        SparseIntArray sparseIntArray = new SparseIntArray();
        SparseArray sparseArray2 = new SparseArray();
        for (HdmiPortInfo hdmiPortInfo : hdmiPortInfoArr) {
            sparseIntArray.put(hdmiPortInfo.getAddress(), hdmiPortInfo.getId());
            sparseArray.put(hdmiPortInfo.getId(), hdmiPortInfo);
            sparseArray2.put(
                    hdmiPortInfo.getId(),
                    HdmiDeviceInfo.hardwarePort(hdmiPortInfo.getAddress(), hdmiPortInfo.getId()));
        }
        this.mPortIdMap = new UnmodifiableSparseIntArray(sparseIntArray);
        this.mPortInfoMap = new UnmodifiableSparseArray(sparseArray);
        HdmiMhlControllerStub hdmiMhlControllerStub = this.mHdmiMhlController;
        if (hdmiMhlControllerStub == null) {
            return;
        }
        hdmiMhlControllerStub.getClass();
        HdmiPortInfo[] hdmiPortInfoArr2 = HdmiMhlControllerStub.EMPTY_PORT_INFO;
        ArraySet arraySet = new ArraySet(hdmiPortInfoArr2.length);
        for (HdmiPortInfo hdmiPortInfo2 : hdmiPortInfoArr2) {
            if (hdmiPortInfo2.isMhlSupported()) {
                arraySet.add(Integer.valueOf(hdmiPortInfo2.getId()));
            }
        }
        if (arraySet.isEmpty()) {
            this.mPortInfo = Collections.unmodifiableList(Arrays.asList(hdmiPortInfoArr));
            return;
        }
        ArrayList arrayList = new ArrayList(hdmiPortInfoArr.length);
        for (HdmiPortInfo hdmiPortInfo3 : hdmiPortInfoArr) {
            if (arraySet.contains(Integer.valueOf(hdmiPortInfo3.getId()))) {
                arrayList.add(
                        new HdmiPortInfo.Builder(
                                        hdmiPortInfo3.getId(),
                                        hdmiPortInfo3.getType(),
                                        hdmiPortInfo3.getAddress())
                                .setCecSupported(hdmiPortInfo3.isCecSupported())
                                .setMhlSupported(true)
                                .setArcSupported(hdmiPortInfo3.isArcSupported())
                                .setEarcSupported(hdmiPortInfo3.isEarcSupported())
                                .build());
            } else {
                arrayList.add(hdmiPortInfo3);
            }
        }
        this.mPortInfo = Collections.unmodifiableList(arrayList);
    }

    public final void invokeDeviceEventListener(HdmiDeviceInfo hdmiDeviceInfo, int i) {
        if (hideDevicesBehindLegacySwitch(hdmiDeviceInfo)) {
            return;
        }
        HdmiControlService hdmiControlService = this.mHdmiControlService;
        synchronized (hdmiControlService.mLock) {
            Iterator it = hdmiControlService.mDeviceEventListenerRecords.iterator();
            while (it.hasNext()) {
                try {
                    ((HdmiControlService.DeviceEventListenerRecord) it.next())
                            .mListener.onStatusChanged(hdmiDeviceInfo, i);
                } catch (RemoteException e) {
                    Slog.e("HdmiControlService", "Failed to report device event:" + e);
                }
            }
        }
    }

    public final boolean isAllocatedLocalDeviceAddress(int i) {
        assertRunOnServiceThread();
        for (int i2 = 0; i2 < this.mLocalDevices.size(); i2++) {
            HdmiCecLocalDevice hdmiCecLocalDevice =
                    (HdmiCecLocalDevice) this.mLocalDevices.valueAt(i2);
            hdmiCecLocalDevice.assertRunOnServiceThread();
            if (i == hdmiCecLocalDevice.mDeviceInfo.getLogicalAddress()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isLocalDeviceAddress(int i) {
        for (int i2 = 0; i2 < this.mLocalDevices.size(); i2++) {
            if (((HdmiCecLocalDevice) this.mLocalDevices.get(this.mLocalDevices.keyAt(i2)))
                            .getDeviceInfo()
                            .getLogicalAddress()
                    == i) {
                return true;
            }
        }
        return false;
    }

    public final int physicalAddressToPortId(int i) {
        int physicalAddress = getPhysicalAddress();
        if (i == physicalAddress) {
            return 0;
        }
        int i2 = 61440;
        int i3 = physicalAddress;
        int i4 = 61440;
        while (i3 != 0) {
            i3 = physicalAddress & i4;
            i2 |= i4;
            i4 >>= 4;
        }
        return this.mPortIdMap.mArray.get(i & i2, -1);
    }

    public final void removeCecDevice(HdmiCecLocalDevice hdmiCecLocalDevice, int i) {
        assertRunOnServiceThread();
        HdmiDeviceInfo removeDeviceInfo = removeDeviceInfo(HdmiDeviceInfo.idForCecDevice(i));
        this.mHdmiControlService.checkAndUpdateAbsoluteVolumeBehavior();
        hdmiCecLocalDevice.mCecMessageCache.mCache.remove(i);
        if (removeDeviceInfo.getPhysicalAddress() == 65535) {
            return;
        }
        invokeDeviceEventListener(removeDeviceInfo, 2);
    }

    public final void removeCecSwitches(int i) {
        Iterator it = this.mCecSwitches.iterator();
        while (it.hasNext()) {
            int physicalAddressToPortId = physicalAddressToPortId(((Integer) it.next()).intValue());
            if (physicalAddressToPortId == i || physicalAddressToPortId == -1) {
                it.remove();
            }
        }
    }

    public final HdmiDeviceInfo removeDeviceInfo(int i) {
        assertRunOnServiceThread();
        HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) this.mDeviceInfos.get(i);
        if (hdmiDeviceInfo != null) {
            this.mDeviceInfos.remove(i);
        }
        updateSafeDeviceInfoList();
        return hdmiDeviceInfo;
    }

    public final void updateCecDevice(HdmiDeviceInfo hdmiDeviceInfo) {
        assertRunOnServiceThread();
        HdmiDeviceInfo addDeviceInfo = addDeviceInfo(hdmiDeviceInfo);
        if (hdmiDeviceInfo.getPhysicalAddress() == 65535) {
            return;
        }
        if (addDeviceInfo == null || addDeviceInfo.getPhysicalAddress() == 65535) {
            invokeDeviceEventListener(hdmiDeviceInfo, 1);
        } else {
            if (addDeviceInfo.equals(hdmiDeviceInfo)) {
                return;
            }
            invokeDeviceEventListener(hdmiDeviceInfo, 3);
        }
    }

    public final boolean updateCecSwitchInfo(int i, int i2, int i3) {
        if (i == 15 && i2 == 6) {
            this.mCecSwitches.add(Integer.valueOf(i3));
            updateSafeDeviceInfoList();
            return true;
        }
        if (i2 != 5) {
            return false;
        }
        this.mCecSwitches.add(Integer.valueOf(i3));
        return false;
    }

    public final void updateDeviceCecVersion(int i, int i2) {
        assertRunOnServiceThread();
        HdmiDeviceInfo cecDeviceInfo = getCecDeviceInfo(i);
        if (cecDeviceInfo == null) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "Can not update CEC version of non-existing device:", "HdmiCecNetwork");
        } else {
            if (cecDeviceInfo.getCecVersion() == i2) {
                return;
            }
            updateCecDevice(cecDeviceInfo.toBuilder().setCecVersion(i2).build());
        }
    }

    public final void updateDevicePowerStatus(int i, int i2) {
        HdmiDeviceInfo cecDeviceInfo = getCecDeviceInfo(i);
        if (cecDeviceInfo == null) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "Can not update power status of non-existing device:", "HdmiCecNetwork");
        } else {
            if (cecDeviceInfo.getDevicePowerStatus() == i2) {
                return;
            }
            updateCecDevice(cecDeviceInfo.toBuilder().setDevicePowerStatus(i2).build());
        }
    }

    public final void updateSafeDeviceInfoList() {
        assertRunOnServiceThread();
        List sparseArrayToList = HdmiUtils.sparseArrayToList(this.mDeviceInfos);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mDeviceInfos.size(); i++) {
            HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) this.mDeviceInfos.valueAt(i);
            if (!isLocalDeviceAddress(hdmiDeviceInfo.getLogicalAddress())
                    && hdmiDeviceInfo.isSourceType()
                    && !hideDevicesBehindLegacySwitch(hdmiDeviceInfo)) {
                arrayList.add(hdmiDeviceInfo);
            }
        }
        this.mSafeAllDeviceInfos = sparseArrayToList;
        this.mSafeExternalInputs = arrayList;
    }
}
