package androidx.compose.foundation.layout;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class OffsetPxElement extends ModifierNodeElement<OffsetPxNode> {
    public final Function1 offset;
    public final boolean rtlAware;

    public OffsetPxElement(Function1 function1, boolean z, Function1 function12) {
        this.offset = function1;
        this.rtlAware = z;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new OffsetPxNode(this.offset, this.rtlAware);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        OffsetPxElement offsetPxElement = obj instanceof OffsetPxElement ? (OffsetPxElement) obj : null;
        return offsetPxElement != null && this.offset == offsetPxElement.offset && this.rtlAware == offsetPxElement.rtlAware;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.rtlAware) + (this.offset.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("OffsetPxModifier(offset=");
        sb.append(this.offset);
        sb.append(", rtlAware=");
        return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.rtlAware, ')');
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        OffsetPxNode offsetPxNode = (OffsetPxNode) node;
        Function1 function1 = offsetPxNode.offset;
        Function1 function12 = this.offset;
        boolean z = this.rtlAware;
        if (function1 != function12 || offsetPxNode.rtlAware != z) {
            LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(offsetPxNode);
            LayoutNode.Companion companion = LayoutNode.Companion;
            requireLayoutNode.requestRelayout$ui_release(false);
        }
        offsetPxNode.offset = function12;
        offsetPxNode.rtlAware = z;
    }
}
