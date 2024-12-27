package com.android.systemui.statusbar.pipeline.shared.data.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;

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

    public final int hashCode() {
        return Boolean.hashCode(this.hasActivityOut) + (Boolean.hashCode(this.hasActivityIn) * 31);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        DataActivityModel dataActivityModel = (DataActivityModel) obj;
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
