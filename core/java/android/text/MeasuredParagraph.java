package android.text;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.text.LineBreakConfig;
import android.graphics.text.MeasuredText;
import android.icu.lang.UCharacter;
import android.icu.text.Bidi;
import android.text.style.LineBreakConfigSpan;
import android.text.style.MetricAffectingSpan;
import android.text.style.ReplacementSpan;
import android.util.Pools;

import java.util.Arrays;

public class MeasuredParagraph {
    private static final char OBJECT_REPLACEMENT_CHARACTER = 65532;
    private static final Pools.SynchronizedPool<MeasuredParagraph> sPool =
            new Pools.SynchronizedPool<>(1);
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

    private MeasuredParagraph() {}

    private static MeasuredParagraph obtain() {
        MeasuredParagraph mt = sPool.acquire();
        return mt != null ? mt : new MeasuredParagraph();
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
        if (ClientFlags.icuBidiMigration()) {
            return (this.mBidi == null || (this.mBidi.getParaLevel() & 1) == 0) ? 1 : -1;
        }
        return this.mParaDir;
    }

    public Layout.Directions getDirections(int start, int end) {
        int vIndex;
        if (ClientFlags.icuBidiMigration()) {
            if (this.mBidi == null) {
                return Layout.DIRS_ALL_LEFT_TO_RIGHT;
            }
            if (start == end) {
                if ((1 & this.mBidi.getParaLevel()) == 0) {
                    return Layout.DIRS_ALL_LEFT_TO_RIGHT;
                }
                return Layout.DIRS_ALL_RIGHT_TO_LEFT;
            }
            Bidi bidi = this.mBidi.createLineBidi(start, end);
            if (bidi.getRunCount() == 1) {
                if (bidi.getRunLevel(0) == 1) {
                    return Layout.DIRS_ALL_RIGHT_TO_LEFT;
                }
                if (bidi.getRunLevel(0) == 0) {
                    return Layout.DIRS_ALL_LEFT_TO_RIGHT;
                }
                return new Layout.Directions(
                        new int[] {0, (bidi.getRunLevel(0) << 26) | (end - start)});
            }
            byte[] levels = new byte[bidi.getRunCount()];
            for (int i = 0; i < bidi.getRunCount(); i++) {
                levels[i] = (byte) bidi.getRunLevel(i);
            }
            int[] visualOrders = Bidi.reorderVisual(levels);
            int[] dirs = new int[bidi.getRunCount() * 2];
            for (int i2 = 0; i2 < bidi.getRunCount(); i2++) {
                if ((this.mBidi.getBaseLevel() & 1) == 1) {
                    vIndex = visualOrders[(bidi.getRunCount() - i2) - 1];
                } else {
                    vIndex = visualOrders[i2];
                }
                dirs[i2 * 2] = bidi.getRunStart(vIndex);
                dirs[(i2 * 2) + 1] =
                        (bidi.getRunLevel(vIndex) << 26)
                                | (bidi.getRunLimit(vIndex) - dirs[i2 * 2]);
            }
            return new Layout.Directions(dirs);
        }
        if (this.mLtrWithoutBidi) {
            return Layout.DIRS_ALL_LEFT_TO_RIGHT;
        }
        int length = end - start;
        return AndroidBidi.directions(
                this.mParaDir,
                this.mLevels.getRawArray(),
                start,
                this.mCopiedBuffer,
                start,
                length);
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

    public float getWidth(int start, int end) {
        if (this.mMeasuredText == null) {
            float[] widths = this.mWidths.getRawArray();
            float r = 0.0f;
            for (int i = start; i < end; i++) {
                r += widths[i];
            }
            return r;
        }
        return this.mMeasuredText.getWidth(start, end);
    }

    public void getBounds(int start, int end, Rect bounds) {
        this.mMeasuredText.getBounds(start, end, bounds);
    }

    public void getFontMetricsInt(int start, int end, Paint.FontMetricsInt fmi) {
        this.mMeasuredText.getFontMetricsInt(start, end, fmi);
    }

    public float getCharWidthAt(int offset) {
        return this.mMeasuredText.getCharWidthAt(offset);
    }

    public static MeasuredParagraph buildForBidi(
            CharSequence text,
            int start,
            int end,
            TextDirectionHeuristic textDir,
            MeasuredParagraph recycle) {
        MeasuredParagraph mt = recycle == null ? obtain() : recycle;
        mt.resetAndAnalyzeBidi(text, start, end, textDir);
        return mt;
    }

    public static MeasuredParagraph buildForMeasurement(
            TextPaint paint,
            CharSequence text,
            int start,
            int end,
            TextDirectionHeuristic textDir,
            MeasuredParagraph recycle) {
        MeasuredParagraph mt = recycle == null ? obtain() : recycle;
        mt.resetAndAnalyzeBidi(text, start, end, textDir);
        mt.mWidths.resize(mt.mTextLength);
        if (mt.mTextLength == 0) {
            return mt;
        }
        if (mt.mSpanned == null) {
            mt.applyMetricsAffectingSpan(paint, null, null, null, start, end, null, null);
            return mt;
        }
        int spanStart = start;
        while (spanStart < end) {
            int maSpanEnd =
                    mt.mSpanned.nextSpanTransition(spanStart, end, MetricAffectingSpan.class);
            int lbcSpanEnd =
                    mt.mSpanned.nextSpanTransition(spanStart, end, LineBreakConfigSpan.class);
            int spanEnd = Math.min(maSpanEnd, lbcSpanEnd);
            MetricAffectingSpan[] spans =
                    (MetricAffectingSpan[])
                            mt.mSpanned.getSpans(spanStart, spanEnd, MetricAffectingSpan.class);
            LineBreakConfigSpan[] lbcSpans =
                    (LineBreakConfigSpan[])
                            mt.mSpanned.getSpans(spanStart, spanEnd, LineBreakConfigSpan.class);
            mt.applyMetricsAffectingSpan(
                    paint,
                    null,
                    (MetricAffectingSpan[])
                            TextUtils.removeEmptySpans(
                                    spans, mt.mSpanned, MetricAffectingSpan.class),
                    (LineBreakConfigSpan[])
                            TextUtils.removeEmptySpans(
                                    lbcSpans, mt.mSpanned, LineBreakConfigSpan.class),
                    spanStart,
                    spanEnd,
                    null,
                    null);
            spanStart = spanEnd;
            mt = mt;
        }
        return mt;
    }

    public static MeasuredParagraph buildForStaticLayout(
            TextPaint paint,
            LineBreakConfig lineBreakConfig,
            CharSequence text,
            int start,
            int end,
            TextDirectionHeuristic textDir,
            int hyphenationMode,
            boolean computeLayout,
            boolean computeBounds,
            MeasuredParagraph hint,
            MeasuredParagraph recycle) {
        return buildForStaticLayoutInternal(
                paint,
                lineBreakConfig,
                text,
                start,
                end,
                textDir,
                hyphenationMode,
                computeLayout,
                computeBounds,
                hint,
                recycle,
                null);
    }

    public static MeasuredParagraph buildForStaticLayoutTest(
            TextPaint paint,
            LineBreakConfig lineBreakConfig,
            CharSequence text,
            int start,
            int end,
            TextDirectionHeuristic textDir,
            int hyphenationMode,
            boolean computeLayout,
            StyleRunCallback testCallback) {
        return buildForStaticLayoutInternal(
                paint,
                lineBreakConfig,
                text,
                start,
                end,
                textDir,
                hyphenationMode,
                computeLayout,
                false,
                null,
                null,
                testCallback);
    }

    private static MeasuredParagraph buildForStaticLayoutInternal(
            TextPaint paint,
            LineBreakConfig lineBreakConfig,
            CharSequence text,
            int start,
            int end,
            TextDirectionHeuristic textDir,
            int hyphenationMode,
            boolean computeLayout,
            boolean computeBounds,
            MeasuredParagraph hint,
            MeasuredParagraph recycle,
            StyleRunCallback testCallback) {
        MeasuredParagraph mt;
        MeasuredParagraph mt2 = recycle == null ? obtain() : recycle;
        mt2.resetAndAnalyzeBidi(text, start, end, textDir);
        MeasuredText.Builder builder =
                hint == null
                        ? new MeasuredText.Builder(mt2.mCopiedBuffer)
                                .setComputeHyphenation(hyphenationMode)
                                .setComputeLayout(computeLayout)
                                .setComputeBounds(computeBounds)
                        : new MeasuredText.Builder(hint.mMeasuredText);
        if (mt2.mTextLength == 0) {
            mt2.mMeasuredText = builder.build();
            return mt2;
        }
        if (mt2.mSpanned == null) {
            mt2.applyMetricsAffectingSpan(
                    paint, lineBreakConfig, null, null, start, end, builder, testCallback);
            mt2.mSpanEndCache.append(end);
            mt = mt2;
        } else {
            int spanStart = start;
            while (spanStart < end) {
                int maSpanEnd =
                        mt2.mSpanned.nextSpanTransition(spanStart, end, MetricAffectingSpan.class);
                int lbcSpanEnd =
                        mt2.mSpanned.nextSpanTransition(spanStart, end, LineBreakConfigSpan.class);
                int spanEnd = Math.min(maSpanEnd, lbcSpanEnd);
                MetricAffectingSpan[] spans =
                        (MetricAffectingSpan[])
                                mt2.mSpanned.getSpans(
                                        spanStart, spanEnd, MetricAffectingSpan.class);
                LineBreakConfigSpan[] lbcSpans =
                        (LineBreakConfigSpan[])
                                mt2.mSpanned.getSpans(
                                        spanStart, spanEnd, LineBreakConfigSpan.class);
                MeasuredParagraph mt3 = mt2;
                mt2.applyMetricsAffectingSpan(
                        paint,
                        lineBreakConfig,
                        (MetricAffectingSpan[])
                                TextUtils.removeEmptySpans(
                                        spans, mt2.mSpanned, MetricAffectingSpan.class),
                        (LineBreakConfigSpan[])
                                TextUtils.removeEmptySpans(
                                        lbcSpans, mt2.mSpanned, LineBreakConfigSpan.class),
                        spanStart,
                        spanEnd,
                        builder,
                        testCallback);
                mt3.mSpanEndCache.append(spanEnd);
                spanStart = spanEnd;
                mt2 = mt3;
            }
            mt = mt2;
        }
        mt.mMeasuredText = builder.build();
        return mt;
    }

    private void resetAndAnalyzeBidi(
            CharSequence charSequence,
            int i,
            int i2,
            TextDirectionHeuristic textDirectionHeuristic) {
        int i3;
        int i4;
        reset();
        this.mSpanned = charSequence instanceof Spanned ? (Spanned) charSequence : null;
        this.mTextStart = i;
        this.mTextLength = i2 - i;
        if (this.mCopiedBuffer == null || this.mCopiedBuffer.length != this.mTextLength) {
            this.mCopiedBuffer = new char[this.mTextLength];
        }
        TextUtils.getChars(charSequence, i, i2, this.mCopiedBuffer, 0);
        if (this.mSpanned != null) {
            ReplacementSpan[] replacementSpanArr =
                    (ReplacementSpan[]) this.mSpanned.getSpans(i, i2, ReplacementSpan.class);
            for (int i5 = 0; i5 < replacementSpanArr.length; i5++) {
                int spanStart = this.mSpanned.getSpanStart(replacementSpanArr[i5]) - i;
                int spanEnd = this.mSpanned.getSpanEnd(replacementSpanArr[i5]) - i;
                if (spanStart < 0) {
                    spanStart = 0;
                }
                if (spanEnd > this.mTextLength) {
                    spanEnd = this.mTextLength;
                }
                Arrays.fill(this.mCopiedBuffer, spanStart, spanEnd, OBJECT_REPLACEMENT_CHARACTER);
            }
        }
        if (ClientFlags.icuBidiMigration()) {
            if ((textDirectionHeuristic == TextDirectionHeuristics.LTR
                            || textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR
                            || textDirectionHeuristic == TextDirectionHeuristics.ANYRTL_LTR)
                    && TextUtils.doesNotNeedBidi(this.mCopiedBuffer, 0, this.mTextLength)) {
                this.mLevels.clear();
                this.mLtrWithoutBidi = true;
                return;
            }
            if (textDirectionHeuristic == TextDirectionHeuristics.LTR) {
                i4 = 0;
            } else if (textDirectionHeuristic == TextDirectionHeuristics.RTL) {
                i4 = 1;
            } else if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
                i4 = 126;
            } else if (textDirectionHeuristic != TextDirectionHeuristics.FIRSTSTRONG_RTL) {
                i4 = textDirectionHeuristic.isRtl(this.mCopiedBuffer, 0, this.mTextLength);
            } else {
                i4 = 127;
            }
            this.mBidi = new Bidi(this.mCopiedBuffer, 0, null, 0, this.mCopiedBuffer.length, i4);
            if (this.mCopiedBuffer.length > 0
                    && this.mBidi.getParagraphIndex(this.mCopiedBuffer.length - 1) != 0) {
                for (int i6 = 0; i6 < this.mTextLength; i6++) {
                    if (!Character.isSurrogate(this.mCopiedBuffer[i6])
                            && UCharacter.getDirection(this.mCopiedBuffer[i6]) == 7) {
                        this.mCopiedBuffer[i6] = OBJECT_REPLACEMENT_CHARACTER;
                    }
                }
                this.mBidi =
                        new Bidi(this.mCopiedBuffer, 0, null, 0, this.mCopiedBuffer.length, i4);
            }
            this.mLevels.resize(this.mTextLength);
            byte[] rawArray = this.mLevels.getRawArray();
            for (int i7 = 0; i7 < this.mTextLength; i7++) {
                rawArray[i7] = this.mBidi.getLevelAt(i7);
            }
            this.mLtrWithoutBidi = false;
            return;
        }
        if ((textDirectionHeuristic == TextDirectionHeuristics.LTR
                        || textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR
                        || textDirectionHeuristic == TextDirectionHeuristics.ANYRTL_LTR)
                && TextUtils.doesNotNeedBidi(this.mCopiedBuffer, 0, this.mTextLength)) {
            this.mLevels.clear();
            this.mParaDir = 1;
            this.mLtrWithoutBidi = true;
            return;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LTR) {
            i3 = 1;
        } else if (textDirectionHeuristic == TextDirectionHeuristics.RTL) {
            i3 = -1;
        } else if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            i3 = 2;
        } else if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL) {
            i3 = -2;
        } else {
            i3 = textDirectionHeuristic.isRtl(this.mCopiedBuffer, 0, this.mTextLength) ? -1 : 1;
        }
        this.mLevels.resize(this.mTextLength);
        this.mParaDir = AndroidBidi.bidi(i3, this.mCopiedBuffer, this.mLevels.getRawArray());
        this.mLtrWithoutBidi = false;
    }

    private void applyReplacementRun(
            ReplacementSpan replacement,
            int start,
            int end,
            TextPaint paint,
            MeasuredText.Builder builder,
            StyleRunCallback testCallback) {
        float width =
                replacement.getSize(
                        paint,
                        this.mSpanned,
                        start + this.mTextStart,
                        end + this.mTextStart,
                        this.mCachedFm);
        if (builder == null) {
            this.mWidths.set(start, width);
            if (end > start + 1) {
                Arrays.fill(this.mWidths.getRawArray(), start + 1, end, 0.0f);
            }
            this.mWholeWidth += width;
        } else {
            builder.appendReplacementRun(paint, end - start, width);
        }
        if (testCallback != null) {
            testCallback.onAppendReplacementRun(paint, end - start, width);
        }
    }

    private void applyStyleRun(
            int start,
            int end,
            TextPaint paint,
            LineBreakConfig config,
            MeasuredText.Builder builder,
            StyleRunCallback testCallback) {
        int levelEnd;
        boolean z;
        boolean isRtl;
        StyleRunCallback styleRunCallback;
        int i;
        int levelEnd2;
        int oldFlag;
        LineBreakConfig lineBreakConfig;
        boolean z2;
        int oldFlag2;
        int i2 = end;
        if (this.mLtrWithoutBidi) {
            if (builder == null) {
                int oldFlag3 = paint.getFlags();
                paint.setFlags(paint.getFlags() | 24576);
                try {
                    oldFlag2 = oldFlag3;
                    try {
                        this.mWholeWidth +=
                                paint.getTextRunAdvances(
                                        this.mCopiedBuffer,
                                        start,
                                        i2 - start,
                                        start,
                                        i2 - start,
                                        false,
                                        this.mWidths.getRawArray(),
                                        start);
                        paint.setFlags(oldFlag2);
                        lineBreakConfig = config;
                        z2 = false;
                    } catch (Throwable th) {
                        th = th;
                        paint.setFlags(oldFlag2);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    oldFlag2 = oldFlag3;
                }
            } else {
                lineBreakConfig = config;
                z2 = false;
                builder.appendStyleRun(paint, lineBreakConfig, i2 - start, false);
            }
            if (testCallback != null) {
                testCallback.onAppendStyleRun(paint, lineBreakConfig, i2 - start, z2);
                return;
            }
            return;
        }
        boolean z3 = false;
        StyleRunCallback styleRunCallback2 = testCallback;
        byte level = this.mLevels.get(start);
        byte level2 = level;
        int levelStart = start;
        int levelEnd3 = start + 1;
        while (true) {
            if (levelEnd3 == i2 || this.mLevels.get(levelEnd3) != level2) {
                boolean isRtl2 = (level2 & 1) != 0 ? true : z3;
                if (builder == null) {
                    int levelLength = levelEnd3 - levelStart;
                    int oldFlag4 = paint.getFlags();
                    paint.setFlags(paint.getFlags() | 24576);
                    try {
                        oldFlag = oldFlag4;
                        levelEnd = levelEnd3;
                        z = z3;
                    } catch (Throwable th3) {
                        th = th3;
                        oldFlag = oldFlag4;
                    }
                    try {
                        this.mWholeWidth +=
                                paint.getTextRunAdvances(
                                        this.mCopiedBuffer,
                                        levelStart,
                                        levelLength,
                                        levelStart,
                                        levelLength,
                                        isRtl2,
                                        this.mWidths.getRawArray(),
                                        levelStart);
                        paint.setFlags(oldFlag);
                        isRtl = isRtl2;
                    } catch (Throwable th4) {
                        th = th4;
                        paint.setFlags(oldFlag);
                        throw th;
                    }
                } else {
                    levelEnd = levelEnd3;
                    z = z3;
                    isRtl = isRtl2;
                    builder.appendStyleRun(paint, config, levelEnd - levelStart, isRtl);
                }
                styleRunCallback = testCallback;
                if (styleRunCallback != null) {
                    styleRunCallback.onAppendStyleRun(paint, config, levelEnd - levelStart, isRtl);
                }
                i = end;
                levelEnd2 = levelEnd;
                if (levelEnd2 != i) {
                    levelStart = levelEnd2;
                    level2 = this.mLevels.get(levelEnd2);
                } else {
                    return;
                }
            } else {
                levelEnd2 = levelEnd3;
                styleRunCallback = styleRunCallback2;
                z = z3;
                i = i2;
            }
            levelEnd3 = levelEnd2 + 1;
            styleRunCallback2 = styleRunCallback;
            i2 = i;
            z3 = z;
        }
    }

    private void applyMetricsAffectingSpan(
            TextPaint paint,
            LineBreakConfig lineBreakConfig,
            MetricAffectingSpan[] spans,
            LineBreakConfigSpan[] lbcSpans,
            int start,
            int end,
            MeasuredText.Builder builder,
            StyleRunCallback testCallback) {
        ReplacementSpan replacement;
        LineBreakConfig lineBreakConfig2;
        this.mCachedPaint.set(paint);
        this.mCachedPaint.baselineShift = 0;
        boolean needFontMetrics = builder != null;
        if (needFontMetrics && this.mCachedFm == null) {
            this.mCachedFm = new Paint.FontMetricsInt();
        }
        ReplacementSpan replacement2 = null;
        if (spans == null) {
            replacement = null;
        } else {
            for (MetricAffectingSpan span : spans) {
                if (span instanceof ReplacementSpan) {
                    replacement2 = (ReplacementSpan) span;
                } else {
                    span.updateMeasureState(this.mCachedPaint);
                }
            }
            replacement = replacement2;
        }
        if (lbcSpans == null) {
            lineBreakConfig2 = lineBreakConfig;
        } else {
            this.mLineBreakConfigBuilder.reset(lineBreakConfig);
            for (LineBreakConfigSpan lbcSpan : lbcSpans) {
                this.mLineBreakConfigBuilder.merge(lbcSpan.getLineBreakConfig());
            }
            lineBreakConfig2 = this.mLineBreakConfigBuilder.build();
        }
        int startInCopiedBuffer = start - this.mTextStart;
        int endInCopiedBuffer = end - this.mTextStart;
        if (builder != null) {
            this.mCachedPaint.getFontMetricsInt(this.mCachedFm);
        }
        if (replacement != null) {
            applyReplacementRun(
                    replacement,
                    startInCopiedBuffer,
                    endInCopiedBuffer,
                    this.mCachedPaint,
                    builder,
                    testCallback);
        } else {
            applyStyleRun(
                    startInCopiedBuffer,
                    endInCopiedBuffer,
                    this.mCachedPaint,
                    lineBreakConfig2,
                    builder,
                    testCallback);
        }
        if (needFontMetrics) {
            if (this.mCachedPaint.baselineShift < 0) {
                this.mCachedFm.ascent += this.mCachedPaint.baselineShift;
                this.mCachedFm.top += this.mCachedPaint.baselineShift;
            } else {
                this.mCachedFm.descent += this.mCachedPaint.baselineShift;
                this.mCachedFm.bottom += this.mCachedPaint.baselineShift;
            }
            this.mFontMetrics.append(this.mCachedFm.top);
            this.mFontMetrics.append(this.mCachedFm.bottom);
            this.mFontMetrics.append(this.mCachedFm.ascent);
            this.mFontMetrics.append(this.mCachedFm.descent);
        }
    }

    int breakText(int limit, boolean forwards, float width) {
        float[] w = this.mWidths.getRawArray();
        if (forwards) {
            int i = 0;
            while (i < limit) {
                width -= w[i];
                if (width < 0.0f) {
                    break;
                }
                i++;
            }
            while (i > 0 && this.mCopiedBuffer[i - 1] == ' ') {
                i--;
            }
            return i;
        }
        int i2 = limit - 1;
        while (i2 >= 0) {
            width -= w[i2];
            if (width < 0.0f) {
                break;
            }
            i2--;
        }
        while (i2 < limit - 1 && (this.mCopiedBuffer[i2 + 1] == ' ' || w[i2 + 1] == 0.0f)) {
            i2++;
        }
        return (limit - i2) - 1;
    }

    float measure(int start, int limit) {
        float width = 0.0f;
        float[] w = this.mWidths.getRawArray();
        for (int i = start; i < limit; i++) {
            width += w[i];
        }
        return width;
    }

    public int getMemoryUsage() {
        return this.mMeasuredText.getMemoryUsage();
    }
}
