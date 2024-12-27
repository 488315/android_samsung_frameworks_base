package com.android.systemui.keyboard.stickykeys;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StickyKeysLogger {
    public final LogBuffer buffer;

    public StickyKeysLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logNewSettingValue(boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        StickyKeysLogger$logNewSettingValue$2 stickyKeysLogger$logNewSettingValue$2 = new Function1() { // from class: com.android.systemui.keyboard.stickykeys.StickyKeysLogger$logNewSettingValue$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return "sticky key setting changed, new state: ".concat(((LogMessage) obj).getBool1() ? "enabled" : "disabled");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("stickyKeys", logLevel, stickyKeysLogger$logNewSettingValue$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logNewStickyKeysReceived(Map map) {
        LogLevel logLevel = LogLevel.VERBOSE;
        StickyKeysLogger$logNewStickyKeysReceived$2 stickyKeysLogger$logNewStickyKeysReceived$2 = new Function1() { // from class: com.android.systemui.keyboard.stickykeys.StickyKeysLogger$logNewStickyKeysReceived$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("new sticky keys state received: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("stickyKeys", logLevel, stickyKeysLogger$logNewStickyKeysReceived$2, null);
        ((LogMessageImpl) obtain).str1 = map.toString();
        logBuffer.commit(obtain);
    }

    public final void logNewUiState(Map map) {
        LogLevel logLevel = LogLevel.INFO;
        StickyKeysLogger$logNewUiState$2 stickyKeysLogger$logNewUiState$2 = new Function1() { // from class: com.android.systemui.keyboard.stickykeys.StickyKeysLogger$logNewUiState$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("new sticky keys state received: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("stickyKeys", logLevel, stickyKeysLogger$logNewUiState$2, null);
        ((LogMessageImpl) obtain).str1 = map.toString();
        logBuffer.commit(obtain);
    }
}
