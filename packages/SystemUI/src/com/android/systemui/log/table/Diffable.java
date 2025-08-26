package com.android.systemui.log.table;

import com.android.systemui.log.table.TableLogBuffer;

/* loaded from: classes2.dex */
public interface Diffable {
    void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl);

    default void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
    }
}
