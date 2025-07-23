package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LazyLayoutBeyondBoundsModifierElement extends ModifierNodeElement<LazyLayoutBeyondBoundsModifierNode> {
    public final LazyLayoutBeyondBoundsInfo beyondBoundsInfo;
    public final Orientation orientation;
    public final boolean reverseLayout;
    public final LazyLayoutBeyondBoundsState state;

    public LazyLayoutBeyondBoundsModifierElement(LazyLayoutBeyondBoundsState lazyLayoutBeyondBoundsState, LazyLayoutBeyondBoundsInfo lazyLayoutBeyondBoundsInfo, boolean z, Orientation orientation) {
        this.state = lazyLayoutBeyondBoundsState;
        this.beyondBoundsInfo = lazyLayoutBeyondBoundsInfo;
        this.reverseLayout = z;
        this.orientation = orientation;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new LazyLayoutBeyondBoundsModifierNode(this.state, this.beyondBoundsInfo, this.reverseLayout, this.orientation);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LazyLayoutBeyondBoundsModifierElement)) {
            return false;
        }
        LazyLayoutBeyondBoundsModifierElement lazyLayoutBeyondBoundsModifierElement = (LazyLayoutBeyondBoundsModifierElement) obj;
        return Intrinsics.areEqual(this.state, lazyLayoutBeyondBoundsModifierElement.state) && Intrinsics.areEqual(this.beyondBoundsInfo, lazyLayoutBeyondBoundsModifierElement.beyondBoundsInfo) && this.reverseLayout == lazyLayoutBeyondBoundsModifierElement.reverseLayout && this.orientation == lazyLayoutBeyondBoundsModifierElement.orientation;
    }

    public final int hashCode() {
        return this.orientation.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((this.beyondBoundsInfo.hashCode() + (this.state.hashCode() * 31)) * 31, 31, this.reverseLayout);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        LazyLayoutBeyondBoundsModifierNode lazyLayoutBeyondBoundsModifierNode = (LazyLayoutBeyondBoundsModifierNode) node;
        lazyLayoutBeyondBoundsModifierNode.state = this.state;
        lazyLayoutBeyondBoundsModifierNode.beyondBoundsInfo = this.beyondBoundsInfo;
        lazyLayoutBeyondBoundsModifierNode.reverseLayout = this.reverseLayout;
        lazyLayoutBeyondBoundsModifierNode.orientation = this.orientation;
    }
}
