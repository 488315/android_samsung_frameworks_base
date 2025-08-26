package com.android.keyguard.logging;

import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class CarrierTextManagerLogger {
    public static final Companion Companion = new Companion(null);
    public final LogBuffer buffer;
    public String location;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public CarrierTextManagerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logUpdateCarrierTextForReason(final int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        Function1 function1 = new Function1() { // from class: com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                CarrierTextManagerLogger.Companion.getClass();
                int i2 = i;
                String str = i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? "unknown" : "SATELLITE_CHANGED" : "ACTIVE_DATA_SUB_CHANGED" : "SIM_ERROR_STATE_CHANGED" : "ON_TELEPHONY_CAPABLE" : "REFRESH_CARRIER_INFO";
                String str1 = logMessage.getStr1();
                if (str1 == null) {
                    str1 = "(unknown)";
                }
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("refreshing carrier info for reason: ", str, " location=", str1);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("CarrierTextManagerLog", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str1 = this.location;
        logBuffer.commit(logMessageObtain);
    }
}
