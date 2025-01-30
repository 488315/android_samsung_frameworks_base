package com.android.systemui.log;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
                return FontProvider$$ExternalSyntheticOutline0.m32m("Bounding rect ", logMessage.getStr1(), " : ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$boundingRect$2, null);
        obtain.setStr1(str);
        obtain.setStr2(rectF.toShortString());
        logBuffer.commit(obtain);
    }

    public final void cameraProtectionBoundsForScanningOverlay(Rect rect) {
        LogLevel logLevel = LogLevel.DEBUG;
        C1769x9884f8f4 c1769x9884f8f4 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$cameraProtectionBoundsForScanningOverlay$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Face scanning overlay present camera protection bounds: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, c1769x9884f8f4, null);
        obtain.setStr1(rect.toShortString());
        logBuffer.commit(obtain);
    }

    public final void cameraProtectionShownOrHidden(boolean z, boolean z2, boolean z3, boolean z4) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$cameraProtectionShownOrHidden$2 screenDecorationsLogger$cameraProtectionShownOrHidden$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$cameraProtectionShownOrHidden$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("isFaceDetectionRunning: ", bool1, ", isBiometricPromptShowing: ", bool2, ", requestedState: "), logMessage.getBool3(), ", currentState: ", logMessage.getBool4());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$cameraProtectionShownOrHidden$2, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        obtain.setBool4(z4);
        logBuffer.commit(obtain);
    }

    public final void dcvCameraBounds(int i, Rect rect) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$dcvCameraBounds$2 screenDecorationsLogger$dcvCameraBounds$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$dcvCameraBounds$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "DisplayCutoutView id=" + logMessage.getInt1() + " present, camera protection bounds: " + logMessage.getStr1();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$dcvCameraBounds$2, null);
        obtain.setStr1(rect.toShortString());
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void faceSensorLocation(Point point) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$faceSensorLocation$2 screenDecorationsLogger$faceSensorLocation$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$faceSensorLocation$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Reinflating view: Face sensor location: " + logMessage.getStr1() + ", faceScanningHeight: " + logMessage.getInt1();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$faceSensorLocation$2, null);
        obtain.setInt1(point != null ? point.y * 2 : 0);
        obtain.setStr1(String.valueOf(point));
        logBuffer.commit(obtain);
    }

    public final void hwcLayerCameraProtectionBounds(Rect rect) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$hwcLayerCameraProtectionBounds$2 screenDecorationsLogger$hwcLayerCameraProtectionBounds$2 = new Function1() { // from class: com.android.systemui.log.ScreenDecorationsLogger$hwcLayerCameraProtectionBounds$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Hwc layer present camera protection bounds: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$hwcLayerCameraProtectionBounds$2, null);
        obtain.setStr1(rect.toShortString());
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
        obtain.setLong1(i);
        obtain.setLong2(i2);
        obtain.setInt1(i3);
        obtain.setInt2(i4);
        logBuffer.commit(obtain);
    }
}
