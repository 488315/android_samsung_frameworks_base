package com.android.server.voiceinteraction;

import java.util.function.Consumer;

public final /* synthetic */ class HotwordDetectionConnection$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ HotwordDetectionConnection$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                HotwordDetectionConnection hotwordDetectionConnection =
                        (HotwordDetectionConnection) obj2;
                DetectorSession detectorSession = (DetectorSession) obj;
                hotwordDetectionConnection.getClass();
                detectorSession.mRemoteDetectionService =
                        detectorSession instanceof VisualQueryDetectorSession
                                ? hotwordDetectionConnection.mRemoteVisualQueryDetectionService
                                : hotwordDetectionConnection.mRemoteHotwordDetectionService;
                detectorSession.informRestartProcessLocked();
                break;
            case 1:
                HotwordDetectionConnection.ServiceConnection.$r8$lambda$soeRIWrLUaITqTFZZ9ww9BHBGAM(
                        (HotwordDetectionConnection.ServiceConnection) obj2, (DetectorSession) obj);
                break;
            default:
                HotwordDetectionConnection.ServiceConnection.$r8$lambda$E52jDMeAIM1fqsgeezf_xP2TouE(
                        (HotwordDetectionConnection.ServiceConnection) obj2, (DetectorSession) obj);
                break;
        }
    }
}
