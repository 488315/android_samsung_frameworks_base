package com.android.keyguard;

import android.content.res.Resources;
import android.text.method.MovementMethod;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.ConfigurationController;

public final class KeyguardSecMessageAreaController extends KeyguardMessageAreaController {
    public KeyguardSecMessageAreaController(BouncerKeyguardMessageArea bouncerKeyguardMessageArea, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController) {
        super(bouncerKeyguardMessageArea, keyguardUpdateMonitor, configurationController);
    }

    public final void announceForAccessibility(CharSequence charSequence) {
        ((BouncerKeyguardMessageArea) this.mView).announceForAccessibility(charSequence);
    }

    public final void displayFailedAnimation() {
        BouncerKeyguardMessageArea bouncerKeyguardMessageArea = (BouncerKeyguardMessageArea) this.mView;
        if (bouncerKeyguardMessageArea.getScaleX() == 1.0f) {
            bouncerKeyguardMessageArea.startAnimation(AnimationUtils.loadAnimation(bouncerKeyguardMessageArea.getContext(), R.anim.giggle));
        }
    }

    public final void formatMessage(int i, Object... objArr) {
        BouncerKeyguardMessageArea bouncerKeyguardMessageArea = (BouncerKeyguardMessageArea) this.mView;
        bouncerKeyguardMessageArea.setMessage(i != 0 ? bouncerKeyguardMessageArea.getContext().getString(i, objArr) : null, false);
    }

    public final ViewGroup.LayoutParams getLayoutParams() {
        return ((BouncerKeyguardMessageArea) this.mView).getLayoutParams();
    }

    @Override // com.android.systemui.util.ViewController
    public final Resources getResources() {
        return ((BouncerKeyguardMessageArea) this.mView).getResources();
    }

    public final void scrollTo(int i, int i2) {
        ((BouncerKeyguardMessageArea) this.mView).scrollTo(0, 0);
    }

    public final void setContentDescription(CharSequence charSequence) {
        ((BouncerKeyguardMessageArea) this.mView).setContentDescription("");
    }

    public final void setFocusable(boolean z) {
        ((BouncerKeyguardMessageArea) this.mView).setFocusable(z);
    }

    public final void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        ((BouncerKeyguardMessageArea) this.mView).setLayoutParams(layoutParams);
    }

    public final void setMaxFontScale(float f) {
        ((BouncerKeyguardMessageArea) this.mView).setMaxFontScale(1.1f);
    }

    @Override // com.android.keyguard.KeyguardMessageAreaController
    public final void setMessage(CharSequence charSequence) {
        setMessage$1(charSequence, false);
    }

    public final void setMovementMethod(MovementMethod movementMethod) {
        ((BouncerKeyguardMessageArea) this.mView).setMovementMethod(movementMethod);
    }

    public final void setPadding(int i, int i2, int i3, int i4) {
        ((BouncerKeyguardMessageArea) this.mView).setPadding(0, i2, 0, 0);
    }

    public final void setTextSize(int i) {
        ((BouncerKeyguardMessageArea) this.mView).setTextSize(0, i);
    }

    public final void setTimeout(int i) {
        ((BouncerKeyguardMessageArea) this.mView).mTimeout = i;
    }

    public final void setVisibility(int i) {
        ((BouncerKeyguardMessageArea) this.mView).setVisibility(i);
    }
}
