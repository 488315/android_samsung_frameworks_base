package androidx.compose.ui.node;

import androidx.collection.MutableObjectIntMap;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DelegatingNode extends Modifier.Node {
    public Modifier.Node delegate;
    public final int selfKindSet = NodeKindKt.calculateNodeKindSetFrom(this);

    public final DelegatableNode delegate(DelegatableNode delegatableNode) {
        Modifier.Node node = ((Modifier.Node) delegatableNode).node;
        if (node != delegatableNode) {
            Modifier.Node node2 = delegatableNode instanceof Modifier.Node ? (Modifier.Node) delegatableNode : null;
            Modifier.Node node3 = node2 != null ? node2.parent : null;
            if (node != this.node || !Intrinsics.areEqual(node3, this)) {
                throw new IllegalStateException("Cannot delegate to an already delegated node");
            }
        } else {
            if (node.isAttached) {
                InlineClassHelperKt.throwIllegalStateException("Cannot delegate to an already attached node");
            }
            node.setAsDelegateTo$ui_release(this.node);
            int i = this.kindSet;
            int calculateNodeKindSetFromIncludingDelegates = NodeKindKt.calculateNodeKindSetFromIncludingDelegates(node);
            node.kindSet = calculateNodeKindSetFromIncludingDelegates;
            int i2 = this.kindSet;
            int i3 = calculateNodeKindSetFromIncludingDelegates & 2;
            if (i3 != 0 && (i2 & 2) != 0 && !(this instanceof LayoutModifierNode)) {
                InlineClassHelperKt.throwIllegalStateException("Delegating to multiple LayoutModifierNodes without the delegating node implementing LayoutModifierNode itself is not allowed.\nDelegating Node: " + this + "\nDelegate Node: " + node);
            }
            node.child = this.delegate;
            this.delegate = node;
            node.parent = this;
            updateNodeKindSet(calculateNodeKindSetFromIncludingDelegates | this.kindSet, false);
            if (this.isAttached) {
                if (i3 == 0 || (i & 2) != 0) {
                    updateCoordinator$ui_release(this.coordinator);
                } else {
                    NodeChain nodeChain = DelegatableNodeKt.requireLayoutNode(this).nodes;
                    this.node.updateCoordinator$ui_release(null);
                    nodeChain.syncCoordinators();
                }
                node.markAsAttached$ui_release();
                node.runAttachLifecycle$ui_release();
                if (!node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("autoInvalidateInsertedNode called on unattached node");
                }
                NodeKindKt.autoInvalidateNodeIncludingDelegates(node, -1, 1);
            }
        }
        return delegatableNode;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void markAsAttached$ui_release() {
        super.markAsAttached$ui_release();
        for (Modifier.Node node = this.delegate; node != null; node = node.child) {
            node.updateCoordinator$ui_release(this.coordinator);
            if (!node.isAttached) {
                node.markAsAttached$ui_release();
            }
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void markAsDetached$ui_release() {
        for (Modifier.Node node = this.delegate; node != null; node = node.child) {
            node.markAsDetached$ui_release();
        }
        super.markAsDetached$ui_release();
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void reset$ui_release() {
        super.reset$ui_release();
        for (Modifier.Node node = this.delegate; node != null; node = node.child) {
            node.reset$ui_release();
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void runAttachLifecycle$ui_release() {
        for (Modifier.Node node = this.delegate; node != null; node = node.child) {
            node.runAttachLifecycle$ui_release();
        }
        super.runAttachLifecycle$ui_release();
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void runDetachLifecycle$ui_release() {
        super.runDetachLifecycle$ui_release();
        for (Modifier.Node node = this.delegate; node != null; node = node.child) {
            node.runDetachLifecycle$ui_release();
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void setAsDelegateTo$ui_release(Modifier.Node node) {
        this.node = node;
        for (Modifier.Node node2 = this.delegate; node2 != null; node2 = node2.child) {
            node2.setAsDelegateTo$ui_release(node);
        }
    }

    public final void undelegate(DelegatableNode delegatableNode) {
        Modifier.Node node = null;
        for (Modifier.Node node2 = this.delegate; node2 != null; node2 = node2.child) {
            if (node2 == delegatableNode) {
                boolean z = node2.isAttached;
                if (z) {
                    MutableObjectIntMap mutableObjectIntMap = NodeKindKt.classToKindSetMap;
                    if (!z) {
                        InlineClassHelperKt.throwIllegalStateException("autoInvalidateRemovedNode called on unattached node");
                    }
                    NodeKindKt.autoInvalidateNodeIncludingDelegates(node2, -1, 2);
                    node2.runDetachLifecycle$ui_release();
                    node2.markAsDetached$ui_release();
                }
                node2.setAsDelegateTo$ui_release(node2);
                node2.aggregateChildKindSet = 0;
                if (node == null) {
                    this.delegate = node2.child;
                } else {
                    node.child = node2.child;
                }
                node2.child = null;
                node2.parent = null;
                int i = this.kindSet;
                int calculateNodeKindSetFromIncludingDelegates = NodeKindKt.calculateNodeKindSetFromIncludingDelegates(this);
                updateNodeKindSet(calculateNodeKindSetFromIncludingDelegates, true);
                if (this.isAttached && (i & 2) != 0 && (calculateNodeKindSetFromIncludingDelegates & 2) == 0) {
                    NodeChain nodeChain = DelegatableNodeKt.requireLayoutNode(this).nodes;
                    this.node.updateCoordinator$ui_release(null);
                    nodeChain.syncCoordinators();
                    return;
                }
                return;
            }
            node = node2;
        }
        throw new IllegalStateException(("Could not find delegate: " + delegatableNode).toString());
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void updateCoordinator$ui_release(NodeCoordinator nodeCoordinator) {
        this.coordinator = nodeCoordinator;
        for (Modifier.Node node = this.delegate; node != null; node = node.child) {
            node.updateCoordinator$ui_release(nodeCoordinator);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    public final void updateNodeKindSet(int i, boolean z) {
        Modifier.Node node;
        int i2 = this.kindSet;
        this.kindSet = i;
        if (i2 != i) {
            Modifier.Node node2 = this.node;
            if (node2 == this) {
                this.aggregateChildKindSet = i;
            }
            boolean z2 = this.isAttached;
            ?? r2 = this;
            if (z2) {
                while (r2 != 0) {
                    i |= r2.kindSet;
                    r2.kindSet = i;
                    if (r2 == node2) {
                        break;
                    } else {
                        r2 = r2.parent;
                    }
                }
                if (z && r2 == node2) {
                    i = NodeKindKt.calculateNodeKindSetFromIncludingDelegates(node2);
                    node2.kindSet = i;
                }
                int i3 = i | ((r2 == 0 || (node = r2.child) == null) ? 0 : node.aggregateChildKindSet);
                for (Modifier.Node node3 = r2; node3 != null; node3 = node3.parent) {
                    i3 |= node3.kindSet;
                    node3.aggregateChildKindSet = i3;
                }
            }
        }
    }
}
