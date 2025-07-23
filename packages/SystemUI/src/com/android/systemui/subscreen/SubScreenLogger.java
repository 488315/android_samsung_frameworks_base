package com.android.systemui.subscreen;

import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SubScreenLogger {
    public final LogBuffer buffer;

    public SubScreenLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void debug(String str) {
        Log.i("SubScreenManager", str);
        LogBuffer.log$default(this.buffer, "SubScreenLog", LogLevel.DEBUG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("[SubScreenManager] : ", str));
    }

    public final void info(String str) {
        Log.i("SubScreenManager", str);
        LogBuffer.log$default(this.buffer, "SubScreenLog", LogLevel.INFO, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("[SubScreenManager] : ", str));
    }

    public final void warn(String str) {
        Log.i("SubScreenManager", str);
        LogBuffer.log$default(this.buffer, "SubScreenLog", LogLevel.WARNING, "[SubScreenManager] : ".concat(str));
    }
}
