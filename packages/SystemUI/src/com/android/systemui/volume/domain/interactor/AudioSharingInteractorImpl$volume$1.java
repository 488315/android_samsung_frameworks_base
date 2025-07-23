package com.android.systemui.volume.domain.interactor;

import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class AudioSharingInteractorImpl$volume$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    int label;

    public AudioSharingInteractorImpl$volume$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        AudioSharingInteractorImpl$volume$1 audioSharingInteractorImpl$volume$1 = new AudioSharingInteractorImpl$volume$1((Continuation) obj3);
        audioSharingInteractorImpl$volume$1.I$0 = intValue;
        audioSharingInteractorImpl$volume$1.L$0 = (Map) obj2;
        return audioSharingInteractorImpl$volume$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        Map map = (Map) this.L$0;
        if (i == -1) {
            return null;
        }
        return (Integer) map.getOrDefault(new Integer(i), new Integer(20));
    }
}
