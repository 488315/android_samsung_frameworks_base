package androidx.compose.ui.node;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DepthSortedSetsForDifferentPasses {
    public final DepthSortedSet lookaheadSet;
    public final DepthSortedSet set;

    public DepthSortedSetsForDifferentPasses(boolean z) {
        this.lookaheadSet = new DepthSortedSet(z);
        this.set = new DepthSortedSet(z);
    }

    public final void add(LayoutNode layoutNode, boolean z) {
        DepthSortedSet depthSortedSet = this.set;
        DepthSortedSet depthSortedSet2 = this.lookaheadSet;
        if (z) {
            depthSortedSet2.add(layoutNode);
            depthSortedSet.add(layoutNode);
        } else {
            if (depthSortedSet2.contains(layoutNode)) {
                return;
            }
            depthSortedSet.add(layoutNode);
        }
    }

    public final boolean contains(LayoutNode layoutNode, boolean z) {
        boolean contains = this.lookaheadSet.contains(layoutNode);
        return z ? contains : contains || this.set.contains(layoutNode);
    }

    public final boolean isNotEmpty() {
        return !(this.set.set.isEmpty() && this.lookaheadSet.set.isEmpty());
    }
}
