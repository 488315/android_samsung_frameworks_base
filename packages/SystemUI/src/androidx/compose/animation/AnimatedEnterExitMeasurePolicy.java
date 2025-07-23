package androidx.compose.animation;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.IntSize;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AnimatedEnterExitMeasurePolicy implements MeasurePolicy {
    public boolean hasLookaheadOccurred;
    public final AnimatedVisibilityScopeImpl scope;

    public AnimatedEnterExitMeasurePolicy(AnimatedVisibilityScopeImpl animatedVisibilityScopeImpl) {
        this.scope = animatedVisibilityScopeImpl;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        if (list.isEmpty()) {
            return 0;
        }
        int maxIntrinsicHeight = ((IntrinsicMeasurable) list.get(0)).maxIntrinsicHeight(i);
        int i2 = 1;
        int size = list.size() - 1;
        if (1 <= size) {
            while (true) {
                int maxIntrinsicHeight2 = ((IntrinsicMeasurable) list.get(i2)).maxIntrinsicHeight(i);
                if (maxIntrinsicHeight2 > maxIntrinsicHeight) {
                    maxIntrinsicHeight = maxIntrinsicHeight2;
                }
                if (i2 == size) {
                    break;
                }
                i2++;
            }
        }
        return maxIntrinsicHeight;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        if (list.isEmpty()) {
            return 0;
        }
        int maxIntrinsicWidth = ((IntrinsicMeasurable) list.get(0)).maxIntrinsicWidth(i);
        int i2 = 1;
        int size = list.size() - 1;
        if (1 <= size) {
            while (true) {
                int maxIntrinsicWidth2 = ((IntrinsicMeasurable) list.get(i2)).maxIntrinsicWidth(i);
                if (maxIntrinsicWidth2 > maxIntrinsicWidth) {
                    maxIntrinsicWidth = maxIntrinsicWidth2;
                }
                if (i2 == size) {
                    break;
                }
                i2++;
            }
        }
        return maxIntrinsicWidth;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
        MeasureResult layout$1;
        final ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            Placeable mo608measureBRTryo0 = ((Measurable) list.get(i3)).mo608measureBRTryo0(j);
            i = Math.max(i, mo608measureBRTryo0.width);
            i2 = Math.max(i2, mo608measureBRTryo0.height);
            arrayList.add(mo608measureBRTryo0);
        }
        boolean isLookingAhead = measureScope.isLookingAhead();
        AnimatedVisibilityScopeImpl animatedVisibilityScopeImpl = this.scope;
        if (isLookingAhead) {
            this.hasLookaheadOccurred = true;
            ((SnapshotMutableStateImpl) animatedVisibilityScopeImpl.targetSize).setValue(IntSize.m859boximpl((i2 & 4294967295L) | (i << 32)));
        } else if (!this.hasLookaheadOccurred) {
            ((SnapshotMutableStateImpl) animatedVisibilityScopeImpl.targetSize).setValue(IntSize.m859boximpl((i2 & 4294967295L) | (i << 32)));
        }
        layout$1 = measureScope.layout$1(i, i2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.animation.AnimatedEnterExitMeasurePolicy$measure$1
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
                int size2 = list2.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    placementScope.place(list2.get(i4), 0, 0, 0.0f);
                }
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        if (list.isEmpty()) {
            return 0;
        }
        int minIntrinsicHeight = ((IntrinsicMeasurable) list.get(0)).minIntrinsicHeight(i);
        int i2 = 1;
        int size = list.size() - 1;
        if (1 <= size) {
            while (true) {
                int minIntrinsicHeight2 = ((IntrinsicMeasurable) list.get(i2)).minIntrinsicHeight(i);
                if (minIntrinsicHeight2 > minIntrinsicHeight) {
                    minIntrinsicHeight = minIntrinsicHeight2;
                }
                if (i2 == size) {
                    break;
                }
                i2++;
            }
        }
        return minIntrinsicHeight;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        if (list.isEmpty()) {
            return 0;
        }
        int minIntrinsicWidth = ((IntrinsicMeasurable) list.get(0)).minIntrinsicWidth(i);
        int i2 = 1;
        int size = list.size() - 1;
        if (1 <= size) {
            while (true) {
                int minIntrinsicWidth2 = ((IntrinsicMeasurable) list.get(i2)).minIntrinsicWidth(i);
                if (minIntrinsicWidth2 > minIntrinsicWidth) {
                    minIntrinsicWidth = minIntrinsicWidth2;
                }
                if (i2 == size) {
                    break;
                }
                i2++;
            }
        }
        return minIntrinsicWidth;
    }
}
