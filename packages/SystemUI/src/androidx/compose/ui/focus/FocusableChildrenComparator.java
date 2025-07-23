package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FocusableChildrenComparator implements Comparator<FocusTargetNode> {
    public static final FocusableChildrenComparator INSTANCE = new FocusableChildrenComparator();

    private FocusableChildrenComparator() {
    }

    @Override // java.util.Comparator
    public final int compare(FocusTargetNode focusTargetNode, FocusTargetNode focusTargetNode2) {
        FocusTargetNode focusTargetNode3 = focusTargetNode;
        FocusTargetNode focusTargetNode4 = focusTargetNode2;
        int i = 0;
        if (FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode3) && FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode4)) {
            LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode3);
            LayoutNode requireLayoutNode2 = DelegatableNodeKt.requireLayoutNode(focusTargetNode4);
            if (!Intrinsics.areEqual(requireLayoutNode, requireLayoutNode2)) {
                MutableVector mutableVector = new MutableVector(new LayoutNode[16], 0);
                while (requireLayoutNode != null) {
                    mutableVector.add(0, requireLayoutNode);
                    requireLayoutNode = requireLayoutNode.getParent$ui_release();
                }
                MutableVector mutableVector2 = new MutableVector(new LayoutNode[16], 0);
                while (requireLayoutNode2 != null) {
                    mutableVector2.add(0, requireLayoutNode2);
                    requireLayoutNode2 = requireLayoutNode2.getParent$ui_release();
                }
                int min = Math.min(mutableVector.size - 1, mutableVector2.size - 1);
                if (min >= 0) {
                    while (Intrinsics.areEqual(mutableVector.content[i], mutableVector2.content[i])) {
                        if (i != min) {
                            i++;
                        }
                    }
                    return Intrinsics.compare(((LayoutNode) mutableVector.content[i]).getPlaceOrder$ui_release(), ((LayoutNode) mutableVector2.content[i]).getPlaceOrder$ui_release());
                }
                throw new IllegalStateException("Could not find a common ancestor between the two FocusModifiers.");
            }
        } else {
            if (FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode3)) {
                return -1;
            }
            if (FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode4)) {
                return 1;
            }
        }
        return 0;
    }
}
