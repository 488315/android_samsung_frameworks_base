package com.android.systemui.log;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SideFpsLogger {
    public final LogBuffer buffer;

    public SideFpsLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void authDurationChanged(long j) {
        LogLevel logLevel = LogLevel.DEBUG;
        SideFpsLogger$authDurationChanged$2 sideFpsLogger$authDurationChanged$2 = new Function1() { // from class: com.android.systemui.log.SideFpsLogger$authDurationChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ValueAnimator$$ExternalSyntheticOutline0.m("SideFpsSensor auth duration changed: ", ((LogMessage) obj).getLong1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("SideFpsLogger", logLevel, sideFpsLogger$authDurationChanged$2, null);
        ((LogMessageImpl) obtain).long1 = j;
        logBuffer.commit(obtain);
    }

    public final void restToUnlockSettingEnabledChanged(boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        SideFpsLogger$restToUnlockSettingEnabledChanged$2 sideFpsLogger$restToUnlockSettingEnabledChanged$2 = new Function1() { // from class: com.android.systemui.log.SideFpsLogger$restToUnlockSettingEnabledChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("restToUnlockSettingEnabled: ", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("SideFpsLogger", logLevel, sideFpsLogger$restToUnlockSettingEnabledChanged$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void sensorLocationStateChanged(int i, int i2, int i3, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        SideFpsLogger$sensorLocationStateChanged$2 sideFpsLogger$sensorLocationStateChanged$2 = new Function1() { // from class: com.android.systemui.log.SideFpsLogger$sensorLocationStateChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str2 = logMessage.getStr2();
                boolean bool1 = logMessage.getBool1();
                StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(int1, int2, "SideFpsSensorLocation state changed: pointOnScreen: (", ", ", "), sensorLength: ");
                m.append(str2);
                m.append(", sensorVerticalInDefaultOrientation: ");
                m.append(bool1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("SideFpsLogger", logLevel, sideFpsLogger$sensorLocationStateChanged$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int2 = i2;
        logMessageImpl.str2 = String.valueOf(i3);
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }
}
