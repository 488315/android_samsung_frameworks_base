package android.text;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.text.LineBreakConfig;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.method.OffsetMapping;
import android.text.style.ReplacementSpan;
import android.text.style.UpdateLayout;
import android.text.style.WrapTogetherSpan;
import android.util.ArraySet;
import android.util.Pools;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import com.android.text.flags.Flags;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class DynamicLayout extends Layout {
    private static final int BLOCK_MINIMUM_CHARACTER_LENGTH = 400;
    private static final int COLUMNS_ELLIPSIZE = 7;
    private static final int COLUMNS_NORMAL = 5;
    private static final int DESCENT = 2;
    private static final int DIR = 0;
    private static final int DIR_SHIFT = 30;
    private static final int ELLIPSIS_COUNT = 6;
    private static final int ELLIPSIS_START = 5;
    private static final int ELLIPSIS_UNDEFINED = Integer.MIN_VALUE;
    private static final int EXTRA = 3;
    private static final int HYPHEN = 4;
    private static final int HYPHEN_MASK = 255;
    public static final int INVALID_BLOCK_INDEX = -1;
    private static final int MAY_PROTRUDE_FROM_TOP_OR_BOTTOM = 4;
    private static final int MAY_PROTRUDE_FROM_TOP_OR_BOTTOM_MASK = 256;
    private static final int PRIORITY = 128;
    private static final int START = 0;
    private static final int START_MASK = 536870911;
    private static final int TAB = 0;
    private static final int TAB_MASK = 536870912;
    private static final int TOP = 1;
    private static StaticLayout.Builder sBuilder;
    private static final Object[] sLock = new Object[0];
    private static StaticLayout sStaticLayout;
    private CharSequence mBase;
    private int[] mBlockEndLines;
    private int[] mBlockIndices;
    private ArraySet<Integer> mBlocksAlwaysNeedToBeRedrawn;
    private int mBottomPadding;
    private int mBreakStrategy;
    private CharSequence mDisplay;
    private boolean mEllipsize;
    private TextUtils.TruncateAt mEllipsizeAt;
    private int mEllipsizedWidth;
    private boolean mFallbackLineSpacing;
    private int mHyphenationFrequency;
    private boolean mIncludePad;
    private int mIndexFirstChangedBlock;
    private PackedIntVector mInts;
    private int mJustificationMode;
    private LineBreakConfig mLineBreakConfig;
    Paint.FontMetrics mMinimumFontMetrics;
    private int mNumberOfBlocks;
    private PackedObjectVector<Layout.Directions> mObjects;
    private boolean mShiftDrawingOffsetForStartOverhang;
    private Rect mTempRect;
    private int mTopPadding;
    private boolean mUseBoundsForWidth;
    private ChangeWatcher mWatcher;

    public static final class Builder {
        private static final Pools.SynchronizedPool<Builder> sPool = new Pools.SynchronizedPool<>(3);
        private Layout.Alignment mAlignment;
        private CharSequence mBase;
        private int mBreakStrategy;
        private CharSequence mDisplay;
        private TextUtils.TruncateAt mEllipsize;
        private int mEllipsizedWidth;
        private boolean mFallbackLineSpacing;
        private int mHyphenationFrequency;
        private boolean mIncludePad;
        private int mJustificationMode;
        private Paint.FontMetrics mMinimumFontMetrics;
        private TextPaint mPaint;
        private boolean mShiftDrawingOffsetForStartOverhang;
        private float mSpacingAdd;
        private float mSpacingMult;
        private TextDirectionHeuristic mTextDir;
        private boolean mUseBoundsForWidth;
        private int mWidth;
        private LineBreakConfig mLineBreakConfig = LineBreakConfig.NONE;
        private final Paint.FontMetricsInt mFontMetricsInt = new Paint.FontMetricsInt();

        private Builder() {
        }

        public static Builder obtain(CharSequence charSequence, TextPaint textPaint, int i) {
            Builder builderAcquire = sPool.acquire();
            if (builderAcquire == null) {
                builderAcquire = new Builder();
            }
            builderAcquire.mBase = charSequence;
            builderAcquire.mDisplay = charSequence;
            builderAcquire.mPaint = textPaint;
            builderAcquire.mWidth = i;
            builderAcquire.mAlignment = Layout.Alignment.ALIGN_NORMAL;
            builderAcquire.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
            builderAcquire.mSpacingMult = 1.0f;
            builderAcquire.mSpacingAdd = 0.0f;
            builderAcquire.mIncludePad = true;
            builderAcquire.mFallbackLineSpacing = false;
            builderAcquire.mEllipsizedWidth = i;
            builderAcquire.mEllipsize = null;
            builderAcquire.mBreakStrategy = 0;
            builderAcquire.mHyphenationFrequency = 0;
            builderAcquire.mJustificationMode = 0;
            builderAcquire.mLineBreakConfig = LineBreakConfig.NONE;
            return builderAcquire;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void recycle(Builder builder) {
            builder.mBase = null;
            builder.mDisplay = null;
            builder.mPaint = null;
            sPool.release(builder);
        }

        public Builder setDisplayText(CharSequence charSequence) {
            this.mDisplay = charSequence;
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

        public Builder setBreakStrategy(int i) {
            this.mBreakStrategy = i;
            return this;
        }

        public Builder setHyphenationFrequency(int i) {
            this.mHyphenationFrequency = i;
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

        public DynamicLayout build() {
            DynamicLayout dynamicLayout = new DynamicLayout(this);
            recycle(this);
            return dynamicLayout;
        }
    }

    @Deprecated
    public DynamicLayout(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, boolean z) {
        this(charSequence, charSequence, textPaint, i, alignment, f, f2, z);
    }

    @Deprecated
    public DynamicLayout(CharSequence charSequence, CharSequence charSequence2, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, boolean z) {
        this(charSequence, charSequence2, textPaint, i, alignment, f, f2, z, null, 0);
    }

    @Deprecated
    public DynamicLayout(CharSequence charSequence, CharSequence charSequence2, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, boolean z, TextUtils.TruncateAt truncateAt, int i2) {
        this(charSequence, charSequence2, textPaint, i, alignment, TextDirectionHeuristics.FIRSTSTRONG_LTR, f, f2, z, 0, 0, 0, LineBreakConfig.NONE, truncateAt, i2);
    }

    @Deprecated
    public DynamicLayout(CharSequence charSequence, CharSequence charSequence2, TextPaint textPaint, int i, Layout.Alignment alignment, TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean z, int i2, int i3, int i4, LineBreakConfig lineBreakConfig, TextUtils.TruncateAt truncateAt, int i5) {
        super(createEllipsizer(truncateAt, charSequence2), textPaint, i, alignment, textDirectionHeuristic, f, f2, z, false, i5, truncateAt, Integer.MAX_VALUE, i2, i3, null, null, i4, lineBreakConfig, false, false, null);
        this.mTempRect = new Rect();
        Builder ellipsize = Builder.obtain(charSequence, textPaint, i).setAlignment(alignment).setTextDirection(textDirectionHeuristic).setLineSpacing(f2, f).setEllipsizedWidth(i5).setEllipsize(truncateAt);
        this.mDisplay = charSequence2;
        this.mIncludePad = z;
        this.mBreakStrategy = i2;
        this.mJustificationMode = i4;
        this.mHyphenationFrequency = i3;
        this.mLineBreakConfig = lineBreakConfig;
        generate(ellipsize);
        Builder.recycle(ellipsize);
    }

    private DynamicLayout(Builder builder) {
        super(createEllipsizer(builder.mEllipsize, builder.mDisplay), builder.mPaint, builder.mWidth, builder.mAlignment, builder.mTextDir, builder.mSpacingMult, builder.mSpacingAdd, builder.mIncludePad, builder.mFallbackLineSpacing, builder.mEllipsizedWidth, builder.mEllipsize, Integer.MAX_VALUE, builder.mBreakStrategy, builder.mHyphenationFrequency, null, null, builder.mJustificationMode, builder.mLineBreakConfig, builder.mUseBoundsForWidth, builder.mShiftDrawingOffsetForStartOverhang, builder.mMinimumFontMetrics);
        this.mTempRect = new Rect();
        this.mDisplay = builder.mDisplay;
        this.mIncludePad = builder.mIncludePad;
        this.mBreakStrategy = builder.mBreakStrategy;
        this.mJustificationMode = builder.mJustificationMode;
        this.mHyphenationFrequency = builder.mHyphenationFrequency;
        this.mLineBreakConfig = builder.mLineBreakConfig;
        generate(builder);
    }

    private static CharSequence createEllipsizer(TextUtils.TruncateAt truncateAt, CharSequence charSequence) {
        if (truncateAt == null) {
            return charSequence;
        }
        if (charSequence instanceof Spanned) {
            return new Layout.SpannedEllipsizer(charSequence);
        }
        return new Layout.Ellipsizer(charSequence);
    }

    private void generate(Builder builder) {
        int[] iArr;
        this.mBase = builder.mBase;
        this.mFallbackLineSpacing = builder.mFallbackLineSpacing;
        this.mUseBoundsForWidth = builder.mUseBoundsForWidth;
        this.mShiftDrawingOffsetForStartOverhang = builder.mShiftDrawingOffsetForStartOverhang;
        this.mMinimumFontMetrics = builder.mMinimumFontMetrics;
        if (builder.mEllipsize != null) {
            this.mInts = new PackedIntVector(7);
            this.mEllipsizedWidth = builder.mEllipsizedWidth;
            this.mEllipsizeAt = builder.mEllipsize;
            Layout.Ellipsizer ellipsizer = (Layout.Ellipsizer) getText();
            ellipsizer.mLayout = this;
            ellipsizer.mWidth = builder.mEllipsizedWidth;
            ellipsizer.mMethod = builder.mEllipsize;
            this.mEllipsize = true;
        } else {
            this.mInts = new PackedIntVector(5);
            this.mEllipsizedWidth = builder.mWidth;
            this.mEllipsizeAt = null;
        }
        this.mObjects = new PackedObjectVector<>(1);
        if (builder.mEllipsize != null) {
            iArr = new int[7];
            iArr[5] = Integer.MIN_VALUE;
        } else {
            iArr = new int[5];
        }
        Layout.Directions[] directionsArr = {DIRS_ALL_LEFT_TO_RIGHT};
        Paint.FontMetricsInt fontMetricsInt = builder.mFontMetricsInt;
        builder.mPaint.getFontMetricsInt(fontMetricsInt);
        int i = fontMetricsInt.ascent;
        int i2 = fontMetricsInt.descent;
        iArr[0] = 1073741824;
        iArr[1] = 0;
        iArr[2] = i2;
        this.mInts.insertAt(0, iArr);
        iArr[1] = i2 - i;
        this.mInts.insertAt(1, iArr);
        this.mObjects.insertAt(0, directionsArr);
        reflow(this.mBase, 0, 0, this.mDisplay.length());
        if (this.mBase instanceof Spannable) {
            if (this.mWatcher == null) {
                this.mWatcher = new ChangeWatcher(this);
            }
            CharSequence charSequence = this.mBase;
            Spannable spannable = (Spannable) charSequence;
            int length = charSequence.length();
            for (ChangeWatcher changeWatcher : (ChangeWatcher[]) spannable.getSpans(0, length, ChangeWatcher.class)) {
                spannable.removeSpan(changeWatcher);
            }
            spannable.setSpan(this.mWatcher, 0, length, 8388626);
        }
    }

    public void reflow(CharSequence charSequence, int i, int i2, int i3) {
        StaticLayout staticLayout;
        StaticLayout.Builder builderObtain;
        int topPadding;
        int bottomPadding;
        int[] iArr;
        boolean z;
        if (charSequence != this.mBase) {
            return;
        }
        CharSequence charSequence2 = this.mDisplay;
        int length = charSequence2.length();
        int iLastIndexOf = TextUtils.lastIndexOf(charSequence2, '\n', i - 1);
        int i4 = i - (iLastIndexOf < 0 ? 0 : iLastIndexOf + 1);
        int i5 = i2 + i4;
        int i6 = i3 + i4;
        int i7 = i - i4;
        int i8 = i7 + i6;
        int iIndexOf = TextUtils.indexOf(charSequence2, '\n', i8);
        int i9 = (iIndexOf < 0 ? length : iIndexOf + 1) - i8;
        int i10 = i5 + i9;
        int i11 = i6 + i9;
        if (charSequence2 instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence2;
            do {
                Object[] spans = spanned.getSpans(i7, i7 + i11, WrapTogetherSpan.class);
                z = false;
                for (int i12 = 0; i12 < spans.length; i12++) {
                    int spanStart = spanned.getSpanStart(spans[i12]);
                    int spanEnd = spanned.getSpanEnd(spans[i12]);
                    if (spanStart < i7) {
                        int i13 = i7 - spanStart;
                        i10 += i13;
                        i11 += i13;
                        i7 -= i13;
                        z = true;
                    }
                    int i14 = i7 + i11;
                    if (spanEnd > i14) {
                        int i15 = spanEnd - i14;
                        i10 += i15;
                        i11 += i15;
                        z = true;
                    }
                }
            } while (z);
        }
        int lineForOffset = getLineForOffset(i7);
        int lineTop = getLineTop(lineForOffset);
        int lineForOffset2 = getLineForOffset(i7 + i10);
        int i16 = i7 + i11;
        if (i16 == length) {
            lineForOffset2 = getLineCount();
        }
        int lineTop2 = getLineTop(lineForOffset2);
        boolean z2 = lineForOffset2 == getLineCount();
        synchronized (sLock) {
            staticLayout = sStaticLayout;
            builderObtain = sBuilder;
            sStaticLayout = null;
            sBuilder = null;
        }
        if (builderObtain == null) {
            builderObtain = StaticLayout.Builder.obtain(charSequence2, i7, i16, getPaint(), getWidth());
        }
        StaticLayout.Builder builder = builderObtain;
        builder.setText(charSequence2, i7, i16).setPaint(getPaint()).setWidth(getWidth()).setTextDirection(getTextDirectionHeuristic()).setLineSpacing(getSpacingAdd(), getSpacingMultiplier()).setUseLineSpacingFromFallbacks(this.mFallbackLineSpacing).setEllipsizedWidth(this.mEllipsizedWidth).setEllipsize(this.mEllipsizeAt).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode).setLineBreakConfig(this.mLineBreakConfig).setAddLastLineLineSpacing(!z2).setIncludePad(false).setUseBoundsForWidth(this.mUseBoundsForWidth).setShiftDrawingOffsetForStartOverhang(this.mShiftDrawingOffsetForStartOverhang).setMinimumFontMetrics(this.mMinimumFontMetrics).setCalculateBounds(true);
        StaticLayout staticLayoutBuildPartialStaticLayoutForDynamicLayout = builder.buildPartialStaticLayoutForDynamicLayout(true, staticLayout);
        int lineCount = staticLayoutBuildPartialStaticLayoutForDynamicLayout.getLineCount();
        if (i16 != length && staticLayoutBuildPartialStaticLayoutForDynamicLayout.getLineStart(lineCount - 1) == i16) {
            lineCount--;
        }
        int i17 = lineForOffset2 - lineForOffset;
        this.mInts.deleteAt(lineForOffset, i17);
        this.mObjects.deleteAt(lineForOffset, i17);
        int lineTop3 = staticLayoutBuildPartialStaticLayoutForDynamicLayout.getLineTop(lineCount);
        if (this.mIncludePad && lineForOffset == 0) {
            topPadding = staticLayoutBuildPartialStaticLayoutForDynamicLayout.getTopPadding();
            this.mTopPadding = topPadding;
            lineTop3 -= topPadding;
        } else {
            topPadding = 0;
        }
        if (this.mIncludePad && z2) {
            bottomPadding = staticLayoutBuildPartialStaticLayoutForDynamicLayout.getBottomPadding();
            this.mBottomPadding = bottomPadding;
            lineTop3 += bottomPadding;
        } else {
            bottomPadding = 0;
        }
        this.mInts.adjustValuesBelow(lineForOffset, 0, i11 - i10);
        this.mInts.adjustValuesBelow(lineForOffset, 1, (lineTop - lineTop2) + lineTop3);
        char c = 5;
        if (this.mEllipsize) {
            iArr = new int[7];
            iArr[5] = Integer.MIN_VALUE;
        } else {
            iArr = new int[5];
        }
        Layout.Directions[] directionsArr = new Layout.Directions[1];
        int i18 = 0;
        while (i18 < lineCount) {
            int lineStart = staticLayoutBuildPartialStaticLayoutForDynamicLayout.getLineStart(i18);
            iArr[0] = lineStart;
            int paragraphDirection = lineStart | (staticLayoutBuildPartialStaticLayoutForDynamicLayout.getParagraphDirection(i18) << 30);
            iArr[0] = paragraphDirection;
            iArr[0] = paragraphDirection | (staticLayoutBuildPartialStaticLayoutForDynamicLayout.getLineContainsTab(i18) ? 536870912 : 0);
            int lineTop4 = staticLayoutBuildPartialStaticLayoutForDynamicLayout.getLineTop(i18) + lineTop;
            if (i18 > 0) {
                lineTop4 -= topPadding;
            }
            iArr[1] = lineTop4;
            int lineDescent = staticLayoutBuildPartialStaticLayoutForDynamicLayout.getLineDescent(i18);
            char c2 = c;
            int i19 = lineCount - 1;
            if (i18 == i19) {
                lineDescent += bottomPadding;
            }
            iArr[2] = lineDescent;
            iArr[3] = staticLayoutBuildPartialStaticLayoutForDynamicLayout.getLineExtra(i18);
            directionsArr[0] = staticLayoutBuildPartialStaticLayoutForDynamicLayout.getLineDirections(i18);
            int lineStart2 = i18 == i19 ? i16 : staticLayoutBuildPartialStaticLayoutForDynamicLayout.getLineStart(i18 + 1);
            StaticLayout.Builder builder2 = builder;
            int i20 = lineTop;
            int iPackHyphenEdit = StaticLayout.packHyphenEdit(staticLayoutBuildPartialStaticLayoutForDynamicLayout.getStartHyphenEdit(i18), staticLayoutBuildPartialStaticLayoutForDynamicLayout.getEndHyphenEdit(i18));
            iArr[4] = iPackHyphenEdit;
            iArr[4] = iPackHyphenEdit | (contentMayProtrudeFromLineTopOrBottom(charSequence2, lineStart, lineStart2) ? 256 : 0);
            if (this.mEllipsize) {
                iArr[c2] = staticLayoutBuildPartialStaticLayoutForDynamicLayout.getEllipsisStart(i18);
                iArr[6] = staticLayoutBuildPartialStaticLayoutForDynamicLayout.getEllipsisCount(i18);
            }
            int i21 = lineForOffset + i18;
            this.mInts.insertAt(i21, iArr);
            this.mObjects.insertAt(i21, directionsArr);
            i18++;
            c = c2;
            lineTop = i20;
            builder = builder2;
        }
        StaticLayout.Builder builder3 = builder;
        updateBlocks(lineForOffset, lineForOffset2 - 1, lineCount);
        builder3.finish();
        synchronized (sLock) {
            sStaticLayout = staticLayoutBuildPartialStaticLayoutForDynamicLayout;
            sBuilder = builder3;
        }
    }

    private boolean contentMayProtrudeFromLineTopOrBottom(CharSequence charSequence, int i, int i2) {
        if ((charSequence instanceof Spanned) && ((ReplacementSpan[]) ((Spanned) charSequence).getSpans(i, i2, ReplacementSpan.class)).length > 0) {
            return true;
        }
        TextPaint paint = getPaint();
        if (charSequence instanceof PrecomputedText) {
            ((PrecomputedText) charSequence).getBounds(i, i2, this.mTempRect);
        } else {
            paint.getTextBounds(charSequence, i, i2, this.mTempRect);
        }
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        return this.mTempRect.top < fontMetricsInt.top || this.mTempRect.bottom > fontMetricsInt.bottom;
    }

    private void createBlocks() {
        this.mNumberOfBlocks = 0;
        CharSequence charSequence = this.mDisplay;
        int i = 400;
        while (true) {
            int iIndexOf = TextUtils.indexOf(charSequence, '\n', i);
            if (iIndexOf < 0) {
                break;
            }
            addBlockAtOffset(iIndexOf);
            i = iIndexOf + 400;
        }
        addBlockAtOffset(charSequence.length());
        this.mBlockIndices = new int[this.mBlockEndLines.length];
        for (int i2 = 0; i2 < this.mBlockEndLines.length; i2++) {
            this.mBlockIndices[i2] = -1;
        }
    }

    public ArraySet<Integer> getBlocksAlwaysNeedToBeRedrawn() {
        return this.mBlocksAlwaysNeedToBeRedrawn;
    }

    private void updateAlwaysNeedsToBeRedrawn(int i) {
        int i2 = this.mBlockEndLines[i];
        for (int i3 = i == 0 ? 0 : this.mBlockEndLines[i - 1] + 1; i3 <= i2; i3++) {
            if (getContentMayProtrudeFromTopOrBottom(i3)) {
                if (this.mBlocksAlwaysNeedToBeRedrawn == null) {
                    this.mBlocksAlwaysNeedToBeRedrawn = new ArraySet<>();
                }
                this.mBlocksAlwaysNeedToBeRedrawn.add(Integer.valueOf(i));
                return;
            }
        }
        ArraySet<Integer> arraySet = this.mBlocksAlwaysNeedToBeRedrawn;
        if (arraySet != null) {
            arraySet.remove(Integer.valueOf(i));
        }
    }

    private void addBlockAtOffset(int i) {
        int lineForOffset = getLineForOffset(i);
        int[] iArr = this.mBlockEndLines;
        if (iArr == null) {
            int[] iArrNewUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(1);
            this.mBlockEndLines = iArrNewUnpaddedIntArray;
            int i2 = this.mNumberOfBlocks;
            iArrNewUnpaddedIntArray[i2] = lineForOffset;
            updateAlwaysNeedsToBeRedrawn(i2);
            this.mNumberOfBlocks++;
            return;
        }
        int i3 = this.mNumberOfBlocks;
        if (lineForOffset > iArr[i3 - 1]) {
            this.mBlockEndLines = GrowingArrayUtils.append(iArr, i3, lineForOffset);
            updateAlwaysNeedsToBeRedrawn(this.mNumberOfBlocks);
            this.mNumberOfBlocks++;
        }
    }

    public void updateBlocks(int i, int i2, int i3) {
        int i4;
        if (this.mBlockEndLines == null) {
            createBlocks();
            return;
        }
        int i5 = 0;
        while (true) {
            if (i5 >= this.mNumberOfBlocks) {
                i5 = -1;
                break;
            } else if (this.mBlockEndLines[i5] >= i) {
                break;
            } else {
                i5++;
            }
        }
        int i6 = i5;
        while (true) {
            i4 = this.mNumberOfBlocks;
            if (i6 >= i4) {
                i6 = -1;
                break;
            } else if (this.mBlockEndLines[i6] >= i2) {
                break;
            } else {
                i6++;
            }
        }
        int[] iArr = this.mBlockEndLines;
        int i7 = iArr[i6];
        int i8 = i > (i5 == 0 ? 0 : iArr[i5 + (-1)] + 1) ? 1 : 0;
        boolean z = i3 > 0;
        boolean z2 = i2 < i7;
        int i9 = z ? i8 + 1 : i8;
        if (z2) {
            i9++;
        }
        int i10 = (i6 - i5) + 1;
        int i11 = (i4 + i9) - i10;
        if (i11 == 0) {
            iArr[0] = 0;
            this.mBlockIndices[0] = -1;
            this.mNumberOfBlocks = 1;
            return;
        }
        if (i11 > iArr.length) {
            int[] iArrNewUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(Math.max(iArr.length * 2, i11));
            int[] iArr2 = new int[iArrNewUnpaddedIntArray.length];
            System.arraycopy(this.mBlockEndLines, 0, iArrNewUnpaddedIntArray, 0, i5);
            System.arraycopy(this.mBlockIndices, 0, iArr2, 0, i5);
            int i12 = i6 + 1;
            int i13 = i5 + i9;
            System.arraycopy(this.mBlockEndLines, i12, iArrNewUnpaddedIntArray, i13, (this.mNumberOfBlocks - i6) - 1);
            System.arraycopy(this.mBlockIndices, i12, iArr2, i13, (this.mNumberOfBlocks - i6) - 1);
            this.mBlockEndLines = iArrNewUnpaddedIntArray;
            this.mBlockIndices = iArr2;
        } else if (i9 + i10 != 0) {
            int i14 = i6 + 1;
            int i15 = i5 + i9;
            System.arraycopy(iArr, i14, iArr, i15, (i4 - i6) - 1);
            int[] iArr3 = this.mBlockIndices;
            System.arraycopy(iArr3, i14, iArr3, i15, (this.mNumberOfBlocks - i6) - 1);
        }
        if (i9 + i10 != 0 && this.mBlocksAlwaysNeedToBeRedrawn != null) {
            ArraySet<Integer> arraySet = new ArraySet<>();
            int i16 = i9 - i10;
            for (int i17 = 0; i17 < this.mBlocksAlwaysNeedToBeRedrawn.size(); i17++) {
                Integer numValueAt = this.mBlocksAlwaysNeedToBeRedrawn.valueAt(i17);
                if (numValueAt.intValue() < i5) {
                    arraySet.add(numValueAt);
                }
                if (numValueAt.intValue() > i6) {
                    arraySet.add(Integer.valueOf(numValueAt.intValue() + i16));
                }
            }
            this.mBlocksAlwaysNeedToBeRedrawn = arraySet;
        }
        this.mNumberOfBlocks = i11;
        int i18 = i3 - ((i2 - i) + 1);
        if (i18 != 0) {
            i11 = i5 + i9;
            for (int i19 = i11; i19 < this.mNumberOfBlocks; i19++) {
                int[] iArr4 = this.mBlockEndLines;
                iArr4[i19] = iArr4[i19] + i18;
            }
        }
        this.mIndexFirstChangedBlock = Math.min(this.mIndexFirstChangedBlock, i11);
        if (i8 != 0) {
            this.mBlockEndLines[i5] = i - 1;
            updateAlwaysNeedsToBeRedrawn(i5);
            this.mBlockIndices[i5] = -1;
            i5++;
        }
        if (z) {
            this.mBlockEndLines[i5] = (i + i3) - 1;
            updateAlwaysNeedsToBeRedrawn(i5);
            this.mBlockIndices[i5] = -1;
            i5++;
        }
        if (z2) {
            this.mBlockEndLines[i5] = i7 + i18;
            updateAlwaysNeedsToBeRedrawn(i5);
            this.mBlockIndices[i5] = -1;
        }
    }

    public void setBlocksDataForTest(int[] iArr, int[] iArr2, int i, int i2) {
        int[] iArr3 = new int[iArr.length];
        this.mBlockEndLines = iArr3;
        this.mBlockIndices = new int[iArr2.length];
        System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
        System.arraycopy(iArr2, 0, this.mBlockIndices, 0, iArr2.length);
        this.mNumberOfBlocks = i;
        while (this.mInts.size() < i2) {
            PackedIntVector packedIntVector = this.mInts;
            packedIntVector.insertAt(packedIntVector.size(), new int[5]);
        }
    }

    public int[] getBlockEndLines() {
        return this.mBlockEndLines;
    }

    public int[] getBlockIndices() {
        return this.mBlockIndices;
    }

    public int getBlockIndex(int i) {
        return this.mBlockIndices[i];
    }

    public void setBlockIndex(int i, int i2) {
        this.mBlockIndices[i] = i2;
    }

    public int getNumberOfBlocks() {
        return this.mNumberOfBlocks;
    }

    public int getIndexFirstChangedBlock() {
        return this.mIndexFirstChangedBlock;
    }

    public void setIndexFirstChangedBlock(int i) {
        this.mIndexFirstChangedBlock = i;
    }

    @Override // android.text.Layout
    public int getLineCount() {
        return this.mInts.size() - 1;
    }

    @Override // android.text.Layout
    public int getLineTop(int i) {
        return this.mInts.getValue(i, 1);
    }

    @Override // android.text.Layout
    public int getLineDescent(int i) {
        return this.mInts.getValue(i, 2);
    }

    @Override // android.text.Layout
    public int getLineExtra(int i) {
        return this.mInts.getValue(i, 3);
    }

    @Override // android.text.Layout
    public int getLineStart(int i) {
        return this.mInts.getValue(i, 0) & 536870911;
    }

    @Override // android.text.Layout
    public boolean getLineContainsTab(int i) {
        return (this.mInts.getValue(i, 0) & 536870912) != 0;
    }

    @Override // android.text.Layout
    public int getParagraphDirection(int i) {
        return this.mInts.getValue(i, 0) >> 30;
    }

    @Override // android.text.Layout
    public final Layout.Directions getLineDirections(int i) {
        return this.mObjects.getValue(i, 0);
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
        return StaticLayout.unpackStartHyphenEdit(this.mInts.getValue(i, 4) & 255);
    }

    @Override // android.text.Layout
    public int getEndHyphenEdit(int i) {
        return StaticLayout.unpackEndHyphenEdit(this.mInts.getValue(i, 4) & 255);
    }

    private boolean getContentMayProtrudeFromTopOrBottom(int i) {
        return (this.mInts.getValue(i, 4) & 256) != 0;
    }

    @Override // android.text.Layout
    public int getEllipsizedWidth() {
        return this.mEllipsizedWidth;
    }

    private static class ChangeWatcher implements TextWatcher, SpanWatcher {
        private WeakReference<DynamicLayout> mLayout;
        private OffsetMapping.TextUpdate mTransformedTextUpdate;

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        public ChangeWatcher(DynamicLayout dynamicLayout) {
            this.mLayout = new WeakReference<>(dynamicLayout);
        }

        private void reflow(CharSequence charSequence, int i, int i2, int i3) {
            DynamicLayout dynamicLayout = this.mLayout.get();
            if (dynamicLayout != null) {
                dynamicLayout.reflow(charSequence, i, i2, i3);
            } else if (charSequence instanceof Spannable) {
                ((Spannable) charSequence).removeSpan(this);
            }
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            DynamicLayout dynamicLayout = this.mLayout.get();
            if (dynamicLayout == null || !(dynamicLayout.mDisplay instanceof OffsetMapping)) {
                return;
            }
            OffsetMapping offsetMapping = (OffsetMapping) dynamicLayout.mDisplay;
            OffsetMapping.TextUpdate textUpdate = this.mTransformedTextUpdate;
            if (textUpdate == null) {
                this.mTransformedTextUpdate = new OffsetMapping.TextUpdate(i, i2, i3);
            } else {
                textUpdate.where = i;
                this.mTransformedTextUpdate.before = i2;
                this.mTransformedTextUpdate.after = i3;
            }
            offsetMapping.originalToTransformed(this.mTransformedTextUpdate);
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            DynamicLayout dynamicLayout = this.mLayout.get();
            if (dynamicLayout != null && (dynamicLayout.mDisplay instanceof OffsetMapping)) {
                OffsetMapping.TextUpdate textUpdate = this.mTransformedTextUpdate;
                if (textUpdate != null && textUpdate.where >= 0) {
                    i = this.mTransformedTextUpdate.where;
                    i2 = this.mTransformedTextUpdate.before;
                    i3 = this.mTransformedTextUpdate.after;
                    this.mTransformedTextUpdate.where = -1;
                } else {
                    i2 = dynamicLayout.getLineEnd(dynamicLayout.getLineCount() - 1);
                    i3 = dynamicLayout.mDisplay.length();
                    i = 0;
                }
            }
            reflow(charSequence, i, i2, i3);
        }

        private void transformAndReflow(Spannable spannable, int i, int i2) {
            DynamicLayout dynamicLayout = this.mLayout.get();
            if (dynamicLayout != null && (dynamicLayout.mDisplay instanceof OffsetMapping)) {
                OffsetMapping offsetMapping = (OffsetMapping) dynamicLayout.mDisplay;
                i = offsetMapping.originalToTransformed(i, 0);
                i2 = offsetMapping.originalToTransformed(i2, 0);
            }
            int i3 = i2 - i;
            reflow(spannable, i, i3, i3);
        }

        @Override // android.text.SpanWatcher
        public void onSpanAdded(Spannable spannable, Object obj, int i, int i2) {
            if (obj instanceof UpdateLayout) {
                transformAndReflow(spannable, i, i2);
            }
        }

        @Override // android.text.SpanWatcher
        public void onSpanRemoved(Spannable spannable, Object obj, int i, int i2) {
            if (obj instanceof UpdateLayout) {
                if (Flags.insertModeCrashWhenDelete()) {
                    DynamicLayout dynamicLayout = this.mLayout.get();
                    if (dynamicLayout != null && (dynamicLayout.mDisplay instanceof OffsetMapping)) {
                        if (Flags.insertModeCrashUpdateLayoutSpan()) {
                            transformAndReflow(spannable, 0, spannable.length());
                            return;
                        } else {
                            reflow(spannable, 0, 0, spannable.length());
                            return;
                        }
                    }
                    int i3 = i2 - i;
                    reflow(spannable, i, i3, i3);
                    return;
                }
                transformAndReflow(spannable, i, i2);
            }
        }

        @Override // android.text.SpanWatcher
        public void onSpanChanged(Spannable spannable, Object obj, int i, int i2, int i3, int i4) {
            if (obj instanceof UpdateLayout) {
                if (i > i2) {
                    i = 0;
                }
                if (Flags.insertModeCrashWhenDelete()) {
                    DynamicLayout dynamicLayout = this.mLayout.get();
                    if (dynamicLayout != null && (dynamicLayout.mDisplay instanceof OffsetMapping)) {
                        if (Flags.insertModeCrashUpdateLayoutSpan()) {
                            transformAndReflow(spannable, 0, spannable.length());
                            return;
                        } else {
                            reflow(spannable, 0, 0, spannable.length());
                            return;
                        }
                    }
                    int i5 = i2 - i;
                    reflow(spannable, i, i5, i5);
                    int i6 = i4 - i3;
                    reflow(spannable, i3, i6, i6);
                    return;
                }
                transformAndReflow(spannable, i, i2);
                transformAndReflow(spannable, i3, i4);
            }
        }
    }

    @Override // android.text.Layout
    public int getEllipsisStart(int i) {
        if (this.mEllipsizeAt == null) {
            return 0;
        }
        return this.mInts.getValue(i, 5);
    }

    @Override // android.text.Layout
    public int getEllipsisCount(int i) {
        if (this.mEllipsizeAt == null) {
            return 0;
        }
        return this.mInts.getValue(i, 6);
    }

    @Override // android.text.Layout
    public LineBreakConfig getLineBreakConfig() {
        return this.mLineBreakConfig;
    }
}
