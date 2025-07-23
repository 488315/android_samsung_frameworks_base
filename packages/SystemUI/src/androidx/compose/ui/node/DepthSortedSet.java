package androidx.compose.ui.node;

import androidx.collection.MutableObjectIntMap;
import androidx.collection.ObjectIntMapKt;
import androidx.compose.ui.internal.InlineClassHelperKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int findKeyIndex = mutableObjectIntMap.findKeyIndex(layoutNode);
            int i = findKeyIndex >= 0 ? mutableObjectIntMap.values[findKeyIndex] : Integer.MAX_VALUE;
            if (i == Integer.MAX_VALUE) {
                mutableObjectIntMap.set(layoutNode.depth, layoutNode);
            } else if (i != layoutNode.depth) {
                InlineClassHelperKt.throwIllegalStateException("invalid node depth");
            }
        }
        this.set.add(layoutNode);
    }

    public final boolean contains(LayoutNode layoutNode) {
        boolean contains = this.set.contains(layoutNode);
        if (this.extraAssertions) {
            if (this.mapOfOriginalDepth == null) {
                this.mapOfOriginalDepth = ObjectIntMapKt.mutableObjectIntMapOf();
            }
            MutableObjectIntMap mutableObjectIntMap = this.mapOfOriginalDepth;
            mutableObjectIntMap.getClass();
            if (contains == (mutableObjectIntMap.findKeyIndex(layoutNode) >= 0)) {
                return contains;
            }
            InlineClassHelperKt.throwIllegalStateException("inconsistency in TreeSet");
        }
        return contains;
    }

    public final boolean remove(LayoutNode layoutNode) {
        if (!layoutNode.isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("DepthSortedSet.remove called on an unattached node");
        }
        boolean remove = this.set.remove(layoutNode);
        if (this.extraAssertions) {
            if (this.mapOfOriginalDepth == null) {
                this.mapOfOriginalDepth = ObjectIntMapKt.mutableObjectIntMapOf();
            }
            MutableObjectIntMap mutableObjectIntMap = this.mapOfOriginalDepth;
            mutableObjectIntMap.getClass();
            if (mutableObjectIntMap.findKeyIndex(layoutNode) >= 0) {
                int i = mutableObjectIntMap.get(layoutNode);
                int findKeyIndex = mutableObjectIntMap.findKeyIndex(layoutNode);
                if (findKeyIndex >= 0) {
                    mutableObjectIntMap.removeValueAt(findKeyIndex);
                }
                if (i == (remove ? layoutNode.depth : Integer.MAX_VALUE)) {
                    return remove;
                }
                InlineClassHelperKt.throwIllegalStateException("invalid node depth");
            }
        }
        return remove;
    }

    public final String toString() {
        return this.set.toString();
    }
}
