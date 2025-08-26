package com.android.systemui.log;

import android.util.Log;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class QuickPanelExternalLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer buffer;
    public final SysuiStatusBarStateController statusBarStateController;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        QuickPanelExternalLogger$$ExternalSyntheticLambda0 quickPanelExternalLogger$$ExternalSyntheticLambda0 = new QuickPanelExternalLogger$$ExternalSyntheticLambda0();
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("QuickPanelLog", logLevel, quickPanelExternalLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logBuffer.commit(logMessageObtain);
        Unit unit = Unit.INSTANCE;
        Log.d("QuickPanelLog", str2);
    }
}
