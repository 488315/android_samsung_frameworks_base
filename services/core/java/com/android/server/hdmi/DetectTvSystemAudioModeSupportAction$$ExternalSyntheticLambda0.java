package com.android.server.hdmi;


public final /* synthetic */ class DetectTvSystemAudioModeSupportAction$$ExternalSyntheticLambda0
        implements HdmiControlService.SendMessageCallback {
    public final /* synthetic */ DetectTvSystemAudioModeSupportAction f$0;

    @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
    public final void onSendCompleted(int i) {
        DetectTvSystemAudioModeSupportAction detectTvSystemAudioModeSupportAction = this.f$0;
        if (i != 0) {
            detectTvSystemAudioModeSupportAction.finishAction(false);
        } else {
            detectTvSystemAudioModeSupportAction.getClass();
        }
    }
}
