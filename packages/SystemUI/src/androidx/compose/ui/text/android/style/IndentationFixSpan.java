package androidx.compose.ui.text.android.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;
import androidx.compose.ui.text.android.TextAndroidCanvas;
import androidx.compose.ui.text.android.TextLayout_androidKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class IndentationFixSpan implements LeadingMarginSpan {
    @Override // android.text.style.LeadingMarginSpan
    public final void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
        int lineForOffset;
        if (layout == null || paint == null || (lineForOffset = layout.getLineForOffset(i6)) != layout.getLineCount() - 1) {
            return;
        }
        TextAndroidCanvas textAndroidCanvas = TextLayout_androidKt.SharedTextAndroidCanvas;
        if (layout.getEllipsisCount(lineForOffset) > 0) {
            float ellipsizedRightPadding = IndentationFixSpan_androidKt.getEllipsizedRightPadding(layout, lineForOffset, paint) + IndentationFixSpan_androidKt.getEllipsizedLeftPadding(layout, lineForOffset, paint);
            if (ellipsizedRightPadding == 0.0f) {
                return;
            }
            canvas.getClass();
            canvas.translate(ellipsizedRightPadding, 0.0f);
        }
    }

    @Override // android.text.style.LeadingMarginSpan
    public final int getLeadingMargin(boolean z) {
        return 0;
    }
}
