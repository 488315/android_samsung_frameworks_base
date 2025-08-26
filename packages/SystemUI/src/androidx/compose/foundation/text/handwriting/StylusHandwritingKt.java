package androidx.compose.foundation.text.handwriting;

import androidx.compose.foundation.text.TextPointerIcon_androidKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.StylusHoverIconModifierElement;
import androidx.compose.ui.node.DpTouchBoundsExpansion;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class StylusHandwritingKt {
    public static final DpTouchBoundsExpansion HandwritingBoundsExpansion;

    static {
        float f = 40;
        Dp.Companion companion = Dp.Companion;
        float f2 = 10;
        HandwritingBoundsExpansion = new DpTouchBoundsExpansion(f2, f, f2, f, true, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1, types: [androidx.compose.ui.input.pointer.StylusHoverIconModifierElement] */
    public static final Modifier stylusHandwriting(Modifier.Companion companion, boolean z, boolean z2, Function0 function0) {
        if (!z) {
            return companion;
        }
        if (z2) {
            ?? stylusHoverIconModifierElement = new StylusHoverIconModifierElement(TextPointerIcon_androidKt.handwritingPointerIcon, false, HandwritingBoundsExpansion);
            companion.getClass();
            companion = stylusHoverIconModifierElement;
        }
        return companion.then(new StylusHandwritingElement(function0));
    }
}
