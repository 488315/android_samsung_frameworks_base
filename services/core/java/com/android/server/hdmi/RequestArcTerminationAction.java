package com.android.server.hdmi;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RequestArcTerminationAction extends RequestArcAction {
    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState == 1) {
            int i = this.mAvrAddress;
            if (HdmiUtils.checkCommandSource(hdmiCecMessage, i, "RequestArcTerminationAction")) {
                int i2 = hdmiCecMessage.mOpcode;
                if (i2 != 0) {
                    if (i2 != 197) {
                        return false;
                    }
                    finishWithCallback(0);
                    return false;
                }
                if ((hdmiCecMessage.mParams[0] & 255) == 196) {
                    HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
                    hdmiCecLocalDevice.addAndStartAction(
                            new SetArcTransmissionStateAction(hdmiCecLocalDevice, i, false));
                    finishWithCallback(3);
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        this.mState = 1;
        addTimer(1, 2000);
        this.mService.sendCecCommand(
                HdmiCecMessage.build(getSourceAddress(), this.mAvrAddress, 196),
                new HdmiControlService
                        .SendMessageCallback() { // from class:
                                                 // com.android.server.hdmi.RequestArcTerminationAction.1
                    @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                    public final void onSendCompleted(int i) {
                        if (i != 0) {
                            RequestArcTerminationAction requestArcTerminationAction =
                                    RequestArcTerminationAction.this;
                            int i2 = requestArcTerminationAction.mAvrAddress;
                            HdmiCecLocalDevice hdmiCecLocalDevice =
                                    requestArcTerminationAction.mSource;
                            hdmiCecLocalDevice.addAndStartAction(
                                    new SetArcTransmissionStateAction(
                                            hdmiCecLocalDevice, i2, false));
                            requestArcTerminationAction.finishWithCallback(3);
                        }
                    }
                });
    }
}
