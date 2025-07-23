package androidx.compose.material3.internal;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DraggableAnchorsElement<T> extends ModifierNodeElement<DraggableAnchorsNode<T>> {
    public final Function2 anchors;
    public final Orientation orientation;
    public final AnchoredDraggableState state;

    public DraggableAnchorsElement(AnchoredDraggableState<T> anchoredDraggableState, Function2 function2, Orientation orientation) {
        this.state = anchoredDraggableState;
        this.anchors = function2;
        this.orientation = orientation;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new DraggableAnchorsNode(this.state, this.anchors, this.orientation);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DraggableAnchorsElement)) {
            return false;
        }
        DraggableAnchorsElement draggableAnchorsElement = (DraggableAnchorsElement) obj;
        return Intrinsics.areEqual(this.state, draggableAnchorsElement.state) && this.anchors == draggableAnchorsElement.anchors && this.orientation == draggableAnchorsElement.orientation;
    }

    public final int hashCode() {
        return this.orientation.hashCode() + ((this.anchors.hashCode() + (this.state.hashCode() * 31)) * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        DraggableAnchorsNode draggableAnchorsNode = (DraggableAnchorsNode) node;
        draggableAnchorsNode.state = this.state;
        draggableAnchorsNode.anchors = this.anchors;
        draggableAnchorsNode.orientation = this.orientation;
    }
}
