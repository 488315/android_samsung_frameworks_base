package androidx.compose.animation;

import androidx.compose.animation.AnimatedContentTransitionScopeImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;

/* loaded from: classes.dex */
final class AnimatedContentMeasurePolicy implements MeasurePolicy {
    public final AnimatedContentTransitionScopeImpl rootScope;

    public AnimatedContentMeasurePolicy(AnimatedContentTransitionScopeImpl<?> animatedContentTransitionScopeImpl) {
        this.rootScope = animatedContentTransitionScopeImpl;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        Integer numValueOf;
        if (list.isEmpty()) {
            numValueOf = null;
        } else {
            numValueOf = Integer.valueOf(((IntrinsicMeasurable) list.get(0)).maxIntrinsicHeight(i));
            int i2 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    Integer numValueOf2 = Integer.valueOf(((IntrinsicMeasurable) list.get(i2)).maxIntrinsicHeight(i));
                    if (numValueOf2.compareTo(numValueOf) > 0) {
                        numValueOf = numValueOf2;
                    }
                    if (i2 == size) {
                        break;
                    }
                    i2++;
                }
            }
        }
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        return 0;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        Integer numValueOf;
        if (list.isEmpty()) {
            numValueOf = null;
        } else {
            numValueOf = Integer.valueOf(((IntrinsicMeasurable) list.get(0)).maxIntrinsicWidth(i));
            int i2 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    Integer numValueOf2 = Integer.valueOf(((IntrinsicMeasurable) list.get(i2)).maxIntrinsicWidth(i));
                    if (numValueOf2.compareTo(numValueOf) > 0) {
                        numValueOf = numValueOf2;
                    }
                    if (i2 == size) {
                        break;
                    }
                    i2++;
                }
            }
        }
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x010c  */
    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
        Placeable placeable;
        Placeable placeable2;
        final int i;
        Placeable placeable3;
        final int i2;
        int size = list.size();
        final Placeable[] placeableArr = new Placeable[size];
        IntSize.Companion.getClass();
        List list2 = list;
        int size2 = list2.size();
        long j2 = 0;
        int i3 = 0;
        while (true) {
            placeable = null;
            if (i3 >= size2) {
                break;
            }
            Measurable measurable = (Measurable) list.get(i3);
            Object parentData = measurable.getParentData();
            AnimatedContentTransitionScopeImpl.ChildData childData = parentData instanceof AnimatedContentTransitionScopeImpl.ChildData ? (AnimatedContentTransitionScopeImpl.ChildData) parentData : null;
            if (childData != null && ((Boolean) ((SnapshotMutableStateImpl) childData.isTarget$delegate).getValue()).booleanValue()) {
                Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
                j2 = (placeableMo610measureBRTryo0.height & 4294967295L) | (placeableMo610measureBRTryo0.width << 32);
                Unit unit = Unit.INSTANCE;
                placeableArr[i3] = placeableMo610measureBRTryo0;
            }
            i3++;
        }
        int size3 = list2.size();
        for (int i4 = 0; i4 < size3; i4++) {
            Measurable measurable2 = (Measurable) list.get(i4);
            if (placeableArr[i4] == null) {
                placeableArr[i4] = measurable2.mo610measureBRTryo0(j);
            }
        }
        if (measureScope.isLookingAhead()) {
            i = (int) (j2 >> 32);
        } else {
            if (size == 0) {
                placeable2 = null;
            } else {
                placeable2 = placeableArr[0];
                int i5 = size - 1;
                if (i5 != 0) {
                    int i6 = placeable2 != null ? placeable2.width : 0;
                    IntProgressionIterator it = new IntRange(1, i5).iterator();
                    while (it.hasNext) {
                        Placeable placeable4 = placeableArr[it.nextInt()];
                        int i7 = placeable4 != null ? placeable4.width : 0;
                        if (i6 < i7) {
                            placeable2 = placeable4;
                            i6 = i7;
                        }
                    }
                }
            }
            i = placeable2 != null ? placeable2.width : 0;
        }
        if (measureScope.isLookingAhead()) {
            i2 = (int) (j2 & 4294967295L);
        } else if (size == 0) {
            placeable3 = placeable;
            i2 = placeable3 == null ? placeable3.height : 0;
        } else {
            placeable3 = placeableArr[0];
            int i8 = size - 1;
            if (i8 != 0) {
                int i9 = placeable3 != null ? placeable3.height : 0;
                IntProgressionIterator it2 = new IntRange(1, i8).iterator();
                placeable = placeable3;
                while (it2.hasNext) {
                    Placeable placeable5 = placeableArr[it2.nextInt()];
                    int i10 = placeable5 != null ? placeable5.height : 0;
                    if (i9 < i10) {
                        placeable = placeable5;
                        i9 = i10;
                    }
                }
                placeable3 = placeable;
            }
            if (placeable3 == null) {
            }
        }
        if (!measureScope.isLookingAhead()) {
            ((SnapshotMutableStateImpl) this.rootScope.measuredSize$delegate).setValue(IntSize.m861boximpl((i << 32) | (i2 & 4294967295L)));
        }
        return measureScope.layout$1(i, i2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.animation.AnimatedContentMeasurePolicy$measure$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                Placeable[] placeableArr2 = placeableArr;
                AnimatedContentMeasurePolicy animatedContentMeasurePolicy = this;
                int i11 = i;
                int i12 = i2;
                for (Placeable placeable6 : placeableArr2) {
                    if (placeable6 != null) {
                        IntSize.Companion companion = IntSize.Companion;
                        long jMo353alignKFBX0sM = animatedContentMeasurePolicy.rootScope.contentAlignment.mo353alignKFBX0sM((placeable6.width << 32) | (placeable6.height & 4294967295L), (i11 << 32) | (i12 & 4294967295L), LayoutDirection.Ltr);
                        IntOffset.Companion companion2 = IntOffset.Companion;
                        placementScope.place(placeable6, (int) (jMo353alignKFBX0sM >> 32), (int) (jMo353alignKFBX0sM & 4294967295L), 0.0f);
                    }
                }
                return Unit.INSTANCE;
            }
        });
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        Integer numValueOf;
        if (list.isEmpty()) {
            numValueOf = null;
        } else {
            numValueOf = Integer.valueOf(((IntrinsicMeasurable) list.get(0)).minIntrinsicHeight(i));
            int i2 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    Integer numValueOf2 = Integer.valueOf(((IntrinsicMeasurable) list.get(i2)).minIntrinsicHeight(i));
                    if (numValueOf2.compareTo(numValueOf) > 0) {
                        numValueOf = numValueOf2;
                    }
                    if (i2 == size) {
                        break;
                    }
                    i2++;
                }
            }
        }
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        return 0;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        Integer numValueOf;
        if (list.isEmpty()) {
            numValueOf = null;
        } else {
            numValueOf = Integer.valueOf(((IntrinsicMeasurable) list.get(0)).minIntrinsicWidth(i));
            int i2 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    Integer numValueOf2 = Integer.valueOf(((IntrinsicMeasurable) list.get(i2)).minIntrinsicWidth(i));
                    if (numValueOf2.compareTo(numValueOf) > 0) {
                        numValueOf = numValueOf2;
                    }
                    if (i2 == size) {
                        break;
                    }
                    i2++;
                }
            }
        }
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        return 0;
    }
}
