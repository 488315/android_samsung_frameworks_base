package com.android.systemui.statusbar.phone.ongoingactivity;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class MediaOngoingActivityInfo {
    public final Icon appIcon;
    public final int bgColor;
    public final PendingIntent clickIntent;
    public final String songTitle;

    public MediaOngoingActivityInfo(String str, int i, Icon icon, PendingIntent pendingIntent) {
        this.songTitle = str;
        this.bgColor = i;
        this.appIcon = icon;
        this.clickIntent = pendingIntent;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaOngoingActivityInfo)) {
            return false;
        }
        MediaOngoingActivityInfo mediaOngoingActivityInfo = (MediaOngoingActivityInfo) obj;
        return Intrinsics.areEqual(this.songTitle, mediaOngoingActivityInfo.songTitle) && this.bgColor == mediaOngoingActivityInfo.bgColor && Intrinsics.areEqual(this.appIcon, mediaOngoingActivityInfo.appIcon) && Intrinsics.areEqual(this.clickIntent, mediaOngoingActivityInfo.clickIntent);
    }

    public final int hashCode() {
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.bgColor, this.songTitle.hashCode() * 31, 31);
        Icon icon = this.appIcon;
        int iHashCode = (iM + (icon == null ? 0 : icon.hashCode())) * 31;
        PendingIntent pendingIntent = this.clickIntent;
        return iHashCode + (pendingIntent != null ? pendingIntent.hashCode() : 0);
    }

    public final String toString() {
        return "MediaOngoingActivityInfo(songTitle=" + this.songTitle + ", bgColor=" + this.bgColor + ", appIcon=" + this.appIcon + ", clickIntent=" + this.clickIntent + ")";
    }
}
