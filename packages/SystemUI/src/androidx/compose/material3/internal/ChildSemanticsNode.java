package androidx.compose.material3.internal;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.SemanticsModifierNode;
import androidx.compose.ui.node.SemanticsModifierNodeKt;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.node.TraversableNodeKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ChildSemanticsNode extends Modifier.Node implements SemanticsModifierNode {
    public Function1 properties;

    public ChildSemanticsNode(Function1 function1) {
        this.properties = function1;
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(final SemanticsPropertyReceiver semanticsPropertyReceiver) {
        TraversableNodeKt.traverseAncestors(this, ParentSemanticsNodeKey.INSTANCE, new Function1() { // from class: androidx.compose.material3.internal.ChildSemanticsNode$applySemantics$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ParentSemanticsNode parentSemanticsNode = (ParentSemanticsNode) ((TraversableNode) obj);
                SemanticsPropertyReceiver semanticsPropertyReceiver2 = SemanticsPropertyReceiver.this;
                parentSemanticsNode.semanticsConsumed = true;
                parentSemanticsNode.properties.mo779invoke(semanticsPropertyReceiver2);
                SemanticsModifierNodeKt.invalidateSemantics(parentSemanticsNode);
                return Boolean.FALSE;
            }
        });
        this.properties.mo779invoke(semanticsPropertyReceiver);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        TraversableNodeKt.traverseAncestors(this, ParentSemanticsNodeKey.INSTANCE, new Function1() { // from class: androidx.compose.material3.internal.ChildSemanticsNode$onDetach$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ParentSemanticsNode parentSemanticsNode = (ParentSemanticsNode) ((TraversableNode) obj);
                parentSemanticsNode.semanticsConsumed = false;
                SemanticsModifierNodeKt.invalidateSemantics(parentSemanticsNode);
                return Boolean.FALSE;
            }
        });
    }
}
