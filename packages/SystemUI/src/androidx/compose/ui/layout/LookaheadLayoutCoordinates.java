package androidx.compose.ui.layout;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.LookaheadDelegate;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntSize;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LookaheadLayoutCoordinates implements LayoutCoordinates {
    public final LookaheadDelegate lookaheadDelegate;

    public LookaheadLayoutCoordinates(LookaheadDelegate lookaheadDelegate) {
        this.lookaheadDelegate = lookaheadDelegate;
    }

    /* renamed from: getLookaheadOffset-F1C5BW0, reason: not valid java name */
    public final long m622getLookaheadOffsetF1C5BW0() {
        LookaheadDelegate lookaheadDelegate = this.lookaheadDelegate;
        LookaheadDelegate rootLookaheadDelegate = LookaheadLayoutCoordinatesKt.getRootLookaheadDelegate(lookaheadDelegate);
        Offset.Companion companion = Offset.Companion;
        companion.getClass();
        long mo612localPositionOfS_NoaFU = mo612localPositionOfS_NoaFU(rootLookaheadDelegate.lookaheadLayoutCoordinates, 0L, true);
        NodeCoordinator nodeCoordinator = lookaheadDelegate.coordinator;
        companion.getClass();
        return Offset.m400minusMKHz9U(mo612localPositionOfS_NoaFU, nodeCoordinator.mo612localPositionOfS_NoaFU(rootLookaheadDelegate.coordinator, 0L, true));
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    public final LayoutCoordinates getParentLayoutCoordinates$1() {
        LookaheadDelegate lookaheadDelegate;
        if (!isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("LayoutCoordinate operations are only valid when isAttached is true");
        }
        NodeCoordinator nodeCoordinator = this.lookaheadDelegate.coordinator.layoutNode.nodes.outerCoordinator.wrappedBy;
        if (nodeCoordinator == null || (lookaheadDelegate = nodeCoordinator.getLookaheadDelegate()) == null) {
            return null;
        }
        return lookaheadDelegate.lookaheadLayoutCoordinates;
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: getSize-YbymL2g */
    public final long mo610getSizeYbymL2g() {
        LookaheadDelegate lookaheadDelegate = this.lookaheadDelegate;
        long j = (lookaheadDelegate.width << 32) | (lookaheadDelegate.height & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j;
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    public final boolean isAttached() {
        return this.lookaheadDelegate.coordinator.getTail().isAttached;
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    public final Rect localBoundingBoxOf(LayoutCoordinates layoutCoordinates, boolean z) {
        return this.lookaheadDelegate.coordinator.localBoundingBoxOf(layoutCoordinates, z);
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: localPositionOf-R5De75A */
    public final long mo611localPositionOfR5De75A(LayoutCoordinates layoutCoordinates, long j) {
        return mo612localPositionOfS_NoaFU(layoutCoordinates, j, true);
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: localPositionOf-S_NoaFU */
    public final long mo612localPositionOfS_NoaFU(LayoutCoordinates layoutCoordinates, long j, boolean z) {
        boolean z2 = layoutCoordinates instanceof LookaheadLayoutCoordinates;
        LookaheadDelegate lookaheadDelegate = this.lookaheadDelegate;
        if (!z2) {
            LookaheadDelegate rootLookaheadDelegate = LookaheadLayoutCoordinatesKt.getRootLookaheadDelegate(lookaheadDelegate);
            long mo612localPositionOfS_NoaFU = mo612localPositionOfS_NoaFU(rootLookaheadDelegate.lookaheadLayoutCoordinates, j, z);
            long j2 = rootLookaheadDelegate.position;
            IntOffset.Companion companion = IntOffset.Companion;
            float f = (int) (j2 & 4294967295L);
            long m400minusMKHz9U = Offset.m400minusMKHz9U(mo612localPositionOfS_NoaFU, (Float.floatToRawIntBits(f) & 4294967295L) | (Float.floatToRawIntBits((int) (j2 >> 32)) << 32));
            NodeCoordinator nodeCoordinator = rootLookaheadDelegate.coordinator;
            if (!nodeCoordinator.getTail().isAttached) {
                InlineClassHelperKt.throwIllegalStateException("LayoutCoordinate operations are only valid when isAttached is true");
            }
            nodeCoordinator.onCoordinatesUsed$ui_release();
            NodeCoordinator nodeCoordinator2 = nodeCoordinator.wrappedBy;
            if (nodeCoordinator2 != null) {
                nodeCoordinator = nodeCoordinator2;
            }
            Offset.Companion.getClass();
            return Offset.m401plusMKHz9U(m400minusMKHz9U, nodeCoordinator.mo612localPositionOfS_NoaFU(layoutCoordinates, 0L, z));
        }
        LookaheadDelegate lookaheadDelegate2 = ((LookaheadLayoutCoordinates) layoutCoordinates).lookaheadDelegate;
        lookaheadDelegate2.coordinator.onCoordinatesUsed$ui_release();
        LookaheadDelegate lookaheadDelegate3 = lookaheadDelegate.coordinator.findCommonAncestor$ui_release(lookaheadDelegate2.coordinator).getLookaheadDelegate();
        if (lookaheadDelegate3 != null) {
            boolean z3 = !z;
            long m850minusqkQi6aY = IntOffset.m850minusqkQi6aY(IntOffset.m851plusqkQi6aY(lookaheadDelegate2.m652positionIniSbpLlY$ui_release(lookaheadDelegate3, z3), IntOffsetKt.m854roundk4lQ0M(j)), lookaheadDelegate.m652positionIniSbpLlY$ui_release(lookaheadDelegate3, z3));
            long floatToRawIntBits = (Float.floatToRawIntBits((int) (m850minusqkQi6aY >> 32)) << 32) | (Float.floatToRawIntBits((int) (m850minusqkQi6aY & 4294967295L)) & 4294967295L);
            Offset.Companion companion2 = Offset.Companion;
            return floatToRawIntBits;
        }
        LookaheadDelegate rootLookaheadDelegate2 = LookaheadLayoutCoordinatesKt.getRootLookaheadDelegate(lookaheadDelegate2);
        boolean z4 = !z;
        long m851plusqkQi6aY = IntOffset.m851plusqkQi6aY(IntOffset.m851plusqkQi6aY(lookaheadDelegate2.m652positionIniSbpLlY$ui_release(rootLookaheadDelegate2, z4), rootLookaheadDelegate2.position), IntOffsetKt.m854roundk4lQ0M(j));
        LookaheadDelegate rootLookaheadDelegate3 = LookaheadLayoutCoordinatesKt.getRootLookaheadDelegate(lookaheadDelegate);
        long m850minusqkQi6aY2 = IntOffset.m850minusqkQi6aY(m851plusqkQi6aY, IntOffset.m851plusqkQi6aY(lookaheadDelegate.m652positionIniSbpLlY$ui_release(rootLookaheadDelegate3, z4), rootLookaheadDelegate3.position));
        long floatToRawIntBits2 = (Float.floatToRawIntBits((int) (m850minusqkQi6aY2 & 4294967295L)) & 4294967295L) | (Float.floatToRawIntBits((int) (m850minusqkQi6aY2 >> 32)) << 32);
        Offset.Companion companion3 = Offset.Companion;
        NodeCoordinator nodeCoordinator3 = rootLookaheadDelegate3.coordinator.wrappedBy;
        nodeCoordinator3.getClass();
        NodeCoordinator nodeCoordinator4 = rootLookaheadDelegate2.coordinator.wrappedBy;
        nodeCoordinator4.getClass();
        return nodeCoordinator3.mo612localPositionOfS_NoaFU(nodeCoordinator4, floatToRawIntBits2, z);
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: localToRoot-MK-Hz9U */
    public final long mo613localToRootMKHz9U(long j) {
        return this.lookaheadDelegate.coordinator.mo613localToRootMKHz9U(Offset.m401plusMKHz9U(j, m622getLookaheadOffsetF1C5BW0()));
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: localToScreen-MK-Hz9U */
    public final long mo614localToScreenMKHz9U(long j) {
        return this.lookaheadDelegate.coordinator.mo614localToScreenMKHz9U(Offset.m401plusMKHz9U(0L, m622getLookaheadOffsetF1C5BW0()));
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: localToWindow-MK-Hz9U */
    public final long mo615localToWindowMKHz9U(long j) {
        return this.lookaheadDelegate.coordinator.mo615localToWindowMKHz9U(Offset.m401plusMKHz9U(j, m622getLookaheadOffsetF1C5BW0()));
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: screenToLocal-MK-Hz9U */
    public final long mo616screenToLocalMKHz9U(long j) {
        return Offset.m401plusMKHz9U(this.lookaheadDelegate.coordinator.mo616screenToLocalMKHz9U(j), m622getLookaheadOffsetF1C5BW0());
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: transformFrom-EL8BTi8 */
    public final void mo617transformFromEL8BTi8(LayoutCoordinates layoutCoordinates, float[] fArr) {
        this.lookaheadDelegate.coordinator.mo617transformFromEL8BTi8(layoutCoordinates, fArr);
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: transformToScreen-58bKbWc */
    public final void mo618transformToScreen58bKbWc(float[] fArr) {
        this.lookaheadDelegate.coordinator.mo618transformToScreen58bKbWc(fArr);
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* renamed from: windowToLocal-MK-Hz9U */
    public final long mo619windowToLocalMKHz9U(long j) {
        return Offset.m401plusMKHz9U(this.lookaheadDelegate.coordinator.mo619windowToLocalMKHz9U(j), m622getLookaheadOffsetF1C5BW0());
    }
}
