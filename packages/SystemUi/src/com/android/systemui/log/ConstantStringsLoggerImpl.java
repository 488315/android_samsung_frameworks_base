package com.android.systemui.log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ConstantStringsLoggerImpl implements ConstantStringsLogger {
    private final LogBuffer buffer;
    private final String tag;

    public ConstantStringsLoggerImpl(LogBuffer logBuffer, String str) {
        this.buffer = logBuffer;
        this.tag = str;
    }

    @Override // com.android.systemui.log.ConstantStringsLogger
    /* renamed from: d */
    public void mo153d(String str) {
        LogBuffer.log$default(this.buffer, this.tag, LogLevel.DEBUG, str, null, 8, null);
    }

    @Override // com.android.systemui.log.ConstantStringsLogger
    /* renamed from: e */
    public void mo154e(String str) {
        LogBuffer.log$default(this.buffer, this.tag, LogLevel.ERROR, str, null, 8, null);
    }

    public final LogBuffer getBuffer() {
        return this.buffer;
    }

    public final String getTag() {
        return this.tag;
    }

    @Override // com.android.systemui.log.ConstantStringsLogger
    /* renamed from: v */
    public void mo155v(String str) {
        LogBuffer.log$default(this.buffer, this.tag, LogLevel.VERBOSE, str, null, 8, null);
    }

    @Override // com.android.systemui.log.ConstantStringsLogger
    /* renamed from: w */
    public void mo156w(String str) {
        LogBuffer.log$default(this.buffer, this.tag, LogLevel.WARNING, str, null, 8, null);
    }
}
