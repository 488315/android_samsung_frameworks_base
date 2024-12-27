package com.android.systemui.dump;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface DumpsysEntry {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DumpableEntry implements DumpsysEntry {
        public final Dumpable dumpable;
        public final String name;
        public final DumpPriority priority;

        public DumpableEntry(Dumpable dumpable, String str, DumpPriority dumpPriority) {
            this.dumpable = dumpable;
            this.name = str;
            this.priority = dumpPriority;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DumpableEntry)) {
                return false;
            }
            DumpableEntry dumpableEntry = (DumpableEntry) obj;
            return Intrinsics.areEqual(this.dumpable, dumpableEntry.dumpable) && Intrinsics.areEqual(this.name, dumpableEntry.name) && this.priority == dumpableEntry.priority;
        }

        @Override // com.android.systemui.dump.DumpsysEntry
        public final String getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.priority.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.dumpable.hashCode() * 31, 31, this.name);
        }

        public final String toString() {
            return "DumpableEntry(dumpable=" + this.dumpable + ", name=" + this.name + ", priority=" + this.priority + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class LogBufferEntry implements DumpsysEntry {
        public final LogBuffer buffer;
        public final String name;

        public LogBufferEntry(LogBuffer logBuffer, String str) {
            this.buffer = logBuffer;
            this.name = str;
            DumpPriority dumpPriority = DumpPriority.CRITICAL;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LogBufferEntry)) {
                return false;
            }
            LogBufferEntry logBufferEntry = (LogBufferEntry) obj;
            return Intrinsics.areEqual(this.buffer, logBufferEntry.buffer) && Intrinsics.areEqual(this.name, logBufferEntry.name);
        }

        @Override // com.android.systemui.dump.DumpsysEntry
        public final String getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.name.hashCode() + (this.buffer.hashCode() * 31);
        }

        public final String toString() {
            return "LogBufferEntry(buffer=" + this.buffer + ", name=" + this.name + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TableLogBufferEntry implements DumpsysEntry {
        public final String name;
        public final TableLogBuffer table;

        public TableLogBufferEntry(TableLogBuffer tableLogBuffer, String str) {
            this.table = tableLogBuffer;
            this.name = str;
            DumpPriority dumpPriority = DumpPriority.CRITICAL;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TableLogBufferEntry)) {
                return false;
            }
            TableLogBufferEntry tableLogBufferEntry = (TableLogBufferEntry) obj;
            return Intrinsics.areEqual(this.table, tableLogBufferEntry.table) && Intrinsics.areEqual(this.name, tableLogBufferEntry.name);
        }

        @Override // com.android.systemui.dump.DumpsysEntry
        public final String getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.name.hashCode() + (this.table.hashCode() * 31);
        }

        public final String toString() {
            return "TableLogBufferEntry(table=" + this.table + ", name=" + this.name + ")";
        }
    }

    String getName();
}
