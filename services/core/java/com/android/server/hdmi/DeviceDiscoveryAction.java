package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.util.Slog;
import com.android.internal.util.Preconditions;
import com.android.server.hdmi.HdmiControlService;
import com.android.server.location.gnss.hal.GnssNative;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class DeviceDiscoveryAction extends HdmiCecFeatureAction {
    public final DeviceDiscoveryCallback mCallback;
    public final int mDelayPeriod;
    public final ArrayList mDevices;
    public boolean mIsTvDevice;
    public int mProcessedDeviceCount;
    public int mTimeoutRetry;

    public interface DeviceDiscoveryCallback {
        void onDeviceDiscoveryDone(List list);
    }

    public final boolean verifyValidLogicalAddress(int i) {
        return i >= 0 && i < 15;
    }

    public final class DeviceInfo {
        public int mDeviceType;
        public String mDisplayName;
        public final int mLogicalAddress;
        public int mPhysicalAddress;
        public int mPortId;
        public int mPowerStatus;
        public int mVendorId;

        public DeviceInfo(int i) {
            this.mPhysicalAddress = GnssNative.GNSS_AIDING_TYPE_ALL;
            this.mPortId = -1;
            this.mVendorId = 16777215;
            this.mPowerStatus = -1;
            this.mDisplayName = "";
            this.mDeviceType = -1;
            this.mLogicalAddress = i;
        }

        public final HdmiDeviceInfo toHdmiDeviceInfo() {
            return HdmiDeviceInfo.cecDeviceBuilder().setLogicalAddress(this.mLogicalAddress).setPhysicalAddress(this.mPhysicalAddress).setPortId(this.mPortId).setVendorId(this.mVendorId).setDeviceType(this.mDeviceType).setDisplayName(this.mDisplayName).setDevicePowerStatus(this.mPowerStatus).build();
        }
    }

    public DeviceDiscoveryAction(HdmiCecLocalDevice hdmiCecLocalDevice, DeviceDiscoveryCallback deviceDiscoveryCallback, int i) {
        super(hdmiCecLocalDevice);
        this.mDevices = new ArrayList();
        this.mProcessedDeviceCount = 0;
        this.mTimeoutRetry = 0;
        this.mIsTvDevice = localDevice().mService.isTvDevice();
        Objects.requireNonNull(deviceDiscoveryCallback);
        this.mCallback = deviceDiscoveryCallback;
        this.mDelayPeriod = i;
    }

    public DeviceDiscoveryAction(HdmiCecLocalDevice hdmiCecLocalDevice, DeviceDiscoveryCallback deviceDiscoveryCallback) {
        this(hdmiCecLocalDevice, deviceDiscoveryCallback, 0);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean start() {
        this.mDevices.clear();
        this.mState = 1;
        pollDevices(new HdmiControlService.DevicePollingCallback() { // from class: com.android.server.hdmi.DeviceDiscoveryAction.1
            @Override // com.android.server.hdmi.HdmiControlService.DevicePollingCallback
            public void onPollingFinished(List list) {
                if (list.isEmpty()) {
                    Slog.v("DeviceDiscoveryAction", "No device is detected.");
                    DeviceDiscoveryAction.this.wrapUpAndFinish();
                    return;
                }
                Slog.v("DeviceDiscoveryAction", "Device detected: " + list);
                DeviceDiscoveryAction.this.allocateDevices(list);
                if (DeviceDiscoveryAction.this.mDelayPeriod > 0) {
                    DeviceDiscoveryAction.this.startToDelayAction();
                } else {
                    DeviceDiscoveryAction.this.startPhysicalAddressStage();
                }
            }
        }, 131073, 1);
        return true;
    }

    public final void allocateDevices(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.mDevices.add(new DeviceInfo(((Integer) it.next()).intValue()));
        }
    }

    public final void startToDelayAction() {
        Slog.v("DeviceDiscoveryAction", "Waiting for connected devices to be ready");
        this.mState = 5;
        checkAndProceedStage();
    }

    public final void startPhysicalAddressStage() {
        Slog.v("DeviceDiscoveryAction", "Start [Physical Address Stage]:" + this.mDevices.size());
        this.mProcessedDeviceCount = 0;
        this.mState = 2;
        checkAndProceedStage();
    }

    public final void queryPhysicalAddress(int i) {
        if (!verifyValidLogicalAddress(i)) {
            checkAndProceedStage();
            return;
        }
        this.mActionTimer.clearTimerMessage();
        if (mayProcessMessageIfCached(i, 132)) {
            return;
        }
        sendCommand(HdmiCecMessageBuilder.buildGivePhysicalAddress(getSourceAddress(), i));
        addTimer(this.mState, 2000);
    }

    public final void delayActionWithTimePeriod(int i) {
        this.mActionTimer.clearTimerMessage();
        addTimer(this.mState, i);
    }

    public final void startOsdNameStage() {
        Slog.v("DeviceDiscoveryAction", "Start [Osd Name Stage]:" + this.mDevices.size());
        this.mProcessedDeviceCount = 0;
        this.mState = 3;
        checkAndProceedStage();
    }

    public final void queryOsdName(int i) {
        if (!verifyValidLogicalAddress(i)) {
            checkAndProceedStage();
            return;
        }
        this.mActionTimer.clearTimerMessage();
        if (mayProcessMessageIfCached(i, 71)) {
            return;
        }
        sendCommand(HdmiCecMessageBuilder.buildGiveOsdNameCommand(getSourceAddress(), i));
        addTimer(this.mState, 2000);
    }

    public final void startVendorIdStage() {
        Slog.v("DeviceDiscoveryAction", "Start [Vendor Id Stage]:" + this.mDevices.size());
        this.mProcessedDeviceCount = 0;
        this.mState = 4;
        checkAndProceedStage();
    }

    public final void queryVendorId(int i) {
        if (!verifyValidLogicalAddress(i)) {
            checkAndProceedStage();
            return;
        }
        this.mActionTimer.clearTimerMessage();
        if (mayProcessMessageIfCached(i, 135)) {
            return;
        }
        sendCommand(HdmiCecMessageBuilder.buildGiveDeviceVendorIdCommand(getSourceAddress(), i));
        addTimer(this.mState, 2000);
    }

    public final void startPowerStatusStage() {
        Slog.v("DeviceDiscoveryAction", "Start [Power Status Stage]:" + this.mDevices.size());
        this.mProcessedDeviceCount = 0;
        this.mState = 6;
        checkAndProceedStage();
    }

    public final void queryPowerStatus(int i) {
        if (!verifyValidLogicalAddress(i)) {
            checkAndProceedStage();
            return;
        }
        this.mActionTimer.clearTimerMessage();
        if (mayProcessMessageIfCached(i, 144)) {
            return;
        }
        sendCommand(HdmiCecMessageBuilder.buildGiveDevicePowerStatus(getSourceAddress(), i));
        addTimer(this.mState, 2000);
    }

    public final boolean mayProcessMessageIfCached(int i, int i2) {
        HdmiCecMessage message = getCecMessageCache().getMessage(i, i2);
        if (message == null) {
            return false;
        }
        processCommand(message);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        int i = this.mState;
        if (i == 2) {
            if (hdmiCecMessage.getOpcode() != 132) {
                return false;
            }
            handleReportPhysicalAddress(hdmiCecMessage);
            return true;
        }
        if (i == 3) {
            if (hdmiCecMessage.getOpcode() == 71) {
                handleSetOsdName(hdmiCecMessage);
                return true;
            }
            if (hdmiCecMessage.getOpcode() != 0 || (hdmiCecMessage.getParams()[0] & 255) != 70) {
                return false;
            }
            handleSetOsdName(hdmiCecMessage);
            return true;
        }
        if (i == 4) {
            if (hdmiCecMessage.getOpcode() == 135) {
                handleVendorId(hdmiCecMessage);
                return true;
            }
            if (hdmiCecMessage.getOpcode() != 0 || (hdmiCecMessage.getParams()[0] & 255) != 140) {
                return false;
            }
            handleVendorId(hdmiCecMessage);
            return true;
        }
        if (i != 6) {
            return false;
        }
        if (hdmiCecMessage.getOpcode() == 144) {
            handleReportPowerStatus(hdmiCecMessage);
            return true;
        }
        if (hdmiCecMessage.getOpcode() != 0 || (hdmiCecMessage.getParams()[0] & 255) != 144) {
            return false;
        }
        handleReportPowerStatus(hdmiCecMessage);
        return true;
    }

    public final void handleReportPhysicalAddress(HdmiCecMessage hdmiCecMessage) {
        Preconditions.checkState(this.mProcessedDeviceCount < this.mDevices.size());
        DeviceInfo deviceInfo = (DeviceInfo) this.mDevices.get(this.mProcessedDeviceCount);
        if (deviceInfo.mLogicalAddress != hdmiCecMessage.getSource()) {
            Slog.w("DeviceDiscoveryAction", "Unmatched address[expected:" + deviceInfo.mLogicalAddress + ", actual:" + hdmiCecMessage.getSource());
            return;
        }
        byte[] params = hdmiCecMessage.getParams();
        deviceInfo.mPhysicalAddress = HdmiUtils.twoBytesToInt(params);
        deviceInfo.mPortId = getPortId(deviceInfo.mPhysicalAddress);
        deviceInfo.mDeviceType = params[2] & 255;
        deviceInfo.mDisplayName = "";
        if (this.mIsTvDevice) {
            localDevice().mService.getHdmiCecNetwork().updateCecSwitchInfo(deviceInfo.mLogicalAddress, deviceInfo.mDeviceType, deviceInfo.mPhysicalAddress);
        }
        increaseProcessedDeviceCount();
        checkAndProceedStage();
    }

    public final int getPortId(int i) {
        return this.mIsTvDevice ? m27tv().getPortId(i) : source().getPortId(i);
    }

    public final void handleSetOsdName(HdmiCecMessage hdmiCecMessage) {
        Preconditions.checkState(this.mProcessedDeviceCount < this.mDevices.size());
        DeviceInfo deviceInfo = (DeviceInfo) this.mDevices.get(this.mProcessedDeviceCount);
        if (deviceInfo.mLogicalAddress != hdmiCecMessage.getSource()) {
            Slog.w("DeviceDiscoveryAction", "Unmatched address[expected:" + deviceInfo.mLogicalAddress + ", actual:" + hdmiCecMessage.getSource());
            return;
        }
        String str = "";
        try {
            if (hdmiCecMessage.getOpcode() != 0) {
                str = new String(hdmiCecMessage.getParams(), "US-ASCII");
            }
        } catch (UnsupportedEncodingException unused) {
            Slog.w("DeviceDiscoveryAction", "Failed to decode display name: " + hdmiCecMessage.toString());
        }
        deviceInfo.mDisplayName = str;
        increaseProcessedDeviceCount();
        checkAndProceedStage();
    }

    public final void handleVendorId(HdmiCecMessage hdmiCecMessage) {
        Preconditions.checkState(this.mProcessedDeviceCount < this.mDevices.size());
        DeviceInfo deviceInfo = (DeviceInfo) this.mDevices.get(this.mProcessedDeviceCount);
        if (deviceInfo.mLogicalAddress != hdmiCecMessage.getSource()) {
            Slog.w("DeviceDiscoveryAction", "Unmatched address[expected:" + deviceInfo.mLogicalAddress + ", actual:" + hdmiCecMessage.getSource());
            return;
        }
        if (hdmiCecMessage.getOpcode() != 0) {
            deviceInfo.mVendorId = HdmiUtils.threeBytesToInt(hdmiCecMessage.getParams());
        }
        increaseProcessedDeviceCount();
        checkAndProceedStage();
    }

    public final void handleReportPowerStatus(HdmiCecMessage hdmiCecMessage) {
        Preconditions.checkState(this.mProcessedDeviceCount < this.mDevices.size());
        DeviceInfo deviceInfo = (DeviceInfo) this.mDevices.get(this.mProcessedDeviceCount);
        if (deviceInfo.mLogicalAddress != hdmiCecMessage.getSource()) {
            Slog.w("DeviceDiscoveryAction", "Unmatched address[expected:" + deviceInfo.mLogicalAddress + ", actual:" + hdmiCecMessage.getSource());
            return;
        }
        if (hdmiCecMessage.getOpcode() != 0) {
            deviceInfo.mPowerStatus = hdmiCecMessage.getParams()[0] & 255;
        }
        increaseProcessedDeviceCount();
        checkAndProceedStage();
    }

    public final void increaseProcessedDeviceCount() {
        this.mProcessedDeviceCount++;
        this.mTimeoutRetry = 0;
    }

    public final void removeDevice(int i) {
        this.mDevices.remove(i);
    }

    public final void wrapUpAndFinish() {
        Slog.v("DeviceDiscoveryAction", "---------Wrap up Device Discovery:[" + this.mDevices.size() + "]---------");
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mDevices.iterator();
        while (it.hasNext()) {
            HdmiDeviceInfo hdmiDeviceInfo = ((DeviceInfo) it.next()).toHdmiDeviceInfo();
            Slog.v("DeviceDiscoveryAction", " DeviceInfo: " + hdmiDeviceInfo);
            arrayList.add(hdmiDeviceInfo);
        }
        Slog.v("DeviceDiscoveryAction", "--------------------------------------------");
        this.mCallback.onDeviceDiscoveryDone(arrayList);
        finish();
        if (this.mIsTvDevice) {
            m27tv().processAllDelayedMessages();
        }
    }

    public final void checkAndProceedStage() {
        if (this.mDevices.isEmpty()) {
            wrapUpAndFinish();
            return;
        }
        if (this.mProcessedDeviceCount == this.mDevices.size()) {
            this.mProcessedDeviceCount = 0;
            int i = this.mState;
            if (i == 2) {
                startOsdNameStage();
                return;
            }
            if (i == 3) {
                startVendorIdStage();
                return;
            } else if (i == 4) {
                startPowerStatusStage();
                return;
            } else {
                if (i != 6) {
                    return;
                }
                wrapUpAndFinish();
                return;
            }
        }
        sendQueryCommand();
    }

    public final void sendQueryCommand() {
        int i = ((DeviceInfo) this.mDevices.get(this.mProcessedDeviceCount)).mLogicalAddress;
        int i2 = this.mState;
        if (i2 == 2) {
            queryPhysicalAddress(i);
            return;
        }
        if (i2 == 3) {
            queryOsdName(i);
            return;
        }
        if (i2 == 4) {
            queryVendorId(i);
        } else if (i2 == 5) {
            delayActionWithTimePeriod(this.mDelayPeriod);
        } else {
            if (i2 != 6) {
                return;
            }
            queryPowerStatus(i);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 == 0 || i2 != i) {
            return;
        }
        if (i2 == 5) {
            startPhysicalAddressStage();
            return;
        }
        int i3 = this.mTimeoutRetry + 1;
        this.mTimeoutRetry = i3;
        if (i3 < 5) {
            sendQueryCommand();
            return;
        }
        this.mTimeoutRetry = 0;
        Slog.v("DeviceDiscoveryAction", "Timeout[State=" + this.mState + ", Processed=" + this.mProcessedDeviceCount);
        int i4 = this.mState;
        if (i4 != 6 && i4 != 3) {
            removeDevice(this.mProcessedDeviceCount);
        } else {
            increaseProcessedDeviceCount();
        }
        checkAndProceedStage();
    }
}
