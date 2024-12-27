package com.android.systemui.deviceentry.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class DeviceEntryInteractor$isAuthenticationRequired$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DeviceEntryInteractor this$0;

    public DeviceEntryInteractor$isAuthenticationRequired$1(DeviceEntryInteractor deviceEntryInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = deviceEntryInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.isAuthenticationRequired(this);
    }
}
