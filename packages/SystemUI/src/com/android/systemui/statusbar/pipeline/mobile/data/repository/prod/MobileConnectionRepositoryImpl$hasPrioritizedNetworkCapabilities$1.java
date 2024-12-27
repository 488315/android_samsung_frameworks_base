package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.net.ConnectivityManager;
import android.net.Network;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class MobileConnectionRepositoryImpl$hasPrioritizedNetworkCapabilities$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConnectivityManager $connectivityManager;
    final /* synthetic */ MobileInputLogger $logger;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionRepositoryImpl$hasPrioritizedNetworkCapabilities$1(ConnectivityManager connectivityManager, MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, MobileInputLogger mobileInputLogger, Continuation continuation) {
        super(2, continuation);
        this.$connectivityManager = connectivityManager;
        this.this$0 = mobileConnectionRepositoryImpl;
        this.$logger = mobileInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionRepositoryImpl$hasPrioritizedNetworkCapabilities$1 mobileConnectionRepositoryImpl$hasPrioritizedNetworkCapabilities$1 = new MobileConnectionRepositoryImpl$hasPrioritizedNetworkCapabilities$1(this.$connectivityManager, this.this$0, this.$logger, continuation);
        mobileConnectionRepositoryImpl$hasPrioritizedNetworkCapabilities$1.L$0 = obj;
        return mobileConnectionRepositoryImpl$hasPrioritizedNetworkCapabilities$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionRepositoryImpl$hasPrioritizedNetworkCapabilities$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.net.ConnectivityManager$NetworkCallback, com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$hasPrioritizedNetworkCapabilities$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileInputLogger mobileInputLogger = this.$logger;
            final ?? r1 = new ConnectivityManager.NetworkCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$hasPrioritizedNetworkCapabilities$1$callback$1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onAvailable(Network network) {
                    MobileInputLogger.this.logPrioritizedNetworkAvailable(network.getNetId());
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.TRUE);
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onLost(Network network) {
                    MobileInputLogger.this.logPrioritizedNetworkLost(network.getNetId());
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.FALSE);
                }
            };
            this.$connectivityManager.registerNetworkCallback(this.this$0.networkSliceRequest, (ConnectivityManager.NetworkCallback) r1);
            final ConnectivityManager connectivityManager = this.$connectivityManager;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$hasPrioritizedNetworkCapabilities$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    connectivityManager.unregisterNetworkCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
