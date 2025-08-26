package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.LayoutNode;
import java.util.Comparator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class OnPositionedDispatcher {
    public LayoutNode[] cachedNodes;
    public final MutableVector layoutNodes = new MutableVector(new LayoutNode[16], 0);

    public final class Companion {

        final class DepthComparator implements Comparator<LayoutNode> {
            public static final DepthComparator INSTANCE = new DepthComparator();

            private DepthComparator() {
            }

            @Override // java.util.Comparator
            public final int compare(LayoutNode layoutNode, LayoutNode layoutNode2) {
                LayoutNode layoutNode3 = layoutNode;
                LayoutNode layoutNode4 = layoutNode2;
                int iCompare = Intrinsics.compare(layoutNode4.depth, layoutNode3.depth);
                return iCompare != 0 ? iCompare : Intrinsics.compare(layoutNode3.hashCode(), layoutNode4.hashCode());
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    public static void dispatchHierarchy(LayoutNode layoutNode) {
        if (layoutNode.layoutDelegate.layoutState == LayoutNode.LayoutState.Idle && !layoutNode.getLayoutPending$ui_release() && !layoutNode.getMeasurePending$ui_release() && !layoutNode.isDeactivated && layoutNode.isPlaced()) {
            Modifier.Node node = layoutNode.nodes.head;
            if ((node.aggregateChildKindSet & 256) != 0) {
                while (node != null) {
                    if ((node.kindSet & 256) != 0) {
                        DelegatingNode delegatingNodeAccess$pop = node;
                        ?? mutableVector = 0;
                        while (delegatingNodeAccess$pop != 0) {
                            if (delegatingNodeAccess$pop instanceof GlobalPositionAwareModifierNode) {
                                GlobalPositionAwareModifierNode globalPositionAwareModifierNode = (GlobalPositionAwareModifierNode) delegatingNodeAccess$pop;
                                globalPositionAwareModifierNode.onGloballyPositioned(DelegatableNodeKt.m634requireCoordinator64DMado(globalPositionAwareModifierNode, 256));
                            } else if ((delegatingNodeAccess$pop.kindSet & 256) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                Modifier.Node node2 = delegatingNodeAccess$pop.delegate;
                                int i = 0;
                                delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                mutableVector = mutableVector;
                                while (node2 != null) {
                                    if ((node2.kindSet & 256) != 0) {
                                        i++;
                                        mutableVector = mutableVector;
                                        if (i == 1) {
                                            delegatingNodeAccess$pop = node2;
                                        } else {
                                            if (mutableVector == 0) {
                                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (delegatingNodeAccess$pop != 0) {
                                                mutableVector.add(delegatingNodeAccess$pop);
                                                delegatingNodeAccess$pop = 0;
                                            }
                                            mutableVector.add(node2);
                                        }
                                    }
                                    node2 = node2.child;
                                    delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                    mutableVector = mutableVector;
                                }
                                if (i == 1) {
                                }
                            }
                            delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                        }
                    }
                    if ((node.aggregateChildKindSet & 256) == 0) {
                        break;
                    } else {
                        node = node.child;
                    }
                }
            }
        }
        layoutNode.needsOnPositionedDispatch = false;
        MutableVector mutableVector2 = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector2.content;
        int i2 = mutableVector2.size;
        for (int i3 = 0; i3 < i2; i3++) {
            dispatchHierarchy((LayoutNode) objArr[i3]);
        }
    }
}
