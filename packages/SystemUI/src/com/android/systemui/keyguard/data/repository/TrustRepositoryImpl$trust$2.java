package com.android.systemui.keyguard.data.repository;

import com.android.keyguard.logging.TrustRepositoryLogger;
import com.android.keyguard.logging.TrustRepositoryLogger$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.shared.model.ActiveUnlockModel;
import com.android.systemui.keyguard.shared.model.TrustManagedModel;
import com.android.systemui.keyguard.shared.model.TrustModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class TrustRepositoryImpl$trust$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TrustRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TrustRepositoryImpl$trust$2(TrustRepositoryImpl trustRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = trustRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TrustRepositoryImpl$trust$2 trustRepositoryImpl$trust$2 = new TrustRepositoryImpl$trust$2(this.this$0, continuation);
        trustRepositoryImpl$trust$2.L$0 = obj;
        return trustRepositoryImpl$trust$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TrustRepositoryImpl$trust$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Object obj2 = this.L$0;
        if (obj2 instanceof TrustModel) {
            TrustModel trustModel = (TrustModel) obj2;
            this.this$0.latestTrustModelForUser.put(new Integer(trustModel.userId), obj2);
            TrustRepositoryLogger trustRepositoryLogger = this.this$0.logger;
            trustRepositoryLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            TrustRepositoryLogger$$ExternalSyntheticLambda0 trustRepositoryLogger$$ExternalSyntheticLambda0 = new TrustRepositoryLogger$$ExternalSyntheticLambda0(5);
            LogBuffer logBuffer = trustRepositoryLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("TrustRepositoryLog", logLevel, trustRepositoryLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.int1 = trustModel.userId;
            logMessageImpl.bool1 = trustModel.isTrusted;
            logBuffer.commit(logMessageObtain);
        } else if (obj2 instanceof ActiveUnlockModel) {
            ActiveUnlockModel activeUnlockModel = (ActiveUnlockModel) obj2;
            this.this$0.activeUnlockRunningForUser.put(new Integer(activeUnlockModel.userId), obj2);
            TrustRepositoryLogger trustRepositoryLogger2 = this.this$0.logger;
            trustRepositoryLogger2.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            TrustRepositoryLogger$$ExternalSyntheticLambda0 trustRepositoryLogger$$ExternalSyntheticLambda02 = new TrustRepositoryLogger$$ExternalSyntheticLambda0(2);
            LogBuffer logBuffer2 = trustRepositoryLogger2.logBuffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("TrustRepositoryLog", logLevel2, trustRepositoryLogger$$ExternalSyntheticLambda02, null);
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
            logMessageImpl2.int1 = activeUnlockModel.userId;
            logMessageImpl2.bool1 = activeUnlockModel.isRunning;
            logBuffer2.commit(logMessageObtain2);
        } else if (obj2 instanceof TrustManagedModel) {
            TrustManagedModel trustManagedModel = (TrustManagedModel) obj2;
            this.this$0.trustManagedForUser.put(new Integer(trustManagedModel.userId), obj2);
            TrustRepositoryLogger trustRepositoryLogger3 = this.this$0.logger;
            trustRepositoryLogger3.getClass();
            LogLevel logLevel3 = LogLevel.DEBUG;
            TrustRepositoryLogger$$ExternalSyntheticLambda0 trustRepositoryLogger$$ExternalSyntheticLambda03 = new TrustRepositoryLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer3 = trustRepositoryLogger3.logBuffer;
            LogMessage logMessageObtain3 = logBuffer3.obtain("TrustRepositoryLog", logLevel3, trustRepositoryLogger$$ExternalSyntheticLambda03, null);
            LogMessageImpl logMessageImpl3 = (LogMessageImpl) logMessageObtain3;
            logMessageImpl3.bool1 = trustManagedModel.isTrustManaged;
            logMessageImpl3.int1 = trustManagedModel.userId;
            logBuffer3.commit(logMessageObtain3);
        }
        return Unit.INSTANCE;
    }
}
