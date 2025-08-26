package com.android.systemui.settings.brightness;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.RestrictedLockUtils;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.haptics.slider.HapticSlider;
import com.android.systemui.haptics.slider.HapticSliderPlugin;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.settings.brightness.ToggleSlider;
import com.android.systemui.settings.brightness.ui.BrightnessWarningToast;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.SecBrightnessMirrorController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.time.SystemClock;
import com.google.android.msdl.domain.MSDLPlayer;
import kotlin.jvm.internal.Ref$IntRef;

/* loaded from: classes3.dex */
public class BrightnessSliderController extends ViewController implements ToggleSlider {
    public final ActivityStarter mActivityStarter;
    public final HapticSliderPlugin mBrightnessSliderHapticPlugin;
    public final FalsingManager mFalsingManager;
    public ToggleSlider.Listener mListener;
    public ToggleSlider mMirror;
    public MirrorController mMirrorController;
    public final AnonymousClass1 mOnInterceptListener;
    public final SecBrightnessSliderController mSecBrightnessSliderController;
    public final AnonymousClass2 mSeekListener;
    public boolean mTracking;
    public final UiEventLogger mUiEventLogger;

    /* renamed from: com.android.systemui.settings.brightness.BrightnessSliderController$1, reason: invalid class name */
    public class AnonymousClass1 implements Gefingerpoken {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 1 && actionMasked != 3) {
                return false;
            }
            BrightnessSliderController.this.mFalsingManager.isFalseTouch(10);
            return false;
        }
    }

    public class BrightnessSliderControllerFactory implements Factory {
        public final ActivityStarter mActivityStarter;
        public final BrightnessWarningToast mBrightnessWarningToast;
        public final FalsingManager mFalsingManager;
        public final MSDLPlayer mMSDLPlayer;
        public final SystemClock mSystemClock;
        public final UiEventLogger mUiEventLogger;
        public final VibratorHelper mVibratorHelper;

        public BrightnessSliderControllerFactory(FalsingManager falsingManager, UiEventLogger uiEventLogger, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SystemClock systemClock, ActivityStarter activityStarter, BrightnessWarningToast brightnessWarningToast) {
            this.mFalsingManager = falsingManager;
            this.mUiEventLogger = uiEventLogger;
            this.mVibratorHelper = vibratorHelper;
            this.mSystemClock = systemClock;
            this.mActivityStarter = activityStarter;
            this.mMSDLPlayer = mSDLPlayer;
            this.mBrightnessWarningToast = brightnessWarningToast;
        }

        public final BrightnessSliderController create(Context context, ViewGroup viewGroup) {
            SecBrightnessSliderController.Companion.getClass();
            BrightnessSliderView brightnessSliderView = (BrightnessSliderView) LayoutInflater.from(context).inflate(R.layout.sec_quick_settings_brightness_dialog, viewGroup, false);
            HapticSliderPlugin hapticSliderPlugin = new HapticSliderPlugin(this.mVibratorHelper, this.mMSDLPlayer, this.mSystemClock, new HapticSlider.SeekBar((SeekBar) brightnessSliderView.requireViewById(R.id.slider)));
            return new BrightnessSliderController(brightnessSliderView, this.mFalsingManager, this.mUiEventLogger, hapticSliderPlugin, this.mActivityStarter, this.mBrightnessWarningToast);
        }
    }

    public interface Factory {
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [android.widget.SeekBar$OnSeekBarChangeListener, com.android.systemui.settings.brightness.BrightnessSliderController$2] */
    public BrightnessSliderController(BrightnessSliderView brightnessSliderView, FalsingManager falsingManager, UiEventLogger uiEventLogger, HapticSliderPlugin hapticSliderPlugin, ActivityStarter activityStarter, BrightnessWarningToast brightnessWarningToast) {
        super(brightnessSliderView);
        this.mOnInterceptListener = new AnonymousClass1();
        ?? r1 = new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessSliderController.2
            /* JADX WARN: Removed duplicated region for block: B:55:0x013f  */
            /* JADX WARN: Removed duplicated region for block: B:58:0x0147  */
            /* JADX WARN: Removed duplicated region for block: B:59:0x014b  */
            /* JADX WARN: Removed duplicated region for block: B:62:0x0157  */
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onProgressChanged(SeekBar seekBar, int i, boolean z) throws Resources.NotFoundException {
                ToggleSeekBar toggleSeekBar;
                Drawable thumb;
                BrightnessSliderController brightnessSliderController = BrightnessSliderController.this;
                ToggleSlider.Listener listener = brightnessSliderController.mListener;
                boolean z2 = false;
                if (listener != null) {
                    ((BrightnessController) listener).onChanged(i, brightnessSliderController.mTracking, false);
                }
                BrightnessSliderController brightnessSliderController2 = BrightnessSliderController.this;
                final SecBrightnessSliderController secBrightnessSliderController = brightnessSliderController2.mSecBrightnessSliderController;
                if (secBrightnessSliderController != null) {
                    int max = ((BrightnessSliderView) ((ViewController) brightnessSliderController2).mView).mSlider.getMax();
                    boolean z3 = secBrightnessSliderController.isAdaptiveBrightness;
                    BrightnessSliderView brightnessSliderView2 = secBrightnessSliderController.view;
                    if (!z3 && secBrightnessSliderController.highBrightnessDialogEnabled && z) {
                        SecBrightnessSliderView secBrightnessSliderView = brightnessSliderView2.mSecBrightnessSliderView;
                        secBrightnessSliderView.getClass();
                        if (secBrightnessSliderView.dualSeekBarThreshold <= i) {
                            if (brightnessSliderView2.mSecBrightnessSliderView != null) {
                                Ref$IntRef ref$IntRef = new Ref$IntRef();
                                ref$IntRef.element = 255;
                                Context context = brightnessSliderView2.getContext();
                                int integer = context.getResources().getInteger(android.R.integer.device_idle_light_idle_to_init_flex_ms);
                                PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
                                Integer numValueOf = powerManager != null ? Integer.valueOf(powerManager.getMaximumScreenBrightnessSetting()) : null;
                                numValueOf.getClass();
                                int iIntValue = numValueOf.intValue();
                                ref$IntRef.element = iIntValue;
                                if (secBrightnessSliderController.highBrightnessDialog != null) {
                                    return;
                                }
                                if (integer <= iIntValue) {
                                    SystemUIDialog systemUIDialog = new SystemUIDialog(brightnessSliderView2.getContext(), R.style.Theme_SystemUI_Dialog_Alert);
                                    secBrightnessSliderController.highBrightnessDialog = systemUIDialog;
                                    systemUIDialog.setMessage(brightnessSliderView2.getContext().getResources().getString(R.string.sec_brightness_using_high_brightness_dialog_message));
                                    systemUIDialog.setPositiveButton(R.string.sec_brightness_using_high_brightness_dialog_ok_button, null);
                                    systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$showUsingHighBrightnessDialog$3$1
                                        @Override // android.content.DialogInterface.OnDismissListener
                                        public final void onDismiss(DialogInterface dialogInterface) {
                                            SecBrightnessSliderController secBrightnessSliderController2 = secBrightnessSliderController;
                                            secBrightnessSliderController2.highBrightnessDialog = null;
                                            secBrightnessSliderController2.highBrightnessDialogEnabled = false;
                                            BrightnessSliderView brightnessSliderView3 = secBrightnessSliderController2.view;
                                            SecBrightnessSliderView secBrightnessSliderView2 = brightnessSliderView3.mSecBrightnessSliderView;
                                            if (secBrightnessSliderView2 != null) {
                                                brightnessSliderView3.mSlider.setProgress(secBrightnessSliderView2.dualSeekBarThreshold + 1);
                                            }
                                            Settings.System.semPutIntForUser(secBrightnessSliderController.view.getContext().getContentResolver(), SettingsHelper.INDEX_MAX_BRIGHTNESS_DIALOG_SHOWN, 1, -2);
                                        }
                                    });
                                    systemUIDialog.show();
                                    return;
                                }
                                SystemUIDialog systemUIDialog2 = new SystemUIDialog(brightnessSliderView2.getContext(), R.style.Theme_SystemUI_Dialog_Alert);
                                secBrightnessSliderController.highBrightnessDialog = systemUIDialog2;
                                systemUIDialog2.setMessage(brightnessSliderView2.getContext().getResources().getString(R.string.sec_brightness_using_high_brightness_dialog_message_support_hbm));
                                systemUIDialog2.setTitle(brightnessSliderView2.getContext().getResources().getString(R.string.sec_brightness_using_high_brightness_dialog_title));
                                systemUIDialog2.setPositiveButton(R.string.sec_brightness_using_high_brightness_dialog_positive_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$showUsingHighBrightnessDialog$2$1
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i2) {
                                        Settings.System.semPutIntForUser(secBrightnessSliderController.view.getContext().getContentResolver(), "screen_brightness_mode", 1, -2);
                                    }
                                });
                                systemUIDialog2.setNegativeButton(R.string.sec_brightness_using_high_brightness_dialog_negative_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$showUsingHighBrightnessDialog$2$2
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i2) {
                                    }
                                });
                                systemUIDialog2.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$showUsingHighBrightnessDialog$2$3
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        BrightnessSliderView brightnessSliderView3;
                                        SecBrightnessSliderView secBrightnessSliderView2;
                                        SecBrightnessSliderController secBrightnessSliderController2 = secBrightnessSliderController;
                                        secBrightnessSliderController2.highBrightnessDialog = null;
                                        secBrightnessSliderController2.highBrightnessDialogEnabled = false;
                                        if (!secBrightnessSliderController2.isAdaptiveBrightness && (secBrightnessSliderView2 = (brightnessSliderView3 = secBrightnessSliderController2.view).mSecBrightnessSliderView) != null) {
                                            brightnessSliderView3.mSlider.setProgress(secBrightnessSliderView2.dualSeekBarThreshold + 1);
                                        }
                                        Settings.System.semPutIntForUser(secBrightnessSliderController.view.getContext().getContentResolver(), SettingsHelper.INDEX_MAX_BRIGHTNESS_DIALOG_SHOWN, 1, -2);
                                    }
                                });
                                systemUIDialog2.show();
                                return;
                            }
                            return;
                        }
                        SystemUIDialog systemUIDialog3 = secBrightnessSliderController.highBrightnessDialog;
                        if (systemUIDialog3 != null) {
                            if (systemUIDialog3.isShowing()) {
                                systemUIDialog3.dismiss();
                            }
                            SecBrightnessMirrorController secBrightnessMirrorController = secBrightnessSliderController.secBrightnessMirrorController;
                            if (secBrightnessMirrorController != null) {
                                secBrightnessMirrorController.showMirror();
                                secBrightnessMirrorController.setLocationAndSize(brightnessSliderView2);
                            }
                        }
                    }
                    if (secBrightnessSliderController.secBrightnessMirrorController != null && (i == max || i == 0)) {
                        brightnessSliderView2.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                    }
                    SecBrightnessSliderView secBrightnessSliderView2 = brightnessSliderView2.mSecBrightnessSliderView;
                    if (secBrightnessSliderView2 != null) {
                        if (secBrightnessSliderView2.dualSeekBarThreshold <= i) {
                            z2 = true;
                            secBrightnessSliderView2.setDualSeekBarResources(z2);
                            int i2 = secBrightnessSliderController.thumbThreshold;
                            int i3 = i >= i2 ? (i * 10000) / i2 : 10000;
                            toggleSeekBar = (ToggleSeekBar) secBrightnessSliderView2.sliderSupplier.get();
                            if (toggleSeekBar != null) {
                                Drawable thumb2 = toggleSeekBar.getThumb();
                                ScaleDrawable scaleDrawable = thumb2 instanceof ScaleDrawable ? (ScaleDrawable) thumb2 : null;
                                if (scaleDrawable != null) {
                                    scaleDrawable.setLevel(i3);
                                }
                            }
                        } else {
                            if (i < secBrightnessSliderController.thumbThreshold) {
                                ToggleSeekBar slider = secBrightnessSliderView2.getSlider();
                                if (((slider == null || (thumb = slider.getThumb()) == null) ? 0 : thumb.getAlpha()) == 255) {
                                }
                            }
                            secBrightnessSliderView2.setDualSeekBarResources(z2);
                            int i22 = secBrightnessSliderController.thumbThreshold;
                            if (i >= i22) {
                            }
                            toggleSeekBar = (ToggleSeekBar) secBrightnessSliderView2.sliderSupplier.get();
                            if (toggleSeekBar != null) {
                            }
                        }
                    }
                    SecBrightnessMirrorController secBrightnessMirrorController2 = secBrightnessSliderController.secBrightnessMirrorController;
                    if (secBrightnessMirrorController2 != null) {
                        BrightnessSliderController brightnessSliderController3 = secBrightnessMirrorController2.toggleSliderController;
                        if (brightnessSliderController3 != null) {
                            brightnessSliderController3.setValue(i);
                        }
                        BrightnessAnimationIcon brightnessAnimationIcon = secBrightnessMirrorController2.brightnessIcon;
                        if (brightnessAnimationIcon != null) {
                            brightnessAnimationIcon.play(i, max);
                        }
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar) {
                BrightnessSliderController brightnessSliderController = BrightnessSliderController.this;
                brightnessSliderController.mTracking = true;
                brightnessSliderController.mUiEventLogger.log(BrightnessSliderEvent.BRIGHTNESS_SLIDER_STARTED_TRACKING_TOUCH);
                BrightnessSliderController brightnessSliderController2 = BrightnessSliderController.this;
                SecBrightnessSliderController secBrightnessSliderController = brightnessSliderController2.mSecBrightnessSliderController;
                if (secBrightnessSliderController != null) {
                    secBrightnessSliderController.tracking = brightnessSliderController2.mTracking;
                }
                ToggleSlider.Listener listener = brightnessSliderController2.mListener;
                if (listener != null) {
                    BrightnessController brightnessController = (BrightnessController) listener;
                    brightnessController.onChanged(brightnessSliderController2.getValue(), brightnessSliderController2.mTracking, false);
                    HapticSliderPlugin hapticSliderPlugin2 = BrightnessSliderController.this.mBrightnessSliderHapticPlugin;
                    if (hapticSliderPlugin2.isTracking()) {
                        hapticSliderPlugin2.sliderEventProducer.onStartTracking(true);
                    }
                }
                MirrorController mirrorController = BrightnessSliderController.this.mMirrorController;
                if (mirrorController != null) {
                    mirrorController.showMirror();
                    BrightnessSliderController brightnessSliderController3 = BrightnessSliderController.this;
                    brightnessSliderController3.mMirrorController.setLocationAndSize(((ViewController) brightnessSliderController3).mView);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeekBar seekBar) {
                BrightnessSliderController brightnessSliderController = BrightnessSliderController.this;
                brightnessSliderController.mTracking = false;
                brightnessSliderController.mUiEventLogger.log(BrightnessSliderEvent.BRIGHTNESS_SLIDER_STOPPED_TRACKING_TOUCH);
                BrightnessSliderController brightnessSliderController2 = BrightnessSliderController.this;
                ToggleSlider.Listener listener = brightnessSliderController2.mListener;
                if (listener != null) {
                    BrightnessController brightnessController = (BrightnessController) listener;
                    brightnessController.onChanged(brightnessSliderController2.getValue(), brightnessSliderController2.mTracking, true);
                    HapticSliderPlugin hapticSliderPlugin2 = BrightnessSliderController.this.mBrightnessSliderHapticPlugin;
                    if (hapticSliderPlugin2.isTracking()) {
                        hapticSliderPlugin2.sliderEventProducer.onStopTracking(true);
                    }
                }
                BrightnessSliderController brightnessSliderController3 = BrightnessSliderController.this;
                SecBrightnessSliderController secBrightnessSliderController = brightnessSliderController3.mSecBrightnessSliderController;
                if (secBrightnessSliderController != null) {
                    secBrightnessSliderController.tracking = brightnessSliderController3.mTracking;
                    SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BRIGHTNESS_SLIDER, "location", "quick panel", SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
                }
                MirrorController mirrorController = BrightnessSliderController.this.mMirrorController;
                if (mirrorController != null) {
                    mirrorController.hideMirror();
                }
            }
        };
        this.mSeekListener = r1;
        this.mFalsingManager = falsingManager;
        this.mUiEventLogger = uiEventLogger;
        this.mBrightnessSliderHapticPlugin = hapticSliderPlugin;
        this.mActivityStarter = activityStarter;
        this.mSecBrightnessSliderController = new SecBrightnessSliderController((BrightnessSliderView) this.mView, r1);
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final int getMax() {
        return ((BrightnessSliderView) this.mView).mSlider.getMax();
    }

    public final View getRootView() {
        return this.mView;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final int getValue() {
        return ((BrightnessSliderView) this.mView).mSlider.getProgress();
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final boolean mirrorTouchEvent(MotionEvent motionEvent) {
        if (this.mMirror == null) {
            return ((BrightnessSliderView) this.mView).dispatchTouchEvent(motionEvent);
        }
        MotionEvent motionEventCopy = motionEvent.copy();
        ToggleSlider toggleSlider = this.mMirror;
        boolean zMirrorTouchEvent = toggleSlider != null ? toggleSlider.mirrorTouchEvent(motionEventCopy) : false;
        motionEventCopy.recycle();
        return zMirrorTouchEvent;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        BrightnessSliderView brightnessSliderView = (BrightnessSliderView) this.mView;
        brightnessSliderView.mSlider.setOnSeekBarChangeListener(this.mSeekListener);
        BrightnessSliderView brightnessSliderView2 = (BrightnessSliderView) this.mView;
        brightnessSliderView2.mOnInterceptListener = this.mOnInterceptListener;
        if (this.mMirror != null) {
            brightnessSliderView2.mListener = new BrightnessSliderController$$ExternalSyntheticLambda0(this);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((BrightnessSliderView) this.mView).mSlider.setOnSeekBarChangeListener(null);
        BrightnessSliderView brightnessSliderView = (BrightnessSliderView) this.mView;
        brightnessSliderView.mListener = null;
        brightnessSliderView.mOnInterceptListener = null;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setEnforcedAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        if (enforcedAdmin == null) {
            ToggleSeekBar toggleSeekBar = ((BrightnessSliderView) this.mView).mSlider;
            toggleSeekBar.mAdminBlocker = null;
            toggleSeekBar.setEnabled(true);
        } else {
            BrightnessSliderView brightnessSliderView = (BrightnessSliderView) this.mView;
            BrightnessSliderController$$ExternalSyntheticLambda1 brightnessSliderController$$ExternalSyntheticLambda1 = new BrightnessSliderController$$ExternalSyntheticLambda1(this, enforcedAdmin);
            ToggleSeekBar toggleSeekBar2 = brightnessSliderView.mSlider;
            toggleSeekBar2.mAdminBlocker = brightnessSliderController$$ExternalSyntheticLambda1;
            toggleSeekBar2.setEnabled(false);
        }
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setMax(int i) {
        BrightnessSliderView brightnessSliderView = (BrightnessSliderView) this.mView;
        brightnessSliderView.mSlider.setMax(i);
        SecBrightnessSliderView secBrightnessSliderView = brightnessSliderView.mSecBrightnessSliderView;
        if (secBrightnessSliderView != null) {
            secBrightnessSliderView.updateSliderResources();
        }
        ToggleSlider toggleSlider = this.mMirror;
        if (toggleSlider != null) {
            toggleSlider.setMax(i);
        }
    }

    public final void setMirror(ToggleSlider toggleSlider) {
        this.mMirror = toggleSlider;
        if (toggleSlider == null) {
            ((BrightnessSliderView) this.mView).mListener = null;
            return;
        }
        toggleSlider.setMax(((BrightnessSliderView) this.mView).mSlider.getMax());
        this.mMirror.setValue(((BrightnessSliderView) this.mView).mSlider.getProgress());
        ((BrightnessSliderView) this.mView).mListener = new BrightnessSliderController$$ExternalSyntheticLambda0(this);
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setOnChangedListener(ToggleSlider.Listener listener) {
        this.mListener = listener;
    }

    @Override // com.android.systemui.settings.brightness.ToggleSlider
    public final void setValue(int i) {
        ((BrightnessSliderView) this.mView).mSlider.setProgress(i);
        ToggleSlider toggleSlider = this.mMirror;
        if (toggleSlider != null) {
            toggleSlider.setValue(i);
        }
        SecBrightnessSliderController secBrightnessSliderController = this.mSecBrightnessSliderController;
        if (secBrightnessSliderController != null) {
            ((BrightnessAnimationIcon) secBrightnessSliderController.brightnessIcon$delegate.getValue()).play(i, ((BrightnessSliderView) this.mView).mSlider.getMax());
        }
    }
}
