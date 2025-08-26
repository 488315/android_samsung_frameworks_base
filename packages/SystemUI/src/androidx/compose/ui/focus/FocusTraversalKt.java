package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes.dex */
public abstract class FocusTraversalKt {

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[LayoutDirection.values().length];
            try {
                iArr[LayoutDirection.Ltr.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutDirection.Rtl.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[FocusStateImpl.values().length];
            try {
                iArr2[FocusStateImpl.Active.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[FocusStateImpl.ActiveParent.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[FocusStateImpl.Captured.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[FocusStateImpl.Inactive.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:71:0x0059, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final FocusTargetNode findActiveFocusNode(FocusTargetNode focusTargetNode) {
        if (!ComposeUiFlags.isTrackFocusEnabled) {
            int i = WhenMappings.$EnumSwitchMapping$1[focusTargetNode.getFocusState().ordinal()];
            if (i != 1) {
                if (i == 2) {
                    if (!focusTargetNode.node.isAttached) {
                        InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
                    }
                    MutableVector mutableVector = new MutableVector(new Modifier.Node[16], 0);
                    Modifier.Node node = focusTargetNode.node;
                    Modifier.Node node2 = node.child;
                    if (node2 == null) {
                        DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node);
                    } else {
                        mutableVector.add(node2);
                    }
                    while (true) {
                        int i2 = mutableVector.size;
                        if (i2 == 0) {
                            break;
                        }
                        Modifier.Node nodeAccess$pop = (Modifier.Node) mutableVector.removeAt(i2 - 1);
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
                                            FocusTargetNode focusTargetNodeFindActiveFocusNode = findActiveFocusNode((FocusTargetNode) nodeAccess$pop);
                                            if (focusTargetNodeFindActiveFocusNode != null) {
                                                return focusTargetNodeFindActiveFocusNode;
                                            }
                                        } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                            int i3 = 0;
                                            for (Modifier.Node node3 = ((DelegatingNode) nodeAccess$pop).delegate; node3 != null; node3 = node3.child) {
                                                if ((node3.kindSet & 1024) != 0) {
                                                    i3++;
                                                    if (i3 == 1) {
                                                        nodeAccess$pop = node3;
                                                    } else {
                                                        if (mutableVector2 == null) {
                                                            mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                                        }
                                                        if (nodeAccess$pop != null) {
                                                            mutableVector2.add(nodeAccess$pop);
                                                            nodeAccess$pop = null;
                                                        }
                                                        mutableVector2.add(node3);
                                                    }
                                                }
                                            }
                                            if (i3 == 1) {
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
                } else if (i != 3) {
                    if (i != 4) {
                        throw new NoWhenBranchMatchedException();
                    }
                }
            }
            return focusTargetNode;
        }
        FocusTargetNode focusTargetNode2 = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.activeFocusTargetNode;
        if (focusTargetNode2 != null && focusTargetNode2.isAttached) {
            return focusTargetNode2;
        }
        return null;
    }

    public static final Rect focusRect(FocusTargetNode focusTargetNode) {
        Rect rectLocalBoundingBoxOf;
        NodeCoordinator nodeCoordinator = focusTargetNode.coordinator;
        if (nodeCoordinator != null && (rectLocalBoundingBoxOf = LayoutCoordinatesKt.findRootCoordinates(nodeCoordinator).localBoundingBoxOf(nodeCoordinator, false)) != null) {
            return rectLocalBoundingBoxOf;
        }
        Rect.Companion.getClass();
        return Rect.Zero;
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x0028, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final FocusTargetNode getActiveChild(FocusTargetNode focusTargetNode) {
        FocusTargetNode focusTargetNode2;
        boolean z = focusTargetNode.node.isAttached;
        if (z) {
            if (!z) {
                InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
            }
            MutableVector mutableVector = new MutableVector(new Modifier.Node[16], 0);
            Modifier.Node node = focusTargetNode.node;
            Modifier.Node node2 = node.child;
            if (node2 == null) {
                DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node);
            } else {
                mutableVector.add(node2);
            }
            loop0: while (true) {
                int i = mutableVector.size;
                if (i == 0) {
                    break;
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
                                    focusTargetNode2 = (FocusTargetNode) nodeAccess$pop;
                                    if (focusTargetNode2.node.isAttached) {
                                        int i2 = WhenMappings.$EnumSwitchMapping$1[focusTargetNode2.getFocusState().ordinal()];
                                        if (i2 == 1 || i2 == 2 || i2 == 3) {
                                            break loop0;
                                        }
                                    }
                                } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                    int i3 = 0;
                                    for (Modifier.Node node3 = ((DelegatingNode) nodeAccess$pop).delegate; node3 != null; node3 = node3.child) {
                                        if ((node3.kindSet & 1024) != 0) {
                                            i3++;
                                            if (i3 == 1) {
                                                nodeAccess$pop = node3;
                                            } else {
                                                if (mutableVector2 == null) {
                                                    mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (nodeAccess$pop != null) {
                                                    mutableVector2.add(nodeAccess$pop);
                                                    nodeAccess$pop = null;
                                                }
                                                mutableVector2.add(node3);
                                            }
                                        }
                                    }
                                    if (i3 == 1) {
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
            return focusTargetNode2;
        }
        return null;
    }

    public static final boolean isEligibleForFocusSearch(FocusTargetNode focusTargetNode) {
        LayoutNode layoutNode;
        NodeCoordinator nodeCoordinator;
        LayoutNode layoutNode2;
        NodeCoordinator nodeCoordinator2 = focusTargetNode.coordinator;
        return (nodeCoordinator2 == null || (layoutNode = nodeCoordinator2.layoutNode) == null || !layoutNode.isPlaced() || (nodeCoordinator = focusTargetNode.coordinator) == null || (layoutNode2 = nodeCoordinator.layoutNode) == null || !layoutNode2.isAttached()) ? false : true;
    }
}
