package com.android.keyguard.logging;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextManager;
import com.android.keyguard.logging.CarrierTextManagerLogger;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CarrierTextManagerLogger {
    public static final Companion Companion = new Companion(null);
    public final LogBuffer buffer;
    public String location;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public CarrierTextManagerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logCallbackSentFromUpdate(CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo) {
        LogLevel logLevel = LogLevel.VERBOSE;
        CarrierTextManagerLogger$logCallbackSentFromUpdate$2 carrierTextManagerLogger$logCallbackSentFromUpdate$2 = new Function1() { // from class: com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder sb = new StringBuilder("┗ updateCarrierText: result=(carrierText=");
                sb.append(str1);
                sb.append(", anySimReady=");
                sb.append(bool1);
                sb.append(", airplaneMode=");
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, bool2, ")");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("CarrierTextManagerLog", logLevel, carrierTextManagerLogger$logCallbackSentFromUpdate$2, null);
        obtain.setStr1(String.valueOf(carrierTextCallbackInfo.carrierText));
        obtain.setBool1(carrierTextCallbackInfo.anySimReady);
        obtain.setBool2(carrierTextCallbackInfo.airplaneMode);
        logBuffer.commit(obtain);
    }

    public final void logUpdate(int i) {
        LogLevel logLevel = LogLevel.VERBOSE;
        Function1 function1 = new Function1() { // from class: com.android.keyguard.logging.CarrierTextManagerLogger$logUpdate$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str = CarrierTextManagerLogger.this.location;
                if (str == null) {
                    str = "(unknown)";
                }
                return "updateCarrierText: location=" + str + " numSubs=" + logMessage.getInt1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("CarrierTextManagerLog", logLevel, function1, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logUpdateCarrierTextForReason(final int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        Function1 function1 = new Function1() { // from class: com.android.keyguard.logging.CarrierTextManagerLogger$logUpdateCarrierTextForReason$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CarrierTextManagerLogger.Companion companion = CarrierTextManagerLogger.Companion;
                int i2 = i;
                companion.getClass();
                String str = i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? "unknown" : "ACTIVE_DATA_SUB_CHANGED" : "SIM_STATE_CHANGED" : "ON_TELEPHONY_CAPABLE" : "REFRESH_CARRIER_INFO";
                String str2 = this.location;
                if (str2 == null) {
                    str2 = "(unknown)";
                }
                return FontProvider$$ExternalSyntheticOutline0.m32m("refreshing carrier info for reason: ", str, " location=", str2);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("CarrierTextManagerLog", logLevel, function1, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logUpdateFromStickyBroadcast(String str, String str2) {
        LogLevel logLevel = LogLevel.VERBOSE;
        CarrierTextManagerLogger$logUpdateFromStickyBroadcast$2 carrierTextManagerLogger$logUpdateFromStickyBroadcast$2 = new Function1() { // from class: com.android.keyguard.logging.CarrierTextManagerLogger$logUpdateFromStickyBroadcast$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m32m("┣ updateCarrierText: getting PLMN/SPN sticky brdcst. slotId=", logMessage.getStr1(), ", spn=", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("CarrierTextManagerLog", logLevel, carrierTextManagerLogger$logUpdateFromStickyBroadcast$2, null);
        CarrierTextManagerLogger$$ExternalSyntheticOutline0.m83m(obtain, str, str2, logBuffer, obtain);
    }

    public final void logUpdateLoopStart(int i, int i2, String str) {
        LogLevel logLevel = LogLevel.VERBOSE;
        CarrierTextManagerLogger$logUpdateLoopStart$2 carrierTextManagerLogger$logUpdateLoopStart$2 = new Function1() { // from class: com.android.keyguard.logging.CarrierTextManagerLogger$logUpdateLoopStart$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str1 = logMessage.getStr1();
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("┣ updateCarrierText: updating sub=", int1, " simState=", int2, " carrierName=");
                m45m.append(str1);
                return m45m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("CarrierTextManagerLog", logLevel, carrierTextManagerLogger$logUpdateLoopStart$2, null);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logUpdateWfcCheck() {
        LogLevel logLevel = LogLevel.VERBOSE;
        CarrierTextManagerLogger$logUpdateWfcCheck$2 carrierTextManagerLogger$logUpdateWfcCheck$2 = new Function1() { // from class: com.android.keyguard.logging.CarrierTextManagerLogger$logUpdateWfcCheck$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "┣ updateCarrierText: found WFC state";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("CarrierTextManagerLog", logLevel, carrierTextManagerLogger$logUpdateWfcCheck$2, null));
    }
}
