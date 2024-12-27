package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.deviceentry.shared.model.DeviceUnlockSource;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class DeviceEntryInteractor$canSwipeToEnter$2 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public DeviceEntryInteractor$canSwipeToEnter$2(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        DeviceEntryInteractor$canSwipeToEnter$2 deviceEntryInteractor$canSwipeToEnter$2 = new DeviceEntryInteractor$canSwipeToEnter$2((Continuation) obj4);
        deviceEntryInteractor$canSwipeToEnter$2.Z$0 = booleanValue;
        deviceEntryInteractor$canSwipeToEnter$2.L$0 = (DeviceUnlockStatus) obj2;
        deviceEntryInteractor$canSwipeToEnter$2.Z$1 = booleanValue2;
        return deviceEntryInteractor$canSwipeToEnter$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        DeviceUnlockSource deviceUnlockSource;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        DeviceUnlockStatus deviceUnlockStatus = (DeviceUnlockStatus) this.L$0;
        return Boolean.valueOf((z || !(!deviceUnlockStatus.isUnlocked || (deviceUnlockSource = deviceUnlockStatus.deviceUnlockSource) == null || deviceUnlockSource.dismissesLockscreen)) && !this.Z$1);
    }
}
