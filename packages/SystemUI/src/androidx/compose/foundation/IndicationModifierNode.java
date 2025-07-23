package androidx.compose.foundation;

import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatingNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class IndicationModifierNode extends DelegatingNode {
    public DelegatableNode indicationNode;

    public IndicationModifierNode(DelegatableNode delegatableNode) {
        this.indicationNode = delegatableNode;
        delegate(delegatableNode);
    }
}
