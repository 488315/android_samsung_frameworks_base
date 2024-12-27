package com.android.systemui.deviceentry.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

final class DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ DeviceEntryFaceAuthRepositoryImpl this$0;

    public DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$1(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, Continuation continuation) {
        super(5, continuation);
        this.this$0 = deviceEntryFaceAuthRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$1 deviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$1 = new DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$1(this.this$0, (Continuation) obj5);
        deviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$1.L$0 = (AuthenticationRequest) obj;
        deviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$1.Z$0 = booleanValue;
        deviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$1.Z$1 = booleanValue2;
        deviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$1.Z$2 = booleanValue3;
        return deviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AuthenticationRequest authenticationRequest = (AuthenticationRequest) this.L$0;
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        if ((authenticationRequest == null || z || (z2 && authenticationRequest.fallbackToDetection)) && !z3) {
            return authenticationRequest;
        }
        this.this$0.faceAuthLogger.notProcessingRequestYet(authenticationRequest != null ? authenticationRequest.uiEvent : null, z, z2, z3);
        return null;
    }
}
