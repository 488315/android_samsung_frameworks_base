package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;

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
            LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode3);
            LayoutNode layoutNodeRequireLayoutNode2 = DelegatableNodeKt.requireLayoutNode(focusTargetNode4);
            if (!Intrinsics.areEqual(layoutNodeRequireLayoutNode, layoutNodeRequireLayoutNode2)) {
                MutableVector mutableVector = new MutableVector(new LayoutNode[16], 0);
                while (layoutNodeRequireLayoutNode != null) {
                    mutableVector.add(0, layoutNodeRequireLayoutNode);
                    layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                }
                MutableVector mutableVector2 = new MutableVector(new LayoutNode[16], 0);
                while (layoutNodeRequireLayoutNode2 != null) {
                    mutableVector2.add(0, layoutNodeRequireLayoutNode2);
                    layoutNodeRequireLayoutNode2 = layoutNodeRequireLayoutNode2.getParent$ui_release();
                }
                int iMin = Math.min(mutableVector.size - 1, mutableVector2.size - 1);
                if (iMin >= 0) {
                    while (Intrinsics.areEqual(mutableVector.content[i], mutableVector2.content[i])) {
                        if (i != iMin) {
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
