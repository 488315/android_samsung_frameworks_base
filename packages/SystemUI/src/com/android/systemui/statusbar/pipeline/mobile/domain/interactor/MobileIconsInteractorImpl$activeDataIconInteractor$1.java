package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class MobileIconsInteractorImpl$activeDataIconInteractor$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileIconsInteractorImpl this$0;

    public MobileIconsInteractorImpl$activeDataIconInteractor$1(MobileIconsInteractorImpl mobileIconsInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileIconsInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileIconsInteractorImpl$activeDataIconInteractor$1 mobileIconsInteractorImpl$activeDataIconInteractor$1 = new MobileIconsInteractorImpl$activeDataIconInteractor$1(this.this$0, continuation);
        mobileIconsInteractorImpl$activeDataIconInteractor$1.L$0 = obj;
        return mobileIconsInteractorImpl$activeDataIconInteractor$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileIconsInteractorImpl$activeDataIconInteractor$1) create((Integer) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Integer num = (Integer) this.L$0;
        if (num != null) {
            return this.this$0.getMobileConnectionInteractorForSubId(num.intValue());
        }
        return null;
    }
}
