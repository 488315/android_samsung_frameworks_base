package com.android.systemui.statusbar;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger$$ExternalSyntheticLambda0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 implements Runnable {
    public final /* synthetic */ LockscreenShadeTransitionController this$0;

    public LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1(LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        this.this$0 = lockscreenShadeTransitionController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        LSShadeTransitionLogger lSShadeTransitionLogger = this.this$0.logger;
        lSShadeTransitionLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda0 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(9);
        LogBuffer logBuffer = lSShadeTransitionLogger.buffer;
        logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$$ExternalSyntheticLambda0, null));
        this.this$0.setDragDownAmountAnimated(0.0f, 0L, null);
    }
}
