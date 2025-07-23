package androidx.compose.material3;

import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.FocusInteraction$Unfocus;
import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.HoverInteraction$Exit;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Cancel;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.foundation.interaction.PressInteraction$Release;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ButtonElevation$animateElevation$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ InteractionSource $interactionSource;
    final /* synthetic */ SnapshotStateList<Interaction> $interactions;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ButtonElevation$animateElevation$1$1(InteractionSource interactionSource, SnapshotStateList<Interaction> snapshotStateList, Continuation continuation) {
        super(2, continuation);
        this.$interactionSource = interactionSource;
        this.$interactions = snapshotStateList;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ButtonElevation$animateElevation$1$1(this.$interactionSource, this.$interactions, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ButtonElevation$animateElevation$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SharedFlowImpl interactions = this.$interactionSource.getInteractions();
            final SnapshotStateList<Interaction> snapshotStateList = this.$interactions;
            FlowCollector flowCollector = new FlowCollector() { // from class: androidx.compose.material3.ButtonElevation$animateElevation$1$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Interaction interaction = (Interaction) obj2;
                    boolean z = interaction instanceof HoverInteraction$Enter;
                    SnapshotStateList snapshotStateList2 = snapshotStateList;
                    if (z) {
                        snapshotStateList2.add(interaction);
                    } else if (interaction instanceof HoverInteraction$Exit) {
                        snapshotStateList2.remove(((HoverInteraction$Exit) interaction).enter);
                    } else if (interaction instanceof FocusInteraction$Focus) {
                        snapshotStateList2.add(interaction);
                    } else if (interaction instanceof FocusInteraction$Unfocus) {
                        snapshotStateList2.remove(((FocusInteraction$Unfocus) interaction).focus);
                    } else if (interaction instanceof PressInteraction$Press) {
                        snapshotStateList2.add(interaction);
                    } else if (interaction instanceof PressInteraction$Release) {
                        snapshotStateList2.remove(((PressInteraction$Release) interaction).press);
                    } else if (interaction instanceof PressInteraction$Cancel) {
                        snapshotStateList2.remove(((PressInteraction$Cancel) interaction).press);
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
