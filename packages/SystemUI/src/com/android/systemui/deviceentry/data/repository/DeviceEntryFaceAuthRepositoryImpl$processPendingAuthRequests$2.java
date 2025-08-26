package com.android.systemui.deviceentry.data.repository;

import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.log.FaceAuthenticationLogger;
import com.android.systemui.log.FaceAuthenticationLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
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
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object objWithContext;
        FaceSensorPropertiesInternal faceSensorPropertiesInternal;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AuthenticationRequest authenticationRequest = (AuthenticationRequest) this.L$0;
            if (authenticationRequest != null) {
                DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = this.this$0;
                FaceAuthenticationLogger faceAuthenticationLogger = deviceEntryFaceAuthRepositoryImpl.faceAuthLogger;
                faceAuthenticationLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                FaceAuthenticationLogger$$ExternalSyntheticLambda0 faceAuthenticationLogger$$ExternalSyntheticLambda0 = new FaceAuthenticationLogger$$ExternalSyntheticLambda0(13);
                LogBuffer logBuffer = faceAuthenticationLogger.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$$ExternalSyntheticLambda0, null);
                FaceAuthUiEvent faceAuthUiEvent = authenticationRequest.uiEvent;
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = String.valueOf(faceAuthUiEvent != null ? faceAuthUiEvent.getReason() : null);
                boolean z = authenticationRequest.fallbackToDetection;
                logMessageImpl.bool1 = z;
                logBuffer.commit(logMessageObtain);
                DeviceEntryFaceAuthRepositoryImpl.access$clearPendingAuthRequest(deviceEntryFaceAuthRepositoryImpl, "Authenticate was invoked");
                this.label = 1;
                StateFlowImpl stateFlowImpl = deviceEntryFaceAuthRepositoryImpl._isAuthRunning;
                boolean zBooleanValue = ((Boolean) stateFlowImpl.getValue()).booleanValue();
                FaceAuthenticationLogger faceAuthenticationLogger2 = deviceEntryFaceAuthRepositoryImpl.faceAuthLogger;
                if (zBooleanValue) {
                    faceAuthenticationLogger2.ignoredFaceAuthTrigger(faceAuthUiEvent, "face auth is currently running");
                    objWithContext = Unit.INSTANCE;
                } else if (((Boolean) deviceEntryFaceAuthRepositoryImpl.cancellationInProgress.getValue()).booleanValue()) {
                    faceAuthenticationLogger2.ignoredFaceAuthTrigger(faceAuthUiEvent, "cancellation in progress");
                    objWithContext = Unit.INSTANCE;
                } else {
                    boolean zBooleanValue2 = ((Boolean) deviceEntryFaceAuthRepositoryImpl.canRunFaceAuth.$$delegate_0.getValue()).booleanValue();
                    CoroutineDispatcher coroutineDispatcher = deviceEntryFaceAuthRepositoryImpl.mainDispatcher;
                    if (zBooleanValue2) {
                        objWithContext = BuildersKt.withContext(coroutineDispatcher, new DeviceEntryFaceAuthRepositoryImpl$authenticate$2(deviceEntryFaceAuthRepositoryImpl, faceAuthUiEvent, null), this);
                    } else {
                        if (!((Boolean) deviceEntryFaceAuthRepositoryImpl.canRunDetection.$$delegate_0.getValue()).booleanValue()) {
                            faceAuthenticationLogger2.ignoredFaceAuthTrigger(faceAuthUiEvent, "face auth & detect gating check is false");
                        } else if (z) {
                            faceAuthenticationLogger2.ignoredFaceAuthTrigger(faceAuthUiEvent, "face auth gating check is false, falling back to detection.");
                            LogBuffer logBuffer2 = faceAuthenticationLogger2.logBuffer;
                            boolean z2 = false;
                            if (!deviceEntryFaceAuthRepositoryImpl.isDetectionSupported) {
                                FaceManager faceManager = deviceEntryFaceAuthRepositoryImpl.faceManager;
                                List sensorPropertiesInternal = faceManager != null ? faceManager.getSensorPropertiesInternal() : null;
                                LogMessage logMessageObtain2 = logBuffer2.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, new FaceAuthenticationLogger$$ExternalSyntheticLambda0(0), null);
                                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                                logMessageImpl2.bool1 = faceManager == null;
                                List list = sensorPropertiesInternal;
                                if (list != null && !list.isEmpty()) {
                                    z = false;
                                }
                                logMessageImpl2.bool2 = z;
                                if (sensorPropertiesInternal != null && (faceSensorPropertiesInternal = (FaceSensorPropertiesInternal) CollectionsKt___CollectionsKt.firstOrNull(sensorPropertiesInternal)) != null) {
                                    z2 = faceSensorPropertiesInternal.supportsFaceDetection;
                                }
                                logMessageImpl2.bool2 = z2;
                                logBuffer2.commit(logMessageObtain2);
                                objWithContext = Unit.INSTANCE;
                            } else if (((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                                boolean zBooleanValue3 = ((Boolean) stateFlowImpl.getValue()).booleanValue();
                                z = deviceEntryFaceAuthRepositoryImpl.detectCancellationSignal != null;
                                LogMessage logMessageObtain3 = logBuffer2.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, new FaceAuthenticationLogger$$ExternalSyntheticLambda0(5), null);
                                LogMessageImpl logMessageImpl3 = (LogMessageImpl) logMessageObtain3;
                                logMessageImpl3.bool1 = zBooleanValue3;
                                logMessageImpl3.bool2 = z;
                                logBuffer2.commit(logMessageObtain3);
                                objWithContext = Unit.INSTANCE;
                            } else {
                                objWithContext = BuildersKt.withContext(coroutineDispatcher, new DeviceEntryFaceAuthRepositoryImpl$detect$2(deviceEntryFaceAuthRepositoryImpl, faceAuthUiEvent, null), this);
                            }
                            if (objWithContext != obj2) {
                                objWithContext = Unit.INSTANCE;
                            }
                        } else {
                            faceAuthenticationLogger2.ignoredFaceAuthTrigger(faceAuthUiEvent, "face auth gating check is false and fallback to detection is not requested");
                        }
                        objWithContext = Unit.INSTANCE;
                    }
                }
                if (objWithContext == obj2) {
                    return obj2;
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
