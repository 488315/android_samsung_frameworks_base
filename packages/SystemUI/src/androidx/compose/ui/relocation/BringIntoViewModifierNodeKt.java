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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(delegatableNode);
        loop0: while (true) {
            obj = null;
            if (requireLayoutNode == null) {
                break;
            }
            if ((requireLayoutNode.nodes.head.aggregateChildKindSet & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0) {
                while (node2 != null) {
                    if ((node2.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0) {
                        Modifier.Node node3 = node2;
                        MutableVector mutableVector = null;
                        while (node3 != null) {
                            if (node3 instanceof BringIntoViewModifierNode) {
                                obj = node3;
                                break loop0;
                            }
                            if ((node3.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 && (node3 instanceof DelegatingNode)) {
                                int i = 0;
                                for (Modifier.Node node4 = ((DelegatingNode) node3).delegate; node4 != null; node4 = node4.child) {
                                    if ((node4.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0) {
                                        i++;
                                        if (i == 1) {
                                            node3 = node4;
                                        } else {
                                            if (mutableVector == null) {
                                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (node3 != null) {
                                                mutableVector.add(node3);
                                                node3 = null;
                                            }
                                            mutableVector.add(node4);
                                        }
                                    }
                                }
                                if (i == 1) {
                                }
                            }
                            node3 = DelegatableNodeKt.access$pop(mutableVector);
                        }
                    }
                    node2 = node2.parent;
                }
            }
            requireLayoutNode = requireLayoutNode.getParent$ui_release();
            node2 = (requireLayoutNode == null || (nodeChain = requireLayoutNode.nodes) == null) ? null : nodeChain.tail;
        }
        BringIntoViewModifierNode bringIntoViewModifierNode = (BringIntoViewModifierNode) obj;
        if (bringIntoViewModifierNode == null) {
            return Unit.INSTANCE;
        }
        final NodeCoordinator requireLayoutCoordinates = DelegatableNodeKt.requireLayoutCoordinates(delegatableNode);
        Object bringIntoView = bringIntoViewModifierNode.bringIntoView(requireLayoutCoordinates, new Function0() { // from class: androidx.compose.ui.relocation.BringIntoViewModifierNodeKt$bringIntoView$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Rect rect;
                Function0 function02 = Function0.this;
                if (function02 != null && (rect = (Rect) function02.invoke()) != null) {
                    return rect;
                }
                LayoutCoordinates layoutCoordinates = requireLayoutCoordinates;
                if (!layoutCoordinates.isAttached()) {
                    layoutCoordinates = null;
                }
                if (layoutCoordinates != null) {
                    return SizeKt.m421toRectuvyYCjk(IntSizeKt.m864toSizeozmzZPI(layoutCoordinates.mo610getSizeYbymL2g()));
                }
                return null;
            }
        }, continuationImpl);
        return bringIntoView == CoroutineSingletons.COROUTINE_SUSPENDED ? bringIntoView : Unit.INSTANCE;
    }
}
