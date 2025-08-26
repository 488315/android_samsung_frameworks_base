package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
final class ExcludeFromSystemGestureElement extends ModifierNodeElement<ExcludeFromSystemGestureNode> {
    public final Function1 exclusion;

    public ExcludeFromSystemGestureElement(Function1 function1) {
        this.exclusion = function1;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new ExcludeFromSystemGestureNode(this.exclusion);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ExcludeFromSystemGestureElement) {
            return this.exclusion == ((ExcludeFromSystemGestureElement) obj).exclusion;
        }
        return false;
    }

    public final int hashCode() {
        Function1 function1 = this.exclusion;
        if (function1 != null) {
            return function1.hashCode();
        }
        return 0;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((ExcludeFromSystemGestureNode) node).rect = this.exclusion;
    }
}
