package android.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.text.LineBreakConfig;
import android.text.Layout;
import android.text.TextUtils;
import android.text.style.ParagraphStyle;
import com.android.text.flags.Flags;

/* loaded from: classes4.dex */
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

    @Override // android.text.Layout
    public boolean getLineContainsTab(int i) {
        return false;
    }

    @Override // android.text.Layout
    public int getLineCount() {
        return 1;
    }

    @Override // android.text.Layout
    public int getParagraphDirection(int i) {
        return 1;
    }

    public static BoringLayout make(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean z) {
        return new BoringLayout(charSequence, textPaint, i, alignment, f, f2, metrics, z);
    }

    public static BoringLayout make(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean z, TextUtils.TruncateAt truncateAt, int i2) {
        return new BoringLayout(charSequence, textPaint, i, alignment, f, f2, metrics, z, truncateAt, i2);
    }

    public static BoringLayout make(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, Metrics metrics, boolean z, TextUtils.TruncateAt truncateAt, int i2, boolean z2) {
        return new BoringLayout(charSequence, textPaint, i, alignment, 1.0f, 0.0f, metrics, z, truncateAt, i2, z2);
    }

    public BoringLayout replaceOrMake(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean z) {
        replaceWith(charSequence, textPaint, i, alignment, f, f2);
        this.mEllipsizedWidth = i;
        this.mEllipsizedStart = 0;
        this.mEllipsizedCount = 0;
        this.mUseFallbackLineSpacing = false;
        init(charSequence, textPaint, alignment, metrics, z, true, false);
        return this;
    }

    public BoringLayout replaceOrMake(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, Metrics metrics, boolean z, TextUtils.TruncateAt truncateAt, int i2, boolean z2) {
        return replaceOrMake(charSequence, textPaint, i, alignment, 1.0f, 0.0f, metrics, z, truncateAt, i2, z2, false, null);
    }

    public BoringLayout replaceOrMake(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean z, TextUtils.TruncateAt truncateAt, int i2, boolean z2, boolean z3, Paint.FontMetrics fontMetrics) {
        boolean z4 = false;
        if (truncateAt == null || truncateAt == TextUtils.TruncateAt.MARQUEE) {
            replaceWith(charSequence, textPaint, i, alignment, 1.0f, 0.0f);
            this.mEllipsizedWidth = i;
            this.mEllipsizedStart = 0;
            this.mEllipsizedCount = 0;
            z4 = true;
        } else {
            replaceWith(TextUtils.ellipsize(charSequence, textPaint, i2, truncateAt, true, this), textPaint, i, alignment, f, f2);
            this.mEllipsizedWidth = i2;
        }
        this.mUseFallbackLineSpacing = z2;
        init(getText(), textPaint, alignment, metrics, z, z4, z2);
        return this;
    }

    public BoringLayout replaceOrMake(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean z, TextUtils.TruncateAt truncateAt, int i2) {
        return replaceOrMake(charSequence, textPaint, i, alignment, metrics, z, truncateAt, i2, false);
    }

    public BoringLayout(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean z) {
        super(charSequence, textPaint, i, alignment, TextDirectionHeuristics.LTR, f, f2, z, false, i, null, 1, 0, 0, null, null, 0, LineBreakConfig.NONE, false, false, null);
        this.mDrawingBounds = new RectF();
        this.mEllipsizedWidth = i;
        this.mEllipsizedStart = 0;
        this.mEllipsizedCount = 0;
        this.mUseFallbackLineSpacing = false;
        init(charSequence, textPaint, alignment, metrics, z, true, false);
    }

    public BoringLayout(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean z, TextUtils.TruncateAt truncateAt, int i2) {
        this(charSequence, textPaint, i, alignment, f, f2, metrics, z, truncateAt, i2, false);
    }

    public BoringLayout(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean z, TextUtils.TruncateAt truncateAt, int i2, boolean z2) {
        this(charSequence, textPaint, i, alignment, TextDirectionHeuristics.LTR, f, f2, z, z2, i2, truncateAt, 1, 0, 0, null, null, 0, LineBreakConfig.NONE, metrics, false, false, null);
    }

    public BoringLayout(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, boolean z, boolean z2, int i2, TextUtils.TruncateAt truncateAt, Metrics metrics, boolean z3, boolean z4, Paint.FontMetrics fontMetrics) {
        this(charSequence, textPaint, i, alignment, TextDirectionHeuristics.LTR, f, f2, z, z2, i2, truncateAt, 1, 0, 0, null, null, 0, LineBreakConfig.NONE, metrics, z3, z4, fontMetrics);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.text.BoringLayout, android.text.Layout, android.text.TextUtils$EllipsizeCallback] */
    BoringLayout(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean z, boolean z2, int i2, TextUtils.TruncateAt truncateAt, int i3, int i4, int i5, int[] iArr, int[] iArr2, int i6, LineBreakConfig lineBreakConfig, Metrics metrics, boolean z3, boolean z4, Paint.FontMetrics fontMetrics) {
        BoringLayout boringLayout;
        ?? layout = new Layout(charSequence, textPaint, i, alignment, textDirectionHeuristic, f, f2, z, z2, i2, truncateAt, i3, i4, i5, iArr, iArr2, i6, lineBreakConfig, z3, z4, fontMetrics);
        layout.mDrawingBounds = new RectF();
        boolean z5 = false;
        if (truncateAt == null || truncateAt == TextUtils.TruncateAt.MARQUEE) {
            layout.mEllipsizedWidth = i;
            layout.mEllipsizedStart = 0;
            layout.mEllipsizedCount = 0;
            z5 = true;
            boringLayout = layout;
        } else {
            layout.replaceWith(TextUtils.ellipsize(charSequence, textPaint, i2, truncateAt, true, layout), textPaint, i, alignment, f, f2);
            BoringLayout boringLayout2 = layout;
            boringLayout2.mEllipsizedWidth = i2;
            boringLayout = boringLayout2;
        }
        boringLayout.mUseFallbackLineSpacing = z2;
        boringLayout.init(boringLayout.getText(), textPaint, alignment, metrics, z, z5, z2);
    }

    void init(CharSequence charSequence, TextPaint textPaint, Layout.Alignment alignment, Metrics metrics, boolean z, boolean z2, boolean z3) {
        int i;
        if ((charSequence instanceof String) && alignment == Layout.Alignment.ALIGN_NORMAL) {
            this.mDirect = charSequence.toString();
        } else {
            this.mDirect = null;
        }
        this.mPaint = textPaint;
        if (z) {
            i = metrics.bottom - metrics.top;
            this.mDesc = metrics.bottom;
        } else {
            i = metrics.descent - metrics.ascent;
            this.mDesc = metrics.descent;
        }
        this.mBottom = i;
        if (z2) {
            this.mMax = metrics.width;
        } else {
            TextLine textLineObtain = TextLine.obtain();
            int length = charSequence.length();
            Layout.Directions directions = Layout.DIRS_ALL_LEFT_TO_RIGHT;
            int i2 = this.mEllipsizedStart;
            textLineObtain.set(textPaint, charSequence, 0, length, 1, directions, false, null, i2, i2 + this.mEllipsizedCount, z3);
            this.mMax = (int) Math.ceil(textLineObtain.metrics(null, null, false, null));
            TextLine.recycle(textLineObtain);
        }
        if (z) {
            this.mTopPadding = metrics.top - metrics.ascent;
            this.mBottomPadding = metrics.bottom - metrics.descent;
        }
        this.mDrawingBounds.set(metrics.mDrawingBounds);
        this.mDrawingBounds.offset(0.0f, this.mBottom - this.mDesc);
    }

    public static Metrics isBoring(CharSequence charSequence, TextPaint textPaint) {
        return isBoring(charSequence, textPaint, TextDirectionHeuristics.FIRSTSTRONG_LTR, null);
    }

    public static Metrics isBoring(CharSequence charSequence, TextPaint textPaint, Metrics metrics) {
        return isBoring(charSequence, textPaint, TextDirectionHeuristics.FIRSTSTRONG_LTR, metrics);
    }

    private static boolean hasAnyInterestingChars(CharSequence charSequence, int i) {
        char[] cArrObtain = TextUtils.obtain(500);
        int i2 = 0;
        while (i2 < i) {
            int i3 = i2 + 500;
            try {
                int iMin = Math.min(i3, i);
                TextUtils.getChars(charSequence, i2, iMin, cArrObtain, 0);
                int i4 = iMin - i2;
                for (int i5 = 0; i5 < i4; i5++) {
                    char c = cArrObtain[i5];
                    if (c == '\n' || c == '\t' || TextUtils.couldAffectRtl(c)) {
                        TextUtils.recycle(cArrObtain);
                        return true;
                    }
                }
                i2 = i3;
            } finally {
                TextUtils.recycle(cArrObtain);
            }
        }
        return false;
    }

    public static Metrics isBoring(CharSequence charSequence, TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, Metrics metrics) {
        return isBoring(charSequence, textPaint, textDirectionHeuristic, false, metrics);
    }

    public static Metrics isBoring(CharSequence charSequence, TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, boolean z, Metrics metrics) {
        return isBoring(charSequence, textPaint, textDirectionHeuristic, z, null, metrics);
    }

    public static Metrics isBoring(CharSequence charSequence, TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, boolean z, Paint.FontMetrics fontMetrics, Metrics metrics) {
        Metrics metrics2;
        int length = charSequence.length();
        if (hasAnyInterestingChars(charSequence, length)) {
            return null;
        }
        if (textDirectionHeuristic != null && textDirectionHeuristic.isRtl(charSequence, 0, length)) {
            return null;
        }
        if ((charSequence instanceof Spanned) && ((Spanned) charSequence).getSpans(0, length, ParagraphStyle.class).length > 0) {
            return null;
        }
        if (metrics == null) {
            metrics2 = new Metrics();
        } else {
            metrics.reset();
            metrics2 = metrics;
        }
        textPaint.set(textPaint);
        if (Flags.fixLineHeightForLocale() && fontMetrics != null) {
            metrics2.set(fontMetrics);
            metrics2.top = Math.min(metrics2.top, metrics2.ascent);
            metrics2.bottom = Math.max(metrics2.bottom, metrics2.descent);
        }
        TextLine textLineObtain = TextLine.obtain();
        textLineObtain.set(textPaint, charSequence, 0, length, 1, Layout.DIRS_ALL_LEFT_TO_RIGHT, false, null, 0, 0, z);
        metrics2.width = (int) Math.ceil(textLineObtain.metrics(metrics2, metrics2.mDrawingBounds, false, null));
        TextLine.recycle(textLineObtain);
        return metrics2;
    }

    @Override // android.text.Layout
    public int getHeight() {
        return this.mBottom;
    }

    @Override // android.text.Layout
    public int getLineTop(int i) {
        if (i == 0) {
            return 0;
        }
        return this.mBottom;
    }

    @Override // android.text.Layout
    public int getLineDescent(int i) {
        return this.mDesc;
    }

    @Override // android.text.Layout
    public int getLineStart(int i) {
        if (i == 0) {
            return 0;
        }
        return getText().length();
    }

    @Override // android.text.Layout
    public float getLineMax(int i) {
        if (getUseBoundsForWidth()) {
            return super.getLineMax(i);
        }
        return this.mMax;
    }

    @Override // android.text.Layout
    public float getLineWidth(int i) {
        if (getUseBoundsForWidth()) {
            return super.getLineWidth(i);
        }
        if (i == 0) {
            return this.mMax;
        }
        return 0.0f;
    }

    @Override // android.text.Layout
    public final Layout.Directions getLineDirections(int i) {
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
    public int getEllipsisCount(int i) {
        return this.mEllipsizedCount;
    }

    @Override // android.text.Layout
    public int getEllipsisStart(int i) {
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x0024  */
    @Override // android.text.Layout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void draw(Canvas canvas, Path path, Paint paint, int i) {
        float f;
        if (this.mDirect != null && path == null) {
            if (getUseBoundsForWidth() && getShiftDrawingOffsetForStartOverhang()) {
                RectF rectFComputeDrawingBoundingBox = computeDrawingBoundingBox();
                if (rectFComputeDrawingBoundingBox.left < 0.0f) {
                    f = -rectFComputeDrawingBoundingBox.left;
                    canvas.translate(f, 0.0f);
                }
            } else {
                f = 0.0f;
            }
            canvas.drawText(this.mDirect, 0.0f, this.mBottom - this.mDesc, this.mPaint);
            if (f != 0.0f) {
                canvas.translate(-f, 0.0f);
                return;
            }
            return;
        }
        super.draw(canvas, path, paint, i);
    }

    @Override // android.text.TextUtils.EllipsizeCallback
    public void ellipsized(int i, int i2) {
        this.mEllipsizedStart = i;
        this.mEllipsizedCount = i2 - i;
    }

    public static class Metrics extends Paint.FontMetricsInt {
        private final RectF mDrawingBounds = new RectF();
        public int width;

        public RectF getDrawingBoundingBox() {
            return this.mDrawingBounds;
        }

        @Override // android.graphics.Paint.FontMetricsInt
        public String toString() {
            return super.toString() + " width=" + this.width + ", drawingBounds = " + this.mDrawingBounds;
        }

        /* JADX INFO: Access modifiers changed from: private */
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
