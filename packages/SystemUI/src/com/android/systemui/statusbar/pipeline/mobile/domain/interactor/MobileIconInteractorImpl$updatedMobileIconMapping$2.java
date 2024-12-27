package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.util.Log;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class MobileIconInteractorImpl$updatedMobileIconMapping$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$updatedMobileIconMapping$2(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileIconInteractorImpl$updatedMobileIconMapping$2 mobileIconInteractorImpl$updatedMobileIconMapping$2 = new MobileIconInteractorImpl$updatedMobileIconMapping$2(this.this$0, continuation);
        mobileIconInteractorImpl$updatedMobileIconMapping$2.L$0 = obj;
        return mobileIconInteractorImpl$updatedMobileIconMapping$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileIconInteractorImpl$updatedMobileIconMapping$2) create((Map) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Map map = (Map) this.L$0;
        Log.d("IconIntr", "mobileIconMappingTable(" + this.this$0.slotId + "): " + map + " ");
        return Unit.INSTANCE;
    }
}
