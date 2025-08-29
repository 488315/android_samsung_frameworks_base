package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.SubscriptionManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1 mobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1 = new MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1(this.this$0, continuation);
        mobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1.L$0 = obj;
        return mobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = this.this$0;
            SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1$callback$1
                @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                public final void onSubscriptionsChanged() {
                    MobileInputLogger mobileInputLogger = mobileConnectionsRepositoryImpl.logger;
                    mobileInputLogger.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(9);
                    LogBuffer logBuffer = mobileInputLogger.buffer;
                    logBuffer.commit(logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null));
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl2 = this.this$0;
            mobileConnectionsRepositoryImpl2.subscriptionManager.addOnSubscriptionsChangedListener(ExecutorsKt.asExecutor(mobileConnectionsRepositoryImpl2.bgDispatcher), onSubscriptionsChangedListener);
            MobileConnectionsRepositoryImpl$isAnySimSecure$1$$ExternalSyntheticLambda0 mobileConnectionsRepositoryImpl$isAnySimSecure$1$$ExternalSyntheticLambda0 = new MobileConnectionsRepositoryImpl$isAnySimSecure$1$$ExternalSyntheticLambda0(this.this$0, onSubscriptionsChangedListener, 3);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, mobileConnectionsRepositoryImpl$isAnySimSecure$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
