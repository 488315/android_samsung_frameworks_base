package com.android.systemui.settings.brightness;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.display.BrightnessInfo;
import android.provider.Settings;
import android.view.ViewConfiguration;
import android.widget.SeekBar;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.R;
import com.android.systemui.settings.brightness.SecBrightnessSliderController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.SecBrightnessMirrorController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.util.ColorUtils;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes3.dex */
public final class SecBrightnessSliderController {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = Reflection.getOrCreateKotlinClass(SecBrightnessSliderController.class).getSimpleName();
    public String appUsingBrightness;
    public final Lazy brightnessIcon$delegate;
    public final Drawable collapsedThumb;
    public final Drawable expandedThumb;
    public SystemUIDialog highBrightnessDialog;
    public boolean highBrightnessDialogEnabled;
    public Toast highBrightnessModeToast;
    public boolean isAdaptiveBrightness;
    public boolean isExpanded;
    public boolean isLongPressed;
    public boolean isSliderDisabled;
    public boolean isThumbShowing;
    public boolean outdoormode;
    public final Lazy packageManager$delegate;
    public SecBrightnessMirrorController secBrightnessMirrorController;
    public final SeekBar.OnSeekBarChangeListener seekListener;
    public Toast sliderDisableToast;
    public final ValueAnimator thumbAnimator;
    public final int touchSlop;
    public boolean tracking;
    public final Drawable transparentThumb;
    public final BrightnessSliderView view;
    public final Lazy resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new SecBrightnessSliderController$$ExternalSyntheticLambda0());
    public final PointF downPoint = new PointF();
    public boolean sliderEnabled = true;
    public int thumbThreshold = 26;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public SecBrightnessSliderController(BrightnessSliderView brightnessSliderView, SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.view = brightnessSliderView;
        this.seekListener = onSeekBarChangeListener;
        final int i = 0;
        this.brightnessIcon$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$$ExternalSyntheticLambda1
            public final /* synthetic */ SecBrightnessSliderController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SecBrightnessSliderController secBrightnessSliderController = this.f$0;
                switch (i) {
                    case 0:
                        SecBrightnessSliderController.Companion companion = SecBrightnessSliderController.Companion;
                        return new BrightnessAnimationIcon((LottieAnimationView) secBrightnessSliderController.view.findViewById(R.id.brightness_icon));
                    default:
                        return secBrightnessSliderController.view.getContext().getPackageManager();
                }
            }
        });
        final int i2 = 1;
        this.packageManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$$ExternalSyntheticLambda1
            public final /* synthetic */ SecBrightnessSliderController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SecBrightnessSliderController secBrightnessSliderController = this.f$0;
                switch (i2) {
                    case 0:
                        SecBrightnessSliderController.Companion companion = SecBrightnessSliderController.Companion;
                        return new BrightnessAnimationIcon((LottieAnimationView) secBrightnessSliderController.view.findViewById(R.id.brightness_icon));
                    default:
                        return secBrightnessSliderController.view.getContext().getPackageManager();
                }
            }
        });
        this.touchSlop = ViewConfiguration.get(brightnessSliderView.getContext()).getScaledTouchSlop();
        this.isAdaptiveBrightness = true;
        this.highBrightnessDialogEnabled = Settings.System.getIntForUser(brightnessSliderView.getContext().getContentResolver(), SettingsHelper.INDEX_MAX_BRIGHTNESS_DIALOG_SHOWN, 0, -2) == 0;
        this.isAdaptiveBrightness = Settings.System.getIntForUser(brightnessSliderView.getContext().getContentResolver(), "screen_brightness_mode", 0, -2) == 1;
        this.collapsedThumb = brightnessSliderView.getContext().getDrawable(R.drawable.sec_qs_slider_thumb_collapsed);
        this.expandedThumb = brightnessSliderView.getContext().getDrawable(R.drawable.sec_qs_slider_thumb);
        this.transparentThumb = brightnessSliderView.getContext().getDrawable(R.drawable.sec_qs_slider_transparent_thumb);
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 255);
        valueAnimatorOfInt.setDuration(200L);
        SecBrightnessSliderView secBrightnessSliderView = brightnessSliderView.mSecBrightnessSliderView;
        final ToggleSeekBar slider = secBrightnessSliderView != null ? secBrightnessSliderView.getSlider() : null;
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$thumbAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Drawable thumb;
                ToggleSeekBar toggleSeekBar = slider;
                if (toggleSeekBar == null || (thumb = toggleSeekBar.getThumb()) == null) {
                    return;
                }
                thumb.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        valueAnimatorOfInt.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$thumbAnimator$1$2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Drawable thumb;
                SecBrightnessSliderController secBrightnessSliderController = this.this$0;
                if (secBrightnessSliderController.isThumbShowing) {
                    return;
                }
                ToggleSeekBar toggleSeekBar = slider;
                if (toggleSeekBar != null) {
                    toggleSeekBar.setThumb(secBrightnessSliderController.transparentThumb);
                }
                ToggleSeekBar toggleSeekBar2 = slider;
                if (toggleSeekBar2 == null || (thumb = toggleSeekBar2.getThumb()) == null) {
                    return;
                }
                thumb.setAlpha(0);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                Drawable thumb;
                SecBrightnessSliderController secBrightnessSliderController = this.this$0;
                if (secBrightnessSliderController.isThumbShowing) {
                    ToggleSeekBar toggleSeekBar = slider;
                    if (toggleSeekBar != null) {
                        toggleSeekBar.setThumb(secBrightnessSliderController.expandedThumb);
                    }
                    ToggleSeekBar toggleSeekBar2 = slider;
                    if (toggleSeekBar2 == null || (thumb = toggleSeekBar2.getThumb()) == null) {
                        return;
                    }
                    thumb.setAlpha(0);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }
        });
        this.thumbAnimator = valueAnimatorOfInt;
    }

    public final boolean isSliderEnabled() throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo;
        Lazy lazy = this.packageManager$delegate;
        BrightnessInfo brightnessInfo = this.view.getContext().getDisplay().getBrightnessInfo();
        if (brightnessInfo == null) {
            return false;
        }
        this.sliderEnabled = !brightnessInfo.isBrightnessOverrideByWindow;
        String string = brightnessInfo.screenBrightnessOverridePackageByWindow;
        try {
            applicationInfo = ((PackageManager) lazy.getValue()).getApplicationInfo(string, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            string = ((PackageManager) lazy.getValue()).getApplicationLabel(applicationInfo).toString();
        }
        this.appUsingBrightness = string;
        return this.sliderEnabled;
    }

    public final void showHighBrightnessModeToast() {
        Toast toast = this.highBrightnessModeToast;
        if (toast != null) {
            toast.cancel();
        }
        Context context = this.view.getContext();
        Toast toastMakeText = context != null ? Toast.makeText(context, context.getString(R.string.sec_brightness_slider_hbm_text), 0) : null;
        this.highBrightnessModeToast = toastMakeText;
        if (toastMakeText != null) {
            toastMakeText.show();
        }
    }

    public final void showSliderDisabledToast() {
        Toast toast = this.sliderDisableToast;
        if (toast != null) {
            toast.cancel();
        }
        Context context = this.view.getContext();
        Toast toastMakeText = context != null ? Toast.makeText(context, context.getString(R.string.sec_brightness_app_usage_toast, this.appUsingBrightness), 0) : null;
        this.sliderDisableToast = toastMakeText;
        if (toastMakeText != null) {
            toastMakeText.show();
        }
    }

    public final void updateSliderDrawable() {
        ToggleSeekBar slider;
        Context context;
        BrightnessSliderView brightnessSliderView = this.view;
        SecBrightnessSliderView secBrightnessSliderView = brightnessSliderView.mSecBrightnessSliderView;
        if (secBrightnessSliderView != null && (slider = secBrightnessSliderView.getSlider()) != null) {
            TransitionDrawable transitionDrawable = (TransitionDrawable) ((LayerDrawable) slider.getProgressDrawable()).findDrawableByLayerId(android.R.id.progress);
            int[] iArr = {slider.getContext().getColor(R.color.tw_progress_color_control_activated_start), slider.getContext().getColor(R.color.tw_progress_color_control_activated_end)};
            Drawable drawable = transitionDrawable.getDrawable(0);
            Drawable drawable2 = transitionDrawable.getDrawable(1);
            if ((drawable instanceof ScaleDrawable) && (drawable2 instanceof ScaleDrawable)) {
                ((GradientDrawable) ((ScaleDrawable) drawable).getDrawable()).setColors(iArr);
                Drawable drawable3 = ((ScaleDrawable) drawable2).getDrawable();
                if (drawable3 != null) {
                    ToggleSeekBar slider2 = secBrightnessSliderView.getSlider();
                    drawable3.setTintList((slider2 == null || (context = slider2.getContext()) == null) ? null : ColorUtils.getSingleColorStateList(R.color.tw_progress_color_control_activated_end, context));
                }
            }
            secBrightnessSliderView.isGradient = false;
            secBrightnessSliderView.setDualSeekBarResources(secBrightnessSliderView.dualSeekBarThreshold <= slider.getProgress());
            slider.invalidate();
        }
        Drawable drawable4 = this.collapsedThumb;
        if (drawable4 != null) {
            drawable4.setTintList(ColorUtils.getSingleColorStateList(R.color.tw_progress_color_thumb, brightnessSliderView.getContext()));
        }
        Drawable drawable5 = this.expandedThumb;
        if (drawable5 != null) {
            drawable5.setTintList(ColorUtils.getSingleColorStateList(R.color.tw_progress_color_thumb, brightnessSliderView.getContext()));
        }
    }

    public final void updateSliderHeight(int i) {
        ToggleSeekBar slider;
        SecBrightnessSliderView secBrightnessSliderView = this.view.mSecBrightnessSliderView;
        if (secBrightnessSliderView == null || (slider = secBrightnessSliderView.getSlider()) == null) {
            return;
        }
        slider.setMaxHeight(i);
        slider.setPaddingRelative(0, 0, 0, 0);
        Unit unit = Unit.INSTANCE;
    }
}
