package com.android.systemui.statusbar.phone;

import android.util.DisplayMetrics;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LSShadeTransitionLogger {
    public final LogBuffer buffer;
    public final DisplayMetrics displayMetrics;
    public final LockscreenGestureLogger lockscreenGestureLogger;

    public LSShadeTransitionLogger(LogBuffer logBuffer, LockscreenGestureLogger lockscreenGestureLogger, DisplayMetrics displayMetrics) {
        this.buffer = logBuffer;
        this.lockscreenGestureLogger = lockscreenGestureLogger;
        this.displayMetrics = displayMetrics;
    }

    public final void logAnimationCancelled(boolean z) {
        LogBuffer logBuffer = this.buffer;
        if (z) {
            logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", LogLevel.DEBUG, new LSShadeTransitionLogger$$ExternalSyntheticLambda0(15), null));
        } else {
            logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", LogLevel.DEBUG, new LSShadeTransitionLogger$$ExternalSyntheticLambda0(16), null));
        }
    }
}
