package com.android.systemui.scene.shared.logger;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneLogger {
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

    public SceneLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public static void logOverlayChangeRequested$default(SceneLogger sceneLogger, OverlayKey overlayKey, OverlayKey overlayKey2, String str, int i) {
        if ((i & 1) != 0) {
            overlayKey = null;
        }
        if ((i & 2) != 0) {
            overlayKey2 = null;
        }
        sceneLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        SceneLogger$$ExternalSyntheticLambda0 sceneLogger$$ExternalSyntheticLambda0 = new SceneLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = sceneLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("SceneFramework", logLevel, sceneLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = overlayKey != null ? overlayKey.toString() : null;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = overlayKey2 != null ? overlayKey2.toString() : null;
        logMessageImpl.str3 = str;
        logBuffer.commit(obtain);
    }

    public final void logSceneChangeRejection(ContentKey contentKey, ContentKey contentKey2, String str, String str2) {
        String str3;
        String str4;
        LogLevel logLevel = LogLevel.INFO;
        SceneLogger$$ExternalSyntheticLambda0 sceneLogger$$ExternalSyntheticLambda0 = new SceneLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("SceneFramework", logLevel, sceneLogger$$ExternalSyntheticLambda0, null);
        String str5 = "<none>";
        if (contentKey == null || (str3 = contentKey.debugName) == null) {
            str3 = "<none>";
        }
        if (contentKey2 != null && (str4 = contentKey2.debugName) != null) {
            str5 = str4;
        }
        ((LogMessageImpl) obtain).str1 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str3, " → ", str5);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = str;
        logMessageImpl.bool1 = contentKey2 instanceof OverlayKey;
        logBuffer.commit(obtain);
    }

    public final void logSceneChanged(SceneKey sceneKey, SceneKey sceneKey2, Object obj, String str, final boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        Function1 function1 = new Function1() { // from class: com.android.systemui.scene.shared.logger.SceneLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                int i = SceneLogger.$r8$clinit;
                StringBuilder sb = new StringBuilder();
                sb.append("Scene changed: " + logMessage.getStr1());
                String str3 = logMessage.getStr3();
                if (str3 != null) {
                    sb.append(" (sceneState=" + str3 + ")");
                }
                if (z) {
                    sb.append(" (instant)");
                }
                sb.append(", reason: " + logMessage.getStr2());
                return sb.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("SceneFramework", logLevel, function1, null);
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sceneKey.debugName, " → ", sceneKey2.debugName);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = m;
        logMessageImpl.str2 = str;
        logMessageImpl.str3 = obj != null ? obj.toString() : null;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }
}
