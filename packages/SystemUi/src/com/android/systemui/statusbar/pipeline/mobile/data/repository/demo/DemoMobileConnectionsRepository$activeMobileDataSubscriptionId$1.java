package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$activeMobileDataSubscriptionId$1", m277f = "DemoMobileConnectionsRepository.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class DemoMobileConnectionsRepository$activeMobileDataSubscriptionId$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public DemoMobileConnectionsRepository$activeMobileDataSubscriptionId$1(Continuation<? super DemoMobileConnectionsRepository$activeMobileDataSubscriptionId$1> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DemoMobileConnectionsRepository$activeMobileDataSubscriptionId$1 demoMobileConnectionsRepository$activeMobileDataSubscriptionId$1 = new DemoMobileConnectionsRepository$activeMobileDataSubscriptionId$1(continuation);
        demoMobileConnectionsRepository$activeMobileDataSubscriptionId$1.L$0 = obj;
        return demoMobileConnectionsRepository$activeMobileDataSubscriptionId$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DemoMobileConnectionsRepository$activeMobileDataSubscriptionId$1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SubscriptionModel subscriptionModel = (SubscriptionModel) CollectionsKt___CollectionsKt.firstOrNull((List) this.L$0);
        return new Integer(subscriptionModel != null ? subscriptionModel.subscriptionId : -1);
    }
}
