package com.android.systemui.shade;

import com.android.systemui.log.ConstantStringsLogger;
import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeWindowLogger implements ConstantStringsLogger {
    public final /* synthetic */ ConstantStringsLoggerImpl $$delegate_0;
    public final LogBuffer buffer;

    public ShadeWindowLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
        this.$$delegate_0 = new ConstantStringsLoggerImpl(logBuffer, "systemui.shadewindow");
    }

    @Override // com.android.systemui.log.ConstantStringsLogger
    /* renamed from: d */
    public final void mo153d(String str) {
        this.$$delegate_0.mo153d(str);
    }

    @Override // com.android.systemui.log.ConstantStringsLogger
    /* renamed from: e */
    public final void mo154e(String str) {
        this.$$delegate_0.mo154e(str);
    }

    @Override // com.android.systemui.log.ConstantStringsLogger
    /* renamed from: v */
    public final void mo155v(String str) {
        this.$$delegate_0.mo155v(str);
    }

    @Override // com.android.systemui.log.ConstantStringsLogger
    /* renamed from: w */
    public final void mo156w(String str) {
        this.$$delegate_0.mo156w(str);
    }
}
