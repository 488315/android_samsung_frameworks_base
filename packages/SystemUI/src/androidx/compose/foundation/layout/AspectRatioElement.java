package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.internal.InlineClassHelperKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class AspectRatioElement extends ModifierNodeElement<AspectRatioNode> {
    public final float aspectRatio;
    public final boolean matchHeightConstraintsFirst;

    public AspectRatioElement(float f, boolean z, Function1 function1) {
        this.aspectRatio = f;
        this.matchHeightConstraintsFirst = z;
        if (f > 0.0f) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("aspectRatio " + f + " must be > 0");
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new AspectRatioNode(this.aspectRatio, this.matchHeightConstraintsFirst);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        AspectRatioElement aspectRatioElement = obj instanceof AspectRatioElement ? (AspectRatioElement) obj : null;
        if (aspectRatioElement != null && this.aspectRatio == aspectRatioElement.aspectRatio) {
            if (this.matchHeightConstraintsFirst == ((AspectRatioElement) obj).matchHeightConstraintsFirst) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.matchHeightConstraintsFirst) + (Float.hashCode(this.aspectRatio) * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        AspectRatioNode aspectRatioNode = (AspectRatioNode) node;
        aspectRatioNode.aspectRatio = this.aspectRatio;
        aspectRatioNode.matchHeightConstraintsFirst = this.matchHeightConstraintsFirst;
    }
}
