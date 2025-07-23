package androidx.compose.foundation.gestures;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScrollableElement extends ModifierNodeElement<ScrollableNode> {
    public final BringIntoViewSpec bringIntoViewSpec;
    public final boolean enabled;
    public final FlingBehavior flingBehavior;
    public final MutableInteractionSource interactionSource;
    public final Orientation orientation;
    public final OverscrollEffect overscrollEffect;
    public final boolean reverseDirection;
    public final ScrollableState state;

    public ScrollableElement(ScrollableState scrollableState, Orientation orientation, OverscrollEffect overscrollEffect, boolean z, boolean z2, FlingBehavior flingBehavior, MutableInteractionSource mutableInteractionSource, BringIntoViewSpec bringIntoViewSpec) {
        this.state = scrollableState;
        this.orientation = orientation;
        this.overscrollEffect = overscrollEffect;
        this.enabled = z;
        this.reverseDirection = z2;
        this.flingBehavior = flingBehavior;
        this.interactionSource = mutableInteractionSource;
        this.bringIntoViewSpec = bringIntoViewSpec;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new ScrollableNode(this.state, this.overscrollEffect, this.flingBehavior, this.orientation, this.enabled, this.reverseDirection, this.interactionSource, this.bringIntoViewSpec);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ScrollableElement)) {
            return false;
        }
        ScrollableElement scrollableElement = (ScrollableElement) obj;
        return Intrinsics.areEqual(this.state, scrollableElement.state) && this.orientation == scrollableElement.orientation && Intrinsics.areEqual(this.overscrollEffect, scrollableElement.overscrollEffect) && this.enabled == scrollableElement.enabled && this.reverseDirection == scrollableElement.reverseDirection && Intrinsics.areEqual(this.flingBehavior, scrollableElement.flingBehavior) && Intrinsics.areEqual(this.interactionSource, scrollableElement.interactionSource) && Intrinsics.areEqual(this.bringIntoViewSpec, scrollableElement.bringIntoViewSpec);
    }

    public final int hashCode() {
        int hashCode = (this.orientation.hashCode() + (this.state.hashCode() * 31)) * 31;
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((hashCode + (overscrollEffect != null ? overscrollEffect.hashCode() : 0)) * 31, 31, this.enabled), 31, this.reverseDirection);
        FlingBehavior flingBehavior = this.flingBehavior;
        int hashCode2 = (m + (flingBehavior != null ? flingBehavior.hashCode() : 0)) * 31;
        MutableInteractionSource mutableInteractionSource = this.interactionSource;
        int hashCode3 = (hashCode2 + (mutableInteractionSource != null ? mutableInteractionSource.hashCode() : 0)) * 31;
        BringIntoViewSpec bringIntoViewSpec = this.bringIntoViewSpec;
        return hashCode3 + (bringIntoViewSpec != null ? bringIntoViewSpec.hashCode() : 0);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((ScrollableNode) node).update(this.state, this.orientation, this.overscrollEffect, this.enabled, this.reverseDirection, this.flingBehavior, this.interactionSource, this.bringIntoViewSpec);
    }
}
