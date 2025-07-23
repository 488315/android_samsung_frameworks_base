package androidx.compose.ui.platform;

import android.graphics.Rect;
import androidx.compose.ui.semantics.SemanticsNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SemanticsNodeWithAdjustedBounds {
    public final Rect adjustedBounds;
    public final SemanticsNode semanticsNode;

    public SemanticsNodeWithAdjustedBounds(SemanticsNode semanticsNode, Rect rect) {
        this.semanticsNode = semanticsNode;
        this.adjustedBounds = rect;
    }
}
