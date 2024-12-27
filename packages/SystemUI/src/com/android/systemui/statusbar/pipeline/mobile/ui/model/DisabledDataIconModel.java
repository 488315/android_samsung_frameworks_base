package com.android.systemui.statusbar.pipeline.mobile.ui.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class DisabledDataIconModel implements Diffable {
    public final int iconId;
    public final IconLocation iconLocation;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DisabledDataIconModel(IconLocation iconLocation, int i) {
        this.iconLocation = iconLocation;
        this.iconId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DisabledDataIconModel)) {
            return false;
        }
        DisabledDataIconModel disabledDataIconModel = (DisabledDataIconModel) obj;
        return this.iconLocation == disabledDataIconModel.iconLocation && this.iconId == disabledDataIconModel.iconId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.iconId) + (this.iconLocation.hashCode() * 31);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        DisabledDataIconModel disabledDataIconModel = (DisabledDataIconModel) obj;
        IconLocation iconLocation = disabledDataIconModel.iconLocation;
        IconLocation iconLocation2 = this.iconLocation;
        if (iconLocation != iconLocation2) {
            tableRowLoggerImpl.logChange("DisabledDataIcon", iconLocation2.toString());
        }
        int i = disabledDataIconModel.iconId;
        int i2 = this.iconId;
        if (i != i2) {
            tableRowLoggerImpl.logChange(i2, "DisabledDataIcon");
        }
    }

    public final String toString() {
        return "DisabledDataIconModel(iconLocation=" + this.iconLocation + ", iconId=" + this.iconId + ")";
    }

    public /* synthetic */ DisabledDataIconModel(IconLocation iconLocation, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? IconLocation.DATA_ICON : iconLocation, i);
    }
}
