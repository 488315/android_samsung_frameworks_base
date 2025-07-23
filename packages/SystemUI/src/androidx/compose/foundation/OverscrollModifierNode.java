package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatingNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
