package com.android.launcher3.icons;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.UserHandle;
import android.util.SparseBooleanArray;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.ClockDrawableWrapper;
import com.android.launcher3.util.FlagOp;
import com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda0;
import com.android.systemui.R;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.Calendar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class BaseIconFactory implements AutoCloseable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Canvas mCanvas;
    public final ColorExtractor mColorExtractor;
    public final Context mContext;
    public final int mFillResIconDpi;
    public final int mIconBitmapSize;
    public final SparseBooleanArray mIsUserBadged;
    public IconNormalizer mNormalizer;
    public final Rect mOldBounds;
    public final PackageManager mPm;
    public ShadowGenerator mShadowGenerator;
    public final boolean mShapeDetection;
    public int mWrapperBackgroundColor;
    public Drawable mWrapperIcon;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IconOptions {
        public boolean mIsCloneProfile;
        public UserHandle mUserHandle;
        public final boolean mShrinkNonAdaptiveIcons = true;
        public final int mGenerationMode = 2;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NoopDrawable extends ColorDrawable {
        public /* synthetic */ NoopDrawable(int i) {
            this();
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicHeight() {
            return 1;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicWidth() {
            return 1;
        }

        private NoopDrawable() {
        }
    }

    static {
        Color.rgb(IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode, IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode, IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode);
    }

    public BaseIconFactory(Context context, int i, int i2, boolean z) {
        this.mOldBounds = new Rect();
        this.mIsUserBadged = new SparseBooleanArray();
        this.mWrapperBackgroundColor = -1;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mShapeDetection = z;
        this.mFillResIconDpi = i;
        this.mIconBitmapSize = i2;
        this.mPm = applicationContext.getPackageManager();
        this.mColorExtractor = new ColorExtractor();
        Canvas canvas = new Canvas();
        this.mCanvas = canvas;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        this.mWrapperBackgroundColor = -1;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mWrapperBackgroundColor = -1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final BitmapInfo createBadgedIconBitmap(Drawable drawable, IconOptions iconOptions) {
        final int i;
        FlagOp flagOp;
        boolean z;
        final int i2 = 1;
        final int i3 = 0;
        float[] fArr = new float[1];
        Drawable normalizeAndWrapToAdaptiveIcon = normalizeAndWrapToAdaptiveIcon(drawable, iconOptions.mShrinkNonAdaptiveIcons, null, fArr);
        Bitmap createIconBitmap = createIconBitmap(normalizeAndWrapToAdaptiveIcon, fArr[0], iconOptions.mGenerationMode);
        int findDominantColorByHue = this.mColorExtractor.findDominantColorByHue(createIconBitmap);
        BitmapInfo bitmapInfo = new BitmapInfo(createIconBitmap, findDominantColorByHue);
        if (normalizeAndWrapToAdaptiveIcon instanceof BitmapInfo.Extender) {
            float f = fArr[0];
            ClockDrawableWrapper clockDrawableWrapper = (ClockDrawableWrapper) ((BitmapInfo.Extender) normalizeAndWrapToAdaptiveIcon);
            clockDrawableWrapper.getClass();
            Bitmap createScaledBitmap = createScaledBitmap(4, new AdaptiveIconDrawable(clockDrawableWrapper.getBackground().getConstantState().newDrawable(), null));
            ClockDrawableWrapper.AnimationInfo animationInfo = clockDrawableWrapper.mAnimationInfo;
            i = 4;
            bitmapInfo = new ClockDrawableWrapper.ClockBitmapInfo(createIconBitmap, findDominantColorByHue, f, animationInfo, createScaledBitmap, null, null);
        } else {
            i = 4;
            int i4 = IconProvider.CONFIG_ICON_MASK_RES_ID;
        }
        final FlagOp$$ExternalSyntheticLambda0 flagOp$$ExternalSyntheticLambda0 = FlagOp.NO_OP;
        UserHandle userHandle = iconOptions.mUserHandle;
        if (userHandle != null) {
            int hashCode = userHandle.hashCode();
            int indexOfKey = this.mIsUserBadged.indexOfKey(hashCode);
            if (indexOfKey >= 0) {
                z = this.mIsUserBadged.valueAt(indexOfKey);
            } else {
                NoopDrawable noopDrawable = new NoopDrawable(i3);
                boolean z2 = noopDrawable != this.mPm.getUserBadgedIcon(noopDrawable, iconOptions.mUserHandle);
                this.mIsUserBadged.put(hashCode, z2);
                z = z2;
            }
            final FlagOp flagOp2 = z && iconOptions.mIsCloneProfile ? new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1
                @Override // com.android.launcher3.util.FlagOp
                public final int apply(int i5) {
                    int i6 = i3;
                    int i7 = i;
                    FlagOp flagOp3 = flagOp$$ExternalSyntheticLambda0;
                    switch (i6) {
                        case 0:
                            return flagOp3.apply(i5) | i7;
                        default:
                            return flagOp3.apply(i5) & (~i7);
                    }
                }
            } : new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1
                @Override // com.android.launcher3.util.FlagOp
                public final int apply(int i5) {
                    int i6 = i2;
                    int i7 = i;
                    FlagOp flagOp3 = flagOp$$ExternalSyntheticLambda0;
                    switch (i6) {
                        case 0:
                            return flagOp3.apply(i5) | i7;
                        default:
                            return flagOp3.apply(i5) & (~i7);
                    }
                }
            };
            flagOp = z && !iconOptions.mIsCloneProfile ? new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1
                @Override // com.android.launcher3.util.FlagOp
                public final int apply(int i5) {
                    int i6 = i3;
                    int i7 = i2;
                    FlagOp flagOp3 = flagOp2;
                    switch (i6) {
                        case 0:
                            return flagOp3.apply(i5) | i7;
                        default:
                            return flagOp3.apply(i5) & (~i7);
                    }
                }
            } : new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1
                @Override // com.android.launcher3.util.FlagOp
                public final int apply(int i5) {
                    int i6 = i2;
                    int i7 = i2;
                    FlagOp flagOp3 = flagOp2;
                    switch (i6) {
                        case 0:
                            return flagOp3.apply(i5) | i7;
                        default:
                            return flagOp3.apply(i5) & (~i7);
                    }
                }
            };
        } else {
            flagOp = flagOp$$ExternalSyntheticLambda0;
        }
        if (flagOp == flagOp$$ExternalSyntheticLambda0) {
            return bitmapInfo;
        }
        BitmapInfo mo364clone = bitmapInfo.mo364clone();
        mo364clone.flags = flagOp.apply(mo364clone.flags);
        return mo364clone;
    }

    public final BitmapInfo createIconBitmap(Bitmap bitmap) {
        if (this.mIconBitmapSize != bitmap.getWidth() || this.mIconBitmapSize != bitmap.getHeight()) {
            bitmap = createIconBitmap(new BitmapDrawable(this.mContext.getResources(), bitmap), 1.0f, 0);
        }
        return new BitmapInfo(bitmap, this.mColorExtractor.findDominantColorByHue(bitmap));
    }

    public final Bitmap createScaledBitmap(int i, Drawable drawable) {
        RectF rectF = new RectF();
        float[] fArr = new float[1];
        Drawable normalizeAndWrapToAdaptiveIcon = normalizeAndWrapToAdaptiveIcon(drawable, true, rectF, fArr);
        float f = fArr[0];
        float min = Math.min(Math.min(rectF.left, rectF.right), rectF.top);
        float f2 = min < 0.035f ? 0.465f / (0.5f - min) : 1.0f;
        float f3 = rectF.bottom;
        if (f3 < 0.055833332f) {
            f2 = Math.min(f2, 0.44416666f / (0.5f - f3));
        }
        return createIconBitmap(normalizeAndWrapToAdaptiveIcon, Math.min(f, f2), i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x013e A[EXC_TOP_SPLITTER, SYNTHETIC] */
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
            int max = Math.max((int) Math.ceil(0.035f * r15), Math.round(((1.0f - f) * i4) / 2.0f));
            int i5 = (i4 - max) - max;
            drawable.setBounds(0, 0, i5, i5);
            int save = canvas.save();
            float f2 = max;
            canvas.translate(f2, f2);
            if (i == 2 || i == 4) {
                if (this.mShadowGenerator == null) {
                    this.mShadowGenerator = new ShadowGenerator(this.mIconBitmapSize);
                }
                ShadowGenerator shadowGenerator2 = this.mShadowGenerator;
                Path iconMask = ((AdaptiveIconDrawable) drawable).getIconMask();
                Paint paint = shadowGenerator2.mDrawPaint;
                paint.setMaskFilter(shadowGenerator2.mDefaultBlurMaskFilter);
                paint.setAlpha(25);
                canvas.drawPath(iconMask, paint);
                int save2 = canvas.save();
                paint.setAlpha(7);
                canvas.translate(0.0f, shadowGenerator2.mIconSize * 0.020833334f);
                canvas.drawPath(iconMask, paint);
                canvas.restoreToCount(save2);
                paint.setMaskFilter(null);
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
                drawable.draw(canvas);
            }
            canvas.restoreToCount(save);
        } else {
            if ((drawable instanceof BitmapDrawable) && (bitmap2 = (bitmapDrawable = (BitmapDrawable) drawable).getBitmap()) != null && bitmap2.getDensity() == 0) {
                bitmapDrawable.setTargetDensity(this.mContext.getResources().getDisplayMetrics());
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                float f3 = intrinsicWidth / intrinsicHeight;
                if (intrinsicWidth > intrinsicHeight) {
                    i3 = (int) (i4 / f3);
                    i2 = i4;
                } else if (intrinsicHeight > intrinsicWidth) {
                    i2 = (int) (i4 * f3);
                    i3 = i4;
                }
                int i9 = (i4 - i2) / 2;
                int i10 = (i4 - i3) / 2;
                drawable.setBounds(i9, i10, i2 + i9, i3 + i10);
                canvas.save();
                float f4 = i4 / 2;
                canvas.scale(f, f, f4, f4);
                drawable.draw(canvas);
                canvas.restore();
                if (i == 2 && bitmap != null) {
                    if (this.mShadowGenerator == null) {
                        this.mShadowGenerator = new ShadowGenerator(this.mIconBitmapSize);
                    }
                    shadowGenerator = this.mShadowGenerator;
                    synchronized (shadowGenerator) {
                        shadowGenerator.mBlurPaint.setMaskFilter(shadowGenerator.mDefaultBlurMaskFilter);
                        Bitmap extractAlpha = bitmap.extractAlpha(shadowGenerator.mBlurPaint, new int[2]);
                        shadowGenerator.mDrawPaint.setAlpha(25);
                        canvas.drawBitmap(extractAlpha, r1[0], r1[1], shadowGenerator.mDrawPaint);
                        shadowGenerator.mDrawPaint.setAlpha(7);
                        canvas.drawBitmap(extractAlpha, r1[0], (shadowGenerator.mIconSize * 0.020833334f) + r1[1], shadowGenerator.mDrawPaint);
                    }
                    canvas.save();
                    canvas.scale(f, f, f4, f4);
                    drawable.draw(canvas);
                    canvas.restore();
                }
            }
            i2 = i4;
            i3 = i2;
            int i92 = (i4 - i2) / 2;
            int i102 = (i4 - i3) / 2;
            drawable.setBounds(i92, i102, i2 + i92, i3 + i102);
            canvas.save();
            float f42 = i4 / 2;
            canvas.scale(f, f, f42, f42);
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
        drawable.setBounds(this.mOldBounds);
    }

    public final IconNormalizer getNormalizer() {
        if (this.mNormalizer == null) {
            this.mNormalizer = new IconNormalizer(this.mContext, this.mIconBitmapSize, this.mShapeDetection);
        }
        return this.mNormalizer;
    }

    public final Drawable normalizeAndWrapToAdaptiveIcon(Drawable drawable, boolean z, RectF rectF, float[] fArr) {
        float scale;
        if (drawable == null) {
            return null;
        }
        if (!z || (drawable instanceof AdaptiveIconDrawable)) {
            scale = getNormalizer().getScale(drawable, rectF, null, null);
        } else {
            if (this.mWrapperIcon == null) {
                this.mWrapperIcon = this.mContext.getDrawable(R.drawable.adaptive_icon_drawable_wrapper).mutate();
            }
            AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) this.mWrapperIcon;
            adaptiveIconDrawable.setBounds(0, 0, 1, 1);
            boolean[] zArr = new boolean[1];
            scale = getNormalizer().getScale(drawable, rectF, adaptiveIconDrawable.getIconMask(), zArr);
            if (!zArr[0]) {
                FixedScaleDrawable fixedScaleDrawable = (FixedScaleDrawable) adaptiveIconDrawable.getForeground();
                fixedScaleDrawable.setDrawable(drawable);
                float intrinsicHeight = fixedScaleDrawable.getIntrinsicHeight();
                float intrinsicWidth = fixedScaleDrawable.getIntrinsicWidth();
                float f = scale * 0.46669f;
                fixedScaleDrawable.mScaleX = f;
                fixedScaleDrawable.mScaleY = f;
                if (intrinsicHeight > intrinsicWidth && intrinsicWidth > 0.0f) {
                    fixedScaleDrawable.mScaleX = (intrinsicWidth / intrinsicHeight) * f;
                } else if (intrinsicWidth > intrinsicHeight && intrinsicHeight > 0.0f) {
                    fixedScaleDrawable.mScaleY = (intrinsicHeight / intrinsicWidth) * f;
                }
                scale = getNormalizer().getScale(adaptiveIconDrawable, rectF, null, null);
                ((ColorDrawable) adaptiveIconDrawable.getBackground()).setColor(this.mWrapperBackgroundColor);
                drawable = adaptiveIconDrawable;
            }
        }
        fArr[0] = scale;
        return drawable;
    }

    public final Bitmap createIconBitmap(Drawable drawable, float f, int i) {
        Bitmap createBitmap;
        int i2 = this.mIconBitmapSize;
        if (i == 1) {
            createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ALPHA_8);
        } else if (i != 3 && i != 4) {
            createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
        } else {
            GraphicsUtils$$ExternalSyntheticLambda0 graphicsUtils$$ExternalSyntheticLambda0 = GraphicsUtils.sOnNewBitmapRunnable;
            Picture picture = new Picture();
            drawIconBitmap(picture.beginRecording(i2, i2), drawable, f, i, null);
            picture.endRecording();
            return Bitmap.createBitmap(picture);
        }
        if (drawable == null) {
            return createBitmap;
        }
        this.mCanvas.setBitmap(createBitmap);
        drawIconBitmap(this.mCanvas, drawable, f, i, createBitmap);
        this.mCanvas.setBitmap(null);
        return createBitmap;
    }

    public BaseIconFactory(Context context, int i, int i2) {
        this(context, i, i2, false);
    }
}
