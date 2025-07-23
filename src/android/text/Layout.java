package android.text;

import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.text.LineBreakConfig;
import android.text.BoringLayout;
import android.text.StaticLayout;
import android.text.TextLine;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.text.style.AlignmentSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.ParagraphStyle;
import android.text.style.ReplacementSpan;
import android.text.style.TabStopSpan;
import com.android.graphics.hwui.flags.Flags;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.util.ArrayUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class Layout {
    public static final int BREAK_STRATEGY_BALANCED = 2;
    public static final int BREAK_STRATEGY_HIGH_QUALITY = 1;
    public static final int BREAK_STRATEGY_SIMPLE = 0;
    public static final float DEFAULT_LINESPACING_ADDITION = 0.0f;
    public static final float DEFAULT_LINESPACING_MULTIPLIER = 1.0f;
    public static final int DIR_LEFT_TO_RIGHT = 1;
    static final int DIR_REQUEST_DEFAULT_LTR = 2;
    static final int DIR_REQUEST_DEFAULT_RTL = -2;
    static final int DIR_REQUEST_LTR = 1;
    static final int DIR_REQUEST_RTL = -1;
    public static final int DIR_RIGHT_TO_LEFT = -1;
    private static final float HIGH_CONTRAST_LAB_THRESHOLD_AOSP = 50.0f;
    private static final float HIGH_CONTRAST_LAB_THRESHOLD_SEC = 60.75f;
    private static final float HIGH_CONTRAST_TEXT_BACKGROUND_ALPHA_PERCENTAGE = 0.7f;
    static final float HIGH_CONTRAST_TEXT_BACKGROUND_CORNER_RADIUS_FACTOR = 0.5f;
    static final float HIGH_CONTRAST_TEXT_BACKGROUND_CORNER_RADIUS_MIN_DP = 5.0f;
    private static final float HIGH_CONTRAST_TEXT_BORDER_WIDTH_FACTOR = 0.0f;
    private static final float HIGH_CONTRAST_TEXT_BORDER_WIDTH_MIN_PX = 0.0f;
    public static final int HYPHENATION_FREQUENCY_FULL = 2;
    public static final int HYPHENATION_FREQUENCY_FULL_FAST = 4;
    public static final int HYPHENATION_FREQUENCY_NONE = 0;
    public static final int HYPHENATION_FREQUENCY_NORMAL = 1;
    public static final int HYPHENATION_FREQUENCY_NORMAL_FAST = 3;
    public static final int JUSTIFICATION_MODE_INTER_CHARACTER = 2;
    public static final int JUSTIFICATION_MODE_INTER_WORD = 1;
    public static final int JUSTIFICATION_MODE_NONE = 0;
    static final int RUN_LEVEL_MASK = 63;
    static final int RUN_LEVEL_SHIFT = 26;
    static final int RUN_RTL_FLAG = 67108864;
    private static final float TAB_INCREMENT = 20.0f;
    public static final int TEXT_SELECTION_LAYOUT_LEFT_TO_RIGHT = 1;
    public static final int TEXT_SELECTION_LAYOUT_RIGHT_TO_LEFT = 0;
    private Alignment mAlignment;
    private int mBreakStrategy;
    private TextUtils.TruncateAt mEllipsize;
    private int mEllipsizedWidth;
    private boolean mFallbackLineSpacing;
    private int mHyphenationFrequency;
    private boolean mIncludePad;
    private int mJustificationMode;
    private int[] mLeftIndents;
    private SpanSet<LineBackgroundSpan> mLineBackgroundSpans;
    private LineBreakConfig mLineBreakConfig;
    private TextLine.LineInfo mLineInfo;
    private int mMaxLines;
    private Paint.FontMetrics mMinimumFontMetrics;
    private TextPaint mPaint;
    private int[] mRightIndents;
    private boolean mShiftDrawingOffsetForStartOverhang;
    private float mSpacingAdd;
    private float mSpacingMult;
    private SpanColors mSpanColors;
    private boolean mSpannedText;
    private CharSequence mText;
    private TextDirectionHeuristic mTextDir;
    private boolean mUseBoundsForWidth;
    private int mWidth;
    private final TextPaint mWorkPaint;
    private final Paint mWorkPlainPaint;
    private static final ParagraphStyle[] NO_PARA_SPANS = (ParagraphStyle[]) ArrayUtils.emptyArray(ParagraphStyle.class);
    public static final TextInclusionStrategy INCLUSION_STRATEGY_ANY_OVERLAP = new TextInclusionStrategy() { // from class: android.text.Layout$$ExternalSyntheticLambda2
        @Override // android.text.Layout.TextInclusionStrategy
        public final boolean isSegmentInside(RectF rectF, RectF rectF2) {
            return RectF.intersects(rectF, rectF2);
        }
    };
    public static final TextInclusionStrategy INCLUSION_STRATEGY_CONTAINS_CENTER = new TextInclusionStrategy() { // from class: android.text.Layout$$ExternalSyntheticLambda3
        @Override // android.text.Layout.TextInclusionStrategy
        public final boolean isSegmentInside(RectF rectF, RectF rectF2) {
            boolean contains;
            contains = rectF2.contains(rectF.centerX(), rectF.centerY());
            return contains;
        }
    };
    public static final TextInclusionStrategy INCLUSION_STRATEGY_CONTAINS_ALL = new TextInclusionStrategy() { // from class: android.text.Layout$$ExternalSyntheticLambda4
        @Override // android.text.Layout.TextInclusionStrategy
        public final boolean isSegmentInside(RectF rectF, RectF rectF2) {
            boolean contains;
            contains = rectF2.contains(rectF);
            return contains;
        }
    };
    private static final Rect sTempRect = new Rect();
    static final int RUN_LENGTH_MASK = 67108863;
    public static final Directions DIRS_ALL_LEFT_TO_RIGHT = new Directions(new int[]{0, RUN_LENGTH_MASK});
    public static final Directions DIRS_ALL_RIGHT_TO_LEFT = new Directions(new int[]{0, 134217727});

    public enum Alignment {
        ALIGN_NORMAL,
        ALIGN_OPPOSITE,
        ALIGN_CENTER,
        ALIGN_LEFT,
        ALIGN_RIGHT
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BreakStrategy {
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface CharacterBoundsListener {
        void onCharacterBounds(int i, int i2, float f, float f2, float f3, float f4);

        default void onEnd() {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HyphenationFrequency {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface JustificationMode {
    }

    @FunctionalInterface
    public interface SelectionRectangleConsumer {
        void accept(float f, float f2, float f3, float f4, int i);
    }

    @FunctionalInterface
    public interface TextInclusionStrategy {
        boolean isSegmentInside(RectF rectF, RectF rectF2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextSelectionLayout {
    }

    public abstract int getBottomPadding();

    public abstract int getEllipsisCount(int i);

    public abstract int getEllipsisStart(int i);

    public int getEndHyphenEdit(int i) {
        return 0;
    }

    public int getIndentAdjust(int i, Alignment alignment) {
        return 0;
    }

    public abstract boolean getLineContainsTab(int i);

    public abstract int getLineCount();

    public abstract int getLineDescent(int i);

    public abstract Directions getLineDirections(int i);

    public int getLineExtra(int i) {
        return 0;
    }

    public abstract int getLineStart(int i);

    public abstract int getLineTop(int i);

    public abstract int getParagraphDirection(int i);

    public int getStartHyphenEdit(int i) {
        return 0;
    }

    public abstract int getTopPadding();

    public static float getDesiredWidth(CharSequence charSequence, TextPaint textPaint) {
        return getDesiredWidth(charSequence, 0, charSequence.length(), textPaint);
    }

    public static float getDesiredWidth(CharSequence charSequence, int i, int i2, TextPaint textPaint) {
        return getDesiredWidth(charSequence, i, i2, textPaint, TextDirectionHeuristics.FIRSTSTRONG_LTR);
    }

    public static float getDesiredWidth(CharSequence charSequence, int i, int i2, TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic) {
        return getDesiredWidthWithLimit(charSequence, i, i2, textPaint, textDirectionHeuristic, Float.MAX_VALUE, false);
    }

    public static float getDesiredWidthWithLimit(CharSequence charSequence, int i, int i2, TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, float f, boolean z) {
        textPaint.set(textPaint);
        float f2 = 0.0f;
        int i3 = i;
        while (i3 <= i2) {
            int indexOf = TextUtils.indexOf(charSequence, '\n', i3, i2);
            int i4 = indexOf < 0 ? i2 : indexOf;
            CharSequence charSequence2 = charSequence;
            TextPaint textPaint2 = textPaint;
            TextDirectionHeuristic textDirectionHeuristic2 = textDirectionHeuristic;
            boolean z2 = z;
            float measurePara = measurePara(textPaint2, charSequence2, i3, i4, textDirectionHeuristic2, z2);
            int i5 = i4;
            if (measurePara > f) {
                return f;
            }
            if (measurePara > f2) {
                f2 = measurePara;
            }
            i3 = i5 + 1;
            textPaint = textPaint2;
            charSequence = charSequence2;
            textDirectionHeuristic = textDirectionHeuristic2;
            z = z2;
        }
        return f2;
    }

    protected Layout(CharSequence charSequence, TextPaint textPaint, int i, Alignment alignment, float f, float f2) {
        this(charSequence, textPaint, i, alignment, TextDirectionHeuristics.FIRSTSTRONG_LTR, f, f2, false, false, 0, null, Integer.MAX_VALUE, 0, 0, null, null, 0, LineBreakConfig.NONE, false, false, null);
    }

    protected Layout(CharSequence charSequence, TextPaint textPaint, int i, Alignment alignment, TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean z, boolean z2, int i2, TextUtils.TruncateAt truncateAt, int i3, int i4, int i5, int[] iArr, int[] iArr2, int i6, LineBreakConfig lineBreakConfig, boolean z3, boolean z4, Paint.FontMetrics fontMetrics) {
        this.mWorkPaint = new TextPaint();
        this.mWorkPlainPaint = new Paint();
        this.mAlignment = Alignment.ALIGN_NORMAL;
        this.mLineInfo = null;
        if (i < 0) {
            throw new IllegalArgumentException("Layout: " + i + " < 0");
        }
        if (textPaint != null) {
            textPaint.bgColor = 0;
            textPaint.baselineShift = 0;
        }
        this.mText = charSequence;
        this.mPaint = textPaint;
        this.mWidth = i;
        this.mAlignment = alignment;
        this.mSpacingMult = f;
        this.mSpacingAdd = f2;
        this.mSpannedText = charSequence instanceof Spanned;
        this.mTextDir = textDirectionHeuristic;
        this.mIncludePad = z;
        this.mFallbackLineSpacing = z2;
        this.mEllipsizedWidth = truncateAt != null ? i2 : i;
        this.mEllipsize = truncateAt;
        this.mMaxLines = i3;
        this.mBreakStrategy = i4;
        this.mHyphenationFrequency = i5;
        this.mLeftIndents = iArr;
        this.mRightIndents = iArr2;
        this.mJustificationMode = i6;
        this.mLineBreakConfig = lineBreakConfig;
        this.mUseBoundsForWidth = z3;
        this.mShiftDrawingOffsetForStartOverhang = z4;
        this.mMinimumFontMetrics = fontMetrics;
        initSpanColors();
    }

    private void initSpanColors() {
        if (this.mSpannedText && Flags.highContrastTextSmallTextRect()) {
            SpanColors spanColors = this.mSpanColors;
            if (spanColors == null) {
                this.mSpanColors = new SpanColors();
                return;
            } else {
                spanColors.recycle();
                return;
            }
        }
        this.mSpanColors = null;
    }

    void replaceWith(CharSequence charSequence, TextPaint textPaint, int i, Alignment alignment, float f, float f2) {
        if (i < 0) {
            throw new IllegalArgumentException("Layout: " + i + " < 0");
        }
        this.mText = charSequence;
        this.mPaint = textPaint;
        this.mWidth = i;
        this.mAlignment = alignment;
        this.mSpacingMult = f;
        this.mSpacingAdd = f2;
        this.mSpannedText = charSequence instanceof Spanned;
        initSpanColors();
    }

    public void draw(Canvas canvas) {
        draw(canvas, null, null, 0);
    }

    public void draw(Canvas canvas, Path path, Paint paint, int i) {
        draw(canvas, null, null, path, paint, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void draw(android.graphics.Canvas r12, java.util.List<android.graphics.Path> r13, java.util.List<android.graphics.Paint> r14, android.graphics.Path r15, android.graphics.Paint r16, int r17) {
        /*
            r11 = this;
            boolean r2 = r11.mUseBoundsForWidth
            r9 = 0
            if (r2 == 0) goto L1b
            boolean r2 = r11.mShiftDrawingOffsetForStartOverhang
            if (r2 == 0) goto L1b
            android.graphics.RectF r2 = r11.computeDrawingBoundingBox()
            float r3 = r2.left
            int r3 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r3 >= 0) goto L1b
            float r2 = r2.left
            float r2 = -r2
            r12.translate(r2, r9)
            r10 = r2
            goto L1c
        L1b:
            r10 = r9
        L1c:
            long r2 = r11.getLineRangeForDraw(r12)
            int r7 = android.text.TextUtils.unpackRangeStartFromLong(r2)
            int r8 = android.text.TextUtils.unpackRangeEndFromLong(r2)
            if (r8 >= 0) goto L2b
            goto L5e
        L2b:
            boolean r2 = shouldDrawHighlightsOnTop(r12)
            if (r2 == 0) goto L35
            r11.drawBackground(r12, r7, r8)
            goto L41
        L35:
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            r5 = r16
            r6 = r17
            r0.drawWithoutText(r1, r2, r3, r4, r5, r6, r7, r8)
        L41:
            r11.drawText(r12, r7, r8)
            boolean r2 = shouldDrawHighlightsOnTop(r12)
            if (r2 == 0) goto L56
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            r5 = r16
            r6 = r17
            r0.drawHighlights(r1, r2, r3, r4, r5, r6, r7, r8)
        L56:
            int r0 = (r10 > r9 ? 1 : (r10 == r9 ? 0 : -1))
            if (r0 == 0) goto L5e
            float r0 = -r10
            r12.translate(r0, r9)
        L5e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.draw(android.graphics.Canvas, java.util.List, java.util.List, android.graphics.Path, android.graphics.Paint, int):void");
    }

    private static boolean shouldDrawHighlightsOnTop(Canvas canvas) {
        return Flags.highContrastTextSmallTextRect() && canvas.isHighContrastTextEnabled();
    }

    private static Paint setToHighlightPaint(Paint paint, BlendMode blendMode, Paint paint2) {
        if (paint == null) {
            return null;
        }
        paint2.set(paint);
        paint2.setBlendMode(blendMode);
        paint2.setColor(-256);
        return paint2;
    }

    public void drawText(Canvas canvas) {
        long lineRangeForDraw = getLineRangeForDraw(canvas);
        int unpackRangeStartFromLong = TextUtils.unpackRangeStartFromLong(lineRangeForDraw);
        int unpackRangeEndFromLong = TextUtils.unpackRangeEndFromLong(lineRangeForDraw);
        if (unpackRangeEndFromLong < 0) {
            return;
        }
        drawText(canvas, unpackRangeStartFromLong, unpackRangeEndFromLong);
    }

    public void drawBackground(Canvas canvas) {
        long lineRangeForDraw = getLineRangeForDraw(canvas);
        int unpackRangeStartFromLong = TextUtils.unpackRangeStartFromLong(lineRangeForDraw);
        int unpackRangeEndFromLong = TextUtils.unpackRangeEndFromLong(lineRangeForDraw);
        if (unpackRangeEndFromLong < 0) {
            return;
        }
        drawBackground(canvas, unpackRangeStartFromLong, unpackRangeEndFromLong);
    }

    public void drawWithoutText(Canvas canvas, List<Path> list, List<Paint> list2, Path path, Paint paint, int i, int i2, int i3) {
        drawBackground(canvas, i2, i3);
        drawHighlights(canvas, list, list2, path, paint, i, i2, i3);
    }

    public void drawHighlights(Canvas canvas, List<Path> list, List<Paint> list2, Path path, Paint paint, int i, int i2, int i3) {
        if (list == null && list2 == null) {
            return;
        }
        if (i != 0) {
            canvas.translate(0.0f, i);
        }
        try {
            BlendMode determineHighContrastHighlightBlendMode = determineHighContrastHighlightBlendMode(canvas);
            if (list != null) {
                if (list2 == null) {
                    throw new IllegalArgumentException("if highlight is specified, highlightPaint must be specified.");
                }
                if (list2.size() != list.size()) {
                    throw new IllegalArgumentException("The highlight path size is different from the size of highlight paints");
                }
                for (int i4 = 0; i4 < list.size(); i4++) {
                    Path path2 = list.get(i4);
                    Paint paint2 = list2.get(i4);
                    if (shouldDrawHighlightsOnTop(canvas)) {
                        paint2 = setToHighlightPaint(paint2, determineHighContrastHighlightBlendMode, this.mWorkPlainPaint);
                    }
                    if (path2 != null) {
                        canvas.drawPath(path2, paint2);
                    }
                }
            }
            if (path != null) {
                if (shouldDrawHighlightsOnTop(canvas)) {
                    paint = setToHighlightPaint(paint, determineHighContrastHighlightBlendMode, this.mWorkPlainPaint);
                }
                canvas.drawPath(path, paint);
            }
        } finally {
            if (i != 0) {
                canvas.translate(0.0f, -i);
            }
        }
    }

    private BlendMode determineHighContrastHighlightBlendMode(Canvas canvas) {
        if (shouldDrawHighlightsOnTop(canvas)) {
            return isHighContrastTextDark(this.mPaint.getColor()) ? BlendMode.MULTIPLY : BlendMode.DIFFERENCE;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isHighContrastTextDark(int i) {
        float f = CoreRune.GRAPHICS_RENDERER_HCF ? HIGH_CONTRAST_LAB_THRESHOLD_SEC : 50.0f;
        double[] dArr = new double[3];
        ColorUtils.colorToLAB(i, dArr);
        return dArr[0] <= ((double) f);
    }

    private boolean isJustificationRequired(int i) {
        int lineEnd;
        return (this.mJustificationMode == 0 || (lineEnd = getLineEnd(i)) >= this.mText.length() || this.mText.charAt(lineEnd - 1) == '\n') ? false : true;
    }

    private float getJustifyWidth(int i) {
        int indentAdjust;
        int indentAdjust2;
        Alignment alignment = this.mAlignment;
        int i2 = this.mWidth;
        int paragraphDirection = getParagraphDirection(i);
        ParagraphStyle[] paragraphStyleArr = NO_PARA_SPANS;
        int i3 = 0;
        if (this.mSpannedText) {
            Spanned spanned = (Spanned) this.mText;
            int lineStart = getLineStart(i);
            boolean z = lineStart == 0 || this.mText.charAt(lineStart + (-1)) == '\n';
            if (z) {
                paragraphStyleArr = (ParagraphStyle[]) getParagraphSpans(spanned, lineStart, spanned.nextSpanTransition(lineStart, this.mText.length(), ParagraphStyle.class), ParagraphStyle.class);
                int length = paragraphStyleArr.length - 1;
                while (true) {
                    if (length < 0) {
                        break;
                    }
                    ParagraphStyle paragraphStyle = paragraphStyleArr[length];
                    if (paragraphStyle instanceof AlignmentSpan) {
                        alignment = ((AlignmentSpan) paragraphStyle).getAlignment();
                        break;
                    }
                    length--;
                }
            }
            int length2 = paragraphStyleArr.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length2) {
                    break;
                }
                ParagraphStyle paragraphStyle2 = paragraphStyleArr[i4];
                if (paragraphStyle2 instanceof LeadingMarginSpan.LeadingMarginSpan2) {
                    if (i < getLineForOffset(spanned.getSpanStart(paragraphStyleArr[i4])) + ((LeadingMarginSpan.LeadingMarginSpan2) paragraphStyle2).getLeadingMarginLineCount()) {
                        z = true;
                        break;
                    }
                }
                i4++;
            }
            int i5 = 0;
            while (i3 < length2) {
                ParagraphStyle paragraphStyle3 = paragraphStyleArr[i3];
                if (paragraphStyle3 instanceof LeadingMarginSpan) {
                    LeadingMarginSpan leadingMarginSpan = (LeadingMarginSpan) paragraphStyle3;
                    if (paragraphDirection == -1) {
                        i2 -= leadingMarginSpan.getLeadingMargin(z);
                    } else {
                        i5 += leadingMarginSpan.getLeadingMargin(z);
                    }
                }
                i3++;
            }
            i3 = i5;
        }
        if (alignment == Alignment.ALIGN_LEFT) {
            alignment = paragraphDirection == 1 ? Alignment.ALIGN_NORMAL : Alignment.ALIGN_OPPOSITE;
        } else if (alignment == Alignment.ALIGN_RIGHT) {
            alignment = paragraphDirection == 1 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_NORMAL;
        }
        if (alignment == Alignment.ALIGN_NORMAL) {
            if (paragraphDirection == 1) {
                indentAdjust = getIndentAdjust(i, Alignment.ALIGN_LEFT);
            } else {
                indentAdjust2 = getIndentAdjust(i, Alignment.ALIGN_RIGHT);
                indentAdjust = -indentAdjust2;
            }
        } else if (alignment != Alignment.ALIGN_OPPOSITE) {
            indentAdjust = getIndentAdjust(i, Alignment.ALIGN_CENTER);
        } else if (paragraphDirection == 1) {
            indentAdjust2 = getIndentAdjust(i, Alignment.ALIGN_RIGHT);
            indentAdjust = -indentAdjust2;
        } else {
            indentAdjust = getIndentAdjust(i, Alignment.ALIGN_LEFT);
        }
        return (i2 - i3) - indentAdjust;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x00ec A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0211  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawText(android.graphics.Canvas r34, int r35, int r36) {
        /*
            Method dump skipped, instructions count: 707
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.drawText(android.graphics.Canvas, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0097 A[LOOP:1: B:18:0x0095->B:19:0x0097, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawBackground(android.graphics.Canvas r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 197
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.drawBackground(android.graphics.Canvas, int, int):void");
    }

    private void drawHighContrastBackground(Canvas canvas, int i, int i2) {
        SpanColors spanColors;
        if (shouldDrawHighlightsOnTop(canvas)) {
            if ((!this.mSpannedText || this.mSpanColors == null) && this.mPaint.getAlpha() == 0) {
                return;
            }
            float max = Math.max(0.0f, this.mPaint.getTextSize() * 0.0f);
            float max2 = Math.max(this.mPaint.density * HIGH_CONTRAST_TEXT_BACKGROUND_CORNER_RADIUS_MIN_DP, this.mPaint.getTextSize() * 0.5f);
            int argb = Color.argb(HIGH_CONTRAST_TEXT_BACKGROUND_ALPHA_PERCENTAGE, 1.0f, 1.0f, 1.0f);
            int argb2 = Color.argb(HIGH_CONTRAST_TEXT_BACKGROUND_ALPHA_PERCENTAGE, 0.0f, 0.0f, 0.0f);
            int color = this.mPaint.getColor();
            Paint paint = this.mWorkPlainPaint;
            paint.reset();
            paint.setColor(isHighContrastTextDark(color) ? argb : argb2);
            paint.setStyle(Paint.Style.FILL);
            int lineStart = getLineStart(i);
            int lineEnd = getLineEnd(i2);
            if (this.mSpannedText && (spanColors = this.mSpanColors) != null) {
                spanColors.init(this.mWorkPaint, (Spanned) this.mText, lineStart, lineEnd);
            }
            forEachCharacterBounds(lineStart, lineEnd, i, i2, new CharacterBoundsListener(color, paint, max, canvas, max2, argb, argb2) { // from class: android.text.Layout.1
                int mLastColor;
                int mLastLineNum = -1;
                final RectF mLineBackground = new RectF();
                final /* synthetic */ Paint val$bgPaint;
                final /* synthetic */ int val$black;
                final /* synthetic */ Canvas val$canvas;
                final /* synthetic */ float val$cornerRadius;
                final /* synthetic */ int val$originalTextColor;
                final /* synthetic */ float val$padding;
                final /* synthetic */ int val$white;

                {
                    this.val$originalTextColor = color;
                    this.val$bgPaint = paint;
                    this.val$padding = max;
                    this.val$canvas = canvas;
                    this.val$cornerRadius = max2;
                    this.val$white = argb;
                    this.val$black = argb2;
                    this.mLastColor = color;
                }

                @Override // android.text.Layout.CharacterBoundsListener
                public void onCharacterBounds(int i3, int i4, float f, float f2, float f3, float f4) {
                    if (TextLine.isLineEndSpace(Layout.this.mText.charAt(i3))) {
                        return;
                    }
                    int determineContrastingBackgroundColor = determineContrastingBackgroundColor(i3);
                    boolean z = determineContrastingBackgroundColor != this.val$bgPaint.getColor();
                    int codePointAt = Character.codePointAt(Layout.this.mText, i3);
                    if ((Character.isEmojiComponent(codePointAt) || Character.isExtendedPictographic(codePointAt)) && !isStandardNumber(i3)) {
                        return;
                    }
                    if (i4 != this.mLastLineNum || z) {
                        drawRect();
                        this.mLineBackground.set(f, f2, f3, f4);
                        this.mLastLineNum = i4;
                        if (z) {
                            this.val$bgPaint.setColor(determineContrastingBackgroundColor);
                            return;
                        }
                        return;
                    }
                    this.mLineBackground.union(f, f2, f3, f4);
                }

                @Override // android.text.Layout.CharacterBoundsListener
                public void onEnd() {
                    drawRect();
                }

                private boolean isStandardNumber(int i3) {
                    int codePointAt = Character.codePointAt(Layout.this.mText, i3);
                    int i4 = i3 + 1;
                    return ((codePointAt >= 48 && codePointAt <= 57) || codePointAt == 35 || codePointAt == 42) && !(i4 < Layout.this.mText.length() && Character.codePointAt(Layout.this.mText, i4) == 65039);
                }

                private void drawRect() {
                    if (this.mLineBackground.isEmpty()) {
                        return;
                    }
                    RectF rectF = this.mLineBackground;
                    float f = this.val$padding;
                    rectF.inset(-f, -f);
                    Canvas canvas2 = this.val$canvas;
                    RectF rectF2 = this.mLineBackground;
                    float f2 = this.val$cornerRadius;
                    canvas2.drawRoundRect(rectF2, f2, f2, this.val$bgPaint);
                }

                private int determineContrastingBackgroundColor(int i3) {
                    if (!Layout.this.mSpannedText || Layout.this.mSpanColors == null) {
                        return this.val$bgPaint.getColor();
                    }
                    int colorAt = Layout.this.mSpanColors.getColorAt(i3);
                    if (colorAt == 0) {
                        colorAt = this.val$originalTextColor;
                    }
                    if (colorAt != this.mLastColor) {
                        this.mLastColor = colorAt;
                        return Layout.this.isHighContrastTextDark(colorAt) ? this.val$white : this.val$black;
                    }
                    return this.val$bgPaint.getColor();
                }
            });
            SpanColors spanColors2 = this.mSpanColors;
            if (spanColors2 != null) {
                spanColors2.recycle();
            }
        }
    }

    public long getLineRangeForDraw(Canvas canvas) {
        Rect rect = sTempRect;
        synchronized (rect) {
            if (!canvas.getClipBounds(rect)) {
                return TextUtils.packRangeInLong(0, -1);
            }
            int i = rect.top;
            int i2 = rect.bottom;
            int max = Math.max(i, 0);
            int min = Math.min(getLineTop(getLineCount()), i2);
            if (max >= min) {
                return TextUtils.packRangeInLong(0, -1);
            }
            return TextUtils.packRangeInLong(getLineForVertical(max), getLineForVertical(min));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getLineStartPos(int r8, int r9, int r10) {
        /*
            r7 = this;
            android.text.Layout$Alignment r0 = r7.getParagraphAlignment(r8)
            int r1 = r7.getParagraphDirection(r8)
            android.text.Layout$Alignment r2 = android.text.Layout.Alignment.ALIGN_LEFT
            r3 = 1
            if (r0 != r2) goto L15
            if (r1 != r3) goto L12
            android.text.Layout$Alignment r0 = android.text.Layout.Alignment.ALIGN_NORMAL
            goto L20
        L12:
            android.text.Layout$Alignment r0 = android.text.Layout.Alignment.ALIGN_OPPOSITE
            goto L20
        L15:
            android.text.Layout$Alignment r2 = android.text.Layout.Alignment.ALIGN_RIGHT
            if (r0 != r2) goto L20
            if (r1 != r3) goto L1e
            android.text.Layout$Alignment r0 = android.text.Layout.Alignment.ALIGN_OPPOSITE
            goto L20
        L1e:
            android.text.Layout$Alignment r0 = android.text.Layout.Alignment.ALIGN_NORMAL
        L20:
            android.text.Layout$Alignment r2 = android.text.Layout.Alignment.ALIGN_NORMAL
            if (r0 != r2) goto L36
            if (r1 != r3) goto L2e
            android.text.Layout$Alignment r10 = android.text.Layout.Alignment.ALIGN_LEFT
            int r7 = r7.getIndentAdjust(r8, r10)
        L2c:
            int r9 = r9 + r7
            return r9
        L2e:
            android.text.Layout$Alignment r9 = android.text.Layout.Alignment.ALIGN_RIGHT
            int r7 = r7.getIndentAdjust(r8, r9)
        L34:
            int r10 = r10 + r7
            return r10
        L36:
            boolean r2 = r7.mSpannedText
            if (r2 == 0) goto L65
            boolean r2 = r7.getLineContainsTab(r8)
            if (r2 == 0) goto L65
            java.lang.CharSequence r2 = r7.mText
            android.text.Spanned r2 = (android.text.Spanned) r2
            int r4 = r7.getLineStart(r8)
            int r5 = r2.length()
            java.lang.Class<android.text.style.TabStopSpan> r6 = android.text.style.TabStopSpan.class
            int r5 = r2.nextSpanTransition(r4, r5, r6)
            java.lang.Class<android.text.style.TabStopSpan> r6 = android.text.style.TabStopSpan.class
            java.lang.Object[] r2 = getParagraphSpans(r2, r4, r5, r6)
            android.text.style.TabStopSpan[] r2 = (android.text.style.TabStopSpan[]) r2
            int r4 = r2.length
            if (r4 <= 0) goto L65
            android.text.Layout$TabStops r4 = new android.text.Layout$TabStops
            r5 = 1101004800(0x41a00000, float:20.0)
            r4.<init>(r5, r2)
            goto L66
        L65:
            r4 = 0
        L66:
            r2 = 0
            float r2 = r7.getLineExtent(r8, r4, r2)
            int r2 = (int) r2
            android.text.Layout$Alignment r4 = android.text.Layout.Alignment.ALIGN_OPPOSITE
            if (r0 != r4) goto L82
            if (r1 != r3) goto L7a
            int r10 = r10 - r2
            android.text.Layout$Alignment r9 = android.text.Layout.Alignment.ALIGN_RIGHT
            int r7 = r7.getIndentAdjust(r8, r9)
            goto L34
        L7a:
            int r9 = r9 - r2
            android.text.Layout$Alignment r10 = android.text.Layout.Alignment.ALIGN_LEFT
            int r7 = r7.getIndentAdjust(r8, r10)
            goto L2c
        L82:
            r0 = r2 & (-2)
            int r9 = r9 + r10
            int r9 = r9 - r0
            android.text.Layout$Alignment r10 = android.text.Layout.Alignment.ALIGN_CENTER
            int r7 = r7.getIndentAdjust(r8, r10)
            int r7 = r7 + r3
            int r7 = r9 >> r7
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.getLineStartPos(int, int, int):int");
    }

    public final void increaseWidthTo(int i) {
        if (i < this.mWidth) {
            throw new RuntimeException("attempted to reduce Layout width");
        }
        this.mWidth = i;
    }

    public int getHeight() {
        return getLineTop(getLineCount());
    }

    public int getHeight(boolean z) {
        return getHeight();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.RectF computeDrawingBoundingBox() {
        /*
            Method dump skipped, instructions count: 256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.computeDrawingBoundingBox():android.graphics.RectF");
    }

    public int getLineBounds(int i, Rect rect) {
        if (rect != null) {
            rect.left = 0;
            rect.top = getLineTop(i);
            rect.right = this.mWidth;
            rect.bottom = getLineTop(i + 1);
        }
        return getLineBaseline(i);
    }

    public boolean isLevelBoundary(int i) {
        int lineForOffset = getLineForOffset(i);
        Directions lineDirections = getLineDirections(lineForOffset);
        if (lineDirections != DIRS_ALL_LEFT_TO_RIGHT && lineDirections != DIRS_ALL_RIGHT_TO_LEFT) {
            int[] iArr = lineDirections.mDirections;
            int lineStart = getLineStart(lineForOffset);
            int lineEnd = getLineEnd(lineForOffset);
            if (i == lineStart || i == lineEnd) {
                if (((iArr[(i == lineStart ? 0 : iArr.length - 2) + 1] >>> 26) & 63) != (getParagraphDirection(lineForOffset) == 1 ? 0 : 1)) {
                    return true;
                }
            } else {
                int i2 = i - lineStart;
                for (int i3 = 0; i3 < iArr.length; i3 += 2) {
                    if (i2 == iArr[i3]) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public boolean isRtlCharAt(int i) {
        int lineForOffset = getLineForOffset(i);
        Directions lineDirections = getLineDirections(lineForOffset);
        if (lineDirections == DIRS_ALL_LEFT_TO_RIGHT) {
            return false;
        }
        if (lineDirections == DIRS_ALL_RIGHT_TO_LEFT) {
            return true;
        }
        int[] iArr = lineDirections.mDirections;
        int lineStart = getLineStart(lineForOffset);
        for (int i2 = 0; i2 < iArr.length; i2 += 2) {
            int i3 = iArr[i2] + lineStart;
            int i4 = iArr[i2 + 1];
            int i5 = (RUN_LENGTH_MASK & i4) + i3;
            if (i >= i3 && i < i5) {
                return ((i4 >>> 26) & 1) != 0;
            }
        }
        return false;
    }

    public long getRunRange(int i) {
        int lineForOffset = getLineForOffset(i);
        Directions lineDirections = getLineDirections(lineForOffset);
        if (lineDirections == DIRS_ALL_LEFT_TO_RIGHT || lineDirections == DIRS_ALL_RIGHT_TO_LEFT) {
            return TextUtils.packRangeInLong(0, getLineEnd(lineForOffset));
        }
        int[] iArr = lineDirections.mDirections;
        int lineStart = getLineStart(lineForOffset);
        for (int i2 = 0; i2 < iArr.length; i2 += 2) {
            int i3 = iArr[i2] + lineStart;
            int i4 = (iArr[i2 + 1] & RUN_LENGTH_MASK) + i3;
            if (i >= i3 && i < i4) {
                return TextUtils.packRangeInLong(i3, i4);
            }
        }
        return TextUtils.packRangeInLong(0, getLineEnd(lineForOffset));
    }

    public boolean primaryIsTrailingPrevious(int i) {
        int i2;
        int i3;
        int lineForOffset = getLineForOffset(i);
        int lineStart = getLineStart(lineForOffset);
        int lineEnd = getLineEnd(lineForOffset);
        int[] iArr = getLineDirections(lineForOffset).mDirections;
        int i4 = 0;
        while (true) {
            i2 = -1;
            if (i4 >= iArr.length) {
                i3 = -1;
                break;
            }
            int i5 = iArr[i4] + lineStart;
            int i6 = iArr[i4 + 1];
            int i7 = (i6 & RUN_LENGTH_MASK) + i5;
            if (i7 > lineEnd) {
                i7 = lineEnd;
            }
            if (i < i5 || i >= i7) {
                i4 += 2;
            } else {
                if (i > i5) {
                    return false;
                }
                i3 = (i6 >>> 26) & 63;
            }
        }
        if (i3 == -1) {
            i3 = getParagraphDirection(lineForOffset) == 1 ? 0 : 1;
        }
        if (i != lineStart) {
            int i8 = i - 1;
            int i9 = 0;
            while (true) {
                if (i9 >= iArr.length) {
                    break;
                }
                int i10 = iArr[i9] + lineStart;
                int i11 = iArr[i9 + 1];
                int i12 = (i11 & RUN_LENGTH_MASK) + i10;
                if (i12 > lineEnd) {
                    i12 = lineEnd;
                }
                if (i8 >= i10 && i8 < i12) {
                    i2 = (i11 >>> 26) & 63;
                    break;
                }
                i9 += 2;
            }
        } else {
            i2 = getParagraphDirection(lineForOffset) == 1 ? 0 : 1;
        }
        return i2 < i3;
    }

    public boolean[] primaryIsTrailingPreviousAllLineOffsets(int i) {
        byte b;
        int lineStart = getLineStart(i);
        int lineEnd = getLineEnd(i);
        int[] iArr = getLineDirections(i).mDirections;
        int i2 = (lineEnd - lineStart) + 1;
        boolean[] zArr = new boolean[i2];
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < iArr.length; i3 += 2) {
            int i4 = iArr[i3] + lineStart;
            int i5 = iArr[i3 + 1];
            int i6 = (RUN_LENGTH_MASK & i5) + i4;
            if (i6 > lineEnd) {
                i6 = lineEnd;
            }
            if (i6 != i4) {
                bArr[(i6 - lineStart) - 1] = (byte) ((i5 >>> 26) & 63);
            }
        }
        for (int i7 = 0; i7 < iArr.length; i7 += 2) {
            int i8 = iArr[i7] + lineStart;
            byte b2 = (byte) ((iArr[i7 + 1] >>> 26) & 63);
            int i9 = i8 - lineStart;
            if (i8 == lineStart) {
                b = getParagraphDirection(i) == 1 ? (byte) 0 : (byte) 1;
            } else {
                b = bArr[i9 - 1];
            }
            zArr[i9] = b2 > b;
        }
        return zArr;
    }

    public float getPrimaryHorizontal(int i) {
        return getPrimaryHorizontal(i, false);
    }

    public float getPrimaryHorizontal(int i, boolean z) {
        return getHorizontal(i, primaryIsTrailingPrevious(i), z);
    }

    public float getSecondaryHorizontal(int i) {
        return getSecondaryHorizontal(i, false);
    }

    public float getSecondaryHorizontal(int i, boolean z) {
        return getHorizontal(i, !primaryIsTrailingPrevious(i), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getHorizontal(int i, boolean z) {
        return z ? getPrimaryHorizontal(i) : getSecondaryHorizontal(i);
    }

    private float getHorizontal(int i, boolean z, boolean z2) {
        return getHorizontal(i, z, getLineForOffset(i), z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float getHorizontal(int r18, boolean r19, int r20, boolean r21) {
        /*
            r17 = this;
            r0 = r17
            r1 = r20
            int r5 = r0.getLineStart(r1)
            int r6 = r0.getLineEnd(r1)
            int r7 = r0.getParagraphDirection(r1)
            boolean r9 = r0.getLineContainsTab(r1)
            android.text.Layout$Directions r8 = r0.getLineDirections(r1)
            if (r9 == 0) goto L35
            java.lang.CharSequence r2 = r0.mText
            boolean r3 = r2 instanceof android.text.Spanned
            if (r3 == 0) goto L35
            android.text.Spanned r2 = (android.text.Spanned) r2
            java.lang.Class<android.text.style.TabStopSpan> r3 = android.text.style.TabStopSpan.class
            java.lang.Object[] r2 = getParagraphSpans(r2, r5, r6, r3)
            android.text.style.TabStopSpan[] r2 = (android.text.style.TabStopSpan[]) r2
            int r3 = r2.length
            if (r3 <= 0) goto L35
            android.text.Layout$TabStops r3 = new android.text.Layout$TabStops
            r4 = 1101004800(0x41a00000, float:20.0)
            r3.<init>(r4, r2)
            goto L36
        L35:
            r3 = 0
        L36:
            r10 = r3
            android.text.TextLine r11 = android.text.TextLine.obtain()
            android.text.TextPaint r3 = r0.mPaint
            java.lang.CharSequence r4 = r0.mText
            r2 = r11
            int r11 = r0.getEllipsisStart(r1)
            int r12 = r0.getEllipsisStart(r1)
            int r13 = r0.getEllipsisCount(r1)
            int r12 = r12 + r13
            boolean r13 = r0.isFallbackLineSpacingEnabled()
            r2.set(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            int r12 = r18 - r5
            r15 = 0
            r16 = 0
            r14 = 0
            r13 = r19
            r11 = r2
            float r2 = r11.measure(r12, r13, r14, r15, r16)
            android.text.TextLine.recycle(r11)
            if (r21 == 0) goto L6e
            int r3 = r0.mWidth
            float r4 = (float) r3
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 <= 0) goto L6e
            float r2 = (float) r3
        L6e:
            int r3 = r0.getParagraphLeft(r1)
            int r4 = r0.getParagraphRight(r1)
            int r0 = r0.getLineStartPos(r1, r3, r4)
            float r0 = (float) r0
            float r0 = r0 + r2
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.getHorizontal(int, boolean, int, boolean):float");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0093 A[LOOP:2: B:28:0x0091->B:29:0x0093, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public float[] getLineHorizontals(int r14, boolean r15, boolean r16) {
        /*
            r13 = this;
            int r3 = r13.getLineStart(r14)
            int r4 = r13.getLineEnd(r14)
            int r5 = r13.getParagraphDirection(r14)
            boolean r7 = r13.getLineContainsTab(r14)
            android.text.Layout$Directions r6 = r13.getLineDirections(r14)
            r12 = 0
            if (r7 == 0) goto L33
            java.lang.CharSequence r0 = r13.mText
            boolean r1 = r0 instanceof android.text.Spanned
            if (r1 == 0) goto L33
            android.text.Spanned r0 = (android.text.Spanned) r0
            java.lang.Class<android.text.style.TabStopSpan> r1 = android.text.style.TabStopSpan.class
            java.lang.Object[] r0 = getParagraphSpans(r0, r3, r4, r1)
            android.text.style.TabStopSpan[] r0 = (android.text.style.TabStopSpan[]) r0
            int r1 = r0.length
            if (r1 <= 0) goto L33
            android.text.Layout$TabStops r1 = new android.text.Layout$TabStops
            r2 = 1101004800(0x41a00000, float:20.0)
            r1.<init>(r2, r0)
            r8 = r1
            goto L34
        L33:
            r8 = r12
        L34:
            android.text.TextLine r0 = android.text.TextLine.obtain()
            android.text.TextPaint r1 = r13.mPaint
            java.lang.CharSequence r2 = r13.mText
            int r9 = r13.getEllipsisStart(r14)
            int r10 = r13.getEllipsisStart(r14)
            int r11 = r13.getEllipsisCount(r14)
            int r10 = r10 + r11
            boolean r11 = r13.isFallbackLineSpacingEnabled()
            r0.set(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            boolean[] r1 = r13.primaryIsTrailingPreviousAllLineOffsets(r14)
            r2 = 0
            if (r16 != 0) goto L64
            r5 = r2
        L58:
            int r6 = r1.length
            if (r5 >= r6) goto L64
            boolean r6 = r1[r5]
            r6 = r6 ^ 1
            r1[r5] = r6
            int r5 = r5 + 1
            goto L58
        L64:
            float[] r1 = r0.measureAllOffsets(r1, r12)
            android.text.TextLine.recycle(r0)
            if (r15 == 0) goto L80
            r0 = r2
        L6e:
            int r5 = r1.length
            if (r0 >= r5) goto L80
            r5 = r1[r0]
            int r6 = r13.mWidth
            float r7 = (float) r6
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 <= 0) goto L7d
            float r5 = (float) r6
            r1[r0] = r5
        L7d:
            int r0 = r0 + 1
            goto L6e
        L80:
            int r0 = r13.getParagraphLeft(r14)
            int r5 = r13.getParagraphRight(r14)
            int r13 = r13.getLineStartPos(r14, r0, r5)
            int r4 = r4 - r3
            int r4 = r4 + 1
            float[] r14 = new float[r4]
        L91:
            if (r2 >= r4) goto L9c
            float r0 = (float) r13
            r3 = r1[r2]
            float r0 = r0 + r3
            r14[r2] = r0
            int r2 = r2 + 1
            goto L91
        L9c:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.getLineHorizontals(int, boolean, boolean):float[]");
    }

    private void fillHorizontalBoundsForLine(int i, float[] fArr) {
        TabStops tabStops;
        int lineStart = getLineStart(i);
        int lineEnd = getLineEnd(i);
        int i2 = lineEnd - lineStart;
        int paragraphDirection = getParagraphDirection(i);
        Directions lineDirections = getLineDirections(i);
        boolean lineContainsTab = getLineContainsTab(i);
        if (lineContainsTab) {
            CharSequence charSequence = this.mText;
            if (charSequence instanceof Spanned) {
                TabStopSpan[] tabStopSpanArr = (TabStopSpan[]) getParagraphSpans((Spanned) charSequence, lineStart, lineEnd, TabStopSpan.class);
                if (tabStopSpanArr.length > 0) {
                    tabStops = new TabStops(TAB_INCREMENT, tabStopSpanArr);
                    TextLine obtain = TextLine.obtain();
                    obtain.set(this.mPaint, this.mText, lineStart, lineEnd, paragraphDirection, lineDirections, lineContainsTab, tabStops, getEllipsisStart(i), getEllipsisStart(i) + getEllipsisCount(i), isFallbackLineSpacingEnabled());
                    obtain.measureAllBounds((fArr != null || fArr.length < i2 * 2) ? new float[i2 * 2] : fArr, null);
                    TextLine.recycle(obtain);
                }
            }
        }
        tabStops = null;
        TextLine obtain2 = TextLine.obtain();
        obtain2.set(this.mPaint, this.mText, lineStart, lineEnd, paragraphDirection, lineDirections, lineContainsTab, tabStops, getEllipsisStart(i), getEllipsisStart(i) + getEllipsisCount(i), isFallbackLineSpacingEnabled());
        obtain2.measureAllBounds((fArr != null || fArr.length < i2 * 2) ? new float[i2 * 2] : fArr, null);
        TextLine.recycle(obtain2);
    }

    public void fillCharacterBounds(final int i, int i2, final float[] fArr, final int i3) {
        if (i < 0 || i2 < i || i2 > this.mText.length()) {
            throw new IndexOutOfBoundsException("given range: " + i + ", " + i2 + " is out of the text range: 0, " + this.mText.length());
        }
        if (fArr == null) {
            throw new IllegalArgumentException("bounds can't be null.");
        }
        int i4 = (i2 - i) * 4;
        if (i4 <= fArr.length - i3) {
            if (i == i2) {
                return;
            }
            forEachCharacterBounds(i, i2, getLineForOffset(i), getLineForOffset(i2 - 1), new CharacterBoundsListener() { // from class: android.text.Layout$$ExternalSyntheticLambda5
                @Override // android.text.Layout.CharacterBoundsListener
                public final void onCharacterBounds(int i5, int i6, float f, float f2, float f3, float f4) {
                    Layout.lambda$fillCharacterBounds$2(i3, i, fArr, i5, i6, f, f2, f3, f4);
                }
            });
        } else {
            throw new IndexOutOfBoundsException("bounds doesn't have enough space to store the result, needed: " + i4 + " had: " + (fArr.length - i3));
        }
    }

    static /* synthetic */ void lambda$fillCharacterBounds$2(int i, int i2, float[] fArr, int i3, int i4, float f, float f2, float f3, float f4) {
        int i5 = i + ((i3 - i2) * 4);
        fArr[i5] = f;
        fArr[i5 + 1] = f2;
        fArr[i5 + 2] = f3;
        fArr[i5 + 3] = f4;
    }

    private void forEachCharacterBounds(int i, int i2, int i3, int i4, CharacterBoundsListener characterBoundsListener) {
        float[] fArr = null;
        for (int i5 = i3; i5 <= i4; i5++) {
            int lineStart = getLineStart(i5);
            int lineEnd = getLineEnd(i5);
            int i6 = lineEnd - lineStart;
            if (fArr == null || fArr.length < i6 * 2) {
                fArr = new float[i6 * 2];
            }
            fillHorizontalBoundsForLine(i5, fArr);
            int lineStartPos = getLineStartPos(i5, getParagraphLeft(i5), getParagraphRight(i5));
            int lineTop = getLineTop(i5);
            int lineBottom = getLineBottom(i5);
            int max = Math.max(i, lineStart);
            for (int min = Math.min(i2, lineEnd); max < min; min = min) {
                int i7 = (max - lineStart) * 2;
                float f = lineStartPos;
                characterBoundsListener.onCharacterBounds(max, i5, fArr[i7] + f, lineTop, f + fArr[i7 + 1], lineBottom);
                max++;
            }
        }
        characterBoundsListener.onEnd();
    }

    public float getLineLeft(int i) {
        Alignment alignment;
        int paragraphDirection = getParagraphDirection(i);
        Alignment paragraphAlignment = getParagraphAlignment(i);
        if (paragraphAlignment == null) {
            paragraphAlignment = Alignment.ALIGN_CENTER;
        }
        int ordinal = paragraphAlignment.ordinal();
        if (ordinal == 0) {
            alignment = paragraphDirection == -1 ? Alignment.ALIGN_RIGHT : Alignment.ALIGN_LEFT;
        } else if (ordinal == 1) {
            alignment = paragraphDirection == -1 ? Alignment.ALIGN_LEFT : Alignment.ALIGN_RIGHT;
        } else if (ordinal == 2) {
            alignment = Alignment.ALIGN_CENTER;
        } else if (ordinal == 4) {
            alignment = Alignment.ALIGN_RIGHT;
        } else {
            alignment = Alignment.ALIGN_LEFT;
        }
        int ordinal2 = alignment.ordinal();
        if (ordinal2 == 2) {
            return (float) Math.floor(getParagraphLeft(i) + ((this.mWidth - getLineMax(i)) / 2.0f));
        }
        if (ordinal2 != 4) {
            return 0.0f;
        }
        return this.mWidth - getLineMax(i);
    }

    public float getLineRight(int i) {
        Alignment alignment;
        int paragraphDirection = getParagraphDirection(i);
        Alignment paragraphAlignment = getParagraphAlignment(i);
        if (paragraphAlignment == null) {
            paragraphAlignment = Alignment.ALIGN_CENTER;
        }
        int ordinal = paragraphAlignment.ordinal();
        if (ordinal == 0) {
            alignment = paragraphDirection == -1 ? Alignment.ALIGN_RIGHT : Alignment.ALIGN_LEFT;
        } else if (ordinal == 1) {
            alignment = paragraphDirection == -1 ? Alignment.ALIGN_LEFT : Alignment.ALIGN_RIGHT;
        } else if (ordinal == 2) {
            alignment = Alignment.ALIGN_CENTER;
        } else if (ordinal == 4) {
            alignment = Alignment.ALIGN_RIGHT;
        } else {
            alignment = Alignment.ALIGN_LEFT;
        }
        int ordinal2 = alignment.ordinal();
        if (ordinal2 == 2) {
            return (float) Math.ceil(getParagraphRight(i) - ((this.mWidth - getLineMax(i)) / 2.0f));
        }
        if (ordinal2 == 4) {
            return this.mWidth;
        }
        return getLineMax(i);
    }

    public float getLineMax(int i) {
        float paragraphLeadingMargin = getParagraphLeadingMargin(i);
        float lineExtent = getLineExtent(i, false);
        if (lineExtent < 0.0f) {
            lineExtent = -lineExtent;
        }
        return paragraphLeadingMargin + lineExtent;
    }

    public float getLineWidth(int i) {
        float paragraphLeadingMargin = getParagraphLeadingMargin(i);
        float lineExtent = getLineExtent(i, true);
        if (lineExtent < 0.0f) {
            lineExtent = -lineExtent;
        }
        return paragraphLeadingMargin + lineExtent;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float getLineExtent(int r13, boolean r14) {
        /*
            r12 = this;
            int r3 = r12.getLineStart(r13)
            if (r14 == 0) goto Lb
            int r14 = r12.getLineEnd(r13)
            goto Lf
        Lb:
            int r14 = r12.getLineVisibleEnd(r13)
        Lf:
            r4 = r14
            boolean r7 = r12.getLineContainsTab(r13)
            r14 = 0
            if (r7 == 0) goto L33
            java.lang.CharSequence r0 = r12.mText
            boolean r1 = r0 instanceof android.text.Spanned
            if (r1 == 0) goto L33
            android.text.Spanned r0 = (android.text.Spanned) r0
            java.lang.Class<android.text.style.TabStopSpan> r1 = android.text.style.TabStopSpan.class
            java.lang.Object[] r0 = getParagraphSpans(r0, r3, r4, r1)
            android.text.style.TabStopSpan[] r0 = (android.text.style.TabStopSpan[]) r0
            int r1 = r0.length
            if (r1 <= 0) goto L33
            android.text.Layout$TabStops r1 = new android.text.Layout$TabStops
            r2 = 1101004800(0x41a00000, float:20.0)
            r1.<init>(r2, r0)
            r8 = r1
            goto L34
        L33:
            r8 = r14
        L34:
            android.text.Layout$Directions r6 = r12.getLineDirections(r13)
            if (r6 != 0) goto L3c
            r12 = 0
            return r12
        L3c:
            int r5 = r12.getParagraphDirection(r13)
            android.text.TextLine r0 = android.text.TextLine.obtain()
            android.text.TextPaint r1 = r12.mWorkPaint
            android.text.TextPaint r2 = r12.mPaint
            r1.set(r2)
            int r2 = r12.getStartHyphenEdit(r13)
            r1.setStartHyphenEdit(r2)
            int r2 = r12.getEndHyphenEdit(r13)
            r1.setEndHyphenEdit(r2)
            java.lang.CharSequence r2 = r12.mText
            int r9 = r12.getEllipsisStart(r13)
            int r10 = r12.getEllipsisStart(r13)
            int r11 = r12.getEllipsisCount(r13)
            int r10 = r10 + r11
            boolean r11 = r12.isFallbackLineSpacingEnabled()
            r0.set(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            boolean r1 = r12.isJustificationRequired(r13)
            if (r1 == 0) goto L7e
            int r1 = r12.mJustificationMode
            float r13 = r12.getJustifyWidth(r13)
            r0.justify(r1, r13)
        L7e:
            boolean r12 = r12.mUseBoundsForWidth
            float r12 = r0.metrics(r14, r14, r12, r14)
            android.text.TextLine.recycle(r0)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.getLineExtent(int, boolean):float");
    }

    public int getLineLetterSpacingUnitCount(int i, boolean z) {
        int lineVisibleEnd;
        int lineStart = getLineStart(i);
        if (z) {
            lineVisibleEnd = getLineEnd(i);
        } else {
            lineVisibleEnd = getLineVisibleEnd(i, getLineStart(i), getLineStart(i + 1), false);
        }
        int i2 = lineVisibleEnd;
        Directions lineDirections = getLineDirections(i);
        if (lineDirections == null) {
            return 0;
        }
        int paragraphDirection = getParagraphDirection(i);
        TextLine obtain = TextLine.obtain();
        TextPaint textPaint = this.mWorkPaint;
        textPaint.set(this.mPaint);
        textPaint.setStartHyphenEdit(getStartHyphenEdit(i));
        textPaint.setEndHyphenEdit(getEndHyphenEdit(i));
        obtain.set(textPaint, this.mText, lineStart, i2, paragraphDirection, lineDirections, false, null, getEllipsisStart(i), getEllipsisStart(i) + getEllipsisCount(i), isFallbackLineSpacingEnabled());
        if (this.mLineInfo == null) {
            this.mLineInfo = new TextLine.LineInfo();
        }
        this.mLineInfo.setClusterCount(0);
        obtain.metrics(null, null, this.mUseBoundsForWidth, this.mLineInfo);
        TextLine.recycle(obtain);
        return this.mLineInfo.getClusterCount();
    }

    private float getLineExtent(int i, TabStops tabStops, boolean z) {
        int lineStart = getLineStart(i);
        int lineEnd = z ? getLineEnd(i) : getLineVisibleEnd(i);
        boolean lineContainsTab = getLineContainsTab(i);
        Directions lineDirections = getLineDirections(i);
        int paragraphDirection = getParagraphDirection(i);
        TextLine obtain = TextLine.obtain();
        TextPaint textPaint = this.mWorkPaint;
        textPaint.set(this.mPaint);
        textPaint.setStartHyphenEdit(getStartHyphenEdit(i));
        textPaint.setEndHyphenEdit(getEndHyphenEdit(i));
        obtain.set(textPaint, this.mText, lineStart, lineEnd, paragraphDirection, lineDirections, lineContainsTab, tabStops, getEllipsisStart(i), getEllipsisStart(i) + getEllipsisCount(i), isFallbackLineSpacingEnabled());
        if (isJustificationRequired(i)) {
            obtain.justify(this.mJustificationMode, getJustifyWidth(i));
        }
        float metrics = obtain.metrics(null, null, this.mUseBoundsForWidth, null);
        TextLine.recycle(obtain);
        return metrics;
    }

    public int getLineForVertical(int i) {
        int lineCount = getLineCount();
        int i2 = -1;
        while (lineCount - i2 > 1) {
            int i3 = (lineCount + i2) / 2;
            if (getLineTop(i3) > i) {
                lineCount = i3;
            } else {
                i2 = i3;
            }
        }
        if (i2 < 0) {
            return 0;
        }
        return i2;
    }

    public int getLineForOffset(int i) {
        int lineCount = getLineCount();
        int i2 = -1;
        while (lineCount - i2 > 1) {
            int i3 = (lineCount + i2) / 2;
            if (getLineStart(i3) > i) {
                lineCount = i3;
            } else {
                i2 = i3;
            }
        }
        if (i2 < 0) {
            return 0;
        }
        return i2;
    }

    public int getOffsetForHorizontal(int i, float f) {
        return getOffsetForHorizontal(i, f, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00fd A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getOffsetForHorizontal(int r18, float r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 282
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.getOffsetForHorizontal(int, float, boolean):int");
    }

    private class HorizontalMeasurementProvider {
        private float[] mHorizontals;
        private final int mLine;
        private int mLineStartOffset;
        private final boolean mPrimary;

        HorizontalMeasurementProvider(int i, boolean z) {
            this.mLine = i;
            this.mPrimary = z;
            init();
        }

        private void init() {
            if (Layout.this.getLineDirections(this.mLine) == Layout.DIRS_ALL_LEFT_TO_RIGHT) {
                return;
            }
            this.mHorizontals = Layout.this.getLineHorizontals(this.mLine, false, this.mPrimary);
            this.mLineStartOffset = Layout.this.getLineStart(this.mLine);
        }

        float get(int i) {
            int i2 = i - this.mLineStartOffset;
            float[] fArr = this.mHorizontals;
            if (fArr == null || i2 < 0 || i2 >= fArr.length) {
                return Layout.this.getHorizontal(i, this.mPrimary);
            }
            return fArr[i2];
        }
    }

    public int[] getRangeForRect(RectF rectF, SegmentFinder segmentFinder, TextInclusionStrategy textInclusionStrategy) {
        int lineForVertical = getLineForVertical((int) rectF.top);
        if (rectF.top > getLineBottom(lineForVertical, false) && (lineForVertical = lineForVertical + 1) >= getLineCount()) {
            return null;
        }
        int i = lineForVertical;
        int lineForVertical2 = getLineForVertical((int) rectF.bottom);
        if ((lineForVertical2 == 0 && rectF.bottom < getLineTop(0)) || lineForVertical2 < i) {
            return null;
        }
        int startOrEndOffsetForAreaWithinLine = getStartOrEndOffsetForAreaWithinLine(i, rectF, segmentFinder, textInclusionStrategy, true);
        while (startOrEndOffsetForAreaWithinLine == -1 && i < lineForVertical2) {
            int i2 = i + 1;
            startOrEndOffsetForAreaWithinLine = getStartOrEndOffsetForAreaWithinLine(i2, rectF, segmentFinder, textInclusionStrategy, true);
            i = i2;
        }
        if (startOrEndOffsetForAreaWithinLine == -1) {
            return null;
        }
        int startOrEndOffsetForAreaWithinLine2 = getStartOrEndOffsetForAreaWithinLine(lineForVertical2, rectF, segmentFinder, textInclusionStrategy, false);
        while (startOrEndOffsetForAreaWithinLine2 == -1 && i < lineForVertical2) {
            int i3 = lineForVertical2 - 1;
            startOrEndOffsetForAreaWithinLine2 = getStartOrEndOffsetForAreaWithinLine(i3, rectF, segmentFinder, textInclusionStrategy, false);
            lineForVertical2 = i3;
        }
        if (startOrEndOffsetForAreaWithinLine2 == -1) {
            return null;
        }
        return new int[]{segmentFinder.previousStartBoundary(startOrEndOffsetForAreaWithinLine + 1), segmentFinder.nextEndBoundary(startOrEndOffsetForAreaWithinLine2 - 1)};
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ba, code lost:
    
        return -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getStartOrEndOffsetForAreaWithinLine(int r19, android.graphics.RectF r20, android.text.SegmentFinder r21, android.text.Layout.TextInclusionStrategy r22, boolean r23) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            int r2 = r18.getLineTop(r19)
            r3 = 0
            r4 = r2
            int r2 = r0.getLineBottom(r1, r3)
            r5 = r3
            int r3 = r18.getLineStart(r19)
            int r6 = r18.getLineEnd(r19)
            r13 = -1
            if (r3 != r6) goto L1b
            return r13
        L1b:
            int r14 = r6 - r3
            int r6 = r14 * 2
            float[] r6 = new float[r6]
            r0.fillHorizontalBoundsForLine(r1, r6)
            int r7 = r18.getParagraphLeft(r19)
            int r8 = r18.getParagraphRight(r19)
            int r7 = r0.getLineStartPos(r1, r7, r8)
            android.text.Layout$Directions r15 = r18.getLineDirections(r19)
            r16 = 1
            if (r23 == 0) goto L3a
            r0 = r5
            goto L40
        L3a:
            int r0 = r15.getRunCount()
            int r0 = r0 + (-1)
        L40:
            if (r23 == 0) goto L4b
            int r1 = r15.getRunCount()
            if (r0 < r1) goto L49
            goto L4b
        L49:
            r5 = r6
            goto L50
        L4b:
            if (r23 != 0) goto Lba
            if (r0 < 0) goto Lba
            goto L49
        L50:
            int r6 = r15.getRunStart(r0)
            int r1 = r15.getRunLength(r0)
            int r1 = r1 + r6
            int r1 = java.lang.Math.min(r1, r14)
            boolean r10 = r15.isRunRtl(r0)
            float r8 = (float) r7
            if (r10 == 0) goto L6b
            int r9 = r1 + (-1)
            int r9 = r9 * 2
            r9 = r5[r9]
            goto L6f
        L6b:
            int r9 = r6 * 2
            r9 = r5[r9]
        L6f:
            float r9 = r9 + r8
            if (r10 == 0) goto L79
            int r11 = r6 * 2
            int r11 = r11 + 1
            r11 = r5[r11]
            goto L81
        L79:
            int r11 = r1 + (-1)
            int r11 = r11 * 2
            int r11 = r11 + 1
            r11 = r5[r11]
        L81:
            float r8 = r8 + r11
            if (r23 == 0) goto L98
            r11 = r7
            r7 = r1
            r1 = r4
            r4 = r11
            r11 = r9
            r9 = r8
            r8 = r11
            r11 = r21
            r12 = r22
            r17 = r0
            r0 = r20
            int r6 = getStartOffsetForAreaWithinRun(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            goto Lab
        L98:
            r11 = r7
            r7 = r1
            r1 = r4
            r4 = r11
            r11 = r9
            r9 = r8
            r8 = r11
            r11 = r21
            r12 = r22
            r17 = r0
            r0 = r20
            int r6 = getEndOffsetForAreaWithinRun(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
        Lab:
            if (r6 < 0) goto Lae
            return r6
        Lae:
            if (r23 == 0) goto Lb3
            r0 = r16
            goto Lb4
        Lb3:
            r0 = r13
        Lb4:
            int r0 = r17 + r0
            r7 = r4
            r6 = r5
            r4 = r1
            goto L40
        Lba:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.getStartOrEndOffsetForAreaWithinLine(int, android.graphics.RectF, android.text.SegmentFinder, android.text.Layout$TextInclusionStrategy, boolean):int");
    }

    private static int getStartOffsetForAreaWithinRun(RectF rectF, int i, int i2, int i3, int i4, float[] fArr, int i5, int i6, float f, float f2, boolean z, SegmentFinder segmentFinder, TextInclusionStrategy textInclusionStrategy) {
        int i7;
        int previousStartBoundary;
        int i8;
        if (f2 >= rectF.left && f <= rectF.right) {
            if ((z || rectF.left > f) && (!z || rectF.right < f2)) {
                int i9 = i5;
                i7 = i6;
                while (i7 - i9 > 1) {
                    int i10 = (i7 + i9) / 2;
                    float f3 = i4 + fArr[i10 * 2];
                    if ((z || f3 <= rectF.left) && (!z || f3 >= rectF.right)) {
                        i9 = i10;
                    } else {
                        i7 = i10;
                    }
                }
                if (!z) {
                    i7 = i9;
                }
            } else {
                i7 = i5;
            }
            int nextEndBoundary = segmentFinder.nextEndBoundary(i7 + i3);
            if (nextEndBoundary == -1 || (previousStartBoundary = segmentFinder.previousStartBoundary(nextEndBoundary)) >= (i8 = i3 + i6)) {
                return -1;
            }
            int max = Math.max(previousStartBoundary, i3 + i5);
            int min = Math.min(nextEndBoundary, i8);
            RectF rectF2 = new RectF(0.0f, i, 0.0f, i2);
            while (true) {
                float f4 = i4;
                float f5 = fArr[((max - i3) * 2) + (z ? 1 : 0)] + f4;
                if ((z || f5 <= rectF.right) && (!z || f5 >= rectF.left)) {
                    float f6 = f4 + fArr[(((min - i3) - 1) * 2) + (!z ? 1 : 0)];
                    rectF2.left = z ? f6 : f5;
                    if (!z) {
                        f5 = f6;
                    }
                    rectF2.right = f5;
                    if (!textInclusionStrategy.isSegmentInside(rectF2, rectF)) {
                        max = segmentFinder.nextStartBoundary(max);
                        if (max == -1 || max >= i8) {
                            break;
                        }
                        min = Math.min(segmentFinder.nextEndBoundary(max), i8);
                    } else {
                        return max;
                    }
                }
            }
            return -1;
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x008d, code lost:
    
        return -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int getEndOffsetForAreaWithinRun(android.graphics.RectF r8, int r9, int r10, int r11, int r12, float[] r13, int r14, int r15, float r16, float r17, boolean r18, android.text.SegmentFinder r19, android.text.Layout.TextInclusionStrategy r20) {
        /*
            Method dump skipped, instructions count: 190
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.getEndOffsetForAreaWithinRun(android.graphics.RectF, int, int, int, int, float[], int, int, float, float, boolean, android.text.SegmentFinder, android.text.Layout$TextInclusionStrategy):int");
    }

    public final int getLineEnd(int i) {
        return getLineStart(i + 1);
    }

    public int getLineVisibleEnd(int i) {
        return getLineVisibleEnd(i, getLineStart(i), getLineStart(i + 1), true);
    }

    private int getLineVisibleEnd(int i, int i2, int i3, boolean z) {
        CharSequence charSequence = this.mText;
        if (z && i == getLineCount() - 1) {
            return i3;
        }
        while (i3 > i2) {
            int i4 = i3 - 1;
            char charAt = charSequence.charAt(i4);
            if (charAt == '\n') {
                return i4;
            }
            if (!TextLine.isLineEndSpace(charAt)) {
                break;
            }
            i3--;
        }
        return i3;
    }

    public final int getLineBottom(int i) {
        return getLineBottom(i, true);
    }

    public int getLineBottom(int i, boolean z) {
        if (z) {
            return getLineTop(i + 1);
        }
        return getLineTop(i + 1) - getLineExtra(i);
    }

    public final int getLineBaseline(int i) {
        return getLineTop(i + 1) - getLineDescent(i);
    }

    public final int getLineAscent(int i) {
        return getLineTop(i) - (getLineTop(i + 1) - getLineDescent(i));
    }

    public int getOffsetToLeftOf(int i) {
        return getOffsetToLeftRightOf(i, true);
    }

    public int getOffsetToRightOf(int i) {
        return getOffsetToLeftRightOf(i, false);
    }

    private int getOffsetToLeftRightOf(int i, boolean z) {
        int i2;
        int i3;
        int i4;
        int lineForOffset = getLineForOffset(i);
        int lineStart = getLineStart(lineForOffset);
        int lineEnd = getLineEnd(lineForOffset);
        int paragraphDirection = getParagraphDirection(lineForOffset);
        boolean z2 = true;
        if (z == (paragraphDirection == -1)) {
            if (i == lineEnd) {
                if (lineForOffset < getLineCount() - 1) {
                    lineForOffset++;
                }
                return i;
            }
            z2 = false;
        } else {
            if (i == lineStart) {
                if (lineForOffset > 0) {
                    lineForOffset--;
                }
                return i;
            }
            z2 = false;
        }
        if (z2) {
            lineStart = getLineStart(lineForOffset);
            lineEnd = getLineEnd(lineForOffset);
            int paragraphDirection2 = getParagraphDirection(lineForOffset);
            if (paragraphDirection2 != paragraphDirection) {
                z = !z;
                i3 = lineEnd;
                i4 = paragraphDirection2;
                i2 = lineStart;
                Directions lineDirections = getLineDirections(lineForOffset);
                TextLine obtain = TextLine.obtain();
                obtain.set(this.mPaint, this.mText, i2, i3, i4, lineDirections, false, null, getEllipsisStart(lineForOffset), getEllipsisStart(lineForOffset) + getEllipsisCount(lineForOffset), isFallbackLineSpacingEnabled());
                int offsetToLeftRightOf = i2 + obtain.getOffsetToLeftRightOf(i - i2, z);
                TextLine.recycle(obtain);
                return offsetToLeftRightOf;
            }
        }
        i2 = lineStart;
        i3 = lineEnd;
        i4 = paragraphDirection;
        Directions lineDirections2 = getLineDirections(lineForOffset);
        TextLine obtain2 = TextLine.obtain();
        obtain2.set(this.mPaint, this.mText, i2, i3, i4, lineDirections2, false, null, getEllipsisStart(lineForOffset), getEllipsisStart(lineForOffset) + getEllipsisCount(lineForOffset), isFallbackLineSpacingEnabled());
        int offsetToLeftRightOf2 = i2 + obtain2.getOffsetToLeftRightOf(i - i2, z);
        TextLine.recycle(obtain2);
        return offsetToLeftRightOf2;
    }

    private int getOffsetAtStartOf(int i) {
        char charAt;
        if (i == 0) {
            return 0;
        }
        CharSequence charSequence = this.mText;
        char charAt2 = charSequence.charAt(i);
        if (charAt2 >= 56320 && charAt2 <= 57343 && (charAt = charSequence.charAt(i - 1)) >= 55296 && charAt <= 56319) {
            i--;
        }
        if (this.mSpannedText) {
            Spanned spanned = (Spanned) charSequence;
            ReplacementSpan[] replacementSpanArr = (ReplacementSpan[]) spanned.getSpans(i, i, ReplacementSpan.class);
            for (int i2 = 0; i2 < replacementSpanArr.length; i2++) {
                int spanStart = spanned.getSpanStart(replacementSpanArr[i2]);
                int spanEnd = spanned.getSpanEnd(replacementSpanArr[i2]);
                if (spanStart < i && spanEnd > i) {
                    i = spanStart;
                }
            }
        }
        return i;
    }

    public boolean shouldClampCursor(int i) {
        int ordinal = getParagraphAlignment(i).ordinal();
        return ordinal != 0 ? ordinal == 3 : getParagraphDirection(i) > 0;
    }

    public void getCursorPath(int i, Path path, CharSequence charSequence) {
        path.reset();
        int lineForOffset = getLineForOffset(i);
        int lineTop = getLineTop(lineForOffset);
        int i2 = 0;
        int lineBottom = getLineBottom(lineForOffset, false);
        float primaryHorizontal = getPrimaryHorizontal(i, shouldClampCursor(lineForOffset)) - 0.5f;
        int metaState = TextKeyListener.getMetaState(charSequence, 1) | TextKeyListener.getMetaState(charSequence, 2048);
        int metaState2 = TextKeyListener.getMetaState(charSequence, 2);
        if (metaState != 0 || metaState2 != 0) {
            i2 = (lineBottom - lineTop) >> 2;
            if (metaState2 != 0) {
                lineTop += i2;
            }
            if (metaState != 0) {
                lineBottom -= i2;
            }
        }
        if (primaryHorizontal < 0.5f) {
            primaryHorizontal = 0.5f;
        }
        float f = lineTop;
        path.moveTo(primaryHorizontal, f);
        float f2 = lineBottom;
        path.lineTo(primaryHorizontal, f2);
        if (metaState == 2) {
            path.moveTo(primaryHorizontal, f2);
            float f3 = i2;
            float f4 = lineBottom + i2;
            path.lineTo(primaryHorizontal - f3, f4);
            path.lineTo(primaryHorizontal, f2);
            path.lineTo(f3 + primaryHorizontal, f4);
        } else if (metaState == 1) {
            path.moveTo(primaryHorizontal, f2);
            float f5 = i2;
            float f6 = primaryHorizontal - f5;
            float f7 = lineBottom + i2;
            path.lineTo(f6, f7);
            float f8 = f7 - 0.5f;
            path.moveTo(f6, f8);
            float f9 = f5 + primaryHorizontal;
            path.lineTo(f9, f8);
            path.moveTo(f9, f7);
            path.lineTo(primaryHorizontal, f2);
        }
        if (metaState2 == 2) {
            path.moveTo(primaryHorizontal, f);
            float f10 = i2;
            float f11 = lineTop - i2;
            path.lineTo(primaryHorizontal - f10, f11);
            path.lineTo(primaryHorizontal, f);
            path.lineTo(primaryHorizontal + f10, f11);
            return;
        }
        if (metaState2 == 1) {
            path.moveTo(primaryHorizontal, f);
            float f12 = i2;
            float f13 = primaryHorizontal - f12;
            float f14 = lineTop - i2;
            path.lineTo(f13, f14);
            float f15 = 0.5f + f14;
            path.moveTo(f13, f15);
            float f16 = f12 + primaryHorizontal;
            path.lineTo(f16, f15);
            path.moveTo(f16, f14);
            path.lineTo(primaryHorizontal, f);
        }
    }

    private void addSelection(int i, int i2, int i3, int i4, int i5, SelectionRectangleConsumer selectionRectangleConsumer) {
        int max;
        int min;
        int lineStart = getLineStart(i);
        int lineEnd = getLineEnd(i);
        Directions lineDirections = getLineDirections(i);
        if (lineEnd > lineStart && this.mText.charAt(lineEnd - 1) == '\n') {
            lineEnd--;
        }
        for (int i6 = 0; i6 < lineDirections.mDirections.length; i6 += 2) {
            int i7 = lineDirections.mDirections[i6] + lineStart;
            int i8 = i6 + 1;
            int i9 = (lineDirections.mDirections[i8] & RUN_LENGTH_MASK) + i7;
            if (i9 > lineEnd) {
                i9 = lineEnd;
            }
            if (i2 <= i9 && i3 >= i7 && (max = Math.max(i2, i7)) != (min = Math.min(i3, i9))) {
                float horizontal = getHorizontal(max, false, i, false);
                float horizontal2 = getHorizontal(min, true, i, false);
                selectionRectangleConsumer.accept(Math.min(horizontal, horizontal2), i4, Math.max(horizontal, horizontal2), i5, (lineDirections.mDirections[i8] & 67108864) != 0 ? 0 : 1);
            }
        }
    }

    public void getSelectionPath(int i, int i2, final Path path) {
        path.reset();
        getSelection(i, i2, new SelectionRectangleConsumer() { // from class: android.text.Layout$$ExternalSyntheticLambda0
            @Override // android.text.Layout.SelectionRectangleConsumer
            public final void accept(float f, float f2, float f3, float f4, int i3) {
                Path.this.addRect(f, f2, f3, f4, Path.Direction.CW);
            }
        });
    }

    public final void getSelection(int i, int i2, SelectionRectangleConsumer selectionRectangleConsumer) {
        int i3;
        int i4;
        float f;
        if (i == i2) {
            return;
        }
        if (i2 < i) {
            i4 = i;
            i3 = i2;
        } else {
            i3 = i;
            i4 = i2;
        }
        int lineForOffset = getLineForOffset(i3);
        int lineForOffset2 = getLineForOffset(i4);
        int lineTop = getLineTop(lineForOffset);
        int lineBottom = getLineBottom(lineForOffset2, false);
        if (lineForOffset == lineForOffset2) {
            addSelection(lineForOffset, i3, i4, lineTop, lineBottom, selectionRectangleConsumer);
            return;
        }
        int i5 = i4;
        float f2 = this.mWidth;
        addSelection(lineForOffset, i3, getLineEnd(lineForOffset), lineTop, getLineBottom(lineForOffset), selectionRectangleConsumer);
        if (getParagraphDirection(lineForOffset) == -1) {
            selectionRectangleConsumer.accept(getLineLeft(lineForOffset), lineTop, 0.0f, getLineBottom(lineForOffset), 0);
            f = f2;
        } else {
            f = f2;
            selectionRectangleConsumer.accept(getLineRight(lineForOffset), lineTop, f, getLineBottom(lineForOffset), 1);
        }
        int i6 = lineForOffset + 1;
        while (i6 < lineForOffset2) {
            int lineTop2 = getLineTop(i6);
            int lineBottom2 = getLineBottom(i6);
            if (getParagraphDirection(i6) == -1) {
                selectionRectangleConsumer.accept(0.0f, lineTop2, f, lineBottom2, 0);
            } else {
                selectionRectangleConsumer.accept(0.0f, lineTop2, f, lineBottom2, 1);
            }
            i6++;
            f = f;
        }
        float f3 = f;
        int lineTop3 = getLineTop(lineForOffset2);
        int lineBottom3 = getLineBottom(lineForOffset2, false);
        addSelection(lineForOffset2, getLineStart(lineForOffset2), i5, lineTop3, lineBottom3, selectionRectangleConsumer);
        if (getParagraphDirection(lineForOffset2) == -1) {
            selectionRectangleConsumer.accept(f3, lineTop3, getLineRight(lineForOffset2), lineBottom3, 0);
        } else {
            selectionRectangleConsumer.accept(0.0f, lineTop3, getLineLeft(lineForOffset2), lineBottom3, 1);
        }
    }

    public final Alignment getParagraphAlignment(int i) {
        AlignmentSpan[] alignmentSpanArr;
        int length;
        return (!this.mSpannedText || (length = (alignmentSpanArr = (AlignmentSpan[]) getParagraphSpans((Spanned) this.mText, getLineStart(i), getLineEnd(i), AlignmentSpan.class)).length) <= 0) ? this.mAlignment : alignmentSpanArr[length - 1].getAlignment();
    }

    public final int getParagraphLeft(int i) {
        if (getParagraphDirection(i) == -1 || !this.mSpannedText) {
            return 0;
        }
        return getParagraphLeadingMargin(i);
    }

    public final int getParagraphRight(int i) {
        int i2 = this.mWidth;
        return (getParagraphDirection(i) == 1 || !this.mSpannedText) ? i2 : i2 - getParagraphLeadingMargin(i);
    }

    private int getParagraphLeadingMargin(int i) {
        if (!this.mSpannedText) {
            return 0;
        }
        Spanned spanned = (Spanned) this.mText;
        int lineStart = getLineStart(i);
        LeadingMarginSpan[] leadingMarginSpanArr = (LeadingMarginSpan[]) getParagraphSpans(spanned, lineStart, spanned.nextSpanTransition(lineStart, getLineEnd(i), LeadingMarginSpan.class), LeadingMarginSpan.class);
        if (leadingMarginSpanArr.length == 0) {
            return 0;
        }
        boolean z = lineStart == 0 || spanned.charAt(lineStart - 1) == '\n';
        for (int i2 = 0; i2 < leadingMarginSpanArr.length; i2++) {
            LeadingMarginSpan leadingMarginSpan = leadingMarginSpanArr[i2];
            if (leadingMarginSpan instanceof LeadingMarginSpan.LeadingMarginSpan2) {
                z |= i < getLineForOffset(spanned.getSpanStart(leadingMarginSpan)) + ((LeadingMarginSpan.LeadingMarginSpan2) leadingMarginSpanArr[i2]).getLeadingMarginLineCount();
            }
        }
        int i3 = 0;
        for (LeadingMarginSpan leadingMarginSpan2 : leadingMarginSpanArr) {
            i3 += leadingMarginSpan2.getLeadingMargin(z);
        }
        return i3;
    }

    private static float measurePara(TextPaint textPaint, CharSequence charSequence, int i, int i2, TextDirectionHeuristic textDirectionHeuristic, boolean z) {
        MeasuredParagraph buildForBidi;
        int i3;
        TabStops tabStops;
        boolean z2;
        TextLine obtain = TextLine.obtain();
        MeasuredParagraph measuredParagraph = null;
        try {
            buildForBidi = MeasuredParagraph.buildForBidi(charSequence, i, i2, textDirectionHeuristic, null);
        } catch (Throwable th) {
            th = th;
        }
        try {
            char[] chars = buildForBidi.getChars();
            int length = chars.length;
            Directions directions = buildForBidi.getDirections(0, length);
            int paragraphDir = buildForBidi.getParagraphDir();
            if (charSequence instanceof Spanned) {
                i3 = 0;
                for (LeadingMarginSpan leadingMarginSpan : (LeadingMarginSpan[]) getParagraphSpans((Spanned) charSequence, i, i2, LeadingMarginSpan.class)) {
                    i3 += leadingMarginSpan.getLeadingMargin(true);
                }
            } else {
                i3 = 0;
            }
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    tabStops = null;
                    z2 = false;
                    break;
                }
                if (chars[i4] != '\t') {
                    i4++;
                } else if (charSequence instanceof Spanned) {
                    Spanned spanned = (Spanned) charSequence;
                    TabStopSpan[] tabStopSpanArr = (TabStopSpan[]) getParagraphSpans(spanned, i, spanned.nextSpanTransition(i, i2, TabStopSpan.class), TabStopSpan.class);
                    tabStops = tabStopSpanArr.length > 0 ? new TabStops(TAB_INCREMENT, tabStopSpanArr) : null;
                    z2 = true;
                } else {
                    z2 = true;
                    tabStops = null;
                }
            }
            obtain.set(textPaint, charSequence, i, i2, paragraphDir, directions, z2, tabStops, 0, 0, false);
            float abs = i3 + Math.abs(obtain.metrics(null, null, z, null));
            TextLine.recycle(obtain);
            if (buildForBidi != null) {
                buildForBidi.recycle();
            }
            return abs;
        } catch (Throwable th2) {
            th = th2;
            measuredParagraph = buildForBidi;
            TextLine.recycle(obtain);
            if (measuredParagraph != null) {
                measuredParagraph.recycle();
            }
            throw th;
        }
    }

    public static class TabStops {
        private float mIncrement;
        private int mNumStops;
        private float[] mStops;

        public static float nextDefaultStop(float f, float f2) {
            return ((int) ((f + f2) / f2)) * f2;
        }

        public TabStops(float f, Object[] objArr) {
            reset(f, objArr);
        }

        void reset(float f, Object[] objArr) {
            this.mIncrement = f;
            int i = 0;
            if (objArr != null) {
                float[] fArr = this.mStops;
                int i2 = 0;
                for (Object obj : objArr) {
                    if (obj instanceof TabStopSpan) {
                        if (fArr == null) {
                            fArr = new float[10];
                        } else if (i2 == fArr.length) {
                            float[] fArr2 = new float[i2 * 2];
                            for (int i3 = 0; i3 < i2; i3++) {
                                fArr2[i3] = fArr[i3];
                            }
                            fArr = fArr2;
                        }
                        fArr[i2] = ((TabStopSpan) r4).getTabStop();
                        i2++;
                    }
                }
                if (i2 > 1) {
                    Arrays.sort(fArr, 0, i2);
                }
                if (fArr != this.mStops) {
                    this.mStops = fArr;
                }
                i = i2;
            }
            this.mNumStops = i;
        }

        float nextTab(float f) {
            int i = this.mNumStops;
            if (i > 0) {
                float[] fArr = this.mStops;
                for (int i2 = 0; i2 < i; i2++) {
                    float f2 = fArr[i2];
                    if (f2 > f) {
                        return f2;
                    }
                }
            }
            return nextDefaultStop(f, this.mIncrement);
        }
    }

    static float nextTab(CharSequence charSequence, int i, int i2, float f, Object[] objArr) {
        boolean z;
        if (charSequence instanceof Spanned) {
            if (objArr == null) {
                objArr = getParagraphSpans((Spanned) charSequence, i, i2, TabStopSpan.class);
                z = true;
            } else {
                z = false;
            }
            float f2 = Float.MAX_VALUE;
            for (int i3 = 0; i3 < objArr.length; i3++) {
                if (z || (objArr[i3] instanceof TabStopSpan)) {
                    float tabStop = ((TabStopSpan) objArr[i3]).getTabStop();
                    if (tabStop < f2 && tabStop > f) {
                        f2 = tabStop;
                    }
                }
            }
            if (f2 != Float.MAX_VALUE) {
                return f2;
            }
        }
        return ((int) ((f + TAB_INCREMENT) / TAB_INCREMENT)) * TAB_INCREMENT;
    }

    protected final boolean isSpanned() {
        return this.mSpannedText;
    }

    static <T> T[] getParagraphSpans(Spanned spanned, int i, int i2, Class<T> cls) {
        if (i == i2 && i > 0) {
            return (T[]) ArrayUtils.emptyArray(cls);
        }
        if (spanned instanceof SpannableStringBuilder) {
            return (T[]) ((SpannableStringBuilder) spanned).getSpans(i, i2, cls, false);
        }
        return (T[]) spanned.getSpans(i, i2, cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ellipsize(int i, int i2, int i3, char[] cArr, int i4, TextUtils.TruncateAt truncateAt) {
        int ellipsisCount = getEllipsisCount(i3);
        if (ellipsisCount == 0) {
            return;
        }
        int ellipsisStart = getEllipsisStart(i3);
        int lineStart = getLineStart(i3);
        String ellipsisString = TextUtils.getEllipsisString(truncateAt);
        int length = ellipsisString.length();
        boolean z = ellipsisCount >= length;
        int max = Math.max(0, (i - ellipsisStart) - lineStart);
        int min = Math.min(ellipsisCount, (i2 - ellipsisStart) - lineStart);
        while (max < min) {
            cArr[(((max + ellipsisStart) + lineStart) + i4) - i] = (!z || max >= length) ? (char) 65279 : ellipsisString.charAt(max);
            max++;
        }
    }

    public static class Directions {
        public int[] mDirections;

        public Directions(int[] iArr) {
            this.mDirections = iArr;
        }

        public int getRunCount() {
            return this.mDirections.length / 2;
        }

        public int getRunStart(int i) {
            return this.mDirections[i * 2];
        }

        public int getRunLength(int i) {
            return this.mDirections[(i * 2) + 1] & Layout.RUN_LENGTH_MASK;
        }

        public int getRunLevel(int i) {
            return (this.mDirections[(i * 2) + 1] >>> 26) & 63;
        }

        public boolean isRunRtl(int i) {
            return (this.mDirections[(i * 2) + 1] & 67108864) != 0;
        }
    }

    static class Ellipsizer implements CharSequence, GetChars {
        Layout mLayout;
        TextUtils.TruncateAt mMethod;
        CharSequence mText;
        int mWidth;

        public Ellipsizer(CharSequence charSequence) {
            this.mText = charSequence;
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            char[] obtain = TextUtils.obtain(1);
            getChars(i, i + 1, obtain, 0);
            char c = obtain[0];
            TextUtils.recycle(obtain);
            return c;
        }

        @Override // android.text.GetChars
        public void getChars(int i, int i2, char[] cArr, int i3) {
            int lineForOffset = this.mLayout.getLineForOffset(i);
            int lineForOffset2 = this.mLayout.getLineForOffset(i2);
            TextUtils.getChars(this.mText, i, i2, cArr, i3);
            for (int i4 = lineForOffset; i4 <= lineForOffset2; i4++) {
                this.mLayout.ellipsize(i, i2, i4, cArr, i3, this.mMethod);
            }
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mText.length();
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            char[] cArr = new char[i2 - i];
            getChars(i, i2, cArr, 0);
            return new String(cArr);
        }

        @Override // java.lang.CharSequence
        public String toString() {
            char[] cArr = new char[length()];
            getChars(0, length(), cArr, 0);
            return new String(cArr);
        }
    }

    static class SpannedEllipsizer extends Ellipsizer implements Spanned {
        private Spanned mSpanned;

        public SpannedEllipsizer(CharSequence charSequence) {
            super(charSequence);
            this.mSpanned = (Spanned) charSequence;
        }

        @Override // android.text.Spanned
        public <T> T[] getSpans(int i, int i2, Class<T> cls) {
            return (T[]) this.mSpanned.getSpans(i, i2, cls);
        }

        @Override // android.text.Spanned
        public int getSpanStart(Object obj) {
            return this.mSpanned.getSpanStart(obj);
        }

        @Override // android.text.Spanned
        public int getSpanEnd(Object obj) {
            return this.mSpanned.getSpanEnd(obj);
        }

        @Override // android.text.Spanned
        public int getSpanFlags(Object obj) {
            return this.mSpanned.getSpanFlags(obj);
        }

        @Override // android.text.Spanned
        public int nextSpanTransition(int i, int i2, Class cls) {
            return this.mSpanned.nextSpanTransition(i, i2, cls);
        }

        @Override // android.text.Layout.Ellipsizer, java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            char[] cArr = new char[i2 - i];
            getChars(i, i2, cArr, 0);
            SpannableString spannableString = new SpannableString(new String(cArr));
            TextUtils.copySpansFrom(this.mSpanned, i, i2, Object.class, spannableString, 0);
            return spannableString;
        }
    }

    public static final class Builder {
        private int mEllipsizedWidth;
        private final int mEnd;
        private Paint.FontMetrics mMinimumFontMetrics;
        private final TextPaint mPaint;
        private boolean mShiftDrawingOffsetForStartOverhang;
        private final int mStart;
        private final CharSequence mText;
        private boolean mUseBoundsForWidth;
        private final int mWidth;
        private Alignment mAlignment = Alignment.ALIGN_NORMAL;
        private float mSpacingMult = 1.0f;
        private float mSpacingAdd = 0.0f;
        private TextDirectionHeuristic mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
        private boolean mIncludePad = true;
        private boolean mFallbackLineSpacing = false;
        private TextUtils.TruncateAt mEllipsize = null;
        private int mMaxLines = Integer.MAX_VALUE;
        private int mBreakStrategy = 0;
        private int mHyphenationFrequency = 0;
        private int[] mLeftIndents = null;
        private int[] mRightIndents = null;
        private int mJustificationMode = 0;
        private LineBreakConfig mLineBreakConfig = LineBreakConfig.NONE;

        public Builder(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3) {
            this.mText = charSequence;
            this.mStart = i;
            this.mEnd = i2;
            this.mPaint = textPaint;
            this.mWidth = i3;
            this.mEllipsizedWidth = i3;
        }

        public Builder setAlignment(Alignment alignment) {
            this.mAlignment = alignment;
            return this;
        }

        public Builder setTextDirectionHeuristic(TextDirectionHeuristic textDirectionHeuristic) {
            this.mTextDir = textDirectionHeuristic;
            return this;
        }

        public Builder setLineSpacingAmount(float f) {
            this.mSpacingAdd = f;
            return this;
        }

        public Builder setLineSpacingMultiplier(float f) {
            this.mSpacingMult = f;
            return this;
        }

        public Builder setFontPaddingIncluded(boolean z) {
            this.mIncludePad = z;
            return this;
        }

        public Builder setFallbackLineSpacingEnabled(boolean z) {
            this.mFallbackLineSpacing = z;
            return this;
        }

        public Builder setEllipsizedWidth(int i) {
            this.mEllipsizedWidth = i;
            return this;
        }

        public Builder setEllipsize(TextUtils.TruncateAt truncateAt) {
            this.mEllipsize = truncateAt;
            return this;
        }

        public Builder setMaxLines(int i) {
            this.mMaxLines = i;
            return this;
        }

        public Builder setBreakStrategy(int i) {
            this.mBreakStrategy = i;
            return this;
        }

        public Builder setHyphenationFrequency(int i) {
            this.mHyphenationFrequency = i;
            return this;
        }

        public Builder setLeftIndents(int[] iArr) {
            this.mLeftIndents = iArr;
            return this;
        }

        public Builder setRightIndents(int[] iArr) {
            this.mRightIndents = iArr;
            return this;
        }

        public Builder setJustificationMode(int i) {
            this.mJustificationMode = i;
            return this;
        }

        public Builder setLineBreakConfig(LineBreakConfig lineBreakConfig) {
            this.mLineBreakConfig = lineBreakConfig;
            return this;
        }

        public Builder setUseBoundsForWidth(boolean z) {
            this.mUseBoundsForWidth = z;
            return this;
        }

        public Builder setShiftDrawingOffsetForStartOverhang(boolean z) {
            this.mShiftDrawingOffsetForStartOverhang = z;
            return this;
        }

        public Builder setMinimumFontMetrics(Paint.FontMetrics fontMetrics) {
            this.mMinimumFontMetrics = fontMetrics;
            return this;
        }

        private BoringLayout.Metrics isBoring() {
            BoringLayout.Metrics isBoring;
            if (this.mStart != 0 || this.mEnd != this.mText.length() || (isBoring = BoringLayout.isBoring(this.mText, this.mPaint, this.mTextDir, this.mFallbackLineSpacing, this.mMinimumFontMetrics, null)) == null) {
                return null;
            }
            if (isBoring.width > this.mWidth && this.mEllipsize == null) {
                return null;
            }
            return isBoring;
        }

        public Layout build() {
            BoringLayout.Metrics isBoring = isBoring();
            if (isBoring == null) {
                return StaticLayout.Builder.obtain(this.mText, this.mStart, this.mEnd, this.mPaint, this.mWidth).setAlignment(this.mAlignment).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setTextDirection(this.mTextDir).setIncludePad(this.mIncludePad).setUseLineSpacingFromFallbacks(this.mFallbackLineSpacing).setEllipsizedWidth(this.mEllipsizedWidth).setEllipsize(this.mEllipsize).setMaxLines(this.mMaxLines).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setIndents(this.mLeftIndents, this.mRightIndents).setJustificationMode(this.mJustificationMode).setLineBreakConfig(this.mLineBreakConfig).setUseBoundsForWidth(this.mUseBoundsForWidth).setShiftDrawingOffsetForStartOverhang(this.mShiftDrawingOffsetForStartOverhang).build();
            }
            return new BoringLayout(this.mText, this.mPaint, this.mWidth, this.mAlignment, this.mTextDir, this.mSpacingMult, this.mSpacingAdd, this.mIncludePad, this.mFallbackLineSpacing, this.mEllipsizedWidth, this.mEllipsize, this.mMaxLines, this.mBreakStrategy, this.mHyphenationFrequency, this.mLeftIndents, this.mRightIndents, this.mJustificationMode, this.mLineBreakConfig, isBoring, this.mUseBoundsForWidth, this.mShiftDrawingOffsetForStartOverhang, this.mMinimumFontMetrics);
        }
    }

    public final CharSequence getText() {
        return this.mText;
    }

    public final TextPaint getPaint() {
        return this.mPaint;
    }

    public final int getWidth() {
        return this.mWidth;
    }

    public final Alignment getAlignment() {
        return this.mAlignment;
    }

    public final TextDirectionHeuristic getTextDirectionHeuristic() {
        return this.mTextDir;
    }

    public final float getSpacingMultiplier() {
        return getLineSpacingMultiplier();
    }

    public final float getLineSpacingMultiplier() {
        return this.mSpacingMult;
    }

    public final float getSpacingAdd() {
        return getLineSpacingAmount();
    }

    public final float getLineSpacingAmount() {
        return this.mSpacingAdd;
    }

    public final boolean isFontPaddingIncluded() {
        return this.mIncludePad;
    }

    public boolean isFallbackLineSpacingEnabled() {
        return this.mFallbackLineSpacing;
    }

    public int getEllipsizedWidth() {
        return this.mEllipsizedWidth;
    }

    public final TextUtils.TruncateAt getEllipsize() {
        return this.mEllipsize;
    }

    public final int getMaxLines() {
        return this.mMaxLines;
    }

    public final int getBreakStrategy() {
        return this.mBreakStrategy;
    }

    public final int getHyphenationFrequency() {
        return this.mHyphenationFrequency;
    }

    public final int[] getLeftIndents() {
        int[] iArr = this.mLeftIndents;
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        int[] iArr2 = new int[length];
        System.arraycopy(iArr, 0, iArr2, 0, length);
        return iArr2;
    }

    public final int[] getRightIndents() {
        int[] iArr = this.mRightIndents;
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        int[] iArr2 = new int[length];
        System.arraycopy(iArr, 0, iArr2, 0, length);
        return iArr2;
    }

    public final int getJustificationMode() {
        return this.mJustificationMode;
    }

    public LineBreakConfig getLineBreakConfig() {
        return this.mLineBreakConfig;
    }

    public boolean getUseBoundsForWidth() {
        return this.mUseBoundsForWidth;
    }

    public boolean getShiftDrawingOffsetForStartOverhang() {
        return this.mShiftDrawingOffsetForStartOverhang;
    }

    public Paint.FontMetrics getMinimumFontMetrics() {
        return this.mMinimumFontMetrics;
    }

    public void addSelectionPath(int i, int i2, final Path path) {
        if (!ViewRune.WIDGET_MULTIPLE_PEN_TEXT_SUPPORTED) {
            path.reset();
        }
        getSelection(i, i2, new SelectionRectangleConsumer() { // from class: android.text.Layout$$ExternalSyntheticLambda1
            @Override // android.text.Layout.SelectionRectangleConsumer
            public final void accept(float f, float f2, float f3, float f4, int i3) {
                Path.this.addRect(f, f2 + 1.0f, f3, f4 - 1.0f, Path.Direction.CW);
            }
        });
    }

    public void getSelectionRect(int i, int i2, int i3, int i4, int i5, Rect rect) {
        int max;
        int min;
        int lineStart = getLineStart(i);
        int lineEnd = getLineEnd(i);
        Directions lineDirections = getLineDirections(i);
        if (lineEnd > lineStart && this.mText.charAt(lineEnd - 1) == '\n') {
            lineEnd--;
        }
        for (int i6 = 0; i6 < lineDirections.mDirections.length; i6 += 2) {
            int i7 = lineDirections.mDirections[i6] + lineStart;
            int i8 = (lineDirections.mDirections[i6 + 1] & RUN_LENGTH_MASK) + i7;
            if (i8 > lineEnd) {
                i8 = lineEnd;
            }
            if (i2 <= i8 && i3 >= i7 && (max = Math.max(i2, i7)) != (min = Math.min(i3, i8))) {
                float horizontal = getHorizontal(max, false, i, false);
                float horizontal2 = getHorizontal(min, true, i, false);
                rect.set((int) Math.min(horizontal, horizontal2), i4, (int) Math.max(horizontal, horizontal2), i5);
            }
        }
    }
}
