package com.android.systemui.deviceentry.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DeviceUnlockedInteractor$lockDelay$1 extends ContinuationImpl {
    long J$0;
    long J$1;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DeviceUnlockedInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceUnlockedInteractor$lockDelay$1(DeviceUnlockedInteractor deviceUnlockedInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = deviceUnlockedInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        DeviceUnlockedInteractor deviceUnlockedInteractor = this.this$0;
        String str = DeviceUnlockedInteractor.TAG;
        return deviceUnlockedInteractor.lockDelay(this);
    }
}
