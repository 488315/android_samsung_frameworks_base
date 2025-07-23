package androidx.compose.ui.text.android;

import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextLayout_androidKt {
    public static final TextAndroidCanvas SharedTextAndroidCanvas = new TextAndroidCanvas();
    public static final long ZeroVerticalPadding;

    static {
        long j = 0;
        ZeroVerticalPadding = (j & 4294967295L) | (j << 32);
    }

    public static final TextDirectionHeuristic getTextDirectionHeuristic(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? TextDirectionHeuristics.FIRSTSTRONG_LTR : TextDirectionHeuristics.LOCALE : TextDirectionHeuristics.ANYRTL_LTR : TextDirectionHeuristics.FIRSTSTRONG_RTL : TextDirectionHeuristics.FIRSTSTRONG_LTR : TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
    }
}
