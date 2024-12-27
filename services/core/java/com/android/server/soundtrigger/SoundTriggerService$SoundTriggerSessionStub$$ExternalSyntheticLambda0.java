package com.android.server.soundtrigger;

import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
