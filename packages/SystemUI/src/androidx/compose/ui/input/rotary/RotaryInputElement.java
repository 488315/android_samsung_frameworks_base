package androidx.compose.ui.input.rotary;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
final class RotaryInputElement extends ModifierNodeElement<RotaryInputNode> {
    public final Function1 onPreRotaryScrollEvent;
    public final Function1 onRotaryScrollEvent;

    public RotaryInputElement(Function1 function1, Function1 function12) {
        this.onRotaryScrollEvent = function1;
        this.onPreRotaryScrollEvent = function12;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new RotaryInputNode(this.onRotaryScrollEvent, this.onPreRotaryScrollEvent);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RotaryInputElement)) {
            return false;
        }
        RotaryInputElement rotaryInputElement = (RotaryInputElement) obj;
        return this.onRotaryScrollEvent == rotaryInputElement.onRotaryScrollEvent && this.onPreRotaryScrollEvent == rotaryInputElement.onPreRotaryScrollEvent;
    }

    public final int hashCode() {
        Function1 function1 = this.onRotaryScrollEvent;
        int iHashCode = (function1 != null ? function1.hashCode() : 0) * 31;
        Function1 function12 = this.onPreRotaryScrollEvent;
        return iHashCode + (function12 != null ? function12.hashCode() : 0);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        RotaryInputNode rotaryInputNode = (RotaryInputNode) node;
        rotaryInputNode.onEvent = this.onRotaryScrollEvent;
        rotaryInputNode.onPreEvent = this.onPreRotaryScrollEvent;
    }
}
