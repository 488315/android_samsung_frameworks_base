package androidx.compose.ui.node;

import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DepthSortedSetKt {
    public static final DepthSortedSetKt$DepthComparator$1 DepthComparator = new Comparator<LayoutNode>() { // from class: androidx.compose.ui.node.DepthSortedSetKt$DepthComparator$1
        @Override // java.util.Comparator
        public final int compare(LayoutNode layoutNode, LayoutNode layoutNode2) {
            LayoutNode layoutNode3 = layoutNode;
            LayoutNode layoutNode4 = layoutNode2;
            int compare = Intrinsics.compare(layoutNode3.depth, layoutNode4.depth);
            return compare != 0 ? compare : Intrinsics.compare(layoutNode3.hashCode(), layoutNode4.hashCode());
        }
    };
}
