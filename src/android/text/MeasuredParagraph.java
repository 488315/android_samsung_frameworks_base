package android.text;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.text.LineBreakConfig;
import android.graphics.text.MeasuredText;
import android.icu.lang.UCharacter;
import android.icu.text.Bidi;
import android.text.AutoGrowArray;
import android.text.Layout;
import android.text.style.LineBreakConfigSpan;
import android.text.style.MetricAffectingSpan;
import android.text.style.ReplacementSpan;
import android.util.Pools;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class MeasuredParagraph {
    private static final char OBJECT_REPLACEMENT_CHARACTER = 65532;
    private static final Pools.SynchronizedPool<MeasuredParagraph> sPool = new Pools.SynchronizedPool<>(1);
    private Bidi mBidi;
    private Paint.FontMetricsInt mCachedFm;
    private char[] mCopiedBuffer;
    private boolean mLtrWithoutBidi;
    private MeasuredText mMeasuredText;
    private int mParaDir;
    private Spanned mSpanned;
    private int mTextLength;
    private int mTextStart;
    private float mWholeWidth;
    private AutoGrowArray.ByteArray mLevels = new AutoGrowArray.ByteArray();
    private AutoGrowArray.FloatArray mWidths = new AutoGrowArray.FloatArray();
    private AutoGrowArray.IntArray mSpanEndCache = new AutoGrowArray.IntArray(4);
    private AutoGrowArray.IntArray mFontMetrics = new AutoGrowArray.IntArray(16);
    private final TextPaint mCachedPaint = new TextPaint();
    private final LineBreakConfig.Builder mLineBreakConfigBuilder = new LineBreakConfig.Builder();

    public interface StyleRunCallback {
        void onAppendReplacementRun(Paint paint, int i, float f);

        void onAppendStyleRun(Paint paint, LineBreakConfig lineBreakConfig, int i, boolean z);
    }

    private MeasuredParagraph() {
    }

    private static MeasuredParagraph obtain() {
        MeasuredParagraph measuredParagraphAcquire = sPool.acquire();
        return measuredParagraphAcquire != null ? measuredParagraphAcquire : new MeasuredParagraph();
    }

    public void recycle() {
        release();
        sPool.release(this);
    }

    public void release() {
        reset();
        this.mLevels.clearWithReleasingLargeArray();
        this.mWidths.clearWithReleasingLargeArray();
        this.mFontMetrics.clearWithReleasingLargeArray();
        this.mSpanEndCache.clearWithReleasingLargeArray();
    }

    private void reset() {
        this.mSpanned = null;
        this.mCopiedBuffer = null;
        this.mWholeWidth = 0.0f;
        this.mLevels.clear();
        this.mWidths.clear();
        this.mFontMetrics.clear();
        this.mSpanEndCache.clear();
        this.mMeasuredText = null;
        this.mBidi = null;
    }

    public int getTextLength() {
        return this.mTextLength;
    }

    public char[] getChars() {
        return this.mCopiedBuffer;
    }

    public int getParagraphDir() {
        Bidi bidi = this.mBidi;
        return (bidi == null || (bidi.getParaLevel() & 1) == 0) ? 1 : -1;
    }

    public Layout.Directions getDirections(int i, int i2) {
        int i3;
        Bidi bidi = this.mBidi;
        if (bidi == null) {
            return Layout.DIRS_ALL_LEFT_TO_RIGHT;
        }
        if (i == i2) {
            if ((bidi.getParaLevel() & 1) == 0) {
                return Layout.DIRS_ALL_LEFT_TO_RIGHT;
            }
            return Layout.DIRS_ALL_RIGHT_TO_LEFT;
        }
        Bidi bidiCreateLineBidi = bidi.createLineBidi(i, i2);
        if (bidiCreateLineBidi.getRunCount() == 1) {
            if (bidiCreateLineBidi.getRunLevel(0) == 1) {
                return Layout.DIRS_ALL_RIGHT_TO_LEFT;
            }
            if (bidiCreateLineBidi.getRunLevel(0) == 0) {
                return Layout.DIRS_ALL_LEFT_TO_RIGHT;
            }
            return new Layout.Directions(new int[]{0, (bidiCreateLineBidi.getRunLevel(0) << 26) | (i2 - i)});
        }
        byte[] bArr = new byte[bidiCreateLineBidi.getRunCount()];
        for (int i4 = 0; i4 < bidiCreateLineBidi.getRunCount(); i4++) {
            bArr[i4] = (byte) bidiCreateLineBidi.getRunLevel(i4);
        }
        int[] iArrReorderVisual = Bidi.reorderVisual(bArr);
        int[] iArr = new int[bidiCreateLineBidi.getRunCount() * 2];
        for (int i5 = 0; i5 < bidiCreateLineBidi.getRunCount(); i5++) {
            if ((this.mBidi.getBaseLevel() & 1) == 1) {
                i3 = iArrReorderVisual[(bidiCreateLineBidi.getRunCount() - i5) - 1];
            } else {
                i3 = iArrReorderVisual[i5];
            }
            int i6 = i5 * 2;
            iArr[i6] = bidiCreateLineBidi.getRunStart(i3);
            iArr[i6 + 1] = (bidiCreateLineBidi.getRunLimit(i3) - iArr[i6]) | (bidiCreateLineBidi.getRunLevel(i3) << 26);
        }
        return new Layout.Directions(iArr);
    }

    public float getWholeWidth() {
        return this.mWholeWidth;
    }

    public AutoGrowArray.FloatArray getWidths() {
        return this.mWidths;
    }

    public AutoGrowArray.IntArray getSpanEndCache() {
        return this.mSpanEndCache;
    }

    public AutoGrowArray.IntArray getFontMetrics() {
        return this.mFontMetrics;
    }

    public MeasuredText getMeasuredText() {
        return this.mMeasuredText;
    }

    public float getWidth(int i, int i2) {
        MeasuredText measuredText = this.mMeasuredText;
        if (measuredText == null) {
            float[] rawArray = this.mWidths.getRawArray();
            float f = 0.0f;
            while (i < i2) {
                f += rawArray[i];
                i++;
            }
            return f;
        }
        return measuredText.getWidth(i, i2);
    }

    public void getBounds(int i, int i2, Rect rect) {
        this.mMeasuredText.getBounds(i, i2, rect);
    }

    public void getFontMetricsInt(int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        this.mMeasuredText.getFontMetricsInt(i, i2, fontMetricsInt);
    }

    public float getCharWidthAt(int i) {
        return this.mMeasuredText.getCharWidthAt(i);
    }

    public static MeasuredParagraph buildForBidi(CharSequence charSequence, int i, int i2, TextDirectionHeuristic textDirectionHeuristic, MeasuredParagraph measuredParagraph) {
        if (measuredParagraph == null) {
            measuredParagraph = obtain();
        }
        measuredParagraph.resetAndAnalyzeBidi(charSequence, i, i2, textDirectionHeuristic);
        return measuredParagraph;
    }

    public static MeasuredParagraph buildForMeasurement(TextPaint textPaint, CharSequence charSequence, int i, int i2, TextDirectionHeuristic textDirectionHeuristic, MeasuredParagraph measuredParagraph) throws Throwable {
        if (measuredParagraph == null) {
            measuredParagraph = obtain();
        }
        MeasuredParagraph measuredParagraph2 = measuredParagraph;
        measuredParagraph2.resetAndAnalyzeBidi(charSequence, i, i2, textDirectionHeuristic);
        measuredParagraph2.mWidths.resize(measuredParagraph2.mTextLength);
        if (measuredParagraph2.mTextLength != 0) {
            if (measuredParagraph2.mSpanned == null) {
                measuredParagraph2.applyMetricsAffectingSpan(textPaint, null, null, null, i, i2, null, null);
                return measuredParagraph2;
            }
            int i3 = i;
            while (i3 < i2) {
                int iMin = Math.min(measuredParagraph2.mSpanned.nextSpanTransition(i3, i2, MetricAffectingSpan.class), measuredParagraph2.mSpanned.nextSpanTransition(i3, i2, LineBreakConfigSpan.class));
                measuredParagraph2.applyMetricsAffectingSpan(textPaint, null, (MetricAffectingSpan[]) TextUtils.removeEmptySpans((MetricAffectingSpan[]) measuredParagraph2.mSpanned.getSpans(i3, iMin, MetricAffectingSpan.class), measuredParagraph2.mSpanned, MetricAffectingSpan.class), (LineBreakConfigSpan[]) TextUtils.removeEmptySpans((LineBreakConfigSpan[]) measuredParagraph2.mSpanned.getSpans(i3, iMin, LineBreakConfigSpan.class), measuredParagraph2.mSpanned, LineBreakConfigSpan.class), i3, iMin, null, null);
                i3 = iMin;
            }
        }
        return measuredParagraph2;
    }

    public static MeasuredParagraph buildForStaticLayout(TextPaint textPaint, LineBreakConfig lineBreakConfig, CharSequence charSequence, int i, int i2, TextDirectionHeuristic textDirectionHeuristic, int i3, boolean z, boolean z2, MeasuredParagraph measuredParagraph, MeasuredParagraph measuredParagraph2) {
        return buildForStaticLayoutInternal(textPaint, lineBreakConfig, charSequence, i, i2, textDirectionHeuristic, i3, z, z2, measuredParagraph, measuredParagraph2, null);
    }

    public static MeasuredParagraph buildForStaticLayoutTest(TextPaint textPaint, LineBreakConfig lineBreakConfig, CharSequence charSequence, int i, int i2, TextDirectionHeuristic textDirectionHeuristic, int i3, boolean z, StyleRunCallback styleRunCallback) {
        return buildForStaticLayoutInternal(textPaint, lineBreakConfig, charSequence, i, i2, textDirectionHeuristic, i3, z, false, null, null, styleRunCallback);
    }

    private static MeasuredParagraph buildForStaticLayoutInternal(TextPaint textPaint, LineBreakConfig lineBreakConfig, CharSequence charSequence, int i, int i2, TextDirectionHeuristic textDirectionHeuristic, int i3, boolean z, boolean z2, MeasuredParagraph measuredParagraph, MeasuredParagraph measuredParagraph2, StyleRunCallback styleRunCallback) throws Throwable {
        MeasuredText.Builder builder;
        MeasuredParagraph measuredParagraph3;
        if (measuredParagraph2 == null) {
            measuredParagraph2 = obtain();
        }
        measuredParagraph2.resetAndAnalyzeBidi(charSequence, i, i2, textDirectionHeuristic);
        if (measuredParagraph == null) {
            builder = new MeasuredText.Builder(measuredParagraph2.mCopiedBuffer).setComputeHyphenation(i3).setComputeLayout(z).setComputeBounds(z2);
        } else {
            builder = new MeasuredText.Builder(measuredParagraph.mMeasuredText);
        }
        MeasuredText.Builder builder2 = builder;
        if (measuredParagraph2.mTextLength == 0) {
            measuredParagraph2.mMeasuredText = builder2.build();
            return measuredParagraph2;
        }
        if (measuredParagraph2.mSpanned == null) {
            measuredParagraph3 = measuredParagraph2;
            measuredParagraph3.applyMetricsAffectingSpan(textPaint, lineBreakConfig, null, null, i, i2, builder2, styleRunCallback);
            measuredParagraph3.mSpanEndCache.append(i2);
        } else {
            int i4 = i;
            measuredParagraph3 = measuredParagraph2;
            while (i4 < i2) {
                int iMin = Math.min(measuredParagraph3.mSpanned.nextSpanTransition(i4, i2, MetricAffectingSpan.class), measuredParagraph3.mSpanned.nextSpanTransition(i4, i2, LineBreakConfigSpan.class));
                measuredParagraph3.applyMetricsAffectingSpan(textPaint, lineBreakConfig, (MetricAffectingSpan[]) TextUtils.removeEmptySpans((MetricAffectingSpan[]) measuredParagraph3.mSpanned.getSpans(i4, iMin, MetricAffectingSpan.class), measuredParagraph3.mSpanned, MetricAffectingSpan.class), (LineBreakConfigSpan[]) TextUtils.removeEmptySpans((LineBreakConfigSpan[]) measuredParagraph3.mSpanned.getSpans(i4, iMin, LineBreakConfigSpan.class), measuredParagraph3.mSpanned, LineBreakConfigSpan.class), i4, iMin, builder2, styleRunCallback);
                measuredParagraph3.mSpanEndCache.append(iMin);
                i4 = iMin;
            }
        }
        measuredParagraph3.mMeasuredText = builder2.build();
        return measuredParagraph3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v29 */
    private void resetAndAnalyzeBidi(CharSequence charSequence, int i, int i2, TextDirectionHeuristic textDirectionHeuristic) {
        ?? IsRtl;
        int i3;
        reset();
        this.mSpanned = charSequence instanceof Spanned ? (Spanned) charSequence : null;
        this.mTextStart = i;
        int i4 = i2 - i;
        this.mTextLength = i4;
        char[] cArr = this.mCopiedBuffer;
        if (cArr == null || cArr.length != i4) {
            this.mCopiedBuffer = new char[i4];
        }
        TextUtils.getChars(charSequence, i, i2, this.mCopiedBuffer, 0);
        Spanned spanned = this.mSpanned;
        if (spanned != null) {
            ReplacementSpan[] replacementSpanArr = (ReplacementSpan[]) spanned.getSpans(i, i2, ReplacementSpan.class);
            for (int i5 = 0; i5 < replacementSpanArr.length; i5++) {
                int spanStart = this.mSpanned.getSpanStart(replacementSpanArr[i5]) - i;
                int spanEnd = this.mSpanned.getSpanEnd(replacementSpanArr[i5]) - i;
                if (spanStart < 0) {
                    spanStart = 0;
                }
                int i6 = this.mTextLength;
                if (spanEnd > i6) {
                    spanEnd = i6;
                }
                Arrays.fill(this.mCopiedBuffer, spanStart, spanEnd, OBJECT_REPLACEMENT_CHARACTER);
            }
        }
        if ((textDirectionHeuristic == TextDirectionHeuristics.LTR || textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR || textDirectionHeuristic == TextDirectionHeuristics.ANYRTL_LTR) && TextUtils.doesNotNeedBidi(this.mCopiedBuffer, 0, this.mTextLength)) {
            this.mLevels.clear();
            this.mLtrWithoutBidi = true;
            return;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LTR) {
            i3 = 0;
        } else if (textDirectionHeuristic == TextDirectionHeuristics.RTL) {
            i3 = 1;
        } else {
            if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
                IsRtl = 126;
            } else {
                IsRtl = textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL ? 127 : textDirectionHeuristic.isRtl(this.mCopiedBuffer, 0, this.mTextLength);
            }
            i3 = IsRtl;
        }
        char[] cArr2 = this.mCopiedBuffer;
        Bidi bidi = new Bidi(cArr2, 0, null, 0, cArr2.length, i3);
        this.mBidi = bidi;
        char[] cArr3 = this.mCopiedBuffer;
        if (cArr3.length > 0 && bidi.getParagraphIndex(cArr3.length - 1) != 0) {
            for (int i7 = 0; i7 < this.mTextLength; i7++) {
                if (!Character.isSurrogate(this.mCopiedBuffer[i7]) && UCharacter.getDirection(this.mCopiedBuffer[i7]) == 7) {
                    this.mCopiedBuffer[i7] = OBJECT_REPLACEMENT_CHARACTER;
                }
            }
            char[] cArr4 = this.mCopiedBuffer;
            this.mBidi = new Bidi(cArr4, 0, null, 0, cArr4.length, i3);
        }
        this.mLevels.resize(this.mTextLength);
        byte[] rawArray = this.mLevels.getRawArray();
        for (int i8 = 0; i8 < this.mTextLength; i8++) {
            rawArray[i8] = this.mBidi.getLevelAt(i8);
        }
        this.mLtrWithoutBidi = false;
    }

    private void applyReplacementRun(ReplacementSpan replacementSpan, int i, int i2, TextPaint textPaint, MeasuredText.Builder builder, StyleRunCallback styleRunCallback) {
        Spanned spanned = this.mSpanned;
        int i3 = this.mTextStart;
        float size = replacementSpan.getSize(textPaint, spanned, i + i3, i2 + i3, this.mCachedFm);
        if (builder == null) {
            this.mWidths.set(i, size);
            int i4 = i + 1;
            if (i2 > i4) {
                Arrays.fill(this.mWidths.getRawArray(), i4, i2, 0.0f);
            }
            this.mWholeWidth += size;
        } else {
            builder.appendReplacementRun(textPaint, i2 - i, size);
        }
        if (styleRunCallback != null) {
            styleRunCallback.onAppendReplacementRun(textPaint, i2 - i, size);
        }
    }

    private void applyStyleRun(int i, int i2, TextPaint textPaint, LineBreakConfig lineBreakConfig, MeasuredText.Builder builder, StyleRunCallback styleRunCallback) throws Throwable {
        int i3;
        int i4;
        boolean z = false;
        if (this.mLtrWithoutBidi) {
            if (builder == null) {
                int flags = textPaint.getFlags();
                textPaint.setFlags(textPaint.getFlags() | 24576);
                try {
                    int i5 = i2 - i;
                    i4 = i;
                    this.mWholeWidth += textPaint.getTextRunAdvances(this.mCopiedBuffer, i4, i5, i, i5, false, this.mWidths.getRawArray(), i);
                } finally {
                    textPaint.setFlags(flags);
                }
            } else {
                i4 = i;
                builder.appendStyleRun(textPaint, lineBreakConfig, i2 - i4, false);
            }
            if (styleRunCallback != null) {
                styleRunCallback.onAppendStyleRun(textPaint, lineBreakConfig, i2 - i4, false);
                return;
            }
            return;
        }
        int i6 = i;
        byte b = this.mLevels.get(i6);
        int i7 = i6 + 1;
        while (true) {
            if (i7 == i2 || this.mLevels.get(i7) != b) {
                boolean z2 = (b & 1) != 0 ? true : z;
                if (builder == null) {
                    int i8 = i7 - i6;
                    int flags2 = textPaint.getFlags();
                    textPaint.setFlags(textPaint.getFlags() | 24576);
                    try {
                        try {
                            i3 = flags2;
                            try {
                                this.mWholeWidth += textPaint.getTextRunAdvances(this.mCopiedBuffer, i6, i8, i6, i8, z2, this.mWidths.getRawArray(), i6);
                                textPaint.setFlags(i3);
                            } catch (Throwable th) {
                                th = th;
                                textPaint.setFlags(i3);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            i3 = flags2;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        i3 = flags2;
                    }
                } else {
                    builder.appendStyleRun(textPaint, lineBreakConfig, i7 - i6, z2);
                }
                if (styleRunCallback != null) {
                    styleRunCallback.onAppendStyleRun(textPaint, lineBreakConfig, i7 - i6, z2);
                }
                if (i7 == i2) {
                    return;
                }
                b = this.mLevels.get(i7);
                i6 = i7;
            }
            i7++;
            z = false;
        }
    }

    private void applyMetricsAffectingSpan(TextPaint textPaint, LineBreakConfig lineBreakConfig, MetricAffectingSpan[] metricAffectingSpanArr, LineBreakConfigSpan[] lineBreakConfigSpanArr, int i, int i2, MeasuredText.Builder builder, StyleRunCallback styleRunCallback) throws Throwable {
        MeasuredParagraph measuredParagraph;
        this.mCachedPaint.set(textPaint);
        this.mCachedPaint.baselineShift = 0;
        boolean z = builder != null;
        if (z && this.mCachedFm == null) {
            this.mCachedFm = new Paint.FontMetricsInt();
        }
        ReplacementSpan replacementSpan = null;
        if (metricAffectingSpanArr != null) {
            for (MetricAffectingSpan metricAffectingSpan : metricAffectingSpanArr) {
                if (metricAffectingSpan instanceof ReplacementSpan) {
                    replacementSpan = (ReplacementSpan) metricAffectingSpan;
                } else {
                    metricAffectingSpan.updateMeasureState(this.mCachedPaint);
                }
            }
        }
        ReplacementSpan replacementSpan2 = replacementSpan;
        if (lineBreakConfigSpanArr != null) {
            this.mLineBreakConfigBuilder.reset(lineBreakConfig);
            for (LineBreakConfigSpan lineBreakConfigSpan : lineBreakConfigSpanArr) {
                this.mLineBreakConfigBuilder.merge(lineBreakConfigSpan.getLineBreakConfig());
            }
            lineBreakConfig = this.mLineBreakConfigBuilder.build();
        }
        LineBreakConfig lineBreakConfig2 = lineBreakConfig;
        int i3 = this.mTextStart;
        int i4 = i - i3;
        int i5 = i2 - i3;
        if (builder != null) {
            this.mCachedPaint.getFontMetricsInt(this.mCachedFm);
        }
        if (replacementSpan2 != null) {
            measuredParagraph = this;
            measuredParagraph.applyReplacementRun(replacementSpan2, i4, i5, this.mCachedPaint, builder, styleRunCallback);
        } else {
            measuredParagraph = this;
            measuredParagraph.applyStyleRun(i4, i5, measuredParagraph.mCachedPaint, lineBreakConfig2, builder, styleRunCallback);
        }
        if (z) {
            if (measuredParagraph.mCachedPaint.baselineShift < 0) {
                measuredParagraph.mCachedFm.ascent += measuredParagraph.mCachedPaint.baselineShift;
                measuredParagraph.mCachedFm.top += measuredParagraph.mCachedPaint.baselineShift;
            } else {
                measuredParagraph.mCachedFm.descent += measuredParagraph.mCachedPaint.baselineShift;
                measuredParagraph.mCachedFm.bottom += measuredParagraph.mCachedPaint.baselineShift;
            }
            measuredParagraph.mFontMetrics.append(measuredParagraph.mCachedFm.top);
            measuredParagraph.mFontMetrics.append(measuredParagraph.mCachedFm.bottom);
            measuredParagraph.mFontMetrics.append(measuredParagraph.mCachedFm.ascent);
            measuredParagraph.mFontMetrics.append(measuredParagraph.mCachedFm.descent);
        }
    }

    int breakText(int i, boolean z, float f) {
        float[] rawArray = this.mWidths.getRawArray();
        if (z) {
            int i2 = 0;
            while (i2 < i) {
                f -= rawArray[i2];
                if (f < 0.0f) {
                    break;
                }
                i2++;
            }
            while (i2 > 0 && this.mCopiedBuffer[i2 - 1] == ' ') {
                i2--;
            }
            return i2;
        }
        int i3 = i - 1;
        int i4 = i3;
        while (i4 >= 0) {
            f -= rawArray[i4];
            if (f < 0.0f) {
                break;
            }
            i4--;
        }
        while (i4 < i3) {
            int i5 = i4 + 1;
            if (this.mCopiedBuffer[i5] != ' ' && rawArray[i5] != 0.0f) {
                break;
            }
            i4 = i5;
        }
        return (i - i4) - 1;
    }

    float measure(int i, int i2) {
        float[] rawArray = this.mWidths.getRawArray();
        float f = 0.0f;
        while (i < i2) {
            f += rawArray[i];
            i++;
        }
        return f;
    }

    public int getMemoryUsage() {
        return this.mMeasuredText.getMemoryUsage();
    }
}
