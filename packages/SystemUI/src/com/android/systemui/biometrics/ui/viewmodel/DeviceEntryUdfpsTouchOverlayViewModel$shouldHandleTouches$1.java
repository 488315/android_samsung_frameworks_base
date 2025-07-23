package com.android.systemui.biometrics.ui.viewmodel;

import com.android.keyguard.logging.DeviceEntryIconLogger;
import com.android.keyguard.logging.DeviceEntryIconLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DeviceEntryUdfpsTouchOverlayViewModel$shouldHandleTouches$1 extends SuspendLambda implements Function4 {
    final /* synthetic */ DeviceEntryIconLogger $logger;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryUdfpsTouchOverlayViewModel$shouldHandleTouches$1(DeviceEntryIconLogger deviceEntryIconLogger, Continuation continuation) {
        super(4, continuation);
        this.$logger = deviceEntryIconLogger;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        DeviceEntryUdfpsTouchOverlayViewModel$shouldHandleTouches$1 deviceEntryUdfpsTouchOverlayViewModel$shouldHandleTouches$1 = new DeviceEntryUdfpsTouchOverlayViewModel$shouldHandleTouches$1(this.$logger, (Continuation) obj4);
        deviceEntryUdfpsTouchOverlayViewModel$shouldHandleTouches$1.Z$0 = booleanValue;
        deviceEntryUdfpsTouchOverlayViewModel$shouldHandleTouches$1.Z$1 = booleanValue2;
        deviceEntryUdfpsTouchOverlayViewModel$shouldHandleTouches$1.Z$2 = booleanValue3;
        return deviceEntryUdfpsTouchOverlayViewModel$shouldHandleTouches$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        boolean z4 = (z && !z3) || z2;
        DeviceEntryIconLogger deviceEntryIconLogger = this.$logger;
        deviceEntryIconLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        DeviceEntryIconLogger$$ExternalSyntheticLambda0 deviceEntryIconLogger$$ExternalSyntheticLambda0 = new DeviceEntryIconLogger$$ExternalSyntheticLambda0();
        LogBuffer logBuffer = deviceEntryIconLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryUdfpsTouchOverlay", logLevel, deviceEntryIconLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool3 = z3;
        logMessageImpl.bool4 = z4;
        logBuffer.commit(obtain);
        return Boolean.valueOf(z4);
    }
}
