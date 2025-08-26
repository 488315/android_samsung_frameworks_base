package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionsRepositoryKairosImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1(MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryKairosImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1 mobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1 = new MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1(this.this$0, continuation);
        mobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1.L$0 = obj;
        return mobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1 mobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1 = new MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1(producerScope);
            this.this$0.telephonyManager.registerTelephonyCallback(new Executor() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1.1
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    runnable.run();
                }
            }, mobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1);
            MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1$$ExternalSyntheticLambda0 mobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1$$ExternalSyntheticLambda0 = new MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1$$ExternalSyntheticLambda0(this.this$0, mobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1, 2);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, mobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
