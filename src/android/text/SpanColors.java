package android.text;

import android.text.style.CharacterStyle;

/* loaded from: classes4.dex */
public class SpanColors {
    public static final int NO_COLOR_FOUND = 0;
    private final SpanSet<CharacterStyle> mCharacterStyleSpanSet = new SpanSet<>(CharacterStyle.class);
    private TextPaint mWorkPaint;

    public void init(TextPaint textPaint, Spanned spanned, int i, int i2) {
        this.mWorkPaint = textPaint;
        this.mCharacterStyleSpanSet.init(spanned, i, i2);
    }

    public void recycle() {
        this.mWorkPaint = null;
        this.mCharacterStyleSpanSet.recycle();
    }

    public int getColorAt(int i) {
        this.mWorkPaint.setColor(0);
        int iCalculateFinalColor = 0;
        for (int i2 = 0; i2 < this.mCharacterStyleSpanSet.numberOfSpans; i2++) {
            if (i >= this.mCharacterStyleSpanSet.spanStarts[i2] && i <= this.mCharacterStyleSpanSet.spanEnds[i2]) {
                this.mCharacterStyleSpanSet.spans[i2].updateDrawState(this.mWorkPaint);
                iCalculateFinalColor = calculateFinalColor(this.mWorkPaint);
            }
        }
        return iCalculateFinalColor;
    }

    private int calculateFinalColor(TextPaint textPaint) {
        return textPaint.getColor();
    }
}
