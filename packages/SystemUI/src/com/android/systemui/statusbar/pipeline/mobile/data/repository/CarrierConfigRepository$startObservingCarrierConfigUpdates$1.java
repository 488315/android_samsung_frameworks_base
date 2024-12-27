package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CarrierConfigRepository$startObservingCarrierConfigUpdates$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CarrierConfigRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
