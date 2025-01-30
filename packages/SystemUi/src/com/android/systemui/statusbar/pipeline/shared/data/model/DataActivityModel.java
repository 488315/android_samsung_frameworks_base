package com.android.systemui.statusbar.pipeline.shared.data.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DataActivityModel implements Diffable {
    public final boolean hasActivityIn;
    public final boolean hasActivityOut;

    public DataActivityModel(boolean z, boolean z2) {
        this.hasActivityIn = z;
        this.hasActivityOut = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataActivityModel)) {
            return false;
        }
        DataActivityModel dataActivityModel = (DataActivityModel) obj;
        return this.hasActivityIn == dataActivityModel.hasActivityIn && this.hasActivityOut == dataActivityModel.hasActivityOut;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        boolean z = this.hasActivityIn;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = i * 31;
        boolean z2 = this.hasActivityOut;
        return i2 + (z2 ? 1 : z2 ? 1 : 0);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        DataActivityModel dataActivityModel = (DataActivityModel) diffable;
        boolean z = dataActivityModel.hasActivityIn;
        boolean z2 = this.hasActivityIn;
        if (z != z2) {
            tableRowLoggerImpl.logChange("in", z2);
        }
        boolean z3 = dataActivityModel.hasActivityOut;
        boolean z4 = this.hasActivityOut;
        if (z3 != z4) {
            tableRowLoggerImpl.logChange("out", z4);
        }
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        tableRowLoggerImpl.logChange("in", this.hasActivityIn);
        tableRowLoggerImpl.logChange("out", this.hasActivityOut);
    }

    public final String toString() {
        return "DataActivityModel(hasActivityIn=" + this.hasActivityIn + ", hasActivityOut=" + this.hasActivityOut + ")";
    }
}
