package com.android.keyguard.logging;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes.dex */
public final class ScrimLogger {
    public static final String TAG;
    public final LogBuffer buffer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        String simpleName = Reflection.getOrCreateKotlinClass(ScrimLogger.class).getSimpleName();
        simpleName.getClass();
        TAG = simpleName;
    }

    public ScrimLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void d(String str, String str2, Object obj) {
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
        sbM.append(TAG);
        String string = sbM.toString();
        LogLevel logLevel = LogLevel.DEBUG;
        ScrimLogger$$ExternalSyntheticLambda0 scrimLogger$$ExternalSyntheticLambda0 = new ScrimLogger$$ExternalSyntheticLambda0();
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain(string, logLevel, scrimLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str2;
        logMessageImpl.str2 = obj.toString();
        logBuffer.commit(logMessageObtain);
    }
}
