package com.android.compose.modifiers;

import androidx.compose.ui.layout.LayoutModifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.platform.InspectorValueInfo;
import androidx.compose.ui.unit.ConstraintsKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class PaddingModifier extends InspectorValueInfo implements LayoutModifier {
    public final Function1 bottom;
    public final Function1 end;
    public final boolean rtlAware;
    public final Function1 start;
    public final Function1 top;

    public PaddingModifier(Function1 function1, Function1 function12, Function1 function13, Function1 function14, boolean z, Function1 function15) {
        super(function15);
        this.start = function1;
        this.top = function12;
        this.end = function13;
        this.bottom = function14;
        this.rtlAware = z;
    }

    public final boolean equals(Object obj) {
        PaddingModifier paddingModifier = obj instanceof PaddingModifier ? (PaddingModifier) obj : null;
        return paddingModifier != null && Intrinsics.areEqual(this.start, paddingModifier.start) && Intrinsics.areEqual(this.top, paddingModifier.top) && Intrinsics.areEqual(this.end, paddingModifier.end) && Intrinsics.areEqual(this.bottom, paddingModifier.bottom) && this.rtlAware == paddingModifier.rtlAware;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.rtlAware) + ((this.bottom.hashCode() + ((this.end.hashCode() + ((this.top.hashCode() + (this.start.hashCode() * 31)) * 31)) * 31)) * 31);
    }

    @Override // androidx.compose.ui.layout.LayoutModifier
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo109measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        final int iIntValue = ((Number) this.start.mo781invoke(measureScope)).intValue();
        final int iIntValue2 = ((Number) this.top.mo781invoke(measureScope)).intValue();
        int iIntValue3 = ((Number) this.end.mo781invoke(measureScope)).intValue() + iIntValue;
        int iIntValue4 = ((Number) this.bottom.mo781invoke(measureScope)).intValue() + iIntValue2;
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(ConstraintsKt.m835offsetNN6EwU(-iIntValue3, -iIntValue4, j));
        return measureScope.layout$1(ConstraintsKt.m834constrainWidthK40F9xA(placeableMo610measureBRTryo0.width + iIntValue3, j), ConstraintsKt.m833constrainHeightK40F9xA(placeableMo610measureBRTryo0.height + iIntValue4, j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.modifiers.PaddingModifier$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                boolean z = this.f$0.rtlAware;
                Placeable placeable = placeableMo610measureBRTryo0;
                int i = iIntValue;
                int i2 = iIntValue2;
                if (z) {
                    placementScope.placeRelative(placeable, i, i2, 0.0f);
                } else {
                    placementScope.place(placeable, i, i2, 0.0f);
                }
                return Unit.INSTANCE;
            }
        });
    }
}
