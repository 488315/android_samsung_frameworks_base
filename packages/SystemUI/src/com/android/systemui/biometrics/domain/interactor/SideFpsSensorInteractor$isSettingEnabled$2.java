package com.android.systemui.biometrics.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class SideFpsSensorInteractor$isSettingEnabled$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ SideFpsSensorInteractor this$0;

    public SideFpsSensorInteractor$isSettingEnabled$2(SideFpsSensorInteractor sideFpsSensorInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sideFpsSensorInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SideFpsSensorInteractor$isSettingEnabled$2 sideFpsSensorInteractor$isSettingEnabled$2 = new SideFpsSensorInteractor$isSettingEnabled$2(this.this$0, continuation);
        sideFpsSensorInteractor$isSettingEnabled$2.Z$0 = ((Boolean) obj).booleanValue();
        return sideFpsSensorInteractor$isSettingEnabled$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((SideFpsSensorInteractor$isSettingEnabled$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.logger.restToUnlockSettingEnabledChanged(this.Z$0);
        return Unit.INSTANCE;
    }
}
