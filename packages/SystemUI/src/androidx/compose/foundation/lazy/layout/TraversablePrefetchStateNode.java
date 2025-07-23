package androidx.compose.foundation.lazy.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.TraversableNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TraversablePrefetchStateNode extends Modifier.Node implements TraversableNode {
    public LazyLayoutPrefetchState prefetchState;
    public final String traverseKey = "androidx.compose.foundation.lazy.layout.TraversablePrefetchStateNode";

    public TraversablePrefetchStateNode(LazyLayoutPrefetchState lazyLayoutPrefetchState) {
        this.prefetchState = lazyLayoutPrefetchState;
    }

    @Override // androidx.compose.ui.node.TraversableNode
    public final Object getTraverseKey() {
        return this.traverseKey;
    }
}
