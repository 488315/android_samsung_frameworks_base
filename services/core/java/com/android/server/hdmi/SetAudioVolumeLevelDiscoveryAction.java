package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.IHdmiControlCallback;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SetAudioVolumeLevelDiscoveryAction extends HdmiCecFeatureAction {
    public final int mTargetAddress;

    public SetAudioVolumeLevelDiscoveryAction(
            HdmiCecLocalDevice hdmiCecLocalDevice,
            int i,
            HdmiCecLocalDevice.AnonymousClass3 anonymousClass3) {
        super(hdmiCecLocalDevice, (IHdmiControlCallback) anonymousClass3);
        this.mTargetAddress = i;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        HdmiCecNetwork hdmiCecNetwork = this.mSource.mService.mHdmiCecNetwork;
        HdmiDeviceInfo cecDeviceInfo = hdmiCecNetwork.getCecDeviceInfo(this.mTargetAddress);
        if (cecDeviceInfo == null) {
            finishWithCallback(5);
        } else {
            hdmiCecNetwork.updateCecDevice(
                    cecDeviceInfo.toBuilder()
                            .setDeviceFeatures(
                                    cecDeviceInfo.getDeviceFeatures().toBuilder()
                                            .setSetAudioVolumeLevelSupport(1)
                                            .build())
                            .build());
            finishWithCallback(0);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1 || hdmiCecMessage.mOpcode != 0) {
            return false;
        }
        byte[] bArr = hdmiCecMessage.mParams;
        if (bArr.length < 2 || (bArr[0] & 255) != 115) {
            return false;
        }
        if (hdmiCecMessage.mSource != this.mTargetAddress) {
            return false;
        }
        finishWithCallback(0);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        this.mService.sendCecCommand(
                SetAudioVolumeLevelMessage.build(getSourceAddress(), this.mTargetAddress, 127),
                new HdmiControlService
                        .SendMessageCallback() { // from class:
                                                 // com.android.server.hdmi.SetAudioVolumeLevelDiscoveryAction$$ExternalSyntheticLambda0
                    @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                    public final void onSendCompleted(int i) {
                        SetAudioVolumeLevelDiscoveryAction setAudioVolumeLevelDiscoveryAction =
                                SetAudioVolumeLevelDiscoveryAction.this;
                        if (i != 0) {
                            setAudioVolumeLevelDiscoveryAction.finishWithCallback(7);
                        } else {
                            setAudioVolumeLevelDiscoveryAction.mState = 1;
                            setAudioVolumeLevelDiscoveryAction.addTimer(1, 2000);
                        }
                    }
                });
    }
}
