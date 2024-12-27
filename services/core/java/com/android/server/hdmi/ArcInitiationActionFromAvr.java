package com.android.server.hdmi;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ArcInitiationActionFromAvr extends HdmiCecFeatureAction {
    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 == i && i2 == 1) {
            HdmiLogger.debug("handleInitiateArcTimeout", new Object[0]);
            finish(true);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1) {
            return false;
        }
        int i = hdmiCecMessage.mOpcode;
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        if (i == 0) {
            if ((hdmiCecMessage.mParams[0] & 255) != 192) {
                return false;
            }
            ((HdmiCecLocalDeviceAudioSystem) hdmiCecLocalDevice).setArcStatus(false);
            finish(true);
            return true;
        }
        if (i == 193) {
            this.mState = 2;
            finish(true);
            return true;
        }
        if (i != 194) {
            return false;
        }
        ((HdmiCecLocalDeviceAudioSystem) hdmiCecLocalDevice).setArcStatus(false);
        finish(true);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        ((HdmiCecLocalDeviceAudioSystem) this.mSource).setArcStatus(true);
        this.mState = 1;
        addTimer(1, 1000);
        this.mService.sendCecCommand(
                HdmiCecMessage.build(getSourceAddress(), 0, 192),
                new HdmiControlService
                        .SendMessageCallback() { // from class:
                                                 // com.android.server.hdmi.ArcInitiationActionFromAvr$$ExternalSyntheticLambda0
                    @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                    public final void onSendCompleted(int i) {
                        ArcInitiationActionFromAvr arcInitiationActionFromAvr =
                                ArcInitiationActionFromAvr.this;
                        if (i == 0) {
                            arcInitiationActionFromAvr.getClass();
                        } else {
                            ((HdmiCecLocalDeviceAudioSystem) arcInitiationActionFromAvr.mSource)
                                    .setArcStatus(false);
                            arcInitiationActionFromAvr.finish(true);
                        }
                    }
                });
    }
}
