package android.text;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.text.LineBreakConfig;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.style.MetricAffectingSpan;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes4.dex */
public class PrecomputedText implements Spannable {
    private static final char LINE_FEED = '\n';
    private final int mEnd;
    private final ParagraphInfo[] mParagraphInfo;
    private final Params mParams;
    private final int mStart;
    private final SpannableString mText;

    private static boolean isFastHyphenation(int i) {
        return i == 4 || i == 3;
    }

    public static final class Params {
        public static final int NEED_RECOMPUTE = 1;
        public static final int UNUSABLE = 0;
        public static final int USABLE = 2;
        private final int mBreakStrategy;
        private final int mHyphenationFrequency;
        private final LineBreakConfig mLineBreakConfig;
        private final TextPaint mPaint;
        private final TextDirectionHeuristic mTextDir;

        @Retention(RetentionPolicy.SOURCE)
        public @interface CheckResultUsableResult {
        }

        public static class Builder {
            private int mBreakStrategy;
            private int mHyphenationFrequency;
            private LineBreakConfig mLineBreakConfig;
            private final TextPaint mPaint;
            private TextDirectionHeuristic mTextDir;

            public Builder(TextPaint textPaint) {
                this.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
                this.mBreakStrategy = 1;
                this.mHyphenationFrequency = 1;
                this.mLineBreakConfig = LineBreakConfig.NONE;
                this.mPaint = textPaint;
            }

            public Builder(Params params) {
                this.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
                this.mBreakStrategy = 1;
                this.mHyphenationFrequency = 1;
                this.mLineBreakConfig = LineBreakConfig.NONE;
                this.mPaint = params.mPaint;
                this.mTextDir = params.mTextDir;
                this.mBreakStrategy = params.mBreakStrategy;
                this.mHyphenationFrequency = params.mHyphenationFrequency;
                this.mLineBreakConfig = params.mLineBreakConfig;
            }

            public Builder setBreakStrategy(int i) {
                this.mBreakStrategy = i;
                return this;
            }

            public Builder setHyphenationFrequency(int i) {
                this.mHyphenationFrequency = i;
                return this;
            }

            public Builder setTextDirection(TextDirectionHeuristic textDirectionHeuristic) {
                this.mTextDir = textDirectionHeuristic;
                return this;
            }

            public Builder setLineBreakConfig(LineBreakConfig lineBreakConfig) {
                this.mLineBreakConfig = lineBreakConfig;
                return this;
            }

            public Params build() {
                return new Params(this.mPaint, this.mLineBreakConfig, this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency);
            }
        }

        public Params(TextPaint textPaint, LineBreakConfig lineBreakConfig, TextDirectionHeuristic textDirectionHeuristic, int i, int i2) {
            this.mPaint = textPaint;
            this.mTextDir = textDirectionHeuristic;
            this.mBreakStrategy = i;
            this.mHyphenationFrequency = i2;
            this.mLineBreakConfig = lineBreakConfig;
        }

        public TextPaint getTextPaint() {
            return this.mPaint;
        }

        public TextDirectionHeuristic getTextDirection() {
            return this.mTextDir;
        }

        public int getBreakStrategy() {
            return this.mBreakStrategy;
        }

        public int getHyphenationFrequency() {
            return this.mHyphenationFrequency;
        }

        public LineBreakConfig getLineBreakConfig() {
            return this.mLineBreakConfig;
        }

        public int checkResultUsable(TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, int i, int i2, LineBreakConfig lineBreakConfig) {
            if (this.mBreakStrategy == i && this.mHyphenationFrequency == i2 && this.mLineBreakConfig.equals(lineBreakConfig) && this.mPaint.equalsForTextMeasurement(textPaint)) {
                return this.mTextDir == textDirectionHeuristic ? 2 : 1;
            }
            return 0;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj != null && (obj instanceof Params)) {
                Params params = (Params) obj;
                if (checkResultUsable(params.mPaint, params.mTextDir, params.mBreakStrategy, params.mHyphenationFrequency, params.mLineBreakConfig) == 2) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Float.valueOf(this.mPaint.getLetterSpacing()), Float.valueOf(this.mPaint.getWordSpacing()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocales(), this.mPaint.getTypeface(), this.mPaint.getFontVariationSettings(), Boolean.valueOf(this.mPaint.isElegantTextHeight()), this.mTextDir, Integer.valueOf(this.mBreakStrategy), Integer.valueOf(this.mHyphenationFrequency), Integer.valueOf(LineBreakConfig.getResolvedLineBreakStyle(this.mLineBreakConfig)), Integer.valueOf(LineBreakConfig.getResolvedLineBreakWordStyle(this.mLineBreakConfig)));
        }

        public String toString() {
            return "{textSize=" + this.mPaint.getTextSize() + ", textScaleX=" + this.mPaint.getTextScaleX() + ", textSkewX=" + this.mPaint.getTextSkewX() + ", letterSpacing=" + this.mPaint.getLetterSpacing() + ", textLocale=" + this.mPaint.getTextLocales() + ", typeface=" + this.mPaint.getTypeface() + ", variationSettings=" + this.mPaint.getFontVariationSettings() + ", elegantTextHeight=" + this.mPaint.isElegantTextHeight() + ", textDir=" + this.mTextDir + ", breakStrategy=" + this.mBreakStrategy + ", hyphenationFrequency=" + this.mHyphenationFrequency + ", lineBreakStyle=" + LineBreakConfig.getResolvedLineBreakStyle(this.mLineBreakConfig) + ", lineBreakWordStyle=" + LineBreakConfig.getResolvedLineBreakWordStyle(this.mLineBreakConfig) + "}";
        }
    }

    public static class ParagraphInfo {
        public final MeasuredParagraph measured;
        public final int paragraphEnd;

        public ParagraphInfo(int i, MeasuredParagraph measuredParagraph) {
            this.paragraphEnd = i;
            this.measured = measuredParagraph;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.text.PrecomputedText create(java.lang.CharSequence r7, android.text.PrecomputedText.Params r8) {
        /*
            boolean r0 = r7 instanceof android.text.PrecomputedText
            if (r0 == 0) goto L44
            r0 = r7
            android.text.PrecomputedText r0 = (android.text.PrecomputedText) r0
            android.text.PrecomputedText$Params r1 = r0.getParams()
            android.text.TextPaint r2 = android.text.PrecomputedText.Params.m5440$$Nest$fgetmPaint(r8)
            android.text.TextDirectionHeuristic r3 = android.text.PrecomputedText.Params.m5441$$Nest$fgetmTextDir(r8)
            int r4 = android.text.PrecomputedText.Params.m5437$$Nest$fgetmBreakStrategy(r8)
            int r5 = android.text.PrecomputedText.Params.m5438$$Nest$fgetmHyphenationFrequency(r8)
            android.graphics.text.LineBreakConfig r6 = android.text.PrecomputedText.Params.m5439$$Nest$fgetmLineBreakConfig(r8)
            int r2 = r1.checkResultUsable(r2, r3, r4, r5, r6)
            r3 = 1
            if (r2 == r3) goto L2b
            r1 = 2
            if (r2 == r1) goto L2a
            goto L44
        L2a:
            return r0
        L2b:
            int r2 = r8.getBreakStrategy()
            int r4 = r1.getBreakStrategy()
            if (r2 != r4) goto L44
            int r2 = r8.getHyphenationFrequency()
            int r1 = r1.getHyphenationFrequency()
            if (r2 != r1) goto L44
            android.text.PrecomputedText$ParagraphInfo[] r0 = createMeasuredParagraphsFromPrecomputedText(r0, r8, r3)
            goto L45
        L44:
            r0 = 0
        L45:
            if (r0 != 0) goto L57
            int r4 = r7.length()
            r5 = 1
            r6 = 1
            r3 = 0
            r1 = r7
            r2 = r8
            android.text.PrecomputedText$ParagraphInfo[] r0 = createMeasuredParagraphs(r1, r2, r3, r4, r5, r6)
            r5 = r2
            r2 = r1
            goto L59
        L57:
            r2 = r7
            r5 = r8
        L59:
            r6 = r0
            android.text.PrecomputedText r1 = new android.text.PrecomputedText
            r3 = 0
            int r4 = r2.length()
            r1.<init>(r2, r3, r4, r5, r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.PrecomputedText.create(java.lang.CharSequence, android.text.PrecomputedText$Params):android.text.PrecomputedText");
    }

    private static ParagraphInfo[] createMeasuredParagraphsFromPrecomputedText(PrecomputedText precomputedText, Params params, boolean z) {
        int i;
        if (params.getBreakStrategy() == 0 || params.getHyphenationFrequency() == 0) {
            i = 0;
        } else {
            i = isFastHyphenation(params.getHyphenationFrequency()) ? 2 : 1;
        }
        LineBreakConfig lineBreakConfig = params.getLineBreakConfig();
        if (lineBreakConfig.getLineBreakWordStyle() == 2 && precomputedText.getParagraphCount() != 1) {
            lineBreakConfig = new LineBreakConfig.Builder().merge(lineBreakConfig).setLineBreakWordStyle(0).build();
        }
        LineBreakConfig lineBreakConfig2 = lineBreakConfig;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < precomputedText.getParagraphCount(); i2++) {
            int paragraphStart = precomputedText.getParagraphStart(i2);
            int paragraphEnd = precomputedText.getParagraphEnd(i2);
            arrayList.add(new ParagraphInfo(paragraphEnd, MeasuredParagraph.buildForStaticLayout(params.getTextPaint(), lineBreakConfig2, precomputedText, paragraphStart, paragraphEnd, params.getTextDirection(), i, z, true, precomputedText.getMeasuredParagraph(i2), null)));
        }
        return (ParagraphInfo[]) arrayList.toArray(new ParagraphInfo[arrayList.size()]);
    }

    public static ParagraphInfo[] createMeasuredParagraphs(CharSequence charSequence, Params params, int i, int i2, boolean z, boolean z2) {
        int i3;
        ArrayList arrayList = new ArrayList();
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(params);
        if (params.getBreakStrategy() == 0 || params.getHyphenationFrequency() == 0) {
            i3 = 0;
        } else {
            i3 = isFastHyphenation(params.getHyphenationFrequency()) ? 2 : 1;
        }
        LineBreakConfig lineBreakConfig = null;
        int i4 = i;
        while (i4 < i2) {
            int indexOf = TextUtils.indexOf(charSequence, LINE_FEED, i4, i2);
            int i5 = indexOf < 0 ? i2 : indexOf + 1;
            if (lineBreakConfig == null) {
                lineBreakConfig = params.getLineBreakConfig();
                if (lineBreakConfig.getLineBreakWordStyle() == 2) {
                    if (i4 != i || i5 != i2) {
                        lineBreakConfig = new LineBreakConfig.Builder().merge(lineBreakConfig).setLineBreakWordStyle(0).build();
                    }
                    LineBreakConfig lineBreakConfig2 = lineBreakConfig;
                    arrayList.add(new ParagraphInfo(i5, MeasuredParagraph.buildForStaticLayout(params.getTextPaint(), lineBreakConfig2, charSequence, i4, i5, params.getTextDirection(), i3, z, z2, null, null)));
                    lineBreakConfig = lineBreakConfig2;
                    i4 = i5;
                }
            }
            LineBreakConfig lineBreakConfig22 = lineBreakConfig;
            arrayList.add(new ParagraphInfo(i5, MeasuredParagraph.buildForStaticLayout(params.getTextPaint(), lineBreakConfig22, charSequence, i4, i5, params.getTextDirection(), i3, z, z2, null, null)));
            lineBreakConfig = lineBreakConfig22;
            i4 = i5;
        }
        return (ParagraphInfo[]) arrayList.toArray(new ParagraphInfo[arrayList.size()]);
    }

    private PrecomputedText(CharSequence charSequence, int i, int i2, Params params, ParagraphInfo[] paragraphInfoArr) {
        this.mText = new SpannableString(charSequence, true);
        this.mStart = i;
        this.mEnd = i2;
        this.mParams = params;
        this.mParagraphInfo = paragraphInfoArr;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public int getStart() {
        return this.mStart;
    }

    public int getEnd() {
        return this.mEnd;
    }

    public Params getParams() {
        return this.mParams;
    }

    public int getParagraphCount() {
        return this.mParagraphInfo.length;
    }

    public int getParagraphStart(int i) {
        Preconditions.checkArgumentInRange(i, 0, getParagraphCount(), "paraIndex");
        return i == 0 ? this.mStart : getParagraphEnd(i - 1);
    }

    public int getParagraphEnd(int i) {
        Preconditions.checkArgumentInRange(i, 0, getParagraphCount(), "paraIndex");
        return this.mParagraphInfo[i].paragraphEnd;
    }

    public MeasuredParagraph getMeasuredParagraph(int i) {
        return this.mParagraphInfo[i].measured;
    }

    public ParagraphInfo[] getParagraphInfo() {
        return this.mParagraphInfo;
    }

    public int checkResultUsable(int i, int i2, TextDirectionHeuristic textDirectionHeuristic, TextPaint textPaint, int i3, int i4, LineBreakConfig lineBreakConfig) {
        if (this.mStart == i && this.mEnd == i2) {
            return this.mParams.checkResultUsable(textPaint, textDirectionHeuristic, i3, i4, lineBreakConfig);
        }
        return 0;
    }

    public int findParaIndex(int i) {
        int i2 = 0;
        while (true) {
            ParagraphInfo[] paragraphInfoArr = this.mParagraphInfo;
            if (i2 < paragraphInfoArr.length) {
                if (i < paragraphInfoArr[i2].paragraphEnd) {
                    return i2;
                }
                i2++;
            } else {
                StringBuilder sb = new StringBuilder("pos must be less than ");
                sb.append(this.mParagraphInfo[r3.length - 1].paragraphEnd);
                sb.append(", gave ");
                sb.append(i);
                throw new IndexOutOfBoundsException(sb.toString());
            }
        }
    }

    public float getWidth(int i, int i2) {
        Preconditions.checkArgument(i >= 0 && i <= this.mText.length(), "invalid start offset");
        Preconditions.checkArgument(i2 >= 0 && i2 <= this.mText.length(), "invalid end offset");
        Preconditions.checkArgument(i <= i2, "start offset can not be larger than end offset");
        if (i == i2) {
            return 0.0f;
        }
        int findParaIndex = findParaIndex(i);
        int paragraphStart = getParagraphStart(findParaIndex);
        int paragraphEnd = getParagraphEnd(findParaIndex);
        if (i < paragraphStart || paragraphEnd < i2) {
            throw new IllegalArgumentException("Cannot measured across the paragraph:para: (" + paragraphStart + ", " + paragraphEnd + "), request: (" + i + ", " + i2 + NavigationBarInflaterView.KEY_CODE_END);
        }
        return getMeasuredParagraph(findParaIndex).getWidth(i - paragraphStart, i2 - paragraphStart);
    }

    public void getBounds(int i, int i2, Rect rect) {
        Preconditions.checkArgument(i >= 0 && i <= this.mText.length(), "invalid start offset");
        Preconditions.checkArgument(i2 >= 0 && i2 <= this.mText.length(), "invalid end offset");
        Preconditions.checkArgument(i <= i2, "start offset can not be larger than end offset");
        Preconditions.checkNotNull(rect);
        if (i == i2) {
            rect.set(0, 0, 0, 0);
            return;
        }
        int findParaIndex = findParaIndex(i);
        int paragraphStart = getParagraphStart(findParaIndex);
        int paragraphEnd = getParagraphEnd(findParaIndex);
        if (i < paragraphStart || paragraphEnd < i2) {
            throw new IllegalArgumentException("Cannot measured across the paragraph:para: (" + paragraphStart + ", " + paragraphEnd + "), request: (" + i + ", " + i2 + NavigationBarInflaterView.KEY_CODE_END);
        }
        getMeasuredParagraph(findParaIndex).getBounds(i - paragraphStart, i2 - paragraphStart, rect);
    }

    public void getFontMetricsInt(int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        Preconditions.checkArgument(i >= 0 && i <= this.mText.length(), "invalid start offset");
        Preconditions.checkArgument(i2 >= 0 && i2 <= this.mText.length(), "invalid end offset");
        Preconditions.checkArgument(i <= i2, "start offset can not be larger than end offset");
        Objects.requireNonNull(fontMetricsInt);
        if (i == i2) {
            this.mParams.getTextPaint().getFontMetricsInt(fontMetricsInt);
            return;
        }
        int findParaIndex = findParaIndex(i);
        int paragraphStart = getParagraphStart(findParaIndex);
        int paragraphEnd = getParagraphEnd(findParaIndex);
        if (i < paragraphStart || paragraphEnd < i2) {
            throw new IllegalArgumentException("Cannot measured across the paragraph:para: (" + paragraphStart + ", " + paragraphEnd + "), request: (" + i + ", " + i2 + NavigationBarInflaterView.KEY_CODE_END);
        }
        getMeasuredParagraph(findParaIndex).getFontMetricsInt(i - paragraphStart, i2 - paragraphStart, fontMetricsInt);
    }

    public float getCharWidthAt(int i) {
        Preconditions.checkArgument(i >= 0 && i < this.mText.length(), "invalid offset");
        int findParaIndex = findParaIndex(i);
        int paragraphStart = getParagraphStart(findParaIndex);
        getParagraphEnd(findParaIndex);
        return getMeasuredParagraph(findParaIndex).getCharWidthAt(i - paragraphStart);
    }

    public int getMemoryUsage() {
        int i = 0;
        for (int i2 = 0; i2 < getParagraphCount(); i2++) {
            i += getMeasuredParagraph(i2).getMemoryUsage();
        }
        return i;
    }

    @Override // android.text.Spannable
    public void setSpan(Object obj, int i, int i2, int i3) {
        if (obj instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be set to PrecomputedText.");
        }
        this.mText.setSpan(obj, i, i2, i3);
    }

    @Override // android.text.Spannable
    public void removeSpan(Object obj) {
        if (obj instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be removed from PrecomputedText.");
        }
        this.mText.removeSpan(obj);
    }

    @Override // android.text.Spanned
    public <T> T[] getSpans(int i, int i2, Class<T> cls) {
        return (T[]) this.mText.getSpans(i, i2, cls);
    }

    @Override // android.text.Spanned
    public int getSpanStart(Object obj) {
        return this.mText.getSpanStart(obj);
    }

    @Override // android.text.Spanned
    public int getSpanEnd(Object obj) {
        return this.mText.getSpanEnd(obj);
    }

    @Override // android.text.Spanned
    public int getSpanFlags(Object obj) {
        return this.mText.getSpanFlags(obj);
    }

    @Override // android.text.Spanned
    public int nextSpanTransition(int i, int i2, Class cls) {
        return this.mText.nextSpanTransition(i, i2, cls);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.mText.length();
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return this.mText.charAt(i);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return create(this.mText.subSequence(i, i2), this.mParams);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return this.mText.toString();
    }
}
