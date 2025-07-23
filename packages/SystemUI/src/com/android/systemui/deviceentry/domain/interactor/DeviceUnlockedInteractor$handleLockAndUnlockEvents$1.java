package com.android.systemui.deviceentry.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DeviceUnlockedInteractor$handleLockAndUnlockEvents$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DeviceUnlockedInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceUnlockedInteractor$handleLockAndUnlockEvents$1(DeviceUnlockedInteractor deviceUnlockedInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = deviceUnlockedInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return DeviceUnlockedInteractor.access$handleLockAndUnlockEvents(this.this$0, this);
    }
}
