package com.android.server.hdmi;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0
        implements HdmiCecLocalDeviceAudioSystem.TvSystemAudioModeSupportedCallback,
                HdmiControlService.SendMessageCallback {
    public final /* synthetic */ SystemAudioInitiationActionFromAvr f$0;

    public /* synthetic */ SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0(
            SystemAudioInitiationActionFromAvr systemAudioInitiationActionFromAvr) {
        this.f$0 = systemAudioInitiationActionFromAvr;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.TvSystemAudioModeSupportedCallback
    public void onResult(boolean z) {
        SystemAudioInitiationActionFromAvr systemAudioInitiationActionFromAvr = this.f$0;
        HdmiCecLocalDevice hdmiCecLocalDevice = systemAudioInitiationActionFromAvr.mSource;
        if (!z) {
            ((HdmiCecLocalDeviceAudioSystem) hdmiCecLocalDevice)
                    .checkSupportAndSetSystemAudioMode(false);
            systemAudioInitiationActionFromAvr.finish(true);
        } else {
            if (((HdmiCecLocalDeviceAudioSystem) hdmiCecLocalDevice)
                    .checkSupportAndSetSystemAudioMode(true)) {
                systemAudioInitiationActionFromAvr.sendCommand(
                        HdmiCecMessageBuilder.buildCommandWithBooleanParam(
                                systemAudioInitiationActionFromAvr.getSourceAddress(),
                                15,
                                114,
                                true),
                        new SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda2(
                                systemAudioInitiationActionFromAvr, true, 15));
            }
            systemAudioInitiationActionFromAvr.finish(true);
        }
    }

    @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
    public void onSendCompleted(int i) {
        SystemAudioInitiationActionFromAvr systemAudioInitiationActionFromAvr = this.f$0;
        if (i == 0) {
            systemAudioInitiationActionFromAvr.getClass();
            return;
        }
        int i2 = systemAudioInitiationActionFromAvr.mSendRequestActiveSourceRetryCount;
        if (i2 >= 5) {
            ((HdmiCecLocalDeviceAudioSystem) systemAudioInitiationActionFromAvr.mSource)
                    .checkSupportAndSetSystemAudioMode(false);
            systemAudioInitiationActionFromAvr.finish(true);
        } else {
            systemAudioInitiationActionFromAvr.mSendRequestActiveSourceRetryCount = i2 + 1;
            systemAudioInitiationActionFromAvr.mService.sendCecCommand(
                    HdmiCecMessage.build(
                            systemAudioInitiationActionFromAvr.getSourceAddress(), 15, 133),
                    new SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0(
                            systemAudioInitiationActionFromAvr));
        }
    }
}
