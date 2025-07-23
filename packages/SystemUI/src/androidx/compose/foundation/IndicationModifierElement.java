package androidx.compose.foundation;

import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class IndicationModifierElement extends ModifierNodeElement<IndicationModifierNode> {
    public final IndicationNodeFactory indication;
    public final InteractionSource interactionSource;

    public IndicationModifierElement(InteractionSource interactionSource, IndicationNodeFactory indicationNodeFactory) {
        this.interactionSource = interactionSource;
        this.indication = indicationNodeFactory;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new IndicationModifierNode(this.indication.create(this.interactionSource));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IndicationModifierElement)) {
            return false;
        }
        IndicationModifierElement indicationModifierElement = (IndicationModifierElement) obj;
        return Intrinsics.areEqual(this.interactionSource, indicationModifierElement.interactionSource) && Intrinsics.areEqual(this.indication, indicationModifierElement.indication);
    }

    public final int hashCode() {
        return this.indication.hashCode() + (this.interactionSource.hashCode() * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        IndicationModifierNode indicationModifierNode = (IndicationModifierNode) node;
        DelegatableNode create = this.indication.create(this.interactionSource);
        indicationModifierNode.undelegate(indicationModifierNode.indicationNode);
        indicationModifierNode.indicationNode = create;
        indicationModifierNode.delegate(create);
    }
}
