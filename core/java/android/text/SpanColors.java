package android.text;

import android.text.style.CharacterStyle;

public class SpanColors {
    public static final int NO_COLOR_FOUND = 0;
    private final SpanSet<CharacterStyle> mCharacterStyleSpanSet =
            new SpanSet<>(CharacterStyle.class);
    private TextPaint mWorkPaint;

    public void init(TextPaint workPaint, Spanned spanned, int start, int end) {
        this.mWorkPaint = workPaint;
        this.mCharacterStyleSpanSet.init(spanned, start, end);
    }

    public void recycle() {
        this.mWorkPaint = null;
        this.mCharacterStyleSpanSet.recycle();
    }

    public int getColorAt(int index) {
        int finalColor = 0;
        this.mWorkPaint.setColor(0);
        for (int k = 0; k < this.mCharacterStyleSpanSet.numberOfSpans; k++) {
            if (index >= this.mCharacterStyleSpanSet.spanStarts[k]
                    && index <= this.mCharacterStyleSpanSet.spanEnds[k]) {
                CharacterStyle span = this.mCharacterStyleSpanSet.spans[k];
                span.updateDrawState(this.mWorkPaint);
                finalColor = calculateFinalColor(this.mWorkPaint);
            }
        }
        return finalColor;
    }

    private int calculateFinalColor(TextPaint workPaint) {
        return workPaint.getColor();
    }
}
