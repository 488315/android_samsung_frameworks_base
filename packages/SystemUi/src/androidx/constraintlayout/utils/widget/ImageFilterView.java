package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ImageFilterView extends AppCompatImageView {
    public Drawable mAltDrawable;
    public float mCrossfade;
    public final ImageMatrix mImageMatrix;
    public LayerDrawable mLayer;
    public final Drawable[] mLayers;
    public boolean mOverlay;
    public float mPanX;
    public float mPanY;
    public Path mPath;
    public RectF mRect;
    public float mRotate;
    public float mRound;
    public float mRoundPercent;
    public ViewOutlineProvider mViewOutlineProvider;
    public float mZoom;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ImageMatrix {

        /* renamed from: m */
        public final float[] f29m = new float[20];
        public final ColorMatrix mColorMatrix = new ColorMatrix();
        public final ColorMatrix mTmpColorMatrix = new ColorMatrix();
        public float mBrightness = 1.0f;
        public float mSaturation = 1.0f;
        public float mContrast = 1.0f;
        public float mWarmth = 1.0f;

        public final void updateMatrix(ImageView imageView) {
            boolean z;
            float log;
            float f;
            float f2;
            float f3;
            ColorMatrix colorMatrix = this.mColorMatrix;
            colorMatrix.reset();
            float f4 = this.mSaturation;
            float f5 = 1.0f;
            float[] fArr = this.f29m;
            boolean z2 = true;
            if (f4 != 1.0f) {
                float f6 = 1.0f - f4;
                float f7 = 0.2999f * f6;
                float f8 = 0.587f * f6;
                float f9 = f6 * 0.114f;
                fArr[0] = f7 + f4;
                fArr[1] = f8;
                fArr[2] = f9;
                fArr[3] = 0.0f;
                fArr[4] = 0.0f;
                fArr[5] = f7;
                fArr[6] = f8 + f4;
                fArr[7] = f9;
                fArr[8] = 0.0f;
                fArr[9] = 0.0f;
                fArr[10] = f7;
                fArr[11] = f8;
                fArr[12] = f9 + f4;
                fArr[13] = 0.0f;
                fArr[14] = 0.0f;
                fArr[15] = 0.0f;
                fArr[16] = 0.0f;
                fArr[17] = 0.0f;
                fArr[18] = 1.0f;
                fArr[19] = 0.0f;
                colorMatrix.set(fArr);
                z = true;
            } else {
                z = false;
            }
            float f10 = this.mContrast;
            ColorMatrix colorMatrix2 = this.mTmpColorMatrix;
            if (f10 != 1.0f) {
                colorMatrix2.setScale(f10, f10, f10, 1.0f);
                colorMatrix.postConcat(colorMatrix2);
                z = true;
            }
            float f11 = this.mWarmth;
            if (f11 != 1.0f) {
                if (f11 <= 0.0f) {
                    f11 = 0.01f;
                }
                float f12 = (5000.0f / f11) / 100.0f;
                if (f12 > 66.0f) {
                    double d = f12 - 60.0f;
                    f = ((float) Math.pow(d, -0.13320475816726685d)) * 329.69873f;
                    log = ((float) Math.pow(d, 0.07551484555006027d)) * 288.12216f;
                } else {
                    log = (((float) Math.log(f12)) * 99.4708f) - 161.11957f;
                    f = 255.0f;
                }
                if (f12 >= 66.0f) {
                    f2 = log;
                    f3 = 255.0f;
                } else if (f12 > 19.0f) {
                    f2 = log;
                    f3 = (((float) Math.log(f12 - 10.0f)) * 138.51773f) - 305.0448f;
                } else {
                    f2 = log;
                    f3 = 0.0f;
                }
                float min = Math.min(255.0f, Math.max(f, 0.0f));
                float min2 = Math.min(255.0f, Math.max(f2, 0.0f));
                float min3 = Math.min(255.0f, Math.max(f3, 0.0f));
                float log2 = (((float) Math.log(50.0f)) * 99.4708f) - 161.11957f;
                float log3 = (((float) Math.log(40.0f)) * 138.51773f) - 305.0448f;
                float min4 = Math.min(255.0f, Math.max(255.0f, 0.0f));
                float min5 = Math.min(255.0f, Math.max(log2, 0.0f));
                float min6 = min3 / Math.min(255.0f, Math.max(log3, 0.0f));
                fArr[0] = min / min4;
                fArr[1] = 0.0f;
                fArr[2] = 0.0f;
                fArr[3] = 0.0f;
                fArr[4] = 0.0f;
                fArr[5] = 0.0f;
                fArr[6] = min2 / min5;
                fArr[7] = 0.0f;
                fArr[8] = 0.0f;
                fArr[9] = 0.0f;
                fArr[10] = 0.0f;
                fArr[11] = 0.0f;
                fArr[12] = min6;
                fArr[13] = 0.0f;
                fArr[14] = 0.0f;
                fArr[15] = 0.0f;
                fArr[16] = 0.0f;
                fArr[17] = 0.0f;
                f5 = 1.0f;
                fArr[18] = 1.0f;
                fArr[19] = 0.0f;
                colorMatrix2.set(fArr);
                colorMatrix.postConcat(colorMatrix2);
                z = true;
            }
            float f13 = this.mBrightness;
            if (f13 != f5) {
                fArr[0] = f13;
                fArr[1] = 0.0f;
                fArr[2] = 0.0f;
                fArr[3] = 0.0f;
                fArr[4] = 0.0f;
                fArr[5] = 0.0f;
                fArr[6] = f13;
                fArr[7] = 0.0f;
                fArr[8] = 0.0f;
                fArr[9] = 0.0f;
                fArr[10] = 0.0f;
                fArr[11] = 0.0f;
                fArr[12] = f13;
                fArr[13] = 0.0f;
                fArr[14] = 0.0f;
                fArr[15] = 0.0f;
                fArr[16] = 0.0f;
                fArr[17] = 0.0f;
                fArr[18] = 1.0f;
                fArr[19] = 0.0f;
                colorMatrix2.set(fArr);
                colorMatrix.postConcat(colorMatrix2);
            } else {
                z2 = z;
            }
            if (z2) {
                imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            } else {
                imageView.clearColorFilter();
            }
        }
    }

    public ImageFilterView(Context context) {
        super(context);
        this.mImageMatrix = new ImageMatrix();
        this.mOverlay = true;
        this.mAltDrawable = null;
        this.mCrossfade = 0.0f;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        this.mLayers = new Drawable[2];
        this.mPanX = Float.NaN;
        this.mPanY = Float.NaN;
        this.mZoom = Float.NaN;
        this.mRotate = Float.NaN;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public final void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ImageFilterView);
            int indexCount = obtainStyledAttributes.getIndexCount();
            this.mAltDrawable = obtainStyledAttributes.getDrawable(0);
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 4) {
                    this.mCrossfade = obtainStyledAttributes.getFloat(index, 0.0f);
                } else if (index == 13) {
                    float f = obtainStyledAttributes.getFloat(index, 0.0f);
                    ImageMatrix imageMatrix = this.mImageMatrix;
                    imageMatrix.mWarmth = f;
                    imageMatrix.updateMatrix(this);
                } else if (index == 12) {
                    float f2 = obtainStyledAttributes.getFloat(index, 0.0f);
                    ImageMatrix imageMatrix2 = this.mImageMatrix;
                    imageMatrix2.mSaturation = f2;
                    imageMatrix2.updateMatrix(this);
                } else if (index == 3) {
                    float f3 = obtainStyledAttributes.getFloat(index, 0.0f);
                    ImageMatrix imageMatrix3 = this.mImageMatrix;
                    imageMatrix3.mContrast = f3;
                    imageMatrix3.updateMatrix(this);
                } else if (index == 2) {
                    float f4 = obtainStyledAttributes.getFloat(index, 0.0f);
                    ImageMatrix imageMatrix4 = this.mImageMatrix;
                    imageMatrix4.mBrightness = f4;
                    imageMatrix4.updateMatrix(this);
                } else if (index == 10) {
                    float dimension = obtainStyledAttributes.getDimension(index, 0.0f);
                    if (Float.isNaN(dimension)) {
                        this.mRound = dimension;
                        float f5 = this.mRoundPercent;
                        this.mRoundPercent = -1.0f;
                        setRoundPercent(f5);
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
                                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.ImageFilterView.2
                                    @Override // android.view.ViewOutlineProvider
                                    public final void getOutline(View view, Outline outline) {
                                        outline.setRoundRect(0, 0, ImageFilterView.this.getWidth(), ImageFilterView.this.getHeight(), ImageFilterView.this.mRound);
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
                            float f6 = this.mRound;
                            path.addRoundRect(rectF, f6, f6, Path.Direction.CW);
                        } else {
                            setClipToOutline(false);
                        }
                        if (z) {
                            invalidateOutline();
                        }
                    }
                } else if (index == 11) {
                    setRoundPercent(obtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == 9) {
                    this.mOverlay = obtainStyledAttributes.getBoolean(index, this.mOverlay);
                } else if (index == 5) {
                    this.mPanX = obtainStyledAttributes.getFloat(index, this.mPanX);
                    updateViewMatrix();
                } else if (index == 6) {
                    this.mPanY = obtainStyledAttributes.getFloat(index, this.mPanY);
                    updateViewMatrix();
                } else if (index == 7) {
                    this.mRotate = obtainStyledAttributes.getFloat(index, this.mRotate);
                    updateViewMatrix();
                } else if (index == 8) {
                    this.mZoom = obtainStyledAttributes.getFloat(index, this.mZoom);
                    updateViewMatrix();
                }
            }
            obtainStyledAttributes.recycle();
            Drawable drawable = getDrawable();
            if (this.mAltDrawable == null || drawable == null) {
                Drawable drawable2 = getDrawable();
                if (drawable2 != null) {
                    this.mLayers[0] = drawable2.mutate();
                    return;
                }
                return;
            }
            this.mLayers[0] = getDrawable().mutate();
            this.mLayers[1] = this.mAltDrawable.mutate();
            LayerDrawable layerDrawable = new LayerDrawable(this.mLayers);
            this.mLayer = layerDrawable;
            layerDrawable.getDrawable(1).setAlpha((int) (this.mCrossfade * 255.0f));
            if (!this.mOverlay) {
                this.mLayer.getDrawable(0).setAlpha((int) ((1.0f - this.mCrossfade) * 255.0f));
            }
            super.setImageDrawable(this.mLayer);
        }
    }

    @Override // android.view.View
    public final void layout(int i, int i2, int i3, int i4) {
        super.layout(i, i2, i3, i4);
        setMatrix();
    }

    public final void setCrossfade(float f) {
        this.mCrossfade = f;
        if (this.mLayers != null) {
            if (!this.mOverlay) {
                this.mLayer.getDrawable(0).setAlpha((int) ((1.0f - this.mCrossfade) * 255.0f));
            }
            this.mLayer.getDrawable(1).setAlpha((int) (this.mCrossfade * 255.0f));
            super.setImageDrawable(this.mLayer);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public final void setImageDrawable(Drawable drawable) {
        if (this.mAltDrawable == null || drawable == null) {
            super.setImageDrawable(drawable);
            return;
        }
        Drawable mutate = drawable.mutate();
        Drawable[] drawableArr = this.mLayers;
        drawableArr[0] = mutate;
        drawableArr[1] = this.mAltDrawable;
        LayerDrawable layerDrawable = new LayerDrawable(this.mLayers);
        this.mLayer = layerDrawable;
        super.setImageDrawable(layerDrawable);
        setCrossfade(this.mCrossfade);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public final void setImageResource(int i) {
        if (this.mAltDrawable == null) {
            super.setImageResource(i);
            return;
        }
        Drawable mutate = AppCompatResources.getDrawable(i, getContext()).mutate();
        Drawable[] drawableArr = this.mLayers;
        drawableArr[0] = mutate;
        drawableArr[1] = this.mAltDrawable;
        LayerDrawable layerDrawable = new LayerDrawable(this.mLayers);
        this.mLayer = layerDrawable;
        super.setImageDrawable(layerDrawable);
        setCrossfade(this.mCrossfade);
    }

    public final void setMatrix() {
        if (Float.isNaN(this.mPanX) && Float.isNaN(this.mPanY) && Float.isNaN(this.mZoom) && Float.isNaN(this.mRotate)) {
            return;
        }
        float f = Float.isNaN(this.mPanX) ? 0.0f : this.mPanX;
        float f2 = Float.isNaN(this.mPanY) ? 0.0f : this.mPanY;
        float f3 = Float.isNaN(this.mZoom) ? 1.0f : this.mZoom;
        float f4 = Float.isNaN(this.mRotate) ? 0.0f : this.mRotate;
        Matrix matrix = new Matrix();
        matrix.reset();
        float intrinsicWidth = getDrawable().getIntrinsicWidth();
        float intrinsicHeight = getDrawable().getIntrinsicHeight();
        float width = getWidth();
        float height = getHeight();
        float f5 = f3 * (intrinsicWidth * height < intrinsicHeight * width ? width / intrinsicWidth : height / intrinsicHeight);
        matrix.postScale(f5, f5);
        float f6 = intrinsicWidth * f5;
        float f7 = f5 * intrinsicHeight;
        matrix.postTranslate(((((width - f6) * f) + width) - f6) * 0.5f, ((((height - f7) * f2) + height) - f7) * 0.5f);
        matrix.postRotate(f4, width / 2.0f, height / 2.0f);
        setImageMatrix(matrix);
        setScaleType(ImageView.ScaleType.MATRIX);
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
                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.ImageFilterView.1
                    @Override // android.view.ViewOutlineProvider
                    public final void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, ImageFilterView.this.getWidth(), ImageFilterView.this.getHeight(), (Math.min(r3, r4) * ImageFilterView.this.mRoundPercent) / 2.0f);
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

    public final void updateViewMatrix() {
        if (Float.isNaN(this.mPanX) && Float.isNaN(this.mPanY) && Float.isNaN(this.mZoom) && Float.isNaN(this.mRotate)) {
            setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            setMatrix();
        }
    }

    public ImageFilterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mImageMatrix = new ImageMatrix();
        this.mOverlay = true;
        this.mAltDrawable = null;
        this.mCrossfade = 0.0f;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        this.mLayers = new Drawable[2];
        this.mPanX = Float.NaN;
        this.mPanY = Float.NaN;
        this.mZoom = Float.NaN;
        this.mRotate = Float.NaN;
        init(context, attributeSet);
    }

    public ImageFilterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mImageMatrix = new ImageMatrix();
        this.mOverlay = true;
        this.mAltDrawable = null;
        this.mCrossfade = 0.0f;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        this.mLayers = new Drawable[2];
        this.mPanX = Float.NaN;
        this.mPanY = Float.NaN;
        this.mZoom = Float.NaN;
        this.mRotate = Float.NaN;
        init(context, attributeSet);
    }
}
