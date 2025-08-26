package com.android.systemui.media.mediaoutput.viewmodel;

import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class MediaSessionViewModel$currentSessionController$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    public MediaSessionViewModel$currentSessionController$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Number) obj).longValue();
        MediaSessionViewModel$currentSessionController$1 mediaSessionViewModel$currentSessionController$1 = new MediaSessionViewModel$currentSessionController$1((Continuation) obj3);
        mediaSessionViewModel$currentSessionController$1.L$0 = (List) obj2;
        return mediaSessionViewModel$currentSessionController$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return (List) this.L$0;
    }
}
