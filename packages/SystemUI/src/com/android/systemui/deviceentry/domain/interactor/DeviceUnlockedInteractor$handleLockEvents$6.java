package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class DeviceUnlockedInteractor$handleLockEvents$6 extends FunctionReferenceImpl implements Function2 {
    public DeviceUnlockedInteractor$handleLockEvents$6(Object obj) {
        super(2, obj, DeviceUnlockedInteractor.class, "onLockEvent", "onLockEvent(Lcom/android/systemui/deviceentry/domain/interactor/DeviceUnlockedInteractor$LockEvent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return DeviceUnlockedInteractor.access$onLockEvent((DeviceUnlockedInteractor) this.receiver, (DeviceUnlockedInteractor.LockEvent) obj, (Continuation) obj2);
    }
}
