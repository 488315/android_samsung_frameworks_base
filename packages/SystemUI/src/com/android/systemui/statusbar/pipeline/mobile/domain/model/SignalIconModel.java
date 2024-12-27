package com.android.systemui.statusbar.pipeline.mobile.domain.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface SignalIconModel extends Diffable {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Companion();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel
        public final int getLevel() {
            return this.level;
        }

        public final int hashCode() {
            return this.icon.hashCode() + (Integer.hashCode(this.level) * 31);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel
        public final void logFully(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("numLevels", "3");
            tableRowLoggerImpl.logChange("type", "s");
            tableRowLoggerImpl.logChange(this.level, ActionResults.RESULT_SET_VOLUME_SUCCESS);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel
        public final void logPartial(SignalIconModel signalIconModel, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            if (!(signalIconModel instanceof Satellite)) {
                logFully(tableRowLoggerImpl);
                return;
            }
            int i = ((Satellite) signalIconModel).level;
            int i2 = this.level;
            if (i != i2) {
                tableRowLoggerImpl.logChange(i2, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            }
        }

        public final String toString() {
            return "Satellite(level=" + this.level + ", icon=" + this.icon + ")";
        }
    }

    static {
        int i = Companion.$r8$clinit;
    }

    int getLevel();

    @Override // com.android.systemui.log.table.Diffable
    default void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        logPartial((SignalIconModel) obj, tableRowLoggerImpl);
    }

    @Override // com.android.systemui.log.table.Diffable
    default void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        logFully(tableRowLoggerImpl);
    }

    void logFully(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl);

    void logPartial(SignalIconModel signalIconModel, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel
        public final int getLevel() {
            return this.level;
        }

        public final int hashCode() {
            return Integer.hashCode(this.iconId) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.numberOfLevels, Integer.hashCode(this.level) * 31, 31), 31, this.showExclamationMark), 31, this.carrierNetworkChange);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel
        public final void logFully(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("type", "c");
            tableRowLoggerImpl.logChange(this.level, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            tableRowLoggerImpl.logChange(this.numberOfLevels, "numLevels");
            tableRowLoggerImpl.logChange("showExclamation", this.showExclamationMark);
            tableRowLoggerImpl.logChange("carrierNetworkChange", this.carrierNetworkChange);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel
        public final void logPartial(SignalIconModel signalIconModel, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            if (!(signalIconModel instanceof Cellular)) {
                logFully(tableRowLoggerImpl);
                return;
            }
            int i = ((Cellular) signalIconModel).level;
            int i2 = this.level;
            if (i != i2) {
                tableRowLoggerImpl.logChange(i2, ActionResults.RESULT_SET_VOLUME_SUCCESS);
            }
            Cellular cellular = (Cellular) signalIconModel;
            int i3 = cellular.numberOfLevels;
            int i4 = this.numberOfLevels;
            if (i3 != i4) {
                tableRowLoggerImpl.logChange(i4, "numLevels");
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
            int i5 = cellular.iconId;
            int i6 = this.iconId;
            if (i5 != i6) {
                tableRowLoggerImpl.logChange(i6, "iconId");
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
            return Anchor$$ExternalSyntheticOutline0.m(this.iconId, ")", sb);
        }

        public /* synthetic */ Cellular(int i, int i2, boolean z, boolean z2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, z, z2, (i4 & 16) != 0 ? 0 : i3);
        }
    }
}
