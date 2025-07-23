package com.android.systemui.shade.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int intValue = ((Number) obj2).intValue();
        ShadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1 shadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1 = new ShadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1((Continuation) obj3);
        shadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1.Z$0 = booleanValue;
        shadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1.I$0 = intValue;
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
