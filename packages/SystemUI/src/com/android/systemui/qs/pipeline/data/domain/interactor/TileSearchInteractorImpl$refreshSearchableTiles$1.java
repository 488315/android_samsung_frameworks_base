package com.android.systemui.qs.pipeline.data.domain.interactor;

import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class TileSearchInteractorImpl$refreshSearchableTiles$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    public TileSearchInteractorImpl$refreshSearchableTiles$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        TileSearchInteractorImpl$refreshSearchableTiles$1 tileSearchInteractorImpl$refreshSearchableTiles$1 = new TileSearchInteractorImpl$refreshSearchableTiles$1((Continuation) obj3);
        tileSearchInteractorImpl$refreshSearchableTiles$1.L$0 = (List) obj;
        return tileSearchInteractorImpl$refreshSearchableTiles$1.invokeSuspend(Unit.INSTANCE);
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
