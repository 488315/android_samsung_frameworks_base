package androidx.compose.material3;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class InteractionSourceModifierElement extends ModifierNodeElement<InteractionSourceModifierNode> {
    public final MutableInteractionSource interactionSource;

    public InteractionSourceModifierElement(MutableInteractionSource mutableInteractionSource) {
        this.interactionSource = mutableInteractionSource;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new InteractionSourceModifierNode(this.interactionSource);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof InteractionSourceModifierElement) && Intrinsics.areEqual(this.interactionSource, ((InteractionSourceModifierElement) obj).interactionSource);
    }

    public final int hashCode() {
        return this.interactionSource.hashCode();
    }

    public final String toString() {
        return "InteractionSourceModifierElement(interactionSource=" + this.interactionSource + ')';
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((InteractionSourceModifierNode) node).getClass();
    }
}
