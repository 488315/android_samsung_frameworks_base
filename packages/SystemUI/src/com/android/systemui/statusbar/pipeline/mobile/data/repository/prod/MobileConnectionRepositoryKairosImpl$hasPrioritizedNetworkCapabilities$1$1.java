package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.net.ConnectivityManager;
import android.net.Network;
import com.android.systemui.kairos.BuildScopeKt;
import com.android.systemui.kairos.CoalescingEventProducerScope;
import com.android.systemui.kairos.internal.BuildScopeImpl$coalescingEvents$1$1;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$$ExternalSyntheticLambda0;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConnectivityManager $connectivityManager;
    final /* synthetic */ MobileInputLogger $logger;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryKairosImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1(ConnectivityManager connectivityManager, MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl, MobileInputLogger mobileInputLogger, Continuation continuation) {
        super(2, continuation);
        this.$connectivityManager = connectivityManager;
        this.this$0 = mobileConnectionRepositoryKairosImpl;
        this.$logger = mobileInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1 mobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1 = new MobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1(this.$connectivityManager, this.this$0, this.$logger, continuation);
        mobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1.L$0 = obj;
        return mobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1) create((CoalescingEventProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final CoalescingEventProducerScope coalescingEventProducerScope = (CoalescingEventProducerScope) this.L$0;
            final MobileInputLogger mobileInputLogger = this.$logger;
            ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1$callback$1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onAvailable(Network network) {
                    MobileInputLogger mobileInputLogger2 = MobileInputLogger.this;
                    int netId = network.getNetId();
                    mobileInputLogger2.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(5);
                    LogBuffer logBuffer = mobileInputLogger2.buffer;
                    LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
                    ((LogMessageImpl) obtain).int1 = netId;
                    logBuffer.commit(obtain);
                    ((BuildScopeImpl$coalescingEvents$1$1) coalescingEventProducerScope).emit(Boolean.TRUE);
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onLost(Network network) {
                    MobileInputLogger mobileInputLogger2 = MobileInputLogger.this;
                    int netId = network.getNetId();
                    mobileInputLogger2.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(11);
                    LogBuffer logBuffer = mobileInputLogger2.buffer;
                    LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
                    ((LogMessageImpl) obtain).int1 = netId;
                    logBuffer.commit(obtain);
                    ((BuildScopeImpl$coalescingEvents$1$1) coalescingEventProducerScope).emit(Boolean.FALSE);
                }
            };
            this.$connectivityManager.registerNetworkCallback(this.this$0.networkSliceRequest, networkCallback);
            MobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0 mobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0 = new MobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0(2, this.$connectivityManager, networkCallback);
            this.label = 1;
            if (BuildScopeKt.awaitClose(mobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
