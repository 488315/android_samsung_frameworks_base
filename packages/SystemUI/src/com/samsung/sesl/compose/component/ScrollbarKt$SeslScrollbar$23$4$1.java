package com.samsung.sesl.compose.component;

import com.samsung.sesl.compose.foundation.scroll.SeslScrollableState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class ScrollbarKt$SeslScrollbar$23$4$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ ScrollAdapter $scrollAdapter;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScrollbarKt$SeslScrollbar$23$4$1(ScrollAdapter scrollAdapter, Continuation continuation) {
        super(3, continuation);
        this.$scrollAdapter = scrollAdapter;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Number) obj2).floatValue();
        return new ScrollbarKt$SeslScrollbar$23$4$1(this.$scrollAdapter, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ScrollAdapter scrollAdapter = this.$scrollAdapter;
        int i = scrollAdapter.scrollBarSize;
        SeslScrollableState seslScrollableState = scrollAdapter.scrollableState;
        int handleSizeFraction = (int) (seslScrollableState.getHandleSizeFraction() * i);
        int i2 = scrollAdapter.handleMinSize;
        if (handleSizeFraction < i2) {
            handleSizeFraction = i2;
        }
        scrollAdapter.setRawPosition(seslScrollableState.getPositionFraction() * (i - handleSizeFraction));
        return Unit.INSTANCE;
    }
}
