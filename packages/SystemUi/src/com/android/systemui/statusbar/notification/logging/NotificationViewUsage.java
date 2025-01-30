package com.android.systemui.statusbar.notification.logging;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationViewUsage {
    public final int customViews;
    public final int largeIcon;
    public final int smallIcon;
    public final int softwareBitmapsPenalty;
    public final int style;
    public final int systemIcons;
    public final ViewType viewType;

    public NotificationViewUsage(ViewType viewType, int i, int i2, int i3, int i4, int i5, int i6) {
        this.viewType = viewType;
        this.smallIcon = i;
        this.largeIcon = i2;
        this.systemIcons = i3;
        this.style = i4;
        this.customViews = i5;
        this.softwareBitmapsPenalty = i6;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationViewUsage)) {
            return false;
        }
        NotificationViewUsage notificationViewUsage = (NotificationViewUsage) obj;
        return this.viewType == notificationViewUsage.viewType && this.smallIcon == notificationViewUsage.smallIcon && this.largeIcon == notificationViewUsage.largeIcon && this.systemIcons == notificationViewUsage.systemIcons && this.style == notificationViewUsage.style && this.customViews == notificationViewUsage.customViews && this.softwareBitmapsPenalty == notificationViewUsage.softwareBitmapsPenalty;
    }

    public final int hashCode() {
        return Integer.hashCode(this.softwareBitmapsPenalty) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.customViews, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.style, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.systemIcons, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.largeIcon, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.smallIcon, this.viewType.hashCode() * 31, 31), 31), 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NotificationViewUsage(viewType=");
        sb.append(this.viewType);
        sb.append(", smallIcon=");
        sb.append(this.smallIcon);
        sb.append(", largeIcon=");
        sb.append(this.largeIcon);
        sb.append(", systemIcons=");
        sb.append(this.systemIcons);
        sb.append(", style=");
        sb.append(this.style);
        sb.append(", customViews=");
        sb.append(this.customViews);
        sb.append(", softwareBitmapsPenalty=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.softwareBitmapsPenalty, ")");
    }
}
