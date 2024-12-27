package com.android.server.hdmi;

import android.hardware.hdmi.IHdmiControlCallback;
import android.util.Slog;

public abstract class RequestArcAction extends HdmiCecFeatureAction {
    public final int mAvrAddress;

    public RequestArcAction(
            HdmiCecLocalDevice hdmiCecLocalDevice,
            int i,
            HdmiControlService.AnonymousClass35 anonymousClass35) {
        super(hdmiCecLocalDevice, (IHdmiControlCallback) anonymousClass35);
        if (!HdmiUtils.verifyAddressType(getSourceAddress(), 0)
                || !HdmiUtils.verifyAddressType(i, 5)) {
            Slog.w("RequestArcAction", "Device type mismatch, stop the action.");
            finish(true);
        }
        this.mAvrAddress = i;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        if (this.mState == i && i == 1) {
            HdmiLogger.debug("[T] RequestArcAction.", new Object[0]);
            HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
            hdmiCecLocalDevice.addAndStartAction(
                    new SetArcTransmissionStateAction(hdmiCecLocalDevice, this.mAvrAddress, false));
            finishWithCallback(1);
        }
    }
}
