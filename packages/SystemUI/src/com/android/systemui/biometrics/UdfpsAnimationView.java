package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
