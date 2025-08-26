package androidx.compose.foundation.text;

import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntOffset;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
final class LinksTextMeasurePolicy implements MeasurePolicy {
    public final Function0 shouldMeasureLinks;

    public LinksTextMeasurePolicy(Function0 function0) {
        this.shouldMeasureLinks = function0;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, final List list, long j) {
        return measureScope.layout$1(Constraints.m823getMaxWidthimpl(j), Constraints.m822getMaxHeightimpl(j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.text.LinksTextMeasurePolicy$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                long j2;
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                List listAccess$measureWithTextRangeMeasureConstraints = BasicTextKt.access$measureWithTextRangeMeasureConstraints(list, this.shouldMeasureLinks);
                if (listAccess$measureWithTextRangeMeasureConstraints != null) {
                    ArrayList arrayList = (ArrayList) listAccess$measureWithTextRangeMeasureConstraints;
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        Pair pair = (Pair) arrayList.get(i);
                        Placeable placeable = (Placeable) pair.component1();
                        Function0 function0 = (Function0) pair.component2();
                        if (function0 != null) {
                            j2 = ((IntOffset) function0.invoke()).packedValue;
                        } else {
                            IntOffset.Companion.getClass();
                            j2 = 0;
                        }
                        Placeable.PlacementScope.m628place70tqf50$default(placementScope, placeable, j2);
                    }
                }
                return Unit.INSTANCE;
            }
        });
    }
}
