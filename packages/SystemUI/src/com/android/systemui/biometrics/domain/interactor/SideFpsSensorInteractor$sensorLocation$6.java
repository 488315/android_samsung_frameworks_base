package com.android.systemui.biometrics.domain.interactor;

import com.android.systemui.biometrics.domain.model.SideFpsSensorLocation;
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
final class SideFpsSensorInteractor$sensorLocation$6 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SideFpsSensorInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SideFpsSensorInteractor$sensorLocation$6(SideFpsSensorInteractor sideFpsSensorInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sideFpsSensorInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SideFpsSensorInteractor$sensorLocation$6 sideFpsSensorInteractor$sensorLocation$6 = new SideFpsSensorInteractor$sensorLocation$6(this.this$0, continuation);
        sideFpsSensorInteractor$sensorLocation$6.L$0 = obj;
        return sideFpsSensorInteractor$sensorLocation$6;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SideFpsSensorInteractor$sensorLocation$6) create((SideFpsSensorLocation) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SideFpsSensorLocation sideFpsSensorLocation = (SideFpsSensorLocation) this.L$0;
        SideFpsLogger sideFpsLogger = this.this$0.logger;
        int i = sideFpsSensorLocation.left;
        sideFpsLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        SideFpsLogger$$ExternalSyntheticLambda0 sideFpsLogger$$ExternalSyntheticLambda0 = new SideFpsLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = sideFpsLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("SideFpsLogger", logLevel, sideFpsLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = sideFpsSensorLocation.top;
        logMessageImpl.str2 = String.valueOf(sideFpsSensorLocation.length);
        logMessageImpl.bool1 = sideFpsSensorLocation.isSensorVerticalInDefaultOrientation;
        logBuffer.commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
