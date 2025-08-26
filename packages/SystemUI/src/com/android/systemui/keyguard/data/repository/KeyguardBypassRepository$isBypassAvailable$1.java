package com.android.systemui.keyguard.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes2.dex */
final class KeyguardBypassRepository$isBypassAvailable$1 extends SuspendLambda implements Function4 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public KeyguardBypassRepository$isBypassAvailable$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue3 = ((Boolean) obj3).booleanValue();
        KeyguardBypassRepository$isBypassAvailable$1 keyguardBypassRepository$isBypassAvailable$1 = new KeyguardBypassRepository$isBypassAvailable$1((Continuation) obj4);
        keyguardBypassRepository$isBypassAvailable$1.Z$0 = zBooleanValue;
        keyguardBypassRepository$isBypassAvailable$1.Z$1 = zBooleanValue2;
        keyguardBypassRepository$isBypassAvailable$1.Z$2 = zBooleanValue3;
        return keyguardBypassRepository$isBypassAvailable$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 && this.Z$1 && this.Z$2);
    }
}
