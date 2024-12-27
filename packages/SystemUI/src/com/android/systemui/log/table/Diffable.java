package com.android.systemui.log.table;

import com.android.systemui.log.table.TableLogBuffer;

public interface Diffable {
    void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl);

    default void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
    }
}
