package com.android.systemui.log;

public final class ConstantStringsLoggerImpl {
    public final LogBuffer buffer;
    public final String tag;

    public ConstantStringsLoggerImpl(LogBuffer logBuffer, String str) {
        this.buffer = logBuffer;
        this.tag = str;
    }
}
