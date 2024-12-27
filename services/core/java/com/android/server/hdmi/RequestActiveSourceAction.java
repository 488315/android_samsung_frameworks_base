package com.android.server.hdmi;

import android.util.Slog;

public final class RequestActiveSourceAction extends HdmiCecFeatureAction {
    public int mSendRetryCount;

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 != i) {
            return;
        }
        HdmiControlService hdmiControlService = this.mService;
        if (i2 == 1) {
            this.mState = 2;
            hdmiControlService.sendCecCommand(
                    HdmiCecMessage.build(getSourceAddress(), 15, 133), null);
            addTimer(this.mState, 2000);
        } else {
            if (i2 != 2) {
                return;
            }
            int i3 = this.mSendRetryCount;
            this.mSendRetryCount = i3 + 1;
            if (i3 >= 1) {
                finishWithCallback(1);
            } else {
                hdmiControlService.sendCecCommand(
                        HdmiCecMessage.build(getSourceAddress(), 15, 133), null);
                addTimer(this.mState, 2000);
            }
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (hdmiCecMessage.mOpcode == 130) {
            finishWithCallback(0);
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        Slog.v("RequestActiveSourceAction", "RequestActiveSourceAction started.");
        this.mState = 1;
        addTimer(1, 4000);
    }
}
