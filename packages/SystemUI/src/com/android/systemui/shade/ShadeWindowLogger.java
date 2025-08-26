package com.android.systemui.shade;

import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;

/* loaded from: classes3.dex */
public final class ShadeWindowLogger {
    public final /* synthetic */ ConstantStringsLoggerImpl $$delegate_0;
    public final LogBuffer buffer;

    public ShadeWindowLogger(LogBuffer logBuffer) {
        this.$$delegate_0 = new ConstantStringsLoggerImpl(logBuffer, "systemui.shadewindow");
        this.buffer = logBuffer;
    }
}
