package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
final class CarrierConfigRepositoryImpl$carrierConfigStream$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ MobileInputLogger $logger;
    /* synthetic */ int I$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CarrierConfigRepositoryImpl$carrierConfigStream$2(MobileInputLogger mobileInputLogger, Continuation continuation) {
        super(2, continuation);
        this.$logger = mobileInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CarrierConfigRepositoryImpl$carrierConfigStream$2 carrierConfigRepositoryImpl$carrierConfigStream$2 = new CarrierConfigRepositoryImpl$carrierConfigStream$2(this.$logger, continuation);
        carrierConfigRepositoryImpl$carrierConfigStream$2.I$0 = ((Number) obj).intValue();
        return carrierConfigRepositoryImpl$carrierConfigStream$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CarrierConfigRepositoryImpl$carrierConfigStream$2) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        MobileInputLogger mobileInputLogger = this.$logger;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(6);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        logBuffer.commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
