package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.FloatLayout;
import androidx.constraintlayout.widget.R$styleable;
import com.android.systemui.R;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MotionLabel extends View implements FloatLayout {
    public boolean mAutoSize;
    public int mAutoSizeTextType;
    public float mBackgroundPanX;
    public float mBackgroundPanY;
    public float mBaseTextSize;
    public float mDeltaLeft;
    public float mFloatHeight;
    public float mFloatWidth;
    public String mFontFamily;
    public int mGravity;
    public boolean mNotBuilt;
    public Matrix mOutlinePositionMatrix;
    public int mPaddingBottom;
    public int mPaddingLeft;
    public int mPaddingRight;
    public int mPaddingTop;
    public final TextPaint mPaint;
    public Path mPath;
    public RectF mRect;
    public float mRotate;
    public float mRound;
    public float mRoundPercent;
    public int mStyleIndex;
    public Paint mTempPaint;
    public Rect mTempRect;
    public String mText;
    public Drawable mTextBackground;
    public Bitmap mTextBackgroundBitmap;
    public final Rect mTextBounds;
    public int mTextFillColor;
    public int mTextOutlineColor;
    public float mTextOutlineThickness;
    public float mTextPanX;
    public float mTextPanY;
    public BitmapShader mTextShader;
    public Matrix mTextShaderMatrix;
    public float mTextSize;
    public int mTextureEffect;
    public float mTextureHeight;
    public float mTextureWidth;
    public int mTypefaceIndex;
    public boolean mUseOutline;
    public ViewOutlineProvider mViewOutlineProvider;
    public float mZoom;
    public final Paint paintCache;
    public float paintTextSize;

    public MotionLabel(Context context) {
        super(context);
        this.mPaint = new TextPaint();
        this.mPath = new Path();
        this.mTextFillColor = CustomDeviceManager.QUICK_PANEL_ALL;
        this.mTextOutlineColor = CustomDeviceManager.QUICK_PANEL_ALL;
        this.mUseOutline = false;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        this.mTextSize = 48.0f;
        this.mBaseTextSize = Float.NaN;
        this.mTextOutlineThickness = 0.0f;
        this.mText = "Hello World";
        this.mNotBuilt = true;
        this.mTextBounds = new Rect();
        this.mPaddingLeft = 1;
        this.mPaddingRight = 1;
        this.mPaddingTop = 1;
        this.mPaddingBottom = 1;
        this.mGravity = 8388659;
        this.mAutoSizeTextType = 0;
        this.mAutoSize = false;
        this.mTextureHeight = Float.NaN;
        this.mTextureWidth = Float.NaN;
        this.mTextPanX = 0.0f;
        this.mTextPanY = 0.0f;
        this.paintCache = new Paint();
        this.mTextureEffect = 0;
        this.mBackgroundPanX = Float.NaN;
        this.mBackgroundPanY = Float.NaN;
        this.mZoom = Float.NaN;
        this.mRotate = Float.NaN;
        init(context, null);
    }

    public final void adjustTexture(float f, float f2, float f3, float f4) {
        if (this.mTextShaderMatrix == null) {
            return;
        }
        this.mFloatWidth = f3 - f;
        this.mFloatHeight = f4 - f2;
        float f5 = Float.isNaN(this.mBackgroundPanX) ? 0.0f : this.mBackgroundPanX;
        float f6 = Float.isNaN(this.mBackgroundPanY) ? 0.0f : this.mBackgroundPanY;
        float f7 = Float.isNaN(this.mZoom) ? 1.0f : this.mZoom;
        float f8 = Float.isNaN(this.mRotate) ? 0.0f : this.mRotate;
        this.mTextShaderMatrix.reset();
        float width = this.mTextBackgroundBitmap.getWidth();
        float height = this.mTextBackgroundBitmap.getHeight();
        float f9 = Float.isNaN(this.mTextureWidth) ? this.mFloatWidth : this.mTextureWidth;
        float f10 = Float.isNaN(this.mTextureHeight) ? this.mFloatHeight : this.mTextureHeight;
        float f11 = f7 * (width * f10 < height * f9 ? f9 / width : f10 / height);
        this.mTextShaderMatrix.postScale(f11, f11);
        float f12 = width * f11;
        float f13 = f9 - f12;
        float f14 = f11 * height;
        float f15 = f10 - f14;
        if (!Float.isNaN(this.mTextureHeight)) {
            f15 = this.mTextureHeight / 2.0f;
        }
        if (!Float.isNaN(this.mTextureWidth)) {
            f13 = this.mTextureWidth / 2.0f;
        }
        this.mTextShaderMatrix.postTranslate((((f5 * f13) + f9) - f12) * 0.5f, (((f6 * f15) + f10) - f14) * 0.5f);
        this.mTextShaderMatrix.postRotate(f8, f9 / 2.0f, f10 / 2.0f);
        this.mTextShader.setLocalMatrix(this.mTextShaderMatrix);
    }

    public final void buildShape(float f) {
        if (this.mUseOutline || f != 1.0f) {
            this.mPath.reset();
            String str = this.mText;
            int length = str.length();
            this.mPaint.getTextBounds(str, 0, length, this.mTextBounds);
            this.mPaint.getTextPath(str, 0, length, 0.0f, 0.0f, this.mPath);
            if (f != 1.0f) {
                Debug.getLoc();
                Matrix matrix = new Matrix();
                matrix.postScale(f, f);
                this.mPath.transform(matrix);
            }
            Rect rect = this.mTextBounds;
            rect.right--;
            rect.left++;
            rect.bottom++;
            rect.top--;
            RectF rectF = new RectF();
            rectF.bottom = getHeight();
            rectF.right = getWidth();
            this.mNotBuilt = false;
        }
    }

    public final float getHorizontalOffset() {
        float f = Float.isNaN(this.mBaseTextSize) ? 1.0f : this.mTextSize / this.mBaseTextSize;
        TextPaint textPaint = this.mPaint;
        String str = this.mText;
        return ((this.mTextPanX + 1.0f) * ((((Float.isNaN(this.mFloatWidth) ? getMeasuredWidth() : this.mFloatWidth) - getPaddingLeft()) - getPaddingRight()) - (textPaint.measureText(str, 0, str.length()) * f))) / 2.0f;
    }

    public final float getVerticalOffset() {
        float f = Float.isNaN(this.mBaseTextSize) ? 1.0f : this.mTextSize / this.mBaseTextSize;
        Paint.FontMetrics fontMetrics = this.mPaint.getFontMetrics();
        float measuredHeight = ((Float.isNaN(this.mFloatHeight) ? getMeasuredHeight() : this.mFloatHeight) - getPaddingTop()) - getPaddingBottom();
        float f2 = fontMetrics.descent;
        float f3 = fontMetrics.ascent;
        return (((1.0f - this.mTextPanY) * (measuredHeight - ((f2 - f3) * f))) / 2.0f) - (f * f3);
    }

    /* JADX WARN: Removed duplicated region for block: B:185:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x038c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void init(Context context, AttributeSet attributeSet) {
        Typeface typeface;
        float f;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        TextPaint textPaint = this.mPaint;
        int i = typedValue.data;
        this.mTextFillColor = i;
        textPaint.setColor(i);
        int i2 = 4;
        int i3 = 2;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.MotionLabel);
            int indexCount = obtainStyledAttributes.getIndexCount();
            int i4 = 0;
            while (i4 < indexCount) {
                int index = obtainStyledAttributes.getIndex(i4);
                if (index == 5) {
                    this.mText = obtainStyledAttributes.getText(index).toString();
                    invalidate();
                } else if (index == 7) {
                    this.mFontFamily = obtainStyledAttributes.getString(index);
                } else if (index == 11) {
                    this.mBaseTextSize = obtainStyledAttributes.getDimensionPixelSize(index, (int) this.mBaseTextSize);
                } else if (index == 0) {
                    this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(index, (int) this.mTextSize);
                } else if (index == i3) {
                    this.mStyleIndex = obtainStyledAttributes.getInt(index, this.mStyleIndex);
                } else if (index == 1) {
                    this.mTypefaceIndex = obtainStyledAttributes.getInt(index, this.mTypefaceIndex);
                } else if (index == 3) {
                    this.mTextFillColor = obtainStyledAttributes.getColor(index, this.mTextFillColor);
                } else if (index == 9) {
                    float dimension = obtainStyledAttributes.getDimension(index, this.mRound);
                    this.mRound = dimension;
                    if (Float.isNaN(dimension)) {
                        this.mRound = dimension;
                        float f2 = this.mRoundPercent;
                        this.mRoundPercent = -1.0f;
                        setRoundPercent(f2);
                    } else {
                        boolean z = this.mRound != dimension;
                        this.mRound = dimension;
                        if (dimension != 0.0f) {
                            if (this.mPath == null) {
                                this.mPath = new Path();
                            }
                            if (this.mRect == null) {
                                this.mRect = new RectF();
                            }
                            if (this.mViewOutlineProvider == null) {
                                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.MotionLabel.2
                                    @Override // android.view.ViewOutlineProvider
                                    public final void getOutline(View view, Outline outline) {
                                        outline.setRoundRect(0, 0, MotionLabel.this.getWidth(), MotionLabel.this.getHeight(), MotionLabel.this.mRound);
                                    }
                                };
                                this.mViewOutlineProvider = viewOutlineProvider;
                                setOutlineProvider(viewOutlineProvider);
                            }
                            setClipToOutline(true);
                            this.mRect.set(0.0f, 0.0f, getWidth(), getHeight());
                            this.mPath.reset();
                            Path path = this.mPath;
                            RectF rectF = this.mRect;
                            float f3 = this.mRound;
                            path.addRoundRect(rectF, f3, f3, Path.Direction.CW);
                        } else {
                            setClipToOutline(false);
                        }
                        if (z) {
                            invalidateOutline();
                        }
                    }
                } else if (index == 10) {
                    float f4 = obtainStyledAttributes.getFloat(index, this.mRoundPercent);
                    this.mRoundPercent = f4;
                    setRoundPercent(f4);
                } else if (index == i2) {
                    int i5 = obtainStyledAttributes.getInt(index, -1);
                    if ((i5 & 8388615) == 0) {
                        i5 |= 8388611;
                    }
                    if ((i5 & 112) == 0) {
                        i5 |= 48;
                    }
                    if (i5 != this.mGravity) {
                        invalidate();
                    }
                    this.mGravity = i5;
                    int i6 = i5 & 112;
                    if (i6 == 48) {
                        this.mTextPanY = -1.0f;
                    } else if (i6 != 80) {
                        this.mTextPanY = 0.0f;
                    } else {
                        this.mTextPanY = 1.0f;
                    }
                    int i7 = i5 & 8388615;
                    if (i7 != 3) {
                        if (i7 != 5) {
                            if (i7 != 8388611) {
                                if (i7 != 8388613) {
                                    this.mTextPanX = 0.0f;
                                }
                            }
                        }
                        this.mTextPanX = 1.0f;
                    }
                    this.mTextPanX = -1.0f;
                } else if (index == 8) {
                    this.mAutoSizeTextType = obtainStyledAttributes.getInt(index, 0);
                } else if (index == 17) {
                    this.mTextOutlineColor = obtainStyledAttributes.getInt(index, this.mTextOutlineColor);
                    this.mUseOutline = true;
                } else if (index == 18) {
                    this.mTextOutlineThickness = obtainStyledAttributes.getDimension(index, this.mTextOutlineThickness);
                    this.mUseOutline = true;
                } else if (index == 12) {
                    this.mTextBackground = obtainStyledAttributes.getDrawable(index);
                    this.mUseOutline = true;
                } else if (index == 13) {
                    this.mBackgroundPanX = obtainStyledAttributes.getFloat(index, this.mBackgroundPanX);
                } else if (index == 14) {
                    this.mBackgroundPanY = obtainStyledAttributes.getFloat(index, this.mBackgroundPanY);
                } else if (index == 19) {
                    this.mTextPanX = obtainStyledAttributes.getFloat(index, this.mTextPanX);
                } else if (index == 20) {
                    this.mTextPanY = obtainStyledAttributes.getFloat(index, this.mTextPanY);
                } else if (index == 15) {
                    this.mRotate = obtainStyledAttributes.getFloat(index, this.mRotate);
                } else if (index == 16) {
                    this.mZoom = obtainStyledAttributes.getFloat(index, this.mZoom);
                } else if (index == 23) {
                    this.mTextureHeight = obtainStyledAttributes.getDimension(index, this.mTextureHeight);
                } else if (index == 24) {
                    this.mTextureWidth = obtainStyledAttributes.getDimension(index, this.mTextureWidth);
                } else if (index == 22) {
                    this.mTextureEffect = obtainStyledAttributes.getInt(index, this.mTextureEffect);
                }
                i4++;
                i2 = 4;
                i3 = 2;
            }
            obtainStyledAttributes.recycle();
        }
        if (this.mTextBackground != null) {
            this.mTextShaderMatrix = new Matrix();
            int intrinsicWidth = this.mTextBackground.getIntrinsicWidth();
            int intrinsicHeight = this.mTextBackground.getIntrinsicHeight();
            if (intrinsicWidth <= 0 && (intrinsicWidth = getWidth()) == 0) {
                intrinsicWidth = Float.isNaN(this.mTextureWidth) ? 128 : (int) this.mTextureWidth;
            }
            if (intrinsicHeight <= 0 && (intrinsicHeight = getHeight()) == 0) {
                intrinsicHeight = Float.isNaN(this.mTextureHeight) ? 128 : (int) this.mTextureHeight;
            }
            if (this.mTextureEffect != 0) {
                intrinsicWidth /= 2;
                intrinsicHeight /= 2;
            }
            this.mTextBackgroundBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(this.mTextBackgroundBitmap);
            this.mTextBackground.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            this.mTextBackground.setFilterBitmap(true);
            this.mTextBackground.draw(canvas);
            if (this.mTextureEffect != 0) {
                Bitmap bitmap = this.mTextBackgroundBitmap;
                System.nanoTime();
                int width = bitmap.getWidth() / 2;
                int height = bitmap.getHeight() / 2;
                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                for (int i8 = 0; i8 < 4 && width >= 32 && height >= 32; i8++) {
                    width /= 2;
                    height /= 2;
                    createScaledBitmap = Bitmap.createScaledBitmap(createScaledBitmap, width, height, true);
                }
                this.mTextBackgroundBitmap = createScaledBitmap;
            }
            Bitmap bitmap2 = this.mTextBackgroundBitmap;
            Shader.TileMode tileMode = Shader.TileMode.REPEAT;
            this.mTextShader = new BitmapShader(bitmap2, tileMode, tileMode);
        }
        this.mPaddingLeft = getPaddingLeft();
        this.mPaddingRight = getPaddingRight();
        this.mPaddingTop = getPaddingTop();
        this.mPaddingBottom = getPaddingBottom();
        String str = this.mFontFamily;
        int i9 = this.mTypefaceIndex;
        int i10 = this.mStyleIndex;
        if (str != null) {
            typeface = Typeface.create(str, i10);
            if (typeface != null) {
                setTypeface(typeface);
                this.mPaint.setColor(this.mTextFillColor);
                this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
                this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                this.mPaint.setFlags(128);
                float f5 = this.mTextSize;
                this.mTextSize = f5;
                Debug.getLoc();
                f = this.mBaseTextSize;
                TextPaint textPaint2 = this.mPaint;
                if (!Float.isNaN(f)) {
                    f5 = this.mBaseTextSize;
                }
                textPaint2.setTextSize(f5);
                buildShape(Float.isNaN(this.mBaseTextSize) ? 1.0f : this.mTextSize / this.mBaseTextSize);
                requestLayout();
                invalidate();
                this.mPaint.setAntiAlias(true);
            }
        } else {
            typeface = null;
        }
        if (i9 == 1) {
            typeface = Typeface.SANS_SERIF;
        } else if (i9 == 2) {
            typeface = Typeface.SERIF;
        } else if (i9 == 3) {
            typeface = Typeface.MONOSPACE;
        }
        if (i10 > 0) {
            Typeface defaultFromStyle = typeface == null ? Typeface.defaultFromStyle(i10) : Typeface.create(typeface, i10);
            setTypeface(defaultFromStyle);
            int i11 = (~(defaultFromStyle != null ? defaultFromStyle.getStyle() : 0)) & i10;
            this.mPaint.setFakeBoldText((i11 & 1) != 0);
            this.mPaint.setTextSkewX((i11 & 2) != 0 ? -0.25f : 0.0f);
        } else {
            this.mPaint.setFakeBoldText(false);
            this.mPaint.setTextSkewX(0.0f);
            setTypeface(typeface);
        }
        this.mPaint.setColor(this.mTextFillColor);
        this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
        this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPaint.setFlags(128);
        float f52 = this.mTextSize;
        this.mTextSize = f52;
        Debug.getLoc();
        f = this.mBaseTextSize;
        TextPaint textPaint22 = this.mPaint;
        if (!Float.isNaN(f)) {
        }
        textPaint22.setTextSize(f52);
        buildShape(Float.isNaN(this.mBaseTextSize) ? 1.0f : this.mTextSize / this.mBaseTextSize);
        requestLayout();
        invalidate();
        this.mPaint.setAntiAlias(true);
    }

    @Override // android.view.View
    public final void layout(int i, int i2, int i3, int i4) {
        super.layout(i, i2, i3, i4);
        boolean isNaN = Float.isNaN(this.mBaseTextSize);
        float f = isNaN ? 1.0f : this.mTextSize / this.mBaseTextSize;
        this.mFloatWidth = i3 - i;
        this.mFloatHeight = i4 - i2;
        if (this.mAutoSize) {
            if (this.mTempRect == null) {
                this.mTempPaint = new Paint();
                this.mTempRect = new Rect();
                this.mTempPaint.set(this.mPaint);
                this.paintTextSize = this.mTempPaint.getTextSize();
            }
            Paint paint = this.mTempPaint;
            String str = this.mText;
            paint.getTextBounds(str, 0, str.length(), this.mTempRect);
            int width = this.mTempRect.width();
            int height = (int) (this.mTempRect.height() * 1.3f);
            float f2 = (this.mFloatWidth - this.mPaddingRight) - this.mPaddingLeft;
            float f3 = (this.mFloatHeight - this.mPaddingBottom) - this.mPaddingTop;
            if (isNaN) {
                float f4 = width;
                float f5 = height;
                if (f4 * f3 > f5 * f2) {
                    this.mPaint.setTextSize((this.paintTextSize * f2) / f4);
                } else {
                    this.mPaint.setTextSize((this.paintTextSize * f3) / f5);
                }
            } else {
                float f6 = width;
                float f7 = height;
                f = f6 * f3 > f7 * f2 ? f2 / f6 : f3 / f7;
            }
        }
        if (this.mUseOutline || !isNaN) {
            adjustTexture(i, i2, i3, i4);
            buildShape(f);
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        float f = Float.isNaN(this.mBaseTextSize) ? 1.0f : this.mTextSize / this.mBaseTextSize;
        super.onDraw(canvas);
        if (!this.mUseOutline && f == 1.0f) {
            canvas.drawText(this.mText, this.mDeltaLeft + getHorizontalOffset() + this.mPaddingLeft, getVerticalOffset() + this.mPaddingTop, this.mPaint);
            return;
        }
        if (this.mNotBuilt) {
            buildShape(f);
        }
        if (this.mOutlinePositionMatrix == null) {
            this.mOutlinePositionMatrix = new Matrix();
        }
        if (!this.mUseOutline) {
            float horizontalOffset = getHorizontalOffset() + this.mPaddingLeft;
            float verticalOffset = getVerticalOffset() + this.mPaddingTop;
            this.mOutlinePositionMatrix.reset();
            this.mOutlinePositionMatrix.preTranslate(horizontalOffset, verticalOffset);
            this.mPath.transform(this.mOutlinePositionMatrix);
            this.mPaint.setColor(this.mTextFillColor);
            this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
            canvas.drawPath(this.mPath, this.mPaint);
            this.mOutlinePositionMatrix.reset();
            this.mOutlinePositionMatrix.preTranslate(-horizontalOffset, -verticalOffset);
            this.mPath.transform(this.mOutlinePositionMatrix);
            return;
        }
        this.paintCache.set(this.mPaint);
        this.mOutlinePositionMatrix.reset();
        float horizontalOffset2 = getHorizontalOffset() + this.mPaddingLeft;
        float verticalOffset2 = getVerticalOffset() + this.mPaddingTop;
        this.mOutlinePositionMatrix.postTranslate(horizontalOffset2, verticalOffset2);
        this.mOutlinePositionMatrix.preScale(f, f);
        this.mPath.transform(this.mOutlinePositionMatrix);
        if (this.mTextShader != null) {
            this.mPaint.setFilterBitmap(true);
            this.mPaint.setShader(this.mTextShader);
        } else {
            this.mPaint.setColor(this.mTextFillColor);
        }
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
        canvas.drawPath(this.mPath, this.mPaint);
        if (this.mTextShader != null) {
            this.mPaint.setShader(null);
        }
        this.mPaint.setColor(this.mTextOutlineColor);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
        canvas.drawPath(this.mPath, this.mPaint);
        this.mOutlinePositionMatrix.reset();
        this.mOutlinePositionMatrix.postTranslate(-horizontalOffset2, -verticalOffset2);
        this.mPath.transform(this.mOutlinePositionMatrix);
        this.mPaint.set(this.paintCache);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        this.mAutoSize = false;
        this.mPaddingLeft = getPaddingLeft();
        this.mPaddingRight = getPaddingRight();
        this.mPaddingTop = getPaddingTop();
        this.mPaddingBottom = getPaddingBottom();
        if (mode != 1073741824 || mode2 != 1073741824) {
            TextPaint textPaint = this.mPaint;
            String str = this.mText;
            textPaint.getTextBounds(str, 0, str.length(), this.mTextBounds);
            if (mode != 1073741824) {
                size = (int) (this.mTextBounds.width() + 0.99999f);
            }
            size += this.mPaddingLeft + this.mPaddingRight;
            if (mode2 != 1073741824) {
                int fontMetricsInt = (int) (this.mPaint.getFontMetricsInt(null) + 0.99999f);
                if (mode2 == Integer.MIN_VALUE) {
                    fontMetricsInt = Math.min(size2, fontMetricsInt);
                }
                size2 = this.mPaddingTop + this.mPaddingBottom + fontMetricsInt;
            }
        } else if (this.mAutoSizeTextType != 0) {
            this.mAutoSize = true;
        }
        setMeasuredDimension(size, size2);
    }

    public final void setRoundPercent(float f) {
        boolean z = this.mRoundPercent != f;
        this.mRoundPercent = f;
        if (f != 0.0f) {
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            if (this.mRect == null) {
                this.mRect = new RectF();
            }
            if (this.mViewOutlineProvider == null) {
                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.MotionLabel.1
                    @Override // android.view.ViewOutlineProvider
                    public final void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, MotionLabel.this.getWidth(), MotionLabel.this.getHeight(), (Math.min(r3, r4) * MotionLabel.this.mRoundPercent) / 2.0f);
                    }
                };
                this.mViewOutlineProvider = viewOutlineProvider;
                setOutlineProvider(viewOutlineProvider);
            }
            setClipToOutline(true);
            int width = getWidth();
            int height = getHeight();
            float min = (Math.min(width, height) * this.mRoundPercent) / 2.0f;
            this.mRect.set(0.0f, 0.0f, width, height);
            this.mPath.reset();
            this.mPath.addRoundRect(this.mRect, min, min, Path.Direction.CW);
        } else {
            setClipToOutline(false);
        }
        if (z) {
            invalidateOutline();
        }
    }

    public final void setTypeface(Typeface typeface) {
        if (this.mPaint.getTypeface() != typeface) {
            this.mPaint.setTypeface(typeface);
        }
    }

    public final void layout(float f, float f2, float f3, float f4) {
        int i = (int) (f + 0.5f);
        this.mDeltaLeft = f - i;
        int i2 = (int) (f3 + 0.5f);
        int i3 = i2 - i;
        int i4 = (int) (f4 + 0.5f);
        int i5 = (int) (0.5f + f2);
        int i6 = i4 - i5;
        float f5 = f3 - f;
        this.mFloatWidth = f5;
        float f6 = f4 - f2;
        this.mFloatHeight = f6;
        adjustTexture(f, f2, f3, f4);
        if (getMeasuredHeight() == i6 && getMeasuredWidth() == i3) {
            super.layout(i, i5, i2, i4);
        } else {
            measure(View.MeasureSpec.makeMeasureSpec(i3, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(i6, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
            super.layout(i, i5, i2, i4);
        }
        if (this.mAutoSize) {
            if (this.mTempRect == null) {
                this.mTempPaint = new Paint();
                this.mTempRect = new Rect();
                this.mTempPaint.set(this.mPaint);
                this.paintTextSize = this.mTempPaint.getTextSize();
            }
            this.mFloatWidth = f5;
            this.mFloatHeight = f6;
            Paint paint = this.mTempPaint;
            String str = this.mText;
            paint.getTextBounds(str, 0, str.length(), this.mTempRect);
            float height = this.mTempRect.height() * 1.3f;
            float f7 = (f5 - this.mPaddingRight) - this.mPaddingLeft;
            float f8 = (f6 - this.mPaddingBottom) - this.mPaddingTop;
            float width = this.mTempRect.width();
            if (width * f8 > height * f7) {
                this.mPaint.setTextSize((this.paintTextSize * f7) / width);
            } else {
                this.mPaint.setTextSize((this.paintTextSize * f8) / height);
            }
            if (this.mUseOutline || !Float.isNaN(this.mBaseTextSize)) {
                buildShape(Float.isNaN(this.mBaseTextSize) ? 1.0f : this.mTextSize / this.mBaseTextSize);
            }
        }
    }

    public MotionLabel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaint = new TextPaint();
        this.mPath = new Path();
        this.mTextFillColor = CustomDeviceManager.QUICK_PANEL_ALL;
        this.mTextOutlineColor = CustomDeviceManager.QUICK_PANEL_ALL;
        this.mUseOutline = false;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        this.mTextSize = 48.0f;
        this.mBaseTextSize = Float.NaN;
        this.mTextOutlineThickness = 0.0f;
        this.mText = "Hello World";
        this.mNotBuilt = true;
        this.mTextBounds = new Rect();
        this.mPaddingLeft = 1;
        this.mPaddingRight = 1;
        this.mPaddingTop = 1;
        this.mPaddingBottom = 1;
        this.mGravity = 8388659;
        this.mAutoSizeTextType = 0;
        this.mAutoSize = false;
        this.mTextureHeight = Float.NaN;
        this.mTextureWidth = Float.NaN;
        this.mTextPanX = 0.0f;
        this.mTextPanY = 0.0f;
        this.paintCache = new Paint();
        this.mTextureEffect = 0;
        this.mBackgroundPanX = Float.NaN;
        this.mBackgroundPanY = Float.NaN;
        this.mZoom = Float.NaN;
        this.mRotate = Float.NaN;
        init(context, attributeSet);
    }

    public MotionLabel(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaint = new TextPaint();
        this.mPath = new Path();
        this.mTextFillColor = CustomDeviceManager.QUICK_PANEL_ALL;
        this.mTextOutlineColor = CustomDeviceManager.QUICK_PANEL_ALL;
        this.mUseOutline = false;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        this.mTextSize = 48.0f;
        this.mBaseTextSize = Float.NaN;
        this.mTextOutlineThickness = 0.0f;
        this.mText = "Hello World";
        this.mNotBuilt = true;
        this.mTextBounds = new Rect();
        this.mPaddingLeft = 1;
        this.mPaddingRight = 1;
        this.mPaddingTop = 1;
        this.mPaddingBottom = 1;
        this.mGravity = 8388659;
        this.mAutoSizeTextType = 0;
        this.mAutoSize = false;
        this.mTextureHeight = Float.NaN;
        this.mTextureWidth = Float.NaN;
        this.mTextPanX = 0.0f;
        this.mTextPanY = 0.0f;
        this.paintCache = new Paint();
        this.mTextureEffect = 0;
        this.mBackgroundPanX = Float.NaN;
        this.mBackgroundPanY = Float.NaN;
        this.mZoom = Float.NaN;
        this.mRotate = Float.NaN;
        init(context, attributeSet);
    }
}
