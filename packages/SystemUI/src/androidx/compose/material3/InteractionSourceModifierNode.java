package androidx.compose.material3;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.TraversableNode;

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
