package androidx.compose.ui.node;

import androidx.collection.MutableObjectIntMap;
import androidx.collection.ObjectIntMapKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.DrawModifier;
import androidx.compose.ui.focus.FocusEventModifierNode;
import androidx.compose.ui.focus.FocusInvalidationManager;
import androidx.compose.ui.focus.FocusPropertiesModifierNode;
import androidx.compose.ui.focus.FocusTargetNode;
import androidx.compose.ui.focus.FocusTargetNodeKt;
import androidx.compose.ui.input.key.KeyInputModifierNode;
import androidx.compose.ui.input.key.SoftKeyboardInterceptionModifierNode;
import androidx.compose.ui.input.pointer.PointerInputModifier;
import androidx.compose.ui.input.rotary.RotaryInputModifierNode;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.ApproachLayoutModifierNode;
import androidx.compose.ui.layout.LayoutModifier;
import androidx.compose.ui.layout.OnGloballyPositionedModifier;
import androidx.compose.ui.layout.ParentDataModifier;
import androidx.compose.ui.modifier.ModifierLocalConsumer;
import androidx.compose.ui.modifier.ModifierLocalModifierNode;
import androidx.compose.ui.modifier.ModifierLocalProvider;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.relocation.BringIntoViewModifierNode;
import androidx.compose.ui.semantics.SemanticsModifier;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* loaded from: classes.dex */
public abstract class NodeKindKt {
    public static final MutableObjectIntMap classToKindSetMap = ObjectIntMapKt.mutableObjectIntMapOf();

    public static final void autoInvalidateNodeIncludingDelegates(Modifier.Node node, int i, int i2) {
        if (!(node instanceof DelegatingNode)) {
            autoInvalidateNodeSelf(node, i & node.kindSet, i2);
            return;
        }
        DelegatingNode delegatingNode = (DelegatingNode) node;
        autoInvalidateNodeSelf(node, delegatingNode.selfKindSet & i, i2);
        int i3 = (~delegatingNode.selfKindSet) & i;
        for (Modifier.Node node2 = delegatingNode.delegate; node2 != null; node2 = node2.child) {
            autoInvalidateNodeIncludingDelegates(node2, i3, i2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void autoInvalidateNodeSelf(Modifier.Node node, int i, int i2) {
        if (i2 != 0 || node.getShouldAutoInvalidate()) {
            if ((i & 2) != 0 && (node instanceof LayoutModifierNode)) {
                LayoutModifierNodeKt.invalidateMeasurement((LayoutModifierNode) node);
                if (i2 == 2) {
                    NodeCoordinator nodeCoordinatorM634requireCoordinator64DMado = DelegatableNodeKt.m634requireCoordinator64DMado(node, 2);
                    nodeCoordinatorM634requireCoordinator64DMado.released = true;
                    ((NodeCoordinator$invalidateParentLayer$1) nodeCoordinatorM634requireCoordinator64DMado.invalidateParentLayer).invoke();
                    if (nodeCoordinatorM634requireCoordinator64DMado.layer != null) {
                        if (nodeCoordinatorM634requireCoordinator64DMado.explicitLayer != null) {
                            nodeCoordinatorM634requireCoordinator64DMado.explicitLayer = null;
                        }
                        nodeCoordinatorM634requireCoordinator64DMado.updateLayerBlock(null, false);
                        nodeCoordinatorM634requireCoordinator64DMado.layoutNode.requestRelayout$ui_release(false);
                    }
                }
            }
            if ((i & 128) != 0 && (node instanceof LayoutAwareModifierNode) && i2 != 2) {
                DelegatableNodeKt.requireLayoutNode(node).invalidateMeasurements$ui_release();
            }
            if ((i & 256) != 0 && (node instanceof GlobalPositionAwareModifierNode) && i2 != 2) {
                LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(node);
                if (!layoutNodeRequireLayoutNode.getLayoutPending$ui_release() && !layoutNodeRequireLayoutNode.getMeasurePending$ui_release() && !layoutNodeRequireLayoutNode.needsOnPositionedDispatch) {
                    AndroidComposeView androidComposeView = (AndroidComposeView) LayoutNodeKt.requireOwner(layoutNodeRequireLayoutNode);
                    androidComposeView.measureAndLayoutDelegate.onPositionedDispatcher.layoutNodes.add(layoutNodeRequireLayoutNode);
                    layoutNodeRequireLayoutNode.needsOnPositionedDispatch = true;
                    androidComposeView.scheduleMeasureAndLayout(null);
                }
            }
            if ((i & 4) != 0 && (node instanceof DrawModifierNode)) {
                DrawModifierNodeKt.invalidateDraw((DrawModifierNode) node);
            }
            if ((i & 8) != 0 && (node instanceof SemanticsModifierNode)) {
                DelegatableNodeKt.requireLayoutNode(node).isSemanticsInvalidated = true;
            }
            if ((i & 64) != 0 && (node instanceof ParentDataModifierNode)) {
                LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = DelegatableNodeKt.requireLayoutNode((ParentDataModifierNode) node).layoutDelegate;
                layoutNodeLayoutDelegate.measurePassDelegate.parentDataDirty = true;
                LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
                if (lookaheadPassDelegate != null) {
                    lookaheadPassDelegate.parentDataDirty = true;
                }
            }
            if ((i & 2048) != 0 && (node instanceof FocusPropertiesModifierNode)) {
                FocusPropertiesModifierNode focusPropertiesModifierNode = (FocusPropertiesModifierNode) node;
                CanFocusChecker canFocusChecker = CanFocusChecker.INSTANCE;
                canFocusChecker.getClass();
                CanFocusChecker.canFocusValue = null;
                focusPropertiesModifierNode.applyFocusProperties(canFocusChecker);
                if (CanFocusChecker.canFocusValue != null) {
                    if (ComposeUiFlags.isTrackFocusEnabled || i2 == 2) {
                        scheduleInvalidationOfAssociatedFocusTargets(focusPropertiesModifierNode);
                    } else {
                        FocusInvalidationManager focusInvalidationManager = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusPropertiesModifierNode)).focusOwner.focusInvalidationManager;
                        focusInvalidationManager.scheduleInvalidationLegacy(focusInvalidationManager.focusPropertiesNodesLegacy, focusPropertiesModifierNode);
                    }
                }
            }
            if ((i & 4096) == 0 || !(node instanceof FocusEventModifierNode)) {
                return;
            }
            FocusEventModifierNode focusEventModifierNode = (FocusEventModifierNode) node;
            FocusInvalidationManager focusInvalidationManager2 = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusEventModifierNode)).focusOwner.focusInvalidationManager;
            focusInvalidationManager2.getClass();
            if (ComposeUiFlags.isTrackFocusEnabled) {
                focusInvalidationManager2.scheduleInvalidation(focusInvalidationManager2.focusEventNodes, focusEventModifierNode);
            } else {
                focusInvalidationManager2.scheduleInvalidationLegacy(focusInvalidationManager2.focusEventNodesLegacy, focusEventModifierNode);
            }
        }
    }

    public static final void autoInvalidateUpdatedNode(Modifier.Node node) {
        if (!node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("autoInvalidateUpdatedNode called on unattached node");
        }
        autoInvalidateNodeIncludingDelegates(node, -1, 0);
    }

    public static final int calculateNodeKindSetFrom(Modifier.Element element) {
        int i = element instanceof LayoutModifier ? 3 : 1;
        if (element instanceof DrawModifier) {
            i |= 4;
        }
        if (element instanceof SemanticsModifier) {
            i |= 8;
        }
        if (element instanceof PointerInputModifier) {
            i |= 16;
        }
        if ((element instanceof ModifierLocalConsumer) || (element instanceof ModifierLocalProvider)) {
            i |= 32;
        }
        if (element instanceof OnGloballyPositionedModifier) {
            i |= 256;
        }
        if (element instanceof ParentDataModifier) {
            i |= 64;
        }
        return element instanceof BringIntoViewModifierNode ? 524288 | i : i;
    }

    public static final int calculateNodeKindSetFromIncludingDelegates(Modifier.Node node) {
        if (!(node instanceof DelegatingNode)) {
            return calculateNodeKindSetFrom(node);
        }
        DelegatingNode delegatingNode = (DelegatingNode) node;
        int iCalculateNodeKindSetFromIncludingDelegates = delegatingNode.selfKindSet;
        for (Modifier.Node node2 = delegatingNode.delegate; node2 != null; node2 = node2.child) {
            iCalculateNodeKindSetFromIncludingDelegates |= calculateNodeKindSetFromIncludingDelegates(node2);
        }
        return iCalculateNodeKindSetFromIncludingDelegates;
    }

    /* renamed from: getIncludeSelfInTraversal-H91voCI, reason: not valid java name */
    public static final boolean m683getIncludeSelfInTraversalH91voCI(int i) {
        return (i & 128) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void scheduleInvalidationOfAssociatedFocusTargets(FocusPropertiesModifierNode focusPropertiesModifierNode) {
        Modifier.Node node = (Modifier.Node) focusPropertiesModifierNode;
        if (!node.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
        }
        MutableVector mutableVector = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node2 = node.node;
        Modifier.Node node3 = node2.child;
        if (node3 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node2);
        } else {
            mutableVector.add(node3);
        }
        while (true) {
            int i = mutableVector.size;
            if (i == 0) {
                return;
            }
            Modifier.Node nodeAccess$pop = (Modifier.Node) mutableVector.removeAt(i - 1);
            if ((nodeAccess$pop.aggregateChildKindSet & 1024) == 0) {
                DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, nodeAccess$pop);
            } else {
                while (true) {
                    if (nodeAccess$pop == null) {
                        break;
                    }
                    if ((nodeAccess$pop.kindSet & 1024) != 0) {
                        MutableVector mutableVector2 = null;
                        while (nodeAccess$pop != null) {
                            if (nodeAccess$pop instanceof FocusTargetNode) {
                                FocusTargetNodeKt.invalidateFocusTarget((FocusTargetNode) nodeAccess$pop);
                            } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                int i2 = 0;
                                for (Modifier.Node node4 = ((DelegatingNode) nodeAccess$pop).delegate; node4 != null; node4 = node4.child) {
                                    if ((node4.kindSet & 1024) != 0) {
                                        i2++;
                                        if (i2 == 1) {
                                            nodeAccess$pop = node4;
                                        } else {
                                            if (mutableVector2 == null) {
                                                mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (nodeAccess$pop != null) {
                                                mutableVector2.add(nodeAccess$pop);
                                                nodeAccess$pop = null;
                                            }
                                            mutableVector2.add(node4);
                                        }
                                    }
                                }
                                if (i2 == 1) {
                                }
                            }
                            nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector2);
                        }
                    } else {
                        nodeAccess$pop = nodeAccess$pop.child;
                    }
                }
            }
        }
    }

    public static final int calculateNodeKindSetFrom(Modifier.Node node) {
        int i = node.kindSet;
        if (i != 0) {
            return i;
        }
        Class<?> cls = node.getClass();
        MutableObjectIntMap mutableObjectIntMap = classToKindSetMap;
        int iFindKeyIndex = mutableObjectIntMap.findKeyIndex(cls);
        if (iFindKeyIndex >= 0) {
            return mutableObjectIntMap.values[iFindKeyIndex];
        }
        int i2 = node instanceof LayoutModifierNode ? 3 : 1;
        if (node instanceof DrawModifierNode) {
            i2 |= 4;
        }
        if (node instanceof SemanticsModifierNode) {
            i2 |= 8;
        }
        if (node instanceof PointerInputModifierNode) {
            i2 |= 16;
        }
        if (node instanceof ModifierLocalModifierNode) {
            i2 |= 32;
        }
        if (node instanceof ParentDataModifierNode) {
            i2 |= 64;
        }
        if (node instanceof LayoutAwareModifierNode) {
            i2 |= 128;
        }
        if (node instanceof GlobalPositionAwareModifierNode) {
            i2 |= 256;
        }
        if (node instanceof ApproachLayoutModifierNode) {
            i2 |= 512;
        }
        if (node instanceof FocusTargetNode) {
            i2 |= 1024;
        }
        if (node instanceof FocusPropertiesModifierNode) {
            i2 |= 2048;
        }
        if (node instanceof FocusEventModifierNode) {
            i2 |= 4096;
        }
        if (node instanceof KeyInputModifierNode) {
            i2 |= 8192;
        }
        if (node instanceof RotaryInputModifierNode) {
            i2 |= NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        }
        if (node instanceof CompositionLocalConsumerModifierNode) {
            i2 |= NetworkAnalyticsConstants.DataPoints.FLAG_UID;
        }
        if (node instanceof SoftKeyboardInterceptionModifierNode) {
            i2 |= 131072;
        }
        if (node instanceof TraversableNode) {
            i2 |= 262144;
        }
        if (node instanceof BringIntoViewModifierNode) {
            i2 |= NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        mutableObjectIntMap.set(i2, cls);
        return i2;
    }
}
