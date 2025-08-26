package com.android.systemui.biometrics.domain.interactor;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.SideFpsLogger;
import com.android.systemui.log.SideFpsLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
final class SideFpsSensorInteractor$isSettingEnabled$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ SideFpsSensorInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SideFpsSensorInteractor$isSettingEnabled$2(SideFpsSensorInteractor sideFpsSensorInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sideFpsSensorInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SideFpsSensorInteractor$isSettingEnabled$2 sideFpsSensorInteractor$isSettingEnabled$2 = new SideFpsSensorInteractor$isSettingEnabled$2(this.this$0, continuation);
        sideFpsSensorInteractor$isSettingEnabled$2.Z$0 = ((Boolean) obj).booleanValue();
        return sideFpsSensorInteractor$isSettingEnabled$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((SideFpsSensorInteractor$isSettingEnabled$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        SideFpsLogger sideFpsLogger = this.this$0.logger;
        sideFpsLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        SideFpsLogger$$ExternalSyntheticLambda0 sideFpsLogger$$ExternalSyntheticLambda0 = new SideFpsLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = sideFpsLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("SideFpsLogger", logLevel, sideFpsLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer.commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
