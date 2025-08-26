package com.android.systemui.brightness.ui.viewmodel;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes.dex */
public final class BrightnessIcons {
    public final int brightnessHigh;
    public final int brightnessLow;
    public final int brightnessMid;

    public BrightnessIcons(int i, int i2, int i3) {
        this.brightnessLow = i;
        this.brightnessMid = i2;
        this.brightnessHigh = i3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BrightnessIcons)) {
            return false;
        }
        BrightnessIcons brightnessIcons = (BrightnessIcons) obj;
        return this.brightnessLow == brightnessIcons.brightnessLow && this.brightnessMid == brightnessIcons.brightnessMid && this.brightnessHigh == brightnessIcons.brightnessHigh;
    }

    public final int hashCode() {
        return Integer.hashCode(this.brightnessHigh) + ReorderTile$$ExternalSyntheticOutline0.m(this.brightnessMid, Integer.hashCode(this.brightnessLow) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BrightnessIcons(brightnessLow=");
        sb.append(this.brightnessLow);
        sb.append(", brightnessMid=");
        sb.append(this.brightnessMid);
        sb.append(", brightnessHigh=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.brightnessHigh, ")", sb);
    }
}
