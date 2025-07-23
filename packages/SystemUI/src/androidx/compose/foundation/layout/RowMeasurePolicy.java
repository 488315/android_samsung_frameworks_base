package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RowMeasurePolicy implements MeasurePolicy, RowColumnMeasurePolicy {
    public final Arrangement.Horizontal horizontalArrangement;
    public final Alignment.Vertical verticalAlignment;

    public RowMeasurePolicy(Arrangement.Horizontal horizontal, Alignment.Vertical vertical) {
        this.horizontalArrangement = horizontal;
        this.verticalAlignment = vertical;
    }

    @Override // androidx.compose.foundation.layout.RowColumnMeasurePolicy
    /* renamed from: createConstraints-xF2OJ5Q */
    public final long mo102createConstraintsxF2OJ5Q(boolean z, int i, int i2, int i3, int i4) {
        RowMeasurePolicy rowMeasurePolicy = RowKt.DefaultRowMeasurePolicy;
        if (!z) {
            return ConstraintsKt.Constraints(i, i3, i2, i4);
        }
        Constraints.Companion.getClass();
        return Constraints.Companion.m826fitPrioritizingWidthZbe2FdA(i, i3, i2, i4);
    }

    @Override // androidx.compose.foundation.layout.RowColumnMeasurePolicy
    public final int crossAxisSize(Placeable placeable) {
        return placeable.height;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RowMeasurePolicy)) {
            return false;
        }
        RowMeasurePolicy rowMeasurePolicy = (RowMeasurePolicy) obj;
        return Intrinsics.areEqual(this.horizontalArrangement, rowMeasurePolicy.horizontalArrangement) && Intrinsics.areEqual(this.verticalAlignment, rowMeasurePolicy.verticalAlignment);
    }

    public final int hashCode() {
        return this.verticalAlignment.hashCode() + (this.horizontalArrangement.hashCode() * 31);
    }

    @Override // androidx.compose.foundation.layout.RowColumnMeasurePolicy
    public final int mainAxisSize(Placeable placeable) {
        return placeable.width;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        IntrinsicMeasureBlocks intrinsicMeasureBlocks = IntrinsicMeasureBlocks.INSTANCE;
        int mo51roundToPx0680j_4 = intrinsicMeasureScope.mo51roundToPx0680j_4(this.horizontalArrangement.mo94getSpacingD9Ej5fM());
        intrinsicMeasureBlocks.getClass();
        if (list.isEmpty()) {
            return 0;
        }
        int min = Math.min((list.size() - 1) * mo51roundToPx0680j_4, i);
        List list2 = list;
        int size = list2.size();
        int i2 = 0;
        float f = 0.0f;
        for (int i3 = 0; i3 < size; i3++) {
            IntrinsicMeasurable intrinsicMeasurable = (IntrinsicMeasurable) list.get(i3);
            float weight = RowColumnImplKt.getWeight(RowColumnImplKt.getRowColumnParentData(intrinsicMeasurable));
            if (weight == 0.0f) {
                int min2 = Math.min(intrinsicMeasurable.maxIntrinsicWidth(Integer.MAX_VALUE), i == Integer.MAX_VALUE ? Integer.MAX_VALUE : i - min);
                min += min2;
                i2 = Math.max(i2, intrinsicMeasurable.maxIntrinsicHeight(min2));
            } else if (weight > 0.0f) {
                f += weight;
            }
        }
        int round = f == 0.0f ? 0 : i == Integer.MAX_VALUE ? Integer.MAX_VALUE : Math.round(Math.max(i - min, 0) / f);
        int size2 = list2.size();
        for (int i4 = 0; i4 < size2; i4++) {
            IntrinsicMeasurable intrinsicMeasurable2 = (IntrinsicMeasurable) list.get(i4);
            float weight2 = RowColumnImplKt.getWeight(RowColumnImplKt.getRowColumnParentData(intrinsicMeasurable2));
            if (weight2 > 0.0f) {
                i2 = Math.max(i2, intrinsicMeasurable2.maxIntrinsicHeight(round != Integer.MAX_VALUE ? Math.round(round * weight2) : Integer.MAX_VALUE));
            }
        }
        return i2;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        IntrinsicMeasureBlocks intrinsicMeasureBlocks = IntrinsicMeasureBlocks.INSTANCE;
        int mo51roundToPx0680j_4 = intrinsicMeasureScope.mo51roundToPx0680j_4(this.horizontalArrangement.mo94getSpacingD9Ej5fM());
        intrinsicMeasureBlocks.getClass();
        if (list.isEmpty()) {
            return 0;
        }
        int size = list.size();
        int i2 = 0;
        int i3 = 0;
        float f = 0.0f;
        for (int i4 = 0; i4 < size; i4++) {
            IntrinsicMeasurable intrinsicMeasurable = (IntrinsicMeasurable) list.get(i4);
            float weight = RowColumnImplKt.getWeight(RowColumnImplKt.getRowColumnParentData(intrinsicMeasurable));
            int maxIntrinsicWidth = intrinsicMeasurable.maxIntrinsicWidth(i);
            if (weight == 0.0f) {
                i3 += maxIntrinsicWidth;
            } else if (weight > 0.0f) {
                f += weight;
                i2 = Math.max(i2, Math.round(maxIntrinsicWidth / weight));
            }
        }
        return ((list.size() - 1) * mo51roundToPx0680j_4) + Math.round(i2 * f) + i3;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
        return RowColumnMeasurePolicyKt.measure(this, Constraints.m823getMinWidthimpl(j), Constraints.m822getMinHeightimpl(j), Constraints.m821getMaxWidthimpl(j), Constraints.m820getMaxHeightimpl(j), measureScope.mo51roundToPx0680j_4(this.horizontalArrangement.mo94getSpacingD9Ej5fM()), measureScope, list, new Placeable[list.size()], 0, list.size(), null, 0);
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        IntrinsicMeasureBlocks intrinsicMeasureBlocks = IntrinsicMeasureBlocks.INSTANCE;
        int mo51roundToPx0680j_4 = intrinsicMeasureScope.mo51roundToPx0680j_4(this.horizontalArrangement.mo94getSpacingD9Ej5fM());
        intrinsicMeasureBlocks.getClass();
        if (list.isEmpty()) {
            return 0;
        }
        int min = Math.min((list.size() - 1) * mo51roundToPx0680j_4, i);
        List list2 = list;
        int size = list2.size();
        int i2 = 0;
        float f = 0.0f;
        for (int i3 = 0; i3 < size; i3++) {
            IntrinsicMeasurable intrinsicMeasurable = (IntrinsicMeasurable) list.get(i3);
            float weight = RowColumnImplKt.getWeight(RowColumnImplKt.getRowColumnParentData(intrinsicMeasurable));
            if (weight == 0.0f) {
                int min2 = Math.min(intrinsicMeasurable.maxIntrinsicWidth(Integer.MAX_VALUE), i == Integer.MAX_VALUE ? Integer.MAX_VALUE : i - min);
                min += min2;
                i2 = Math.max(i2, intrinsicMeasurable.minIntrinsicHeight(min2));
            } else if (weight > 0.0f) {
                f += weight;
            }
        }
        int round = f == 0.0f ? 0 : i == Integer.MAX_VALUE ? Integer.MAX_VALUE : Math.round(Math.max(i - min, 0) / f);
        int size2 = list2.size();
        for (int i4 = 0; i4 < size2; i4++) {
            IntrinsicMeasurable intrinsicMeasurable2 = (IntrinsicMeasurable) list.get(i4);
            float weight2 = RowColumnImplKt.getWeight(RowColumnImplKt.getRowColumnParentData(intrinsicMeasurable2));
            if (weight2 > 0.0f) {
                i2 = Math.max(i2, intrinsicMeasurable2.minIntrinsicHeight(round != Integer.MAX_VALUE ? Math.round(round * weight2) : Integer.MAX_VALUE));
            }
        }
        return i2;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        IntrinsicMeasureBlocks intrinsicMeasureBlocks = IntrinsicMeasureBlocks.INSTANCE;
        int mo51roundToPx0680j_4 = intrinsicMeasureScope.mo51roundToPx0680j_4(this.horizontalArrangement.mo94getSpacingD9Ej5fM());
        intrinsicMeasureBlocks.getClass();
        if (list.isEmpty()) {
            return 0;
        }
        int size = list.size();
        int i2 = 0;
        int i3 = 0;
        float f = 0.0f;
        for (int i4 = 0; i4 < size; i4++) {
            IntrinsicMeasurable intrinsicMeasurable = (IntrinsicMeasurable) list.get(i4);
            float weight = RowColumnImplKt.getWeight(RowColumnImplKt.getRowColumnParentData(intrinsicMeasurable));
            int minIntrinsicWidth = intrinsicMeasurable.minIntrinsicWidth(i);
            if (weight == 0.0f) {
                i3 += minIntrinsicWidth;
            } else if (weight > 0.0f) {
                f += weight;
                i2 = Math.max(i2, Math.round(minIntrinsicWidth / weight));
            }
        }
        return ((list.size() - 1) * mo51roundToPx0680j_4) + Math.round(i2 * f) + i3;
    }

    @Override // androidx.compose.foundation.layout.RowColumnMeasurePolicy
    public final MeasureResult placeHelper(final Placeable[] placeableArr, MeasureScope measureScope, final int[] iArr, int i, final int i2, int[] iArr2, int i3, int i4, int i5) {
        MeasureResult layout$1;
        final int i6 = 0;
        layout$1 = measureScope.layout$1(i, i2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.RowMeasurePolicy$placeHelper$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                Placeable[] placeableArr2 = placeableArr;
                RowMeasurePolicy rowMeasurePolicy = this;
                int i7 = i2;
                int[] iArr3 = iArr;
                int length = placeableArr2.length;
                int i8 = 0;
                int i9 = 0;
                while (i8 < length) {
                    Placeable placeable = placeableArr2[i8];
                    int i10 = i9 + 1;
                    placeable.getClass();
                    Object parentData = placeable.getParentData();
                    RowColumnParentData rowColumnParentData = parentData instanceof RowColumnParentData ? (RowColumnParentData) parentData : null;
                    rowMeasurePolicy.getClass();
                    CrossAxisAlignment crossAxisAlignment = rowColumnParentData != null ? rowColumnParentData.crossAxisAlignment : null;
                    placementScope.place(placeable, iArr3[i9], crossAxisAlignment != null ? crossAxisAlignment.align$foundation_layout(i7 - placeable.height, LayoutDirection.Ltr) : ((BiasAlignment.Vertical) rowMeasurePolicy.verticalAlignment).align(0, i7 - placeable.height), 0.0f);
                    i8++;
                    i9 = i10;
                }
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    @Override // androidx.compose.foundation.layout.RowColumnMeasurePolicy
    public final void populateMainAxisPositions(int i, int[] iArr, int[] iArr2, MeasureScope measureScope) {
        this.horizontalArrangement.arrange(measureScope, i, iArr, measureScope.getLayoutDirection(), iArr2);
    }

    public final String toString() {
        return "RowMeasurePolicy(horizontalArrangement=" + this.horizontalArrangement + ", verticalAlignment=" + this.verticalAlignment + ')';
    }
}
