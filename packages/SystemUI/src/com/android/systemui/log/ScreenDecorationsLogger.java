package com.android.systemui.log;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TraceData$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

public final class ScreenDecorationsLogger {
    public final LogBuffer logBuffer;

    public ScreenDecorationsLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void boundingRect(RectF rectF, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$boundingRect$2 screenDecorationsLogger$boundingRect$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$boundingRect$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("Bounding rect ", logMessage.getStr1(), " : ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$boundingRect$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = rectF.toShortString();
        logBuffer.commit(obtain);
    }

    public final void cameraProtectionBoundsForScanningOverlay(Rect rect) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$cameraProtectionBoundsForScanningOverlay$2 screenDecorationsLogger$cameraProtectionBoundsForScanningOverlay$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$cameraProtectionBoundsForScanningOverlay$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Face scanning overlay present camera protection bounds: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$cameraProtectionBoundsForScanningOverlay$2, null);
        ((LogMessageImpl) obtain).str1 = rect.toShortString();
        logBuffer.commit(obtain);
    }

    public final void cameraProtectionShownOrHidden(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$cameraProtectionShownOrHidden$2 screenDecorationsLogger$cameraProtectionShownOrHidden$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$cameraProtectionShownOrHidden$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                String str2 = logMessage.getStr2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("cameraProtectionShownOrHidden showAnimationNow: ", str1, ", isFaceDetectionRunning: ", ", isBiometricPromptShowing: ", bool1);
                m.append(bool2);
                m.append(", faceAuthenticated: ");
                m.append(str2);
                m.append(", isCameraActive: ");
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool3, ", currentState: ", bool4);
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$cameraProtectionShownOrHidden$2, null);
        ((LogMessageImpl) obtain).str1 = String.valueOf(z);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z2;
        logMessageImpl.bool2 = z3;
        logMessageImpl.str2 = String.valueOf(z4);
        logMessageImpl.bool3 = z5;
        logMessageImpl.bool4 = z6;
        logBuffer.commit(obtain);
    }

    public final void dcvCameraBounds(int i, Rect rect) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$dcvCameraBounds$2 screenDecorationsLogger$dcvCameraBounds$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$dcvCameraBounds$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "DisplayCutoutView id=", " present, camera protection bounds: ", logMessage.getStr1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$dcvCameraBounds$2, null);
        ((LogMessageImpl) obtain).str1 = rect.toShortString();
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void faceSensorLocation(Point point) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$faceSensorLocation$2 screenDecorationsLogger$faceSensorLocation$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$faceSensorLocation$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return TraceData$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Reinflating view: Face sensor location: ", logMessage.getStr1(), ", faceScanningHeight: ");
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$faceSensorLocation$2, null);
        ((LogMessageImpl) obtain).int1 = point != null ? point.y * 2 : 0;
        ((LogMessageImpl) obtain).str1 = String.valueOf(point);
        logBuffer.commit(obtain);
    }

    public final void hwcLayerCameraProtectionBounds(Rect rect) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$hwcLayerCameraProtectionBounds$2 screenDecorationsLogger$hwcLayerCameraProtectionBounds$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$hwcLayerCameraProtectionBounds$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Hwc layer present camera protection bounds: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$hwcLayerCameraProtectionBounds$2, null);
        ((LogMessageImpl) obtain).str1 = rect.toShortString();
        logBuffer.commit(obtain);
    }

    public final void logDisplaySizeChanged(Point point, Point point2) {
        LogLevel logLevel = LogLevel.INFO;
        ScreenDecorationsLogger$logDisplaySizeChanged$2 screenDecorationsLogger$logDisplaySizeChanged$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$logDisplaySizeChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("Resolution changed, deferring size change to ", logMessage.getStr2(), ", staying at ", logMessage.getStr1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$logDisplaySizeChanged$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = point.flattenToString();
        logMessageImpl.str2 = point2.flattenToString();
        logBuffer.commit(obtain);
    }

    public final void logRotationChangeDeferred(int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        ScreenDecorationsLogger$logRotationChangeDeferred$2 screenDecorationsLogger$logRotationChangeDeferred$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$logRotationChangeDeferred$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(logMessage.getInt2(), logMessage.getInt2(), "Rotation changed, deferring ", ", staying at ");
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$logRotationChangeDeferred$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logRotationChanged(int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        ScreenDecorationsLogger$logRotationChanged$2 screenDecorationsLogger$logRotationChanged$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$logRotationChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Rotation changed from ", " to ");
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$logRotationChanged$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logUserSwitched(int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$logUserSwitched$2 screenDecorationsLogger$logUserSwitched$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$logUserSwitched$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "UserSwitched newUserId=", ". Updating color inversion setting");
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$logUserSwitched$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void onMeasureDimensions(int i, int i2, int i3, int i4) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$onMeasureDimensions$2 screenDecorationsLogger$onMeasureDimensions$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$onMeasureDimensions$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Face scanning animation: widthMeasureSpec: " + logMessage.getLong1() + " measuredWidth: " + logMessage.getInt1() + ", heightMeasureSpec: " + logMessage.getLong2() + " measuredHeight: " + logMessage.getInt2();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$onMeasureDimensions$2, null);
        ((LogMessageImpl) obtain).long1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.long2 = i2;
        logMessageImpl.int1 = i3;
        logMessageImpl.int2 = i4;
        logBuffer.commit(obtain);
    }
}
