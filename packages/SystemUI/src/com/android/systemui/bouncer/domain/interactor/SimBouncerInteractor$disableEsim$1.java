package com.android.systemui.bouncer.domain.interactor;

import android.app.PendingIntent;
import android.telephony.SubscriptionInfo;
import android.telephony.euicc.EuiccManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SimBouncerInteractor$disableEsim$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SubscriptionInfo $activeSubscription;
    final /* synthetic */ PendingIntent $callbackIntent;
    int label;
    final /* synthetic */ SimBouncerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimBouncerInteractor$disableEsim$1(SimBouncerInteractor simBouncerInteractor, SubscriptionInfo subscriptionInfo, PendingIntent pendingIntent, Continuation continuation) {
        super(2, continuation);
        this.this$0 = simBouncerInteractor;
        this.$activeSubscription = subscriptionInfo;
        this.$callbackIntent = pendingIntent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SimBouncerInteractor$disableEsim$1(this.this$0, this.$activeSubscription, this.$callbackIntent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SimBouncerInteractor$disableEsim$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        EuiccManager euiccManager = this.this$0.euiccManager;
        if (euiccManager != null) {
            euiccManager.switchToSubscription(-1, this.$activeSubscription.getPortIndex(), this.$callbackIntent);
        }
        return Unit.INSTANCE;
    }
}
