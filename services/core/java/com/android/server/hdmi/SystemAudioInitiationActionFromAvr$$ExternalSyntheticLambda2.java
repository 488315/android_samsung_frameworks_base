package com.android.server.hdmi;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda2
        implements HdmiControlService.SendMessageCallback {
    public final /* synthetic */ SystemAudioInitiationActionFromAvr f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda2(
            SystemAudioInitiationActionFromAvr systemAudioInitiationActionFromAvr,
            boolean z,
            int i) {
        this.f$0 = systemAudioInitiationActionFromAvr;
        this.f$1 = z;
        this.f$2 = i;
    }

    @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
    public final void onSendCompleted(int i) {
        SystemAudioInitiationActionFromAvr systemAudioInitiationActionFromAvr = this.f$0;
        if (i == 0) {
            systemAudioInitiationActionFromAvr.getClass();
            return;
        }
        int i2 = systemAudioInitiationActionFromAvr.mSendSetSystemAudioModeRetryCount;
        if (i2 >= 5) {
            ((HdmiCecLocalDeviceAudioSystem) systemAudioInitiationActionFromAvr.mSource)
                    .checkSupportAndSetSystemAudioMode(false);
            systemAudioInitiationActionFromAvr.finish(true);
            return;
        }
        systemAudioInitiationActionFromAvr.mSendSetSystemAudioModeRetryCount = i2 + 1;
        int sourceAddress = systemAudioInitiationActionFromAvr.getSourceAddress();
        int i3 = this.f$2;
        boolean z = this.f$1;
        systemAudioInitiationActionFromAvr.sendCommand(
                HdmiCecMessageBuilder.buildCommandWithBooleanParam(sourceAddress, i3, 114, z),
                new SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda2(
                        systemAudioInitiationActionFromAvr, z, i3));
    }
}
