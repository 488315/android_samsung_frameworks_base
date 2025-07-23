package androidx.compose.material3;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.TraversableNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class InteractionSourceModifierNode extends Modifier.Node implements TraversableNode {
    public final Object traverseKey = InteractionSourceModifierNodeTraverseKey.INSTANCE;

    public InteractionSourceModifierNode(MutableInteractionSource mutableInteractionSource) {
    }

    @Override // androidx.compose.ui.node.TraversableNode
    public final Object getTraverseKey() {
        return this.traverseKey;
    }
}
