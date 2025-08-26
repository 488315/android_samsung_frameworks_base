package com.samsung.sesl.compose.component;

import com.samsung.sesl.compose.foundation.scroll.SeslScrollableState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
final class ScrollAdapter$position$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ float $value;
    int label;
    final /* synthetic */ ScrollAdapter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScrollAdapter$position$1(float f, ScrollAdapter scrollAdapter, Continuation continuation) {
        super(2, continuation);
        this.$value = f;
        this.this$0 = scrollAdapter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScrollAdapter$position$1(this.$value, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScrollAdapter$position$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            float f = this.$value;
            ScrollAdapter scrollAdapter = this.this$0;
            int handleSizeFraction = (int) (scrollAdapter.scrollableState.getHandleSizeFraction() * scrollAdapter.scrollBarSize);
            int i2 = scrollAdapter.handleMinSize;
            if (handleSizeFraction < i2) {
                handleSizeFraction = i2;
            }
            float fCoerceIn = RangesKt___RangesKt.coerceIn(f / (r3 - handleSizeFraction), 0.0f, 1.0f);
            SeslScrollableState seslScrollableState = this.this$0.scrollableState;
            this.label = 1;
            if (seslScrollableState.scrollTo(fCoerceIn, this) == coroutineSingletons) {
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
