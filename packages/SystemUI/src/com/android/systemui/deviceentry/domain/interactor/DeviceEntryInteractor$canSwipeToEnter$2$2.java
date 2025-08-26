package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.deviceentry.shared.model.DeviceUnlockSource;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* loaded from: classes2.dex */
final class DeviceEntryInteractor$canSwipeToEnter$2$2 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public DeviceEntryInteractor$canSwipeToEnter$2$2(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue3 = ((Boolean) obj4).booleanValue();
        DeviceEntryInteractor$canSwipeToEnter$2$2 deviceEntryInteractor$canSwipeToEnter$2$2 = new DeviceEntryInteractor$canSwipeToEnter$2$2((Continuation) obj5);
        deviceEntryInteractor$canSwipeToEnter$2$2.Z$0 = zBooleanValue;
        deviceEntryInteractor$canSwipeToEnter$2$2.Z$1 = zBooleanValue2;
        deviceEntryInteractor$canSwipeToEnter$2$2.L$0 = (DeviceUnlockStatus) obj3;
        deviceEntryInteractor$canSwipeToEnter$2$2.Z$2 = zBooleanValue3;
        return deviceEntryInteractor$canSwipeToEnter$2$2.invokeSuspend(Unit.INSTANCE);
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
        boolean z2 = this.Z$1;
        DeviceUnlockStatus deviceUnlockStatus = (DeviceUnlockStatus) this.L$0;
        return Boolean.valueOf(((z && z2) || !(!deviceUnlockStatus.isUnlocked || (deviceUnlockSource = deviceUnlockStatus.deviceUnlockSource) == null || deviceUnlockSource.dismissesLockscreen)) && !this.Z$2);
    }
}
