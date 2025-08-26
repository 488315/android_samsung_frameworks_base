package androidx.compose.ui.node;

import androidx.compose.ui.Modifier;

/* loaded from: classes.dex */
public final class TailModifierNode extends Modifier.Node {
    public boolean attachHasBeenRun;

    public TailModifierNode() {
        this.aggregateChildKindSet = 0;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        this.attachHasBeenRun = true;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        this.attachHasBeenRun = false;
    }

    public final String toString() {
        return "<tail>";
    }
}
