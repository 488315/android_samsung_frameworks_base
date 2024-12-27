package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LockscreenToAodTransitionViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ ShadeDependentFlows $shadeDependentFlows$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ LockscreenToAodTransitionViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockscreenToAodTransitionViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, ShadeDependentFlows shadeDependentFlows, LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel) {
        super(3, continuation);
        this.$shadeDependentFlows$inlined = shadeDependentFlows;
        this.this$0 = lockscreenToAodTransitionViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        LockscreenToAodTransitionViewModel$special$$inlined$flatMapLatest$1 lockscreenToAodTransitionViewModel$special$$inlined$flatMapLatest$1 = new LockscreenToAodTransitionViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$shadeDependentFlows$inlined, this.this$0);
        lockscreenToAodTransitionViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        lockscreenToAodTransitionViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return lockscreenToAodTransitionViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ChannelLimitedFlowMerge transitionFlow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                ShadeDependentFlows shadeDependentFlows = this.$shadeDependentFlows$inlined;
                KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder = this.this$0.transitionAnimation;
                Duration.Companion companion = Duration.Companion;
                transitionFlow = shadeDependentFlows.transitionFlow(KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(flowBuilder, DurationKt.toDuration(300, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToAodTransitionViewModel$deviceEntryParentViewAlpha$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        return Float.valueOf(((Number) obj2).floatValue());
                    }
                }, 0L, null, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToAodTransitionViewModel$deviceEntryParentViewAlpha$1$2
                    @Override // kotlin.jvm.functions.Function0
                    public final /* bridge */ /* synthetic */ Object invoke() {
                        return Float.valueOf(1.0f);
                    }
                }, null, null, 220), this.this$0.transitionAnimation.immediatelyTransitionTo(1.0f));
            } else {
                ShadeDependentFlows shadeDependentFlows2 = this.$shadeDependentFlows$inlined;
                KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 immediatelyTransitionTo = this.this$0.transitionAnimation.immediatelyTransitionTo(0.0f);
                KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder2 = this.this$0.transitionAnimation;
                Duration.Companion companion2 = Duration.Companion;
                transitionFlow = shadeDependentFlows2.transitionFlow(immediatelyTransitionTo, KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(flowBuilder2, DurationKt.toDuration(200, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToAodTransitionViewModel$deviceEntryParentViewAlpha$1$3
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        return Float.valueOf(1.0f - ((Number) obj2).floatValue());
                    }
                }, 0L, null, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToAodTransitionViewModel$deviceEntryParentViewAlpha$1$4
                    @Override // kotlin.jvm.functions.Function0
                    public final /* bridge */ /* synthetic */ Object invoke() {
                        return Float.valueOf(0.0f);
                    }
                }, null, null, 220));
            }
            this.label = 1;
            if (FlowKt.emitAll(this, transitionFlow, flowCollector) == coroutineSingletons) {
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
