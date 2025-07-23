package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.platform.AndroidComposeView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DelegatableNodeKt {
    public static final void access$addLayoutNodeChildren(MutableVector mutableVector, Modifier.Node node) {
        MutableVector mutableVector2 = requireLayoutNode(node).get_children$ui_release();
        int i = mutableVector2.size - 1;
        Object[] objArr = mutableVector2.content;
        if (i < objArr.length) {
            while (i >= 0) {
                mutableVector.add(((LayoutNode) objArr[i]).nodes.head);
                i--;
            }
        }
    }

    public static final Modifier.Node access$pop(MutableVector mutableVector) {
        int i;
        if (mutableVector == null || (i = mutableVector.size) == 0) {
            return null;
        }
        return (Modifier.Node) mutableVector.removeAt(i - 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final LayoutModifierNode asLayoutModifierNode(Modifier.Node node) {
        if ((node.kindSet & 2) != 0) {
            if (node instanceof LayoutModifierNode) {
                return (LayoutModifierNode) node;
            }
            if (node instanceof DelegatingNode) {
                Modifier.Node node2 = ((DelegatingNode) node).delegate;
                while (node2 != 0) {
                    if (node2 instanceof LayoutModifierNode) {
                        return (LayoutModifierNode) node2;
                    }
                    node2 = (!(node2 instanceof DelegatingNode) || (node2.kindSet & 2) == 0) ? node2.child : ((DelegatingNode) node2).delegate;
                }
            }
        }
        return null;
    }

    /* renamed from: requireCoordinator-64DMado, reason: not valid java name */
    public static final NodeCoordinator m632requireCoordinator64DMado(DelegatableNode delegatableNode, int i) {
        NodeCoordinator nodeCoordinator = ((Modifier.Node) delegatableNode).node.coordinator;
        nodeCoordinator.getClass();
        if (nodeCoordinator.getTail() != delegatableNode || !NodeKindKt.m681getIncludeSelfInTraversalH91voCI(i)) {
            return nodeCoordinator;
        }
        NodeCoordinator nodeCoordinator2 = nodeCoordinator.wrapped;
        nodeCoordinator2.getClass();
        return nodeCoordinator2;
    }

    public static final GraphicsContext requireGraphicsContext(DelegatableNode delegatableNode) {
        return ((AndroidComposeView) requireOwner(delegatableNode)).graphicsContext;
    }

    public static final NodeCoordinator requireLayoutCoordinates(DelegatableNode delegatableNode) {
        if (!((Modifier.Node) delegatableNode).node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("Cannot get LayoutCoordinates, Modifier.Node is not attached.");
        }
        NodeCoordinator m632requireCoordinator64DMado = m632requireCoordinator64DMado(delegatableNode, 2);
        if (!m632requireCoordinator64DMado.getTail().isAttached) {
            InlineClassHelperKt.throwIllegalStateException("LayoutCoordinates is not attached.");
        }
        return m632requireCoordinator64DMado;
    }

    public static final LayoutNode requireLayoutNode(DelegatableNode delegatableNode) {
        NodeCoordinator nodeCoordinator = ((Modifier.Node) delegatableNode).node.coordinator;
        if (nodeCoordinator != null) {
            return nodeCoordinator.layoutNode;
        }
        throw AndroidAutofill$$ExternalSyntheticOutline0.m("Cannot obtain node coordinator. Is the Modifier.Node attached?");
    }

    public static final Owner requireOwner(DelegatableNode delegatableNode) {
        AndroidComposeView androidComposeView = requireLayoutNode(delegatableNode).owner;
        if (androidComposeView != null) {
            return androidComposeView;
        }
        throw AndroidAutofill$$ExternalSyntheticOutline0.m("This node does not have an owner.");
    }
}
