package com.google.android.systemui.lowlightclock;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.service.dreams.DreamService;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.dream.lowlight.LowLightTransitionCoordinator;
import com.android.systemui.R;
import com.android.systemui.lowlightclock.ChargingStatusProvider;
import com.android.systemui.lowlightclock.LowLightClockAnimationProvider;
import com.android.systemui.statusbar.CrossFadeHelper;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LowLightClockDreamService extends DreamService {
    public Animator mAnimationIn;
    public Animator mAnimationOut;
    public final LowLightClockAnimationProvider mAnimationProvider;
    public final ChargingStatusProvider mChargingStatusProvider;
    public TextView mChargingStatusTextView;
    public final LowLightTransitionCoordinator mLowLightTransitionCoordinator;
    public TextClock mTextClock;

    public LowLightClockDreamService(ChargingStatusProvider chargingStatusProvider, LowLightClockAnimationProvider lowLightClockAnimationProvider, LowLightTransitionCoordinator lowLightTransitionCoordinator, Optional<Provider> optional) {
        this.mAnimationProvider = lowLightClockAnimationProvider;
        if (optional.map(new LowLightClockDreamService$$ExternalSyntheticLambda1()).orElse(null) != null) {
            throw new ClassCastException();
        }
        this.mChargingStatusProvider = chargingStatusProvider;
        this.mLowLightTransitionCoordinator = lowLightTransitionCoordinator;
    }

    @Override // android.service.dreams.DreamService, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        setFullscreen(true);
        setContentView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.low_light_clock_dream, (ViewGroup) null));
        this.mTextClock = (TextClock) findViewById(R.id.low_light_text_clock);
        this.mChargingStatusTextView = (TextView) findViewById(R.id.charging_status_text_view);
        this.mChargingStatusProvider.startUsing(new LowLightClockDreamService$$ExternalSyntheticLambda0(this));
        this.mLowLightTransitionCoordinator.mLowLightExitListener = this;
    }

    @Override // android.service.dreams.DreamService, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Animator animator = this.mAnimationOut;
        if (animator != null) {
            animator.cancel();
        }
        ChargingStatusProvider chargingStatusProvider = this.mChargingStatusProvider;
        chargingStatusProvider.mCallback = null;
        ChargingStatusProvider.ChargingStatusCallback chargingStatusCallback = chargingStatusProvider.mChargingStatusCallback;
        if (chargingStatusCallback != null) {
            chargingStatusProvider.mKeyguardUpdateMonitor.removeCallback(chargingStatusCallback);
            chargingStatusProvider.mChargingStatusCallback = null;
        }
        this.mLowLightTransitionCoordinator.mLowLightExitListener = null;
    }

    @Override // android.service.dreams.DreamService
    public final void onDreamingStarted() {
        LowLightClockAnimationProvider lowLightClockAnimationProvider = this.mAnimationProvider;
        View[] viewArr = {this.mTextClock, this.mChargingStatusTextView};
        lowLightClockAnimationProvider.getClass();
        AnimatorSet animatorSet = new AnimatorSet();
        for (int i = 0; i < 2; i++) {
            View view = viewArr[i];
            if (view != null) {
                CrossFadeHelper.fadeOut(view, 0.0f, false);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 1.0f);
                ofFloat.setStartDelay(lowLightClockAnimationProvider.mAlphaAnimationInStartDelayMillis);
                ofFloat.setDuration(lowLightClockAnimationProvider.mAlphaAnimationDurationMillis);
                ofFloat.setInterpolator(Interpolators.LINEAR);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, lowLightClockAnimationProvider.mYTranslationAnimationInStartOffset, 0.0f);
                ofFloat2.setDuration(lowLightClockAnimationProvider.mYTranslationAnimationInDurationMillis);
                ofFloat2.setInterpolator(Interpolators.EMPHASIZED);
                animatorSet.playTogether(ofFloat2, ofFloat);
            }
        }
        this.mAnimationIn = animatorSet;
        animatorSet.start();
    }

    @Override // android.service.dreams.DreamService
    public final void onWakeUp() {
        Animator animator = this.mAnimationIn;
        if (animator != null) {
            animator.cancel();
        }
        Animator provideAnimationOut = this.mAnimationProvider.provideAnimationOut(this.mTextClock, this.mChargingStatusTextView);
        this.mAnimationOut = provideAnimationOut;
        provideAnimationOut.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.systemui.lowlightclock.LowLightClockDreamService.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                LowLightClockDreamService.super.onWakeUp();
            }
        });
        this.mAnimationOut.start();
    }

    @Override // android.service.dreams.DreamService
    public final void onDreamingStopped() {
    }
}
