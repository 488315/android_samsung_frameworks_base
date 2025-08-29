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
    public static List m3354getSmoothCornerPathNodes1ivO6K8(float f, long j, float f2, long j2) {
        Iterable iterable;
        if (Size.m420isEmptyimpl(j)) {
            iterable = EmptyList.INSTANCE;
        } else {
            float fM418getMinDimensionimpl = Size.m418getMinDimensionimpl(j) / 2.0f;
            float fCoerceIn = RangesKt___RangesKt.coerceIn(f, 0.0f, fM418getMinDimensionimpl);
            float f3 = fCoerceIn / fM418getMinDimensionimpl;
            float fMin = f3 > 0.5f ? 1.0f - (Math.min(1.0f, (f3 - 0.5f) / 0.4f) * 0.13877845f) : 1.0f;
            float fMin2 = ((double) f3) > 0.6d ? (Math.min(1.0f, (f3 - 0.6f) / 0.3f) * 0.042454004f) + 1 : 1.0f;
            float fM418getMinDimensionimpl2 = ((Size.m418getMinDimensionimpl(j) / 2.0f) / fCoerceIn) * 100.0f;
            float f4 = fMin * 128.19f;
            float f5 = fMin2 * 83.62f;
            List listAsList = Arrays.asList(new PathNode.LineTo(0.0f, Math.min(fM418getMinDimensionimpl2, f4)), new PathNode.CurveTo(0.0f, f5, 4.64f, 67.45f, 13.36f, 51.16f), new PathNode.CurveTo(22.07f, 34.86f, 34.86f, 22.07f, 51.16f, 13.36f), new PathNode.CurveTo(67.45f, 4.64f, f5, 0.0f, Math.min(fM418getMinDimensionimpl2, f4), 0.0f), new PathNode.LineTo(Math.min(fM418getMinDimensionimpl2, f4), 0.0f));
            Matrix matrix = new Matrix();
            float f6 = fCoerceIn / 100.0f;
            matrix.setScale(f6, f6);
            List list = listAsList;
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
        matrix3.setTranslate(Offset.m400getXimpl(j2), Offset.m401getYimpl(j2));
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
    public final Outline mo41createOutlinePq9zytI(long j, LayoutDirection layoutDirection, Density density) {
        List listSingletonList;
        float f;
        List listSingletonList2;
        float fMo185toPxTmRCtEA = this.topStart.mo185toPxTmRCtEA(density, j);
        float f2 = 2;
        float fMin = Math.min(Size.m419getWidthimpl(j), Size.m417getHeightimpl(j)) / f2;
        if (fMo185toPxTmRCtEA > fMin) {
            fMo185toPxTmRCtEA = fMin;
        }
        float fMo185toPxTmRCtEA2 = this.topEnd.mo185toPxTmRCtEA(density, j);
        float fMin2 = Math.min(Size.m419getWidthimpl(j), Size.m417getHeightimpl(j)) / f2;
        if (fMo185toPxTmRCtEA2 <= fMin2) {
            fMin2 = fMo185toPxTmRCtEA2;
        }
        float fMo185toPxTmRCtEA3 = this.bottomStart.mo185toPxTmRCtEA(density, j);
        float fMin3 = Math.min(Size.m419getWidthimpl(j), Size.m417getHeightimpl(j)) / f2;
        if (fMo185toPxTmRCtEA3 <= fMin3) {
            fMin3 = fMo185toPxTmRCtEA3;
        }
        float fMo185toPxTmRCtEA4 = this.bottomEnd.mo185toPxTmRCtEA(density, j);
        float fMin4 = Math.min(Size.m419getWidthimpl(j), Size.m417getHeightimpl(j)) / f2;
        float f3 = fMo185toPxTmRCtEA4 > fMin4 ? fMin4 : fMo185toPxTmRCtEA4;
        float fM419getWidthimpl = Size.m419getWidthimpl(j);
        float fM417getHeightimpl = Size.m417getHeightimpl(j);
        List[] listArr = new List[9];
        listArr[0] = Collections.singletonList(new PathNode.MoveTo(0.0f, fMo185toPxTmRCtEA));
        if (fMo185toPxTmRCtEA > 0.0f) {
            Offset.Companion.getClass();
            listSingletonList = m3354getSmoothCornerPathNodes1ivO6K8(fMo185toPxTmRCtEA, j, 0.0f, 0L);
        } else {
            listSingletonList = Collections.singletonList(new PathNode.LineTo(0.0f, 0.0f));
        }
        listArr[1] = listSingletonList;
        float f4 = fM419getWidthimpl - fMin2;
        listArr[2] = Collections.singletonList(new PathNode.LineTo(f4, 0.0f));
        listArr[3] = fMin2 > 0.0f ? m3354getSmoothCornerPathNodes1ivO6K8(fMin2, j, 90.0f, OffsetKt.Offset(f4, 0.0f)) : Collections.singletonList(new PathNode.LineTo(fM419getWidthimpl, 0.0f));
        float f5 = fM417getHeightimpl - f3;
        listArr[4] = Collections.singletonList(new PathNode.LineTo(fM419getWidthimpl, f5));
        listArr[5] = f3 > 0.0f ? m3354getSmoothCornerPathNodes1ivO6K8(f3, j, 180.0f, OffsetKt.Offset(fM419getWidthimpl - f3, f5)) : Collections.singletonList(new PathNode.LineTo(fM419getWidthimpl, fM417getHeightimpl));
        listArr[6] = Collections.singletonList(new PathNode.LineTo(fMin3, fM417getHeightimpl));
        if (fMin3 > 0.0f) {
            f = fMin3;
            listSingletonList2 = m3354getSmoothCornerPathNodes1ivO6K8(f, j, 270.0f, OffsetKt.Offset(0.0f, fM417getHeightimpl - fMin3));
        } else {
            f = fMin3;
            listSingletonList2 = Collections.singletonList(new PathNode.LineTo(0.0f, fM417getHeightimpl));
        }
        listArr[7] = listSingletonList2;
        PathNode.LineTo lineTo = new PathNode.LineTo(0.0f, f);
        PathNode.Close close = PathNode.Close.INSTANCE;
        listArr[8] = Arrays.asList(lineTo, close);
        AndroidPath path = PathParserKt.toPath(CollectionsKt__IterablesKt.flatten(Arrays.asList(listArr)), AndroidPath_androidKt.Path());
        if (this.inverse) {
            Path path2 = PathParserKt.toPath(Arrays.asList(new PathNode.MoveTo(0.0f, 0.0f), new PathNode.LineTo(fM419getWidthimpl, 0.0f), new PathNode.LineTo(fM419getWidthimpl, fM417getHeightimpl), new PathNode.LineTo(0.0f, fM417getHeightimpl), new PathNode.LineTo(0.0f, 0.0f), close), AndroidPath_androidKt.Path());
            PathOperation.Companion.getClass();
            path.m445opN5in7k0(path2, path, 0);
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
