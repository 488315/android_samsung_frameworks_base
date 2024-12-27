package com.android.systemui.settings.brightness.ui.viewModel;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LocationAndSize {
    public final int height;
    public final int width;
    public final int yOffset;

    public LocationAndSize() {
        this(0, 0, 0, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocationAndSize)) {
            return false;
        }
        LocationAndSize locationAndSize = (LocationAndSize) obj;
        return this.yOffset == locationAndSize.yOffset && this.width == locationAndSize.width && this.height == locationAndSize.height;
    }

    public final int hashCode() {
        return Integer.hashCode(this.height) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.width, Integer.hashCode(this.yOffset) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("LocationAndSize(yOffset=");
        sb.append(this.yOffset);
        sb.append(", width=");
        sb.append(this.width);
        sb.append(", height=");
        return Anchor$$ExternalSyntheticOutline0.m(this.height, ")", sb);
    }

    public LocationAndSize(int i, int i2, int i3) {
        this.yOffset = i;
        this.width = i2;
        this.height = i3;
    }

    public /* synthetic */ LocationAndSize(int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 0 : i, (i4 & 2) != 0 ? 0 : i2, (i4 & 4) != 0 ? 0 : i3);
    }
}
