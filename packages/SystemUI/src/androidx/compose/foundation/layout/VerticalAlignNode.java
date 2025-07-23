package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.CrossAxisAlignment;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ParentDataModifierNode;
import androidx.compose.ui.unit.Density;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VerticalAlignNode extends Modifier.Node implements ParentDataModifierNode {
    public Alignment.Vertical vertical;

    public VerticalAlignNode(Alignment.Vertical vertical) {
        this.vertical = vertical;
    }

    @Override // androidx.compose.ui.node.ParentDataModifierNode
    public final Object modifyParentData(Density density, Object obj) {
        RowColumnParentData rowColumnParentData = obj instanceof RowColumnParentData ? (RowColumnParentData) obj : null;
        if (rowColumnParentData == null) {
            rowColumnParentData = new RowColumnParentData(0.0f, false, null, null, 15, null);
        }
        CrossAxisAlignment.Companion companion = CrossAxisAlignment.Companion;
        Alignment.Vertical vertical = this.vertical;
        companion.getClass();
        rowColumnParentData.crossAxisAlignment = new CrossAxisAlignment.VerticalCrossAxisAlignment(vertical);
        return rowColumnParentData;
    }
}
