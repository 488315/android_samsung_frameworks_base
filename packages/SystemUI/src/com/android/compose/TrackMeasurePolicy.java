package com.android.compose;

import androidx.compose.material3.SliderState;
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

/* loaded from: classes.dex */
public final class TrackMeasurePolicy implements MeasurePolicy {
    public final boolean enabled;
    public final boolean isRtl;
    public final Function1 onDrawingStateMeasured;
    public final SliderState sliderState;
    public final int thumbSize;

    public TrackMeasurePolicy(SliderState sliderState, boolean z, int i, boolean z2, Function1 function1) {
        this.sliderState = sliderState;
        this.enabled = z;
        this.thumbSize = i;
        this.isRtl = z2;
        this.onDrawingStateMeasured = function1;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
        Object obj;
        Object obj2;
        DrawingState drawingState;
        int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j) + this.thumbSize;
        int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Measurable measurable = (Measurable) list.get(i);
            if (LayoutIdKt.getLayoutId(measurable) == TrackComponent.Background) {
                final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(ConstraintsKt.Constraints(iM823getMaxWidthimpl, iM823getMaxWidthimpl, iM822getMaxHeightimpl, iM822getMaxHeightimpl));
                List list2 = list;
                int size2 = list2.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size2) {
                        obj = null;
                        break;
                    }
                    obj = list.get(i2);
                    if (LayoutIdKt.getLayoutId((Measurable) obj) == TrackComponent.Icon) {
                        break;
                    }
                    i2++;
                }
                Measurable measurable2 = (Measurable) obj;
                final Placeable placeableMo610measureBRTryo02 = measurable2 != null ? measurable2.mo610measureBRTryo0(ConstraintsKt.Constraints(iM822getMaxHeightimpl, iM822getMaxHeightimpl, iM822getMaxHeightimpl, iM822getMaxHeightimpl)) : null;
                int i3 = placeableMo610measureBRTryo02 != null ? placeableMo610measureBRTryo02.width : 0;
                int i4 = this.enabled ? (iM823getMaxWidthimpl - i3) / 2 : iM823getMaxWidthimpl - i3;
                int size3 = list2.size();
                int i5 = 0;
                while (true) {
                    if (i5 >= size3) {
                        obj2 = null;
                        break;
                    }
                    obj2 = list.get(i5);
                    if (LayoutIdKt.getLayoutId((Measurable) obj2) == TrackComponent.Label) {
                        break;
                    }
                    i5++;
                }
                Measurable measurable3 = (Measurable) obj2;
                final Placeable placeableMo610measureBRTryo03 = measurable3 != null ? measurable3.mo610measureBRTryo0(ConstraintsKt.Constraints(0, i4, iM822getMaxHeightimpl, iM822getMaxHeightimpl)) : null;
                boolean z = this.isRtl;
                SliderState sliderState = this.sliderState;
                if (z) {
                    float f = iM823getMaxWidthimpl;
                    float f2 = iM822getMaxHeightimpl;
                    drawingState = new DrawingState(true, f, f2, (1 - PlatformSliderKt.access$getCoercedNormalizedValue(sliderState)) * (iM823getMaxWidthimpl - i3), 0.0f, f, f2, i3, placeableMo610measureBRTryo03 != null ? placeableMo610measureBRTryo03.width : 0.0f);
                } else {
                    float f3 = iM822getMaxHeightimpl;
                    float f4 = i3;
                    drawingState = new DrawingState(false, iM823getMaxWidthimpl, f3, 0.0f, 0.0f, (PlatformSliderKt.access$getCoercedNormalizedValue(sliderState) * (iM823getMaxWidthimpl - i3)) + f4, f3, f4, placeableMo610measureBRTryo03 != null ? placeableMo610measureBRTryo03.width : 0.0f);
                }
                this.onDrawingStateMeasured.mo781invoke(drawingState);
                return measureScope.layout$1(iM823getMaxWidthimpl, iM822getMaxHeightimpl, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.TrackMeasurePolicy$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj3) {
                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj3;
                        placementScope.placeRelative(placeableMo610measureBRTryo0, 0, 0, TrackComponent.Background.getZIndex());
                        Placeable placeable = placeableMo610measureBRTryo02;
                        if (placeable != null) {
                            placementScope.placeRelative(placeable, 0, 0, TrackComponent.Icon.getZIndex());
                        }
                        Placeable placeable2 = placeableMo610measureBRTryo03;
                        if (placeable2 != null) {
                            placementScope.placeRelative(placeable2, 0, 0, TrackComponent.Label.getZIndex());
                        }
                        return Unit.INSTANCE;
                    }
                });
            }
        }
        ListUtilsKt.throwNoSuchElementException("Collection contains no element matching the predicate.");
        throw new KotlinNothingValueException();
    }
}
