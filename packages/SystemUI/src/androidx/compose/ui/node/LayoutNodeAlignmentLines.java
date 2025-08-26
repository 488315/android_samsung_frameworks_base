package androidx.compose.ui.node;

import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.unit.IntOffsetKt;
import java.util.Map;

/* loaded from: classes.dex */
public final class LayoutNodeAlignmentLines extends AlignmentLines {
    public LayoutNodeAlignmentLines(AlignmentLinesOwner alignmentLinesOwner) {
        super(alignmentLinesOwner, null);
    }

    @Override // androidx.compose.ui.node.AlignmentLines
    /* renamed from: calculatePositionInParent-R5De75A */
    public final long mo633calculatePositionInParentR5De75A(NodeCoordinator nodeCoordinator, long j) {
        OwnedLayer ownedLayer = nodeCoordinator.layer;
        if (ownedLayer != null) {
            j = ownedLayer.mo687mapOffset8S9VItk(j, false);
        }
        return IntOffsetKt.m855plusNvtHpc(j, nodeCoordinator.position);
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
