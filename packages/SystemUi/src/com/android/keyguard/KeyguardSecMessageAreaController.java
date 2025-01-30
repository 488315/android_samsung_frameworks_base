package com.android.keyguard;

import android.text.method.MovementMethod;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    public final void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        ((BouncerKeyguardMessageArea) this.mView).setLayoutParams(layoutParams);
    }

    @Override // com.android.keyguard.KeyguardMessageAreaController
    public final void setMessage(CharSequence charSequence) {
        setMessage(charSequence, false);
    }

    public final void setMovementMethod(MovementMethod movementMethod) {
        ((BouncerKeyguardMessageArea) this.mView).setMovementMethod(movementMethod);
    }

    public final void setVisibility(int i) {
        ((BouncerKeyguardMessageArea) this.mView).setVisibility(i);
    }
}
