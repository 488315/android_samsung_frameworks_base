package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        MeasureResult layout$1;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Measurable measurable = (Measurable) list.get(i);
            if (LayoutIdKt.getLayoutId(measurable) == VolumeSliderContentComponent.Label) {
                final Placeable mo528measureBRTryo0 = measurable.mo528measureBRTryo0(j);
                boolean z = this.isEnabled;
                int m724getMaxWidthimpl = z ? mo528measureBRTryo0.width : Constraints.m724getMaxWidthimpl(j);
                int m724getMaxWidthimpl2 = z ? Constraints.m724getMaxWidthimpl(j) * 2 : Constraints.m724getMaxWidthimpl(j);
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
                final Placeable mo528measureBRTryo02 = measurable2 != null ? measurable2.mo528measureBRTryo0(Constraints.m717copyZbe2FdA$default(j, 0, m724getMaxWidthimpl2, 0, 0, 13)) : null;
                layout$1 = measureScope.layout$1(m724getMaxWidthimpl, mo528measureBRTryo0.height + (mo528measureBRTryo02 != null ? mo528measureBRTryo02.height : 0), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderContentMeasurePolicy$measure$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj2;
                        placementScope.placeRelative(Placeable.this, 0, 0, 0.0f);
                        Placeable placeable = mo528measureBRTryo02;
                        if (placeable != null) {
                            placementScope.placeRelative(placeable, 0, Placeable.this.height, 0.0f);
                        }
                        return Unit.INSTANCE;
                    }
                });
                return layout$1;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }
}
