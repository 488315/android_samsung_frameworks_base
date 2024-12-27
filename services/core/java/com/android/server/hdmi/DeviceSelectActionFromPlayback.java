package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.IHdmiControlCallback;
import android.util.Slog;

public final class DeviceSelectActionFromPlayback extends HdmiCecFeatureAction {
    static final int STATE_WAIT_FOR_ACTIVE_SOURCE_MESSAGE_AFTER_ROUTING_CHANGE = 4;
    private static final int STATE_WAIT_FOR_ACTIVE_SOURCE_MESSAGE_AFTER_SET_STREAM_PATH = 5;
    static final int STATE_WAIT_FOR_DEVICE_POWER_ON = 3;
    static final int STATE_WAIT_FOR_REPORT_POWER_STATUS = 1;
    public final HdmiCecMessage mGivePowerStatus;
    public final boolean mIsCec20;
    public int mPowerStatusCounter;
    public final HdmiDeviceInfo mTarget;

    /* renamed from: com.android.server.hdmi.DeviceSelectActionFromPlayback$1, reason: invalid class name */
    public final class AnonymousClass1 implements HdmiControlService.SendMessageCallback {
        public AnonymousClass1() {}

        @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
        public final void onSendCompleted(int i) {
            if (i != 0) {
                DeviceSelectActionFromPlayback.this.finishWithCallback(7);
            }
        }
    }

    public DeviceSelectActionFromPlayback(
            HdmiCecLocalDevicePlayback hdmiCecLocalDevicePlayback,
            HdmiDeviceInfo hdmiDeviceInfo,
            IHdmiControlCallback iHdmiControlCallback,
            boolean z) {
        super(hdmiCecLocalDevicePlayback, iHdmiControlCallback);
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
            Slog.w("DeviceSelectActionFromPlayback", "Timer in a wrong state. Ignored.");
            return;
        }
        if (i2 == 1) {
            selectDevice();
            addTimer(this.mState, 2000);
            return;
        }
        if (i2 == 3) {
            this.mPowerStatusCounter++;
            sendCommand(this.mGivePowerStatus, new AnonymousClass1());
            this.mState = 1;
            addTimer(1, 2000);
            return;
        }
        if (i2 != 4) {
            if (i2 != 5) {
                return;
            }
            finishWithCallback(1);
        } else {
            this.mService.sendCecCommand(
                    HdmiCecMessage.build(
                            getSourceAddress(),
                            15,
                            134,
                            HdmiCecMessageBuilder.physicalAddressToParam(
                                    this.mTarget.getPhysicalAddress())),
                    null);
            this.mState = 5;
            addTimer(5, 2000);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (hdmiCecMessage.mSource != this.mTarget.getLogicalAddress()) {
            return false;
        }
        int i = hdmiCecMessage.mOpcode;
        if (i == 130) {
            finishWithCallback(0);
            return true;
        }
        if (this.mState != 1 || i != 144) {
            return false;
        }
        byte b = hdmiCecMessage.mParams[0];
        if (b == 0) {
            selectDevice();
        } else if (b != 1) {
            if (b != 2) {
                if (b != 3) {
                    return false;
                }
                if (this.mPowerStatusCounter < 4) {
                    this.mState = 2;
                    addTimer(2, 5000);
                } else {
                    selectDevice();
                }
            } else if (this.mPowerStatusCounter < 2) {
                this.mState = 3;
                addTimer(3, 5000);
            } else {
                selectDevice();
            }
        } else if (this.mPowerStatusCounter == 0) {
            sendRoutingChange();
            this.mState = 3;
            addTimer(3, 5000);
        } else {
            selectDevice();
        }
        return true;
    }

    public final void selectDevice() {
        sendRoutingChange();
        this.mState = 4;
        addTimer(4, 2000);
    }

    public final void sendRoutingChange() {
        sendCommand(
                HdmiCecMessageBuilder.buildRoutingChange(
                        getSourceAddress(),
                        ((HdmiCecLocalDevicePlayback) this.mSource)
                                .mService.getLocalActiveSource().physicalAddress,
                        this.mTarget.getPhysicalAddress()));
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        sendRoutingChange();
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
                this.mState = 4;
                addTimer(4, 2000);
                return;
            }
        } else {
            sendCommand(hdmiCecMessage, new AnonymousClass1());
        }
        this.mState = 1;
        addTimer(1, 2000);
    }
}
