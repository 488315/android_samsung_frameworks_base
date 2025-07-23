package com.android.keyguard.logging;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScrimLogger {
    public static final String TAG;
    public final LogBuffer buffer;

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
        String simpleName = Reflection.getOrCreateKotlinClass(ScrimLogger.class).getSimpleName();
        simpleName.getClass();
        TAG = simpleName;
    }

    public ScrimLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void d(String str, String str2, Object obj) {
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
        m.append(TAG);
        String sb = m.toString();
        LogLevel logLevel = LogLevel.DEBUG;
        ScrimLogger$$ExternalSyntheticLambda0 scrimLogger$$ExternalSyntheticLambda0 = new ScrimLogger$$ExternalSyntheticLambda0();
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(sb, logLevel, scrimLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str2;
        logMessageImpl.str2 = obj.toString();
        logBuffer.commit(obtain);
    }
}
