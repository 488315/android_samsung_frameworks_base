package com.android.systemui.log;

import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TraceData$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.deviceentry.shared.model.ErrorFaceAuthenticationStatus;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FaceAuthenticationLogger {
    public final LogBuffer logBuffer;

    public FaceAuthenticationLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void addLockoutResetCallbackDone() {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$addLockoutResetCallbackDone$2 faceAuthenticationLogger$addLockoutResetCallbackDone$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$addLockoutResetCallbackDone$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "addlockoutResetCallback done";
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        logBuffer.commit(logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$addLockoutResetCallbackDone$2, null));
    }

    public final void attemptingRetryAfterHardwareError(int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$attemptingRetryAfterHardwareError$2 faceAuthenticationLogger$attemptingRetryAfterHardwareError$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$attemptingRetryAfterHardwareError$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Attempting face auth again because of HW error: retry attempt ");
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$attemptingRetryAfterHardwareError$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void authenticating(FaceAuthUiEvent faceAuthUiEvent) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$authenticating$2 faceAuthenticationLogger$authenticating$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$authenticating$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Running authenticate for ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$authenticating$2, null);
        ((LogMessageImpl) obtain).str1 = faceAuthUiEvent.getReason();
        logBuffer.commit(obtain);
    }

    public final void authenticationError(int i, CharSequence charSequence, boolean z, boolean z2) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$authenticationError$2 faceAuthenticationLogger$authenticationError$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$authenticationError$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "Received authentication error: errorCode: ", ", errString: ", str1, ", isLockoutError: "), logMessage.getBool1(), ", isCancellationError: ", logMessage.getBool2());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$authenticationError$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        String valueOf = String.valueOf(charSequence);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = valueOf;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logBuffer.commit(obtain);
    }

    public final void canFaceAuthRunChanged(boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$canFaceAuthRunChanged$2 faceAuthenticationLogger$canFaceAuthRunChanged$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$canFaceAuthRunChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("canFaceAuthRun value changed to ", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$canFaceAuthRunChanged$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void cancelSignalNotReceived(FaceAuthUiEvent faceAuthUiEvent, boolean z, boolean z2, boolean z3) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$cancelSignalNotReceived$2 faceAuthenticationLogger$cancelSignalNotReceived$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$cancelSignalNotReceived$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                String str1 = logMessage.getStr1();
                StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("Cancel signal was not received, running timeout handler to reset state. State before reset: isAuthRunning: ", ", isLockedOut: ", ", cancellationInProgress: ", bool1, bool2);
                m.append(bool3);
                m.append(", faceAuthRequestedWhileCancellation: ");
                m.append(str1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$cancelSignalNotReceived$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool3 = z3;
        logMessageImpl.str1 = String.valueOf(faceAuthUiEvent != null ? faceAuthUiEvent.getReason() : null);
        logBuffer.commit(obtain);
    }

    public final void clearingPendingAuthRequest(String str, FaceAuthUiEvent faceAuthUiEvent, Boolean bool) {
        if (faceAuthUiEvent != null) {
            LogLevel logLevel = LogLevel.DEBUG;
            FaceAuthenticationLogger$clearingPendingAuthRequest$1$2 faceAuthenticationLogger$clearingPendingAuthRequest$1$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$clearingPendingAuthRequest$1$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    String str1 = logMessage.getStr1();
                    String str2 = logMessage.getStr2();
                    String str3 = logMessage.getStr3();
                    StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Clearing pending auth: ", str1, ", fallbackToDetection: ", str2, ", reason: ");
                    m.append(str3);
                    return m.toString();
                }
            };
            LogBuffer logBuffer = this.logBuffer;
            LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$clearingPendingAuthRequest$1$2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = faceAuthUiEvent.getReason();
            logMessageImpl.str2 = String.valueOf(bool);
            logMessageImpl.str3 = str;
            logBuffer.commit(obtain);
        }
    }

    public final void detectionNotSupported(FaceManager faceManager, List list) {
        FaceSensorPropertiesInternal faceSensorPropertiesInternal;
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$detectionNotSupported$2 faceAuthenticationLogger$detectionNotSupported$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$detectionNotSupported$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("skipping detection request because it is not supported, faceManager isNull: ", ", sensorPropertiesInternal isNullOrEmpty: ", ", supportsFaceDetection: ", bool1, bool2);
                m.append(bool3);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$detectionNotSupported$2, null);
        boolean z = true;
        boolean z2 = false;
        ((LogMessageImpl) obtain).bool1 = faceManager == null;
        List list2 = list;
        if (list2 != null && !list2.isEmpty()) {
            z = false;
        }
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool2 = z;
        if (list != null && (faceSensorPropertiesInternal = (FaceSensorPropertiesInternal) CollectionsKt___CollectionsKt.firstOrNull(list)) != null) {
            z2 = faceSensorPropertiesInternal.supportsFaceDetection;
        }
        logMessageImpl.bool2 = z2;
        logBuffer.commit(obtain);
    }

    public final void faceAuthSuccess(FaceManager.AuthenticationResult authenticationResult) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$faceAuthSuccess$2 faceAuthenticationLogger$faceAuthSuccess$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$faceAuthSuccess$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Face authenticated successfully: userId: " + logMessage.getInt1() + ", isStrongBiometric: " + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$faceAuthSuccess$2, null);
        ((LogMessageImpl) obtain).int1 = authenticationResult.getUserId();
        ((LogMessageImpl) obtain).bool1 = authenticationResult.isStrongBiometric();
        logBuffer.commit(obtain);
    }

    public final void hardwareError(ErrorFaceAuthenticationStatus errorFaceAuthenticationStatus) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$hardwareError$2 faceAuthenticationLogger$hardwareError$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$hardwareError$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return TraceData$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Received face hardware error: ", logMessage.getStr1(), " , code: ");
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$hardwareError$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = String.valueOf(errorFaceAuthenticationStatus.msg);
        logMessageImpl.int1 = errorFaceAuthenticationStatus.msgId;
        logBuffer.commit(obtain);
    }

    public final void ignoredFaceAuthTrigger(FaceAuthUiEvent faceAuthUiEvent, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$ignoredFaceAuthTrigger$2 faceAuthenticationLogger$ignoredFaceAuthTrigger$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$ignoredFaceAuthTrigger$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("Ignoring trigger because ", logMessage.getStr2(), ", Trigger reason: ", logMessage.getStr1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$ignoredFaceAuthTrigger$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = String.valueOf(faceAuthUiEvent != null ? faceAuthUiEvent.getReason() : null);
        logMessageImpl.str2 = str;
        logBuffer.commit(obtain);
    }

    public final void notProcessingRequestYet(FaceAuthUiEvent faceAuthUiEvent, boolean z, boolean z2, boolean z3) {
        if (faceAuthUiEvent != null) {
            LogLevel logLevel = LogLevel.DEBUG;
            FaceAuthenticationLogger$notProcessingRequestYet$1$2 faceAuthenticationLogger$notProcessingRequestYet$1$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$notProcessingRequestYet$1$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    String str1 = logMessage.getStr1();
                    boolean bool1 = logMessage.getBool1();
                    return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("Waiting to process request: reason: ", str1, ", canRunAuth: ", ", canRunDetect: ", bool1), logMessage.getBool2(), ", cancelInProgress: ", logMessage.getBool3());
                }
            };
            LogBuffer logBuffer = this.logBuffer;
            LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$notProcessingRequestYet$1$2, null);
            ((LogMessageImpl) obtain).str1 = faceAuthUiEvent.getReason();
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.bool1 = z;
            logMessageImpl.bool2 = z2;
            logMessageImpl.bool3 = z3;
            logBuffer.commit(obtain);
        }
    }

    public final void processingRequest(FaceAuthUiEvent faceAuthUiEvent, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$processingRequest$2 faceAuthenticationLogger$processingRequest$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$processingRequest$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Processing face auth request: " + logMessage.getStr1() + ", fallbackToDetect: " + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$processingRequest$2, null);
        ((LogMessageImpl) obtain).str1 = String.valueOf(faceAuthUiEvent != null ? faceAuthUiEvent.getReason() : null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void queueingRequest(FaceAuthUiEvent faceAuthUiEvent) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$queueingRequest$2 faceAuthenticationLogger$queueingRequest$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$queueingRequest$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Queueing " + logMessage.getStr1() + " request for face auth, fallbackToDetection: " + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$queueingRequest$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = String.valueOf(faceAuthUiEvent);
        logMessageImpl.bool1 = false;
        logBuffer.commit(obtain);
    }

    public final void skippingDetection(boolean z, boolean z2) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$skippingDetection$2 faceAuthenticationLogger$skippingDetection$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$skippingDetection$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Skipping running detection: isAuthRunning: " + logMessage.getBool1() + ", detectCancellationNotNull: " + logMessage.getBool2();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$skippingDetection$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        ((LogMessageImpl) obtain).bool2 = z2;
        logBuffer.commit(obtain);
    }
}
