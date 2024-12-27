package com.android.server.soundtrigger;

import android.os.IBinder;

public final /* synthetic */
class SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda0
        implements IBinder.DeathRecipient {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((SoundTriggerService.SoundTriggerSessionStub) obj).clientDied();
                break;
            default:
                ((SoundTriggerService.LocalSoundTriggerService.SessionImpl) obj).clientDied();
                break;
        }
    }
}
