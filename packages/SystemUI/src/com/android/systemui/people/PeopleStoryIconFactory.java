package com.android.systemui.people;

import android.R;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.view.ContextThemeWrapper;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PeopleStoryIconFactory implements AutoCloseable {
    public final int mAccentColor;
    public final Context mContext;
    public final float mDensity;
    public final int mIconBitmapSize;
    public final float mIconSize;
    public final int mImportantConversationColor;
    public final PackageManager mPackageManager;

    public PeopleStoryIconFactory(Context context, PackageManager packageManager, IconDrawableFactory iconDrawableFactory, int i) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.DayNight);
        this.mContext = contextThemeWrapper;
        float f = i;
        this.mIconBitmapSize = (int) (contextThemeWrapper.getResources().getDisplayMetrics().density * f);
        float f2 = contextThemeWrapper.getResources().getDisplayMetrics().density;
        this.mDensity = f2;
        this.mIconSize = f2 * f;
        this.mPackageManager = packageManager;
        this.mImportantConversationColor = contextThemeWrapper.getColor(com.android.systemui.R.color.important_conversation);
        this.mAccentColor = Utils.getColorAttr(R.^attr-private.colorAccentPrimaryVariant, contextThemeWrapper).getDefaultColor();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PeopleStoryIconDrawable extends Drawable {
        public final RoundedBitmapDrawable mAvatar;
        public final Drawable mBadgeIcon;
        public final float mDensity;
        public final float mFullIconSize;
        public final int mIconSize;
        public final Paint mPriorityRingPaint;
        public final boolean mShowImportantRing;
        public final boolean mShowStoryRing;
        public final Paint mStoryPaint;

        public PeopleStoryIconDrawable(RoundedBitmapDrawable roundedBitmapDrawable, Drawable drawable, int i, int i2, boolean z, float f, float f2, int i3, boolean z2) {
            roundedBitmapDrawable.mIsCircular = true;
            roundedBitmapDrawable.mApplyGravity = true;
            roundedBitmapDrawable.mCornerRadius = Math.min(roundedBitmapDrawable.mBitmapHeight, roundedBitmapDrawable.mBitmapWidth) / 2;
            roundedBitmapDrawable.mPaint.setShader(roundedBitmapDrawable.mBitmapShader);
            roundedBitmapDrawable.invalidateSelf();
            this.mAvatar = roundedBitmapDrawable;
            this.mBadgeIcon = drawable;
            this.mIconSize = i;
            this.mShowImportantRing = z;
            Paint paint = new Paint();
            this.mPriorityRingPaint = paint;
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(i2);
            this.mShowStoryRing = z2;
            Paint paint2 = new Paint();
            this.mStoryPaint = paint2;
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setColor(i3);
            this.mFullIconSize = f;
            this.mDensity = f2;
        }

        @Override // android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            Rect bounds = getBounds();
            float min = Math.min(bounds.height(), bounds.width()) / this.mFullIconSize;
            float f = this.mDensity;
            int i = (int) (f * 2.0f);
            int i2 = (int) (f * 2.0f);
            float f2 = i2;
            this.mPriorityRingPaint.setStrokeWidth(f2);
            this.mStoryPaint.setStrokeWidth(f2);
            int i3 = (int) (this.mFullIconSize * min);
            int i4 = i3 - (i * 2);
            if (this.mAvatar != null) {
                int i5 = i4 + i;
                if (this.mShowStoryRing) {
                    float f3 = i3 / 2;
                    canvas.drawCircle(f3, f3, (i4 - i2) / 2, this.mStoryPaint);
                    int i6 = i2 + i;
                    i += i6;
                    i5 -= i6;
                }
                this.mAvatar.setBounds(i, i, i5, i5);
                this.mAvatar.draw(canvas);
            } else {
                Log.w("PeopleStoryIconFactory", "Null avatar icon");
            }
            int min2 = Math.min((int) (this.mDensity * 40.0f), (int) (i4 / 2.4d));
            if (this.mBadgeIcon == null) {
                Log.w("PeopleStoryIconFactory", "Null badge icon");
                return;
            }
            int i7 = i3 - min2;
            if (this.mShowImportantRing) {
                float f4 = (min2 / 2) + i7;
                canvas.drawCircle(f4, f4, (min2 - i2) / 2, this.mPriorityRingPaint);
                i7 += i2;
                i3 -= i2;
            }
            this.mBadgeIcon.setBounds(i7, i7, i3, i3);
            this.mBadgeIcon.draw(canvas);
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicHeight() {
            return this.mIconSize;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicWidth() {
            return this.mIconSize;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            return -3;
        }

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {
            RoundedBitmapDrawable roundedBitmapDrawable = this.mAvatar;
            if (roundedBitmapDrawable != null) {
                roundedBitmapDrawable.setColorFilter(colorFilter);
            }
            Drawable drawable = this.mBadgeIcon;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {
        }
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
    }
}
