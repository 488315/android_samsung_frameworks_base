package androidx.compose.ui.graphics.vector;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.pathData, this.name.hashCode() * 31, 31);
        Brush brush = this.fill;
        int m2 = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.fillAlpha, (m + (brush != null ? brush.hashCode() : 0)) * 31, 31);
        Brush brush2 = this.stroke;
        int m3 = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.strokeLineWidth, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.strokeAlpha, (m2 + (brush2 != null ? brush2.hashCode() : 0)) * 31, 31), 31);
        StrokeCap.Companion companion = StrokeCap.Companion;
        int m4 = ReorderTile$$ExternalSyntheticOutline0.m(this.strokeLineCap, m3, 31);
        StrokeJoin.Companion companion2 = StrokeJoin.Companion;
        int m5 = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.trimPathOffset, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.trimPathEnd, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.trimPathStart, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.strokeLineMiter, ReorderTile$$ExternalSyntheticOutline0.m(this.strokeLineJoin, m4, 31), 31), 31), 31), 31);
        PathFillType.Companion companion3 = PathFillType.Companion;
        return Integer.hashCode(this.pathFillType) + m5;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ VectorPath(java.lang.String r19, java.util.List r20, int r21, androidx.compose.ui.graphics.Brush r22, float r23, androidx.compose.ui.graphics.Brush r24, float r25, float r26, int r27, int r28, float r29, float r30, float r31, float r32, int r33, kotlin.jvm.internal.DefaultConstructorMarker r34) {
        /*
            r18 = this;
            r0 = r33
            r1 = r0 & 1
            if (r1 == 0) goto La
            java.lang.String r1 = ""
            r3 = r1
            goto Lc
        La:
            r3 = r19
        Lc:
            r1 = r0 & 8
            r2 = 0
            if (r1 == 0) goto L13
            r6 = r2
            goto L15
        L13:
            r6 = r22
        L15:
            r1 = r0 & 16
            r4 = 1065353216(0x3f800000, float:1.0)
            if (r1 == 0) goto L1d
            r7 = r4
            goto L1f
        L1d:
            r7 = r23
        L1f:
            r1 = r0 & 32
            if (r1 == 0) goto L25
            r8 = r2
            goto L27
        L25:
            r8 = r24
        L27:
            r1 = r0 & 64
            if (r1 == 0) goto L2d
            r9 = r4
            goto L2f
        L2d:
            r9 = r25
        L2f:
            r1 = r0 & 128(0x80, float:1.8E-43)
            r2 = 0
            if (r1 == 0) goto L36
            r10 = r2
            goto L38
        L36:
            r10 = r26
        L38:
            r1 = r0 & 256(0x100, float:3.59E-43)
            r5 = 0
            if (r1 == 0) goto L41
            kotlin.collections.EmptyList r1 = androidx.compose.ui.graphics.vector.VectorKt.EmptyPath
            r11 = r5
            goto L43
        L41:
            r11 = r27
        L43:
            r1 = r0 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto L4b
            kotlin.collections.EmptyList r1 = androidx.compose.ui.graphics.vector.VectorKt.EmptyPath
            r12 = r5
            goto L4d
        L4b:
            r12 = r28
        L4d:
            r1 = r0 & 1024(0x400, float:1.435E-42)
            if (r1 == 0) goto L55
            r1 = 1082130432(0x40800000, float:4.0)
            r13 = r1
            goto L57
        L55:
            r13 = r29
        L57:
            r1 = r0 & 2048(0x800, float:2.87E-42)
            if (r1 == 0) goto L5d
            r14 = r2
            goto L5f
        L5d:
            r14 = r30
        L5f:
            r1 = r0 & 4096(0x1000, float:5.74E-42)
            if (r1 == 0) goto L65
            r15 = r4
            goto L67
        L65:
            r15 = r31
        L67:
            r0 = r0 & 8192(0x2000, float:1.148E-41)
            if (r0 == 0) goto L6e
            r16 = r2
            goto L70
        L6e:
            r16 = r32
        L70:
            r17 = 0
            r2 = r18
            r4 = r20
            r5 = r21
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.VectorPath.<init>(java.lang.String, java.util.List, int, androidx.compose.ui.graphics.Brush, float, androidx.compose.ui.graphics.Brush, float, float, int, int, float, float, float, float, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
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
