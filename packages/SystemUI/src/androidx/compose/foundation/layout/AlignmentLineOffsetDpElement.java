package androidx.compose.foundation.layout;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AlignmentLineOffsetDpElement extends ModifierNodeElement<AlignmentLineOffsetDpNode> {
    public final float after;
    public final AlignmentLine alignmentLine;
    public final float before;

    public /* synthetic */ AlignmentLineOffsetDpElement(AlignmentLine alignmentLine, float f, float f2, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(alignmentLine, f, f2, function1);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new AlignmentLineOffsetDpNode(this.alignmentLine, this.before, this.after, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        AlignmentLineOffsetDpElement alignmentLineOffsetDpElement = obj instanceof AlignmentLineOffsetDpElement ? (AlignmentLineOffsetDpElement) obj : null;
        return alignmentLineOffsetDpElement != null && Intrinsics.areEqual(this.alignmentLine, alignmentLineOffsetDpElement.alignmentLine) && Dp.m836equalsimpl0(this.before, alignmentLineOffsetDpElement.before) && Dp.m836equalsimpl0(this.after, alignmentLineOffsetDpElement.after);
    }

    public final int hashCode() {
        int hashCode = this.alignmentLine.hashCode() * 31;
        Dp.Companion companion = Dp.Companion;
        return Float.hashCode(this.after) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.before, hashCode, 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        AlignmentLineOffsetDpNode alignmentLineOffsetDpNode = (AlignmentLineOffsetDpNode) node;
        alignmentLineOffsetDpNode.alignmentLine = this.alignmentLine;
        alignmentLineOffsetDpNode.before = this.before;
        alignmentLineOffsetDpNode.after = this.after;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002a, code lost:
    
        if (androidx.compose.ui.unit.Dp.m836equalsimpl0(r3, androidx.compose.ui.unit.Dp.Unspecified) != false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x0019, code lost:
    
        if (androidx.compose.ui.unit.Dp.m836equalsimpl0(r2, androidx.compose.ui.unit.Dp.Unspecified) != false) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x002d, code lost:
    
        r0 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private AlignmentLineOffsetDpElement(androidx.compose.ui.layout.AlignmentLine r1, float r2, float r3, kotlin.jvm.functions.Function1 r4) {
        /*
            r0 = this;
            r0.<init>()
            r0.alignmentLine = r1
            r0.before = r2
            r0.after = r3
            r0 = 0
            int r1 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r1 >= 0) goto L1b
            androidx.compose.ui.unit.Dp$Companion r1 = androidx.compose.ui.unit.Dp.Companion
            r1.getClass()
            float r1 = androidx.compose.ui.unit.Dp.Unspecified
            boolean r1 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r2, r1)
            if (r1 == 0) goto L2d
        L1b:
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 >= 0) goto L2f
            androidx.compose.ui.unit.Dp$Companion r0 = androidx.compose.ui.unit.Dp.Companion
            r0.getClass()
            float r0 = androidx.compose.ui.unit.Dp.Unspecified
            boolean r0 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r3, r0)
            if (r0 == 0) goto L2d
            goto L2f
        L2d:
            r0 = 0
            goto L30
        L2f:
            r0 = 1
        L30:
            if (r0 != 0) goto L37
            java.lang.String r0 = "Padding from alignment line must be a non-negative number"
            androidx.compose.foundation.layout.internal.InlineClassHelperKt.throwIllegalArgumentException(r0)
        L37:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.AlignmentLineOffsetDpElement.<init>(androidx.compose.ui.layout.AlignmentLine, float, float, kotlin.jvm.functions.Function1):void");
    }
}
