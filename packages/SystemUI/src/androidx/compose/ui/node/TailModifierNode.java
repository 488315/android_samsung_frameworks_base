package androidx.compose.ui.node;

import androidx.compose.ui.Modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
