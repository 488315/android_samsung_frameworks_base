package com.android.systemui.log;

import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0662xaf167275;
import com.android.keyguard.FaceAuthUiEvent;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.ErrorAuthenticationStatus;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceAuthenticationLogger {
    public final LogBuffer logBuffer;

    public FaceAuthenticationLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void attemptingRetryAfterHardwareError(int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$attemptingRetryAfterHardwareError$2 faceAuthenticationLogger$attemptingRetryAfterHardwareError$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$attemptingRetryAfterHardwareError$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractC0000x2c234b15.m0m("Attempting face auth again because of HW error: retry attempt ", ((LogMessage) obj).getInt1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$attemptingRetryAfterHardwareError$2, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void authenticating(FaceAuthUiEvent faceAuthUiEvent) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$authenticating$2 faceAuthenticationLogger$authenticating$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$authenticating$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Running authenticate for ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$authenticating$2, null);
        obtain.setStr1(faceAuthUiEvent.getReason());
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
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(AbstractC0662xaf167275.m61m("Received authentication error: errorCode: ", int1, ", errString: ", str1, ", isLockoutError: "), logMessage.getBool1(), ", isCancellationError: ", logMessage.getBool2());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$authenticationError$2, null);
        obtain.setInt1(i);
        obtain.setStr1(String.valueOf(charSequence));
        obtain.setBool1(z);
        obtain.setBool2(z2);
        logBuffer.commit(obtain);
    }

    public final void cancelSignalNotReceived(boolean z, boolean z2, boolean z3, FaceAuthUiEvent faceAuthUiEvent) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$cancelSignalNotReceived$2 faceAuthenticationLogger$cancelSignalNotReceived$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$cancelSignalNotReceived$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                String str1 = logMessage.getStr1();
                StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("Cancel signal was not received, running timeout handler to reset state. State before reset: isAuthRunning: ", bool1, ", isLockedOut: ", bool2, ", cancellationInProgress: ");
                m69m.append(bool3);
                m69m.append(", faceAuthRequestedWhileCancellation: ");
                m69m.append(str1);
                return m69m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$cancelSignalNotReceived$2, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        obtain.setStr1(String.valueOf(faceAuthUiEvent != null ? faceAuthUiEvent.getReason() : null));
        logBuffer.commit(obtain);
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
                StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("skipping detection request because it is not supported, faceManager isNull: ", bool1, ", sensorPropertiesInternal isNullOrEmpty: ", bool2, ", supportsFaceDetection: ");
                m69m.append(bool3);
                return m69m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$detectionNotSupported$2, null);
        boolean z = true;
        boolean z2 = false;
        obtain.setBool1(faceManager == null);
        if (list != null && !list.isEmpty()) {
            z = false;
        }
        obtain.setBool2(z);
        if (list != null && (faceSensorPropertiesInternal = (FaceSensorPropertiesInternal) CollectionsKt___CollectionsKt.firstOrNull(list)) != null) {
            z2 = faceSensorPropertiesInternal.supportsFaceDetection;
        }
        obtain.setBool2(z2);
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
        obtain.setInt1(authenticationResult.getUserId());
        obtain.setBool1(authenticationResult.isStrongBiometric());
        logBuffer.commit(obtain);
    }

    public final void hardwareError(ErrorAuthenticationStatus errorAuthenticationStatus) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$hardwareError$2 faceAuthenticationLogger$hardwareError$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$hardwareError$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Received face hardware error: " + logMessage.getStr1() + " , code: " + logMessage.getInt1();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$hardwareError$2, null);
        obtain.setStr1(String.valueOf(errorAuthenticationStatus.msg));
        obtain.setInt1(errorAuthenticationStatus.msgId);
        logBuffer.commit(obtain);
    }

    public final void ignoredFaceAuthTrigger(FaceAuthUiEvent faceAuthUiEvent, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$ignoredFaceAuthTrigger$2 faceAuthenticationLogger$ignoredFaceAuthTrigger$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$ignoredFaceAuthTrigger$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m32m("Ignoring trigger because ", logMessage.getStr2(), ", Trigger reason: ", logMessage.getStr1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$ignoredFaceAuthTrigger$2, null);
        obtain.setStr1(faceAuthUiEvent.getReason());
        obtain.setStr2(str);
        logBuffer.commit(obtain);
    }

    public final void launchingQueuedFaceAuthRequest(FaceAuthUiEvent faceAuthUiEvent) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$launchingQueuedFaceAuthRequest$2 faceAuthenticationLogger$launchingQueuedFaceAuthRequest$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$launchingQueuedFaceAuthRequest$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Received cancellation error and starting queued face auth request: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$launchingQueuedFaceAuthRequest$2, null);
        obtain.setStr1(String.valueOf(faceAuthUiEvent.getReason()));
        logBuffer.commit(obtain);
    }

    public final void queuingRequestWhileCancelling(FaceAuthUiEvent faceAuthUiEvent, FaceAuthUiEvent faceAuthUiEvent2) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$queuingRequestWhileCancelling$2 faceAuthenticationLogger$queuingRequestWhileCancelling$2 = new Function1() { // from class: com.android.systemui.log.FaceAuthenticationLogger$queuingRequestWhileCancelling$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m32m("Face auth requested while previous request is being cancelled, already queued request: ", logMessage.getStr1(), " queueing the new request: ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$queuingRequestWhileCancelling$2, null);
        obtain.setStr1(faceAuthUiEvent != null ? faceAuthUiEvent.getReason() : null);
        obtain.setStr2(faceAuthUiEvent2.getReason());
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
        obtain.setBool1(z);
        obtain.setBool2(z2);
        logBuffer.commit(obtain);
    }
}
