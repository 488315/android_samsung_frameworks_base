package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class MobileIconInteractorImpl$isVoWifiConnected$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MobileIconInteractorImpl$isVoWifiConnected$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconInteractorImpl$isVoWifiConnected$1 mobileIconInteractorImpl$isVoWifiConnected$1 = new MobileIconInteractorImpl$isVoWifiConnected$1((Continuation) obj3);
        mobileIconInteractorImpl$isVoWifiConnected$1.L$0 = (ImsRegState) obj;
        mobileIconInteractorImpl$isVoWifiConnected$1.L$1 = (MobileServiceState) obj2;
        return mobileIconInteractorImpl$isVoWifiConnected$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(((ImsRegState) this.L$0).voWifiRegState && ((MobileServiceState) this.L$1).telephonyDisplayInfo.getNetworkType() == 18);
    }
}
