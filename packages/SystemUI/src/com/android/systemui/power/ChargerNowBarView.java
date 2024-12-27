package com.android.systemui.power;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Slog;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.AnimatorSet.Builder;
import androidx.core.animation.ObjectAnimator;
import androidx.core.animation.PathInterpolator;
import androidx.core.animation.ValueAnimator;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.StringCompanionObject;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

public final class ChargerNowBarView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ChargerAnimationListener animationListener;
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
        this.batteryLevelContainer$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.power.ChargerNowBarView$batteryLevelContainer$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (ViewGroup) ChargerNowBarView.this.requireViewById(R.id.battery_level_container);
            }
        });
        this.batteryLevel$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.power.ChargerNowBarView$batteryLevel$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (TextView) ChargerNowBarView.this.requireViewById(R.id.battery_level);
            }
        });
        this.batteryPercent$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.power.ChargerNowBarView$batteryPercent$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (TextView) ChargerNowBarView.this.requireViewById(R.id.battery_percent);
            }
        });
        this.batteryPercentRtl$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.power.ChargerNowBarView$batteryPercentRtl$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (TextView) ChargerNowBarView.this.requireViewById(R.id.battery_percent_rtl);
            }
        });
        this.batteryProgressbar$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.power.ChargerNowBarView$batteryProgressbar$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (LottieAnimationView) ChargerNowBarView.this.requireViewById(R.id.battery_progressbar);
            }
        });
        this.batteryIcon$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.power.ChargerNowBarView$batteryIcon$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (ImageView) ChargerNowBarView.this.requireViewById(R.id.battery_icon);
            }
        });
        this.batteryIconShadow$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.power.ChargerNowBarView$batteryIconShadow$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (ImageView) ChargerNowBarView.this.requireViewById(R.id.battery_icon_shadow);
            }
        });
        this.isRTL = ChargerAnimationUtil.checkExceptionalLanguage();
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
        super.onAttachedToWindow();
        if (DeviceType.isDebuggable()) {
            Log.d("PowerUI.ChargerNowBarView", "onAttachedWindow()", new Exception("Need to check callstack, it is not real exception"));
        }
        if (this.isRTL) {
            TextView textView = (TextView) this.batteryPercentRtl$delegate.getValue();
            int i = StringCompanionObject.$r8$clinit;
            textView.setText(String.format("%s", Arrays.copyOf(new Object[]{"%"}, 1)));
            ((TextView) this.batteryPercent$delegate.getValue()).setVisibility(8);
        } else {
            TextView textView2 = (TextView) this.batteryPercent$delegate.getValue();
            int i2 = StringCompanionObject.$r8$clinit;
            textView2.setText(String.format("%s", Arrays.copyOf(new Object[]{"%"}, 1)));
            ((TextView) this.batteryPercentRtl$delegate.getValue()).setVisibility(8);
        }
        ((TextView) this.batteryLevel$delegate.getValue()).setText(String.format("%d", Arrays.copyOf(new Object[]{Integer.valueOf(this.currentBatteryLevel)}, 1)));
        ImageView batteryIcon = getBatteryIcon();
        ChargerAnimationUtil chargerAnimationUtil = ChargerAnimationUtil.INSTANCE;
        int i3 = this.chargingType;
        chargerAnimationUtil.getClass();
        batteryIcon.setImageResource((i3 == 3 || i3 == 4 || i3 == 5) ? R.drawable.nowbar_battery_fast_charging_icon : R.drawable.nowbar_battery_charging_icon);
        ImageView batteryIconShadow = getBatteryIconShadow();
        ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_XY;
        batteryIconShadow.setScaleType(scaleType);
        int color = Settings.System.getInt(getContext().getContentResolver(), SettingsHelper.INDEX_MINIMAL_BATTERY_USE, 0) == 1 ? getContext().getColor(R.color.charging_vi_now_bar_battery_main_color) : ((Settings.System.getInt(getContext().getContentResolver(), SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY, 0) != 1 && PowerUiRune.WINDOW_BLUR_SUPPORTED) || WallpaperUtils.isWhiteKeyguardWallpaper("bottom")) ? getContext().getColor(R.color.charging_vi_now_bar_battery_main_color) : getContext().getColor(R.color.charging_vi_now_bar_battery_main_white_bg_color);
        getBatteryIcon().setColorFilter(color);
        ((TextView) this.batteryLevel$delegate.getValue()).setTextColor(color);
        (this.isRTL ? (TextView) this.batteryPercentRtl$delegate.getValue() : (TextView) this.batteryPercent$delegate.getValue()).setTextColor(color);
        LottieAnimationView batteryProgressbar = getBatteryProgressbar();
        int i4 = this.chargingType;
        batteryProgressbar.setAnimation((this.currentLayoutDirection == 1 ? "rtl_" : "").concat(i4 != 3 ? (i4 == 4 || i4 == 5) ? "nowbar_gradient_superfast" : "nowbar_gradient_normal" : "nowbar_gradient_fast") + ".json");
        getBatteryProgressbar().setScaleType(scaleType);
        LottieAnimationView batteryProgressbar2 = getBatteryProgressbar();
        ChargerAnimationConstants.INSTANCE.getClass();
        batteryProgressbar2.addValueCallback(ChargerAnimationConstants.WAVE_KEY_PATH, (KeyPath) LottieProperty.BLUR_RADIUS, (SimpleLottieValueCallback) new SimpleLottieValueCallback() { // from class: com.android.systemui.power.ChargerNowBarView$setProgressBarLottie$1
            @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
            public final /* bridge */ /* synthetic */ Object getValue() {
                return Float.valueOf(16.0f);
            }
        });
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(getBatteryIcon(), "alpha", 0.0f, 1.0f);
        ofFloat.m788setDuration(600L);
        ofFloat.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.05f, 1.0f);
        ofFloat2.setDuration(1700L);
        ofFloat2.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.ChargerNowBarView$addBatteryIconAnimation$batteryIconScaleAnimator$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                int i5 = ChargerNowBarView.$r8$clinit;
                ChargerNowBarView chargerNowBarView = ChargerNowBarView.this;
                ImageView batteryIcon2 = chargerNowBarView.getBatteryIcon();
                ValueAnimator valueAnimator = ofFloat2;
                batteryIcon2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                chargerNowBarView.getBatteryIcon().setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(getBatteryIcon(), "translationY", -12.0f, 0.0f);
        ofFloat3.m788setDuration(1700L);
        ofFloat3.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        this.chargerAnimationSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        final ValueAnimator ofFloat4 = ValueAnimator.ofFloat(1.05f, 1.0f);
        ofFloat4.setDuration(1700L);
        ofFloat4.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        ofFloat4.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.ChargerNowBarView$addBatteryIconShadowAnimation$batteryIconShadowScaleAnimator$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                int i5 = ChargerNowBarView.$r8$clinit;
                ChargerNowBarView chargerNowBarView = ChargerNowBarView.this;
                ImageView batteryIconShadow2 = chargerNowBarView.getBatteryIconShadow();
                ValueAnimator valueAnimator = ofFloat4;
                batteryIconShadow2.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                chargerNowBarView.getBatteryIconShadow().setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(getBatteryIconShadow(), "alpha", 0.0f, 1.0f);
        ofFloat5.m788setDuration(600L);
        ofFloat5.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(getBatteryIconShadow(), "translationX", 8.0f, 0.0f);
        ofFloat6.m788setDuration(1700L);
        ofFloat6.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(getBatteryIconShadow(), "translationY", 8.0f, 0.0f);
        ofFloat7.m788setDuration(1700L);
        ofFloat7.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        this.chargerAnimationSet.playTogether(ofFloat4, ofFloat5, ofFloat6, ofFloat7);
        ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat((ViewGroup) this.batteryLevelContainer$delegate.getValue(), "alpha", 0.0f, 1.0f);
        ofFloat8.setStartDelay(200L);
        ofFloat8.m788setDuration(400L);
        ofFloat8.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        int i5 = this.currentBatteryLevel;
        int i6 = i5 - 12;
        if (i6 < 0) {
            i6 = 0;
        }
        final ValueAnimator ofInt = ValueAnimator.ofInt(i6, i5);
        ofInt.setDuration(1000L);
        ofInt.mInterpolator = new PathInterpolator(0.17f, 0.17f, 0.0f, 1.0f);
        ofInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.ChargerNowBarView$addBatteryLevelAnimation$batteryLevelSliderAnimator$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                int i7 = ChargerNowBarView.$r8$clinit;
                TextView textView3 = (TextView) ChargerNowBarView.this.batteryLevel$delegate.getValue();
                int i8 = StringCompanionObject.$r8$clinit;
                textView3.setText(String.format("%d", Arrays.copyOf(new Object[]{ofInt.getAnimatedValue()}, 1)));
            }
        });
        final ValueAnimator ofFloat9 = ValueAnimator.ofFloat(1.05f, 1.0f);
        ofFloat9.setDuration(1700L);
        ofFloat9.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        ofFloat9.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.ChargerNowBarView$addBatteryLevelAnimation$batteryLevelScaleAnimator$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                int i7 = ChargerNowBarView.$r8$clinit;
                ChargerNowBarView chargerNowBarView = ChargerNowBarView.this;
                ViewGroup viewGroup = (ViewGroup) chargerNowBarView.batteryLevelContainer$delegate.getValue();
                ValueAnimator valueAnimator = ofFloat9;
                viewGroup.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                ((ViewGroup) chargerNowBarView.batteryLevelContainer$delegate.getValue()).setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        ObjectAnimator ofFloat10 = ObjectAnimator.ofFloat((ViewGroup) this.batteryLevelContainer$delegate.getValue(), "translationY", -12.0f, 0.0f);
        ofFloat10.m788setDuration(1700L);
        ofFloat10.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        this.chargerAnimationSet.playTogether(ofFloat8, ofInt, ofFloat9, ofFloat10);
        AnimatorSet animatorSet = this.chargerAnimationSet;
        final ValueAnimator ofFloat11 = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat11.setDuration(1700L);
        ofFloat11.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        ofFloat11.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.ChargerNowBarView$addBatteryLevelShadowAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                ChargerAnimationUtil chargerAnimationUtil2 = ChargerAnimationUtil.INSTANCE;
                float floatValue = ((Float) ValueAnimator.this.getAnimatedValue()).floatValue();
                chargerAnimationUtil2.getClass();
                float f = floatValue * 8.0f;
                float f2 = 8.0f + f;
                float f3 = f + 0.0f;
                float[] fArr = {f2, f3, f3};
                float f4 = fArr[0];
                float f5 = fArr[1];
                float f6 = fArr[2];
                ChargerNowBarView chargerNowBarView = this;
                TextView[] textViewArr = {(TextView) chargerNowBarView.batteryLevel$delegate.getValue(), chargerNowBarView.isRTL ? (TextView) chargerNowBarView.batteryPercentRtl$delegate.getValue() : (TextView) chargerNowBarView.batteryPercent$delegate.getValue()};
                for (int i7 = 0; i7 < 2; i7++) {
                    textViewArr[i7].setShadowLayer(f4, f5, f6, chargerNowBarView.getContext().getColor(R.color.charging_vi_now_bar_battery_shadow_color));
                }
            }
        });
        animatorSet.getClass();
        animatorSet.new Builder(ofFloat11);
        Context context = getContext();
        float nowBarCollapsedWidth = this.currentLayoutDirection == 0 ? -ChargerAnimationUtil.getNowBarCollapsedWidth(context) : ChargerAnimationUtil.getNowBarCollapsedWidth(context);
        Context context2 = getContext();
        int i7 = this.currentLayoutDirection;
        int i8 = this.currentBatteryLevel;
        float nowBarCollapsedWidth2 = i7 == 0 ? (i8 / 100.0f) * ChargerAnimationUtil.getNowBarCollapsedWidth(context2) : -((i8 / 100.0f) * ChargerAnimationUtil.getNowBarCollapsedWidth(context2));
        AnimatorSet animatorSet2 = this.chargerAnimationSet;
        ObjectAnimator ofFloat12 = ObjectAnimator.ofFloat(getBatteryProgressbar(), "translationX", nowBarCollapsedWidth, nowBarCollapsedWidth2 + nowBarCollapsedWidth);
        ofFloat12.m788setDuration(1350L);
        ofFloat12.mInterpolator = new PathInterpolator(0.22f, 0.16f, 0.0f, 1.0f);
        animatorSet2.getClass();
        animatorSet2.new Builder(ofFloat12);
        AnimatorSet animatorSet3 = this.chargerAnimationSet;
        final ValueAnimator ofFloat13 = ValueAnimator.ofFloat(16.0f, 0.0f);
        ofFloat13.setDuration(1350L);
        ofFloat13.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        ofFloat13.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.power.ChargerNowBarView$addProgressbarWaveBlurAnimation$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                int i9 = ChargerNowBarView.$r8$clinit;
                LottieAnimationView batteryProgressbar3 = ChargerNowBarView.this.getBatteryProgressbar();
                ChargerAnimationConstants.INSTANCE.getClass();
                KeyPath keyPath = ChargerAnimationConstants.WAVE_KEY_PATH;
                Float f = LottieProperty.BLUR_RADIUS;
                final ValueAnimator valueAnimator = ofFloat13;
                batteryProgressbar3.addValueCallback(keyPath, (KeyPath) f, new SimpleLottieValueCallback() { // from class: com.android.systemui.power.ChargerNowBarView$addProgressbarWaveBlurAnimation$1$1.1
                    @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                    public final Object getValue() {
                        return (Float) ValueAnimator.this.getAnimatedValue();
                    }
                });
            }
        });
        animatorSet3.getClass();
        animatorSet3.new Builder(ofFloat13);
        getBatteryProgressbar().playAnimation();
        this.chargerAnimationSet.start();
        Slog.d("PowerUI.ChargerNowBarView", "addOnChargerAnimationEndHandler");
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new ChargerNowBarView$addOnChargerAnimationEndHandler$1(3300L, this, null), 3);
        Log.d("PowerUI.ChargerNowBarView", "Animation Started");
    }
}
