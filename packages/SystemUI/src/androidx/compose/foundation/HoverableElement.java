package androidx.compose.foundation;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class HoverableElement extends ModifierNodeElement<HoverableNode> {
    public final MutableInteractionSource interactionSource;

    public HoverableElement(MutableInteractionSource mutableInteractionSource) {
        this.interactionSource = mutableInteractionSource;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new HoverableNode(this.interactionSource);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof HoverableElement) && Intrinsics.areEqual(((HoverableElement) obj).interactionSource, this.interactionSource);
    }

    public final int hashCode() {
        return this.interactionSource.hashCode() * 31;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        HoverableNode hoverableNode = (HoverableNode) node;
        MutableInteractionSource mutableInteractionSource = hoverableNode.interactionSource;
        MutableInteractionSource mutableInteractionSource2 = this.interactionSource;
        if (Intrinsics.areEqual(mutableInteractionSource, mutableInteractionSource2)) {
            return;
        }
        hoverableNode.tryEmitExit();
        hoverableNode.interactionSource = mutableInteractionSource2;
    }
}
