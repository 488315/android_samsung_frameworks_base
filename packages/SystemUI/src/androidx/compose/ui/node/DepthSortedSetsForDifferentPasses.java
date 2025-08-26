package androidx.compose.ui.node;

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
        boolean zContains = this.lookaheadSet.contains(layoutNode);
        return z ? zContains : zContains || this.set.contains(layoutNode);
    }

    public final boolean isNotEmpty() {
        return !(this.set.set.isEmpty() && this.lookaheadSet.set.isEmpty());
    }
}
