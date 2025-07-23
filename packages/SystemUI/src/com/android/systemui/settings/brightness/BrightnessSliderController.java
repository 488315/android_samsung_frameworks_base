package com.android.systemui.settings.brightness;

import android.content.Context;
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
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.time.SystemClock;
import com.google.android.msdl.domain.MSDLPlayer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [android.widget.SeekBar$OnSeekBarChangeListener, com.android.systemui.settings.brightness.BrightnessSliderController$2] */
    public BrightnessSliderController(BrightnessSliderView brightnessSliderView, FalsingManager falsingManager, UiEventLogger uiEventLogger, HapticSliderPlugin hapticSliderPlugin, ActivityStarter activityStarter, BrightnessWarningToast brightnessWarningToast) {
        super(brightnessSliderView);
        this.mOnInterceptListener = new AnonymousClass1();
        ?? r1 = new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessSliderController.2
            /* JADX WARN: Code restructure failed: missing block: B:57:0x014b, code lost:
            
                if (((r0 == null || (r0 = r0.getThumb()) == null) ? 0 : r0.getAlpha()) == 255) goto L58;
             */
            /* JADX WARN: Removed duplicated region for block: B:61:0x0155  */
            /* JADX WARN: Removed duplicated region for block: B:64:0x0165  */
            /* JADX WARN: Removed duplicated region for block: B:69:0x0159  */
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onProgressChanged(android.widget.SeekBar r6, int r7, boolean r8) {
                /*
                    Method dump skipped, instructions count: 392
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.settings.brightness.BrightnessSliderController.AnonymousClass2.onProgressChanged(android.widget.SeekBar, int, boolean):void");
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
        MotionEvent copy = motionEvent.copy();
        ToggleSlider toggleSlider = this.mMirror;
        boolean mirrorTouchEvent = toggleSlider != null ? toggleSlider.mirrorTouchEvent(copy) : false;
        copy.recycle();
        return mirrorTouchEvent;
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
