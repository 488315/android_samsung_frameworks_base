package com.android.systemui.statusbar.disableflags.data.repository;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;
import com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DisableFlagsRepositoryImpl$disableFlags$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DisableFlagsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisableFlagsRepositoryImpl$disableFlags$2(DisableFlagsRepositoryImpl disableFlagsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = disableFlagsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisableFlagsRepositoryImpl$disableFlags$2 disableFlagsRepositoryImpl$disableFlags$2 = new DisableFlagsRepositoryImpl$disableFlags$2(this.this$0, continuation);
        disableFlagsRepositoryImpl$disableFlags$2.L$0 = obj;
        return disableFlagsRepositoryImpl$disableFlags$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisableFlagsRepositoryImpl$disableFlags$2) create((DisableFlagsModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DisableFlagsModel disableFlagsModel = (DisableFlagsModel) this.L$0;
        DisableFlagsRepositoryImpl disableFlagsRepositoryImpl = this.this$0;
        LogBuffer logBuffer = disableFlagsRepositoryImpl.logBuffer;
        disableFlagsModel.getClass();
        LogLevel logLevel = LogLevel.INFO;
        final DisableFlagsLogger disableFlagsLogger = disableFlagsRepositoryImpl.disableFlagsLogger;
        LogMessage obtain = logBuffer.obtain("DisableFlagsModel", logLevel, new Function1() { // from class: com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                int i = DisableFlagsModel.$r8$clinit;
                return DisableFlagsLogger.this.getDisableFlagsString(new DisableFlagsLogger.DisableState(logMessage.getInt1(), logMessage.getInt2()), null);
            }
        }, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = disableFlagsModel.disable1;
        logMessageImpl.int2 = disableFlagsModel.disable2;
        logBuffer.commit(obtain);
        return Unit.INSTANCE;
    }
}
