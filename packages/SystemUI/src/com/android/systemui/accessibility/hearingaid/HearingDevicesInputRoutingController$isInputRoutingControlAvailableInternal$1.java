package com.android.systemui.accessibility.hearingaid;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class HearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ HearingDevicesInputRoutingController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1(HearingDevicesInputRoutingController hearingDevicesInputRoutingController, Continuation continuation) {
        super(continuation);
        this.this$0 = hearingDevicesInputRoutingController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return HearingDevicesInputRoutingController.access$isInputRoutingControlAvailableInternal(this.this$0, this);
    }
}
