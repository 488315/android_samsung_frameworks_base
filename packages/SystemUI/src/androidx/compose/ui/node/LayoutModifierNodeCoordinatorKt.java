package androidx.compose.ui.node;

import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.HorizontalAlignmentLine;
import androidx.compose.ui.unit.IntOffset;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LayoutModifierNodeCoordinatorKt {
    public static final int access$calculateAlignmentAndPlaceChildAsNeeded(LookaheadCapablePlaceable lookaheadCapablePlaceable, AlignmentLine alignmentLine) {
        LookaheadCapablePlaceable child = lookaheadCapablePlaceable.getChild();
        if (child == null) {
            InlineClassHelperKt.throwIllegalStateException("Child of " + lookaheadCapablePlaceable + " cannot be null when calculating alignment line");
        }
        if (lookaheadCapablePlaceable.getMeasureResult$ui_release().getAlignmentLines().containsKey(alignmentLine)) {
            Integer num = (Integer) lookaheadCapablePlaceable.getMeasureResult$ui_release().getAlignmentLines().get(alignmentLine);
            if (num != null) {
                return num.intValue();
            }
        } else {
            int i = child.get(alignmentLine);
            if (i != Integer.MIN_VALUE) {
                child.isShallowPlacing = true;
                lookaheadCapablePlaceable.isPlacingForAlignment = true;
                lookaheadCapablePlaceable.replace$ui_release();
                child.isShallowPlacing = false;
                lookaheadCapablePlaceable.isPlacingForAlignment = false;
                if (alignmentLine instanceof HorizontalAlignmentLine) {
                    long mo649getPositionnOccac = child.mo649getPositionnOccac();
                    IntOffset.Companion companion = IntOffset.Companion;
                    return i + ((int) (mo649getPositionnOccac & 4294967295L));
                }
                long mo649getPositionnOccac2 = child.mo649getPositionnOccac();
                IntOffset.Companion companion2 = IntOffset.Companion;
                return i + ((int) (mo649getPositionnOccac2 >> 32));
            }
        }
        return Integer.MIN_VALUE;
    }
}
