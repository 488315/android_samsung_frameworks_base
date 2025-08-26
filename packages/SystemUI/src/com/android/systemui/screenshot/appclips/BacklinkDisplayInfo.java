package com.android.systemui.screenshot.appclips;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class BacklinkDisplayInfo {
    public final Drawable appIcon;
    public String displayLabel;

    public BacklinkDisplayInfo(Drawable drawable, String str) {
        this.appIcon = drawable;
        this.displayLabel = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BacklinkDisplayInfo)) {
            return false;
        }
        BacklinkDisplayInfo backlinkDisplayInfo = (BacklinkDisplayInfo) obj;
        return Intrinsics.areEqual(this.appIcon, backlinkDisplayInfo.appIcon) && Intrinsics.areEqual(this.displayLabel, backlinkDisplayInfo.displayLabel);
    }

    public final int hashCode() {
        return this.displayLabel.hashCode() + (this.appIcon.hashCode() * 31);
    }

    public final String toString() {
        return "BacklinkDisplayInfo(appIcon=" + this.appIcon + ", displayLabel=" + this.displayLabel + ")";
    }
}
