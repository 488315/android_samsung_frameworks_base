package com.android.systemui.statusbar.pipeline.airplane.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class AirplaneModeInteractor$setIsAirplaneMode$1 extends ContinuationImpl {
    Object L$0;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AirplaneModeInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AirplaneModeInteractor$setIsAirplaneMode$1(AirplaneModeInteractor airplaneModeInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = airplaneModeInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.setIsAirplaneMode(false, this);
    }
}
