package com.android.systemui.shade.data.repository;

import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShadeDisplaysRepositoryImpl$displayIdFromPolicy$2 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    int label;

    public ShadeDisplaysRepositoryImpl$displayIdFromPolicy$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        ShadeDisplaysRepositoryImpl$displayIdFromPolicy$2 shadeDisplaysRepositoryImpl$displayIdFromPolicy$2 = new ShadeDisplaysRepositoryImpl$displayIdFromPolicy$2((Continuation) obj3);
        shadeDisplaysRepositoryImpl$displayIdFromPolicy$2.I$0 = intValue;
        shadeDisplaysRepositoryImpl$displayIdFromPolicy$2.L$0 = (Set) obj2;
        return shadeDisplaysRepositoryImpl$displayIdFromPolicy$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        if (!((Set) this.L$0).contains(new Integer(i))) {
            i = 0;
        }
        return new Integer(i);
    }
}
