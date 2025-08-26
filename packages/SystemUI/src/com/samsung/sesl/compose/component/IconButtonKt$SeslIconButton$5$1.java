package com.samsung.sesl.compose.component;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.material3.TooltipState;
import androidx.compose.material3.TooltipStateImpl;
import androidx.compose.runtime.State;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
final class IconButtonKt$SeslIconButton$5$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<Boolean> $isHovered$delegate;
    final /* synthetic */ TooltipState $tooltipState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IconButtonKt$SeslIconButton$5$1(TooltipState tooltipState, State<Boolean> state, Continuation continuation) {
        super(2, continuation);
        this.$tooltipState = tooltipState;
        this.$isHovered$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new IconButtonKt$SeslIconButton$5$1(this.$tooltipState, this.$isHovered$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((IconButtonKt$SeslIconButton$5$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (((Boolean) this.$isHovered$delegate.getValue()).booleanValue()) {
                TooltipState tooltipState = this.$tooltipState;
                this.label = 1;
                if (((TooltipStateImpl) tooltipState).show(MutatePriority.Default, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
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
