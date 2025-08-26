package com.android.systemui.power.view;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Slog;
import android.view.SemBlurInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.LinearInterpolator;
import androidx.core.animation.ObjectAnimator;
import androidx.core.animation.PathInterpolator;
import androidx.core.animation.ValueAnimator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.power.SecPowerUI;
import com.android.systemui.power.constants.ChargerAnimationConstants;
import com.android.systemui.power.listener.ChargerAnimationListener;
import com.android.systemui.power.utils.ChargerAnimationUtils;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.StringCompanionObject;

/* loaded from: classes2.dex */
public final class ChargerFlipCoverView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ChargerAnimationListener animationListener;
    public final Lazy backgroundBlur$delegate;
    public int backgroundColor;
    public final Lazy batteryIcon$delegate;
    public final Lazy batteryLevel$delegate;
    public final Lazy batteryProgressbar$delegate;
    public final AnimatorSet chargerAnimationSet;
    public int chargingType;
    public final Lazy coverCard$delegate;
    public int currentBatteryLevel;
    public final int currentLayoutDirection;

    public ChargerFlipCoverView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        final int i = 0;
        this.coverCard$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.power.view.ChargerFlipCoverView$$ExternalSyntheticLambda0
            public final /* synthetic */ ChargerFlipCoverView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ChargerFlipCoverView chargerFlipCoverView = this.f$0;
                switch (i) {
                    case 0:
                        int i2 = ChargerFlipCoverView.$r8$clinit;
                        return (CardView) chargerFlipCoverView.requireViewById(R.id.flip_cover_card_view);
                    case 1:
                        int i3 = ChargerFlipCoverView.$r8$clinit;
                        return (ImageView) chargerFlipCoverView.requireViewById(R.id.background_blur);
                    case 2:
                        int i4 = ChargerFlipCoverView.$r8$clinit;
                        return (TextView) chargerFlipCoverView.requireViewById(R.id.battery_level);
                    case 3:
                        int i5 = ChargerFlipCoverView.$r8$clinit;
                        return (LottieAnimationView) chargerFlipCoverView.requireViewById(R.id.battery_progressbar);
                    default:
                        int i6 = ChargerFlipCoverView.$r8$clinit;
                        return (ImageView) chargerFlipCoverView.requireViewById(R.id.battery_icon);
                }
            }
        });
        final int i2 = 1;
        this.backgroundBlur$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.power.view.ChargerFlipCoverView$$ExternalSyntheticLambda0
            public final /* synthetic */ ChargerFlipCoverView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ChargerFlipCoverView chargerFlipCoverView = this.f$0;
                switch (i2) {
                    case 0:
                        int i22 = ChargerFlipCoverView.$r8$clinit;
                        return (CardView) chargerFlipCoverView.requireViewById(R.id.flip_cover_card_view);
                    case 1:
                        int i3 = ChargerFlipCoverView.$r8$clinit;
                        return (ImageView) chargerFlipCoverView.requireViewById(R.id.background_blur);
                    case 2:
                        int i4 = ChargerFlipCoverView.$r8$clinit;
                        return (TextView) chargerFlipCoverView.requireViewById(R.id.battery_level);
                    case 3:
                        int i5 = ChargerFlipCoverView.$r8$clinit;
                        return (LottieAnimationView) chargerFlipCoverView.requireViewById(R.id.battery_progressbar);
                    default:
                        int i6 = ChargerFlipCoverView.$r8$clinit;
                        return (ImageView) chargerFlipCoverView.requireViewById(R.id.battery_icon);
                }
            }
        });
        final int i3 = 2;
        this.batteryLevel$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.power.view.ChargerFlipCoverView$$ExternalSyntheticLambda0
            public final /* synthetic */ ChargerFlipCoverView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ChargerFlipCoverView chargerFlipCoverView = this.f$0;
                switch (i3) {
                    case 0:
                        int i22 = ChargerFlipCoverView.$r8$clinit;
                        return (CardView) chargerFlipCoverView.requireViewById(R.id.flip_cover_card_view);
                    case 1:
                        int i32 = ChargerFlipCoverView.$r8$clinit;
                        return (ImageView) chargerFlipCoverView.requireViewById(R.id.background_blur);
                    case 2:
                        int i4 = ChargerFlipCoverView.$r8$clinit;
                        return (TextView) chargerFlipCoverView.requireViewById(R.id.battery_level);
                    case 3:
                        int i5 = ChargerFlipCoverView.$r8$clinit;
                        return (LottieAnimationView) chargerFlipCoverView.requireViewById(R.id.battery_progressbar);
                    default:
                        int i6 = ChargerFlipCoverView.$r8$clinit;
                        return (ImageView) chargerFlipCoverView.requireViewById(R.id.battery_icon);
                }
            }
        });
        final int i4 = 3;
        this.batteryProgressbar$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.power.view.ChargerFlipCoverView$$ExternalSyntheticLambda0
            public final /* synthetic */ ChargerFlipCoverView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ChargerFlipCoverView chargerFlipCoverView = this.f$0;
                switch (i4) {
                    case 0:
                        int i22 = ChargerFlipCoverView.$r8$clinit;
                        return (CardView) chargerFlipCoverView.requireViewById(R.id.flip_cover_card_view);
                    case 1:
                        int i32 = ChargerFlipCoverView.$r8$clinit;
                        return (ImageView) chargerFlipCoverView.requireViewById(R.id.background_blur);
                    case 2:
                        int i42 = ChargerFlipCoverView.$r8$clinit;
                        return (TextView) chargerFlipCoverView.requireViewById(R.id.battery_level);
                    case 3:
                        int i5 = ChargerFlipCoverView.$r8$clinit;
                        return (LottieAnimationView) chargerFlipCoverView.requireViewById(R.id.battery_progressbar);
                    default:
                        int i6 = ChargerFlipCoverView.$r8$clinit;
                        return (ImageView) chargerFlipCoverView.requireViewById(R.id.battery_icon);
                }
            }
        });
        final int i5 = 4;
        this.batteryIcon$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.power.view.ChargerFlipCoverView$$ExternalSyntheticLambda0
            public final /* synthetic */ ChargerFlipCoverView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ChargerFlipCoverView chargerFlipCoverView = this.f$0;
                switch (i5) {
                    case 0:
                        int i22 = ChargerFlipCoverView.$r8$clinit;
                        return (CardView) chargerFlipCoverView.requireViewById(R.id.flip_cover_card_view);
                    case 1:
                        int i32 = ChargerFlipCoverView.$r8$clinit;
                        return (ImageView) chargerFlipCoverView.requireViewById(R.id.background_blur);
                    case 2:
                        int i42 = ChargerFlipCoverView.$r8$clinit;
                        return (TextView) chargerFlipCoverView.requireViewById(R.id.battery_level);
                    case 3:
                        int i52 = ChargerFlipCoverView.$r8$clinit;
                        return (LottieAnimationView) chargerFlipCoverView.requireViewById(R.id.battery_progressbar);
                    default:
                        int i6 = ChargerFlipCoverView.$r8$clinit;
                        return (ImageView) chargerFlipCoverView.requireViewById(R.id.battery_icon);
                }
            }
        });
        this.currentLayoutDirection = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
        this.chargerAnimationSet = new AnimatorSet();
        this.backgroundColor = context.getColor(R.color.charging_vi_flip_cover_background_dark_bg_color);
    }

    public static final SemBlurInfo access$getBackgroundBlur(ChargerFlipCoverView chargerFlipCoverView, int i) {
        chargerFlipCoverView.getClass();
        SemBlurInfo.Builder backgroundColor = new SemBlurInfo.Builder(0).setBackgroundColor(chargerFlipCoverView.backgroundColor);
        ChargerAnimationUtils.INSTANCE.getClass();
        boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper(32L, true);
        Slog.d("PowerUI.ChargerAnimationUtil", "isCoverWhiteWallpaper in TOP : " + zIsWhiteKeyguardWallpaper);
        return backgroundColor.setColorCurvePreset(!zIsWhiteKeyguardWallpaper ? 112 : 123).setBackgroundCornerRadius(chargerFlipCoverView.getResources().getDimension(R.dimen.charging_flip_cover_card_radius)).setRadius(i).build();
    }

    public final LottieAnimationView getBatteryProgressbar() {
        return (LottieAnimationView) this.batteryProgressbar$delegate.getValue();
    }

    public final CardView getCoverCard() {
        return (CardView) this.coverCard$delegate.getValue();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() throws Resources.NotFoundException {
        super.onAttachedToWindow();
        post(new Runnable() { // from class: com.android.systemui.power.view.ChargerFlipCoverView$setScalePivot$1
            @Override // java.lang.Runnable
            public final void run() {
                ChargerFlipCoverView chargerFlipCoverView = this.this$0;
                int i = ChargerFlipCoverView.$r8$clinit;
                chargerFlipCoverView.getCoverCard().setPivotX(this.this$0.currentLayoutDirection == 1 ? 0.0f : r1.getCoverCard().getMeasuredWidth());
                this.this$0.getCoverCard().setPivotY(0.0f);
            }
        });
        TextView textView = (TextView) this.batteryLevel$delegate.getValue();
        int i = StringCompanionObject.$r8$clinit;
        textView.setText(String.format("%d", Arrays.copyOf(new Object[]{Integer.valueOf(this.currentBatteryLevel)}, 1)));
        int color = getContext().getColor(R.color.charging_vi_now_bar_battery_main_dark_bg_color);
        ChargerAnimationUtils.INSTANCE.getClass();
        boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper(32L, true);
        Slog.d("PowerUI.ChargerAnimationUtil", "isCoverWhiteWallpaper in TOP : " + zIsWhiteKeyguardWallpaper);
        if (zIsWhiteKeyguardWallpaper) {
            this.backgroundColor = getContext().getColor(R.color.charging_vi_flip_cover_background_bg_color);
            color = getContext().getColor(R.color.charging_vi_now_bar_battery_main_color);
        }
        ((ImageView) this.batteryIcon$delegate.getValue()).setColorFilter(color);
        ((TextView) this.batteryLevel$delegate.getValue()).setTextColor(color);
        ((ImageView) this.backgroundBlur$delegate.getValue()).setBackgroundColor(this.backgroundColor);
        getBatteryProgressbar().setAnimation(ChargerAnimationUtils.getLottieString(this.chargingType, this.currentLayoutDirection, false));
        getBatteryProgressbar().setScaleType(ImageView.ScaleType.FIT_XY);
        LottieAnimationView batteryProgressbar = getBatteryProgressbar();
        ChargerAnimationConstants.INSTANCE.getClass();
        batteryProgressbar.addValueCallback(ChargerAnimationConstants.WAVE_KEY_PATH, (KeyPath) LottieProperty.BLUR_RADIUS, (SimpleLottieValueCallback) new SimpleLottieValueCallback() { // from class: com.android.systemui.power.view.ChargerFlipCoverView$setProgressBarLottie$1
            @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
            public final /* bridge */ /* synthetic */ Object getValue() {
                return Float.valueOf(16.0f);
            }
        });
        AnimatorSet animatorSet = this.chargerAnimationSet;
        final ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(getCoverCard(), "alpha", 0.0f, 1.0f);
        objectAnimatorOfFloat.m896setDuration(100L);
        objectAnimatorOfFloat.mInterpolator = new LinearInterpolator();
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.power.view.ChargerFlipCoverView$addFadeInAnimations$1$1
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                int i2 = ChargerFlipCoverView.$r8$clinit;
                ChargerFlipCoverView chargerFlipCoverView = this.this$0;
                chargerFlipCoverView.getClass();
                SpringAnimation springAnimation = new SpringAnimation(chargerFlipCoverView.getCoverCard(), DynamicAnimation.SCALE_X);
                springAnimation.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(200.0f, 0.71f);
                springAnimation.mValue = 0.5f;
                springAnimation.mStartValueIsSet = true;
                springAnimation.animateToFinalPosition(1.0f);
                SpringAnimation springAnimation2 = new SpringAnimation(chargerFlipCoverView.getCoverCard(), DynamicAnimation.SCALE_Y);
                springAnimation2.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(200.0f, 0.71f);
                springAnimation2.mValue = 0.5f;
                springAnimation2.mStartValueIsSet = true;
                springAnimation2.animateToFinalPosition(1.0f);
                SpringAnimation springAnimation3 = new SpringAnimation((ImageView) chargerFlipCoverView.batteryIcon$delegate.getValue(), DynamicAnimation.TRANSLATION_X);
                springAnimation3.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(200.0f, 0.71f);
                springAnimation3.mValue = chargerFlipCoverView.currentLayoutDirection == 1 ? -20.0f : 20.0f;
                springAnimation3.mStartValueIsSet = true;
                springAnimation3.animateToFinalPosition(0.0f);
            }
        });
        objectAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.view.ChargerFlipCoverView$addFadeInAnimations$1$2
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                int iFloatValue = (int) (((Float) objectAnimatorOfFloat.getAnimatedValue()).floatValue() * 150.0f);
                int i2 = ChargerFlipCoverView.$r8$clinit;
                ChargerFlipCoverView chargerFlipCoverView = this;
                ((ImageView) chargerFlipCoverView.backgroundBlur$delegate.getValue()).semSetBlurInfo(ChargerFlipCoverView.access$getBackgroundBlur(chargerFlipCoverView, iFloatValue));
            }
        });
        animatorSet.play(objectAnimatorOfFloat);
        float dimension = getResources().getDimension(R.dimen.charging_flip_cover_card_width);
        int i2 = this.currentLayoutDirection;
        float f = i2 == 0 ? -dimension : dimension;
        float progressbarComputeWidth = ChargerAnimationUtils.getProgressbarComputeWidth(dimension, i2, this.currentBatteryLevel) + f;
        AnimatorSet animatorSet2 = this.chargerAnimationSet;
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(getBatteryProgressbar(), "translationX", f, progressbarComputeWidth);
        objectAnimatorOfFloat2.m896setDuration(1000L);
        objectAnimatorOfFloat2.mInterpolator = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        animatorSet2.play(objectAnimatorOfFloat2);
        AnimatorSet animatorSet3 = this.chargerAnimationSet;
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(10.0f, 0.0f);
        valueAnimatorOfFloat.setDuration(1000L);
        valueAnimatorOfFloat.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.view.ChargerFlipCoverView$addProgressbarWaveBlurAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                int i3 = ChargerFlipCoverView.$r8$clinit;
                LottieAnimationView batteryProgressbar2 = this.this$0.getBatteryProgressbar();
                ChargerAnimationConstants.INSTANCE.getClass();
                KeyPath keyPath = ChargerAnimationConstants.WAVE_KEY_PATH;
                Float f2 = LottieProperty.BLUR_RADIUS;
                final ValueAnimator valueAnimator = valueAnimatorOfFloat;
                batteryProgressbar2.addValueCallback(keyPath, (KeyPath) f2, new SimpleLottieValueCallback() { // from class: com.android.systemui.power.view.ChargerFlipCoverView$addProgressbarWaveBlurAnimation$1$1.1
                    @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                    public final Object getValue() {
                        return (Float) valueAnimator.getAnimatedValue();
                    }
                });
            }
        });
        animatorSet3.play(valueAnimatorOfFloat);
        AnimatorSet animatorSet4 = this.chargerAnimationSet;
        final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat2.setDuration(1700L);
        valueAnimatorOfFloat2.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        valueAnimatorOfFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.view.ChargerFlipCoverView$addBatteryLevelShadowAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                ChargerAnimationUtils chargerAnimationUtils = ChargerAnimationUtils.INSTANCE;
                float fFloatValue = ((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue();
                chargerAnimationUtils.getClass();
                float f2 = fFloatValue * 8.0f;
                float f3 = 8.0f + f2;
                float f4 = f2 + 0.0f;
                float[] fArr = {f3, f4, f4};
                int i3 = ChargerFlipCoverView.$r8$clinit;
                ChargerFlipCoverView chargerFlipCoverView = this;
                ((TextView) chargerFlipCoverView.batteryLevel$delegate.getValue()).setShadowLayer(fArr[0], fArr[1], fArr[2], chargerFlipCoverView.getContext().getColor(R.color.charging_vi_flip_cover_battery_shadow_color));
            }
        });
        valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.power.view.ChargerFlipCoverView$addBatteryLevelShadowAnimation$1$2
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                int i3 = ChargerFlipCoverView.$r8$clinit;
                this.this$0.getCoverCard().setPivotY(r1.getCoverCard().getMeasuredHeight() / 2.0f);
            }
        });
        animatorSet4.play(valueAnimatorOfFloat2);
        AnimatorSet animatorSet5 = this.chargerAnimationSet;
        final ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(1.0f, 0.5f);
        valueAnimatorOfFloat3.setStartDelay(3200L);
        valueAnimatorOfFloat3.setDuration(200L);
        valueAnimatorOfFloat3.mInterpolator = new PathInterpolator(0.17f, 0.17f, 0.67f, 1.0f);
        valueAnimatorOfFloat3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.view.ChargerFlipCoverView$addScaleDownAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                int i3 = ChargerFlipCoverView.$r8$clinit;
                ChargerFlipCoverView chargerFlipCoverView = this.this$0;
                CardView coverCard = chargerFlipCoverView.getCoverCard();
                ValueAnimator valueAnimator = valueAnimatorOfFloat3;
                coverCard.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                chargerFlipCoverView.getCoverCard().setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        animatorSet5.play(valueAnimatorOfFloat3);
        AnimatorSet animatorSet6 = this.chargerAnimationSet;
        final ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(getCoverCard(), "alpha", 1.0f, 0.0f);
        objectAnimatorOfFloat3.setStartDelay(3200L);
        objectAnimatorOfFloat3.m896setDuration(200L);
        objectAnimatorOfFloat3.mInterpolator = new PathInterpolator(0.17f, 0.17f, 0.67f, 1.0f);
        objectAnimatorOfFloat3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.power.view.ChargerFlipCoverView$addFadeOutAnimation$1$1
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Slog.i("PowerUI.ChargerFlipCoverView", "FadeOutAnimationEnd, call onChargerAnimationEnd()");
                ChargerAnimationListener chargerAnimationListener = this.this$0.animationListener;
                if (chargerAnimationListener != null) {
                    ((SecPowerUI) chargerAnimationListener).onChargerAnimationEnd();
                }
            }
        });
        objectAnimatorOfFloat3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.view.ChargerFlipCoverView$addFadeOutAnimation$1$2
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                int iFloatValue = (int) (((Float) objectAnimatorOfFloat3.getAnimatedValue()).floatValue() * 150.0f);
                int i3 = ChargerFlipCoverView.$r8$clinit;
                ChargerFlipCoverView chargerFlipCoverView = this;
                ((ImageView) chargerFlipCoverView.backgroundBlur$delegate.getValue()).semSetBlurInfo(ChargerFlipCoverView.access$getBackgroundBlur(chargerFlipCoverView, iFloatValue));
            }
        });
        animatorSet6.play(objectAnimatorOfFloat3);
        getBatteryProgressbar().playAnimation();
        this.chargerAnimationSet.start();
        Log.d("PowerUI.ChargerFlipCoverView", "Animation Started");
    }
}
