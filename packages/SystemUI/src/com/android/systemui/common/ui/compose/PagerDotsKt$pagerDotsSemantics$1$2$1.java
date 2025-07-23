package com.android.systemui.common.ui.compose;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.pager.PagerState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PagerDotsKt$pagerDotsSemantics$1$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ PagerState $pagerState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PagerDotsKt$pagerDotsSemantics$1$2$1(PagerState pagerState, Continuation continuation) {
        super(2, continuation);
        this.$pagerState = pagerState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PagerDotsKt$pagerDotsSemantics$1$2$1(this.$pagerState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PagerDotsKt$pagerDotsSemantics$1$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object animateScrollToPage;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PagerState pagerState = this.$pagerState;
            int currentPage = pagerState.getCurrentPage() + 1;
            this.label = 1;
            animateScrollToPage = pagerState.animateScrollToPage(currentPage, AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7), this);
            if (animateScrollToPage == coroutineSingletons) {
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
