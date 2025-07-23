package com.android.systemui.brightness.ui.compose;

import androidx.compose.foundation.interaction.DragInteraction$Start;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.State;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BrightnessSliderKt$BrightnessSlider$7$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<Function0> $currentShowToast$delegate;
    final /* synthetic */ MutableInteractionSource $interactionSource;
    final /* synthetic */ boolean $overriddenByAppState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BrightnessSliderKt$BrightnessSlider$7$1(MutableInteractionSource mutableInteractionSource, boolean z, State<? extends Function0> state, Continuation continuation) {
        super(2, continuation);
        this.$interactionSource = mutableInteractionSource;
        this.$overriddenByAppState = z;
        this.$currentShowToast$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BrightnessSliderKt$BrightnessSlider$7$1(this.$interactionSource, this.$overriddenByAppState, this.$currentShowToast$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BrightnessSliderKt$BrightnessSlider$7$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SharedFlowImpl interactions = this.$interactionSource.getInteractions();
            final boolean z = this.$overriddenByAppState;
            final State<Function0> state = this.$currentShowToast$delegate;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSlider$7$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    if ((((Interaction) obj2) instanceof DragInteraction$Start) && z) {
                        ((Function0) state.getValue()).invoke();
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            interactions.getClass();
            if (SharedFlowImpl.collect$suspendImpl(interactions, flowCollector, this) == coroutineSingletons) {
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
