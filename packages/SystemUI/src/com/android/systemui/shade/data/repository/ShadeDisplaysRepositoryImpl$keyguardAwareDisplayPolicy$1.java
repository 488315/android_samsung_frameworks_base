package com.android.systemui.shade.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class ShadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    int label;

    public ShadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        int iIntValue = ((Number) obj2).intValue();
        ShadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1 shadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1 = new ShadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1((Continuation) obj3);
        shadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1.Z$0 = zBooleanValue;
        shadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1.I$0 = iIntValue;
        return shadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        int i = this.I$0;
        if (z) {
            i = 0;
        }
        return new Integer(i);
    }
}
