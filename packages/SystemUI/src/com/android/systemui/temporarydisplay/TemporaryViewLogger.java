package com.android.systemui.temporarydisplay;

import android.view.View;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public class TemporaryViewLogger {
    public static final Companion Companion = new Companion(null);
    public final LogBuffer buffer;
    public final String tag;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public TemporaryViewLogger(LogBuffer logBuffer, String str) {
        this.buffer = logBuffer;
        this.tag = str;
    }

    public final void logViewAdditionDelayed(TemporaryViewInfo temporaryViewInfo) {
        LogLevel logLevel = LogLevel.DEBUG;
        TemporaryViewLogger$$ExternalSyntheticLambda0 temporaryViewLogger$$ExternalSyntheticLambda0 = new TemporaryViewLogger$$ExternalSyntheticLambda0(6);
        String str = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain(str, logLevel, temporaryViewLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = temporaryViewInfo.getId();
        logMessageImpl.str2 = temporaryViewInfo.getWindowTitle();
        logMessageImpl.str3 = temporaryViewInfo.getPriority().name();
        logBuffer.commit(logMessageObtain);
    }

    public final void logViewExpiration(TemporaryViewInfo temporaryViewInfo) {
        LogLevel logLevel = LogLevel.DEBUG;
        TemporaryViewLogger$$ExternalSyntheticLambda0 temporaryViewLogger$$ExternalSyntheticLambda0 = new TemporaryViewLogger$$ExternalSyntheticLambda0(5);
        String str = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain(str, logLevel, temporaryViewLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = temporaryViewInfo.getId();
        logMessageImpl.str2 = temporaryViewInfo.getWindowTitle();
        logMessageImpl.str3 = temporaryViewInfo.getPriority().name();
        logBuffer.commit(logMessageObtain);
    }

    public final void logViewRemovalIgnored(String str, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        TemporaryViewLogger$$ExternalSyntheticLambda0 temporaryViewLogger$$ExternalSyntheticLambda0 = new TemporaryViewLogger$$ExternalSyntheticLambda0(9);
        String str3 = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain(str3, logLevel, temporaryViewLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str2;
        logMessageImpl.str2 = str;
        logBuffer.commit(logMessageObtain);
    }

    public final void logViewRemovedFromWindowManager(TemporaryViewInfo temporaryViewInfo, View view, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        TemporaryViewLogger$$ExternalSyntheticLambda0 temporaryViewLogger$$ExternalSyntheticLambda0 = new TemporaryViewLogger$$ExternalSyntheticLambda0(3);
        String str = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain(str, logLevel, temporaryViewLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = temporaryViewInfo.getId();
        logMessageImpl.str2 = temporaryViewInfo.getWindowTitle();
        logMessageImpl.str3 = view.getClass().getName();
        Companion.getClass();
        logMessageImpl.int1 = System.identityHashCode(view);
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
    }

    public final void logViewUpdate(TemporaryViewInfo temporaryViewInfo) {
        LogLevel logLevel = LogLevel.DEBUG;
        TemporaryViewLogger$$ExternalSyntheticLambda0 temporaryViewLogger$$ExternalSyntheticLambda0 = new TemporaryViewLogger$$ExternalSyntheticLambda0(8);
        String str = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain(str, logLevel, temporaryViewLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = temporaryViewInfo.getId();
        logMessageImpl.str2 = temporaryViewInfo.getWindowTitle();
        logMessageImpl.str3 = temporaryViewInfo.getPriority().name();
        logBuffer.commit(logMessageObtain);
    }
}
