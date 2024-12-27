package android.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.text.LineBreakConfig;
import android.text.style.ParagraphStyle;

public class BoringLayout extends Layout implements TextUtils.EllipsizeCallback {
    int mBottom;
    private int mBottomPadding;
    int mDesc;
    private String mDirect;
    private final RectF mDrawingBounds;
    private int mEllipsizedCount;
    private int mEllipsizedStart;
    private int mEllipsizedWidth;
    private float mMax;
    private Paint mPaint;
    private int mTopPadding;
    private boolean mUseFallbackLineSpacing;

    public static BoringLayout make(
            CharSequence source,
            TextPaint paint,
            int outerWidth,
            Layout.Alignment align,
            float spacingMult,
            float spacingAdd,
            Metrics metrics,
            boolean includePad) {
        return new BoringLayout(
                source, paint, outerWidth, align, spacingMult, spacingAdd, metrics, includePad);
    }

    public static BoringLayout make(
            CharSequence source,
            TextPaint paint,
            int outerWidth,
            Layout.Alignment align,
            float spacingmult,
            float spacingadd,
            Metrics metrics,
            boolean includePad,
            TextUtils.TruncateAt ellipsize,
            int ellipsizedWidth) {
        return new BoringLayout(
                source,
                paint,
                outerWidth,
                align,
                spacingmult,
                spacingadd,
                metrics,
                includePad,
                ellipsize,
                ellipsizedWidth);
    }

    public static BoringLayout make(
            CharSequence source,
            TextPaint paint,
            int outerWidth,
            Layout.Alignment align,
            Metrics metrics,
            boolean includePad,
            TextUtils.TruncateAt ellipsize,
            int ellipsizedWidth,
            boolean useFallbackLineSpacing) {
        return new BoringLayout(
                source,
                paint,
                outerWidth,
                align,
                1.0f,
                0.0f,
                metrics,
                includePad,
                ellipsize,
                ellipsizedWidth,
                useFallbackLineSpacing);
    }

    public BoringLayout replaceOrMake(
            CharSequence source,
            TextPaint paint,
            int outerwidth,
            Layout.Alignment align,
            float spacingMult,
            float spacingAdd,
            Metrics metrics,
            boolean includePad) {
        replaceWith(source, paint, outerwidth, align, spacingMult, spacingAdd);
        this.mEllipsizedWidth = outerwidth;
        this.mEllipsizedStart = 0;
        this.mEllipsizedCount = 0;
        this.mUseFallbackLineSpacing = false;
        init(source, paint, align, metrics, includePad, true, false);
        return this;
    }

    public BoringLayout replaceOrMake(
            CharSequence source,
            TextPaint paint,
            int outerWidth,
            Layout.Alignment align,
            Metrics metrics,
            boolean includePad,
            TextUtils.TruncateAt ellipsize,
            int ellipsizedWidth,
            boolean useFallbackLineSpacing) {
        return replaceOrMake(
                source,
                paint,
                outerWidth,
                align,
                1.0f,
                0.0f,
                metrics,
                includePad,
                ellipsize,
                ellipsizedWidth,
                useFallbackLineSpacing,
                false,
                null);
    }

    public BoringLayout replaceOrMake(
            CharSequence source,
            TextPaint paint,
            int outerWidth,
            Layout.Alignment align,
            float spacingMultiplier,
            float spacingAmount,
            Metrics metrics,
            boolean includePad,
            TextUtils.TruncateAt ellipsize,
            int ellipsizedWidth,
            boolean useFallbackLineSpacing,
            boolean useBoundsForWidth,
            Paint.FontMetrics minimumFontMetrics) {
        boolean trust;
        if (ellipsize == null || ellipsize == TextUtils.TruncateAt.MARQUEE) {
            replaceWith(source, paint, outerWidth, align, 1.0f, 0.0f);
            this.mEllipsizedWidth = outerWidth;
            this.mEllipsizedStart = 0;
            this.mEllipsizedCount = 0;
            trust = true;
        } else {
            replaceWith(
                    TextUtils.ellipsize(source, paint, ellipsizedWidth, ellipsize, true, this),
                    paint,
                    outerWidth,
                    align,
                    spacingMultiplier,
                    spacingAmount);
            this.mEllipsizedWidth = ellipsizedWidth;
            trust = false;
        }
        this.mUseFallbackLineSpacing = useFallbackLineSpacing;
        init(getText(), paint, align, metrics, includePad, trust, useFallbackLineSpacing);
        return this;
    }

    public BoringLayout replaceOrMake(
            CharSequence source,
            TextPaint paint,
            int outerWidth,
            Layout.Alignment align,
            float spacingMult,
            float spacingAdd,
            Metrics metrics,
            boolean includePad,
            TextUtils.TruncateAt ellipsize,
            int ellipsizedWidth) {
        return replaceOrMake(
                source,
                paint,
                outerWidth,
                align,
                metrics,
                includePad,
                ellipsize,
                ellipsizedWidth,
                false);
    }

    public BoringLayout(
            CharSequence source,
            TextPaint paint,
            int outerwidth,
            Layout.Alignment align,
            float spacingMult,
            float spacingAdd,
            Metrics metrics,
            boolean includePad) {
        super(
                source,
                paint,
                outerwidth,
                align,
                TextDirectionHeuristics.LTR,
                spacingMult,
                spacingAdd,
                includePad,
                false,
                outerwidth,
                null,
                1,
                0,
                0,
                null,
                null,
                0,
                LineBreakConfig.NONE,
                false,
                false,
                null);
        this.mDrawingBounds = new RectF();
        this.mEllipsizedWidth = outerwidth;
        this.mEllipsizedStart = 0;
        this.mEllipsizedCount = 0;
        this.mUseFallbackLineSpacing = false;
        init(source, paint, align, metrics, includePad, true, false);
    }

    public BoringLayout(
            CharSequence source,
            TextPaint paint,
            int outerWidth,
            Layout.Alignment align,
            float spacingMult,
            float spacingAdd,
            Metrics metrics,
            boolean includePad,
            TextUtils.TruncateAt ellipsize,
            int ellipsizedWidth) {
        this(
                source,
                paint,
                outerWidth,
                align,
                spacingMult,
                spacingAdd,
                metrics,
                includePad,
                ellipsize,
                ellipsizedWidth,
                false);
    }

    public BoringLayout(
            CharSequence source,
            TextPaint paint,
            int outerWidth,
            Layout.Alignment align,
            float spacingMult,
            float spacingAdd,
            Metrics metrics,
            boolean includePad,
            TextUtils.TruncateAt ellipsize,
            int ellipsizedWidth,
            boolean useFallbackLineSpacing) {
        this(
                source,
                paint,
                outerWidth,
                align,
                TextDirectionHeuristics.LTR,
                spacingMult,
                spacingAdd,
                includePad,
                useFallbackLineSpacing,
                ellipsizedWidth,
                ellipsize,
                1,
                0,
                0,
                null,
                null,
                0,
                LineBreakConfig.NONE,
                metrics,
                false,
                false,
                null);
    }

    public BoringLayout(
            CharSequence text,
            TextPaint paint,
            int width,
            Layout.Alignment align,
            float spacingMult,
            float spacingAdd,
            boolean includePad,
            boolean fallbackLineSpacing,
            int ellipsizedWidth,
            TextUtils.TruncateAt ellipsize,
            Metrics metrics,
            boolean useBoundsForWidth,
            boolean shiftDrawingOffsetForStartOverhang,
            Paint.FontMetrics minimumFontMetrics) {
        this(
                text,
                paint,
                width,
                align,
                TextDirectionHeuristics.LTR,
                spacingMult,
                spacingAdd,
                includePad,
                fallbackLineSpacing,
                ellipsizedWidth,
                ellipsize,
                1,
                0,
                0,
                null,
                null,
                0,
                LineBreakConfig.NONE,
                metrics,
                useBoundsForWidth,
                shiftDrawingOffsetForStartOverhang,
                minimumFontMetrics);
    }

    BoringLayout(
            CharSequence text,
            TextPaint paint,
            int width,
            Layout.Alignment align,
            TextDirectionHeuristic textDir,
            float spacingMult,
            float spacingAdd,
            boolean includePad,
            boolean fallbackLineSpacing,
            int ellipsizedWidth,
            TextUtils.TruncateAt ellipsize,
            int maxLines,
            int breakStrategy,
            int hyphenationFrequency,
            int[] leftIndents,
            int[] rightIndents,
            int justificationMode,
            LineBreakConfig lineBreakConfig,
            Metrics metrics,
            boolean useBoundsForWidth,
            boolean shiftDrawingOffsetForStartOverhang,
            Paint.FontMetrics minimumFontMetrics) {
        super(
                text,
                paint,
                width,
                align,
                textDir,
                spacingMult,
                spacingAdd,
                includePad,
                fallbackLineSpacing,
                ellipsizedWidth,
                ellipsize,
                maxLines,
                breakStrategy,
                hyphenationFrequency,
                leftIndents,
                rightIndents,
                justificationMode,
                lineBreakConfig,
                useBoundsForWidth,
                shiftDrawingOffsetForStartOverhang,
                minimumFontMetrics);
        boolean trust;
        this.mDrawingBounds = new RectF();
        if (ellipsize != null && ellipsize != TextUtils.TruncateAt.MARQUEE) {
            replaceWith(
                    TextUtils.ellipsize(text, paint, ellipsizedWidth, ellipsize, true, this),
                    paint,
                    width,
                    align,
                    spacingMult,
                    spacingAdd);
            this.mEllipsizedWidth = ellipsizedWidth;
            trust = false;
            this.mUseFallbackLineSpacing = fallbackLineSpacing;
            init(getText(), paint, align, metrics, includePad, trust, fallbackLineSpacing);
        }
        this.mEllipsizedWidth = width;
        this.mEllipsizedStart = 0;
        this.mEllipsizedCount = 0;
        trust = true;
        this.mUseFallbackLineSpacing = fallbackLineSpacing;
        init(getText(), paint, align, metrics, includePad, trust, fallbackLineSpacing);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void init(
            java.lang.CharSequence r22,
            android.text.TextPaint r23,
            android.text.Layout.Alignment r24,
            android.text.BoringLayout.Metrics r25,
            boolean r26,
            boolean r27,
            boolean r28) {
        /*
            r21 = this;
            r0 = r21
            r1 = r25
            r14 = r22
            boolean r2 = r14 instanceof java.lang.String
            r15 = 0
            if (r2 == 0) goto L18
            android.text.Layout$Alignment r2 = android.text.Layout.Alignment.ALIGN_NORMAL
            r13 = r24
            if (r13 != r2) goto L1a
            java.lang.String r2 = r22.toString()
            r0.mDirect = r2
            goto L1c
        L18:
            r13 = r24
        L1a:
            r0.mDirect = r15
        L1c:
            r12 = r23
            r0.mPaint = r12
            if (r26 == 0) goto L2d
            int r2 = r1.bottom
            int r3 = r1.top
            int r2 = r2 - r3
            int r3 = r1.bottom
            r0.mDesc = r3
            r11 = r2
            goto L37
        L2d:
            int r2 = r1.descent
            int r3 = r1.ascent
            int r2 = r2 - r3
            int r3 = r1.descent
            r0.mDesc = r3
            r11 = r2
        L37:
            r0.mBottom = r11
            if (r27 == 0) goto L43
            int r2 = r1.width
            float r2 = (float) r2
            r0.mMax = r2
            r17 = r11
            goto L86
        L43:
            android.text.TextLine r10 = android.text.TextLine.obtain()
            int r6 = r22.length()
            android.text.Layout$Directions r8 = android.text.Layout.DIRS_ALL_LEFT_TO_RIGHT
            int r9 = r0.mEllipsizedStart
            int r2 = r0.mEllipsizedStart
            int r3 = r0.mEllipsizedCount
            int r16 = r2 + r3
            r5 = 0
            r7 = 1
            r17 = 0
            r18 = 0
            r2 = r10
            r3 = r23
            r4 = r22
            r19 = r9
            r9 = r17
            r20 = r10
            r10 = r18
            r17 = r11
            r11 = r19
            r12 = r16
            r13 = r28
            r2.set(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            r2 = 0
            r3 = r20
            float r2 = r3.metrics(r15, r15, r2, r15)
            double r4 = (double) r2
            double r4 = java.lang.Math.ceil(r4)
            int r2 = (int) r4
            float r2 = (float) r2
            r0.mMax = r2
            android.text.TextLine.recycle(r3)
        L86:
            if (r26 == 0) goto L96
            int r2 = r1.top
            int r3 = r1.ascent
            int r2 = r2 - r3
            r0.mTopPadding = r2
            int r2 = r1.bottom
            int r3 = r1.descent
            int r2 = r2 - r3
            r0.mBottomPadding = r2
        L96:
            android.graphics.RectF r2 = r0.mDrawingBounds
            android.graphics.RectF r3 = android.text.BoringLayout.Metrics.m5107$$Nest$fgetmDrawingBounds(r25)
            r2.set(r3)
            android.graphics.RectF r2 = r0.mDrawingBounds
            int r3 = r0.mBottom
            int r4 = r0.mDesc
            int r3 = r3 - r4
            float r3 = (float) r3
            r4 = 0
            r2.offset(r4, r3)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.text.BoringLayout.init(java.lang.CharSequence,"
                    + " android.text.TextPaint, android.text.Layout$Alignment,"
                    + " android.text.BoringLayout$Metrics, boolean, boolean, boolean):void");
    }

    public static Metrics isBoring(CharSequence text, TextPaint paint) {
        return isBoring(text, paint, TextDirectionHeuristics.FIRSTSTRONG_LTR, null);
    }

    public static Metrics isBoring(CharSequence text, TextPaint paint, Metrics metrics) {
        return isBoring(text, paint, TextDirectionHeuristics.FIRSTSTRONG_LTR, metrics);
    }

    private static boolean hasAnyInterestingChars(CharSequence text, int textLength) {
        char[] buffer = TextUtils.obtain(500);
        for (int start = 0; start < textLength; start += 500) {
            try {
                int end = Math.min(start + 500, textLength);
                TextUtils.getChars(text, start, end, buffer, 0);
                int len = end - start;
                for (int i = 0; i < len; i++) {
                    char c = buffer[i];
                    if (c == '\n' || c == '\t' || TextUtils.couldAffectRtl(c)) {
                        TextUtils.recycle(buffer);
                        return true;
                    }
                }
            } finally {
                TextUtils.recycle(buffer);
            }
        }
        return false;
    }

    public static Metrics isBoring(
            CharSequence text, TextPaint paint, TextDirectionHeuristic textDir, Metrics metrics) {
        return isBoring(text, paint, textDir, false, metrics);
    }

    public static Metrics isBoring(
            CharSequence text,
            TextPaint paint,
            TextDirectionHeuristic textDir,
            boolean useFallbackLineSpacing,
            Metrics metrics) {
        return isBoring(text, paint, textDir, useFallbackLineSpacing, null, metrics);
    }

    public static Metrics isBoring(
            CharSequence text,
            TextPaint paint,
            TextDirectionHeuristic textDir,
            boolean useFallbackLineSpacing,
            Paint.FontMetrics minimumFontMetrics,
            Metrics metrics) {
        Metrics fm;
        int textLength = text.length();
        if (hasAnyInterestingChars(text, textLength)) {
            return null;
        }
        if (textDir != null && textDir.isRtl(text, 0, textLength)) {
            return null;
        }
        if (text instanceof Spanned) {
            Spanned sp = (Spanned) text;
            Object[] styles = sp.getSpans(0, textLength, ParagraphStyle.class);
            if (styles.length > 0) {
                return null;
            }
        }
        if (metrics == null) {
            Metrics fm2 = new Metrics();
            fm = fm2;
        } else {
            metrics.reset();
            fm = metrics;
        }
        paint.set(paint);
        if (ClientFlags.fixLineHeightForLocale() && minimumFontMetrics != null) {
            fm.set(minimumFontMetrics);
            fm.top = Math.min(fm.top, fm.ascent);
            fm.bottom = Math.max(fm.bottom, fm.descent);
        }
        TextLine line = TextLine.obtain();
        Metrics fm3 = fm;
        line.set(
                paint,
                text,
                0,
                textLength,
                1,
                Layout.DIRS_ALL_LEFT_TO_RIGHT,
                false,
                null,
                0,
                0,
                useFallbackLineSpacing);
        fm3.width = (int) Math.ceil(line.metrics(fm3, fm3.mDrawingBounds, false, null));
        TextLine.recycle(line);
        return fm3;
    }

    @Override // android.text.Layout
    public int getHeight() {
        return this.mBottom;
    }

    @Override // android.text.Layout
    public int getLineCount() {
        return 1;
    }

    @Override // android.text.Layout
    public int getLineTop(int line) {
        if (line == 0) {
            return 0;
        }
        return this.mBottom;
    }

    @Override // android.text.Layout
    public int getLineDescent(int line) {
        return this.mDesc;
    }

    @Override // android.text.Layout
    public int getLineStart(int line) {
        if (line == 0) {
            return 0;
        }
        return getText().length();
    }

    @Override // android.text.Layout
    public int getParagraphDirection(int line) {
        return 1;
    }

    @Override // android.text.Layout
    public boolean getLineContainsTab(int line) {
        return false;
    }

    @Override // android.text.Layout
    public float getLineMax(int line) {
        if (getUseBoundsForWidth()) {
            return super.getLineMax(line);
        }
        return this.mMax;
    }

    @Override // android.text.Layout
    public float getLineWidth(int line) {
        if (getUseBoundsForWidth()) {
            return super.getLineWidth(line);
        }
        if (line == 0) {
            return this.mMax;
        }
        return 0.0f;
    }

    @Override // android.text.Layout
    public final Layout.Directions getLineDirections(int line) {
        return Layout.DIRS_ALL_LEFT_TO_RIGHT;
    }

    @Override // android.text.Layout
    public int getTopPadding() {
        return this.mTopPadding;
    }

    @Override // android.text.Layout
    public int getBottomPadding() {
        return this.mBottomPadding;
    }

    @Override // android.text.Layout
    public int getEllipsisCount(int line) {
        return this.mEllipsizedCount;
    }

    @Override // android.text.Layout
    public int getEllipsisStart(int line) {
        return this.mEllipsizedStart;
    }

    @Override // android.text.Layout
    public int getEllipsizedWidth() {
        return this.mEllipsizedWidth;
    }

    @Override // android.text.Layout
    public boolean isFallbackLineSpacingEnabled() {
        return this.mUseFallbackLineSpacing;
    }

    @Override // android.text.Layout
    public RectF computeDrawingBoundingBox() {
        return this.mDrawingBounds;
    }

    @Override // android.text.Layout
    public void draw(Canvas c, Path highlight, Paint highlightpaint, int cursorOffset) {
        if (this.mDirect != null && highlight == null) {
            float leftShift = 0.0f;
            if (getUseBoundsForWidth() && getShiftDrawingOffsetForStartOverhang()) {
                RectF drawingRect = computeDrawingBoundingBox();
                if (drawingRect.left < 0.0f) {
                    leftShift = -drawingRect.left;
                    c.translate(leftShift, 0.0f);
                }
            }
            c.drawText(this.mDirect, 0.0f, this.mBottom - this.mDesc, this.mPaint);
            if (leftShift != 0.0f) {
                c.translate(-leftShift, 0.0f);
                return;
            }
            return;
        }
        super.draw(c, highlight, highlightpaint, cursorOffset);
    }

    @Override // android.text.TextUtils.EllipsizeCallback
    public void ellipsized(int start, int end) {
        this.mEllipsizedStart = start;
        this.mEllipsizedCount = end - start;
    }

    public static class Metrics extends Paint.FontMetricsInt {
        private final RectF mDrawingBounds = new RectF();
        public int width;

        public RectF getDrawingBoundingBox() {
            return this.mDrawingBounds;
        }

        @Override // android.graphics.Paint.FontMetricsInt
        public String toString() {
            return super.toString()
                    + " width="
                    + this.width
                    + ", drawingBounds = "
                    + this.mDrawingBounds;
        }

        public void reset() {
            this.top = 0;
            this.bottom = 0;
            this.ascent = 0;
            this.descent = 0;
            this.width = 0;
            this.leading = 0;
            this.mDrawingBounds.setEmpty();
        }
    }
}
