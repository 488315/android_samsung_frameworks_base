package com.android.systemui.keyguard.data.repository;

import com.android.keyguard.logging.TrustRepositoryLogger;
import com.android.keyguard.logging.TrustRepositoryLogger$$ExternalSyntheticLambda0;
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
final class TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$5 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ TrustRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$5(TrustRepositoryImpl trustRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = trustRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$5 trustRepositoryImpl$isCurrentUserActiveUnlockRunning$5 = new TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$5(this.this$0, continuation);
        trustRepositoryImpl$isCurrentUserActiveUnlockRunning$5.Z$0 = ((Boolean) obj).booleanValue();
        return trustRepositoryImpl$isCurrentUserActiveUnlockRunning$5;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$5) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        TrustRepositoryLogger trustRepositoryLogger = this.this$0.logger;
        trustRepositoryLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        TrustRepositoryLogger$$ExternalSyntheticLambda0 trustRepositoryLogger$$ExternalSyntheticLambda0 = new TrustRepositoryLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = trustRepositoryLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("TrustRepositoryLog", logLevel, trustRepositoryLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer.commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
