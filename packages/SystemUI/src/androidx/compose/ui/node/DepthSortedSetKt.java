package androidx.compose.ui.node;

import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class DepthSortedSetKt {
    public static final DepthSortedSetKt$DepthComparator$1 DepthComparator = new Comparator<LayoutNode>() { // from class: androidx.compose.ui.node.DepthSortedSetKt$DepthComparator$1
        @Override // java.util.Comparator
        public final int compare(LayoutNode layoutNode, LayoutNode layoutNode2) {
            LayoutNode layoutNode3 = layoutNode;
            LayoutNode layoutNode4 = layoutNode2;
            int iCompare = Intrinsics.compare(layoutNode3.depth, layoutNode4.depth);
            return iCompare != 0 ? iCompare : Intrinsics.compare(layoutNode3.hashCode(), layoutNode4.hashCode());
        }
    };
}
