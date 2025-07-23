package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MobileConnectionsRepositoryKairosImpl$isDeviceEmergencyCallCapable$1$1$1 extends SuspendLambda implements Function1 {
    int label;
    final /* synthetic */ MobileConnectionsRepositoryKairosImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryKairosImpl$isDeviceEmergencyCallCapable$1$1$1(MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl, Continuation continuation) {
        super(1, continuation);
        this.this$0 = mobileConnectionsRepositoryKairosImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new MobileConnectionsRepositoryKairosImpl$isDeviceEmergencyCallCapable$1$1$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((MobileConnectionsRepositoryKairosImpl$isDeviceEmergencyCallCapable$1$1$1) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl = this.this$0;
        this.label = 1;
        int i2 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
        mobileConnectionsRepositoryKairosImpl.getClass();
        Object withContext = BuildersKt.withContext(mobileConnectionsRepositoryKairosImpl.bgDispatcher, new MobileConnectionsRepositoryKairosImpl$doAnyModemsSupportEmergencyCalls$2(mobileConnectionsRepositoryKairosImpl, null), this);
        return withContext == coroutineSingletons ? coroutineSingletons : withContext;
    }
}
