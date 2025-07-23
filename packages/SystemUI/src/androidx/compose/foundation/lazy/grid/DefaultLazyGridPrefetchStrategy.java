package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.lazy.layout.LazyLayoutPrefetchState;
import androidx.compose.runtime.collection.MutableVector;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DefaultLazyGridPrefetchStrategy implements LazyGridPrefetchStrategy {
    public final MutableVector currentLinePrefetchHandles;
    public int lineToPrefetch;
    public final int nestedPrefetchItemCount;
    public boolean wasScrollingForward;

    public DefaultLazyGridPrefetchStrategy() {
        this(0, 1, null);
    }

    public DefaultLazyGridPrefetchStrategy(int i) {
        this.nestedPrefetchItemCount = i;
        this.lineToPrefetch = -1;
        this.currentLinePrefetchHandles = new MutableVector(new LazyLayoutPrefetchState.PrefetchHandle[16], 0);
    }

    public /* synthetic */ DefaultLazyGridPrefetchStrategy(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 2 : i);
    }
}
