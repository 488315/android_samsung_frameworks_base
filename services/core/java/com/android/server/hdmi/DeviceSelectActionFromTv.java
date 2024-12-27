package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.IHdmiControlCallback;
import android.util.Slog;

public final class DeviceSelectActionFromTv extends HdmiCecFeatureAction {
    static final int STATE_WAIT_FOR_DEVICE_POWER_ON = 3;
    static final int STATE_WAIT_FOR_REPORT_POWER_STATUS = 1;
    public final HdmiCecMessage mGivePowerStatus;
    public final boolean mIsCec20;
    public int mPowerStatusCounter;
    public final HdmiDeviceInfo mTarget;

    /* renamed from: com.android.server.hdmi.DeviceSelectActionFromTv$1, reason: invalid class name */
    public final class AnonymousClass1 implements HdmiControlService.SendMessageCallback {
        public AnonymousClass1() {}

        @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
        public final void onSendCompleted(int i) {
            if (i != 0) {
                DeviceSelectActionFromTv.this.finishWithCallback(7);
            }
        }
    }

    public DeviceSelectActionFromTv(
            HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv,
            HdmiDeviceInfo hdmiDeviceInfo,
            IHdmiControlCallback iHdmiControlCallback,
            boolean z) {
        super(hdmiCecLocalDeviceTv, iHdmiControlCallback);
        this.mPowerStatusCounter = 0;
        this.mTarget = hdmiDeviceInfo;
        this.mGivePowerStatus =
                HdmiCecMessage.build(getSourceAddress(), hdmiDeviceInfo.getLogicalAddress(), 143);
        this.mIsCec20 = z;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 != i) {
            Slog.w("DeviceSelect", "Timer in a wrong state. Ignored.");
            return;
        }
        if (i2 == 1) {
            if (((HdmiCecLocalDeviceTv) this.mSource).mService.isPowerStandbyOrTransient()) {
                finishWithCallback(6);
                return;
            } else {
                selectDevice$1();
                return;
            }
        }
        if (i2 == 2 || i2 == 3) {
            this.mPowerStatusCounter++;
            sendCommand(this.mGivePowerStatus, new AnonymousClass1());
            this.mState = 1;
            addTimer(1, 2000);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (hdmiCecMessage.mSource != this.mTarget.getLogicalAddress()
                || this.mState != 1
                || hdmiCecMessage.mOpcode != 144) {
            return false;
        }
        byte b = hdmiCecMessage.mParams[0];
        if (b == 0) {
            selectDevice$1();
        } else if (b != 1) {
            if (b != 2) {
                if (b != 3) {
                    return false;
                }
                if (this.mPowerStatusCounter < 4) {
                    this.mState = 2;
                    addTimer(2, 5000);
                } else {
                    selectDevice$1();
                }
            } else if (this.mPowerStatusCounter < 20) {
                this.mState = 3;
                addTimer(3, 5000);
            } else {
                selectDevice$1();
            }
        } else if (this.mPowerStatusCounter == 0) {
            if (!this.mIsCec20) {
                int logicalAddress = this.mTarget.getLogicalAddress();
                HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
                hdmiCecLocalDevice.sendUserControlPressedAndReleased(logicalAddress, 64);
                hdmiCecLocalDevice.sendUserControlPressedAndReleased(
                        this.mTarget.getLogicalAddress(), 109);
            }
            this.mState = 3;
            addTimer(3, 5000);
        } else {
            selectDevice$1();
        }
        return true;
    }

    public final void selectDevice$1() {
        if (!this.mIsCec20) {
            sendSetStreamPath$1();
        }
        finishWithCallback(0);
    }

    public final void sendSetStreamPath$1() {
        HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv = (HdmiCecLocalDeviceTv) this.mSource;
        hdmiCecLocalDeviceTv.mService.getLocalActiveSource().invalidate();
        hdmiCecLocalDeviceTv.setActivePath(this.mTarget.getPhysicalAddress());
        this.mService.sendCecCommand(
                HdmiCecMessage.build(
                        getSourceAddress(),
                        15,
                        134,
                        HdmiCecMessageBuilder.physicalAddressToParam(
                                this.mTarget.getPhysicalAddress())),
                null);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        sendSetStreamPath$1();
        HdmiCecMessage hdmiCecMessage = this.mGivePowerStatus;
        if (this.mIsCec20) {
            HdmiDeviceInfo cecDeviceInfo =
                    this.mSource.mService.mHdmiCecNetwork.getCecDeviceInfo(
                            this.mTarget.getLogicalAddress());
            int devicePowerStatus =
                    cecDeviceInfo != null ? cecDeviceInfo.getDevicePowerStatus() : -1;
            if (devicePowerStatus == -1) {
                sendCommand(hdmiCecMessage, new AnonymousClass1());
            } else if (devicePowerStatus == 0) {
                finishWithCallback(0);
                return;
            }
        } else {
            sendCommand(hdmiCecMessage, new AnonymousClass1());
        }
        this.mState = 1;
        addTimer(1, 2000);
    }
}
