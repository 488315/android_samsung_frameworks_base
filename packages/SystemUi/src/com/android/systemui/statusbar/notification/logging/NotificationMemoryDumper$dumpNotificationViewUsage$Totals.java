package com.android.systemui.statusbar.notification.logging;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationMemoryDumper$dumpNotificationViewUsage$Totals {
    public int customViews;
    public int largeIcon;
    public int smallIcon;
    public int softwareBitmapsPenalty;
    public int style;

    public NotificationMemoryDumper$dumpNotificationViewUsage$Totals(int i, int i2, int i3, int i4, int i5) {
        this.smallIcon = i;
        this.largeIcon = i2;
        this.style = i3;
        this.customViews = i4;
        this.softwareBitmapsPenalty = i5;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationMemoryDumper$dumpNotificationViewUsage$Totals)) {
            return false;
        }
        NotificationMemoryDumper$dumpNotificationViewUsage$Totals notificationMemoryDumper$dumpNotificationViewUsage$Totals = (NotificationMemoryDumper$dumpNotificationViewUsage$Totals) obj;
        return this.smallIcon == notificationMemoryDumper$dumpNotificationViewUsage$Totals.smallIcon && this.largeIcon == notificationMemoryDumper$dumpNotificationViewUsage$Totals.largeIcon && this.style == notificationMemoryDumper$dumpNotificationViewUsage$Totals.style && this.customViews == notificationMemoryDumper$dumpNotificationViewUsage$Totals.customViews && this.softwareBitmapsPenalty == notificationMemoryDumper$dumpNotificationViewUsage$Totals.softwareBitmapsPenalty;
    }

    public final int hashCode() {
        return Integer.hashCode(this.softwareBitmapsPenalty) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.customViews, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.style, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.largeIcon, Integer.hashCode(this.smallIcon) * 31, 31), 31), 31);
    }

    public final String toString() {
        int i = this.smallIcon;
        int i2 = this.largeIcon;
        int i3 = this.style;
        int i4 = this.customViews;
        int i5 = this.softwareBitmapsPenalty;
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("Totals(smallIcon=", i, ", largeIcon=", i2, ", style=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, i3, ", customViews=", i4, ", softwareBitmapsPenalty=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(m45m, i5, ")");
    }

    public /* synthetic */ NotificationMemoryDumper$dumpNotificationViewUsage$Totals(int i, int i2, int i3, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? 0 : i, (i6 & 2) != 0 ? 0 : i2, (i6 & 4) != 0 ? 0 : i3, (i6 & 8) != 0 ? 0 : i4, (i6 & 16) != 0 ? 0 : i5);
    }
}
