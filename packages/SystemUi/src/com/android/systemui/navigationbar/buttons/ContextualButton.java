package com.android.systemui.navigationbar.buttons;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.samsung.systemui.splugins.navigationbar.IconType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ContextualButton extends ButtonDispatcher {
    public ContextualButtonGroup mGroup;
    public final int mIconResId;
    public final IconType mIconType;
    public final Context mLightContext;
    public RotationContextButton$$ExternalSyntheticLambda0 mListener;

    public ContextualButton(int i, Context context, int i2) {
        super(i);
        this.mIconType = null;
        this.mLightContext = context;
        this.mIconResId = i2;
    }

    public KeyButtonDrawable getNewDrawable(int i, int i2) {
        IconType iconType;
        return (!BasicRune.NAVBAR_ENABLED || (iconType = this.mIconType) == null) ? KeyButtonDrawable.create(this.mLightContext, i, i2, this.mIconResId, false) : this.mGroup.mKeyButtonMapper.getButtonDrawable(iconType);
    }

    public final boolean hide() {
        ContextualButtonGroup contextualButtonGroup = this.mGroup;
        if (contextualButtonGroup != null) {
            return contextualButtonGroup.setButtonVisibility(this.mId, false) != 0;
        }
        setVisibility(4);
        return false;
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonDispatcher
    public void setVisibility(int i) {
        AutoHideController autoHideController;
        super.setVisibility(i);
        KeyButtonDrawable keyButtonDrawable = this.mImageDrawable;
        if (i != 0 && keyButtonDrawable != null && keyButtonDrawable.mState.mSupportsAnimation) {
            AnimatedVectorDrawable animatedVectorDrawable = keyButtonDrawable.mAnimatedDrawable;
            if (animatedVectorDrawable != null) {
                animatedVectorDrawable.clearAnimationCallbacks();
            }
            AnimatedVectorDrawable animatedVectorDrawable2 = keyButtonDrawable.mAnimatedDrawable;
            if (animatedVectorDrawable2 != null) {
                animatedVectorDrawable2.reset();
            }
        }
        RotationContextButton$$ExternalSyntheticLambda0 rotationContextButton$$ExternalSyntheticLambda0 = this.mListener;
        if (rotationContextButton$$ExternalSyntheticLambda0 != null) {
            boolean z = i == 0;
            NavigationBarView.C18592 c18592 = rotationContextButton$$ExternalSyntheticLambda0.f$0;
            if (c18592 != null) {
                NavigationBarView navigationBarView = NavigationBarView.this;
                if (z && (autoHideController = navigationBarView.mAutoHideController) != null) {
                    autoHideController.touchAutoHide();
                }
                navigationBarView.notifyActiveTouchRegions();
            }
        }
    }

    public final boolean show() {
        ContextualButtonGroup contextualButtonGroup = this.mGroup;
        if (contextualButtonGroup != null) {
            return contextualButtonGroup.setButtonVisibility(this.mId, true) == 0;
        }
        setVisibility(0);
        return true;
    }

    public final void updateIcon(int i, int i2) {
        if (this.mIconType == null && this.mIconResId == 0) {
            return;
        }
        KeyButtonDrawable keyButtonDrawable = this.mImageDrawable;
        KeyButtonDrawable newDrawable = getNewDrawable(i, i2);
        if (keyButtonDrawable != null) {
            newDrawable.setDarkIntensity(keyButtonDrawable.mState.mDarkIntensity);
        }
        setImageDrawable(newDrawable);
    }

    public ContextualButton(int i, Context context, IconType iconType) {
        super(i);
        this.mIconType = null;
        this.mIconResId = 0;
        this.mLightContext = context;
        this.mIconType = iconType;
        super.setVisibility(4);
    }
}
