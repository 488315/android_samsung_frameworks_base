package com.android.systemui.dreams.homecontrols;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.JobKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class HomeControlsDreamService$onDetachedFromWindow$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ HomeControlsDreamService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeControlsDreamService$onDetachedFromWindow$1(HomeControlsDreamService homeControlsDreamService, Continuation continuation) {
        super(2, continuation);
        this.this$0 = homeControlsDreamService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HomeControlsDreamService$onDetachedFromWindow$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HomeControlsDreamService$onDetachedFromWindow$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            HomeControlsDreamService.Companion.getClass();
            long j = HomeControlsDreamService.CANCELLATION_DELAY_AFTER_DETACHED;
            this.label = 1;
            if (DelayKt.m2546delayVtjQ1oo(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        JobKt.cancel$default("Dream detached from window", this.this$0.serviceJob);
        return Unit.INSTANCE;
    }
}
