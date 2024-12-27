package com.android.server.hdmi;

import com.android.server.hdmi.HdmiControlService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DetectTvSystemAudioModeSupportAction$$ExternalSyntheticLambda0 implements HdmiControlService.SendMessageCallback {
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
