package android.util;

import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.LayerDrawable;
import android.media.audio.Enums;
import com.android.internal.R;

/* loaded from: classes4.dex */
public final class LauncherIcons {
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final float ICON_SIZE_BLUR_FACTOR = 0.010416667f;
    private static final float ICON_SIZE_KEY_SHADOW_DELTA_FACTOR = 0.020833334f;
    private static final int KEY_SHADOW_ALPHA = 61;
    private final Context mContext;
    private final int mIconSize;
    private final Resources mRes;
    private final SparseArray<Bitmap> mShadowCache = new SparseArray<>();

    public LauncherIcons(Context context) {
        Resources resources = context.getResources();
        this.mRes = resources;
        this.mIconSize = resources.getDimensionPixelSize(17104896);
        this.mContext = context;
    }

    public Drawable wrapIconDrawableWithShadow(Drawable drawable) {
        return !(drawable instanceof AdaptiveIconDrawable) ? drawable : new ShadowDrawable(getShadowBitmap((AdaptiveIconDrawable) drawable), drawable);
    }

    private Bitmap getShadowBitmap(AdaptiveIconDrawable adaptiveIconDrawable) {
        int iMax = Math.max(this.mIconSize, adaptiveIconDrawable.getIntrinsicHeight());
        synchronized (this.mShadowCache) {
            Bitmap bitmap = this.mShadowCache.get(iMax);
            if (bitmap != null) {
                return bitmap;
            }
            adaptiveIconDrawable.setBounds(0, 0, iMax, iMax);
            float f = iMax;
            float f2 = ICON_SIZE_BLUR_FACTOR * f;
            float f3 = ICON_SIZE_KEY_SHADOW_DELTA_FACTOR * f;
            int i = (int) (f + (f2 * 2.0f) + f3);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.translate((f3 / 2.0f) + f2, f2);
            Paint paint = new Paint(1);
            paint.setColor(0);
            paint.setShadowLayer(f2, 0.0f, 0.0f, Enums.AUDIO_FORMAT_AAC_ADTS);
            canvas.drawPath(adaptiveIconDrawable.getIconMask(), paint);
            canvas.translate(0.0f, f3);
            paint.setShadowLayer(f2, 0.0f, 0.0f, 1023410176);
            canvas.drawPath(adaptiveIconDrawable.getIconMask(), paint);
            canvas.setBitmap(null);
            synchronized (this.mShadowCache) {
                this.mShadowCache.put(iMax, bitmapCreateBitmap);
            }
            return bitmapCreateBitmap;
        }
    }

    public Drawable getBadgeDrawable(Drawable drawable, int i) {
        return getBadgedDrawable(null, drawable, i);
    }

    public Drawable getBadgedDrawable(Drawable drawable, Drawable drawable2, int i) throws Resources.NotFoundException {
        Drawable[] drawableArr;
        Resources resources = ActivityThread.currentActivityThread().getApplication().getResources();
        Drawable drawable3 = resources.getDrawable(R.drawable.ic_corp_icon_badge_shadow);
        Drawable drawableMutate = resources.getDrawable(R.drawable.ic_corp_icon_badge_color).getConstantState().newDrawable().mutate();
        drawable2.setTint(i);
        if (drawable == null) {
            drawableArr = new Drawable[]{drawable3, drawableMutate, drawable2};
        } else {
            drawableArr = new Drawable[]{drawable, drawable3, drawableMutate, drawable2};
        }
        return new LayerDrawable(drawableArr);
    }

    private static class ShadowDrawable extends DrawableWrapper {
        final MyConstantState mState;

        public ShadowDrawable(Bitmap bitmap, Drawable drawable) {
            super(drawable);
            this.mState = new MyConstantState(bitmap, drawable.getConstantState());
        }

        ShadowDrawable(MyConstantState myConstantState) {
            super(myConstantState.mChildState.newDrawable());
            this.mState = myConstantState;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public Drawable.ConstantState getConstantState() {
            return this.mState;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            canvas.drawBitmap(this.mState.mShadow, (Rect) null, getBounds(), this.mState.mPaint);
            canvas.save();
            canvas.translate(r0.width() * 0.9599999f * LauncherIcons.ICON_SIZE_KEY_SHADOW_DELTA_FACTOR, r0.height() * 0.9599999f * LauncherIcons.ICON_SIZE_BLUR_FACTOR);
            canvas.scale(0.9599999f, 0.9599999f);
            super.draw(canvas);
            canvas.restore();
        }

        private static class MyConstantState extends Drawable.ConstantState {
            final Drawable.ConstantState mChildState;
            final Paint mPaint = new Paint(2);
            final Bitmap mShadow;

            MyConstantState(Bitmap bitmap, Drawable.ConstantState constantState) {
                this.mShadow = bitmap;
                this.mChildState = constantState;
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public Drawable newDrawable() {
                return new ShadowDrawable(this);
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public int getChangingConfigurations() {
                return this.mChildState.getChangingConfigurations();
            }
        }
    }
}
