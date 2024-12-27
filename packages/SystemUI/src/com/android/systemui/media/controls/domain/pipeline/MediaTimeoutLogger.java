package com.android.systemui.media.controls.domain.pipeline;

import com.android.systemui.log.LogBuffer;

public final class MediaTimeoutLogger {
    public final LogBuffer buffer;

    public MediaTimeoutLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
