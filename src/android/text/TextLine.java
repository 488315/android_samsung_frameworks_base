package android.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.text.PositionedGlyphs;
import android.graphics.text.TextRunShaper;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.Layout;
import android.text.TextShaper;
import android.text.style.CharacterStyle;
import android.text.style.MetricAffectingSpan;
import android.text.style.ReplacementSpan;
import com.android.internal.util.ArrayUtils;
import java.util.ArrayList;

/* loaded from: classes4.dex */
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
    private final SpanSet<MetricAffectingSpan> mMetricAffectingSpanSpanSet = new SpanSet<>(MetricAffectingSpan.class);
    private final SpanSet<CharacterStyle> mCharacterStyleSpanSet = new SpanSet<>(CharacterStyle.class);
    private final SpanSet<ReplacementSpan> mReplacementSpanSpanSet = new SpanSet<>(ReplacementSpan.class);
    private final DecorationInfo mDecorationInfo = new DecorationInfo();
    private final ArrayList<DecorationInfo> mDecorations = new ArrayList<>();

    private int adjustStartHyphenEdit(int i, int i2) {
        if (i > 0) {
            return 0;
        }
        return i2;
    }

    public static int calculateRunFlag(int i, int i2, int i3) {
        if (i2 == 1) {
            return 24576;
        }
        if (i != 0 && i != i2 - 1) {
            return 0;
        }
        int i4 = i == 0 ? i3 == 1 ? 8192 : 16384 : 0;
        return i == i2 - 1 ? i3 == 1 ? i4 | 16384 : i4 | 8192 : i4;
    }

    public static boolean isLineEndSpace(char c) {
        if (c == ' ' || c == '\t' || c == 5760) {
            return true;
        }
        return (8192 <= c && c <= 8202 && c != 8199) || c == 8287 || c == 12288;
    }

    private boolean isStretchableWhitespace(int i) {
        return i == 32;
    }

    public static int resolveRunFlagForSubSequence(int i, boolean z, int i2, int i3, int i4, int i5) {
        if (i == 0) {
            return 0;
        }
        int i6 = ((i & 8192) == 0 || (!z ? i4 != i2 : i5 != i3)) ? i : i & (-8193);
        if ((i & 16384) != 0) {
            if (z) {
                if (i4 != i2) {
                    return i6 & (-16385);
                }
            } else if (i5 != i3) {
                return i6 & (-16385);
            }
        }
        return i6;
    }

    public static final class LineInfo {
        private int mClusterCount;

        public int getClusterCount() {
            return this.mClusterCount;
        }

        public void setClusterCount(int i) {
            this.mClusterCount = i;
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
        TextLine[] textLineArr;
        TextLine textLine;
        TextLine[] textLineArr2 = sCached;
        synchronized (textLineArr2) {
            int length = textLineArr2.length;
            do {
                length--;
                if (length >= 0) {
                    textLineArr = sCached;
                    textLine = textLineArr[length];
                } else {
                    return new TextLine();
                }
            } while (textLine == null);
            textLineArr[length] = null;
            return textLine;
        }
    }

    public static TextLine recycle(TextLine textLine) {
        textLine.mText = null;
        textLine.mPaint = null;
        textLine.mDirections = null;
        textLine.mSpanned = null;
        textLine.mTabs = null;
        textLine.mChars = null;
        textLine.mComputed = null;
        int i = 0;
        textLine.mUseFallbackExtent = false;
        textLine.mMetricAffectingSpanSpanSet.recycle();
        textLine.mCharacterStyleSpanSet.recycle();
        textLine.mReplacementSpanSpanSet.recycle();
        synchronized (sCached) {
            while (true) {
                TextLine[] textLineArr = sCached;
                if (i >= textLineArr.length) {
                    break;
                }
                if (textLineArr[i] == null) {
                    textLineArr[i] = textLine;
                    break;
                }
                i++;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void set(TextPaint textPaint, CharSequence charSequence, int i, int i2, int i3, Layout.Directions directions, boolean z, Layout.TabStops tabStops, int i4, int i5, boolean z2) {
        boolean z3;
        int i6;
        this.mPaint = textPaint;
        this.mText = charSequence;
        this.mStart = i;
        this.mLen = i2 - i;
        this.mDir = i3;
        this.mDirections = directions;
        this.mUseFallbackExtent = z2;
        if (directions == null) {
            throw new IllegalArgumentException("Directions cannot be null");
        }
        this.mHasTabs = z;
        this.mSpanned = null;
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            this.mSpanned = spanned;
            this.mReplacementSpanSpanSet.init(spanned, i, i2);
            z3 = this.mReplacementSpanSpanSet.numberOfSpans > 0;
        }
        this.mComputed = null;
        if (charSequence instanceof PrecomputedText) {
            PrecomputedText precomputedText = (PrecomputedText) charSequence;
            this.mComputed = precomputedText;
            if (!precomputedText.getParams().getTextPaint().equalsForTextMeasurement(textPaint)) {
                this.mComputed = null;
            }
        }
        this.mCharsValid = z3;
        if (z3) {
            char[] cArr = this.mChars;
            if (cArr == null || cArr.length < this.mLen) {
                this.mChars = ArrayUtils.newUnpaddedCharArray(this.mLen);
            }
            TextUtils.getChars(charSequence, i, i2, this.mChars, 0);
            if (z3) {
                char[] cArr2 = this.mChars;
                int i7 = i;
                while (i7 < i2) {
                    int nextTransition = this.mReplacementSpanSpanSet.getNextTransition(i7, i2);
                    if (this.mReplacementSpanSpanSet.hasSpansIntersecting(i7, nextTransition) && ((i6 = i7 - i) >= i5 || nextTransition - i <= i4)) {
                        cArr2[i6] = 65532;
                        int i8 = nextTransition - i;
                        for (int i9 = i6 + 1; i9 < i8; i9++) {
                            cArr2[i9] = 65279;
                        }
                    }
                    i7 = nextTransition;
                }
            }
        }
        this.mTabs = tabStops;
        this.mAddedWordSpacingInPx = 0.0f;
        this.mIsJustifying = false;
        this.mEllipsisStart = i4 != i5 ? i4 : 0;
        if (i4 == i5) {
            i5 = 0;
        }
        this.mEllipsisEnd = i5;
    }

    private char charAt(int i) {
        return this.mCharsValid ? this.mChars[i] : this.mText.charAt(i + this.mStart);
    }

    public void justify(int i, float f) {
        TextLine textLine;
        int i2 = this.mLen;
        while (i2 > 0 && isLineEndSpace(this.mText.charAt((this.mStart + i2) - 1))) {
            i2--;
        }
        if (i == 1) {
            textLine = this;
            float fAbs = Math.abs(textLine.measure(i2, false, null, null, null));
            int iCountStretchableSpaces = textLine.countStretchableSpaces(0, i2);
            if (iCountStretchableSpaces == 0) {
                return;
            }
            textLine.mAddedWordSpacingInPx = (f - fAbs) / iCountStretchableSpaces;
            textLine.mAddedLetterSpacingInPx = 0.0f;
        } else {
            textLine = this;
            LineInfo lineInfo = new LineInfo();
            float fAbs2 = Math.abs(textLine.measure(i2, false, null, null, lineInfo));
            if (lineInfo.getClusterCount() < 2) {
                return;
            }
            float f2 = (f - fAbs2) / (r9 - 1);
            textLine.mAddedLetterSpacingInPx = f2;
            if (f2 > 0.03d) {
                String fontFeatureSettings = textLine.mPaint.getFontFeatureSettings();
                textLine.mPaint.setFontFeatureSettings(fontFeatureSettings + ", \"liga\" off, \"cliga\" off");
                textLine.mAddedLetterSpacingInPx = (f - Math.abs(textLine.measure(i2, false, null, null, lineInfo))) / ((float) (lineInfo.getClusterCount() - 1));
                textLine.mPaint.setFontFeatureSettings(fontFeatureSettings);
            }
            textLine.mAddedWordSpacingInPx = 0.0f;
        }
        textLine.mIsJustifying = true;
    }

    void draw(Canvas canvas, float f, int i, int i2, int i3) {
        boolean z;
        int i4;
        int i5;
        int i6;
        Canvas canvas2;
        int runCount = this.mDirections.getRunCount();
        float f2 = 0.0f;
        int i7 = 0;
        while (i7 < runCount) {
            int runStart = this.mDirections.getRunStart(i7);
            if (runStart > this.mLen) {
                return;
            }
            int iMin = Math.min(this.mDirections.getRunLength(i7) + runStart, this.mLen);
            boolean zIsRunRtl = this.mDirections.isRunRtl(i7);
            int iCalculateRunFlag = calculateRunFlag(i7, runCount, this.mDir);
            float fDrawRun = f2;
            for (int i8 = this.mHasTabs ? runStart : iMin; i8 <= iMin; i8++) {
                if (i8 == iMin || charAt(i8) == '\t') {
                    float f3 = f + fDrawRun;
                    if (i7 == runCount - 1 && i8 == this.mLen) {
                        z = false;
                        canvas2 = canvas;
                        i4 = i;
                        i5 = i2;
                        i6 = i3;
                    } else {
                        z = true;
                        i4 = i;
                        i5 = i2;
                        i6 = i3;
                        canvas2 = canvas;
                    }
                    fDrawRun += drawRun(canvas2, runStart, i8, zIsRunRtl, f3, i4, i5, i6, z, iCalculateRunFlag);
                    if (i8 != iMin) {
                        int i9 = this.mDir;
                        fDrawRun = i9 * nextTab(fDrawRun * i9);
                    }
                    runStart = i8 + 1;
                }
            }
            i7++;
            f2 = fDrawRun;
        }
    }

    public float metrics(Paint.FontMetricsInt fontMetricsInt, RectF rectF, boolean z, LineInfo lineInfo) {
        float fMax;
        if (z) {
            if (rectF == null) {
                if (this.mTmpRectForMeasure == null) {
                    this.mTmpRectForMeasure = new RectF();
                }
                rectF = this.mTmpRectForMeasure;
            }
            RectF rectF2 = rectF;
            rectF2.setEmpty();
            float fMeasure = measure(this.mLen, false, fontMetricsInt, rectF2, lineInfo);
            if (fMeasure >= 0.0f) {
                fMax = Math.max(rectF2.right, fMeasure) - Math.min(0.0f, rectF2.left);
            } else {
                fMax = Math.max(rectF2.right, 0.0f) - Math.min(fMeasure, rectF2.left);
            }
            return Math.abs(fMeasure) > fMax ? fMeasure : Math.signum(fMeasure) * fMax;
        }
        return measure(this.mLen, false, fontMetricsInt, rectF, lineInfo);
    }

    void shape(TextShaper.GlyphsConsumer glyphsConsumer) {
        int runCount = this.mDirections.getRunCount();
        float f = 0.0f;
        int i = 0;
        while (i < runCount) {
            int runStart = this.mDirections.getRunStart(i);
            if (runStart > this.mLen) {
                return;
            }
            int iMin = Math.min(this.mDirections.getRunLength(i) + runStart, this.mLen);
            boolean zIsRunRtl = this.mDirections.isRunRtl(i);
            int iCalculateRunFlag = calculateRunFlag(i, runCount, this.mDir);
            int i2 = this.mHasTabs ? runStart : iMin;
            float fShapeRun = f;
            while (i2 <= iMin) {
                if (i2 == iMin || charAt(i2) == '\t') {
                    fShapeRun += shapeRun(glyphsConsumer, runStart, i2, zIsRunRtl, 0.0f + fShapeRun, (i == runCount + (-1) && i2 == this.mLen) ? false : true, iCalculateRunFlag);
                    if (i2 != iMin) {
                        int i3 = this.mDir;
                        fShapeRun = i3 * nextTab(fShapeRun * i3);
                    }
                    runStart = i2 + 1;
                }
                i2++;
            }
            i++;
            f = fShapeRun;
        }
    }

    public float measure(int i, boolean z, Paint.FontMetricsInt fontMetricsInt, RectF rectF, LineInfo lineInfo) {
        boolean z2;
        int i2;
        float fMeasureRun;
        TextLine textLine = this;
        int i3 = i;
        LineInfo lineInfo2 = lineInfo;
        if (i3 > textLine.mLen) {
            throw new IndexOutOfBoundsException("offset(" + i3 + ") should be less than line limit(" + textLine.mLen + NavigationBarInflaterView.KEY_CODE_END);
        }
        boolean z3 = false;
        if (lineInfo2 != null) {
            lineInfo2.setClusterCount(0);
        }
        int i4 = z ? i3 - 1 : i3;
        float f = 0.0f;
        if (i4 < 0) {
            return 0.0f;
        }
        int runCount = textLine.mDirections.getRunCount();
        int i5 = 0;
        while (i5 < runCount) {
            int runStart = textLine.mDirections.getRunStart(i5);
            if (runStart > textLine.mLen) {
                break;
            }
            int iMin = Math.min(textLine.mDirections.getRunLength(i5) + runStart, textLine.mLen);
            boolean zIsRunRtl = textLine.mDirections.isRunRtl(i5);
            int iCalculateRunFlag = calculateRunFlag(i5, runCount, textLine.mDir);
            float fNextTab = f;
            int i6 = runStart;
            int i7 = textLine.mHasTabs ? runStart : iMin;
            while (i7 <= iMin) {
                if (i7 == iMin || textLine.charAt(i7) == '\t') {
                    boolean z4 = (i4 < i6 || i4 >= i7) ? z3 : true;
                    boolean z5 = (textLine.mDir == -1 ? true : z3) == zIsRunRtl ? true : z3;
                    if (z4 && z5) {
                        fMeasureRun = textLine.measureRun(i6, i3, i7, zIsRunRtl, fontMetricsInt, rectF, null, 0, fNextTab, lineInfo2, iCalculateRunFlag);
                    } else {
                        int i8 = iMin;
                        z2 = zIsRunRtl;
                        int i9 = i7;
                        i2 = i8;
                        float fMeasureRun2 = measureRun(i6, i9, i7, z2, fontMetricsInt, rectF, null, 0, fNextTab, lineInfo, iCalculateRunFlag);
                        if (!z5) {
                            fMeasureRun2 = -fMeasureRun2;
                        }
                        fNextTab += fMeasureRun2;
                        if (z4) {
                            fMeasureRun = measureRun(i6, i, i9, z2, null, null, null, 0, fNextTab, lineInfo, iCalculateRunFlag);
                        } else {
                            textLine = this;
                            i7 = i9;
                            i3 = i;
                            if (i7 != i2) {
                                if (i3 == i7) {
                                    return fNextTab;
                                }
                                int i10 = textLine.mDir;
                                fNextTab = i10 * textLine.nextTab(fNextTab * i10);
                                if (i4 == i7) {
                                    return fNextTab;
                                }
                            }
                            i6 = i7 + 1;
                        }
                    }
                    return fNextTab + fMeasureRun;
                }
                i2 = iMin;
                z2 = zIsRunRtl;
                i7++;
                lineInfo2 = lineInfo;
                zIsRunRtl = z2;
                iMin = i2;
                z3 = false;
            }
            i5++;
            lineInfo2 = lineInfo;
            f = fNextTab;
            z3 = false;
        }
        return f;
    }

    public void measureAllBounds(float[] fArr, float[] fArr2) {
        int i;
        float fNextTab;
        float fNextTab2;
        if (fArr == null) {
            throw new IllegalArgumentException("bounds can't be null");
        }
        int length = fArr.length;
        int i2 = this.mLen;
        if (length < i2 * 2) {
            throw new IndexOutOfBoundsException("bounds doesn't have enough space to receive the result, needed: " + (this.mLen * 2) + " had: " + fArr.length);
        }
        float[] fArr3 = fArr2 == null ? new float[i2] : fArr2;
        if (fArr3.length < i2) {
            throw new IndexOutOfBoundsException("advance doesn't have enough space to receive the result, needed: " + this.mLen + " had: " + fArr3.length);
        }
        int runCount = this.mDirections.getRunCount();
        float f = 0.0f;
        int i3 = 0;
        while (i3 < runCount) {
            int runStart = this.mDirections.getRunStart(i3);
            if (runStart > this.mLen) {
                return;
            }
            int iMin = Math.min(this.mDirections.getRunLength(i3) + runStart, this.mLen);
            boolean zIsRunRtl = this.mDirections.isRunRtl(i3);
            int iCalculateRunFlag = calculateRunFlag(i3, runCount, this.mDir);
            float f2 = f;
            int i4 = runStart;
            int i5 = this.mHasTabs ? runStart : iMin;
            while (i5 <= iMin) {
                if (i5 == iMin || charAt(i5) == '\t') {
                    boolean z = (this.mDir == -1) == zIsRunRtl;
                    i = iMin;
                    float fMeasureRun = measureRun(i4, i5, i5, zIsRunRtl, null, null, fArr3, i4, 0.0f, null, iCalculateRunFlag);
                    if (!z) {
                        fMeasureRun = -fMeasureRun;
                    }
                    float f3 = f2 + fMeasureRun;
                    if (!z) {
                        f2 = f3;
                    }
                    while (i4 < i5 && i4 < this.mLen) {
                        if (zIsRunRtl) {
                            int i6 = i4 * 2;
                            fArr[i6 + 1] = f2;
                            f2 -= fArr3[i4];
                            fArr[i6] = f2;
                        } else {
                            int i7 = i4 * 2;
                            fArr[i7] = f2;
                            f2 += fArr3[i4];
                            fArr[i7 + 1] = f2;
                        }
                        i4++;
                    }
                    if (i5 != i) {
                        if (zIsRunRtl) {
                            int i8 = this.mDir;
                            fNextTab2 = i8 * nextTab(i8 * f3);
                            fNextTab = f3;
                            f3 = fNextTab2;
                        } else {
                            int i9 = this.mDir;
                            fNextTab = i9 * nextTab(i9 * f3);
                            fNextTab2 = fNextTab;
                        }
                        int i10 = i5 * 2;
                        fArr[i10] = f3;
                        fArr[i10 + 1] = fNextTab;
                        fArr3[i5] = fNextTab - f3;
                        f2 = fNextTab2;
                    } else {
                        f2 = f3;
                    }
                    i4 = i5 + 1;
                } else {
                    i = iMin;
                }
                i5++;
                iMin = i;
            }
            i3++;
            f = f2;
        }
    }

    public float[] measureAllOffsets(boolean[] zArr, Paint.FontMetricsInt fontMetricsInt) {
        int i;
        int i2;
        boolean z;
        float f;
        boolean z2 = true;
        float[] fArr = new float[this.mLen + 1];
        if (zArr[0]) {
            fArr[0] = 0.0f;
        }
        int runCount = this.mDirections.getRunCount();
        int i3 = 0;
        float f2 = 0.0f;
        while (i3 < runCount) {
            int runStart = this.mDirections.getRunStart(i3);
            if (runStart > this.mLen) {
                break;
            }
            int iMin = Math.min(this.mDirections.getRunLength(i3) + runStart, this.mLen);
            boolean zIsRunRtl = this.mDirections.isRunRtl(i3);
            int iCalculateRunFlag = calculateRunFlag(i3, runCount, this.mDir);
            float f3 = f2;
            int i4 = this.mHasTabs ? runStart : iMin;
            while (i4 <= iMin) {
                if (i4 == iMin || charAt(i4) == '\t') {
                    boolean z3 = (this.mDir == -1 ? z2 : false) == zIsRunRtl ? z2 : false;
                    float f4 = fArr[runStart];
                    int i5 = i3;
                    int i6 = runStart;
                    i = i5;
                    i2 = iMin;
                    z = zIsRunRtl;
                    float fMeasureRun = measureRun(i6, i4, i4, z, fontMetricsInt, null, fArr, i6, 0.0f, null, iCalculateRunFlag);
                    if (!z3) {
                        fMeasureRun = -fMeasureRun;
                    }
                    float fNextTab = f3 + fMeasureRun;
                    if (!z3) {
                        f3 = fNextTab;
                    }
                    int iMin2 = Math.min(i4, this.mLen);
                    for (int i7 = i6; i7 <= iMin2; i7++) {
                        if (i7 < iMin2) {
                            f = z ? -fArr[i7] : fArr[i7];
                        } else {
                            f = 0.0f;
                        }
                        if (i7 == i6 && zArr[i7]) {
                            fArr[i7] = f4;
                        } else if (i7 != iMin2 || zArr[i7]) {
                            fArr[i7] = f3;
                        }
                        f3 += f;
                    }
                    if (i4 != i2) {
                        if (!zArr[i4]) {
                            fArr[i4] = fNextTab;
                        }
                        int i8 = this.mDir;
                        fNextTab = i8 * nextTab(fNextTab * i8);
                        int i9 = i4 + 1;
                        if (zArr[i9]) {
                            fArr[i9] = fNextTab;
                        }
                    }
                    f3 = fNextTab;
                    runStart = i4 + 1;
                } else {
                    i = i3;
                    i2 = iMin;
                    z = zIsRunRtl;
                }
                i4++;
                zIsRunRtl = z;
                iMin = i2;
                i3 = i;
                z2 = true;
            }
            i3++;
            f2 = f3;
            z2 = true;
        }
        int i10 = this.mLen;
        if (!zArr[i10]) {
            fArr[i10] = f2;
        }
        return fArr;
    }

    private float drawRun(Canvas canvas, int i, int i2, boolean z, float f, int i3, int i4, int i5, boolean z2, int i6) {
        if ((this.mDir == 1) == z) {
            float f2 = -measureRun(i, i2, i2, z, null, null, null, 0, 0.0f, null, i6);
            handleRun(i, i2, i2, z, canvas, null, f + f2, i3, i4, i5, null, null, false, null, 0, null, i6);
            return f2;
        }
        return handleRun(i, i2, i2, z, canvas, null, f, i3, i4, i5, null, null, z2, null, 0, null, i6);
    }

    private float measureRun(int i, int i2, int i3, boolean z, Paint.FontMetricsInt fontMetricsInt, RectF rectF, float[] fArr, int i4, float f, LineInfo lineInfo, int i5) {
        if (rectF != null) {
            if ((this.mDir == 1) == z) {
                return handleRun(i, i2, i3, z, null, null, f + (-measureRun(i, i2, i3, z, null, null, null, 0, 0.0f, null, i5)), 0, 0, 0, fontMetricsInt, rectF, true, fArr, i4, lineInfo, i5);
            }
        }
        return handleRun(i, i2, i3, z, null, null, f, 0, 0, 0, fontMetricsInt, rectF, true, fArr, i4, lineInfo, i5);
    }

    private float shapeRun(TextShaper.GlyphsConsumer glyphsConsumer, int i, int i2, boolean z, float f, boolean z2, int i3) {
        if ((this.mDir == 1) == z) {
            float f2 = -measureRun(i, i2, i2, z, null, null, null, 0, 0.0f, null, i3);
            handleRun(i, i2, i2, z, null, glyphsConsumer, f + f2, 0, 0, 0, null, null, false, null, 0, null, i3);
            return f2;
        }
        return handleRun(i, i2, i2, z, null, glyphsConsumer, f, 0, 0, 0, null, null, z2, null, 0, null, i3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x011b, code lost:
    
        if (r0 == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0121, code lost:
    
        return r20.mLen + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0122, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0123, code lost:
    
        if (r4 > r8) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0125, code lost:
    
        if (r0 == false) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x0127, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0128, code lost:
    
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0129, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0119, code lost:
    
        if (r4 != (-1)) goto L104;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00d3  */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1 */
    /* JADX WARN: Type inference failed for: r17v3 */
    /* JADX WARN: Type inference failed for: r17v4 */
    /* JADX WARN: Type inference failed for: r17v5, types: [int] */
    /* JADX WARN: Type inference failed for: r17v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int getOffsetToLeftRightOf(int i, boolean z) {
        int length;
        int i2;
        boolean z2;
        ?? r17;
        boolean z3;
        int i3;
        int offsetBeforeAfter;
        int i4;
        int i5;
        int i6 = this.mLen;
        boolean z4 = this.mDir == -1;
        int[] iArr = this.mDirections.mDirections;
        int i7 = 67108863;
        if (i == 0) {
            offsetBeforeAfter = -1;
            i2 = 67108863;
            length = -2;
        } else if (i == i6) {
            length = iArr.length;
            offsetBeforeAfter = -1;
            i2 = 67108863;
        } else {
            int i8 = i6;
            length = 0;
            int i9 = 0;
            while (true) {
                if (length >= iArr.length) {
                    i2 = i7;
                    z2 = true;
                    r17 = 0;
                    z3 = false;
                    i3 = 0;
                    break;
                }
                i9 = iArr[length];
                if (i >= i9) {
                    int i10 = iArr[length + 1];
                    int i11 = (i10 & i7) + i9;
                    if (i11 > i6) {
                        i11 = i6;
                    }
                    if (i < i11) {
                        int i12 = (i10 >>> 26) & 63;
                        if (i == i9) {
                            int i13 = i - 1;
                            int i14 = 0;
                            z2 = true;
                            r17 = 0;
                            while (true) {
                                if (i14 >= iArr.length) {
                                    i2 = i7;
                                    i4 = i11;
                                    z3 = false;
                                    break;
                                }
                                int i15 = iArr[i14];
                                if (i13 >= i15) {
                                    int i16 = iArr[i14 + 1];
                                    i4 = i15 + (i16 & i7);
                                    if (i4 > i6) {
                                        i4 = i6;
                                    }
                                    if (i13 < i4) {
                                        i2 = i7;
                                        int i17 = (i16 >>> 26) & 63;
                                        if (i17 < i12) {
                                            length = i14;
                                            i9 = i15;
                                            i12 = i17;
                                            z3 = true;
                                            break;
                                        }
                                    } else {
                                        i2 = i7;
                                    }
                                }
                                i14 += 2;
                                i7 = i2;
                            }
                            i3 = i12;
                            i8 = i4;
                        } else {
                            i2 = i7;
                            z2 = true;
                            r17 = 0;
                            i3 = i12;
                            i8 = i11;
                            z3 = false;
                        }
                    } else {
                        i8 = i11;
                    }
                }
                length += 2;
                i7 = i7;
            }
            if (length == iArr.length) {
                offsetBeforeAfter = -1;
            } else {
                boolean z5 = (i3 & 1) != 0 ? z2 : r17 == true ? 1 : 0;
                boolean z6 = z5;
                boolean z7 = z == z5 ? z2 : r17 == true ? 1 : 0;
                if (i != (z7 ? i8 : i9) || z7 != z3) {
                    offsetBeforeAfter = getOffsetBeforeAfter(length, i9, i8, z6, i, z7);
                    if (z7) {
                        i9 = i8;
                    }
                    if (offsetBeforeAfter != i9) {
                        return offsetBeforeAfter;
                    }
                }
            }
            while (true) {
                boolean z8 = z != z4 ? z2 : r17;
                length += !z8 ? 2 : -2;
                if (length < 0 || length >= iArr.length) {
                    break;
                }
                int i18 = iArr[length];
                int i19 = iArr[length + 1];
                int i20 = (i19 & i2) + i18;
                if (i20 > i6) {
                    i20 = i6;
                }
                int i21 = i19 >>> 26;
                i5 = i21 & 63;
                boolean z9 = (i21 & 1) != 0 ? z2 : r17;
                boolean z10 = z == z9 ? z2 : r17;
                if (offsetBeforeAfter == -1) {
                    offsetBeforeAfter = getOffsetBeforeAfter(length, i18, i20, z9, z10 ? i18 : i20, z10);
                    if (z10) {
                        i18 = i20;
                    }
                    if (offsetBeforeAfter != i18) {
                        return offsetBeforeAfter;
                    }
                    i3 = i5;
                } else if (i5 < i3) {
                    return z10 ? i18 : i20;
                }
            }
        }
        i3 = 0;
        z2 = true;
        r17 = 0;
        while (true) {
            if (z != z4) {
            }
            length += !z8 ? 2 : -2;
            if (length < 0) {
                break;
            }
            break;
            break;
            i3 = i5;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x00a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int getOffsetBeforeAfter(int i, int i2, int i3, boolean z, int i4, boolean z2) {
        int offsetBefore;
        int i5;
        int i6;
        if (i >= 0) {
            if (i4 != (z2 ? this.mLen : 0)) {
                TextPaint textPaint = this.mWorkPaint;
                textPaint.set(this.mPaint);
                if (this.mIsJustifying) {
                    textPaint.setWordSpacing(this.mAddedWordSpacingInPx);
                    textPaint.setLetterSpacing(this.mAddedLetterSpacingInPx / textPaint.getTextSize());
                }
                if (this.mSpanned != null && i2 != i3) {
                    int i7 = z2 ? i4 + 1 : i4;
                    int i8 = this.mStart + i3;
                    while (true) {
                        int iNextSpanTransition = this.mSpanned.nextSpanTransition(this.mStart + i2, i8, MetricAffectingSpan.class);
                        i6 = this.mStart;
                        i3 = iNextSpanTransition - i6;
                        if (i3 >= i7) {
                            break;
                        }
                        i2 = i3;
                    }
                    MetricAffectingSpan[] metricAffectingSpanArr = (MetricAffectingSpan[]) TextUtils.removeEmptySpans((MetricAffectingSpan[]) this.mSpanned.getSpans(i6 + i2, i6 + i3, MetricAffectingSpan.class), this.mSpanned, MetricAffectingSpan.class);
                    if (metricAffectingSpanArr.length > 0) {
                        ReplacementSpan replacementSpan = null;
                        for (MetricAffectingSpan metricAffectingSpan : metricAffectingSpanArr) {
                            if (metricAffectingSpan instanceof ReplacementSpan) {
                                replacementSpan = (ReplacementSpan) metricAffectingSpan;
                            } else {
                                metricAffectingSpan.updateMeasureState(textPaint);
                            }
                        }
                        if (replacementSpan != null) {
                            return z2 ? i3 : i2;
                        }
                    }
                }
                int i9 = i2;
                int i10 = z2 ? 0 : 2;
                if (this.mCharsValid) {
                    return textPaint.getTextRunCursor(this.mChars, i9, i3 - i9, z, i4, i10);
                }
                CharSequence charSequence = this.mText;
                int i11 = this.mStart;
                offsetBefore = textPaint.getTextRunCursor(charSequence, i9 + i11, i11 + i3, z, i4 + i11, i10);
                i5 = this.mStart;
            } else if (z2) {
                offsetBefore = TextUtils.getOffsetAfter(this.mText, i4 + this.mStart);
                i5 = this.mStart;
            } else {
                offsetBefore = TextUtils.getOffsetBefore(this.mText, i4 + this.mStart);
                i5 = this.mStart;
            }
        }
        return offsetBefore - i5;
    }

    private static void expandMetricsFromPaint(Paint.FontMetricsInt fontMetricsInt, TextPaint textPaint) {
        int i = fontMetricsInt.top;
        int i2 = fontMetricsInt.ascent;
        int i3 = fontMetricsInt.descent;
        int i4 = fontMetricsInt.bottom;
        int i5 = fontMetricsInt.leading;
        textPaint.getFontMetricsInt(fontMetricsInt);
        updateMetrics(fontMetricsInt, i, i2, i3, i4, i5);
    }

    private void expandMetricsFromPaint(TextPaint textPaint, int i, int i2, int i3, int i4, boolean z, Paint.FontMetricsInt fontMetricsInt) {
        Paint.FontMetricsInt fontMetricsInt2;
        int i5 = fontMetricsInt.top;
        int i6 = fontMetricsInt.ascent;
        int i7 = fontMetricsInt.descent;
        int i8 = fontMetricsInt.bottom;
        int i9 = fontMetricsInt.leading;
        int i10 = i2 - i;
        int i11 = i4 - i3;
        if (this.mCharsValid) {
            fontMetricsInt2 = fontMetricsInt;
            textPaint.getFontMetricsInt(this.mChars, i, i10, i3, i11, z, fontMetricsInt2);
        } else {
            fontMetricsInt2 = fontMetricsInt;
            PrecomputedText precomputedText = this.mComputed;
            if (precomputedText == null) {
                CharSequence charSequence = this.mText;
                int i12 = this.mStart;
                textPaint.getFontMetricsInt(charSequence, i + i12, i10, i3 + i12, i11, z, fontMetricsInt2);
            } else {
                int i13 = this.mStart;
                precomputedText.getFontMetricsInt(i13 + i, i13 + i2, fontMetricsInt2);
            }
        }
        updateMetrics(fontMetricsInt2, i5, i6, i7, i8, i9);
    }

    static void updateMetrics(Paint.FontMetricsInt fontMetricsInt, int i, int i2, int i3, int i4, int i5) {
        fontMetricsInt.top = Math.min(fontMetricsInt.top, i);
        fontMetricsInt.ascent = Math.min(fontMetricsInt.ascent, i2);
        fontMetricsInt.descent = Math.max(fontMetricsInt.descent, i3);
        fontMetricsInt.bottom = Math.max(fontMetricsInt.bottom, i4);
        fontMetricsInt.leading = Math.max(fontMetricsInt.leading, i5);
    }

    private static void drawStroke(TextPaint textPaint, Canvas canvas, int i, float f, float f2, float f3, float f4, float f5) {
        float f6 = f5 + textPaint.baselineShift + f;
        int color = textPaint.getColor();
        Paint.Style style = textPaint.getStyle();
        boolean zIsAntiAlias = textPaint.isAntiAlias();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(i);
        canvas.drawRect(f3, f6, f4, f2 + f6, textPaint);
        textPaint.setStyle(style);
        textPaint.setColor(color);
        textPaint.setAntiAlias(zIsAntiAlias);
    }

    private float getRunAdvance(TextPaint textPaint, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6, RectF rectF, LineInfo lineInfo) {
        if (lineInfo != null) {
            if (this.mRunInfo == null) {
                this.mRunInfo = new Paint.RunInfo();
            }
            this.mRunInfo.setClusterCount(0);
        } else {
            this.mRunInfo = null;
        }
        if (this.mCharsValid) {
            float runCharacterAdvance = textPaint.getRunCharacterAdvance(this.mChars, i, i2, i3, i4, z, i5, fArr, i6, rectF, this.mRunInfo);
            if (lineInfo != null) {
                lineInfo.setClusterCount(lineInfo.getClusterCount() + this.mRunInfo.getClusterCount());
            }
            return runCharacterAdvance;
        }
        int i7 = this.mStart;
        if (this.mComputed == null || fArr != null || lineInfo != null) {
            float runCharacterAdvance2 = textPaint.getRunCharacterAdvance(this.mText, i7 + i, i7 + i2, i7 + i3, i7 + i4, z, i7 + i5, fArr, i6, rectF, this.mRunInfo);
            if (lineInfo != null) {
                lineInfo.setClusterCount(lineInfo.getClusterCount() + this.mRunInfo.getClusterCount());
            }
            return runCharacterAdvance2;
        }
        if (rectF != null) {
            if (this.mTmpRectForPrecompute == null) {
                this.mTmpRectForPrecompute = new Rect();
            }
            this.mComputed.getBounds(i + i7, i2 + i7, this.mTmpRectForPrecompute);
            rectF.set(this.mTmpRectForPrecompute);
        }
        return this.mComputed.getWidth(i + i7, i2 + i7);
    }

    private float handleText(TextPaint textPaint, int i, int i2, int i3, int i4, boolean z, Canvas canvas, TextShaper.GlyphsConsumer glyphsConsumer, float f, int i5, int i6, int i7, Paint.FontMetricsInt fontMetricsInt, RectF rectF, boolean z2, int i8, ArrayList<DecorationInfo> arrayList, float[] fArr, int i9, LineInfo lineInfo, int i10) {
        int i11;
        Paint.FontMetricsInt fontMetricsInt2;
        float f2;
        TextPaint textPaint2;
        float f3;
        float f4;
        int i12;
        float f5 = f;
        if (this.mIsJustifying) {
            textPaint.setWordSpacing(this.mAddedWordSpacingInPx);
            textPaint.setLetterSpacing(this.mAddedLetterSpacingInPx / textPaint.getTextSize());
        }
        Paint.FontMetricsInt fontMetricsInt3 = (rectF == null || fontMetricsInt != null) ? fontMetricsInt : new Paint.FontMetricsInt();
        if (fontMetricsInt3 != null) {
            expandMetricsFromPaint(fontMetricsInt3, textPaint);
        }
        float fMeasureText = 0.0f;
        if (i2 == i) {
            return 0.0f;
        }
        if ((i10 & 8192) == 8192) {
            textPaint.setFlags(textPaint.getFlags() | 8192);
        } else {
            textPaint.setFlags(textPaint.getFlags() & (-8193));
        }
        if ((i10 & 16384) == 16384) {
            textPaint.setFlags(textPaint.getFlags() | 16384);
        } else {
            textPaint.setFlags(textPaint.getFlags() & (-16385));
        }
        int size = arrayList == null ? 0 : arrayList.size();
        if (z2 || !((canvas == null && glyphsConsumer == null) || (textPaint.bgColor == 0 && size == 0 && !z))) {
            if (rectF != null && this.mTmpRectForPaintAPI == null) {
                this.mTmpRectForPaintAPI = new RectF();
            }
            i11 = size;
            fontMetricsInt2 = fontMetricsInt3;
            float runAdvance = getRunAdvance(textPaint, i, i2, i3, i4, z, i8, fArr, i9, rectF == null ? null : this.mTmpRectForPaintAPI, lineInfo);
            if (rectF != null) {
                if (z) {
                    this.mTmpRectForPaintAPI.offset(f5 - runAdvance, 0.0f);
                } else {
                    this.mTmpRectForPaintAPI.offset(f5, 0.0f);
                }
                rectF.union(this.mTmpRectForPaintAPI);
            }
            fMeasureText = runAdvance;
        } else {
            i11 = size;
            fontMetricsInt2 = fontMetricsInt3;
        }
        int i13 = (this.mStart + i2) - 1;
        if (i13 >= 0 && TextUtils.semNeedMoreWidth(this.mText.charAt(i13))) {
            fMeasureText += textPaint.measureText(" ");
        }
        float f6 = fMeasureText;
        if (z) {
            f2 = f5 - f6;
        } else {
            f2 = f5;
            f5 += f6;
        }
        if (glyphsConsumer != null) {
            shapeTextRun(glyphsConsumer, textPaint, i, i2, i3, i4, z, f2);
        }
        if (!this.mUseFallbackExtent || fontMetricsInt2 == null) {
            textPaint2 = textPaint;
        } else {
            textPaint2 = textPaint;
            expandMetricsFromPaint(textPaint2, i, i2, i3, i4, z, fontMetricsInt2);
        }
        if (canvas != null) {
            if (textPaint2.bgColor != 0) {
                int color = textPaint2.getColor();
                Paint.Style style = textPaint2.getStyle();
                textPaint2.setColor(textPaint2.bgColor);
                textPaint2.setStyle(Paint.Style.FILL);
                TextPaint textPaint3 = textPaint2;
                canvas.drawRect(f2, i5, f5, i7, textPaint3);
                textPaint2 = textPaint3;
                textPaint2.setStyle(style);
                textPaint2.setColor(color);
            }
            drawTextRun(canvas, textPaint2, i, i2, i3, i4, z, f2, i6 + textPaint2.baselineShift);
            int i14 = i;
            float f7 = f2;
            if (i11 != 0) {
                int i15 = 0;
                while (i15 < i11) {
                    DecorationInfo decorationInfo = arrayList.get(i15);
                    int iMax = Math.max(decorationInfo.start, i14);
                    int iMin = Math.min(decorationInfo.end, i8);
                    int i16 = i15;
                    float f8 = f5;
                    float runAdvance2 = getRunAdvance(textPaint, i14, i2, i3, i4, z, iMax, null, 0, null, null);
                    float runAdvance3 = getRunAdvance(textPaint, i, i2, i3, i4, z, iMin, null, 0, null, null);
                    if (z) {
                        f3 = f8 - runAdvance3;
                        f4 = f8 - runAdvance2;
                    } else {
                        f3 = f7 + runAdvance2;
                        f4 = f7 + runAdvance3;
                    }
                    float f9 = f3;
                    float f10 = f4;
                    if (decorationInfo.underlineColor != 0) {
                        i12 = i6;
                        drawStroke(textPaint, canvas, decorationInfo.underlineColor, textPaint.getUnderlinePosition(), decorationInfo.underlineThickness, f9, f10, i12);
                    } else {
                        i12 = i6;
                    }
                    if (decorationInfo.isUnderlineText) {
                        drawStroke(textPaint, canvas, textPaint.getColor(), textPaint.getUnderlinePosition(), Math.max(textPaint.getUnderlineThickness(), 1.0f), f9, f10, i12);
                    }
                    if (decorationInfo.isStrikeThruText) {
                        drawStroke(textPaint, canvas, textPaint.getColor(), textPaint.getStrikeThruPosition(), Math.max(textPaint.getStrikeThruThickness(), 1.0f), f9, f10, i12);
                    }
                    i15 = i16 + 1;
                    i14 = i;
                    f5 = f8;
                }
            }
        }
        return z ? -f6 : f6;
    }

    private float handleReplacement(ReplacementSpan replacementSpan, TextPaint textPaint, int i, int i2, boolean z, Canvas canvas, float f, int i3, int i4, int i5, Paint.FontMetricsInt fontMetricsInt, boolean z2) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        float f2;
        int i13 = this.mStart;
        int i14 = i13 + i;
        int i15 = i13 + i2;
        if (z2 || (canvas != null && z)) {
            boolean z3 = fontMetricsInt != null;
            if (z3) {
                int i16 = fontMetricsInt.top;
                int i17 = fontMetricsInt.ascent;
                i8 = fontMetricsInt.descent;
                i9 = fontMetricsInt.bottom;
                i10 = fontMetricsInt.leading;
                i6 = i16;
                i7 = i17;
            } else {
                i6 = 0;
                i7 = 0;
                i8 = 0;
                i9 = 0;
                i10 = 0;
            }
            i11 = i14;
            i12 = i15;
            float size = replacementSpan.getSize(textPaint, this.mText, i14, i15, fontMetricsInt);
            if (z3) {
                updateMetrics(fontMetricsInt, i6, i7, i8, i9, i10);
            }
            f2 = size;
        } else {
            f2 = 0.0f;
            i11 = i14;
            i12 = i15;
        }
        if (canvas != null) {
            replacementSpan.draw(canvas, this.mText, i11, i12, z ? f - f2 : f, i3, i4, i5, textPaint);
        }
        return z ? -f2 : f2;
    }

    private int adjustEndHyphenEdit(int i, int i2) {
        if (i < this.mLen) {
            return 0;
        }
        return i2;
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
            DecorationInfo decorationInfo = new DecorationInfo();
            decorationInfo.isStrikeThruText = this.isStrikeThruText;
            decorationInfo.isUnderlineText = this.isUnderlineText;
            decorationInfo.underlineColor = this.underlineColor;
            decorationInfo.underlineThickness = this.underlineThickness;
            return decorationInfo;
        }
    }

    private void extractDecorationInfo(TextPaint textPaint, DecorationInfo decorationInfo) {
        decorationInfo.isStrikeThruText = textPaint.isStrikeThruText();
        if (decorationInfo.isStrikeThruText) {
            textPaint.setStrikeThruText(false);
        }
        decorationInfo.isUnderlineText = textPaint.isUnderlineText();
        if (decorationInfo.isUnderlineText) {
            textPaint.setUnderlineText(false);
        }
        decorationInfo.underlineColor = textPaint.underlineColor;
        decorationInfo.underlineThickness = textPaint.underlineThickness;
        textPaint.setUnderlineText(0, 0.0f);
    }

    /* JADX WARN: Removed duplicated region for block: B:96:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x028c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private float handleRun(int i, int i2, int i3, boolean z, Canvas canvas, TextShaper.GlyphsConsumer glyphsConsumer, float f, int i4, int i5, int i6, Paint.FontMetricsInt fontMetricsInt, RectF rectF, boolean z2, float[] fArr, int i7, LineInfo lineInfo, int i8) {
        float f2;
        int i9;
        int i10;
        TextPaint textPaint;
        float f3;
        boolean z3;
        float f4;
        int i11;
        TextPaint textPaint2;
        DecorationInfo decorationInfo;
        int i12;
        int i13;
        int i14;
        TextPaint textPaint3;
        int i15;
        TextLine textLine = this;
        Paint.FontMetricsInt fontMetricsInt2 = fontMetricsInt;
        if (i2 < i || i2 > i3) {
            throw new IndexOutOfBoundsException("measureLimit (" + i2 + ") is out of start (" + i + ") and limit (" + i3 + ") bounds");
        }
        if (fArr != null && fArr.length - i7 < i2 - i) {
            throw new IndexOutOfBoundsException("advances doesn't have enough space to receive the result");
        }
        float f5 = 0.0f;
        if (i == i2) {
            TextPaint textPaint4 = textLine.mWorkPaint;
            textPaint4.set(textLine.mPaint);
            if (fontMetricsInt2 != null) {
                expandMetricsFromPaint(fontMetricsInt2, textPaint4);
            }
            if (rectF != null) {
                if (fontMetricsInt2 == null) {
                    Paint.FontMetricsInt fontMetricsInt3 = new Paint.FontMetricsInt();
                    expandMetricsFromPaint(fontMetricsInt3, textPaint4);
                    fontMetricsInt2 = fontMetricsInt3;
                }
                rectF.union(0.0f, fontMetricsInt2.top, 0.0f, fontMetricsInt2.bottom);
            }
            return 0.0f;
        }
        Spanned spanned = textLine.mSpanned;
        if (spanned != null) {
            SpanSet<MetricAffectingSpan> spanSet = textLine.mMetricAffectingSpanSpanSet;
            int i16 = textLine.mStart;
            spanSet.init(spanned, i16 + i, i16 + i3);
            SpanSet<CharacterStyle> spanSet2 = textLine.mCharacterStyleSpanSet;
            Spanned spanned2 = textLine.mSpanned;
            int i17 = textLine.mStart;
            spanSet2.init(spanned2, i17 + i, i17 + i3);
            if (textLine.mMetricAffectingSpanSpanSet.numberOfSpans != 0 || textLine.mCharacterStyleSpanSet.numberOfSpans != 0) {
                int i18 = i2;
                int i19 = i;
                float fHandleText = f;
                while (i19 < i18) {
                    TextPaint textPaint5 = textLine.mWorkPaint;
                    textPaint5.set(textLine.mPaint);
                    SpanSet<MetricAffectingSpan> spanSet3 = textLine.mMetricAffectingSpanSpanSet;
                    int i20 = textLine.mStart;
                    int nextTransition = spanSet3.getNextTransition(i20 + i19, i20 + i3) - textLine.mStart;
                    int iMin = Math.min(nextTransition, i18);
                    ReplacementSpan replacementSpan = null;
                    int i21 = 0;
                    while (true) {
                        if (i21 >= textLine.mMetricAffectingSpanSpanSet.numberOfSpans) {
                            break;
                        }
                        if (textLine.mMetricAffectingSpanSpanSet.spanStarts[i21] < textLine.mStart + iMin) {
                            int i22 = textLine.mMetricAffectingSpanSpanSet.spanEnds[i21];
                            int i23 = textLine.mStart;
                            if (i22 > i23 + i19) {
                                boolean z4 = i23 + textLine.mEllipsisStart <= textLine.mMetricAffectingSpanSpanSet.spanStarts[i21] && textLine.mMetricAffectingSpanSpanSet.spanEnds[i21] <= textLine.mStart + textLine.mEllipsisEnd;
                                MetricAffectingSpan metricAffectingSpan = textLine.mMetricAffectingSpanSpanSet.spans[i21];
                                if (metricAffectingSpan instanceof ReplacementSpan) {
                                    replacementSpan = !z4 ? (ReplacementSpan) metricAffectingSpan : null;
                                } else {
                                    metricAffectingSpan.updateDrawState(textPaint5);
                                }
                            }
                        }
                        i21++;
                    }
                    if (replacementSpan != null) {
                        int i24 = i19;
                        float fHandleReplacement = textLine.handleReplacement(replacementSpan, textPaint5, i24, iMin, z, canvas, fHandleText, i4, i5, i6, fontMetricsInt, z2 || iMin < i18);
                        float f6 = fHandleText + fHandleReplacement;
                        if (fArr != null) {
                            int i25 = (i7 + i24) - i;
                            if (z) {
                                fHandleReplacement = -fHandleReplacement;
                            }
                            fArr[i25] = fHandleReplacement;
                            for (int i26 = i24 + 1; i26 < iMin; i26++) {
                                fArr[(i7 + i26) - i] = f5;
                            }
                        }
                        fHandleText = f6;
                        f2 = f5;
                        i10 = i18;
                        i9 = nextTransition;
                    } else {
                        TextLine textLine2 = textLine;
                        TextPaint textPaint6 = textPaint5;
                        int i27 = iMin;
                        float f7 = fHandleText;
                        int i28 = i19;
                        TextPaint textPaint7 = textLine2.mActivePaint;
                        textPaint7.set(textLine2.mPaint);
                        DecorationInfo decorationInfo2 = textLine2.mDecorationInfo;
                        textLine2.mDecorations.clear();
                        float f8 = f7;
                        int i29 = i28;
                        int i30 = i29;
                        while (i30 < i27) {
                            SpanSet<CharacterStyle> spanSet4 = textLine2.mCharacterStyleSpanSet;
                            int i31 = textLine2.mStart;
                            int nextTransition2 = spanSet4.getNextTransition(i31 + i30, i31 + nextTransition) - textLine2.mStart;
                            int iMin2 = Math.min(nextTransition2, i27);
                            textPaint6.set(textLine2.mPaint);
                            for (int i32 = 0; i32 < textLine2.mCharacterStyleSpanSet.numberOfSpans; i32++) {
                                if (textLine2.mCharacterStyleSpanSet.spanStarts[i32] < textLine2.mStart + iMin2 && textLine2.mCharacterStyleSpanSet.spanEnds[i32] > textLine2.mStart + i30) {
                                    textLine2.mCharacterStyleSpanSet.spans[i32].updateDrawState(textPaint6);
                                }
                            }
                            textLine2.extractDecorationInfo(textPaint6, decorationInfo2);
                            if (i30 == i28) {
                                textPaint7.set(textPaint6);
                            } else {
                                if (!equalAttributes(textPaint6, textPaint7)) {
                                    int i33 = iMin;
                                    int i34 = i29;
                                    int i35 = i18;
                                    int iResolveRunFlagForSubSequence = resolveRunFlagForSubSequence(i8, z, i, i35, i34, i33);
                                    textPaint7.setStartHyphenEdit(textLine2.adjustStartHyphenEdit(i34, textLine2.mPaint.getStartHyphenEdit()));
                                    textPaint7.setEndHyphenEdit(textLine2.adjustEndHyphenEdit(i33, textLine2.mPaint.getEndHyphenEdit()));
                                    if (z2 || i33 < i35) {
                                        textPaint = textPaint7;
                                        f3 = f8;
                                        z3 = true;
                                    } else {
                                        textPaint = textPaint7;
                                        f3 = f8;
                                        z3 = false;
                                    }
                                    f4 = 0.0f;
                                    i11 = i27;
                                    int i36 = i28;
                                    TextLine textLine3 = textLine2;
                                    textPaint2 = textPaint6;
                                    decorationInfo = decorationInfo2;
                                    i12 = i30;
                                    i13 = nextTransition2;
                                    i14 = nextTransition;
                                    float fHandleText2 = textLine3.handleText(textPaint, i34, i33, i36, i14, z, canvas, glyphsConsumer, f3, i4, i5, i6, fontMetricsInt, rectF, z3, Math.min(i33, i27), textLine2.mDecorations, fArr, (i7 + i34) - i, lineInfo, iResolveRunFlagForSubSequence);
                                    textLine2 = textLine3;
                                    textPaint3 = textPaint;
                                    i28 = i36;
                                    f8 = f3 + fHandleText2;
                                    textPaint3.set(textPaint2);
                                    textLine2.mDecorations.clear();
                                    i29 = i12;
                                }
                                if (decorationInfo.hasDecoration()) {
                                    i15 = i13;
                                } else {
                                    DecorationInfo decorationInfoCopyInfo = decorationInfo.copyInfo();
                                    decorationInfoCopyInfo.start = i12;
                                    i15 = i13;
                                    decorationInfoCopyInfo.end = i15;
                                    textLine2.mDecorations.add(decorationInfoCopyInfo);
                                }
                                i18 = i2;
                                nextTransition = i14;
                                textPaint7 = textPaint3;
                                iMin = i15;
                                i30 = iMin;
                                i27 = i11;
                                decorationInfo2 = decorationInfo;
                                f5 = f4;
                                textPaint6 = textPaint2;
                            }
                            i13 = nextTransition2;
                            i11 = i27;
                            textPaint2 = textPaint6;
                            textPaint3 = textPaint7;
                            decorationInfo = decorationInfo2;
                            i12 = i30;
                            i14 = nextTransition;
                            f4 = 0.0f;
                            f8 = f8;
                            if (decorationInfo.hasDecoration()) {
                            }
                            i18 = i2;
                            nextTransition = i14;
                            textPaint7 = textPaint3;
                            iMin = i15;
                            i30 = iMin;
                            i27 = i11;
                            decorationInfo2 = decorationInfo;
                            f5 = f4;
                            textPaint6 = textPaint2;
                        }
                        int i37 = iMin;
                        int i38 = i27;
                        TextPaint textPaint8 = textPaint7;
                        f2 = f5;
                        i9 = nextTransition;
                        float f9 = f8;
                        int i39 = i29;
                        int i40 = i18;
                        int iResolveRunFlagForSubSequence2 = resolveRunFlagForSubSequence(i8, z, i, i40, i39, i37);
                        i10 = i40;
                        textPaint8.setStartHyphenEdit(textLine2.adjustStartHyphenEdit(i39, textLine2.mPaint.getStartHyphenEdit()));
                        textPaint8.setEndHyphenEdit(textLine2.adjustEndHyphenEdit(i37, textLine2.mPaint.getEndHyphenEdit()));
                        fHandleText = f9 + textLine2.handleText(textPaint8, i39, i37, i28, i9, z, canvas, glyphsConsumer, f9, i4, i5, i6, fontMetricsInt, rectF, z2 || i37 < i10, Math.min(i37, i38), textLine2.mDecorations, fArr, (i7 + i39) - i, lineInfo, iResolveRunFlagForSubSequence2);
                    }
                    textLine = this;
                    i18 = i10;
                    i19 = i9;
                    f5 = f2;
                }
                return fHandleText - f;
            }
        }
        TextPaint textPaint9 = textLine.mWorkPaint;
        textPaint9.set(textLine.mPaint);
        textPaint9.setStartHyphenEdit(textLine.adjustStartHyphenEdit(i, textPaint9.getStartHyphenEdit()));
        textPaint9.setEndHyphenEdit(textLine.adjustEndHyphenEdit(i3, textPaint9.getEndHyphenEdit()));
        return textLine.handleText(textPaint9, i, i3, i, i3, z, canvas, glyphsConsumer, f, i4, i5, i6, fontMetricsInt2, rectF, z2, i2, null, fArr, i7, lineInfo, i8);
    }

    private void drawTextRun(Canvas canvas, TextPaint textPaint, int i, int i2, int i3, int i4, boolean z, float f, int i5) {
        if (this.mCharsValid) {
            canvas.drawTextRun(this.mChars, i, i2 - i, i3, i4 - i3, f, i5, z, textPaint);
        } else {
            int i6 = this.mStart;
            canvas.drawTextRun(this.mText, i6 + i, i6 + i2, i6 + i3, i6 + i4, f, i5, z, textPaint);
        }
    }

    private void shapeTextRun(TextShaper.GlyphsConsumer glyphsConsumer, TextPaint textPaint, int i, int i2, int i3, int i4, boolean z, float f) {
        PositionedGlyphs positionedGlyphsShapeTextRun;
        int i5 = i2 - i;
        int i6 = i4 - i3;
        if (this.mCharsValid) {
            positionedGlyphsShapeTextRun = TextRunShaper.shapeTextRun(this.mChars, i, i5, i3, i6, f, 0.0f, z, textPaint);
        } else {
            CharSequence charSequence = this.mText;
            int i7 = this.mStart;
            positionedGlyphsShapeTextRun = TextRunShaper.shapeTextRun(charSequence, i7 + i, i5, i7 + i3, i6, f, 0.0f, z, textPaint);
        }
        glyphsConsumer.accept(i, i5, positionedGlyphsShapeTextRun, textPaint);
    }

    float nextTab(float f) {
        Layout.TabStops tabStops = this.mTabs;
        if (tabStops != null) {
            return tabStops.nextTab(f);
        }
        return Layout.TabStops.nextDefaultStop(f, 20.0f);
    }

    private int countStretchableSpaces(int i, int i2) {
        int i3 = 0;
        while (i < i2) {
            if (isStretchableWhitespace(this.mCharsValid ? this.mChars[i] : this.mText.charAt(this.mStart + i))) {
                i3++;
            }
            i++;
        }
        return i3;
    }

    private static boolean equalAttributes(TextPaint textPaint, TextPaint textPaint2) {
        return textPaint.getColorFilter() == textPaint2.getColorFilter() && textPaint.getMaskFilter() == textPaint2.getMaskFilter() && textPaint.getShader() == textPaint2.getShader() && textPaint.getTypeface() == textPaint2.getTypeface() && textPaint.getXfermode() == textPaint2.getXfermode() && textPaint.getTextLocales().equals(textPaint2.getTextLocales()) && TextUtils.equals(textPaint.getFontFeatureSettings(), textPaint2.getFontFeatureSettings()) && TextUtils.equals(textPaint.getFontVariationSettings(), textPaint2.getFontVariationSettings()) && textPaint.getShadowLayerRadius() == textPaint2.getShadowLayerRadius() && textPaint.getShadowLayerDx() == textPaint2.getShadowLayerDx() && textPaint.getShadowLayerDy() == textPaint2.getShadowLayerDy() && textPaint.getShadowLayerColor() == textPaint2.getShadowLayerColor() && textPaint.getFlags() == textPaint2.getFlags() && textPaint.getHinting() == textPaint2.getHinting() && textPaint.getStyle() == textPaint2.getStyle() && textPaint.getColor() == textPaint2.getColor() && textPaint.getStrokeWidth() == textPaint2.getStrokeWidth() && textPaint.getStrokeMiter() == textPaint2.getStrokeMiter() && textPaint.getStrokeCap() == textPaint2.getStrokeCap() && textPaint.getStrokeJoin() == textPaint2.getStrokeJoin() && textPaint.getTextAlign() == textPaint2.getTextAlign() && textPaint.isElegantTextHeight() == textPaint2.isElegantTextHeight() && textPaint.getTextSize() == textPaint2.getTextSize() && textPaint.getTextScaleX() == textPaint2.getTextScaleX() && textPaint.getTextSkewX() == textPaint2.getTextSkewX() && textPaint.getLetterSpacing() == textPaint2.getLetterSpacing() && textPaint.getWordSpacing() == textPaint2.getWordSpacing() && textPaint.getStartHyphenEdit() == textPaint2.getStartHyphenEdit() && textPaint.getEndHyphenEdit() == textPaint2.getEndHyphenEdit() && textPaint.bgColor == textPaint2.bgColor && textPaint.baselineShift == textPaint2.baselineShift && textPaint.linkColor == textPaint2.linkColor && textPaint.drawableState == textPaint2.drawableState && textPaint.density == textPaint2.density && textPaint.underlineColor == textPaint2.underlineColor && textPaint.underlineThickness == textPaint2.underlineThickness;
    }
}
