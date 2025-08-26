package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
final class OnPlacedElement extends ModifierNodeElement<OnPlacedNode> {
    public final Function1 onPlaced;

    public OnPlacedElement(Function1 function1) {
        this.onPlaced = function1;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new OnPlacedNode(this.onPlaced);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OnPlacedElement) {
            return this.onPlaced == ((OnPlacedElement) obj).onPlaced;
        }
        return false;
    }

    public final int hashCode() {
        return this.onPlaced.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((OnPlacedNode) node).callback = this.onPlaced;
    }
}
