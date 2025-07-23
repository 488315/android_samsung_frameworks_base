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
        int i2 = 0;
        for (int i3 = 0; i3 < this.mCharacterStyleSpanSet.numberOfSpans; i3++) {
            if (i >= this.mCharacterStyleSpanSet.spanStarts[i3] && i <= this.mCharacterStyleSpanSet.spanEnds[i3]) {
                this.mCharacterStyleSpanSet.spans[i3].updateDrawState(this.mWorkPaint);
                i2 = calculateFinalColor(this.mWorkPaint);
            }
        }
        return i2;
    }

    private int calculateFinalColor(TextPaint textPaint) {
        return textPaint.getColor();
    }
}
