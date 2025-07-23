package androidx.compose.foundation.lazy.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TraversablePrefetchStateModifierElement extends ModifierNodeElement<TraversablePrefetchStateNode> {
    public final LazyLayoutPrefetchState prefetchState;

    public TraversablePrefetchStateModifierElement(LazyLayoutPrefetchState lazyLayoutPrefetchState) {
        this.prefetchState = lazyLayoutPrefetchState;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new TraversablePrefetchStateNode(this.prefetchState);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof TraversablePrefetchStateModifierElement) && Intrinsics.areEqual(this.prefetchState, ((TraversablePrefetchStateModifierElement) obj).prefetchState);
    }

    public final int hashCode() {
        return this.prefetchState.hashCode();
    }

    public final String toString() {
        return "TraversablePrefetchStateModifierElement(prefetchState=" + this.prefetchState + ')';
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((TraversablePrefetchStateNode) node).prefetchState = this.prefetchState;
    }
}
