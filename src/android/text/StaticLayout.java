package android.text;

import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.text.LineBreakConfig;
import android.graphics.text.LineBreaker;
import android.hardware.scontext.SContextConstants;
import android.os.Trace;
import android.text.Layout;
import android.text.PrecomputedText;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;
import android.text.style.TabStopSpan;
import android.util.Log;
import android.util.Pools;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import com.android.text.flags.Flags;
import java.util.Arrays;

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

    private static int getBaseHyphenationFrequency(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        return 0;
                    }
                }
            }
            return 2;
        }
        return 1;
    }

    static int packHyphenEdit(int i, int i2) {
        return (i << 3) | i2;
    }

    static int unpackEndHyphenEdit(int i) {
        return i & 7;
    }

    static int unpackStartHyphenEdit(int i) {
        return (i & 24) >> 3;
    }

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

        public static Builder obtain(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3) {
            Builder builderAcquire = sPool.acquire();
            if (builderAcquire == null) {
                builderAcquire = new Builder();
            }
            builderAcquire.mText = charSequence;
            builderAcquire.mStart = i;
            builderAcquire.mEnd = i2;
            builderAcquire.mPaint = textPaint;
            builderAcquire.mWidth = i3;
            builderAcquire.mAlignment = Layout.Alignment.ALIGN_NORMAL;
            builderAcquire.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
            builderAcquire.mSpacingMult = 1.0f;
            builderAcquire.mSpacingAdd = 0.0f;
            builderAcquire.mIncludePad = true;
            builderAcquire.mFallbackLineSpacing = false;
            builderAcquire.mEllipsizedWidth = i3;
            builderAcquire.mEllipsize = null;
            builderAcquire.mMaxLines = Integer.MAX_VALUE;
            builderAcquire.mBreakStrategy = 0;
            builderAcquire.mHyphenationFrequency = 0;
            builderAcquire.mJustificationMode = 0;
            builderAcquire.mLineBreakConfig = LineBreakConfig.NONE;
            builderAcquire.mUseBoundsForWidth = false;
            builderAcquire.mMinimumFontMetrics = null;
            return builderAcquire;
        }

        private static void recycle(Builder builder) {
            builder.mPaint = null;
            builder.mText = null;
            builder.mLeftIndents = null;
            builder.mRightIndents = null;
            builder.mMinimumFontMetrics = null;
            sPool.release(builder);
        }

        void finish() {
            this.mText = null;
            this.mPaint = null;
            this.mLeftIndents = null;
            this.mRightIndents = null;
            this.mMinimumFontMetrics = null;
        }

        public Builder setText(CharSequence charSequence) {
            return setText(charSequence, 0, charSequence.length());
        }

        public Builder setText(CharSequence charSequence, int i, int i2) {
            this.mText = charSequence;
            this.mStart = i;
            this.mEnd = i2;
            return this;
        }

        public Builder setPaint(TextPaint textPaint) {
            this.mPaint = textPaint;
            return this;
        }

        public Builder setWidth(int i) {
            this.mWidth = i;
            if (this.mEllipsize == null) {
                this.mEllipsizedWidth = i;
            }
            return this;
        }

        public Builder setAlignment(Layout.Alignment alignment) {
            this.mAlignment = alignment;
            return this;
        }

        public Builder setTextDirection(TextDirectionHeuristic textDirectionHeuristic) {
            this.mTextDir = textDirectionHeuristic;
            return this;
        }

        public Builder setLineSpacing(float f, float f2) {
            this.mSpacingAdd = f;
            this.mSpacingMult = f2;
            return this;
        }

        public Builder setIncludePad(boolean z) {
            this.mIncludePad = z;
            return this;
        }

        public Builder setUseLineSpacingFromFallbacks(boolean z) {
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

        public Builder setIndents(int[] iArr, int[] iArr2) {
            this.mLeftIndents = iArr;
            this.mRightIndents = iArr2;
            return this;
        }

        public Builder setJustificationMode(int i) {
            this.mJustificationMode = i;
            return this;
        }

        Builder setAddLastLineLineSpacing(boolean z) {
            this.mAddLastLineLineSpacing = z;
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

        public Builder setCalculateBounds(boolean z) {
            this.mCalculateBounds = z;
            return this;
        }

        public Builder setMinimumFontMetrics(Paint.FontMetrics fontMetrics) {
            this.mMinimumFontMetrics = fontMetrics;
            return this;
        }

        public StaticLayout build() {
            StaticLayout staticLayout = new StaticLayout(this, this.mIncludePad, this.mEllipsize != null ? 7 : 5);
            recycle(this);
            return staticLayout;
        }

        StaticLayout buildPartialStaticLayoutForDynamicLayout(boolean z, StaticLayout staticLayout) {
            if (staticLayout == null) {
                staticLayout = new StaticLayout();
            }
            Trace.beginSection("Generating StaticLayout For DynamicLayout");
            try {
                staticLayout.generate(this, this.mIncludePad, z);
                return staticLayout;
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
    public StaticLayout(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, boolean z) {
        this(charSequence, 0, charSequence.length(), textPaint, i, alignment, f, f2, z);
    }

    @Deprecated
    public StaticLayout(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, Layout.Alignment alignment, float f, float f2, boolean z) {
        this(charSequence, i, i2, textPaint, i3, alignment, f, f2, z, null, 0);
    }

    @Deprecated
    public StaticLayout(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, Layout.Alignment alignment, float f, float f2, boolean z, TextUtils.TruncateAt truncateAt, int i4) {
        this(charSequence, i, i2, textPaint, i3, alignment, TextDirectionHeuristics.FIRSTSTRONG_LTR, f, f2, z, truncateAt, i4, Integer.MAX_VALUE);
    }

    @Deprecated
    public StaticLayout(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, Layout.Alignment alignment, TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean z, TextUtils.TruncateAt truncateAt, int i4, int i5) {
        this(Builder.obtain(charSequence, i, i2, textPaint, i3).setAlignment(alignment).setTextDirection(textDirectionHeuristic).setLineSpacing(f2, f).setIncludePad(z).setEllipsize(truncateAt).setEllipsizedWidth(i4).setMaxLines(i5), z, truncateAt != null ? 7 : 5);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private StaticLayout(Builder builder, boolean z, int i) {
        CharSequence spannedEllipsizer;
        if (builder.mEllipsize == null) {
            spannedEllipsizer = builder.mText;
        } else {
            spannedEllipsizer = builder.mText instanceof Spanned ? new Layout.SpannedEllipsizer(builder.mText) : new Layout.Ellipsizer(builder.mText);
        }
        super(spannedEllipsizer, builder.mPaint, builder.mWidth, builder.mAlignment, builder.mTextDir, builder.mSpacingMult, builder.mSpacingAdd, builder.mIncludePad, builder.mFallbackLineSpacing, builder.mEllipsizedWidth, builder.mEllipsize, builder.mMaxLines, builder.mBreakStrategy, builder.mHyphenationFrequency, builder.mLeftIndents, builder.mRightIndents, builder.mJustificationMode, builder.mLineBreakConfig, builder.mUseBoundsForWidth, builder.mShiftDrawingOffsetForStartOverhang, builder.mMinimumFontMetrics);
        this.mDrawingBounds = null;
        this.mMaxLineHeight = -1;
        this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
        this.mColumns = i;
        if (builder.mEllipsize != null) {
            Layout.Ellipsizer ellipsizer = (Layout.Ellipsizer) getText();
            ellipsizer.mLayout = this;
            ellipsizer.mWidth = builder.mEllipsizedWidth;
            ellipsizer.mMethod = builder.mEllipsize;
        }
        this.mLineDirections = (Layout.Directions[]) ArrayUtils.newUnpaddedArray(Layout.Directions.class, 2);
        this.mLines = ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
        this.mMaximumVisibleLineCount = builder.mMaxLines;
        this.mLeftIndents = builder.mLeftIndents;
        this.mRightIndents = builder.mRightIndents;
        Trace.beginSection("Constructing StaticLayout");
        try {
            generate(builder, builder.mIncludePad, z);
        } finally {
            Trace.endSection();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:193:0x058a, code lost:
    
        r35 = r11;
        r60 = r42;
        r61 = r55;
        r55 = r1;
        r42 = r39;
        r39 = r36;
        r36 = r25;
        r25 = r15;
        r15 = r40;
        r32 = r32;
        r33 = r33;
        r34 = r34;
        r65 = r65;
        r4 = r4;
        r28 = r28;
        r7 = r16;
        r16 = r17;
        r2 = r2;
        r3 = r3;
        r6 = r6;
        r8 = r8;
        r27 = r27;
        r29 = r29;
        r1 = r61;
        r19 = r19;
        r17 = r10;
        r43 = r43;
        r20 = r20;
        r52 = r52;
        r18 = r18;
        r10 = r53;
        r0 = r54;
        r22 = r55;
        r13 = r13;
        r40 = r15;
        r15 = r25;
        r11 = r35;
        r25 = r36;
        r36 = r39;
        r39 = r42;
        r42 = r60;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x032d A[LOOP:3: B:101:0x032b->B:102:0x032d, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0378  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x03c2  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0474  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x04dc  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x050b  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0635 A[LOOP:0: B:55:0x01df->B:197:0x0635, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x06af  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x066d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0687 A[EDGE_INSN: B:214:0x0687->B:199:0x0687 BREAK  A[LOOP:0: B:55:0x01df->B:197:0x0635], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0320  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void generate(Builder builder, boolean z, boolean z2) {
        int i;
        int i2;
        int iMin;
        int iRound;
        int iRound2;
        int iMax;
        LineBreaker.ParagraphConstraints paragraphConstraints;
        int i3;
        Spanned spanned;
        int i4;
        int i5;
        float f;
        int i6;
        LineBreaker lineBreaker;
        int i7;
        PrecomputedText.ParagraphInfo[] paragraphInfoArrCreateMeasuredParagraphs;
        TextDirectionHeuristic textDirectionHeuristic;
        TextPaint textPaint;
        int i8;
        int i9;
        int i10;
        int i11;
        int[] iArrNewUnpaddedIntArray;
        int[] iArr;
        float[] fArr;
        float[] fArr2;
        float[] fArr3;
        boolean[] zArr;
        int[] iArr2;
        CharSequence charSequence;
        int i12;
        int i13;
        TextDirectionHeuristic textDirectionHeuristic2;
        float f2;
        float f3;
        float f4;
        TextPaint textPaint2;
        int i14;
        int i15;
        int i16;
        StaticLayout staticLayout;
        TextUtils.TruncateAt truncateAt;
        boolean z3;
        int i17;
        int i18;
        int[] iArr3;
        int i19;
        TextDirectionHeuristic textDirectionHeuristic3;
        int i20;
        int i21;
        LineHeightSpan[] lineHeightSpanArr;
        int i22;
        Spanned spanned2;
        LineHeightSpan[] lineHeightSpanArr2;
        int[] iArr4;
        float[] fArr4;
        float[] fArr5;
        float[] fArr6;
        int lineCount;
        int i23;
        LineBreaker lineBreaker2;
        int i24;
        float[] fArr7;
        boolean[] zArr2;
        int[] iArr5;
        int i25;
        int i26;
        int i27;
        int i28;
        int i29;
        int i30;
        LineBreaker.ParagraphConstraints paragraphConstraints2;
        PrecomputedText.ParagraphInfo[] paragraphInfoArr;
        int[] iArr6;
        int i31;
        LineBreaker lineBreaker3;
        int i32;
        int i33;
        float f5;
        boolean z4;
        int i34;
        int i35;
        float f6;
        int i36;
        float f7;
        int i37;
        int i38;
        int i39;
        int i40;
        int i41;
        int i42;
        int i43;
        int i44;
        int i45;
        int iMax2;
        int iMin2;
        int iMax3;
        int i46;
        StaticLayout staticLayout2 = this;
        CharSequence charSequence2 = builder.mText;
        int i47 = builder.mStart;
        int i48 = builder.mEnd;
        TextPaint textPaint3 = builder.mPaint;
        int i49 = builder.mWidth;
        TextDirectionHeuristic textDirectionHeuristic4 = builder.mTextDir;
        float f8 = builder.mSpacingMult;
        float f9 = builder.mSpacingAdd;
        float f10 = builder.mEllipsizedWidth;
        TextUtils.TruncateAt truncateAt2 = builder.mEllipsize;
        boolean z5 = builder.mAddLastLineLineSpacing;
        staticLayout2.mLineCount = 0;
        staticLayout2.mEllipsized = false;
        staticLayout2.mMaxLineHeight = staticLayout2.mMaximumVisibleLineCount < 1 ? 0 : -1;
        int[] iArr7 = null;
        staticLayout2.mDrawingBounds = null;
        boolean z6 = builder.mFallbackLineSpacing;
        boolean z7 = (f8 == 1.0f && f9 == 0.0f) ? false : true;
        float f11 = f10;
        Paint.FontMetricsInt fontMetricsInt = builder.mFontMetricsInt;
        int[] iArr8 = staticLayout2.mLeftIndents;
        if (iArr8 != null || staticLayout2.mRightIndents != null) {
            int length = iArr8 == null ? 0 : iArr8.length;
            int[] iArr9 = staticLayout2.mRightIndents;
            int length2 = iArr9 == null ? 0 : iArr9.length;
            iArr7 = new int[Math.max(length, length2)];
            for (int i50 = 0; i50 < length; i50++) {
                iArr7[i50] = staticLayout2.mLeftIndents[i50];
            }
            for (int i51 = 0; i51 < length2; i51++) {
                iArr7[i51] = iArr7[i51] + staticLayout2.mRightIndents[i51];
            }
        }
        if (!Flags.fixLineHeightForLocale() || builder.mMinimumFontMetrics == null) {
            i = i47;
            i2 = i48;
            iMin = 0;
            iRound = 0;
            iRound2 = 0;
            iMax = 0;
        } else {
            int iFloor = (int) Math.floor(builder.mMinimumFontMetrics.top);
            iRound = Math.round(builder.mMinimumFontMetrics.ascent);
            iRound2 = Math.round(builder.mMinimumFontMetrics.descent);
            i = i47;
            i2 = i48;
            int iCeil = (int) Math.ceil(builder.mMinimumFontMetrics.bottom);
            iMin = Math.min(iFloor, iRound);
            iMax = Math.max(iCeil, iRound2);
        }
        LineBreaker lineBreakerBuild = new LineBreaker.Builder().setBreakStrategy(builder.mBreakStrategy).setHyphenationFrequency(getBaseHyphenationFrequency(builder.mHyphenationFrequency)).setJustificationMode(builder.mJustificationMode).setIndents(iArr7).setUseBoundsForWidth(builder.mUseBoundsForWidth).build();
        LineBreaker.ParagraphConstraints paragraphConstraints3 = new LineBreaker.ParagraphConstraints();
        Spanned spanned3 = charSequence2 instanceof Spanned ? (Spanned) charSequence2 : null;
        if (charSequence2 instanceof PrecomputedText) {
            PrecomputedText precomputedText = (PrecomputedText) charSequence2;
            paragraphConstraints = paragraphConstraints3;
            i3 = iMin;
            f = f8;
            i6 = i2;
            lineBreaker = lineBreakerBuild;
            i7 = i;
            spanned = spanned3;
            i4 = iRound;
            i5 = iRound2;
            int iCheckResultUsable = precomputedText.checkResultUsable(i7, i6, textDirectionHeuristic4, textPaint3, builder.mBreakStrategy, builder.mHyphenationFrequency, builder.mLineBreakConfig);
            if (iCheckResultUsable == 1) {
                paragraphInfoArrCreateMeasuredParagraphs = PrecomputedText.create(precomputedText, new PrecomputedText.Params.Builder(textPaint3).setBreakStrategy(builder.mBreakStrategy).setHyphenationFrequency(builder.mHyphenationFrequency).setTextDirection(textDirectionHeuristic4).setLineBreakConfig(builder.mLineBreakConfig).build()).getParagraphInfo();
            } else if (iCheckResultUsable == 2) {
                paragraphInfoArrCreateMeasuredParagraphs = precomputedText.getParagraphInfo();
            }
            if (paragraphInfoArrCreateMeasuredParagraphs != null) {
                i8 = iMax;
                textDirectionHeuristic = textDirectionHeuristic4;
                textPaint = textPaint3;
                i9 = 0;
                paragraphInfoArrCreateMeasuredParagraphs = PrecomputedText.createMeasuredParagraphs(charSequence2, new PrecomputedText.Params(textPaint3, builder.mLineBreakConfig, textDirectionHeuristic, builder.mBreakStrategy, builder.mHyphenationFrequency), i7, i6, false, builder.mCalculateBounds);
            } else {
                textDirectionHeuristic = textDirectionHeuristic4;
                textPaint = textPaint3;
                i8 = iMax;
                i9 = 0;
            }
            i10 = i9;
            i11 = i10;
            iArrNewUnpaddedIntArray = null;
            iArr = null;
            fArr = null;
            fArr2 = null;
            fArr3 = null;
            zArr = null;
            iArr2 = null;
            while (true) {
                if (i9 < paragraphInfoArrCreateMeasuredParagraphs.length) {
                    charSequence = charSequence2;
                    i12 = i7;
                    i13 = i6;
                    textDirectionHeuristic2 = textDirectionHeuristic;
                    f2 = f9;
                    f3 = f;
                    f4 = f11;
                    textPaint2 = textPaint;
                    i14 = i3;
                    i15 = i4;
                    i16 = i5;
                    staticLayout = staticLayout2;
                    truncateAt = truncateAt2;
                    z3 = z7;
                    i17 = i8;
                    break;
                }
                int i52 = i9 == 0 ? i7 : paragraphInfoArrCreateMeasuredParagraphs[i9 - 1].paragraphEnd;
                CharSequence charSequence3 = charSequence2;
                int i53 = paragraphInfoArrCreateMeasuredParagraphs[i9].paragraphEnd;
                PrecomputedText.ParagraphInfo[] paragraphInfoArr2 = paragraphInfoArrCreateMeasuredParagraphs;
                Spanned spanned4 = spanned;
                int i54 = i7;
                if (spanned4 != null) {
                    LeadingMarginSpan[] leadingMarginSpanArr = (LeadingMarginSpan[]) getParagraphSpans(spanned4, i52, i53, LeadingMarginSpan.class);
                    iArr3 = iArr;
                    i19 = i9;
                    textDirectionHeuristic3 = textDirectionHeuristic;
                    int leadingMargin = i49;
                    int leadingMargin2 = leadingMargin;
                    int iMax4 = 1;
                    int i55 = 0;
                    while (i55 < leadingMarginSpanArr.length) {
                        LeadingMarginSpan leadingMarginSpan = leadingMarginSpanArr[i55];
                        LeadingMarginSpan[] leadingMarginSpanArr2 = leadingMarginSpanArr;
                        leadingMargin -= leadingMarginSpan.getLeadingMargin(true);
                        int i56 = i55;
                        leadingMargin2 -= leadingMarginSpanArr2[i55].getLeadingMargin(false);
                        if (leadingMarginSpan instanceof LeadingMarginSpan.LeadingMarginSpan2) {
                            iMax4 = Math.max(iMax4, ((LeadingMarginSpan.LeadingMarginSpan2) leadingMarginSpan).getLeadingMarginLineCount());
                        }
                        i55 = i56 + 1;
                        leadingMarginSpanArr = leadingMarginSpanArr2;
                    }
                    lineHeightSpanArr = (LineHeightSpan[]) getParagraphSpans(spanned4, i52, i53, LineHeightSpan.class);
                    if (lineHeightSpanArr.length == 0) {
                        i22 = iMax4;
                        i20 = leadingMargin;
                        i21 = leadingMargin2;
                        lineHeightSpanArr = null;
                    } else {
                        if (iArrNewUnpaddedIntArray == null || iArrNewUnpaddedIntArray.length < lineHeightSpanArr.length) {
                            iArrNewUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(lineHeightSpanArr.length);
                        }
                        for (int i57 = 0; i57 < lineHeightSpanArr.length; i57++) {
                            int spanStart = spanned4.getSpanStart(lineHeightSpanArr[i57]);
                            if (spanStart < i52) {
                                iArrNewUnpaddedIntArray[i57] = staticLayout2.getLineTop(staticLayout2.getLineForOffset(spanStart));
                            } else {
                                iArrNewUnpaddedIntArray[i57] = i10;
                            }
                        }
                        i22 = iMax4;
                        i20 = leadingMargin;
                        i21 = leadingMargin2;
                    }
                } else {
                    iArr3 = iArr;
                    i19 = i9;
                    textDirectionHeuristic3 = textDirectionHeuristic;
                    i20 = i49;
                    i21 = i20;
                    lineHeightSpanArr = null;
                    i22 = 1;
                }
                if (spanned4 != null) {
                    lineHeightSpanArr2 = lineHeightSpanArr;
                    TabStopSpan[] tabStopSpanArr = (TabStopSpan[]) getParagraphSpans(spanned4, i52, i53, TabStopSpan.class);
                    spanned2 = spanned4;
                    if (tabStopSpanArr.length > 0) {
                        int length3 = tabStopSpanArr.length;
                        iArr4 = iArrNewUnpaddedIntArray;
                        float[] fArr8 = new float[length3];
                        fArr4 = fArr;
                        fArr5 = fArr2;
                        for (int i58 = 0; i58 < tabStopSpanArr.length; i58++) {
                            fArr8[i58] = tabStopSpanArr[i58].getTabStop();
                        }
                        Arrays.sort(fArr8, 0, length3);
                        fArr6 = fArr8;
                    }
                    MeasuredParagraph measuredParagraph = paragraphInfoArr2[i19].measured;
                    char[] chars = measuredParagraph.getChars();
                    int[] rawArray = measuredParagraph.getSpanEndCache().getRawArray();
                    int[] rawArray2 = measuredParagraph.getFontMetrics().getRawArray();
                    LineBreaker.ParagraphConstraints paragraphConstraints4 = paragraphConstraints;
                    paragraphConstraints4.setWidth(i21);
                    paragraphConstraints4.setIndent(i20, i22);
                    paragraphConstraints4.setTabStops(fArr6, TAB_INCREMENT);
                    LineBreaker lineBreaker4 = lineBreaker;
                    LineBreaker.Result resultComputeLineBreaks = lineBreaker4.computeLineBreaks(measuredParagraph.getMeasuredText(), paragraphConstraints4, staticLayout2.mLineCount);
                    lineCount = resultComputeLineBreaks.getLineCount();
                    i23 = i11;
                    if (i23 >= lineCount) {
                        lineBreaker2 = lineBreaker4;
                        i24 = lineCount;
                        iArr5 = new int[lineCount];
                        fArr4 = new float[lineCount];
                        fArr5 = new float[lineCount];
                        fArr7 = new float[lineCount];
                        zArr2 = new boolean[lineCount];
                        iArr3 = new int[lineCount];
                    } else {
                        lineBreaker2 = lineBreaker4;
                        i24 = i23;
                        fArr7 = fArr3;
                        zArr2 = zArr;
                        iArr5 = iArr2;
                    }
                    i25 = 0;
                    while (i25 < lineCount) {
                        iArr3[i25] = resultComputeLineBreaks.getLineBreakOffset(i25);
                        fArr4[i25] = resultComputeLineBreaks.getLineWidth(i25);
                        fArr5[i25] = resultComputeLineBreaks.getLineAscent(i25);
                        fArr7[i25] = resultComputeLineBreaks.getLineDescent(i25);
                        zArr2[i25] = resultComputeLineBreaks.hasLineTab(i25);
                        iArr5[i25] = packHyphenEdit(resultComputeLineBreaks.getStartLineHyphenEdit(i25), resultComputeLineBreaks.getEndLineHyphenEdit(i25));
                        i25++;
                        paragraphConstraints4 = paragraphConstraints4;
                    }
                    LineBreaker.ParagraphConstraints paragraphConstraints5 = paragraphConstraints4;
                    i26 = staticLayout2.mMaximumVisibleLineCount - staticLayout2.mLineCount;
                    boolean z8 = truncateAt2 == null && (truncateAt2 == TextUtils.TruncateAt.END || (staticLayout2.mMaximumVisibleLineCount == 1 && truncateAt2 != TextUtils.TruncateAt.MARQUEE));
                    if (i26 > 0 || i26 >= lineCount || !z8) {
                        i27 = lineCount;
                    } else {
                        int i59 = i26 - 1;
                        int i60 = i59;
                        float charWidthAt = 0.0f;
                        boolean z9 = false;
                        while (i60 < lineCount) {
                            if (i60 == lineCount - 1) {
                                charWidthAt += fArr4[i60];
                                i46 = i26;
                            } else {
                                i46 = i26;
                                for (int i61 = i60 == 0 ? 0 : iArr3[i60 - 1]; i61 < iArr3[i60]; i61++) {
                                    charWidthAt += measuredParagraph.getCharWidthAt(i61);
                                }
                            }
                            z9 |= zArr2[i60];
                            i60++;
                            i26 = i46;
                        }
                        iArr3[i59] = iArr3[lineCount - 1];
                        fArr4[i59] = charWidthAt;
                        zArr2[i59] = z9;
                        i27 = i26;
                    }
                    MeasuredParagraph measuredParagraph2 = measuredParagraph;
                    i28 = i52;
                    int i62 = i28;
                    int i63 = i8;
                    int i64 = i3;
                    int iMin3 = i4;
                    int iMax5 = i5;
                    int i65 = 0;
                    int i66 = 0;
                    int i67 = 0;
                    while (i28 < i53) {
                        int i68 = i65 + 1;
                        int i69 = rawArray[i65];
                        int i70 = i67 * 4;
                        int i71 = i53;
                        fontMetricsInt.top = rawArray2[i70];
                        fontMetricsInt.bottom = rawArray2[i70 + 1];
                        fontMetricsInt.ascent = rawArray2[i70 + 2];
                        fontMetricsInt.descent = rawArray2[i70 + 3];
                        boolean z10 = true;
                        i67++;
                        if (fontMetricsInt.top < i64) {
                            i64 = fontMetricsInt.top;
                        }
                        if (fontMetricsInt.ascent < iMin3) {
                            iMin3 = fontMetricsInt.ascent;
                        }
                        if (fontMetricsInt.descent > iMax5) {
                            iMax5 = fontMetricsInt.descent;
                        }
                        if (fontMetricsInt.bottom > i63) {
                            i63 = fontMetricsInt.bottom;
                        }
                        int i72 = i66;
                        while (true) {
                            if (i72 >= i27) {
                                i32 = i72;
                                break;
                            }
                            i32 = i72;
                            if (i52 + iArr3[i72] >= i28) {
                                break;
                            } else {
                                i72 = i32 + 1;
                            }
                        }
                        int i73 = i32;
                        while (true) {
                            if (i73 >= i27) {
                                boolean z11 = z7;
                                i33 = i69;
                                f5 = f;
                                z4 = z11;
                                i34 = i27;
                                break;
                            }
                            int i74 = iArr3[i73];
                            i34 = i27;
                            if (i52 + i74 > i69) {
                                boolean z12 = z7;
                                i33 = i69;
                                f5 = f;
                                z4 = z12;
                                break;
                            }
                            int i75 = i74 + i52;
                            int i76 = i54;
                            boolean z13 = i75 < i6 ? z10 : false;
                            if (z6) {
                                i35 = i76;
                                iMin3 = Math.min(iMin3, Math.round(fArr5[i73]));
                            } else {
                                i35 = i76;
                            }
                            if (z6) {
                                iMax5 = Math.max(iMax5, Math.round(fArr7[i73]));
                            }
                            if (z6) {
                                if (iMin3 < i64) {
                                    i64 = iMin3;
                                }
                                if (iMax5 > i63) {
                                    i63 = iMax5;
                                }
                            }
                            if (i73 == 0 && i20 != i49) {
                                i36 = i49 - i20;
                            } else if (i73 == 0 || i21 == i49) {
                                f6 = f11;
                                if (f6 >= 0.0f) {
                                    f6 = f11;
                                    f7 = f6;
                                } else {
                                    f7 = f11;
                                }
                                TextUtils.TruncateAt truncateAt3 = truncateAt2;
                                i37 = i69;
                                float f12 = f;
                                boolean z14 = z7;
                                PrecomputedText.ParagraphInfo[] paragraphInfoArr3 = paragraphInfoArr2;
                                boolean z15 = z10;
                                int i77 = i21;
                                int i78 = i49;
                                float f13 = f9;
                                int i79 = i8;
                                TextDirectionHeuristic textDirectionHeuristic5 = textDirectionHeuristic3;
                                int[] iArr10 = iArr4;
                                int i80 = i71;
                                int i81 = i35;
                                int i82 = i73;
                                int i83 = i6;
                                int i84 = i20;
                                int i85 = iMax5;
                                int i86 = i10;
                                int i87 = i19;
                                int i88 = i64;
                                int i89 = i52;
                                LineBreaker lineBreaker5 = lineBreaker2;
                                LineHeightSpan[] lineHeightSpanArr3 = lineHeightSpanArr2;
                                CharSequence charSequence4 = charSequence3;
                                LineBreaker.ParagraphConstraints paragraphConstraints6 = paragraphConstraints5;
                                MeasuredParagraph measuredParagraph3 = measuredParagraph2;
                                float f14 = f7;
                                boolean z16 = z5;
                                int iOut = out(charSequence4, i62, i75, iMin3, i85, i88, i63, i86, f12, f13, lineHeightSpanArr3, iArr10, fontMetricsInt, zArr2[i73], iArr5[i73], z14, measuredParagraph3, i83, z, z2, z16, chars, i89, truncateAt3, f6, fArr4[i73], textPaint, z13);
                                z5 = z16;
                                i52 = i89;
                                TextPaint textPaint4 = textPaint;
                                if (i75 >= i37) {
                                    i41 = i3;
                                    int iMin4 = Math.min(i41, fontMetricsInt.top);
                                    i38 = i37;
                                    i44 = i79;
                                    iMax2 = Math.max(i44, fontMetricsInt.bottom);
                                    i39 = iOut;
                                    i42 = i4;
                                    iMin2 = Math.min(i42, fontMetricsInt.ascent);
                                    i40 = i75;
                                    i43 = i5;
                                    iMax3 = Math.max(i43, fontMetricsInt.descent);
                                    i45 = iMin4;
                                } else {
                                    i38 = i37;
                                    i39 = iOut;
                                    i40 = i75;
                                    i41 = i3;
                                    i42 = i4;
                                    i43 = i5;
                                    i44 = i79;
                                    i45 = 0;
                                    iMax2 = 0;
                                    iMin2 = 0;
                                    iMax3 = 0;
                                }
                                int i90 = i82 + 1;
                                int i91 = i45;
                                i3 = i41;
                                if (this.mLineCount < this.mMaximumVisibleLineCount && this.mEllipsized) {
                                    return;
                                }
                                f = f12;
                                i69 = i38;
                                z7 = z14;
                                i64 = i91;
                                i4 = i42;
                                i5 = i43;
                                charSequence3 = charSequence4;
                                i6 = i83;
                                textPaint = textPaint4;
                                i63 = iMax2;
                                iMin3 = iMin2;
                                iMax5 = iMax3;
                                i73 = i90;
                                i20 = i84;
                                i21 = i77;
                                z10 = z15;
                                paragraphInfoArr2 = paragraphInfoArr3;
                                i54 = i81;
                                i71 = i80;
                                iArr4 = iArr10;
                                measuredParagraph2 = measuredParagraph3;
                                i10 = i39;
                                i62 = i40;
                                paragraphConstraints5 = paragraphConstraints6;
                                i8 = i44;
                                f9 = f13;
                                lineHeightSpanArr2 = lineHeightSpanArr3;
                                truncateAt2 = truncateAt3;
                                i49 = i78;
                                f11 = f14;
                                lineBreaker2 = lineBreaker5;
                                i19 = i87;
                                i27 = i34;
                                textDirectionHeuristic3 = textDirectionHeuristic5;
                            } else {
                                i36 = i49 - i21;
                            }
                            f6 = f11 - i36;
                            if (f6 >= 0.0f) {
                            }
                            TextUtils.TruncateAt truncateAt32 = truncateAt2;
                            i37 = i69;
                            float f122 = f;
                            boolean z142 = z7;
                            PrecomputedText.ParagraphInfo[] paragraphInfoArr32 = paragraphInfoArr2;
                            boolean z152 = z10;
                            int i772 = i21;
                            int i782 = i49;
                            float f132 = f9;
                            int i792 = i8;
                            TextDirectionHeuristic textDirectionHeuristic52 = textDirectionHeuristic3;
                            int[] iArr102 = iArr4;
                            int i802 = i71;
                            int i812 = i35;
                            int i822 = i73;
                            int i832 = i6;
                            int i842 = i20;
                            int i852 = iMax5;
                            int i862 = i10;
                            int i872 = i19;
                            int i882 = i64;
                            int i892 = i52;
                            LineBreaker lineBreaker52 = lineBreaker2;
                            LineHeightSpan[] lineHeightSpanArr32 = lineHeightSpanArr2;
                            CharSequence charSequence42 = charSequence3;
                            LineBreaker.ParagraphConstraints paragraphConstraints62 = paragraphConstraints5;
                            MeasuredParagraph measuredParagraph32 = measuredParagraph2;
                            float f142 = f7;
                            boolean z162 = z5;
                            int iOut2 = out(charSequence42, i62, i75, iMin3, i852, i882, i63, i862, f122, f132, lineHeightSpanArr32, iArr102, fontMetricsInt, zArr2[i73], iArr5[i73], z142, measuredParagraph32, i832, z, z2, z162, chars, i892, truncateAt32, f6, fArr4[i73], textPaint, z13);
                            z5 = z162;
                            i52 = i892;
                            TextPaint textPaint42 = textPaint;
                            if (i75 >= i37) {
                            }
                            int i902 = i822 + 1;
                            int i912 = i45;
                            i3 = i41;
                            if (this.mLineCount < this.mMaximumVisibleLineCount) {
                            }
                            f = f122;
                            i69 = i38;
                            z7 = z142;
                            i64 = i912;
                            i4 = i42;
                            i5 = i43;
                            charSequence3 = charSequence42;
                            i6 = i832;
                            textPaint = textPaint42;
                            i63 = iMax2;
                            iMin3 = iMin2;
                            iMax5 = iMax3;
                            i73 = i902;
                            i20 = i842;
                            i21 = i772;
                            z10 = z152;
                            paragraphInfoArr2 = paragraphInfoArr32;
                            i54 = i812;
                            i71 = i802;
                            iArr4 = iArr102;
                            measuredParagraph2 = measuredParagraph32;
                            i10 = i39;
                            i62 = i40;
                            paragraphConstraints5 = paragraphConstraints62;
                            i8 = i44;
                            f9 = f132;
                            lineHeightSpanArr2 = lineHeightSpanArr32;
                            truncateAt2 = truncateAt32;
                            i49 = i782;
                            f11 = f142;
                            lineBreaker2 = lineBreaker52;
                            i19 = i872;
                            i27 = i34;
                            textDirectionHeuristic3 = textDirectionHeuristic52;
                        }
                    }
                    charSequence = charSequence3;
                    i29 = i53;
                    i13 = i6;
                    i30 = i49;
                    f2 = f9;
                    f3 = f;
                    paragraphConstraints2 = paragraphConstraints5;
                    i17 = i8;
                    paragraphInfoArr = paragraphInfoArr2;
                    textPaint2 = textPaint;
                    i12 = i54;
                    i14 = i3;
                    i15 = i4;
                    i16 = i5;
                    textDirectionHeuristic2 = textDirectionHeuristic3;
                    iArr6 = iArr4;
                    staticLayout = this;
                    z3 = z7;
                    i31 = i19;
                    lineBreaker3 = lineBreaker2;
                    f4 = f11;
                    truncateAt = truncateAt2;
                    if (i29 != i13) {
                        break;
                    }
                    i8 = i17;
                    staticLayout2 = staticLayout;
                    i3 = i14;
                    i4 = i15;
                    i5 = i16;
                    charSequence2 = charSequence;
                    i6 = i13;
                    textPaint = textPaint2;
                    z7 = z3;
                    truncateAt2 = truncateAt;
                    f11 = f4;
                    iArr = iArr3;
                    fArr3 = fArr7;
                    paragraphConstraints = paragraphConstraints2;
                    spanned = spanned2;
                    fArr = fArr4;
                    zArr = zArr2;
                    iArr2 = iArr5;
                    i11 = i24;
                    paragraphInfoArrCreateMeasuredParagraphs = paragraphInfoArr;
                    i7 = i12;
                    textDirectionHeuristic = textDirectionHeuristic2;
                    i9 = i31 + 1;
                    f = f3;
                    iArrNewUnpaddedIntArray = iArr6;
                    fArr2 = fArr5;
                    f9 = f2;
                    i49 = i30;
                    lineBreaker = lineBreaker3;
                } else {
                    spanned2 = spanned4;
                    lineHeightSpanArr2 = lineHeightSpanArr;
                }
                iArr4 = iArrNewUnpaddedIntArray;
                fArr4 = fArr;
                fArr5 = fArr2;
                fArr6 = null;
                MeasuredParagraph measuredParagraph4 = paragraphInfoArr2[i19].measured;
                char[] chars2 = measuredParagraph4.getChars();
                int[] rawArray3 = measuredParagraph4.getSpanEndCache().getRawArray();
                int[] rawArray22 = measuredParagraph4.getFontMetrics().getRawArray();
                LineBreaker.ParagraphConstraints paragraphConstraints42 = paragraphConstraints;
                paragraphConstraints42.setWidth(i21);
                paragraphConstraints42.setIndent(i20, i22);
                paragraphConstraints42.setTabStops(fArr6, TAB_INCREMENT);
                LineBreaker lineBreaker42 = lineBreaker;
                LineBreaker.Result resultComputeLineBreaks2 = lineBreaker42.computeLineBreaks(measuredParagraph4.getMeasuredText(), paragraphConstraints42, staticLayout2.mLineCount);
                lineCount = resultComputeLineBreaks2.getLineCount();
                i23 = i11;
                if (i23 >= lineCount) {
                }
                i25 = 0;
                while (i25 < lineCount) {
                }
                LineBreaker.ParagraphConstraints paragraphConstraints52 = paragraphConstraints42;
                i26 = staticLayout2.mMaximumVisibleLineCount - staticLayout2.mLineCount;
                if (truncateAt2 == null) {
                    if (i26 > 0) {
                        i27 = lineCount;
                        MeasuredParagraph measuredParagraph22 = measuredParagraph4;
                        i28 = i52;
                        int i622 = i28;
                        int i632 = i8;
                        int i642 = i3;
                        int iMin32 = i4;
                        int iMax52 = i5;
                        int i652 = 0;
                        int i662 = 0;
                        int i672 = 0;
                        while (i28 < i53) {
                        }
                        charSequence = charSequence3;
                        i29 = i53;
                        i13 = i6;
                        i30 = i49;
                        f2 = f9;
                        f3 = f;
                        paragraphConstraints2 = paragraphConstraints52;
                        i17 = i8;
                        paragraphInfoArr = paragraphInfoArr2;
                        textPaint2 = textPaint;
                        i12 = i54;
                        i14 = i3;
                        i15 = i4;
                        i16 = i5;
                        textDirectionHeuristic2 = textDirectionHeuristic3;
                        iArr6 = iArr4;
                        staticLayout = this;
                        z3 = z7;
                        i31 = i19;
                        lineBreaker3 = lineBreaker2;
                        f4 = f11;
                        truncateAt = truncateAt2;
                        if (i29 != i13) {
                        }
                    }
                }
            }
            i18 = i12;
            if ((i13 != i18 || charSequence.charAt(i13 - 1) == '\n') && staticLayout.mLineCount < staticLayout.mMaximumVisibleLineCount) {
                MeasuredParagraph measuredParagraphBuildForBidi = MeasuredParagraph.buildForBidi(charSequence, i13, i13, textDirectionHeuristic2, null);
                if (i15 == 0 && i16 != 0) {
                    fontMetricsInt.top = i14;
                    fontMetricsInt.ascent = i15;
                    fontMetricsInt.descent = i16;
                    fontMetricsInt.bottom = i17;
                } else {
                    textPaint2.getFontMetricsInt(fontMetricsInt);
                }
                int i92 = i13;
                staticLayout.out(charSequence, i92, i92, fontMetricsInt.ascent, fontMetricsInt.descent, fontMetricsInt.top, fontMetricsInt.bottom, i10, f3, f2, null, null, fontMetricsInt, false, 0, z3, measuredParagraphBuildForBidi, i92, z, z2, z5, null, i18, truncateAt, f4, 0.0f, textPaint2, false);
            }
            return;
        }
        paragraphConstraints = paragraphConstraints3;
        i3 = iMin;
        spanned = spanned3;
        i4 = iRound;
        i5 = iRound2;
        f = f8;
        i6 = i2;
        lineBreaker = lineBreakerBuild;
        i7 = i;
        paragraphInfoArrCreateMeasuredParagraphs = null;
        if (paragraphInfoArrCreateMeasuredParagraphs != null) {
        }
        i10 = i9;
        i11 = i10;
        iArrNewUnpaddedIntArray = null;
        iArr = null;
        fArr = null;
        fArr2 = null;
        fArr3 = null;
        zArr = null;
        iArr2 = null;
        while (true) {
            if (i9 < paragraphInfoArrCreateMeasuredParagraphs.length) {
            }
            i8 = i17;
            staticLayout2 = staticLayout;
            i3 = i14;
            i4 = i15;
            i5 = i16;
            charSequence2 = charSequence;
            i6 = i13;
            textPaint = textPaint2;
            z7 = z3;
            truncateAt2 = truncateAt;
            f11 = f4;
            iArr = iArr3;
            fArr3 = fArr7;
            paragraphConstraints = paragraphConstraints2;
            spanned = spanned2;
            fArr = fArr4;
            zArr = zArr2;
            iArr2 = iArr5;
            i11 = i24;
            paragraphInfoArrCreateMeasuredParagraphs = paragraphInfoArr;
            i7 = i12;
            textDirectionHeuristic = textDirectionHeuristic2;
            i9 = i31 + 1;
            f = f3;
            iArrNewUnpaddedIntArray = iArr6;
            fArr2 = fArr5;
            f9 = f2;
            i49 = i30;
            lineBreaker = lineBreaker3;
        }
        i18 = i12;
        if (i13 != i18) {
        }
        MeasuredParagraph measuredParagraphBuildForBidi2 = MeasuredParagraph.buildForBidi(charSequence, i13, i13, textDirectionHeuristic2, null);
        if (i15 == 0) {
            textPaint2.getFontMetricsInt(fontMetricsInt);
        }
        int i922 = i13;
        staticLayout.out(charSequence, i922, i922, fontMetricsInt.ascent, fontMetricsInt.descent, fontMetricsInt.top, fontMetricsInt.bottom, i10, f3, f2, null, null, fontMetricsInt, false, 0, z3, measuredParagraphBuildForBidi2, i922, z, z2, z5, null, i18, truncateAt, f4, 0.0f, textPaint2, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01e4  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int out(CharSequence charSequence, int i, int i2, int i3, int i4, int i5, int i6, int i7, float f, float f2, LineHeightSpan[] lineHeightSpanArr, int[] iArr, Paint.FontMetricsInt fontMetricsInt, boolean z, int i8, boolean z2, MeasuredParagraph measuredParagraph, int i9, boolean z3, boolean z4, boolean z5, char[] cArr, int i10, TextUtils.TruncateAt truncateAt, float f3, float f4, TextPaint textPaint, boolean z6) {
        int[] iArr2;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        TextUtils.TruncateAt truncateAt2;
        int i17;
        int i18;
        int i19;
        int i20;
        boolean z7;
        int i21;
        Paint.FontMetricsInt fontMetricsInt2 = fontMetricsInt;
        int i22 = this.mLineCount;
        int i23 = this.mColumns;
        int i24 = i22 * i23;
        int i25 = i23 + i24 + 1;
        int[] iArr3 = this.mLines;
        int paragraphDir = measuredParagraph.getParagraphDir();
        if (i25 >= iArr3.length) {
            int[] iArrNewUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(i25));
            System.arraycopy(iArr3, 0, iArrNewUnpaddedIntArray, 0, iArr3.length);
            this.mLines = iArrNewUnpaddedIntArray;
            iArr2 = iArrNewUnpaddedIntArray;
        } else {
            iArr2 = iArr3;
        }
        if (i22 >= this.mLineDirections.length) {
            Layout.Directions[] directionsArr = (Layout.Directions[]) ArrayUtils.newUnpaddedArray(Layout.Directions.class, GrowingArrayUtils.growSize(i22));
            Layout.Directions[] directionsArr2 = this.mLineDirections;
            System.arraycopy(directionsArr2, 0, directionsArr, 0, directionsArr2.length);
            this.mLineDirections = directionsArr;
        }
        int i26 = i3;
        if (lineHeightSpanArr != null) {
            fontMetricsInt2.ascent = i26;
            fontMetricsInt2.descent = i4;
            fontMetricsInt2.top = i5;
            fontMetricsInt2.bottom = i6;
            int i27 = 0;
            while (i27 < lineHeightSpanArr.length) {
                int i28 = i27;
                LineHeightSpan lineHeightSpan = lineHeightSpanArr[i28];
                if (lineHeightSpan instanceof LineHeightSpan.WithDensity) {
                    i21 = i28;
                    ((LineHeightSpan.WithDensity) lineHeightSpan).chooseHeight(charSequence, i, i2, iArr[i28], i7, fontMetricsInt2, textPaint);
                    fontMetricsInt2 = fontMetricsInt;
                } else {
                    i21 = i28;
                    fontMetricsInt2 = fontMetricsInt;
                    lineHeightSpan.chooseHeight(charSequence, i, i2, iArr[i21], i7, fontMetricsInt2);
                }
                i27 = i21 + 1;
            }
            i26 = fontMetricsInt2.ascent;
            i11 = fontMetricsInt2.descent;
            i12 = fontMetricsInt2.top;
            i13 = fontMetricsInt2.bottom;
        } else {
            i11 = i4;
            i12 = i5;
            i13 = i6;
        }
        int i29 = i26;
        int i30 = i11;
        int i31 = i12;
        boolean z8 = i22 == 0;
        int i32 = i22 + 1;
        int i33 = this.mMaximumVisibleLineCount;
        boolean z9 = i32 == i33;
        if (truncateAt != null) {
            boolean z10 = z6 && this.mLineCount + 1 == i33;
            if ((((i33 == 1 && z6) || (z8 && !z6)) && truncateAt != TextUtils.TruncateAt.MARQUEE) || (!z8 && ((z9 || !z6) && truncateAt == TextUtils.TruncateAt.END))) {
                truncateAt2 = truncateAt;
                i17 = i22;
                calculateEllipsis(i, i2, measuredParagraph, i10, f3, truncateAt2, i17, f4, textPaint, z10, cArr);
                i14 = i;
                i16 = i10;
                i15 = i2;
            } else {
                i14 = i;
                i15 = i2;
                i16 = i10;
                truncateAt2 = truncateAt;
                i17 = i22;
                int[] iArr4 = this.mLines;
                int i34 = this.mColumns;
                i18 = 0;
                iArr4[(i34 * i17) + 5] = 0;
                iArr4[(i34 * i17) + 6] = 0;
                if (!this.mEllipsized) {
                    int i35 = (i16 == i9 || i9 <= 0 || charSequence.charAt(i9 + (-1)) != '\n') ? i18 : 1;
                    i19 = (!(i15 == i9 && i35 == 0) && (i14 != i9 || i35 == 0)) ? i18 : 1;
                }
                if (z8) {
                    if (z4) {
                        this.mTopPadding = i31 - i29;
                    }
                    if (z3) {
                        i29 = i31;
                    }
                }
                if (i19 != 0) {
                    if (z4) {
                        this.mBottomPadding = i13 - i30;
                    }
                    if (z3) {
                        i30 = i13;
                    }
                }
                if (z2 || !(z5 || i19 == 0)) {
                    i20 = i18;
                } else {
                    double d = ((i30 - i29) * (f - 1.0f)) + f2;
                    i20 = d >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? (int) (d + EXTRA_ROUNDING) : -((int) ((-d) + EXTRA_ROUNDING));
                }
                iArr2[i24] = i14;
                iArr2[i24 + 1] = i7;
                iArr2[i24 + 2] = i30 + i20;
                iArr2[i24 + 3] = i20;
                z7 = this.mEllipsized;
                if (!z7 && z9) {
                    if (!z3) {
                        i13 = i30;
                    }
                    this.mMaxLineHeight = i7 + (i13 - i29);
                }
                int i36 = i7 + (i30 - i29) + i20;
                int i37 = this.mColumns;
                iArr2[i24 + i37] = i15;
                iArr2[i37 + i24 + 1] = i36;
                iArr2[i24] = iArr2[i24] | (!z ? 536870912 : i18);
                if (!z7) {
                    if (truncateAt2 == TextUtils.TruncateAt.START) {
                        iArr2[i24 + 4] = packHyphenEdit(i18, unpackEndHyphenEdit(i8));
                    } else if (truncateAt2 == TextUtils.TruncateAt.END) {
                        iArr2[i24 + 4] = packHyphenEdit(unpackStartHyphenEdit(i8), i18);
                    } else {
                        iArr2[i24 + 4] = packHyphenEdit(i18, i18);
                    }
                } else {
                    iArr2[i24 + 4] = i8;
                }
                iArr2[i24] = iArr2[i24] | (paragraphDir << 30);
                this.mLineDirections[i17] = measuredParagraph.getDirections(i14 - i16, i15 - i16);
                this.mLineCount++;
                return i36;
            }
        } else {
            i14 = i;
            i15 = i2;
            i16 = i10;
            truncateAt2 = truncateAt;
            i17 = i22;
        }
        i18 = 0;
        if (!this.mEllipsized) {
        }
        if (z8) {
        }
        if (i19 != 0) {
        }
        if (z2) {
            i20 = i18;
        }
        iArr2[i24] = i14;
        iArr2[i24 + 1] = i7;
        iArr2[i24 + 2] = i30 + i20;
        iArr2[i24 + 3] = i20;
        z7 = this.mEllipsized;
        if (!z7) {
            if (!z3) {
            }
            this.mMaxLineHeight = i7 + (i13 - i29);
        }
        int i362 = i7 + (i30 - i29) + i20;
        int i372 = this.mColumns;
        iArr2[i24 + i372] = i15;
        iArr2[i372 + i24 + 1] = i362;
        iArr2[i24] = iArr2[i24] | (!z ? 536870912 : i18);
        if (!z7) {
        }
        iArr2[i24] = iArr2[i24] | (paragraphDir << 30);
        this.mLineDirections[i17] = measuredParagraph.getDirections(i14 - i16, i15 - i16);
        this.mLineCount++;
        return i362;
    }

    private void calculateEllipsis(int i, int i2, MeasuredParagraph measuredParagraph, int i3, float f, TextUtils.TruncateAt truncateAt, int i4, float f2, TextPaint textPaint, boolean z, char[] cArr) {
        int i5;
        int i6;
        float totalInsets = f - getTotalInsets(i4);
        int i7 = 0;
        if (f2 <= totalInsets && !z) {
            int[] iArr = this.mLines;
            int i8 = this.mColumns;
            iArr[(i8 * i4) + 5] = 0;
            iArr[(i8 * i4) + 6] = 0;
            return;
        }
        float fMeasureText = textPaint.measureText(TextUtils.getEllipsisString(truncateAt));
        int i9 = i2 - i;
        float charWidthAt = 0.0f;
        if (truncateAt == TextUtils.TruncateAt.START) {
            if (this.mMaximumVisibleLineCount == 1) {
                int i10 = i9;
                float charWidthAt2 = 0.0f;
                while (true) {
                    if (i10 <= 0) {
                        break;
                    }
                    charWidthAt2 += measuredParagraph.getCharWidthAt(((i10 - 1) + i) - i3);
                    if (charWidthAt2 + fMeasureText > totalInsets) {
                        while (i10 < i9 && measuredParagraph.getCharWidthAt((i10 + i) - i3) == 0.0f) {
                            i10++;
                        }
                    } else {
                        i10--;
                    }
                }
                i6 = i10;
            } else {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Start Ellipsis only supported with one line");
                }
                i6 = 0;
            }
        } else if (truncateAt == TextUtils.TruncateAt.END || truncateAt == TextUtils.TruncateAt.MARQUEE || truncateAt == TextUtils.TruncateAt.END_SMALL) {
            float charWidthAt3 = 0.0f;
            while (i7 < i9) {
                charWidthAt3 += measuredParagraph.getCharWidthAt((i7 + i) - i3);
                if (charWidthAt3 + fMeasureText > totalInsets) {
                    break;
                } else {
                    i7++;
                }
            }
            int i11 = i9 - i7;
            if (z && i11 == 0 && i9 > 0) {
                int i12 = i9 - 1;
                while (i12 > 0) {
                    int i13 = (i12 + i) - i3;
                    if (measuredParagraph.getCharWidthAt(i13) != 0.0f || cArr == null || cArr[i13] == '\n') {
                        break;
                    } else {
                        i12--;
                    }
                }
                i5 = i9 - i12;
                i7 = i12;
            } else {
                i5 = i11;
            }
            i6 = i5;
        } else if (this.mMaximumVisibleLineCount == 1) {
            float f3 = totalInsets - fMeasureText;
            float f4 = f3 / 2.0f;
            int i14 = i9;
            float f5 = 0.0f;
            while (true) {
                if (i14 <= 0) {
                    break;
                }
                float charWidthAt4 = measuredParagraph.getCharWidthAt(((i14 - 1) + i) - i3) + f5;
                if (charWidthAt4 > f4) {
                    while (i14 < i9 && measuredParagraph.getCharWidthAt((i14 + i) - i3) == 0.0f) {
                        i14++;
                    }
                } else {
                    i14--;
                    f5 = charWidthAt4;
                }
            }
            float f6 = f3 - f5;
            while (i7 < i14) {
                charWidthAt += measuredParagraph.getCharWidthAt((i7 + i) - i3);
                if (charWidthAt > f6) {
                    break;
                } else {
                    i7++;
                }
            }
            i6 = i14 - i7;
        } else {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Middle Ellipsis only supported with one line");
            }
            i6 = 0;
        }
        this.mEllipsized = true;
        int[] iArr2 = this.mLines;
        int i15 = this.mColumns;
        iArr2[(i15 * i4) + 5] = i7;
        iArr2[(i15 * i4) + 6] = i6;
    }

    private float getTotalInsets(int i) {
        int[] iArr = this.mLeftIndents;
        int i2 = iArr != null ? iArr[Math.min(i, iArr.length - 1)] : 0;
        int[] iArr2 = this.mRightIndents;
        if (iArr2 != null) {
            i2 += iArr2[Math.min(i, iArr2.length - 1)];
        }
        return i2;
    }

    @Override // android.text.Layout
    public int getLineForVertical(int i) {
        int i2 = this.mLineCount;
        int[] iArr = this.mLines;
        int i3 = -1;
        while (i2 - i3 > 1) {
            int i4 = (i2 + i3) >> 1;
            if (iArr[(this.mColumns * i4) + 1] > i) {
                i2 = i4;
            } else {
                i3 = i4;
            }
        }
        if (i3 < 0) {
            return 0;
        }
        return i3;
    }

    @Override // android.text.Layout
    public int getLineCount() {
        return this.mLineCount;
    }

    @Override // android.text.Layout
    public int getLineTop(int i) {
        return this.mLines[(this.mColumns * i) + 1];
    }

    @Override // android.text.Layout
    public int getLineExtra(int i) {
        return this.mLines[(this.mColumns * i) + 3];
    }

    @Override // android.text.Layout
    public int getLineDescent(int i) {
        return this.mLines[(this.mColumns * i) + 2];
    }

    @Override // android.text.Layout
    public int getLineStart(int i) {
        return this.mLines[this.mColumns * i] & 536870911;
    }

    @Override // android.text.Layout
    public int getParagraphDirection(int i) {
        return this.mLines[this.mColumns * i] >> 30;
    }

    @Override // android.text.Layout
    public boolean getLineContainsTab(int i) {
        return (this.mLines[this.mColumns * i] & 536870912) != 0;
    }

    @Override // android.text.Layout
    public final Layout.Directions getLineDirections(int i) {
        if (i > getLineCount()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.mLineDirections[i];
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
    public int getStartHyphenEdit(int i) {
        return unpackStartHyphenEdit(this.mLines[(this.mColumns * i) + 4] & 255);
    }

    @Override // android.text.Layout
    public int getEndHyphenEdit(int i) {
        return unpackEndHyphenEdit(this.mLines[(this.mColumns * i) + 4] & 255);
    }

    @Override // android.text.Layout
    public int getIndentAdjust(int i, Layout.Alignment alignment) {
        if (alignment == Layout.Alignment.ALIGN_LEFT) {
            int[] iArr = this.mLeftIndents;
            if (iArr == null) {
                return 0;
            }
            return iArr[Math.min(i, iArr.length - 1)];
        }
        if (alignment == Layout.Alignment.ALIGN_RIGHT) {
            int[] iArr2 = this.mRightIndents;
            if (iArr2 == null) {
                return 0;
            }
            return -iArr2[Math.min(i, iArr2.length - 1)];
        }
        if (alignment == Layout.Alignment.ALIGN_CENTER) {
            int[] iArr3 = this.mLeftIndents;
            int i2 = iArr3 != null ? iArr3[Math.min(i, iArr3.length - 1)] : 0;
            int[] iArr4 = this.mRightIndents;
            return (i2 - (iArr4 != null ? iArr4[Math.min(i, iArr4.length - 1)] : 0)) >> 1;
        }
        throw new AssertionError("unhandled alignment " + alignment);
    }

    @Override // android.text.Layout
    public int getEllipsisCount(int i) {
        int i2 = this.mColumns;
        if (i2 < 7) {
            return 0;
        }
        return this.mLines[(i2 * i) + 6];
    }

    @Override // android.text.Layout
    public int getEllipsisStart(int i) {
        int i2 = this.mColumns;
        if (i2 < 7) {
            return 0;
        }
        return this.mLines[(i2 * i) + 5];
    }

    @Override // android.text.Layout
    public RectF computeDrawingBoundingBox() {
        if (this.mDrawingBounds == null) {
            this.mDrawingBounds = super.computeDrawingBoundingBox();
        }
        return this.mDrawingBounds;
    }

    @Override // android.text.Layout
    public int getHeight(boolean z) {
        int i;
        if (z && this.mLineCount > this.mMaximumVisibleLineCount && this.mMaxLineHeight == -1 && Log.isLoggable(TAG, 5)) {
            Log.w(TAG, "maxLineHeight should not be -1.  maxLines:" + this.mMaximumVisibleLineCount + " lineCount:" + this.mLineCount);
        }
        return (!z || this.mLineCount <= this.mMaximumVisibleLineCount || (i = this.mMaxLineHeight) == -1) ? super.getHeight() : i;
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
