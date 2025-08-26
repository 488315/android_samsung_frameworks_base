package androidx.compose.ui.node;

import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.platform.AndroidComposeView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class LayoutNodeLayoutDelegate {
    public int childrenAccessingCoordinatesDuringPlacement;
    public int childrenAccessingLookaheadCoordinatesDuringPlacement;
    public boolean coordinatesAccessedDuringModifierPlacement;
    public boolean coordinatesAccessedDuringPlacement;
    public boolean detachedFromParentLookaheadPass;
    public boolean detachedFromParentLookaheadPlacement;
    public final LayoutNode layoutNode;
    public boolean lookaheadCoordinatesAccessedDuringModifierPlacement;
    public boolean lookaheadCoordinatesAccessedDuringPlacement;
    public boolean lookaheadLayoutPending;
    public boolean lookaheadLayoutPendingForAlignment;
    public boolean lookaheadMeasurePending;
    public LookaheadPassDelegate lookaheadPassDelegate;
    public int nextChildLookaheadPlaceOrder;
    public int nextChildPlaceOrder;
    public LayoutNode.LayoutState layoutState = LayoutNode.LayoutState.Idle;
    public final MeasurePassDelegate measurePassDelegate = new MeasurePassDelegate(this);

    public LayoutNodeLayoutDelegate(LayoutNode layoutNode) {
        this.layoutNode = layoutNode;
    }

    public final NodeCoordinator getOuterCoordinator() {
        return this.layoutNode.nodes.outerCoordinator;
    }

    public final void onCoordinatesUsed() {
        LayoutNode.LayoutState layoutState = this.layoutNode.layoutDelegate.layoutState;
        if (layoutState == LayoutNode.LayoutState.LayingOut || layoutState == LayoutNode.LayoutState.LookaheadLayingOut) {
            if (this.measurePassDelegate.layingOutChildren) {
                setCoordinatesAccessedDuringPlacement(true);
            } else {
                setCoordinatesAccessedDuringModifierPlacement(true);
            }
        }
        if (layoutState == LayoutNode.LayoutState.LookaheadLayingOut) {
            LookaheadPassDelegate lookaheadPassDelegate = this.lookaheadPassDelegate;
            if (lookaheadPassDelegate == null || !lookaheadPassDelegate.layingOutChildren) {
                setLookaheadCoordinatesAccessedDuringModifierPlacement(true);
            } else {
                setLookaheadCoordinatesAccessedDuringPlacement(true);
            }
        }
    }

    /* renamed from: performLookaheadMeasure-BRTryo0$ui_release, reason: not valid java name */
    public final void m650performLookaheadMeasureBRTryo0$ui_release(final long j) {
        final LookaheadPassDelegate lookaheadPassDelegate = this.lookaheadPassDelegate;
        if (lookaheadPassDelegate != null) {
            LayoutNode.LayoutState layoutState = LayoutNode.LayoutState.LookaheadMeasuring;
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = lookaheadPassDelegate.layoutNodeLayoutDelegate;
            layoutNodeLayoutDelegate.layoutState = layoutState;
            layoutNodeLayoutDelegate.lookaheadMeasurePending = false;
            LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
            OwnerSnapshotObserver ownerSnapshotObserver = ((AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode)).snapshotObserver;
            Function0 function0 = new Function0() { // from class: androidx.compose.ui.node.LookaheadPassDelegate$performMeasure$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    LookaheadDelegate lookaheadDelegate = lookaheadPassDelegate.layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate();
                    lookaheadDelegate.getClass();
                    lookaheadDelegate.mo610measureBRTryo0(j);
                    return Unit.INSTANCE;
                }
            };
            ownerSnapshotObserver.getClass();
            if (layoutNode.lookaheadRoot != null) {
                ownerSnapshotObserver.observeReads$ui_release(layoutNode, ownerSnapshotObserver.onCommitAffectingLookaheadMeasure, function0);
            } else {
                ownerSnapshotObserver.observeReads$ui_release(layoutNode, ownerSnapshotObserver.onCommitAffectingMeasure, function0);
            }
            layoutNodeLayoutDelegate.lookaheadLayoutPending = true;
            layoutNodeLayoutDelegate.lookaheadLayoutPendingForAlignment = true;
            boolean zIsOutMostLookaheadRoot = LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(layoutNode);
            MeasurePassDelegate measurePassDelegate = layoutNodeLayoutDelegate.measurePassDelegate;
            if (zIsOutMostLookaheadRoot) {
                measurePassDelegate.layoutPending = true;
                measurePassDelegate.layoutPendingForAlignment = true;
            } else {
                measurePassDelegate.measurePending = true;
            }
            layoutNodeLayoutDelegate.layoutState = LayoutNode.LayoutState.Idle;
        }
    }

    public final void setChildrenAccessingCoordinatesDuringPlacement(int i) {
        int i2 = this.childrenAccessingCoordinatesDuringPlacement;
        this.childrenAccessingCoordinatesDuringPlacement = i;
        if ((i2 == 0) != (i == 0)) {
            LayoutNode parent$ui_release = this.layoutNode.getParent$ui_release();
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = parent$ui_release != null ? parent$ui_release.layoutDelegate : null;
            if (layoutNodeLayoutDelegate != null) {
                if (i == 0) {
                    layoutNodeLayoutDelegate.setChildrenAccessingCoordinatesDuringPlacement(layoutNodeLayoutDelegate.childrenAccessingCoordinatesDuringPlacement - 1);
                } else {
                    layoutNodeLayoutDelegate.setChildrenAccessingCoordinatesDuringPlacement(layoutNodeLayoutDelegate.childrenAccessingCoordinatesDuringPlacement + 1);
                }
            }
        }
    }

    public final void setChildrenAccessingLookaheadCoordinatesDuringPlacement(int i) {
        int i2 = this.childrenAccessingLookaheadCoordinatesDuringPlacement;
        this.childrenAccessingLookaheadCoordinatesDuringPlacement = i;
        if ((i2 == 0) != (i == 0)) {
            LayoutNode parent$ui_release = this.layoutNode.getParent$ui_release();
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = parent$ui_release != null ? parent$ui_release.layoutDelegate : null;
            if (layoutNodeLayoutDelegate != null) {
                if (i == 0) {
                    layoutNodeLayoutDelegate.setChildrenAccessingLookaheadCoordinatesDuringPlacement(layoutNodeLayoutDelegate.childrenAccessingLookaheadCoordinatesDuringPlacement - 1);
                } else {
                    layoutNodeLayoutDelegate.setChildrenAccessingLookaheadCoordinatesDuringPlacement(layoutNodeLayoutDelegate.childrenAccessingLookaheadCoordinatesDuringPlacement + 1);
                }
            }
        }
    }

    public final void setCoordinatesAccessedDuringModifierPlacement(boolean z) {
        if (this.coordinatesAccessedDuringModifierPlacement != z) {
            this.coordinatesAccessedDuringModifierPlacement = z;
            if (z && !this.coordinatesAccessedDuringPlacement) {
                setChildrenAccessingCoordinatesDuringPlacement(this.childrenAccessingCoordinatesDuringPlacement + 1);
            } else {
                if (z || this.coordinatesAccessedDuringPlacement) {
                    return;
                }
                setChildrenAccessingCoordinatesDuringPlacement(this.childrenAccessingCoordinatesDuringPlacement - 1);
            }
        }
    }

    public final void setCoordinatesAccessedDuringPlacement(boolean z) {
        if (this.coordinatesAccessedDuringPlacement != z) {
            this.coordinatesAccessedDuringPlacement = z;
            if (z && !this.coordinatesAccessedDuringModifierPlacement) {
                setChildrenAccessingCoordinatesDuringPlacement(this.childrenAccessingCoordinatesDuringPlacement + 1);
            } else {
                if (z || this.coordinatesAccessedDuringModifierPlacement) {
                    return;
                }
                setChildrenAccessingCoordinatesDuringPlacement(this.childrenAccessingCoordinatesDuringPlacement - 1);
            }
        }
    }

    public final void setLookaheadCoordinatesAccessedDuringModifierPlacement(boolean z) {
        if (this.lookaheadCoordinatesAccessedDuringModifierPlacement != z) {
            this.lookaheadCoordinatesAccessedDuringModifierPlacement = z;
            if (z && !this.lookaheadCoordinatesAccessedDuringPlacement) {
                setChildrenAccessingLookaheadCoordinatesDuringPlacement(this.childrenAccessingLookaheadCoordinatesDuringPlacement + 1);
            } else {
                if (z || this.lookaheadCoordinatesAccessedDuringPlacement) {
                    return;
                }
                setChildrenAccessingLookaheadCoordinatesDuringPlacement(this.childrenAccessingLookaheadCoordinatesDuringPlacement - 1);
            }
        }
    }

    public final void setLookaheadCoordinatesAccessedDuringPlacement(boolean z) {
        if (this.lookaheadCoordinatesAccessedDuringPlacement != z) {
            this.lookaheadCoordinatesAccessedDuringPlacement = z;
            if (z && !this.lookaheadCoordinatesAccessedDuringModifierPlacement) {
                setChildrenAccessingLookaheadCoordinatesDuringPlacement(this.childrenAccessingLookaheadCoordinatesDuringPlacement + 1);
            } else {
                if (z || this.lookaheadCoordinatesAccessedDuringModifierPlacement) {
                    return;
                }
                setChildrenAccessingLookaheadCoordinatesDuringPlacement(this.childrenAccessingLookaheadCoordinatesDuringPlacement - 1);
            }
        }
    }

    public final void updateParentData() {
        MeasurePassDelegate measurePassDelegate = this.measurePassDelegate;
        Object obj = measurePassDelegate.parentData;
        LayoutNode layoutNode = this.layoutNode;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = measurePassDelegate.layoutNodeLayoutDelegate;
        if ((obj != null || layoutNodeLayoutDelegate.getOuterCoordinator().getParentData() != null) && measurePassDelegate.parentDataDirty) {
            measurePassDelegate.parentDataDirty = false;
            measurePassDelegate.parentData = layoutNodeLayoutDelegate.getOuterCoordinator().getParentData();
            LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
            if (parent$ui_release != null) {
                LayoutNode.requestRemeasure$ui_release$default(parent$ui_release, false, 7);
            }
        }
        LookaheadPassDelegate lookaheadPassDelegate = this.lookaheadPassDelegate;
        if (lookaheadPassDelegate != null) {
            Object obj2 = lookaheadPassDelegate.parentData;
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = lookaheadPassDelegate.layoutNodeLayoutDelegate;
            if (obj2 == null) {
                LookaheadDelegate lookaheadDelegate = layoutNodeLayoutDelegate2.getOuterCoordinator().getLookaheadDelegate();
                lookaheadDelegate.getClass();
                if (lookaheadDelegate.coordinator.getParentData() == null) {
                    return;
                }
            }
            if (lookaheadPassDelegate.parentDataDirty) {
                lookaheadPassDelegate.parentDataDirty = false;
                LookaheadDelegate lookaheadDelegate2 = layoutNodeLayoutDelegate2.getOuterCoordinator().getLookaheadDelegate();
                lookaheadDelegate2.getClass();
                lookaheadPassDelegate.parentData = lookaheadDelegate2.coordinator.getParentData();
                if (LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(layoutNode)) {
                    LayoutNode parent$ui_release2 = layoutNode.getParent$ui_release();
                    if (parent$ui_release2 != null) {
                        LayoutNode.requestRemeasure$ui_release$default(parent$ui_release2, false, 7);
                        return;
                    }
                    return;
                }
                LayoutNode parent$ui_release3 = layoutNode.getParent$ui_release();
                if (parent$ui_release3 != null) {
                    LayoutNode.requestLookaheadRemeasure$ui_release$default(parent$ui_release3, false, 7);
                }
            }
        }
    }
}
