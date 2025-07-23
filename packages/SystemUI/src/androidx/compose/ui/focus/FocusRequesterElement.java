package androidx.compose.ui.focus;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FocusRequesterElement extends ModifierNodeElement<FocusRequesterNode> {
    public final FocusRequester focusRequester;

    public FocusRequesterElement(FocusRequester focusRequester) {
        this.focusRequester = focusRequester;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new FocusRequesterNode(this.focusRequester);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FocusRequesterElement) && Intrinsics.areEqual(this.focusRequester, ((FocusRequesterElement) obj).focusRequester);
    }

    public final int hashCode() {
        return this.focusRequester.hashCode();
    }

    public final String toString() {
        return "FocusRequesterElement(focusRequester=" + this.focusRequester + ')';
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        FocusRequesterNode focusRequesterNode = (FocusRequesterNode) node;
        focusRequesterNode.focusRequester.focusRequesterNodes.remove(focusRequesterNode);
        FocusRequester focusRequester = this.focusRequester;
        focusRequesterNode.focusRequester = focusRequester;
        focusRequester.focusRequesterNodes.add(focusRequesterNode);
    }
}
