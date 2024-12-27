package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.util.Log;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class MobileIconsInteractorImpl$defaultMobileIconMappingTable$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public MobileIconsInteractorImpl$defaultMobileIconMappingTable$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileIconsInteractorImpl$defaultMobileIconMappingTable$1 mobileIconsInteractorImpl$defaultMobileIconMappingTable$1 = new MobileIconsInteractorImpl$defaultMobileIconMappingTable$1(continuation);
        mobileIconsInteractorImpl$defaultMobileIconMappingTable$1.L$0 = obj;
        return mobileIconsInteractorImpl$defaultMobileIconMappingTable$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileIconsInteractorImpl$defaultMobileIconMappingTable$1) create((Map) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        for (Map.Entry entry : ((Map) this.L$0).entrySet()) {
            Log.d("IconsIntr", "mobileIconMappingTable(" + entry.getKey() + "): " + entry.getValue() + " ");
        }
        return Unit.INSTANCE;
    }
}
