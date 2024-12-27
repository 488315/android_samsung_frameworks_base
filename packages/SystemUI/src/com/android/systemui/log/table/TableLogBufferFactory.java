package com.android.systemui.log.table;

import android.app.ActivityManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpsysEntry;
import com.android.systemui.log.LogBufferHelper;
import com.android.systemui.log.LogcatEchoTracker;
import com.android.systemui.util.time.SystemClock;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

public final class TableLogBufferFactory {
    public final CoroutineDispatcher bgDispatcher;
    public final CoroutineScope coroutineScope;
    public final DumpManager dumpManager;
    public final Map existingBuffers = new LinkedHashMap();
    public final LogcatEchoTracker logcatEchoTracker;
    public final SystemClock systemClock;

    public TableLogBufferFactory(DumpManager dumpManager, SystemClock systemClock, LogcatEchoTracker logcatEchoTracker, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
        this.dumpManager = dumpManager;
        this.systemClock = systemClock;
        this.logcatEchoTracker = logcatEchoTracker;
        this.bgDispatcher = coroutineDispatcher;
        this.coroutineScope = coroutineScope;
    }

    public final TableLogBuffer create(int i, String str) {
        LogBufferHelper.Companion.getClass();
        if (ActivityManager.isLowRamDeviceStatic()) {
            i = Math.min(i, 20);
        }
        TableLogBuffer tableLogBuffer = new TableLogBuffer(i, str, this.systemClock, this.logcatEchoTracker, this.bgDispatcher, this.coroutineScope, null, 64, null);
        DumpManager dumpManager = this.dumpManager;
        synchronized (dumpManager) {
            if (!dumpManager.canAssignToNameLocked(tableLogBuffer, str)) {
                throw new IllegalArgumentException("'" + str + "' is already registered");
            }
            ((TreeMap) dumpManager.tableLogBuffers).put(str, new DumpsysEntry.TableLogBufferEntry(tableLogBuffer, str));
        }
        return tableLogBuffer;
    }
}
