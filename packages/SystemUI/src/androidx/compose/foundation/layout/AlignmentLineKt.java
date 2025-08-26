package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.HorizontalAlignmentLine;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public abstract class AlignmentLineKt {
    /* renamed from: paddingFrom-4j6BHR0$default, reason: not valid java name */
    public static Modifier m91paddingFrom4j6BHR0$default(Modifier.Companion companion, HorizontalAlignmentLine horizontalAlignmentLine, float f, float f2, int i) {
        if ((i & 2) != 0) {
            Dp.Companion.getClass();
            f = Dp.Unspecified;
        }
        float f3 = f;
        if ((i & 4) != 0) {
            Dp.Companion.getClass();
            f2 = Dp.Unspecified;
        }
        AlignmentLineOffsetDpElement alignmentLineOffsetDpElement = new AlignmentLineOffsetDpElement(horizontalAlignmentLine, f3, f2, InspectableValueKt.NoInspectorInfo, null);
        companion.getClass();
        return alignmentLineOffsetDpElement;
    }
}
