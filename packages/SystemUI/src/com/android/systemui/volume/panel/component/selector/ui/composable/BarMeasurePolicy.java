package com.android.systemui.volume.panel.component.selector.ui.composable;

import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.util.ListUtilsKt;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
        long m814copyZbe2FdA$default = Constraints.m814copyZbe2FdA$default(j, Constraints.m821getMaxWidthimpl(j), 0, 0, 0, 14);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Measurable measurable = (Measurable) list.get(i);
            if (LayoutIdKt.getLayoutId(measurable) == RadioButtonBarComponent.Buttons) {
                final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(m814copyZbe2FdA$default);
                List list2 = list;
                int size2 = list2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    Measurable measurable2 = (Measurable) list.get(i2);
                    if (LayoutIdKt.getLayoutId(measurable2) == RadioButtonBarComponent.Labels) {
                        final Placeable mo608measureBRTryo02 = measurable2.mo608measureBRTryo0(m814copyZbe2FdA$default);
                        int size3 = list2.size();
                        for (int i3 = 0; i3 < size3; i3++) {
                            Measurable measurable3 = (Measurable) list.get(i3);
                            if (LayoutIdKt.getLayoutId(measurable3) == RadioButtonBarComponent.ButtonsBackground) {
                                int i4 = mo608measureBRTryo0.width;
                                int i5 = mo608measureBRTryo0.height;
                                final Placeable mo608measureBRTryo03 = measurable3.mo608measureBRTryo0(ConstraintsKt.Constraints(i4, i4, i5, i5));
                                int i6 = this.buttonsCount;
                                int i7 = this.spacingPx;
                                int i8 = (mo608measureBRTryo03.width - ((i6 - 1) * i7)) / i6;
                                int size4 = list2.size();
                                for (int i9 = 0; i9 < size4; i9++) {
                                    Measurable measurable4 = (Measurable) list.get(i9);
                                    if (LayoutIdKt.getLayoutId(measurable4) == RadioButtonBarComponent.Indicator) {
                                        int i10 = mo608measureBRTryo03.height;
                                        final Placeable mo608measureBRTryo04 = measurable4.mo608measureBRTryo0(ConstraintsKt.Constraints(i8, i8, i10, i10));
                                        int i11 = this.selectedIndex;
                                        this.onTargetIndicatorOffsetMeasured.mo779invoke(Integer.valueOf((i7 * i11) + (i8 * i11)));
                                        layout$1 = measureScope.layout$1(Constraints.m821getMaxWidthimpl(j), mo608measureBRTryo0.height + mo608measureBRTryo02.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.BarMeasurePolicy$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo779invoke(Object obj) {
                                                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                                                float zIndex = RadioButtonBarComponent.ButtonsBackground.getZIndex();
                                                Placeable placeable = Placeable.this;
                                                placementScope.placeRelative(placeable, 0, 0, zIndex);
                                                placementScope.placeRelative(mo608measureBRTryo04, 0, 0, RadioButtonBarComponent.Indicator.getZIndex());
                                                placementScope.placeRelative(mo608measureBRTryo0, 0, 0, RadioButtonBarComponent.Buttons.getZIndex());
                                                placementScope.placeRelative(mo608measureBRTryo02, 0, placeable.height, RadioButtonBarComponent.Labels.getZIndex());
                                                return Unit.INSTANCE;
                                            }
                                        });
                                        return layout$1;
                                    }
                                }
                                ListUtilsKt.throwNoSuchElementException("Collection contains no element matching the predicate.");
                                throw new KotlinNothingValueException();
                            }
                        }
                        ListUtilsKt.throwNoSuchElementException("Collection contains no element matching the predicate.");
                        throw new KotlinNothingValueException();
                    }
                }
                ListUtilsKt.throwNoSuchElementException("Collection contains no element matching the predicate.");
                throw new KotlinNothingValueException();
            }
        }
        ListUtilsKt.throwNoSuchElementException("Collection contains no element matching the predicate.");
        throw new KotlinNothingValueException();
    }
}
