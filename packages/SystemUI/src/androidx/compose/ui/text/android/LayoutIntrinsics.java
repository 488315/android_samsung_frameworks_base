package androidx.compose.ui.text.android;

import android.text.BoringLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.MetricAffectingSpan;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LayoutIntrinsics {
    public BoringLayout.Metrics _boringMetrics;
    public CharSequence _charSequenceForIntrinsicWidth;
    public float _maxIntrinsicWidth = Float.NaN;
    public float _minIntrinsicWidth = Float.NaN;
    public boolean boringMetricsIsInit;
    public final CharSequence charSequence;
    public final int textDirectionHeuristic;
    public final TextPaint textPaint;

    public LayoutIntrinsics(CharSequence charSequence, TextPaint textPaint, int i) {
        this.charSequence = charSequence;
        this.textPaint = textPaint;
        this.textDirectionHeuristic = i;
    }

    public final BoringLayout.Metrics getBoringMetrics() {
        if (!this.boringMetricsIsInit) {
            TextDirectionHeuristic textDirectionHeuristic = TextLayout_androidKt.getTextDirectionHeuristic(this.textDirectionHeuristic);
            BoringLayoutFactory boringLayoutFactory = BoringLayoutFactory.INSTANCE;
            CharSequence charSequence = this.charSequence;
            TextPaint textPaint = this.textPaint;
            boringLayoutFactory.getClass();
            int i = BoringLayoutFactory33.$r8$clinit;
            this._boringMetrics = BoringLayout.isBoring(charSequence, textPaint, textDirectionHeuristic, true, null);
            this.boringMetricsIsInit = true;
        }
        return this._boringMetrics;
    }

    public final CharSequence getCharSequenceForIntrinsicWidth() {
        CharSequence charSequence = this._charSequenceForIntrinsicWidth;
        if (charSequence != null) {
            charSequence.getClass();
            return charSequence;
        }
        CharSequence charSequence2 = this.charSequence;
        if (charSequence2 instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence2;
            if (SpannedExtensions_androidKt.hasSpan(spanned, CharacterStyle.class)) {
                CharacterStyle[] characterStyleArr = (CharacterStyle[]) spanned.getSpans(0, charSequence2.length(), CharacterStyle.class);
                if (characterStyleArr != null && characterStyleArr.length != 0) {
                    SpannableString spannableString = null;
                    for (CharacterStyle characterStyle : characterStyleArr) {
                        if (!(characterStyle instanceof MetricAffectingSpan)) {
                            if (spannableString == null) {
                                spannableString = new SpannableString(charSequence2);
                            }
                            spannableString.removeSpan(characterStyle);
                        }
                    }
                    if (spannableString != null) {
                        charSequence2 = spannableString;
                    }
                }
            }
        }
        this._charSequenceForIntrinsicWidth = charSequence2;
        return charSequence2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0051, code lost:
    
        if (androidx.compose.ui.text.android.SpannedExtensions_androidKt.hasSpan(r2, androidx.compose.ui.text.android.style.LetterSpacingSpanEm.class) == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0059, code lost:
    
        if (r3.getLetterSpacing() == 0.0f) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float getMaxIntrinsicWidth() {
        /*
            r5 = this;
            float r0 = r5._maxIntrinsicWidth
            boolean r0 = java.lang.Float.isNaN(r0)
            if (r0 != 0) goto Lb
            float r5 = r5._maxIntrinsicWidth
            return r5
        Lb:
            android.text.BoringLayout$Metrics r0 = r5.getBoringMetrics()
            if (r0 == 0) goto L14
            int r0 = r0.width
            goto L15
        L14:
            r0 = -1
        L15:
            float r0 = (float) r0
            r1 = 0
            int r2 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r2 >= 0) goto L34
            java.lang.CharSequence r0 = r5.getCharSequenceForIntrinsicWidth()
            int r0 = r0.length()
            java.lang.CharSequence r2 = r5.getCharSequenceForIntrinsicWidth()
            android.text.TextPaint r3 = r5.textPaint
            r4 = 0
            float r0 = android.text.Layout.getDesiredWidth(r2, r4, r0, r3)
            double r2 = (double) r0
            double r2 = java.lang.Math.ceil(r2)
            float r0 = (float) r2
        L34:
            java.lang.CharSequence r2 = r5.charSequence
            android.text.TextPaint r3 = r5.textPaint
            int r4 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r4 != 0) goto L3d
            goto L5f
        L3d:
            boolean r4 = r2 instanceof android.text.Spanned
            if (r4 == 0) goto L53
            android.text.Spanned r2 = (android.text.Spanned) r2
            java.lang.Class<androidx.compose.ui.text.android.style.LetterSpacingSpanPx> r4 = androidx.compose.ui.text.android.style.LetterSpacingSpanPx.class
            boolean r4 = androidx.compose.ui.text.android.SpannedExtensions_androidKt.hasSpan(r2, r4)
            if (r4 != 0) goto L5c
            java.lang.Class<androidx.compose.ui.text.android.style.LetterSpacingSpanEm> r4 = androidx.compose.ui.text.android.style.LetterSpacingSpanEm.class
            boolean r2 = androidx.compose.ui.text.android.SpannedExtensions_androidKt.hasSpan(r2, r4)
            if (r2 != 0) goto L5c
        L53:
            float r2 = r3.getLetterSpacing()
            int r1 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r1 != 0) goto L5c
            goto L5f
        L5c:
            r1 = 1056964608(0x3f000000, float:0.5)
            float r0 = r0 + r1
        L5f:
            r5._maxIntrinsicWidth = r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.android.LayoutIntrinsics.getMaxIntrinsicWidth():float");
    }
}
