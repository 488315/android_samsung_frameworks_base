package com.samsung.sesl.compose.foundation.shape;

import android.graphics.Matrix;
import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.PathOperation;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.vector.PathNode;
import androidx.compose.ui.graphics.vector.PathParserKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.sesl.compose.utils.ext.PathNodeExtKt;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslRoundedCornerShape implements Shape {
    public final CornerSize bottomEnd;
    public final CornerSize bottomStart;
    public final boolean inverse;
    public final CornerSize topEnd;
    public final CornerSize topStart;

    public SeslRoundedCornerShape(CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, CornerSize cornerSize4, boolean z) {
        this.topStart = cornerSize;
        this.topEnd = cornerSize2;
        this.bottomEnd = cornerSize3;
        this.bottomStart = cornerSize4;
        this.inverse = z;
    }

    /* renamed from: getSmoothCornerPathNodes-1ivO6K8, reason: not valid java name */
    public static List m3337getSmoothCornerPathNodes1ivO6K8(float f, long j, float f2, long j2) {
        Iterable iterable;
        if (Size.m418isEmptyimpl(j)) {
            iterable = EmptyList.INSTANCE;
        } else {
            float m416getMinDimensionimpl = Size.m416getMinDimensionimpl(j) / 2.0f;
            float coerceIn = RangesKt___RangesKt.coerceIn(f, 0.0f, m416getMinDimensionimpl);
            float f3 = coerceIn / m416getMinDimensionimpl;
            float min = f3 > 0.5f ? 1.0f - (Math.min(1.0f, (f3 - 0.5f) / 0.4f) * 0.13877845f) : 1.0f;
            float min2 = ((double) f3) > 0.6d ? (Math.min(1.0f, (f3 - 0.6f) / 0.3f) * 0.042454004f) + 1 : 1.0f;
            float m416getMinDimensionimpl2 = ((Size.m416getMinDimensionimpl(j) / 2.0f) / coerceIn) * 100.0f;
            float f4 = min * 128.19f;
            float f5 = min2 * 83.62f;
            List asList = Arrays.asList(new PathNode.LineTo(0.0f, Math.min(m416getMinDimensionimpl2, f4)), new PathNode.CurveTo(0.0f, f5, 4.64f, 67.45f, 13.36f, 51.16f), new PathNode.CurveTo(22.07f, 34.86f, 34.86f, 22.07f, 51.16f, 13.36f), new PathNode.CurveTo(67.45f, 4.64f, f5, 0.0f, Math.min(m416getMinDimensionimpl2, f4), 0.0f), new PathNode.LineTo(Math.min(m416getMinDimensionimpl2, f4), 0.0f));
            Matrix matrix = new Matrix();
            float f6 = coerceIn / 100.0f;
            matrix.setScale(f6, f6);
            List list = asList;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(PathNodeExtKt.transform((PathNode) it.next(), matrix));
            }
            iterable = arrayList;
        }
        Matrix matrix2 = new Matrix();
        float f7 = (-f) / 2.0f;
        matrix2.preTranslate(f7, f7);
        matrix2.postRotate(f2);
        float f8 = f / 2.0f;
        matrix2.postTranslate(f8, f8);
        Matrix matrix3 = new Matrix();
        matrix3.setTranslate(Offset.m398getXimpl(j2), Offset.m399getYimpl(j2));
        Iterable iterable2 = iterable;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable2, 10));
        Iterator it2 = iterable2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(PathNodeExtKt.transform((PathNode) it2.next(), matrix2));
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            arrayList3.add(PathNodeExtKt.transform((PathNode) obj, matrix3));
        }
        return arrayList3;
    }

    @Override // androidx.compose.ui.graphics.Shape
    /* renamed from: createOutline-Pq9zytI */
    public final Outline mo40createOutlinePq9zytI(long j, LayoutDirection layoutDirection, Density density) {
        List singletonList;
        float f;
        List singletonList2;
        AndroidPath path;
        Path path2;
        float mo184toPxTmRCtEA = this.topStart.mo184toPxTmRCtEA(density, j);
        float f2 = 2;
        float min = Math.min(Size.m417getWidthimpl(j), Size.m415getHeightimpl(j)) / f2;
        if (mo184toPxTmRCtEA > min) {
            mo184toPxTmRCtEA = min;
        }
        float mo184toPxTmRCtEA2 = this.topEnd.mo184toPxTmRCtEA(density, j);
        float min2 = Math.min(Size.m417getWidthimpl(j), Size.m415getHeightimpl(j)) / f2;
        if (mo184toPxTmRCtEA2 <= min2) {
            min2 = mo184toPxTmRCtEA2;
        }
        float mo184toPxTmRCtEA3 = this.bottomStart.mo184toPxTmRCtEA(density, j);
        float min3 = Math.min(Size.m417getWidthimpl(j), Size.m415getHeightimpl(j)) / f2;
        if (mo184toPxTmRCtEA3 <= min3) {
            min3 = mo184toPxTmRCtEA3;
        }
        float mo184toPxTmRCtEA4 = this.bottomEnd.mo184toPxTmRCtEA(density, j);
        float min4 = Math.min(Size.m417getWidthimpl(j), Size.m415getHeightimpl(j)) / f2;
        float f3 = mo184toPxTmRCtEA4 > min4 ? min4 : mo184toPxTmRCtEA4;
        float m417getWidthimpl = Size.m417getWidthimpl(j);
        float m415getHeightimpl = Size.m415getHeightimpl(j);
        List[] listArr = new List[9];
        listArr[0] = Collections.singletonList(new PathNode.MoveTo(0.0f, mo184toPxTmRCtEA));
        if (mo184toPxTmRCtEA > 0.0f) {
            Offset.Companion.getClass();
            singletonList = m3337getSmoothCornerPathNodes1ivO6K8(mo184toPxTmRCtEA, j, 0.0f, 0L);
        } else {
            singletonList = Collections.singletonList(new PathNode.LineTo(0.0f, 0.0f));
        }
        listArr[1] = singletonList;
        float f4 = m417getWidthimpl - min2;
        listArr[2] = Collections.singletonList(new PathNode.LineTo(f4, 0.0f));
        listArr[3] = min2 > 0.0f ? m3337getSmoothCornerPathNodes1ivO6K8(min2, j, 90.0f, OffsetKt.Offset(f4, 0.0f)) : Collections.singletonList(new PathNode.LineTo(m417getWidthimpl, 0.0f));
        float f5 = m415getHeightimpl - f3;
        listArr[4] = Collections.singletonList(new PathNode.LineTo(m417getWidthimpl, f5));
        listArr[5] = f3 > 0.0f ? m3337getSmoothCornerPathNodes1ivO6K8(f3, j, 180.0f, OffsetKt.Offset(m417getWidthimpl - f3, f5)) : Collections.singletonList(new PathNode.LineTo(m417getWidthimpl, m415getHeightimpl));
        listArr[6] = Collections.singletonList(new PathNode.LineTo(min3, m415getHeightimpl));
        if (min3 > 0.0f) {
            f = min3;
            singletonList2 = m3337getSmoothCornerPathNodes1ivO6K8(f, j, 270.0f, OffsetKt.Offset(0.0f, m415getHeightimpl - min3));
        } else {
            f = min3;
            singletonList2 = Collections.singletonList(new PathNode.LineTo(0.0f, m415getHeightimpl));
        }
        listArr[7] = singletonList2;
        PathNode.LineTo lineTo = new PathNode.LineTo(0.0f, f);
        PathNode.Close close = PathNode.Close.INSTANCE;
        listArr[8] = Arrays.asList(lineTo, close);
        path = PathParserKt.toPath(CollectionsKt__IterablesKt.flatten(Arrays.asList(listArr)), AndroidPath_androidKt.Path());
        if (this.inverse) {
            path2 = PathParserKt.toPath(Arrays.asList(new PathNode.MoveTo(0.0f, 0.0f), new PathNode.LineTo(m417getWidthimpl, 0.0f), new PathNode.LineTo(m417getWidthimpl, m415getHeightimpl), new PathNode.LineTo(0.0f, m415getHeightimpl), new PathNode.LineTo(0.0f, 0.0f), close), AndroidPath_androidKt.Path());
            PathOperation.Companion.getClass();
            path.m443opN5in7k0(path2, path, 0);
        }
        return new Outline.Generic(path);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslRoundedCornerShape)) {
            return false;
        }
        SeslRoundedCornerShape seslRoundedCornerShape = (SeslRoundedCornerShape) obj;
        return Intrinsics.areEqual(this.topStart, seslRoundedCornerShape.topStart) && Intrinsics.areEqual(this.topEnd, seslRoundedCornerShape.topEnd) && Intrinsics.areEqual(this.bottomEnd, seslRoundedCornerShape.bottomEnd) && Intrinsics.areEqual(this.bottomStart, seslRoundedCornerShape.bottomStart) && this.inverse == seslRoundedCornerShape.inverse;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.inverse) + ((this.bottomStart.hashCode() + ((this.bottomEnd.hashCode() + ((this.topEnd.hashCode() + (this.topStart.hashCode() * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SeslRoundedCornerShape(topStart=");
        sb.append(this.topStart);
        sb.append(", topEnd=");
        sb.append(this.topEnd);
        sb.append(", bottomEnd=");
        sb.append(this.bottomEnd);
        sb.append(", bottomStart=");
        sb.append(this.bottomStart);
        sb.append(", inverse=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.inverse, ")");
    }

    public /* synthetic */ SeslRoundedCornerShape(CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, CornerSize cornerSize4, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(cornerSize, cornerSize2, cornerSize3, cornerSize4, (i & 16) != 0 ? false : z);
    }
}
