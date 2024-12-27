package com.android.systemui.keyguard.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class KeyguardRepositoryImpl$isEncryptedOrLockdown$4 extends SuspendLambda implements Function2 {
    /* synthetic */ int I$0;
    int label;
    final /* synthetic */ KeyguardRepositoryImpl this$0;

    public KeyguardRepositoryImpl$isEncryptedOrLockdown$4(KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardRepositoryImpl$isEncryptedOrLockdown$4 keyguardRepositoryImpl$isEncryptedOrLockdown$4 = new KeyguardRepositoryImpl$isEncryptedOrLockdown$4(this.this$0, continuation);
        keyguardRepositoryImpl$isEncryptedOrLockdown$4.I$0 = ((Number) obj).intValue();
        return keyguardRepositoryImpl$isEncryptedOrLockdown$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardRepositoryImpl$isEncryptedOrLockdown$4) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.this$0.keyguardUpdateMonitor.isEncryptedOrLockdown(this.I$0));
    }
}
