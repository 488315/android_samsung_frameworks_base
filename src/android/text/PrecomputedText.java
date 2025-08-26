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

    public static PrecomputedText create(CharSequence charSequence, Params params) {
        ParagraphInfo[] paragraphInfoArrCreateMeasuredParagraphs;
        CharSequence charSequence2;
        Params params2;
        if (charSequence instanceof PrecomputedText) {
            PrecomputedText precomputedText = (PrecomputedText) charSequence;
            Params params3 = precomputedText.getParams();
            int iCheckResultUsable = params3.checkResultUsable(params.mPaint, params.mTextDir, params.mBreakStrategy, params.mHyphenationFrequency, params.mLineBreakConfig);
            if (iCheckResultUsable != 1) {
                if (iCheckResultUsable == 2) {
                    return precomputedText;
                }
            } else if (params.getBreakStrategy() == params3.getBreakStrategy() && params.getHyphenationFrequency() == params3.getHyphenationFrequency()) {
                paragraphInfoArrCreateMeasuredParagraphs = createMeasuredParagraphsFromPrecomputedText(precomputedText, params, true);
            }
            paragraphInfoArrCreateMeasuredParagraphs = null;
        } else {
            paragraphInfoArrCreateMeasuredParagraphs = null;
        }
        if (paragraphInfoArrCreateMeasuredParagraphs == null) {
            paragraphInfoArrCreateMeasuredParagraphs = createMeasuredParagraphs(charSequence, params, 0, charSequence.length(), true, true);
            params2 = params;
            charSequence2 = charSequence;
        } else {
            charSequence2 = charSequence;
            params2 = params;
        }
        return new PrecomputedText(charSequence2, 0, charSequence2.length(), params2, paragraphInfoArrCreateMeasuredParagraphs);
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

    /* JADX WARN: Removed duplicated region for block: B:25:0x0063 A[PHI: r2
      0x0063: PHI (r2v3 android.graphics.text.LineBreakConfig) = (r2v2 android.graphics.text.LineBreakConfig), (r2v7 android.graphics.text.LineBreakConfig) binds: [B:18:0x003f, B:20:0x0049] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
            int iIndexOf = TextUtils.indexOf(charSequence, LINE_FEED, i4, i2);
            int i5 = iIndexOf < 0 ? i2 : iIndexOf + 1;
            if (lineBreakConfig == null) {
                lineBreakConfig = params.getLineBreakConfig();
                if (lineBreakConfig.getLineBreakWordStyle() == 2) {
                    if (i4 != i || i5 != i2) {
                        lineBreakConfig = new LineBreakConfig.Builder().merge(lineBreakConfig).setLineBreakWordStyle(0).build();
                    }
                }
            }
            LineBreakConfig lineBreakConfig2 = lineBreakConfig;
            arrayList.add(new ParagraphInfo(i5, MeasuredParagraph.buildForStaticLayout(params.getTextPaint(), lineBreakConfig2, charSequence, i4, i5, params.getTextDirection(), i3, z, z2, null, null)));
            lineBreakConfig = lineBreakConfig2;
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
        int iFindParaIndex = findParaIndex(i);
        int paragraphStart = getParagraphStart(iFindParaIndex);
        int paragraphEnd = getParagraphEnd(iFindParaIndex);
        if (i < paragraphStart || paragraphEnd < i2) {
            throw new IllegalArgumentException("Cannot measured across the paragraph:para: (" + paragraphStart + ", " + paragraphEnd + "), request: (" + i + ", " + i2 + NavigationBarInflaterView.KEY_CODE_END);
        }
        return getMeasuredParagraph(iFindParaIndex).getWidth(i - paragraphStart, i2 - paragraphStart);
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
        int iFindParaIndex = findParaIndex(i);
        int paragraphStart = getParagraphStart(iFindParaIndex);
        int paragraphEnd = getParagraphEnd(iFindParaIndex);
        if (i < paragraphStart || paragraphEnd < i2) {
            throw new IllegalArgumentException("Cannot measured across the paragraph:para: (" + paragraphStart + ", " + paragraphEnd + "), request: (" + i + ", " + i2 + NavigationBarInflaterView.KEY_CODE_END);
        }
        getMeasuredParagraph(iFindParaIndex).getBounds(i - paragraphStart, i2 - paragraphStart, rect);
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
        int iFindParaIndex = findParaIndex(i);
        int paragraphStart = getParagraphStart(iFindParaIndex);
        int paragraphEnd = getParagraphEnd(iFindParaIndex);
        if (i < paragraphStart || paragraphEnd < i2) {
            throw new IllegalArgumentException("Cannot measured across the paragraph:para: (" + paragraphStart + ", " + paragraphEnd + "), request: (" + i + ", " + i2 + NavigationBarInflaterView.KEY_CODE_END);
        }
        getMeasuredParagraph(iFindParaIndex).getFontMetricsInt(i - paragraphStart, i2 - paragraphStart, fontMetricsInt);
    }

    public float getCharWidthAt(int i) {
        Preconditions.checkArgument(i >= 0 && i < this.mText.length(), "invalid offset");
        int iFindParaIndex = findParaIndex(i);
        int paragraphStart = getParagraphStart(iFindParaIndex);
        getParagraphEnd(iFindParaIndex);
        return getMeasuredParagraph(iFindParaIndex).getCharWidthAt(i - paragraphStart);
    }

    public int getMemoryUsage() {
        int memoryUsage = 0;
        for (int i = 0; i < getParagraphCount(); i++) {
            memoryUsage += getMeasuredParagraph(i).getMemoryUsage();
        }
        return memoryUsage;
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
