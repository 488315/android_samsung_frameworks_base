package com.samsung.sesl.compose.ui.interaction;

import androidx.compose.foundation.interaction.DragInteraction$Cancel;
import androidx.compose.foundation.interaction.DragInteraction$Start;
import androidx.compose.foundation.interaction.DragInteraction$Stop;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Cancel;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.foundation.interaction.PressInteraction$Release;
import androidx.compose.runtime.MutableState;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes4.dex */
final class HighlightedInteractionKt$collectIsHighlightedAsState$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<Boolean> $isHighlighted;
    final /* synthetic */ InteractionSource $this_collectIsHighlightedAsState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HighlightedInteractionKt$collectIsHighlightedAsState$1$1(InteractionSource interactionSource, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$this_collectIsHighlightedAsState = interactionSource;
        this.$isHighlighted = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HighlightedInteractionKt$collectIsHighlightedAsState$1$1(this.$this_collectIsHighlightedAsState, this.$isHighlighted, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HighlightedInteractionKt$collectIsHighlightedAsState$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ArrayList arrayList = new ArrayList();
            final ArrayList arrayList2 = new ArrayList();
            SharedFlowImpl interactions = this.$this_collectIsHighlightedAsState.getInteractions();
            final MutableState<Boolean> mutableState = this.$isHighlighted;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.samsung.sesl.compose.ui.interaction.HighlightedInteractionKt$collectIsHighlightedAsState$1$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Interaction interaction = (Interaction) obj2;
                    if (interaction instanceof DragInteraction$Start) {
                        arrayList.add(interaction);
                    } else if (interaction instanceof DragInteraction$Stop) {
                        arrayList.remove(((DragInteraction$Stop) interaction).start);
                    } else if (interaction instanceof DragInteraction$Cancel) {
                        arrayList.remove(((DragInteraction$Cancel) interaction).start);
                    } else if (interaction instanceof PressInteraction$Press) {
                        arrayList2.add(interaction);
                    } else if (interaction instanceof PressInteraction$Release) {
                        arrayList2.remove(((PressInteraction$Release) interaction).press);
                    } else if (interaction instanceof PressInteraction$Cancel) {
                        arrayList2.remove(((PressInteraction$Cancel) interaction).press);
                    }
                    mutableState.setValue(Boolean.valueOf((!arrayList.isEmpty()) & (!arrayList2.isEmpty())));
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
