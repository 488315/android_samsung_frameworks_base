package com.android.systemui.log;

import android.util.Log;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QuickPanelExternalLogger {
    public final LogBuffer buffer;
    public final SysuiStatusBarStateController statusBarStateController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public QuickPanelExternalLogger(LogBuffer logBuffer, SysuiStatusBarStateController sysuiStatusBarStateController) {
        this.buffer = logBuffer;
        this.statusBarStateController = sysuiStatusBarStateController;
    }

    public final void log(String str, String str2) {
        LogLevel logLevel = LogLevel.INFO;
        QuickPanelExternalLogger$log$2 quickPanelExternalLogger$log$2 = new Function1() { // from class: com.android.systemui.log.QuickPanelExternalLogger$log$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("[", logMessage.getStr1(), "] ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QuickPanelLog", logLevel, quickPanelExternalLogger$log$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logBuffer.commit(obtain);
        Unit unit = Unit.INSTANCE;
        Log.d("QuickPanelLog", str2);
    }
}
