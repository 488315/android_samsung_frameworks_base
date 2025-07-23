package com.android.systemui.keyguard.data.repository;

import android.app.trust.TrustManager;
import com.android.keyguard.TrustGrantFlags;
import com.android.keyguard.logging.TrustRepositoryLogger;
import com.android.keyguard.logging.TrustRepositoryLogger$$ExternalSyntheticLambda0;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.shared.model.ActiveUnlockModel;
import com.android.systemui.keyguard.shared.model.TrustManagedModel;
import com.android.systemui.keyguard.shared.model.TrustModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TrustRepositoryImpl$trust$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TrustRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TrustRepositoryImpl$trust$1(TrustRepositoryImpl trustRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = trustRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TrustRepositoryImpl$trust$1 trustRepositoryImpl$trust$1 = new TrustRepositoryImpl$trust$1(this.this$0, continuation);
        trustRepositoryImpl$trust$1.L$0 = obj;
        return trustRepositoryImpl$trust$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TrustRepositoryImpl$trust$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.app.trust.TrustManager$TrustListener, com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$trust$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final TrustRepositoryImpl trustRepositoryImpl = this.this$0;
            final ?? r1 = new TrustManager.TrustListener() { // from class: com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$trust$1$callback$1
                public final void onIsActiveUnlockRunningChanged(boolean z, int i2) {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    ProducerScope producerScope2 = producerScope;
                    ActiveUnlockModel activeUnlockModel = new ActiveUnlockModel(z, i2);
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(producerScope2, activeUnlockModel, "TrustRepositoryLog", "onActiveUnlockRunningChanged");
                }

                public final void onTrustChanged(boolean z, boolean z2, int i2, int i3, List list) {
                    TrustRepositoryLogger trustRepositoryLogger = TrustRepositoryImpl.this.logger;
                    trustRepositoryLogger.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    TrustRepositoryLogger$$ExternalSyntheticLambda0 trustRepositoryLogger$$ExternalSyntheticLambda0 = new TrustRepositoryLogger$$ExternalSyntheticLambda0(7);
                    LogBuffer logBuffer = trustRepositoryLogger.logBuffer;
                    LogMessage obtain = logBuffer.obtain("TrustRepositoryLog", logLevel, trustRepositoryLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                    logMessageImpl.bool1 = z;
                    logMessageImpl.bool2 = z2;
                    logMessageImpl.int1 = i2;
                    logMessageImpl.int2 = i3;
                    logMessageImpl.str1 = list != null ? CollectionsKt___CollectionsKt.joinToString$default(list, null, null, null, null, 63) : null;
                    logBuffer.commit(obtain);
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    ProducerScope producerScope2 = producerScope;
                    TrustModel trustModel = new TrustModel(z, i2, new TrustGrantFlags(i3));
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(producerScope2, trustModel, "TrustRepositoryLog", "onTrustChanged");
                }

                public final void onTrustManagedChanged(boolean z, int i2) {
                    TrustRepositoryLogger trustRepositoryLogger = TrustRepositoryImpl.this.logger;
                    trustRepositoryLogger.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    TrustRepositoryLogger$$ExternalSyntheticLambda0 trustRepositoryLogger$$ExternalSyntheticLambda0 = new TrustRepositoryLogger$$ExternalSyntheticLambda0(6);
                    LogBuffer logBuffer = trustRepositoryLogger.logBuffer;
                    LogMessage obtain = logBuffer.obtain("TrustRepositoryLog", logLevel, trustRepositoryLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                    logMessageImpl.bool1 = z;
                    logMessageImpl.int1 = i2;
                    logBuffer.commit(obtain);
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    ProducerScope producerScope2 = producerScope;
                    TrustManagedModel trustManagedModel = new TrustManagedModel(i2, z);
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(producerScope2, trustManagedModel, "TrustRepositoryLog", "onTrustManagedChanged");
                }

                public final void onEnabledTrustAgentsChanged(int i2) {
                }

                public final void onTrustError(CharSequence charSequence) {
                }
            };
            this.this$0.trustManager.registerTrustListener((TrustManager.TrustListener) r1);
            TrustRepositoryLogger trustRepositoryLogger = this.this$0.logger;
            trustRepositoryLogger.getClass();
            LogBuffer.log$default(trustRepositoryLogger.logBuffer, "TrustRepositoryLog", LogLevel.VERBOSE, "TrustRepository#registerTrustListener");
            final TrustRepositoryImpl trustRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$trust$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    TrustRepositoryImpl trustRepositoryImpl3 = TrustRepositoryImpl.this;
                    TrustRepositoryLogger trustRepositoryLogger2 = trustRepositoryImpl3.logger;
                    trustRepositoryLogger2.getClass();
                    LogBuffer.log$default(trustRepositoryLogger2.logBuffer, "TrustRepositoryLog", LogLevel.VERBOSE, "TrustRepository#unregisterTrustListener");
                    trustRepositoryImpl3.trustManager.unregisterTrustListener(r1);
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
