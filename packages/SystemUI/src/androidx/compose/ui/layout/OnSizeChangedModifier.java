package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
final class OnSizeChangedModifier extends ModifierNodeElement<OnSizeChangedNode> {
    public final Function1 onSizeChanged;

    public OnSizeChangedModifier(Function1 function1) {
        this.onSizeChanged = function1;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new OnSizeChangedNode(this.onSizeChanged);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OnSizeChangedModifier) {
            return this.onSizeChanged == ((OnSizeChangedModifier) obj).onSizeChanged;
        }
        return false;
    }

    public final int hashCode() {
        return this.onSizeChanged.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        OnSizeChangedNode onSizeChangedNode = (OnSizeChangedNode) node;
        onSizeChangedNode.onSizeChanged = this.onSizeChanged;
        long j = Integer.MIN_VALUE;
        IntSize.Companion companion = IntSize.Companion;
        onSizeChangedNode.previousSize = (j & 4294967295L) | (j << 32);
    }
}
