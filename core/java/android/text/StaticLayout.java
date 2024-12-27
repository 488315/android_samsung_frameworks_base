package android.text;

import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.text.LineBreakConfig;
import android.hardware.scontext.SContextConstants;
import android.os.Trace;
import android.text.Layout;
import android.text.TextUtils;
import android.text.style.LineHeightSpan;
import android.util.Log;
import android.util.Pools;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;

/* loaded from: classes4.dex */
public class StaticLayout extends Layout {
    private static final char CHAR_NEW_LINE = '\n';
    private static final int COLUMNS_ELLIPSIZE = 7;
    private static final int COLUMNS_NORMAL = 5;
    private static final int DEFAULT_MAX_LINE_HEIGHT = -1;
    private static final int DESCENT = 2;
    private static final int DIR = 0;
    private static final int DIR_SHIFT = 30;
    private static final int ELLIPSIS_COUNT = 6;
    private static final int ELLIPSIS_START = 5;
    private static final int END_HYPHEN_MASK = 7;
    private static final int EXTRA = 3;
    private static final double EXTRA_ROUNDING = 0.5d;
    private static final int HYPHEN = 4;
    private static final int HYPHEN_MASK = 255;
    private static final int START = 0;
    private static final int START_HYPHEN_BITS_SHIFT = 3;
    private static final int START_HYPHEN_MASK = 24;
    private static final int START_MASK = 536870911;
    private static final int TAB = 0;
    private static final float TAB_INCREMENT = 20.0f;
    private static final int TAB_MASK = 536870912;
    static final String TAG = "StaticLayout";
    private static final int TOP = 1;
    private int mBottomPadding;
    private int mColumns;
    private RectF mDrawingBounds;
    private boolean mEllipsized;
    private int[] mLeftIndents;
    private int mLineCount;
    private Layout.Directions[] mLineDirections;
    private int[] mLines;
    private int mMaxLineHeight;
    private int mMaximumVisibleLineCount;
    private int[] mRightIndents;
    private int mTopPadding;

    public static final class Builder {
        private static final Pools.SynchronizedPool<Builder> sPool = new Pools.SynchronizedPool<>(3);
        private boolean mAddLastLineLineSpacing;
        private Layout.Alignment mAlignment;
        private int mBreakStrategy;
        private boolean mCalculateBounds;
        private TextUtils.TruncateAt mEllipsize;
        private int mEllipsizedWidth;
        private int mEnd;
        private boolean mFallbackLineSpacing;
        private int mHyphenationFrequency;
        private boolean mIncludePad;
        private int mJustificationMode;
        private int[] mLeftIndents;
        private int mMaxLines;
        private Paint.FontMetrics mMinimumFontMetrics;
        private TextPaint mPaint;
        private int[] mRightIndents;
        private boolean mShiftDrawingOffsetForStartOverhang;
        private float mSpacingAdd;
        private float mSpacingMult;
        private int mStart;
        private CharSequence mText;
        private TextDirectionHeuristic mTextDir;
        private boolean mUseBoundsForWidth;
        private int mWidth;
        private LineBreakConfig mLineBreakConfig = LineBreakConfig.NONE;
        private final Paint.FontMetricsInt mFontMetricsInt = new Paint.FontMetricsInt();

        private Builder() {
        }

        public static Builder obtain(CharSequence source, int start, int end, TextPaint paint, int width) {
            Builder b = sPool.acquire();
            if (b == null) {
                b = new Builder();
            }
            b.mText = source;
            b.mStart = start;
            b.mEnd = end;
            b.mPaint = paint;
            b.mWidth = width;
            b.mAlignment = Layout.Alignment.ALIGN_NORMAL;
            b.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
            b.mSpacingMult = 1.0f;
            b.mSpacingAdd = 0.0f;
            b.mIncludePad = true;
            b.mFallbackLineSpacing = false;
            b.mEllipsizedWidth = width;
            b.mEllipsize = null;
            b.mMaxLines = Integer.MAX_VALUE;
            b.mBreakStrategy = 0;
            b.mHyphenationFrequency = 0;
            b.mJustificationMode = 0;
            b.mLineBreakConfig = LineBreakConfig.NONE;
            b.mMinimumFontMetrics = null;
            return b;
        }

        private static void recycle(Builder b) {
            b.mPaint = null;
            b.mText = null;
            b.mLeftIndents = null;
            b.mRightIndents = null;
            b.mMinimumFontMetrics = null;
            sPool.release(b);
        }

        void finish() {
            this.mText = null;
            this.mPaint = null;
            this.mLeftIndents = null;
            this.mRightIndents = null;
            this.mMinimumFontMetrics = null;
        }

        public Builder setText(CharSequence source) {
            return setText(source, 0, source.length());
        }

        public Builder setText(CharSequence source, int start, int end) {
            this.mText = source;
            this.mStart = start;
            this.mEnd = end;
            return this;
        }

        public Builder setPaint(TextPaint paint) {
            this.mPaint = paint;
            return this;
        }

        public Builder setWidth(int width) {
            this.mWidth = width;
            if (this.mEllipsize == null) {
                this.mEllipsizedWidth = width;
            }
            return this;
        }

        public Builder setAlignment(Layout.Alignment alignment) {
            this.mAlignment = alignment;
            return this;
        }

        public Builder setTextDirection(TextDirectionHeuristic textDir) {
            this.mTextDir = textDir;
            return this;
        }

        public Builder setLineSpacing(float spacingAdd, float spacingMult) {
            this.mSpacingAdd = spacingAdd;
            this.mSpacingMult = spacingMult;
            return this;
        }

        public Builder setIncludePad(boolean includePad) {
            this.mIncludePad = includePad;
            return this;
        }

        public Builder setUseLineSpacingFromFallbacks(boolean useLineSpacingFromFallbacks) {
            this.mFallbackLineSpacing = useLineSpacingFromFallbacks;
            return this;
        }

        public Builder setEllipsizedWidth(int ellipsizedWidth) {
            this.mEllipsizedWidth = ellipsizedWidth;
            return this;
        }

        public Builder setEllipsize(TextUtils.TruncateAt ellipsize) {
            this.mEllipsize = ellipsize;
            return this;
        }

        public Builder setMaxLines(int maxLines) {
            this.mMaxLines = maxLines;
            return this;
        }

        public Builder setBreakStrategy(int breakStrategy) {
            this.mBreakStrategy = breakStrategy;
            return this;
        }

        public Builder setHyphenationFrequency(int hyphenationFrequency) {
            this.mHyphenationFrequency = hyphenationFrequency;
            return this;
        }

        public Builder setIndents(int[] leftIndents, int[] rightIndents) {
            this.mLeftIndents = leftIndents;
            this.mRightIndents = rightIndents;
            return this;
        }

        public Builder setJustificationMode(int justificationMode) {
            this.mJustificationMode = justificationMode;
            return this;
        }

        Builder setAddLastLineLineSpacing(boolean value) {
            this.mAddLastLineLineSpacing = value;
            return this;
        }

        public Builder setLineBreakConfig(LineBreakConfig lineBreakConfig) {
            this.mLineBreakConfig = lineBreakConfig;
            return this;
        }

        public Builder setUseBoundsForWidth(boolean useBoundsForWidth) {
            this.mUseBoundsForWidth = useBoundsForWidth;
            return this;
        }

        public Builder setShiftDrawingOffsetForStartOverhang(boolean shiftDrawingOffsetForStartOverhang) {
            this.mShiftDrawingOffsetForStartOverhang = shiftDrawingOffsetForStartOverhang;
            return this;
        }

        public Builder setCalculateBounds(boolean value) {
            this.mCalculateBounds = value;
            return this;
        }

        public Builder setMinimumFontMetrics(Paint.FontMetrics minimumFontMetrics) {
            this.mMinimumFontMetrics = minimumFontMetrics;
            return this;
        }

        public StaticLayout build() {
            StaticLayout result = new StaticLayout(this, this.mIncludePad, this.mEllipsize != null ? 7 : 5);
            recycle(this);
            return result;
        }

        StaticLayout buildPartialStaticLayoutForDynamicLayout(boolean trackpadding, StaticLayout recycle) {
            if (recycle == null) {
                recycle = new StaticLayout();
            }
            Trace.beginSection("Generating StaticLayout For DynamicLayout");
            try {
                recycle.generate(this, this.mIncludePad, trackpadding);
                return recycle;
            } finally {
                Trace.endSection();
            }
        }
    }

    private StaticLayout() {
        super(null, null, 0, null, null, 1.0f, 0.0f, false, false, 0, null, 1, 0, 0, null, null, 0, null, false, false, null);
        this.mDrawingBounds = null;
        this.mMaxLineHeight = -1;
        this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
        this.mColumns = 7;
        this.mLineDirections = (Layout.Directions[]) ArrayUtils.newUnpaddedArray(Layout.Directions.class, 2);
        this.mLines = ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
    }

    @Deprecated
    public StaticLayout(CharSequence source, TextPaint paint, int width, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad) {
        this(source, 0, source.length(), paint, width, align, spacingmult, spacingadd, includepad);
    }

    @Deprecated
    public StaticLayout(CharSequence source, int bufstart, int bufend, TextPaint paint, int outerwidth, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad) {
        this(source, bufstart, bufend, paint, outerwidth, align, spacingmult, spacingadd, includepad, null, 0);
    }

    @Deprecated
    public StaticLayout(CharSequence source, int bufstart, int bufend, TextPaint paint, int outerwidth, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad, TextUtils.TruncateAt ellipsize, int ellipsizedWidth) {
        this(source, bufstart, bufend, paint, outerwidth, align, TextDirectionHeuristics.FIRSTSTRONG_LTR, spacingmult, spacingadd, includepad, ellipsize, ellipsizedWidth, Integer.MAX_VALUE);
    }

    @Deprecated
    public StaticLayout(CharSequence source, int bufstart, int bufend, TextPaint paint, int outerwidth, Layout.Alignment align, TextDirectionHeuristic textDir, float spacingmult, float spacingadd, boolean includepad, TextUtils.TruncateAt ellipsize, int ellipsizedWidth, int maxLines) {
        this(Builder.obtain(source, bufstart, bufend, paint, outerwidth).setAlignment(align).setTextDirection(textDir).setLineSpacing(spacingadd, spacingmult).setIncludePad(includepad).setEllipsize(ellipsize).setEllipsizedWidth(ellipsizedWidth).setMaxLines(maxLines), includepad, ellipsize != null ? 7 : 5);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private StaticLayout(android.text.StaticLayout.Builder r24, boolean r25, int r26) {
        /*
            r23 = this;
            r2 = r23
            android.text.TextUtils$TruncateAt r0 = android.text.StaticLayout.Builder.m5153$$Nest$fgetmEllipsize(r24)
            if (r0 != 0) goto Ld
            java.lang.CharSequence r0 = android.text.StaticLayout.Builder.m5171$$Nest$fgetmText(r24)
            goto L28
        Ld:
            java.lang.CharSequence r0 = android.text.StaticLayout.Builder.m5171$$Nest$fgetmText(r24)
            boolean r0 = r0 instanceof android.text.Spanned
            if (r0 == 0) goto L1f
            android.text.Layout$SpannedEllipsizer r0 = new android.text.Layout$SpannedEllipsizer
            java.lang.CharSequence r1 = android.text.StaticLayout.Builder.m5171$$Nest$fgetmText(r24)
            r0.<init>(r1)
            goto L28
        L1f:
            android.text.Layout$Ellipsizer r0 = new android.text.Layout$Ellipsizer
            java.lang.CharSequence r1 = android.text.StaticLayout.Builder.m5171$$Nest$fgetmText(r24)
            r0.<init>(r1)
        L28:
            android.text.TextPaint r3 = android.text.StaticLayout.Builder.m5165$$Nest$fgetmPaint(r24)
            int r4 = android.text.StaticLayout.Builder.m5174$$Nest$fgetmWidth(r24)
            android.text.Layout$Alignment r5 = android.text.StaticLayout.Builder.m5150$$Nest$fgetmAlignment(r24)
            android.text.TextDirectionHeuristic r6 = android.text.StaticLayout.Builder.m5172$$Nest$fgetmTextDir(r24)
            float r7 = android.text.StaticLayout.Builder.m5169$$Nest$fgetmSpacingMult(r24)
            float r8 = android.text.StaticLayout.Builder.m5168$$Nest$fgetmSpacingAdd(r24)
            boolean r9 = android.text.StaticLayout.Builder.m5159$$Nest$fgetmIncludePad(r24)
            boolean r10 = android.text.StaticLayout.Builder.m5156$$Nest$fgetmFallbackLineSpacing(r24)
            int r11 = android.text.StaticLayout.Builder.m5154$$Nest$fgetmEllipsizedWidth(r24)
            android.text.TextUtils$TruncateAt r12 = android.text.StaticLayout.Builder.m5153$$Nest$fgetmEllipsize(r24)
            int r13 = android.text.StaticLayout.Builder.m5163$$Nest$fgetmMaxLines(r24)
            int r14 = android.text.StaticLayout.Builder.m5151$$Nest$fgetmBreakStrategy(r24)
            int r15 = android.text.StaticLayout.Builder.m5158$$Nest$fgetmHyphenationFrequency(r24)
            int[] r16 = android.text.StaticLayout.Builder.m5161$$Nest$fgetmLeftIndents(r24)
            int[] r17 = android.text.StaticLayout.Builder.m5166$$Nest$fgetmRightIndents(r24)
            int r18 = android.text.StaticLayout.Builder.m5160$$Nest$fgetmJustificationMode(r24)
            android.graphics.text.LineBreakConfig r19 = android.text.StaticLayout.Builder.m5162$$Nest$fgetmLineBreakConfig(r24)
            boolean r20 = android.text.StaticLayout.Builder.m5173$$Nest$fgetmUseBoundsForWidth(r24)
            boolean r21 = android.text.StaticLayout.Builder.m5167$$Nest$fgetmShiftDrawingOffsetForStartOverhang(r24)
            android.graphics.Paint$FontMetrics r22 = android.text.StaticLayout.Builder.m5164$$Nest$fgetmMinimumFontMetrics(r24)
            r1 = r23
            r2 = r0
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            r0 = 0
            r1.mDrawingBounds = r0
            r0 = -1
            r1.mMaxLineHeight = r0
            r0 = 2147483647(0x7fffffff, float:NaN)
            r1.mMaximumVisibleLineCount = r0
            r2 = r26
            r1.mColumns = r2
            android.text.TextUtils$TruncateAt r0 = android.text.StaticLayout.Builder.m5153$$Nest$fgetmEllipsize(r24)
            if (r0 == 0) goto La7
            java.lang.CharSequence r0 = r23.getText()
            android.text.Layout$Ellipsizer r0 = (android.text.Layout.Ellipsizer) r0
            r0.mLayout = r1
            int r3 = android.text.StaticLayout.Builder.m5154$$Nest$fgetmEllipsizedWidth(r24)
            r0.mWidth = r3
            android.text.TextUtils$TruncateAt r3 = android.text.StaticLayout.Builder.m5153$$Nest$fgetmEllipsize(r24)
            r0.mMethod = r3
        La7:
            java.lang.Class<android.text.Layout$Directions> r0 = android.text.Layout.Directions.class
            r3 = 2
            java.lang.Object[] r0 = com.android.internal.util.ArrayUtils.newUnpaddedArray(r0, r3)
            android.text.Layout$Directions[] r0 = (android.text.Layout.Directions[]) r0
            r1.mLineDirections = r0
            int r0 = r1.mColumns
            int r0 = r0 * r3
            int[] r0 = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(r0)
            r1.mLines = r0
            int r0 = android.text.StaticLayout.Builder.m5163$$Nest$fgetmMaxLines(r24)
            r1.mMaximumVisibleLineCount = r0
            int[] r0 = android.text.StaticLayout.Builder.m5161$$Nest$fgetmLeftIndents(r24)
            r1.mLeftIndents = r0
            int[] r0 = android.text.StaticLayout.Builder.m5166$$Nest$fgetmRightIndents(r24)
            r1.mRightIndents = r0
            java.lang.String r0 = "Constructing StaticLayout"
            android.os.Trace.beginSection(r0)
            boolean r0 = android.text.StaticLayout.Builder.m5159$$Nest$fgetmIncludePad(r24)     // Catch: java.lang.Throwable -> Le4
            r3 = r24
            r4 = r25
            r1.generate(r3, r0, r4)     // Catch: java.lang.Throwable -> Le2
            android.os.Trace.endSection()
            return
        Le2:
            r0 = move-exception
            goto Le9
        Le4:
            r0 = move-exception
            r3 = r24
            r4 = r25
        Le9:
            android.os.Trace.endSection()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.StaticLayout.<init>(android.text.StaticLayout$Builder, boolean, int):void");
    }

    private static int getBaseHyphenationFrequency(int frequency) {
        switch (frequency) {
            case 1:
            case 3:
                return 1;
            case 2:
            case 4:
                return 2;
            default:
                return 0;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:165:0x06cc, code lost:
    
        r9 = r0;
        r55 = r4;
        r51 = r7;
        r54 = r8;
        r52 = r10;
        r15 = r12;
        r11 = r30;
        r12 = r31;
        r23 = r32;
        r7 = r63;
        r21 = r53;
        r25 = r58;
        r17 = r60;
        r22 = r62;
        r50 = r50;
        r6 = r64;
        r5 = r65;
        r18 = r66;
        r24 = r67;
        r32 = r73;
        r14 = r74;
        r26 = r75;
        r20 = r76;
        r4 = r79;
        r60 = r81;
        r0 = r82;
        r8 = r84;
        r27 = r85;
        r10 = r87;
        r30 = r88;
        r31 = r89;
        r13 = r90;
        r53 = r3;
        r3 = r37;
        r58 = r56;
        r37 = r72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x07d5, code lost:
    
        r0 = r88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x07d9, code lost:
    
        if (r5 == r0) goto L216;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x07db, code lost:
    
        r2 = r89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x07eb, code lost:
    
        if (r2.charAt(r5 - 1) != '\n') goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0800, code lost:
    
        if (r12.mLineCount >= r12.mMaximumVisibleLineCount) goto L225;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x0803, code lost:
    
        r6 = android.text.MeasuredParagraph.buildForBidi(r2, r5, r5, r75, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x080a, code lost:
    
        if (r8 == 0) goto L223;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x080c, code lost:
    
        if (r10 == 0) goto L223;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x080e, code lost:
    
        r3.top = r4;
        r3.ascent = r8;
        r3.descent = r10;
        r3.bottom = r7;
        r1 = r85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x0822, code lost:
    
        r4 = r3.ascent;
        out(r2, r5, r5, r4, r3.descent, r3.top, r3.bottom, r15, r46, r47, null, null, r3, false, 0, r16, r6, r5, r97, r98, r48, null, r0, r76, r72, 0.0f, r1, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x081b, code lost:
    
        r1 = r85;
        r1.getFontMetricsInt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x086f, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x07f4, code lost:
    
        r2 = r89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x03cb, code lost:
    
        if (r2 != android.text.TextUtils.TruncateAt.MARQUEE) goto L117;
     */
    /* JADX WARN: Removed duplicated region for block: B:175:0x076b A[LOOP:0: B:30:0x0208->B:175:0x076b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0757 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:199:0x03d3  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x036b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0343  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x037e A[LOOP:3: B:63:0x037c->B:64:0x037e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x03be  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x03db A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0450  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void generate(android.text.StaticLayout.Builder r96, boolean r97, boolean r98) {
        /*
            Method dump skipped, instructions count: 2170
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.StaticLayout.generate(android.text.StaticLayout$Builder, boolean, boolean):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v2, types: [int] */
    /* JADX WARN: Type inference failed for: r13v7 */
    private int out(CharSequence text, int start, int end, int above, int below, int top, int bottom, int v, float spacingmult, float spacingadd, LineHeightSpan[] chooseHt, int[] chooseHtv, Paint.FontMetricsInt fm, boolean hasTab, int hyphenEdit, boolean needMultiply, MeasuredParagraph measured, int bufEnd, boolean includePad, boolean trackPad, boolean addLastLineLineSpacing, char[] chs, int widthStart, TextUtils.TruncateAt ellipsize, float ellipsisWidth, float textWidth, TextPaint paint, boolean moreChars) {
        int[] lines;
        ?? r13;
        int j;
        int above2;
        int below2;
        int top2;
        int bottom2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean lastCharIsNewLine;
        int extra;
        int want;
        boolean z;
        int j2;
        int j3 = this.mLineCount;
        int off = j3 * this.mColumns;
        boolean z2 = true;
        int want2 = off + this.mColumns + 1;
        int[] lines2 = this.mLines;
        int dir = measured.getParagraphDir();
        if (want2 < lines2.length) {
            lines = lines2;
        } else {
            int[] grow = ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(want2));
            System.arraycopy(lines2, 0, grow, 0, lines2.length);
            this.mLines = grow;
            lines = grow;
        }
        if (j3 >= this.mLineDirections.length) {
            Layout.Directions[] grow2 = (Layout.Directions[]) ArrayUtils.newUnpaddedArray(Layout.Directions.class, GrowingArrayUtils.growSize(j3));
            System.arraycopy(this.mLineDirections, 0, grow2, 0, this.mLineDirections.length);
            this.mLineDirections = grow2;
        }
        if (chooseHt != null) {
            fm.ascent = above;
            fm.descent = below;
            fm.top = top;
            fm.bottom = bottom;
            int i7 = 0;
            while (i7 < chooseHt.length) {
                if (chooseHt[i7] instanceof LineHeightSpan.WithDensity) {
                    want = want2;
                    z = z2;
                    j2 = j3;
                    ((LineHeightSpan.WithDensity) chooseHt[i7]).chooseHeight(text, start, end, chooseHtv[i7], v, fm, paint);
                } else {
                    want = want2;
                    z = z2;
                    j2 = j3;
                    chooseHt[i7].chooseHeight(text, start, end, chooseHtv[i7], v, fm);
                }
                i7++;
                z2 = z;
                want2 = want;
                j3 = j2;
            }
            r13 = z2;
            j = j3;
            above2 = fm.ascent;
            below2 = fm.descent;
            top2 = fm.top;
            bottom2 = fm.bottom;
        } else {
            r13 = 1;
            j = j3;
            above2 = above;
            below2 = below;
            top2 = top;
            bottom2 = bottom;
        }
        boolean firstLine = j == 0 ? r13 : false;
        boolean currentLineIsTheLastVisibleOne = j + 1 == this.mMaximumVisibleLineCount ? r13 : false;
        if (ellipsize == null) {
            i = widthStart;
            i2 = bufEnd;
            i3 = 0;
        } else {
            boolean forceEllipsis = (moreChars && this.mLineCount + r13 == this.mMaximumVisibleLineCount) ? r13 : false;
            boolean doEllipsis = (((!(this.mMaximumVisibleLineCount == r13 && moreChars) && (!firstLine || moreChars)) || ellipsize == TextUtils.TruncateAt.MARQUEE) && (firstLine || ((!currentLineIsTheLastVisibleOne && moreChars) || ellipsize != TextUtils.TruncateAt.END))) ? false : r13;
            if (doEllipsis) {
                i = widthStart;
                i2 = bufEnd;
                calculateEllipsis(start, end, measured, widthStart, ellipsisWidth, ellipsize, j, textWidth, paint, forceEllipsis, chs);
                i3 = 0;
            } else {
                i = widthStart;
                i2 = bufEnd;
                i3 = 0;
                this.mLines[(this.mColumns * j) + 5] = 0;
                this.mLines[(this.mColumns * j) + 6] = 0;
            }
        }
        if (this.mEllipsized) {
            lastCharIsNewLine = true;
            i5 = i3;
            i6 = start;
        } else {
            if (i != i2 && i2 > 0) {
                if (text.charAt(i2 - 1) == '\n') {
                    i4 = 1;
                    int i8 = i4;
                    if (end != i2 && i8 == 0) {
                        lastCharIsNewLine = true;
                        i5 = i3;
                        i6 = start;
                    } else {
                        i5 = i3;
                        i6 = start;
                        if (i6 != i2 && i8 != 0) {
                            lastCharIsNewLine = true;
                        } else {
                            lastCharIsNewLine = false;
                        }
                    }
                }
            }
            i4 = i3;
            int i82 = i4;
            if (end != i2) {
            }
            i5 = i3;
            i6 = start;
            if (i6 != i2) {
            }
            lastCharIsNewLine = false;
        }
        if (firstLine) {
            if (trackPad) {
                this.mTopPadding = top2 - above2;
            }
            if (includePad) {
                above2 = top2;
            }
        }
        if (lastCharIsNewLine) {
            if (trackPad) {
                this.mBottomPadding = bottom2 - below2;
            }
            if (includePad) {
                below2 = bottom2;
            }
        }
        if (needMultiply && (addLastLineLineSpacing || !lastCharIsNewLine)) {
            double ex = ((below2 - above2) * (spacingmult - 1.0f)) + spacingadd;
            if (ex >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                extra = (int) (EXTRA_ROUNDING + ex);
            } else {
                extra = -((int) ((-ex) + EXTRA_ROUNDING));
            }
        } else {
            extra = 0;
        }
        lines[off + 0] = i6;
        lines[off + 1] = v;
        lines[off + 2] = below2 + extra;
        lines[off + 3] = extra;
        if (!this.mEllipsized && currentLineIsTheLastVisibleOne) {
            int maxLineBelow = includePad ? bottom2 : below2;
            this.mMaxLineHeight = v + (maxLineBelow - above2);
        }
        int maxLineBelow2 = below2 - above2;
        int v2 = v + maxLineBelow2 + extra;
        lines[off + this.mColumns + i5] = end;
        lines[off + this.mColumns + 1] = v2;
        int i9 = off + 0;
        lines[i9] = lines[i9] | (hasTab ? 536870912 : i5);
        if (!this.mEllipsized) {
            lines[off + 4] = hyphenEdit;
        } else if (ellipsize == TextUtils.TruncateAt.START) {
            lines[off + 4] = packHyphenEdit(i5, unpackEndHyphenEdit(hyphenEdit));
        } else if (ellipsize == TextUtils.TruncateAt.END) {
            lines[off + 4] = packHyphenEdit(unpackStartHyphenEdit(hyphenEdit), i5);
        } else {
            lines[off + 4] = packHyphenEdit(i5, i5);
        }
        int i10 = off + 0;
        lines[i10] = lines[i10] | (dir << 30);
        this.mLineDirections[j] = measured.getDirections(i6 - i, end - i);
        this.mLineCount++;
        return v2;
    }

    private void calculateEllipsis(int lineStart, int lineEnd, MeasuredParagraph measured, int widthStart, float avail, TextUtils.TruncateAt where, int line, float textWidth, TextPaint paint, boolean forceEllipsis, char[] chs) {
        float avail2 = avail - getTotalInsets(line);
        if (textWidth <= avail2 && !forceEllipsis) {
            this.mLines[(this.mColumns * line) + 5] = 0;
            this.mLines[(this.mColumns * line) + 6] = 0;
            return;
        }
        float ellipsisWidth = paint.measureText(TextUtils.getEllipsisString(where));
        int ellipsisStart = 0;
        int ellipsisCount = 0;
        int len = lineEnd - lineStart;
        if (where == TextUtils.TruncateAt.START) {
            if (this.mMaximumVisibleLineCount == 1) {
                float sum = 0.0f;
                int i = len;
                while (true) {
                    if (i <= 0) {
                        break;
                    }
                    float w = measured.getCharWidthAt(((i - 1) + lineStart) - widthStart);
                    if (w + sum + ellipsisWidth > avail2) {
                        while (i < len && measured.getCharWidthAt((i + lineStart) - widthStart) == 0.0f) {
                            i++;
                        }
                    } else {
                        sum += w;
                        i--;
                    }
                }
                ellipsisStart = 0;
                ellipsisCount = i;
            } else if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Start Ellipsis only supported with one line");
            }
        } else if (where == TextUtils.TruncateAt.END || where == TextUtils.TruncateAt.MARQUEE || where == TextUtils.TruncateAt.END_SMALL) {
            float sum2 = 0.0f;
            int i2 = 0;
            while (i2 < len) {
                float w2 = measured.getCharWidthAt((i2 + lineStart) - widthStart);
                if (w2 + sum2 + ellipsisWidth > avail2) {
                    break;
                }
                sum2 += w2;
                i2++;
            }
            ellipsisStart = i2;
            ellipsisCount = len - i2;
            if (forceEllipsis && ellipsisCount == 0 && len > 0) {
                ellipsisStart = len - 1;
                while (ellipsisStart > 0 && measured.getCharWidthAt((ellipsisStart + lineStart) - widthStart) == 0.0f && chs != null && chs[(ellipsisStart + lineStart) - widthStart] != '\n') {
                    ellipsisStart--;
                }
                ellipsisCount = len - ellipsisStart;
            }
        } else if (this.mMaximumVisibleLineCount == 1) {
            float lsum = 0.0f;
            float rsum = 0.0f;
            int right = len;
            float ravail = (avail2 - ellipsisWidth) / 2.0f;
            while (true) {
                if (right <= 0) {
                    break;
                }
                float w3 = measured.getCharWidthAt(((right - 1) + lineStart) - widthStart);
                if (w3 + rsum > ravail) {
                    while (right < len && measured.getCharWidthAt((right + lineStart) - widthStart) == 0.0f) {
                        right++;
                    }
                } else {
                    rsum += w3;
                    right--;
                }
            }
            float lavail = (avail2 - ellipsisWidth) - rsum;
            int left = 0;
            while (left < right) {
                float w4 = measured.getCharWidthAt((left + lineStart) - widthStart);
                if (w4 + lsum > lavail) {
                    break;
                }
                lsum += w4;
                left++;
            }
            ellipsisStart = left;
            ellipsisCount = right - left;
        } else if (Log.isLoggable(TAG, 5)) {
            Log.w(TAG, "Middle Ellipsis only supported with one line");
        }
        this.mEllipsized = true;
        this.mLines[(this.mColumns * line) + 5] = ellipsisStart;
        this.mLines[(this.mColumns * line) + 6] = ellipsisCount;
    }

    private float getTotalInsets(int line) {
        int totalIndent = 0;
        if (this.mLeftIndents != null) {
            totalIndent = this.mLeftIndents[Math.min(line, this.mLeftIndents.length - 1)];
        }
        if (this.mRightIndents != null) {
            totalIndent += this.mRightIndents[Math.min(line, this.mRightIndents.length - 1)];
        }
        return totalIndent;
    }

    @Override // android.text.Layout
    public int getLineForVertical(int vertical) {
        int high = this.mLineCount;
        int low = -1;
        int[] lines = this.mLines;
        while (high - low > 1) {
            int guess = (high + low) >> 1;
            if (lines[(this.mColumns * guess) + 1] > vertical) {
                high = guess;
            } else {
                low = guess;
            }
        }
        if (low < 0) {
            return 0;
        }
        return low;
    }

    @Override // android.text.Layout
    public int getLineCount() {
        return this.mLineCount;
    }

    @Override // android.text.Layout
    public int getLineTop(int line) {
        return this.mLines[(this.mColumns * line) + 1];
    }

    @Override // android.text.Layout
    public int getLineExtra(int line) {
        return this.mLines[(this.mColumns * line) + 3];
    }

    @Override // android.text.Layout
    public int getLineDescent(int line) {
        return this.mLines[(this.mColumns * line) + 2];
    }

    @Override // android.text.Layout
    public int getLineStart(int line) {
        return this.mLines[(this.mColumns * line) + 0] & 536870911;
    }

    @Override // android.text.Layout
    public int getParagraphDirection(int line) {
        return this.mLines[(this.mColumns * line) + 0] >> 30;
    }

    @Override // android.text.Layout
    public boolean getLineContainsTab(int line) {
        return (this.mLines[(this.mColumns * line) + 0] & 536870912) != 0;
    }

    @Override // android.text.Layout
    public final Layout.Directions getLineDirections(int line) {
        if (line > getLineCount()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.mLineDirections[line];
    }

    @Override // android.text.Layout
    public int getTopPadding() {
        return this.mTopPadding;
    }

    @Override // android.text.Layout
    public int getBottomPadding() {
        return this.mBottomPadding;
    }

    static int packHyphenEdit(int start, int end) {
        return (start << 3) | end;
    }

    static int unpackStartHyphenEdit(int packedHyphenEdit) {
        return (packedHyphenEdit & 24) >> 3;
    }

    static int unpackEndHyphenEdit(int packedHyphenEdit) {
        return packedHyphenEdit & 7;
    }

    @Override // android.text.Layout
    public int getStartHyphenEdit(int lineNumber) {
        return unpackStartHyphenEdit(this.mLines[(this.mColumns * lineNumber) + 4] & 255);
    }

    @Override // android.text.Layout
    public int getEndHyphenEdit(int lineNumber) {
        return unpackEndHyphenEdit(this.mLines[(this.mColumns * lineNumber) + 4] & 255);
    }

    @Override // android.text.Layout
    public int getIndentAdjust(int line, Layout.Alignment align) {
        if (align == Layout.Alignment.ALIGN_LEFT) {
            if (this.mLeftIndents == null) {
                return 0;
            }
            return this.mLeftIndents[Math.min(line, this.mLeftIndents.length - 1)];
        }
        if (align == Layout.Alignment.ALIGN_RIGHT) {
            if (this.mRightIndents == null) {
                return 0;
            }
            return -this.mRightIndents[Math.min(line, this.mRightIndents.length - 1)];
        }
        if (align == Layout.Alignment.ALIGN_CENTER) {
            int left = 0;
            if (this.mLeftIndents != null) {
                left = this.mLeftIndents[Math.min(line, this.mLeftIndents.length - 1)];
            }
            int right = 0;
            if (this.mRightIndents != null) {
                right = this.mRightIndents[Math.min(line, this.mRightIndents.length - 1)];
            }
            return (left - right) >> 1;
        }
        throw new AssertionError("unhandled alignment " + align);
    }

    @Override // android.text.Layout
    public int getEllipsisCount(int line) {
        if (this.mColumns < 7) {
            return 0;
        }
        return this.mLines[(this.mColumns * line) + 6];
    }

    @Override // android.text.Layout
    public int getEllipsisStart(int line) {
        if (this.mColumns < 7) {
            return 0;
        }
        return this.mLines[(this.mColumns * line) + 5];
    }

    @Override // android.text.Layout
    public RectF computeDrawingBoundingBox() {
        if (this.mDrawingBounds == null) {
            this.mDrawingBounds = super.computeDrawingBoundingBox();
        }
        return this.mDrawingBounds;
    }

    @Override // android.text.Layout
    public int getHeight(boolean cap) {
        if (cap && this.mLineCount > this.mMaximumVisibleLineCount && this.mMaxLineHeight == -1 && Log.isLoggable(TAG, 5)) {
            Log.w(TAG, "maxLineHeight should not be -1.  maxLines:" + this.mMaximumVisibleLineCount + " lineCount:" + this.mLineCount);
        }
        return (!cap || this.mLineCount <= this.mMaximumVisibleLineCount || this.mMaxLineHeight == -1) ? super.getHeight() : this.mMaxLineHeight;
    }

    static class LineBreaks {
        private static final int INITIAL_SIZE = 16;
        public int[] breaks = new int[16];
        public float[] widths = new float[16];
        public float[] ascents = new float[16];
        public float[] descents = new float[16];
        public int[] flags = new int[16];

        LineBreaks() {
        }
    }
}
