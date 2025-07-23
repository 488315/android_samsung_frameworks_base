package android.text;

import android.graphics.text.PositionedGlyphs;

/* loaded from: classes4.dex */
public class TextShaper {

    public interface GlyphsConsumer {
        void accept(int i, int i2, PositionedGlyphs positionedGlyphs, TextPaint textPaint);
    }

    private TextShaper() {
    }

    public static void shapeText(CharSequence charSequence, int i, int i2, TextDirectionHeuristic textDirectionHeuristic, TextPaint textPaint, GlyphsConsumer glyphsConsumer) {
        int i3 = i + i2;
        MeasuredParagraph buildForBidi = MeasuredParagraph.buildForBidi(charSequence, i, i3, textDirectionHeuristic, null);
        TextLine obtain = TextLine.obtain();
        try {
            obtain.set(textPaint, charSequence, i, i3, buildForBidi.getParagraphDir(), buildForBidi.getDirections(0, i2), false, null, -1, -1, false);
            obtain.shape(glyphsConsumer);
        } finally {
            TextLine.recycle(obtain);
        }
    }
}
