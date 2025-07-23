package androidx.compose.animation;

import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SizeAnimationModifierElement extends ModifierNodeElement<SizeAnimationModifierNode> {
    public final Alignment alignment;
    public final FiniteAnimationSpec animationSpec;
    public final Function2 finishedListener;

    public SizeAnimationModifierElement(FiniteAnimationSpec<IntSize> finiteAnimationSpec, Alignment alignment, Function2 function2) {
        this.animationSpec = finiteAnimationSpec;
        this.alignment = alignment;
        this.finishedListener = function2;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new SizeAnimationModifierNode(this.animationSpec, this.alignment, this.finishedListener);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SizeAnimationModifierElement)) {
            return false;
        }
        SizeAnimationModifierElement sizeAnimationModifierElement = (SizeAnimationModifierElement) obj;
        return Intrinsics.areEqual(this.animationSpec, sizeAnimationModifierElement.animationSpec) && Intrinsics.areEqual(this.alignment, sizeAnimationModifierElement.alignment) && Intrinsics.areEqual(this.finishedListener, sizeAnimationModifierElement.finishedListener);
    }

    public final int hashCode() {
        int hashCode = (this.alignment.hashCode() + (this.animationSpec.hashCode() * 31)) * 31;
        Function2 function2 = this.finishedListener;
        return hashCode + (function2 == null ? 0 : function2.hashCode());
    }

    public final String toString() {
        return "SizeAnimationModifierElement(animationSpec=" + this.animationSpec + ", alignment=" + this.alignment + ", finishedListener=" + this.finishedListener + ')';
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        SizeAnimationModifierNode sizeAnimationModifierNode = (SizeAnimationModifierNode) node;
        sizeAnimationModifierNode.animationSpec = this.animationSpec;
        sizeAnimationModifierNode.listener = this.finishedListener;
        sizeAnimationModifierNode.alignment = this.alignment;
    }
}
