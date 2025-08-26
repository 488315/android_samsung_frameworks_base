package com.android.systemui.log.table;

import android.app.ActivityManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpsysEntry;
import com.android.systemui.log.LogBufferHelper;
import com.android.systemui.log.LogcatEchoTracker;
import com.android.systemui.util.time.SystemClock;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class TableLogBufferFactory {
    public final DumpManager dumpManager;
    public final ConcurrentHashMap existingBuffers = new ConcurrentHashMap();
    public final LogcatEchoTracker logcatEchoTracker;
    public final SystemClock systemClock;

    public TableLogBufferFactory(DumpManager dumpManager, SystemClock systemClock, LogcatEchoTracker logcatEchoTracker) {
        this.dumpManager = dumpManager;
        this.systemClock = systemClock;
        this.logcatEchoTracker = logcatEchoTracker;
    }

    public final TableLogBuffer create(int i, String str) {
        LogBufferHelper.Companion.getClass();
        if (ActivityManager.isLowRamDeviceStatic()) {
            i = Math.min(i, 20);
        }
        TableLogBuffer tableLogBuffer = new TableLogBuffer(i, str, this.systemClock, this.logcatEchoTracker, null, 16, null);
        DumpManager dumpManager = this.dumpManager;
        synchronized (dumpManager) {
            if (!dumpManager.canAssignToNameLocked(tableLogBuffer, str)) {
                throw new IllegalArgumentException("'" + str + "' is already registered");
            }
            ((TreeMap) dumpManager.tableLogBuffers).put(str, new DumpsysEntry.TableLogBufferEntry(tableLogBuffer, str));
        }
        return tableLogBuffer;
    }

    public final TableLogBuffer getOrCreate(int i, String str) {
        TableLogBuffer tableLogBuffer;
        synchronized (this.existingBuffers) {
            try {
                Object objCreate = this.existingBuffers.get(str);
                if (objCreate == null) {
                    objCreate = create(i, str);
                    this.existingBuffers.put(str, objCreate);
                }
                tableLogBuffer = (TableLogBuffer) objCreate;
            } catch (Throwable th) {
                throw th;
            }
        }
        return tableLogBuffer;
    }
}
