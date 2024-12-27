package com.android.systemui.haptics.slider;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class SliderStateTracker$launchTimer$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SliderStateTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SliderStateTracker$launchTimer$1(SliderStateTracker sliderStateTracker, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sliderStateTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SliderStateTracker$launchTimer$1 sliderStateTracker$launchTimer$1 = new SliderStateTracker$launchTimer$1(this.this$0, continuation);
        sliderStateTracker$launchTimer$1.L$0 = obj;
        return sliderStateTracker$launchTimer$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SliderStateTracker$launchTimer$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineScope coroutineScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope2 = (CoroutineScope) this.L$0;
            long j = this.this$0.config.waitTimeMillis;
            this.L$0 = coroutineScope2;
            this.label = 1;
            if (DelayKt.delay(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            coroutineScope = coroutineScope2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            coroutineScope = (CoroutineScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        if (CoroutineScopeKt.isActive(coroutineScope)) {
            SliderStateTracker sliderStateTracker = this.this$0;
            if (sliderStateTracker.currentState == SliderState.WAIT) {
                sliderStateTracker.setState(SliderState.DRAG_HANDLE_ACQUIRED_BY_TOUCH);
                SliderStateTracker sliderStateTracker2 = this.this$0;
                sliderStateTracker2.executeOnState(sliderStateTracker2.currentState);
            }
        }
        return Unit.INSTANCE;
    }
}
