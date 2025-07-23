package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.lazy.grid.LazyGridSpanLayoutProvider;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.InlineClassHelperKt;
import java.util.List;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyGridMeasuredLineProvider {
    public final int gridItemsCount;
    public final boolean isVertical;
    public final LazyGridMeasuredItemProvider measuredItemProvider;
    public final LazyGridSlots slots;
    public final int spaceBetweenLines;
    public final LazyGridSpanLayoutProvider spanLayoutProvider;

    public LazyGridMeasuredLineProvider(boolean z, LazyGridSlots lazyGridSlots, int i, int i2, LazyGridMeasuredItemProvider lazyGridMeasuredItemProvider, LazyGridSpanLayoutProvider lazyGridSpanLayoutProvider) {
        this.isVertical = z;
        this.slots = lazyGridSlots;
        this.gridItemsCount = i;
        this.spaceBetweenLines = i2;
        this.measuredItemProvider = lazyGridMeasuredItemProvider;
        this.spanLayoutProvider = lazyGridSpanLayoutProvider;
    }

    /* renamed from: childConstraints-JhjzzOo$foundation_release, reason: not valid java name */
    public final long m163childConstraintsJhjzzOo$foundation_release(int i, int i2) {
        int i3;
        LazyGridSlots lazyGridSlots = this.slots;
        if (i2 == 1) {
            i3 = lazyGridSlots.sizes[i];
        } else {
            int i4 = (i2 + i) - 1;
            int[] iArr = lazyGridSlots.positions;
            i3 = (iArr[i4] + lazyGridSlots.sizes[i4]) - iArr[i];
        }
        if (i3 < 0) {
            i3 = 0;
        }
        if (this.isVertical) {
            Constraints.Companion.getClass();
            return Constraints.Companion.m828fixedWidthOenEA2s(i3);
        }
        Constraints.Companion.getClass();
        if (i3 < 0) {
            InlineClassHelperKt.throwIllegalArgumentException("height must be >= 0");
        }
        return ConstraintsKt.createConstraints(0, Integer.MAX_VALUE, i3, i3);
    }

    public abstract LazyGridMeasuredLine createLine(int i, LazyGridMeasuredItem[] lazyGridMeasuredItemArr, List list, int i2);

    public final LazyGridMeasuredLine getAndMeasure(int i) {
        LazyGridSpanLayoutProvider.LineConfiguration lineConfiguration = this.spanLayoutProvider.getLineConfiguration(i);
        int size = lineConfiguration.spans.size();
        int i2 = lineConfiguration.firstItemIndex;
        int i3 = (size == 0 || i2 + size == this.gridItemsCount) ? 0 : this.spaceBetweenLines;
        LazyGridMeasuredItem[] lazyGridMeasuredItemArr = new LazyGridMeasuredItem[size];
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
            int i6 = (int) ((GridItemSpan) lineConfiguration.spans.get(i5)).packedValue;
            LazyGridMeasuredItem m162getAndMeasurem8Kt_7k = this.measuredItemProvider.m162getAndMeasurem8Kt_7k(i2 + i5, m163childConstraintsJhjzzOo$foundation_release(i4, i6), i4, i6, i3);
            i4 += i6;
            Unit unit = Unit.INSTANCE;
            lazyGridMeasuredItemArr[i5] = m162getAndMeasurem8Kt_7k;
        }
        return createLine(i, lazyGridMeasuredItemArr, lineConfiguration.spans, i3);
    }
}
