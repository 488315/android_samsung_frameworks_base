package com.android.systemui.dump;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpsysEntry;
import com.android.systemui.log.LogBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DumpManager {
    public final Map dumpables = new TreeMap();
    public final Map buffers = new TreeMap();
    public final Map tableLogBuffers = new TreeMap();

    public static /* synthetic */ void registerDumpable$default(DumpManager dumpManager, String str, Dumpable dumpable) {
        dumpManager.registerDumpable(dumpable, str, DumpPriority.CRITICAL);
    }

    public final boolean canAssignToNameLocked(Object obj, String str) {
        Object obj2;
        DumpsysEntry.DumpableEntry dumpableEntry = (DumpsysEntry.DumpableEntry) ((TreeMap) this.dumpables).get(str);
        if (dumpableEntry == null || (obj2 = dumpableEntry.dumpable) == null) {
            DumpsysEntry.LogBufferEntry logBufferEntry = (DumpsysEntry.LogBufferEntry) ((TreeMap) this.buffers).get(str);
            if (logBufferEntry != null) {
                obj2 = logBufferEntry.buffer;
            } else {
                DumpsysEntry.TableLogBufferEntry tableLogBufferEntry = (DumpsysEntry.TableLogBufferEntry) ((TreeMap) this.tableLogBuffers).get(str);
                obj2 = tableLogBufferEntry != null ? tableLogBufferEntry.table : null;
            }
        }
        return obj2 == null || Intrinsics.areEqual(obj, obj2);
    }

    public final synchronized Collection getDumpables() {
        return CollectionsKt___CollectionsKt.toList(((TreeMap) this.dumpables).values());
    }

    public final synchronized Collection getLogBuffers() {
        return CollectionsKt___CollectionsKt.toList(((TreeMap) this.buffers).values());
    }

    public final synchronized Collection getTableLogBuffers() {
        return CollectionsKt___CollectionsKt.toList(((TreeMap) this.tableLogBuffers).values());
    }

    public final synchronized void registerBuffer(LogBuffer logBuffer, String str) {
        if (!canAssignToNameLocked(logBuffer, str)) {
            throw new IllegalArgumentException("'" + str + "' is already registered");
        }
        ((TreeMap) this.buffers).put(str, new DumpsysEntry.LogBufferEntry(logBuffer, str));
    }

    public final void registerCriticalDumpable(String str, Dumpable dumpable) {
        registerDumpable(dumpable, str, DumpPriority.CRITICAL);
    }

    public final void registerDumpable(String str, Dumpable dumpable) {
        registerDumpable$default(this, str, dumpable);
    }

    public final void registerNormalDumpable(Dumpable dumpable) {
        registerNormalDumpable(dumpable.getClass().getCanonicalName(), dumpable);
    }

    public final synchronized void unregisterDumpable(String str) {
        ((TreeMap) this.dumpables).remove(str);
    }

    public final synchronized void registerDumpable(Dumpable dumpable, String str, DumpPriority dumpPriority) {
        try {
            if (!canAssignToNameLocked(dumpable, str)) {
                unregisterDumpable(str);
            }
            ((TreeMap) this.dumpables).put(str, new DumpsysEntry.DumpableEntry(dumpable, str, dumpPriority));
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void registerNormalDumpable(String str, Dumpable dumpable) {
        registerDumpable(dumpable, str, DumpPriority.NORMAL);
    }

    public final synchronized void registerDumpable(Dumpable dumpable) {
        registerDumpable$default(this, dumpable.getClass().getCanonicalName(), dumpable);
    }
}
