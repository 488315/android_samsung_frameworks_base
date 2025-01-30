package com.android.systemui.statusbar.pipeline.mobile.domain.model;

import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface NetworkTypeIconModel extends Diffable {
    int[] getActivityIcons();

    int getContentDescription();

    int getIconId();

    String getName();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DefaultIcon implements NetworkTypeIconModel {
        public final int[] activityIcons;
        public final int contentDescription;
        public final SignalIcon$MobileIconGroup iconGroup;
        public final int iconId;
        public final String name;

        public DefaultIcon(SignalIcon$MobileIconGroup signalIcon$MobileIconGroup) {
            this.iconGroup = signalIcon$MobileIconGroup;
            this.contentDescription = signalIcon$MobileIconGroup.dataContentDescription;
            this.iconId = signalIcon$MobileIconGroup.dataType;
            this.name = signalIcon$MobileIconGroup.name;
            this.activityIcons = signalIcon$MobileIconGroup.activityIcons;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DefaultIcon) && Intrinsics.areEqual(this.iconGroup, ((DefaultIcon) obj).iconGroup);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel
        public final int[] getActivityIcons() {
            return this.activityIcons;
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel
        public final int getContentDescription() {
            return this.contentDescription;
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel
        public final int getIconId() {
            return this.iconId;
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel
        public final String getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.iconGroup.hashCode();
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            NetworkTypeIconModel networkTypeIconModel = (NetworkTypeIconModel) diffable;
            boolean z = networkTypeIconModel instanceof DefaultIcon;
            String str = this.name;
            if (z && Intrinsics.areEqual(networkTypeIconModel.getName(), str)) {
                return;
            }
            tableRowLoggerImpl.logChange("networkTypeIcon", str);
        }

        public final String toString() {
            return "DefaultIcon(iconGroup=" + this.iconGroup + ")";
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OverriddenIcon implements NetworkTypeIconModel {
        public final int[] activityIcons;
        public final int contentDescription;
        public final SignalIcon$MobileIconGroup iconGroup;
        public final int iconId;
        public final String name;

        public OverriddenIcon(SignalIcon$MobileIconGroup signalIcon$MobileIconGroup, int i) {
            this.iconGroup = signalIcon$MobileIconGroup;
            this.iconId = i;
            this.contentDescription = signalIcon$MobileIconGroup.dataContentDescription;
            this.name = signalIcon$MobileIconGroup.name;
            this.activityIcons = signalIcon$MobileIconGroup.activityIcons;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof OverriddenIcon)) {
                return false;
            }
            OverriddenIcon overriddenIcon = (OverriddenIcon) obj;
            return Intrinsics.areEqual(this.iconGroup, overriddenIcon.iconGroup) && this.iconId == overriddenIcon.iconId;
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel
        public final int[] getActivityIcons() {
            return this.activityIcons;
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel
        public final int getContentDescription() {
            return this.contentDescription;
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel
        public final int getIconId() {
            return this.iconId;
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel
        public final String getName() {
            return this.name;
        }

        public final int hashCode() {
            return Integer.hashCode(this.iconId) + (this.iconGroup.hashCode() * 31);
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            NetworkTypeIconModel networkTypeIconModel = (NetworkTypeIconModel) diffable;
            boolean z = networkTypeIconModel instanceof OverriddenIcon;
            String str = this.name;
            if (z && Intrinsics.areEqual(networkTypeIconModel.getName(), str) && networkTypeIconModel.getIconId() == this.iconId && Intrinsics.areEqual(networkTypeIconModel.getActivityIcons(), this.activityIcons)) {
                return;
            }
            tableRowLoggerImpl.logChange("networkTypeIcon", "Ovrd(" + str + ")");
        }

        public final String toString() {
            return "OverriddenIcon(iconGroup=" + this.iconGroup + ", iconId=" + this.iconId + ")";
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        }
    }
}
