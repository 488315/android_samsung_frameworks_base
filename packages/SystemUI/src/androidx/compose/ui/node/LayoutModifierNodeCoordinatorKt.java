package androidx.compose.ui.node;

import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.HorizontalAlignmentLine;
import androidx.compose.ui.unit.IntOffset;

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
                    long jMo651getPositionnOccac = child.mo651getPositionnOccac();
                    IntOffset.Companion companion = IntOffset.Companion;
                    return i + ((int) (jMo651getPositionnOccac & 4294967295L));
                }
                long jMo651getPositionnOccac2 = child.mo651getPositionnOccac();
                IntOffset.Companion companion2 = IntOffset.Companion;
                return i + ((int) (jMo651getPositionnOccac2 >> 32));
            }
        }
        return Integer.MIN_VALUE;
    }
}
