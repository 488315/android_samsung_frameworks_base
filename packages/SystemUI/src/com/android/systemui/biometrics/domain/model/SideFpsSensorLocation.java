package com.android.systemui.biometrics.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;

public final class SideFpsSensorLocation {
    public final boolean isSensorVerticalInDefaultOrientation;
    public final int left;
    public final int length;
    public final int top;

    public SideFpsSensorLocation(int i, int i2, int i3, boolean z) {
        this.left = i;
        this.top = i2;
        this.length = i3;
        this.isSensorVerticalInDefaultOrientation = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SideFpsSensorLocation)) {
            return false;
        }
        SideFpsSensorLocation sideFpsSensorLocation = (SideFpsSensorLocation) obj;
        return this.left == sideFpsSensorLocation.left && this.top == sideFpsSensorLocation.top && this.length == sideFpsSensorLocation.length && this.isSensorVerticalInDefaultOrientation == sideFpsSensorLocation.isSensorVerticalInDefaultOrientation;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isSensorVerticalInDefaultOrientation) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.length, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.top, Integer.hashCode(this.left) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SideFpsSensorLocation(left=");
        sb.append(this.left);
        sb.append(", top=");
        sb.append(this.top);
        sb.append(", length=");
        sb.append(this.length);
        sb.append(", isSensorVerticalInDefaultOrientation=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isSensorVerticalInDefaultOrientation, ")");
    }
}
