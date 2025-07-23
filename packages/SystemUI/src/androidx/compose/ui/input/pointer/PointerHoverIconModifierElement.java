package androidx.compose.ui.input.pointer;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PointerHoverIconModifierElement extends ModifierNodeElement<PointerHoverIconModifierNode> {
    public final PointerIcon icon;
    public final boolean overrideDescendants;

    public /* synthetic */ PointerHoverIconModifierElement(PointerIcon pointerIcon, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(pointerIcon, (i & 2) != 0 ? false : z);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new PointerHoverIconModifierNode(this.icon, this.overrideDescendants);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PointerHoverIconModifierElement)) {
            return false;
        }
        PointerHoverIconModifierElement pointerHoverIconModifierElement = (PointerHoverIconModifierElement) obj;
        return Intrinsics.areEqual(this.icon, pointerHoverIconModifierElement.icon) && this.overrideDescendants == pointerHoverIconModifierElement.overrideDescendants;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.overrideDescendants) + (this.icon.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PointerHoverIconModifierElement(icon=");
        sb.append(this.icon);
        sb.append(", overrideDescendants=");
        return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.overrideDescendants, ')');
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        PointerHoverIconModifierNode pointerHoverIconModifierNode = (PointerHoverIconModifierNode) node;
        PointerIcon pointerIcon = pointerHoverIconModifierNode.icon;
        PointerIcon pointerIcon2 = this.icon;
        if (!Intrinsics.areEqual(pointerIcon, pointerIcon2)) {
            pointerHoverIconModifierNode.icon = pointerIcon2;
            if (pointerHoverIconModifierNode.cursorInBoundsOfNode) {
                pointerHoverIconModifierNode.displayIconIfDescendantsDoNotHavePriority();
            }
        }
        pointerHoverIconModifierNode.setOverrideDescendants(this.overrideDescendants);
    }

    public PointerHoverIconModifierElement(PointerIcon pointerIcon, boolean z) {
        this.icon = pointerIcon;
        this.overrideDescendants = z;
    }
}
