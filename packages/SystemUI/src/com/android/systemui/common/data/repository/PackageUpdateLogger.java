package com.android.systemui.common.data.repository;

import com.android.systemui.log.LogBuffer;

public final class PackageUpdateLogger {
    public final LogBuffer buffer;

    public PackageUpdateLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
