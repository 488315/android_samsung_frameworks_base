package androidx.compose.foundation.draganddrop;

import androidx.compose.ui.draganddrop.DragAndDropNode;
import androidx.compose.ui.draganddrop.DragAndDropNodeKt;
import androidx.compose.ui.draganddrop.DragAndDropTarget;
import androidx.compose.ui.draganddrop.DragAndDropTargetModifierNode;
import androidx.compose.ui.node.DelegatingNode;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DragAndDropTargetNode extends DelegatingNode {
    public DragAndDropTargetModifierNode dragAndDropNode;
    public Function1 shouldStartDragAndDrop;
    public DragAndDropTarget target;

    public DragAndDropTargetNode(Function1 function1, DragAndDropTarget dragAndDropTarget) {
        this.shouldStartDragAndDrop = function1;
        this.target = dragAndDropTarget;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        DragAndDropNode DragAndDropTargetModifierNode = DragAndDropNodeKt.DragAndDropTargetModifierNode(new DragAndDropTargetNode$createAndAttachDragAndDropModifierNode$1(this), this.target);
        delegate(DragAndDropTargetModifierNode);
        this.dragAndDropNode = DragAndDropTargetModifierNode;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        DragAndDropTargetModifierNode dragAndDropTargetModifierNode = this.dragAndDropNode;
        dragAndDropTargetModifierNode.getClass();
        undelegate(dragAndDropTargetModifierNode);
    }
}
