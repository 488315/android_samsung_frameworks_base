package com.android.server.hdmi;

import com.android.server.hdmi.HdmiControlService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ArcTerminationActionFromAvr extends HdmiCecFeatureAction {
    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 == i && i2 == 1) {
            ((HdmiCecLocalDeviceAudioSystem) this.mSource).setArcStatus(false);
            HdmiLogger.debug("handleTerminateArcTimeout", new Object[0]);
            finishWithCallback(1);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1) {
            return false;
        }
        int i = hdmiCecMessage.mOpcode;
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        if (i != 0) {
            if (i != 194) {
                return false;
            }
            this.mState = 2;
            ((HdmiCecLocalDeviceAudioSystem) hdmiCecLocalDevice).processArcTermination();
            finishWithCallback(0);
            return true;
        }
        if ((hdmiCecMessage.mParams[0] & 255) != 197) {
            return false;
        }
        this.mState = 2;
        ((HdmiCecLocalDeviceAudioSystem) hdmiCecLocalDevice).processArcTermination();
        finishWithCallback(3);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        this.mState = 1;
        addTimer(1, 1000);
        this.mService.sendCecCommand(HdmiCecMessage.build(getSourceAddress(), 0, 197), new HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.ArcTerminationActionFromAvr$$ExternalSyntheticLambda0
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public final void onSendCompleted(int i) {
                ArcTerminationActionFromAvr arcTerminationActionFromAvr = ArcTerminationActionFromAvr.this;
                arcTerminationActionFromAvr.getClass();
                if (i != 0) {
                    if (i == 1) {
                        ((HdmiCecLocalDeviceAudioSystem) arcTerminationActionFromAvr.mSource).setArcStatus(false);
                    }
                    HdmiLogger.debug("Terminate ARC was not successfully sent.", new Object[0]);
                    arcTerminationActionFromAvr.finishWithCallback(3);
                }
            }
        });
    }
}
