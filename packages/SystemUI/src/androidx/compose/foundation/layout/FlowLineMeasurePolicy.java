package androidx.compose.foundation.layout;

import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public interface FlowLineMeasurePolicy extends RowColumnMeasurePolicy {
    @Override // androidx.compose.foundation.layout.RowColumnMeasurePolicy
    /* renamed from: createConstraints-xF2OJ5Q */
    default long mo103createConstraintsxF2OJ5Q(boolean z, int i, int i2, int i3, int i4) {
        if (((FlowMeasurePolicy) this).isHorizontal) {
            RowMeasurePolicy rowMeasurePolicy = RowKt.DefaultRowMeasurePolicy;
            if (!z) {
                return ConstraintsKt.Constraints(i, i3, i2, i4);
            }
            Constraints.Companion.getClass();
            return Constraints.Companion.m828fitPrioritizingWidthZbe2FdA(i, i3, i2, i4);
        }
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.DefaultColumnMeasurePolicy;
        if (!z) {
            return ConstraintsKt.Constraints(i2, i4, i, i3);
        }
        Constraints.Companion.getClass();
        return Constraints.Companion.m827fitPrioritizingHeightZbe2FdA(i2, i4, i, i3);
    }

    @Override // androidx.compose.foundation.layout.RowColumnMeasurePolicy
    default int crossAxisSize(Placeable placeable) {
        return ((FlowMeasurePolicy) this).isHorizontal ? placeable.getMeasuredHeight() : placeable.getMeasuredWidth();
    }

    @Override // androidx.compose.foundation.layout.RowColumnMeasurePolicy
    default int mainAxisSize(Placeable placeable) {
        return ((FlowMeasurePolicy) this).isHorizontal ? placeable.getMeasuredWidth() : placeable.getMeasuredHeight();
    }

    @Override // androidx.compose.foundation.layout.RowColumnMeasurePolicy
    default MeasureResult placeHelper(final Placeable[] placeableArr, MeasureScope measureScope, final int[] iArr, int i, final int i2, final int[] iArr2, final int i3, final int i4, final int i5) {
        int i6;
        int i7;
        boolean z = ((FlowMeasurePolicy) this).isHorizontal;
        if (z) {
            i7 = i;
            i6 = i2;
        } else {
            i6 = i;
            i7 = i2;
        }
        final LayoutDirection layoutDirection = z ? LayoutDirection.Ltr : measureScope.getLayoutDirection();
        final int i8 = 0;
        return measureScope.layout$1(i7, i6, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.FlowLineMeasurePolicy$placeHelper$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                CrossAxisAlignment crossAxisAlignment;
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                int[] iArr3 = iArr2;
                int i9 = iArr3 != null ? iArr3[i3] : 0;
                for (int i10 = i4; i10 < i5; i10++) {
                    Placeable placeable = placeableArr[i10];
                    placeable.getClass();
                    FlowLineMeasurePolicy flowLineMeasurePolicy = this;
                    int i11 = i2;
                    LayoutDirection layoutDirection2 = layoutDirection;
                    flowLineMeasurePolicy.getClass();
                    Object parentData = placeable.getParentData();
                    RowColumnParentData rowColumnParentData = parentData instanceof RowColumnParentData ? (RowColumnParentData) parentData : null;
                    if (rowColumnParentData == null || (crossAxisAlignment = rowColumnParentData.crossAxisAlignment) == null) {
                        crossAxisAlignment = ((FlowMeasurePolicy) flowLineMeasurePolicy).crossAxisAlignment;
                    }
                    int iAlign$foundation_layout = crossAxisAlignment.align$foundation_layout(i11 - flowLineMeasurePolicy.crossAxisSize(placeable), layoutDirection2) + i9;
                    if (((FlowMeasurePolicy) this).isHorizontal) {
                        placementScope.place(placeable, iArr[i10 - i4], iAlign$foundation_layout, 0.0f);
                    } else {
                        placementScope.place(placeable, iAlign$foundation_layout, iArr[i10 - i4], 0.0f);
                    }
                }
                return Unit.INSTANCE;
            }
        });
    }

    @Override // androidx.compose.foundation.layout.RowColumnMeasurePolicy
    default void populateMainAxisPositions(int i, int[] iArr, int[] iArr2, MeasureScope measureScope) {
        FlowMeasurePolicy flowMeasurePolicy = (FlowMeasurePolicy) this;
        if (!flowMeasurePolicy.isHorizontal) {
            flowMeasurePolicy.verticalArrangement.arrange(measureScope, i, iArr, iArr2);
        } else {
            flowMeasurePolicy.horizontalArrangement.arrange(measureScope, i, iArr, measureScope.getLayoutDirection(), iArr2);
        }
    }
}
