package com.android.systemui.qs.logging;

import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            public final Object mo779invoke(Object obj2) {
                return MutablePreferences$$ExternalSyntheticOutline0.m(new StringBuilder(), str, ": ", ((LogMessage) obj2).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, function1, null);
        ((LogMessageImpl) obtain).str1 = obj.toString();
        logBuffer.commit(obtain);
    }

    public final void logAllTilesChangeListening(String str, String str2, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$$ExternalSyntheticLambda0 qSLogger$$ExternalSyntheticLambda0 = new QSLogger$$ExternalSyntheticLambda0(11);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).bool1 = z;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logBuffer.commit(obtain);
    }
}
