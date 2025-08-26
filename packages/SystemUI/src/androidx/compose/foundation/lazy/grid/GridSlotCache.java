package androidx.compose.foundation.lazy.grid;

import androidx.compose.ui.unit.ConstraintsKt;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
final class GridSlotCache implements LazyGridSlotsProvider {
    public long cachedConstraints = ConstraintsKt.Constraints$default(0, 0, 0, 0, 15);
    public float cachedDensity;
    public LazyGridSlots cachedSizes;
    public final Function2 calculation;

    public GridSlotCache(Function2 function2) {
        this.calculation = function2;
    }
}
