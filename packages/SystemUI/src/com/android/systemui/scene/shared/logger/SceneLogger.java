package com.android.systemui.scene.shared.logger;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SceneLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer logBuffer;

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

    public SceneLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void logFrameworkEnabled(String str) {
        LogLevel logLevel = LogLevel.WARNING;
        SceneLogger$logFrameworkEnabled$2 sceneLogger$logFrameworkEnabled$2 = new Function1() { // from class: com.android.systemui.scene.shared.logger.SceneLogger$logFrameworkEnabled$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                int i = SceneLogger.$r8$clinit;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Scene framework is ", bool1 ? "enabled" : "disabled", logMessage.getStr1() != null ? AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(" ", logMessage.getStr1()) : "");
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("SceneFramework", logLevel, sceneLogger$logFrameworkEnabled$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = false;
        logMessageImpl.str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logSceneChangeCommitted(SceneKey sceneKey, SceneKey sceneKey2) {
        LogLevel logLevel = LogLevel.INFO;
        SceneLogger$logSceneChangeCommitted$2 sceneLogger$logSceneChangeCommitted$2 = new Function1() { // from class: com.android.systemui.scene.shared.logger.SceneLogger$logSceneChangeCommitted$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("Scene change committed: ", logMessage.getStr1(), " → ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("SceneFramework", logLevel, sceneLogger$logSceneChangeCommitted$2, null);
        ((LogMessageImpl) obtain).str1 = sceneKey.toString();
        ((LogMessageImpl) obtain).str2 = sceneKey2.toString();
        logBuffer.commit(obtain);
    }

    public final void logSceneChangeRequested(SceneKey sceneKey, SceneKey sceneKey2, String str, final boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        Function1 function1 = new Function1() { // from class: com.android.systemui.scene.shared.logger.SceneLogger$logSceneChangeRequested$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean z2 = z;
                StringBuilder sb = new StringBuilder();
                sb.append("Scene change requested: " + logMessage.getStr1() + " → " + logMessage.getStr2());
                if (z2) {
                    sb.append(" (instant)");
                }
                sb.append(", reason: " + logMessage.getStr3());
                return sb.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("SceneFramework", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = sceneKey.toString();
        logMessageImpl.str2 = sceneKey2.toString();
        logMessageImpl.str3 = str;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }
}
