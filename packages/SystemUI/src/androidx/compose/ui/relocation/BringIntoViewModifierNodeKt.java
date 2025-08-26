package androidx.compose.ui.relocation;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeChain;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.unit.IntSizeKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class BringIntoViewModifierNodeKt {
    public static final Object bringIntoView(DelegatableNode delegatableNode, final Function0 function0, ContinuationImpl continuationImpl) {
        Object obj;
        NodeChain nodeChain;
        Modifier.Node node = (Modifier.Node) delegatableNode;
        boolean z = node.node.isAttached;
        if (!z) {
            return Unit.INSTANCE;
        }
        if (!z) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node2 = node.node.parent;
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(delegatableNode);
        loop0: while (true) {
            obj = null;
            if (layoutNodeRequireLayoutNode == null) {
                break;
            }
            if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0) {
                while (node2 != null) {
                    if ((node2.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0) {
                        Modifier.Node nodeAccess$pop = node2;
                        MutableVector mutableVector = null;
                        while (nodeAccess$pop != null) {
                            if (nodeAccess$pop instanceof BringIntoViewModifierNode) {
                                obj = nodeAccess$pop;
                                break loop0;
                            }
                            if ((nodeAccess$pop.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                int i = 0;
                                for (Modifier.Node node3 = ((DelegatingNode) nodeAccess$pop).delegate; node3 != null; node3 = node3.child) {
                                    if ((node3.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0) {
                                        i++;
                                        if (i == 1) {
                                            nodeAccess$pop = node3;
                                        } else {
                                            if (mutableVector == null) {
                                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (nodeAccess$pop != null) {
                                                mutableVector.add(nodeAccess$pop);
                                                nodeAccess$pop = null;
                                            }
                                            mutableVector.add(node3);
                                        }
                                    }
                                }
                                if (i == 1) {
                                }
                            }
                            nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                        }
                    }
                    node2 = node2.parent;
                }
            }
            layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
            node2 = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
        }
        BringIntoViewModifierNode bringIntoViewModifierNode = (BringIntoViewModifierNode) obj;
        if (bringIntoViewModifierNode == null) {
            return Unit.INSTANCE;
        }
        final NodeCoordinator nodeCoordinatorRequireLayoutCoordinates = DelegatableNodeKt.requireLayoutCoordinates(delegatableNode);
        Object objBringIntoView = bringIntoViewModifierNode.bringIntoView(nodeCoordinatorRequireLayoutCoordinates, new Function0() { // from class: androidx.compose.ui.relocation.BringIntoViewModifierNodeKt.bringIntoView.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Rect rect;
                Function0 function02 = function0;
                if (function02 != null && (rect = (Rect) function02.invoke()) != null) {
                    return rect;
                }
                LayoutCoordinates layoutCoordinates = nodeCoordinatorRequireLayoutCoordinates;
                if (!layoutCoordinates.isAttached()) {
                    layoutCoordinates = null;
                }
                if (layoutCoordinates != null) {
                    return SizeKt.m423toRectuvyYCjk(IntSizeKt.m866toSizeozmzZPI(layoutCoordinates.mo612getSizeYbymL2g()));
                }
                return null;
            }
        }, continuationImpl);
        return objBringIntoView == CoroutineSingletons.COROUTINE_SUSPENDED ? objBringIntoView : Unit.INSTANCE;
    }
}
