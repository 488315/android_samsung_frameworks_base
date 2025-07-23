package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
