package com.android.systemui.statusbar.pipeline.mobile.util;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class SubscriptionManagerProxyImpl$getActiveSubscriptionInfo$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $subId;
    int label;
    final /* synthetic */ SubscriptionManagerProxyImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubscriptionManagerProxyImpl$getActiveSubscriptionInfo$2(SubscriptionManagerProxyImpl subscriptionManagerProxyImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = subscriptionManagerProxyImpl;
        this.$subId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SubscriptionManagerProxyImpl$getActiveSubscriptionInfo$2(this.this$0, this.$subId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SubscriptionManagerProxyImpl$getActiveSubscriptionInfo$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.this$0.subscriptionManager.getActiveSubscriptionInfo(this.$subId);
    }
}
