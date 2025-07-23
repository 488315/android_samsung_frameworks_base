package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class IntrinsicWidthElement extends ModifierNodeElement<IntrinsicWidthNode> {
    public final boolean enforceIncoming;
    public final IntrinsicSize width;

    public IntrinsicWidthElement(IntrinsicSize intrinsicSize, boolean z, Function1 function1) {
        this.width = intrinsicSize;
        this.enforceIncoming = z;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new IntrinsicWidthNode(this.width, this.enforceIncoming);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        IntrinsicWidthElement intrinsicWidthElement = obj instanceof IntrinsicWidthElement ? (IntrinsicWidthElement) obj : null;
        return intrinsicWidthElement != null && this.width == intrinsicWidthElement.width && this.enforceIncoming == intrinsicWidthElement.enforceIncoming;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.enforceIncoming) + (this.width.hashCode() * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        IntrinsicWidthNode intrinsicWidthNode = (IntrinsicWidthNode) node;
        intrinsicWidthNode.width = this.width;
        intrinsicWidthNode.enforceIncoming = this.enforceIncoming;
    }
}
