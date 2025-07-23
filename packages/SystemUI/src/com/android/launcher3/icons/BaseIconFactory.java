package com.android.launcher3.icons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.SparseArray;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.ClockDrawableWrapper;
import com.android.launcher3.util.FlagOp;
import com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda0;
import com.android.launcher3.util.UserIconInfo;
import com.android.systemui.R;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BaseIconFactory implements AutoCloseable {
    public static final float LEGACY_ICON_SCALE = (1.0f / ((AdaptiveIconDrawable.getExtraInsetFraction() * 2.0f) + 1.0f)) * 0.7f;
    public final Canvas mCanvas;
    public final Context mContext;
    public final int mIconBitmapSize;
    public final Rect mOldBounds;
    public ShadowGenerator mShadowGenerator;
    public int mWrapperBackgroundColor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        AdaptiveIconDrawable normalizeAndWrapToAdaptiveIcon = normalizeAndWrapToAdaptiveIcon(drawable, fArr);
        Bitmap createIconBitmap = createIconBitmap(normalizeAndWrapToAdaptiveIcon, fArr[0], iconOptions.mGenerationMode);
        Integer num = iconOptions.mExtractedColor;
        int intValue = num != null ? num.intValue() : ColorExtractor.findDominantColorByHue(createIconBitmap);
        BitmapInfo bitmapInfo = new BitmapInfo(createIconBitmap, intValue);
        if (normalizeAndWrapToAdaptiveIcon instanceof BitmapInfo.Extender) {
            float f = fArr[0];
            ClockDrawableWrapper clockDrawableWrapper = (ClockDrawableWrapper) ((BitmapInfo.Extender) normalizeAndWrapToAdaptiveIcon);
            clockDrawableWrapper.getClass();
            i = 4;
            bitmapInfo = new ClockDrawableWrapper.ClockBitmapInfo(createIconBitmap, intValue, f, clockDrawableWrapper.mAnimationInfo, createScaledBitmap(new AdaptiveIconDrawable(clockDrawableWrapper.getBackground().getConstantState().newDrawable(), null), 4), null, null);
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
        BitmapInfo mo972clone = bitmapInfo.mo972clone();
        mo972clone.flags = flagOp.apply(mo972clone.flags);
        return mo972clone;
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
    /* JADX WARN: Removed duplicated region for block: B:46:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x014c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void drawIconBitmap(android.graphics.Canvas r11, android.graphics.drawable.Drawable r12, float r13, int r14, android.graphics.Bitmap r15) {
        /*
            Method dump skipped, instructions count: 408
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.launcher3.icons.BaseIconFactory.drawIconBitmap(android.graphics.Canvas, android.graphics.drawable.Drawable, float, int, android.graphics.Bitmap):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:92:0x0062, code lost:
    
        if (r0 <= r5.mMaxSize) goto L30;
     */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00f7 A[Catch: all -> 0x005b, TryCatch #0 {all -> 0x005b, blocks: (B:12:0x0033, B:30:0x003c, B:34:0x0049, B:37:0x006e, B:41:0x009f, B:48:0x00b0, B:51:0x00b7, B:55:0x00cb, B:57:0x00d5, B:64:0x00e7, B:66:0x00f7, B:70:0x010a, B:71:0x0102, B:74:0x010d, B:81:0x0144, B:89:0x004f, B:91:0x0060, B:94:0x0068, B:96:0x006c, B:97:0x0064), top: B:11:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0131  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.drawable.AdaptiveIconDrawable normalizeAndWrapToAdaptiveIcon(android.graphics.drawable.Drawable r20, float[] r21) {
        /*
            Method dump skipped, instructions count: 402
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.launcher3.icons.BaseIconFactory.normalizeAndWrapToAdaptiveIcon(android.graphics.drawable.Drawable, float[]):android.graphics.drawable.AdaptiveIconDrawable");
    }

    public BaseIconFactory(Context context, int i, int i2) {
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
        Bitmap createBitmap;
        int i3 = this.mIconBitmapSize;
        if (i == 1) {
            baseIconFactory = this;
            drawable2 = drawable;
            f2 = f;
            i2 = i;
            createBitmap = Bitmap.createBitmap(i3, i3, Bitmap.Config.ALPHA_8);
        } else if (i != 3 && i != 4) {
            createBitmap = Bitmap.createBitmap(i3, i3, Bitmap.Config.ARGB_8888);
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
        Bitmap bitmap = createBitmap;
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
