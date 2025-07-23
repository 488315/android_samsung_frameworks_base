package com.android.keyguard;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class KeyguardSecVisibilityHelper$mSetVisibleEndRunnable$1 implements Runnable {
    public final /* synthetic */ KeyguardSecVisibilityHelper this$0;

    public KeyguardSecVisibilityHelper$mSetVisibleEndRunnable$1(KeyguardSecVisibilityHelper keyguardSecVisibilityHelper) {
        this.this$0 = keyguardSecVisibilityHelper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardSecVisibilityHelper keyguardSecVisibilityHelper = this.this$0;
        keyguardSecVisibilityHelper.isVisibilityAnimating = false;
        keyguardSecVisibilityHelper.mView.setVisibility(0);
        LogBuffer logBuffer = this.this$0.mLogBuffer;
        if (logBuffer != null) {
            LogBuffer.log$default(logBuffer, "KeyguardSecVisibilityHelper", LogLevel.DEBUG, "Callback Set Visibility to VISIBLE");
        }
    }
}
