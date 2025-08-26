package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/* loaded from: classes.dex */
public abstract class UdfpsAnimationView extends FrameLayout {
    public float mDialogSuggestedAlpha;
    public float mNotificationShadeExpansion;
    public boolean mPauseAuth;

    public UdfpsAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDialogSuggestedAlpha = 1.0f;
        this.mNotificationShadeExpansion = 0.0f;
    }

    public int calculateAlpha() {
        int i = (int) ((this.mNotificationShadeExpansion >= 0.4f ? 0 : (int) ((1.0f - (r0 / 0.4f)) * 255.0f)) * this.mDialogSuggestedAlpha);
        if (this.mPauseAuth) {
            return i;
        }
        return 255;
    }

    public abstract UdfpsFpDrawable getDrawable();

    public final void updateAlpha() {
        int iCalculateAlpha = calculateAlpha();
        getDrawable().setAlpha(iCalculateAlpha);
        if (this.mPauseAuth && iCalculateAlpha == 0 && getParent() != null) {
            ((ViewGroup) getParent()).setVisibility(4);
        } else {
            ((ViewGroup) getParent()).setVisibility(0);
        }
    }
}
