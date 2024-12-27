package com.android.systemui.navigationbar.buttons;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import com.samsung.systemui.splugins.navigationbar.IconType;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ContextualButton extends ButtonDispatcher {
    public ContextualButtonGroup mGroup;
    public final int mIconResId;
    public final IconType mIconType;
    public final Context mLightContext;

    public ContextualButton(int i, Context context, int i2) {
        super(i);
        this.mIconType = null;
        this.mLightContext = context;
        this.mIconResId = i2;
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonDispatcher
    public final void setVisibility(int i) {
        super.setVisibility(i);
        KeyButtonDrawable keyButtonDrawable = this.mImageDrawable;
        if (i == 0 || keyButtonDrawable == null || !keyButtonDrawable.mState.mSupportsAnimation) {
            return;
        }
        AnimatedVectorDrawable animatedVectorDrawable = keyButtonDrawable.mAnimatedDrawable;
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.clearAnimationCallbacks();
        }
        AnimatedVectorDrawable animatedVectorDrawable2 = keyButtonDrawable.mAnimatedDrawable;
        if (animatedVectorDrawable2 != null) {
            animatedVectorDrawable2.reset();
        }
    }

    public ContextualButton(int i, Context context, IconType iconType) {
        super(i);
        this.mIconResId = 0;
        this.mLightContext = context;
        this.mIconType = iconType;
        super.setVisibility(4);
    }
}
