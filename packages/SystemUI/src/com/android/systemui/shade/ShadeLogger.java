package com.android.systemui.shade;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.MotionEvent;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

public final class ShadeLogger {
    public final LogBuffer buffer;

    public ShadeLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void d(String str) {
        this.buffer.log("systemui.shade", LogLevel.DEBUG, str, null);
    }

    public final void logEndMotionEvent(String str, boolean z, boolean z2) {
        LogLevel logLevel = LogLevel.VERBOSE;
        ShadeLogger$logEndMotionEvent$2 shadeLogger$logEndMotionEvent$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logEndMotionEvent$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return logMessage.getStr1() + "; force=" + logMessage.getBool1() + "; expand=" + logMessage.getBool2();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logEndMotionEvent$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logBuffer.commit(obtain);
    }

    public final void logExpansionChanged(String str, float f, boolean z, boolean z2, final float f2) {
        LogLevel logLevel = LogLevel.VERBOSE;
        Function1 function1 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logExpansionChanged$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                double double1 = logMessage.getDouble1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                float f3 = f2;
                StringBuilder sb = new StringBuilder();
                sb.append(str1);
                sb.append(" fraction=");
                sb.append(double1);
                sb.append(",expanded=");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, bool1, ",tracking=", bool2, ",dragDownPxAmount=");
                sb.append(f3);
                return sb.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.double1 = f;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.long1 = (long) f2;
        logBuffer.commit(obtain);
    }

    public final void logMotionEvent(MotionEvent motionEvent, String str) {
        LogLevel logLevel = LogLevel.VERBOSE;
        ShadeLogger$logMotionEvent$2 shadeLogger$logMotionEvent$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logMotionEvent$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return logMessage.getStr1() + ": eventTime=" + logMessage.getLong1() + ",downTime=" + logMessage.getLong2() + ",action=" + logMessage.getInt1() + ",class=" + logMessage.getInt2();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logMotionEvent$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.long1 = motionEvent.getEventTime();
        logMessageImpl.long2 = motionEvent.getDownTime();
        logMessageImpl.int1 = motionEvent.getAction();
        logMessageImpl.int2 = motionEvent.getClassification();
        logBuffer.commit(obtain);
    }

    public final void logMotionEventStatusBarState(MotionEvent motionEvent, int i, String str) {
        LogLevel logLevel = LogLevel.VERBOSE;
        ShadeLogger$logMotionEventStatusBarState$2 shadeLogger$logMotionEventStatusBarState$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logMotionEventStatusBarState$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                long long1 = logMessage.getLong1();
                long long2 = logMessage.getLong2();
                double double1 = logMessage.getDouble1();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                return str1 + "\neventTime=" + long1 + ",downTime=" + long2 + ",y=" + double1 + ",action=" + int1 + ",statusBarState=" + (int2 != 0 ? int2 != 1 ? int2 != 2 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt2(), "UNKNOWN:") : "SHADE_LOCKED" : "KEYGUARD" : "SHADE");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logMotionEventStatusBarState$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.long1 = motionEvent.getEventTime();
        logMessageImpl.long2 = motionEvent.getDownTime();
        logMessageImpl.int1 = motionEvent.getAction();
        logMessageImpl.int2 = i;
        logMessageImpl.double1 = motionEvent.getY();
        logBuffer.commit(obtain);
    }

    public final void logUpdateNotificationPanelTouchState(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        LogLevel logLevel = LogLevel.VERBOSE;
        ShadeLogger$logUpdateNotificationPanelTouchState$2 shadeLogger$logUpdateNotificationPanelTouchState$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logUpdateNotificationPanelTouchState$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                String str1 = logMessage.getStr1();
                StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("CentralSurfaces updateNotificationPanelTouchState set disabled to: ", "\nisGoingToSleep: ", ", !shouldControlScreenOff: ", bool1, bool2);
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool3, ",!mDeviceInteractive: ", bool4, ", !isPulsing: ");
                m.append(str1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logUpdateNotificationPanelTouchState$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool3 = z3;
        logMessageImpl.bool4 = z4;
        logMessageImpl.str1 = String.valueOf(z5);
        logBuffer.commit(obtain);
    }

    public final void v(String str) {
        this.buffer.log("systemui.shade", LogLevel.VERBOSE, str, null);
    }
}
