package com.android.systemui.statusbar.events;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.ValueAnimator;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.DarkIconDispatcher;
import java.util.ArrayList;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SamsungBatteryStatusChip extends FrameLayout implements BackgroundAnimatableView, DarkIconDispatcher.DarkReceiver {
    public final FrameLayout background;
    public final TextView batteryChargingTextView;
    public final ConstraintLayout batteryChipContainer;
    public final LinearLayout batteryCombinedChip;
    public final LinearLayout batteryContent;
    public final LottieAnimationView batteryLevelProgress;
    public final ImageView batteryLevelProgressBg;
    public final BatteryStatusChipClearTextView batteryLevelText;
    public final ImageView chargingIcon;
    public final Locale locale;
    public final TextView percentSign;
    public final TextView percentSignRtl;

    public SamsungBatteryStatusChip(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public static Rect getScaledBounds(Rect rect, float f) {
        return new Rect(MathKt__MathJVMKt.roundToInt(rect.right - (rect.width() * f)), MathKt__MathJVMKt.roundToInt(rect.centerY() - ((rect.height() * f) / 2.0f)), rect.right, MathKt__MathJVMKt.roundToInt(((rect.height() * f) / 2.0f) + rect.centerY()));
    }

    public final AnimatorSet getBackgroundAnimator(boolean z) {
        FrameLayout frameLayout = this.background;
        frameLayout.setPivotX(frameLayout.getMeasuredWidth());
        frameLayout.setPivotY(frameLayout.getMeasuredHeight() / 2.0f);
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 1.0f);
        ofFloat.setDuration(300L);
        ofFloat.setInterpolator(null);
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getBackgroundAnimator$backgroundScaleX$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip samsungBatteryStatusChip = SamsungBatteryStatusChip.this;
                FrameLayout frameLayout2 = samsungBatteryStatusChip.background;
                ValueAnimator valueAnimator = ofFloat;
                frameLayout2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.batteryCombinedChip.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 1.0f);
        ofFloat2.setDuration(300L);
        ofFloat2.setInterpolator(null);
        ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getBackgroundAnimator$backgroundScaleY$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip samsungBatteryStatusChip = SamsungBatteryStatusChip.this;
                FrameLayout frameLayout2 = samsungBatteryStatusChip.background;
                ValueAnimator valueAnimator = ofFloat2;
                frameLayout2.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.batteryCombinedChip.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        final ValueAnimator ofFloat3 = ValueAnimator.ofFloat(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        ofFloat3.setDuration(300L);
        ofFloat3.setInterpolator(null);
        ofFloat3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getBackgroundAnimator$alpha$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip.this.batteryLevelProgress.setAlpha(((Float) ofFloat3.getAnimatedValue()).floatValue());
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        return animatorSet;
    }

    public final AnimatorSet getContentAnimator(boolean z, Rect rect, Rect rect2, float f) {
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        ofFloat.setDuration(300L);
        ofFloat.setInterpolator(null);
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContentAnimator$alpha$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip samsungBatteryStatusChip = SamsungBatteryStatusChip.this;
                BatteryStatusChipClearTextView batteryStatusChipClearTextView = samsungBatteryStatusChip.batteryLevelText;
                ValueAnimator valueAnimator = ofFloat;
                batteryStatusChipClearTextView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.chargingIcon.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.percentSign.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.batteryChargingTextView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 1.0f);
        ofFloat2.setDuration(300L);
        ofFloat2.setInterpolator(null);
        ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContentAnimator$contentScaleXY$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip samsungBatteryStatusChip = SamsungBatteryStatusChip.this;
                BatteryStatusChipClearTextView batteryStatusChipClearTextView = samsungBatteryStatusChip.batteryLevelText;
                ValueAnimator valueAnimator = ofFloat2;
                batteryStatusChipClearTextView.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.batteryLevelText.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.chargingIcon.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.chargingIcon.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.percentSign.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.percentSign.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.batteryChargingTextView.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.batteryChargingTextView.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        float centerX = rect.centerX() - getScaledBounds(new Rect(this.chargingIcon.getMeasuredWidth() + (rect2.centerX() - (this.batteryContent.getMeasuredWidth() / 2)), rect2.centerY() - (this.batteryLevelText.getMeasuredHeight() / 2), this.batteryLevelText.getMeasuredWidth() + (this.chargingIcon.getMeasuredWidth() + (rect2.centerX() - (this.batteryContent.getMeasuredWidth() / 2))), (this.batteryLevelText.getMeasuredHeight() / 2) + rect2.centerY()), f).centerX();
        float f2 = z ? centerX : 0.0f;
        if (z) {
            centerX = 0.0f;
        }
        final ValueAnimator ofFloat3 = ValueAnimator.ofFloat(f2, centerX);
        ofFloat3.setDuration(300L);
        ofFloat3.setInterpolator(null);
        ofFloat3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContentAnimator$translateTextX$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip samsungBatteryStatusChip = SamsungBatteryStatusChip.this;
                BatteryStatusChipClearTextView batteryStatusChipClearTextView = samsungBatteryStatusChip.batteryLevelText;
                ValueAnimator valueAnimator = ofFloat3;
                batteryStatusChipClearTextView.setTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.percentSign.setTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                samsungBatteryStatusChip.batteryChargingTextView.setTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        float centerX2 = rect.centerX() - getScaledBounds(new Rect(rect2.centerX() - (this.batteryContent.getMeasuredWidth() / 2), rect2.centerY() - (this.chargingIcon.getMeasuredHeight() / 2), this.chargingIcon.getMeasuredWidth() + (rect2.centerX() - (this.batteryContent.getMeasuredWidth() / 2)), (this.chargingIcon.getMeasuredHeight() / 2) + rect2.centerY()), f).centerX();
        final ValueAnimator ofFloat4 = ValueAnimator.ofFloat(z ? centerX2 : 0.0f, z ? 0.0f : centerX2);
        ofFloat4.setDuration(300L);
        ofFloat4.setInterpolator(null);
        ofFloat4.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getContentAnimator$translateIconX$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SamsungBatteryStatusChip.this.chargingIcon.setTranslationX(((Float) ofFloat4.getAnimatedValue()).floatValue());
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3, ofFloat4);
        return animatorSet;
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
        if (!DarkIconDispatcher.isInAreas(arrayList, getRootView().requireViewById(R.id.battery))) {
            f = 0.0f;
        }
        int color = getContext().getColor(f >= 0.5f ? R.color.status_bar_battery_combined_chip_background_color_light : R.color.status_bar_battery_combined_chip_background_color);
        int color2 = getContext().getColor(f >= 0.5f ? R.color.status_bar_battery_chip_background_color_light : R.color.status_bar_battery_chip_background_color);
        Drawable background = this.background.getBackground();
        BlendMode blendMode = BlendMode.SRC_ATOP;
        background.setColorFilter(new BlendModeColorFilter(color2, blendMode));
        this.batteryCombinedChip.getBackground().setColorFilter(new BlendModeColorFilter(color, blendMode));
        this.batteryLevelProgressBg.getBackground().setColorFilter(new BlendModeColorFilter(getContext().getColor(f >= 0.5f ? R.color.status_bar_battery_chip_level_color_light : R.color.status_bar_battery_chip_level_color), blendMode));
        Context context = getContext();
        int i2 = R.color.status_bar_battery_chip_text_color;
        int color3 = context.getColor(f >= 0.5f ? R.color.status_bar_battery_chip_text_color_light : R.color.status_bar_battery_chip_text_color);
        Context context2 = getContext();
        if (f < 0.5f) {
            i2 = R.color.status_bar_battery_chip_text_color_light;
        }
        int color4 = context2.getColor(i2);
        this.chargingIcon.setColorFilter(new BlendModeColorFilter(color3, blendMode));
        BatteryStatusChipClearTextView batteryStatusChipClearTextView = this.batteryLevelText;
        batteryStatusChipClearTextView.textPaint.setColor(color3);
        batteryStatusChipClearTextView.invalidate();
        this.percentSign.setTextColor(color3);
        this.percentSignRtl.setTextColor(color3);
        this.batteryChargingTextView.setTextColor(color4);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((DarkIconDispatcher) Dependency.sDependency.getDependencyInner(DarkIconDispatcher.class)).removeDarkReceiver(this);
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final void setBoundsForAnimation(int i, int i2, int i3, int i4) {
        this.batteryChipContainer.setLeftTopRightBottom(i - getLeft(), i2 - getTop(), i3 - getLeft(), i4 - getTop());
    }

    public /* synthetic */ SamsungBatteryStatusChip(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public SamsungBatteryStatusChip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Locale locale = context.getResources().getConfiguration().locale;
        this.locale = locale;
        FrameLayout.inflate(context, R.layout.samsung_battery_combined_chip, this);
        this.batteryChipContainer = (ConstraintLayout) requireViewById(R.id.battery_chip_container);
        this.batteryCombinedChip = (LinearLayout) requireViewById(R.id.battery_combined_chip);
        TextView textView = (TextView) requireViewById(R.id.battery_combined_chip_charging_text);
        this.batteryChargingTextView = textView;
        this.background = (FrameLayout) requireViewById(R.id.battery_chip_background);
        this.batteryContent = (LinearLayout) requireViewById(R.id.battery_chip_content);
        this.chargingIcon = (ImageView) requireViewById(R.id.charging_icon);
        this.batteryLevelProgress = (LottieAnimationView) requireViewById(R.id.battery_level_progress);
        this.batteryLevelText = (BatteryStatusChipClearTextView) requireViewById(R.id.battery_level_text);
        TextView textView2 = (TextView) requireViewById(R.id.percent_sign);
        this.percentSign = textView2;
        this.batteryLevelProgressBg = (ImageView) requireViewById(R.id.battery_level_progress_background);
        TextView textView3 = (TextView) requireViewById(R.id.percent_sign_rtl);
        this.percentSignRtl = textView3;
        String language = Locale.getDefault().getLanguage();
        if (!"he".equals(language) && !"ur".equals(language) && !"tr".equals(language) && !"eu".equals(language)) {
            textView3.setVisibility(8);
        } else {
            textView2.setVisibility(8);
            textView3.setVisibility(0);
        }
        if (Intrinsics.areEqual(locale.toString(), "my_MM")) {
            textView.setGravity(48);
            textView.setTextSize(0, context.getResources().getDimension(R.dimen.status_bar_battery_chip_level_text_size_myanmar));
        }
    }
}
