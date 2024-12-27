package com.android.systemui.volume.panel.component.selector.ui.composable;

import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BarMeasurePolicy implements MeasurePolicy {
    public final int buttonsCount;
    public final Function1 onTargetIndicatorOffsetMeasured;
    public final int selectedIndex;
    public final int spacingPx;

    public BarMeasurePolicy(int i, int i2, int i3, Function1 function1) {
        this.buttonsCount = i;
        this.selectedIndex = i2;
        this.spacingPx = i3;
        this.onTargetIndicatorOffsetMeasured = function1;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
        MeasureResult layout$1;
        long m717copyZbe2FdA$default = Constraints.m717copyZbe2FdA$default(j, Constraints.m724getMaxWidthimpl(j), 0, 0, 0, 14);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Measurable measurable = (Measurable) list.get(i);
            if (LayoutIdKt.getLayoutId(measurable) == RadioButtonBarComponent.Buttons) {
                final Placeable mo528measureBRTryo0 = measurable.mo528measureBRTryo0(m717copyZbe2FdA$default);
                int size2 = list.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    Measurable measurable2 = (Measurable) list.get(i2);
                    if (LayoutIdKt.getLayoutId(measurable2) == RadioButtonBarComponent.Labels) {
                        final Placeable mo528measureBRTryo02 = measurable2.mo528measureBRTryo0(m717copyZbe2FdA$default);
                        int size3 = list.size();
                        for (int i3 = 0; i3 < size3; i3++) {
                            Measurable measurable3 = (Measurable) list.get(i3);
                            if (LayoutIdKt.getLayoutId(measurable3) == RadioButtonBarComponent.ButtonsBackground) {
                                int i4 = mo528measureBRTryo0.width;
                                int i5 = mo528measureBRTryo0.height;
                                final Placeable mo528measureBRTryo03 = measurable3.mo528measureBRTryo0(ConstraintsKt.Constraints(i4, i4, i5, i5));
                                int i6 = this.buttonsCount;
                                int i7 = this.spacingPx;
                                int i8 = (mo528measureBRTryo03.width - ((i6 - 1) * i7)) / i6;
                                int size4 = list.size();
                                for (int i9 = 0; i9 < size4; i9++) {
                                    Measurable measurable4 = (Measurable) list.get(i9);
                                    if (LayoutIdKt.getLayoutId(measurable4) == RadioButtonBarComponent.Indicator) {
                                        int i10 = mo528measureBRTryo03.height;
                                        final Placeable mo528measureBRTryo04 = measurable4.mo528measureBRTryo0(ConstraintsKt.Constraints(i8, i8, i10, i10));
                                        int i11 = this.selectedIndex;
                                        this.onTargetIndicatorOffsetMeasured.invoke(Integer.valueOf((i7 * i11) + (i8 * i11)));
                                        layout$1 = measureScope.layout$1(Constraints.m724getMaxWidthimpl(j), mo528measureBRTryo0.height + mo528measureBRTryo02.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.BarMeasurePolicy$measure$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj) {
                                                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                                                placementScope.placeRelative(Placeable.this, 0, 0, RadioButtonBarComponent.ButtonsBackground.getZIndex());
                                                placementScope.placeRelative(mo528measureBRTryo04, 0, 0, RadioButtonBarComponent.Indicator.getZIndex());
                                                placementScope.placeRelative(mo528measureBRTryo0, 0, 0, RadioButtonBarComponent.Buttons.getZIndex());
                                                placementScope.placeRelative(mo528measureBRTryo02, 0, Placeable.this.height, RadioButtonBarComponent.Labels.getZIndex());
                                                return Unit.INSTANCE;
                                            }
                                        });
                                        return layout$1;
                                    }
                                }
                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                            }
                        }
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                }
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }
}
