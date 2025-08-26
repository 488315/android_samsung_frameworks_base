package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class OverscrollModifierElement extends ModifierNodeElement<OverscrollModifierNode> {
    public final OverscrollEffect overscrollEffect;

    public OverscrollModifierElement(OverscrollEffect overscrollEffect) {
        this.overscrollEffect = overscrollEffect;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        return new OverscrollModifierNode(overscrollEffect != null ? overscrollEffect.getNode() : null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OverscrollModifierElement) {
            return Intrinsics.areEqual(this.overscrollEffect, ((OverscrollModifierElement) obj).overscrollEffect);
        }
        return false;
    }

    public final int hashCode() {
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        if (overscrollEffect != null) {
            return overscrollEffect.hashCode();
        }
        return 0;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        OverscrollModifierNode overscrollModifierNode = (OverscrollModifierNode) node;
        DelegatableNode delegatableNode = null;
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        DelegatableNode node2 = overscrollEffect != null ? overscrollEffect.getNode() : null;
        DelegatableNode delegatableNode2 = overscrollModifierNode.overscrollNode;
        if (delegatableNode2 != null) {
            overscrollModifierNode.undelegate(delegatableNode2);
        }
        overscrollModifierNode.overscrollNode = node2;
        if (node2 != null && !((Modifier.Node) node2).node.isAttached) {
            overscrollModifierNode.delegate(node2);
            delegatableNode = node2;
        }
        overscrollModifierNode.overscrollNode = delegatableNode;
    }
}
