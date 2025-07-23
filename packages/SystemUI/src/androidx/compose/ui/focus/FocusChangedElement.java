package androidx.compose.ui.focus;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FocusChangedElement extends ModifierNodeElement<FocusChangedNode> {
    public final Function1 onFocusChanged;

    public FocusChangedElement(Function1 function1) {
        this.onFocusChanged = function1;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new FocusChangedNode(this.onFocusChanged);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FocusChangedElement) {
            return this.onFocusChanged == ((FocusChangedElement) obj).onFocusChanged;
        }
        return false;
    }

    public final int hashCode() {
        return this.onFocusChanged.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((FocusChangedNode) node).onFocusChanged = this.onFocusChanged;
    }
}
