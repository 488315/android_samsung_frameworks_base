package com.android.systemui.log;

public final class MediaLogWriter {
    public final LogBuffer buffer;

    public MediaLogWriter(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
