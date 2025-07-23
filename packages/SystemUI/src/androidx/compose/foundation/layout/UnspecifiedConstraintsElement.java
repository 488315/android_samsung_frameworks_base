package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class UnspecifiedConstraintsElement extends ModifierNodeElement<UnspecifiedConstraintsNode> {
    public final float minHeight;
    public final float minWidth;

    public /* synthetic */ UnspecifiedConstraintsElement(float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new UnspecifiedConstraintsNode(this.minWidth, this.minHeight, null);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof UnspecifiedConstraintsElement)) {
            return false;
        }
        UnspecifiedConstraintsElement unspecifiedConstraintsElement = (UnspecifiedConstraintsElement) obj;
        return Dp.m836equalsimpl0(this.minWidth, unspecifiedConstraintsElement.minWidth) && Dp.m836equalsimpl0(this.minHeight, unspecifiedConstraintsElement.minHeight);
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return Float.hashCode(this.minHeight) + (Float.hashCode(this.minWidth) * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        UnspecifiedConstraintsNode unspecifiedConstraintsNode = (UnspecifiedConstraintsNode) node;
        unspecifiedConstraintsNode.minWidth = this.minWidth;
        unspecifiedConstraintsNode.minHeight = this.minHeight;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public UnspecifiedConstraintsElement(float r1, float r2, int r3, kotlin.jvm.internal.DefaultConstructorMarker r4) {
        /*
            r0 = this;
            r4 = r3 & 1
            if (r4 == 0) goto Lb
            androidx.compose.ui.unit.Dp$Companion r1 = androidx.compose.ui.unit.Dp.Companion
            r1.getClass()
            float r1 = androidx.compose.ui.unit.Dp.Unspecified
        Lb:
            r3 = r3 & 2
            if (r3 == 0) goto L16
            androidx.compose.ui.unit.Dp$Companion r2 = androidx.compose.ui.unit.Dp.Companion
            r2.getClass()
            float r2 = androidx.compose.ui.unit.Dp.Unspecified
        L16:
            r3 = 0
            r0.<init>(r1, r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.UnspecifiedConstraintsElement.<init>(float, float, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private UnspecifiedConstraintsElement(float f, float f2) {
        this.minWidth = f;
        this.minHeight = f2;
    }
}
