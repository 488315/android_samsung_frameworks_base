package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$$ExternalSyntheticLambda0;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class UserAutoAddRepository$autoAdded$3 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ UserAutoAddRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserAutoAddRepository$autoAdded$3(UserAutoAddRepository userAutoAddRepository, Continuation continuation) {
        super(3, continuation);
        this.this$0 = userAutoAddRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        UserAutoAddRepository$autoAdded$3 userAutoAddRepository$autoAdded$3 = new UserAutoAddRepository$autoAdded$3(this.this$0, (Continuation) obj3);
        userAutoAddRepository$autoAdded$3.L$0 = (Set) obj;
        userAutoAddRepository$autoAdded$3.L$1 = (UserAutoAddRepository.ChangeAction) obj2;
        return userAutoAddRepository$autoAdded$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Set set = (Set) this.L$0;
        UserAutoAddRepository.ChangeAction changeAction = (UserAutoAddRepository.ChangeAction) this.L$1;
        Set apply = changeAction.apply(set);
        UserAutoAddRepository userAutoAddRepository = this.this$0;
        if (changeAction instanceof UserAutoAddRepository.RestoreTiles) {
            QSPipelineLogger qSPipelineLogger = userAutoAddRepository.logger;
            qSPipelineLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(10);
            LogBuffer logBuffer = qSPipelineLogger.tileAutoAddLogBuffer;
            LogMessage obtain = logBuffer.obtain("QSAutoAddableLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = apply.toString();
            logMessageImpl.int1 = userAutoAddRepository.userId;
            logBuffer.commit(obtain);
        }
        return apply;
    }
}
