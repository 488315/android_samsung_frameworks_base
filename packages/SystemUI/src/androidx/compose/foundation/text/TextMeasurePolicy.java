package androidx.compose.foundation.text;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntOffset;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TextMeasurePolicy implements MeasurePolicy {
    public final Function0 placements;
    public final Function0 shouldMeasureLinks;

    public TextMeasurePolicy(Function0 function0, Function0 function02) {
        this.shouldMeasureLinks = function0;
        this.placements = function02;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
        final ArrayList arrayList;
        MeasureResult layout$1;
        ArrayList arrayList2;
        Pair pair;
        ArrayList arrayList3 = new ArrayList(list.size());
        List list2 = list;
        int size = list2.size();
        for (int i = 0; i < size; i++) {
            Object obj = list.get(i);
            if (!(((Measurable) obj).getParentData() instanceof TextRangeLayoutModifier)) {
                arrayList3.add(obj);
            }
        }
        List list3 = (List) this.placements.invoke();
        if (list3 != null) {
            ArrayList arrayList4 = new ArrayList(list3.size());
            int size2 = list3.size();
            int i2 = 0;
            while (i2 < size2) {
                Rect rect = (Rect) list3.get(i2);
                if (rect != null) {
                    Measurable measurable = (Measurable) arrayList3.get(i2);
                    float f = rect.right;
                    float f2 = rect.left;
                    arrayList2 = arrayList4;
                    float f3 = rect.bottom;
                    float f4 = rect.top;
                    pair = new Pair(measurable.mo608measureBRTryo0(ConstraintsKt.Constraints$default(0, (int) Math.floor(f - f2), 0, (int) Math.floor(f3 - f4), 5)), IntOffset.m847boximpl((Math.round(f4) & 4294967295L) | (Math.round(f2) << 32)));
                } else {
                    arrayList2 = arrayList4;
                    pair = null;
                }
                ArrayList arrayList5 = arrayList2;
                if (pair != null) {
                    arrayList5.add(pair);
                }
                i2++;
                arrayList4 = arrayList5;
            }
            arrayList = arrayList4;
        } else {
            arrayList = null;
        }
        ArrayList arrayList6 = new ArrayList(list.size());
        int size3 = list2.size();
        for (int i3 = 0; i3 < size3; i3++) {
            Object obj2 = list.get(i3);
            if (((Measurable) obj2).getParentData() instanceof TextRangeLayoutModifier) {
                arrayList6.add(obj2);
            }
        }
        final List access$measureWithTextRangeMeasureConstraints = BasicTextKt.access$measureWithTextRangeMeasureConstraints(arrayList6, this.shouldMeasureLinks);
        layout$1 = measureScope.layout$1(Constraints.m821getMaxWidthimpl(j), Constraints.m820getMaxHeightimpl(j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.text.TextMeasurePolicy$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj3) {
                long j2;
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj3;
                List<Pair<Placeable, IntOffset>> list4 = arrayList;
                if (list4 != null) {
                    int size4 = list4.size();
                    for (int i4 = 0; i4 < size4; i4++) {
                        Pair<Placeable, IntOffset> pair2 = list4.get(i4);
                        Placeable.PlacementScope.m626place70tqf50$default(placementScope, (Placeable) pair2.component1(), ((IntOffset) pair2.component2()).packedValue);
                    }
                }
                List<Pair<Placeable, Function0>> list5 = access$measureWithTextRangeMeasureConstraints;
                if (list5 != null) {
                    int size5 = list5.size();
                    for (int i5 = 0; i5 < size5; i5++) {
                        Pair<Placeable, Function0> pair3 = list5.get(i5);
                        Placeable placeable = (Placeable) pair3.component1();
                        Function0 function0 = (Function0) pair3.component2();
                        if (function0 != null) {
                            j2 = ((IntOffset) function0.invoke()).packedValue;
                        } else {
                            IntOffset.Companion.getClass();
                            j2 = 0;
                        }
                        Placeable.PlacementScope.m626place70tqf50$default(placementScope, placeable, j2);
                    }
                }
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }
}
