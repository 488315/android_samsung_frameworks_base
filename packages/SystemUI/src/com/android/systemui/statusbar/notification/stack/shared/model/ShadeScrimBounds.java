package com.android.systemui.statusbar.notification.stack.shared.model;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ShadeScrimBounds {
    public final float bottom;
    public final float left;
    public final float right;
    public final float top;

    public ShadeScrimBounds() {
        this(0.0f, 0.0f, 0.0f, 0.0f, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShadeScrimBounds)) {
            return false;
        }
        ShadeScrimBounds shadeScrimBounds = (ShadeScrimBounds) obj;
        return Float.compare(this.left, shadeScrimBounds.left) == 0 && Float.compare(this.top, shadeScrimBounds.top) == 0 && Float.compare(this.right, shadeScrimBounds.right) == 0 && Float.compare(this.bottom, shadeScrimBounds.bottom) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.bottom) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.right, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.top, Float.hashCode(this.left) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ShadeScrimBounds(left=");
        sb.append(this.left);
        sb.append(", top=");
        sb.append(this.top);
        sb.append(", right=");
        sb.append(this.right);
        sb.append(", bottom=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(sb, this.bottom, ")");
    }

    public ShadeScrimBounds(float f, float f2, float f3, float f4) {
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    public /* synthetic */ ShadeScrimBounds(float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2, (i & 4) != 0 ? 0.0f : f3, (i & 8) != 0 ? 0.0f : f4);
    }
}
