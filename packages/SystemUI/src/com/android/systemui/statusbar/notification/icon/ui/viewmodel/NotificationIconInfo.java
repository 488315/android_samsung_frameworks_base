package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import android.graphics.drawable.Icon;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public final class NotificationIconInfo {
    public final String groupKey;
    public final String notifKey;
    public final Icon sourceIcon;

    public NotificationIconInfo(Icon icon, String str, String str2) {
        this.sourceIcon = icon;
        this.notifKey = str;
        this.groupKey = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationIconInfo)) {
            return false;
        }
        NotificationIconInfo notificationIconInfo = (NotificationIconInfo) obj;
        return Intrinsics.areEqual(this.sourceIcon, notificationIconInfo.sourceIcon) && Intrinsics.areEqual(this.notifKey, notificationIconInfo.notifKey) && Intrinsics.areEqual(this.groupKey, notificationIconInfo.groupKey);
    }

    public final int hashCode() {
        return this.groupKey.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.sourceIcon.hashCode() * 31, 31, this.notifKey);
    }

    public final String toString() {
        Icon icon = this.sourceIcon;
        StringBuilder sb = new StringBuilder("NotificationIconInfo(sourceIcon=");
        sb.append(icon);
        sb.append(", notifKey=");
        sb.append(this.notifKey);
        sb.append(", groupKey=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.groupKey, ")");
    }
}
