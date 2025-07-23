package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MobileConnectionRepositoryImpl$callbackEvents$1$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MobileConnectionRepositoryImpl$callbackEvents$1$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileConnectionRepositoryImpl$callbackEvents$1$2 mobileConnectionRepositoryImpl$callbackEvents$1$2 = new MobileConnectionRepositoryImpl$callbackEvents$1$2((Continuation) obj3);
        mobileConnectionRepositoryImpl$callbackEvents$1$2.L$0 = (TelephonyCallbackState) obj;
        mobileConnectionRepositoryImpl$callbackEvents$1$2.L$1 = (CallbackEvent) obj2;
        return mobileConnectionRepositoryImpl$callbackEvents$1$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return ((TelephonyCallbackState) this.L$0).applyEvent((CallbackEvent) this.L$1);
    }
}
