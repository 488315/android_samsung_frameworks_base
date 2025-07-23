package com.android.systemui.unfold;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NoCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$3 extends SuspendLambda implements Function1 {
    int label;
    final /* synthetic */ NoCooldownDisplaySwitchLatencyTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$3(NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker, Continuation continuation) {
        super(1, continuation);
        this.this$0 = noCooldownDisplaySwitchLatencyTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new NoCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$3(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((NoCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$3) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker = this.this$0;
            this.label = 1;
            if (NoCooldownDisplaySwitchLatencyTracker.access$waitForScreenTurnedOn(noCooldownDisplaySwitchLatencyTracker, this) == coroutineSingletons) {
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
