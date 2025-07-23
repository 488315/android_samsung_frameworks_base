package com.android.systemui.samsung.quicksetting.domain.model;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GridTileData {
    public final int spanHeight;
    public final int spanWidth;
    public final int spanX;
    public final int spanY;
    public final String spec;
    public final String type;

    public GridTileData(String str, String str2, int i, int i2, int i3, int i4) {
        this.type = str;
        this.spec = str2;
        this.spanX = i;
        this.spanY = i2;
        this.spanWidth = i3;
        this.spanHeight = i4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GridTileData)) {
            return false;
        }
        GridTileData gridTileData = (GridTileData) obj;
        return Intrinsics.areEqual(this.type, gridTileData.type) && Intrinsics.areEqual(this.spec, gridTileData.spec) && this.spanX == gridTileData.spanX && this.spanY == gridTileData.spanY && this.spanWidth == gridTileData.spanWidth && this.spanHeight == gridTileData.spanHeight;
    }

    public final int hashCode() {
        return Integer.hashCode(this.spanHeight) + ReorderTile$$ExternalSyntheticOutline0.m(this.spanWidth, ReorderTile$$ExternalSyntheticOutline0.m(this.spanY, ReorderTile$$ExternalSyntheticOutline0.m(this.spanX, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.type.hashCode() * 31, 31, this.spec), 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("GridTileData(type=");
        sb.append(this.type);
        sb.append(", spec=");
        sb.append(this.spec);
        sb.append(", spanX=");
        sb.append(this.spanX);
        sb.append(", spanY=");
        sb.append(this.spanY);
        sb.append(", spanWidth=");
        sb.append(this.spanWidth);
        sb.append(", spanHeight=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.spanHeight, ")", sb);
    }

    public /* synthetic */ GridTileData(String str, String str2, int i, int i2, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i5 & 2) != 0 ? "" : str2, (i5 & 4) != 0 ? -1 : i, (i5 & 8) != 0 ? -1 : i2, i3, i4);
    }
}
