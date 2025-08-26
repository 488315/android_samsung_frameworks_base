package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorizedFiniteAnimationSpec;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;

/* loaded from: classes.dex */
final class CombinedSpec<T> implements FiniteAnimationSpec<T> {
    public final List specs;

    public CombinedSpec(List<? extends Pair<Integer, ? extends FiniteAnimationSpec<T>>> list) {
        this.specs = list;
    }

    @Override // androidx.compose.animation.core.AnimationSpec
    public final VectorizedFiniteAnimationSpec vectorize(TwoWayConverter twoWayConverter) {
        List list = this.specs;
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(new Pair(Long.valueOf(((Number) r3.component1()).intValue() * 1000000), ((FiniteAnimationSpec) ((Pair) list.get(i)).component2()).vectorize(twoWayConverter)));
        }
        return new VectorizedCombinedSpec(arrayList);
    }
}
