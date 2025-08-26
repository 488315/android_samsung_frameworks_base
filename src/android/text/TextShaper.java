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
        MeasuredParagraph measuredParagraphBuildForBidi = MeasuredParagraph.buildForBidi(charSequence, i, i3, textDirectionHeuristic, null);
        TextLine textLineObtain = TextLine.obtain();
        try {
            textLineObtain.set(textPaint, charSequence, i, i3, measuredParagraphBuildForBidi.getParagraphDir(), measuredParagraphBuildForBidi.getDirections(0, i2), false, null, -1, -1, false);
            textLineObtain.shape(glyphsConsumer);
        } finally {
            TextLine.recycle(textLineObtain);
        }
    }
}
