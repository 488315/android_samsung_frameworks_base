package com.android.systemui.keyguard.ui.viewmodel;

import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.domain.interactor.FromGlanceableHubTransitionInteractor;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GlanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ GlanceableHubToLockscreenTransitionViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GlanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$2(Continuation continuation, GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel) {
        super(3, continuation);
        this.this$0 = glanceableHubToLockscreenTransitionViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        GlanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$2 glanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$2 = new GlanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        glanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        glanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return glanceableHubToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final int intValue = ((Number) this.L$1).intValue();
            KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder = this.this$0.transitionAnimation;
            FromGlanceableHubTransitionInteractor.Companion.getClass();
            long j = FromGlanceableHubTransitionInteractor.TO_LOCKSCREEN_DURATION;
            Interpolator interpolator = Interpolators.EMPHASIZED;
            final GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel = this.this$0;
            Flow m2600sharedFlowWithState74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2600sharedFlowWithState74qcysc$default(flowBuilder, j, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToLockscreenTransitionViewModel$keyguardTranslationX$1$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    float floatValue = ((Number) obj2).floatValue();
                    if (((Boolean) GlanceableHubToLockscreenTransitionViewModel.this.willRotateToPortraitInTransition.$$delegate_0.getValue()).booleanValue()) {
                        return Float.valueOf(0.0f);
                    }
                    return Float.valueOf((floatValue * intValue) + (-r1));
                }
            }, 0L, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToLockscreenTransitionViewModel$keyguardTranslationX$1$2
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Float.valueOf(0.0f);
                }
            }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToLockscreenTransitionViewModel$keyguardTranslationX$1$3
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Float.valueOf(0.0f);
                }
            }, interpolator, "GLANCEABLE_HUB->LOCKSCREEN: keyguardTranslationX", 12);
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, m2600sharedFlowWithState74qcysc$default, this) == coroutineSingletons) {
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
