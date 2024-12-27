package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

import java.io.UnsupportedEncodingException;

public final class NewDeviceAction extends HdmiCecFeatureAction {
    public final int mDeviceLogicalAddress;
    public final int mDevicePhysicalAddress;
    public final int mDeviceType;
    public String mDisplayName;
    public HdmiDeviceInfo mOldDeviceInfo;
    public int mTimeoutRetry;
    public int mVendorId;

    public NewDeviceAction(HdmiCecLocalDevice hdmiCecLocalDevice, int i, int i2, int i3) {
        super(hdmiCecLocalDevice);
        this.mDeviceLogicalAddress = i;
        this.mDevicePhysicalAddress = i2;
        this.mDeviceType = i3;
        this.mVendorId = 16777215;
    }

    public final void addDeviceInfo() {
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        HdmiCecNetwork hdmiCecNetwork = hdmiCecLocalDevice.mService.mHdmiCecNetwork;
        hdmiCecNetwork.assertRunOnServiceThread();
        int i = this.mDeviceLogicalAddress;
        HdmiDeviceInfo cecDeviceInfo = hdmiCecNetwork.getCecDeviceInfo(i);
        int i2 = this.mDevicePhysicalAddress;
        boolean z = false;
        if (cecDeviceInfo != null && cecDeviceInfo.getPhysicalAddress() == i2) {
            z = true;
        }
        if (!z) {
            Slog.w(
                    "NewDeviceAction",
                    String.format(
                            "Device not found (%02x, %04x)",
                            Integer.valueOf(i), Integer.valueOf(i2)));
            return;
        }
        if (this.mDisplayName == null) {
            this.mDisplayName = "";
        }
        HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv = (HdmiCecLocalDeviceTv) hdmiCecLocalDevice;
        HdmiDeviceInfo.Builder portId =
                HdmiDeviceInfo.cecDeviceBuilder()
                        .setLogicalAddress(i)
                        .setPhysicalAddress(i2)
                        .setPortId(
                                hdmiCecLocalDeviceTv.mService.mHdmiCecNetwork
                                        .physicalAddressToPortId(i2));
        int i3 = this.mDeviceType;
        HdmiDeviceInfo build =
                portId.setDeviceType(i3)
                        .setVendorId(this.mVendorId)
                        .setDisplayName(this.mDisplayName)
                        .build();
        HdmiDeviceInfo hdmiDeviceInfo = this.mOldDeviceInfo;
        if (hdmiDeviceInfo != null
                && hdmiDeviceInfo.getLogicalAddress() == i
                && this.mOldDeviceInfo.getPhysicalAddress() == i2
                && this.mOldDeviceInfo.getDeviceType() == i3
                && this.mOldDeviceInfo.getVendorId() == this.mVendorId
                && this.mOldDeviceInfo.getDisplayName().equals(this.mDisplayName)) {
            hdmiCecLocalDeviceTv.processDelayedMessages(i);
            Slog.d("NewDeviceAction", "Ignore NewDevice, deviceInfo is same as current device");
            Slog.d(
                    "NewDeviceAction",
                    "Old:[" + this.mOldDeviceInfo.toString() + "]; New:[" + build.toString() + "]");
            return;
        }
        Slog.d("NewDeviceAction", "Add NewDevice:[" + build.toString() + "]");
        hdmiCecLocalDevice.mService.mHdmiCecNetwork.addCecDevice(build);
        hdmiCecLocalDeviceTv.processDelayedMessages(i);
        if (HdmiUtils.isEligibleAddressForDevice(5, i)) {
            hdmiCecLocalDeviceTv.onNewAvrAdded(build);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 == 0 || i2 != i) {
            return;
        }
        if (i == 1) {
            int i3 = this.mTimeoutRetry + 1;
            this.mTimeoutRetry = i3;
            if (i3 < 5) {
                requestOsdName(false);
                return;
            } else {
                requestVendorId(true);
                return;
            }
        }
        if (i == 2) {
            int i4 = this.mTimeoutRetry + 1;
            this.mTimeoutRetry = i4;
            if (i4 < 5) {
                requestVendorId(false);
            } else {
                addDeviceInfo();
                finish(true);
            }
        }
    }

    public final boolean mayProcessCommandIfCached(int i, int i2) {
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        hdmiCecLocalDevice.assertRunOnServiceThread();
        SparseArray sparseArray = (SparseArray) hdmiCecLocalDevice.mCecMessageCache.mCache.get(i);
        HdmiCecMessage hdmiCecMessage =
                sparseArray == null ? null : (HdmiCecMessage) sparseArray.get(i2);
        if (hdmiCecMessage != null) {
            return processCommand(hdmiCecMessage);
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        int i = hdmiCecMessage.mOpcode;
        if (this.mDeviceLogicalAddress != hdmiCecMessage.mSource) {
            return false;
        }
        int i2 = this.mState;
        byte[] bArr = hdmiCecMessage.mParams;
        if (i2 == 1) {
            if (i == 71) {
                try {
                    this.mDisplayName = new String(bArr, "US-ASCII");
                } catch (UnsupportedEncodingException e) {
                    Slog.e("NewDeviceAction", "Failed to get OSD name: " + e.getMessage());
                }
                requestVendorId(true);
                return true;
            }
            if (i == 0 && (bArr[0] & 255) == 70) {
                requestVendorId(true);
                return true;
            }
        } else if (i2 == 2) {
            if (i == 135) {
                this.mVendorId = HdmiUtils.threeBytesToInt(bArr);
                addDeviceInfo();
                finish(true);
                return true;
            }
            if (i == 0 && (bArr[0] & 255) == 140) {
                addDeviceInfo();
                finish(true);
                return true;
            }
        }
        return false;
    }

    public final void requestOsdName(boolean z) {
        if (z) {
            this.mTimeoutRetry = 0;
        }
        this.mState = 1;
        int i = this.mDeviceLogicalAddress;
        if (mayProcessCommandIfCached(i, 71)) {
            return;
        }
        this.mService.sendCecCommand(HdmiCecMessage.build(getSourceAddress(), i, 70), null);
        addTimer(this.mState, 2000);
    }

    public final void requestVendorId(boolean z) {
        if (z) {
            this.mTimeoutRetry = 0;
        }
        this.mState = 2;
        int i = this.mDeviceLogicalAddress;
        if (mayProcessCommandIfCached(i, 135)) {
            return;
        }
        this.mService.sendCecCommand(HdmiCecMessage.build(getSourceAddress(), i, 140), null);
        addTimer(this.mState, 2000);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        HdmiCecNetwork hdmiCecNetwork = hdmiCecLocalDevice.mService.mHdmiCecNetwork;
        int i = this.mDeviceLogicalAddress;
        HdmiDeviceInfo cecDeviceInfo = hdmiCecNetwork.getCecDeviceInfo(i);
        this.mOldDeviceInfo = cecDeviceInfo;
        int i2 = this.mDevicePhysicalAddress;
        if (cecDeviceInfo == null || cecDeviceInfo.getPhysicalAddress() != i2) {
            Slog.d("NewDeviceAction", "Start NewDeviceAction with default deviceInfo");
            HdmiDeviceInfo build =
                    HdmiDeviceInfo.cecDeviceBuilder()
                            .setLogicalAddress(i)
                            .setPhysicalAddress(i2)
                            .setPortId(
                                    ((HdmiCecLocalDeviceTv) hdmiCecLocalDevice)
                                            .mService.mHdmiCecNetwork.physicalAddressToPortId(i2))
                            .setDeviceType(this.mDeviceType)
                            .setVendorId(16777215)
                            .build();
            HdmiDeviceInfo hdmiDeviceInfo = this.mOldDeviceInfo;
            HdmiControlService hdmiControlService = hdmiCecLocalDevice.mService;
            if (hdmiDeviceInfo != null) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        i2,
                        "Remove device by NewDeviceAction, logical address conflicts: ",
                        "NewDeviceAction");
                hdmiControlService.mHdmiCecNetwork.removeCecDevice(hdmiCecLocalDevice, i);
            }
            hdmiControlService.mHdmiCecNetwork.addCecDevice(build);
        } else {
            Slog.d(
                    "NewDeviceAction",
                    "Start NewDeviceAction with old deviceInfo:["
                            + this.mOldDeviceInfo.toString()
                            + "]");
        }
        requestOsdName(true);
    }
}
