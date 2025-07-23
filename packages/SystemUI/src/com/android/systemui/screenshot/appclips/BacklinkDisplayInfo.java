package com.android.systemui.screenshot.appclips;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
