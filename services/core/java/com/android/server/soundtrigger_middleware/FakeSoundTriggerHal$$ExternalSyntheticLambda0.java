package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger_middleware.IAcknowledgeEvent;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class FakeSoundTriggerHal$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingConsumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FakeSoundTriggerHal$$ExternalSyntheticLambda0(
            IAcknowledgeEvent iAcknowledgeEvent) {
        this.f$0 = iAcknowledgeEvent;
    }

    public /* synthetic */ FakeSoundTriggerHal$$ExternalSyntheticLambda0(
            FakeSoundTriggerHal.AnonymousClass1 anonymousClass1) {
        this.f$0 = anonymousClass1;
    }

    public /* synthetic */ FakeSoundTriggerHal$$ExternalSyntheticLambda0(
            FakeSoundTriggerHal fakeSoundTriggerHal) {
        this.f$0 = fakeSoundTriggerHal;
    }

    public final void acceptOrThrow(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((ISoundTriggerInjection) obj)
                        .registerGlobalEventInjection(
                                ((FakeSoundTriggerHal) obj2).mGlobalEventSession);
                break;
            case 1:
                ((IAcknowledgeEvent) obj2).eventReceived();
                break;
            default:
                FakeSoundTriggerHal.AnonymousClass1 anonymousClass1 =
                        (FakeSoundTriggerHal.AnonymousClass1) obj2;
                anonymousClass1.getClass();
                ((ISoundTriggerInjection) obj).onRestarted(anonymousClass1);
                break;
        }
    }
}
