package androidx.compose.foundation;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScrollingLayoutElement extends ModifierNodeElement<ScrollNode> {
    public final boolean isVertical;
    public final boolean reverseScrolling;
    public final ScrollState scrollState;

    public ScrollingLayoutElement(ScrollState scrollState, boolean z, boolean z2) {
        this.scrollState = scrollState;
        this.reverseScrolling = z;
        this.isVertical = z2;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new ScrollNode(this.scrollState, this.reverseScrolling, this.isVertical);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ScrollingLayoutElement)) {
            return false;
        }
        ScrollingLayoutElement scrollingLayoutElement = (ScrollingLayoutElement) obj;
        return Intrinsics.areEqual(this.scrollState, scrollingLayoutElement.scrollState) && this.reverseScrolling == scrollingLayoutElement.reverseScrolling && this.isVertical == scrollingLayoutElement.isVertical;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isVertical) + TransitionData$$ExternalSyntheticOutline0.m(this.scrollState.hashCode() * 31, 31, this.reverseScrolling);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ScrollNode scrollNode = (ScrollNode) node;
        scrollNode.state = this.scrollState;
        scrollNode.reverseScrolling = this.reverseScrolling;
        scrollNode.isVertical = this.isVertical;
    }
}
