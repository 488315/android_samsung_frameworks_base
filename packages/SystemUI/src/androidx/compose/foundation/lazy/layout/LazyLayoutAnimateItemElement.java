package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.IntOffset;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyLayoutAnimateItemElement extends ModifierNodeElement<LazyLayoutAnimationSpecsNode> {
    public final FiniteAnimationSpec fadeInSpec;
    public final FiniteAnimationSpec fadeOutSpec;
    public final FiniteAnimationSpec placementSpec;

    public LazyLayoutAnimateItemElement(FiniteAnimationSpec<Float> finiteAnimationSpec, FiniteAnimationSpec<IntOffset> finiteAnimationSpec2, FiniteAnimationSpec<Float> finiteAnimationSpec3) {
        this.fadeInSpec = finiteAnimationSpec;
        this.placementSpec = finiteAnimationSpec2;
        this.fadeOutSpec = finiteAnimationSpec3;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new LazyLayoutAnimationSpecsNode(this.fadeInSpec, this.placementSpec, this.fadeOutSpec);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LazyLayoutAnimateItemElement)) {
            return false;
        }
        LazyLayoutAnimateItemElement lazyLayoutAnimateItemElement = (LazyLayoutAnimateItemElement) obj;
        return Intrinsics.areEqual(this.fadeInSpec, lazyLayoutAnimateItemElement.fadeInSpec) && Intrinsics.areEqual(this.placementSpec, lazyLayoutAnimateItemElement.placementSpec) && Intrinsics.areEqual(this.fadeOutSpec, lazyLayoutAnimateItemElement.fadeOutSpec);
    }

    public final int hashCode() {
        FiniteAnimationSpec finiteAnimationSpec = this.fadeInSpec;
        int hashCode = (finiteAnimationSpec == null ? 0 : finiteAnimationSpec.hashCode()) * 31;
        FiniteAnimationSpec finiteAnimationSpec2 = this.placementSpec;
        int hashCode2 = (hashCode + (finiteAnimationSpec2 == null ? 0 : finiteAnimationSpec2.hashCode())) * 31;
        FiniteAnimationSpec finiteAnimationSpec3 = this.fadeOutSpec;
        return hashCode2 + (finiteAnimationSpec3 != null ? finiteAnimationSpec3.hashCode() : 0);
    }

    public final String toString() {
        return "LazyLayoutAnimateItemElement(fadeInSpec=" + this.fadeInSpec + ", placementSpec=" + this.placementSpec + ", fadeOutSpec=" + this.fadeOutSpec + ')';
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        LazyLayoutAnimationSpecsNode lazyLayoutAnimationSpecsNode = (LazyLayoutAnimationSpecsNode) node;
        lazyLayoutAnimationSpecsNode.fadeInSpec = this.fadeInSpec;
        lazyLayoutAnimationSpecsNode.placementSpec = this.placementSpec;
        lazyLayoutAnimationSpecsNode.fadeOutSpec = this.fadeOutSpec;
    }
}
