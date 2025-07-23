package com.android.systemui.power.view;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.ObjectAnimator;
import androidx.core.animation.PathInterpolator;
import androidx.core.animation.ValueAnimator;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.SecPowerUI;
import com.android.systemui.power.constants.ChargerAnimationConstants;
import com.android.systemui.power.utils.ChargerAnimationUtils;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.StringCompanionObject;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ChargerNowBarView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SecPowerUI animationListener;
    public final Lazy batteryIcon$delegate;
    public final Lazy batteryIconShadow$delegate;
    public final Lazy batteryLevel$delegate;
    public final Lazy batteryLevelContainer$delegate;
    public final Lazy batteryPercent$delegate;
    public final Lazy batteryPercentRtl$delegate;
    public final Lazy batteryProgressbar$delegate;
    public final AnimatorSet chargerAnimationSet;
    public int chargingType;
    public int currentBatteryLevel;
    public final int currentLayoutDirection;
    public final boolean isRTL;

    public ChargerNowBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        final int i = 0;
        this.batteryLevelContainer$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.power.view.ChargerNowBarView$$ExternalSyntheticLambda0
            public final /* synthetic */ ChargerNowBarView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ChargerNowBarView chargerNowBarView = this.f$0;
                switch (i) {
                    case 0:
                        int i2 = ChargerNowBarView.$r8$clinit;
                        return (ViewGroup) chargerNowBarView.requireViewById(R.id.battery_level_container);
                    case 1:
                        int i3 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_level);
                    case 2:
                        int i4 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent);
                    case 3:
                        int i5 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent_rtl);
                    case 4:
                        int i6 = ChargerNowBarView.$r8$clinit;
                        return (LottieAnimationView) chargerNowBarView.requireViewById(R.id.battery_progressbar);
                    case 5:
                        int i7 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon);
                    default:
                        int i8 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon_shadow);
                }
            }
        });
        final int i2 = 1;
        this.batteryLevel$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.power.view.ChargerNowBarView$$ExternalSyntheticLambda0
            public final /* synthetic */ ChargerNowBarView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ChargerNowBarView chargerNowBarView = this.f$0;
                switch (i2) {
                    case 0:
                        int i22 = ChargerNowBarView.$r8$clinit;
                        return (ViewGroup) chargerNowBarView.requireViewById(R.id.battery_level_container);
                    case 1:
                        int i3 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_level);
                    case 2:
                        int i4 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent);
                    case 3:
                        int i5 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent_rtl);
                    case 4:
                        int i6 = ChargerNowBarView.$r8$clinit;
                        return (LottieAnimationView) chargerNowBarView.requireViewById(R.id.battery_progressbar);
                    case 5:
                        int i7 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon);
                    default:
                        int i8 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon_shadow);
                }
            }
        });
        final int i3 = 2;
        this.batteryPercent$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.power.view.ChargerNowBarView$$ExternalSyntheticLambda0
            public final /* synthetic */ ChargerNowBarView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ChargerNowBarView chargerNowBarView = this.f$0;
                switch (i3) {
                    case 0:
                        int i22 = ChargerNowBarView.$r8$clinit;
                        return (ViewGroup) chargerNowBarView.requireViewById(R.id.battery_level_container);
                    case 1:
                        int i32 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_level);
                    case 2:
                        int i4 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent);
                    case 3:
                        int i5 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent_rtl);
                    case 4:
                        int i6 = ChargerNowBarView.$r8$clinit;
                        return (LottieAnimationView) chargerNowBarView.requireViewById(R.id.battery_progressbar);
                    case 5:
                        int i7 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon);
                    default:
                        int i8 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon_shadow);
                }
            }
        });
        final int i4 = 3;
        this.batteryPercentRtl$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.power.view.ChargerNowBarView$$ExternalSyntheticLambda0
            public final /* synthetic */ ChargerNowBarView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ChargerNowBarView chargerNowBarView = this.f$0;
                switch (i4) {
                    case 0:
                        int i22 = ChargerNowBarView.$r8$clinit;
                        return (ViewGroup) chargerNowBarView.requireViewById(R.id.battery_level_container);
                    case 1:
                        int i32 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_level);
                    case 2:
                        int i42 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent);
                    case 3:
                        int i5 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent_rtl);
                    case 4:
                        int i6 = ChargerNowBarView.$r8$clinit;
                        return (LottieAnimationView) chargerNowBarView.requireViewById(R.id.battery_progressbar);
                    case 5:
                        int i7 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon);
                    default:
                        int i8 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon_shadow);
                }
            }
        });
        final int i5 = 4;
        this.batteryProgressbar$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.power.view.ChargerNowBarView$$ExternalSyntheticLambda0
            public final /* synthetic */ ChargerNowBarView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ChargerNowBarView chargerNowBarView = this.f$0;
                switch (i5) {
                    case 0:
                        int i22 = ChargerNowBarView.$r8$clinit;
                        return (ViewGroup) chargerNowBarView.requireViewById(R.id.battery_level_container);
                    case 1:
                        int i32 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_level);
                    case 2:
                        int i42 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent);
                    case 3:
                        int i52 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent_rtl);
                    case 4:
                        int i6 = ChargerNowBarView.$r8$clinit;
                        return (LottieAnimationView) chargerNowBarView.requireViewById(R.id.battery_progressbar);
                    case 5:
                        int i7 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon);
                    default:
                        int i8 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon_shadow);
                }
            }
        });
        final int i6 = 5;
        this.batteryIcon$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.power.view.ChargerNowBarView$$ExternalSyntheticLambda0
            public final /* synthetic */ ChargerNowBarView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ChargerNowBarView chargerNowBarView = this.f$0;
                switch (i6) {
                    case 0:
                        int i22 = ChargerNowBarView.$r8$clinit;
                        return (ViewGroup) chargerNowBarView.requireViewById(R.id.battery_level_container);
                    case 1:
                        int i32 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_level);
                    case 2:
                        int i42 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent);
                    case 3:
                        int i52 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent_rtl);
                    case 4:
                        int i62 = ChargerNowBarView.$r8$clinit;
                        return (LottieAnimationView) chargerNowBarView.requireViewById(R.id.battery_progressbar);
                    case 5:
                        int i7 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon);
                    default:
                        int i8 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon_shadow);
                }
            }
        });
        final int i7 = 6;
        this.batteryIconShadow$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.power.view.ChargerNowBarView$$ExternalSyntheticLambda0
            public final /* synthetic */ ChargerNowBarView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ChargerNowBarView chargerNowBarView = this.f$0;
                switch (i7) {
                    case 0:
                        int i22 = ChargerNowBarView.$r8$clinit;
                        return (ViewGroup) chargerNowBarView.requireViewById(R.id.battery_level_container);
                    case 1:
                        int i32 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_level);
                    case 2:
                        int i42 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent);
                    case 3:
                        int i52 = ChargerNowBarView.$r8$clinit;
                        return (TextView) chargerNowBarView.requireViewById(R.id.battery_percent_rtl);
                    case 4:
                        int i62 = ChargerNowBarView.$r8$clinit;
                        return (LottieAnimationView) chargerNowBarView.requireViewById(R.id.battery_progressbar);
                    case 5:
                        int i72 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon);
                    default:
                        int i8 = ChargerNowBarView.$r8$clinit;
                        return (ImageView) chargerNowBarView.requireViewById(R.id.battery_icon_shadow);
                }
            }
        });
        this.isRTL = ChargerAnimationUtils.checkExceptionalLanguage();
        this.currentLayoutDirection = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
        this.chargerAnimationSet = new AnimatorSet();
    }

    public final ImageView getBatteryIcon() {
        return (ImageView) this.batteryIcon$delegate.getValue();
    }

    public final ImageView getBatteryIconShadow() {
        return (ImageView) this.batteryIconShadow$delegate.getValue();
    }

    public final LottieAnimationView getBatteryProgressbar() {
        return (LottieAnimationView) this.batteryProgressbar$delegate.getValue();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        int i;
        int i2;
        super.onAttachedToWindow();
        TextView textView = (TextView) this.batteryLevel$delegate.getValue();
        int i3 = StringCompanionObject.$r8$clinit;
        textView.setText(String.format("%d", Arrays.copyOf(new Object[]{Integer.valueOf(this.currentBatteryLevel)}, 1)));
        if (this.isRTL) {
            ((TextView) this.batteryPercentRtl$delegate.getValue()).setText(String.format("%s", Arrays.copyOf(new Object[]{"%"}, 1)));
            ((TextView) this.batteryPercent$delegate.getValue()).setVisibility(8);
        } else {
            ((TextView) this.batteryPercent$delegate.getValue()).setText(String.format("%s", Arrays.copyOf(new Object[]{"%"}, 1)));
            ((TextView) this.batteryPercentRtl$delegate.getValue()).setVisibility(8);
        }
        boolean isTablet = DeviceType.isTablet();
        ChargerAnimationUtils.INSTANCE.getClass();
        boolean equals = "my-MM".equals(Locale.getDefault().toLanguageTag());
        if (isTablet) {
            if (equals) {
                ChargerAnimationConstants.INSTANCE.getClass();
                i = ChargerAnimationConstants.BATTERY_LEVEL_SIZE_TABLET_MYANMAR;
            } else {
                ChargerAnimationConstants.INSTANCE.getClass();
                i = ChargerAnimationConstants.BATTERY_LEVEL_SIZE_TABLET;
            }
        } else if (equals) {
            ChargerAnimationConstants.INSTANCE.getClass();
            i = ChargerAnimationConstants.BATTERY_LEVEL_SIZE_MYANMAR;
        } else {
            ChargerAnimationConstants.INSTANCE.getClass();
            i = ChargerAnimationConstants.BATTERY_LEVEL_SIZE;
        }
        if (isTablet) {
            ChargerAnimationConstants.INSTANCE.getClass();
            i2 = ChargerAnimationConstants.BATTERY_LEVEL_PERCENT_SIZE_TABLET;
        } else {
            ChargerAnimationConstants.INSTANCE.getClass();
            i2 = ChargerAnimationConstants.BATTERY_LEVEL_PERCENT_SIZE;
        }
        Pair pair = new Pair(Integer.valueOf(i), Integer.valueOf(i2));
        int intValue = ((Number) pair.component1()).intValue();
        int intValue2 = ((Number) pair.component2()).intValue();
        FontSizeUtils.updateFontSize((TextView) this.batteryLevel$delegate.getValue(), intValue);
        FontSizeUtils.updateFontSize(this.isRTL ? (TextView) this.batteryPercentRtl$delegate.getValue() : (TextView) this.batteryPercent$delegate.getValue(), intValue2);
        ImageView batteryIcon = getBatteryIcon();
        int i4 = this.chargingType;
        batteryIcon.setImageResource((i4 == 3 || i4 == 4 || i4 == 5) ? R.drawable.nowbar_battery_fast_charging_icon : R.drawable.nowbar_battery_charging_icon);
        ImageView batteryIconShadow = getBatteryIconShadow();
        ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_XY;
        batteryIconShadow.setScaleType(scaleType);
        int color = Settings.System.getInt(getContext().getContentResolver(), SettingsHelper.INDEX_MINIMAL_BATTERY_USE, 0) == 1 ? getContext().getColor(R.color.charging_vi_now_bar_battery_main_color) : ((Settings.System.getInt(getContext().getContentResolver(), SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY, 0) != 1 && PowerUiRune.WINDOW_BLUR_SUPPORTED) || ChargerAnimationUtils.isWhiteWallpaper(false)) ? getContext().getColor(R.color.charging_vi_now_bar_battery_main_color) : getContext().getColor(R.color.charging_vi_now_bar_battery_main_dark_bg_color);
        getBatteryIcon().setColorFilter(color);
        ((TextView) this.batteryLevel$delegate.getValue()).setTextColor(color);
        (this.isRTL ? (TextView) this.batteryPercentRtl$delegate.getValue() : (TextView) this.batteryPercent$delegate.getValue()).setTextColor(color);
        getBatteryProgressbar().setAnimation(ChargerAnimationUtils.getLottieString(this.chargingType, this.currentLayoutDirection, true));
        getBatteryProgressbar().setScaleType(scaleType);
        LottieAnimationView batteryProgressbar = getBatteryProgressbar();
        ChargerAnimationConstants.INSTANCE.getClass();
        batteryProgressbar.addValueCallback(ChargerAnimationConstants.WAVE_KEY_PATH, (KeyPath) LottieProperty.BLUR_RADIUS, (SimpleLottieValueCallback) new SimpleLottieValueCallback() { // from class: com.android.systemui.power.view.ChargerNowBarView$setProgressBarLottie$1
            @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
            public final /* bridge */ /* synthetic */ Object getValue() {
                return Float.valueOf(16.0f);
            }
        });
        ((View) getParent()).post(new Runnable() { // from class: com.android.systemui.power.view.ChargerNowBarView$onAttachedToWindow$1
            @Override // java.lang.Runnable
            public final void run() {
                final ChargerNowBarView chargerNowBarView = ChargerNowBarView.this;
                int i5 = ChargerNowBarView.$r8$clinit;
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(chargerNowBarView.getBatteryIcon(), "alpha", 0.0f, 1.0f);
                ofFloat.m894setDuration(600L);
                ofFloat.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
                final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.05f, 1.0f);
                ofFloat2.setDuration(1700L);
                ofFloat2.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
                ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.view.ChargerNowBarView$addBatteryIconAnimation$batteryIconScaleAnimator$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        int i6 = ChargerNowBarView.$r8$clinit;
                        ChargerNowBarView chargerNowBarView2 = ChargerNowBarView.this;
                        ImageView batteryIcon2 = chargerNowBarView2.getBatteryIcon();
                        ValueAnimator valueAnimator = ofFloat2;
                        batteryIcon2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        chargerNowBarView2.getBatteryIcon().setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(chargerNowBarView.getBatteryIcon(), "translationY", -12.0f, 0.0f);
                ofFloat3.m894setDuration(1700L);
                ofFloat3.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
                chargerNowBarView.chargerAnimationSet.playTogether(ofFloat, ofFloat2, ofFloat3);
                final ValueAnimator ofFloat4 = ValueAnimator.ofFloat(1.05f, 1.0f);
                ofFloat4.setDuration(1700L);
                ofFloat4.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
                ofFloat4.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.view.ChargerNowBarView$addBatteryIconShadowAnimation$batteryIconShadowScaleAnimator$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        int i6 = ChargerNowBarView.$r8$clinit;
                        ChargerNowBarView chargerNowBarView2 = ChargerNowBarView.this;
                        ImageView batteryIconShadow2 = chargerNowBarView2.getBatteryIconShadow();
                        ValueAnimator valueAnimator = ofFloat4;
                        batteryIconShadow2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        chargerNowBarView2.getBatteryIconShadow().setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
                ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(chargerNowBarView.getBatteryIconShadow(), "alpha", 0.0f, 1.0f);
                ofFloat5.m894setDuration(600L);
                ofFloat5.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
                ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(chargerNowBarView.getBatteryIconShadow(), "translationX", 8.0f, 0.0f);
                ofFloat6.m894setDuration(1700L);
                ofFloat6.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
                ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(chargerNowBarView.getBatteryIconShadow(), "translationY", 8.0f, 0.0f);
                ofFloat7.m894setDuration(1700L);
                ofFloat7.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
                chargerNowBarView.chargerAnimationSet.playTogether(ofFloat4, ofFloat5, ofFloat6, ofFloat7);
                ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat((ViewGroup) chargerNowBarView.batteryLevelContainer$delegate.getValue(), "alpha", 0.0f, 1.0f);
                ofFloat8.setStartDelay(200L);
                ofFloat8.m894setDuration(400L);
                ofFloat8.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
                int i6 = chargerNowBarView.currentBatteryLevel;
                int i7 = i6 - 12;
                if (i7 < 0) {
                    i7 = 0;
                }
                final ValueAnimator ofInt = ValueAnimator.ofInt(i7, i6);
                ofInt.setDuration(1000L);
                ofInt.mInterpolator = new PathInterpolator(0.17f, 0.17f, 0.0f, 1.0f);
                ofInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.view.ChargerNowBarView$addBatteryLevelAnimation$batteryLevelSliderAnimator$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        int i8 = ChargerNowBarView.$r8$clinit;
                        TextView textView2 = (TextView) ChargerNowBarView.this.batteryLevel$delegate.getValue();
                        int i9 = StringCompanionObject.$r8$clinit;
                        textView2.setText(String.format("%d", Arrays.copyOf(new Object[]{ofInt.getAnimatedValue()}, 1)));
                    }
                });
                final ValueAnimator ofFloat9 = ValueAnimator.ofFloat(1.05f, 1.0f);
                ofFloat9.setDuration(1700L);
                ofFloat9.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
                ofFloat9.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.view.ChargerNowBarView$addBatteryLevelAnimation$batteryLevelScaleAnimator$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        int i8 = ChargerNowBarView.$r8$clinit;
                        ChargerNowBarView chargerNowBarView2 = ChargerNowBarView.this;
                        ViewGroup viewGroup = (ViewGroup) chargerNowBarView2.batteryLevelContainer$delegate.getValue();
                        ValueAnimator valueAnimator = ofFloat9;
                        viewGroup.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        ((ViewGroup) chargerNowBarView2.batteryLevelContainer$delegate.getValue()).setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
                ObjectAnimator ofFloat10 = ObjectAnimator.ofFloat((ViewGroup) chargerNowBarView.batteryLevelContainer$delegate.getValue(), "translationY", -12.0f, 0.0f);
                ofFloat10.m894setDuration(1700L);
                ofFloat10.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
                chargerNowBarView.chargerAnimationSet.playTogether(ofFloat8, ofInt, ofFloat9, ofFloat10);
                AnimatorSet animatorSet = chargerNowBarView.chargerAnimationSet;
                final ValueAnimator ofFloat11 = ValueAnimator.ofFloat(1.0f, 0.0f);
                ofFloat11.setDuration(1700L);
                ofFloat11.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
                ofFloat11.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.view.ChargerNowBarView$addBatteryLevelShadowAnimation$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        ChargerAnimationUtils chargerAnimationUtils = ChargerAnimationUtils.INSTANCE;
                        float floatValue = ((Float) ValueAnimator.this.getAnimatedValue()).floatValue();
                        chargerAnimationUtils.getClass();
                        float f = floatValue * 8.0f;
                        float f2 = 8.0f + f;
                        float f3 = f + 0.0f;
                        float[] fArr = {f2, f3, f3};
                        float f4 = fArr[0];
                        float f5 = fArr[1];
                        float f6 = fArr[2];
                        ChargerNowBarView chargerNowBarView2 = chargerNowBarView;
                        TextView[] textViewArr = {(TextView) chargerNowBarView2.batteryLevel$delegate.getValue(), chargerNowBarView2.isRTL ? (TextView) chargerNowBarView2.batteryPercentRtl$delegate.getValue() : (TextView) chargerNowBarView2.batteryPercent$delegate.getValue()};
                        for (int i8 = 0; i8 < 2; i8++) {
                            textViewArr[i8].setShadowLayer(f4, f5, f6, chargerNowBarView2.getContext().getColor(R.color.charging_vi_now_bar_battery_shadow_color));
                        }
                    }
                });
                animatorSet.play(ofFloat11);
                Float valueOf = ((View) chargerNowBarView.getParent()) != null ? Float.valueOf(r1.getWidth()) : null;
                float floatValue = valueOf != null ? valueOf.floatValue() : 0.0f;
                ChargerAnimationUtils chargerAnimationUtils = ChargerAnimationUtils.INSTANCE;
                int i8 = chargerNowBarView.currentLayoutDirection;
                chargerAnimationUtils.getClass();
                float f = i8 == 0 ? -floatValue : floatValue;
                float progressbarComputeWidth = ChargerAnimationUtils.getProgressbarComputeWidth(floatValue, chargerNowBarView.currentLayoutDirection, chargerNowBarView.currentBatteryLevel) + f;
                AnimatorSet animatorSet2 = chargerNowBarView.chargerAnimationSet;
                ObjectAnimator ofFloat12 = ObjectAnimator.ofFloat(chargerNowBarView.getBatteryProgressbar(), "translationX", f, progressbarComputeWidth);
                ofFloat12.m894setDuration(1350L);
                ofFloat12.mInterpolator = new PathInterpolator(0.22f, 0.16f, 0.0f, 1.0f);
                animatorSet2.play(ofFloat12);
                AnimatorSet animatorSet3 = chargerNowBarView.chargerAnimationSet;
                final ValueAnimator ofFloat13 = ValueAnimator.ofFloat(16.0f, 0.0f);
                ofFloat13.setDuration(1350L);
                ofFloat13.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
                ofFloat13.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.view.ChargerNowBarView$addProgressbarWaveBlurAnimation$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        int i9 = ChargerNowBarView.$r8$clinit;
                        LottieAnimationView batteryProgressbar2 = ChargerNowBarView.this.getBatteryProgressbar();
                        ChargerAnimationConstants.INSTANCE.getClass();
                        KeyPath keyPath = ChargerAnimationConstants.WAVE_KEY_PATH;
                        Float f2 = LottieProperty.BLUR_RADIUS;
                        final ValueAnimator valueAnimator = ofFloat13;
                        batteryProgressbar2.addValueCallback(keyPath, (KeyPath) f2, new SimpleLottieValueCallback() { // from class: com.android.systemui.power.view.ChargerNowBarView$addProgressbarWaveBlurAnimation$1$1.1
                            @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                            public final Object getValue() {
                                return (Float) ValueAnimator.this.getAnimatedValue();
                            }
                        });
                    }
                });
                animatorSet3.play(ofFloat13);
                ChargerNowBarView chargerNowBarView2 = ChargerNowBarView.this;
                chargerNowBarView2.getBatteryProgressbar().playAnimation();
                chargerNowBarView2.chargerAnimationSet.start();
                Slog.d("PowerUI.ChargerNowBarView", "addOnChargerAnimationEndHandler");
                DefaultScheduler defaultScheduler = Dispatchers.Default;
                BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new ChargerNowBarView$addOnChargerAnimationEndHandler$1(3300L, chargerNowBarView2, null), 3);
                Log.d("PowerUI.ChargerNowBarView", "Animation Started");
            }
        });
    }
}
