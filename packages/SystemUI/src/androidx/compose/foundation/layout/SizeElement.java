package androidx.compose.foundation.layout;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class SizeElement extends ModifierNodeElement<SizeNode> {
    public final boolean enforceIncoming;
    public final float maxHeight;
    public final float maxWidth;
    public final float minHeight;
    public final float minWidth;

    public /* synthetic */ SizeElement(float f, float f2, float f3, float f4, boolean z, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, z, function1);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new SizeNode(this.minWidth, this.minHeight, this.maxWidth, this.maxHeight, this.enforceIncoming, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SizeElement)) {
            return false;
        }
        SizeElement sizeElement = (SizeElement) obj;
        return Dp.m838equalsimpl0(this.minWidth, sizeElement.minWidth) && Dp.m838equalsimpl0(this.minHeight, sizeElement.minHeight) && Dp.m838equalsimpl0(this.maxWidth, sizeElement.maxWidth) && Dp.m838equalsimpl0(this.maxHeight, sizeElement.maxHeight) && this.enforceIncoming == sizeElement.enforceIncoming;
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return Boolean.hashCode(this.enforceIncoming) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxWidth, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.minHeight, Float.hashCode(this.minWidth) * 31, 31), 31), 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        SizeNode sizeNode = (SizeNode) node;
        sizeNode.minWidth = this.minWidth;
        sizeNode.minHeight = this.minHeight;
        sizeNode.maxWidth = this.maxWidth;
        sizeNode.maxHeight = this.maxHeight;
        sizeNode.enforceIncoming = this.enforceIncoming;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SizeElement(float f, float f2, float f3, float f4, boolean z, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            Dp.Companion.getClass();
            f = Dp.Unspecified;
        }
        float f5 = f;
        if ((i & 2) != 0) {
            Dp.Companion.getClass();
            f2 = Dp.Unspecified;
        }
        float f6 = f2;
        if ((i & 4) != 0) {
            Dp.Companion.getClass();
            f3 = Dp.Unspecified;
        }
        float f7 = f3;
        if ((i & 8) != 0) {
            Dp.Companion.getClass();
            f4 = Dp.Unspecified;
        }
        this(f5, f6, f7, f4, z, function1, null);
    }

    private SizeElement(float f, float f2, float f3, float f4, boolean z, Function1 function1) {
        this.minWidth = f;
        this.minHeight = f2;
        this.maxWidth = f3;
        this.maxHeight = f4;
        this.enforceIncoming = z;
    }
}
