package com.android.systemui.statusbar.pipeline.mobile.p026ui.model;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface SignalIconModel extends Diffable {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Satellite implements SignalIconModel {
        public final Icon.Resource icon;
        public final int level;

        public Satellite(int i, Icon.Resource resource) {
            this.level = i;
            this.icon = resource;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Satellite)) {
                return false;
            }
            Satellite satellite = (Satellite) obj;
            return this.level == satellite.level && Intrinsics.areEqual(this.icon, satellite.icon);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.model.SignalIconModel
        public final int getLevel() {
            return this.level;
        }

        public final int hashCode() {
            return this.icon.hashCode() + (Integer.hashCode(this.level) * 31);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            logPartial((SignalIconModel) diffable, tableRowLoggerImpl);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            logFully(tableRowLoggerImpl);
        }

        public final void logFully(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("numLevels", "HELLO");
            tableRowLoggerImpl.logChange("type", "s");
            tableRowLoggerImpl.logChange(this.level, ActionResults.RESULT_SET_VOLUME_SUCCESS);
        }

        public final void logPartial(SignalIconModel signalIconModel, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            if (!(signalIconModel instanceof Satellite)) {
                logFully(tableRowLoggerImpl);
                return;
            }
            int level = signalIconModel.getLevel();
            int i = this.level;
            if (level != i) {
                tableRowLoggerImpl.logChange(i, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            }
        }

        public final String toString() {
            return "Satellite(level=" + this.level + ", icon=" + this.icon + ")";
        }
    }

    int getLevel();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Cellular implements SignalIconModel {
        public final boolean carrierNetworkChange;
        public final int iconId;
        public final int level;
        public final int numberOfLevels;
        public final boolean showExclamationMark;

        public Cellular(int i, int i2, boolean z, boolean z2, int i3) {
            this.level = i;
            this.numberOfLevels = i2;
            this.showExclamationMark = z;
            this.carrierNetworkChange = z2;
            this.iconId = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Cellular)) {
                return false;
            }
            Cellular cellular = (Cellular) obj;
            return this.level == cellular.level && this.numberOfLevels == cellular.numberOfLevels && this.showExclamationMark == cellular.showExclamationMark && this.carrierNetworkChange == cellular.carrierNetworkChange && this.iconId == cellular.iconId;
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.model.SignalIconModel
        public final int getLevel() {
            return this.level;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.numberOfLevels, Integer.hashCode(this.level) * 31, 31);
            boolean z = this.showExclamationMark;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            int i2 = (m42m + i) * 31;
            boolean z2 = this.carrierNetworkChange;
            return Integer.hashCode(this.iconId) + ((i2 + (z2 ? 1 : z2 ? 1 : 0)) * 31);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            logPartial((SignalIconModel) diffable, tableRowLoggerImpl);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            logFully(tableRowLoggerImpl);
        }

        public final void logFully(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "c");
            tableRowLoggerImpl.logChange(this.level, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            tableRowLoggerImpl.logChange(this.numberOfLevels, "numLevels");
            tableRowLoggerImpl.logChange("showExclamation", this.showExclamationMark);
            tableRowLoggerImpl.logChange("carrierNetworkChange", this.carrierNetworkChange);
        }

        public final void logPartial(SignalIconModel signalIconModel, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            if (!(signalIconModel instanceof Cellular)) {
                logFully(tableRowLoggerImpl);
                return;
            }
            int level = signalIconModel.getLevel();
            int i = this.level;
            if (level != i) {
                tableRowLoggerImpl.logChange(i, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            }
            Cellular cellular = (Cellular) signalIconModel;
            int i2 = cellular.numberOfLevels;
            int i3 = this.numberOfLevels;
            if (i2 != i3) {
                tableRowLoggerImpl.logChange(i3, "numLevels");
            }
            boolean z = cellular.showExclamationMark;
            boolean z2 = this.showExclamationMark;
            if (z != z2) {
                tableRowLoggerImpl.logChange("showExclamation", z2);
            }
            boolean z3 = cellular.carrierNetworkChange;
            boolean z4 = this.carrierNetworkChange;
            if (z3 != z4) {
                tableRowLoggerImpl.logChange("carrierNetworkChange", z4);
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Cellular(level=");
            sb.append(this.level);
            sb.append(", numberOfLevels=");
            sb.append(this.numberOfLevels);
            sb.append(", showExclamationMark=");
            sb.append(this.showExclamationMark);
            sb.append(", carrierNetworkChange=");
            sb.append(this.carrierNetworkChange);
            sb.append(", iconId=");
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.iconId, ")");
        }

        public /* synthetic */ Cellular(int i, int i2, boolean z, boolean z2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, z, z2, (i4 & 16) != 0 ? 0 : i3);
        }
    }
}
