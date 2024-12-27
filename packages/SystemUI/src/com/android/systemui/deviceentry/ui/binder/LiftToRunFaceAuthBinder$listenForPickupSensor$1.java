package com.android.systemui.deviceentry.ui.binder;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class LiftToRunFaceAuthBinder$listenForPickupSensor$1 extends SuspendLambda implements Function4 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ LiftToRunFaceAuthBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LiftToRunFaceAuthBinder$listenForPickupSensor$1(LiftToRunFaceAuthBinder liftToRunFaceAuthBinder, Continuation continuation) {
        super(4, continuation);
        this.this$0 = liftToRunFaceAuthBinder;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        LiftToRunFaceAuthBinder$listenForPickupSensor$1 liftToRunFaceAuthBinder$listenForPickupSensor$1 = new LiftToRunFaceAuthBinder$listenForPickupSensor$1(this.this$0, (Continuation) obj4);
        liftToRunFaceAuthBinder$listenForPickupSensor$1.Z$0 = booleanValue;
        liftToRunFaceAuthBinder$listenForPickupSensor$1.Z$1 = booleanValue2;
        return liftToRunFaceAuthBinder$listenForPickupSensor$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf((this.Z$1 || this.Z$0) && this.this$0.deviceEntryFaceAuthInteractor.isFaceAuthEnabledAndEnrolled());
    }
}
