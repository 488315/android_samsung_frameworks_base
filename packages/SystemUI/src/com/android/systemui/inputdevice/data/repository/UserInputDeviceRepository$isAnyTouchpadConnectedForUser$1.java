package com.android.systemui.inputdevice.data.repository;

import com.android.systemui.inputdevice.data.model.UserDeviceConnectionStatus;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class UserInputDeviceRepository$isAnyTouchpadConnectedForUser$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    int label;

    public UserInputDeviceRepository$isAnyTouchpadConnectedForUser$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        int iIntValue = ((Number) obj2).intValue();
        UserInputDeviceRepository$isAnyTouchpadConnectedForUser$1 userInputDeviceRepository$isAnyTouchpadConnectedForUser$1 = new UserInputDeviceRepository$isAnyTouchpadConnectedForUser$1((Continuation) obj3);
        userInputDeviceRepository$isAnyTouchpadConnectedForUser$1.Z$0 = zBooleanValue;
        userInputDeviceRepository$isAnyTouchpadConnectedForUser$1.I$0 = iIntValue;
        return userInputDeviceRepository$isAnyTouchpadConnectedForUser$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new UserDeviceConnectionStatus(this.Z$0, this.I$0);
    }
}
