package com.android.keyguard;

import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.keyguard.ClockEventController$listenForDozeAmountTransition$1", m277f = "ClockEventController.kt", m278l = {373}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class ClockEventController$listenForDozeAmountTransition$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ClockEventController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockEventController$listenForDozeAmountTransition$1(ClockEventController clockEventController, Continuation<? super ClockEventController$listenForDozeAmountTransition$1> continuation) {
        super(2, continuation);
        this.this$0 = clockEventController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ClockEventController$listenForDozeAmountTransition$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ClockEventController$listenForDozeAmountTransition$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ClockEventController clockEventController = this.this$0;
            ChannelLimitedFlowMerge channelLimitedFlowMerge = clockEventController.keyguardTransitionInteractor.dozeAmountTransition;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.keyguard.ClockEventController$listenForDozeAmountTransition$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ClockEventController.access$handleDoze(ClockEventController.this, ((TransitionStep) obj2).value);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (channelLimitedFlowMerge.collect(flowCollector, this) == coroutineSingletons) {
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
