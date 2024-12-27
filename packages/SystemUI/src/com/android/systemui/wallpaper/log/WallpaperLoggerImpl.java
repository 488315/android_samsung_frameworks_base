package com.android.systemui.wallpaper.log;

import android.util.Log;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.log.core.LogLevel;
import kotlin.jvm.functions.Function1;

public final class WallpaperLoggerImpl implements WallpaperLogger {
    public final LogBuffer buffer;

    public WallpaperLoggerImpl(String str, int i, LogBufferFactory logBufferFactory) {
        this.buffer = logBufferFactory.create(i, str, true);
    }

    public final void log(String str, final String str2) {
        Log.i(str, str2);
        LogLevel logLevel = LogLevel.INFO;
        Function1 function1 = new Function1() { // from class: com.android.systemui.wallpaper.log.WallpaperLoggerImpl$log$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return str2;
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain(str, logLevel, function1, null));
    }
}
