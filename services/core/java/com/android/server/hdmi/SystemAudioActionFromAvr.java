package com.android.server.hdmi;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemAudioActionFromAvr extends SystemAudioAction {
    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        hdmiCecLocalDevice.removeActionExcept(SystemAudioActionFromTv.class, this);
        hdmiCecLocalDevice.removeActionExcept(SystemAudioActionFromAvr.class, this);
        HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv = (HdmiCecLocalDeviceTv) hdmiCecLocalDevice;
        if (this.mTargetAudioStatus == hdmiCecLocalDeviceTv.isSystemAudioActivated()) {
            finishWithCallback(0);
            return;
        }
        if (hdmiCecLocalDeviceTv.mService.isProhibitMode()) {
            this.mService.sendCecCommand(HdmiCecMessage.build(getSourceAddress(), this.mAvrLogicalAddress, 0, new byte[]{(byte) 114, (byte) 4}), null);
            this.mTargetAudioStatus = false;
            sendSystemAudioModeRequest();
            return;
        }
        hdmiCecLocalDevice.removeActionExcept(SystemAudioAutoInitiationAction.class, null);
        if (this.mTargetAudioStatus) {
            setSystemAudioMode(true);
            finish(true);
        } else {
            setSystemAudioMode(false);
            finishWithCallback(0);
        }
    }
}
