package androidx.compose.foundation.lazy.grid;

import java.util.List;
import kotlin.Unit;

/* loaded from: classes.dex */
public final class LazyGridMeasuredLine {
    public final int index;
    public final boolean isVertical;
    public final LazyGridMeasuredItem[] items;
    public final int mainAxisSize;
    public final int mainAxisSizeWithSpacings;
    public final int mainAxisSpacing;
    public final LazyGridSlots slots;
    public final List spans;

    public LazyGridMeasuredLine(int i, LazyGridMeasuredItem[] lazyGridMeasuredItemArr, LazyGridSlots lazyGridSlots, List<GridItemSpan> list, boolean z, int i2) {
        this.index = i;
        this.items = lazyGridMeasuredItemArr;
        this.slots = lazyGridSlots;
        this.spans = list;
        this.isVertical = z;
        this.mainAxisSpacing = i2;
        int iMax = 0;
        for (LazyGridMeasuredItem lazyGridMeasuredItem : lazyGridMeasuredItemArr) {
            iMax = Math.max(iMax, lazyGridMeasuredItem.mainAxisSize);
        }
        this.mainAxisSize = iMax;
        int i3 = iMax + this.mainAxisSpacing;
        this.mainAxisSizeWithSpacings = i3 >= 0 ? i3 : 0;
    }

    public final LazyGridMeasuredItem[] position(int i, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        LazyGridMeasuredItem[] lazyGridMeasuredItemArr = this.items;
        int length = lazyGridMeasuredItemArr.length;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        while (i8 < length) {
            LazyGridMeasuredItem lazyGridMeasuredItem = lazyGridMeasuredItemArr[i8];
            int i11 = i9 + 1;
            int i12 = (int) ((GridItemSpan) this.spans.get(i9)).packedValue;
            int i13 = this.slots.positions[i10];
            int i14 = this.index;
            boolean z = this.isVertical;
            int i15 = z ? i14 : i10;
            if (z) {
                i4 = i10;
                i7 = i;
                i5 = i2;
                i6 = i3;
            } else {
                i4 = i14;
                i5 = i2;
                i6 = i3;
                i7 = i;
            }
            lazyGridMeasuredItem.position(i7, i13, i5, i6, i15, i4);
            Unit unit = Unit.INSTANCE;
            i10 += i12;
            i8++;
            i9 = i11;
        }
        return lazyGridMeasuredItemArr;
    }
}
