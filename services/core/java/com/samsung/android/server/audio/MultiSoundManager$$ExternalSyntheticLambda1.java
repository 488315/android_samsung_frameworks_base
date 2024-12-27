package com.samsung.android.server.audio;

import com.samsung.android.media.AudioParameter;

public final /* synthetic */ class MultiSoundManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MultiSoundManager f$0;
    public final /* synthetic */ AudioParameter f$1;

    public /* synthetic */ MultiSoundManager$$ExternalSyntheticLambda1(
            MultiSoundManager multiSoundManager, AudioParameter audioParameter, int i) {
        this.$r8$classId = i;
        this.f$0 = multiSoundManager;
        this.f$1 = audioParameter;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mAudioSystem.setParameters(this.f$1.toString());
                break;
            default:
                this.f$0.mAudioSystem.setParameters(this.f$1.toString());
                break;
        }
    }
}
