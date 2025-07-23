package androidx.compose.foundation.layout;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ParentDataModifierNode;
import androidx.compose.ui.unit.Density;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BoxChildDataNode extends Modifier.Node implements ParentDataModifierNode {
    public Alignment alignment;
    public boolean matchParentSize;

    public BoxChildDataNode(Alignment alignment, boolean z) {
        this.alignment = alignment;
        this.matchParentSize = z;
    }

    @Override // androidx.compose.ui.node.ParentDataModifierNode
    public final Object modifyParentData(Density density, Object obj) {
        return this;
    }
}
