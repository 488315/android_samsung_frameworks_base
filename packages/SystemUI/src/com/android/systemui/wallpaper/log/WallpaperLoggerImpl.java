package com.android.systemui.wallpaper.log;

import android.util.Log;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WallpaperLoggerImpl implements WallpaperLogger {
    public final LogBuffer buffer;

    public WallpaperLoggerImpl(String str, int i, LogBufferFactory logBufferFactory) {
        this.buffer = LogBufferFactory.create$default(logBufferFactory, str, i, false, null, 28);
    }

    public final void log(String str, String str2) {
        Log.i(str, str2);
        LogLevel logLevel = LogLevel.INFO;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7(str2);
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain(str, logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda7, null));
    }
}
