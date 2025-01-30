package com.android.systemui.settings.brightness;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.RestrictedLockUtils;
import com.android.systemui.settings.brightness.ToggleSlider;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.gls.GlsIntent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BrightnessDetailSliderView extends FrameLayout implements ToggleSlider {
    public static boolean mUsingHighBrightnessDialogEnabled;
    public BrightnessAnimationIcon mBrightnessIcon;
    public final Context mContext;
    public int mDualSeekBarThreshold;
    public boolean mHighBrightnessModeEnter;
    public Toast mHighBrightnessModeToast;
    public boolean mIsSliderWarning;
    public ToggleSlider.Listener mListener;
    public boolean mOutdoorMode;
    public final PowerManager mPowerManager;
    public final C23912 mSeekListener;
    public ToggleSeekBar mSlider;
    public Toast mSliderDisableToast;
    public boolean mSliderEnabled;
    public boolean mSystemBrightnessEnabled;
    public boolean mTracking;
    public SystemUIDialog mUsingHighBrightnessDialog;

    /* renamed from: -$$Nest$misAutoChecked, reason: not valid java name */
    public static boolean m1642$$Nest$misAutoChecked(BrightnessDetailSliderView brightnessDetailSliderView) {
        return Settings.System.getIntForUser(brightnessDetailSliderView.mContext.getContentResolver(), "screen_brightness_mode", 0, -2) != 0;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.settings.brightness.BrightnessDetailSliderView$2] */
    public BrightnessDetailSliderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSliderEnabled = true;
        this.mSystemBrightnessEnabled = true;
        this.mSeekListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailSliderView.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (!BrightnessDetailSliderView.m1642$$Nest$misAutoChecked(BrightnessDetailSliderView.this) && BrightnessDetailSliderView.mUsingHighBrightnessDialogEnabled && z) {
                    final BrightnessDetailSliderView brightnessDetailSliderView = BrightnessDetailSliderView.this;
                    if (brightnessDetailSliderView.mDualSeekBarThreshold <= i) {
                        if (brightnessDetailSliderView.mContext.getResources().getInteger(R.integer.config_screenTimeoutOverride) <= brightnessDetailSliderView.mPowerManager.getMaximumScreenBrightnessSetting()) {
                            if (brightnessDetailSliderView.mUsingHighBrightnessDialog == null) {
                                brightnessDetailSliderView.mUsingHighBrightnessDialog = new SystemUIDialog(brightnessDetailSliderView.mContext, 2132018528);
                                brightnessDetailSliderView.mUsingHighBrightnessDialog.setMessage(brightnessDetailSliderView.mContext.getResources().getString(com.android.systemui.R.string.sec_brightness_using_high_brightness_dialog_message));
                                brightnessDetailSliderView.mUsingHighBrightnessDialog.setPositiveButton(com.android.systemui.R.string.sec_brightness_using_high_brightness_dialog_ok_button, null);
                                brightnessDetailSliderView.mUsingHighBrightnessDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailSliderView.4
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        BrightnessDetailSliderView brightnessDetailSliderView2 = BrightnessDetailSliderView.this;
                                        brightnessDetailSliderView2.mUsingHighBrightnessDialog = null;
                                        BrightnessDetailSliderView.mUsingHighBrightnessDialogEnabled = false;
                                        brightnessDetailSliderView2.setValue(brightnessDetailSliderView2.mDualSeekBarThreshold + 1);
                                    }
                                });
                                brightnessDetailSliderView.mUsingHighBrightnessDialog.show();
                                return;
                            }
                            return;
                        }
                        if (brightnessDetailSliderView.mUsingHighBrightnessDialog == null) {
                            brightnessDetailSliderView.mUsingHighBrightnessDialog = new SystemUIDialog(brightnessDetailSliderView.mContext, 2132018528);
                            String string = brightnessDetailSliderView.mContext.getResources().getString(com.android.systemui.R.string.sec_brightness_using_high_brightness_dialog_message_support_hbm);
                            brightnessDetailSliderView.mUsingHighBrightnessDialog.setTitle(brightnessDetailSliderView.mContext.getResources().getString(com.android.systemui.R.string.sec_brightness_using_high_brightness_dialog_title));
                            brightnessDetailSliderView.mUsingHighBrightnessDialog.setMessage(string);
                            brightnessDetailSliderView.mUsingHighBrightnessDialog.setPositiveButton(com.android.systemui.R.string.sec_brightness_using_high_brightness_dialog_positive_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailSliderView$$ExternalSyntheticLambda0
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i2) {
                                    Settings.System.semPutIntForUser(BrightnessDetailSliderView.this.mContext.getContentResolver(), "screen_brightness_mode", 1, -2);
                                }
                            });
                            brightnessDetailSliderView.mUsingHighBrightnessDialog.setNegativeButton(com.android.systemui.R.string.sec_brightness_using_high_brightness_dialog_negative_button, new BrightnessDetailSliderView$$ExternalSyntheticLambda1());
                            brightnessDetailSliderView.mUsingHighBrightnessDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailSliderView.3
                                @Override // android.content.DialogInterface.OnDismissListener
                                public final void onDismiss(DialogInterface dialogInterface) {
                                    BrightnessDetailSliderView brightnessDetailSliderView2 = BrightnessDetailSliderView.this;
                                    brightnessDetailSliderView2.mUsingHighBrightnessDialog = null;
                                    BrightnessDetailSliderView.mUsingHighBrightnessDialogEnabled = false;
                                    if (!BrightnessDetailSliderView.m1642$$Nest$misAutoChecked(brightnessDetailSliderView2)) {
                                        BrightnessDetailSliderView brightnessDetailSliderView3 = BrightnessDetailSliderView.this;
                                        brightnessDetailSliderView3.setValue(brightnessDetailSliderView3.mDualSeekBarThreshold + 1);
                                    }
                                    Settings.System.semPutIntForUser(BrightnessDetailSliderView.this.mContext.getContentResolver(), "shown_max_brightness_dialog", 1, -2);
                                }
                            });
                            brightnessDetailSliderView.mUsingHighBrightnessDialog.show();
                            return;
                        }
                        return;
                    }
                    SystemUIDialog systemUIDialog = brightnessDetailSliderView.mUsingHighBrightnessDialog;
                    if (systemUIDialog != null && systemUIDialog.isShowing()) {
                        BrightnessDetailSliderView.this.mUsingHighBrightnessDialog.dismiss();
                    }
                }
                BrightnessDetailSliderView brightnessDetailSliderView2 = BrightnessDetailSliderView.this;
                brightnessDetailSliderView2.setDualSeekBarResources(brightnessDetailSliderView2.mDualSeekBarThreshold <= i && brightnessDetailSliderView2.mTracking);
                BrightnessDetailSliderView brightnessDetailSliderView3 = BrightnessDetailSliderView.this;
                ToggleSlider.Listener listener = brightnessDetailSliderView3.mListener;
                if (listener != null) {
                    ((BrightnessController) listener).onChanged(i, brightnessDetailSliderView3.mTracking, false);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar) {
                BrightnessDetailSliderView.this.mTracking = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeekBar seekBar) {
                BrightnessDetailSliderView brightnessDetailSliderView = BrightnessDetailSliderView.this;
                brightnessDetailSliderView.mTracking = false;
                brightnessDetailSliderView.setDualSeekBarResources(false);
                BrightnessDetailSliderView brightnessDetailSliderView2 = BrightnessDetailSliderView.this;
                ToggleSlider.Listener listener = brightnessDetailSliderView2.mListener;
                if (listener != null) {
                    BrightnessController brightnessController = (BrightnessController) listener;
                    brightnessController.onChanged(brightnessDetailSliderView2.getValue(), brightnessDetailSliderView2.mTracking, true);
                }
                SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1009", GlsIntent.Extras.EXTRA_LOCATION, "detail panel", "QUICK_PANEL_LAYOUT");
            }
        };
        this.mContext = context;
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
    }

    @Override // android.view.View
    public final /* bridge */ /* synthetic */ Object getTag() {
        return null;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final int getValue() {
        return this.mSlider.getProgress();
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void initBrightnessIconResources() {
        this.mBrightnessIcon.initBrightnessIconResources(this.mContext);
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

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mBrightnessIcon = new BrightnessAnimationIcon((LottieAnimationView) requireViewById(com.android.systemui.R.id.brightness_icon));
        ToggleSeekBar toggleSeekBar = (ToggleSeekBar) requireViewById(com.android.systemui.R.id.detail_seekbar);
        this.mSlider = toggleSeekBar;
        toggleSeekBar.mAccessibilityLabel = getContentDescription().toString();
        Context context = this.mContext;
        this.mSliderDisableToast = Toast.makeText(context, context.getString(com.android.systemui.R.string.sec_brightness_slider_disabled_toast), 0);
        updateSystemBrightnessEnabled(Settings.System.getIntForUser(this.mContext.getContentResolver(), "pms_notification_panel_brightness_adjustment", 1, -2) != 0);
        mUsingHighBrightnessDialogEnabled = Settings.System.getIntForUser(this.mContext.getContentResolver(), "shown_max_brightness_dialog", 0, -2) == 0;
        updateHighBrightnessModeEnter(Settings.System.getIntForUser(this.mContext.getContentResolver(), "high_brightness_mode_pms_enter", 0, -2) != 0);
        updateOutdoorMode(Settings.System.getIntForUser(this.mContext.getContentResolver(), "display_outdoor_mode", 0, -2) != 0);
        this.mSlider.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailSliderView.1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                BrightnessDetailSliderView brightnessDetailSliderView = BrightnessDetailSliderView.this;
                if (brightnessDetailSliderView.mSystemBrightnessEnabled) {
                    if (brightnessDetailSliderView.mHighBrightnessModeEnter && BrightnessDetailSliderView.m1642$$Nest$misAutoChecked(brightnessDetailSliderView) && motionEvent.getAction() == 0) {
                        BrightnessDetailSliderView brightnessDetailSliderView2 = BrightnessDetailSliderView.this;
                        if (brightnessDetailSliderView2.mHighBrightnessModeToast == null) {
                            Context context2 = brightnessDetailSliderView2.mContext;
                            brightnessDetailSliderView2.mHighBrightnessModeToast = Toast.makeText(context2, context2.getString(com.android.systemui.R.string.sec_brightness_slider_hbm_text), 0);
                        }
                        brightnessDetailSliderView2.mHighBrightnessModeToast.show();
                    }
                    return false;
                }
                if (motionEvent.getAction() != 0) {
                    return true;
                }
                BrightnessDetailSliderView brightnessDetailSliderView3 = BrightnessDetailSliderView.this;
                Toast toast = brightnessDetailSliderView3.mSliderDisableToast;
                if (toast == null) {
                    Context context3 = brightnessDetailSliderView3.mContext;
                    brightnessDetailSliderView3.mSliderDisableToast = Toast.makeText(context3, context3.getString(com.android.systemui.R.string.sec_brightness_slider_disabled_toast), 0);
                } else {
                    toast.cancel();
                }
                brightnessDetailSliderView3.mSliderDisableToast.show();
                return true;
            }
        });
        setDualSeekBarResources(false);
        initBrightnessIconResources();
    }

    public final void setDualSeekBarResources(boolean z) {
        if (z != this.mIsSliderWarning) {
            this.mIsSliderWarning = z;
            this.mSlider.setProgressDrawable(this.mContext.getDrawable(z ? com.android.systemui.R.drawable.sec_brightness_progress_warning_drawable : com.android.systemui.R.drawable.sec_brightness_progress_drawable));
        }
        this.mBrightnessIcon.playBrightnessIconAnimation(getValue(), this.mSlider.getMax());
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setEnforcedAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        this.mSlider.setEnabled(enforcedAdmin == null && !this.mOutdoorMode);
        this.mSlider.mEnforcedAdmin = enforcedAdmin;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setMax(int i) {
        this.mSlider.setMax(i);
        this.mDualSeekBarThreshold = (int) Math.floor((this.mSlider.getMax() * this.mContext.getResources().getInteger(com.android.systemui.R.integer.sec_brightness_slider_warning_percent)) / 100.0d);
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setOnChangedListener(BrightnessController brightnessController) {
        this.mListener = brightnessController;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setValue(int i) {
        this.mSlider.setProgress(i);
        this.mBrightnessIcon.playBrightnessIconAnimation(i, this.mSlider.getMax());
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void updateHighBrightnessModeEnter(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("updateHighBrightnessModeEnter : ", z, "BrightnessDetailSlider");
        this.mHighBrightnessModeEnter = z;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void updateOutdoorMode(boolean z) {
        this.mOutdoorMode = z;
        boolean z2 = !z;
        this.mSlider.setEnabled(z2);
        StringBuilder sb = new StringBuilder("updateSliderEnabled() = ");
        sb.append(z2);
        sb.append(", mSystemBrightnessEnabled = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mSystemBrightnessEnabled, "BrightnessDetailSlider");
        boolean z3 = z2 && this.mSystemBrightnessEnabled;
        if (this.mSliderEnabled != z3) {
            this.mSliderEnabled = z3;
            ToggleSeekBar toggleSeekBar = this.mSlider;
            if (toggleSeekBar != null) {
                toggleSeekBar.setOnSeekBarChangeListener(z3 ? this.mSeekListener : null);
            }
        }
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void updateSystemBrightnessEnabled(boolean z) {
        this.mSystemBrightnessEnabled = z;
        if (this.mSliderEnabled != z) {
            this.mSliderEnabled = z;
            ToggleSeekBar toggleSeekBar = this.mSlider;
            if (toggleSeekBar != null) {
                toggleSeekBar.setOnSeekBarChangeListener(z ? this.mSeekListener : null);
            }
        }
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void updateUsingHighBrightnessDialog(boolean z) {
        mUsingHighBrightnessDialogEnabled = z;
    }

    @Override // android.view.View, com.android.systemui.settings.brightness.ToggleSlider
    public final String getTag() {
        return null;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void updateDualSeekBar() {
    }
}
