package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.util.ListUtilsKt;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final class VolumeSliderContentMeasurePolicy implements MeasurePolicy {
    public final boolean isEnabled;

    public VolumeSliderContentMeasurePolicy(boolean z) {
        this.isEnabled = z;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
        Object obj;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Measurable measurable = (Measurable) list.get(i);
            if (LayoutIdKt.getLayoutId(measurable) == VolumeSliderContentComponent.Label) {
                final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
                boolean z = this.isEnabled;
                int iM823getMaxWidthimpl = z ? placeableMo610measureBRTryo0.width : Constraints.m823getMaxWidthimpl(j);
                int iM823getMaxWidthimpl2 = z ? Constraints.m823getMaxWidthimpl(j) * 2 : Constraints.m823getMaxWidthimpl(j);
                int size2 = list.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size2) {
                        obj = null;
                        break;
                    }
                    obj = list.get(i2);
                    if (LayoutIdKt.getLayoutId((Measurable) obj) == VolumeSliderContentComponent.DisabledMessage) {
                        break;
                    }
                    i2++;
                }
                Measurable measurable2 = (Measurable) obj;
                final Placeable placeableMo610measureBRTryo02 = measurable2 != null ? measurable2.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(j, 0, iM823getMaxWidthimpl2, 0, 0, 13)) : null;
                return measureScope.layout$1(iM823getMaxWidthimpl, placeableMo610measureBRTryo0.height + (placeableMo610measureBRTryo02 != null ? placeableMo610measureBRTryo02.height : 0), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderContentMeasurePolicy$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj2;
                        Placeable placeable = placeableMo610measureBRTryo0;
                        placementScope.placeRelative(placeable, 0, 0, 0.0f);
                        Placeable placeable2 = placeableMo610measureBRTryo02;
                        if (placeable2 != null) {
                            placementScope.placeRelative(placeable2, 0, placeable.height, 0.0f);
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
