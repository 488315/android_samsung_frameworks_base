package com.android.systemui.deviceentry.data.repository;

import android.hardware.face.FaceManager;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.log.FaceAuthenticationLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceEntryFaceAuthRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$2(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFaceAuthRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$2 deviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$2 = new DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$2(this.this$0, continuation);
        deviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$2.L$0 = obj;
        return deviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFaceAuthRepositoryImpl$processPendingAuthRequests$2) create((AuthenticationRequest) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        Object obj3 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AuthenticationRequest authenticationRequest = (AuthenticationRequest) this.L$0;
            if (authenticationRequest != null) {
                DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = this.this$0;
                FaceAuthenticationLogger faceAuthenticationLogger = deviceEntryFaceAuthRepositoryImpl.faceAuthLogger;
                FaceAuthUiEvent faceAuthUiEvent = authenticationRequest.uiEvent;
                boolean z = authenticationRequest.fallbackToDetection;
                faceAuthenticationLogger.processingRequest(faceAuthUiEvent, z);
                DeviceEntryFaceAuthRepositoryImpl.access$clearPendingAuthRequest(deviceEntryFaceAuthRepositoryImpl, "Authenticate was invoked");
                this.label = 1;
                StateFlowImpl stateFlowImpl = deviceEntryFaceAuthRepositoryImpl._isAuthRunning;
                boolean booleanValue = ((Boolean) stateFlowImpl.getValue()).booleanValue();
                FaceAuthenticationLogger faceAuthenticationLogger2 = deviceEntryFaceAuthRepositoryImpl.faceAuthLogger;
                if (booleanValue) {
                    faceAuthenticationLogger2.ignoredFaceAuthTrigger(faceAuthUiEvent, "face auth is currently running");
                    obj2 = Unit.INSTANCE;
                } else if (((Boolean) deviceEntryFaceAuthRepositoryImpl.cancellationInProgress.getValue()).booleanValue()) {
                    faceAuthenticationLogger2.ignoredFaceAuthTrigger(faceAuthUiEvent, "cancellation in progress");
                    obj2 = Unit.INSTANCE;
                } else {
                    boolean booleanValue2 = ((Boolean) deviceEntryFaceAuthRepositoryImpl.canRunFaceAuth.$$delegate_0.getValue()).booleanValue();
                    CoroutineDispatcher coroutineDispatcher = deviceEntryFaceAuthRepositoryImpl.mainDispatcher;
                    if (booleanValue2) {
                        obj2 = BuildersKt.withContext(coroutineDispatcher, new DeviceEntryFaceAuthRepositoryImpl$authenticate$2(deviceEntryFaceAuthRepositoryImpl, faceAuthUiEvent, null), this);
                    } else {
                        if (!((Boolean) deviceEntryFaceAuthRepositoryImpl.canRunDetection.$$delegate_0.getValue()).booleanValue()) {
                            faceAuthenticationLogger2.ignoredFaceAuthTrigger(faceAuthUiEvent, "face auth & detect gating check is false");
                        } else if (z) {
                            faceAuthenticationLogger2.ignoredFaceAuthTrigger(faceAuthUiEvent, "face auth gating check is false, falling back to detection.");
                            if (!deviceEntryFaceAuthRepositoryImpl.isDetectionSupported) {
                                FaceManager faceManager = deviceEntryFaceAuthRepositoryImpl.faceManager;
                                faceAuthenticationLogger2.detectionNotSupported(faceManager, faceManager != null ? faceManager.getSensorPropertiesInternal() : null);
                                obj2 = Unit.INSTANCE;
                            } else if (((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                                faceAuthenticationLogger2.skippingDetection(((Boolean) stateFlowImpl.getValue()).booleanValue(), deviceEntryFaceAuthRepositoryImpl.detectCancellationSignal != null);
                                obj2 = Unit.INSTANCE;
                            } else {
                                obj2 = BuildersKt.withContext(coroutineDispatcher, new DeviceEntryFaceAuthRepositoryImpl$detect$2(deviceEntryFaceAuthRepositoryImpl, faceAuthUiEvent, null), this);
                            }
                            if (obj2 != obj3) {
                                obj2 = Unit.INSTANCE;
                            }
                        } else {
                            faceAuthenticationLogger2.ignoredFaceAuthTrigger(faceAuthUiEvent, "face auth gating check is false and fallback to detection is not requested");
                        }
                        obj2 = Unit.INSTANCE;
                    }
                }
                if (obj2 == obj3) {
                    return obj3;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
