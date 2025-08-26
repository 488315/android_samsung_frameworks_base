package androidx.compose.foundation.layout;

import androidx.collection.MutableScatterMap;
import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;

/* loaded from: classes.dex */
final class BoxMeasurePolicy implements MeasurePolicy {
    public final Alignment alignment;
    public final boolean propagateMinConstraints;

    public BoxMeasurePolicy(Alignment alignment, boolean z) {
        this.alignment = alignment;
        this.propagateMinConstraints = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BoxMeasurePolicy)) {
            return false;
        }
        BoxMeasurePolicy boxMeasurePolicy = (BoxMeasurePolicy) obj;
        return Intrinsics.areEqual(this.alignment, boxMeasurePolicy.alignment) && this.propagateMinConstraints == boxMeasurePolicy.propagateMinConstraints;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.propagateMinConstraints) + (this.alignment.hashCode() * 31);
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(final MeasureScope measureScope, final List list, long j) {
        long j2;
        int iM825getMinWidthimpl;
        int iM824getMinHeightimpl;
        Placeable placeableMo610measureBRTryo0;
        boolean z = true;
        if (list.isEmpty()) {
            return measureScope.layout$1(Constraints.m825getMinWidthimpl(j), Constraints.m824getMinHeightimpl(j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.BoxMeasurePolicy$measure$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                    return Unit.INSTANCE;
                }
            });
        }
        if (this.propagateMinConstraints) {
            j2 = j;
        } else {
            j2 = j & (-8589934589L);
            Constraints.Companion companion = Constraints.Companion;
        }
        if (list.size() == 1) {
            final Measurable measurable = (Measurable) list.get(0);
            MutableScatterMap mutableScatterMap = BoxKt.Cache1;
            Object parentData = measurable.getParentData();
            BoxChildDataNode boxChildDataNode = parentData instanceof BoxChildDataNode ? (BoxChildDataNode) parentData : null;
            if (boxChildDataNode != null ? boxChildDataNode.matchParentSize : false) {
                iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j);
                iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(j);
                Constraints.Companion companion2 = Constraints.Companion;
                int iM825getMinWidthimpl2 = Constraints.m825getMinWidthimpl(j);
                int iM824getMinHeightimpl2 = Constraints.m824getMinHeightimpl(j);
                companion2.getClass();
                placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(Constraints.Companion.m829fixedJhjzzOo(iM825getMinWidthimpl2, iM824getMinHeightimpl2));
            } else {
                placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j2);
                iM825getMinWidthimpl = Math.max(Constraints.m825getMinWidthimpl(j), placeableMo610measureBRTryo0.width);
                iM824getMinHeightimpl = Math.max(Constraints.m824getMinHeightimpl(j), placeableMo610measureBRTryo0.height);
            }
            final int i = iM824getMinHeightimpl;
            final int i2 = iM825getMinWidthimpl;
            final Placeable placeable = placeableMo610measureBRTryo0;
            return measureScope.layout$1(i2, i, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.BoxMeasurePolicy$measure$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    BoxKt.access$placeInBox((Placeable.PlacementScope) obj, placeable, measurable, measureScope.getLayoutDirection(), i2, i, this.alignment);
                    return Unit.INSTANCE;
                }
            });
        }
        final Placeable[] placeableArr = new Placeable[list.size()];
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = Constraints.m825getMinWidthimpl(j);
        final Ref$IntRef ref$IntRef2 = new Ref$IntRef();
        ref$IntRef2.element = Constraints.m824getMinHeightimpl(j);
        List list2 = list;
        int size = list2.size();
        int i3 = 0;
        boolean z2 = false;
        while (i3 < size) {
            Measurable measurable2 = (Measurable) list.get(i3);
            MutableScatterMap mutableScatterMap2 = BoxKt.Cache1;
            Object parentData2 = measurable2.getParentData();
            boolean z3 = z;
            BoxChildDataNode boxChildDataNode2 = parentData2 instanceof BoxChildDataNode ? (BoxChildDataNode) parentData2 : null;
            if (boxChildDataNode2 != null ? boxChildDataNode2.matchParentSize : false) {
                z2 = z3;
            } else {
                Placeable placeableMo610measureBRTryo02 = measurable2.mo610measureBRTryo0(j2);
                placeableArr[i3] = placeableMo610measureBRTryo02;
                ref$IntRef.element = Math.max(ref$IntRef.element, placeableMo610measureBRTryo02.width);
                ref$IntRef2.element = Math.max(ref$IntRef2.element, placeableMo610measureBRTryo02.height);
            }
            i3++;
            z = z3;
        }
        if (z2) {
            int i4 = ref$IntRef.element;
            int i5 = i4 != Integer.MAX_VALUE ? i4 : 0;
            int i6 = ref$IntRef2.element;
            long jConstraints = ConstraintsKt.Constraints(i5, i4, i6 != Integer.MAX_VALUE ? i6 : 0, i6);
            int size2 = list2.size();
            for (int i7 = 0; i7 < size2; i7++) {
                Measurable measurable3 = (Measurable) list.get(i7);
                MutableScatterMap mutableScatterMap3 = BoxKt.Cache1;
                Object parentData3 = measurable3.getParentData();
                BoxChildDataNode boxChildDataNode3 = parentData3 instanceof BoxChildDataNode ? (BoxChildDataNode) parentData3 : null;
                if (boxChildDataNode3 != null ? boxChildDataNode3.matchParentSize : false) {
                    placeableArr[i7] = measurable3.mo610measureBRTryo0(jConstraints);
                }
            }
        }
        return measureScope.layout$1(ref$IntRef.element, ref$IntRef2.element, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.BoxMeasurePolicy$measure$5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                Placeable[] placeableArr2 = placeableArr;
                List<Measurable> list3 = list;
                MeasureScope measureScope2 = measureScope;
                Ref$IntRef ref$IntRef3 = ref$IntRef;
                Ref$IntRef ref$IntRef4 = ref$IntRef2;
                BoxMeasurePolicy boxMeasurePolicy = this;
                int length = placeableArr2.length;
                int i8 = 0;
                int i9 = 0;
                while (i9 < length) {
                    int i10 = i8;
                    BoxKt.access$placeInBox(placementScope, placeableArr2[i9], list3.get(i10), measureScope2.getLayoutDirection(), ref$IntRef3.element, ref$IntRef4.element, boxMeasurePolicy.alignment);
                    i9++;
                    i8 = i10 + 1;
                }
                return Unit.INSTANCE;
            }
        });
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BoxMeasurePolicy(alignment=");
        sb.append(this.alignment);
        sb.append(", propagateMinConstraints=");
        return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.propagateMinConstraints, ')');
    }
}
