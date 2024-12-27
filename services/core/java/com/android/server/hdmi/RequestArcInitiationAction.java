package com.android.server.hdmi;


public final class RequestArcInitiationAction extends RequestArcAction {
    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState == 1
                && HdmiUtils.checkCommandSource(
                        hdmiCecMessage, this.mAvrAddress, "RequestArcInitiationAction")) {
            int i = hdmiCecMessage.mOpcode;
            if (i != 0) {
                if (i != 192) {
                    return false;
                }
                finishWithCallback(0);
                return false;
            }
            if ((hdmiCecMessage.mParams[0] & 255) == 195) {
                ((HdmiCecLocalDeviceTv) this.mSource).disableArc();
                finishWithCallback(3);
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        this.mState = 1;
        addTimer(1, 2000);
        this.mService.sendCecCommand(
                HdmiCecMessage.build(getSourceAddress(), this.mAvrAddress, 195),
                new HdmiControlService
                        .SendMessageCallback() { // from class:
                                                 // com.android.server.hdmi.RequestArcInitiationAction.1
                    @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                    public final void onSendCompleted(int i) {
                        if (i != 0) {
                            RequestArcInitiationAction requestArcInitiationAction =
                                    RequestArcInitiationAction.this;
                            ((HdmiCecLocalDeviceTv) requestArcInitiationAction.mSource)
                                    .disableArc();
                            requestArcInitiationAction.finishWithCallback(3);
                        }
                    }
                });
    }
}
