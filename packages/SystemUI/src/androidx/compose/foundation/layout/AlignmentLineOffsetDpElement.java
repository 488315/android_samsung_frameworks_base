package androidx.compose.foundation.layout;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.internal.InlineClassHelperKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
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
        return alignmentLineOffsetDpElement != null && Intrinsics.areEqual(this.alignmentLine, alignmentLineOffsetDpElement.alignmentLine) && Dp.m838equalsimpl0(this.before, alignmentLineOffsetDpElement.before) && Dp.m838equalsimpl0(this.after, alignmentLineOffsetDpElement.after);
    }

    public final int hashCode() {
        int iHashCode = this.alignmentLine.hashCode() * 31;
        Dp.Companion companion = Dp.Companion;
        return Float.hashCode(this.after) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.before, iHashCode, 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        AlignmentLineOffsetDpNode alignmentLineOffsetDpNode = (AlignmentLineOffsetDpNode) node;
        alignmentLineOffsetDpNode.alignmentLine = this.alignmentLine;
        alignmentLineOffsetDpNode.before = this.before;
        alignmentLineOffsetDpNode.after = this.after;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private AlignmentLineOffsetDpElement(AlignmentLine alignmentLine, float f, float f2, Function1 function1) {
        boolean z;
        this.alignmentLine = alignmentLine;
        this.before = f;
        this.after = f2;
        if (f < 0.0f) {
            Dp.Companion.getClass();
            if (Dp.m838equalsimpl0(f, Dp.Unspecified)) {
                if (f2 < 0.0f) {
                    Dp.Companion.getClass();
                    if (!Dp.m838equalsimpl0(f2, Dp.Unspecified)) {
                        z = false;
                    }
                }
                z = true;
            }
        }
        if (z) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("Padding from alignment line must be a non-negative number");
    }
}
