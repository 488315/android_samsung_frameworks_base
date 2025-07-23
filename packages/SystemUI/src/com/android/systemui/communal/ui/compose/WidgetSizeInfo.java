package com.android.systemui.communal.ui.compose;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WidgetSizeInfo {
    public final int maxHeightPx;
    public final int minHeightPx;

    public WidgetSizeInfo(int i, int i2) {
        this.minHeightPx = i;
        this.maxHeightPx = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WidgetSizeInfo)) {
            return false;
        }
        WidgetSizeInfo widgetSizeInfo = (WidgetSizeInfo) obj;
        return this.minHeightPx == widgetSizeInfo.minHeightPx && this.maxHeightPx == widgetSizeInfo.maxHeightPx;
    }

    public final int hashCode() {
        return Integer.hashCode(this.maxHeightPx) + (Integer.hashCode(this.minHeightPx) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("WidgetSizeInfo(minHeightPx=");
        sb.append(this.minHeightPx);
        sb.append(", maxHeightPx=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.maxHeightPx, ")", sb);
    }
}
