package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.IHdmiControlCallback;

public final class DevicePowerStatusAction extends HdmiCecFeatureAction {
    public int mRetriesOnTimeout;
    public final int mTargetAddress;

    public DevicePowerStatusAction(
            HdmiCecLocalDeviceSource hdmiCecLocalDeviceSource,
            IHdmiControlCallback iHdmiControlCallback) {
        super(hdmiCecLocalDeviceSource, iHdmiControlCallback);
        this.mRetriesOnTimeout = 1;
        this.mTargetAddress = 0;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        if (this.mState == i && i == 1) {
            int i2 = this.mRetriesOnTimeout;
            if (i2 <= 0) {
                finishWithCallback(-1);
            } else {
                this.mRetriesOnTimeout = i2 - 1;
                start();
            }
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState == 1) {
            if (this.mTargetAddress == hdmiCecMessage.mSource && hdmiCecMessage.mOpcode == 144) {
                finishWithCallback(hdmiCecMessage.mParams[0]);
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        HdmiDeviceInfo cecDeviceInfo;
        int devicePowerStatus;
        HdmiControlService hdmiControlService = this.mSource.mService;
        int cecVersion = hdmiControlService.getCecVersion();
        int i = this.mTargetAddress;
        if (cecVersion >= 6
                && (cecDeviceInfo = hdmiControlService.mHdmiCecNetwork.getCecDeviceInfo(i)) != null
                && cecDeviceInfo.getCecVersion() >= 6
                && (devicePowerStatus = cecDeviceInfo.getDevicePowerStatus()) != -1) {
            finishWithCallback(devicePowerStatus);
            return;
        }
        this.mService.sendCecCommand(
                HdmiCecMessage.build(getSourceAddress(), i, 143),
                new HdmiControlService
                        .SendMessageCallback() { // from class:
                                                 // com.android.server.hdmi.DevicePowerStatusAction$$ExternalSyntheticLambda0
                    @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                    public final void onSendCompleted(int i2) {
                        DevicePowerStatusAction devicePowerStatusAction =
                                DevicePowerStatusAction.this;
                        if (i2 == 1) {
                            devicePowerStatusAction.finishWithCallback(-1);
                        } else {
                            devicePowerStatusAction.getClass();
                        }
                    }
                });
        this.mState = 1;
        addTimer(1, 2000);
    }
}
