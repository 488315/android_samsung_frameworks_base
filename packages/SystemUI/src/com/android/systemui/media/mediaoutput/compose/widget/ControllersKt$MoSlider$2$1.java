package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.foundation.interaction.DragInteraction$Cancel;
import androidx.compose.foundation.interaction.DragInteraction$Start;
import androidx.compose.foundation.interaction.DragInteraction$Stop;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.interaction.MutableInteractionSourceImpl;
import androidx.compose.runtime.MutableState;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class ControllersKt$MoSlider$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableInteractionSource $interactionSource;
    final /* synthetic */ MutableState $isDragging;
    final /* synthetic */ Function0 $onValueChangeFinished;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ControllersKt$MoSlider$2$1(MutableInteractionSource mutableInteractionSource, MutableState mutableState, Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.$interactionSource = mutableInteractionSource;
        this.$isDragging = mutableState;
        this.$onValueChangeFinished = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ControllersKt$MoSlider$2$1(this.$interactionSource, this.$isDragging, this.$onValueChangeFinished, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ControllersKt$MoSlider$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SharedFlowImpl sharedFlowImpl = ((MutableInteractionSourceImpl) this.$interactionSource).interactions;
            final MutableState mutableState = this.$isDragging;
            final Function0 function0 = this.$onValueChangeFinished;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt$MoSlider$2$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Interaction interaction = (Interaction) obj2;
                    boolean z = interaction instanceof DragInteraction$Start;
                    MutableState mutableState2 = MutableState.this;
                    if (z) {
                        mutableState2.setValue(Boolean.TRUE);
                    } else {
                        if (interaction instanceof DragInteraction$Stop ? true : interaction instanceof DragInteraction$Cancel) {
                            mutableState2.setValue(Boolean.FALSE);
                            Function0 function02 = function0;
                            if (function02 != null) {
                                function02.invoke();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            sharedFlowImpl.getClass();
            if (SharedFlowImpl.collect$suspendImpl(sharedFlowImpl, flowCollector, this) == coroutineSingletons) {
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
