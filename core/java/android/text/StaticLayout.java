package android.text;

import android.graphics.Paint;
import android.graphics.text.LineBreakConfig;
import android.graphics.text.LineBreaker;
import android.graphics.text.MeasuredText;
import android.hardware.scontext.SContextConstants;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;
import android.text.style.TabStopSpan;
import android.util.Log;
import android.util.Pools;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.util.Arrays;

/* loaded from: classes3.dex */
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
  private boolean mEllipsized;
  private int mEllipsizedWidth;
  private boolean mFallbackLineSpacing;
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
    private TextUtils.TruncateAt mEllipsize;
    private int mEllipsizedWidth;
    private int mEnd;
    private boolean mFallbackLineSpacing;
    private int mHyphenationFrequency;
    private boolean mIncludePad;
    private int mJustificationMode;
    private int[] mLeftIndents;
    private int mMaxLines;
    private TextPaint mPaint;
    private int[] mRightIndents;
    private float mSpacingAdd;
    private float mSpacingMult;
    private int mStart;
    private CharSequence mText;
    private TextDirectionHeuristic mTextDir;
    private int mWidth;
    private LineBreakConfig mLineBreakConfig = LineBreakConfig.NONE;
    private final Paint.FontMetricsInt mFontMetricsInt = new Paint.FontMetricsInt();

    private Builder() {}

    public static Builder obtain(
        CharSequence source, int start, int end, TextPaint paint, int width) {
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
      return b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void recycle(Builder b) {
      b.mPaint = null;
      b.mText = null;
      b.mLeftIndents = null;
      b.mRightIndents = null;
      sPool.release(b);
    }

    void finish() {
      this.mText = null;
      this.mPaint = null;
      this.mLeftIndents = null;
      this.mRightIndents = null;
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

    public StaticLayout build() {
      StaticLayout result = new StaticLayout(this);
      recycle(this);
      return result;
    }
  }

  @Deprecated
  public StaticLayout(
      CharSequence source,
      TextPaint paint,
      int width,
      Layout.Alignment align,
      float spacingmult,
      float spacingadd,
      boolean includepad) {
    this(source, 0, source.length(), paint, width, align, spacingmult, spacingadd, includepad);
  }

  @Deprecated
  public StaticLayout(
      CharSequence source,
      int bufstart,
      int bufend,
      TextPaint paint,
      int outerwidth,
      Layout.Alignment align,
      float spacingmult,
      float spacingadd,
      boolean includepad) {
    this(
        source,
        bufstart,
        bufend,
        paint,
        outerwidth,
        align,
        spacingmult,
        spacingadd,
        includepad,
        null,
        0);
  }

  @Deprecated
  public StaticLayout(
      CharSequence source,
      int bufstart,
      int bufend,
      TextPaint paint,
      int outerwidth,
      Layout.Alignment align,
      float spacingmult,
      float spacingadd,
      boolean includepad,
      TextUtils.TruncateAt ellipsize,
      int ellipsizedWidth) {
    this(
        source,
        bufstart,
        bufend,
        paint,
        outerwidth,
        align,
        TextDirectionHeuristics.FIRSTSTRONG_LTR,
        spacingmult,
        spacingadd,
        includepad,
        ellipsize,
        ellipsizedWidth,
        Integer.MAX_VALUE);
  }

  /* JADX WARN: Illegal instructions before constructor call */
  @Deprecated
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public StaticLayout(
      CharSequence source,
      int bufstart,
      int bufend,
      TextPaint paint,
      int outerwidth,
      Layout.Alignment align,
      TextDirectionHeuristic textDir,
      float spacingmult,
      float spacingadd,
      boolean includepad,
      TextUtils.TruncateAt ellipsize,
      int ellipsizedWidth,
      int maxLines) {
    super(r1, paint, outerwidth, align, textDir, spacingmult, spacingadd);
    CharSequence ellipsizer;
    if (ellipsize == null) {
      ellipsizer = source;
    } else if (source instanceof Spanned) {
      ellipsizer = new Layout.SpannedEllipsizer(source);
    } else {
      ellipsizer = new Layout.Ellipsizer(source);
    }
    this.mMaxLineHeight = -1;
    this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
    Builder b =
        Builder.obtain(source, bufstart, bufend, paint, outerwidth)
            .setAlignment(align)
            .setTextDirection(textDir)
            .setLineSpacing(spacingadd, spacingmult)
            .setIncludePad(includepad)
            .setEllipsizedWidth(ellipsizedWidth)
            .setEllipsize(ellipsize)
            .setMaxLines(maxLines);
    if (ellipsize == null) {
      this.mColumns = 5;
      this.mEllipsizedWidth = outerwidth;
    } else {
      Layout.Ellipsizer e = (Layout.Ellipsizer) getText();
      e.mLayout = this;
      e.mWidth = ellipsizedWidth;
      e.mMethod = ellipsize;
      this.mEllipsizedWidth = ellipsizedWidth;
      this.mColumns = 7;
    }
    this.mLineDirections =
        (Layout.Directions[]) ArrayUtils.newUnpaddedArray(Layout.Directions.class, 2);
    this.mLines = ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
    this.mMaximumVisibleLineCount = maxLines;
    generate(b, b.mIncludePad, b.mIncludePad);
    Builder.recycle(b);
  }

  StaticLayout(CharSequence text) {
    super(text, null, 0, null, 0.0f, 0.0f);
    this.mMaxLineHeight = -1;
    this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
    this.mColumns = 7;
    this.mLineDirections =
        (Layout.Directions[]) ArrayUtils.newUnpaddedArray(Layout.Directions.class, 2);
    this.mLines = ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
  }

  /* JADX WARN: Illegal instructions before constructor call */
  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r0v23, types: [java.lang.CharSequence] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private StaticLayout(Builder b) {
    super(r3, b.mPaint, b.mWidth, b.mAlignment, b.mTextDir, b.mSpacingMult, b.mSpacingAdd);
    Layout.Ellipsizer ellipsizer;
    if (b.mEllipsize == null) {
      ellipsizer = b.mText;
    } else if (b.mText instanceof Spanned) {
      ellipsizer = new Layout.SpannedEllipsizer(b.mText);
    } else {
      ellipsizer = new Layout.Ellipsizer(b.mText);
    }
    this.mMaxLineHeight = -1;
    this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
    if (b.mEllipsize != null) {
      Layout.Ellipsizer e = (Layout.Ellipsizer) getText();
      e.mLayout = this;
      e.mWidth = b.mEllipsizedWidth;
      e.mMethod = b.mEllipsize;
      this.mEllipsizedWidth = b.mEllipsizedWidth;
      this.mColumns = 7;
    } else {
      this.mColumns = 5;
      this.mEllipsizedWidth = b.mWidth;
    }
    this.mLineDirections =
        (Layout.Directions[]) ArrayUtils.newUnpaddedArray(Layout.Directions.class, 2);
    this.mLines = ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
    this.mMaximumVisibleLineCount = b.mMaxLines;
    this.mLeftIndents = b.mLeftIndents;
    this.mRightIndents = b.mRightIndents;
    setJustificationMode(b.mJustificationMode);
    generate(b, b.mIncludePad, b.mIncludePad);
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

  /* JADX WARN: Code restructure failed: missing block: B:68:0x0352, code lost:

     if (r2 != android.text.TextUtils.TruncateAt.MARQUEE) goto L110;
  */
  /* JADX WARN: Removed duplicated region for block: B:173:0x069b A[LOOP:0: B:25:0x018f->B:173:0x069b, LOOP_END] */
  /* JADX WARN: Removed duplicated region for block: B:174:0x068b A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:192:0x0359  */
  /* JADX WARN: Removed duplicated region for block: B:193:0x02f2  */
  /* JADX WARN: Removed duplicated region for block: B:56:0x02ca  */
  /* JADX WARN: Removed duplicated region for block: B:59:0x0305 A[LOOP:3: B:58:0x0303->B:59:0x0305, LOOP_END] */
  /* JADX WARN: Removed duplicated region for block: B:63:0x0345  */
  /* JADX WARN: Removed duplicated region for block: B:72:0x0360 A[ADDED_TO_REGION] */
  /* JADX WARN: Removed duplicated region for block: B:93:0x03d1  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  void generate(Builder b, boolean includepad, boolean trackpad) {
    int[] indents;
    LineBreaker.ParagraphConstraints constraints;
    Paint.FontMetricsInt fm;
    LineBreaker lineBreaker;
    TextUtils.TruncateAt ellipsize;
    float ellipsizedWidth;
    boolean z;
    Spanned spanned;
    PrecomputedText.ParagraphInfo[] paragraphInfo;
    TextPaint paint;
    int bufEnd;
    int bufStart;
    CharSequence source;
    TextDirectionHeuristic textDir;
    float ellipsizedWidth2;
    Paint.FontMetricsInt fm2;
    TextUtils.TruncateAt ellipsize2;
    StaticLayout staticLayout;
    int fmDescent;
    CharSequence source2;
    TextPaint paint2;
    int bufStart2;
    CharSequence source3;
    TextDirectionHeuristic textDir2;
    StaticLayout staticLayout2;
    int[] chooseHtv;
    int firstWidthLineCount;
    int firstWidth;
    int restWidth;
    LineHeightSpan[] chooseHt;
    float[] variableTabStops;
    float[] variableTabStops2;
    int breakCount;
    LineBreaker.ParagraphConstraints constraints2;
    int lineBreakCapacity;
    int[] breaks;
    float[] lineWidths;
    float[] ascents;
    float[] descents;
    boolean[] hasTabs;
    int[] hyphenEdits;
    int i;
    int remainingLineCount;
    TextUtils.TruncateAt ellipsize3;
    LineBreaker.Result res;
    boolean z2;
    TextUtils.TruncateAt ellipsize4;
    float[] variableTabStops3;
    LineBreaker lineBreaker2;
    int breakCount2;
    int spanStart;
    int paraEnd;
    int breakIndex;
    int fmTop;
    int paraStart;
    int fmDescent2;
    int outerWidth;
    int bufEnd2;
    int breakCount3;
    Spanned spanned2;
    StaticLayout staticLayout3;
    int remainingLineCount2;
    int firstWidthLineCount2;
    TextDirectionHeuristic textDir3;
    PrecomputedText.ParagraphInfo[] paragraphInfo2;
    TextPaint paint3;
    int bufStart3;
    CharSequence source4;
    float ellipsizedWidth3;
    LineBreaker.ParagraphConstraints constraints3;
    LineBreaker.Result res2;
    float[] variableTabStops4;
    int paraEnd2;
    int ascent;
    int descent;
    int fmTop2;
    int fmBottom;
    float elipsizeWidth_span;
    LineBreaker lineBreaker3;
    LeadingMarginSpan[] sp;
    CharSequence source5 = b.mText;
    int bufStart4 = b.mStart;
    int bufEnd3 = b.mEnd;
    TextPaint paint4 = b.mPaint;
    int outerWidth2 = b.mWidth;
    TextDirectionHeuristic textDir4 = b.mTextDir;
    float spacingmult = b.mSpacingMult;
    float spacingadd = b.mSpacingAdd;
    float ellipsizedWidth4 = b.mEllipsizedWidth;
    TextUtils.TruncateAt ellipsize5 = b.mEllipsize;
    boolean addLastLineSpacing = b.mAddLastLineLineSpacing;
    int[] breaks2 = null;
    float[] lineWidths2 = null;
    float[] ascents2 = null;
    float[] descents2 = null;
    boolean[] hasTabs2 = null;
    int[] hyphenEdits2 = null;
    this.mLineCount = 0;
    this.mEllipsized = false;
    this.mMaxLineHeight = this.mMaximumVisibleLineCount < 1 ? 0 : -1;
    this.mFallbackLineSpacing = b.mFallbackLineSpacing;
    int v = 0;
    boolean needMultiply = (spacingmult == 1.0f && spacingadd == 0.0f) ? false : true;
    Paint.FontMetricsInt fm3 = b.mFontMetricsInt;
    int[] indents2 = this.mLeftIndents;
    if (indents2 != null || this.mRightIndents != null) {
      int leftLen = indents2 == null ? 0 : indents2.length;
      int[] iArr = this.mRightIndents;
      int rightLen = iArr == null ? 0 : iArr.length;
      int indentsLen = Math.max(leftLen, rightLen);
      int[] indents3 = new int[indentsLen];
      for (int i2 = 0; i2 < leftLen; i2++) {
        indents3[i2] = this.mLeftIndents[i2];
      }
      int i3 = 0;
      while (i3 < rightLen) {
        indents3[i3] = indents3[i3] + this.mRightIndents[i3];
        i3++;
        leftLen = leftLen;
      }
      indents = indents3;
    } else {
      indents = null;
    }
    LineBreaker lineBreaker4 =
        new LineBreaker.Builder()
            .setBreakStrategy(b.mBreakStrategy)
            .setHyphenationFrequency(getBaseHyphenationFrequency(b.mHyphenationFrequency))
            .setJustificationMode(b.mJustificationMode)
            .setIndents(indents)
            .build();
    LineBreaker.ParagraphConstraints constraints4 = new LineBreaker.ParagraphConstraints();
    PrecomputedText.ParagraphInfo[] paragraphInfo3 = null;
    Spanned spanned3 = source5 instanceof Spanned ? (Spanned) source5 : null;
    if (!(source5 instanceof PrecomputedText)) {
      constraints = constraints4;
      fm = fm3;
      lineBreaker = lineBreaker4;
      ellipsize = ellipsize5;
      ellipsizedWidth = ellipsizedWidth4;
      z = false;
      spanned = spanned3;
    } else {
      PrecomputedText precomputed = (PrecomputedText) source5;
      ellipsizedWidth = ellipsizedWidth4;
      spanned = spanned3;
      constraints = constraints4;
      fm = fm3;
      lineBreaker = lineBreaker4;
      z = false;
      ellipsize = ellipsize5;
      int checkResult =
          precomputed.checkResultUsable(
              bufStart4,
              bufEnd3,
              textDir4,
              paint4,
              b.mBreakStrategy,
              b.mHyphenationFrequency,
              b.mLineBreakConfig);
      switch (checkResult) {
        case 1:
          PrecomputedText.Params newParams =
              new PrecomputedText.Params.Builder(paint4)
                  .setBreakStrategy(b.mBreakStrategy)
                  .setHyphenationFrequency(b.mHyphenationFrequency)
                  .setTextDirection(textDir4)
                  .setLineBreakConfig(b.mLineBreakConfig)
                  .build();
          paragraphInfo3 = PrecomputedText.create(precomputed, newParams).getParagraphInfo();
          break;
        case 2:
          paragraphInfo3 = precomputed.getParagraphInfo();
          break;
      }
    }
    if (paragraphInfo3 != null) {
      paragraphInfo = paragraphInfo3;
    } else {
      PrecomputedText.Params param =
          new PrecomputedText.Params(
              paint4, b.mLineBreakConfig, textDir4, b.mBreakStrategy, b.mHyphenationFrequency);
      paragraphInfo =
          PrecomputedText.createMeasuredParagraphs(source5, param, bufStart4, bufEnd3, z);
    }
    int fmAscent = 0;
    int lineBreakCapacity2 = 0;
    int[] chooseHtv2 = null;
    while (true) {
      if (fmAscent < paragraphInfo.length) {
        int paraStart2 = fmAscent == 0 ? bufStart4 : paragraphInfo[fmAscent - 1].paragraphEnd;
        int paraEnd3 = paragraphInfo[fmAscent].paragraphEnd;
        int firstWidthLineCount3 = 1;
        int firstWidth2 = outerWidth2;
        int restWidth2 = outerWidth2;
        if (spanned == null) {
          paint2 = paint4;
          bufStart2 = bufStart4;
          source3 = source5;
          textDir2 = textDir4;
          staticLayout2 = this;
          chooseHtv = chooseHtv2;
          firstWidthLineCount = 1;
          firstWidth = firstWidth2;
          restWidth = restWidth2;
          chooseHt = null;
        } else {
          LeadingMarginSpan[] sp2 =
              (LeadingMarginSpan[])
                  getParagraphSpans(spanned, paraStart2, paraEnd3, LeadingMarginSpan.class);
          paint2 = paint4;
          int i4 = 0;
          while (true) {
            bufStart2 = bufStart4;
            if (i4 < sp2.length) {
              LeadingMarginSpan lms = sp2[i4];
              TextDirectionHeuristic textDir5 = textDir4;
              CharSequence source6 = source5;
              firstWidth2 -= sp2[i4].getLeadingMargin(true);
              restWidth2 -= sp2[i4].getLeadingMargin(false);
              if (lms instanceof LeadingMarginSpan.LeadingMarginSpan2) {
                LeadingMarginSpan.LeadingMarginSpan2 lms2 =
                    (LeadingMarginSpan.LeadingMarginSpan2) lms;
                firstWidthLineCount3 =
                    Math.max(firstWidthLineCount3, lms2.getLeadingMarginLineCount());
              }
              i4++;
              textDir4 = textDir5;
              bufStart4 = bufStart2;
              source5 = source6;
            } else {
              source3 = source5;
              textDir2 = textDir4;
              LineHeightSpan[] chooseHt2 =
                  (LineHeightSpan[])
                      getParagraphSpans(spanned, paraStart2, paraEnd3, LineHeightSpan.class);
              if (chooseHt2.length == 0) {
                staticLayout2 = this;
                chooseHtv = chooseHtv2;
                firstWidthLineCount = firstWidthLineCount3;
                firstWidth = firstWidth2;
                restWidth = restWidth2;
                chooseHt = null;
              } else {
                if (chooseHtv2 == null || chooseHtv2.length < chooseHt2.length) {
                  chooseHtv2 = ArrayUtils.newUnpaddedIntArray(chooseHt2.length);
                }
                int i5 = 0;
                while (i5 < chooseHt2.length) {
                  int o = spanned.getSpanStart(chooseHt2[i5]);
                  if (o < paraStart2) {
                    sp = sp2;
                    chooseHtv2[i5] = getLineTop(getLineForOffset(o));
                  } else {
                    sp = sp2;
                    chooseHtv2[i5] = v;
                  }
                  i5++;
                  sp2 = sp;
                }
                staticLayout2 = this;
                chooseHtv = chooseHtv2;
                firstWidthLineCount = firstWidthLineCount3;
                firstWidth = firstWidth2;
                chooseHt = chooseHt2;
                restWidth = restWidth2;
              }
            }
          }
        }
        float[] variableTabStops5 = null;
        if (spanned == null) {
          variableTabStops = null;
        } else {
          TabStopSpan[] spans =
              (TabStopSpan[]) getParagraphSpans(spanned, paraStart2, paraEnd3, TabStopSpan.class);
          if (spans.length <= 0) {
            variableTabStops = null;
          } else {
            float[] stops = new float[spans.length];
            int i6 = 0;
            while (true) {
              float[] variableTabStops6 = variableTabStops5;
              if (i6 < spans.length) {
                stops[i6] = spans[i6].getTabStop();
                i6++;
                variableTabStops5 = variableTabStops6;
              } else {
                Arrays.sort(stops, 0, stops.length);
                variableTabStops2 = stops;
                MeasuredParagraph measuredPara = paragraphInfo[fmAscent].measured;
                char[] chs = measuredPara.getChars();
                int[] spanEndCache = measuredPara.getSpanEndCache().getRawArray();
                int[] fmCache = measuredPara.getFontMetrics().getRawArray();
                LineBreaker.ParagraphConstraints constraints5 = constraints;
                constraints5.setWidth(restWidth);
                constraints5.setIndent(firstWidth, firstWidthLineCount);
                constraints5.setTabStops(variableTabStops2, TAB_INCREMENT);
                MeasuredText measuredText = measuredPara.getMeasuredText();
                int paraIndex = fmAscent;
                int paraIndex2 = staticLayout2.mLineCount;
                int firstWidthLineCount4 = firstWidthLineCount;
                LineBreaker lineBreaker5 = lineBreaker;
                LineBreaker.Result res3 =
                    lineBreaker5.computeLineBreaks(measuredText, constraints5, paraIndex2);
                breakCount = res3.getLineCount();
                if (lineBreakCapacity2 < breakCount) {
                  constraints2 = constraints5;
                  lineBreakCapacity = lineBreakCapacity2;
                  breaks = breaks2;
                  lineWidths = lineWidths2;
                  ascents = ascents2;
                  descents = descents2;
                  hasTabs = hasTabs2;
                  hyphenEdits = hyphenEdits2;
                } else {
                  constraints2 = constraints5;
                  int[] breaks3 = new int[breakCount];
                  float[] lineWidths3 = new float[breakCount];
                  float[] lineWidths4 = new float[breakCount];
                  float[] ascents3 = new float[breakCount];
                  boolean[] hasTabs3 = new boolean[breakCount];
                  lineBreakCapacity = breakCount;
                  hyphenEdits = new int[breakCount];
                  breaks = breaks3;
                  lineWidths = lineWidths3;
                  ascents = lineWidths4;
                  descents = ascents3;
                  hasTabs = hasTabs3;
                }
                i = 0;
                while (i < breakCount) {
                  breaks[i] = res3.getLineBreakOffset(i);
                  lineWidths[i] = res3.getLineWidth(i);
                  ascents[i] = res3.getLineAscent(i);
                  descents[i] = res3.getLineDescent(i);
                  hasTabs[i] = res3.hasLineTab(i);
                  hyphenEdits[i] =
                      packHyphenEdit(res3.getStartLineHyphenEdit(i), res3.getEndLineHyphenEdit(i));
                  i++;
                  paragraphInfo = paragraphInfo;
                }
                PrecomputedText.ParagraphInfo[] paragraphInfo4 = paragraphInfo;
                int i7 = staticLayout2.mMaximumVisibleLineCount;
                remainingLineCount = i7 - staticLayout2.mLineCount;
                ellipsize3 = ellipsize;
                if (ellipsize3 == null) {
                  if (ellipsize3 != TextUtils.TruncateAt.END) {
                    res = res3;
                    if (staticLayout2.mMaximumVisibleLineCount == 1) {}
                  } else {
                    res = res3;
                  }
                  z2 = true;
                  boolean ellipsisMayBeApplied = z2;
                  if (remainingLineCount > 0
                      || remainingLineCount >= breakCount
                      || !ellipsisMayBeApplied) {
                    ellipsize4 = ellipsize3;
                    variableTabStops3 = variableTabStops2;
                    lineBreaker2 = lineBreaker5;
                    breakCount2 = breakCount;
                  } else {
                    float width = 0.0f;
                    boolean hasTab = false;
                    ellipsize4 = ellipsize3;
                    int i8 = remainingLineCount - 1;
                    while (i8 < breakCount) {
                      float[] variableTabStops7 = variableTabStops2;
                      if (i8 == breakCount - 1) {
                        width += lineWidths[i8];
                        lineBreaker3 = lineBreaker5;
                      } else {
                        int j = i8 == 0 ? 0 : breaks[i8 - 1];
                        while (true) {
                          lineBreaker3 = lineBreaker5;
                          if (j < breaks[i8]) {
                            width += measuredPara.getCharWidthAt(j);
                            j++;
                            lineBreaker5 = lineBreaker3;
                          }
                        }
                      }
                      hasTab |= hasTabs[i8];
                      i8++;
                      variableTabStops2 = variableTabStops7;
                      lineBreaker5 = lineBreaker3;
                    }
                    variableTabStops3 = variableTabStops2;
                    lineBreaker2 = lineBreaker5;
                    int i9 = remainingLineCount - 1;
                    breaks[i9] = breaks[breakCount - 1];
                    lineWidths[remainingLineCount - 1] = width;
                    hasTabs[remainingLineCount - 1] = hasTab;
                    breakCount2 = remainingLineCount;
                  }
                  int spanEnd = paraStart2;
                  int fmTop3 = 0;
                  int fmBottom2 = 0;
                  int fmAscent2 = 0;
                  int fmDescent3 = 0;
                  int fmCacheIndex = 0;
                  int spanEndCacheIndex = 0;
                  int breakIndex2 = 0;
                  int remainingLineCount3 = remainingLineCount;
                  spanStart = paraStart2;
                  while (spanStart < paraEnd3) {
                    int spanEndCacheIndex2 = spanEndCacheIndex + 1;
                    int paraEnd4 = paraEnd3;
                    int breakIndex3 = spanEndCache[spanEndCacheIndex];
                    int i10 = 0;
                    int here = spanEnd;
                    int here2 = fmCache[(fmCacheIndex * 4) + 0];
                    MeasuredParagraph measuredPara2 = measuredPara;
                    Paint.FontMetricsInt fm4 = fm;
                    fm4.top = here2;
                    boolean z3 = true;
                    fm4.bottom = fmCache[(fmCacheIndex * 4) + 1];
                    fm4.ascent = fmCache[(fmCacheIndex * 4) + 2];
                    fm4.descent = fmCache[(fmCacheIndex * 4) + 3];
                    int fmCacheIndex2 = fmCacheIndex + 1;
                    if (fm4.top < fmTop3) {
                      fmTop3 = fm4.top;
                    }
                    if (fm4.ascent < fmAscent2) {
                      fmAscent2 = fm4.ascent;
                    }
                    if (fm4.descent > fmDescent3) {
                      fmDescent3 = fm4.descent;
                    }
                    if (fm4.bottom <= fmBottom2) {
                      breakIndex = breakIndex2;
                    } else {
                      fmBottom2 = fm4.bottom;
                      breakIndex = breakIndex2;
                    }
                    while (true) {
                      if (breakIndex < breakCount2) {
                        fmTop = fmTop3;
                        int fmTop4 = paraStart2 + breaks[breakIndex];
                        if (fmTop4 < spanStart) {
                          breakIndex++;
                          fmTop3 = fmTop;
                        }
                      } else {
                        fmTop = fmTop3;
                      }
                    }
                    int v2 = v;
                    fmTop3 = fmTop;
                    int here3 = here;
                    int i11 = fmDescent3;
                    int fmAscent3 = fmAscent2;
                    int fmDescent4 = i11;
                    while (true) {
                      if (breakIndex < breakCount2) {
                        Paint.FontMetricsInt fm5 = fm4;
                        if (paraStart2 + breaks[breakIndex] > breakIndex3) {
                          paraStart = paraStart2;
                          fmDescent2 = fmDescent4;
                          outerWidth = outerWidth2;
                          bufEnd2 = bufEnd3;
                          breakCount3 = breakCount2;
                          spanned2 = spanned;
                          staticLayout3 = staticLayout2;
                          remainingLineCount2 = remainingLineCount3;
                          firstWidthLineCount2 = firstWidthLineCount4;
                          textDir3 = textDir2;
                          paragraphInfo2 = paragraphInfo4;
                          paint3 = paint2;
                          bufStart3 = bufStart2;
                          source4 = source3;
                          ellipsizedWidth3 = ellipsizedWidth;
                          constraints3 = constraints2;
                          res2 = res;
                          variableTabStops4 = variableTabStops3;
                          paraEnd2 = paraEnd4;
                          fm4 = fm5;
                        } else {
                          int endPos = breaks[breakIndex] + paraStart2;
                          boolean moreChars = endPos < bufEnd3 ? z3 : i10;
                          if (staticLayout2.mFallbackLineSpacing) {
                            ascent = Math.min(fmAscent3, Math.round(ascents[breakIndex]));
                          } else {
                            ascent = fmAscent3;
                          }
                          int spanEnd2 = breakIndex3;
                          if (staticLayout2.mFallbackLineSpacing) {
                            descent = Math.max(fmDescent4, Math.round(descents[breakIndex]));
                          } else {
                            descent = fmDescent4;
                          }
                          int paraStart3 = paraStart2;
                          if (!staticLayout2.mFallbackLineSpacing) {
                            fmTop2 = fmTop3;
                            fmBottom = fmBottom2;
                          } else {
                            if (ascent < fmTop3) {
                              fmTop3 = ascent;
                            }
                            if (descent <= fmBottom2) {
                              fmTop2 = fmTop3;
                              fmBottom = fmBottom2;
                            } else {
                              int fmBottom3 = descent;
                              fmTop2 = fmTop3;
                              fmBottom = fmBottom3;
                            }
                          }
                          float elipsizeWidth_span2 = ellipsizedWidth;
                          if (breakIndex == 0 && firstWidth != outerWidth2) {
                            elipsizeWidth_span2 = ellipsizedWidth - (outerWidth2 - firstWidth);
                          } else if (breakIndex != 0 && restWidth != outerWidth2) {
                            elipsizeWidth_span2 = ellipsizedWidth - (outerWidth2 - restWidth);
                          }
                          if (elipsizeWidth_span2 >= 0.0f) {
                            elipsizeWidth_span = elipsizeWidth_span2;
                          } else {
                            elipsizeWidth_span = ellipsizedWidth;
                          }
                          Spanned spanned4 = spanned;
                          float ellipsizedWidth5 = ellipsizedWidth;
                          TextDirectionHeuristic textDir6 = textDir2;
                          int breakIndex4 = breakIndex;
                          LineBreaker.ParagraphConstraints constraints6 = constraints2;
                          TextUtils.TruncateAt ellipsize6 = ellipsize4;
                          int paraEnd5 = paraEnd4;
                          LineBreaker.Result res4 = res;
                          int paraIndex3 = paraIndex;
                          int fmDescent5 = fmTop2;
                          int spanStart2 = spanStart;
                          int remainingLineCount4 = remainingLineCount3;
                          PrecomputedText.ParagraphInfo[] paragraphInfo5 = paragraphInfo4;
                          int spanStart3 = fmBottom;
                          int outerWidth3 = outerWidth2;
                          int outerWidth4 = v2;
                          TextPaint paint5 = paint2;
                          float[] variableTabStops8 = variableTabStops3;
                          int bufEnd4 = bufEnd3;
                          int bufStart5 = bufStart2;
                          int bufStart6 = restWidth;
                          CharSequence source7 = source3;
                          int firstWidth3 = firstWidth;
                          int breakCount4 = breakCount2;
                          boolean z4 = z3;
                          int fmDescent6 = i10;
                          int firstWidthLineCount5 = firstWidthLineCount4;
                          v2 =
                              out(
                                  source3,
                                  here3,
                                  endPos,
                                  ascent,
                                  descent,
                                  fmDescent5,
                                  spanStart3,
                                  outerWidth4,
                                  spacingmult,
                                  spacingadd,
                                  chooseHt,
                                  chooseHtv,
                                  fm5,
                                  hasTabs[breakIndex],
                                  hyphenEdits[breakIndex],
                                  needMultiply,
                                  measuredPara2,
                                  bufEnd4,
                                  includepad,
                                  trackpad,
                                  addLastLineSpacing,
                                  chs,
                                  paraStart3,
                                  ellipsize6,
                                  elipsizeWidth_span,
                                  lineWidths[breakIndex],
                                  paint5,
                                  moreChars);
                          if (endPos < spanEnd2) {
                            fm4 = fm5;
                            fmTop3 = fm4.top;
                            fmBottom2 = fm4.bottom;
                            int fmAscent4 = fm4.ascent;
                            fmDescent4 = fm4.descent;
                            fmAscent3 = fmAscent4;
                          } else {
                            fm4 = fm5;
                            fmAscent3 = fmDescent6;
                            fmBottom2 = fmDescent6;
                            fmDescent4 = fmDescent6;
                            fmTop3 = fmDescent6;
                          }
                          here3 = endPos;
                          int breakIndex5 = breakIndex4 + 1;
                          if (this.mLineCount >= this.mMaximumVisibleLineCount
                              && this.mEllipsized) {
                            return;
                          }
                          staticLayout2 = this;
                          variableTabStops3 = variableTabStops8;
                          restWidth = bufStart6;
                          firstWidth = firstWidth3;
                          ellipsize4 = ellipsize6;
                          paraIndex = paraIndex3;
                          paraStart2 = paraStart3;
                          res = res4;
                          ellipsizedWidth = ellipsizedWidth5;
                          spanned = spanned4;
                          constraints2 = constraints6;
                          paraEnd4 = paraEnd5;
                          textDir2 = textDir6;
                          remainingLineCount3 = remainingLineCount4;
                          spanStart = spanStart2;
                          paragraphInfo4 = paragraphInfo5;
                          outerWidth2 = outerWidth3;
                          paint2 = paint5;
                          bufEnd3 = bufEnd4;
                          bufStart2 = bufStart5;
                          source3 = source7;
                          firstWidthLineCount4 = firstWidthLineCount5;
                          breakCount2 = breakCount4;
                          i10 = fmDescent6;
                          z3 = z4;
                          breakIndex3 = spanEnd2;
                          breakIndex = breakIndex5;
                        }
                      } else {
                        paraStart = paraStart2;
                        fmDescent2 = fmDescent4;
                        outerWidth = outerWidth2;
                        bufEnd2 = bufEnd3;
                        breakCount3 = breakCount2;
                        spanned2 = spanned;
                        staticLayout3 = staticLayout2;
                        remainingLineCount2 = remainingLineCount3;
                        firstWidthLineCount2 = firstWidthLineCount4;
                        textDir3 = textDir2;
                        paragraphInfo2 = paragraphInfo4;
                        paint3 = paint2;
                        bufStart3 = bufStart2;
                        source4 = source3;
                        ellipsizedWidth3 = ellipsizedWidth;
                        constraints3 = constraints2;
                        res2 = res;
                        variableTabStops4 = variableTabStops3;
                        paraEnd2 = paraEnd4;
                      }
                    }
                    staticLayout2 = staticLayout3;
                    restWidth = restWidth;
                    firstWidth = firstWidth;
                    breakIndex2 = breakIndex;
                    fmCacheIndex = fmCacheIndex2;
                    ellipsize4 = ellipsize4;
                    spanEnd = here3;
                    paraIndex = paraIndex;
                    fmAscent2 = fmAscent3;
                    spanEndCacheIndex = spanEndCacheIndex2;
                    v = v2;
                    paraStart2 = paraStart;
                    res = res2;
                    ellipsizedWidth = ellipsizedWidth3;
                    spanned = spanned2;
                    constraints2 = constraints3;
                    textDir2 = textDir3;
                    fmDescent3 = fmDescent2;
                    remainingLineCount3 = remainingLineCount2;
                    paragraphInfo4 = paragraphInfo2;
                    outerWidth2 = outerWidth;
                    bufEnd3 = bufEnd2;
                    bufStart2 = bufStart3;
                    source3 = source4;
                    firstWidthLineCount4 = firstWidthLineCount2;
                    breakCount2 = breakCount3;
                    fm = fm4;
                    spanStart = breakIndex3;
                    measuredPara = measuredPara2;
                    variableTabStops3 = variableTabStops4;
                    paraEnd3 = paraEnd2;
                    paint2 = paint3;
                  }
                  paraEnd = paraEnd3;
                  int outerWidth5 = outerWidth2;
                  Spanned spanned5 = spanned;
                  staticLayout = staticLayout2;
                  textDir = textDir2;
                  PrecomputedText.ParagraphInfo[] paragraphInfo6 = paragraphInfo4;
                  paint = paint2;
                  bufStart = bufStart2;
                  source = source3;
                  ellipsizedWidth2 = ellipsizedWidth;
                  LineBreaker.ParagraphConstraints constraints7 = constraints2;
                  fm2 = fm;
                  int paraIndex4 = paraIndex;
                  ellipsize2 = ellipsize4;
                  bufEnd = bufEnd3;
                  if (paraEnd != bufEnd) {
                    fmAscent = paraIndex4 + 1;
                    fm = fm2;
                    bufEnd3 = bufEnd;
                    chooseHtv2 = chooseHtv;
                    lineBreakCapacity2 = lineBreakCapacity;
                    breaks2 = breaks;
                    lineWidths2 = lineWidths;
                    ascents2 = ascents;
                    descents2 = descents;
                    ellipsize = ellipsize2;
                    hasTabs2 = hasTabs;
                    hyphenEdits2 = hyphenEdits;
                    lineBreaker = lineBreaker2;
                    ellipsizedWidth = ellipsizedWidth2;
                    spanned = spanned5;
                    constraints = constraints7;
                    textDir4 = textDir;
                    paragraphInfo = paragraphInfo6;
                    outerWidth2 = outerWidth5;
                    paint4 = paint;
                    bufStart4 = bufStart;
                    source5 = source;
                  } else {
                    fmDescent = v;
                  }
                } else {
                  res = res3;
                }
                z2 = false;
                boolean ellipsisMayBeApplied2 = z2;
                if (remainingLineCount > 0) {}
                ellipsize4 = ellipsize3;
                variableTabStops3 = variableTabStops2;
                lineBreaker2 = lineBreaker5;
                breakCount2 = breakCount;
                int spanEnd3 = paraStart2;
                int fmTop32 = 0;
                int fmBottom22 = 0;
                int fmAscent22 = 0;
                int fmDescent32 = 0;
                int fmCacheIndex3 = 0;
                int spanEndCacheIndex3 = 0;
                int breakIndex22 = 0;
                int remainingLineCount32 = remainingLineCount;
                spanStart = paraStart2;
                while (spanStart < paraEnd3) {}
                paraEnd = paraEnd3;
                int outerWidth52 = outerWidth2;
                Spanned spanned52 = spanned;
                staticLayout = staticLayout2;
                textDir = textDir2;
                PrecomputedText.ParagraphInfo[] paragraphInfo62 = paragraphInfo4;
                paint = paint2;
                bufStart = bufStart2;
                source = source3;
                ellipsizedWidth2 = ellipsizedWidth;
                LineBreaker.ParagraphConstraints constraints72 = constraints2;
                fm2 = fm;
                int paraIndex42 = paraIndex;
                ellipsize2 = ellipsize4;
                bufEnd = bufEnd3;
                if (paraEnd != bufEnd) {}
              }
            }
          }
        }
        variableTabStops2 = variableTabStops;
        MeasuredParagraph measuredPara3 = paragraphInfo[fmAscent].measured;
        char[] chs2 = measuredPara3.getChars();
        int[] spanEndCache2 = measuredPara3.getSpanEndCache().getRawArray();
        int[] fmCache2 = measuredPara3.getFontMetrics().getRawArray();
        LineBreaker.ParagraphConstraints constraints52 = constraints;
        constraints52.setWidth(restWidth);
        constraints52.setIndent(firstWidth, firstWidthLineCount);
        constraints52.setTabStops(variableTabStops2, TAB_INCREMENT);
        MeasuredText measuredText2 = measuredPara3.getMeasuredText();
        int paraIndex5 = fmAscent;
        int paraIndex22 = staticLayout2.mLineCount;
        int firstWidthLineCount42 = firstWidthLineCount;
        LineBreaker lineBreaker52 = lineBreaker;
        LineBreaker.Result res32 =
            lineBreaker52.computeLineBreaks(measuredText2, constraints52, paraIndex22);
        breakCount = res32.getLineCount();
        if (lineBreakCapacity2 < breakCount) {}
        i = 0;
        while (i < breakCount) {}
        PrecomputedText.ParagraphInfo[] paragraphInfo42 = paragraphInfo;
        int i72 = staticLayout2.mMaximumVisibleLineCount;
        remainingLineCount = i72 - staticLayout2.mLineCount;
        ellipsize3 = ellipsize;
        if (ellipsize3 == null) {}
        z2 = false;
        boolean ellipsisMayBeApplied22 = z2;
        if (remainingLineCount > 0) {}
        ellipsize4 = ellipsize3;
        variableTabStops3 = variableTabStops2;
        lineBreaker2 = lineBreaker52;
        breakCount2 = breakCount;
        int spanEnd32 = paraStart2;
        int fmTop322 = 0;
        int fmBottom222 = 0;
        int fmAscent222 = 0;
        int fmDescent322 = 0;
        int fmCacheIndex32 = 0;
        int spanEndCacheIndex32 = 0;
        int breakIndex222 = 0;
        int remainingLineCount322 = remainingLineCount;
        spanStart = paraStart2;
        while (spanStart < paraEnd3) {}
        paraEnd = paraEnd3;
        int outerWidth522 = outerWidth2;
        Spanned spanned522 = spanned;
        staticLayout = staticLayout2;
        textDir = textDir2;
        PrecomputedText.ParagraphInfo[] paragraphInfo622 = paragraphInfo42;
        paint = paint2;
        bufStart = bufStart2;
        source = source3;
        ellipsizedWidth2 = ellipsizedWidth;
        LineBreaker.ParagraphConstraints constraints722 = constraints2;
        fm2 = fm;
        int paraIndex422 = paraIndex5;
        ellipsize2 = ellipsize4;
        bufEnd = bufEnd3;
        if (paraEnd != bufEnd) {}
      } else {
        paint = paint4;
        bufEnd = bufEnd3;
        bufStart = bufStart4;
        source = source5;
        textDir = textDir4;
        ellipsizedWidth2 = ellipsizedWidth;
        fm2 = fm;
        ellipsize2 = ellipsize;
        staticLayout = this;
        fmDescent = v;
      }
    }
    int bufStart7 = bufStart;
    if (bufEnd != bufStart7) {
      source2 = source;
      if (source2.charAt(bufEnd - 1) != '\n') {
        return;
      }
    } else {
      source2 = source;
    }
    if (staticLayout.mLineCount < staticLayout.mMaximumVisibleLineCount) {
      MeasuredParagraph measuredPara4 =
          MeasuredParagraph.buildForBidi(source2, bufEnd, bufEnd, textDir, null);
      TextPaint paint6 = paint;
      paint6.getFontMetricsInt(fm2);
      out(
          source2,
          bufEnd,
          bufEnd,
          fm2.ascent,
          fm2.descent,
          fm2.top,
          fm2.bottom,
          fmDescent,
          spacingmult,
          spacingadd,
          null,
          null,
          fm2,
          false,
          0,
          needMultiply,
          measuredPara4,
          bufEnd,
          includepad,
          trackpad,
          addLastLineSpacing,
          null,
          bufStart7,
          ellipsize2,
          ellipsizedWidth2,
          0.0f,
          paint6,
          false);
    }
  }

  private int out(
      CharSequence text,
      int start,
      int end,
      int above,
      int below,
      int top,
      int bottom,
      int v,
      float spacingmult,
      float spacingadd,
      LineHeightSpan[] chooseHt,
      int[] chooseHtv,
      Paint.FontMetricsInt fm,
      boolean hasTab,
      int hyphenEdit,
      boolean needMultiply,
      MeasuredParagraph measured,
      int bufEnd,
      boolean includePad,
      boolean trackPad,
      boolean addLastLineLineSpacing,
      char[] chs,
      int widthStart,
      TextUtils.TruncateAt ellipsize,
      float ellipsisWidth,
      float textWidth,
      TextPaint paint,
      boolean moreChars) {
    int[] lines;
    int i;
    int j;
    int above2;
    int below2;
    int top2;
    int bottom2;
    int i2;
    int i3;
    int i4;
    int i5;
    int i6;
    int i7;
    boolean lastCharIsNewLine;
    int extra;
    int want;
    int i8;
    int j2;
    int j3 = this.mLineCount;
    int i9 = this.mColumns;
    int off = j3 * i9;
    int i10 = 1;
    int want2 = off + i9 + 1;
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
      Layout.Directions[] grow2 =
          (Layout.Directions[])
              ArrayUtils.newUnpaddedArray(Layout.Directions.class, GrowingArrayUtils.growSize(j3));
      Layout.Directions[] directionsArr = this.mLineDirections;
      System.arraycopy(directionsArr, 0, grow2, 0, directionsArr.length);
      this.mLineDirections = grow2;
    }
    if (chooseHt != null) {
      fm.ascent = above;
      fm.descent = below;
      fm.top = top;
      fm.bottom = bottom;
      int i11 = 0;
      while (i11 < chooseHt.length) {
        if (chooseHt[i11] instanceof LineHeightSpan.WithDensity) {
          want = want2;
          i8 = i10;
          j2 = j3;
          ((LineHeightSpan.WithDensity) chooseHt[i11])
              .chooseHeight(text, start, end, chooseHtv[i11], v, fm, paint);
        } else {
          want = want2;
          i8 = i10;
          j2 = j3;
          chooseHt[i11].chooseHeight(text, start, end, chooseHtv[i11], v, fm);
        }
        i11++;
        i10 = i8;
        want2 = want;
        j3 = j2;
      }
      i = i10;
      j = j3;
      above2 = fm.ascent;
      below2 = fm.descent;
      top2 = fm.top;
      bottom2 = fm.bottom;
    } else {
      i = 1;
      j = j3;
      above2 = above;
      below2 = below;
      top2 = top;
      bottom2 = bottom;
    }
    int i12 = j == 0 ? i : 0;
    int i13 = j + 1;
    int i14 = this.mMaximumVisibleLineCount;
    int i15 = i13 == i14 ? i : 0;
    if (ellipsize == null) {
      i2 = widthStart;
      i3 = bufEnd;
      i4 = 0;
    } else {
      boolean forceEllipsis = (moreChars && this.mLineCount + i == i14) ? i : 0;
      if (((((!(i14 == i && moreChars) && (i12 == 0 || moreChars))
                      || ellipsize == TextUtils.TruncateAt.MARQUEE)
                  && (i12 != 0
                      || ((i15 == 0 && moreChars) || ellipsize != TextUtils.TruncateAt.END)))
              ? 0
              : i)
          != 0) {
        i2 = widthStart;
        i3 = bufEnd;
        calculateEllipsis(
            start,
            end,
            measured,
            widthStart,
            ellipsisWidth,
            ellipsize,
            j,
            textWidth,
            paint,
            forceEllipsis,
            chs);
        i4 = 0;
      } else {
        i2 = widthStart;
        i3 = bufEnd;
        int[] iArr = this.mLines;
        int i16 = this.mColumns;
        i4 = 0;
        iArr[(i16 * j) + 5] = 0;
        iArr[(i16 * j) + 6] = 0;
      }
    }
    if (this.mEllipsized) {
      lastCharIsNewLine = true;
      i6 = i4;
      i7 = start;
    } else {
      if (i2 != i3 && i3 > 0) {
        if (text.charAt(i3 - 1) == '\n') {
          i5 = 1;
          int i17 = i5;
          if (end != i3 && i17 == 0) {
            lastCharIsNewLine = true;
            i6 = i4;
            i7 = start;
          } else {
            i6 = i4;
            i7 = start;
            if (i7 != i3 && i17 != 0) {
              lastCharIsNewLine = true;
            } else {
              lastCharIsNewLine = false;
            }
          }
        }
      }
      i5 = i4;
      int i172 = i5;
      if (end != i3) {}
      i6 = i4;
      i7 = start;
      if (i7 != i3) {}
      lastCharIsNewLine = false;
    }
    if (i12 != 0) {
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
    lines[off + 0] = i7;
    lines[off + 1] = v;
    lines[off + 2] = below2 + extra;
    lines[off + 3] = extra;
    boolean z = this.mEllipsized;
    if (!z && i15 != 0) {
      int maxLineBelow = includePad ? bottom2 : below2;
      this.mMaxLineHeight = v + (maxLineBelow - above2);
    }
    int maxLineBelow2 = below2 - above2;
    int v2 = v + maxLineBelow2 + extra;
    int i18 = this.mColumns;
    lines[off + i18 + i6] = end;
    lines[off + i18 + 1] = v2;
    int i19 = off + 0;
    lines[i19] = lines[i19] | (hasTab ? 536870912 : i6);
    if (!z) {
      lines[off + 4] = hyphenEdit;
    } else if (ellipsize == TextUtils.TruncateAt.START) {
      lines[off + 4] = packHyphenEdit(i6, unpackEndHyphenEdit(hyphenEdit));
    } else if (ellipsize == TextUtils.TruncateAt.END) {
      lines[off + 4] = packHyphenEdit(unpackStartHyphenEdit(hyphenEdit), i6);
    } else {
      lines[off + 4] = packHyphenEdit(i6, i6);
    }
    int i20 = off + 0;
    lines[i20] = lines[i20] | (dir << 30);
    this.mLineDirections[j] = measured.getDirections(i7 - i2, end - i2);
    this.mLineCount++;
    return v2;
  }

  private void calculateEllipsis(
      int lineStart,
      int lineEnd,
      MeasuredParagraph measured,
      int widthStart,
      float avail,
      TextUtils.TruncateAt where,
      int line,
      float textWidth,
      TextPaint paint,
      boolean forceEllipsis,
      char[] chs) {
    float avail2 = avail - getTotalInsets(line);
    if (textWidth <= avail2 && !forceEllipsis) {
      int[] iArr = this.mLines;
      int i = this.mColumns;
      iArr[(i * line) + 5] = 0;
      iArr[(i * line) + 6] = 0;
      return;
    }
    float ellipsisWidth = paint.measureText(TextUtils.getEllipsisString(where));
    int ellipsisStart = 0;
    int ellipsisCount = 0;
    int len = lineEnd - lineStart;
    if (where == TextUtils.TruncateAt.START) {
      if (this.mMaximumVisibleLineCount == 1) {
        float sum = 0.0f;
        int i2 = len;
        while (true) {
          if (i2 <= 0) {
            break;
          }
          float w = measured.getCharWidthAt(((i2 - 1) + lineStart) - widthStart);
          if (w + sum + ellipsisWidth > avail2) {
            while (i2 < len && measured.getCharWidthAt((i2 + lineStart) - widthStart) == 0.0f) {
              i2++;
            }
          } else {
            sum += w;
            i2--;
          }
        }
        ellipsisStart = 0;
        ellipsisCount = i2;
      } else if (Log.isLoggable(TAG, 5)) {
        Log.m102w(TAG, "Start Ellipsis only supported with one line");
      }
    } else if (where == TextUtils.TruncateAt.END
        || where == TextUtils.TruncateAt.MARQUEE
        || where == TextUtils.TruncateAt.END_SMALL) {
      float sum2 = 0.0f;
      int i3 = 0;
      while (i3 < len) {
        float w2 = measured.getCharWidthAt((i3 + lineStart) - widthStart);
        if (w2 + sum2 + ellipsisWidth > avail2) {
          break;
        }
        sum2 += w2;
        i3++;
      }
      ellipsisStart = i3;
      ellipsisCount = len - i3;
      if (forceEllipsis && ellipsisCount == 0 && len > 0) {
        ellipsisStart = len - 1;
        while (ellipsisStart > 0
            && measured.getCharWidthAt((ellipsisStart + lineStart) - widthStart) == 0.0f
            && chs != null
            && chs[(ellipsisStart + lineStart) - widthStart] != '\n') {
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
      Log.m102w(TAG, "Middle Ellipsis only supported with one line");
    }
    this.mEllipsized = true;
    int[] iArr2 = this.mLines;
    int i4 = this.mColumns;
    iArr2[(i4 * line) + 5] = ellipsisStart;
    iArr2[(i4 * line) + 6] = ellipsisCount;
  }

  private float getTotalInsets(int line) {
    int totalIndent = 0;
    int[] iArr = this.mLeftIndents;
    if (iArr != null) {
      totalIndent = iArr[Math.min(line, iArr.length - 1)];
    }
    int[] iArr2 = this.mRightIndents;
    if (iArr2 != null) {
      totalIndent += iArr2[Math.min(line, iArr2.length - 1)];
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
      int[] iArr = this.mLeftIndents;
      if (iArr == null) {
        return 0;
      }
      return iArr[Math.min(line, iArr.length - 1)];
    }
    if (align == Layout.Alignment.ALIGN_RIGHT) {
      int[] iArr2 = this.mRightIndents;
      if (iArr2 == null) {
        return 0;
      }
      return -iArr2[Math.min(line, iArr2.length - 1)];
    }
    if (align == Layout.Alignment.ALIGN_CENTER) {
      int left = 0;
      int[] iArr3 = this.mLeftIndents;
      if (iArr3 != null) {
        left = iArr3[Math.min(line, iArr3.length - 1)];
      }
      int right = 0;
      int[] iArr4 = this.mRightIndents;
      if (iArr4 != null) {
        right = iArr4[Math.min(line, iArr4.length - 1)];
      }
      return (left - right) >> 1;
    }
    throw new AssertionError("unhandled alignment " + align);
  }

  @Override // android.text.Layout
  public int getEllipsisCount(int line) {
    int i = this.mColumns;
    if (i < 7) {
      return 0;
    }
    return this.mLines[(i * line) + 6];
  }

  @Override // android.text.Layout
  public int getEllipsisStart(int line) {
    int i = this.mColumns;
    if (i < 7) {
      return 0;
    }
    return this.mLines[(i * line) + 5];
  }

  @Override // android.text.Layout
  public int getEllipsizedWidth() {
    return this.mEllipsizedWidth;
  }

  @Override // android.text.Layout
  public boolean isFallbackLineSpacingEnabled() {
    return this.mFallbackLineSpacing;
  }

  @Override // android.text.Layout
  public int getHeight(boolean cap) {
    int i;
    if (cap
        && this.mLineCount > this.mMaximumVisibleLineCount
        && this.mMaxLineHeight == -1
        && Log.isLoggable(TAG, 5)) {
      Log.m102w(
          TAG,
          "maxLineHeight should not be -1.  maxLines:"
              + this.mMaximumVisibleLineCount
              + " lineCount:"
              + this.mLineCount);
    }
    return (!cap
            || this.mLineCount <= this.mMaximumVisibleLineCount
            || (i = this.mMaxLineHeight) == -1)
        ? super.getHeight()
        : i;
  }

  static class LineBreaks {
    private static final int INITIAL_SIZE = 16;
    public int[] breaks = new int[16];
    public float[] widths = new float[16];
    public float[] ascents = new float[16];
    public float[] descents = new float[16];
    public int[] flags = new int[16];

    LineBreaks() {}
  }
}
