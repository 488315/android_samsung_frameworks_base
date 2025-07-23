package android.text;

import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.text.LineBreakConfig;
import android.os.Trace;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pools;
import com.android.internal.util.ArrayUtils;

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
            Builder acquire = sPool.acquire();
            if (acquire == null) {
                acquire = new Builder();
            }
            acquire.mText = charSequence;
            acquire.mStart = i;
            acquire.mEnd = i2;
            acquire.mPaint = textPaint;
            acquire.mWidth = i3;
            acquire.mAlignment = Layout.Alignment.ALIGN_NORMAL;
            acquire.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
            acquire.mSpacingMult = 1.0f;
            acquire.mSpacingAdd = 0.0f;
            acquire.mIncludePad = true;
            acquire.mFallbackLineSpacing = false;
            acquire.mEllipsizedWidth = i3;
            acquire.mEllipsize = null;
            acquire.mMaxLines = Integer.MAX_VALUE;
            acquire.mBreakStrategy = 0;
            acquire.mHyphenationFrequency = 0;
            acquire.mJustificationMode = 0;
            acquire.mLineBreakConfig = LineBreakConfig.NONE;
            acquire.mUseBoundsForWidth = false;
            acquire.mMinimumFontMetrics = null;
            return acquire;
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private StaticLayout(android.text.StaticLayout.Builder r23, boolean r24, int r25) {
        /*
            r22 = this;
            android.text.TextUtils$TruncateAt r0 = android.text.StaticLayout.Builder.m5447$$Nest$fgetmEllipsize(r23)
            if (r0 != 0) goto Lc
            java.lang.CharSequence r0 = android.text.StaticLayout.Builder.m5465$$Nest$fgetmText(r23)
        La:
            r1 = r0
            goto L28
        Lc:
            java.lang.CharSequence r0 = android.text.StaticLayout.Builder.m5465$$Nest$fgetmText(r23)
            boolean r0 = r0 instanceof android.text.Spanned
            if (r0 == 0) goto L1e
            android.text.Layout$SpannedEllipsizer r0 = new android.text.Layout$SpannedEllipsizer
            java.lang.CharSequence r1 = android.text.StaticLayout.Builder.m5465$$Nest$fgetmText(r23)
            r0.<init>(r1)
            goto La
        L1e:
            android.text.Layout$Ellipsizer r0 = new android.text.Layout$Ellipsizer
            java.lang.CharSequence r1 = android.text.StaticLayout.Builder.m5465$$Nest$fgetmText(r23)
            r0.<init>(r1)
            goto La
        L28:
            android.text.TextPaint r2 = android.text.StaticLayout.Builder.m5459$$Nest$fgetmPaint(r23)
            int r3 = android.text.StaticLayout.Builder.m5468$$Nest$fgetmWidth(r23)
            android.text.Layout$Alignment r4 = android.text.StaticLayout.Builder.m5444$$Nest$fgetmAlignment(r23)
            android.text.TextDirectionHeuristic r5 = android.text.StaticLayout.Builder.m5466$$Nest$fgetmTextDir(r23)
            float r6 = android.text.StaticLayout.Builder.m5463$$Nest$fgetmSpacingMult(r23)
            float r7 = android.text.StaticLayout.Builder.m5462$$Nest$fgetmSpacingAdd(r23)
            boolean r8 = android.text.StaticLayout.Builder.m5453$$Nest$fgetmIncludePad(r23)
            boolean r9 = android.text.StaticLayout.Builder.m5450$$Nest$fgetmFallbackLineSpacing(r23)
            int r10 = android.text.StaticLayout.Builder.m5448$$Nest$fgetmEllipsizedWidth(r23)
            android.text.TextUtils$TruncateAt r11 = android.text.StaticLayout.Builder.m5447$$Nest$fgetmEllipsize(r23)
            int r12 = android.text.StaticLayout.Builder.m5457$$Nest$fgetmMaxLines(r23)
            int r13 = android.text.StaticLayout.Builder.m5445$$Nest$fgetmBreakStrategy(r23)
            int r14 = android.text.StaticLayout.Builder.m5452$$Nest$fgetmHyphenationFrequency(r23)
            int[] r15 = android.text.StaticLayout.Builder.m5455$$Nest$fgetmLeftIndents(r23)
            int[] r16 = android.text.StaticLayout.Builder.m5460$$Nest$fgetmRightIndents(r23)
            int r17 = android.text.StaticLayout.Builder.m5454$$Nest$fgetmJustificationMode(r23)
            android.graphics.text.LineBreakConfig r18 = android.text.StaticLayout.Builder.m5456$$Nest$fgetmLineBreakConfig(r23)
            boolean r19 = android.text.StaticLayout.Builder.m5467$$Nest$fgetmUseBoundsForWidth(r23)
            boolean r20 = android.text.StaticLayout.Builder.m5461$$Nest$fgetmShiftDrawingOffsetForStartOverhang(r23)
            android.graphics.Paint$FontMetrics r21 = android.text.StaticLayout.Builder.m5458$$Nest$fgetmMinimumFontMetrics(r23)
            r0 = r22
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21)
            r1 = 0
            r0.mDrawingBounds = r1
            r1 = -1
            r0.mMaxLineHeight = r1
            r1 = 2147483647(0x7fffffff, float:NaN)
            r0.mMaximumVisibleLineCount = r1
            r1 = r25
            r0.mColumns = r1
            android.text.TextUtils$TruncateAt r1 = android.text.StaticLayout.Builder.m5447$$Nest$fgetmEllipsize(r23)
            if (r1 == 0) goto La6
            java.lang.CharSequence r1 = r0.getText()
            android.text.Layout$Ellipsizer r1 = (android.text.Layout.Ellipsizer) r1
            r1.mLayout = r0
            int r2 = android.text.StaticLayout.Builder.m5448$$Nest$fgetmEllipsizedWidth(r23)
            r1.mWidth = r2
            android.text.TextUtils$TruncateAt r2 = android.text.StaticLayout.Builder.m5447$$Nest$fgetmEllipsize(r23)
            r1.mMethod = r2
        La6:
            java.lang.Class<android.text.Layout$Directions> r1 = android.text.Layout.Directions.class
            r2 = 2
            java.lang.Object[] r1 = com.android.internal.util.ArrayUtils.newUnpaddedArray(r1, r2)
            android.text.Layout$Directions[] r1 = (android.text.Layout.Directions[]) r1
            r0.mLineDirections = r1
            int r1 = r0.mColumns
            int r1 = r1 * r2
            int[] r1 = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(r1)
            r0.mLines = r1
            int r1 = android.text.StaticLayout.Builder.m5457$$Nest$fgetmMaxLines(r23)
            r0.mMaximumVisibleLineCount = r1
            int[] r1 = android.text.StaticLayout.Builder.m5455$$Nest$fgetmLeftIndents(r23)
            r0.mLeftIndents = r1
            int[] r1 = android.text.StaticLayout.Builder.m5460$$Nest$fgetmRightIndents(r23)
            r0.mRightIndents = r1
            java.lang.String r1 = "Constructing StaticLayout"
            android.os.Trace.beginSection(r1)
            boolean r1 = android.text.StaticLayout.Builder.m5453$$Nest$fgetmIncludePad(r23)     // Catch: java.lang.Throwable -> Le0
            r2 = r23
            r3 = r24
            r0.generate(r2, r1, r3)     // Catch: java.lang.Throwable -> Le0
            android.os.Trace.endSection()
            return
        Le0:
            r0 = move-exception
            android.os.Trace.endSection()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.StaticLayout.<init>(android.text.StaticLayout$Builder, boolean, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:162:0x058a, code lost:
    
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
    /* JADX WARN: Removed duplicated region for block: B:137:0x0474  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x04dc  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x050b  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0635 A[LOOP:0: B:31:0x01df->B:171:0x0635, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0687 A[EDGE_INSN: B:172:0x0687->B:173:0x0687 BREAK  A[LOOP:0: B:31:0x01df->B:171:0x0635], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0320  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x066d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:213:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x032d A[LOOP:3: B:63:0x032b->B:64:0x032d, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x03d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void generate(android.text.StaticLayout.Builder r65, boolean r66, boolean r67) {
        /*
            Method dump skipped, instructions count: 1769
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.StaticLayout.generate(android.text.StaticLayout$Builder, boolean, boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x015b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x018f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01e4  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0123  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int out(java.lang.CharSequence r24, int r25, int r26, int r27, int r28, int r29, int r30, int r31, float r32, float r33, android.text.style.LineHeightSpan[] r34, int[] r35, android.graphics.Paint.FontMetricsInt r36, boolean r37, int r38, boolean r39, android.text.MeasuredParagraph r40, int r41, boolean r42, boolean r43, boolean r44, char[] r45, int r46, android.text.TextUtils.TruncateAt r47, float r48, float r49, android.text.TextPaint r50, boolean r51) {
        /*
            Method dump skipped, instructions count: 514
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.StaticLayout.out(java.lang.CharSequence, int, int, int, int, int, int, int, float, float, android.text.style.LineHeightSpan[], int[], android.graphics.Paint$FontMetricsInt, boolean, int, boolean, android.text.MeasuredParagraph, int, boolean, boolean, boolean, char[], int, android.text.TextUtils$TruncateAt, float, float, android.text.TextPaint, boolean):int");
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
        float measureText = textPaint.measureText(TextUtils.getEllipsisString(truncateAt));
        int i9 = i2 - i;
        float f3 = 0.0f;
        if (truncateAt == TextUtils.TruncateAt.START) {
            if (this.mMaximumVisibleLineCount == 1) {
                int i10 = i9;
                float f4 = 0.0f;
                while (true) {
                    if (i10 <= 0) {
                        break;
                    }
                    f4 += measuredParagraph.getCharWidthAt(((i10 - 1) + i) - i3);
                    if (f4 + measureText > totalInsets) {
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
            float f5 = 0.0f;
            while (i7 < i9) {
                f5 += measuredParagraph.getCharWidthAt((i7 + i) - i3);
                if (f5 + measureText > totalInsets) {
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
            float f6 = totalInsets - measureText;
            float f7 = f6 / 2.0f;
            int i14 = i9;
            float f8 = 0.0f;
            while (true) {
                if (i14 <= 0) {
                    break;
                }
                float charWidthAt = measuredParagraph.getCharWidthAt(((i14 - 1) + i) - i3) + f8;
                if (charWidthAt > f7) {
                    while (i14 < i9 && measuredParagraph.getCharWidthAt((i14 + i) - i3) == 0.0f) {
                        i14++;
                    }
                } else {
                    i14--;
                    f8 = charWidthAt;
                }
            }
            float f9 = f6 - f8;
            while (i7 < i14) {
                f3 += measuredParagraph.getCharWidthAt((i7 + i) - i3);
                if (f3 > f9) {
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
