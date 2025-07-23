package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LayoutElement extends ModifierNodeElement<LayoutModifierImpl> {
    public final Function3 measure;

    public LayoutElement(Function3 function3) {
        this.measure = function3;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new LayoutModifierImpl(this.measure);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LayoutElement) {
            return this.measure == ((LayoutElement) obj).measure;
        }
        return false;
    }

    public final int hashCode() {
        return this.measure.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((LayoutModifierImpl) node).measureBlock = this.measure;
    }
}
