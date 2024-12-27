package com.android.systemui.deviceentry.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$3 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ DeviceEntryFaceAuthRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$3(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFaceAuthRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$3 deviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$3 = new DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$3(this.this$0, continuation);
        deviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$3.Z$0 = ((Boolean) obj).booleanValue();
        return deviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((DeviceEntryFaceAuthRepositoryImpl$observeFaceAuthResettingConditions$3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.Z$0) {
            DeviceEntryFaceAuthRepositoryImpl.access$clearPendingAuthRequest(this.this$0, "Resetting auth status");
            this.this$0._isAuthenticated.updateState(null, Boolean.FALSE);
            DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = this.this$0;
            deviceEntryFaceAuthRepositoryImpl.retryCount = 0;
            Job job = deviceEntryFaceAuthRepositoryImpl.halErrorRetryJob;
            if (job != null) {
                job.cancel(null);
            }
        }
        return Unit.INSTANCE;
    }
}
