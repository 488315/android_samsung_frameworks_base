package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class LockscreenToDozingTransitionViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ LockscreenToDozingTransitionViewModel this$0;

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
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 immediatelyTransitionTo = this.this$0.transitionAnimation.immediatelyTransitionTo(((Boolean) this.L$1).booleanValue() ? 1.0f : 0.0f);
            this.label = 1;
            if (FlowKt.emitAll(this, immediatelyTransitionTo, flowCollector) == coroutineSingletons) {
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
