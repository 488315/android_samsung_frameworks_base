package androidx.compose.ui.node;

import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.unit.IntOffsetKt;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LayoutNodeAlignmentLines extends AlignmentLines {
    public LayoutNodeAlignmentLines(AlignmentLinesOwner alignmentLinesOwner) {
        super(alignmentLinesOwner, null);
    }

    @Override // androidx.compose.ui.node.AlignmentLines
    /* renamed from: calculatePositionInParent-R5De75A */
    public final long mo631calculatePositionInParentR5De75A(NodeCoordinator nodeCoordinator, long j) {
        OwnedLayer ownedLayer = nodeCoordinator.layer;
        if (ownedLayer != null) {
            j = ownedLayer.mo685mapOffset8S9VItk(j, false);
        }
        return IntOffsetKt.m853plusNvtHpc(j, nodeCoordinator.position);
    }

    @Override // androidx.compose.ui.node.AlignmentLines
    public final Map getAlignmentLinesMap(NodeCoordinator nodeCoordinator) {
        return nodeCoordinator.getMeasureResult$ui_release().getAlignmentLines();
    }

    @Override // androidx.compose.ui.node.AlignmentLines
    public final int getPositionFor(NodeCoordinator nodeCoordinator, AlignmentLine alignmentLine) {
        return nodeCoordinator.get(alignmentLine);
    }
}
