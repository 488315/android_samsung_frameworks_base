package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class MobileIconsInteractorImpl$isDefaultConnectionFailed$1 extends SuspendLambda implements Function4 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public MobileIconsInteractorImpl$isDefaultConnectionFailed$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        MobileIconsInteractorImpl$isDefaultConnectionFailed$1 mobileIconsInteractorImpl$isDefaultConnectionFailed$1 = new MobileIconsInteractorImpl$isDefaultConnectionFailed$1((Continuation) obj4);
        mobileIconsInteractorImpl$isDefaultConnectionFailed$1.Z$0 = booleanValue;
        mobileIconsInteractorImpl$isDefaultConnectionFailed$1.Z$1 = booleanValue2;
        mobileIconsInteractorImpl$isDefaultConnectionFailed$1.Z$2 = booleanValue3;
        return mobileIconsInteractorImpl$isDefaultConnectionFailed$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        boolean z4 = false;
        if (z && !z3 && !z2) {
            z4 = true;
        }
        return Boolean.valueOf(z4);
    }
}
