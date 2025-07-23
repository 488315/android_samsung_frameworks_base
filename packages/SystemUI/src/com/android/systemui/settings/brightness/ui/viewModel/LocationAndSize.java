package com.android.systemui.settings.brightness.ui.viewModel;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LocationAndSize {
    public final int height;
    public final int width;
    public final int yOffsetFromContainer;
    public final int yOffsetFromWindow;

    public LocationAndSize() {
        this(0, 0, 0, 0, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocationAndSize)) {
            return false;
        }
        LocationAndSize locationAndSize = (LocationAndSize) obj;
        return this.yOffsetFromContainer == locationAndSize.yOffsetFromContainer && this.yOffsetFromWindow == locationAndSize.yOffsetFromWindow && this.width == locationAndSize.width && this.height == locationAndSize.height;
    }

    public final int hashCode() {
        return Integer.hashCode(this.height) + ReorderTile$$ExternalSyntheticOutline0.m(this.width, ReorderTile$$ExternalSyntheticOutline0.m(this.yOffsetFromWindow, Integer.hashCode(this.yOffsetFromContainer) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("LocationAndSize(yOffsetFromContainer=");
        sb.append(this.yOffsetFromContainer);
        sb.append(", yOffsetFromWindow=");
        sb.append(this.yOffsetFromWindow);
        sb.append(", width=");
        sb.append(this.width);
        sb.append(", height=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.height, ")", sb);
    }

    public LocationAndSize(int i, int i2, int i3, int i4) {
        this.yOffsetFromContainer = i;
        this.yOffsetFromWindow = i2;
        this.width = i3;
        this.height = i4;
    }

    public /* synthetic */ LocationAndSize(int i, int i2, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? 0 : i, (i5 & 2) != 0 ? 0 : i2, (i5 & 4) != 0 ? 0 : i3, (i5 & 8) != 0 ? 0 : i4);
    }
}
