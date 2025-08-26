package com.android.systemui.statusbar.pipeline.shared.data.repository;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger;
import com.android.systemui.statusbar.pipeline.shared.LoggerHelper;
import com.android.systemui.statusbar.pipeline.shared.LoggerHelper$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class ConnectivityRepositoryImpl$defaultNetworkCapabilities$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConnectivityInputLogger $logger;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ConnectivityRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConnectivityRepositoryImpl$defaultNetworkCapabilities$1(ConnectivityRepositoryImpl connectivityRepositoryImpl, ConnectivityInputLogger connectivityInputLogger, Continuation continuation) {
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

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ConnectivityInputLogger connectivityInputLogger = this.$logger;
            ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$defaultNetworkCapabilities$1$callback$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                    ConnectivityInputLogger connectivityInputLogger2 = connectivityInputLogger;
                    connectivityInputLogger2.getClass();
                    LoggerHelper.INSTANCE.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    LoggerHelper$$ExternalSyntheticLambda0 loggerHelper$$ExternalSyntheticLambda0 = new LoggerHelper$$ExternalSyntheticLambda0(0);
                    LogBuffer logBuffer = connectivityInputLogger2.buffer;
                    LogMessage logMessageObtain = logBuffer.obtain("ConnectivityInputLogger", logLevel, loggerHelper$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                    logMessageImpl.bool1 = true;
                    logMessageImpl.int1 = network.getNetId();
                    logMessageImpl.str1 = networkCapabilities.toString();
                    logBuffer.commit(logMessageObtain);
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(networkCapabilities);
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onLost(Network network) {
                    ConnectivityInputLogger connectivityInputLogger2 = connectivityInputLogger;
                    connectivityInputLogger2.getClass();
                    LoggerHelper.INSTANCE.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    LoggerHelper$$ExternalSyntheticLambda0 loggerHelper$$ExternalSyntheticLambda0 = new LoggerHelper$$ExternalSyntheticLambda0(1);
                    LogBuffer logBuffer = connectivityInputLogger2.buffer;
                    LogMessage logMessageObtain = logBuffer.obtain("ConnectivityInputLogger", logLevel, loggerHelper$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                    logMessageImpl.int1 = network.getNetId();
                    logMessageImpl.bool1 = true;
                    logBuffer.commit(logMessageObtain);
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(null);
                }
            };
            this.this$0.connectivityManager.registerDefaultNetworkCallback(networkCallback);
            ConnectivityRepositoryImpl$forceHiddenSlots$1$$ExternalSyntheticLambda0 connectivityRepositoryImpl$forceHiddenSlots$1$$ExternalSyntheticLambda0 = new ConnectivityRepositoryImpl$forceHiddenSlots$1$$ExternalSyntheticLambda0(1, this.this$0, networkCallback);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, connectivityRepositoryImpl$forceHiddenSlots$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
