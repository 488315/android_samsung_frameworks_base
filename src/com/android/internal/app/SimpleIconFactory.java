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
        SimpleIconFactory acquire = sPoolEnabled ? sPool.acquire() : null;
        if (acquire != null) {
            return acquire;
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
        Bitmap createBitmap = Bitmap.createBitmap(i4, i4, Bitmap.Config.ALPHA_8);
        this.mBitmap = createBitmap;
        this.mScaleCheckCanvas = new Canvas(createBitmap);
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
    Bitmap createUserBadgedIconBitmap(Drawable drawable, UserHandle userHandle) {
        float[] fArr = new float[1];
        if (drawable == null) {
            drawable = getFullResDefaultActivityIcon(this.mFillResIconDpi);
        }
        Drawable normalizeAndWrapToAdaptiveIcon = normalizeAndWrapToAdaptiveIcon(drawable, null, fArr);
        Bitmap createIconBitmap = createIconBitmap(normalizeAndWrapToAdaptiveIcon, fArr[0]);
        if (normalizeAndWrapToAdaptiveIcon instanceof AdaptiveIconDrawable) {
            this.mCanvas.setBitmap(createIconBitmap);
            recreateIcon(Bitmap.createBitmap(createIconBitmap), this.mCanvas);
            this.mCanvas.setBitmap(null);
        }
        if (userHandle == null) {
            return createIconBitmap;
        }
        Drawable userBadgedIcon = this.mPm.getUserBadgedIcon(new FixedSizeBitmapDrawable(createIconBitmap), userHandle);
        if (userBadgedIcon instanceof BitmapDrawable) {
            return ((BitmapDrawable) userBadgedIcon).getBitmap();
        }
        return createIconBitmap(userBadgedIcon, 1.0f);
    }

    @Deprecated
    public Bitmap createAppBadgedIconBitmap(Drawable drawable, Bitmap bitmap) {
        if (drawable == null) {
            drawable = getFullResDefaultActivityIcon(this.mFillResIconDpi);
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), maskBitmapToCircle(createIconBitmapNoInsetOrMask(drawable, (intrinsicHeight <= intrinsicWidth || intrinsicWidth <= 0) ? (intrinsicWidth <= intrinsicHeight || intrinsicHeight <= 0) ? 1.0f : intrinsicWidth / intrinsicHeight : intrinsicHeight / intrinsicWidth)));
        Bitmap createIconBitmap = createIconBitmap(bitmapDrawable, getScale(bitmapDrawable, null));
        this.mCanvas.setBitmap(createIconBitmap);
        recreateIcon(Bitmap.createBitmap(createIconBitmap), this.mCanvas);
        if (bitmap != null) {
            int i = this.mBadgeBitmapSize;
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, i, i, false);
            Canvas canvas = this.mCanvas;
            int i2 = this.mIconBitmapSize;
            int i3 = this.mBadgeBitmapSize;
            canvas.drawBitmap(createScaledBitmap, i2 - i3, i2 - i3, (Paint) null);
        }
        this.mCanvas.setBitmap(null);
        return createIconBitmap;
    }

    private Bitmap maskBitmapToCircle(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(7);
        int max = Math.max((int) Math.ceil(bitmap.getWidth() * BLUR_FACTOR), 1);
        paint.setColor(-1);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f, (bitmap.getWidth() / 2.0f) - max, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
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

    private Bitmap createIconBitmap(Drawable drawable, float f, int i, boolean z, boolean z2) {
        int i2;
        int i3;
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        this.mCanvas.setBitmap(createBitmap);
        this.mOldBounds.set(drawable.getBounds());
        if (drawable instanceof AdaptiveIconDrawable) {
            AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable;
            int round = Math.round(((1.0f - f) * i) / 2.0f);
            if (z) {
                round = Math.max((int) Math.ceil(r2 * BLUR_FACTOR), round);
            }
            int i4 = i - round;
            Rect rect = new Rect(round, round, i4, i4);
            if (z2) {
                int width = rect.width() / 2;
                int height = rect.height() / 2;
                float extraInsetFraction = (1.0f / ((AdaptiveIconDrawable.getExtraInsetFraction() * 2.0f) + 1.0f)) * 2.0f;
                int width2 = (int) (rect.width() / extraInsetFraction);
                int height2 = (int) (rect.height() / extraInsetFraction);
                final Rect rect2 = new Rect(width - width2, height - height2, width + width2, height + height2);
                Optional.ofNullable(adaptiveIconDrawable.getBackground()).ifPresent(new Consumer() { // from class: com.android.internal.app.SimpleIconFactory$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SimpleIconFactory.this.lambda$createIconBitmap$0(rect2, (Drawable) obj);
                    }
                });
                Optional.ofNullable(adaptiveIconDrawable.getForeground()).ifPresent(new Consumer() { // from class: com.android.internal.app.SimpleIconFactory$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SimpleIconFactory.this.lambda$createIconBitmap$1(rect2, (Drawable) obj);
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
                if (createBitmap != null && bitmap.getDensity() == 0) {
                    bitmapDrawable.setTargetDensity(this.mContext.getResources().getDisplayMetrics());
                }
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                float f2 = intrinsicWidth / intrinsicHeight;
                if (intrinsicWidth > intrinsicHeight) {
                    i3 = (int) (i / f2);
                    i2 = i;
                } else if (intrinsicHeight > intrinsicWidth) {
                    i2 = (int) (i * f2);
                    i3 = i;
                }
                int i5 = (i - i2) / 2;
                int i6 = (i - i3) / 2;
                drawable.setBounds(i5, i6, i2 + i5, i3 + i6);
                this.mCanvas.save();
                float f3 = i / 2;
                this.mCanvas.scale(f, f, f3, f3);
                drawable.draw(this.mCanvas);
                this.mCanvas.restore();
            }
            i2 = i;
            i3 = i2;
            int i52 = (i - i2) / 2;
            int i62 = (i - i3) / 2;
            drawable.setBounds(i52, i62, i2 + i52, i3 + i62);
            this.mCanvas.save();
            float f32 = i / 2;
            this.mCanvas.scale(f, f, f32, f32);
            drawable.draw(this.mCanvas);
            this.mCanvas.restore();
        }
        drawable.setBounds(this.mOldBounds);
        this.mCanvas.setBitmap(null);
        return createBitmap;
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

    /* JADX WARN: Code restructure failed: missing block: B:80:0x0040, code lost:
    
        if (r3 <= r18.mMaxSize) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00d4 A[Catch: all -> 0x0160, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000c, B:9:0x0014, B:10:0x0019, B:14:0x001d, B:18:0x002a, B:21:0x004c, B:25:0x007b, B:32:0x008c, B:35:0x0093, B:39:0x00a7, B:41:0x00b1, B:48:0x00c4, B:50:0x00d4, B:54:0x00e6, B:55:0x00df, B:58:0x00e9, B:61:0x0108, B:63:0x011a, B:64:0x013b, B:66:0x0142, B:67:0x0149, B:69:0x014d, B:71:0x0153, B:74:0x00fe, B:77:0x0030, B:79:0x003e, B:82:0x0046, B:84:0x004a, B:85:0x0042), top: B:3:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x011a A[Catch: all -> 0x0160, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000c, B:9:0x0014, B:10:0x0019, B:14:0x001d, B:18:0x002a, B:21:0x004c, B:25:0x007b, B:32:0x008c, B:35:0x0093, B:39:0x00a7, B:41:0x00b1, B:48:0x00c4, B:50:0x00d4, B:54:0x00e6, B:55:0x00df, B:58:0x00e9, B:61:0x0108, B:63:0x011a, B:64:0x013b, B:66:0x0142, B:67:0x0149, B:69:0x014d, B:71:0x0153, B:74:0x00fe, B:77:0x0030, B:79:0x003e, B:82:0x0046, B:84:0x004a, B:85:0x0042), top: B:3:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0142 A[Catch: all -> 0x0160, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000c, B:9:0x0014, B:10:0x0019, B:14:0x001d, B:18:0x002a, B:21:0x004c, B:25:0x007b, B:32:0x008c, B:35:0x0093, B:39:0x00a7, B:41:0x00b1, B:48:0x00c4, B:50:0x00d4, B:54:0x00e6, B:55:0x00df, B:58:0x00e9, B:61:0x0108, B:63:0x011a, B:64:0x013b, B:66:0x0142, B:67:0x0149, B:69:0x014d, B:71:0x0153, B:74:0x00fe, B:77:0x0030, B:79:0x003e, B:82:0x0046, B:84:0x004a, B:85:0x0042), top: B:3:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00fe A[Catch: all -> 0x0160, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000c, B:9:0x0014, B:10:0x0019, B:14:0x001d, B:18:0x002a, B:21:0x004c, B:25:0x007b, B:32:0x008c, B:35:0x0093, B:39:0x00a7, B:41:0x00b1, B:48:0x00c4, B:50:0x00d4, B:54:0x00e6, B:55:0x00df, B:58:0x00e9, B:61:0x0108, B:63:0x011a, B:64:0x013b, B:66:0x0142, B:67:0x0149, B:69:0x014d, B:71:0x0153, B:74:0x00fe, B:77:0x0030, B:79:0x003e, B:82:0x0046, B:84:0x004a, B:85:0x0042), top: B:3:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized float getScale(android.graphics.drawable.Drawable r19, android.graphics.RectF r20) {
        /*
            Method dump skipped, instructions count: 355
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.SimpleIconFactory.getScale(android.graphics.drawable.Drawable, android.graphics.RectF):float");
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

    private synchronized void recreateIcon(Bitmap bitmap, Canvas canvas) {
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            recreateIcon(bitmap, this.mDefaultBlurMaskFilter, 7, 10, canvas);
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    private synchronized void recreateIcon(Bitmap bitmap, BlurMaskFilter blurMaskFilter, int i, int i2, Canvas canvas) {
        this.mBlurPaint.setMaskFilter(blurMaskFilter);
        Bitmap extractAlpha = bitmap.extractAlpha(this.mBlurPaint, new int[2]);
        this.mDrawPaint.setAlpha(i);
        canvas.drawBitmap(extractAlpha, r0[0], r0[1], this.mDrawPaint);
        this.mDrawPaint.setAlpha(i2);
        canvas.drawBitmap(extractAlpha, r0[0], r0[1] + (this.mIconBitmapSize * KEY_SHADOW_DISTANCE), this.mDrawPaint);
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
            int save = canvas.save();
            canvas.scale(this.mScaleX, this.mScaleY, getBounds().exactCenterX(), getBounds().exactCenterY());
            super.draw(canvas);
            canvas.restoreToCount(save);
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
