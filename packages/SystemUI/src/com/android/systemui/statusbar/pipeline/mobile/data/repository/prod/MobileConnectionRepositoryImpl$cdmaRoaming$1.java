package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class MobileConnectionRepositoryImpl$cdmaRoaming$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MobileConnectionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionRepositoryImpl$cdmaRoaming$1(MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MobileConnectionRepositoryImpl$cdmaRoaming$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionRepositoryImpl$cdmaRoaming$1) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = false;
        try {
            int cdmaEnhancedRoamingIndicatorDisplayNumber = this.this$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber();
            if (cdmaEnhancedRoamingIndicatorDisplayNumber == 0 || cdmaEnhancedRoamingIndicatorDisplayNumber == 2) {
                z = true;
            }
        } catch (UnsupportedOperationException unused) {
        }
        return Boolean.valueOf(z);
    }
}
