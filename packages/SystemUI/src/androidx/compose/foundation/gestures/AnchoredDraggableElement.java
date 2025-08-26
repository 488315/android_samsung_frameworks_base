package androidx.compose.foundation.gestures;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class AnchoredDraggableElement<T> extends ModifierNodeElement<AnchoredDraggableNode<T>> {
    public final boolean enabled;
    public final FlingBehavior flingBehavior;
    public final MutableInteractionSource interactionSource;
    public final Orientation orientation;
    public final OverscrollEffect overscrollEffect;
    public final Boolean reverseDirection;
    public final Boolean startDragImmediately;
    public final AnchoredDraggableState state;

    public /* synthetic */ AnchoredDraggableElement(AnchoredDraggableState anchoredDraggableState, Orientation orientation, boolean z, Boolean bool, MutableInteractionSource mutableInteractionSource, Boolean bool2, OverscrollEffect overscrollEffect, FlingBehavior flingBehavior, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(anchoredDraggableState, orientation, z, bool, mutableInteractionSource, (i & 32) != 0 ? null : bool2, overscrollEffect, (i & 128) != 0 ? null : flingBehavior);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new AnchoredDraggableNode(this.state, this.orientation, this.enabled, this.reverseDirection, this.interactionSource, this.overscrollEffect, this.startDragImmediately, this.flingBehavior);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AnchoredDraggableElement)) {
            return false;
        }
        AnchoredDraggableElement anchoredDraggableElement = (AnchoredDraggableElement) obj;
        return Intrinsics.areEqual(this.state, anchoredDraggableElement.state) && this.orientation == anchoredDraggableElement.orientation && this.enabled == anchoredDraggableElement.enabled && Intrinsics.areEqual(this.reverseDirection, anchoredDraggableElement.reverseDirection) && Intrinsics.areEqual(this.interactionSource, anchoredDraggableElement.interactionSource) && Intrinsics.areEqual(this.startDragImmediately, anchoredDraggableElement.startDragImmediately) && Intrinsics.areEqual(this.overscrollEffect, anchoredDraggableElement.overscrollEffect) && Intrinsics.areEqual(this.flingBehavior, anchoredDraggableElement.flingBehavior);
    }

    public final int hashCode() {
        int iM = TransitionData$$ExternalSyntheticOutline0.m((this.orientation.hashCode() + (this.state.hashCode() * 31)) * 31, 31, this.enabled);
        Boolean bool = this.reverseDirection;
        int iHashCode = (iM + (bool != null ? bool.hashCode() : 0)) * 31;
        MutableInteractionSource mutableInteractionSource = this.interactionSource;
        int iHashCode2 = (iHashCode + (mutableInteractionSource != null ? mutableInteractionSource.hashCode() : 0)) * 31;
        Boolean bool2 = this.startDragImmediately;
        int iHashCode3 = (iHashCode2 + (bool2 != null ? bool2.hashCode() : 0)) * 31;
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        int iHashCode4 = (iHashCode3 + (overscrollEffect != null ? overscrollEffect.hashCode() : 0)) * 31;
        FlingBehavior flingBehavior = this.flingBehavior;
        return iHashCode4 + (flingBehavior != null ? flingBehavior.hashCode() : 0);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        boolean z;
        boolean z2;
        AnchoredDraggableNode anchoredDraggableNode = (AnchoredDraggableNode) node;
        FlingBehavior flingBehavior = this.flingBehavior;
        anchoredDraggableNode.flingBehavior = flingBehavior;
        AnchoredDraggableState anchoredDraggableState = anchoredDraggableNode.state;
        AnchoredDraggableState anchoredDraggableState2 = this.state;
        if (Intrinsics.areEqual(anchoredDraggableState, anchoredDraggableState2)) {
            z = false;
        } else {
            anchoredDraggableNode.state = anchoredDraggableState2;
            anchoredDraggableNode.updateFlingBehavior(flingBehavior);
            z = true;
        }
        Orientation orientation = anchoredDraggableNode.orientation;
        Orientation orientation2 = this.orientation;
        if (orientation != orientation2) {
            anchoredDraggableNode.orientation = orientation2;
            z = true;
        }
        Boolean bool = anchoredDraggableNode.reverseDirection;
        Boolean bool2 = this.reverseDirection;
        if (Intrinsics.areEqual(bool, bool2)) {
            z2 = z;
        } else {
            anchoredDraggableNode.reverseDirection = bool2;
            z2 = true;
        }
        anchoredDraggableNode.startDragImmediately = this.startDragImmediately;
        anchoredDraggableNode.overscrollEffect = this.overscrollEffect;
        anchoredDraggableNode.update(anchoredDraggableNode.canDrag, this.enabled, this.interactionSource, orientation2, z2);
    }

    public AnchoredDraggableElement(AnchoredDraggableState<T> anchoredDraggableState, Orientation orientation, boolean z, Boolean bool, MutableInteractionSource mutableInteractionSource, Boolean bool2, OverscrollEffect overscrollEffect, FlingBehavior flingBehavior) {
        this.state = anchoredDraggableState;
        this.orientation = orientation;
        this.enabled = z;
        this.reverseDirection = bool;
        this.interactionSource = mutableInteractionSource;
        this.startDragImmediately = bool2;
        this.overscrollEffect = overscrollEffect;
        this.flingBehavior = flingBehavior;
    }
}
