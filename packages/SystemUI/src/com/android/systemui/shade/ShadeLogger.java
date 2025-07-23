package com.android.systemui.shade;

import android.view.MotionEvent;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeLogger {
    public final LogBuffer buffer;

    public ShadeLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void d(String str) {
        LogBuffer.log$default(this.buffer, "systemui.shade", LogLevel.DEBUG, str);
    }

    public final void logEndMotionEvent(String str, boolean z, boolean z2) {
        LogLevel logLevel = LogLevel.VERBOSE;
        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(7);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logBuffer.commit(obtain);
    }

    public final void logExpansionChanged(String str, float f, boolean z, boolean z2, final float f2) {
        LogLevel logLevel = LogLevel.VERBOSE;
        Function1 function1 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                double double1 = logMessage.getDouble1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder sb = new StringBuilder();
                sb.append(str1);
                sb.append(" fraction=");
                sb.append(double1);
                sb.append(",expanded=");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, bool1, ",tracking=", bool2, ",dragDownPxAmount=");
                sb.append(f2);
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
        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(17);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
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
        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(15);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.long1 = motionEvent.getEventTime();
        logMessageImpl.long2 = motionEvent.getDownTime();
        logMessageImpl.int1 = motionEvent.getAction();
        logMessageImpl.int2 = i;
        logMessageImpl.double1 = motionEvent.getY();
        logBuffer.commit(obtain);
    }

    public final void logPanelClosedOnDown(String str, boolean z, float f) {
        LogLevel logLevel = LogLevel.VERBOSE;
        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logMessageImpl.double1 = f;
        logBuffer.commit(obtain);
    }

    public final void v(String str) {
        LogBuffer.log$default(this.buffer, "systemui.shade", LogLevel.VERBOSE, str);
    }
}
