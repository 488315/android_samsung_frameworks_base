package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.View;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.util.ColorUtilKt;
import com.android.systemui.util.DrawableDumpKt;
import java.io.PrintWriter;
import java.util.Arrays;

public class NotificationBackgroundView extends View implements Dumpable {
    public int mActualHeight;
    public int mActualWidth;
    public Drawable mBackground;
    public int mBgWidth;
    public boolean mBottomAmountClips;
    public boolean mBottomClipRounded;
    public boolean mBottomIsRounded;
    public int mClipBottomAmount;
    public int mClipTopAmount;
    public final float[] mCornerRadii;
    public final boolean mDontModifyCorners;
    public int mDrawableAlpha;
    public int mExpandAnimationHeight;
    public boolean mExpandAnimationRunning;
    public int mExpandAnimationWidth;
    public final float[] mFocusOverlayCornerRadii;
    public boolean mIsPinned;
    public Integer mRippleColor;
    public int mTintColor;

    public NotificationBackgroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCornerRadii = new float[8];
        this.mFocusOverlayCornerRadii = new float[8];
        this.mBottomAmountClips = true;
        this.mActualHeight = -1;
        this.mActualWidth = -1;
        this.mExpandAnimationWidth = -1;
        this.mExpandAnimationHeight = -1;
        this.mDrawableAlpha = 255;
        this.mDontModifyCorners = getResources().getBoolean(R.bool.config_clipNotificationsToOutline);
        getResources().getColorStateList(R.color.notification_state_color_light);
        getResources().getColorStateList(R.color.notification_state_color_dark);
        Utils.getColorAttrDefaultColor(((View) this).mContext, android.R.^attr-private.materialColorSurfaceContainerLowest, 0);
        getResources().getDimension(R.dimen.notification_focus_stroke_width);
    }

    @Override // android.view.View
    public final void drawableHotspotChanged(float f, float f2) {
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    @Override // android.view.View
    public final void drawableStateChanged() {
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mBackground;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        this.mBackground.setState(drawableState);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("mDontModifyCorners: "), this.mDontModifyCorners, printWriter, "mClipTopAmount: "), this.mClipTopAmount, printWriter, "mClipBottomAmount: "), this.mClipBottomAmount, printWriter, "mCornerRadii: ");
        m.append(Arrays.toString(this.mCornerRadii));
        printWriter.println(m.toString());
        StringBuilder m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("mBottomIsRounded: "), this.mBottomIsRounded, printWriter, "mBottomAmountClips: "), this.mBottomAmountClips, printWriter, "mActualWidth: "), this.mActualWidth, printWriter, "mActualHeight: "), this.mActualHeight, printWriter, "mTintColor: ");
        m2.append(ColorUtilKt.hexColorString(Integer.valueOf(this.mTintColor)));
        printWriter.println(m2.toString());
        printWriter.println("mRippleColor: " + ColorUtilKt.hexColorString(this.mRippleColor));
        printWriter.println("mBackground: " + DrawableDumpKt.dumpToString(this.mBackground));
    }

    public final int getActualHeight() {
        int i;
        if (this.mExpandAnimationRunning && (i = this.mExpandAnimationHeight) > -1) {
            return i;
        }
        int i2 = this.mActualHeight;
        return i2 > -1 ? i2 : getHeight();
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int i;
        if (this.mClipTopAmount + this.mClipBottomAmount < getActualHeight() || this.mExpandAnimationRunning) {
            canvas.save();
            if (!this.mExpandAnimationRunning) {
                canvas.clipRect(0, this.mClipTopAmount, getWidth(), getActualHeight() - this.mClipBottomAmount);
            }
            Drawable drawable = this.mBackground;
            if (drawable != null) {
                int actualHeight = getActualHeight();
                if (this.mBottomClipRounded || (this.mBottomIsRounded && this.mBottomAmountClips && !this.mExpandAnimationRunning && this.mIsPinned)) {
                    actualHeight -= this.mClipBottomAmount;
                }
                boolean isLayoutRtl = isLayoutRtl();
                int width = getWidth();
                if ((!this.mExpandAnimationRunning || (i = this.mExpandAnimationWidth) <= -1) && (i = this.mActualWidth) <= -1) {
                    i = getWidth();
                }
                int i2 = isLayoutRtl ? width - i : 0;
                int i3 = isLayoutRtl ? width : i;
                if (this.mExpandAnimationRunning) {
                    i2 = (int) ((width - i) / 2.0f);
                    i3 = i2 + i;
                }
                if (this.mBgWidth != 0 && getWidth() - this.mBgWidth > 0) {
                    int width2 = (getWidth() - this.mBgWidth) / 2;
                    i2 += width2;
                    i3 -= width2;
                }
                drawable.setBounds(i2, 0, i3, actualHeight);
                drawable.draw(canvas);
            }
            canvas.restore();
        }
    }

    public final void setCustomBackground(Drawable drawable) {
        Drawable drawable2 = this.mBackground;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.mBackground);
        }
        this.mBackground = drawable;
        this.mRippleColor = null;
        drawable.mutate();
        Drawable drawable3 = this.mBackground;
        if (drawable3 != null) {
            drawable3.setCallback(this);
            int i = this.mTintColor;
            if (i != 0) {
                this.mBackground.setColorFilter(i, PorterDuff.Mode.SRC);
            } else {
                this.mBackground.clearColorFilter();
            }
            this.mTintColor = i;
            invalidate();
        }
        Drawable drawable4 = this.mBackground;
        if (drawable4 instanceof RippleDrawable) {
            ((RippleDrawable) drawable4).setForceSoftware(true);
        }
        updateBackgroundRadii();
        invalidate();
    }

    public final void updateBackgroundRadii() {
        if (this.mDontModifyCorners) {
            return;
        }
        Drawable drawable = this.mBackground;
        if (!(drawable instanceof LayerDrawable)) {
            return;
        }
        LayerDrawable layerDrawable = (LayerDrawable) drawable;
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        for (int i = 0; i < numberOfLayers; i++) {
            ((GradientDrawable) layerDrawable.getDrawable(i)).setCornerRadii(this.mCornerRadii);
        }
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.notification_focus_overlay);
        int i2 = 0;
        while (true) {
            float[] fArr = this.mCornerRadii;
            if (i2 >= fArr.length) {
                gradientDrawable.setCornerRadii(this.mFocusOverlayCornerRadii);
                return;
            } else {
                this.mFocusOverlayCornerRadii[i2] = Math.max(0.0f, fArr[i2] + (i2 >= 4 ? 1 : 0));
                i2++;
            }
        }
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mBackground;
    }
}
