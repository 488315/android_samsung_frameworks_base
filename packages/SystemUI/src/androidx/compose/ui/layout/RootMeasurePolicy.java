package androidx.compose.ui.layout;

import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RootMeasurePolicy extends LayoutNode.NoIntrinsicsMeasurePolicy {
    public static final RootMeasurePolicy INSTANCE = new RootMeasurePolicy();

    private RootMeasurePolicy() {
        super("Undefined intrinsics block and it is required");
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
        MeasureResult layout$1;
        MeasureResult layout$12;
        MeasureResult layout$13;
        int size = list.size();
        if (size == 0) {
            layout$1 = measureScope.layout$1(Constraints.m823getMinWidthimpl(j), Constraints.m822getMinHeightimpl(j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.layout.RootMeasurePolicy$measure$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                    return Unit.INSTANCE;
                }
            });
            return layout$1;
        }
        if (size == 1) {
            final Placeable mo608measureBRTryo0 = ((Measurable) list.get(0)).mo608measureBRTryo0(j);
            layout$12 = measureScope.layout$1(ConstraintsKt.m832constrainWidthK40F9xA(mo608measureBRTryo0.width, j), ConstraintsKt.m831constrainHeightK40F9xA(mo608measureBRTryo0.height, j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.layout.RootMeasurePolicy$measure$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    Placeable.PlacementScope.placeRelativeWithLayer$default((Placeable.PlacementScope) obj, Placeable.this, 0, 0);
                    return Unit.INSTANCE;
                }
            });
            return layout$12;
        }
        final ArrayList arrayList = new ArrayList(list.size());
        int size2 = list.size();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < size2; i3++) {
            Placeable mo608measureBRTryo02 = ((Measurable) list.get(i3)).mo608measureBRTryo0(j);
            i = Math.max(mo608measureBRTryo02.width, i);
            i2 = Math.max(mo608measureBRTryo02.height, i2);
            arrayList.add(mo608measureBRTryo02);
        }
        layout$13 = measureScope.layout$1(ConstraintsKt.m832constrainWidthK40F9xA(i, j), ConstraintsKt.m831constrainHeightK40F9xA(i2, j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.layout.RootMeasurePolicy$measure$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                List<Placeable> list2 = arrayList;
                int size3 = list2.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    Placeable.PlacementScope.placeRelativeWithLayer$default(placementScope, list2.get(i4), 0, 0);
                }
                return Unit.INSTANCE;
            }
        });
        return layout$13;
    }
}
