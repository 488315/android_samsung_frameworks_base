package androidx.compose.ui.node;

import androidx.compose.ui.Modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class NodeCoordinatorKt {
    /* renamed from: access$nextUntil-hw7D004, reason: not valid java name */
    public static final Modifier.Node m680access$nextUntilhw7D004(DelegatableNode delegatableNode, int i) {
        Modifier.Node node = ((Modifier.Node) delegatableNode).node.child;
        if (node == null || (node.aggregateChildKindSet & i) == 0) {
            return null;
        }
        while (node != null) {
            int i2 = node.kindSet;
            if ((i2 & 2) != 0) {
                return null;
            }
            if ((i2 & i) != 0) {
                return node;
            }
            node = node.child;
        }
        return null;
    }
}
