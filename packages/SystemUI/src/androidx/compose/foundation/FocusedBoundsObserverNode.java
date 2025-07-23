package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.node.TraversableNodeKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FocusedBoundsObserverNode extends Modifier.Node implements TraversableNode {
    public static final TraverseKey TraverseKey = new TraverseKey(null);
    public final Function1 onPositioned;
    public final TraverseKey traverseKey = TraverseKey;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TraverseKey {
        public /* synthetic */ TraverseKey(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private TraverseKey() {
        }
    }

    public FocusedBoundsObserverNode(Function1 function1) {
        this.onPositioned = function1;
    }

    @Override // androidx.compose.ui.node.TraversableNode
    public final Object getTraverseKey() {
        return this.traverseKey;
    }

    public final void onFocusBoundsChanged(LayoutCoordinates layoutCoordinates) {
        this.onPositioned.mo779invoke(layoutCoordinates);
        FocusedBoundsObserverNode focusedBoundsObserverNode = (FocusedBoundsObserverNode) TraversableNodeKt.findNearestAncestor(this);
        if (focusedBoundsObserverNode != null) {
            focusedBoundsObserverNode.onFocusBoundsChanged(layoutCoordinates);
        }
    }
}
