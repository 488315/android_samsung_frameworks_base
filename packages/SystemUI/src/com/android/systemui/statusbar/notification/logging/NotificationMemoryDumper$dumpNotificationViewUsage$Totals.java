package com.android.systemui.statusbar.notification.logging;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
        return Integer.hashCode(this.softwareBitmapsPenalty) + ReorderTile$$ExternalSyntheticOutline0.m(this.customViews, ReorderTile$$ExternalSyntheticOutline0.m(this.style, ReorderTile$$ExternalSyntheticOutline0.m(this.largeIcon, Integer.hashCode(this.smallIcon) * 31, 31), 31), 31);
    }

    public final String toString() {
        int i = this.smallIcon;
        int i2 = this.largeIcon;
        int i3 = this.style;
        int i4 = this.customViews;
        int i5 = this.softwareBitmapsPenalty;
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "Totals(smallIcon=", ", largeIcon=", ", style=");
        ViewPager$$ExternalSyntheticOutline0.m(m, i3, ", customViews=", i4, ", softwareBitmapsPenalty=");
        return ReorderTile$$ExternalSyntheticOutline0.m(i5, ")", m);
    }

    public /* synthetic */ NotificationMemoryDumper$dumpNotificationViewUsage$Totals(int i, int i2, int i3, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? 0 : i, (i6 & 2) != 0 ? 0 : i2, (i6 & 4) != 0 ? 0 : i3, (i6 & 8) != 0 ? 0 : i4, (i6 & 16) != 0 ? 0 : i5);
    }
}
