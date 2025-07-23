package androidx.compose.foundation;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.BringIntoViewSpec;
import androidx.compose.foundation.gestures.FlingBehavior;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.ScrollableState;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ScrollingContainerElement extends ModifierNodeElement<ScrollingContainerNode> {
    public final BringIntoViewSpec bringIntoViewSpec;
    public final boolean enabled;
    public final FlingBehavior flingBehavior;
    public final MutableInteractionSource interactionSource;
    public final Orientation orientation;
    public final OverscrollEffect overscrollEffect;
    public final boolean reverseScrolling;
    public final ScrollableState state;
    public final boolean useLocalOverscrollFactory;

    public ScrollingContainerElement(ScrollableState scrollableState, Orientation orientation, boolean z, boolean z2, FlingBehavior flingBehavior, MutableInteractionSource mutableInteractionSource, BringIntoViewSpec bringIntoViewSpec, boolean z3, OverscrollEffect overscrollEffect) {
        this.state = scrollableState;
        this.orientation = orientation;
        this.enabled = z;
        this.reverseScrolling = z2;
        this.flingBehavior = flingBehavior;
        this.interactionSource = mutableInteractionSource;
        this.bringIntoViewSpec = bringIntoViewSpec;
        this.useLocalOverscrollFactory = z3;
        this.overscrollEffect = overscrollEffect;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new ScrollingContainerNode(this.state, this.orientation, this.enabled, this.reverseScrolling, this.flingBehavior, this.interactionSource, this.bringIntoViewSpec, this.useLocalOverscrollFactory, this.overscrollEffect);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ScrollingContainerElement.class != obj.getClass()) {
            return false;
        }
        ScrollingContainerElement scrollingContainerElement = (ScrollingContainerElement) obj;
        return Intrinsics.areEqual(this.state, scrollingContainerElement.state) && this.orientation == scrollingContainerElement.orientation && this.enabled == scrollingContainerElement.enabled && this.reverseScrolling == scrollingContainerElement.reverseScrolling && Intrinsics.areEqual(this.flingBehavior, scrollingContainerElement.flingBehavior) && Intrinsics.areEqual(this.interactionSource, scrollingContainerElement.interactionSource) && Intrinsics.areEqual(this.bringIntoViewSpec, scrollingContainerElement.bringIntoViewSpec) && this.useLocalOverscrollFactory == scrollingContainerElement.useLocalOverscrollFactory && Intrinsics.areEqual(this.overscrollEffect, scrollingContainerElement.overscrollEffect);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.orientation.hashCode() + (this.state.hashCode() * 31)) * 31, 31, this.enabled), 31, this.reverseScrolling);
        FlingBehavior flingBehavior = this.flingBehavior;
        int hashCode = (m + (flingBehavior != null ? flingBehavior.hashCode() : 0)) * 31;
        MutableInteractionSource mutableInteractionSource = this.interactionSource;
        int hashCode2 = (hashCode + (mutableInteractionSource != null ? mutableInteractionSource.hashCode() : 0)) * 31;
        BringIntoViewSpec bringIntoViewSpec = this.bringIntoViewSpec;
        int m2 = TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (bringIntoViewSpec != null ? bringIntoViewSpec.hashCode() : 0)) * 31, 31, this.useLocalOverscrollFactory);
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        return m2 + (overscrollEffect != null ? overscrollEffect.hashCode() : 0);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        MutableInteractionSource mutableInteractionSource = this.interactionSource;
        BringIntoViewSpec bringIntoViewSpec = this.bringIntoViewSpec;
        ScrollableState scrollableState = this.state;
        Orientation orientation = this.orientation;
        boolean z = this.useLocalOverscrollFactory;
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        ((ScrollingContainerNode) node).update(scrollableState, orientation, z, this.enabled, this.flingBehavior, mutableInteractionSource, bringIntoViewSpec, this.reverseScrolling, overscrollEffect);
    }
}
