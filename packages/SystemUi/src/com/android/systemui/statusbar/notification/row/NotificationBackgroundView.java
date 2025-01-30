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
import com.android.internal.util.ArrayUtils;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.util.ColorUtilKt;
import java.io.PrintWriter;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationBackgroundView extends View implements Dumpable {
    public int mActualHeight;
    public int mActualWidth;
    public Drawable mBackground;
    public boolean mBottomAmountClips;
    public boolean mBottomIsRounded;
    public int mClipBottomAmount;
    public int mClipTopAmount;
    public final float[] mCornerRadii;
    public float mDistanceToTopRoundness;
    public final boolean mDontModifyCorners;
    public int mDrawableAlpha;
    public int mExpandAnimationHeight;
    public boolean mExpandAnimationRunning;
    public int mExpandAnimationWidth;
    public boolean mIsPinned;
    public boolean mIsPressedAllowed;
    public boolean mOnKeyguard;
    public Integer mRippleColor;
    public int mTintColor;

    public NotificationBackgroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCornerRadii = new float[8];
        this.mBottomAmountClips = true;
        this.mActualHeight = -1;
        this.mActualWidth = -1;
        this.mExpandAnimationWidth = -1;
        this.mExpandAnimationHeight = -1;
        this.mDrawableAlpha = 255;
        this.mDontModifyCorners = getResources().getBoolean(R.bool.config_clipNotificationsToOutline);
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
        if (!this.mIsPressedAllowed) {
            drawableState = ArrayUtils.removeInt(drawableState, android.R.attr.state_pressed);
        }
        this.mBackground.setState(drawableState);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m77m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("mDontModifyCorners: "), this.mDontModifyCorners, printWriter, "mClipTopAmount: "), this.mClipTopAmount, printWriter, "mClipBottomAmount: "), this.mClipBottomAmount, printWriter, "mCornerRadii: ");
        m77m.append(Arrays.toString(this.mCornerRadii));
        printWriter.println(m77m.toString());
        StringBuilder m77m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("mBottomIsRounded: "), this.mBottomIsRounded, printWriter, "mBottomAmountClips: "), this.mBottomAmountClips, printWriter, "mActualWidth: "), this.mActualWidth, printWriter, "mActualHeight: ");
        m77m2.append(this.mActualHeight);
        printWriter.println(m77m2.toString());
        printWriter.println("mTintColor: ".concat(ColorUtilKt.hexColorString(Integer.valueOf(this.mTintColor))));
        printWriter.println("mRippleColor: ".concat(ColorUtilKt.hexColorString(this.mRippleColor)));
        printWriter.println("mBackground: " + this.mBackground);
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
                if (this.mBottomIsRounded && this.mBottomAmountClips && !this.mExpandAnimationRunning && (this.mIsPinned || this.mOnKeyguard)) {
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
        if (!this.mDontModifyCorners) {
            Drawable drawable5 = this.mBackground;
            if (drawable5 instanceof LayerDrawable) {
                ((GradientDrawable) ((LayerDrawable) drawable5).getDrawable(0)).setCornerRadii(this.mCornerRadii);
            }
        }
        invalidate();
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mBackground;
    }
}
