package androidx.compose.foundation.layout;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VerticalAlignElement extends ModifierNodeElement<VerticalAlignNode> {
    public final Alignment.Vertical alignment;

    public VerticalAlignElement(Alignment.Vertical vertical) {
        this.alignment = vertical;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new VerticalAlignNode(this.alignment);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        VerticalAlignElement verticalAlignElement = obj instanceof VerticalAlignElement ? (VerticalAlignElement) obj : null;
        if (verticalAlignElement == null) {
            return false;
        }
        return Intrinsics.areEqual(this.alignment, verticalAlignElement.alignment);
    }

    public final int hashCode() {
        return this.alignment.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((VerticalAlignNode) node).vertical = this.alignment;
    }
}
