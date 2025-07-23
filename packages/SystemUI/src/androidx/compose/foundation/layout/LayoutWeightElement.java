package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LayoutWeightElement extends ModifierNodeElement<LayoutWeightNode> {
    public final boolean fill;
    public final float weight;

    public LayoutWeightElement(float f, boolean z) {
        this.weight = f;
        this.fill = z;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new LayoutWeightNode(this.weight, this.fill);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        LayoutWeightElement layoutWeightElement = obj instanceof LayoutWeightElement ? (LayoutWeightElement) obj : null;
        return layoutWeightElement != null && this.weight == layoutWeightElement.weight && this.fill == layoutWeightElement.fill;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.fill) + (Float.hashCode(this.weight) * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        LayoutWeightNode layoutWeightNode = (LayoutWeightNode) node;
        layoutWeightNode.weight = this.weight;
        layoutWeightNode.fill = this.fill;
    }
}
