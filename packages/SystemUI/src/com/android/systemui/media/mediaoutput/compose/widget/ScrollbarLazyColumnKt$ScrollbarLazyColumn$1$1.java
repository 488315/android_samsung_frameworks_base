package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.runtime.MutableState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ScrollbarLazyColumnKt$ScrollbarLazyColumn$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<Boolean> $isScrolling$delegate;
    final /* synthetic */ LazyListState $state;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScrollbarLazyColumnKt$ScrollbarLazyColumn$1$1(LazyListState lazyListState, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$state = lazyListState;
        this.$isScrolling$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScrollbarLazyColumnKt$ScrollbarLazyColumn$1$1(this.$state, this.$isScrolling$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScrollbarLazyColumnKt$ScrollbarLazyColumn$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.$state.scrollableState.isScrollInProgress()) {
                this.$isScrolling$delegate.setValue(Boolean.TRUE);
                return Unit.INSTANCE;
            }
            this.label = 1;
            if (DelayKt.delay(1000L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.$isScrolling$delegate.setValue(Boolean.FALSE);
        return Unit.INSTANCE;
    }
}
