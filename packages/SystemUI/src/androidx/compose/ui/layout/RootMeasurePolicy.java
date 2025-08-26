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

/* loaded from: classes.dex */
public final class RootMeasurePolicy extends LayoutNode.NoIntrinsicsMeasurePolicy {
    public static final RootMeasurePolicy INSTANCE = new RootMeasurePolicy();

    private RootMeasurePolicy() {
        super("Undefined intrinsics block and it is required");
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
        int size = list.size();
        if (size == 0) {
            return measureScope.layout$1(Constraints.m825getMinWidthimpl(j), Constraints.m824getMinHeightimpl(j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.layout.RootMeasurePolicy$measure$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                    return Unit.INSTANCE;
                }
            });
        }
        if (size == 1) {
            final Placeable placeableMo610measureBRTryo0 = ((Measurable) list.get(0)).mo610measureBRTryo0(j);
            return measureScope.layout$1(ConstraintsKt.m834constrainWidthK40F9xA(placeableMo610measureBRTryo0.width, j), ConstraintsKt.m833constrainHeightK40F9xA(placeableMo610measureBRTryo0.height, j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.layout.RootMeasurePolicy$measure$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    Placeable.PlacementScope.placeRelativeWithLayer$default((Placeable.PlacementScope) obj, placeableMo610measureBRTryo0, 0, 0);
                    return Unit.INSTANCE;
                }
            });
        }
        final ArrayList arrayList = new ArrayList(list.size());
        int size2 = list.size();
        int iMax = 0;
        int iMax2 = 0;
        for (int i = 0; i < size2; i++) {
            Placeable placeableMo610measureBRTryo02 = ((Measurable) list.get(i)).mo610measureBRTryo0(j);
            iMax = Math.max(placeableMo610measureBRTryo02.width, iMax);
            iMax2 = Math.max(placeableMo610measureBRTryo02.height, iMax2);
            arrayList.add(placeableMo610measureBRTryo02);
        }
        return measureScope.layout$1(ConstraintsKt.m834constrainWidthK40F9xA(iMax, j), ConstraintsKt.m833constrainHeightK40F9xA(iMax2, j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.layout.RootMeasurePolicy$measure$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                List<Placeable> list2 = arrayList;
                int size3 = list2.size();
                for (int i2 = 0; i2 < size3; i2++) {
                    Placeable.PlacementScope.placeRelativeWithLayer$default(placementScope, list2.get(i2), 0, 0);
                }
                return Unit.INSTANCE;
            }
        });
    }
}
