package androidx.compose.ui.input.key;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class KeyInputElement extends ModifierNodeElement<KeyInputNode> {
    public final Function1 onKeyEvent;
    public final Function1 onPreKeyEvent;

    public KeyInputElement(Function1 function1, Function1 function12) {
        this.onKeyEvent = function1;
        this.onPreKeyEvent = function12;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new KeyInputNode(this.onKeyEvent, this.onPreKeyEvent);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyInputElement)) {
            return false;
        }
        KeyInputElement keyInputElement = (KeyInputElement) obj;
        return this.onKeyEvent == keyInputElement.onKeyEvent && this.onPreKeyEvent == keyInputElement.onPreKeyEvent;
    }

    public final int hashCode() {
        Function1 function1 = this.onKeyEvent;
        int hashCode = (function1 != null ? function1.hashCode() : 0) * 31;
        Function1 function12 = this.onPreKeyEvent;
        return hashCode + (function12 != null ? function12.hashCode() : 0);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        KeyInputNode keyInputNode = (KeyInputNode) node;
        keyInputNode.onEvent = this.onKeyEvent;
        keyInputNode.onPreEvent = this.onPreKeyEvent;
    }
}
