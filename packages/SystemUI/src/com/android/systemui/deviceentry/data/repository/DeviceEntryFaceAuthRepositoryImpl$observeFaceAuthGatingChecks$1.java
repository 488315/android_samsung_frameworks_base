package com.android.systemui.deviceentry.data.repository;

import com.android.systemui.log.FaceAuthenticationLogger;
import com.android.systemui.log.core.LogLevel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthGatingChecks$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ DeviceEntryFaceAuthRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthGatingChecks$1(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFaceAuthRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthGatingChecks$1 deviceEntryFaceAuthRepositoryImpl$observeFaceAuthGatingChecks$1 = new DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthGatingChecks$1(this.this$0, continuation);
        deviceEntryFaceAuthRepositoryImpl$observeFaceAuthGatingChecks$1.Z$0 = ((Boolean) obj).booleanValue();
        return deviceEntryFaceAuthRepositoryImpl$observeFaceAuthGatingChecks$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthGatingChecks$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        this.this$0.faceAuthLogger.canFaceAuthRunChanged(z);
        if (!z) {
            FaceAuthenticationLogger faceAuthenticationLogger = this.this$0.faceAuthLogger;
            faceAuthenticationLogger.getClass();
            faceAuthenticationLogger.logBuffer.log("DeviceEntryFaceAuthRepositoryLog", LogLevel.DEBUG, "cancelling face auth because a gating condition became false", null);
            this.this$0.cancel();
        }
        return Unit.INSTANCE;
    }
}
