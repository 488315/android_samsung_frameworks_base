package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class CarrierConfigRepository$startObservingCarrierConfigUpdates$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CarrierConfigRepository this$0;

    public CarrierConfigRepository$startObservingCarrierConfigUpdates$1(CarrierConfigRepository carrierConfigRepository, Continuation continuation) {
        super(continuation);
        this.this$0 = carrierConfigRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.startObservingCarrierConfigUpdates(this);
    }
}
