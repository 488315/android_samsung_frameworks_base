package androidx.compose.ui.draganddrop;

import android.view.DragEvent;
import android.view.View;
import androidx.collection.ArraySet;
import androidx.collection.ArraySet.ElementIterator;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.TraversableNode$Companion$TraverseDescendantsAction;
import androidx.compose.ui.node.TraversableNodeKt;
import androidx.compose.ui.platform.AndroidComposeView;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$BooleanRef;

/* loaded from: classes.dex */
public final class AndroidDragAndDropManager implements View.OnDragListener {
    public final Function3 startDrag;
    public final DragAndDropNode rootDragAndDropNode = new DragAndDropNode(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    public final ArraySet interestedTargets = new ArraySet(0, 1, null);
    public final AndroidDragAndDropManager$modifier$1 modifier = new ModifierNodeElement<DragAndDropNode>() { // from class: androidx.compose.ui.draganddrop.AndroidDragAndDropManager$modifier$1
        @Override // androidx.compose.ui.node.ModifierNodeElement
        public final Modifier.Node create() {
            return this.this$0.rootDragAndDropNode;
        }

        public final boolean equals(Object obj) {
            return obj == this;
        }

        public final int hashCode() {
            return this.this$0.rootDragAndDropNode.hashCode();
        }

        @Override // androidx.compose.ui.node.ModifierNodeElement
        public final /* bridge */ /* synthetic */ void update(Modifier.Node node) {
        }
    };

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v3, types: [androidx.compose.ui.draganddrop.AndroidDragAndDropManager$modifier$1] */
    public AndroidDragAndDropManager(Function3 function3) {
        this.startDrag = function3;
    }

    @Override // android.view.View.OnDragListener
    public final boolean onDrag(View view, DragEvent dragEvent) {
        final DragAndDropEvent dragAndDropEvent = new DragAndDropEvent(dragEvent);
        switch (dragEvent.getAction()) {
            case 1:
                final DragAndDropNode dragAndDropNode = this.rootDragAndDropNode;
                dragAndDropNode.getClass();
                final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                Function1 function1 = new Function1() { // from class: androidx.compose.ui.draganddrop.DragAndDropNode$acceptDragAndDropTransfer$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        DragAndDropNode dragAndDropNode2 = (DragAndDropNode) obj;
                        if (!dragAndDropNode2.isAttached) {
                            return TraversableNode$Companion$TraverseDescendantsAction.SkipSubtreeAndContinueTraversal;
                        }
                        if (dragAndDropNode2.thisDragAndDropTarget != null) {
                            InlineClassHelperKt.throwIllegalStateException("DragAndDropTarget self reference must be null at the start of a drag and drop session");
                        }
                        Function1 function12 = dragAndDropNode2.onDropTargetValidate;
                        DragAndDropTarget dragAndDropTarget = function12 != null ? (DragAndDropTarget) function12.mo781invoke(dragAndDropEvent) : null;
                        dragAndDropNode2.thisDragAndDropTarget = dragAndDropTarget;
                        boolean z = dragAndDropTarget != null;
                        if (z) {
                            DragAndDropNode dragAndDropNode3 = dragAndDropNode;
                            dragAndDropNode3.getClass();
                            ((AndroidComposeView) DelegatableNodeKt.requireOwner(dragAndDropNode3)).dragAndDropManager.interestedTargets.add(dragAndDropNode2);
                        }
                        Ref$BooleanRef ref$BooleanRef2 = ref$BooleanRef;
                        ref$BooleanRef2.element = ref$BooleanRef2.element || z;
                        return TraversableNode$Companion$TraverseDescendantsAction.ContinueTraversal;
                    }
                };
                if (function1.mo781invoke(dragAndDropNode) == TraversableNode$Companion$TraverseDescendantsAction.ContinueTraversal) {
                    TraversableNodeKt.traverseDescendants(dragAndDropNode, function1);
                }
                boolean z = ref$BooleanRef.element;
                ArraySet arraySet = this.interestedTargets;
                arraySet.getClass();
                ArraySet.ElementIterator elementIterator = arraySet.new ElementIterator();
                while (elementIterator.hasNext()) {
                    ((DragAndDropTarget) elementIterator.next()).onStarted(dragAndDropEvent);
                }
                break;
            case 2:
                this.rootDragAndDropNode.onMoved(dragAndDropEvent);
                break;
            case 4:
                this.rootDragAndDropNode.onEnded(dragAndDropEvent);
                this.interestedTargets.clear();
                break;
            case 5:
                this.rootDragAndDropNode.onEntered(dragAndDropEvent);
                break;
            case 6:
                this.rootDragAndDropNode.onExited(dragAndDropEvent);
                break;
        }
        return false;
    }
}
