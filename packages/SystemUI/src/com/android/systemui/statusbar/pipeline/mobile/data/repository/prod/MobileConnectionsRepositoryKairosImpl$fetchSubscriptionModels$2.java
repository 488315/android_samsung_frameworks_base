package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.SubscriptionInfo;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MobileConnectionsRepositoryKairosImpl$fetchSubscriptionModels$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MobileConnectionsRepositoryKairosImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryKairosImpl$fetchSubscriptionModels$2(MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryKairosImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MobileConnectionsRepositoryKairosImpl$fetchSubscriptionModels$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryKairosImpl$fetchSubscriptionModels$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List<SubscriptionInfo> completeActiveSubscriptionInfoList = this.this$0.subscriptionManager.getCompleteActiveSubscriptionInfoList();
        MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl = this.this$0;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(completeActiveSubscriptionInfoList, 10));
        for (SubscriptionInfo subscriptionInfo : completeActiveSubscriptionInfoList) {
            subscriptionInfo.getClass();
            mobileConnectionsRepositoryKairosImpl.getClass();
            arrayList.add(new SubscriptionModel(subscriptionInfo.getSubscriptionId(), subscriptionInfo.isOpportunistic(), subscriptionInfo.isOnlyNonTerrestrialNetwork(), subscriptionInfo.getGroupUuid(), subscriptionInfo.getCarrierName().toString(), subscriptionInfo.getProfileClass(), false, false, 0, false, 960, null));
        }
        return arrayList;
    }
}
