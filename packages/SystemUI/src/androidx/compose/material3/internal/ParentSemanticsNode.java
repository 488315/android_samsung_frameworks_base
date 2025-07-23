package androidx.compose.material3.internal;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.SemanticsModifierNode;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ParentSemanticsNode extends Modifier.Node implements TraversableNode, SemanticsModifierNode {
    public final Function1 properties;
    public boolean semanticsConsumed;
    public final ParentSemanticsNodeKey traverseKey = ParentSemanticsNodeKey.INSTANCE;

    public ParentSemanticsNode(Function1 function1) {
        this.properties = function1;
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        if (this.semanticsConsumed) {
            return;
        }
        this.properties.mo779invoke(semanticsPropertyReceiver);
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final boolean getShouldMergeDescendantSemantics() {
        return true;
    }

    @Override // androidx.compose.ui.node.TraversableNode
    public final Object getTraverseKey() {
        return this.traverseKey;
    }
}
