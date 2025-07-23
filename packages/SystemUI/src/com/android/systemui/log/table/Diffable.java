package com.android.systemui.log.table;

import com.android.systemui.log.table.TableLogBuffer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface Diffable {
    void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl);

    default void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
    }
}
