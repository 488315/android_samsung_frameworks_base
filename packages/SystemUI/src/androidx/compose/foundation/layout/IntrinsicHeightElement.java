package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class IntrinsicHeightElement extends ModifierNodeElement<IntrinsicHeightNode> {
    public final boolean enforceIncoming;
    public final IntrinsicSize height;

    public IntrinsicHeightElement(IntrinsicSize intrinsicSize, boolean z, Function1 function1) {
        this.height = intrinsicSize;
        this.enforceIncoming = z;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new IntrinsicHeightNode(this.height, this.enforceIncoming);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        IntrinsicHeightElement intrinsicHeightElement = obj instanceof IntrinsicHeightElement ? (IntrinsicHeightElement) obj : null;
        return intrinsicHeightElement != null && this.height == intrinsicHeightElement.height && this.enforceIncoming == intrinsicHeightElement.enforceIncoming;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.enforceIncoming) + (this.height.hashCode() * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        IntrinsicHeightNode intrinsicHeightNode = (IntrinsicHeightNode) node;
        intrinsicHeightNode.height = this.height;
        intrinsicHeightNode.enforceIncoming = this.enforceIncoming;
    }
}
