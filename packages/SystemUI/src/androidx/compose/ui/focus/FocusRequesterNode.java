package androidx.compose.ui.focus;

import androidx.compose.ui.Modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FocusRequesterNode extends Modifier.Node implements FocusRequesterModifierNode {
    public FocusRequester focusRequester;

    public FocusRequesterNode(FocusRequester focusRequester) {
        this.focusRequester = focusRequester;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        this.focusRequester.focusRequesterNodes.add(this);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        this.focusRequester.focusRequesterNodes.remove(this);
    }
}
