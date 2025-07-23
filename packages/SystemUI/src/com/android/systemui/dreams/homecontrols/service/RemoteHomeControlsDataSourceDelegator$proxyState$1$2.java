package com.android.systemui.dreams.homecontrols.service;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class RemoteHomeControlsDataSourceDelegator$proxyState$1$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;

    public RemoteHomeControlsDataSourceDelegator$proxyState$1$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RemoteHomeControlsDataSourceDelegator$proxyState$1$2 remoteHomeControlsDataSourceDelegator$proxyState$1$2 = new RemoteHomeControlsDataSourceDelegator$proxyState$1$2(continuation);
        remoteHomeControlsDataSourceDelegator$proxyState$1$2.Z$0 = ((Boolean) obj).booleanValue();
        return remoteHomeControlsDataSourceDelegator$proxyState$1$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((RemoteHomeControlsDataSourceDelegator$proxyState$1$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(!this.Z$0);
    }
}
