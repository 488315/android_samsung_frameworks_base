package com.android.systemui.settings.brightness;

import android.R;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.display.BrightnessInfo;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.settingslib.RestrictedLockUtils;
import com.android.systemui.settings.brightness.ToggleSlider;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BrightnessDetailSliderView extends FrameLayout implements ToggleSlider {
    public static final /* synthetic */ int $r8$clinit = 0;
    public String mAppUsingBrightness;
    public BrightnessAnimationIcon mBrightnessIcon;
    public final Context mContext;
    public int mDualSeekBarThreshold;
    public boolean mHighBrightnessModeEnter;
    public Toast mHighBrightnessModeToast;
    public boolean mIsSliderWarning;
    public boolean mIsThumbShowing;
    public ToggleSlider.Listener mListener;
    public boolean mOutdoorMode;
    public final PackageManager mPackageManager;
    public final PowerManager mPowerManager;
    public final AnonymousClass2 mSeekListener;
    public ToggleSeekBar mSlider;
    public Toast mSliderDisableToast;
    public boolean mSliderEnabled;
    public boolean mSystemBrightnessEnabled;
    public Drawable mThumb;
    public ValueAnimator mThumbAnimator;
    public int mThumbThreshold;
    public boolean mTracking;
    public Drawable mTransparentThumb;
    public SystemUIDialog mUsingHighBrightnessDialog;
    public boolean mUsingHighBrightnessDialogEnabled;

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.settings.brightness.BrightnessDetailSliderView$2] */
    public BrightnessDetailSliderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSliderEnabled = true;
        this.mSystemBrightnessEnabled = true;
        this.mIsThumbShowing = false;
        this.mSeekListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailSliderView.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                ToggleSeekBar toggleSeekBar;
                BrightnessDetailSliderView brightnessDetailSliderView = BrightnessDetailSliderView.this;
                int i2 = BrightnessDetailSliderView.$r8$clinit;
                if (!brightnessDetailSliderView.isAutoChecked$1()) {
                    final BrightnessDetailSliderView brightnessDetailSliderView2 = BrightnessDetailSliderView.this;
                    if (brightnessDetailSliderView2.mUsingHighBrightnessDialogEnabled && z) {
                        if (brightnessDetailSliderView2.mDualSeekBarThreshold <= i) {
                            if (brightnessDetailSliderView2.mContext.getResources().getInteger(R.integer.device_idle_light_idle_to_init_flex_ms) <= brightnessDetailSliderView2.mPowerManager.getMaximumScreenBrightnessSetting()) {
                                if (brightnessDetailSliderView2.mUsingHighBrightnessDialog == null) {
                                    brightnessDetailSliderView2.mUsingHighBrightnessDialog = new SystemUIDialog(brightnessDetailSliderView2.mContext, com.android.systemui.R.style.Theme_SystemUI_Dialog_Alert);
                                    brightnessDetailSliderView2.mUsingHighBrightnessDialog.setMessage(brightnessDetailSliderView2.mContext.getResources().getString(com.android.systemui.R.string.sec_brightness_using_high_brightness_dialog_message));
                                    brightnessDetailSliderView2.mUsingHighBrightnessDialog.setPositiveButton(com.android.systemui.R.string.sec_brightness_using_high_brightness_dialog_ok_button, null);
                                    brightnessDetailSliderView2.mUsingHighBrightnessDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailSliderView.4
                                        @Override // android.content.DialogInterface.OnDismissListener
                                        public final void onDismiss(DialogInterface dialogInterface) {
                                            BrightnessDetailSliderView brightnessDetailSliderView3 = BrightnessDetailSliderView.this;
                                            brightnessDetailSliderView3.mUsingHighBrightnessDialog = null;
                                            brightnessDetailSliderView3.mUsingHighBrightnessDialogEnabled = false;
                                            brightnessDetailSliderView3.setValue(brightnessDetailSliderView3.mDualSeekBarThreshold + 1);
                                        }
                                    });
                                    brightnessDetailSliderView2.mUsingHighBrightnessDialog.show();
                                    return;
                                }
                                return;
                            }
                            if (brightnessDetailSliderView2.mUsingHighBrightnessDialog == null) {
                                brightnessDetailSliderView2.mUsingHighBrightnessDialog = new SystemUIDialog(brightnessDetailSliderView2.mContext, com.android.systemui.R.style.Theme_SystemUI_Dialog_Alert);
                                String string = brightnessDetailSliderView2.mContext.getResources().getString(com.android.systemui.R.string.sec_brightness_using_high_brightness_dialog_message_support_hbm);
                                brightnessDetailSliderView2.mUsingHighBrightnessDialog.setTitle(brightnessDetailSliderView2.mContext.getResources().getString(com.android.systemui.R.string.sec_brightness_using_high_brightness_dialog_title));
                                brightnessDetailSliderView2.mUsingHighBrightnessDialog.setMessage(string);
                                brightnessDetailSliderView2.mUsingHighBrightnessDialog.setPositiveButton(com.android.systemui.R.string.sec_brightness_using_high_brightness_dialog_positive_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailSliderView$$ExternalSyntheticLambda1
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i3) {
                                        Settings.System.semPutIntForUser(BrightnessDetailSliderView.this.mContext.getContentResolver(), "screen_brightness_mode", 1, -2);
                                    }
                                });
                                brightnessDetailSliderView2.mUsingHighBrightnessDialog.setNegativeButton(com.android.systemui.R.string.sec_brightness_using_high_brightness_dialog_negative_button, new BrightnessDetailSliderView$$ExternalSyntheticLambda2());
                                brightnessDetailSliderView2.mUsingHighBrightnessDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailSliderView.3
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        BrightnessDetailSliderView brightnessDetailSliderView3 = BrightnessDetailSliderView.this;
                                        brightnessDetailSliderView3.mUsingHighBrightnessDialog = null;
                                        brightnessDetailSliderView3.mUsingHighBrightnessDialogEnabled = false;
                                        if (!brightnessDetailSliderView3.isAutoChecked$1()) {
                                            BrightnessDetailSliderView brightnessDetailSliderView4 = BrightnessDetailSliderView.this;
                                            brightnessDetailSliderView4.setValue(brightnessDetailSliderView4.mDualSeekBarThreshold + 1);
                                        }
                                        Settings.System.semPutIntForUser(BrightnessDetailSliderView.this.mContext.getContentResolver(), SettingsHelper.INDEX_MAX_BRIGHTNESS_DIALOG_SHOWN, 1, -2);
                                    }
                                });
                                brightnessDetailSliderView2.mUsingHighBrightnessDialog.show();
                                return;
                            }
                            return;
                        }
                        SystemUIDialog systemUIDialog = brightnessDetailSliderView2.mUsingHighBrightnessDialog;
                        if (systemUIDialog != null && systemUIDialog.isShowing()) {
                            BrightnessDetailSliderView.this.mUsingHighBrightnessDialog.dismiss();
                        }
                    }
                }
                BrightnessDetailSliderView brightnessDetailSliderView3 = BrightnessDetailSliderView.this;
                ToggleSeekBar toggleSeekBar2 = brightnessDetailSliderView3.mSlider;
                if (toggleSeekBar2 != null && toggleSeekBar2.getThumb() != null) {
                    int max = (brightnessDetailSliderView3.mSlider.getMax() * brightnessDetailSliderView3.mSlider.getHeight()) / brightnessDetailSliderView3.mSlider.getWidth();
                    brightnessDetailSliderView3.mThumbThreshold = max;
                    if (i < max) {
                        brightnessDetailSliderView3.mSlider.getThumb().setLevel((10000 * i) / brightnessDetailSliderView3.mThumbThreshold);
                    } else {
                        brightnessDetailSliderView3.mSlider.getThumb().setLevel(10000);
                    }
                }
                BrightnessDetailSliderView brightnessDetailSliderView4 = BrightnessDetailSliderView.this;
                boolean z2 = (brightnessDetailSliderView4.mDualSeekBarThreshold <= i || ((toggleSeekBar = brightnessDetailSliderView4.mSlider) != null && toggleSeekBar.getThumb() != null && i < brightnessDetailSliderView4.mThumbThreshold && brightnessDetailSliderView4.mSlider.getThumb().getAlpha() == 255)) && BrightnessDetailSliderView.this.mTracking;
                if (z2 != brightnessDetailSliderView4.mIsSliderWarning) {
                    brightnessDetailSliderView4.mIsSliderWarning = z2;
                    if (z2) {
                        ((TransitionDrawable) ((LayerDrawable) brightnessDetailSliderView4.mSlider.getProgressDrawable()).getDrawable(1)).startTransition(200);
                    } else {
                        ((TransitionDrawable) ((LayerDrawable) brightnessDetailSliderView4.mSlider.getProgressDrawable()).getDrawable(1)).reverseTransition(200);
                    }
                }
                brightnessDetailSliderView4.mBrightnessIcon.play(brightnessDetailSliderView4.mSlider.getProgress(), brightnessDetailSliderView4.mSlider.getMax());
                if (i == BrightnessDetailSliderView.this.mSlider.getMax() || i == 0) {
                    BrightnessDetailSliderView.this.mSlider.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                }
                BrightnessDetailSliderView brightnessDetailSliderView5 = BrightnessDetailSliderView.this;
                ToggleSlider.Listener listener = brightnessDetailSliderView5.mListener;
                if (listener != null) {
                    ((BrightnessController) listener).onChanged(i, brightnessDetailSliderView5.mTracking, false);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar) {
                BrightnessDetailSliderView brightnessDetailSliderView = BrightnessDetailSliderView.this;
                brightnessDetailSliderView.mTracking = true;
                if (seekBar != null) {
                    brightnessDetailSliderView.mIsThumbShowing = true;
                    brightnessDetailSliderView.mThumbAnimator.start();
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeekBar seekBar) {
                BrightnessDetailSliderView brightnessDetailSliderView = BrightnessDetailSliderView.this;
                brightnessDetailSliderView.mTracking = false;
                ToggleSlider.Listener listener = brightnessDetailSliderView.mListener;
                if (listener != null) {
                    ((BrightnessController) listener).onChanged(brightnessDetailSliderView.mSlider.getProgress(), false, true);
                }
                BrightnessDetailSliderView brightnessDetailSliderView2 = BrightnessDetailSliderView.this;
                brightnessDetailSliderView2.mIsThumbShowing = false;
                brightnessDetailSliderView2.mThumbAnimator.reverse();
                SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BRIGHTNESS_SLIDER, "location", "detail panel", SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
            }
        };
        this.mContext = context;
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mPackageManager = context.getPackageManager();
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final int getMax() {
        return this.mSlider.getMax();
    }

    @Override // android.view.View
    public final /* bridge */ /* synthetic */ Object getTag() {
        return null;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final int getValue() {
        return this.mSlider.getProgress();
    }

    public final boolean isAutoChecked$1() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness_mode", 0, -2) != 0;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final boolean mirrorTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSlider.setOnSeekBarChangeListener(this.mSeekListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mSlider.setOnSeekBarChangeListener(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v40, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v41 */
    /* JADX WARN: Type inference failed for: r0v48, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r4v12, types: [android.content.pm.PackageManager] */
    @Override // android.view.View
    public final void onFinishInflate() {
        ApplicationInfo applicationInfo;
        super.onFinishInflate();
        this.mBrightnessIcon = new BrightnessAnimationIcon((LottieAnimationView) requireViewById(com.android.systemui.R.id.brightness_icon));
        ToggleSeekBar toggleSeekBar = (ToggleSeekBar) requireViewById(com.android.systemui.R.id.detail_seekbar);
        this.mSlider = toggleSeekBar;
        boolean z = false;
        toggleSeekBar.setPaddingRelative(0, 0, 0, 0);
        this.mThumb = this.mContext.getDrawable(com.android.systemui.R.drawable.sec_qs_slider_thumb);
        Drawable drawable = this.mContext.getDrawable(com.android.systemui.R.drawable.sec_qs_slider_transparent_thumb);
        this.mTransparentThumb = drawable;
        this.mSlider.setThumb(drawable);
        ValueAnimator ofInt = ValueAnimator.ofInt(0, 255);
        this.mThumbAnimator = ofInt;
        ofInt.setDuration(200L);
        this.mThumbAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailSliderView$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                BrightnessDetailSliderView.this.mSlider.getThumb().setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        this.mThumbAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailSliderView.1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                BrightnessDetailSliderView brightnessDetailSliderView = BrightnessDetailSliderView.this;
                if (brightnessDetailSliderView.mIsThumbShowing) {
                    return;
                }
                brightnessDetailSliderView.mSlider.setThumb(brightnessDetailSliderView.mTransparentThumb);
                BrightnessDetailSliderView.this.mSlider.getThumb().setAlpha(0);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                BrightnessDetailSliderView brightnessDetailSliderView = BrightnessDetailSliderView.this;
                if (brightnessDetailSliderView.mIsThumbShowing) {
                    brightnessDetailSliderView.mSlider.setThumb(brightnessDetailSliderView.mThumb);
                    BrightnessDetailSliderView.this.mSlider.getThumb().setAlpha(0);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }
        });
        BrightnessInfo brightnessInfo = this.mContext.getDisplay().getBrightnessInfo();
        if (brightnessInfo != null) {
            this.mSliderEnabled = !brightnessInfo.isBrightnessOverrideByWindow;
            ?? r0 = brightnessInfo.screenBrightnessOverridePackageByWindow;
            try {
                applicationInfo = this.mPackageManager.getApplicationInfo(r0, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                applicationInfo = null;
            }
            if (applicationInfo != null) {
                r0 = this.mPackageManager.getApplicationLabel(applicationInfo);
            }
            String str = (String) r0;
            this.mAppUsingBrightness = str;
            boolean isEmpty = TextUtils.isEmpty(str);
            this.mSystemBrightnessEnabled = isEmpty;
            if (this.mSliderEnabled != isEmpty) {
                this.mSliderEnabled = isEmpty;
                ToggleSeekBar toggleSeekBar2 = this.mSlider;
                if (toggleSeekBar2 != null) {
                    toggleSeekBar2.setOnSeekBarChangeListener(isEmpty ? this.mSeekListener : null);
                }
            }
        }
        this.mUsingHighBrightnessDialogEnabled = Settings.System.getIntForUser(this.mContext.getContentResolver(), SettingsHelper.INDEX_MAX_BRIGHTNESS_DIALOG_SHOWN, 0, -2) == 0;
        boolean z2 = Settings.System.getIntForUser(this.mContext.getContentResolver(), "high_brightness_mode_pms_enter", 0, -2) != 0;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("updateHighBrightnessModeEnter : ", "BrightnessDetailSlider", z2);
        this.mHighBrightnessModeEnter = z2;
        boolean z3 = Settings.System.getIntForUser(this.mContext.getContentResolver(), "display_outdoor_mode", 0, -2) != 0;
        this.mOutdoorMode = z3;
        boolean z4 = !z3;
        this.mSlider.setEnabled(z4);
        StringBuilder sb = new StringBuilder("updateSliderEnabled() = ");
        sb.append(z4);
        sb.append(", mSystemBrightnessEnabled = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mSystemBrightnessEnabled, "BrightnessDetailSlider");
        if (!z3 && this.mSystemBrightnessEnabled) {
            z = true;
        }
        if (this.mSliderEnabled != z) {
            this.mSliderEnabled = z;
            ToggleSeekBar toggleSeekBar3 = this.mSlider;
            if (toggleSeekBar3 != null) {
                toggleSeekBar3.setOnSeekBarChangeListener(z ? this.mSeekListener : null);
            }
        }
        this.mSlider.setProgressDrawable(this.mContext.getDrawable(com.android.systemui.R.drawable.sec_brightness_detail_progress_drawable));
        this.mSlider.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailSliderView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                BrightnessDetailSliderView brightnessDetailSliderView = BrightnessDetailSliderView.this;
                if (brightnessDetailSliderView.mSystemBrightnessEnabled) {
                    if (brightnessDetailSliderView.mHighBrightnessModeEnter && brightnessDetailSliderView.isAutoChecked$1() && motionEvent.getAction() == 0) {
                        if (brightnessDetailSliderView.mHighBrightnessModeToast == null) {
                            Context context = brightnessDetailSliderView.mContext;
                            brightnessDetailSliderView.mHighBrightnessModeToast = Toast.makeText(context, context.getString(com.android.systemui.R.string.sec_brightness_slider_hbm_text), 0);
                        }
                        brightnessDetailSliderView.mHighBrightnessModeToast.show();
                    }
                    return false;
                }
                if (motionEvent.getAction() != 0) {
                    return true;
                }
                Toast toast = brightnessDetailSliderView.mSliderDisableToast;
                if (toast != null) {
                    toast.cancel();
                }
                Context context2 = brightnessDetailSliderView.mContext;
                Toast makeText = Toast.makeText(context2, context2.getString(com.android.systemui.R.string.sec_brightness_app_usage_toast, brightnessDetailSliderView.mAppUsingBrightness), 0);
                brightnessDetailSliderView.mSliderDisableToast = makeText;
                makeText.show();
                return true;
            }
        });
        this.mBrightnessIcon.init(this.mContext);
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setEnforcedAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        this.mSlider.setEnabled(enforcedAdmin == null && !this.mOutdoorMode);
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setMax(int i) {
        this.mSlider.setMax(i);
        this.mDualSeekBarThreshold = (int) Math.floor((this.mSlider.getMax() * this.mContext.getResources().getInteger(com.android.systemui.R.integer.sec_brightness_slider_warning_percent)) / 100.0d);
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setOnChangedListener(ToggleSlider.Listener listener) {
        this.mListener = listener;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setValue(int i) {
        this.mSlider.setProgress(i);
        this.mBrightnessIcon.play(i, this.mSlider.getMax());
        if (i >= this.mDualSeekBarThreshold) {
            ((TransitionDrawable) ((LayerDrawable) this.mSlider.getProgressDrawable()).getDrawable(1)).startTransition(200);
        }
    }
}
