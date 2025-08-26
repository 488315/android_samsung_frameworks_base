package com.android.systemui.qs.logging;

import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class QSLogger {
    public final /* synthetic */ ConstantStringsLoggerImpl $$delegate_0;
    public final LogBuffer buffer;

    public QSLogger(LogBuffer logBuffer, LogBuffer logBuffer2) {
        this.$$delegate_0 = new ConstantStringsLoggerImpl(logBuffer, "QSLog");
        this.buffer = logBuffer;
    }

    public static String toStateString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "wrong state" : SystemUIAnalytics.QPBSE_KEY_ACTIVE : "inactive" : "unavailable";
    }

    public final void d(Object obj, final String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) {
                return MutablePreferences$$ExternalSyntheticOutline0.m(new StringBuilder(), str, ": ", ((LogMessage) obj2).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSLog", logLevel, function1, null);
        ((LogMessageImpl) logMessageObtain).str1 = obj.toString();
        logBuffer.commit(logMessageObtain);
    }

    public final void logAllTilesChangeListening(String str, String str2, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$$ExternalSyntheticLambda0 qSLogger$$ExternalSyntheticLambda0 = new QSLogger$$ExternalSyntheticLambda0(11);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSLog", logLevel, qSLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logBuffer.commit(logMessageObtain);
    }
}
