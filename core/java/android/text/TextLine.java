package android.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.text.PositionedGlyphs;
import android.graphics.text.TextRunShaper;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.style.CharacterStyle;
import android.text.style.MetricAffectingSpan;
import android.text.style.ReplacementSpan;

import com.android.internal.util.ArrayUtils;

import java.util.ArrayList;

public class TextLine {
    private static final boolean DEBUG = false;
    private static final char TAB_CHAR = '\t';
    private static final int TAB_INCREMENT = 20;
    private static final TextLine[] sCached = new TextLine[3];
    private float mAddedLetterSpacingInPx;
    private float mAddedWordSpacingInPx;
    private char[] mChars;
    private boolean mCharsValid;
    private PrecomputedText mComputed;
    private int mDir;
    private Layout.Directions mDirections;
    private int mEllipsisEnd;
    private int mEllipsisStart;
    private boolean mHasTabs;
    private boolean mIsJustifying;
    private int mLen;
    private TextPaint mPaint;
    private Paint.RunInfo mRunInfo;
    private Spanned mSpanned;
    private int mStart;
    private Layout.TabStops mTabs;
    private CharSequence mText;
    private RectF mTmpRectForMeasure;
    private RectF mTmpRectForPaintAPI;
    private Rect mTmpRectForPrecompute;
    private boolean mUseFallbackExtent = false;
    private final TextPaint mWorkPaint = new TextPaint();
    private final TextPaint mActivePaint = new TextPaint();
    private final SpanSet<MetricAffectingSpan> mMetricAffectingSpanSpanSet =
            new SpanSet<>(MetricAffectingSpan.class);
    private final SpanSet<CharacterStyle> mCharacterStyleSpanSet =
            new SpanSet<>(CharacterStyle.class);
    private final SpanSet<ReplacementSpan> mReplacementSpanSpanSet =
            new SpanSet<>(ReplacementSpan.class);
    private final DecorationInfo mDecorationInfo = new DecorationInfo();
    private final ArrayList<DecorationInfo> mDecorations = new ArrayList<>();

    public static final class LineInfo {
        private int mClusterCount;

        public int getClusterCount() {
            return this.mClusterCount;
        }

        public void setClusterCount(int clusterCount) {
            this.mClusterCount = clusterCount;
        }
    }

    public float getAddedWordSpacingInPx() {
        return this.mAddedWordSpacingInPx;
    }

    public float getAddedLetterSpacingInPx() {
        return this.mAddedLetterSpacingInPx;
    }

    public boolean isJustifying() {
        return this.mIsJustifying;
    }

    public static TextLine obtain() {
        synchronized (sCached) {
            int i = sCached.length;
            do {
                i--;
                if (i < 0) {
                    TextLine tl = new TextLine();
                    return tl;
                }
            } while (sCached[i] == null);
            TextLine tl2 = sCached[i];
            sCached[i] = null;
            return tl2;
        }
    }

    public static TextLine recycle(TextLine tl) {
        tl.mText = null;
        tl.mPaint = null;
        tl.mDirections = null;
        tl.mSpanned = null;
        tl.mTabs = null;
        tl.mChars = null;
        tl.mComputed = null;
        tl.mUseFallbackExtent = false;
        tl.mMetricAffectingSpanSpanSet.recycle();
        tl.mCharacterStyleSpanSet.recycle();
        tl.mReplacementSpanSpanSet.recycle();
        synchronized (sCached) {
            int i = 0;
            while (true) {
                if (i >= sCached.length) {
                    break;
                }
                if (sCached[i] != null) {
                    i++;
                } else {
                    sCached[i] = tl;
                    break;
                }
            }
        }
        return null;
    }

    public void set(
            TextPaint paint,
            CharSequence text,
            int start,
            int limit,
            int dir,
            Layout.Directions directions,
            boolean hasTabs,
            Layout.TabStops tabStops,
            int ellipsisStart,
            int ellipsisEnd,
            boolean useFallbackLineSpacing) {
        this.mPaint = paint;
        this.mText = text;
        this.mStart = start;
        this.mLen = limit - start;
        this.mDir = dir;
        this.mDirections = directions;
        this.mUseFallbackExtent = useFallbackLineSpacing;
        if (this.mDirections == null) {
            throw new IllegalArgumentException("Directions cannot be null");
        }
        this.mHasTabs = hasTabs;
        this.mSpanned = null;
        boolean hasReplacement = false;
        if (text instanceof Spanned) {
            this.mSpanned = (Spanned) text;
            this.mReplacementSpanSpanSet.init(this.mSpanned, start, limit);
            hasReplacement = this.mReplacementSpanSpanSet.numberOfSpans > 0;
        }
        this.mComputed = null;
        if (text instanceof PrecomputedText) {
            this.mComputed = (PrecomputedText) text;
            if (!this.mComputed.getParams().getTextPaint().equalsForTextMeasurement(paint)) {
                this.mComputed = null;
            }
        }
        this.mCharsValid = hasReplacement;
        if (this.mCharsValid) {
            if (this.mChars == null || this.mChars.length < this.mLen) {
                this.mChars = ArrayUtils.newUnpaddedCharArray(this.mLen);
            }
            TextUtils.getChars(text, start, limit, this.mChars, 0);
            if (hasReplacement) {
                char[] chars = this.mChars;
                int i = start;
                while (i < limit) {
                    int inext = this.mReplacementSpanSpanSet.getNextTransition(i, limit);
                    if (this.mReplacementSpanSpanSet.hasSpansIntersecting(i, inext)
                            && (i - start >= ellipsisEnd || inext - start <= ellipsisStart)) {
                        chars[i - start] = 65532;
                        int e = inext - start;
                        for (int j = (i - start) + 1; j < e; j++) {
                            chars[j] = 65279;
                        }
                    }
                    i = inext;
                }
            }
        }
        this.mTabs = tabStops;
        this.mAddedWordSpacingInPx = 0.0f;
        this.mIsJustifying = false;
        this.mEllipsisStart = ellipsisStart != ellipsisEnd ? ellipsisStart : 0;
        this.mEllipsisEnd = ellipsisStart != ellipsisEnd ? ellipsisEnd : 0;
    }

    private char charAt(int i) {
        return this.mCharsValid ? this.mChars[i] : this.mText.charAt(this.mStart + i);
    }

    public void justify(int justificationMode, float justifyWidth) {
        int end = this.mLen;
        while (end > 0 && isLineEndSpace(this.mText.charAt((this.mStart + end) - 1))) {
            end--;
        }
        if (justificationMode == 1) {
            float width = Math.abs(measure(end, false, null, null, null));
            int spaces = countStretchableSpaces(0, end);
            if (spaces != 0) {
                this.mAddedWordSpacingInPx = (justifyWidth - width) / spaces;
                this.mAddedLetterSpacingInPx = 0.0f;
            } else {
                return;
            }
        } else {
            LineInfo lineInfo = new LineInfo();
            float width2 = Math.abs(measure(end, false, null, null, lineInfo));
            int lettersCount = lineInfo.getClusterCount();
            if (lettersCount >= 2) {
                this.mAddedLetterSpacingInPx = (justifyWidth - width2) / (lettersCount - 1);
                if (this.mAddedLetterSpacingInPx > 0.03d) {
                    String oldFontFeatures = this.mPaint.getFontFeatureSettings();
                    this.mPaint.setFontFeatureSettings(
                            oldFontFeatures + ", \"liga\" off, \"cliga\" off");
                    float width3 = Math.abs(measure(end, false, null, null, lineInfo));
                    this.mAddedLetterSpacingInPx =
                            (justifyWidth - width3) / (lineInfo.getClusterCount() - 1);
                    this.mPaint.setFontFeatureSettings(oldFontFeatures);
                }
                this.mAddedWordSpacingInPx = 0.0f;
            } else {
                return;
            }
        }
        this.mIsJustifying = true;
    }

    public static int calculateRunFlag(int bidiRunIndex, int bidiRunCount, int lineDirection) {
        if (bidiRunCount == 1) {
            return 24576;
        }
        if (bidiRunIndex != 0 && bidiRunIndex != bidiRunCount - 1) {
            return 0;
        }
        int runFlag = 0;
        if (bidiRunIndex == 0) {
            if (lineDirection == 1) {
                runFlag = 0 | 8192;
            } else {
                runFlag = 0 | 16384;
            }
        }
        if (bidiRunIndex == bidiRunCount - 1) {
            if (lineDirection == 1) {
                return runFlag | 16384;
            }
            return runFlag | 8192;
        }
        return runFlag;
    }

    public static int resolveRunFlagForSubSequence(
            int runFlag, boolean isRtlRun, int runStart, int runEnd, int spanStart, int spanEnd) {
        if (runFlag == 0) {
            return 0;
        }
        int localRunFlag = runFlag;
        if ((runFlag & 8192) != 0) {
            if (isRtlRun) {
                if (spanEnd != runEnd) {
                    localRunFlag &= -8193;
                }
            } else if (spanStart != runStart) {
                localRunFlag &= -8193;
            }
        }
        if ((runFlag & 16384) != 0) {
            if (isRtlRun) {
                if (spanStart != runStart) {
                    return localRunFlag & (-16385);
                }
                return localRunFlag;
            }
            if (spanEnd != runEnd) {
                return localRunFlag & (-16385);
            }
            return localRunFlag;
        }
        return localRunFlag;
    }

    void draw(Canvas c, float x, int top, int y, int bottom) {
        int runCount;
        int runCount2;
        float h = 0.0f;
        int j = this.mDirections.getRunCount();
        int runIndex = 0;
        while (runIndex < j) {
            int runStart = this.mDirections.getRunStart(runIndex);
            if (runStart <= this.mLen) {
                int runLimit =
                        Math.min(this.mDirections.getRunLength(runIndex) + runStart, this.mLen);
                boolean runIsRtl = this.mDirections.isRunRtl(runIndex);
                int runFlag = calculateRunFlag(runIndex, j, this.mDir);
                float h2 = h;
                int segStart = runStart;
                int j2 = this.mHasTabs ? runStart : runLimit;
                while (j2 <= runLimit) {
                    if (j2 == runLimit || charAt(j2) == '\t') {
                        float f = x + h2;
                        boolean z = (runIndex == j + (-1) && j2 == this.mLen) ? false : true;
                        runCount = j;
                        runCount2 = j2;
                        h2 += drawRun(c, segStart, j2, runIsRtl, f, top, y, bottom, z, runFlag);
                        if (runCount2 != runLimit) {
                            h2 = this.mDir * nextTab(this.mDir * h2);
                        }
                        segStart = runCount2 + 1;
                    } else {
                        runCount = j;
                        runCount2 = j2;
                    }
                    j2 = runCount2 + 1;
                    j = runCount;
                }
                int runCount3 = j;
                runIndex++;
                h = h2;
                j = runCount3;
            } else {
                return;
            }
        }
    }

    public float metrics(
            Paint.FontMetricsInt fmi,
            RectF drawBounds,
            boolean returnDrawWidth,
            LineInfo lineInfo) {
        float boundsWidth;
        if (returnDrawWidth) {
            if (drawBounds == null) {
                if (this.mTmpRectForMeasure == null) {
                    this.mTmpRectForMeasure = new RectF();
                }
                drawBounds = this.mTmpRectForMeasure;
            }
            drawBounds.setEmpty();
            float w = measure(this.mLen, false, fmi, drawBounds, lineInfo);
            if (w >= 0.0f) {
                boundsWidth = Math.max(drawBounds.right, w) - Math.min(0.0f, drawBounds.left);
            } else {
                float boundsWidth2 = drawBounds.right;
                boundsWidth = Math.max(boundsWidth2, 0.0f) - Math.min(w, drawBounds.left);
            }
            if (Math.abs(w) > boundsWidth) {
                return w;
            }
            return Math.signum(w) * boundsWidth;
        }
        return measure(this.mLen, false, fmi, drawBounds, lineInfo);
    }

    void shape(TextShaper.GlyphsConsumer consumer) {
        float x;
        int j;
        float horizontal = 0.0f;
        float j2 = 0.0f;
        int runCount = this.mDirections.getRunCount();
        int runIndex = 0;
        while (runIndex < runCount) {
            int runStart = this.mDirections.getRunStart(runIndex);
            if (runStart <= this.mLen) {
                int runLimit =
                        Math.min(this.mDirections.getRunLength(runIndex) + runStart, this.mLen);
                boolean runIsRtl = this.mDirections.isRunRtl(runIndex);
                int runFlag = calculateRunFlag(runIndex, runCount, this.mDir);
                float horizontal2 = horizontal;
                int segStart = runStart;
                int j3 = this.mHasTabs ? runStart : runLimit;
                while (j3 <= runLimit) {
                    if (j3 == runLimit || charAt(j3) == '\t') {
                        float f = j2 + horizontal2;
                        x = j2;
                        j = j3;
                        horizontal2 +=
                                shapeRun(
                                        consumer,
                                        segStart,
                                        j3,
                                        runIsRtl,
                                        f,
                                        (runIndex == runCount + (-1) && j3 == this.mLen)
                                                ? false
                                                : true,
                                        runFlag);
                        if (j != runLimit) {
                            horizontal2 = this.mDir * nextTab(this.mDir * horizontal2);
                        }
                        segStart = j + 1;
                    } else {
                        x = j2;
                        j = j3;
                    }
                    j3 = j + 1;
                    j2 = x;
                }
                float x2 = j2;
                runIndex++;
                horizontal = horizontal2;
                j2 = x2;
            } else {
                return;
            }
        }
    }

    public float measure(
            int offset,
            boolean trailing,
            Paint.FontMetricsInt fmi,
            RectF drawBounds,
            LineInfo lineInfo) {
        boolean runIsRtl;
        int runLimit;
        int runStart;
        int runIndex;
        int runCount;
        int target;
        int j;
        if (offset > this.mLen) {
            throw new IndexOutOfBoundsException(
                    "offset("
                            + offset
                            + ") should be less than line limit("
                            + this.mLen
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
        boolean z = false;
        if (lineInfo != null) {
            lineInfo.setClusterCount(0);
        }
        int target2 = trailing ? offset - 1 : offset;
        if (target2 < 0) {
            return 0.0f;
        }
        float h = 0.0f;
        int runCount2 = this.mDirections.getRunCount();
        int runIndex2 = 0;
        while (runIndex2 < runCount2) {
            int runStart2 = this.mDirections.getRunStart(runIndex2);
            if (runStart2 > this.mLen) {
                break;
            }
            int runLimit2 =
                    Math.min(this.mDirections.getRunLength(runIndex2) + runStart2, this.mLen);
            boolean runIsRtl2 = this.mDirections.isRunRtl(runIndex2);
            int runFlag = calculateRunFlag(runIndex2, runCount2, this.mDir);
            float h2 = h;
            int segStart = runStart2;
            int j2 = this.mHasTabs ? runStart2 : runLimit2;
            while (j2 <= runLimit2) {
                if (j2 == runLimit2 || charAt(j2) == '\t') {
                    boolean targetIsInThisSegment =
                            (target2 < segStart || target2 >= j2) ? z : true;
                    boolean sameDirection = (this.mDir == -1 ? true : z) != runIsRtl2 ? z : true;
                    if (!targetIsInThisSegment || !sameDirection) {
                        int j3 = j2;
                        int segStart2 = segStart;
                        runIsRtl = runIsRtl2;
                        runLimit = runLimit2;
                        runStart = runStart2;
                        runIndex = runIndex2;
                        runCount = runCount2;
                        target = target2;
                        float segmentWidth =
                                measureRun(
                                        segStart2,
                                        j3,
                                        j3,
                                        runIsRtl,
                                        fmi,
                                        drawBounds,
                                        null,
                                        0,
                                        h2,
                                        lineInfo,
                                        runFlag);
                        h2 += sameDirection ? segmentWidth : -segmentWidth;
                        if (targetIsInThisSegment) {
                            return h2
                                    + measureRun(
                                            segStart2, offset, j3, runIsRtl, null, null, null, 0,
                                            h2, lineInfo, runFlag);
                        }
                        j = j3;
                        if (j != runLimit) {
                            if (offset != j) {
                                float h3 = this.mDir * nextTab(this.mDir * h2);
                                if (target != j) {
                                    h2 = h3;
                                } else {
                                    return h3;
                                }
                            } else {
                                return h2;
                            }
                        }
                        segStart = j + 1;
                    } else {
                        return h2
                                + measureRun(
                                        segStart,
                                        offset,
                                        j2,
                                        runIsRtl2,
                                        fmi,
                                        drawBounds,
                                        null,
                                        0,
                                        h2,
                                        lineInfo,
                                        runFlag);
                    }
                } else {
                    j = j2;
                    runIsRtl = runIsRtl2;
                    runLimit = runLimit2;
                    runStart = runStart2;
                    runIndex = runIndex2;
                    runCount = runCount2;
                    target = target2;
                }
                j2 = j + 1;
                runLimit2 = runLimit;
                target2 = target;
                runIndex2 = runIndex;
                runIsRtl2 = runIsRtl;
                runStart2 = runStart;
                runCount2 = runCount;
                z = false;
            }
            runIndex2++;
            h = h2;
            z = false;
        }
        return h;
    }

    public void measureAllBounds(float[] bounds, float[] advances) {
        float[] advances2;
        boolean runIsRtl;
        int runStart;
        int runIndex;
        int j;
        int runLimit;
        float leftX;
        float h;
        float rightX;
        if (bounds != null) {
            if (bounds.length < this.mLen * 2) {
                throw new IndexOutOfBoundsException(
                        "bounds doesn't have enough space to receive the result, needed: "
                                + (this.mLen * 2)
                                + " had: "
                                + bounds.length);
            }
            if (advances != null) {
                advances2 = advances;
            } else {
                advances2 = new float[this.mLen];
            }
            if (advances2.length < this.mLen) {
                throw new IndexOutOfBoundsException(
                        "advance doesn't have enough space to receive the result, needed: "
                                + this.mLen
                                + " had: "
                                + advances2.length);
            }
            float h2 = 0.0f;
            int runCount = this.mDirections.getRunCount();
            int runIndex2 = 0;
            while (runIndex2 < runCount) {
                int runStart2 = this.mDirections.getRunStart(runIndex2);
                if (runStart2 <= this.mLen) {
                    int runLimit2 =
                            Math.min(
                                    this.mDirections.getRunLength(runIndex2) + runStart2,
                                    this.mLen);
                    boolean runIsRtl2 = this.mDirections.isRunRtl(runIndex2);
                    int runFlag = calculateRunFlag(runIndex2, runCount, this.mDir);
                    float h3 = h2;
                    int segStart = runStart2;
                    int j2 = this.mHasTabs ? runStart2 : runLimit2;
                    while (j2 <= runLimit2) {
                        if (j2 == runLimit2 || charAt(j2) == '\t') {
                            boolean sameDirection = (this.mDir == -1) == runIsRtl2;
                            int j3 = j2;
                            runIsRtl = runIsRtl2;
                            int runLimit3 = runLimit2;
                            runStart = runStart2;
                            runIndex = runIndex2;
                            float segmentWidth =
                                    measureRun(
                                            segStart, j2, j2, runIsRtl2, null, null, advances2,
                                            segStart, 0.0f, null, runFlag);
                            float oldh = h3;
                            h3 += sameDirection ? segmentWidth : -segmentWidth;
                            float currh = sameDirection ? oldh : h3;
                            int offset = segStart;
                            while (true) {
                                j = j3;
                                if (offset >= j || offset >= this.mLen) {
                                    break;
                                }
                                if (runIsRtl) {
                                    bounds[(offset * 2) + 1] = currh;
                                    currh -= advances2[offset];
                                    bounds[offset * 2] = currh;
                                } else {
                                    bounds[offset * 2] = currh;
                                    currh += advances2[offset];
                                    bounds[(offset * 2) + 1] = currh;
                                }
                                offset++;
                                j3 = j;
                            }
                            runLimit = runLimit3;
                            if (j != runLimit) {
                                if (runIsRtl) {
                                    rightX = h3;
                                    h = this.mDir * nextTab(this.mDir * h3);
                                    leftX = h;
                                } else {
                                    leftX = h3;
                                    h = nextTab(this.mDir * h3) * this.mDir;
                                    rightX = h;
                                }
                                bounds[j * 2] = leftX;
                                bounds[(j * 2) + 1] = rightX;
                                advances2[j] = rightX - leftX;
                                h3 = h;
                            }
                            segStart = j + 1;
                        } else {
                            j = j2;
                            runIsRtl = runIsRtl2;
                            runLimit = runLimit2;
                            runStart = runStart2;
                            runIndex = runIndex2;
                        }
                        j2 = j + 1;
                        runLimit2 = runLimit;
                        runStart2 = runStart;
                        runIndex2 = runIndex;
                        runIsRtl2 = runIsRtl;
                    }
                    runIndex2++;
                    h2 = h3;
                } else {
                    return;
                }
            }
            return;
        }
        throw new IllegalArgumentException("bounds can't be null");
    }

    public float[] measureAllOffsets(boolean[] trailing, Paint.FontMetricsInt fmi) {
        int j;
        boolean runIsRtl;
        int runLimit;
        int runStart;
        int runIndex;
        int runCount;
        boolean z = true;
        float[] measurement = new float[this.mLen + 1];
        boolean z2 = false;
        if (trailing[0]) {
            measurement[0] = 0.0f;
        }
        float horizontal = 0.0f;
        int runCount2 = this.mDirections.getRunCount();
        int runIndex2 = 0;
        while (runIndex2 < runCount2) {
            int runStart2 = this.mDirections.getRunStart(runIndex2);
            if (runStart2 > this.mLen) {
                break;
            }
            int runLimit2 =
                    Math.min(this.mDirections.getRunLength(runIndex2) + runStart2, this.mLen);
            boolean runIsRtl2 = this.mDirections.isRunRtl(runIndex2);
            int runFlag = calculateRunFlag(runIndex2, runCount2, this.mDir);
            float horizontal2 = horizontal;
            int segStart = runStart2;
            int segStart2 = this.mHasTabs ? runStart2 : runLimit2;
            while (segStart2 <= runLimit2) {
                if (segStart2 == runLimit2 || charAt(segStart2) == '\t') {
                    float oldHorizontal = horizontal2;
                    boolean sameDirection = (this.mDir == -1 ? z : z2) == runIsRtl2 ? z : z2;
                    float previousSegEndHorizontal = measurement[segStart];
                    j = segStart2;
                    int segStart3 = segStart;
                    runIsRtl = runIsRtl2;
                    runLimit = runLimit2;
                    runStart = runStart2;
                    runIndex = runIndex2;
                    runCount = runCount2;
                    float width =
                            measureRun(
                                    segStart,
                                    segStart2,
                                    segStart2,
                                    runIsRtl2,
                                    fmi,
                                    null,
                                    measurement,
                                    segStart3,
                                    0.0f,
                                    null,
                                    runFlag);
                    horizontal2 += sameDirection ? width : -width;
                    float currHorizontal = sameDirection ? oldHorizontal : horizontal2;
                    int segLimit = Math.min(j, this.mLen);
                    int offset = segStart3;
                    while (offset <= segLimit) {
                        float advance = 0.0f;
                        if (offset < segLimit) {
                            advance = runIsRtl ? -measurement[offset] : measurement[offset];
                        }
                        int segStart4 = segStart3;
                        if (offset == segStart4 && trailing[offset]) {
                            measurement[offset] = previousSegEndHorizontal;
                        } else if (offset != segLimit || trailing[offset]) {
                            measurement[offset] = currHorizontal;
                        }
                        currHorizontal += advance;
                        offset++;
                        segStart3 = segStart4;
                    }
                    if (j != runLimit) {
                        if (!trailing[j]) {
                            measurement[j] = horizontal2;
                        }
                        float horizontal3 = this.mDir * nextTab(this.mDir * horizontal2);
                        if (trailing[j + 1]) {
                            measurement[j + 1] = horizontal3;
                        }
                        horizontal2 = horizontal3;
                    }
                    int segStart5 = j + 1;
                    segStart = segStart5;
                } else {
                    j = segStart2;
                    runIsRtl = runIsRtl2;
                    runLimit = runLimit2;
                    runStart = runStart2;
                    runIndex = runIndex2;
                    runCount = runCount2;
                }
                segStart2 = j + 1;
                runLimit2 = runLimit;
                runIsRtl2 = runIsRtl;
                runIndex2 = runIndex;
                runCount2 = runCount;
                runStart2 = runStart;
                z = true;
                z2 = false;
            }
            runIndex2++;
            horizontal = horizontal2;
            z = true;
            z2 = false;
        }
        if (!trailing[this.mLen]) {
            measurement[this.mLen] = horizontal;
        }
        return measurement;
    }

    private float drawRun(
            Canvas c,
            int start,
            int limit,
            boolean runIsRtl,
            float x,
            int top,
            int y,
            int bottom,
            boolean needWidth,
            int runFlag) {
        if ((this.mDir == 1) == runIsRtl) {
            float w =
                    -measureRun(
                            start, limit, limit, runIsRtl, null, null, null, 0, 0.0f, null,
                            runFlag);
            handleRun(
                    start, limit, limit, runIsRtl, c, null, x + w, top, y, bottom, null, null,
                    false, null, 0, null, runFlag);
            return w;
        }
        return handleRun(
                start, limit, limit, runIsRtl, c, null, x, top, y, bottom, null, null, needWidth,
                null, 0, null, runFlag);
    }

    private float measureRun(
            int start,
            int offset,
            int limit,
            boolean runIsRtl,
            Paint.FontMetricsInt fmi,
            RectF drawBounds,
            float[] advances,
            int advancesIndex,
            float x,
            LineInfo lineInfo,
            int runFlag) {
        if (drawBounds != null) {
            if ((this.mDir == 1) == runIsRtl) {
                float w =
                        -measureRun(
                                start, offset, limit, runIsRtl, null, null, null, 0, 0.0f, null,
                                runFlag);
                return handleRun(
                        start,
                        offset,
                        limit,
                        runIsRtl,
                        null,
                        null,
                        x + w,
                        0,
                        0,
                        0,
                        fmi,
                        drawBounds,
                        true,
                        advances,
                        advancesIndex,
                        lineInfo,
                        runFlag);
            }
        }
        return handleRun(
                start,
                offset,
                limit,
                runIsRtl,
                null,
                null,
                x,
                0,
                0,
                0,
                fmi,
                drawBounds,
                true,
                advances,
                advancesIndex,
                lineInfo,
                runFlag);
    }

    private float shapeRun(
            TextShaper.GlyphsConsumer consumer,
            int start,
            int limit,
            boolean runIsRtl,
            float x,
            boolean needWidth,
            int runFlag) {
        if ((this.mDir == 1) == runIsRtl) {
            float w =
                    -measureRun(
                            start, limit, limit, runIsRtl, null, null, null, 0, 0.0f, null,
                            runFlag);
            handleRun(
                    start, limit, limit, runIsRtl, null, consumer, x + w, 0, 0, 0, null, null,
                    false, null, 0, null, runFlag);
            return w;
        }
        return handleRun(
                start, limit, limit, runIsRtl, null, consumer, x, 0, 0, 0, null, null, needWidth,
                null, 0, null, runFlag);
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x0186, code lost:

       r8 = r6;
       r1 = -1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x018c, code lost:

       if (r8 != (-1)) goto L115;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x018e, code lost:

       if (r0 == false) goto L114;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0190, code lost:

       r1 = r27.mLen + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0194, code lost:

       r6 = r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:?, code lost:

       return r6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0196, code lost:

       if (r8 > r11) goto L120;
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0198, code lost:

       if (r0 == false) goto L118;
    */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x019a, code lost:

       r1 = r11;
    */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x019d, code lost:

       r6 = r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:?, code lost:

       return r6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x019c, code lost:

       r1 = 0;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    int getOffsetToLeftRightOf(int r28, boolean r29) {
        /*
            Method dump skipped, instructions count: 417
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.text.TextLine.getOffsetToLeftRightOf(int,"
                    + " boolean):int");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getOffsetBeforeAfter(int r20, int r21, int r22, boolean r23, int r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 237
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.text.TextLine.getOffsetBeforeAfter(int, int, int,"
                    + " boolean, int, boolean):int");
    }

    private static void expandMetricsFromPaint(Paint.FontMetricsInt fmi, TextPaint wp) {
        int previousTop = fmi.top;
        int previousAscent = fmi.ascent;
        int previousDescent = fmi.descent;
        int previousBottom = fmi.bottom;
        int previousLeading = fmi.leading;
        wp.getFontMetricsInt(fmi);
        updateMetrics(
                fmi, previousTop, previousAscent, previousDescent, previousBottom, previousLeading);
    }

    private void expandMetricsFromPaint(
            TextPaint wp,
            int start,
            int end,
            int contextStart,
            int contextEnd,
            boolean runIsRtl,
            Paint.FontMetricsInt fmi) {
        int previousTop = fmi.top;
        int previousAscent = fmi.ascent;
        int previousDescent = fmi.descent;
        int previousBottom = fmi.bottom;
        int previousLeading = fmi.leading;
        int count = end - start;
        int contextCount = contextEnd - contextStart;
        if (this.mCharsValid) {
            wp.getFontMetricsInt(
                    this.mChars, start, count, contextStart, contextCount, runIsRtl, fmi);
        } else if (this.mComputed == null) {
            wp.getFontMetricsInt(
                    this.mText,
                    this.mStart + start,
                    count,
                    this.mStart + contextStart,
                    contextCount,
                    runIsRtl,
                    fmi);
        } else {
            this.mComputed.getFontMetricsInt(this.mStart + start, this.mStart + end, fmi);
        }
        updateMetrics(
                fmi, previousTop, previousAscent, previousDescent, previousBottom, previousLeading);
    }

    static void updateMetrics(
            Paint.FontMetricsInt fmi,
            int previousTop,
            int previousAscent,
            int previousDescent,
            int previousBottom,
            int previousLeading) {
        fmi.top = Math.min(fmi.top, previousTop);
        fmi.ascent = Math.min(fmi.ascent, previousAscent);
        fmi.descent = Math.max(fmi.descent, previousDescent);
        fmi.bottom = Math.max(fmi.bottom, previousBottom);
        fmi.leading = Math.max(fmi.leading, previousLeading);
    }

    private static void drawStroke(
            TextPaint wp,
            Canvas c,
            int color,
            float position,
            float thickness,
            float xleft,
            float xright,
            float baseline) {
        float strokeTop = baseline + wp.baselineShift + position;
        int previousColor = wp.getColor();
        Paint.Style previousStyle = wp.getStyle();
        boolean previousAntiAlias = wp.isAntiAlias();
        wp.setStyle(Paint.Style.FILL);
        wp.setAntiAlias(true);
        wp.setColor(color);
        c.drawRect(xleft, strokeTop, xright, strokeTop + thickness, wp);
        wp.setStyle(previousStyle);
        wp.setColor(previousColor);
        wp.setAntiAlias(previousAntiAlias);
    }

    private float getRunAdvance(
            TextPaint wp,
            int start,
            int end,
            int contextStart,
            int contextEnd,
            boolean runIsRtl,
            int offset,
            float[] advances,
            int advancesIndex,
            RectF drawingBounds,
            LineInfo lineInfo) {
        if (lineInfo == null) {
            this.mRunInfo = null;
        } else {
            if (this.mRunInfo == null) {
                this.mRunInfo = new Paint.RunInfo();
            }
            this.mRunInfo.setClusterCount(0);
        }
        if (this.mCharsValid) {
            float r =
                    wp.getRunCharacterAdvance(
                            this.mChars,
                            start,
                            end,
                            contextStart,
                            contextEnd,
                            runIsRtl,
                            offset,
                            advances,
                            advancesIndex,
                            drawingBounds,
                            this.mRunInfo);
            if (lineInfo != null) {
                lineInfo.setClusterCount(
                        lineInfo.getClusterCount() + this.mRunInfo.getClusterCount());
            }
            return r;
        }
        int delta = this.mStart;
        if (this.mComputed == null || advances != null || lineInfo != null) {
            float r2 =
                    wp.getRunCharacterAdvance(
                            this.mText,
                            delta + start,
                            delta + end,
                            delta + contextStart,
                            delta + contextEnd,
                            runIsRtl,
                            delta + offset,
                            advances,
                            advancesIndex,
                            drawingBounds,
                            this.mRunInfo);
            if (lineInfo != null) {
                lineInfo.setClusterCount(
                        lineInfo.getClusterCount() + this.mRunInfo.getClusterCount());
            }
            return r2;
        }
        if (drawingBounds != null) {
            if (this.mTmpRectForPrecompute == null) {
                this.mTmpRectForPrecompute = new Rect();
            }
            this.mComputed.getBounds(start + delta, end + delta, this.mTmpRectForPrecompute);
            drawingBounds.set(this.mTmpRectForPrecompute);
        }
        return this.mComputed.getWidth(start + delta, end + delta);
    }

    private float handleText(
            TextPaint wp,
            int start,
            int end,
            int contextStart,
            int contextEnd,
            boolean runIsRtl,
            Canvas c,
            TextShaper.GlyphsConsumer consumer,
            float x,
            int top,
            int y,
            int bottom,
            Paint.FontMetricsInt fmi,
            RectF drawBounds,
            boolean needWidth,
            int offset,
            ArrayList<DecorationInfo> decorations,
            float[] advances,
            int advancesIndex,
            LineInfo lineInfo,
            int runFlag) {
        Paint.FontMetricsInt fmi2;
        int numDecorations;
        Paint.FontMetricsInt fmi3;
        TextPaint textPaint;
        float totalWidth;
        float leftX;
        float rightX;
        float totalWidth2;
        float decorationXLeft;
        float decorationXRight;
        int i;
        float f;
        if (this.mIsJustifying) {
            wp.setWordSpacing(this.mAddedWordSpacingInPx);
            wp.setLetterSpacing(this.mAddedLetterSpacingInPx / wp.getTextSize());
        }
        if (drawBounds != null && fmi == null) {
            fmi2 = new Paint.FontMetricsInt();
        } else {
            fmi2 = fmi;
        }
        if (fmi2 != null) {
            expandMetricsFromPaint(fmi2, wp);
        }
        if (end == start) {
            return 0.0f;
        }
        float totalWidth3 = 0.0f;
        if ((runFlag & 8192) == 8192) {
            wp.setFlags(wp.getFlags() | 8192);
        } else {
            wp.setFlags(wp.getFlags() & (-8193));
        }
        if ((runFlag & 16384) == 16384) {
            wp.setFlags(wp.getFlags() | 16384);
        } else {
            wp.setFlags(wp.getFlags() & (-16385));
        }
        int numDecorations2 = decorations == null ? 0 : decorations.size();
        if (needWidth
                || ((c != null || consumer != null)
                        && (wp.bgColor != 0 || numDecorations2 != 0 || runIsRtl))) {
            if (drawBounds != null && this.mTmpRectForPaintAPI == null) {
                this.mTmpRectForPaintAPI = new RectF();
            }
            numDecorations = numDecorations2;
            fmi3 = fmi2;
            totalWidth3 =
                    getRunAdvance(
                            wp,
                            start,
                            end,
                            contextStart,
                            contextEnd,
                            runIsRtl,
                            offset,
                            advances,
                            advancesIndex,
                            drawBounds == null ? null : this.mTmpRectForPaintAPI,
                            lineInfo);
            if (drawBounds != null) {
                if (runIsRtl) {
                    this.mTmpRectForPaintAPI.offset(x - totalWidth3, 0.0f);
                } else {
                    this.mTmpRectForPaintAPI.offset(x, 0.0f);
                }
                drawBounds.union(this.mTmpRectForPaintAPI);
            }
        } else {
            numDecorations = numDecorations2;
            fmi3 = fmi2;
        }
        int lastIndex = (this.mStart + end) - 1;
        if (lastIndex < 0 || !TextUtils.semNeedMoreWidth(this.mText.charAt(lastIndex))) {
            textPaint = wp;
            totalWidth = totalWidth3;
        } else {
            textPaint = wp;
            totalWidth = totalWidth3 + textPaint.measureText(" ");
        }
        if (runIsRtl) {
            float leftX2 = x - totalWidth;
            leftX = leftX2;
            rightX = x;
        } else {
            leftX = x;
            rightX = x + totalWidth;
        }
        if (consumer != null) {
            shapeTextRun(consumer, wp, start, end, contextStart, contextEnd, runIsRtl, leftX);
        }
        if (this.mUseFallbackExtent && fmi3 != null) {
            expandMetricsFromPaint(wp, start, end, contextStart, contextEnd, runIsRtl, fmi3);
        }
        if (c != null) {
            if (textPaint.bgColor != 0) {
                int previousColor = wp.getColor();
                Paint.Style previousStyle = wp.getStyle();
                textPaint.setColor(textPaint.bgColor);
                textPaint.setStyle(Paint.Style.FILL);
                c.drawRect(leftX, top, rightX, bottom, wp);
                textPaint.setStyle(previousStyle);
                textPaint.setColor(previousColor);
            }
            drawTextRun(
                    c,
                    wp,
                    start,
                    end,
                    contextStart,
                    contextEnd,
                    runIsRtl,
                    leftX,
                    y + textPaint.baselineShift);
            if (numDecorations != 0) {
                int i2 = 0;
                while (true) {
                    int numDecorations3 = numDecorations;
                    if (i2 >= numDecorations3) {
                        break;
                    }
                    DecorationInfo info = decorations.get(i2);
                    int decorationStart = Math.max(info.start, start);
                    int decorationEnd = Math.min(info.end, offset);
                    int i3 = i2;
                    float totalWidth4 = totalWidth;
                    int lastIndex2 = lastIndex;
                    float decorationStartAdvance =
                            getRunAdvance(
                                    wp,
                                    start,
                                    end,
                                    contextStart,
                                    contextEnd,
                                    runIsRtl,
                                    decorationStart,
                                    null,
                                    0,
                                    null,
                                    null);
                    float decorationEndAdvance =
                            getRunAdvance(
                                    wp,
                                    start,
                                    end,
                                    contextStart,
                                    contextEnd,
                                    runIsRtl,
                                    decorationEnd,
                                    null,
                                    0,
                                    null,
                                    null);
                    if (runIsRtl) {
                        float decorationXLeft2 = rightX - decorationEndAdvance;
                        decorationXLeft = decorationXLeft2;
                        decorationXRight = rightX - decorationStartAdvance;
                    } else {
                        float decorationXLeft3 = leftX + decorationStartAdvance;
                        decorationXLeft = decorationXLeft3;
                        decorationXRight = leftX + decorationEndAdvance;
                    }
                    if (info.underlineColor != 0) {
                        i = y;
                        drawStroke(
                                wp,
                                c,
                                info.underlineColor,
                                wp.getUnderlinePosition(),
                                info.underlineThickness,
                                decorationXLeft,
                                decorationXRight,
                                y);
                    } else {
                        i = y;
                    }
                    if (!info.isUnderlineText) {
                        f = 1.0f;
                    } else {
                        float thickness = Math.max(wp.getUnderlineThickness(), 1.0f);
                        f = 1.0f;
                        drawStroke(
                                wp,
                                c,
                                wp.getColor(),
                                wp.getUnderlinePosition(),
                                thickness,
                                decorationXLeft,
                                decorationXRight,
                                i);
                    }
                    if (info.isStrikeThruText) {
                        float thickness2 = Math.max(wp.getStrikeThruThickness(), f);
                        drawStroke(
                                wp,
                                c,
                                wp.getColor(),
                                wp.getStrikeThruPosition(),
                                thickness2,
                                decorationXLeft,
                                decorationXRight,
                                i);
                    }
                    i2 = i3 + 1;
                    numDecorations = numDecorations3;
                    lastIndex = lastIndex2;
                    totalWidth = totalWidth4;
                }
                totalWidth2 = totalWidth;
            } else {
                totalWidth2 = totalWidth;
            }
        } else {
            totalWidth2 = totalWidth;
        }
        return runIsRtl ? -totalWidth2 : totalWidth2;
    }

    private float handleReplacement(
            ReplacementSpan replacement,
            TextPaint wp,
            int start,
            int limit,
            boolean runIsRtl,
            Canvas c,
            float x,
            int top,
            int y,
            int bottom,
            Paint.FontMetricsInt fmi,
            boolean needWidth) {
        int previousTop;
        int previousAscent;
        int previousDescent;
        int previousBottom;
        int previousLeading;
        float x2;
        float ret = 0.0f;
        int textStart = this.mStart + start;
        int textLimit = this.mStart + limit;
        if (needWidth || (c != null && runIsRtl)) {
            boolean needUpdateMetrics = fmi != null;
            if (!needUpdateMetrics) {
                previousTop = 0;
                previousAscent = 0;
                previousDescent = 0;
                previousBottom = 0;
                previousLeading = 0;
            } else {
                int previousTop2 = fmi.top;
                int previousAscent2 = fmi.ascent;
                int previousDescent2 = fmi.descent;
                int previousBottom2 = fmi.bottom;
                int previousLeading2 = fmi.leading;
                previousTop = previousTop2;
                previousAscent = previousAscent2;
                previousDescent = previousDescent2;
                previousBottom = previousBottom2;
                previousLeading = previousLeading2;
            }
            ret = replacement.getSize(wp, this.mText, textStart, textLimit, fmi);
            if (needUpdateMetrics) {
                updateMetrics(
                        fmi,
                        previousTop,
                        previousAscent,
                        previousDescent,
                        previousBottom,
                        previousLeading);
            }
        }
        float ret2 = ret;
        if (c != null) {
            if (!runIsRtl) {
                x2 = x;
            } else {
                x2 = x - ret2;
            }
            replacement.draw(c, this.mText, textStart, textLimit, x2, top, y, bottom, wp);
        }
        return runIsRtl ? -ret2 : ret2;
    }

    private int adjustStartHyphenEdit(int start, int startHyphenEdit) {
        if (start > 0) {
            return 0;
        }
        return startHyphenEdit;
    }

    private int adjustEndHyphenEdit(int limit, int endHyphenEdit) {
        if (limit < this.mLen) {
            return 0;
        }
        return endHyphenEdit;
    }

    private static final class DecorationInfo {
        public int end;
        public boolean isStrikeThruText;
        public boolean isUnderlineText;
        public int start;
        public int underlineColor;
        public float underlineThickness;

        private DecorationInfo() {
            this.start = -1;
            this.end = -1;
        }

        public boolean hasDecoration() {
            return this.isStrikeThruText || this.isUnderlineText || this.underlineColor != 0;
        }

        public DecorationInfo copyInfo() {
            DecorationInfo copy = new DecorationInfo();
            copy.isStrikeThruText = this.isStrikeThruText;
            copy.isUnderlineText = this.isUnderlineText;
            copy.underlineColor = this.underlineColor;
            copy.underlineThickness = this.underlineThickness;
            return copy;
        }
    }

    private void extractDecorationInfo(TextPaint paint, DecorationInfo info) {
        info.isStrikeThruText = paint.isStrikeThruText();
        if (info.isStrikeThruText) {
            paint.setStrikeThruText(false);
        }
        info.isUnderlineText = paint.isUnderlineText();
        if (info.isUnderlineText) {
            paint.setUnderlineText(false);
        }
        info.underlineColor = paint.underlineColor;
        info.underlineThickness = paint.underlineThickness;
        paint.setUnderlineText(0, 0.0f);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float handleRun(
            int r41,
            int r42,
            int r43,
            boolean r44,
            android.graphics.Canvas r45,
            android.text.TextShaper.GlyphsConsumer r46,
            float r47,
            int r48,
            int r49,
            int r50,
            android.graphics.Paint.FontMetricsInt r51,
            android.graphics.RectF r52,
            boolean r53,
            float[] r54,
            int r55,
            android.text.TextLine.LineInfo r56,
            int r57) {
        /*
            Method dump skipped, instructions count: 990
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.text.TextLine.handleRun(int, int, int, boolean,"
                    + " android.graphics.Canvas, android.text.TextShaper$GlyphsConsumer, float,"
                    + " int, int, int, android.graphics.Paint$FontMetricsInt,"
                    + " android.graphics.RectF, boolean, float[], int,"
                    + " android.text.TextLine$LineInfo, int):float");
    }

    private void drawTextRun(
            Canvas c,
            TextPaint wp,
            int start,
            int end,
            int contextStart,
            int contextEnd,
            boolean runIsRtl,
            float x,
            int y) {
        if (this.mCharsValid) {
            int count = end - start;
            int contextCount = contextEnd - contextStart;
            c.drawTextRun(
                    this.mChars, start, count, contextStart, contextCount, x, y, runIsRtl, wp);
        } else {
            int delta = this.mStart;
            c.drawTextRun(
                    this.mText,
                    delta + start,
                    delta + end,
                    delta + contextStart,
                    delta + contextEnd,
                    x,
                    y,
                    runIsRtl,
                    wp);
        }
    }

    private void shapeTextRun(
            TextShaper.GlyphsConsumer consumer,
            TextPaint paint,
            int start,
            int end,
            int contextStart,
            int contextEnd,
            boolean runIsRtl,
            float x) {
        PositionedGlyphs glyphs;
        int count = end - start;
        int contextCount = contextEnd - contextStart;
        if (!this.mCharsValid) {
            glyphs =
                    TextRunShaper.shapeTextRun(
                            this.mText,
                            this.mStart + start,
                            count,
                            this.mStart + contextStart,
                            contextCount,
                            x,
                            0.0f,
                            runIsRtl,
                            paint);
        } else {
            glyphs =
                    TextRunShaper.shapeTextRun(
                            this.mChars,
                            start,
                            count,
                            contextStart,
                            contextCount,
                            x,
                            0.0f,
                            runIsRtl,
                            paint);
        }
        consumer.accept(start, count, glyphs, paint);
    }

    float nextTab(float h) {
        if (this.mTabs != null) {
            return this.mTabs.nextTab(h);
        }
        return Layout.TabStops.nextDefaultStop(h, 20.0f);
    }

    private boolean isStretchableWhitespace(int ch) {
        return ch == 32;
    }

    private int countStretchableSpaces(int start, int end) {
        int count = 0;
        for (int i = start; i < end; i++) {
            char c = this.mCharsValid ? this.mChars[i] : this.mText.charAt(this.mStart + i);
            if (isStretchableWhitespace(c)) {
                count++;
            }
        }
        return count;
    }

    public static boolean isLineEndSpace(char ch) {
        return ch == ' '
                || ch == '\t'
                || ch == 5760
                || (8192 <= ch && ch <= 8202 && ch != 8199)
                || ch == 8287
                || ch == 12288;
    }

    private static boolean equalAttributes(TextPaint lp, TextPaint rp) {
        return lp.getColorFilter() == rp.getColorFilter()
                && lp.getMaskFilter() == rp.getMaskFilter()
                && lp.getShader() == rp.getShader()
                && lp.getTypeface() == rp.getTypeface()
                && lp.getXfermode() == rp.getXfermode()
                && lp.getTextLocales().equals(rp.getTextLocales())
                && TextUtils.equals(lp.getFontFeatureSettings(), rp.getFontFeatureSettings())
                && TextUtils.equals(lp.getFontVariationSettings(), rp.getFontVariationSettings())
                && lp.getShadowLayerRadius() == rp.getShadowLayerRadius()
                && lp.getShadowLayerDx() == rp.getShadowLayerDx()
                && lp.getShadowLayerDy() == rp.getShadowLayerDy()
                && lp.getShadowLayerColor() == rp.getShadowLayerColor()
                && lp.getFlags() == rp.getFlags()
                && lp.getHinting() == rp.getHinting()
                && lp.getStyle() == rp.getStyle()
                && lp.getColor() == rp.getColor()
                && lp.getStrokeWidth() == rp.getStrokeWidth()
                && lp.getStrokeMiter() == rp.getStrokeMiter()
                && lp.getStrokeCap() == rp.getStrokeCap()
                && lp.getStrokeJoin() == rp.getStrokeJoin()
                && lp.getTextAlign() == rp.getTextAlign()
                && lp.isElegantTextHeight() == rp.isElegantTextHeight()
                && lp.getTextSize() == rp.getTextSize()
                && lp.getTextScaleX() == rp.getTextScaleX()
                && lp.getTextSkewX() == rp.getTextSkewX()
                && lp.getLetterSpacing() == rp.getLetterSpacing()
                && lp.getWordSpacing() == rp.getWordSpacing()
                && lp.getStartHyphenEdit() == rp.getStartHyphenEdit()
                && lp.getEndHyphenEdit() == rp.getEndHyphenEdit()
                && lp.bgColor == rp.bgColor
                && lp.baselineShift == rp.baselineShift
                && lp.linkColor == rp.linkColor
                && lp.drawableState == rp.drawableState
                && lp.density == rp.density
                && lp.underlineColor == rp.underlineColor
                && lp.underlineThickness == rp.underlineThickness;
    }
}
