package androidx.compose.ui.semantics;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.SemanticsModifierNode;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CoreSemanticsModifierNode extends Modifier.Node implements SemanticsModifierNode {
    public final boolean isClearingSemantics;
    public boolean mergeDescendants;
    public Function1 properties;

    public CoreSemanticsModifierNode(boolean z, boolean z2, Function1 function1) {
        this.mergeDescendants = z;
        this.isClearingSemantics = z2;
        this.properties = function1;
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        this.properties.mo779invoke(semanticsPropertyReceiver);
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final boolean getShouldClearDescendantSemantics() {
        return this.isClearingSemantics;
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final boolean getShouldMergeDescendantSemantics() {
        return this.mergeDescendants;
    }
}
