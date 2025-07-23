package com.android.systemui.accessibility.hearingaid;

import com.android.systemui.accessibility.hearingaid.HearingDevicesInputRoutingController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class HearingDevicesInputRoutingController$isInputRoutingControlAvailable$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ HearingDevicesInputRoutingController.InputRoutingControlAvailableCallback $callback;
    int label;
    final /* synthetic */ HearingDevicesInputRoutingController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HearingDevicesInputRoutingController$isInputRoutingControlAvailable$1(HearingDevicesInputRoutingController hearingDevicesInputRoutingController, HearingDevicesInputRoutingController.InputRoutingControlAvailableCallback inputRoutingControlAvailableCallback, Continuation continuation) {
        super(2, continuation);
        this.this$0 = hearingDevicesInputRoutingController;
        this.$callback = inputRoutingControlAvailableCallback;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HearingDevicesInputRoutingController$isInputRoutingControlAvailable$1(this.this$0, this.$callback, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HearingDevicesInputRoutingController$isInputRoutingControlAvailable$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            HearingDevicesInputRoutingController hearingDevicesInputRoutingController = this.this$0;
            this.label = 1;
            obj = HearingDevicesInputRoutingController.access$isInputRoutingControlAvailableInternal(hearingDevicesInputRoutingController, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.$callback.onResult(((Boolean) obj).booleanValue());
        return Unit.INSTANCE;
    }
}
