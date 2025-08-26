package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.os.ParcelUuid;
import android.telephony.SubscriptionInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class MobileConnectionsRepositoryKairosImpl$isActiveSubChangeInGroup$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $newId;
    final /* synthetic */ int $prevId;
    int label;
    final /* synthetic */ MobileConnectionsRepositoryKairosImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryKairosImpl$isActiveSubChangeInGroup$2(MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl, int i, int i2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryKairosImpl;
        this.$prevId = i;
        this.$newId = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MobileConnectionsRepositoryKairosImpl$isActiveSubChangeInGroup$2(this.this$0, this.$prevId, this.$newId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryKairosImpl$isActiveSubChangeInGroup$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SubscriptionInfo activeSubscriptionInfo = this.this$0.subscriptionManager.getActiveSubscriptionInfo(this.$prevId);
        ParcelUuid groupUuid = activeSubscriptionInfo != null ? activeSubscriptionInfo.getGroupUuid() : null;
        SubscriptionInfo activeSubscriptionInfo2 = this.this$0.subscriptionManager.getActiveSubscriptionInfo(this.$newId);
        return Boolean.valueOf(groupUuid != null && groupUuid.equals(activeSubscriptionInfo2 != null ? activeSubscriptionInfo2.getGroupUuid() : null));
    }
}
