package androidx.compose.ui.node;

import androidx.collection.MutableObjectIntMap;
import androidx.collection.ObjectIntMapKt;
import androidx.compose.ui.internal.InlineClassHelperKt;

/* loaded from: classes.dex */
public final class DepthSortedSet {
    public final boolean extraAssertions;
    public MutableObjectIntMap mapOfOriginalDepth;
    public final SortedSet set = new SortedSet(DepthSortedSetKt.DepthComparator);

    public DepthSortedSet(boolean z) {
        this.extraAssertions = z;
    }

    public final void add(LayoutNode layoutNode) {
        if (!layoutNode.isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("DepthSortedSet.add called on an unattached node");
        }
        if (this.extraAssertions) {
            if (this.mapOfOriginalDepth == null) {
                this.mapOfOriginalDepth = ObjectIntMapKt.mutableObjectIntMapOf();
            }
            MutableObjectIntMap mutableObjectIntMap = this.mapOfOriginalDepth;
            mutableObjectIntMap.getClass();
            int iFindKeyIndex = mutableObjectIntMap.findKeyIndex(layoutNode);
            int i = iFindKeyIndex >= 0 ? mutableObjectIntMap.values[iFindKeyIndex] : Integer.MAX_VALUE;
            if (i == Integer.MAX_VALUE) {
                mutableObjectIntMap.set(layoutNode.depth, layoutNode);
            } else if (i != layoutNode.depth) {
                InlineClassHelperKt.throwIllegalStateException("invalid node depth");
            }
        }
        this.set.add(layoutNode);
    }

    public final boolean contains(LayoutNode layoutNode) {
        boolean zContains = this.set.contains(layoutNode);
        if (this.extraAssertions) {
            if (this.mapOfOriginalDepth == null) {
                this.mapOfOriginalDepth = ObjectIntMapKt.mutableObjectIntMapOf();
            }
            MutableObjectIntMap mutableObjectIntMap = this.mapOfOriginalDepth;
            mutableObjectIntMap.getClass();
            if (zContains == (mutableObjectIntMap.findKeyIndex(layoutNode) >= 0)) {
                return zContains;
            }
            InlineClassHelperKt.throwIllegalStateException("inconsistency in TreeSet");
        }
        return zContains;
    }

    public final boolean remove(LayoutNode layoutNode) {
        if (!layoutNode.isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("DepthSortedSet.remove called on an unattached node");
        }
        boolean zRemove = this.set.remove(layoutNode);
        if (this.extraAssertions) {
            if (this.mapOfOriginalDepth == null) {
                this.mapOfOriginalDepth = ObjectIntMapKt.mutableObjectIntMapOf();
            }
            MutableObjectIntMap mutableObjectIntMap = this.mapOfOriginalDepth;
            mutableObjectIntMap.getClass();
            if (mutableObjectIntMap.findKeyIndex(layoutNode) >= 0) {
                int i = mutableObjectIntMap.get(layoutNode);
                int iFindKeyIndex = mutableObjectIntMap.findKeyIndex(layoutNode);
                if (iFindKeyIndex >= 0) {
                    mutableObjectIntMap.removeValueAt(iFindKeyIndex);
                }
                if (i == (zRemove ? layoutNode.depth : Integer.MAX_VALUE)) {
                    return zRemove;
                }
                InlineClassHelperKt.throwIllegalStateException("invalid node depth");
            }
        }
        return zRemove;
    }

    public final String toString() {
        return this.set.toString();
    }
}
