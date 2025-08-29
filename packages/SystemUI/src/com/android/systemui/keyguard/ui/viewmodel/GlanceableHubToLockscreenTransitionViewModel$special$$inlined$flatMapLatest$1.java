package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
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

/* loaded from: classes2.dex */
public final class GlanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ CommunalSceneInteractor $communalSceneInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ GlanceableHubToLockscreenTransitionViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GlanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel, CommunalSceneInteractor communalSceneInteractor) {
        super(3, continuation);
        this.this$0 = glanceableHubToLockscreenTransitionViewModel;
        this.$communalSceneInteractor$inlined = communalSceneInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        GlanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$1 glanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$1 = new GlanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$communalSceneInteractor$inlined);
        glanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        glanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return glanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final boolean zBooleanValue = ((Boolean) this.L$1).booleanValue();
            KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder = this.this$0.transitionAnimation;
            Duration.Companion companion = Duration.Companion;
            DurationUnit durationUnit = DurationUnit.MILLISECONDS;
            long duration = DurationKt.toDuration(167, durationUnit);
            long duration2 = DurationKt.toDuration(zBooleanValue ? 500 : 167, durationUnit);
            final CommunalSceneInteractor communalSceneInteractor = this.$communalSceneInteractor$inlined;
            KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilder, duration, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToLockscreenTransitionViewModel$keyguardAlpha$1$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    float fFloatValue = ((Number) obj2).floatValue();
                    if (zBooleanValue) {
                        return Float.valueOf(!((Boolean) communalSceneInteractor.rotatedToPortrait.$$delegate_0.getValue()).booleanValue() ? 0.0f : 1.0f);
                    }
                    return Float.valueOf(fFloatValue);
                }
            }, duration2, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToLockscreenTransitionViewModel$keyguardAlpha$1$2
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Float.valueOf(0.0f);
                }
            }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToLockscreenTransitionViewModel$keyguardAlpha$1$3
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Float.valueOf(1.0f);
                }
            }, null, "GLANCEABLE_HUB->LOCKSCREEN: keyguardAlpha", 72);
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default, this) == coroutineSingletons) {
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
