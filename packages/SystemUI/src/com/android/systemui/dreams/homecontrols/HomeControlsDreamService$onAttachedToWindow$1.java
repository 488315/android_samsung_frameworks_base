package com.android.systemui.dreams.homecontrols;

import com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class HomeControlsDreamService$onAttachedToWindow$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ HomeControlsDreamService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeControlsDreamService$onAttachedToWindow$1(HomeControlsDreamService homeControlsDreamService, Continuation continuation) {
        super(2, continuation);
        this.this$0 = homeControlsDreamService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HomeControlsDreamService$onAttachedToWindow$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HomeControlsDreamService$onAttachedToWindow$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            HomeControlsComponentInteractor homeControlsComponentInteractor = this.this$0.homeControlsComponentInteractor;
            this.label = 1;
            if (homeControlsComponentInteractor.monitorUpdatesAndRestart(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
