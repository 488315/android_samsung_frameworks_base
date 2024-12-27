package com.android.systemui.deviceentry.ui.binder;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class LiftToRunFaceAuthBinder$onAwakeKeyguard$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public LiftToRunFaceAuthBinder$onAwakeKeyguard$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        LiftToRunFaceAuthBinder$onAwakeKeyguard$1 liftToRunFaceAuthBinder$onAwakeKeyguard$1 = new LiftToRunFaceAuthBinder$onAwakeKeyguard$1((Continuation) obj3);
        liftToRunFaceAuthBinder$onAwakeKeyguard$1.Z$0 = booleanValue;
        liftToRunFaceAuthBinder$onAwakeKeyguard$1.Z$1 = booleanValue2;
        return liftToRunFaceAuthBinder$onAwakeKeyguard$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 && this.Z$1);
    }
}
