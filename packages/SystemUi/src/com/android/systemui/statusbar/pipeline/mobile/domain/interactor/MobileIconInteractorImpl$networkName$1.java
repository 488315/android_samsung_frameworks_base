package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$networkName$1", m277f = "MobileIconInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconInteractorImpl$networkName$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MobileIconInteractorImpl$networkName$1(Continuation<? super MobileIconInteractorImpl$networkName$1> continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconInteractorImpl$networkName$1 mobileIconInteractorImpl$networkName$1 = new MobileIconInteractorImpl$networkName$1((Continuation) obj3);
        mobileIconInteractorImpl$networkName$1.L$0 = (String) obj;
        mobileIconInteractorImpl$networkName$1.L$1 = (NetworkNameModel) obj2;
        return mobileIconInteractorImpl$networkName$1.invokeSuspend(Unit.INSTANCE);
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
        return (!(networkNameModel instanceof NetworkNameModel.Default) || str == null) ? networkNameModel : new NetworkNameModel.IntentDerived(str);
    }
}
