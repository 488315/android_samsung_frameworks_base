package com.android.systemui.haptics.msdl.qs;

import com.android.systemui.plugins.qs.QSTile;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class TileHapticsViewModel$toggleHapticsState$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public TileHapticsViewModel$toggleHapticsState$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TileHapticsViewModel$toggleHapticsState$1 tileHapticsViewModel$toggleHapticsState$1 = new TileHapticsViewModel$toggleHapticsState$1(continuation);
        tileHapticsViewModel$toggleHapticsState$1.L$0 = obj;
        return tileHapticsViewModel$toggleHapticsState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileHapticsViewModel$toggleHapticsState$1) create((QSTile.State) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Integer(((QSTile.State) this.L$0).state);
    }
}
