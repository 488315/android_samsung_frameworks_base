package com.android.systemui.statusbar.events;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.icu.text.NumberFormat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.animation.Animator;
import androidx.core.animation.ValueAnimator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.SamsungBatteryMeterDrawable;
import com.android.systemui.battery.SamsungBatteryState;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.util.animation.AnimationUtil;
import java.util.ArrayList;
import java.util.Locale;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes3.dex */
public final class SamsungBatteryStatusChip extends FrameLayout implements BackgroundAnimatableView, DarkIconDispatcher.DarkReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final FrameLayout background;
    public final ConstraintLayout batteryChipContainer;
    public final LinearLayout batteryContent;
    public final Space batteryContentSpaceEnd;
    public final LottieAnimationView batteryLevelProgress;
    public final ImageView batteryLevelProgressBg;
    public final BatteryStatusChipClearTextView batteryLevelText;
    public final BatteryStatusChipClearImageView chargingIcon;
    public final LinearLayout chargingIconContainer;

    /* JADX WARN: Multi-variable type inference failed */
    public SamsungBatteryStatusChip(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public static SpringAnimation getSpringAnimation(Object obj, DynamicAnimation.ViewProperty viewProperty, float f, float f2, boolean z) {
        SpringForce springForce;
        SpringAnimation springAnimation = new SpringAnimation(obj, viewProperty);
        if (z) {
            springForce = new SpringForce();
            springForce.setDampingRatio(0.71f);
            springForce.setStiffness(200.0f);
        } else {
            springForce = new SpringForce();
            springForce.setDampingRatio(0.85f);
            springForce.setStiffness(300.0f);
        }
        springAnimation.setStartValue(z ? f : f2);
        if (z) {
            f = f2;
        }
        springForce.mFinalPosition = f;
        springAnimation.mSpring = springForce;
        return springAnimation;
    }

    public final SpringAnimatorSet getBatteryBackgroundAnimator(float f, Rect rect, boolean z, boolean z2) {
        int marginEnd;
        FrameLayout frameLayout = this.background;
        frameLayout.setPivotX(z2 ? 0.0f : frameLayout.getMeasuredWidth());
        frameLayout.setPivotY(frameLayout.getMeasuredHeight() / 2.0f);
        ConstraintLayout constraintLayout = this.batteryChipContainer;
        constraintLayout.setPivotX(z2 ? 0.0f : constraintLayout.getMeasuredWidth());
        constraintLayout.setPivotY(constraintLayout.getMeasuredHeight() / 2.0f);
        if (z2) {
            ViewGroup.LayoutParams layoutParams = this.batteryChipContainer.getLayoutParams();
            marginEnd = -(layoutParams instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams).getMarginEnd() : 0);
        } else {
            ViewGroup.LayoutParams layoutParams2 = this.batteryChipContainer.getLayoutParams();
            marginEnd = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams2).getMarginEnd() : 0;
        }
        SpringAnimation springAnimation = getSpringAnimation(this.batteryChipContainer, DynamicAnimation.TRANSLATION_X, marginEnd, 0.0f, z);
        SpringAnimation springAnimation2 = getSpringAnimation(this.background, DynamicAnimation.SCALE_X, (rect.width() / getContext().getResources().getDimension(R.dimen.status_bar_battery_chip_width)) / f, 1.0f, z);
        SpringAnimation springAnimation3 = getSpringAnimation(this.background, DynamicAnimation.SCALE_Y, (rect.height() / getContext().getResources().getDimension(R.dimen.status_bar_battery_chip_height)) / f, 1.0f, z);
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.setInterpolator(null);
        valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getBatteryBackgroundAnimator$alphaAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                this.this$0.batteryLevelProgress.setAlpha(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue());
            }
        });
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        if (z) {
            valueAnimatorOfFloat = null;
        }
        if (valueAnimatorOfFloat != null) {
            springAnimatorSet.playTogether(valueAnimatorOfFloat, springAnimation2, springAnimation3, springAnimation);
        }
        return springAnimatorSet;
    }

    public final SpringAnimatorSet getBatteryLevelTextAnimator(float f, Rect rect, boolean z, boolean z2) throws Resources.NotFoundException {
        Rect rect2;
        SpringAnimation springAnimation;
        SamsungBatteryMeterDrawable samsungBatteryMeterDrawable;
        BatteryStatusChipClearTextView batteryStatusChipClearTextView = this.batteryLevelText;
        batteryStatusChipClearTextView.setPivotX(batteryStatusChipClearTextView.getMeasuredWidth() / 2.0f);
        batteryStatusChipClearTextView.setPivotY(batteryStatusChipClearTextView.getMeasuredHeight() / 2.0f);
        BatteryMeterView batteryMeterView = getBatteryMeterView();
        if (batteryMeterView == null || (samsungBatteryMeterDrawable = batteryMeterView.mSamsungDrawable) == null || !samsungBatteryMeterDrawable.showPercentSetting) {
            rect2 = new Rect();
        } else {
            SamsungBatteryState samsungBatteryState = samsungBatteryMeterDrawable.batteryState;
            String strValueOf = String.valueOf(samsungBatteryState.isDirectPowerMode ? 100 : samsungBatteryState.level);
            Rect textBounds = samsungBatteryMeterDrawable.getTextBounds(strValueOf);
            float textOriginX = samsungBatteryMeterDrawable.getTextOriginX(textBounds);
            float fHeight = ((textBounds.height() / 2.0f) + (samsungBatteryMeterDrawable.intrinsicHeight / 2.0f)) - textBounds.bottom;
            Locale locale = samsungBatteryMeterDrawable.context.getResources().getConfiguration().locale;
            NumberFormat numberFormat = NumberFormat.getInstance(locale);
            if (!Intrinsics.areEqual(locale.toString(), "my_MM")) {
                strValueOf = numberFormat.format(Integer.valueOf(Integer.parseInt(strValueOf)));
                textBounds = samsungBatteryMeterDrawable.getTextBounds(strValueOf);
            }
            float fMeasureText = samsungBatteryMeterDrawable.textPaint.measureText(strValueOf) / 2;
            rect2 = new Rect((int) (textOriginX - fMeasureText), (int) (fHeight - (textBounds.height() / 2)), (int) (textOriginX + fMeasureText), (int) (fHeight + (textBounds.height() / 2)));
        }
        if (rect2.isEmpty()) {
            this.batteryLevelText.isClear = false;
            springAnimation = null;
        } else {
            this.batteryLevelText.isClear = true;
            float measuredWidth = (this.batteryLevelText.getMeasuredWidth() / 2.0f) + this.batteryContentSpaceEnd.getMeasuredWidth();
            float fWidth = z2 ? (rect2.left + rect2.right) / 2.0f : (rect.width() / f) - ((rect2.left + rect2.right) / 2.0f);
            springAnimation = getSpringAnimation(this.batteryLevelText, DynamicAnimation.TRANSLATION_X, z2 ? fWidth - measuredWidth : measuredWidth - fWidth, 0.0f, z);
        }
        float fHeight2 = ((rect.height() * 0.78f) / this.batteryLevelText.textPaint.getTextSize()) / f;
        float f2 = z ? fHeight2 : 1.0f;
        if (z) {
            fHeight2 = 1.0f;
        }
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f2, fHeight2);
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getBatteryLevelTextAnimator$scaleAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip samsungBatteryStatusChip = this.this$0;
                BatteryStatusChipClearTextView batteryStatusChipClearTextView2 = samsungBatteryStatusChip.batteryLevelText;
                ValueAnimator valueAnimator = valueAnimatorOfFloat;
                batteryStatusChipClearTextView2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.batteryLevelText.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        valueAnimatorOfFloat2.setDuration(300L);
        valueAnimatorOfFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getBatteryLevelTextAnimator$alphaAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                this.this$0.batteryLevelText.setAlpha(((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue());
            }
        });
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(valueAnimatorOfFloat2, valueAnimatorOfFloat);
        if (springAnimation != null) {
            springAnimatorSet.playTogether(springAnimation);
        }
        return springAnimatorSet;
    }

    public final BatteryMeterView getBatteryMeterView() {
        try {
            return (BatteryMeterView) getRootView().requireViewById(R.id.battery);
        } catch (Exception e) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("ERROR: ", e, "SamsungBatteryStatusChip");
            return null;
        }
    }

    public final SpringAnimatorSet getChargingIconAnimator(float f, Rect rect, boolean z, boolean z2) {
        SpringAnimation springAnimation;
        SamsungBatteryMeterDrawable samsungBatteryMeterDrawable;
        BatteryMeterView batteryMeterView;
        SamsungBatteryMeterDrawable samsungBatteryMeterDrawable2;
        LinearLayout linearLayout = this.chargingIconContainer;
        linearLayout.setPivotX(z2 ? 0.0f : linearLayout.getMeasuredWidth());
        linearLayout.setPivotY(linearLayout.getMeasuredHeight() / 2.0f);
        BatteryStatusChipClearImageView batteryStatusChipClearImageView = this.chargingIcon;
        batteryStatusChipClearImageView.setPivotX(z2 ? 0.0f : batteryStatusChipClearImageView.getMeasuredWidth());
        batteryStatusChipClearImageView.setPivotY(batteryStatusChipClearImageView.getMeasuredHeight() / 2.0f);
        this.chargingIcon.isClear = (z || (batteryMeterView = getBatteryMeterView()) == null || (samsungBatteryMeterDrawable2 = batteryMeterView.mSamsungDrawable) == null || !samsungBatteryMeterDrawable2.batteryState.shouldShowChargingIcon() || samsungBatteryMeterDrawable2.flagBlinkingNeeded) ? false : true;
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.setInterpolator(null);
        valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getChargingIconAnimator$alphaAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                this.this$0.chargingIcon.setAlpha(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue());
            }
        });
        BatteryMeterView batteryMeterView2 = getBatteryMeterView();
        Rect rect2 = (batteryMeterView2 == null || (samsungBatteryMeterDrawable = batteryMeterView2.mSamsungDrawable) == null) ? new Rect() : samsungBatteryMeterDrawable.getChargingIconBounds();
        if (z) {
            float measuredWidth = this.batteryLevelText.getMeasuredWidth() + this.batteryContentSpaceEnd.getMeasuredWidth();
            if (z2) {
                measuredWidth = -measuredWidth;
            }
            springAnimation = getSpringAnimation(this.chargingIcon, DynamicAnimation.TRANSLATION_X, measuredWidth, 1.0f, true);
        } else {
            int measuredWidth2 = this.batteryLevelText.getMeasuredWidth() + this.batteryContentSpaceEnd.getMeasuredWidth();
            float fWidth = z2 ? rect2.left : (rect.width() / f) - rect2.right;
            springAnimation = getSpringAnimation(this.chargingIconContainer, DynamicAnimation.TRANSLATION_X, 0.0f, z2 ? fWidth - measuredWidth2 : measuredWidth2 - fWidth, true);
        }
        SpringAnimation springAnimation2 = getSpringAnimation(this.chargingIconContainer, DynamicAnimation.SCALE_X, rect2.width() / this.chargingIcon.getMeasuredWidth(), 1.0f, z);
        SpringAnimation springAnimation3 = getSpringAnimation(this.chargingIconContainer, DynamicAnimation.SCALE_Y, rect2.height() / this.chargingIcon.getMeasuredHeight(), 1.0f, z);
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(valueAnimatorOfFloat, springAnimation, springAnimation2, springAnimation3);
        return springAnimatorSet;
    }

    public final SpringAnimatorSet getContainerBackgroundAnimator(boolean z, Rect rect, float f, boolean z2, boolean z3) {
        final ValueAnimator valueAnimatorOfFloat;
        final ValueAnimator valueAnimatorOfFloat2;
        FrameLayout frameLayout = this.background;
        frameLayout.setPivotX(z2 ? 0.0f : this.batteryChipContainer.getMeasuredWidth());
        frameLayout.setPivotY(this.batteryChipContainer.getMeasuredHeight() / 2.0f);
        SpringAnimation springAnimation = getSpringAnimation(this.background, DynamicAnimation.SCALE_X, (rect.width() / this.batteryChipContainer.getMeasuredWidth()) / f, 0.97f, z);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip.getContainerBackgroundAnimator.2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f2, float f3) {
                SamsungBatteryStatusChip.this.invalidateOutline();
            }
        });
        SpringAnimation springAnimation2 = getSpringAnimation(this.background, DynamicAnimation.SCALE_Y, (rect.height() / this.batteryChipContainer.getMeasuredHeight()) / f, 1.0f, z);
        springAnimation2.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$scaleYAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f2, float f3) {
                this.this$0.invalidateOutline();
            }
        });
        if (!z3) {
            if (z) {
                valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                valueAnimatorOfFloat.setDuration(AnimationUtil.Companion.getFrames(1));
                valueAnimatorOfFloat.setInterpolator(null);
                valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$alpha$3$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        this.this$0.setAlpha(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue());
                    }
                });
            } else {
                valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                valueAnimatorOfFloat.setDuration(AnimationUtil.Companion.getFrames(1));
                valueAnimatorOfFloat.setStartDelay(250L);
                valueAnimatorOfFloat.setInterpolator(null);
                valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$alpha$4$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        this.this$0.setAlpha(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue());
                    }
                });
            }
            valueAnimatorOfFloat2 = valueAnimatorOfFloat;
        } else if (z) {
            valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat2.setDuration(50L);
            valueAnimatorOfFloat2.setInterpolator(null);
            valueAnimatorOfFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$alpha$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    this.this$0.setAlpha(((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue());
                }
            });
        } else {
            valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
            valueAnimatorOfFloat2.setDuration(200L);
            valueAnimatorOfFloat2.setInterpolator(null);
            valueAnimatorOfFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$alpha$2$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    this.this$0.setAlpha(((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue());
                }
            });
        }
        final ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat3.setStartDelay(300L);
        valueAnimatorOfFloat3.setDuration(150L);
        valueAnimatorOfFloat3.setInterpolator(null);
        valueAnimatorOfFloat3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$batteryChipAlpha$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                this.this$0.background.setAlpha(((Float) valueAnimatorOfFloat3.getAnimatedValue()).floatValue());
            }
        });
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(springAnimation, springAnimation2, valueAnimatorOfFloat2);
        if (!z && !z3) {
            springAnimatorSet.playTogether(valueAnimatorOfFloat3);
        }
        return springAnimatorSet;
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final View getContentView() {
        return this.batteryContent;
    }

    public final SpringAnimatorSet getSystemIconAnimator(final View view, final boolean z, boolean z2) throws Resources.NotFoundException {
        if (view == null) {
            return new SpringAnimatorSet();
        }
        Pair pair = z ? new Pair(Float.valueOf(1.0f), Float.valueOf(0.0f)) : new Pair(Float.valueOf(0.0f), Float.valueOf(1.0f));
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(((Number) pair.component1()).floatValue(), ((Number) pair.component2()).floatValue());
        valueAnimatorOfFloat.setDuration(z ? 67L : 270L);
        valueAnimatorOfFloat.setInterpolator(null);
        valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getSystemIconAnimator$alphaAnimator$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                boolean z3 = z;
                SamsungBatteryStatusChip samsungBatteryStatusChip = this;
                ValueAnimator valueAnimator = valueAnimatorOfFloat;
                if (z3) {
                    int i = SamsungBatteryStatusChip.$r8$clinit;
                    BatteryMeterView batteryMeterView = samsungBatteryStatusChip.getBatteryMeterView();
                    if (batteryMeterView != null) {
                        batteryMeterView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        return;
                    }
                    return;
                }
                int i2 = SamsungBatteryStatusChip.$r8$clinit;
                BatteryMeterView batteryMeterView2 = samsungBatteryStatusChip.getBatteryMeterView();
                if (batteryMeterView2 != null) {
                    batteryMeterView2.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue() > 0.95f ? ((Float) valueAnimator.getAnimatedValue()).floatValue() : 0.0f);
                }
            }
        });
        final float dimension = getContext().getResources().getDimension(R.dimen.status_bar_battery_chip_system_icon_translate);
        if (z != z2) {
            dimension = -dimension;
        }
        final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        AnimationUtil.Companion companion = AnimationUtil.Companion;
        valueAnimatorOfFloat2.setDuration(companion.getFrames(11));
        valueAnimatorOfFloat2.setStartDelay(z ? companion.getFrames(1) : companion.getFrames(11));
        valueAnimatorOfFloat2.setInterpolator(null);
        valueAnimatorOfFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getSystemIconAnimator$translationXAnimator$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                view.setTranslationX(((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue() * dimension);
            }
        });
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2);
        return springAnimatorSet;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((DarkIconDispatcher) Dependency.sDependency.getDependencyInner(DarkIconDispatcher.class)).addDarkReceiver(this);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        BatteryMeterView batteryMeterView = getBatteryMeterView();
        float f2 = 0.0f;
        if (batteryMeterView != null) {
            if (!DarkIconDispatcher.isInAreas(arrayList, batteryMeterView)) {
                f = 0.0f;
            }
            f2 = f;
        }
        this.background.setBackgroundResource(f2 >= 0.5f ? R.drawable.samsung_battery_chip_container_bg_light : R.drawable.samsung_battery_chip_container_bg_dark);
        int color = getContext().getColor(f2 >= 0.5f ? R.color.status_bar_battery_chip_text_color_light : R.color.status_bar_battery_chip_text_color_dark);
        BatteryStatusChipClearImageView batteryStatusChipClearImageView = this.chargingIcon;
        batteryStatusChipClearImageView.paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        batteryStatusChipClearImageView.invalidate();
        BatteryStatusChipClearTextView batteryStatusChipClearTextView = this.batteryLevelText;
        batteryStatusChipClearTextView.textPaint.setColor(color);
        batteryStatusChipClearTextView.invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((DarkIconDispatcher) Dependency.sDependency.getDependencyInner(DarkIconDispatcher.class)).removeDarkReceiver(this);
    }

    public final void playProgressLottieAnimation(boolean z) {
        if (!z) {
            this.batteryLevelProgress.setRepeatCount(0);
            this.batteryLevelProgress.cancelAnimation();
            return;
        }
        this.batteryLevelProgress.setRepeatCount(-1);
        this.batteryLevelProgress.setScaleType(ImageView.ScaleType.FIT_XY);
        LottieAnimationView lottieAnimationView = this.batteryLevelProgress;
        BatteryChipConstants.INSTANCE.getClass();
        lottieAnimationView.addValueCallback(BatteryChipConstants.WAVE_KEY_PATH, (KeyPath) LottieProperty.BLUR_RADIUS, (SimpleLottieValueCallback) new SimpleLottieValueCallback() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip.playProgressLottieAnimation.1
            @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
            public final /* bridge */ /* synthetic */ Object getValue() {
                return Float.valueOf(10.0f);
            }
        });
        this.batteryLevelProgress.playAnimation();
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final void setBoundsForAnimation(int i, int i2, int i3, int i4) {
        int measuredWidth = isLayoutRtl() ? i : i3 - getMeasuredWidth();
        float f = (i2 + i4) / 2.0f;
        int iRoundToInt = MathKt__MathJVMKt.roundToInt(f - (getMeasuredHeight() / 2.0f));
        if (isLayoutRtl()) {
            i3 = getMeasuredWidth() + i;
        }
        setLeftTopRightBottom(measuredWidth, iRoundToInt, i3, MathKt__MathJVMKt.roundToInt((getMeasuredHeight() / 2.0f) + f));
    }

    public /* synthetic */ SamsungBatteryStatusChip(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public SamsungBatteryStatusChip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        FrameLayout.inflate(context, R.layout.samsung_battery_status_chip, this);
        this.batteryChipContainer = (ConstraintLayout) requireViewById(R.id.battery_chip_container);
        this.background = (FrameLayout) requireViewById(R.id.battery_chip_background);
        this.batteryContent = (LinearLayout) requireViewById(R.id.battery_chip_content);
        this.chargingIcon = (BatteryStatusChipClearImageView) requireViewById(R.id.charging_icon);
        this.chargingIconContainer = (LinearLayout) requireViewById(R.id.charging_icon_container);
        this.batteryLevelProgress = (LottieAnimationView) requireViewById(R.id.battery_level_progress);
        this.batteryLevelText = (BatteryStatusChipClearTextView) requireViewById(R.id.battery_level_text);
        this.batteryLevelProgressBg = (ImageView) requireViewById(R.id.battery_level_progress_background);
        this.batteryContentSpaceEnd = (Space) requireViewById(R.id.battery_chip_content_space_end);
        setClipToOutline(true);
        setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip.1
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                float scaleX = SamsungBatteryStatusChip.this.background.getScaleX() * SamsungBatteryStatusChip.this.batteryChipContainer.getMeasuredWidth();
                float scaleY = SamsungBatteryStatusChip.this.background.getScaleY() * SamsungBatteryStatusChip.this.batteryChipContainer.getMeasuredHeight();
                if (outline != null) {
                    outline.setRoundRect(SamsungBatteryStatusChip.this.isLayoutRtl() ? 0 : SamsungBatteryStatusChip.this.batteryChipContainer.getMeasuredWidth() - MathKt__MathJVMKt.roundToInt(scaleX), MathKt__MathJVMKt.roundToInt((SamsungBatteryStatusChip.this.batteryChipContainer.getMeasuredHeight() - scaleY) / 2.0f), SamsungBatteryStatusChip.this.isLayoutRtl() ? MathKt__MathJVMKt.roundToInt(scaleX) : SamsungBatteryStatusChip.this.batteryChipContainer.getMeasuredWidth(), MathKt__MathJVMKt.roundToInt((SamsungBatteryStatusChip.this.batteryChipContainer.getMeasuredHeight() + scaleY) / 2.0f), scaleY / 2.0f);
                }
            }
        });
    }
}
