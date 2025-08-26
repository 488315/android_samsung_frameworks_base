package com.samsung.sesl.compose.component;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes4.dex */
final class ScrollbarKt$SeslScrollbar$24$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable<Float, AnimationVector1D> $offsetAnimated;
    final /* synthetic */ ScrollAdapter $scrollAdapter;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScrollbarKt$SeslScrollbar$24$1(ScrollAdapter scrollAdapter, Animatable<Float, AnimationVector1D> animatable, Continuation continuation) {
        super(2, continuation);
        this.$scrollAdapter = scrollAdapter;
        this.$offsetAnimated = animatable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScrollbarKt$SeslScrollbar$24$1(this.$scrollAdapter, this.$offsetAnimated, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScrollbarKt$SeslScrollbar$24$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SafeFlow safeFlowSnapshotFlow = SnapshotStateKt.snapshotFlow(new ScrollbarKt$$ExternalSyntheticLambda2(this.$scrollAdapter, 1));
            final Animatable<Float, AnimationVector1D> animatable = this.$offsetAnimated;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.samsung.sesl.compose.component.ScrollbarKt$SeslScrollbar$24$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Object objSnapTo = animatable.snapTo(new Float(((Number) obj2).floatValue()), continuation);
                    return objSnapTo == CoroutineSingletons.COROUTINE_SUSPENDED ? objSnapTo : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (safeFlowSnapshotFlow.collect(flowCollector, this) == coroutineSingletons) {
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
