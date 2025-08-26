package com.android.launcher3.icons;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.SparseArray;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.ClockDrawableWrapper;
import com.android.launcher3.util.FlagOp;
import com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda0;
import com.android.launcher3.util.UserIconInfo;
import com.android.systemui.R;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.nio.ByteBuffer;
import java.util.Calendar;

/* loaded from: classes.dex */
public class BaseIconFactory implements AutoCloseable {
    public static final float LEGACY_ICON_SCALE = (1.0f / ((AdaptiveIconDrawable.getExtraInsetFraction() * 2.0f) + 1.0f)) * 0.7f;
    public final Canvas mCanvas;
    public final Context mContext;
    public final int mIconBitmapSize;
    public final Rect mOldBounds;
    public ShadowGenerator mShadowGenerator;
    public int mWrapperBackgroundColor;

    public class EmptyWrapper extends DrawableWrapper {
        public EmptyWrapper() {
            super(new ColorDrawable());
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public final Drawable.ConstantState getConstantState() {
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return null;
            }
            return drawable.getConstantState();
        }
    }

    public class IconOptions {
        public Integer mExtractedColor;
        public int mGenerationMode = 2;
        public UserIconInfo mUserIconInfo;
    }

    static {
        Color.rgb(IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode, IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode, IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode);
    }

    public BaseIconFactory(Context context, int i, int i2, boolean z) {
        this(context, i, i2);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mWrapperBackgroundColor = -1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final BitmapInfo createBadgedIconBitmap(Drawable drawable, IconOptions iconOptions) {
        final int i;
        FlagOp flagOp;
        final int i2 = 0;
        final int i3 = 1;
        float[] fArr = new float[1];
        AdaptiveIconDrawable adaptiveIconDrawableNormalizeAndWrapToAdaptiveIcon = normalizeAndWrapToAdaptiveIcon(drawable, fArr);
        Bitmap bitmapCreateIconBitmap = createIconBitmap(adaptiveIconDrawableNormalizeAndWrapToAdaptiveIcon, fArr[0], iconOptions.mGenerationMode);
        Integer num = iconOptions.mExtractedColor;
        int iIntValue = num != null ? num.intValue() : ColorExtractor.findDominantColorByHue(bitmapCreateIconBitmap);
        BitmapInfo bitmapInfo = new BitmapInfo(bitmapCreateIconBitmap, iIntValue);
        if (adaptiveIconDrawableNormalizeAndWrapToAdaptiveIcon instanceof BitmapInfo.Extender) {
            float f = fArr[0];
            ClockDrawableWrapper clockDrawableWrapper = (ClockDrawableWrapper) ((BitmapInfo.Extender) adaptiveIconDrawableNormalizeAndWrapToAdaptiveIcon);
            clockDrawableWrapper.getClass();
            i = 4;
            bitmapInfo = new ClockDrawableWrapper.ClockBitmapInfo(bitmapCreateIconBitmap, iIntValue, f, clockDrawableWrapper.mAnimationInfo, createScaledBitmap(new AdaptiveIconDrawable(clockDrawableWrapper.getBackground().getConstantState().newDrawable(), null), 4), null, null);
        } else {
            i = 4;
            boolean z = IconProvider.ATLEAST_T;
        }
        final FlagOp$$ExternalSyntheticLambda0 flagOp$$ExternalSyntheticLambda0 = FlagOp.NO_OP;
        UserIconInfo userIconInfo = iconOptions.mUserIconInfo;
        if (userIconInfo != null) {
            int i4 = userIconInfo.type;
            final FlagOp flagOp2 = i4 == 1 ? new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1
                @Override // com.android.launcher3.util.FlagOp
                public final int apply(int i5) {
                    switch (i2) {
                        case 0:
                            return i3 | flagOp$$ExternalSyntheticLambda0.apply(i5);
                        default:
                            return (~i3) & flagOp$$ExternalSyntheticLambda0.apply(i5);
                    }
                }
            } : new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1
                @Override // com.android.launcher3.util.FlagOp
                public final int apply(int i5) {
                    switch (i3) {
                        case 0:
                            return i3 | flagOp$$ExternalSyntheticLambda0.apply(i5);
                        default:
                            return (~i3) & flagOp$$ExternalSyntheticLambda0.apply(i5);
                    }
                }
            };
            final FlagOp flagOp3 = i4 == 2 ? new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1
                @Override // com.android.launcher3.util.FlagOp
                public final int apply(int i5) {
                    switch (i2) {
                        case 0:
                            return i | flagOp2.apply(i5);
                        default:
                            return (~i) & flagOp2.apply(i5);
                    }
                }
            } : new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1
                @Override // com.android.launcher3.util.FlagOp
                public final int apply(int i5) {
                    switch (i3) {
                        case 0:
                            return i | flagOp2.apply(i5);
                        default:
                            return (~i) & flagOp2.apply(i5);
                    }
                }
            };
            boolean z2 = i4 == 3;
            final int i5 = 8;
            flagOp = z2 ? new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1
                @Override // com.android.launcher3.util.FlagOp
                public final int apply(int i52) {
                    switch (i2) {
                        case 0:
                            return i5 | flagOp3.apply(i52);
                        default:
                            return (~i5) & flagOp3.apply(i52);
                    }
                }
            } : new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1
                @Override // com.android.launcher3.util.FlagOp
                public final int apply(int i52) {
                    switch (i3) {
                        case 0:
                            return i5 | flagOp3.apply(i52);
                        default:
                            return (~i5) & flagOp3.apply(i52);
                    }
                }
            };
        } else {
            flagOp = flagOp$$ExternalSyntheticLambda0;
        }
        if (flagOp == flagOp$$ExternalSyntheticLambda0) {
            return bitmapInfo;
        }
        BitmapInfo bitmapInfoClone = bitmapInfo.mo974clone();
        bitmapInfoClone.flags = flagOp.apply(bitmapInfoClone.flags);
        return bitmapInfoClone;
    }

    public final BitmapInfo createIconBitmap(Bitmap bitmap) {
        if (this.mIconBitmapSize != bitmap.getWidth() || this.mIconBitmapSize != bitmap.getHeight()) {
            bitmap = createIconBitmap(new BitmapDrawable(this.mContext.getResources(), bitmap), 1.0f, 0);
        }
        return new BitmapInfo(bitmap, ColorExtractor.findDominantColorByHue(bitmap));
    }

    public final Bitmap createScaledBitmap(Drawable drawable, int i) {
        float[] fArr = new float[1];
        return createIconBitmap(normalizeAndWrapToAdaptiveIcon(drawable, fArr), Math.min(fArr[0], 0.93f), i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x014c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void drawIconBitmap(Canvas canvas, Drawable drawable, float f, int i, Bitmap bitmap) {
        int i2;
        int i3;
        ShadowGenerator shadowGenerator;
        BitmapDrawable bitmapDrawable;
        Bitmap bitmap2;
        int i4 = this.mIconBitmapSize;
        this.mOldBounds.set(drawable.getBounds());
        if (drawable instanceof AdaptiveIconDrawable) {
            AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable;
            int iMax = Math.max((int) Math.ceil(0.035f * r1), Math.round(((1.0f - f) * i4) / 2.0f));
            int i5 = i4 - (iMax * 2);
            drawable.setBounds(0, 0, i5, i5);
            drawable.getBounds();
            Path iconMask = adaptiveIconDrawable.getIconMask();
            int iSave = canvas.save();
            float f2 = iMax;
            canvas.translate(f2, f2);
            if (i == 2 || i == 4) {
                if (this.mShadowGenerator == null) {
                    this.mShadowGenerator = new ShadowGenerator(this.mIconBitmapSize);
                }
                ShadowGenerator shadowGenerator2 = this.mShadowGenerator;
                shadowGenerator2.mDrawPaint.setMaskFilter(shadowGenerator2.mDefaultBlurMaskFilter);
                shadowGenerator2.mDrawPaint.setAlpha(25);
                canvas.drawPath(iconMask, shadowGenerator2.mDrawPaint);
                int iSave2 = canvas.save();
                shadowGenerator2.mDrawPaint.setAlpha(7);
                canvas.translate(0.0f, shadowGenerator2.mIconSize * 0.020833334f);
                canvas.drawPath(iconMask, shadowGenerator2.mDrawPaint);
                canvas.restoreToCount(iSave2);
                shadowGenerator2.mDrawPaint.setMaskFilter(null);
            }
            if (drawable instanceof BitmapInfo.Extender) {
                ClockDrawableWrapper clockDrawableWrapper = (ClockDrawableWrapper) ((BitmapInfo.Extender) drawable);
                LayerDrawable layerDrawable = (LayerDrawable) clockDrawableWrapper.getForeground();
                int i6 = clockDrawableWrapper.mAnimationInfo.hourLayerIndex;
                if (i6 != -1) {
                    layerDrawable.getDrawable(i6).setLevel(0);
                }
                int i7 = clockDrawableWrapper.mAnimationInfo.minuteLayerIndex;
                if (i7 != -1) {
                    layerDrawable.getDrawable(i7).setLevel(0);
                }
                int i8 = clockDrawableWrapper.mAnimationInfo.secondLayerIndex;
                if (i8 != -1) {
                    layerDrawable.getDrawable(i8).setLevel(0);
                }
                clockDrawableWrapper.draw(canvas);
                clockDrawableWrapper.mAnimationInfo.applyTime(Calendar.getInstance(), (LayerDrawable) clockDrawableWrapper.getForeground());
            } else {
                adaptiveIconDrawable.draw(canvas);
            }
            canvas.restoreToCount(iSave);
        } else {
            if ((drawable instanceof BitmapDrawable) && (bitmap2 = (bitmapDrawable = (BitmapDrawable) drawable).getBitmap()) != null && bitmap2.getDensity() == 0) {
                bitmapDrawable.setTargetDensity(this.mContext.getResources().getDisplayMetrics());
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                i2 = i4;
                i3 = i2;
                int i9 = (i4 - i2) / 2;
                int i10 = (i4 - i3) / 2;
                drawable.setBounds(i9, i10, i2 + i9, i3 + i10);
                canvas.save();
                float f3 = i4 / 2;
                canvas.scale(f, f, f3, f3);
                drawable.draw(canvas);
                canvas.restore();
                if (i == 2 && bitmap != null) {
                    if (this.mShadowGenerator == null) {
                        this.mShadowGenerator = new ShadowGenerator(this.mIconBitmapSize);
                    }
                    shadowGenerator = this.mShadowGenerator;
                    synchronized (shadowGenerator) {
                        shadowGenerator.mBlurPaint.setMaskFilter(shadowGenerator.mDefaultBlurMaskFilter);
                        Bitmap bitmapExtractAlpha = bitmap.extractAlpha(shadowGenerator.mBlurPaint, new int[2]);
                        shadowGenerator.mDrawPaint.setAlpha(25);
                        canvas.drawBitmap(bitmapExtractAlpha, r1[0], r1[1], shadowGenerator.mDrawPaint);
                        shadowGenerator.mDrawPaint.setAlpha(7);
                        canvas.drawBitmap(bitmapExtractAlpha, r1[0], (shadowGenerator.mIconSize * 0.020833334f) + r1[1], shadowGenerator.mDrawPaint);
                    }
                    canvas.save();
                    canvas.scale(f, f, f3, f3);
                    drawable.draw(canvas);
                    canvas.restore();
                }
            } else {
                float f4 = intrinsicWidth / intrinsicHeight;
                if (intrinsicWidth > intrinsicHeight) {
                    i3 = (int) (i4 / f4);
                    i2 = i4;
                } else if (intrinsicHeight > intrinsicWidth) {
                    i2 = (int) (i4 * f4);
                    i3 = i4;
                }
                int i92 = (i4 - i2) / 2;
                int i102 = (i4 - i3) / 2;
                drawable.setBounds(i92, i102, i2 + i92, i3 + i102);
                canvas.save();
                float f32 = i4 / 2;
                canvas.scale(f, f, f32, f32);
                drawable.draw(canvas);
                canvas.restore();
                if (i == 2) {
                    if (this.mShadowGenerator == null) {
                    }
                    shadowGenerator = this.mShadowGenerator;
                    synchronized (shadowGenerator) {
                    }
                }
            }
        }
        drawable.setBounds(this.mOldBounds);
    }

    public final AdaptiveIconDrawable normalizeAndWrapToAdaptiveIcon(Drawable drawable, float[] fArr) {
        float f;
        float f2;
        float f3;
        if (drawable == null) {
            return null;
        }
        float fSqrt = 0.92f;
        fArr[0] = 0.92f;
        if (drawable instanceof AdaptiveIconDrawable) {
            return (AdaptiveIconDrawable) drawable;
        }
        EmptyWrapper emptyWrapper = new EmptyWrapper();
        AdaptiveIconDrawable adaptiveIconDrawable = new AdaptiveIconDrawable(new ColorDrawable(this.mWrapperBackgroundColor), emptyWrapper);
        adaptiveIconDrawable.setBounds(0, 0, 1, 1);
        IconNormalizer iconNormalizer = new IconNormalizer(this.mIconBitmapSize);
        synchronized (iconNormalizer) {
            try {
                if (drawable instanceof AdaptiveIconDrawable) {
                    f = 0.0f;
                } else {
                    int intrinsicWidth = drawable.getIntrinsicWidth();
                    int intrinsicHeight = drawable.getIntrinsicHeight();
                    if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                        if (intrinsicWidth <= 0 || intrinsicWidth > iconNormalizer.mMaxSize) {
                            intrinsicWidth = iconNormalizer.mMaxSize;
                        }
                        if (intrinsicHeight <= 0 || intrinsicHeight > iconNormalizer.mMaxSize) {
                            intrinsicHeight = iconNormalizer.mMaxSize;
                        }
                    } else {
                        int i = iconNormalizer.mMaxSize;
                        if (intrinsicWidth > i || intrinsicHeight > i) {
                            int iMax = Math.max(intrinsicWidth, intrinsicHeight);
                            int i2 = iconNormalizer.mMaxSize;
                            intrinsicWidth = (intrinsicWidth * i2) / iMax;
                            intrinsicHeight = (i2 * intrinsicHeight) / iMax;
                        }
                    }
                    iconNormalizer.mBitmap.eraseColor(0);
                    drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
                    drawable.draw(iconNormalizer.mCanvas);
                    ByteBuffer byteBufferWrap = ByteBuffer.wrap(iconNormalizer.mPixels);
                    byteBufferWrap.rewind();
                    iconNormalizer.mBitmap.copyPixelsToBuffer(byteBufferWrap);
                    int i3 = iconNormalizer.mMaxSize;
                    int i4 = i3 + 1;
                    int i5 = i3 - intrinsicWidth;
                    int i6 = 0;
                    int i7 = 0;
                    int i8 = -1;
                    int i9 = -1;
                    int iMax2 = -1;
                    while (i6 < intrinsicHeight) {
                        int i10 = -1;
                        int i11 = 0;
                        int i12 = -1;
                        while (i11 < intrinsicWidth) {
                            int i13 = intrinsicWidth;
                            if ((iconNormalizer.mPixels[i7] & 255) > 40) {
                                if (i12 == -1) {
                                    i12 = i11;
                                }
                                i10 = i11;
                            }
                            i7++;
                            i11++;
                            intrinsicWidth = i13;
                        }
                        int i14 = intrinsicWidth;
                        i7 += i5;
                        iconNormalizer.mLeftBorder[i6] = i12;
                        iconNormalizer.mRightBorder[i6] = i10;
                        if (i12 != -1) {
                            if (i9 == -1) {
                                i9 = i6;
                            }
                            int iMin = Math.min(i4, i12);
                            iMax2 = Math.max(iMax2, i10);
                            i4 = iMin;
                            i8 = i6;
                        }
                        i6++;
                        intrinsicWidth = i14;
                    }
                    int i15 = intrinsicWidth;
                    f = 0.0f;
                    if (i9 == -1 || iMax2 == -1) {
                        fSqrt = 1.0f;
                    } else {
                        IconNormalizer.convertToConvexArray(1, i9, i8, iconNormalizer.mLeftBorder);
                        IconNormalizer.convertToConvexArray(-1, i9, i8, iconNormalizer.mRightBorder);
                        float f4 = 0.0f;
                        for (int i16 = 0; i16 < intrinsicHeight; i16++) {
                            float f5 = iconNormalizer.mLeftBorder[i16];
                            if (f5 > -1.0f) {
                                f4 += (iconNormalizer.mRightBorder[i16] - f5) + 1.0f;
                            }
                        }
                        Rect rect = iconNormalizer.mBounds;
                        rect.left = i4;
                        rect.right = iMax2;
                        rect.top = i9;
                        rect.bottom = i8;
                        float f6 = ((iMax2 + 1) - i4) * ((i8 + 1) - i9);
                        float f7 = i15 * intrinsicHeight;
                        float f8 = f4 / f6;
                        fSqrt = f4 / f7 > (f8 < 0.7853982f ? 0.6597222f : DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(1.0f, f8, 0.040449437f, 0.6510417f)) ? (float) Math.sqrt(r0 / r4) : 1.0f;
                    }
                }
            } finally {
            }
        }
        float f9 = fSqrt * LEGACY_ICON_SCALE;
        float intrinsicHeight2 = drawable.getIntrinsicHeight();
        float intrinsicWidth2 = drawable.getIntrinsicWidth();
        if (intrinsicHeight2 <= intrinsicWidth2 || intrinsicWidth2 <= f) {
            f2 = (intrinsicWidth2 <= intrinsicHeight2 || intrinsicHeight2 <= f) ? f9 : (intrinsicHeight2 / intrinsicWidth2) * f9;
            f3 = 1.0f;
        } else {
            float f10 = (intrinsicWidth2 / intrinsicHeight2) * f9;
            f2 = f9;
            f3 = 1.0f;
            f9 = f10;
        }
        float f11 = (f3 - f9) / 2.0f;
        float f12 = (f3 - f2) / 2.0f;
        emptyWrapper.setDrawable(new InsetDrawable(drawable, f11, f12, f11, f12));
        return adaptiveIconDrawable;
    }

    public BaseIconFactory(Context context, int i, int i2) throws Resources.NotFoundException {
        this.mOldBounds = new Rect();
        new SparseArray();
        this.mWrapperBackgroundColor = -1;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mIconBitmapSize = i2;
        applicationContext.getPackageManager();
        Canvas canvas = new Canvas();
        this.mCanvas = canvas;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        this.mWrapperBackgroundColor = -1;
        applicationContext.getResources().getBoolean(R.bool.enable_forced_themed_icon);
    }

    public final Bitmap createIconBitmap(Drawable drawable, float f, int i) {
        BaseIconFactory baseIconFactory;
        Drawable drawable2;
        float f2;
        int i2;
        Bitmap bitmapCreateBitmap;
        int i3 = this.mIconBitmapSize;
        if (i == 1) {
            baseIconFactory = this;
            drawable2 = drawable;
            f2 = f;
            i2 = i;
            bitmapCreateBitmap = Bitmap.createBitmap(i3, i3, Bitmap.Config.ALPHA_8);
        } else if (i != 3 && i != 4) {
            bitmapCreateBitmap = Bitmap.createBitmap(i3, i3, Bitmap.Config.ARGB_8888);
            baseIconFactory = this;
            drawable2 = drawable;
            f2 = f;
            i2 = i;
        } else {
            GraphicsUtils$$ExternalSyntheticLambda0 graphicsUtils$$ExternalSyntheticLambda0 = GraphicsUtils.sOnNewBitmapRunnable;
            Picture picture = new Picture();
            drawIconBitmap(picture.beginRecording(i3, i3), drawable, f, i, null);
            picture.endRecording();
            return Bitmap.createBitmap(picture);
        }
        Bitmap bitmap = bitmapCreateBitmap;
        if (drawable2 == null) {
            return bitmap;
        }
        baseIconFactory.mCanvas.setBitmap(bitmap);
        BaseIconFactory baseIconFactory2 = baseIconFactory;
        baseIconFactory2.drawIconBitmap(baseIconFactory.mCanvas, drawable2, f2, i2, bitmap);
        baseIconFactory2.mCanvas.setBitmap(null);
        return bitmap;
    }
}
