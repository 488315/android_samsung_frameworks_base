package com.android.internal.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.Pools;
import android.util.TypedValue;
import com.android.internal.R;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParser;

@Deprecated
/* loaded from: classes5.dex */
public class SimpleIconFactory {
    private static final int AMBIENT_SHADOW_ALPHA = 7;
    private static final float BLUR_FACTOR = 0.03125f;
    private static final float CIRCLE_AREA_BY_RECT = 0.7853982f;
    private static final int DEFAULT_WRAPPER_BACKGROUND = -1;
    private static final int KEY_SHADOW_ALPHA = 10;
    private static final float KEY_SHADOW_DISTANCE = 0.020833334f;
    private static final float LINEAR_SCALE_SLOPE = 0.040449437f;
    private static final float MAX_CIRCLE_AREA_FACTOR = 0.6597222f;
    private static final float MAX_SQUARE_AREA_FACTOR = 0.6510417f;
    private static final int MIN_VISIBLE_ALPHA = 40;
    private static final float SCALE_NOT_INITIALIZED = 0.0f;
    private static final Pools.SynchronizedPool<SimpleIconFactory> sPool = new Pools.SynchronizedPool<>(Runtime.getRuntime().availableProcessors());
    private static boolean sPoolEnabled = true;
    private final Rect mAdaptiveIconBounds;
    private float mAdaptiveIconScale;
    private int mBadgeBitmapSize;
    private final Bitmap mBitmap;
    private final Rect mBounds;
    private Canvas mCanvas;
    private Context mContext;
    private BlurMaskFilter mDefaultBlurMaskFilter;
    private int mFillResIconDpi;
    private int mIconBitmapSize;
    private final float[] mLeftBorder;
    private final int mMaxSize;
    private final byte[] mPixels;
    private PackageManager mPm;
    private final float[] mRightBorder;
    private final Canvas mScaleCheckCanvas;
    private int mWrapperBackgroundColor;
    private Drawable mWrapperIcon;
    private final Rect mOldBounds = new Rect();
    private Paint mBlurPaint = new Paint(3);
    private Paint mDrawPaint = new Paint(3);

    @Deprecated
    public static SimpleIconFactory obtain(Context context) {
        SimpleIconFactory simpleIconFactoryAcquire = sPoolEnabled ? sPool.acquire() : null;
        if (simpleIconFactoryAcquire != null) {
            return simpleIconFactoryAcquire;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        SimpleIconFactory simpleIconFactory = new SimpleIconFactory(context, activityManager == null ? 0 : activityManager.getLauncherLargeIconDensity(), getIconSizeFromContext(context), getBadgeSizeFromContext(context));
        simpleIconFactory.setWrapperBackgroundColor(-1);
        return simpleIconFactory;
    }

    public static void setPoolEnabled(boolean z) {
        sPoolEnabled = z;
    }

    private static int getAttrDimFromContext(Context context, int i, String str) {
        Resources resources = context.getResources();
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(i, typedValue, true)) {
            throw new IllegalStateException(str);
        }
        return resources.getDimensionPixelSize(typedValue.resourceId);
    }

    private static int getIconSizeFromContext(Context context) {
        return getAttrDimFromContext(context, R.attr.iconfactoryIconSize, "Expected theme to define iconfactoryIconSize.");
    }

    private static int getBadgeSizeFromContext(Context context) {
        return getAttrDimFromContext(context, R.attr.iconfactoryBadgeSize, "Expected theme to define iconfactoryBadgeSize.");
    }

    @Deprecated
    public void recycle() {
        setWrapperBackgroundColor(-1);
        sPool.release(this);
    }

    @Deprecated
    private SimpleIconFactory(Context context, int i, int i2, int i3) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mPm = applicationContext.getPackageManager();
        this.mIconBitmapSize = i2;
        this.mBadgeBitmapSize = i3;
        this.mFillResIconDpi = i;
        Canvas canvas = new Canvas();
        this.mCanvas = canvas;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        int i4 = i2 * 2;
        this.mMaxSize = i4;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i4, i4, Bitmap.Config.ALPHA_8);
        this.mBitmap = bitmapCreateBitmap;
        this.mScaleCheckCanvas = new Canvas(bitmapCreateBitmap);
        this.mPixels = new byte[i4 * i4];
        this.mLeftBorder = new float[i4];
        this.mRightBorder = new float[i4];
        this.mBounds = new Rect();
        this.mAdaptiveIconBounds = new Rect();
        this.mAdaptiveIconScale = 0.0f;
        this.mDefaultBlurMaskFilter = new BlurMaskFilter(i2 * BLUR_FACTOR, BlurMaskFilter.Blur.NORMAL);
    }

    @Deprecated
    void setWrapperBackgroundColor(int i) {
        if (Color.alpha(i) < 255) {
            i = -1;
        }
        this.mWrapperBackgroundColor = i;
    }

    @Deprecated
    Bitmap createUserBadgedIconBitmap(Drawable drawable, UserHandle userHandle) throws Throwable {
        float[] fArr = new float[1];
        if (drawable == null) {
            drawable = getFullResDefaultActivityIcon(this.mFillResIconDpi);
        }
        Drawable drawableNormalizeAndWrapToAdaptiveIcon = normalizeAndWrapToAdaptiveIcon(drawable, null, fArr);
        Bitmap bitmapCreateIconBitmap = createIconBitmap(drawableNormalizeAndWrapToAdaptiveIcon, fArr[0]);
        if (drawableNormalizeAndWrapToAdaptiveIcon instanceof AdaptiveIconDrawable) {
            this.mCanvas.setBitmap(bitmapCreateIconBitmap);
            recreateIcon(Bitmap.createBitmap(bitmapCreateIconBitmap), this.mCanvas);
            this.mCanvas.setBitmap(null);
        }
        if (userHandle == null) {
            return bitmapCreateIconBitmap;
        }
        Drawable userBadgedIcon = this.mPm.getUserBadgedIcon(new FixedSizeBitmapDrawable(bitmapCreateIconBitmap), userHandle);
        if (userBadgedIcon instanceof BitmapDrawable) {
            return ((BitmapDrawable) userBadgedIcon).getBitmap();
        }
        return createIconBitmap(userBadgedIcon, 1.0f);
    }

    @Deprecated
    public Bitmap createAppBadgedIconBitmap(Drawable drawable, Bitmap bitmap) throws Throwable {
        if (drawable == null) {
            drawable = getFullResDefaultActivityIcon(this.mFillResIconDpi);
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), maskBitmapToCircle(createIconBitmapNoInsetOrMask(drawable, (intrinsicHeight <= intrinsicWidth || intrinsicWidth <= 0) ? (intrinsicWidth <= intrinsicHeight || intrinsicHeight <= 0) ? 1.0f : intrinsicWidth / intrinsicHeight : intrinsicHeight / intrinsicWidth)));
        Bitmap bitmapCreateIconBitmap = createIconBitmap(bitmapDrawable, getScale(bitmapDrawable, null));
        this.mCanvas.setBitmap(bitmapCreateIconBitmap);
        recreateIcon(Bitmap.createBitmap(bitmapCreateIconBitmap), this.mCanvas);
        if (bitmap != null) {
            int i = this.mBadgeBitmapSize;
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, i, i, false);
            Canvas canvas = this.mCanvas;
            int i2 = this.mIconBitmapSize;
            int i3 = this.mBadgeBitmapSize;
            canvas.drawBitmap(bitmapCreateScaledBitmap, i2 - i3, i2 - i3, (Paint) null);
        }
        this.mCanvas.setBitmap(null);
        return bitmapCreateIconBitmap;
    }

    private Bitmap maskBitmapToCircle(Bitmap bitmap) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint(7);
        int iMax = Math.max((int) Math.ceil(bitmap.getWidth() * BLUR_FACTOR), 1);
        paint.setColor(-1);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f, (bitmap.getWidth() / 2.0f) - iMax, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmapCreateBitmap;
    }

    private static Drawable getFullResDefaultActivityIcon(int i) {
        return Resources.getSystem().getDrawableForDensity(17629184, i);
    }

    private Bitmap createIconBitmap(Drawable drawable, float f) {
        return createIconBitmap(drawable, f, this.mIconBitmapSize, true, false);
    }

    private Bitmap createIconBitmapNoInsetOrMask(Drawable drawable, float f) {
        return createIconBitmap(drawable, f, this.mIconBitmapSize, false, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00d5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Bitmap createIconBitmap(Drawable drawable, float f, int i, boolean z, boolean z2) {
        int i2;
        int i3;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        this.mCanvas.setBitmap(bitmapCreateBitmap);
        this.mOldBounds.set(drawable.getBounds());
        if (drawable instanceof AdaptiveIconDrawable) {
            AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable;
            int iRound = Math.round(((1.0f - f) * i) / 2.0f);
            if (z) {
                iRound = Math.max((int) Math.ceil(r2 * BLUR_FACTOR), iRound);
            }
            int i4 = i - iRound;
            Rect rect = new Rect(iRound, iRound, i4, i4);
            if (z2) {
                int iWidth = rect.width() / 2;
                int iHeight = rect.height() / 2;
                float extraInsetFraction = (1.0f / ((AdaptiveIconDrawable.getExtraInsetFraction() * 2.0f) + 1.0f)) * 2.0f;
                int iWidth2 = (int) (rect.width() / extraInsetFraction);
                int iHeight2 = (int) (rect.height() / extraInsetFraction);
                final Rect rect2 = new Rect(iWidth - iWidth2, iHeight - iHeight2, iWidth + iWidth2, iHeight + iHeight2);
                Optional.ofNullable(adaptiveIconDrawable.getBackground()).ifPresent(new Consumer() { // from class: com.android.internal.app.SimpleIconFactory$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$createIconBitmap$0(rect2, (Drawable) obj);
                    }
                });
                Optional.ofNullable(adaptiveIconDrawable.getForeground()).ifPresent(new Consumer() { // from class: com.android.internal.app.SimpleIconFactory$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$createIconBitmap$1(rect2, (Drawable) obj);
                    }
                });
            } else {
                adaptiveIconDrawable.setBounds(rect);
                adaptiveIconDrawable.draw(this.mCanvas);
            }
        } else {
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmapCreateBitmap != null && bitmap.getDensity() == 0) {
                    bitmapDrawable.setTargetDensity(this.mContext.getResources().getDisplayMetrics());
                }
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                i2 = i;
                i3 = i2;
                int i5 = (i - i2) / 2;
                int i6 = (i - i3) / 2;
                drawable.setBounds(i5, i6, i2 + i5, i3 + i6);
                this.mCanvas.save();
                float f2 = i / 2;
                this.mCanvas.scale(f, f, f2, f2);
                drawable.draw(this.mCanvas);
                this.mCanvas.restore();
            } else {
                float f3 = intrinsicWidth / intrinsicHeight;
                if (intrinsicWidth > intrinsicHeight) {
                    i3 = (int) (i / f3);
                    i2 = i;
                } else if (intrinsicHeight > intrinsicWidth) {
                    i2 = (int) (i * f3);
                    i3 = i;
                }
                int i52 = (i - i2) / 2;
                int i62 = (i - i3) / 2;
                drawable.setBounds(i52, i62, i2 + i52, i3 + i62);
                this.mCanvas.save();
                float f22 = i / 2;
                this.mCanvas.scale(f, f, f22, f22);
                drawable.draw(this.mCanvas);
                this.mCanvas.restore();
            }
        }
        drawable.setBounds(this.mOldBounds);
        this.mCanvas.setBitmap(null);
        return bitmapCreateBitmap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createIconBitmap$0(Rect rect, Drawable drawable) {
        drawable.setBounds(rect);
        drawable.draw(this.mCanvas);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createIconBitmap$1(Rect rect, Drawable drawable) {
        drawable.setBounds(rect);
        drawable.draw(this.mCanvas);
    }

    private Drawable normalizeAndWrapToAdaptiveIcon(Drawable drawable, RectF rectF, float[] fArr) {
        if (this.mWrapperIcon == null) {
            this.mWrapperIcon = this.mContext.getDrawable(R.drawable.iconfactory_adaptive_icon_drawable_wrapper).mutate();
        }
        AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) this.mWrapperIcon;
        adaptiveIconDrawable.setBounds(0, 0, 1, 1);
        float scale = getScale(drawable, rectF);
        if (!(drawable instanceof AdaptiveIconDrawable)) {
            FixedScaleDrawable fixedScaleDrawable = (FixedScaleDrawable) adaptiveIconDrawable.getForeground();
            fixedScaleDrawable.setDrawable(drawable);
            fixedScaleDrawable.setScale(scale);
            scale = getScale(adaptiveIconDrawable, rectF);
            ((ColorDrawable) adaptiveIconDrawable.getBackground()).setColor(this.mWrapperBackgroundColor);
            drawable = adaptiveIconDrawable;
        }
        fArr[0] = scale;
        return drawable;
    }

    private synchronized float getScale(Drawable drawable, RectF rectF) {
        float f = 0.0f;
        if ((drawable instanceof AdaptiveIconDrawable) && this.mAdaptiveIconScale != 0.0f) {
            if (rectF != null) {
                rectF.set(this.mAdaptiveIconBounds);
            }
            return this.mAdaptiveIconScale;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            if (intrinsicWidth <= 0 || intrinsicWidth > this.mMaxSize) {
                intrinsicWidth = this.mMaxSize;
            }
            if (intrinsicHeight <= 0 || intrinsicHeight > this.mMaxSize) {
                intrinsicHeight = this.mMaxSize;
            }
        } else {
            int i = this.mMaxSize;
            if (intrinsicWidth > i || intrinsicHeight > i) {
                int iMax = Math.max(intrinsicWidth, intrinsicHeight);
                int i2 = this.mMaxSize;
                intrinsicWidth = (intrinsicWidth * i2) / iMax;
                intrinsicHeight = (i2 * intrinsicHeight) / iMax;
            }
        }
        int i3 = 0;
        this.mBitmap.eraseColor(0);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(this.mScaleCheckCanvas);
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(this.mPixels);
        byteBufferWrap.rewind();
        this.mBitmap.copyPixelsToBuffer(byteBufferWrap);
        int i4 = this.mMaxSize;
        int i5 = i4 + 1;
        int i6 = i4 - intrinsicWidth;
        int i7 = 0;
        int i8 = 0;
        int i9 = -1;
        int iMax2 = -1;
        int i10 = -1;
        while (i7 < intrinsicHeight) {
            float f2 = f;
            int i11 = i3;
            int i12 = -1;
            int i13 = -1;
            while (i11 < intrinsicWidth) {
                int i14 = i6;
                if ((this.mPixels[i8] & 255) > 40) {
                    if (i12 == -1) {
                        i12 = i11;
                    }
                    i13 = i11;
                }
                i8++;
                i11++;
                i6 = i14;
            }
            int i15 = i6;
            i8 += i15;
            this.mLeftBorder[i7] = i12;
            this.mRightBorder[i7] = i13;
            if (i12 != -1) {
                if (i9 == -1) {
                    i9 = i7;
                }
                int iMin = Math.min(i5, i12);
                iMax2 = Math.max(iMax2, i13);
                i5 = iMin;
                i10 = i7;
            }
            i7++;
            f = f2;
            i6 = i15;
            i3 = 0;
        }
        float f3 = f;
        if (i9 != -1 && iMax2 != -1) {
            convertToConvexArray(this.mLeftBorder, 1, i9, i10);
            convertToConvexArray(this.mRightBorder, -1, i9, i10);
            float f4 = f3;
            for (int i16 = 0; i16 < intrinsicHeight; i16++) {
                float f5 = this.mLeftBorder[i16];
                if (f5 > -1.0f) {
                    f4 += (this.mRightBorder[i16] - f5) + 1.0f;
                }
            }
            float f6 = f4 / (((i10 + 1) - i9) * ((iMax2 + 1) - i5));
            float f7 = f6 < CIRCLE_AREA_BY_RECT ? MAX_CIRCLE_AREA_FACTOR : ((1.0f - f6) * LINEAR_SCALE_SLOPE) + MAX_SQUARE_AREA_FACTOR;
            this.mBounds.left = i5;
            this.mBounds.right = iMax2;
            this.mBounds.top = i9;
            this.mBounds.bottom = i10;
            if (rectF != null) {
                float f8 = intrinsicWidth;
                float f9 = intrinsicHeight;
                rectF.set(this.mBounds.left / f8, this.mBounds.top / f9, 1.0f - (this.mBounds.right / f8), 1.0f - (this.mBounds.bottom / f9));
            }
            float fSqrt = f4 / (intrinsicWidth * intrinsicHeight) > f7 ? (float) Math.sqrt(f7 / r6) : 1.0f;
            if ((drawable instanceof AdaptiveIconDrawable) && this.mAdaptiveIconScale == f3) {
                this.mAdaptiveIconScale = fSqrt;
                this.mAdaptiveIconBounds.set(this.mBounds);
            }
            return fSqrt;
        }
        return 1.0f;
    }

    private static void convertToConvexArray(float[] fArr, int i, int i2, int i3) {
        float[] fArr2 = new float[fArr.length - 1];
        int i4 = -1;
        float f = Float.MAX_VALUE;
        for (int i5 = i2 + 1; i5 <= i3; i5++) {
            float f2 = fArr[i5];
            if (f2 > -1.0f) {
                if (f == Float.MAX_VALUE) {
                    i4 = i2;
                } else {
                    float f3 = ((f2 - fArr[i4]) / (i5 - i4)) - f;
                    float f4 = i;
                    if (f3 * f4 < 0.0f) {
                        while (i4 > i2) {
                            i4--;
                            if ((((fArr[i5] - fArr[i4]) / (i5 - i4)) - fArr2[i4]) * f4 >= 0.0f) {
                                break;
                            }
                        }
                    }
                }
                f = (fArr[i5] - fArr[i4]) / (i5 - i4);
                for (int i6 = i4; i6 < i5; i6++) {
                    fArr2[i6] = f;
                    fArr[i6] = fArr[i4] + ((i6 - i4) * f);
                }
                i4 = i5;
            }
        }
    }

    private synchronized void recreateIcon(Bitmap bitmap, Canvas canvas) throws Throwable {
        try {
            try {
                recreateIcon(bitmap, this.mDefaultBlurMaskFilter, 7, 10, canvas);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private synchronized void recreateIcon(Bitmap bitmap, BlurMaskFilter blurMaskFilter, int i, int i2, Canvas canvas) {
        this.mBlurPaint.setMaskFilter(blurMaskFilter);
        Bitmap bitmapExtractAlpha = bitmap.extractAlpha(this.mBlurPaint, new int[2]);
        this.mDrawPaint.setAlpha(i);
        canvas.drawBitmap(bitmapExtractAlpha, r0[0], r0[1], this.mDrawPaint);
        this.mDrawPaint.setAlpha(i2);
        canvas.drawBitmap(bitmapExtractAlpha, r0[0], r0[1] + (this.mIconBitmapSize * KEY_SHADOW_DISTANCE), this.mDrawPaint);
        this.mDrawPaint.setAlpha(255);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mDrawPaint);
    }

    public static class FixedScaleDrawable extends DrawableWrapper {
        private static final float LEGACY_ICON_SCALE = 0.46669f;
        private float mScaleX;
        private float mScaleY;

        @Override // android.graphics.drawable.Drawable
        public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        }

        public FixedScaleDrawable() {
            super(new ColorDrawable());
            this.mScaleX = LEGACY_ICON_SCALE;
            this.mScaleY = LEGACY_ICON_SCALE;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            int iSave = canvas.save();
            canvas.scale(this.mScaleX, this.mScaleY, getBounds().exactCenterX(), getBounds().exactCenterY());
            super.draw(canvas);
            canvas.restoreToCount(iSave);
        }

        public void setScale(float f) {
            float intrinsicHeight = getIntrinsicHeight();
            float intrinsicWidth = getIntrinsicWidth();
            float f2 = f * LEGACY_ICON_SCALE;
            this.mScaleX = f2;
            this.mScaleY = f2;
            if (intrinsicHeight > intrinsicWidth && intrinsicWidth > 0.0f) {
                this.mScaleX = f2 * (intrinsicWidth / intrinsicHeight);
            } else {
                if (intrinsicWidth <= intrinsicHeight || intrinsicHeight <= 0.0f) {
                    return;
                }
                this.mScaleY = f2 * (intrinsicHeight / intrinsicWidth);
            }
        }
    }

    private static class FixedSizeBitmapDrawable extends BitmapDrawable {
        FixedSizeBitmapDrawable(Bitmap bitmap) {
            super((Resources) null, bitmap);
        }

        @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return getBitmap().getWidth();
        }

        @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return getBitmap().getWidth();
        }
    }
}
