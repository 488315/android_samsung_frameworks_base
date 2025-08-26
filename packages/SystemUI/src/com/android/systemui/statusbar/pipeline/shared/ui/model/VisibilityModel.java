package com.android.systemui.statusbar.pipeline.shared.ui.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.DumpUtilsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class VisibilityModel implements Diffable {
    public final boolean shouldAnimateChange;
    public final int visibility;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public VisibilityModel(int i, boolean z) {
        this.visibility = i;
        this.shouldAnimateChange = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VisibilityModel)) {
            return false;
        }
        VisibilityModel visibilityModel = (VisibilityModel) obj;
        return this.visibility == visibilityModel.visibility && this.shouldAnimateChange == visibilityModel.shouldAnimateChange;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.shouldAnimateChange) + (Integer.hashCode(this.visibility) * 31);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        VisibilityModel visibilityModel = (VisibilityModel) diffable;
        int i = visibilityModel.visibility;
        int i2 = this.visibility;
        if (i2 != i) {
            tableRowLoggerImpl.logChange("vis", DumpUtilsKt.visibilityString(i2));
        }
        boolean z = visibilityModel.shouldAnimateChange;
        boolean z2 = this.shouldAnimateChange;
        if (z2 != z) {
            tableRowLoggerImpl.logChange("animate", z2);
        }
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        tableRowLoggerImpl.logChange("vis", DumpUtilsKt.visibilityString(this.visibility));
        tableRowLoggerImpl.logChange("animate", this.shouldAnimateChange);
    }

    public final String toString() {
        return "VisibilityModel(visibility=" + this.visibility + ", shouldAnimateChange=" + this.shouldAnimateChange + ")";
    }
}
