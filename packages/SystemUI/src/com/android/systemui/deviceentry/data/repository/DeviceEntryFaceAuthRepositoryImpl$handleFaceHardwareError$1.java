package com.android.systemui.deviceentry.data.repository;

import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.log.FaceAuthenticationLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.StateFlowImpl;

final class DeviceEntryFaceAuthRepositoryImpl$handleFaceHardwareError$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DeviceEntryFaceAuthRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFaceAuthRepositoryImpl$handleFaceHardwareError$1(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFaceAuthRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceEntryFaceAuthRepositoryImpl$handleFaceHardwareError$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFaceAuthRepositoryImpl$handleFaceHardwareError$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(500L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = this.this$0;
        int i2 = deviceEntryFaceAuthRepositoryImpl.retryCount;
        if (i2 < 5) {
            deviceEntryFaceAuthRepositoryImpl.faceAuthLogger.attemptingRetryAfterHardwareError(i2);
            DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl2 = this.this$0;
            FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_TRIGGERED_RETRY_AFTER_HW_UNAVAILABLE;
            StateFlowImpl stateFlowImpl = deviceEntryFaceAuthRepositoryImpl2.pendingAuthenticateRequest;
            Object value = stateFlowImpl.getValue();
            FaceAuthenticationLogger faceAuthenticationLogger = deviceEntryFaceAuthRepositoryImpl2.faceAuthLogger;
            if (value != null) {
                AuthenticationRequest authenticationRequest = (AuthenticationRequest) stateFlowImpl.getValue();
                faceAuthenticationLogger.ignoredFaceAuthTrigger(authenticationRequest != null ? authenticationRequest.uiEvent : null, "Previously queued trigger skipped due to new request");
            }
            faceAuthenticationLogger.queueingRequest(faceAuthUiEvent);
            stateFlowImpl.updateState(null, new AuthenticationRequest(faceAuthUiEvent, false));
        }
        return Unit.INSTANCE;
    }
}
