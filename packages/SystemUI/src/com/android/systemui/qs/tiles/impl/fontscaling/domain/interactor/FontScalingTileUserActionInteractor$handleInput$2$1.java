package com.android.systemui.qs.tiles.impl.fontscaling.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class FontScalingTileUserActionInteractor$handleInput$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Runnable $runnable;
    int label;
    final /* synthetic */ FontScalingTileUserActionInteractor this$0;

    public FontScalingTileUserActionInteractor$handleInput$2$1(FontScalingTileUserActionInteractor fontScalingTileUserActionInteractor, Runnable runnable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fontScalingTileUserActionInteractor;
        this.$runnable = runnable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FontScalingTileUserActionInteractor$handleInput$2$1(this.this$0, this.$runnable, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FontScalingTileUserActionInteractor$handleInput$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.activityStarter.executeRunnableDismissingKeyguard(this.$runnable, null, true, true, false);
        return Unit.INSTANCE;
    }
}
