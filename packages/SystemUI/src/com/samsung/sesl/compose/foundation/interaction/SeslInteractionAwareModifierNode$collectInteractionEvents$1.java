package com.samsung.sesl.compose.foundation.interaction;

import androidx.compose.foundation.interaction.DragInteraction$Cancel;
import androidx.compose.foundation.interaction.DragInteraction$Start;
import androidx.compose.foundation.interaction.DragInteraction$Stop;
import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.FocusInteraction$Unfocus;
import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.HoverInteraction$Exit;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Cancel;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.foundation.interaction.PressInteraction$Release;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes4.dex */
final class SeslInteractionAwareModifierNode$collectInteractionEvents$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ InteractionSource $interactionSource;
    int label;
    final /* synthetic */ SeslInteractionAwareModifierNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeslInteractionAwareModifierNode$collectInteractionEvents$1(InteractionSource interactionSource, SeslInteractionAwareModifierNode seslInteractionAwareModifierNode, Continuation continuation) {
        super(2, continuation);
        this.$interactionSource = interactionSource;
        this.this$0 = seslInteractionAwareModifierNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SeslInteractionAwareModifierNode$collectInteractionEvents$1(this.$interactionSource, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SeslInteractionAwareModifierNode$collectInteractionEvents$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SharedFlowImpl interactions = this.$interactionSource.getInteractions();
            final SeslInteractionAwareModifierNode seslInteractionAwareModifierNode = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.samsung.sesl.compose.foundation.interaction.SeslInteractionAwareModifierNode$collectInteractionEvents$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Interaction interaction = (Interaction) obj2;
                    boolean z = interaction instanceof SeslTouchInteraction$Press;
                    SeslInteractionAwareModifierNode seslInteractionAwareModifierNode2 = seslInteractionAwareModifierNode;
                    if (z) {
                        ((ArrayList) seslInteractionAwareModifierNode2.touchInteraction).add(interaction);
                    } else if (interaction instanceof PressInteraction$Press) {
                        ((ArrayList) seslInteractionAwareModifierNode2.pressInteraction).add(interaction);
                    } else if (interaction instanceof DragInteraction$Start) {
                        ((ArrayList) seslInteractionAwareModifierNode2.dragInteraction).add(interaction);
                    } else if (interaction instanceof HoverInteraction$Enter) {
                        ((ArrayList) seslInteractionAwareModifierNode2.hoverInteraction).add(interaction);
                    } else if (interaction instanceof FocusInteraction$Focus) {
                        ((ArrayList) seslInteractionAwareModifierNode2.focusInteraction).add(interaction);
                    } else if (interaction instanceof SeslTouchInteraction$Move) {
                        ((ArrayList) seslInteractionAwareModifierNode2.touchInteraction).remove(((SeslTouchInteraction$Move) interaction).press);
                    } else if (interaction instanceof SeslTouchInteraction$Release) {
                        ((ArrayList) seslInteractionAwareModifierNode2.touchInteraction).remove(((SeslTouchInteraction$Release) interaction).press);
                    } else if (interaction instanceof PressInteraction$Cancel) {
                        ((ArrayList) seslInteractionAwareModifierNode2.pressInteraction).remove(((PressInteraction$Cancel) interaction).press);
                    } else if (interaction instanceof PressInteraction$Release) {
                        ((ArrayList) seslInteractionAwareModifierNode2.pressInteraction).remove(((PressInteraction$Release) interaction).press);
                    } else if (interaction instanceof DragInteraction$Cancel) {
                        ((ArrayList) seslInteractionAwareModifierNode2.dragInteraction).remove(((DragInteraction$Cancel) interaction).start);
                    } else if (interaction instanceof DragInteraction$Stop) {
                        ((ArrayList) seslInteractionAwareModifierNode2.dragInteraction).remove(((DragInteraction$Stop) interaction).start);
                    } else if (interaction instanceof HoverInteraction$Exit) {
                        ((ArrayList) seslInteractionAwareModifierNode2.hoverInteraction).remove(((HoverInteraction$Exit) interaction).enter);
                    } else if (interaction instanceof FocusInteraction$Unfocus) {
                        ((ArrayList) seslInteractionAwareModifierNode2.focusInteraction).remove(((FocusInteraction$Unfocus) interaction).focus);
                    }
                    SeslInteractionState seslInteractionState = new SeslInteractionState((!((ArrayList) seslInteractionAwareModifierNode2.pressInteraction).isEmpty()) | (!((ArrayList) seslInteractionAwareModifierNode2.touchInteraction).isEmpty()), !((ArrayList) seslInteractionAwareModifierNode2.focusInteraction).isEmpty(), !((ArrayList) seslInteractionAwareModifierNode2.hoverInteraction).isEmpty(), !((ArrayList) seslInteractionAwareModifierNode2.dragInteraction).isEmpty());
                    if (!Intrinsics.areEqual(seslInteractionAwareModifierNode2.previous, seslInteractionState)) {
                        seslInteractionAwareModifierNode2.previous = seslInteractionState;
                        seslInteractionAwareModifierNode2.onState.mo781invoke(seslInteractionState);
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
