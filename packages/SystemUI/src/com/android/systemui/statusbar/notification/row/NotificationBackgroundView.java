package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.shared.NotificationAddXOnHoverToDismiss;
import com.android.systemui.util.ColorUtilKt;
import com.android.systemui.util.DrawableDumpKt;
import java.io.PrintWriter;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class NotificationBackgroundView extends View implements Dumpable {
    public int mActualHeight;
    public int mActualWidth;
    public SeslRecoilDrawable mBackground;
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

    public NotificationBackgroundView(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
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
        ((View) this).mContext.getColor(android.R.color.sliding_tab_text_color_shadow);
        getResources().getDimension(R.dimen.notification_focus_stroke_width);
    }

    @Override // android.view.View
    public final void drawableHotspotChanged(float f, float f2) {
        SeslRecoilDrawable seslRecoilDrawable = this.mBackground;
        if (seslRecoilDrawable != null) {
            seslRecoilDrawable.setHotspot(f, f2);
        }
    }

    @Override // android.view.View
    public final void drawableStateChanged() {
        int[] drawableState = getDrawableState();
        SeslRecoilDrawable seslRecoilDrawable = this.mBackground;
        if (seslRecoilDrawable != null) {
            seslRecoilDrawable.setState(drawableState);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("mDontModifyCorners: "), this.mDontModifyCorners, printWriter, "mClipTopAmount: "), this.mClipTopAmount, printWriter, "mClipBottomAmount: "), this.mClipBottomAmount, printWriter, "mCornerRadii: ");
        sbM.append(Arrays.toString(this.mCornerRadii));
        printWriter.println(sbM.toString());
        StringBuilder sbM2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("mBottomIsRounded: "), this.mBottomIsRounded, printWriter, "mBottomAmountClips: "), this.mBottomAmountClips, printWriter, "mActualWidth: "), this.mActualWidth, printWriter, "mActualHeight: "), this.mActualHeight, printWriter, "mTintColor: ");
        sbM2.append(ColorUtilKt.hexColorString(Integer.valueOf(this.mTintColor)));
        printWriter.println(sbM2.toString());
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

    public boolean isAlignedToRight() {
        return isLayoutRtl();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int width;
        if (this.mClipTopAmount + this.mClipBottomAmount < getActualHeight() || this.mExpandAnimationRunning) {
            canvas.save();
            if (!this.mExpandAnimationRunning) {
                canvas.clipRect(0, this.mClipTopAmount, getWidth(), getActualHeight() - this.mClipBottomAmount);
            }
            int i = NotificationAddXOnHoverToDismiss.$r8$clinit;
            SeslRecoilDrawable seslRecoilDrawable = this.mBackground;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            if (seslRecoilDrawable != null) {
                int actualHeight = getActualHeight();
                if (this.mBottomClipRounded || (this.mBottomIsRounded && this.mBottomAmountClips && !this.mExpandAnimationRunning && this.mIsPinned)) {
                    actualHeight -= this.mClipBottomAmount;
                }
                boolean zIsAlignedToRight = isAlignedToRight();
                int width2 = getWidth();
                if ((!this.mExpandAnimationRunning || (width = this.mExpandAnimationWidth) <= -1) && (width = this.mActualWidth) <= -1) {
                    width = getWidth();
                }
                int i2 = zIsAlignedToRight ? width2 - width : 0;
                int i3 = zIsAlignedToRight ? width2 : width;
                if (this.mExpandAnimationRunning) {
                    i2 = (int) ((width2 - width) / 2.0f);
                    i3 = i2 + width;
                }
                if (this.mBgWidth != 0 && getWidth() - this.mBgWidth > 0) {
                    int width3 = (getWidth() - this.mBgWidth) / 2;
                    i2 += width3;
                    i3 -= width3;
                }
                seslRecoilDrawable.setBounds(i2, 0, i3, actualHeight);
                seslRecoilDrawable.draw(canvas);
            }
            canvas.restore();
        }
    }

    public final void setCustomBackground(SeslRecoilDrawable seslRecoilDrawable) {
        SeslRecoilDrawable seslRecoilDrawable2 = this.mBackground;
        if (seslRecoilDrawable2 != null) {
            seslRecoilDrawable2.setCallback(null);
            unscheduleDrawable(this.mBackground);
        }
        this.mBackground = seslRecoilDrawable;
        this.mRippleColor = null;
        seslRecoilDrawable.mutate();
        SeslRecoilDrawable seslRecoilDrawable3 = this.mBackground;
        if (seslRecoilDrawable3 != null) {
            seslRecoilDrawable3.setCallback(this);
            int i = this.mTintColor;
            if (i != 0) {
                this.mBackground.setColorFilter(i, PorterDuff.Mode.SRC);
            } else {
                this.mBackground.clearColorFilter();
            }
            this.mTintColor = i;
            invalidate();
        }
        updateBackgroundRadii();
        invalidate();
    }

    public String toDumpString() {
        return "<NotificationBackgroundView tintColor=" + ColorUtilKt.hexColorString(Integer.valueOf(this.mTintColor)) + " rippleColor=" + ColorUtilKt.hexColorString(this.mRippleColor) + " bgColor=" + DrawableDumpKt.getSolidColor(this.mBackground) + ">";
    }

    public final void updateBackgroundRadii() {
        SeslRecoilDrawable seslRecoilDrawable;
        if (this.mDontModifyCorners || (seslRecoilDrawable = this.mBackground) == null) {
            return;
        }
        int numberOfLayers = seslRecoilDrawable.getNumberOfLayers();
        for (int i = 0; i < numberOfLayers; i++) {
            ((GradientDrawable) seslRecoilDrawable.getDrawable(i)).setCornerRadii(this.mCornerRadii);
        }
        GradientDrawable gradientDrawable = (GradientDrawable) seslRecoilDrawable.findDrawableByLayerId(R.id.notification_focus_overlay);
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
