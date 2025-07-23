package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class OnGloballyPositionedElement extends ModifierNodeElement<OnGloballyPositionedNode> {
    public final Function1 onGloballyPositioned;

    public OnGloballyPositionedElement(Function1 function1) {
        this.onGloballyPositioned = function1;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new OnGloballyPositionedNode(this.onGloballyPositioned);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OnGloballyPositionedElement) {
            return this.onGloballyPositioned == ((OnGloballyPositionedElement) obj).onGloballyPositioned;
        }
        return false;
    }

    public final int hashCode() {
        return this.onGloballyPositioned.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((OnGloballyPositionedNode) node).callback = this.onGloballyPositioned;
    }
}
