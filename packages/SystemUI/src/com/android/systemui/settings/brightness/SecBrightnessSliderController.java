package com.android.systemui.settings.brightness;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.provider.Settings;
import android.util.Log;
import android.view.ViewConfiguration;
import android.widget.SeekBar;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.SecBrightnessMirrorController;
import com.android.systemui.volume.util.ColorUtils;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecBrightnessSliderController {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = Reflection.getOrCreateKotlinClass(SecBrightnessSliderController.class).getSimpleName();
    public String appUsingBrightness;
    public final Drawable collapsedThumb;
    public final Drawable expandedThumb;
    public SystemUIDialog highBrightnessDialog;
    public boolean highBrightnessDialogEnabled;
    public Toast highBrightnessModeToast;
    public boolean isExpanded;
    public boolean isLongPressed;
    public boolean isSliderDisabled;
    public boolean isThumbShowing;
    public boolean outdoormode;
    public SecBrightnessMirrorController secBrightnessMirrorController;
    public final SeekBar.OnSeekBarChangeListener seekListener;
    public Toast sliderDisableToast;
    public final ValueAnimator thumbAnimator;
    public final int touchSlop;
    public boolean tracking;
    public final Drawable transparentThumb;
    public final BrightnessSliderView view;
    public final Lazy resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$resourcePicker$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        }
    });
    public final Lazy brightnessIcon$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$brightnessIcon$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new BrightnessAnimationIcon((LottieAnimationView) SecBrightnessSliderController.this.view.findViewById(R.id.brightness_icon));
        }
    });
    public final Lazy packageManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$packageManager$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return SecBrightnessSliderController.this.view.getContext().getPackageManager();
        }
    });
    public final PointF downPoint = new PointF();
    public boolean sliderEnabled = true;
    public int thumbThreshold = 26;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SecBrightnessSliderController(BrightnessSliderView brightnessSliderView, SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.view = brightnessSliderView;
        this.seekListener = onSeekBarChangeListener;
        this.touchSlop = ViewConfiguration.get(brightnessSliderView.getContext()).getScaledTouchSlop();
        this.collapsedThumb = brightnessSliderView.getContext().getDrawable(R.drawable.sec_qs_slider_thumb_collapsed);
        this.expandedThumb = brightnessSliderView.getContext().getDrawable(R.drawable.sec_qs_slider_thumb);
        this.transparentThumb = brightnessSliderView.getContext().getDrawable(R.drawable.sec_qs_slider_transparent_thumb);
        ValueAnimator ofInt = ValueAnimator.ofInt(0, 255);
        ofInt.setDuration(200L);
        SecBrightnessSliderView secBrightnessSliderView = brightnessSliderView.mSecBrightnessSliderView;
        final ToggleSeekBar slider = secBrightnessSliderView != null ? secBrightnessSliderView.getSlider() : null;
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$thumbAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ToggleSeekBar toggleSeekBar = ToggleSeekBar.this;
                Drawable thumb = toggleSeekBar != null ? toggleSeekBar.getThumb() : null;
                if (thumb == null) {
                    return;
                }
                thumb.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        ofInt.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$thumbAnimator$1$2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SecBrightnessSliderController secBrightnessSliderController = SecBrightnessSliderController.this;
                if (secBrightnessSliderController.isThumbShowing) {
                    return;
                }
                ToggleSeekBar toggleSeekBar = slider;
                if (toggleSeekBar != null) {
                    toggleSeekBar.setThumb(secBrightnessSliderController.transparentThumb);
                }
                ToggleSeekBar toggleSeekBar2 = slider;
                Drawable thumb = toggleSeekBar2 != null ? toggleSeekBar2.getThumb() : null;
                if (thumb == null) {
                    return;
                }
                thumb.setAlpha(0);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                SecBrightnessSliderController secBrightnessSliderController = SecBrightnessSliderController.this;
                if (secBrightnessSliderController.isThumbShowing) {
                    ToggleSeekBar toggleSeekBar = slider;
                    if (toggleSeekBar != null) {
                        toggleSeekBar.setThumb(secBrightnessSliderController.isExpanded ? secBrightnessSliderController.expandedThumb : secBrightnessSliderController.collapsedThumb);
                    }
                    ToggleSeekBar toggleSeekBar2 = slider;
                    Drawable thumb = toggleSeekBar2 != null ? toggleSeekBar2.getThumb() : null;
                    if (thumb == null) {
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
        this.thumbAnimator = ofInt;
    }

    public final boolean isAutoChecked() {
        return Settings.System.getIntForUser(this.view.getContext().getContentResolver(), "screen_brightness_mode", 0, -2) != 0;
    }

    public final void showHighBrightnessModeToast() {
        Toast toast = this.highBrightnessModeToast;
        if (toast != null) {
            toast.cancel();
        }
        Context context = this.view.getContext();
        Toast makeText = context != null ? Toast.makeText(context, context.getString(R.string.sec_brightness_slider_hbm_text), 0) : null;
        this.highBrightnessModeToast = makeText;
        if (makeText != null) {
            makeText.show();
        }
    }

    public final void showSliderDisabledToast() {
        Toast toast = this.sliderDisableToast;
        if (toast != null) {
            toast.cancel();
        }
        Context context = this.view.getContext();
        Toast makeText = context != null ? Toast.makeText(context, context.getString(R.string.sec_brightness_app_usage_toast, this.appUsingBrightness), 0) : null;
        this.sliderDisableToast = makeText;
        if (makeText != null) {
            makeText.show();
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

    public final void updateSystemBrightnessEnabled(boolean z) {
        ToggleSeekBar slider;
        boolean z2 = this.tracking;
        String str = TAG;
        if (z2) {
            Log.d(str, "Can't using updateSystemBrightnessEnabled() now.");
            if (!z) {
                return;
            }
        }
        if (this.sliderEnabled != z) {
            Log.d(str, "updateSystemBrightnessEnabled: ");
            this.sliderEnabled = z;
            SecBrightnessSliderView secBrightnessSliderView = this.view.mSecBrightnessSliderView;
            if (secBrightnessSliderView == null || (slider = secBrightnessSliderView.getSlider()) == null) {
                return;
            }
            if (this.sliderEnabled) {
                slider.setOnSeekBarChangeListener(this.seekListener);
            } else {
                slider.setOnSeekBarChangeListener(null);
            }
        }
    }
}
