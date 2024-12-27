package com.android.server.hdmi;

import java.util.Map;

public final class DetectTvSystemAudioModeSupportAction extends HdmiCecFeatureAction {
    public HdmiCecLocalDeviceAudioSystem.TvSystemAudioModeSupportedCallback mCallback;
    public int mSendSetSystemAudioModeRetryCount;

    public final void finishAction(boolean z) {
        this.mCallback.onResult(z);
        ((HdmiCecLocalDeviceAudioSystem) this.mSource).mTvSystemAudioModeSupport =
                Boolean.valueOf(z);
        finish(true);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 != i) {
            return;
        }
        if (i2 == 1) {
            finishAction(true);
            return;
        }
        if (i2 != 2) {
            return;
        }
        int i3 = this.mSendSetSystemAudioModeRetryCount + 1;
        this.mSendSetSystemAudioModeRetryCount = i3;
        if (i3 >= 5) {
            finishAction(false);
            return;
        }
        this.mState = 1;
        addTimer(1, 2000);
        sendCommand(
                HdmiCecMessageBuilder.buildCommandWithBooleanParam(
                        getSourceAddress(), 0, 114, true),
                new DetectTvSystemAudioModeSupportAction$$ExternalSyntheticLambda0(this));
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (hdmiCecMessage.mOpcode != 0 || this.mState != 1) {
            return false;
        }
        Map map = HdmiUtils.ADDRESS_TO_TYPE;
        byte[] bArr = hdmiCecMessage.mParams;
        if ((bArr[0] & 255) == 114) {
            if (bArr[1] == 1) {
                ((HdmiCecFeatureAction.ActionTimerHandler) this.mActionTimer).clearTimerMessage();
                this.mState = 2;
                addTimer(2, 300);
            } else {
                finishAction(false);
            }
            return true;
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        this.mState = 1;
        addTimer(1, 2000);
        sendCommand(
                HdmiCecMessageBuilder.buildCommandWithBooleanParam(
                        getSourceAddress(), 0, 114, true),
                new DetectTvSystemAudioModeSupportAction$$ExternalSyntheticLambda0(this));
    }
}
