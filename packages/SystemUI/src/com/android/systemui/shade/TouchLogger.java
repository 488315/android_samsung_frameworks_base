package com.android.systemui.shade;

import android.view.MotionEvent;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.sec.ims.settings.ImsProfile;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TouchLogger {
    public static final Companion Companion = new Companion(null);
    public static DispatchTouchLogger touchLogger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static void logDispatchTouch(MotionEvent motionEvent, String str, boolean z) {
            final DispatchTouchLogger dispatchTouchLogger = TouchLogger.touchLogger;
            if (dispatchTouchLogger != null) {
                LogLevel logLevel = LogLevel.DEBUG;
                Function1 function1 = new Function1() { // from class: com.android.systemui.shade.DispatchTouchLogger$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        String str1 = logMessage.getStr1();
                        int int1 = logMessage.getInt1();
                        DispatchTouchLogger.this.getClass();
                        String str2 = int1 != 0 ? int1 != 1 ? int1 != 2 ? int1 != 3 ? int1 != 5 ? int1 != 6 ? "OTHER" : "POINTER_UP" : "POINTER_DOWN" : "CANCEL" : "MOVE" : ImsProfile.RCS_PROFILE_UP : "DOWN";
                        long long1 = logMessage.getLong1();
                        boolean bool1 = logMessage.getBool1();
                        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Touch: view=", str1, ", type=", str2, ", downtime=");
                        m.append(long1);
                        m.append(", result=");
                        m.append(bool1);
                        return m.toString();
                    }
                };
                LogBuffer logBuffer = dispatchTouchLogger.buffer;
                LogMessage obtain = logBuffer.obtain("systemui.shade.touch", logLevel, function1, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = str;
                logMessageImpl.int1 = motionEvent.getAction();
                logMessageImpl.long1 = motionEvent.getDownTime();
                logMessageImpl.bool1 = z;
                logBuffer.commit(obtain);
            }
        }

        private Companion() {
        }
    }
}
