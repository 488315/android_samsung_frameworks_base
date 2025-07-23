package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LayoutIdElement extends ModifierNodeElement<LayoutIdModifier> {
    public final Object layoutId;

    public LayoutIdElement(Object obj) {
        this.layoutId = obj;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new LayoutIdModifier(this.layoutId);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof LayoutIdElement) && Intrinsics.areEqual(this.layoutId, ((LayoutIdElement) obj).layoutId);
    }

    public final int hashCode() {
        return this.layoutId.hashCode();
    }

    public final String toString() {
        return "LayoutIdElement(layoutId=" + this.layoutId + ')';
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((LayoutIdModifier) node).layoutId = this.layoutId;
    }
}
