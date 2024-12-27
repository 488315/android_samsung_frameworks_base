package com.android.server.soundtrigger;


public final /* synthetic */
class SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SoundTriggerService.SoundTriggerSessionStub
                    .RemoteSoundTriggerDetectionService
            f$0;

    public /* synthetic */
    SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda0(
            SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService
                    remoteSoundTriggerDetectionService,
            int i) {
        this.$r8$classId = i;
        this.f$0 = remoteSoundTriggerDetectionService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService
                remoteSoundTriggerDetectionService = this.f$0;
        switch (i) {
            case 0:
                if (remoteSoundTriggerDetectionService.mRecognitionConfig.allowMultipleTriggers) {
                    return;
                }
                synchronized (remoteSoundTriggerDetectionService.this$1.mCallbacksLock) {
                    remoteSoundTriggerDetectionService.this$1.mCallbacks.remove(
                            remoteSoundTriggerDetectionService.mPuuid.getUuid());
                }
                remoteSoundTriggerDetectionService.mDestroyOnceRunningOpsDone = true;
                return;
            default:
                synchronized (remoteSoundTriggerDetectionService.this$1.mCallbacksLock) {
                    remoteSoundTriggerDetectionService.this$1.mCallbacks.remove(
                            remoteSoundTriggerDetectionService.mPuuid.getUuid());
                }
                remoteSoundTriggerDetectionService.mDestroyOnceRunningOpsDone = true;
                return;
        }
    }
}
