package androidx.compose.foundation;

import androidx.compose.foundation.DefaultDebugIndication;
import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.FocusInteraction$Unfocus;
import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.HoverInteraction$Exit;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.PressInteraction$Cancel;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.foundation.interaction.PressInteraction$Release;
import androidx.compose.ui.node.DrawModifierNodeKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$IntRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes.dex */
final class DefaultDebugIndication$DefaultDebugIndicationInstance$onAttach$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DefaultDebugIndication.DefaultDebugIndicationInstance this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultDebugIndication$DefaultDebugIndicationInstance$onAttach$1(DefaultDebugIndication.DefaultDebugIndicationInstance defaultDebugIndicationInstance, Continuation continuation) {
        super(2, continuation);
        this.this$0 = defaultDebugIndicationInstance;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DefaultDebugIndication$DefaultDebugIndicationInstance$onAttach$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DefaultDebugIndication$DefaultDebugIndicationInstance$onAttach$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref$IntRef ref$IntRef = new Ref$IntRef();
            final Ref$IntRef ref$IntRef2 = new Ref$IntRef();
            final Ref$IntRef ref$IntRef3 = new Ref$IntRef();
            SharedFlowImpl interactions = this.this$0.interactionSource.getInteractions();
            final DefaultDebugIndication.DefaultDebugIndicationInstance defaultDebugIndicationInstance = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: androidx.compose.foundation.DefaultDebugIndication$DefaultDebugIndicationInstance$onAttach$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Interaction interaction = (Interaction) obj2;
                    boolean z = interaction instanceof PressInteraction$Press;
                    Ref$IntRef ref$IntRef4 = ref$IntRef3;
                    Ref$IntRef ref$IntRef5 = ref$IntRef2;
                    Ref$IntRef ref$IntRef6 = ref$IntRef;
                    boolean z2 = true;
                    if (z) {
                        ref$IntRef6.element++;
                    } else if ((interaction instanceof PressInteraction$Release) || (interaction instanceof PressInteraction$Cancel)) {
                        ref$IntRef6.element--;
                    } else if (interaction instanceof HoverInteraction$Enter) {
                        ref$IntRef5.element++;
                    } else if (interaction instanceof HoverInteraction$Exit) {
                        ref$IntRef5.element--;
                    } else if (interaction instanceof FocusInteraction$Focus) {
                        ref$IntRef4.element++;
                    } else if (interaction instanceof FocusInteraction$Unfocus) {
                        ref$IntRef4.element--;
                    }
                    boolean z3 = false;
                    boolean z4 = ref$IntRef6.element > 0;
                    boolean z5 = ref$IntRef5.element > 0;
                    boolean z6 = ref$IntRef4.element > 0;
                    DefaultDebugIndication.DefaultDebugIndicationInstance defaultDebugIndicationInstance2 = defaultDebugIndicationInstance;
                    if (defaultDebugIndicationInstance2.isPressed != z4) {
                        defaultDebugIndicationInstance2.isPressed = z4;
                        z3 = true;
                    }
                    if (defaultDebugIndicationInstance2.isHovered != z5) {
                        defaultDebugIndicationInstance2.isHovered = z5;
                        z3 = true;
                    }
                    if (defaultDebugIndicationInstance2.isFocused != z6) {
                        defaultDebugIndicationInstance2.isFocused = z6;
                    } else {
                        z2 = z3;
                    }
                    if (z2) {
                        DrawModifierNodeKt.invalidateDraw(defaultDebugIndicationInstance2);
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
