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

    /* JADX WARN: Removed duplicated region for block: B:10:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void set(android.text.TextPaint r2, java.lang.CharSequence r3, int r4, int r5, int r6, android.text.Layout.Directions r7, boolean r8, android.text.Layout.TabStops r9, int r10, int r11, boolean r12) {
        /*
            r1 = this;
            r1.mPaint = r2
            r1.mText = r3
            r1.mStart = r4
            int r0 = r5 - r4
            r1.mLen = r0
            r1.mDir = r6
            r1.mDirections = r7
            r1.mUseFallbackExtent = r12
            if (r7 == 0) goto Laa
            r1.mHasTabs = r8
            r6 = 0
            r1.mSpanned = r6
            boolean r7 = r3 instanceof android.text.Spanned
            r8 = 0
            if (r7 == 0) goto L2e
            r7 = r3
            android.text.Spanned r7 = (android.text.Spanned) r7
            r1.mSpanned = r7
            android.text.SpanSet<android.text.style.ReplacementSpan> r12 = r1.mReplacementSpanSpanSet
            r12.init(r7, r4, r5)
            android.text.SpanSet<android.text.style.ReplacementSpan> r7 = r1.mReplacementSpanSpanSet
            int r7 = r7.numberOfSpans
            if (r7 <= 0) goto L2e
            r7 = 1
            goto L2f
        L2e:
            r7 = r8
        L2f:
            r1.mComputed = r6
            boolean r12 = r3 instanceof android.text.PrecomputedText
            if (r12 == 0) goto L4a
            r12 = r3
            android.text.PrecomputedText r12 = (android.text.PrecomputedText) r12
            r1.mComputed = r12
            android.text.PrecomputedText$Params r12 = r12.getParams()
            android.text.TextPaint r12 = r12.getTextPaint()
            boolean r2 = r12.equalsForTextMeasurement(r2)
            if (r2 != 0) goto L4a
            r1.mComputed = r6
        L4a:
            r1.mCharsValid = r7
            if (r7 == 0) goto L95
            char[] r2 = r1.mChars
            if (r2 == 0) goto L57
            int r2 = r2.length
            int r6 = r1.mLen
            if (r2 >= r6) goto L5f
        L57:
            int r2 = r1.mLen
            char[] r2 = com.android.internal.util.ArrayUtils.newUnpaddedCharArray(r2)
            r1.mChars = r2
        L5f:
            char[] r2 = r1.mChars
            android.text.TextUtils.getChars(r3, r4, r5, r2, r8)
            if (r7 == 0) goto L95
            char[] r2 = r1.mChars
            r3 = r4
        L69:
            if (r3 >= r5) goto L95
            android.text.SpanSet<android.text.style.ReplacementSpan> r6 = r1.mReplacementSpanSpanSet
            int r6 = r6.getNextTransition(r3, r5)
            android.text.SpanSet<android.text.style.ReplacementSpan> r7 = r1.mReplacementSpanSpanSet
            boolean r7 = r7.hasSpansIntersecting(r3, r6)
            if (r7 == 0) goto L93
            int r3 = r3 - r4
            if (r3 >= r11) goto L80
            int r7 = r6 - r4
            if (r7 > r10) goto L93
        L80:
            r7 = 65532(0xfffc, float:9.183E-41)
            r2[r3] = r7
            int r3 = r3 + 1
            int r7 = r6 - r4
        L89:
            if (r3 >= r7) goto L93
            r12 = 65279(0xfeff, float:9.1475E-41)
            r2[r3] = r12
            int r3 = r3 + 1
            goto L89
        L93:
            r3 = r6
            goto L69
        L95:
            r1.mTabs = r9
            r2 = 0
            r1.mAddedWordSpacingInPx = r2
            r1.mIsJustifying = r8
            if (r10 == r11) goto La0
            r2 = r10
            goto La1
        La0:
            r2 = r8
        La1:
            r1.mEllipsisStart = r2
            if (r10 == r11) goto La6
            goto La7
        La6:
            r11 = r8
        La7:
            r1.mEllipsisEnd = r11
            return
        Laa:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Directions cannot be null"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextLine.set(android.text.TextPaint, java.lang.CharSequence, int, int, int, android.text.Layout$Directions, boolean, android.text.Layout$TabStops, int, int, boolean):void");
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
            float abs = Math.abs(textLine.measure(i2, false, null, null, null));
            int countStretchableSpaces = textLine.countStretchableSpaces(0, i2);
            if (countStretchableSpaces == 0) {
                return;
            }
            textLine.mAddedWordSpacingInPx = (f - abs) / countStretchableSpaces;
            textLine.mAddedLetterSpacingInPx = 0.0f;
        } else {
            textLine = this;
            LineInfo lineInfo = new LineInfo();
            float abs2 = Math.abs(textLine.measure(i2, false, null, null, lineInfo));
            if (lineInfo.getClusterCount() < 2) {
                return;
            }
            float f2 = (f - abs2) / (r9 - 1);
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
            int min = Math.min(this.mDirections.getRunLength(i7) + runStart, this.mLen);
            boolean isRunRtl = this.mDirections.isRunRtl(i7);
            int calculateRunFlag = calculateRunFlag(i7, runCount, this.mDir);
            float f3 = f2;
            for (int i8 = this.mHasTabs ? runStart : min; i8 <= min; i8++) {
                if (i8 == min || charAt(i8) == '\t') {
                    float f4 = f + f3;
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
                    f3 += drawRun(canvas2, runStart, i8, isRunRtl, f4, i4, i5, i6, z, calculateRunFlag);
                    if (i8 != min) {
                        int i9 = this.mDir;
                        f3 = i9 * nextTab(f3 * i9);
                    }
                    runStart = i8 + 1;
                }
            }
            i7++;
            f2 = f3;
        }
    }

    public float metrics(Paint.FontMetricsInt fontMetricsInt, RectF rectF, boolean z, LineInfo lineInfo) {
        float max;
        if (z) {
            if (rectF == null) {
                if (this.mTmpRectForMeasure == null) {
                    this.mTmpRectForMeasure = new RectF();
                }
                rectF = this.mTmpRectForMeasure;
            }
            RectF rectF2 = rectF;
            rectF2.setEmpty();
            float measure = measure(this.mLen, false, fontMetricsInt, rectF2, lineInfo);
            if (measure >= 0.0f) {
                max = Math.max(rectF2.right, measure) - Math.min(0.0f, rectF2.left);
            } else {
                max = Math.max(rectF2.right, 0.0f) - Math.min(measure, rectF2.left);
            }
            return Math.abs(measure) > max ? measure : Math.signum(measure) * max;
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
            int min = Math.min(this.mDirections.getRunLength(i) + runStart, this.mLen);
            boolean isRunRtl = this.mDirections.isRunRtl(i);
            int calculateRunFlag = calculateRunFlag(i, runCount, this.mDir);
            int i2 = this.mHasTabs ? runStart : min;
            float f2 = f;
            while (i2 <= min) {
                if (i2 == min || charAt(i2) == '\t') {
                    f2 += shapeRun(glyphsConsumer, runStart, i2, isRunRtl, 0.0f + f2, (i == runCount + (-1) && i2 == this.mLen) ? false : true, calculateRunFlag);
                    if (i2 != min) {
                        int i3 = this.mDir;
                        f2 = i3 * nextTab(f2 * i3);
                    }
                    runStart = i2 + 1;
                }
                i2++;
            }
            i++;
            f = f2;
        }
    }

    public float measure(int i, boolean z, Paint.FontMetricsInt fontMetricsInt, RectF rectF, LineInfo lineInfo) {
        boolean z2;
        int i2;
        float measureRun;
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
            int min = Math.min(textLine.mDirections.getRunLength(i5) + runStart, textLine.mLen);
            boolean isRunRtl = textLine.mDirections.isRunRtl(i5);
            int calculateRunFlag = calculateRunFlag(i5, runCount, textLine.mDir);
            float f2 = f;
            int i6 = runStart;
            int i7 = textLine.mHasTabs ? runStart : min;
            while (i7 <= min) {
                if (i7 == min || textLine.charAt(i7) == '\t') {
                    boolean z4 = (i4 < i6 || i4 >= i7) ? z3 : true;
                    boolean z5 = (textLine.mDir == -1 ? true : z3) == isRunRtl ? true : z3;
                    if (z4 && z5) {
                        measureRun = textLine.measureRun(i6, i3, i7, isRunRtl, fontMetricsInt, rectF, null, 0, f2, lineInfo2, calculateRunFlag);
                    } else {
                        int i8 = min;
                        z2 = isRunRtl;
                        int i9 = i7;
                        i2 = i8;
                        float measureRun2 = measureRun(i6, i9, i7, z2, fontMetricsInt, rectF, null, 0, f2, lineInfo, calculateRunFlag);
                        if (!z5) {
                            measureRun2 = -measureRun2;
                        }
                        f2 += measureRun2;
                        if (z4) {
                            measureRun = measureRun(i6, i, i9, z2, null, null, null, 0, f2, lineInfo, calculateRunFlag);
                        } else {
                            textLine = this;
                            i7 = i9;
                            i3 = i;
                            if (i7 != i2) {
                                if (i3 == i7) {
                                    return f2;
                                }
                                int i10 = textLine.mDir;
                                f2 = i10 * textLine.nextTab(f2 * i10);
                                if (i4 == i7) {
                                    return f2;
                                }
                            }
                            i6 = i7 + 1;
                        }
                    }
                    return f2 + measureRun;
                }
                i2 = min;
                z2 = isRunRtl;
                i7++;
                lineInfo2 = lineInfo;
                isRunRtl = z2;
                min = i2;
                z3 = false;
            }
            i5++;
            lineInfo2 = lineInfo;
            f = f2;
            z3 = false;
        }
        return f;
    }

    public void measureAllBounds(float[] fArr, float[] fArr2) {
        int i;
        float nextTab;
        float f;
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
        float f2 = 0.0f;
        int i3 = 0;
        while (i3 < runCount) {
            int runStart = this.mDirections.getRunStart(i3);
            if (runStart > this.mLen) {
                return;
            }
            int min = Math.min(this.mDirections.getRunLength(i3) + runStart, this.mLen);
            boolean isRunRtl = this.mDirections.isRunRtl(i3);
            int calculateRunFlag = calculateRunFlag(i3, runCount, this.mDir);
            float f3 = f2;
            int i4 = runStart;
            int i5 = this.mHasTabs ? runStart : min;
            while (i5 <= min) {
                if (i5 == min || charAt(i5) == '\t') {
                    boolean z = (this.mDir == -1) == isRunRtl;
                    i = min;
                    float measureRun = measureRun(i4, i5, i5, isRunRtl, null, null, fArr3, i4, 0.0f, null, calculateRunFlag);
                    if (!z) {
                        measureRun = -measureRun;
                    }
                    float f4 = f3 + measureRun;
                    if (!z) {
                        f3 = f4;
                    }
                    while (i4 < i5 && i4 < this.mLen) {
                        if (isRunRtl) {
                            int i6 = i4 * 2;
                            fArr[i6 + 1] = f3;
                            f3 -= fArr3[i4];
                            fArr[i6] = f3;
                        } else {
                            int i7 = i4 * 2;
                            fArr[i7] = f3;
                            f3 += fArr3[i4];
                            fArr[i7 + 1] = f3;
                        }
                        i4++;
                    }
                    if (i5 != i) {
                        if (isRunRtl) {
                            int i8 = this.mDir;
                            f = i8 * nextTab(i8 * f4);
                            nextTab = f4;
                            f4 = f;
                        } else {
                            int i9 = this.mDir;
                            nextTab = i9 * nextTab(i9 * f4);
                            f = nextTab;
                        }
                        int i10 = i5 * 2;
                        fArr[i10] = f4;
                        fArr[i10 + 1] = nextTab;
                        fArr3[i5] = nextTab - f4;
                        f3 = f;
                    } else {
                        f3 = f4;
                    }
                    i4 = i5 + 1;
                } else {
                    i = min;
                }
                i5++;
                min = i;
            }
            i3++;
            f2 = f3;
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
            int min = Math.min(this.mDirections.getRunLength(i3) + runStart, this.mLen);
            boolean isRunRtl = this.mDirections.isRunRtl(i3);
            int calculateRunFlag = calculateRunFlag(i3, runCount, this.mDir);
            float f3 = f2;
            int i4 = this.mHasTabs ? runStart : min;
            while (i4 <= min) {
                if (i4 == min || charAt(i4) == '\t') {
                    boolean z3 = (this.mDir == -1 ? z2 : false) == isRunRtl ? z2 : false;
                    float f4 = fArr[runStart];
                    int i5 = i3;
                    int i6 = runStart;
                    i = i5;
                    i2 = min;
                    z = isRunRtl;
                    float measureRun = measureRun(i6, i4, i4, z, fontMetricsInt, null, fArr, i6, 0.0f, null, calculateRunFlag);
                    if (!z3) {
                        measureRun = -measureRun;
                    }
                    float f5 = f3 + measureRun;
                    if (!z3) {
                        f3 = f5;
                    }
                    int min2 = Math.min(i4, this.mLen);
                    for (int i7 = i6; i7 <= min2; i7++) {
                        if (i7 < min2) {
                            f = z ? -fArr[i7] : fArr[i7];
                        } else {
                            f = 0.0f;
                        }
                        if (i7 == i6 && zArr[i7]) {
                            fArr[i7] = f4;
                        } else if (i7 != min2 || zArr[i7]) {
                            fArr[i7] = f3;
                        }
                        f3 += f;
                    }
                    if (i4 != i2) {
                        if (!zArr[i4]) {
                            fArr[i4] = f5;
                        }
                        int i8 = this.mDir;
                        f5 = i8 * nextTab(f5 * i8);
                        int i9 = i4 + 1;
                        if (zArr[i9]) {
                            fArr[i9] = f5;
                        }
                    }
                    f3 = f5;
                    runStart = i4 + 1;
                } else {
                    i = i3;
                    i2 = min;
                    z = isRunRtl;
                }
                i4++;
                isRunRtl = z;
                min = i2;
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

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0119, code lost:
    
        if (r4 != (-1)) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x011b, code lost:
    
        if (r0 == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0121, code lost:
    
        return r20.mLen + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0122, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0123, code lost:
    
        if (r4 > r8) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0125, code lost:
    
        if (r0 == false) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0127, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0128, code lost:
    
        return r17;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0117 A[ADDED_TO_REGION, EDGE_INSN: B:56:0x0117->B:46:0x0117 BREAK  A[LOOP:0: B:9:0x00c8->B:32:0x010e], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00cd  */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1 */
    /* JADX WARN: Type inference failed for: r17v3 */
    /* JADX WARN: Type inference failed for: r17v4 */
    /* JADX WARN: Type inference failed for: r17v5, types: [int] */
    /* JADX WARN: Type inference failed for: r17v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    int getOffsetToLeftRightOf(int r21, boolean r22) {
        /*
            Method dump skipped, instructions count: 298
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextLine.getOffsetToLeftRightOf(int, boolean):int");
    }

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
                        int nextSpanTransition = this.mSpanned.nextSpanTransition(this.mStart + i2, i8, MetricAffectingSpan.class);
                        i6 = this.mStart;
                        i3 = nextSpanTransition - i6;
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
                return offsetBefore - i5;
            }
        }
        if (z2) {
            offsetBefore = TextUtils.getOffsetAfter(this.mText, i4 + this.mStart);
            i5 = this.mStart;
        } else {
            offsetBefore = TextUtils.getOffsetBefore(this.mText, i4 + this.mStart);
            i5 = this.mStart;
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
        boolean isAntiAlias = textPaint.isAntiAlias();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(i);
        canvas.drawRect(f3, f6, f4, f2 + f6, textPaint);
        textPaint.setStyle(style);
        textPaint.setColor(color);
        textPaint.setAntiAlias(isAntiAlias);
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
        float f6 = 0.0f;
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
            f6 = runAdvance;
        } else {
            i11 = size;
            fontMetricsInt2 = fontMetricsInt3;
        }
        int i13 = (this.mStart + i2) - 1;
        if (i13 >= 0 && TextUtils.semNeedMoreWidth(this.mText.charAt(i13))) {
            f6 += textPaint.measureText(" ");
        }
        float f7 = f6;
        if (z) {
            f2 = f5 - f7;
        } else {
            f2 = f5;
            f5 += f7;
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
            float f8 = f2;
            if (i11 != 0) {
                int i15 = 0;
                while (i15 < i11) {
                    DecorationInfo decorationInfo = arrayList.get(i15);
                    int max = Math.max(decorationInfo.start, i14);
                    int min = Math.min(decorationInfo.end, i8);
                    int i16 = i15;
                    float f9 = f5;
                    float runAdvance2 = getRunAdvance(textPaint, i14, i2, i3, i4, z, max, null, 0, null, null);
                    float runAdvance3 = getRunAdvance(textPaint, i, i2, i3, i4, z, min, null, 0, null, null);
                    if (z) {
                        f3 = f9 - runAdvance3;
                        f4 = f9 - runAdvance2;
                    } else {
                        f3 = f8 + runAdvance2;
                        f4 = f8 + runAdvance3;
                    }
                    float f10 = f3;
                    float f11 = f4;
                    if (decorationInfo.underlineColor != 0) {
                        i12 = i6;
                        drawStroke(textPaint, canvas, decorationInfo.underlineColor, textPaint.getUnderlinePosition(), decorationInfo.underlineThickness, f10, f11, i12);
                    } else {
                        i12 = i6;
                    }
                    if (decorationInfo.isUnderlineText) {
                        drawStroke(textPaint, canvas, textPaint.getColor(), textPaint.getUnderlinePosition(), Math.max(textPaint.getUnderlineThickness(), 1.0f), f10, f11, i12);
                    }
                    if (decorationInfo.isStrikeThruText) {
                        drawStroke(textPaint, canvas, textPaint.getColor(), textPaint.getStrikeThruPosition(), Math.max(textPaint.getStrikeThruThickness(), 1.0f), f10, f11, i12);
                    }
                    i15 = i16 + 1;
                    i14 = i;
                    f5 = f9;
                }
            }
        }
        return z ? -f7 : f7;
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

    /* JADX WARN: Removed duplicated region for block: B:91:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x028c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float handleRun(int r31, int r32, int r33, boolean r34, android.graphics.Canvas r35, android.text.TextShaper.GlyphsConsumer r36, float r37, int r38, int r39, int r40, android.graphics.Paint.FontMetricsInt r41, android.graphics.RectF r42, boolean r43, float[] r44, int r45, android.text.TextLine.LineInfo r46, int r47) {
        /*
            Method dump skipped, instructions count: 830
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextLine.handleRun(int, int, int, boolean, android.graphics.Canvas, android.text.TextShaper$GlyphsConsumer, float, int, int, int, android.graphics.Paint$FontMetricsInt, android.graphics.RectF, boolean, float[], int, android.text.TextLine$LineInfo, int):float");
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
        PositionedGlyphs shapeTextRun;
        int i5 = i2 - i;
        int i6 = i4 - i3;
        if (this.mCharsValid) {
            shapeTextRun = TextRunShaper.shapeTextRun(this.mChars, i, i5, i3, i6, f, 0.0f, z, textPaint);
        } else {
            CharSequence charSequence = this.mText;
            int i7 = this.mStart;
            shapeTextRun = TextRunShaper.shapeTextRun(charSequence, i7 + i, i5, i7 + i3, i6, f, 0.0f, z, textPaint);
        }
        glyphsConsumer.accept(i, i5, shapeTextRun, textPaint);
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
