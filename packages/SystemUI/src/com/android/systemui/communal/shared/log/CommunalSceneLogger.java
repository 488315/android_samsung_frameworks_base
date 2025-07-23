package com.android.systemui.communal.shared.log;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalSceneLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer logBuffer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public CommunalSceneLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void logSceneChangeRequested(SceneKey sceneKey, SceneKey sceneKey2, String str, final boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        Function1 function1 = new Function1() { // from class: com.android.systemui.communal.shared.log.CommunalSceneLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int i = CommunalSceneLogger.$r8$clinit;
                StringBuilder sb = new StringBuilder();
                sb.append("Scene change requested: " + logMessage.getStr1() + " → " + logMessage.getStr2());
                if (z) {
                    sb.append(" (instant)");
                }
                sb.append(", reason: " + logMessage.getStr3());
                return sb.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("CommunalSceneLogger", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = sceneKey.toString();
        logMessageImpl.str2 = sceneKey2.toString();
        logMessageImpl.str3 = str;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }
}
