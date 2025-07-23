package androidx.compose.foundation.layout;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return Dp.m836equalsimpl0(this.minWidth, sizeElement.minWidth) && Dp.m836equalsimpl0(this.minHeight, sizeElement.minHeight) && Dp.m836equalsimpl0(this.maxWidth, sizeElement.maxWidth) && Dp.m836equalsimpl0(this.maxHeight, sizeElement.maxHeight) && this.enforceIncoming == sizeElement.enforceIncoming;
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SizeElement(float r9, float r10, float r11, float r12, boolean r13, kotlin.jvm.functions.Function1 r14, int r15, kotlin.jvm.internal.DefaultConstructorMarker r16) {
        /*
            r8 = this;
            r0 = r15 & 1
            if (r0 == 0) goto Lb
            androidx.compose.ui.unit.Dp$Companion r9 = androidx.compose.ui.unit.Dp.Companion
            r9.getClass()
            float r9 = androidx.compose.ui.unit.Dp.Unspecified
        Lb:
            r1 = r9
            r9 = r15 & 2
            if (r9 == 0) goto L17
            androidx.compose.ui.unit.Dp$Companion r9 = androidx.compose.ui.unit.Dp.Companion
            r9.getClass()
            float r10 = androidx.compose.ui.unit.Dp.Unspecified
        L17:
            r2 = r10
            r9 = r15 & 4
            if (r9 == 0) goto L23
            androidx.compose.ui.unit.Dp$Companion r9 = androidx.compose.ui.unit.Dp.Companion
            r9.getClass()
            float r11 = androidx.compose.ui.unit.Dp.Unspecified
        L23:
            r3 = r11
            r9 = r15 & 8
            if (r9 == 0) goto L2f
            androidx.compose.ui.unit.Dp$Companion r9 = androidx.compose.ui.unit.Dp.Companion
            r9.getClass()
            float r12 = androidx.compose.ui.unit.Dp.Unspecified
        L2f:
            r4 = r12
            r7 = 0
            r0 = r8
            r5 = r13
            r6 = r14
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.SizeElement.<init>(float, float, float, float, boolean, kotlin.jvm.functions.Function1, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private SizeElement(float f, float f2, float f3, float f4, boolean z, Function1 function1) {
        this.minWidth = f;
        this.minHeight = f2;
        this.maxWidth = f3;
        this.maxHeight = f4;
        this.enforceIncoming = z;
    }
}
