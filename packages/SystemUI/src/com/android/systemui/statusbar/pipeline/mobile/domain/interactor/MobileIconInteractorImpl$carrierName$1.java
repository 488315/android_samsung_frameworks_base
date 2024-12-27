package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class MobileIconInteractorImpl$carrierName$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MobileIconInteractorImpl$carrierName$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconInteractorImpl$carrierName$1 mobileIconInteractorImpl$carrierName$1 = new MobileIconInteractorImpl$carrierName$1((Continuation) obj3);
        mobileIconInteractorImpl$carrierName$1.L$0 = (String) obj;
        mobileIconInteractorImpl$carrierName$1.L$1 = (NetworkNameModel) obj2;
        return mobileIconInteractorImpl$carrierName$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String str = (String) this.L$0;
        NetworkNameModel networkNameModel = (NetworkNameModel) this.L$1;
        return (!(networkNameModel instanceof NetworkNameModel.Default) || str == null) ? networkNameModel.getName() : str;
    }
}
