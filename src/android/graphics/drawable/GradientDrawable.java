package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import com.android.graphics.flags.Flags;
import com.android.internal.R;
import com.samsung.android.content.smartclip.SemSmartClipMetaTagType;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.util.SemViewUtils;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class GradientDrawable extends Drawable {
    public static final int ARC = 4;
    private static final int BUTT = 0;
    private static final float DEFAULT_INNER_RADIUS_RATIO = 3.0f;
    private static final Orientation DEFAULT_ORIENTATION = Orientation.TOP_BOTTOM;
    private static final float DEFAULT_THICKNESS_RATIO = 9.0f;
    public static final int LINE = 2;
    public static final int LINEAR_GRADIENT = 0;
    public static final int OVAL = 1;
    public static final int RADIAL_GRADIENT = 1;
    private static final int RADIUS_TYPE_FRACTION = 1;
    private static final int RADIUS_TYPE_FRACTION_PARENT = 2;
    private static final int RADIUS_TYPE_PIXELS = 0;
    public static final int RECTANGLE = 0;
    public static final int RING = 3;
    private static final int ROUND = 1;
    private static final int SQUARE = 2;
    public static final int SWEEP_GRADIENT = 2;
    public static boolean sWrapNegativeAngleMeasurements = true;
    private int mAlpha;
    private Path mArcOutlinePath;
    private Path mArcPath;
    private BlendModeColorFilter mBlendModeColorFilter;
    private ColorFilter mColorFilter;
    private final Paint mFillPaint;
    private boolean mGradientIsDirty;
    private float mGradientRadius;
    private GradientState mGradientState;
    private boolean mIsSmoothCorner;
    private Paint mLayerPaint;
    private boolean mMutated;
    private Rect mPadding;
    private final Path mPath;
    private boolean mPathIsDirty;
    private final RectF mRect;
    private Path mRingPath;
    private Paint mStrokePaint;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GradientType {
    }

    public enum Orientation {
        TOP_BOTTOM,
        TR_BL,
        RIGHT_LEFT,
        BR_TL,
        BOTTOM_TOP,
        BL_TR,
        LEFT_RIGHT,
        TL_BR
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RadiusType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Shape {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StrokeCap {
    }

    static boolean isOpaque(int i) {
        return ((i >> 24) & 255) == 255;
    }

    public GradientDrawable() {
        this(new GradientState(DEFAULT_ORIENTATION, (int[]) null), (Resources) null);
    }

    public GradientDrawable(Orientation orientation, int[] iArr) {
        this(new GradientState(orientation, iArr), (Resources) null);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        Rect rect2 = this.mPadding;
        if (rect2 != null) {
            rect.set(rect2);
            return true;
        }
        return super.getPadding(rect);
    }

    public void setCornerRadii(float[] fArr) {
        this.mGradientState.setCornerRadii(fArr);
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public float[] getCornerRadii() {
        float[] fArr = this.mGradientState.mRadiusArray;
        if (fArr == null) {
            return null;
        }
        return (float[]) fArr.clone();
    }

    public void setCornerRadius(float f) {
        this.mGradientState.setCornerRadius(f);
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public float getCornerRadius() {
        return this.mGradientState.mRadius;
    }

    public void setStroke(int i, int i2) {
        setStroke(i, i2, 0.0f, 0.0f);
    }

    public void setStroke(int i, ColorStateList colorStateList) {
        setStroke(i, colorStateList, 0.0f, 0.0f);
    }

    public void setStroke(int i, int i2, float f, float f2) {
        this.mGradientState.setStroke(i, ColorStateList.valueOf(i2), f, f2);
        setStrokeInternal(i, i2, f, f2);
    }

    public void setStroke(int i, ColorStateList colorStateList, float f, float f2) {
        this.mGradientState.setStroke(i, colorStateList, f, f2);
        setStrokeInternal(i, colorStateList != null ? colorStateList.getColorForState(getState(), 0) : 0, f, f2);
    }

    private void setStrokeInternal(int i, int i2, float f, float f2) {
        if (this.mStrokePaint == null) {
            Paint paint = new Paint(1);
            this.mStrokePaint = paint;
            paint.setStyle(Paint.Style.STROKE);
        }
        this.mStrokePaint.setStrokeWidth(i);
        this.mStrokePaint.setColor(i2);
        this.mStrokePaint.setPathEffect(f > 0.0f ? new DashPathEffect(new float[]{f, f2}, 0.0f) : null);
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public void setSize(int i, int i2) {
        this.mGradientState.setSize(i, i2);
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public void setShape(int i) {
        this.mRingPath = null;
        this.mPathIsDirty = true;
        this.mGradientState.setShape(i);
        invalidateSelf();
    }

    public int getShape() {
        return this.mGradientState.mShape;
    }

    public void setGradientType(int i) {
        this.mGradientState.setGradientType(i);
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public int getGradientType() {
        return this.mGradientState.mGradient;
    }

    public void setGradientCenter(float f, float f2) {
        this.mGradientState.setGradientCenter(f, f2);
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public float getGradientCenterX() {
        return this.mGradientState.mCenterX;
    }

    public float getGradientCenterY() {
        return this.mGradientState.mCenterY;
    }

    public void setGradientRadius(float f) {
        this.mGradientState.setGradientRadius(f, 0);
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public float getGradientRadius() {
        if (this.mGradientState.mGradient != 1) {
            return 0.0f;
        }
        ensureValidRect();
        return this.mGradientRadius;
    }

    public void setUseLevel(boolean z) {
        this.mGradientState.mUseLevel = z;
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public boolean getUseLevel() {
        return this.mGradientState.mUseLevel;
    }

    private int modulateAlpha(int i) {
        int i2 = this.mAlpha;
        return (i * (i2 + (i2 >> 7))) >> 8;
    }

    public Orientation getOrientation() {
        return this.mGradientState.mOrientation;
    }

    public void setOrientation(Orientation orientation) {
        this.mGradientState.mOrientation = orientation;
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public void setColors(int[] iArr) {
        setColors(iArr, null);
    }

    public void setColors(int[] iArr, float[] fArr) {
        this.mGradientState.setGradientColors(iArr);
        this.mGradientState.mPositions = fArr;
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public int[] getColors() {
        if (this.mGradientState.mGradientColors == null) {
            return null;
        }
        int[] iArr = new int[this.mGradientState.mGradientColors.length];
        for (int i = 0; i < this.mGradientState.mGradientColors.length; i++) {
            if (this.mGradientState.mGradientColors[i] != null) {
                iArr[i] = this.mGradientState.mGradientColors[i].getDefaultColor();
            }
        }
        return iArr;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Canvas canvas2;
        Paint paint;
        if (ensureValidRect()) {
            int alpha = this.mFillPaint.getAlpha();
            Paint paint2 = this.mStrokePaint;
            int alpha2 = paint2 != null ? paint2.getAlpha() : 0;
            int modulateAlpha = modulateAlpha(alpha);
            int modulateAlpha2 = modulateAlpha(alpha2);
            boolean z = modulateAlpha2 > 0 && (paint = this.mStrokePaint) != null && paint.getStrokeWidth() > 0.0f;
            boolean z2 = modulateAlpha > 0;
            GradientState gradientState = this.mGradientState;
            ColorFilter colorFilter = this.mColorFilter;
            if (colorFilter == null) {
                colorFilter = this.mBlendModeColorFilter;
            }
            ColorFilter colorFilter2 = colorFilter;
            boolean z3 = z && z2 && gradientState.mShape != 2 && modulateAlpha2 < 255 && (this.mAlpha < 255 || colorFilter2 != null);
            if (z3) {
                if (this.mLayerPaint == null) {
                    this.mLayerPaint = new Paint();
                }
                this.mLayerPaint.setDither(gradientState.mDither);
                this.mLayerPaint.setAlpha(this.mAlpha);
                this.mLayerPaint.setColorFilter(colorFilter2);
                float strokeWidth = this.mStrokePaint.getStrokeWidth();
                canvas.saveLayer(this.mRect.left - strokeWidth, this.mRect.top - strokeWidth, this.mRect.right + strokeWidth, this.mRect.bottom + strokeWidth, this.mLayerPaint);
                this.mFillPaint.setColorFilter(null);
                this.mStrokePaint.setColorFilter(null);
            } else {
                this.mFillPaint.setAlpha(modulateAlpha);
                this.mFillPaint.setDither(gradientState.mDither);
                this.mFillPaint.setColorFilter(colorFilter2);
                if (colorFilter2 != null && gradientState.mSolidColors == null) {
                    this.mFillPaint.setColor(this.mAlpha << 24);
                }
                if (z) {
                    this.mStrokePaint.setAlpha(modulateAlpha2);
                    this.mStrokePaint.setDither(gradientState.mDither);
                    this.mStrokePaint.setColorFilter(colorFilter2);
                }
            }
            int i = gradientState.mShape;
            if (i == 0) {
                canvas2 = canvas;
                if (gradientState.mRadiusArray != null) {
                    buildPathIfDirty();
                    canvas2.drawPath(this.mPath, this.mFillPaint);
                    if (z) {
                        canvas2.drawPath(this.mPath, this.mStrokePaint);
                    }
                } else if (gradientState.mRadius > 0.0f) {
                    float min = Math.min(gradientState.mRadius, Math.min(this.mRect.width(), this.mRect.height()) * 0.5f);
                    if (this.mIsSmoothCorner) {
                        drawSmoothCornerRect(canvas2, min, z);
                    } else {
                        canvas2.drawRoundRect(this.mRect, min, min, this.mFillPaint);
                        if (z) {
                            canvas2.drawRoundRect(this.mRect, min, min, this.mStrokePaint);
                        }
                    }
                } else {
                    if (this.mFillPaint.getColor() != 0 || colorFilter2 != null || this.mFillPaint.getShader() != null) {
                        canvas2.drawRect(this.mRect, this.mFillPaint);
                    }
                    if (z) {
                        canvas2.drawRect(this.mRect, this.mStrokePaint);
                    }
                }
            } else if (i == 1) {
                canvas2 = canvas;
                canvas2.drawOval(this.mRect, this.mFillPaint);
                if (z) {
                    canvas2.drawOval(this.mRect, this.mStrokePaint);
                }
            } else if (i == 2) {
                canvas2 = canvas;
                RectF rectF = this.mRect;
                float centerY = rectF.centerY();
                if (z) {
                    canvas2.drawLine(rectF.left, centerY, rectF.right, centerY, this.mStrokePaint);
                }
            } else {
                if (i != 3) {
                    if (i != 4) {
                        canvas2 = canvas;
                    } else if (Flags.gradientDrawableShapeArcForRoundedCap()) {
                        float centerX = this.mRect.centerX();
                        float centerY2 = this.mRect.centerY();
                        float width = gradientState.mThickness != -1 ? gradientState.mThickness : this.mRect.width() / gradientState.mThicknessRatio;
                        float width2 = (gradientState.mInnerRadius != -1 ? gradientState.mInnerRadius : this.mRect.width() / gradientState.mInnerRadiusRatio) - width;
                        float level = gradientState.mUseLevelForShape ? (getLevel() * 360.0f) / 10000.0f : 360.0f;
                        this.mRect.set(centerX - width2, centerY2 - width2, centerX + width2, centerY2 + width2);
                        this.mFillPaint.setStyle(Paint.Style.STROKE);
                        this.mFillPaint.setStrokeWidth(width);
                        this.mFillPaint.setStrokeCap(getStrokeLineCapForPaint(gradientState.mStrokeCap));
                        float f = level;
                        canvas2 = canvas;
                        canvas2.drawArc(this.mRect, 0.0f, f, false, this.mFillPaint);
                        if (z) {
                            Path path = this.mArcPath;
                            if (path == null) {
                                this.mArcPath = new Path();
                            } else {
                                path.reset();
                            }
                            Path path2 = this.mArcOutlinePath;
                            if (path2 == null) {
                                this.mArcOutlinePath = new Path();
                            } else {
                                path2.reset();
                            }
                            if (f == 360.0f) {
                                this.mArcPath.addOval(this.mRect, Path.Direction.CW);
                            } else {
                                this.mArcPath.arcTo(this.mRect, 0.0f, f, false);
                            }
                            this.mFillPaint.getFillPath(this.mArcPath, this.mArcOutlinePath);
                            canvas2.drawPath(this.mArcOutlinePath, this.mStrokePaint);
                        }
                        this.mFillPaint.setStyle(Paint.Style.FILL);
                    }
                }
                canvas2 = canvas;
                Path buildRing = buildRing(gradientState);
                canvas2.drawPath(buildRing, this.mFillPaint);
                if (z) {
                    canvas2.drawPath(buildRing, this.mStrokePaint);
                }
            }
            if (z3) {
                canvas2.restore();
                return;
            }
            this.mFillPaint.setAlpha(alpha);
            if (z) {
                this.mStrokePaint.setAlpha(alpha2);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setXfermode(Xfermode xfermode) {
        super.setXfermode(xfermode);
        this.mFillPaint.setXfermode(xfermode);
    }

    public void setAntiAlias(boolean z) {
        this.mFillPaint.setAntiAlias(z);
    }

    private void buildPathIfDirty() {
        GradientState gradientState = this.mGradientState;
        if (this.mPathIsDirty) {
            ensureValidRect();
            this.mPath.reset();
            this.mPath.addRoundRect(this.mRect, gradientState.mRadiusArray, Path.Direction.CW);
            this.mPathIsDirty = false;
        }
    }

    public void setInnerRadiusRatio(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Ratio must be greater than zero");
        }
        this.mGradientState.mInnerRadiusRatio = f;
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public float getInnerRadiusRatio() {
        return this.mGradientState.mInnerRadiusRatio;
    }

    public void setInnerRadius(int i) {
        this.mGradientState.mInnerRadius = i;
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public int getInnerRadius() {
        return this.mGradientState.mInnerRadius;
    }

    public void setThicknessRatio(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Ratio must be greater than zero");
        }
        this.mGradientState.mThicknessRatio = f;
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public float getThicknessRatio() {
        return this.mGradientState.mThicknessRatio;
    }

    public void setThickness(int i) {
        this.mGradientState.mThickness = i;
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public int getThickness() {
        return this.mGradientState.mThickness;
    }

    public int getStrokeCap() {
        return this.mGradientState.mStrokeCap;
    }

    public void setStrokeCap(int i) {
        this.mGradientState.mStrokeCap = i;
        invalidateSelf();
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        if (this.mGradientState.mPadding == null) {
            this.mGradientState.mPadding = new Rect();
        }
        this.mGradientState.mPadding.set(i, i2, i3, i4);
        this.mPadding = this.mGradientState.mPadding;
        invalidateSelf();
    }

    private Path buildRing(GradientState gradientState) {
        if (this.mRingPath != null && (!gradientState.mUseLevelForShape || !this.mPathIsDirty)) {
            return this.mRingPath;
        }
        this.mPathIsDirty = false;
        float level = gradientState.mUseLevelForShape ? (getLevel() * 360.0f) / 10000.0f : 360.0f;
        RectF rectF = new RectF(this.mRect);
        float width = rectF.width() / 2.0f;
        float height = rectF.height() / 2.0f;
        float width2 = gradientState.mThickness != -1 ? gradientState.mThickness : rectF.width() / gradientState.mThicknessRatio;
        float width3 = gradientState.mInnerRadius != -1 ? gradientState.mInnerRadius : rectF.width() / gradientState.mInnerRadiusRatio;
        RectF rectF2 = new RectF(rectF);
        rectF2.inset(width - width3, height - width3);
        RectF rectF3 = new RectF(rectF2);
        float f = -width2;
        rectF3.inset(f, f);
        Path path = this.mRingPath;
        if (path == null) {
            this.mRingPath = new Path();
        } else {
            path.reset();
        }
        Path path2 = this.mRingPath;
        if (level < 360.0f && level > -360.0f) {
            path2.setFillType(Path.FillType.EVEN_ODD);
            float f2 = width + width3;
            path2.moveTo(f2, height);
            path2.lineTo(f2 + width2, height);
            path2.arcTo(rectF3, 0.0f, level, false);
            path2.arcTo(rectF2, level, -level, false);
            path2.close();
            return path2;
        }
        path2.addOval(rectF3, Path.Direction.CW);
        path2.addOval(rectF2, Path.Direction.CCW);
        return path2;
    }

    private Paint.Cap getStrokeLineCapForPaint(int i) {
        if (i == 0) {
            return Paint.Cap.BUTT;
        }
        if (i == 1) {
            return Paint.Cap.ROUND;
        }
        if (i == 2) {
            return Paint.Cap.SQUARE;
        }
        return Paint.Cap.SQUARE;
    }

    public void setColor(int i) {
        this.mGradientState.setSolidColors(ColorStateList.valueOf(i));
        this.mFillPaint.setColor(i);
        invalidateSelf();
    }

    public void setColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            setColor(0);
            return;
        }
        int colorForState = colorStateList.getColorForState(getState(), 0);
        this.mGradientState.setSolidColors(colorStateList);
        this.mFillPaint.setColor(colorForState);
        invalidateSelf();
    }

    public ColorStateList getColor() {
        return this.mGradientState.mSolidColors;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean z;
        ColorStateList colorStateList;
        int colorForState;
        int colorForState2;
        GradientState gradientState = this.mGradientState;
        ColorStateList colorStateList2 = gradientState.mSolidColors;
        if (colorStateList2 == null || this.mFillPaint.getColor() == (colorForState2 = colorStateList2.getColorForState(iArr, 0))) {
            z = false;
        } else {
            this.mFillPaint.setColor(colorForState2);
            z = true;
        }
        Paint paint = this.mStrokePaint;
        if (paint != null && (colorStateList = gradientState.mStrokeColors) != null && paint.getColor() != (colorForState = colorStateList.getColorForState(iArr, 0))) {
            paint.setColor(colorForState);
            z = true;
        }
        if (gradientState.mTint != null && gradientState.mBlendMode != null) {
            this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, gradientState.mTint, gradientState.mBlendMode);
            z = true;
        }
        if (!z) {
            return false;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        GradientState gradientState = this.mGradientState;
        if (super.isStateful()) {
            return true;
        }
        if (gradientState.mSolidColors != null && gradientState.mSolidColors.isStateful()) {
            return true;
        }
        if (gradientState.mStrokeColors == null || !gradientState.mStrokeColors.isStateful()) {
            return gradientState.mTint != null && gradientState.mTint.isStateful();
        }
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        GradientState gradientState = this.mGradientState;
        if (gradientState.mSolidColors != null && gradientState.mSolidColors.hasFocusStateSpecified()) {
            return true;
        }
        if (gradientState.mStrokeColors == null || !gradientState.mStrokeColors.hasFocusStateSpecified()) {
            return gradientState.mTint != null && gradientState.mTint.hasFocusStateSpecified();
        }
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mGradientState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.mAlpha) {
            this.mAlpha = i;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        if (z != this.mGradientState.mDither) {
            this.mGradientState.mDither = z;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        if (colorFilter != this.mColorFilter) {
            this.mColorFilter = colorFilter;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mGradientState.mTint = colorStateList;
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, colorStateList, this.mGradientState.mBlendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(BlendMode blendMode) {
        this.mGradientState.mBlendMode = blendMode;
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, this.mGradientState.mTint, blendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return (this.mAlpha == 255 && this.mGradientState.mOpaqueOverBounds && isOpaqueForState()) ? -1 : -3;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mRingPath = null;
        this.mPathIsDirty = true;
        this.mGradientIsDirty = true;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        super.onLevelChange(i);
        this.mGradientIsDirty = true;
        this.mPathIsDirty = true;
        invalidateSelf();
        return true;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:25:0x0074. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:56:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x013a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean ensureValidRect() {
        /*
            Method dump skipped, instructions count: 474
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.drawable.GradientDrawable.ensureValidRect():boolean");
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.mGradientState.setDensity(Drawable.resolveDensity(resources, 0));
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.GradientDrawable);
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        updateLocalState(resources);
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        GradientState gradientState = this.mGradientState;
        if (gradientState == null) {
            return;
        }
        gradientState.setDensity(Drawable.resolveDensity(theme.getResources(), 0));
        if (gradientState.mThemeAttrs != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(gradientState.mThemeAttrs, R.styleable.GradientDrawable);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
        if (gradientState.mTint != null && gradientState.mTint.canApplyTheme()) {
            gradientState.mTint = gradientState.mTint.obtainForTheme(theme);
        }
        if (gradientState.mSolidColors != null && gradientState.mSolidColors.canApplyTheme()) {
            gradientState.mSolidColors = gradientState.mSolidColors.obtainForTheme(theme);
        }
        if (gradientState.mStrokeColors != null && gradientState.mStrokeColors.canApplyTheme()) {
            gradientState.mStrokeColors = gradientState.mStrokeColors.obtainForTheme(theme);
        }
        if (gradientState.mGradientColors != null) {
            for (int i = 0; i < gradientState.mGradientColors.length; i++) {
                if (gradientState.mGradientColors[i] != null && gradientState.mGradientColors[i].canApplyTheme()) {
                    gradientState.mGradientColors[i] = gradientState.mGradientColors[i].obtainForTheme(theme);
                }
            }
        }
        applyThemeChildElements(theme);
        updateLocalState(theme.getResources());
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mThemeAttrs = typedArray.extractThemeAttrs();
        gradientState.mShape = typedArray.getInt(3, gradientState.mShape);
        gradientState.mDither = typedArray.getBoolean(0, gradientState.mDither);
        if (gradientState.mShape == 3 || gradientState.mShape == 4) {
            gradientState.mInnerRadius = typedArray.getDimensionPixelSize(7, gradientState.mInnerRadius);
            if (gradientState.mInnerRadius == -1) {
                gradientState.mInnerRadiusRatio = typedArray.getFloat(4, gradientState.mInnerRadiusRatio);
            }
            gradientState.mThickness = typedArray.getDimensionPixelSize(8, gradientState.mThickness);
            if (gradientState.mThickness == -1) {
                gradientState.mThicknessRatio = typedArray.getFloat(5, gradientState.mThicknessRatio);
            }
            gradientState.mUseLevelForShape = typedArray.getBoolean(6, gradientState.mUseLevelForShape);
            gradientState.mStrokeCap = typedArray.getInt(14, gradientState.mStrokeCap);
        }
        int i = typedArray.getInt(9, -1);
        if (i != -1) {
            gradientState.mBlendMode = Drawable.parseBlendMode(i, BlendMode.SRC_IN);
        }
        ColorStateList colorStateList = typedArray.getColorStateList(1);
        if (colorStateList != null) {
            gradientState.mTint = colorStateList;
        }
        gradientState.mOpticalInsets = Insets.of(typedArray.getDimensionPixelSize(10, gradientState.mOpticalInsets.left), typedArray.getDimensionPixelSize(11, gradientState.mOpticalInsets.top), typedArray.getDimensionPixelSize(12, gradientState.mOpticalInsets.right), typedArray.getDimensionPixelSize(13, gradientState.mOpticalInsets.bottom));
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        GradientState gradientState = this.mGradientState;
        return (gradientState != null && gradientState.canApplyTheme()) || super.canApplyTheme();
    }

    private void applyThemeChildElements(Resources.Theme theme) {
        GradientState gradientState = this.mGradientState;
        if (gradientState.mAttrSize != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(gradientState.mAttrSize, R.styleable.GradientDrawableSize);
            updateGradientDrawableSize(resolveAttributes);
            resolveAttributes.recycle();
        }
        if (gradientState.mAttrGradient != null) {
            TypedArray resolveAttributes2 = theme.resolveAttributes(gradientState.mAttrGradient, R.styleable.GradientDrawableGradient);
            try {
                updateGradientDrawableGradient(theme.getResources(), resolveAttributes2);
                resolveAttributes2.recycle();
            } finally {
                resolveAttributes2.recycle();
            }
        }
        if (gradientState.mAttrSolid != null) {
            TypedArray resolveAttributes3 = theme.resolveAttributes(gradientState.mAttrSolid, R.styleable.GradientDrawableSolid);
            updateGradientDrawableSolid(resolveAttributes3);
            resolveAttributes3.recycle();
        }
        if (gradientState.mAttrStroke != null) {
            TypedArray resolveAttributes4 = theme.resolveAttributes(gradientState.mAttrStroke, R.styleable.GradientDrawableStroke);
            updateGradientDrawableStroke(resolveAttributes4);
            resolveAttributes4.recycle();
        }
        if (gradientState.mAttrCorners != null) {
            updateDrawableCorners(theme.resolveAttributes(gradientState.mAttrCorners, R.styleable.DrawableCorners));
        }
        if (gradientState.mAttrPadding != null) {
            updateGradientDrawablePadding(theme.resolveAttributes(gradientState.mAttrPadding, R.styleable.GradientDrawablePadding));
        }
    }

    private void inflateChildElements(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            int depth2 = xmlPullParser.getDepth();
            if (depth2 < depth && next == 3) {
                return;
            }
            if (next == 2 && depth2 <= depth) {
                String name = xmlPullParser.getName();
                if (name.equals(Contract.DatabaseSize.PATH)) {
                    TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.GradientDrawableSize);
                    updateGradientDrawableSize(obtainAttributes);
                    obtainAttributes.recycle();
                } else if (name.equals("gradient")) {
                    TypedArray obtainAttributes2 = obtainAttributes(resources, theme, attributeSet, R.styleable.GradientDrawableGradient);
                    updateGradientDrawableGradient(resources, obtainAttributes2);
                    obtainAttributes2.recycle();
                } else if (name.equals("solid")) {
                    TypedArray obtainAttributes3 = obtainAttributes(resources, theme, attributeSet, R.styleable.GradientDrawableSolid);
                    updateGradientDrawableSolid(obtainAttributes3);
                    obtainAttributes3.recycle();
                } else if (name.equals(SemSmartClipMetaTagType.STROKE)) {
                    TypedArray obtainAttributes4 = obtainAttributes(resources, theme, attributeSet, R.styleable.GradientDrawableStroke);
                    updateGradientDrawableStroke(obtainAttributes4);
                    obtainAttributes4.recycle();
                } else if (name.equals("corners")) {
                    TypedArray obtainAttributes5 = obtainAttributes(resources, theme, attributeSet, R.styleable.DrawableCorners);
                    updateDrawableCorners(obtainAttributes5);
                    obtainAttributes5.recycle();
                } else if (name.equals("padding")) {
                    TypedArray obtainAttributes6 = obtainAttributes(resources, theme, attributeSet, R.styleable.GradientDrawablePadding);
                    updateGradientDrawablePadding(obtainAttributes6);
                    obtainAttributes6.recycle();
                } else {
                    Log.w("drawable", "Bad element under <shape>: " + name);
                }
            }
        }
    }

    private void updateGradientDrawablePadding(TypedArray typedArray) {
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrPadding = typedArray.extractThemeAttrs();
        if (gradientState.mPadding == null) {
            gradientState.mPadding = new Rect();
        }
        Rect rect = gradientState.mPadding;
        rect.set(typedArray.getDimensionPixelOffset(0, rect.left), typedArray.getDimensionPixelOffset(1, rect.top), typedArray.getDimensionPixelOffset(2, rect.right), typedArray.getDimensionPixelOffset(3, rect.bottom));
        this.mPadding = rect;
    }

    private void updateDrawableCorners(TypedArray typedArray) {
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrCorners = typedArray.extractThemeAttrs();
        int dimensionPixelSize = typedArray.getDimensionPixelSize(0, (int) gradientState.mRadius);
        setCornerRadius(dimensionPixelSize);
        int dimensionPixelSize2 = typedArray.getDimensionPixelSize(1, dimensionPixelSize);
        int dimensionPixelSize3 = typedArray.getDimensionPixelSize(2, dimensionPixelSize);
        int dimensionPixelSize4 = typedArray.getDimensionPixelSize(3, dimensionPixelSize);
        int dimensionPixelSize5 = typedArray.getDimensionPixelSize(4, dimensionPixelSize);
        if (dimensionPixelSize2 == dimensionPixelSize && dimensionPixelSize3 == dimensionPixelSize && dimensionPixelSize4 == dimensionPixelSize && dimensionPixelSize5 == dimensionPixelSize) {
            return;
        }
        float f = dimensionPixelSize2;
        float f2 = dimensionPixelSize3;
        float f3 = dimensionPixelSize5;
        float f4 = dimensionPixelSize4;
        setCornerRadii(new float[]{f, f, f2, f2, f3, f3, f4, f4});
    }

    private void updateGradientDrawableStroke(TypedArray typedArray) {
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrStroke = typedArray.extractThemeAttrs();
        int dimensionPixelSize = typedArray.getDimensionPixelSize(0, Math.max(0, gradientState.mStrokeWidth));
        float dimension = typedArray.getDimension(2, gradientState.mStrokeDashWidth);
        ColorStateList colorStateList = typedArray.getColorStateList(1);
        if (colorStateList == null) {
            colorStateList = gradientState.mStrokeColors;
        }
        if (dimension != 0.0f) {
            setStroke(dimensionPixelSize, colorStateList, dimension, typedArray.getDimension(3, gradientState.mStrokeDashGap));
        } else {
            setStroke(dimensionPixelSize, colorStateList);
        }
    }

    private void updateGradientDrawableSolid(TypedArray typedArray) {
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrSolid = typedArray.extractThemeAttrs();
        ColorStateList colorStateList = typedArray.getColorStateList(0);
        if (colorStateList != null) {
            setColor(colorStateList);
        }
    }

    private void updateGradientDrawableGradient(Resources resources, TypedArray typedArray) {
        float f;
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrGradient = typedArray.extractThemeAttrs();
        gradientState.mCenterX = getFloatOrFraction(typedArray, 5, gradientState.mCenterX);
        gradientState.mCenterY = getFloatOrFraction(typedArray, 6, gradientState.mCenterY);
        int i = 2;
        gradientState.mUseLevel = typedArray.getBoolean(2, gradientState.mUseLevel);
        gradientState.mGradient = typedArray.getInt(4, gradientState.mGradient);
        ColorStateList colorStateList = typedArray.getColorStateList(0);
        ColorStateList colorStateList2 = typedArray.getColorStateList(8);
        ColorStateList colorStateList3 = typedArray.getColorStateList(1);
        boolean z = gradientState.mGradientColors != null;
        boolean hasCenterColor = gradientState.hasCenterColor();
        int defaultColor = colorStateList != null ? colorStateList.getDefaultColor() : 0;
        int defaultColor2 = colorStateList2 != null ? colorStateList2.getDefaultColor() : 0;
        int defaultColor3 = colorStateList3 != null ? colorStateList3.getDefaultColor() : 0;
        if (z && gradientState.mGradientColors[0] != null) {
            defaultColor = gradientState.mGradientColors[0].getDefaultColor();
        }
        if (hasCenterColor && gradientState.mGradientColors[1] != null) {
            defaultColor2 = gradientState.mGradientColors[1].getDefaultColor();
        }
        if (hasCenterColor && gradientState.mGradientColors[2] != null) {
            defaultColor3 = gradientState.mGradientColors[2].getDefaultColor();
        } else if (z && gradientState.mGradientColors[1] != null) {
            defaultColor3 = gradientState.mGradientColors[1].getDefaultColor();
        }
        if (typedArray.hasValue(8) || hasCenterColor) {
            gradientState.mGradientColors = new ColorStateList[3];
            ColorStateList[] colorStateListArr = gradientState.mGradientColors;
            if (colorStateList == null) {
                colorStateList = ColorStateList.valueOf(defaultColor);
            }
            colorStateListArr[0] = colorStateList;
            ColorStateList[] colorStateListArr2 = gradientState.mGradientColors;
            if (colorStateList2 == null) {
                colorStateList2 = ColorStateList.valueOf(defaultColor2);
            }
            colorStateListArr2[1] = colorStateList2;
            ColorStateList[] colorStateListArr3 = gradientState.mGradientColors;
            if (colorStateList3 == null) {
                colorStateList3 = ColorStateList.valueOf(defaultColor3);
            }
            colorStateListArr3[2] = colorStateList3;
            gradientState.mPositions = new float[3];
            gradientState.mPositions[0] = 0.0f;
            gradientState.mPositions[1] = gradientState.mCenterX != 0.5f ? gradientState.mCenterX : gradientState.mCenterY;
            gradientState.mPositions[2] = 1.0f;
        } else {
            gradientState.mGradientColors = new ColorStateList[2];
            ColorStateList[] colorStateListArr4 = gradientState.mGradientColors;
            if (colorStateList == null) {
                colorStateList = ColorStateList.valueOf(defaultColor);
            }
            colorStateListArr4[0] = colorStateList;
            ColorStateList[] colorStateListArr5 = gradientState.mGradientColors;
            if (colorStateList3 == null) {
                colorStateList3 = ColorStateList.valueOf(defaultColor3);
            }
            colorStateListArr5[1] = colorStateList3;
        }
        int i2 = (int) typedArray.getFloat(3, gradientState.mAngle);
        if (sWrapNegativeAngleMeasurements) {
            gradientState.mAngle = ((i2 % 360) + 360) % 360;
        } else {
            gradientState.mAngle = i2 % 360;
        }
        if (gradientState.mAngle >= 0) {
            int i3 = gradientState.mAngle;
            if (i3 == 0) {
                gradientState.mOrientation = Orientation.LEFT_RIGHT;
            } else if (i3 == 45) {
                gradientState.mOrientation = Orientation.BL_TR;
            } else if (i3 == 90) {
                gradientState.mOrientation = Orientation.BOTTOM_TOP;
            } else if (i3 == 135) {
                gradientState.mOrientation = Orientation.BR_TL;
            } else if (i3 == 180) {
                gradientState.mOrientation = Orientation.RIGHT_LEFT;
            } else if (i3 == 225) {
                gradientState.mOrientation = Orientation.TR_BL;
            } else if (i3 == 270) {
                gradientState.mOrientation = Orientation.TOP_BOTTOM;
            } else if (i3 == 315) {
                gradientState.mOrientation = Orientation.TL_BR;
            }
        } else {
            gradientState.mOrientation = DEFAULT_ORIENTATION;
        }
        TypedValue peekValue = typedArray.peekValue(7);
        if (peekValue != null) {
            if (peekValue.type == 6) {
                f = peekValue.getFraction(1.0f, 1.0f);
                if ((peekValue.data & 15) != 1) {
                    i = 1;
                }
            } else {
                if (peekValue.type == 5) {
                    f = peekValue.getDimension(resources.getDisplayMetrics());
                } else {
                    f = peekValue.getFloat();
                }
                i = 0;
            }
            gradientState.mGradientRadius = f;
            gradientState.mGradientRadiusType = i;
        }
    }

    private void updateGradientDrawableSize(TypedArray typedArray) {
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrSize = typedArray.extractThemeAttrs();
        gradientState.mWidth = typedArray.getDimensionPixelSize(1, gradientState.mWidth);
        gradientState.mHeight = typedArray.getDimensionPixelSize(0, gradientState.mHeight);
    }

    private static float getFloatOrFraction(TypedArray typedArray, int i, float f) {
        TypedValue peekValue = typedArray.peekValue(i);
        return peekValue != null ? peekValue.type == 6 ? peekValue.getFraction(1.0f, 1.0f) : peekValue.getFloat() : f;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mGradientState.mWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mGradientState.mHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public Insets getOpticalInsets() {
        return this.mGradientState.mOpticalInsets;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        this.mGradientState.mChangingConfigurations = getChangingConfigurations();
        return this.mGradientState;
    }

    private boolean isOpaqueForState() {
        Paint paint;
        if (this.mGradientState.mStrokeWidth < 0 || (paint = this.mStrokePaint) == null || isOpaque(paint.getColor())) {
            return this.mGradientState.mGradientColors != null || isOpaque(this.mFillPaint.getColor());
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        Paint paint;
        GradientState gradientState = this.mGradientState;
        Rect bounds = getBounds();
        outline.setAlpha((gradientState.mOpaqueOverShape && (this.mGradientState.mStrokeWidth <= 0 || (paint = this.mStrokePaint) == null || paint.getAlpha() == this.mFillPaint.getAlpha())) ? modulateAlpha(this.mFillPaint.getAlpha()) / 255.0f : 0.0f);
        int i = gradientState.mShape;
        if (i != 0) {
            if (i == 1) {
                outline.setOval(bounds);
                return;
            } else {
                if (i != 2) {
                    return;
                }
                Paint paint2 = this.mStrokePaint;
                float strokeWidth = paint2 == null ? 1.0E-4f : paint2.getStrokeWidth() * 0.5f;
                float centerY = bounds.centerY();
                outline.setRect(bounds.left, (int) Math.floor(centerY - strokeWidth), bounds.right, (int) Math.ceil(centerY + strokeWidth));
                return;
            }
        }
        if (gradientState.mRadiusArray != null) {
            buildPathIfDirty();
            outline.setPath(this.mPath);
            return;
        }
        float min = gradientState.mRadius > 0.0f ? Math.min(gradientState.mRadius, Math.min(bounds.width(), bounds.height()) * 0.5f) : 0.0f;
        if (this.mIsSmoothCorner) {
            SemViewUtils.getSmoothCornerRectPath(this.mPath, min, bounds.left, bounds.top, bounds.width(), bounds.height());
            outline.setPath(this.mPath);
        } else {
            outline.setRoundRect(bounds, min);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mGradientState = new GradientState(this.mGradientState, (Resources) null);
            updateLocalState(null);
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    static final class GradientState extends Drawable.ConstantState {
        public int mAngle;
        int[] mAttrCorners;
        int[] mAttrGradient;
        int[] mAttrPadding;
        int[] mAttrSize;
        int[] mAttrSolid;
        int[] mAttrStroke;
        BlendMode mBlendMode;
        float mCenterX;
        float mCenterY;
        public int mChangingConfigurations;
        int mDensity;
        public boolean mDither;
        public int mGradient;
        public ColorStateList[] mGradientColors;
        float mGradientRadius;
        int mGradientRadiusType;
        public int mHeight;
        public int mInnerRadius;
        public float mInnerRadiusRatio;
        boolean mOpaqueOverBounds;
        boolean mOpaqueOverShape;
        public Insets mOpticalInsets;
        public Orientation mOrientation;
        public Rect mPadding;
        public float[] mPositions;
        public float mRadius;
        public float[] mRadiusArray;
        public int mShape;
        public ColorStateList mSolidColors;
        public int mStrokeCap;
        public ColorStateList mStrokeColors;
        public float mStrokeDashGap;
        public float mStrokeDashWidth;
        public int mStrokeWidth;
        public int[] mTempColors;
        public float[] mTempPositions;
        int[] mThemeAttrs;
        public int mThickness;
        public float mThicknessRatio;
        ColorStateList mTint;
        boolean mUseLevel;
        boolean mUseLevelForShape;
        public int mWidth;

        public GradientState(Orientation orientation, int[] iArr) {
            this.mShape = 0;
            this.mGradient = 0;
            this.mAngle = 0;
            this.mStrokeWidth = -1;
            this.mStrokeDashWidth = 0.0f;
            this.mStrokeDashGap = 0.0f;
            this.mRadius = 0.0f;
            this.mRadiusArray = null;
            this.mPadding = null;
            this.mWidth = -1;
            this.mHeight = -1;
            this.mInnerRadiusRatio = 3.0f;
            this.mThicknessRatio = GradientDrawable.DEFAULT_THICKNESS_RATIO;
            this.mInnerRadius = -1;
            this.mThickness = -1;
            this.mStrokeCap = 1;
            this.mDither = false;
            this.mOpticalInsets = Insets.NONE;
            this.mCenterX = 0.5f;
            this.mCenterY = 0.5f;
            this.mGradientRadius = 0.5f;
            this.mGradientRadiusType = 0;
            this.mUseLevel = false;
            this.mUseLevelForShape = true;
            this.mTint = null;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mDensity = 160;
            this.mOrientation = orientation;
            setGradientColors(iArr);
        }

        public GradientState(GradientState gradientState, Resources resources) {
            this.mShape = 0;
            this.mGradient = 0;
            this.mAngle = 0;
            this.mStrokeWidth = -1;
            this.mStrokeDashWidth = 0.0f;
            this.mStrokeDashGap = 0.0f;
            this.mRadius = 0.0f;
            this.mRadiusArray = null;
            this.mPadding = null;
            this.mWidth = -1;
            this.mHeight = -1;
            this.mInnerRadiusRatio = 3.0f;
            this.mThicknessRatio = GradientDrawable.DEFAULT_THICKNESS_RATIO;
            this.mInnerRadius = -1;
            this.mThickness = -1;
            this.mStrokeCap = 1;
            this.mDither = false;
            this.mOpticalInsets = Insets.NONE;
            this.mCenterX = 0.5f;
            this.mCenterY = 0.5f;
            this.mGradientRadius = 0.5f;
            this.mGradientRadiusType = 0;
            this.mUseLevel = false;
            this.mUseLevelForShape = true;
            this.mTint = null;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mDensity = 160;
            this.mChangingConfigurations = gradientState.mChangingConfigurations;
            this.mShape = gradientState.mShape;
            this.mGradient = gradientState.mGradient;
            this.mAngle = gradientState.mAngle;
            this.mOrientation = gradientState.mOrientation;
            this.mSolidColors = gradientState.mSolidColors;
            ColorStateList[] colorStateListArr = gradientState.mGradientColors;
            if (colorStateListArr != null) {
                this.mGradientColors = (ColorStateList[]) colorStateListArr.clone();
            }
            float[] fArr = gradientState.mPositions;
            if (fArr != null) {
                this.mPositions = (float[]) fArr.clone();
            }
            this.mStrokeColors = gradientState.mStrokeColors;
            this.mStrokeWidth = gradientState.mStrokeWidth;
            this.mStrokeDashWidth = gradientState.mStrokeDashWidth;
            this.mStrokeDashGap = gradientState.mStrokeDashGap;
            this.mRadius = gradientState.mRadius;
            float[] fArr2 = gradientState.mRadiusArray;
            if (fArr2 != null) {
                this.mRadiusArray = (float[]) fArr2.clone();
            }
            if (gradientState.mPadding != null) {
                this.mPadding = new Rect(gradientState.mPadding);
            }
            this.mWidth = gradientState.mWidth;
            this.mHeight = gradientState.mHeight;
            this.mInnerRadiusRatio = gradientState.mInnerRadiusRatio;
            this.mThicknessRatio = gradientState.mThicknessRatio;
            this.mInnerRadius = gradientState.mInnerRadius;
            this.mThickness = gradientState.mThickness;
            this.mDither = gradientState.mDither;
            this.mOpticalInsets = gradientState.mOpticalInsets;
            this.mCenterX = gradientState.mCenterX;
            this.mCenterY = gradientState.mCenterY;
            this.mGradientRadius = gradientState.mGradientRadius;
            this.mGradientRadiusType = gradientState.mGradientRadiusType;
            this.mUseLevel = gradientState.mUseLevel;
            this.mUseLevelForShape = gradientState.mUseLevelForShape;
            this.mOpaqueOverBounds = gradientState.mOpaqueOverBounds;
            this.mOpaqueOverShape = gradientState.mOpaqueOverShape;
            this.mTint = gradientState.mTint;
            this.mBlendMode = gradientState.mBlendMode;
            this.mThemeAttrs = gradientState.mThemeAttrs;
            this.mAttrSize = gradientState.mAttrSize;
            this.mAttrGradient = gradientState.mAttrGradient;
            this.mAttrSolid = gradientState.mAttrSolid;
            this.mAttrStroke = gradientState.mAttrStroke;
            this.mAttrCorners = gradientState.mAttrCorners;
            this.mAttrPadding = gradientState.mAttrPadding;
            int resolveDensity = Drawable.resolveDensity(resources, gradientState.mDensity);
            this.mDensity = resolveDensity;
            int i = gradientState.mDensity;
            if (i != resolveDensity) {
                applyDensityScaling(i, resolveDensity);
            }
        }

        public final void setDensity(int i) {
            int i2 = this.mDensity;
            if (i2 != i) {
                this.mDensity = i;
                applyDensityScaling(i2, i);
            }
        }

        public boolean hasCenterColor() {
            ColorStateList[] colorStateListArr = this.mGradientColors;
            return colorStateListArr != null && colorStateListArr.length == 3;
        }

        private void applyDensityScaling(int i, int i2) {
            int i3 = this.mInnerRadius;
            if (i3 > 0) {
                this.mInnerRadius = Drawable.scaleFromDensity(i3, i, i2, true);
            }
            int i4 = this.mThickness;
            if (i4 > 0) {
                this.mThickness = Drawable.scaleFromDensity(i4, i, i2, true);
            }
            if (this.mOpticalInsets != Insets.NONE) {
                this.mOpticalInsets = Insets.of(Drawable.scaleFromDensity(this.mOpticalInsets.left, i, i2, true), Drawable.scaleFromDensity(this.mOpticalInsets.top, i, i2, true), Drawable.scaleFromDensity(this.mOpticalInsets.right, i, i2, true), Drawable.scaleFromDensity(this.mOpticalInsets.bottom, i, i2, true));
            }
            Rect rect = this.mPadding;
            if (rect != null) {
                rect.left = Drawable.scaleFromDensity(rect.left, i, i2, false);
                Rect rect2 = this.mPadding;
                rect2.top = Drawable.scaleFromDensity(rect2.top, i, i2, false);
                Rect rect3 = this.mPadding;
                rect3.right = Drawable.scaleFromDensity(rect3.right, i, i2, false);
                Rect rect4 = this.mPadding;
                rect4.bottom = Drawable.scaleFromDensity(rect4.bottom, i, i2, false);
            }
            float f = this.mRadius;
            if (f > 0.0f) {
                this.mRadius = Drawable.scaleFromDensity(f, i, i2);
            }
            float[] fArr = this.mRadiusArray;
            if (fArr != null) {
                fArr[0] = Drawable.scaleFromDensity((int) fArr[0], i, i2, true);
                this.mRadiusArray[1] = Drawable.scaleFromDensity((int) r0[1], i, i2, true);
                this.mRadiusArray[2] = Drawable.scaleFromDensity((int) r0[2], i, i2, true);
                this.mRadiusArray[3] = Drawable.scaleFromDensity((int) r0[3], i, i2, true);
            }
            int i5 = this.mStrokeWidth;
            if (i5 > 0) {
                this.mStrokeWidth = Drawable.scaleFromDensity(i5, i, i2, true);
            }
            if (this.mStrokeDashWidth > 0.0f) {
                this.mStrokeDashWidth = Drawable.scaleFromDensity(this.mStrokeDashGap, i, i2);
            }
            float f2 = this.mStrokeDashGap;
            if (f2 > 0.0f) {
                this.mStrokeDashGap = Drawable.scaleFromDensity(f2, i, i2);
            }
            if (this.mGradientRadiusType == 0) {
                this.mGradientRadius = Drawable.scaleFromDensity(this.mGradientRadius, i, i2);
            }
            int i6 = this.mWidth;
            if (i6 > 0) {
                this.mWidth = Drawable.scaleFromDensity(i6, i, i2, true);
            }
            int i7 = this.mHeight;
            if (i7 > 0) {
                this.mHeight = Drawable.scaleFromDensity(i7, i, i2, true);
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            ColorStateList colorStateList;
            ColorStateList colorStateList2;
            ColorStateList colorStateList3;
            ColorStateList[] colorStateListArr = this.mGradientColors;
            boolean z = colorStateListArr != null;
            if (colorStateListArr != null) {
                int i = 0;
                while (true) {
                    ColorStateList[] colorStateListArr2 = this.mGradientColors;
                    if (i >= colorStateListArr2.length) {
                        break;
                    }
                    ColorStateList colorStateList4 = colorStateListArr2[i];
                    z |= colorStateList4 != null && colorStateList4.canApplyTheme();
                    i++;
                }
            }
            return (this.mThemeAttrs == null && this.mAttrSize == null && this.mAttrGradient == null && this.mAttrSolid == null && this.mAttrStroke == null && this.mAttrCorners == null && this.mAttrPadding == null && ((colorStateList = this.mTint) == null || !colorStateList.canApplyTheme()) && (((colorStateList2 = this.mStrokeColors) == null || !colorStateList2.canApplyTheme()) && (((colorStateList3 = this.mSolidColors) == null || !colorStateList3.canApplyTheme()) && !z && !super.canApplyTheme()))) ? false : true;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new GradientDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            if (Drawable.resolveDensity(resources, this.mDensity) != this.mDensity) {
                this = new GradientState(this, resources);
            }
            return new GradientDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            int i = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mStrokeColors;
            int changingConfigurations = i | (colorStateList != null ? colorStateList.getChangingConfigurations() : 0);
            ColorStateList colorStateList2 = this.mSolidColors;
            int changingConfigurations2 = changingConfigurations | (colorStateList2 != null ? colorStateList2.getChangingConfigurations() : 0);
            ColorStateList colorStateList3 = this.mTint;
            return changingConfigurations2 | (colorStateList3 != null ? colorStateList3.getChangingConfigurations() : 0);
        }

        public void setShape(int i) {
            this.mShape = i;
            computeOpacity();
        }

        public void setGradientType(int i) {
            this.mGradient = i;
        }

        public void setGradientCenter(float f, float f2) {
            this.mCenterX = f;
            this.mCenterY = f2;
        }

        public Orientation getOrientation() {
            return this.mOrientation;
        }

        public void setGradientColors(int[] iArr) {
            if (iArr == null) {
                this.mGradientColors = null;
            } else {
                ColorStateList[] colorStateListArr = this.mGradientColors;
                if (colorStateListArr == null || colorStateListArr.length != iArr.length) {
                    this.mGradientColors = new ColorStateList[iArr.length];
                }
                for (int i = 0; i < iArr.length; i++) {
                    this.mGradientColors[i] = ColorStateList.valueOf(iArr[i]);
                }
            }
            this.mSolidColors = null;
            computeOpacity();
        }

        public void setSolidColors(ColorStateList colorStateList) {
            this.mGradientColors = null;
            this.mSolidColors = colorStateList;
            computeOpacity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void computeOpacity() {
            boolean z = false;
            this.mOpaqueOverBounds = false;
            this.mOpaqueOverShape = false;
            if (this.mGradientColors != null) {
                int i = 0;
                while (true) {
                    ColorStateList[] colorStateListArr = this.mGradientColors;
                    if (i >= colorStateListArr.length) {
                        break;
                    }
                    ColorStateList colorStateList = colorStateListArr[i];
                    if (colorStateList != null && !GradientDrawable.isOpaque(colorStateList.getDefaultColor())) {
                        return;
                    } else {
                        i++;
                    }
                }
            }
            if (this.mGradientColors == null && this.mSolidColors == null) {
                return;
            }
            this.mOpaqueOverShape = true;
            if (this.mShape == 0 && this.mRadius <= 0.0f && this.mRadiusArray == null) {
                z = true;
            }
            this.mOpaqueOverBounds = z;
        }

        public void setStroke(int i, ColorStateList colorStateList, float f, float f2) {
            this.mStrokeWidth = i;
            this.mStrokeColors = colorStateList;
            this.mStrokeDashWidth = f;
            this.mStrokeDashGap = f2;
            computeOpacity();
        }

        public void setCornerRadius(float f) {
            if (f < 0.0f) {
                f = 0.0f;
            }
            this.mRadius = f;
            this.mRadiusArray = null;
            computeOpacity();
        }

        public void setCornerRadii(float[] fArr) {
            this.mRadiusArray = fArr;
            if (fArr == null) {
                this.mRadius = 0.0f;
            }
            computeOpacity();
        }

        public void setSize(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
        }

        public void setGradientRadius(float f, int i) {
            this.mGradientRadius = f;
            this.mGradientRadiusType = i;
        }
    }

    private GradientDrawable(GradientState gradientState, Resources resources) {
        this.mFillPaint = new Paint(1);
        this.mAlpha = 255;
        this.mPath = new Path();
        this.mRect = new RectF();
        this.mPathIsDirty = true;
        this.mIsSmoothCorner = false;
        this.mGradientState = gradientState;
        updateLocalState(resources);
    }

    private void updateLocalState(Resources resources) {
        GradientState gradientState = this.mGradientState;
        if (gradientState.mSolidColors != null) {
            this.mFillPaint.setColor(gradientState.mSolidColors.getColorForState(getState(), 0));
        } else if (gradientState.mGradientColors == null) {
            this.mFillPaint.setColor(0);
        } else {
            this.mFillPaint.setColor(-16777216);
        }
        this.mPadding = gradientState.mPadding;
        if (gradientState.mStrokeWidth >= 0) {
            Paint paint = new Paint(1);
            this.mStrokePaint = paint;
            paint.setStyle(Paint.Style.STROKE);
            this.mStrokePaint.setStrokeWidth(gradientState.mStrokeWidth);
            if (gradientState.mStrokeColors != null) {
                this.mStrokePaint.setColor(gradientState.mStrokeColors.getColorForState(getState(), 0));
            }
            if (gradientState.mStrokeDashWidth != 0.0f) {
                this.mStrokePaint.setPathEffect(new DashPathEffect(new float[]{gradientState.mStrokeDashWidth, gradientState.mStrokeDashGap}, 0.0f));
            }
        }
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, gradientState.mTint, gradientState.mBlendMode);
        this.mGradientIsDirty = true;
        gradientState.computeOpacity();
    }

    void setSmoothCorner(boolean z) {
        this.mIsSmoothCorner = z;
    }

    private void drawSmoothCornerRect(Canvas canvas, float f, boolean z) {
        SemViewUtils.getSmoothCornerRectPath(this.mPath, f, this.mRect.left, this.mRect.top, this.mRect.width(), this.mRect.height());
        canvas.drawPath(this.mPath, this.mFillPaint);
        if (z) {
            canvas.drawPath(this.mPath, this.mStrokePaint);
        }
    }
}
