package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.CrossAxisAlignment;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ParentDataModifierNode;
import androidx.compose.ui.unit.Density;

/* loaded from: classes.dex */
public final class HorizontalAlignNode extends Modifier.Node implements ParentDataModifierNode {
    public Alignment.Horizontal horizontal;

    public HorizontalAlignNode(Alignment.Horizontal horizontal) {
        this.horizontal = horizontal;
    }

    @Override // androidx.compose.ui.node.ParentDataModifierNode
    public final Object modifyParentData(Density density, Object obj) {
        RowColumnParentData rowColumnParentData = obj instanceof RowColumnParentData ? (RowColumnParentData) obj : null;
        if (rowColumnParentData == null) {
            rowColumnParentData = new RowColumnParentData(0.0f, false, null, null, 15, null);
        }
        CrossAxisAlignment.Companion companion = CrossAxisAlignment.Companion;
        Alignment.Horizontal horizontal = this.horizontal;
        companion.getClass();
        rowColumnParentData.crossAxisAlignment = new CrossAxisAlignment.HorizontalCrossAxisAlignment(horizontal);
        return rowColumnParentData;
    }
}
