package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class UdfpsAnimationView extends FrameLayout {
    public float mDialogSuggestedAlpha;
    public float mNotificationShadeExpansion;
    public boolean mPauseAuth;
    public boolean mUseExpandedOverlay;

    public UdfpsAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDialogSuggestedAlpha = 1.0f;
        this.mNotificationShadeExpansion = 0.0f;
        this.mUseExpandedOverlay = false;
    }

    public int calculateAlpha() {
        int i = (int) ((this.mNotificationShadeExpansion >= 0.4f ? 0 : (int) ((1.0f - (r0 / 0.4f)) * 255.0f)) * this.mDialogSuggestedAlpha);
        if (this.mPauseAuth) {
            return i;
        }
        return 255;
    }

    public boolean dozeTimeTick() {
        return false;
    }

    public abstract UdfpsFpDrawable getDrawable();

    public void onDisplayConfiguring() {
        UdfpsFpDrawable drawable = getDrawable();
        if (!drawable.isDisplayConfigured) {
            drawable.isDisplayConfigured = true;
            drawable.invalidateSelf();
        }
        getDrawable().invalidateSelf();
    }

    public void onDisplayUnconfigured() {
        UdfpsFpDrawable drawable = getDrawable();
        if (drawable.isDisplayConfigured) {
            drawable.isDisplayConfigured = false;
            drawable.invalidateSelf();
        }
        getDrawable().invalidateSelf();
    }

    public void onSensorRectUpdated(RectF rectF) {
        UdfpsFpDrawable drawable = getDrawable();
        drawable.getClass();
        int height = ((int) rectF.height()) / 8;
        drawable.fingerprintDrawable.setBounds(new Rect(((int) rectF.left) + height, ((int) rectF.top) + height, ((int) rectF.right) - height, ((int) rectF.bottom) - height));
        drawable.invalidateSelf();
    }

    public int updateAlpha() {
        int calculateAlpha = calculateAlpha();
        getDrawable().setAlpha(calculateAlpha);
        if (this.mPauseAuth && calculateAlpha == 0 && getParent() != null) {
            ((ViewGroup) getParent()).setVisibility(4);
        } else {
            ((ViewGroup) getParent()).setVisibility(0);
        }
        return calculateAlpha;
    }
}
