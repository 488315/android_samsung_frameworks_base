package com.android.compose.gesture.effect;

import androidx.compose.animation.core.Animatable;
import com.android.compose.ui.util.SpaceVectorConverter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BaseContentOverscrollEffect$applyToScroll$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ float $currentOffset;
    final /* synthetic */ long $overscrollDelta;
    final /* synthetic */ SpaceVectorConverter $this_applyToScroll;
    int label;
    final /* synthetic */ BaseContentOverscrollEffect this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseContentOverscrollEffect$applyToScroll$1(BaseContentOverscrollEffect baseContentOverscrollEffect, float f, SpaceVectorConverter spaceVectorConverter, long j, Continuation continuation) {
        super(2, continuation);
        this.this$0 = baseContentOverscrollEffect;
        this.$currentOffset = f;
        this.$this_applyToScroll = spaceVectorConverter;
        this.$overscrollDelta = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BaseContentOverscrollEffect$applyToScroll$1(this.this$0, this.$currentOffset, this.$this_applyToScroll, this.$overscrollDelta, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BaseContentOverscrollEffect$applyToScroll$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Animatable animatable = this.this$0.animatable;
            Float f = new Float(this.$this_applyToScroll.mo915toFloatk4lQ0M$1(this.$overscrollDelta) + this.$currentOffset);
            this.label = 1;
            if (animatable.snapTo(f, this) == coroutineSingletons) {
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
