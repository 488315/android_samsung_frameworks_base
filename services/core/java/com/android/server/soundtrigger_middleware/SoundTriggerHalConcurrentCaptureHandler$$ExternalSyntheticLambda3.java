package com.android.server.soundtrigger_middleware;

import android.os.IBinder;

public final /* synthetic */ class SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda3(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((ISoundTriggerHal.GlobalCallback) obj).onResourcesAvailable();
                break;
            default:
                ((IBinder.DeathRecipient) obj).binderDied();
                break;
        }
    }
}
