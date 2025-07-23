package com.android.compose.gesture;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.node.ModifierNodeElement;
import com.android.compose.gesture.NestedDraggableNode;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NestedDraggableElement extends ModifierNodeElement<NestedDraggableRootNode> {
    public final NestedDraggable draggable;
    public final boolean enabled;
    public final boolean nestedDragsEnabled;
    public final Orientation orientation;
    public final OverscrollEffect overscrollEffect;

    public NestedDraggableElement(NestedDraggable nestedDraggable, Orientation orientation, OverscrollEffect overscrollEffect, boolean z, boolean z2) {
        this.draggable = nestedDraggable;
        this.orientation = orientation;
        this.overscrollEffect = overscrollEffect;
        this.enabled = z;
        this.nestedDragsEnabled = z2;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new NestedDraggableRootNode(this.draggable, this.orientation, this.overscrollEffect, this.enabled, this.nestedDragsEnabled);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NestedDraggableElement)) {
            return false;
        }
        NestedDraggableElement nestedDraggableElement = (NestedDraggableElement) obj;
        return Intrinsics.areEqual(this.draggable, nestedDraggableElement.draggable) && this.orientation == nestedDraggableElement.orientation && Intrinsics.areEqual(this.overscrollEffect, nestedDraggableElement.overscrollEffect) && this.enabled == nestedDraggableElement.enabled && this.nestedDragsEnabled == nestedDraggableElement.nestedDragsEnabled;
    }

    public final int hashCode() {
        int hashCode = (this.orientation.hashCode() + (this.draggable.hashCode() * 31)) * 31;
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        return Boolean.hashCode(this.nestedDragsEnabled) + TransitionData$$ExternalSyntheticOutline0.m((hashCode + (overscrollEffect == null ? 0 : overscrollEffect.hashCode())) * 31, 31, this.enabled);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NestedDraggableElement(draggable=");
        sb.append(this.draggable);
        sb.append(", orientation=");
        sb.append(this.orientation);
        sb.append(", overscrollEffect=");
        sb.append(this.overscrollEffect);
        sb.append(", enabled=");
        sb.append(this.enabled);
        sb.append(", nestedDragsEnabled=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.nestedDragsEnabled, ")");
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        NestedDraggableRootNode nestedDraggableRootNode = (NestedDraggableRootNode) node;
        if (!this.enabled) {
            NestedDraggableNode nestedDraggableNode = nestedDraggableRootNode.delegateNode;
            if (nestedDraggableNode != null) {
                nestedDraggableRootNode.undelegate(nestedDraggableNode);
            }
            nestedDraggableRootNode.delegateNode = null;
            return;
        }
        NestedDraggableNode nestedDraggableNode2 = nestedDraggableRootNode.delegateNode;
        NestedDraggable nestedDraggable = this.draggable;
        Orientation orientation = this.orientation;
        OverscrollEffect overscrollEffect = this.overscrollEffect;
        boolean z = this.nestedDragsEnabled;
        if (nestedDraggableNode2 == null) {
            NestedDraggableNode nestedDraggableNode3 = new NestedDraggableNode(nestedDraggable, orientation, overscrollEffect, z);
            nestedDraggableRootNode.delegate(nestedDraggableNode3);
            nestedDraggableRootNode.delegateNode = nestedDraggableNode3;
            return;
        }
        nestedDraggableNode2.draggable = nestedDraggable;
        nestedDraggableNode2.orientation = orientation;
        nestedDraggableNode2.overscrollEffect = overscrollEffect;
        nestedDraggableNode2.nestedDragsEnabled = z;
        ((SuspendingPointerInputModifierNodeImpl) nestedDraggableNode2.trackWheelScroll).resetPointerInputHandler();
        ((SuspendingPointerInputModifierNodeImpl) nestedDraggableNode2.trackDownPositionDelegate).resetPointerInputHandler();
        ((SuspendingPointerInputModifierNodeImpl) nestedDraggableNode2.detectDragsDelegate).resetPointerInputHandler();
        NestedDraggableNode.NestedScrollController nestedScrollController = nestedDraggableNode2.nestedScrollController;
        if (nestedScrollController != null) {
            nestedScrollController.ensureOnDragStoppedIsCalled();
        }
        nestedDraggableNode2.nestedScrollController = null;
    }
}
