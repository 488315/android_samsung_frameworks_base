package com.android.systemui.statusbar.pipeline.shared.data.repository;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger;
import com.android.systemui.statusbar.pipeline.shared.LoggerHelper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$defaultNetworkCapabilities$1", m277f = "ConnectivityRepository.kt", m278l = {158}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class ConnectivityRepositoryImpl$defaultNetworkCapabilities$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConnectivityInputLogger $logger;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ConnectivityRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConnectivityRepositoryImpl$defaultNetworkCapabilities$1(ConnectivityRepositoryImpl connectivityRepositoryImpl, ConnectivityInputLogger connectivityInputLogger, Continuation<? super ConnectivityRepositoryImpl$defaultNetworkCapabilities$1> continuation) {
        super(2, continuation);
        this.this$0 = connectivityRepositoryImpl;
        this.$logger = connectivityInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ConnectivityRepositoryImpl$defaultNetworkCapabilities$1 connectivityRepositoryImpl$defaultNetworkCapabilities$1 = new ConnectivityRepositoryImpl$defaultNetworkCapabilities$1(this.this$0, this.$logger, continuation);
        connectivityRepositoryImpl$defaultNetworkCapabilities$1.L$0 = obj;
        return connectivityRepositoryImpl$defaultNetworkCapabilities$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConnectivityRepositoryImpl$defaultNetworkCapabilities$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.net.ConnectivityManager$NetworkCallback, com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$defaultNetworkCapabilities$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ConnectivityInputLogger connectivityInputLogger = this.$logger;
            final ?? r1 = new ConnectivityManager.NetworkCallback() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$defaultNetworkCapabilities$1$callback$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                    ConnectivityInputLogger connectivityInputLogger2 = ConnectivityInputLogger.this;
                    connectivityInputLogger2.getClass();
                    LoggerHelper.INSTANCE.getClass();
                    LoggerHelper.logOnCapabilitiesChanged(connectivityInputLogger2.buffer, "ConnectivityInputLogger", network, networkCapabilities, true);
                    ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(networkCapabilities);
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onLost(Network network) {
                    ConnectivityInputLogger connectivityInputLogger2 = ConnectivityInputLogger.this;
                    connectivityInputLogger2.getClass();
                    LoggerHelper.INSTANCE.getClass();
                    LoggerHelper.logOnLost(connectivityInputLogger2.buffer, "ConnectivityInputLogger", network, true);
                    ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(null);
                }
            };
            this.this$0.connectivityManager.registerDefaultNetworkCallback(r1);
            final ConnectivityRepositoryImpl connectivityRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$defaultNetworkCapabilities$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ConnectivityRepositoryImpl.this.connectivityManager.unregisterNetworkCallback(r1);
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
