package androidx.compose.foundation;

import androidx.compose.foundation.MarqueeAnimationMode;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MarqueeModifierElement extends ModifierNodeElement<MarqueeModifierNode> {
    public final int animationMode;
    public final int delayMillis;
    public final int initialDelayMillis;
    public final int iterations;
    public final MarqueeSpacing spacing;
    public final float velocity;

    public /* synthetic */ MarqueeModifierElement(int i, int i2, int i3, int i4, MarqueeSpacing marqueeSpacing, float f, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, i3, i4, marqueeSpacing, f);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new MarqueeModifierNode(this.iterations, this.animationMode, this.delayMillis, this.initialDelayMillis, this.spacing, this.velocity, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MarqueeModifierElement)) {
            return false;
        }
        MarqueeModifierElement marqueeModifierElement = (MarqueeModifierElement) obj;
        if (this.iterations != marqueeModifierElement.iterations) {
            return false;
        }
        MarqueeAnimationMode.Companion companion = MarqueeAnimationMode.Companion;
        return this.animationMode == marqueeModifierElement.animationMode && this.delayMillis == marqueeModifierElement.delayMillis && this.initialDelayMillis == marqueeModifierElement.initialDelayMillis && Intrinsics.areEqual(this.spacing, marqueeModifierElement.spacing) && Dp.m836equalsimpl0(this.velocity, marqueeModifierElement.velocity);
    }

    public final int hashCode() {
        int hashCode = Integer.hashCode(this.iterations) * 31;
        MarqueeAnimationMode.Companion companion = MarqueeAnimationMode.Companion;
        int hashCode2 = (this.spacing.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.initialDelayMillis, ReorderTile$$ExternalSyntheticOutline0.m(this.delayMillis, ReorderTile$$ExternalSyntheticOutline0.m(this.animationMode, hashCode, 31), 31), 31)) * 31;
        Dp.Companion companion2 = Dp.Companion;
        return Float.hashCode(this.velocity) + hashCode2;
    }

    public final String toString() {
        return "MarqueeModifierElement(iterations=" + this.iterations + ", animationMode=" + ((Object) MarqueeAnimationMode.m44toStringimpl(this.animationMode)) + ", delayMillis=" + this.delayMillis + ", initialDelayMillis=" + this.initialDelayMillis + ", spacing=" + this.spacing + ", velocity=" + ((Object) Dp.m837toStringimpl(this.velocity)) + ')';
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        MarqueeModifierNode marqueeModifierNode = (MarqueeModifierNode) node;
        ((SnapshotMutableStateImpl) marqueeModifierNode.spacing$delegate).setValue(this.spacing);
        ((SnapshotMutableStateImpl) marqueeModifierNode.animationMode$delegate).setValue(MarqueeAnimationMode.m43boximpl(this.animationMode));
        int i = marqueeModifierNode.iterations;
        int i2 = this.iterations;
        int i3 = this.delayMillis;
        int i4 = this.initialDelayMillis;
        float f = this.velocity;
        if (i == i2 && marqueeModifierNode.delayMillis == i3 && marqueeModifierNode.initialDelayMillis == i4 && Dp.m836equalsimpl0(marqueeModifierNode.velocity, f)) {
            return;
        }
        marqueeModifierNode.iterations = i2;
        marqueeModifierNode.delayMillis = i3;
        marqueeModifierNode.initialDelayMillis = i4;
        marqueeModifierNode.velocity = f;
        marqueeModifierNode.restartAnimation();
    }

    private MarqueeModifierElement(int i, int i2, int i3, int i4, MarqueeSpacing marqueeSpacing, float f) {
        this.iterations = i;
        this.animationMode = i2;
        this.delayMillis = i3;
        this.initialDelayMillis = i4;
        this.spacing = marqueeSpacing;
        this.velocity = f;
    }
}
