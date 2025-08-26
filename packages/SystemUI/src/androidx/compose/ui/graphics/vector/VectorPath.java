package androidx.compose.ui.graphics.vector;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class VectorPath extends VectorNode {
    public final Brush fill;
    public final float fillAlpha;
    public final String name;
    public final List pathData;
    public final int pathFillType;
    public final Brush stroke;
    public final float strokeAlpha;
    public final int strokeLineCap;
    public final int strokeLineJoin;
    public final float strokeLineMiter;
    public final float strokeLineWidth;
    public final float trimPathEnd;
    public final float trimPathOffset;
    public final float trimPathStart;

    public /* synthetic */ VectorPath(String str, List list, int i, Brush brush, float f, Brush brush2, float f2, float f3, int i2, int i3, float f4, float f5, float f6, float f7, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, list, i, brush, f, brush2, f2, f3, i2, i3, f4, f5, f6, f7);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && VectorPath.class == obj.getClass()) {
            VectorPath vectorPath = (VectorPath) obj;
            if (Intrinsics.areEqual(this.name, vectorPath.name) && Intrinsics.areEqual(this.fill, vectorPath.fill) && this.fillAlpha == vectorPath.fillAlpha && Intrinsics.areEqual(this.stroke, vectorPath.stroke) && this.strokeAlpha == vectorPath.strokeAlpha && this.strokeLineWidth == vectorPath.strokeLineWidth) {
                StrokeCap.Companion companion = StrokeCap.Companion;
                if (this.strokeLineCap == vectorPath.strokeLineCap) {
                    StrokeJoin.Companion companion2 = StrokeJoin.Companion;
                    if (this.strokeLineJoin == vectorPath.strokeLineJoin && this.strokeLineMiter == vectorPath.strokeLineMiter && this.trimPathStart == vectorPath.trimPathStart && this.trimPathEnd == vectorPath.trimPathEnd && this.trimPathOffset == vectorPath.trimPathOffset) {
                        PathFillType.Companion companion3 = PathFillType.Companion;
                        return this.pathFillType == vectorPath.pathFillType && Intrinsics.areEqual(this.pathData, vectorPath.pathData);
                    }
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.pathData, this.name.hashCode() * 31, 31);
        Brush brush = this.fill;
        int iM2 = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.fillAlpha, (iM + (brush != null ? brush.hashCode() : 0)) * 31, 31);
        Brush brush2 = this.stroke;
        int iM3 = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.strokeLineWidth, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.strokeAlpha, (iM2 + (brush2 != null ? brush2.hashCode() : 0)) * 31, 31), 31);
        StrokeCap.Companion companion = StrokeCap.Companion;
        int iM4 = ReorderTile$$ExternalSyntheticOutline0.m(this.strokeLineCap, iM3, 31);
        StrokeJoin.Companion companion2 = StrokeJoin.Companion;
        int iM5 = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.trimPathOffset, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.trimPathEnd, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.trimPathStart, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.strokeLineMiter, ReorderTile$$ExternalSyntheticOutline0.m(this.strokeLineJoin, iM4, 31), 31), 31), 31), 31);
        PathFillType.Companion companion3 = PathFillType.Companion;
        return Integer.hashCode(this.pathFillType) + iM5;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ VectorPath(String str, List list, int i, Brush brush, float f, Brush brush2, float f2, float f3, int i2, int i3, float f4, float f5, float f6, float f7, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        int i5;
        int i6;
        String str2 = (i4 & 1) != 0 ? "" : str;
        Brush brush3 = (i4 & 8) != 0 ? null : brush;
        float f8 = (i4 & 16) != 0 ? 1.0f : f;
        Brush brush4 = (i4 & 32) != 0 ? null : brush2;
        float f9 = (i4 & 64) != 0 ? 1.0f : f2;
        float f10 = (i4 & 128) != 0 ? 0.0f : f3;
        if ((i4 & 256) != 0) {
            EmptyList emptyList = VectorKt.EmptyPath;
            i5 = 0;
        } else {
            i5 = i2;
        }
        if ((i4 & 512) != 0) {
            EmptyList emptyList2 = VectorKt.EmptyPath;
            i6 = 0;
        } else {
            i6 = i3;
        }
        this(str2, list, i, brush3, f8, brush4, f9, f10, i5, i6, (i4 & 1024) != 0 ? 4.0f : f4, (i4 & 2048) != 0 ? 0.0f : f5, (i4 & 4096) != 0 ? 1.0f : f6, (i4 & 8192) != 0 ? 0.0f : f7, null);
    }

    private VectorPath(String str, List<? extends PathNode> list, int i, Brush brush, float f, Brush brush2, float f2, float f3, int i2, int i3, float f4, float f5, float f6, float f7) {
        super(null);
        this.name = str;
        this.pathData = list;
        this.pathFillType = i;
        this.fill = brush;
        this.fillAlpha = f;
        this.stroke = brush2;
        this.strokeAlpha = f2;
        this.strokeLineWidth = f3;
        this.strokeLineCap = i2;
        this.strokeLineJoin = i3;
        this.strokeLineMiter = f4;
        this.trimPathStart = f5;
        this.trimPathEnd = f6;
        this.trimPathOffset = f7;
    }
}
