package com.android.keyguard;

import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.MigrateClocksToBlueprint;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.Assert;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public final AnonymousClass1 mSetInvisibleEndAction = new Consumer() { // from class: com.android.keyguard.KeyguardVisibilityHelper.1
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            KeyguardVisibilityHelper keyguardVisibilityHelper = KeyguardVisibilityHelper.this;
            keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating = false;
            keyguardVisibilityHelper.mView.setVisibility(4);
            KeyguardVisibilityHelper.this.log("Callback Set Visibility to INVISIBLE");
        }
    };
    public final AnonymousClass2 mSetGoneEndAction = new Consumer() { // from class: com.android.keyguard.KeyguardVisibilityHelper.2
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            KeyguardVisibilityHelper keyguardVisibilityHelper = KeyguardVisibilityHelper.this;
            keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating = false;
            keyguardVisibilityHelper.mView.setVisibility(8);
            KeyguardVisibilityHelper.this.log("CallbackSet Visibility to GONE");
        }
    };
    public final KeyguardVisibilityHelper$$ExternalSyntheticLambda0 mSetVisibleEndRunnable = new KeyguardVisibilityHelper$$ExternalSyntheticLambda0(this);

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.keyguard.KeyguardVisibilityHelper$1] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.keyguard.KeyguardVisibilityHelper$2] */
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
            logBuffer.log("KeyguardVisibilityHelper", LogLevel.DEBUG, str, null);
        }
    }

    public final void setViewVisibility(int i, int i2, boolean z, boolean z2) {
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        Assert.isMainThread();
        View view = this.mView;
        AnimatableProperty.AnonymousClass7 anonymousClass7 = AnimatableProperty.ALPHA;
        PropertyAnimator.cancelAnimation(view, anonymousClass7);
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
            Flags.migrateClocksToBlueprint();
            PropertyAnimator.setProperty(this.mView, anonymousClass7, 0.0f, animationProperties, true);
        } else if (i2 == 2 && i == 1) {
            this.mView.setVisibility(0);
            this.mKeyguardViewVisibilityAnimating = true;
            this.mView.setAlpha(0.0f);
            View view2 = this.mView;
            AnimationProperties animationProperties2 = new AnimationProperties();
            animationProperties2.delay = 0L;
            animationProperties2.duration = 320L;
            animationProperties2.setCustomInterpolator(View.ALPHA, Interpolators.ALPHA_IN);
            animationProperties2.mAnimationEndAction = new Consumer() { // from class: com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    KeyguardVisibilityHelper.this.mSetVisibleEndRunnable.run();
                }
            };
            PropertyAnimator.setProperty(view2, anonymousClass7, 1.0f, animationProperties2, true);
            log("keyguardFadingAway transition w/ Y Aniamtion");
        } else if (i == 1) {
            if (i2 != 0) {
                log("statusBarState == KEYGUARD && oldStatusBarState != SHADE");
            } else {
                log("statusBarState == KEYGUARD && oldStatusBarState == SHADE");
            }
            if (!z || i2 == 0) {
                ScreenOffAnimationController screenOffAnimationController = this.mScreenOffAnimationController;
                if (screenOffAnimationController.shouldAnimateInKeyguard()) {
                    Flags.migrateClocksToBlueprint();
                    log("ScreenOff transition");
                    this.mKeyguardViewVisibilityAnimating = true;
                    screenOffAnimationController.animateInKeyguard$1(this.mView, this.mSetVisibleEndRunnable);
                } else {
                    log("Direct set Visibility to VISIBLE");
                    this.mView.setVisibility(0);
                }
            } else {
                this.mKeyguardViewVisibilityAnimating = true;
                AnimationProperties animationProperties3 = new AnimationProperties();
                animationProperties3.delay = 0L;
                animationProperties3.setCustomInterpolator(View.ALPHA, Interpolators.FAST_OUT_LINEAR_IN);
                animationProperties3.mAnimationEndAction = this.mSetInvisibleEndAction;
                if (this.mAnimateYPos) {
                    float y = this.mView.getY() - (this.mView.getHeight() * 0.05f);
                    long j = 125;
                    AnimationProperties animationProperties4 = this.mAnimationProperties;
                    animationProperties4.duration = j;
                    long j2 = 0;
                    animationProperties4.delay = j2;
                    View view3 = this.mView;
                    AnimatableProperty.AnonymousClass7 anonymousClass72 = AnimatableProperty.Y;
                    PropertyAnimator.cancelAnimation(view3, anonymousClass72);
                    PropertyAnimator.setProperty(this.mView, anonymousClass72, y, animationProperties4, true);
                    animationProperties3.duration = j;
                    animationProperties3.delay = j2;
                    log("keyguardFadingAway transition w/ Y Aniamtion");
                } else {
                    log("keyguardFadingAway transition w/o Y Animation");
                }
                PropertyAnimator.setProperty(this.mView, anonymousClass7, 0.0f, animationProperties3, true);
            }
        } else {
            Flags.migrateClocksToBlueprint();
            log("Direct set Visibility to GONE");
            this.mView.setVisibility(8);
            this.mView.setAlpha(1.0f);
        }
        this.mLastOccludedState = z3;
    }
}
