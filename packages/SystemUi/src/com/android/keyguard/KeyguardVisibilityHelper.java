package com.android.keyguard;

import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardVisibilityHelper {
    public final boolean mAnimateYPos;
    public final KeyguardStateController mKeyguardStateController;
    public boolean mKeyguardViewVisibilityAnimating;
    public final LogBuffer mLogBuffer;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final View mView;
    public DcmMascotViewContainer mMascotViewContainer = null;
    public boolean mLastOccludedState = false;
    public final AnimationProperties mAnimationProperties = new AnimationProperties();
    public final C08181 mSetInvisibleEndAction = new Consumer() { // from class: com.android.keyguard.KeyguardVisibilityHelper.1
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            KeyguardVisibilityHelper keyguardVisibilityHelper = KeyguardVisibilityHelper.this;
            keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating = false;
            keyguardVisibilityHelper.mView.setVisibility(4);
            KeyguardVisibilityHelper.this.log("Callback Set Visibility to INVISIBLE");
        }
    };
    public final C08192 mSetGoneEndAction = new Consumer() { // from class: com.android.keyguard.KeyguardVisibilityHelper.2
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            KeyguardVisibilityHelper keyguardVisibilityHelper = KeyguardVisibilityHelper.this;
            keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating = false;
            keyguardVisibilityHelper.mView.setVisibility(8);
            KeyguardVisibilityHelper.this.log("CallbackSet Visibility to GONE");
        }
    };
    public final KeyguardVisibilityHelper$$ExternalSyntheticLambda0 mAnimateKeyguardStatusViewInvisibleEndRunnable = new KeyguardVisibilityHelper$$ExternalSyntheticLambda0(this, 0);
    public final KeyguardVisibilityHelper$$ExternalSyntheticLambda0 mAnimateKeyguardStatusViewGoneEndRunnable = new KeyguardVisibilityHelper$$ExternalSyntheticLambda0(this, 1);
    public final KeyguardVisibilityHelper$$ExternalSyntheticLambda0 mSetVisibleEndRunnable = new KeyguardVisibilityHelper$$ExternalSyntheticLambda0(this, 2);

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.keyguard.KeyguardVisibilityHelper$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.keyguard.KeyguardVisibilityHelper$2] */
    public KeyguardVisibilityHelper(View view, KeyguardStateController keyguardStateController, DozeParameters dozeParameters, ScreenOffAnimationController screenOffAnimationController, boolean z, LogBuffer logBuffer) {
        this.mView = view;
        this.mKeyguardStateController = keyguardStateController;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mAnimateYPos = z;
        this.mLogBuffer = logBuffer;
    }

    public final void log(String str) {
        LogBuffer logBuffer = this.mLogBuffer;
        if (logBuffer != null) {
            logBuffer.log("KeyguardVisibilityHelper", LogLevel.DEBUG, str);
        }
    }

    public final void setViewVisibility(int i, int i2, boolean z, boolean z2) {
        AnimatableProperty.C27067 c27067 = AnimatableProperty.ALPHA;
        View view = this.mView;
        PropertyAnimator.cancelAnimation(view, c27067);
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
        boolean z3 = keyguardStateControllerImpl.mOccluded;
        this.mKeyguardViewVisibilityAnimating = false;
        if ((!z && i2 == 1 && i != 1) || z2) {
            this.mKeyguardViewVisibilityAnimating = true;
            AnimationProperties animationProperties = new AnimationProperties();
            animationProperties.setCustomInterpolator(View.ALPHA, Interpolators.ALPHA_OUT);
            animationProperties.mAnimationEndAction = this.mSetGoneEndAction;
            if (z) {
                animationProperties.delay = keyguardStateControllerImpl.mKeyguardFadingAwayDelay;
                keyguardStateController.getClass();
                animationProperties.duration = ((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAwayDuration / 2;
                log("goingToFullShade && keyguardFadingAway");
            } else {
                animationProperties.delay = 0L;
                animationProperties.duration = 160L;
                log("goingToFullShade && !keyguardFadingAway");
            }
            PropertyAnimator.setProperty(view, c27067, 0.0f, animationProperties, true);
        } else if (i2 == 2 && i == 1) {
            view.setVisibility(0);
            this.mKeyguardViewVisibilityAnimating = true;
            view.setAlpha(0.0f);
            AnimationProperties animationProperties2 = new AnimationProperties();
            animationProperties2.delay = 0L;
            animationProperties2.duration = 320L;
            animationProperties2.setCustomInterpolator(View.ALPHA, Interpolators.ALPHA_IN);
            animationProperties2.mAnimationEndAction = new Consumer() { // from class: com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    KeyguardVisibilityHelper.this.mSetVisibleEndRunnable.run();
                }
            };
            PropertyAnimator.setProperty(view, c27067, 1.0f, animationProperties2, true);
            log("keyguardFadingAway transition w/ Y Aniamtion");
        } else if (i != 1) {
            log("Direct set Visibility to GONE");
            view.setVisibility(8);
            view.setAlpha(1.0f);
        } else if (z) {
            this.mKeyguardViewVisibilityAnimating = true;
            AnimationProperties animationProperties3 = new AnimationProperties();
            animationProperties3.delay = 0L;
            animationProperties3.setCustomInterpolator(View.ALPHA, Interpolators.FAST_OUT_LINEAR_IN);
            animationProperties3.mAnimationEndAction = this.mSetInvisibleEndAction;
            if (this.mAnimateYPos) {
                float y = view.getY() - (view.getHeight() * 0.05f);
                AnimationProperties animationProperties4 = this.mAnimationProperties;
                long j = 125;
                animationProperties4.duration = j;
                long j2 = 0;
                animationProperties4.delay = j2;
                AnimatableProperty.C27067 c270672 = AnimatableProperty.f351Y;
                PropertyAnimator.cancelAnimation(view, c270672);
                PropertyAnimator.setProperty(view, c270672, y, animationProperties4, true);
                animationProperties3.duration = j;
                animationProperties3.delay = j2;
                log("keyguardFadingAway transition w/ Y Aniamtion");
            } else {
                log("keyguardFadingAway transition w/o Y Animation");
            }
            PropertyAnimator.setProperty(view, c27067, 0.0f, animationProperties3, true);
        } else {
            ScreenOffAnimationController screenOffAnimationController = this.mScreenOffAnimationController;
            if (screenOffAnimationController.shouldAnimateInKeyguard()) {
                log("ScreenOff transition");
                this.mKeyguardViewVisibilityAnimating = true;
                screenOffAnimationController.animateInKeyguard$1(view, this.mSetVisibleEndRunnable);
            } else {
                log("Direct set Visibility to VISIBLE");
                view.setVisibility(0);
            }
        }
        this.mLastOccludedState = z3;
    }
}
