package com.android.keyguard;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;

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
