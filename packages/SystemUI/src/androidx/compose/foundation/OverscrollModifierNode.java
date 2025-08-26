package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatingNode;

/* loaded from: classes.dex */
final class OverscrollModifierNode extends DelegatingNode {
    public DelegatableNode overscrollNode;

    public OverscrollModifierNode(DelegatableNode delegatableNode) {
        this.overscrollNode = delegatableNode;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        DelegatableNode delegatableNode = this.overscrollNode;
        if (delegatableNode == null || ((Modifier.Node) delegatableNode).node.isAttached) {
            delegatableNode = null;
        } else {
            delegate(delegatableNode);
        }
        this.overscrollNode = delegatableNode;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        DelegatableNode delegatableNode = this.overscrollNode;
        if (delegatableNode != null) {
            undelegate(delegatableNode);
        }
    }
}
