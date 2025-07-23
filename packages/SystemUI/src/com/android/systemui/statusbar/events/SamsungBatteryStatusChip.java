package com.android.systemui.statusbar.events;

import android.content.Context;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SamsungBatteryStatusChip extends FrameLayout implements BackgroundAnimatableView, DarkIconDispatcher.DarkReceiver {
    public final FrameLayout background;
    public final ConstraintLayout batteryChipContainer;
    public final LinearLayout batteryContent;
    public final Space batteryContentSpaceEnd;
    public final LottieAnimationView batteryLevelProgress;
    public final ImageView batteryLevelProgressBg;
    public final BatteryStatusChipClearTextView batteryLevelText;
    public final BatteryStatusChipClearImageView chargingIcon;
    public final LinearLayout chargingIconContainer;

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

    public static SpringAnimatorSet getSystemIconAnimator(final View view, boolean z) {
        Float valueOf = Float.valueOf(3.0f);
        Float valueOf2 = Float.valueOf(0.0f);
        Pair pair = z ? new Pair(valueOf2, valueOf) : new Pair(valueOf, valueOf2);
        float floatValue = ((Number) pair.component1()).floatValue();
        float floatValue2 = ((Number) pair.component2()).floatValue();
        Float valueOf3 = Float.valueOf(1.0f);
        Pair pair2 = z ? new Pair(valueOf3, valueOf2) : new Pair(valueOf2, valueOf3);
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(((Number) pair2.component1()).floatValue(), ((Number) pair2.component2()).floatValue());
        ofFloat.setDuration(300L);
        ofFloat.setInterpolator(null);
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getSystemIconAnimator$alphaAnimator$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                view.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
            }
        });
        SpringAnimation springAnimation = getSpringAnimation(view, DynamicAnimation.TRANSLATION_X, floatValue, floatValue2, z);
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(ofFloat, springAnimation);
        return springAnimatorSet;
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
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        ofFloat.setDuration(300L);
        ofFloat.setInterpolator(null);
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getBatteryBackgroundAnimator$alphaAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip.this.batteryLevelProgress.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
            }
        });
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(ofFloat, springAnimation2, springAnimation3, springAnimation);
        return springAnimatorSet;
    }

    public final SpringAnimatorSet getBatteryLevelTextAnimator(float f, Rect rect, boolean z, boolean z2) {
        Rect rect2;
        SpringAnimation springAnimation;
        BatteryStatusChipClearTextView batteryStatusChipClearTextView = this.batteryLevelText;
        batteryStatusChipClearTextView.setPivotX(batteryStatusChipClearTextView.getMeasuredWidth() / 2.0f);
        batteryStatusChipClearTextView.setPivotY(batteryStatusChipClearTextView.getMeasuredHeight() / 2.0f);
        BatteryMeterView batteryMeterView = getBatteryMeterView();
        if (batteryMeterView != null) {
            SamsungBatteryMeterDrawable samsungBatteryMeterDrawable = batteryMeterView.mSamsungDrawable;
            if (samsungBatteryMeterDrawable == null) {
                rect2 = new Rect();
            } else if (samsungBatteryMeterDrawable.showPercentSetting) {
                SamsungBatteryState samsungBatteryState = samsungBatteryMeterDrawable.batteryState;
                String valueOf = String.valueOf(samsungBatteryState.isDirectPowerMode ? 100 : samsungBatteryState.level);
                Rect textBounds = samsungBatteryMeterDrawable.getTextBounds(valueOf);
                float textOriginX = samsungBatteryMeterDrawable.getTextOriginX(textBounds);
                float height = ((textBounds.height() / 2.0f) + (samsungBatteryMeterDrawable.intrinsicHeight / 2.0f)) - textBounds.bottom;
                Locale locale = samsungBatteryMeterDrawable.context.getResources().getConfiguration().locale;
                NumberFormat numberFormat = NumberFormat.getInstance(locale);
                if (!Intrinsics.areEqual(locale.toString(), "my_MM")) {
                    valueOf = numberFormat.format(Integer.valueOf(Integer.parseInt(valueOf)));
                    textBounds = samsungBatteryMeterDrawable.getTextBounds(valueOf);
                }
                float measureText = samsungBatteryMeterDrawable.textPaint.measureText(valueOf) / 2;
                rect2 = new Rect((int) (textOriginX - measureText), (int) (height - (textBounds.height() / 2)), (int) (textOriginX + measureText), (int) (height + (textBounds.height() / 2)));
            } else {
                rect2 = new Rect();
            }
        } else {
            rect2 = new Rect();
        }
        if (rect2.isEmpty()) {
            this.batteryLevelText.isClear = false;
            springAnimation = null;
        } else {
            this.batteryLevelText.isClear = true;
            float measuredWidth = (this.batteryLevelText.getMeasuredWidth() / 2.0f) + this.batteryContentSpaceEnd.getMeasuredWidth();
            float width = z2 ? (rect2.left + rect2.right) / 2.0f : (rect.width() / f) - ((rect2.left + rect2.right) / 2.0f);
            springAnimation = getSpringAnimation(this.batteryLevelText, DynamicAnimation.TRANSLATION_X, z2 ? width - measuredWidth : measuredWidth - width, 0.0f, z);
        }
        float height2 = ((rect.height() * 0.78f) / this.batteryLevelText.textPaint.getTextSize()) / f;
        float f2 = z ? height2 : 1.0f;
        if (z) {
            height2 = 1.0f;
        }
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(f2, height2);
        ofFloat.setDuration(300L);
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getBatteryLevelTextAnimator$scaleAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip samsungBatteryStatusChip = SamsungBatteryStatusChip.this;
                BatteryStatusChipClearTextView batteryStatusChipClearTextView2 = samsungBatteryStatusChip.batteryLevelText;
                ValueAnimator valueAnimator = ofFloat;
                batteryStatusChipClearTextView2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.batteryLevelText.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        ofFloat2.setDuration(300L);
        ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getBatteryLevelTextAnimator$alphaAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip.this.batteryLevelText.setAlpha(((Float) ofFloat2.getAnimatedValue()).floatValue());
            }
        });
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(ofFloat2, ofFloat);
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
        Rect rect2;
        SpringAnimation springAnimation;
        BatteryMeterView batteryMeterView;
        SamsungBatteryMeterDrawable samsungBatteryMeterDrawable;
        LinearLayout linearLayout = this.chargingIconContainer;
        linearLayout.setPivotX(z2 ? 0.0f : linearLayout.getMeasuredWidth());
        linearLayout.setPivotY(linearLayout.getMeasuredHeight() / 2.0f);
        BatteryStatusChipClearImageView batteryStatusChipClearImageView = this.chargingIcon;
        batteryStatusChipClearImageView.setPivotX(z2 ? 0.0f : batteryStatusChipClearImageView.getMeasuredWidth());
        batteryStatusChipClearImageView.setPivotY(batteryStatusChipClearImageView.getMeasuredHeight() / 2.0f);
        this.chargingIcon.isClear = (z || (batteryMeterView = getBatteryMeterView()) == null || (samsungBatteryMeterDrawable = batteryMeterView.mSamsungDrawable) == null || !samsungBatteryMeterDrawable.batteryState.shouldShowChargingIcon() || samsungBatteryMeterDrawable.flagBlinkingNeeded) ? false : true;
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        ofFloat.setDuration(300L);
        ofFloat.setInterpolator(null);
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getChargingIconAnimator$alphaAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip.this.chargingIcon.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
            }
        });
        BatteryMeterView batteryMeterView2 = getBatteryMeterView();
        if (batteryMeterView2 != null) {
            SamsungBatteryMeterDrawable samsungBatteryMeterDrawable2 = batteryMeterView2.mSamsungDrawable;
            rect2 = samsungBatteryMeterDrawable2 != null ? samsungBatteryMeterDrawable2.getChargingIconBounds() : new Rect();
        } else {
            rect2 = new Rect();
        }
        if (z) {
            float measuredWidth = this.batteryLevelText.getMeasuredWidth() + this.batteryContentSpaceEnd.getMeasuredWidth();
            if (z2) {
                measuredWidth = -measuredWidth;
            }
            springAnimation = getSpringAnimation(this.chargingIcon, DynamicAnimation.TRANSLATION_X, measuredWidth, 1.0f, true);
        } else {
            int measuredWidth2 = this.batteryLevelText.getMeasuredWidth() + this.batteryContentSpaceEnd.getMeasuredWidth();
            float width = z2 ? rect2.left : (rect.width() / f) - rect2.right;
            springAnimation = getSpringAnimation(this.chargingIconContainer, DynamicAnimation.TRANSLATION_X, 0.0f, z2 ? width - measuredWidth2 : measuredWidth2 - width, true);
        }
        SpringAnimation springAnimation2 = getSpringAnimation(this.chargingIconContainer, DynamicAnimation.SCALE_X, rect2.width() / this.chargingIcon.getMeasuredWidth(), 1.0f, z);
        SpringAnimation springAnimation3 = getSpringAnimation(this.chargingIconContainer, DynamicAnimation.SCALE_Y, rect2.height() / this.chargingIcon.getMeasuredHeight(), 1.0f, z);
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(ofFloat, springAnimation, springAnimation2, springAnimation3);
        return springAnimatorSet;
    }

    public final SpringAnimatorSet getContainerBackgroundAnimator(boolean z, Rect rect, float f, boolean z2, boolean z3) {
        final ValueAnimator ofFloat;
        final ValueAnimator valueAnimator;
        FrameLayout frameLayout = this.background;
        frameLayout.setPivotX(z2 ? 0.0f : this.batteryChipContainer.getMeasuredWidth());
        frameLayout.setPivotY(this.batteryChipContainer.getMeasuredHeight() / 2.0f);
        SpringAnimation springAnimation = getSpringAnimation(this.background, DynamicAnimation.SCALE_X, (rect.width() / this.batteryChipContainer.getMeasuredWidth()) / f, 0.97f, z);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f2, float f3) {
                SamsungBatteryStatusChip.this.invalidateOutline();
            }
        });
        SpringAnimation springAnimation2 = getSpringAnimation(this.background, DynamicAnimation.SCALE_Y, (rect.height() / this.batteryChipContainer.getMeasuredHeight()) / f, 1.0f, z);
        springAnimation2.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$scaleYAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f2, float f3) {
                SamsungBatteryStatusChip.this.invalidateOutline();
            }
        });
        if (!z3) {
            if (z) {
                ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(AnimationUtil.Companion.getFrames(1));
                ofFloat.setInterpolator(null);
                ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$alpha$3$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SamsungBatteryStatusChip.this.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
                    }
                });
            } else {
                ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                ofFloat.setDuration(AnimationUtil.Companion.getFrames(1));
                ofFloat.setStartDelay(250L);
                ofFloat.setInterpolator(null);
                ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$alpha$4$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SamsungBatteryStatusChip.this.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
                    }
                });
            }
            valueAnimator = ofFloat;
        } else if (z) {
            valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.setDuration(50L);
            valueAnimator.setInterpolator(null);
            valueAnimator.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$alpha$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    SamsungBatteryStatusChip.this.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
        } else {
            valueAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
            valueAnimator.setDuration(200L);
            valueAnimator.setInterpolator(null);
            valueAnimator.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$alpha$2$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    SamsungBatteryStatusChip.this.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
        }
        final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat2.setStartDelay(300L);
        ofFloat2.setDuration(150L);
        ofFloat2.setInterpolator(null);
        ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContainerBackgroundAnimator$batteryChipAlpha$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip.this.background.setAlpha(((Float) ofFloat2.getAnimatedValue()).floatValue());
            }
        });
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(springAnimation, springAnimation2, valueAnimator);
        if (!z && !z3) {
            springAnimatorSet.playTogether(ofFloat2);
        }
        return springAnimatorSet;
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final View getContentView() {
        return this.batteryContent;
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
        lottieAnimationView.addValueCallback(BatteryChipConstants.WAVE_KEY_PATH, (KeyPath) LottieProperty.BLUR_RADIUS, (SimpleLottieValueCallback) new SimpleLottieValueCallback() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$playProgressLottieAnimation$1
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
        int roundToInt = MathKt__MathJVMKt.roundToInt(f - (getMeasuredHeight() / 2.0f));
        if (isLayoutRtl()) {
            i3 = getMeasuredWidth() + i;
        }
        setLeftTopRightBottom(measuredWidth, roundToInt, i3, MathKt__MathJVMKt.roundToInt((getMeasuredHeight() / 2.0f) + f));
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
