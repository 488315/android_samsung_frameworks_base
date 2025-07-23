package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LockscreenToDozingTransitionViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ LockscreenToDozingTransitionViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockscreenToDozingTransitionViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, LockscreenToDozingTransitionViewModel lockscreenToDozingTransitionViewModel) {
        super(3, continuation);
        this.this$0 = lockscreenToDozingTransitionViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        LockscreenToDozingTransitionViewModel$special$$inlined$flatMapLatest$1 lockscreenToDozingTransitionViewModel$special$$inlined$flatMapLatest$1 = new LockscreenToDozingTransitionViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        lockscreenToDozingTransitionViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        lockscreenToDozingTransitionViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return lockscreenToDozingTransitionViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                m2599sharedFlow74qcysc$default = this.this$0.transitionAnimation.immediatelyTransitionTo(1.0f);
            } else {
                KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder = this.this$0.transitionAnimation;
                Duration.Companion companion = Duration.Companion;
                m2599sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(flowBuilder, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDozingTransitionViewModel$deviceEntryParentViewAlpha$1$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        return Float.valueOf(1.0f - ((Number) obj2).floatValue());
                    }
                }, 0L, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, m2599sharedFlow74qcysc$default, this) == coroutineSingletons) {
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
