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
        long jM816copyZbe2FdA$default = Constraints.m816copyZbe2FdA$default(j, Constraints.m823getMaxWidthimpl(j), 0, 0, 0, 14);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Measurable measurable = (Measurable) list.get(i);
            if (LayoutIdKt.getLayoutId(measurable) == RadioButtonBarComponent.Buttons) {
                final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(jM816copyZbe2FdA$default);
                List list2 = list;
                int size2 = list2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    Measurable measurable2 = (Measurable) list.get(i2);
                    if (LayoutIdKt.getLayoutId(measurable2) == RadioButtonBarComponent.Labels) {
                        final Placeable placeableMo610measureBRTryo02 = measurable2.mo610measureBRTryo0(jM816copyZbe2FdA$default);
                        int size3 = list2.size();
                        for (int i3 = 0; i3 < size3; i3++) {
                            Measurable measurable3 = (Measurable) list.get(i3);
                            if (LayoutIdKt.getLayoutId(measurable3) == RadioButtonBarComponent.ButtonsBackground) {
                                int i4 = placeableMo610measureBRTryo0.width;
                                int i5 = placeableMo610measureBRTryo0.height;
                                final Placeable placeableMo610measureBRTryo03 = measurable3.mo610measureBRTryo0(ConstraintsKt.Constraints(i4, i4, i5, i5));
                                int i6 = this.buttonsCount;
                                int i7 = this.spacingPx;
                                int i8 = (placeableMo610measureBRTryo03.width - ((i6 - 1) * i7)) / i6;
                                int size4 = list2.size();
                                for (int i9 = 0; i9 < size4; i9++) {
                                    Measurable measurable4 = (Measurable) list.get(i9);
                                    if (LayoutIdKt.getLayoutId(measurable4) == RadioButtonBarComponent.Indicator) {
                                        int i10 = placeableMo610measureBRTryo03.height;
                                        final Placeable placeableMo610measureBRTryo04 = measurable4.mo610measureBRTryo0(ConstraintsKt.Constraints(i8, i8, i10, i10));
                                        int i11 = this.selectedIndex;
                                        this.onTargetIndicatorOffsetMeasured.mo781invoke(Integer.valueOf((i7 * i11) + (i8 * i11)));
                                        return measureScope.layout$1(Constraints.m823getMaxWidthimpl(j), placeableMo610measureBRTryo0.height + placeableMo610measureBRTryo02.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.volume.panel.component.selector.ui.composable.BarMeasurePolicy$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj) {
                                                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                                                float zIndex = RadioButtonBarComponent.ButtonsBackground.getZIndex();
                                                Placeable placeable = placeableMo610measureBRTryo03;
                                                placementScope.placeRelative(placeable, 0, 0, zIndex);
                                                placementScope.placeRelative(placeableMo610measureBRTryo04, 0, 0, RadioButtonBarComponent.Indicator.getZIndex());
                                                placementScope.placeRelative(placeableMo610measureBRTryo0, 0, 0, RadioButtonBarComponent.Buttons.getZIndex());
                                                placementScope.placeRelative(placeableMo610measureBRTryo02, 0, placeable.height, RadioButtonBarComponent.Labels.getZIndex());
                                                return Unit.INSTANCE;
                                            }
                                        });
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
