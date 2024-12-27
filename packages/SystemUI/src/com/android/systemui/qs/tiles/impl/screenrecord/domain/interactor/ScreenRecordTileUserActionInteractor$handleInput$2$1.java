package com.android.systemui.qs.tiles.impl.screenrecord.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class ScreenRecordTileUserActionInteractor$handleInput$2$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ScreenRecordTileUserActionInteractor this$0;

    public ScreenRecordTileUserActionInteractor$handleInput$2$1(ScreenRecordTileUserActionInteractor screenRecordTileUserActionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenRecordTileUserActionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScreenRecordTileUserActionInteractor$handleInput$2$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenRecordTileUserActionInteractor$handleInput$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.recordingController.cancelCountdown$1();
        return Unit.INSTANCE;
    }
}
