package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardRootViewModel$isOnOrGoingToLockscreen$2 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public KeyguardRootViewModel$isOnOrGoingToLockscreen$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        KeyguardRootViewModel$isOnOrGoingToLockscreen$2 keyguardRootViewModel$isOnOrGoingToLockscreen$2 = new KeyguardRootViewModel$isOnOrGoingToLockscreen$2((Continuation) obj3);
        keyguardRootViewModel$isOnOrGoingToLockscreen$2.Z$0 = booleanValue;
        keyguardRootViewModel$isOnOrGoingToLockscreen$2.Z$1 = booleanValue2;
        return keyguardRootViewModel$isOnOrGoingToLockscreen$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 || this.Z$1);
    }
}
