package com.android.server.hdmi;

import android.hardware.hdmi.IHdmiControlCallback;
import android.util.Slog;

public final class SystemAudioActionFromTv extends SystemAudioAction {
    public SystemAudioActionFromTv(
            HdmiCecLocalDevice hdmiCecLocalDevice,
            int i,
            IHdmiControlCallback iHdmiControlCallback,
            boolean z) {
        super(hdmiCecLocalDevice, i, iHdmiControlCallback, z);
        if (HdmiUtils.verifyAddressType(getSourceAddress(), 0)) {
            return;
        }
        Slog.w("SystemAudioActionFromTv", "Device type mismatch, stop the action.");
        finish(true);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        hdmiCecLocalDevice.removeActionExcept(SystemAudioActionFromTv.class, this);
        hdmiCecLocalDevice.removeActionExcept(SystemAudioActionFromAvr.class, this);
        sendSystemAudioModeRequest();
    }
}
