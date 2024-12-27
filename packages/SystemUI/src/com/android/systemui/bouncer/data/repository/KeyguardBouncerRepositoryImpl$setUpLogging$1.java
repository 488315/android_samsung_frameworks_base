package com.android.systemui.bouncer.data.repository;

import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class KeyguardBouncerRepositoryImpl$setUpLogging$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;

    public KeyguardBouncerRepositoryImpl$setUpLogging$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardBouncerRepositoryImpl$setUpLogging$1 keyguardBouncerRepositoryImpl$setUpLogging$1 = new KeyguardBouncerRepositoryImpl$setUpLogging$1(continuation);
        keyguardBouncerRepositoryImpl$setUpLogging$1.Z$0 = ((Boolean) obj).booleanValue();
        return keyguardBouncerRepositoryImpl$setUpLogging$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((KeyguardBouncerRepositoryImpl$setUpLogging$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.d("KeyguardBouncerRepositoryImpl", "Keyguard Bouncer is ".concat(this.Z$0 ? "showing" : "hiding."));
        return Unit.INSTANCE;
    }
}
