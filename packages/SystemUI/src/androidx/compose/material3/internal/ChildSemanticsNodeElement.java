package androidx.compose.material3.internal;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.SemanticsModifierNodeKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ChildSemanticsNodeElement extends ModifierNodeElement<ChildSemanticsNode> {
    public final Function1 properties;

    public ChildSemanticsNodeElement(Function1 function1) {
        this.properties = function1;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new ChildSemanticsNode(this.properties);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ChildSemanticsNodeElement) {
            return this.properties == ((ChildSemanticsNodeElement) obj).properties;
        }
        return false;
    }

    public final int hashCode() {
        return this.properties.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ChildSemanticsNode childSemanticsNode = (ChildSemanticsNode) node;
        childSemanticsNode.properties = this.properties;
        SemanticsModifierNodeKt.invalidateSemantics(childSemanticsNode);
    }
}
