package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.android.app.animation.Interpolators;
import com.android.settingslib.Utils;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;

public class BarTransitions {
    public BarBackgroundDrawable mBarBackground;
    public int mMode;
    public final String mTag;
    public final View mView;

    public BarTransitions(View view, int i) {
        this.mTag = "BarTransitions.".concat(view.getClass().getSimpleName());
        this.mView = view;
        BarBackgroundDrawable barBackgroundDrawable = new BarBackgroundDrawable(view.getContext(), i);
        this.mBarBackground = barBackgroundDrawable;
        view.setBackground(barBackgroundDrawable);
    }

    public static String modeToString(int i) {
        if (i == 4) {
            return "MODE_OPAQUE";
        }
        if (i == 1) {
            return "MODE_SEMI_TRANSPARENT";
        }
        if (i == 2) {
            return "MODE_TRANSLUCENT";
        }
        if (i == 3) {
            return "MODE_LIGHTS_OUT";
        }
        if (i == 0) {
            return "MODE_TRANSPARENT";
        }
        if (i == 5) {
            return "MODE_WARNING";
        }
        if (i == 6) {
            return "MODE_LIGHTS_OUT_TRANSPARENT";
        }
        boolean z = BasicRune.NAVBAR_ENABLED;
        if (z && i == 7) {
            return "MODE_LIGHT_SEMI_TRANSPARENT";
        }
        if (z && i == 8) {
            return "MODE_ACTIVITY_EMBEDED";
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown mode "));
    }

    public final void applyModeBackground(int i, boolean z) {
        BarBackgroundDrawable barBackgroundDrawable = this.mBarBackground;
        if (barBackgroundDrawable.mMode == i) {
            return;
        }
        barBackgroundDrawable.mMode = i;
        barBackgroundDrawable.mAnimating = z;
        if (z) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            barBackgroundDrawable.mStartTime = elapsedRealtime;
            barBackgroundDrawable.mEndTime = elapsedRealtime + 200;
            barBackgroundDrawable.mGradientAlphaStart = barBackgroundDrawable.mGradientAlpha;
            barBackgroundDrawable.mColorStart = barBackgroundDrawable.mColor;
        }
        barBackgroundDrawable.invalidateSelf();
    }

    public boolean isLightsOut(int i) {
        return i == 3 || i == 6;
    }

    public void onTransition(int i, int i2, boolean z) {
        applyModeBackground(i2, z);
    }

    public final void transitionTo(int i, boolean z) {
        int i2 = this.mMode;
        if (i2 == i) {
            return;
        }
        this.mMode = i;
        Log.d(this.mTag, modeToString(i2) + " -> " + modeToString(i) + " animate=" + z);
        onTransition(i2, this.mMode, z);
    }

    public class BarBackgroundDrawable extends Drawable {
        public boolean mAnimating;
        public int mColor;
        public int mColorStart;
        public long mEndTime;
        public Rect mFrame;
        public final Drawable mGradient;
        public int mGradientAlpha;
        public int mGradientAlphaStart;
        public int mLightSemiTransparent;
        public final NavBarStateManager mNavBarStateManager;
        public int mOpaque;
        public final int mSemiTransparent;
        public long mStartTime;
        public PorterDuffColorFilter mTintFilter;
        public final int mTransparent;
        public boolean mUseFrame;
        public final int mWarning;
        public int mMode = -1;
        public float mOverrideAlpha = 1.0f;
        public final Paint mPaint = new Paint();

        public BarBackgroundDrawable(Context context, int i) {
            context.getResources();
            this.mOpaque = context.getColor(R.color.system_bar_background_opaque);
            this.mSemiTransparent = context.getColor(17171533);
            this.mTransparent = context.getColor(R.color.system_bar_background_transparent);
            this.mWarning = Utils.getColorAttrDefaultColor(context, android.R.attr.colorError, 0);
            this.mGradient = context.getDrawable(i);
            if (BasicRune.NAVBAR_ENABLED) {
                this.mNavBarStateManager = ((NavBarStoreImpl) ((NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class))).getNavStateManager(context.getDisplayId());
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            int i;
            int i2 = this.mMode;
            if (i2 == 5) {
                i = this.mWarning;
            } else if (i2 == 2) {
                i = this.mSemiTransparent;
            } else if (i2 == 1) {
                i = this.mSemiTransparent;
            } else if (i2 == 0 || i2 == 6) {
                i = this.mTransparent;
            } else {
                boolean z = BasicRune.NAVBAR_ENABLED;
                i = (z && i2 == 7) ? this.mLightSemiTransparent : (z && i2 == 8) ? ((NavBarStateManagerImpl) this.mNavBarStateManager).isNavigationBarUseThemeDefault() ? this.mLightSemiTransparent : this.mOpaque : this.mOpaque;
            }
            if (this.mAnimating) {
                if (SystemClock.elapsedRealtime() >= this.mEndTime) {
                    this.mAnimating = false;
                    this.mColor = i;
                    this.mGradientAlpha = 0;
                } else {
                    long j = this.mStartTime;
                    float max = Math.max(0.0f, Math.min(((LinearInterpolator) Interpolators.LINEAR).getInterpolation((r3 - j) / (r5 - j)), 1.0f));
                    float f = 1.0f - max;
                    this.mGradientAlpha = (int) ((this.mGradientAlphaStart * f) + (0 * max));
                    this.mColor = Color.argb((int) ((Color.alpha(this.mColorStart) * f) + (Color.alpha(i) * max)), (int) ((Color.red(this.mColorStart) * f) + (Color.red(i) * max)), (int) ((Color.green(this.mColorStart) * f) + (Color.green(i) * max)), (int) ((Color.blue(this.mColorStart) * f) + (max * Color.blue(i))));
                }
            } else {
                this.mColor = i;
                this.mGradientAlpha = 0;
            }
            int i3 = this.mGradientAlpha;
            if (i3 > 0) {
                this.mGradient.setAlpha(i3);
                this.mGradient.draw(canvas);
            }
            if (Color.alpha(this.mColor) > 0) {
                this.mPaint.setColor(this.mColor);
                PorterDuffColorFilter porterDuffColorFilter = this.mTintFilter;
                if (porterDuffColorFilter != null) {
                    this.mPaint.setColorFilter(porterDuffColorFilter);
                }
                this.mPaint.setAlpha((int) (Color.alpha(this.mColor) * this.mOverrideAlpha));
                Rect rect = this.mFrame;
                if (rect == null) {
                    canvas.drawPaint(this.mPaint);
                } else if (!BasicRune.NAVBAR_LIGHTBAR || this.mUseFrame) {
                    canvas.drawRect(rect, this.mPaint);
                } else {
                    canvas.drawPaint(this.mPaint);
                }
            }
            if (this.mAnimating) {
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            return -3;
        }

        @Override // android.graphics.drawable.Drawable
        public final void onBoundsChange(Rect rect) {
            super.onBoundsChange(rect);
            this.mGradient.setBounds(rect);
        }

        @Override // android.graphics.drawable.Drawable
        public final void setTint(int i) {
            PorterDuffColorFilter porterDuffColorFilter = this.mTintFilter;
            PorterDuff.Mode mode = porterDuffColorFilter == null ? PorterDuff.Mode.SRC_IN : porterDuffColorFilter.getMode();
            PorterDuffColorFilter porterDuffColorFilter2 = this.mTintFilter;
            if (porterDuffColorFilter2 == null || porterDuffColorFilter2.getColor() != i) {
                this.mTintFilter = new PorterDuffColorFilter(i, mode);
            }
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public final void setTintMode(PorterDuff.Mode mode) {
            PorterDuffColorFilter porterDuffColorFilter = this.mTintFilter;
            int color = porterDuffColorFilter == null ? 0 : porterDuffColorFilter.getColor();
            PorterDuffColorFilter porterDuffColorFilter2 = this.mTintFilter;
            if (porterDuffColorFilter2 == null || porterDuffColorFilter2.getMode() != mode) {
                this.mTintFilter = new PorterDuffColorFilter(color, mode);
            }
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {
        }

        public void updateOpaqueColor(int i) {
        }
    }
}
