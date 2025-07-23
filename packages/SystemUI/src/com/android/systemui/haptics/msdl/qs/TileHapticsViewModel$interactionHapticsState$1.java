package com.android.systemui.haptics.msdl.qs;

import com.android.systemui.haptics.msdl.qs.TileHapticsViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TileHapticsViewModel$interactionHapticsState$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public TileHapticsViewModel$interactionHapticsState$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        TileHapticsViewModel$interactionHapticsState$1 tileHapticsViewModel$interactionHapticsState$1 = new TileHapticsViewModel$interactionHapticsState$1((Continuation) obj3);
        tileHapticsViewModel$interactionHapticsState$1.L$0 = (TileHapticsViewModel.TileInteractionState) obj;
        tileHapticsViewModel$interactionHapticsState$1.L$1 = (TileHapticsViewModel.TileAnimationState) obj2;
        return tileHapticsViewModel$interactionHapticsState$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return (((TileHapticsViewModel.TileInteractionState) this.L$0) == TileHapticsViewModel.TileInteractionState.LONG_CLICKED && ((TileHapticsViewModel.TileAnimationState) this.L$1) == TileHapticsViewModel.TileAnimationState.ACTIVITY_LAUNCH) ? TileHapticsViewModel.TileHapticsState.LONG_PRESS : TileHapticsViewModel.TileHapticsState.NO_HAPTICS;
    }
}
